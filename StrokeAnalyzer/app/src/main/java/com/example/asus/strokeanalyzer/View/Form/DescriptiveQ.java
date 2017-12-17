package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 04.12.2017.
 */

public class DescriptiveQ implements Question {

    private int id;
    private String text;
    private String answer;

    public DescriptiveQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getText() { return text; }

    public void setAnswer(String text)
    {
        answer = text;
    }
    public String getAnswer() { return answer;}
    public int getID() {return id;}

    @Override
    public int getListItemType() {
        return Question.DESCRIPTIVE;
    }
}
