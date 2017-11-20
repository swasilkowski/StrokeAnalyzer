package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Created by Asus on 20.11.2017.
 */

public class ExpectedAnswer {

    private int questionId;

    public ExpectedAnswer(int questionId)
    {
        this.questionId = questionId;
    }

    public int GetQuestionID()
    {
        return questionId;
    }
}
