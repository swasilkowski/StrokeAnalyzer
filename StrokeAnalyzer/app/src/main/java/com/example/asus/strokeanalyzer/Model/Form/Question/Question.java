package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 * Created by Asus on 20.11.2017.
 */

public class Question {

    private int ID;
    private String Text;

    public Question(int id, String text)
    {
        ID = id;
        Text = text;
    }

    public int GetID()
    {
        return ID;
    }

    public String GetText()
    {
        return Text;
    }
}
