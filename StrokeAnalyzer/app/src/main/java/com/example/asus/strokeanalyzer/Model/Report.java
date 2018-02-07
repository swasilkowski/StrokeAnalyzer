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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Klasa wykorzystywana do generowania raportu dla pacjenta zawierającego wyniki analiz przeprowadzanych
 * w aplikacji.
 *
 * @author Stanisław Wasilkowski
 */

public final class Report {

    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     *
     */
    private Report(){

    }

    /**
     * Metoda pozwalająca na wygenerowanie raportu zawierającego dane pacjenta. Funkcja tworzy
     * katalog 'Dir', w którym przechowywane będą wygenerowane raporty. Następnie generuje dokument,
     * do którego wprowadzane są dane zawarte w profilu pacjenta. Plik zapisywany jest w wygenerowanym katalogu.
     *
     * @param patient obiekt klasy Patient, zawierający dane pacjenta, które mają zostać umieszczone w raporcie
     * @param context kontekst aplikacji niezbędny do stworzenia nowego pliku z danymi
     */
    public static String GenerateReport(Patient patient, Context context)
    {
        Document doc = new Document();
        String filePath =null;

        try {
            String path = context.getExternalFilesDir(null).getAbsolutePath() + "/" + context.getString(R.string.reports_folder_name);

            File dir = new File(path);
            if(!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException();
                }
            }

            File file = new File(dir, context.getString(R.string.report) + patient.PatientNumber + context.getString(R.string.report_extension));
            if (!file.createNewFile()) {
                throw new IOException();
            }
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            BaseFont baseFont = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font paraFont = new Font(baseFont, 12);
            String noResult = context.getString(R.string.report_no_result);

            Paragraph p1 = new Paragraph(context.getString(R.string.report_patient_number) + " " + patient.PatientNumber, paraFont);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);

            Paragraph p2 = new Paragraph(context.getString(R.string.report_name) + patient.Name, paraFont);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p2);

            Paragraph p3 = new Paragraph(context.getString(R.string.report_surname) + patient.Surname + "\n\n", paraFont);
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p3);

            String nihssResult = patient.getNihss() >= 0? ((Integer)patient.getNihss()).toString() : noResult;
            Paragraph p4 = new Paragraph(context.getString(R.string.report_nihss_result) + nihssResult + "\n\n", paraFont);
            p4.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p4);

            String treatmentDecisionText = noResult;
            TreatmentResult treatmentResult = patient.getTreatmentDecision();
            if (treatmentResult != null) {
                if (treatmentResult.Decision) {
                    treatmentDecisionText = context.getString(R.string.report_yes) + " \n";
                } else {
                    treatmentDecisionText = context.getString(R.string.report_no) + " \n"
                            + context.getString(R.string.report_treatment_exclusion_reasons) + "\n";

                    StringBuilder sb = new StringBuilder(treatmentDecisionText);
                    for (Answer badAnswer:
                            treatmentResult.badAnswers) {
                        sb.append(FormsStructure.Questions.get(badAnswer.GetQuestionID()).GetText()).append("\n");
                    }
                    treatmentDecisionText = sb.toString();
                }
            }
            Paragraph p5 = new Paragraph(context.getString(R.string.report_qualification_for_treatment) + treatmentDecisionText + "\n", paraFont);
            p5.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p5);

            String hatPrognosisText = noResult;
            if (patient.getHatPrognosis() != null) {
                hatPrognosisText = context.getString(R.string.report_haemorrhage_risk) +
                        patient.getHatPrognosis().RiskOfSymptomaticICH+
                        "%\n" + context.getString(R.string.report_fatal_haemorrhage_risk) +
                        patient.getHatPrognosis().RiskOfFatalICH+"%";
            }
            Paragraph p6 = new Paragraph(context.getString(R.string.report_hat_prognosis) + "\n" + hatPrognosisText + "\n\n", paraFont);
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p6);

            String dragonPrognosisText = noResult;
            if (patient.getDragonPrognosis() != null) {
                dragonPrognosisText = context.getString(R.string.report_probability_of_good_outcome) +
                        patient.getDragonPrognosis().GoodOutcomePrognosis+
                        "%\n" + context.getString(R.string.report_probability_of_miserable_outcome) +
                        patient.getDragonPrognosis().MiserableOutcomePrognosis+"%";
            }
            Paragraph p7 = new Paragraph(context.getString(R.string.report_dragon_prognosis) + "\n" + dragonPrognosisText + "\n\n", paraFont);
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p7);

            String iscoreResultText = noResult;
            if (patient.getIscorePrognosis() != null) {
                iscoreResultText = context.getString(R.string.report_probability_of_30_days_death) +
                        patient.getIscorePrognosis().PrognosisFor30Days+"%\n" +
                        context.getString(R.string.report_probability_of_1_year_death) +
                        patient.getIscorePrognosis().PrognosisFor1Year+"%";
            }
            Paragraph p8 = new Paragraph(context.getString(R.string.report_iscore_prognosis) + "\n" + iscoreResultText + "\n\n", paraFont);
            p8.setAlignment(Paragraph.ALIGN_LEFT);
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
            Paragraph p9 = new Paragraph(context.getString(R.string.report_stroke_bricks) + "\n" + strokeBrickResultText, paraFont);
            p9.setAlignment(Paragraph.ALIGN_LEFT);
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
        } catch (NullPointerException e) {
            Log.e("Report", "nullPointerException:" + e);
        }
        finally {
            doc.close();
        }

        return filePath;
    }

}
