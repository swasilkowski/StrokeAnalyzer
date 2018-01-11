package com.example.asus.strokeanalyzer;

import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by S. Wasilkowski on 1/10/2018.
 */

public class NihssAnalyzerUnitTest {
    @Test
    public void countNihssSum_isCorrect() throws Exception {
        NihssExamination examination = new NihssExamination();
        for (int i = 0; i < 15; i++) {
            examination.Answers.add(new NumericAnswer(i, 1));
        }

        assertEquals(15, NihssAnalyzer.CountNihssSum(examination));

    }
}
