package com.example.asus.strokeanalyzer.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;
import com.example.asus.strokeanalyzer.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/" + context.getString(R.string.reports_folder_name);

            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, context.getString(R.string.report) + patient.PatientNumber + context.getString(R.string.report_extension));
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Font paraFont= new Font(Font.FontFamily.COURIER);
            String noResult = String.valueOf(R.string.report_no_result);

            Paragraph p1 = new Paragraph(context.getString(R.string.report_patient_number) + " " + patient.PatientNumber);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            doc.add(p1);

            Paragraph p2 = new Paragraph(context.getString(R.string.report_name) + patient.Name);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont);
            doc.add(p2);

            Paragraph p3 = new Paragraph(context.getString(R.string.report_surname) + patient.Surname + "\n\n");
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            p3.setFont(paraFont);
            doc.add(p3);

            String nihssResult = patient.getNihss() >= 0? ((Integer)patient.getNihss()).toString() : noResult;
            Paragraph p4 = new Paragraph(context.getString(R.string.report_nihss_result) + nihssResult + "\n\n");
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            p4.setFont(paraFont);
            doc.add(p4);

            String treatmentDecisionText = noResult;
            if (patient.getTreatmentDecision() != null) {
                TreatmentResult treatmentResult = patient.getTreatmentDecision();
                if (treatmentResult.Decision) {
                    treatmentDecisionText = context.getString(R.string.report_yes);
                } else {
                    treatmentDecisionText = context.getString(R.string.report_no) + "\n"
                            + context.getString(R.string.report_treatment_exclusion_reasons) + "\n";

                    StringBuilder sb = new StringBuilder(treatmentDecisionText);
                    for (Answer badAnswer:
                            treatmentResult.badAnswers) {
                        sb.append(FormsStructure.Questions.get(badAnswer.GetQuestionID()).GetText()).append("\n");
                    }
                    treatmentDecisionText = sb.toString();
                }
            }
            Paragraph p5 = new Paragraph(context.getString(R.string.report_qualification_for_treatment) + treatmentDecisionText + "\n");
            p5.setAlignment(Paragraph.ALIGN_LEFT);
            p5.setFont(paraFont);
            doc.add(p5);

            String hatPrognosisText = noResult;
            if (patient.getHatPrognosis() != null) {
                hatPrognosisText = context.getString(R.string.report_haemorrhage_risk) +
                        patient.getHatPrognosis().RiskOfSymptomaticICH+
                        "%\n" + context.getString(R.string.report_fatal_haemorrhage_risk) +
                        patient.getHatPrognosis().RiskOfFatalICH+"%";
            }
            Paragraph p6 = new Paragraph(context.getString(R.string.report_hat_prognosis) + "\n" + hatPrognosisText + "\n\n");
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            p6.setFont(paraFont);
            doc.add(p6);

            String dragonPrognosisText = noResult;
            if (patient.getDragonPrognosis() != null) {
                dragonPrognosisText = context.getString(R.string.report_probability_of_good_outcome) +
                        patient.getDragonPrognosis().GoodOutcomePrognosis+
                        "%\n" + context.getString(R.string.report_probability_of_miserable_outcome) +
                        patient.getDragonPrognosis().MiserableOutcomePrognosis+"%";
            }
            Paragraph p7 = new Paragraph(context.getString(R.string.report_dragon_prognosis) + "\n" + dragonPrognosisText + "\n\n");
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont);
            doc.add(p7);

            String iscoreResultText = noResult;
            if (patient.getIscorePrognosis() != null) {
                iscoreResultText = context.getString(R.string.report_probability_of_30_days_death) +
                        patient.getIscorePrognosis().PrognosisFor30Days+"%\n" +
                        context.getString(R.string.report_probability_of_1_year_death) +
                        patient.getIscorePrognosis().PrognosisFor1Year+"%";
            }
            Paragraph p8 = new Paragraph(context.getString(R.string.report_iscore_prognosis) + "\n" + iscoreResultText + "\n\n");
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            p8.setFont(paraFont);
            doc.add(p8);

            String strokeBrickResultText = noResult;
            List<Region> regions = patient.getStrokeBricksAffectedRegions();
            if (regions != null) {
                strokeBrickResultText = "";
                StringBuilder sb = new StringBuilder(strokeBrickResultText);
                for (Region region:
                     regions) {
                    sb.append(region.toString()).append("\n");
                }
                strokeBrickResultText = sb.toString();
            }
            Paragraph p9 = new Paragraph(context.getString(R.string.report_stroke_bricks) + "\n" + strokeBrickResultText);
            p9.setAlignment(Paragraph.ALIGN_LEFT);
            p9.setFont(paraFont);
            doc.add(p9);

            CTPictures.InitializeCTPictures(context);
            Bitmap[] sbImages = CTPictures.GenerateOutputImage(regions);
            for (Bitmap bmp:
                 sbImages) {
                Bitmap bm = Bitmap.createScaledBitmap(bmp, 340, 340, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image myImg = Image.getInstance(stream.toByteArray());
               myImg.setAlignment(Image.MIDDLE);
                doc.add(myImg);
            }

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
