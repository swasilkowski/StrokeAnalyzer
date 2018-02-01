package com.example.asus.strokeanalyzer.Model.EnumValues;

/**
 * Typ wyliczeniowy rodzaji skal wykorzystywanych w aplikacji.
 * Klasa zawiera również metodę Print, która pozwala na wypisanie nazwy danej wartości typu wyliczeniowego
 * (tu: nazwę skali).
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
     * Metoda zwracająca nazwę danej wartości typu wyliczeniowego.
     *
     * @return poprawna nazwa danego elementu typu wyliczeniowego {@link Form}
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
