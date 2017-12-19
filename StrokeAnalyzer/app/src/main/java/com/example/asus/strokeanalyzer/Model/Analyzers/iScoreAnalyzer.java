package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedNumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedTextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedTrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class iScoreAnalyzer {

    //key - prognosis sum of points, value - description for a particular sum
    //prognosisDescription - contains descriptions for particular results of a scale analysis
    private static Dictionary<Integer, String> prognosisDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor30Days;
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor1Year;

    private iScoreAnalyzer() {}

    public static double AnalyzePrognosisFor30Days(Patient p)
    {
        int iScorePoints = countPoints(p, correctAnswersFor30Days);
        int nihssScore = NihssAnalyzer.CountNihssSum(p.getLatestNihssExamination());
        if (nihssScore > 22) {
            iScorePoints += 105;
        }
        if (nihssScore <= 22 && nihssScore >= 14) {
            iScorePoints += 65;
        }
        if (nihssScore < 14 && nihssScore >= 9) {
            iScorePoints += 40;
        }
        return getPredictionFor30Days(iScorePoints);
    }

    public static double AnalyzePrognosisFor1Year(Patient p)
    {
        int iScorePoints = countPoints(p, correctAnswersFor30Days);
        int nihssScore = NihssAnalyzer.CountNihssSum(p.getLatestNihssExamination());
        if (nihssScore > 22) {
            iScorePoints += 70;
        }
        if (nihssScore <= 22 && nihssScore >= 14) {
            iScorePoints += 40;
        }
        if (nihssScore < 14 && nihssScore >= 9) {
            iScorePoints += 25;
        }
        return getPredictionFor30Days(iScorePoints);
    }

    private static double getPredictionFor30Days(int points) {
        if (points < 59) {
            return 0;
        }
        if (points >= 59 && points <= 70) {
            return 0.43;
        }
        if (points >= 71 && points <= 80) {
            return 0.73;
        }
        if (points >= 81 && points <= 90) {
            return 0.89;
        }
        if (points >= 91 && points <= 100) {
            return 1.33;
        }
        if (points >= 101 && points <= 110) {
            return 1.87;
        }
        if (points >= 111 && points <= 120) {
            return 2.58;
        }
        if (points >= 121 && points <= 130) {
            return 3.73;
        }
        if (points >= 131 && points <= 140) {
            return 5.03;
        }
        if (points >= 141 && points <= 150) {
            return 7.39;
        }
        if (points >= 151 && points <= 160) {
            return 9.78;
        }
        if (points >= 161 && points <= 170) {
            return 13.1;
        }
        if (points >= 171 && points <= 180) {
            return 18.0;
        }
        if (points >= 181 && points <= 190) {
            return 24.2;
        }
        if (points >= 191 && points <= 200) {
            return 33.0;
        }
        if (points >= 201 && points <= 210) {
            return 39.2;
        }
        if (points >= 211 && points <= 220) {
            return 49.2;
        }
        if (points >= 221 && points <= 230) {
            return 57.6;
        }
        if (points >= 231 && points <= 240) {
            return 65.7;
        }
        if (points >= 241 && points <= 250) {
            return 80.0;
        }
        if (points >= 251 && points <= 260) {
            return 84.2;
        }
        if (points >= 261 && points <= 270) {
            return 90.0;
        }
        if (points >= 271 && points <= 280) {
            return 90.2;
        }
        if (points >= 281 && points <= 285) {
            return 90.5;
        }
        return 100;
    }

    private static double getPredictionFor1Year(int points) {
        if (points < 59) {
            return 0;
        }
        if (points >= 59 && points <= 70) {
            return 1.78;
        }
        if (points >= 71 && points <= 80) {
            return 2.93;
        }
        if (points >= 81 && points <= 90) {
            return 4.36;
        }
        if (points >= 91 && points <= 100) {
            return 6.56;
        }
        if (points >= 101 && points <= 110) {
            return 9.76;
        }
        if (points >= 111 && points <= 120) {
            return 14.4;
        }
        if (points >= 121 && points <= 130) {
            return 20.4;
        }
        if (points >= 131 && points <= 140) {
            return 29.0;
        }
        if (points >= 141 && points <= 150) {
            return 38.2;
        }
        if (points >= 151 && points <= 160) {
            return 48.9;
        }
        if (points >= 161 && points <= 170) {
            return 59.3;
        }
        if (points >= 171 && points <= 180) {
            return 74.8;
        }
        if (points >= 181 && points <= 190) {
            return 75.7;
        }
        if (points >= 191 && points <= 200) {
            return 87.3;
        }
        if (points >= 201 && points <= 210) {
            return 93.8;
        }
        if (points >= 211 && points <= 220) {
            return 95.3;
        }
        if (points >= 221 && points <= 230) {
            return 97.3;
        }
        if (points >= 231 && points <= 240) {
            return 97.7;
        }
        return 100;
    }

    private static int countPoints(Patient p, Dictionary<Integer, ExpectedAnswer> correctAnswers) {
        int pointsSum = (int)((NumericAnswer)(p.PatientAnswers.get(200))).Value;
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.iScore);
        questionIDs.remove(200);

        for(int i=0;i<questionIDs.size();i++) {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

            if (userAnswer != null && expectedAnswer != null) {
            } else if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
                pointsSum += ((ExpectedNumericAnswer) expectedAnswer).CalculatePoints(((NumericAnswer) userAnswer).Value);
            } else if (userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer) {
                if (((TrueFalseAnswer) userAnswer).Value == ((ExpectedTrueFalseAnswer) expectedAnswer).CorrectValue) {
                    pointsSum += ((ExpectedTrueFalseAnswer) expectedAnswer).Score;
                }
            } else {
                //throw new WrongQuestionsSetException();
            }
        }
        return pointsSum;
    }

    private static void Initialize() {
        correctAnswersFor30Days = new Hashtable<>();
        correctAnswersFor1Year = new Hashtable<>();

        ExpectedNumericAnswer answer201a = new ExpectedNumericAnswer(201);
        answer201a.Ranges.add(new RangeClassifier(1,1, 10));
        correctAnswersFor30Days.put(201, answer201a);

        ExpectedNumericAnswer answer201b = new ExpectedNumericAnswer(201);
        answer201b.Ranges.add(new RangeClassifier(1,1, 5));
        correctAnswersFor30Days.put(201, answer201b);

        correctAnswersFor1Year.put(202,  new ExpectedTrueFalseAnswer(202, true, 5));

        correctAnswersFor30Days.put(204, new ExpectedTrueFalseAnswer(204, true, 15));
        correctAnswersFor1Year.put(204, new ExpectedTrueFalseAnswer(204, true, 20));

        ExpectedNumericAnswer answer206a = new ExpectedNumericAnswer(206);
        answer206a.Ranges.add(new RangeClassifier(135, Double.MAX_VALUE, 15));
        correctAnswersFor30Days.put(206, answer206a);

        ExpectedNumericAnswer answer206b = new ExpectedNumericAnswer(206);
        answer206b.Ranges.add(new RangeClassifier(135, Double.MAX_VALUE, 10));
        correctAnswersFor30Days.put(206, answer206b);

        ExpectedNumericAnswer answer211a = new ExpectedNumericAnswer(211);
        answer211a.Ranges.add(new RangeClassifier(1,1,30));
        answer211a.Ranges.add(new RangeClassifier(2,2,35));
        correctAnswersFor30Days.put(211, answer211a);

        ExpectedNumericAnswer answer211b = new ExpectedNumericAnswer(211);
        answer211b.Ranges.add(new RangeClassifier(1,1,15));
        answer211b.Ranges.add(new RangeClassifier(2,2,20));
        correctAnswersFor1Year.put(211, answer211b);

        correctAnswersFor30Days.put(301, new ExpectedTrueFalseAnswer(301, true, 10));
        correctAnswersFor1Year.put(301, new ExpectedTrueFalseAnswer(301, true, 15));

        correctAnswersFor30Days.put(401, new ExpectedTrueFalseAnswer(401, true, 10));
        correctAnswersFor1Year.put(401, new ExpectedTrueFalseAnswer(401, true, 5));

        correctAnswersFor30Days.put(402, new ExpectedTrueFalseAnswer(402, true, 10));
        correctAnswersFor1Year.put(402, new ExpectedTrueFalseAnswer(402, true, 10));

        correctAnswersFor1Year.put(403, new ExpectedTrueFalseAnswer(403, true, 5));

        correctAnswersFor30Days.put(404, new ExpectedTrueFalseAnswer(404, true, 35));
        correctAnswersFor1Year.put(404, new ExpectedTrueFalseAnswer(404, true, 40));

    }
}
