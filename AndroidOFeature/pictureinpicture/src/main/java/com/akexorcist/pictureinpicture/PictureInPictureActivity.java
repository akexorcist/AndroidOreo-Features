package com.akexorcist.pictureinpicture;

import android.annotation.SuppressLint;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Rational;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PictureInPictureActivity extends AppCompatActivity {
    private ImageButton btnPictureInPicture;
    private SimpleExoPlayerView pvVideoPreview;

    private SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_in_picture);
        bindView();
        setupView();
        setupExoPlayer();
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setupExoPlayer();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            setupExoPlayer();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            pvVideoPreview.hideController();
        }
    }

    private void bindView() {
        btnPictureInPicture = findViewById(R.id.btnPictureInPicture);
        pvVideoPreview = findViewById(R.id.pvVideoPreview);
    }

    private void setupView() {
        btnPictureInPicture.setOnClickListener(onPictureInPictureClick());
        pvVideoPreview.setControllerVisibilityListener(onControllerVisibilityChanges());
    }

    private void setupExoPlayer() {
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());
        pvVideoPreview.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(true);

        Uri uri = Uri.parse("asset:///sample_video.mp4");
        MediaSource mediaSource = createMediaSource(this, uri);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource);
        exoPlayer.prepare(loopingMediaSource, true, false);
    }

    private MediaSource createMediaSource(Context context, Uri uri) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getPackageName()), bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        return new ExtractorMediaSource(uri,
                dataSourceFactory,
                extractorsFactory,
                null,
                null);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private View.OnClickListener onPictureInPictureClick() {
        return view -> {
            if (exoPlayer != null && exoPlayer.getPlaybackState() == SimpleExoPlayer.STATE_READY) {
                int videoWidth = exoPlayer.getVideoFormat().width;
                int videoHeight = exoPlayer.getVideoFormat().height;
                PictureInPictureParams params = new PictureInPictureParams.Builder()
                        .setAspectRatio(new Rational(videoWidth, videoHeight))
                        .build();
                enterPictureInPictureMode(params);
            } else {
                Toast.makeText(PictureInPictureActivity.this, R.string.video_playback_not_ready, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private PlaybackControlView.VisibilityListener onControllerVisibilityChanges() {
        return visibility -> btnPictureInPicture.setVisibility(visibility);
    }
}
