package com.example.asus.strokeanalyzer.Model;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;

import java.util.List;

/**
 * Created by Asus on 16.01.2018.
 */

public class ReportChooserTargetService extends ChooserTargetService {
    @Override
    public List<ChooserTarget> onGetChooserTargets(ComponentName componentName, IntentFilter intentFilter) {


        return null;
    }
}
