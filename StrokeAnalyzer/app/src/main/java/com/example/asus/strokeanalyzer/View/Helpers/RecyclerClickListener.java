package com.example.asus.strokeanalyzer.View.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Klasa zarządzająca akcją kliknięcia elementu RecyclerView.
 *
 * @author Marta Marciszewicz
 */

public class RecyclerClickListener implements RecyclerView.OnItemTouchListener{

    /**
     * Obiekt umożliwiający obsługę zdarzenia kliknięcia.
     */
    final private ClickListener clickListener;
    /**
     * Obiekt wykrywający gesty użytkownika oraz zdarzenia w aplikacji. Informuje o zajściu danego
     * zdarzenia związanego z ruchem.
     */
    final private GestureDetector gestureDetector;

    /**
     * Konstruktor inicjalizujący detektor gestów oraz obiekt klasy {@link ClickListener}.
     *
     * @param context kontekst aplikacji
     * @param clickListener obiekt klasy ClickListener, wykorzystywany do obsługi zdarzenia kliknięcia
     */
    public RecyclerClickListener(Context context, final ClickListener clickListener) {
        this.clickListener = clickListener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }
        });
    }

    /**
     * Metoda nasłuchująca zdarzeń związanych z dotknięciem ekranu, pozwalająca na ich przechwycenie i
     * obsługę. Funkcja wywołuje metodę onClick listenera {@link ClickListener}, jeżeli użytkownik wybrał
     * jeden z elementów RecyclerView.
     *
     * @param rv obiekt klasy RecyclerView, na którym zostało zaobserwowane kliknięcie
     * @param e obiekt klasy MotionEvent opisujące zdarzenie dotknięcia
     * @return true - jeżeli zdarzenie dotknięcia ma zostać interpretowane przez obiekt;
     *          false - jeżeli obiekt ma kontynuować nasłuchiwanie zdarzeń
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildLayoutPosition(child));
        }
        return false;
    }

    /**
     * Metoda wymagana przy implementacji interfejsu {@link RecyclerView.OnItemTouchListener} - nie
     * jest wykorzystywana w programie.
     *
     * @param rv obiekt klasy RecyclerView, na którym zostało zaobserwowane kliknięcie
     * @param e obiekt klasy MotionEvent opisujące zdarzenie dotknięcia
     */
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    /**
     * Metoda wymagana przy implementacji interfejsu {@link RecyclerView.OnItemTouchListener} - nie
     * jest wykorzystywana w programie.
     *
     * @param disallowIntercept parametr informujący o tym, czy zdarzenie na elemencie RecyclerView ma
     *                          zostać przez niego obsłużone (true oznacza brak obsługi)
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
