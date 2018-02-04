package com.example.asus.strokeanalyzer.Model;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca pojedyncze badanie w skali NIHSS. Zawiera datę wykonanego badania oraz
 * listę odpowiedzi, które wybrał użytkownik w trakcie wypełniania formularza skali NIHSS.
 *
 * @author Stanisław Wasilkowski
 */

public class NihssExamination {

    /**
     * Data powstania badania w skali NIHSS.
     */
    public Date Date;
    /**
     * Lista odpowiedzi udzielonych przez użytkownika na pytania skali NIHSS.
     */
    public List<Answer> Answers;

    /**
     * Konstruktor bezparametrowy klasy.
     */
    public NihssExamination() {
        Answers = new LinkedList<>();
    }
}
