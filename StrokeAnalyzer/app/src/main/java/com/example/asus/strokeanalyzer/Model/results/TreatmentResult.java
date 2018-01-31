package com.example.asus.strokeanalyzer.Model.results;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przechowująca wynik analizy dotyczącej możliwości zastosowania leczenia trombolitycznego.
 * Zawiera pole przechowujące ostateczną decyzję odnoszącą się do leczenia oraz listę odpowiedzi,
 * które różniły się od spodziewanej odpowiedzi na dane pytanie
 *
 * @author Marta Marciszewicz
 */

public class TreatmentResult {

    //final decision whether treatment should be conducted
    public boolean Decision;
    //list of answers that were different than expected answers, and influenced final decision
    public List<Answer> badAnswers;

    public TreatmentResult()
    {
        Decision = true;
        badAnswers = new ArrayList<>();
    }
}
