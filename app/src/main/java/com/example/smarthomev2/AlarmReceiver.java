package com.example.smarthomev2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import static com.example.smarthomev2.database_test.GetData;

/**
 * Created by Andrew on 3/4/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        database_test.GetData retrieveData = new database_test.GetData();
        retrieveData.execute("");
        Toast.makeText(context,"Data Retrieved",Toast.LENGTH_SHORT).show();
    }
}