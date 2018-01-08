package com.example.asus.strokeanalyzer.Model.EnumValues;

/**
 * Created by Asus on 20.11.2017.
 */

public enum Form {
    NIHSS,
    StrokeBricks,
    DemographicAndClinic,
    ThrombolyticTreatment,
    Hat,
    iScore,
    Dragon;

    public String Print()
    {
        if(this==NIHSS)
            return "NIHSS";
        if(this==StrokeBricks)
            return "Stroke Bricks";
        if(this==DemographicAndClinic)
            return "Dane demograficzne i kliniczne";
        if(this==ThrombolyticTreatment)
            return "Leczenie trombolityczne";
        if(this==Hat)
            return "Hat";
        if(this==iScore)
            return "iScore";
        if(this==Dragon)
            return "Dragon";
        else return "...";
    }

}
