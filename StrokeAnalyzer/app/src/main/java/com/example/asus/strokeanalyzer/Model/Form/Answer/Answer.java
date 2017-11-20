package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Created by Asus on 20.11.2017.
 */

public class Answer {

    private int questionId;

    public Answer(int questionId)
    {
        this.questionId = questionId;
    }

    public int GetQuestionID()
    {
        return questionId;
    }
}
