package com.example.asus.strokeanalyzer.View.Helpers;

import android.view.View;

/**
 * Interfejs kliknięcia elementu RecyclerView.
 *
 * @author Marta Marciszewicz
 */

public interface ClickListener {
    /**
     * Nagłówek funkcji informującej o wystąpieniu kliknięcia jednego z elementów RecyclerView.
     *
     * @param view widok klikniętego obiektu
     * @param position pozycja klikniętego obiektu
     */
    void onClick(View view, int position);
}
