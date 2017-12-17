package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 04.12.2017.
 */

public class TrueFalseQ implements Question
{
    private int id;
    private String text;
    private boolean answer;

    public TrueFalseQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getText() { return text; }

    public int getId()
    {
        return id;
    }

    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }
    public boolean getAnswer() {return answer;}

    @Override
    public int getListItemType() {
        return Question.TRUEFALSE;
    }
}
