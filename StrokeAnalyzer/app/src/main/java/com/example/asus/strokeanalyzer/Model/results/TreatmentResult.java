package com.example.asus.strokeanalyzer.Model.results;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przechowująca wynik analizy dotyczącej możliwości zastosowania leczenia trombolitycznego.
 * Zawiera pole przechowujące ostateczną decyzję odnoszącą się do leczenia oraz listę odpowiedzi,
 * które przyczyniły się do decyzji negatywnej.
 *
 * @author Marta Marciszewicz
 */

public class TreatmentResult {

    /**
     * Decyzja dotycząca leczenia. true - leczenie trombolityczne jest zalecane; false - leczenie
     * trombolityczne jest niezalecane
     */
    public boolean Decision;
    /**
     * Lista obiektów typu {@link Answer} zawierających odpowiedzi róźniące się od odpowiedzi oczekiwanej
     * dla formularza dotyczącego zastosowania leczenia trombolitycznego i tym samym wpływające na ostateczną decyzję
     * dotyczącą leczenia.
     */
    final public List<Answer> badAnswers;

    /**
     * Bezparametrowy konstruktor klasy inicjalizujący jej pola.
     */
    public TreatmentResult()
    {
        Decision = true;
        badAnswers = new ArrayList<>();
    }
}
