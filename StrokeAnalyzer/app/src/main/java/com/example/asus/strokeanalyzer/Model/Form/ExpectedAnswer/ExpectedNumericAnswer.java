package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Created by Asus on 20.11.2017.
 */

public class ExpectedNumericAnswer extends ExpectedAnswer {

    public int CorrectValue;
    public int MinValue;
    public int MaxValue;

    public ExpectedNumericAnswer(int questionId)
    {
        super(questionId);
    }
}
