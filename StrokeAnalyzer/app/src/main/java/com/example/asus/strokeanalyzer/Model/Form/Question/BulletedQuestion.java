package com.example.asus.strokeanalyzer.Model.Form.Question;

import java.util.Arrays;
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
    private Map<Integer, String> possibleValues;

    public BulletedQuestion(int id, String text, Map<Integer, String> answers)
    {
        super(id, text);
        possibleValues = answers;
    }

    public String GetValueDescription(int ind)
    {
        return possibleValues.get(ind);
    }

    public List<Integer> GetListOfPosiibleValues()
    {
        return Arrays.asList(possibleValues.keySet().toArray(new Integer[0]));
    }
}
