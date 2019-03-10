package com.example.android.dreamdestinations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by joycelin12 on 10/18/18.
 * referencing https://medium.com/@harunwangereka/android-background-services-b5aac6be3f04
 */

public class PriceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent= new Intent(context, AddPriceService.class);
            context.startService(serviceIntent);

        }
}
