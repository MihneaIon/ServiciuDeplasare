package com.licenta.app.UbberLicenta.compare;

import java.util.Comparator;

public class CompareDistance implements Comparator<Float> {

    @Override
    public int compare(Float o1, Float o2) {
        if(o1< o2)
            return -1;
        else
            if(o1>o2)
        return 1;
        return 0;
    }



}
