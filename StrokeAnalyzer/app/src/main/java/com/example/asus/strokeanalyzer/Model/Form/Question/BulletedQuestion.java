package com.example.asus.strokeanalyzer.Model.Form.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 20.11.2017.
 */

public class BulletedQuestion extends Question {

    //key - value of a particular answer, value - description for this answer to be printed
    //possibleValues - field containing all of the values user may pick
    private Dictionary<Integer, String> possibleValues;

    public BulletedQuestion(int id, String text)
    {
        super(id, text);
    }

    public String GetValueDescription(int ind)
    {
        return possibleValues.get(ind);
    }

    public List<Integer> GetListOfPosiibleValues()
    {
        return Collections.list(possibleValues.keys());
    }
}
