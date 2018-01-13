package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 * Created by Asus on 28.12.2017.
 */

public class NumericQ implements Question {
    private int id;
    private String text;
    private double answer;
    private boolean answerSet;
    private RangeClassifier range;

    public NumericQ(int id ,String text, RangeClassifier _range)
    {
        this.id = id;
        this.text = text;
        this.answerSet = false;
        this.range = _range;
    }

    public String getText() { return text; }

    public boolean setAnswer(double ans)
    {
        if(range.withinARange(ans))
        {
            answer = ans;
            answerSet = true;
            return true;
        }
        else
            return false;

    }

    public void clearAnswer()
    {
        answer =0;
        answerSet = false;
    }

    public double getAnswer() { return answer;}
    public int getID() {return id;}
    public boolean getAnswerSet() {return answerSet;}

    @Override
    public int getListItemType() {
        return Question.NUMERIC;
    }
}
