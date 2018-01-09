package com.example.asus.strokeanalyzer.Model;

import android.content.Context;
import android.util.Log;

import com.example.asus.strokeanalyzer.Model.Analyzers.DragonAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.HatAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.StrokeBricksAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.TreatmentAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.iScoreAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
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
    public long PatientNumber;

    public Map<Integer, Answer> PatientAnswers;

    public int getNihss()
    {
        NihssExamination examination = getLatestNihssExamination();
        if(examination == null)
            return -1;
        return NihssAnalyzer.CountNihssSum(examination);
    }

    public int getNihssOnAdmission() {
        NihssExamination examination = NihssService.getEarliestNihssExaminationForPatient(Id);
        if(examination == null)
            return -1;
        return NihssAnalyzer.CountNihssSum(examination);
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

    @Override
    public boolean equals(Object p)
    {
        return ((Patient)p).Id == Id;
    }

    public void GenerateReport(Context context) {
        Document doc = new Document();

        try {
            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "Raport"+PatientNumber+".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Font paraFont= new Font(Font.FontFamily.COURIER);
            String noResult = "Brak wyniku.";

            Paragraph p1 = new Paragraph("Nr pacjenta "+PatientNumber);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            doc.add(p1);

            Paragraph p2 = new Paragraph("Imię: " + Name);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont);
            doc.add(p2);

            Paragraph p3 = new Paragraph("Nazwisko: " + Surname);
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            p3.setFont(paraFont);
            doc.add(p3);

            String nihssResult = getNihss() >= 0? ((Integer)getNihss()).toString() : noResult;
            Paragraph p4 = new Paragraph("Wynik NIHSS: " + nihssResult);
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            p4.setFont(paraFont);
            doc.add(p4);

            String treatmentDecisionText = noResult;
            if (getTreatmentDecision() != null) {
                TreatmentResult treatmentResult = getTreatmentDecision();
                treatmentDecisionText = (getTreatmentDecision().Decision ? "TAK":"NIE");
                if (treatmentResult.Decision) {
                    treatmentDecisionText = "TAK";
                } else {
                    treatmentDecisionText = "NIE\nPowody wykluczenia:\n";
                    for (Answer badAnswer:
                         treatmentResult.badAnswers) {
                        treatmentDecisionText += FormsStructure.Questions.get(badAnswer.GetQuestionID()).GetText()+"\n";
                    }
                }
            }
            Paragraph p5 = new Paragraph("Kwalifikacja do leczenia? " + treatmentDecisionText);
            p5.setAlignment(Paragraph.ALIGN_LEFT);
            p5.setFont(paraFont);
            doc.add(p5);

            String hatPrognosisText = noResult;
            if (getHatPrognosis() != null) {
                hatPrognosisText = "Ryzyko wylewu - " +
                getHatPrognosis().RiskOfSymptomaticICH+
                        "%\nRyzyko śmiertelnego wylewu - "+
                        getHatPrognosis().RiskOfFatalICH+"%";
            }
            Paragraph p6 = new Paragraph("Rokowania HAT:\n" + hatPrognosisText);
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            p6.setFont(paraFont);
            doc.add(p6);

            String dragonPrognosisText = noResult;
            if (getDragonPrognosis() != null) {
                dragonPrognosisText = "Prawdopodobieństwo dobrego wyniku (mRS < 3) - " +
                getDragonPrognosis().GoodOutcomePrognosis+
                        "%\nPrawdopodobieństwo złego wyniku (mRS > 4) - "+
                        getDragonPrognosis().MiserableOutcomePrognosis+"%";
            }
            Paragraph p7 = new Paragraph("Rokowania DRAGON:\n" + dragonPrognosisText);
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont);
            doc.add(p7);

            String iscoreResultText = noResult;
            if (getIscorePrognosis() != null) {
                iscoreResultText = "Prawdopodobieństwo śmierci po 30 dniach - \" +\n" +
                        "                    getIscorePrognosis().PrognosisFor30Days+\n" +
                        "                    \"%\\nPrawdopodobieństwo śmierci po roku - \"+\n" +
                        "                    getIscorePrognosis().PrognosisFor1Year+\"%";
            }
            Paragraph p8 = new Paragraph("Rokowania iScore:\n" + iscoreResultText);
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            p8.setFont(paraFont);
            doc.add(p8);


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
