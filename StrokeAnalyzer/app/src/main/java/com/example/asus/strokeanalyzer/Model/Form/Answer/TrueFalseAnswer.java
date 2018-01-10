package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Created by Asus on 20.11.2017.
 */

public class TrueFalseAnswer extends Answer {

    public boolean Value;

    public TrueFalseAnswer(int questionId)
    {
        super(questionId);
    }

    public TrueFalseAnswer(int questionId, boolean value) {
        super(questionId);
        Value = value;
    }
}
