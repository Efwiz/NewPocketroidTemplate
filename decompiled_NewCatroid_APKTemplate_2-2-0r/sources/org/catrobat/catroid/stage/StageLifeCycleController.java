package org.catrobat.catroid.stage;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceView;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserDataWrapper;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.io.StageAudioFocus;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.catrobat.catroid.ui.dialogs.StageDialog;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageLifeCycleController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class StageLifeCycleController {
    private static final int REQUEST_PERMISSIONS_STAGE_RESOURCE_CREATE = 601;
    public static final String TAG = "StageLifeCycleController";

    private StageLifeCycleController() {
        throw new AssertionError("no.");
    }

    static void stageCreate(final StageActivity stageActivity) {
        if (ProjectManager.getInstance().getCurrentProject() == null) {
            stageActivity.finish();
            Log.d(TAG, "no current project set, cowardly refusing to run");
            return;
        }
        StageActivity.numberOfSpritesCloned = 0;
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            stageActivity.setRequestedOrientation(0);
        } else {
            stageActivity.setRequestedOrientation(1);
        }
        UserDataWrapper.resetAllUserData(ProjectManager.getInstance().getCurrentProject());
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            it.next().firstStart = true;
        }
        stageActivity.getWindow().addFlags(128);
        stageActivity.stageListener = new StageListener();
        stageActivity.stageDialog = new StageDialog(stageActivity, stageActivity.stageListener, R.style.StageDialog);
        stageActivity.brickDialogManager = new BrickDialogManager(stageActivity);
        stageActivity.calculateScreenSizes();
        stageActivity.configuration = new AndroidApplicationConfiguration();
        AndroidApplicationConfiguration androidApplicationConfiguration = stageActivity.configuration;
        AndroidApplicationConfiguration androidApplicationConfiguration2 = stageActivity.configuration;
        AndroidApplicationConfiguration androidApplicationConfiguration3 = stageActivity.configuration;
        stageActivity.configuration.a = 8;
        androidApplicationConfiguration3.b = 8;
        androidApplicationConfiguration2.g = 8;
        androidApplicationConfiguration.r = 8;
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            stageActivity.setRequestedOrientation(0);
            stageActivity.setContentView(R.layout.activity_stage_gamepad);
            CastManager.getInstance().initializeGamepadActivity(stageActivity);
            CastManager.getInstance().addStageViewToLayout((GLSurfaceView20) stageActivity.initializeForView(stageActivity.stageListener, stageActivity.configuration));
        } else {
            stageActivity.initialize(stageActivity.stageListener, stageActivity.configuration);
        }
        if (stageActivity.getGdxGraphics().getView() instanceof SurfaceView) {
            ((SurfaceView) stageActivity.getGdxGraphics().getView()).getHolder().setFormat(-3);
        }
        stageActivity.stageAudioFocus = new StageAudioFocus(stageActivity);
        stageActivity.stageResourceHolder = new StageResourceHolder(stageActivity);
        MidiSoundManager.getInstance().reset();
        List<String> projectsRuntimePermissionList = StageResourceHolder.getProjectsRuntimePermissionList();
        if (projectsRuntimePermissionList.isEmpty()) {
            stageActivity.stageResourceHolder.initResources();
        } else {
            new RequiresPermissionTask(601, projectsRuntimePermissionList, R.string.runtime_permission_general) { // from class: org.catrobat.catroid.stage.StageLifeCycleController.1
                @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
                public void task() {
                    stageActivity.stageResourceHolder.initResources();
                }
            }.execute(stageActivity);
        }
    }

    static void stagePause(final StageActivity stageActivity) {
        if (RequiresPermissionTask.checkPermission(stageActivity, StageResourceHolder.getProjectsRuntimePermissionList())) {
            if (stageActivity.nfcAdapter != null) {
                try {
                    stageActivity.nfcAdapter.disableForegroundDispatch(stageActivity);
                } catch (IllegalStateException e) {
                    Log.e(TAG, "Disabling NFC foreground dispatching went wrong!", e);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            List<Sprite> spritesFromStage = ((StageListener) stageActivity.getApplicationListener()).getSpritesFromStage();
            if (spritesFromStage != null) {
                for (int i = 0; i < spritesFromStage.size(); i++) {
                    try {
                        Sprite sprite = spritesFromStage.get(i);
                        if (sprite != null && sprite.look != null) {
                            sprite.look.pauseParticleEffect();
                        }
                    } catch (IndexOutOfBoundsException | NullPointerException unused) {
                    }
                }
            }
            ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
            SensorHandler.timerPauseValue = SystemClock.uptimeMillis();
            SensorHandler.stopSensorListeners();
            SoundManager.getInstance().pause();
            MidiSoundManager.getInstance().pause();
            StageActivity.getActiveStageListener().menuPause();
            stageActivity.stageAudioFocus.releaseAudioFocus();
            if (stageActivity.cameraManager != null) {
                stageActivity.cameraManager.pause();
            }
            BluetoothDeviceService bluetoothDeviceService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
            if (bluetoothDeviceService != null) {
                bluetoothDeviceService.pause();
            }
            if (stageActivity.vibrationManager != null) {
                stageActivity.vibrationManager.pause();
            }
            if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                CastManager.getInstance().setRemoteLayoutToPauseScreen(stageActivity);
            }
        }
    }

    public static void stageResume(final StageActivity stageActivity) {
        List<Sprite> spritesFromStage;
        if (!stageActivity.dialogIsShowing() && RequiresPermissionTask.checkPermission(stageActivity, StageResourceHolder.getProjectsRuntimePermissionList())) {
            Brick.ResourcesSet requiredResources = ProjectManager.getInstance().getCurrentProject().getRequiredResources();
            List<Sprite> spriteList = ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList();
            SensorHandler.startSensorListener(stageActivity);
            Iterator<Sprite> it = spriteList.iterator();
            while (it.hasNext()) {
                if (it.next().getPlaySoundBricks().size() > 0) {
                    stageActivity.stageAudioFocus.requestAudioFocus();
                    break;
                }
            }
            try {
                spritesFromStage = ((StageListener) stageActivity.getApplicationListener()).getSpritesFromStage();
            } catch (Exception unused) {
                spritesFromStage = null;
            }
            if (spritesFromStage != null) {
                for (int i = 0; i < spritesFromStage.size(); i++) {
                    try {
                        Sprite sprite = spritesFromStage.get(i);
                        if (sprite != null && sprite.look != null) {
                            sprite.look.resumeParticleEffect();
                        }
                    } catch (IndexOutOfBoundsException | NullPointerException unused2) {
                    }
                }
            }
            if (stageActivity.vibrationManager != null) {
                stageActivity.vibrationManager.resume();
            }
            if (requiredResources.contains(2) || requiredResources.contains(10) || requiredResources.contains(6) || ProjectManager.getInstance().getCurrentProject().hasMultiplayerVariables()) {
                try {
                    ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).start();
                } catch (MindstormsException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            if (stageActivity.cameraManager != null) {
                stageActivity.cameraManager.resume();
            }
            if (requiredResources.contains(1)) {
                stageActivity.stageAudioFocus.requestAudioFocus();
            }
            if (requiredResources.contains(16)) {
                stageActivity.nfcAdapter = NfcAdapter.getDefaultAdapter(stageActivity);
                if (stageActivity.nfcAdapter != null) {
                    stageActivity.pendingIntent = PendingIntent.getActivity(stageActivity, 0, new Intent(stageActivity, stageActivity.getClass()).addFlags(536870912), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728);
                    stageActivity.nfcAdapter.enableForegroundDispatch(stageActivity, stageActivity.pendingIntent, null, null);
                    Log.d(TAG, "NFC foreground dispatch enabled.");
                } else {
                    Log.w(TAG, "NFC is required by the project, but the device does not support it.");
                }
            }
            if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                CastManager.getInstance().resumeRemoteLayoutFromPauseScreen();
            }
            SoundManager.getInstance().resume();
            MidiSoundManager.getInstance().resume();
            if (stageActivity.stageResourceHolder.initFinished()) {
                try {
                    StageActivity.getActiveStageListener().menuResume();
                } catch (Exception unused3) {
                }
            }
        }
    }

    static void stageDestroy(StageActivity stageActivity) {
        if (RequiresPermissionTask.checkPermission(stageActivity, StageResourceHolder.getProjectsRuntimePermissionList())) {
            if (stageActivity.brickDialogManager != null) {
                stageActivity.brickDialogManager.dismissAllDialogs();
            }
            BluetoothDeviceService bluetoothDeviceService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
            if (bluetoothDeviceService != null) {
                bluetoothDeviceService.destroy();
            }
            stageActivity.vibrationManager = null;
            if (stageActivity.cameraManager != null) {
                stageActivity.cameraManager.destroy();
                stageActivity.cameraManager = null;
            }
            SensorHandler.destroy();
            if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                CastManager.getInstance().onStageDestroyed();
            }
            StageActivity.getActiveStageListener().finish();
            stageActivity.manageLoadAndFinish();
        }
        ProjectManager.getInstance().setCurrentlyPlayingScene(ProjectManager.getInstance().getCurrentlyEditedScene());
    }
}
