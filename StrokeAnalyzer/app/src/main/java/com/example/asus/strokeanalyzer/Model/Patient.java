package com.example.asus.strokeanalyzer.Model;

import android.os.Environment;
import android.util.Log;

import com.example.asus.strokeanalyzer.Model.Analyzers.DragonAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.HatAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.StrokeBricksAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.TreatmentAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.iScoreAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.results.DragonResult;
import com.example.asus.strokeanalyzer.Model.results.HatResult;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;
import com.example.asus.strokeanalyzer.Model.results.iScoreResult;
import com.example.asus.strokeanalyzer.Services.NihssService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 20.11.2017.
 */

public class Patient {

    public int Id;
    public String Name;
    public String Surname;
    public int PatientNumber;

    public Map<Integer, Answer> PatientAnswers;

    public int getNihss() {
        return NihssAnalyzer.CountNihssSum(getLatestNihssExamination());
    }

    public int getNihssOnAdmission() {
        return NihssAnalyzer.CountNihssSum(NihssService.getEarliestNihssExaminationForPatient(Id));
    }

    public List<Region> getStrokeBricksAffectedRegions() {
        return StrokeBricksAnalyzer.AnalyzeRegionsAffection(this);
    }

    public TreatmentResult getTreatmentDecision() {
        return TreatmentAnalyzer.MakeTreatmentDecision(this);
    }

    public HatResult getHatPrognosis() {
        return HatAnalyzer.AnalyzePrognosis(this);
    }
    public iScoreResult getIscorePrognosis() {
        return iScoreAnalyzer.AnalyzePrognosis(this);
    }
    public DragonResult getDragonPrognosis() {
        return DragonAnalyzer.AnalyzePrognosis(this);
    }

    public Patient() {
        PatientAnswers = new Hashtable<>();
    }

    public void GenarateReport() {
        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph("blalblabla");
            Font paraFont= new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

    }

    public List<NihssExamination> getNihssHistory() {
        return NihssService.getNihssExaminationsForPatient(Id);
    }

    public NihssExamination getLatestNihssExamination() {
        return NihssService.getLatestNihssExaminationForPatient(Id);
    }

    public void addNihssExamination(NihssExamination nihssExamination) {
        NihssService.addNihssExaminationForPatient(nihssExamination, Id);
    }
}
