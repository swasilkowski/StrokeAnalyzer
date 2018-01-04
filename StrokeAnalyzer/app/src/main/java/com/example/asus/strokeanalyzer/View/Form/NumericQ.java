package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 28.12.2017.
 */

public class NumericQ implements Question {
    private int id;
    private String text;
    private double answer;
    private double minValue;
    private double maxValue;

    public NumericQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getText() { return text; }

    public void setAnswer(double ans)
    {
        answer = ans;
    }
    public double getAnswer() { return answer;}
    public int getID() {return id;}

    @Override
    public int getListItemType() {
        return Question.NUMERIC;
    }
}
