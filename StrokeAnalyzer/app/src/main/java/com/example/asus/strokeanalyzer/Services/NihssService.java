package com.example.asus.strokeanalyzer.Services;

import com.example.asus.strokeanalyzer.Database.DatabaseAccess;
import com.example.asus.strokeanalyzer.Database.StrokeAnalyzerDatabase;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Klasa udostępniająca operacje na bazie danych związane z tablą zawierająca badania w skali NIHSS.
 *
 * @author Stanisław Wasilkowski
 */

public class NihssService {
    final private static StrokeAnalyzerDatabase db = DatabaseAccess.getInstance().database;

    /**
     * Metoda zwracająca z bazy danych listę badań w skali NIHSS dla pacjenta o podanym id.
     *
     * @param patientId id pacjenta, którego badania mają zostać pobrane z bazy danych
     * @return lista badań w skali NIHSS pacjenta o podanym id
     */
    public static List<NihssExamination> getNihssExaminationsForPatient(int patientId){
        List<NihssExamination> examinations = new ArrayList<>();

        List<com.example.asus.strokeanalyzer.Entities.NihssExamination> entities;
        entities = db.nihssDao().SelectByPatientId(patientId);

        for (com.example.asus.strokeanalyzer.Entities.NihssExamination entity:
             entities) {
            examinations.add(EntityToModel(entity));
        }

        return examinations;
    }

    /**
     * Metoda zwracająca z bazy danych najnowsze badanie w skali NIHSS dla pacjenta o podanym id.
     *
     * @param patientId id pacjenta, którego badanie ma zostać pobrane z bazy danych
     * @return obiekt zawierający dane najświeższego badania w skali NIHSS
     */
    public static NihssExamination getLatestNihssExaminationForPatient(int patientId) {
        com.example.asus.strokeanalyzer.Entities.NihssExamination entity;
        entity = db.nihssDao().SelectLatestByPatientId(patientId);
        return EntityToModel(entity);
    }

    /**
     * Metoda zwracająca z bazy danych nastarsze badanie w skali NIHSS dla pacjenta o podany id.
     *
     * @param patientId id pacjenta, którego badanie ma zostać pobrane z bazy danych
     * @return obiekt zawierający dane nastarszego badania w skali NIHSS
     */
    public static NihssExamination getEarliestNihssExaminationForPatient(int patientId) {
        com.example.asus.strokeanalyzer.Entities.NihssExamination entity;
        entity = db.nihssDao().SelectEarliestByPatientId(patientId);
        return EntityToModel(entity);
    }

    /**
     * Metoda umożliwiająca dodanie do bazy danych nowego badania w skali NIHSS dla pacjenta o podanym id.
     *
     * @param examination obiekt przechowujący dane badania w skali NIHSS
     * @param patientId id pacjenta, dla którego badanie w skali NIHSS chcemy dodać do bazy danych
     * @return id badanie w bazie danych
     */
    public static long addNihssExaminationForPatient(NihssExamination examination, int patientId){
        return db.nihssDao().insert(ModelToEntity(examination, patientId));
    }

    /**
     * Metoda mapująca pojedyncze badanie w skalii NIHSS z modelu aplikacji na obiekty
     * typu Entities.NihssExamination przechowywane w tabeli z badaniami w skali NIHSS.
     *
     * @param model obiekt klasy Model.NihssExamination, który ma zostać zmapowany na obiekt bazodanowy
     * @param patientId id pacjenta, którego badanie ma zostać przekonwertowane do obiektu bazodanowego
     * @return zmapowany obiekt, zawierający dane pojedynczego badania w skali NIHSS
     * @throws IndexOutOfBoundsException niepoprawny indeks pytania, na które została udzielona odpowiedź
     *                                      (najprawdopodobniej nie dotyczy badania NIHSS)
     */
    private static com.example.asus.strokeanalyzer.Entities.NihssExamination
        ModelToEntity(NihssExamination model, int patientId) throws IndexOutOfBoundsException {

        if (model == null) {
            return null;
        }
        com.example.asus.strokeanalyzer.Entities.NihssExamination entity;
        entity = new com.example.asus.strokeanalyzer.Entities.NihssExamination();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.Date);
        entity.addedOn = calendar.getTimeInMillis();

        entity.PatientId = patientId;

        for (Answer answer:
             model.Answers) {
            NumericAnswer numericAnswer = answer instanceof NumericAnswer ? ((NumericAnswer) answer) : null;
            switch (numericAnswer.GetQuestionID()){
                case 101:
                    entity.Question1 = numericAnswer.Value;
                    break;
                case 102:
                    entity.Question2 = numericAnswer.Value;
                    break;
                case 103:
                    entity.Question3 = numericAnswer.Value;
                    break;
                case 104:
                    entity.Question4 = numericAnswer.Value;
                    break;
                case 105:
                    entity.Question5 = numericAnswer.Value;
                    break;
                case 106:
                    entity.Question6 = numericAnswer.Value;
                    break;
                case 107:
                    entity.Question7 = numericAnswer.Value;
                    break;
                case 108:
                    entity.Question8 = numericAnswer.Value;
                    break;
                case 109:
                    entity.Question9 = numericAnswer.Value;
                    break;
                case 110:
                    entity.Question10 = numericAnswer.Value;
                    break;
                case 111:
                    entity.Question11 = numericAnswer.Value;
                    break;
                case 112:
                    entity.Question12 = numericAnswer.Value;
                    break;
                case 113:
                    entity.Question13 = numericAnswer.Value;
                    break;
                case 114:
                    entity.Question14 = numericAnswer.Value;
                    break;
                case 115:
                    entity.Question15 = numericAnswer.Value;
                    break;
                case 116:
                    entity.DominantHemisphere = numericAnswer.Value;
                    break;
                case 117:
                    entity.SymptomsSide = numericAnswer.Value;
                    break;
                default:
                    throw new IndexOutOfBoundsException();
            }
        }

        return entity;
    }

    /**
     * Metoda mapująca badanie w skali NIHSS typu Entites.NihssExamination przechowywane w tabeli bazy danych
     * zawierającej badania tej skali na obiekty klasy Model.NihssExamination.
     *
     * @param entity obiekt bazodanowy przechowujący badanie w skali NIHSS, które ma zostać zmapowane na obiekt modelu aplikacji
     * @return zmapowany obiekt modelu aplikacji przechowujący dane dotyczące
     *          pojedynczego badania w skali NIHSS
     */
    private static NihssExamination EntityToModel(com.example.asus.strokeanalyzer.Entities.NihssExamination entity){
        if (entity == null) {
            return null;
        }

        NihssExamination model = new NihssExamination();

        model.Date = new Date(entity.addedOn);
        model.Answers = new ArrayList<>();

        NumericAnswer answer;
        answer = new NumericAnswer(101);
        answer.Value = entity.Question1;
        model.Answers.add(answer);
        answer = new NumericAnswer(102);
        answer.Value = entity.Question2;
        model.Answers.add(answer);
        answer = new NumericAnswer(103);
        answer.Value = entity.Question3;
        model.Answers.add(answer);
        answer = new NumericAnswer(104);
        answer.Value = entity.Question4;
        model.Answers.add(answer);
        answer = new NumericAnswer(105);
        answer.Value = entity.Question5;
        model.Answers.add(answer);
        answer = new NumericAnswer(106);
        answer.Value = entity.Question6;
        model.Answers.add(answer);
        answer = new NumericAnswer(107);
        answer.Value = entity.Question7;
        model.Answers.add(answer);
        answer = new NumericAnswer(108);
        answer.Value = entity.Question8;
        model.Answers.add(answer);
        answer = new NumericAnswer(109);
        answer.Value = entity.Question9;
        model.Answers.add(answer);
        answer = new NumericAnswer(110);
        answer.Value = entity.Question10;
        model.Answers.add(answer);
        answer = new NumericAnswer(111);
        answer.Value = entity.Question11;
        model.Answers.add(answer);
        answer = new NumericAnswer(112);
        answer.Value = entity.Question12;
        model.Answers.add(answer);
        answer = new NumericAnswer(113);
        answer.Value = entity.Question13;
        model.Answers.add(answer);
        answer = new NumericAnswer(114);
        answer.Value = entity.Question14;
        model.Answers.add(answer);
        answer = new NumericAnswer(115);
        answer.Value = entity.Question15;
        model.Answers.add(answer);
        answer = new NumericAnswer(116);
        answer.Value = entity.DominantHemisphere;
        model.Answers.add(answer);
        answer = new NumericAnswer(117);
        answer.Value = entity.SymptomsSide;
        model.Answers.add(answer);

        return model;
    }
}
