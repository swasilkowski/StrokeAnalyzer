package com.example.asus.strokeanalyzer.View.Helpers;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import java.util.List;

/**
 * Klasa umożliwiająca udostępnianie raportu innym aplikacjom zainstalowanym na urządzeniu mobilnym.
 * Wymagana przez mechanizm udostępniania - nie zawiera żadnej implementacji.
 *
 * @author Marta Marciszewicz
 */

public class ReportChooserTargetService extends ChooserTargetService {
    @Override
    public List<ChooserTarget> onGetChooserTargets(ComponentName componentName, IntentFilter intentFilter) {
        return null;
    }
}
