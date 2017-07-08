package com.akexorcist.notificationchannel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NotificationChannelActivity extends AppCompatActivity {
    private Button btnPushNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_channel);

        bindView();
        setupView();
        setupNotificationChannel(this);
    }

    private void bindView() {
        btnPushNotification = findViewById(R.id.btnPushNotification);
    }

    private void setupView() {
        btnPushNotification.setOnClickListener(onPushNotification());
    }

    private View.OnClickListener onPushNotification() {
        return view -> pushNotification(getApplicationContext());
    }

    private void pushNotification(Context context) {
        int notificationId = 0;
        String channelId = NotificationKey.CHANNEL_ID_COMMENT;
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.ic_comment_white)
                .setContentTitle("Palakorn says")
                .setContentText("Hey! How are you?")
                .build();
        NotificationManagerCompat.from(context).notify(notificationId, notification);
    }

    private void setupNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelGroup timelineGroup = new NotificationChannelGroup(
                    NotificationKey.CHANNEL_GROUP_TIMELINE,
                    NotificationKey.CHANNEL_NAME_TIMELINE);

            NotificationChannelGroup friendGroup = new NotificationChannelGroup(
                    NotificationKey.CHANNEL_GROUP_FRIEND,
                    NotificationKey.CHANNEL_NAME_FRIEND);

            NotificationChannel friendConfirmationsChannel = new NotificationChannel(
                    NotificationKey.CHANNEL_ID_FRIEND_CONFIRMATIONS,
                    NotificationKey.CHANNEL_NAME_FRIEND_CONFIRMATIONS,
                    NotificationManager.IMPORTANCE_DEFAULT);
            friendConfirmationsChannel.setGroup(NotificationKey.CHANNEL_GROUP_FRIEND);

            NotificationChannel friendRequestsChannel = new NotificationChannel(
                    NotificationKey.CHANNEL_ID_FRIEND_REQUESTS,
                    NotificationKey.CHANNEL_NAME_FRIEND_REQUESTS,
                    NotificationManager.IMPORTANCE_DEFAULT);
            friendRequestsChannel.setGroup(NotificationKey.CHANNEL_GROUP_FRIEND);

            NotificationChannel photoTagsChannel = new NotificationChannel(
                    NotificationKey.CHANNEL_ID_PHOTO_TAGS,
                    NotificationKey.CHANNEL_NAME_PHOTO_TAGS,
                    NotificationManager.IMPORTANCE_DEFAULT);
            photoTagsChannel.setGroup(NotificationKey.CHANNEL_GROUP_FRIEND);

            NotificationChannel commentChannel = new NotificationChannel(
                    NotificationKey.CHANNEL_ID_COMMENT,
                    NotificationKey.CHANNEL_NAME_COMMENT,
                    NotificationManager.IMPORTANCE_DEFAULT);
            commentChannel.setGroup(NotificationKey.CHANNEL_GROUP_TIMELINE);

            NotificationChannel logInAlertChannel = new NotificationChannel(
                    NotificationKey.CHANNEL_ID_LOG_IN_ALERT,
                    NotificationKey.CHANNEL_NAME_LOG_IN_ALERT,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannelGroup(timelineGroup);
            manager.createNotificationChannelGroup(friendGroup);
            manager.createNotificationChannel(friendConfirmationsChannel);
            manager.createNotificationChannel(friendRequestsChannel);
            manager.createNotificationChannel(photoTagsChannel);
            manager.createNotificationChannel(commentChannel);
            manager.createNotificationChannel(logInAlertChannel);
        }
    }
}
