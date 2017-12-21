package com.example.asus.strokeanalyzer.View.Helpers;

import java.util.function.Predicate;

/**
 * Created by Asus on 21.12.2017.
 */

public class RemovePatientPredicate<T> implements Predicate<T> {
    public T patient;

    public boolean test(T patientID){
        if(patient.equals(patientID)){
            return true;
        }
        return false;
    }
}
