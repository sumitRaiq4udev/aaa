package com.lody.virtual.client.stub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import com.lody.virtual.R;
import com.lody.virtual.server.notification.NotificationCompat;


/**
 * @author Lody
 *
 */
public class DaemonService extends Service {

    private static final int NOTIFY_ID = 1001;

	public static void startup(Context context) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			context.startForegroundService(new Intent(context, DaemonService.class));
		}else {
			context.startService(new Intent(context, DaemonService.class));
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		startup(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(this, InnerService.class));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			startMyOwnForeground();

			//startForeground(NOTIFY_ID, new Notification());

       // startForeground(NOTIFY_ID, new Notification());



	}

	private void startMyOwnForeground() {
		String NOTIFICATION_CHANNEL_ID = "io.virtualapp";
		String channelName = "sumit Background Service";
		NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
		chan.setLightColor(Color.BLUE);
		chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		assert manager != null;
		manager.createNotificationChannel(chan);

		Notification.Builder notificationBuilder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
		Notification notification = notificationBuilder.setOngoing(true)
				.setSmallIcon(R.drawable.icon_1)
				.setContentTitle("sumit App is running in background")
				.setPriority(Notification.PRIORITY_HIGH)
				.setCategory(Notification.CATEGORY_SERVICE)
				.build();
		startForeground(NOTIFY_ID, notification);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	public static final class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(NOTIFY_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}


}
