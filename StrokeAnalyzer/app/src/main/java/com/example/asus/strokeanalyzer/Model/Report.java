package com.example.asus.strokeanalyzer.Model;

import android.content.Context;
import android.util.Log;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Asus on 13.01.2018.
 */

public final class Report {

    private Report(){

    }

    /**
     *     /**
     * Metoda pozwalająca na wygenerowanie raportu zawierającego dane pacjenta.
     * Funkcja tworzy katalog 'Dir', w którym przechowywane będą wygenerowane raporty.
     * Następnie generuje dokument, do którego wprowadzane są dane zawarte w profilu pacjenta.
     * Plik zapisywany jest w wygenerowanym katalogu
     * @param patient Obiekt klasy Patient, zawierająca dane pacjenta, które mają zostać zawarte w raporcie
     * @param context Kontekst aplikacji niezbędny do stworzenia nowego pliku z danymi
     */
    public static String GenerateReport(Patient patient, Context context)
    {
        Document doc = new Document();
        String filePath =null;

        try {
            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "Raport"+patient.PatientNumber+".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Font paraFont= new Font(Font.FontFamily.COURIER);
            String noResult = "Brak wyniku.";

            Paragraph p1 = new Paragraph("Nr pacjenta "+patient.PatientNumber);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            doc.add(p1);

            Paragraph p2 = new Paragraph("Imię: " + patient.Name);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont);
            doc.add(p2);

            Paragraph p3 = new Paragraph("Nazwisko: " + patient.Surname);
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            p3.setFont(paraFont);
            doc.add(p3);

            String nihssResult = patient.getNihss() >= 0? ((Integer)patient.getNihss()).toString() : noResult;
            Paragraph p4 = new Paragraph("Wynik NIHSS: " + nihssResult);
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            p4.setFont(paraFont);
            doc.add(p4);

            String treatmentDecisionText = noResult;
            if (patient.getTreatmentDecision() != null) {
                TreatmentResult treatmentResult = patient.getTreatmentDecision();
                treatmentDecisionText = (patient.getTreatmentDecision().Decision ? "TAK":"NIE");
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
            if (patient.getHatPrognosis() != null) {
                hatPrognosisText = "Ryzyko wylewu - " +
                        patient.getHatPrognosis().RiskOfSymptomaticICH+
                        "%\nRyzyko śmiertelnego wylewu - "+
                        patient.getHatPrognosis().RiskOfFatalICH+"%";
            }
            Paragraph p6 = new Paragraph("Rokowania HAT:\n" + hatPrognosisText);
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            p6.setFont(paraFont);
            doc.add(p6);

            String dragonPrognosisText = noResult;
            if (patient.getDragonPrognosis() != null) {
                dragonPrognosisText = "Prawdopodobieństwo dobrego wyniku (mRS < 3) - " +
                        patient.getDragonPrognosis().GoodOutcomePrognosis+
                        "%\nPrawdopodobieństwo złego wyniku (mRS > 4) - "+
                        patient.getDragonPrognosis().MiserableOutcomePrognosis+"%";
            }
            Paragraph p7 = new Paragraph("Rokowania DRAGON:\n" + dragonPrognosisText);
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont);
            doc.add(p7);

            String iscoreResultText = noResult;
            if (patient.getIscorePrognosis() != null) {
                iscoreResultText = "Prawdopodobieństwo śmierci po 30 dniach - \" +\n" +
                        "                    getIscorePrognosis().PrognosisFor30Days+\n" +
                        "                    \"%\\nPrawdopodobieństwo śmierci po roku - \"+\n" +
                        "                    getIscorePrognosis().PrognosisFor1Year+\"%";
            }
            Paragraph p8 = new Paragraph("Rokowania iScore:\n" + iscoreResultText);
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            p8.setFont(paraFont);
            doc.add(p8);

            filePath=file.getPath();


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        return filePath;
    }

}
