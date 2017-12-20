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
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

            File dir = new File(path);
            boolean mkdirs = true;
            if(!dir.exists()) {
                mkdirs = dir.mkdirs();
            }

            boolean d = dir.exists();
            File file = new File(dir, "Raport"+PatientNumber+".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Font paraFont= new Font(Font.FontFamily.COURIER);

            Paragraph p1 = new Paragraph("Nr pacjenta "+PatientNumber);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            Paragraph p2 = new Paragraph("Imię: " + Name);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont);

            Paragraph p3 = new Paragraph("Nazwisko: " + Surname);
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            p3.setFont(paraFont);

            Paragraph p4 = new Paragraph("Wynik NIHSS: " + getNihss());
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            p4.setFont(paraFont);

            Paragraph p5 = new Paragraph("Kwalifikacja do leczenia? " + (getTreatmentDecision().Decision ? "TAK":"NIE"));
            p5.setAlignment(Paragraph.ALIGN_LEFT);
            p5.setFont(paraFont);

            Paragraph p6 = new Paragraph("Rokowania HAT:\n Ryzyko wylewu - " +
                    getHatPrognosis().RiskOfSymptomaticICH+
                    "%\nRyzyko śmiertelnego wylewu - "+
                    getHatPrognosis().RiskOfFatalICH+"%");
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            p6.setFont(paraFont);

            Paragraph p7 = new Paragraph("Rokowania DRAGON:\n Prawdopodobieństwo dobrego wyniku (mRS < 3) - " +
                    getDragonPrognosis().GoodOutcomePrognosis+
                    "%\nPrawdopodobieństwo złego wyniku (mRS > 4) - "+
                    getDragonPrognosis().MiserableOutcomePrognosis+"%");
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont);

            Paragraph p8 = new Paragraph("Rokowania iScore:\n Prawdopodobieństwo śmierci po 30 dniach - " +
                    getIscorePrognosis().PrognosisFor30Days+
                    "%\nPrawdopodobieństwo śmierci po roku - "+
                    getIscorePrognosis().PrognosisFor1Year+"%");
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            p8.setFont(paraFont);

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
