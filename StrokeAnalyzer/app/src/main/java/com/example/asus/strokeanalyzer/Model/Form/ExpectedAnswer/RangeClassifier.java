package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Created by S. Wasilkowski on 2017-12-19.
 */

public class RangeClassifier
{
    public double MinValue;
    public double MaxValue;
    public int Points;

    boolean withinARange(double value)
    {
        if(value>MaxValue || value<MinValue)
            return false;
        return true;
    }

    public RangeClassifier(double minValue, double maxValue, int points) {
        MinValue = minValue;
        MaxValue = maxValue;
        Points = points;
    }

    public RangeClassifier(double minValue, double maxValue) {
        MinValue = minValue;
        MaxValue = maxValue;
    }
}