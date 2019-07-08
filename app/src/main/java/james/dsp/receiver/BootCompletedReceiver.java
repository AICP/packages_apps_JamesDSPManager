package james.dsp.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import james.dsp.R;

import james.dsp.service.HeadsetService;

import static james.dsp.activity.DSPManager.NOTIFICATION_CHANNEL;

/**
 * This receiver starts our {@link HeadsetService} after system boot. Since
 * Android 2.3, we will always need a persistent process, because we are forced
 * to keep track of all open audio sessions.
 *
 * Co-founder alankila
 */
public class BootCompletedReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, HeadsetService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initializeNotificationChannel(context);
            context.startForegroundService(service);
        } else {
            context.startService(service);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeNotificationChannel(Context context) {
        final CharSequence name = context.getString(R.string.notification_channel_name);
        final int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, importance);

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
    }

}
