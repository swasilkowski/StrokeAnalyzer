package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 04.12.2017.
 */

public class BulletedAnswer {

    private Integer id;
    private String text;

    public BulletedAnswer(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getText() { return text; }

    public Integer getId()
    {
        return id;
    }
}
