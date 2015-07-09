package com.inventory.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Useful helper methods and fields.
 */
public class Utils {

    /**
     * Show a toast message.
     */
    public static void showToast(Context context,
                                 String message) {
        Toast.makeText(context,
                       message,
                       Toast.LENGTH_SHORT).show();
    }

}
