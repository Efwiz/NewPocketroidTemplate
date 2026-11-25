package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.badlogic.gdx.graphics.Color;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.embroidery.DSTFileGenerator;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.stage.ScreenshotSaverCallback;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageLifeCycleController;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.ui.ExportEmbroideryFileLauncher;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/StageDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StageDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "StageDialog";
    private StageActivity stageActivity;
    private StageListener stageListener;

    public StageDialog(StageActivity stageActivity, StageListener stageListener, int theme) {
        super(stageActivity, theme);
        this.stageActivity = stageActivity;
        this.stageListener = stageListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.dialog_stage);
            getWindow().getAttributes();
            getWindow().getAttributes();
            getWindow().setLayout(-1, -2);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            ((Button) findViewById(R.id.stage_dialog_button_back)).setOnClickListener(this);
            ((Button) findViewById(R.id.stage_dialog_button_continue)).setOnClickListener(this);
            ((Button) findViewById(R.id.stage_dialog_button_restart)).setOnClickListener(this);
            ((Button) findViewById(R.id.stage_dialog_button_toggle_axes)).setOnClickListener(this);
            ((Button) findViewById(R.id.stage_dialog_button_screenshot)).setOnClickListener(this);
            ((Button) findViewById(R.id.stage_dialog_button_debug)).setOnClickListener(this);
            if (this.stageActivity.isResizePossible()) {
                ((ImageButton) findViewById(R.id.stage_dialog_button_maximize)).setOnClickListener(this);
            } else {
                ((ImageButton) findViewById(R.id.stage_dialog_button_maximize)).setVisibility(8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dismiss();
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        if (this.stageListener.embroideryPatternManager != null && this.stageListener.embroideryPatternManager.validPatternExists()) {
            findViewById(R.id.stage_layout_linear_share).setVisibility(0);
            findViewById(R.id.stage_dialog_button_share).setOnClickListener(this);
        } else {
            findViewById(R.id.stage_layout_linear_share).setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stage_dialog_button_back /* 2131365281 */:
                onBackPressed();
                break;
            case R.id.stage_dialog_button_continue /* 2131365282 */:
                onContinuePressed();
                break;
            case R.id.stage_dialog_button_debug /* 2131365283 */:
                showDebugMenu();
                dismiss();
                StageLifeCycleController.stageResume(this.stageActivity);
                break;
            case R.id.stage_dialog_button_maximize /* 2131365284 */:
                this.stageListener.toggleScreenMode();
                break;
            case R.id.stage_dialog_button_restart /* 2131365285 */:
                onRestartPressed();
                break;
            case R.id.stage_dialog_button_screenshot /* 2131365286 */:
                makeScreenshot();
                break;
            case R.id.stage_dialog_button_share /* 2131365287 */:
                shareEmbroideryFile();
                break;
            case R.id.stage_dialog_button_toggle_axes /* 2131365288 */:
                toggleAxes();
                break;
            default:
                Log.w(TAG, "Unimplemented button clicked! This shouldn't happen!");
                break;
        }
    }

    private void showDebugMenu() {
        DebugMenuManager.getInstance().show(this.stageActivity);
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        clearBroadcastMaps();
        resetEmbroideryThreadColor();
        dismiss();
        this.stageActivity.exit();
        new FinishThreadAndDisposeTexturesTask().execute(null, null, null);
    }

    private void shareEmbroideryFile() {
        if (this.stageListener.embroideryPatternManager.validPatternExists()) {
            String strEncodeSpecialCharsForFileSystem = FileMetaDataExtractor.encodeSpecialCharsForFileSystem(ProjectManager.getInstance().getCurrentProject().getName());
            DSTFileGenerator dSTFileGenerator = new DSTFileGenerator(this.stageListener.embroideryPatternManager.getEmbroideryStream());
            File file = new File(Constants.CACHE_DIRECTORY, strEncodeSpecialCharsForFileSystem + Constants.EMBROIDERY_FILE_EXTENSION);
            if (file.exists()) {
                file.delete();
            }
            try {
                if (file.createNewFile()) {
                    dSTFileGenerator.writeToDSTFile(file);
                    new ExportEmbroideryFileLauncher(this.stageActivity, file).startActivity();
                }
            } catch (IOException unused) {
                ToastUtil.showError(this.stageActivity, R.string.error_embroidery_file_export);
                Log.e(TAG, "Writing to dst file failed");
            }
        }
        dismiss();
    }

    public void onContinuePressed() {
        if (ProjectManager.getInstance().getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected()) {
            ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
            return;
        }
        dismiss();
        SensorHandler.timerReferenceValue += SystemClock.uptimeMillis() - SensorHandler.timerPauseValue;
        StageLifeCycleController.stageResume(this.stageActivity);
    }

    public void onRestartPressed() {
        if (ProjectManager.getInstance().getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected()) {
            ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
            return;
        }
        clearBroadcastMaps();
        resetEmbroideryThreadColor();
        dismiss();
        SensorHandler.timerReferenceValue = SystemClock.uptimeMillis();
        restartProject();
    }

    private void makeScreenshot() {
        if (ProjectManager.getInstance().getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected()) {
            ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
        } else {
            this.stageListener.requestTakingScreenshot(Constants.SCREENSHOT_MANUAL_FILE_NAME, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.ui.dialogs.StageDialog$$ExternalSyntheticLambda0
                @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                public final void screenshotSaved(boolean z) {
                    this.f$0.m5678x39b13365(z);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$makeScreenshot$0$org-catrobat-catroid-ui-dialogs-StageDialog, reason: not valid java name */
    /* synthetic */ void m5678x39b13365(boolean z) {
        if (!z) {
            ToastUtil.showError(this.stageActivity, R.string.error_screenshot_failed);
        } else {
            ToastUtil.showSuccess(this.stageActivity, R.string.notification_screenshot_ok);
            ProjectManager.getInstance().changedProject(ProjectManager.getInstance().getCurrentProject().getName());
        }
    }

    private void restartProject() {
        this.stageListener.reloadProject(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread activated too early!", e);
            }
        }
        StageLifeCycleController.stageResume(this.stageActivity);
    }

    private void toggleAxes() {
        Button button = (Button) findViewById(R.id.stage_dialog_button_toggle_axes);
        if (this.stageListener.axesOn) {
            this.stageListener.axesOn = false;
            button.setText(R.string.stage_dialog_axes_on);
        } else {
            this.stageListener.axesOn = true;
            button.setText(R.string.stage_dialog_axes_off);
        }
    }

    private void clearBroadcastMaps() {
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                it2.next().getIdToEventThreadMap().clear();
            }
        }
    }

    private void resetEmbroideryThreadColor() {
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                it2.next().setEmbroideryThreadColor(Color.BLACK);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/StageDialog$FinishThreadAndDisposeTexturesTask.class
      classes14.dex
     */
    private class FinishThreadAndDisposeTexturesTask extends AsyncTask<Void, Void, Void> {
        private FinishThreadAndDisposeTexturesTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... params) {
            StageDialog.this.stageActivity.manageLoadAndFinish();
            return null;
        }
    }
}
