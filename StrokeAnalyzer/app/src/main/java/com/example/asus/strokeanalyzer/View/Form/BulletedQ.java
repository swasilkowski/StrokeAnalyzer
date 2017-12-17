package com.example.asus.strokeanalyzer.View.Form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 04.12.2017.
 */

public class BulletedQ implements Question{

    private int id;
    private String text;
    private int answerId;
    private List<BulletedAnswer> answers;

    public BulletedQ(int id ,String text, Map<Integer, String> possibleValues)
    {
        this.id = id;
        this.text = text;
        answers = new ArrayList<>();
        for(Integer key: possibleValues.keySet())
        {
            answers.add(new BulletedAnswer(key,possibleValues.get(key)));
        }
    }

    public String getText() { return text; }

    public int getID(){return id;}

    public void setAnswer(int answer)
    {
        answerId = answer;
    }

    public int getAnswer(){return answerId;}

    public List<BulletedAnswer> getAnswers()
    {
        return answers;
    }

    @Override
    public int getListItemType() {
        return Question.BULLETED;
    }
}
