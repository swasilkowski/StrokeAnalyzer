package com.example.asus.strokeanalyzer.Model.EnumValues;

/**
 * Wyliczenie rodzaji skal.
 * Klasa zawiera również metodę Print, która pozwala na wypisanie nazwy danej wartości typu wyliczeniowego
 *
 * @author Marta Marciszewicz
 */

public enum Form {
    NIHSS,
    StrokeBricks,
    DemographicAndClinic,
    ThrombolyticTreatment,
    Hat,
    iScore,
    Dragon;

    /**
     * Wypisanie nazwy danej wartości typu wyliczeniowego
     * @return (String) poprawna nazwa danego elementu typu wyliczeniowego Form
     */
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
