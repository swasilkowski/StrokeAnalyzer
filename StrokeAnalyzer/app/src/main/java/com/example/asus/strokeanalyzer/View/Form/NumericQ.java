package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 28.12.2017.
 */

public class NumericQ implements Question {
    private int id;
    private String text;
    private double answer;
    private boolean answerSet;

    public NumericQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
        this.answerSet = false;
    }

    public String getText() { return text; }

    public void setAnswer(double ans)
    {
        answer = ans;
        answerSet = true;
    }
    public double getAnswer() { return answer;}
    public int getID() {return id;}
    public boolean getAnswerSet() {return answerSet;}

    @Override
    public int getListItemType() {
        return Question.NUMERIC;
    }
}
