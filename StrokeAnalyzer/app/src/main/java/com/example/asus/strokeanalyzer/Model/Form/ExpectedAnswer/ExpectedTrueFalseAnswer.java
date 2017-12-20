package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Created by Asus on 20.11.2017.
 */

public class ExpectedTrueFalseAnswer extends ExpectedAnswer {

    public boolean CorrectValue;

    public int Score;

    public ExpectedTrueFalseAnswer(int questionId)
    {
        super(questionId);
    }

    public ExpectedTrueFalseAnswer(int questionId, boolean correctValue)
    {
        super(questionId);
        CorrectValue = correctValue;
    }

    public ExpectedTrueFalseAnswer(int questionId, boolean correctValue, int score)
    {
        super(questionId);
        CorrectValue = correctValue;
        Score = score;
    }
}
