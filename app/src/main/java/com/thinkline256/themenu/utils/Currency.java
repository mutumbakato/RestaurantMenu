package com.thinkline256.themenu.utils;

import java.text.DecimalFormat;

/**
 * Created by cato on 3/29/18.
 */

public class Currency {
    public static String format(float value) {
        return new DecimalFormat("#,###").format(value);
    }
}
