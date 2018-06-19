package com.mykapps.androidlearners.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.mykapps.androidlearners.R;
import com.mykapps.androidlearners.data.Contract;
import com.mykapps.androidlearners.data.DoUpdateJsonLikes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProgramDetailDisplayActvitity extends AppCompatActivity {

    private static final String PROGRAM_VIDEO_PLAY_POSITION = "program_step_video_play_position";
    private static final String PROGRAM_VIDEO_PLAY_STATUS = "program_step_video_play_status";
    private final String TAG = "ProgramDetailDisplay";
    private SimpleExoPlayerView programDisplaySimpleExoPlayerView;
    private SimpleExoPlayer programDisplaySimpleExoPlayer;
    private String programVideoUrlIntent;
    private long exoPlayerPosition = 0;
    private boolean playStatuscheck = true;
    private Uri programVideoUrlIntent_Uri;
    private ToggleButton toggleButton;
    private TextView textViewTopicName;
    private String likesCount;
    private String topicID;
    private String commentsCount;
    private String topicName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail_display_actvitity);
        if (savedInstanceState != null) {
            playStatuscheck = savedInstanceState.getBoolean(PROGRAM_VIDEO_PLAY_STATUS);
            exoPlayerPosition = savedInstanceState.getLong(PROGRAM_VIDEO_PLAY_POSITION);

            //recipeStepVideoUrlIntent_Uri = Uri.parse(savedInstanceState.getString(RECIPE_STEP_VIDEO_URL));
        }

        programDisplaySimpleExoPlayerView = findViewById(R.id.program_video_exoPlayer_view);
        toggleButton = findViewById(R.id.detail_topic_toggle);
        textViewTopicName = findViewById(R.id.detailTopicNameDisplay);
        programVideoUrlIntent = getIntent().getStringExtra("topicVideo");
        likesCount = getIntent().getStringExtra("topicLikesCount");
        topicName = getIntent().getStringExtra("topicTitle");
        textViewTopicName.setText(topicName);
        topicID = getIntent().getStringExtra("topicID");
        commentsCount = getIntent().getStringExtra("topicCommentsCount");
        programVideoUrlIntent_Uri = Uri.parse(programVideoUrlIntent);
        exoPlayerDisplay(programVideoUrlIntent_Uri);
        toggleStatusCheck(topicID);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                learnersFavorites(isChecked);

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void exoPlayerDisplay(Uri programVideoUri) {
        if (programDisplaySimpleExoPlayer == null) {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                programDisplaySimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(programVideoUri, dataSourceFactory, extractorsFactory, null, null);
                programDisplaySimpleExoPlayerView.setPlayer(programDisplaySimpleExoPlayer);
                programDisplaySimpleExoPlayer.prepare(mediaSource);
                programDisplaySimpleExoPlayer.setPlayWhenReady(playStatuscheck);
                if (exoPlayerPosition > 0) {
                    programDisplaySimpleExoPlayer.seekTo(exoPlayerPosition);
                    programDisplaySimpleExoPlayer.setPlayWhenReady(playStatuscheck);
                }

            } catch (Exception e) {
                Log.e(TAG, "exoplayer_error" + e.toString());
            }
        }
    }

    private void saveState() {
        if (programDisplaySimpleExoPlayer != null) {
            exoPlayerPosition = programDisplaySimpleExoPlayer.getCurrentPosition();
            playStatuscheck = programDisplaySimpleExoPlayer.getPlayWhenReady();
        }
    }

    public void onSaveInstanceState(Bundle currentState) {
        saveState();
        super.onSaveInstanceState(currentState);
        currentState.putBoolean(PROGRAM_VIDEO_PLAY_STATUS, playStatuscheck);
        currentState.putLong(PROGRAM_VIDEO_PLAY_POSITION, exoPlayerPosition);

    }

    private void releasePlayer() {
        saveState();
        programDisplaySimpleExoPlayer.stop();
        programDisplaySimpleExoPlayer.release();
        programDisplaySimpleExoPlayer = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (programDisplaySimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onPause() {
        super.onPause();
        if (programDisplaySimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onStop() {
        super.onStop();
        if (programDisplaySimpleExoPlayer == null) {
            return;
        } else {
            releasePlayer();
        }
    }

    public void onResume() {
        super.onResume();
        if (programDisplaySimpleExoPlayer == null) {
            exoPlayerDisplay(programVideoUrlIntent_Uri);
        }

    }

    private void learnersFavorites(boolean isChecked) {

        if (isChecked) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.EntryInfo.COLUMN_ID, getIntent().getStringExtra("topicID"));
            contentValues.put(Contract.EntryInfo.COLUMN_TITLE, getIntent().getStringExtra("topicTitle"));
            contentValues.put(Contract.EntryInfo.COLUMN_IMAGE, getIntent().getStringExtra("topicImage"));
            contentValues.put(Contract.EntryInfo.COLUMN_VIDEO_URL, getIntent().getStringExtra("topicVideo"));
            contentValues.put(Contract.EntryInfo.COLUMN_LIKES_COUNT, getIntent().getStringExtra("topicLikesCount"));
            contentValues.put(Contract.EntryInfo.COLUMN_COMMENTS_COUNT, getIntent().getStringExtra("topicCommentsCount"));
            contentValues.put(Contract.EntryInfo.COLUMN_PLACEHOLDER_STRING, getIntent().getStringExtra("topicPlaceholderString"));
            Uri uri = getContentResolver().insert(Contract.EntryInfo.CONTENT_URI, contentValues);

            if (uri != null) {
                Toast.makeText(getApplicationContext(), getIntent().getStringExtra("topicTitle") + "Added to Favorites", Toast.LENGTH_LONG).show();
            }
        } else {
            // The toggle is disabled
            Uri uri = Contract.EntryInfo.CONTENT_URI;
            uri = uri.buildUpon().appendPath(topicID).build();
            int deleteCheck = getContentResolver().delete(uri, null, null);
            // COMPLETED (2) Delete a single row of data using a ContentResolver
            if (deleteCheck == 1) {
                Toast.makeText(getApplicationContext(), getIntent().getStringExtra("topicTitle") + "Removed from Favorites", Toast.LENGTH_LONG).show();

            }
        }

    }

    private void toggleStatusCheck(String topicIdReceive) {
        Uri uri = Contract.EntryInfo.CONTENT_URI;
        uri = uri.buildUpon().appendPath(topicIdReceive).build();
        Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);

        }
        cursor.close();
    }
}
