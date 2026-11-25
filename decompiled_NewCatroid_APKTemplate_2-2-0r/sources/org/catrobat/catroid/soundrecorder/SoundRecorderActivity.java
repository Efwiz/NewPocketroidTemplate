package org.catrobat.catroid.soundrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/soundrecorder/SoundRecorderActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundRecorderActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSIONS_RECORD_AUDIO = 401;
    private static final String TAG = "SoundRecorderActivity";
    private RecordButton recordButton;
    private SoundRecorder soundRecorder;
    private Chronometer timeRecorderChronometer;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundrecorder);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.soundrecorder_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.recordButton = (RecordButton) findViewById(R.id.soundrecorder_record_button);
        this.timeRecorderChronometer = (Chronometer) findViewById(R.id.soundrecorder_chronometer_time_recorded);
        this.recordButton.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(final View view) {
        new RequiresPermissionTask(401, Arrays.asList("android.permission.RECORD_AUDIO"), R.string.runtime_permission_general) { // from class: org.catrobat.catroid.soundrecorder.SoundRecorderActivity.1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() {
                if (view.getId() == R.id.soundrecorder_record_button) {
                    if (SoundRecorderActivity.this.soundRecorder != null && SoundRecorderActivity.this.soundRecorder.isRecording()) {
                        SoundRecorderActivity.this.stopRecording();
                        SoundRecorderActivity.this.timeRecorderChronometer.stop();
                        SoundRecorderActivity.this.finish();
                    } else {
                        SoundRecorderActivity.this.startRecording();
                        SoundRecorderActivity.this.timeRecorderChronometer.setBase(SystemClock.elapsedRealtime());
                        SoundRecorderActivity.this.timeRecorderChronometer.start();
                    }
                }
            }
        }.execute(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        stopRecording();
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void startRecording() {
        SoundRecorder soundRecorder = this.soundRecorder;
        if (soundRecorder != null && soundRecorder.isRecording()) {
            return;
        }
        try {
            try {
                try {
                    SoundRecorder soundRecorder2 = this.soundRecorder;
                    if (soundRecorder2 != null) {
                        soundRecorder2.stop();
                    }
                    Constants.SOUND_RECORDER_CACHE_DIRECTORY.mkdirs();
                    if (!Constants.SOUND_RECORDER_CACHE_DIRECTORY.isDirectory()) {
                        throw new IOException("Cannot create " + Constants.SOUND_RECORDER_CACHE_DIRECTORY);
                    }
                    SoundRecorder soundRecorder3 = new SoundRecorder(new File(Constants.SOUND_RECORDER_CACHE_DIRECTORY, getString(R.string.soundrecorder_recorded_filename)).getAbsolutePath());
                    this.soundRecorder = soundRecorder3;
                    soundRecorder3.start();
                    setViewsToRecordingState();
                } catch (IOException e) {
                    Log.e(TAG, "Error recording sound.", e);
                    ToastUtil.showError(this, R.string.soundrecorder_error);
                }
            } catch (IllegalStateException e2) {
                Log.e(TAG, "Error recording sound (Other recorder running?).", e2);
                ToastUtil.showError(this, R.string.soundrecorder_error);
            }
        } catch (RuntimeException e3) {
            Log.e(TAG, "Device does not support audio or video format.", e3);
            ToastUtil.showError(this, R.string.soundrecorder_error);
        }
    }

    private void setViewsToRecordingState() {
        this.recordButton.setState(RecordButton.RecordState.RECORD);
        this.recordButton.setImageResource(R.drawable.ic_microphone_active);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void stopRecording() {
        SoundRecorder soundRecorder = this.soundRecorder;
        if (soundRecorder != null && soundRecorder.isRecording()) {
            setViewsToNotRecordingState();
            try {
                this.soundRecorder.stop();
                setResult(-1, new Intent("android.intent.action.PICK", FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileProvider", new File(this.soundRecorder.getPath()))));
            } catch (IOException e) {
                Log.e(TAG, "Error recording sound.", e);
                ToastUtil.showError(this, R.string.soundrecorder_error);
                setResult(0);
            }
        }
    }

    private void setViewsToNotRecordingState() {
        this.recordButton.setState(RecordButton.RecordState.STOP);
        this.recordButton.setImageResource(R.drawable.ic_microphone);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
