package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public class ExpectedNumericAnswer extends ExpectedAnswer {

    public class RangeClassifier
    {
        public Integer MinValue;
        public Integer MaxValue;
        public Integer Points;

        boolean withinARange(int value)
        {
            if(value>MaxValue || value<MinValue)
                return false;
            return true;
        }

    }

    //null value indicates that particular parameter should not be taken into consideration
    //if correctValue is considered we give 1 point
    public Integer CorrectValue;
    public List<RangeClassifier> Ranges;

    public ExpectedNumericAnswer(int questionId)
    {
        super(questionId);
    }
    public int CalculatePoints(Integer value)
    {
        if(CorrectValue!= null && CorrectValue.equals(value))
            return 1;
        else if(Ranges!=null)
        {
            for(RangeClassifier r:Ranges)
            {
                if(r.withinARange(value))
                    return r.Points;
            }
        }
        return 0;
    }

    public boolean CheckCorrecteness(Integer value)
    {
        if(CorrectValue!= null && CorrectValue.equals(value))
            return true;
        else if(Ranges!=null)
        {
            for(RangeClassifier r:Ranges)
            {
                if(r.withinARange(value))
                    return true;
            }
        }
        return false;
    }


}
