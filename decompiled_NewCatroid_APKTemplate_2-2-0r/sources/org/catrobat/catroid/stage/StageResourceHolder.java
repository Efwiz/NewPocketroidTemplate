package org.catrobat.catroid.stage;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.SensorLoudness;
import org.catrobat.catroid.sensing.GatherCollisionInformationTask;
import org.catrobat.catroid.ui.runtimepermissions.BrickResourcesToRuntimePermissions;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.TouchUtil;
import org.catrobat.catroid.utils.VibrationManager;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageResourceHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StageResourceHolder implements GatherCollisionInformationTask.OnPolygonLoadedListener {
    private static final int REQUEST_CONNECT_DEVICE = 1000;
    private static final int REQUEST_GPS = 1;
    private static final String TAG = "StageResourceHolder";
    private Set<Integer> failedResources;
    private int requiredResourceCounter;
    private Brick.ResourcesSet requiredResourcesSet;
    private final SpeechRecognitionHolderFactory speechRecognitionHolderFactory = (SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class);
    private StageActivity stageActivity;

    StageResourceHolder(final StageActivity stageActivity) {
        this.stageActivity = stageActivity;
        TouchUtil.reset();
    }

    public static List<String> getProjectsRuntimePermissionList() {
        return BrickResourcesToRuntimePermissions.translate(ProjectManager.getInstance().getCurrentProject().getRequiredResources());
    }

    public void initResources() {
        this.failedResources = new HashSet();
        Brick.ResourcesSet requiredResources = ProjectManager.getInstance().getCurrentProject().getRequiredResources();
        this.requiredResourcesSet = requiredResources;
        this.requiredResourceCounter = requiredResources.size();
        SensorHandler sensorHandler = SensorHandler.getInstance(this.stageActivity);
        if (this.requiredResourcesSet.contains(13)) {
            if (sensorHandler.accelerationAvailable()) {
                resourceInitialized();
            } else {
                resourceFailed(13);
            }
        }
        if (this.requiredResourcesSet.contains(14)) {
            if (sensorHandler.inclinationAvailable()) {
                resourceInitialized();
            } else {
                resourceFailed(14);
            }
        }
        if (this.requiredResourcesSet.contains(15)) {
            if (sensorHandler.compassAvailable()) {
                resourceInitialized();
            } else {
                resourceFailed(15);
            }
        }
        if (this.requiredResourcesSet.contains(24)) {
            sensorHandler.setSensorLoudness(new SensorLoudness());
            resourceInitialized();
        }
        if (this.requiredResourcesSet.contains(18)) {
            sensorHandler.setLocationManager((LocationManager) this.stageActivity.getSystemService(FirebaseAnalytics.Param.LOCATION));
            if (SensorHandler.gpsAvailable()) {
                resourceInitialized();
            } else {
                Intent intent = new Intent();
                intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                this.stageActivity.startActivityForResult(intent, 1);
            }
        }
        if (this.requiredResourcesSet.contains(1)) {
            MobileServiceAvailability mobileServiceAvailability = (MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class);
            if (mobileServiceAvailability.isGmsAvailable(this.stageActivity)) {
                TextToSpeechHolder.INSTANCE.getInstance().initTextToSpeech(this.stageActivity, this);
            } else if (mobileServiceAvailability.isHmsAvailable(this.stageActivity)) {
                HuaweiTextToSpeechHolder.INSTANCE.getInstance().initTextToSpeech(this.stageActivity, this);
            }
        }
        if (this.requiredResourcesSet.contains(2)) {
            connectBTDevice(BluetoothDevice.LEGO_NXT);
        }
        if (this.requiredResourcesSet.contains(20)) {
            connectBTDevice(BluetoothDevice.LEGO_EV3);
        }
        if (this.requiredResourcesSet.contains(10)) {
            connectBTDevice(BluetoothDevice.PHIRO);
        }
        if (this.requiredResourcesSet.contains(6)) {
            connectBTDevice(BluetoothDevice.ARDUINO);
        }
        if (ProjectManager.getInstance().getCurrentProject().hasMultiplayerVariables()) {
            this.requiredResourceCounter++;
            connectBTDevice(BluetoothDevice.MULTIPLAYER);
        }
        if (this.requiredResourcesSet.contains(11)) {
            if (getCameraManager().getHasBackCamera()) {
                resourceInitialized();
            } else {
                resourceFailed(11);
            }
        }
        if (this.requiredResourcesSet.contains(12)) {
            if (getCameraManager().getHasFrontCamera()) {
                resourceInitialized();
            } else {
                resourceFailed(12);
            }
        }
        if (this.requiredResourcesSet.contains(17)) {
            if (getCameraManager().getHasFrontCamera() || getCameraManager().getHasBackCamera()) {
                resourceInitialized();
            } else {
                resourceFailed(17);
            }
        }
        if (this.requiredResourcesSet.contains(8)) {
            if (getCameraManager().getHasFlash()) {
                resourceInitialized();
            } else {
                resourceFailed(8);
            }
        }
        if (this.requiredResourcesSet.contains(9)) {
            Vibrator vibrator = (Vibrator) this.stageActivity.getSystemService("vibrator");
            if (vibrator != null) {
                getVibrationManager().setVibration(vibrator);
                resourceInitialized();
            } else {
                resourceFailed(9);
            }
        }
        if (this.requiredResourcesSet.contains(16)) {
            if (this.requiredResourcesSet.contains(4)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.stageActivity, 2131952355));
                builder.setMessage(this.stageActivity.getString(R.string.nfc_facedetection_support)).setCancelable(false).setPositiveButton(this.stageActivity.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageResourceHolder.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int id2) {
                        StageResourceHolder.this.nfcInitialize();
                    }
                });
                builder.create().show();
            } else {
                nfcInitialize();
            }
        }
        if (this.requiredResourcesSet.contains(4)) {
            if (getCameraManager().startDetection()) {
                resourceInitialized();
            } else {
                resourceFailed(4);
            }
        }
        if (this.requiredResourcesSet.contains(28)) {
            if (getCameraManager().startDetection()) {
                resourceInitialized();
            } else {
                resourceFailed(28);
            }
        }
        if (this.requiredResourcesSet.contains(27)) {
            if (getCameraManager().startDetection()) {
                resourceInitialized();
            } else {
                resourceFailed(27);
            }
        }
        if (this.requiredResourcesSet.contains(5)) {
            if (getCameraManager().startDetection()) {
                resourceInitialized();
            } else {
                resourceFailed(5);
            }
        }
        if (this.requiredResourcesSet.contains(22)) {
            if (CastManager.getInstance().isConnected()) {
                resourceInitialized();
            } else {
                if (!SettingsFragment.isCastSharedPreferenceEnabled(this.stageActivity)) {
                    StageActivity stageActivity = this.stageActivity;
                    ToastUtil.showError(stageActivity, stageActivity.getString(R.string.cast_enable_cast_feature));
                } else if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                    StageActivity stageActivity2 = this.stageActivity;
                    ToastUtil.showError(stageActivity2, stageActivity2.getString(R.string.cast_error_not_connected_msg));
                } else {
                    StageActivity stageActivity3 = this.stageActivity;
                    ToastUtil.showError(stageActivity3, stageActivity3.getString(R.string.cast_error_cast_bricks_in_no_cast_project));
                }
                endStageActivity();
            }
        }
        if (this.requiredResourcesSet.contains(19)) {
            new GatherCollisionInformationTask(this).execute(new Void[0]);
        }
        if (this.requiredResourcesSet.contains(21)) {
            resourceInitialized();
        }
        if (this.requiredResourcesSet.contains(7)) {
            RaspberryPiService.getInstance().enableRaspberryInterruptPinsForProject(ProjectManager.getInstance().getCurrentProject());
            connectRaspberrySocket();
        }
        if (this.requiredResourcesSet.contains(26)) {
            File file = Constants.EXTERNAL_STORAGE_ROOT_EXPORT_DIRECTORY;
            if (file.exists() || file.mkdirs()) {
                resourceInitialized();
            } else {
                resourceFailed(26);
            }
        }
        if (this.requiredResourcesSet.contains(25)) {
            resourceInitialized();
        }
        if (this.requiredResourcesSet.contains(23)) {
            if (this.speechRecognitionHolderFactory.isRecognitionAvailable(this.stageActivity)) {
                resourceInitialized();
            } else {
                resourceFailed(23);
            }
        }
        if (initFinished()) {
            initFinishedRunStage();
        }
    }

    public boolean initFinished() {
        return this.requiredResourceCounter == 0 && this.failedResources.isEmpty();
    }

    public void initFinishedRunStage() {
        try {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).initialise();
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
        this.stageActivity.setupAskHandler();
        this.speechRecognitionHolderFactory.getInstance().initSpeechRecognition(this.stageActivity, this);
        Intent intent = new Intent(this.stageActivity, getClass());
        if (Build.VERSION.SDK_INT >= 31) {
            intent.addFlags(CompilerOptions.RedundantSuperinterface);
            StageActivity stageActivity = this.stageActivity;
            stageActivity.pendingIntent = PendingIntent.getActivity(stageActivity, 0, intent, CompilerOptions.RedundantSuperinterface);
        } else {
            intent.addFlags(536870912);
            StageActivity stageActivity2 = this.stageActivity;
            stageActivity2.pendingIntent = PendingIntent.getActivity(stageActivity2, 0, new Intent(this.stageActivity, getClass()).addFlags(536870912), 67108864);
        }
        StageActivity stageActivity3 = this.stageActivity;
        stageActivity3.nfcAdapter = NfcAdapter.getDefaultAdapter(stageActivity3);
        this.stageActivity.stageListener.setPaused(false);
    }

    public synchronized void resourceFailed(int failedResource) {
        Log.d(TAG, "resourceFailed: " + failedResource);
        this.failedResources.add(Integer.valueOf(failedResource));
        resourceInitialized();
    }

    public synchronized void resourceInitialized() {
        int i = this.requiredResourceCounter - 1;
        this.requiredResourceCounter = i;
        if (i == 0) {
            if (this.failedResources.isEmpty()) {
                initFinishedRunStage();
            } else {
                showResourceFailedErrorDialog();
            }
        }
    }

    private CameraManager getCameraManager() {
        if (this.stageActivity.cameraManager == null) {
            this.stageActivity.cameraManager = new CameraManager(this.stageActivity);
        }
        return this.stageActivity.cameraManager;
    }

    public VibrationManager getVibrationManager() {
        if (this.stageActivity.vibrationManager == null) {
            this.stageActivity.vibrationManager = new VibrationManager();
        }
        return this.stageActivity.vibrationManager;
    }

    public void endStageActivity() {
        this.stageActivity.setResult(0, new Intent());
        this.stageActivity.finish();
    }

    private void showResourceFailedErrorDialog() {
        StringBuilder sb = new StringBuilder(this.stageActivity.getString(R.string.prestage_resource_not_available_text));
        Iterator<Integer> it = this.failedResources.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (iIntValue == 1) {
                sb.append(this.stageActivity.getString(R.string.prestage_text_to_speech_error));
            } else if (iIntValue == 23) {
                sb.append(this.stageActivity.getString(R.string.speech_recognition_not_available));
            } else if (iIntValue == 4) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_face_detection_available));
            } else if (iIntValue == 5) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_text_detection_available));
            } else if (iIntValue == 8) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_flash_available));
            } else if (iIntValue == 9) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_vibration_available));
            } else if (iIntValue == 17) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_camera_available));
            } else if (iIntValue == 18) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_gps_sensor_available));
            } else if (iIntValue == 27) {
                sb.append(this.stageActivity.getString(R.string.prestage_no_pose_detection_available));
            } else if (iIntValue != 28) {
                switch (iIntValue) {
                    case 11:
                        sb.append(this.stageActivity.getString(R.string.prestage_no_back_camera_available));
                        break;
                    case 12:
                        sb.append(this.stageActivity.getString(R.string.prestage_no_front_camera_available));
                        break;
                    case 13:
                        sb.append(this.stageActivity.getString(R.string.prestage_no_acceleration_sensor_available));
                        break;
                    case 14:
                        sb.append(this.stageActivity.getString(R.string.prestage_no_inclination_sensor_available));
                        break;
                    case 15:
                        sb.append(this.stageActivity.getString(R.string.prestage_no_compass_sensor_available));
                        break;
                    default:
                        sb.append(this.stageActivity.getString(R.string.prestage_default_resource_not_available));
                        break;
                }
            } else {
                sb.append(this.stageActivity.getString(R.string.prestage_no_object_detection_available));
            }
        }
        new AlertDialog.Builder(new ContextThemeWrapper(this.stageActivity, 2131952355)).setTitle(R.string.prestage_resource_not_available_title).setMessage(sb).setCancelable(false).setPositiveButton(this.stageActivity.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageResourceHolder$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5557x90893715(dialogInterface, i);
            }
        }).create().show();
    }

    /* JADX INFO: renamed from: lambda$showResourceFailedErrorDialog$0$org-catrobat-catroid-stage-StageResourceHolder, reason: not valid java name */
    /* synthetic */ void m5557x90893715(DialogInterface dialogInterface, int i) {
        endStageActivity();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 0 && SensorHandler.gpsAvailable()) {
                resourceInitialized();
                return;
            } else {
                resourceFailed(18);
                return;
            }
        }
        if (requestCode != 1000) {
            endStageActivity();
        } else if (resultCode == -1) {
            resourceInitialized();
        } else {
            if (resultCode != 0) {
                return;
            }
            endStageActivity();
        }
    }

    private void connectBTDevice(Class<? extends BluetoothDevice> service) {
        if (((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(service, this.stageActivity, 1000) == BluetoothDeviceService.ConnectDeviceResult.ALREADY_CONNECTED) {
            resourceInitialized();
        }
    }

    private void connectRaspberrySocket() {
        String raspiHost = SettingsFragment.getRaspiHost(this.stageActivity);
        int raspiPort = SettingsFragment.getRaspiPort(this.stageActivity);
        if (RaspberryPiService.getInstance().connect(raspiHost, raspiPort)) {
            resourceInitialized();
            return;
        }
        StageActivity stageActivity = this.stageActivity;
        ToastUtil.showError(stageActivity, stageActivity.getString(R.string.error_connecting_to, new Object[]{raspiHost, Integer.valueOf(raspiPort)}));
        endStageActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nfcInitialize() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.stageActivity);
        if (defaultAdapter != null && !defaultAdapter.isEnabled()) {
            ToastUtil.showError(this.stageActivity, R.string.nfc_not_activated);
            this.stageActivity.startActivity(new Intent("android.settings.NFC_SETTINGS"));
        } else if (defaultAdapter == null) {
            ToastUtil.showError(this.stageActivity, R.string.no_nfc_available);
        }
        resourceInitialized();
    }

    @Override // org.catrobat.catroid.sensing.GatherCollisionInformationTask.OnPolygonLoadedListener
    public void onFinished() {
        resourceInitialized();
    }
}
