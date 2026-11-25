package org.catrobat.catroid.formulaeditor;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import com.badlogic.gdx.Gdx;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.camera.Position;
import org.catrobat.catroid.camera.VisualDetectionHandler;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.LocalServer;
import org.catrobat.catroid.content.NetworkUtils;
import org.catrobat.catroid.content.VolumeManager;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.utils.TouchUtil;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class SensorHandler implements SensorEventListener, SensorCustomEventListener, LocationListener {
    private static final float RADIAN_TO_DEGREE_CONST = 57.295776f;
    private static final String TAG = "SensorHandler";
    private static SensorHandler instance;
    private static String listeningLanguageSensor;
    public static double timerPauseValue;
    public static double timerReferenceValue;
    private boolean accelerationAvailable;
    private Sensor accelerometerSensor;
    private boolean compassAvailable;
    private final GpsStatusHandler gpsSensor;
    private boolean inclinationAvailable;
    private boolean isGpsConnected;
    private final Sensor linearAccelerationSensor;
    private float linearAccelerationX;
    private float linearAccelerationY;
    private float linearAccelerationZ;
    private LocationManager locationManager;
    private Sensor magneticFieldSensor;
    private final Sensor rotationVectorSensor;
    private SensorLoudness sensorLoudness;
    private final SensorManagerInterface sensorManager;
    private float signAccelerationZ;
    private boolean useLinearAccelerationFallback;
    private boolean useRotationVectorFallback;
    private static final BluetoothDeviceService BT_SERVICE = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private static String userLocaleTag = Locale.getDefault().toLanguageTag();
    private final float[] rotationMatrix = new float[16];
    private final float[] rotationVector = new float[3];
    private float[] accelerationXYZ = new float[3];
    private final float[] gravity = {0.0f, 0.0f, 0.0f};
    private final Map<Sensors, Object> sensorValueMap = new HashMap();
    private float mouseX = 0.0f;
    private float mouseY = 0.0f;
    private float mouseDeltaX = 0.0f;
    private float mouseDeltaY = 0.0f;
    private float lastScrollAmount = 0.0f;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String provider) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String provider) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public static void setUserLocaleTag(String userLocale) {
        userLocaleTag = userLocale;
    }

    private SensorHandler(Context context) {
        this.compassAvailable = true;
        this.accelerationAvailable = true;
        this.inclinationAvailable = true;
        SensorManager sensorManager = new SensorManager((android.hardware.SensorManager) context.getSystemService("sensor"));
        this.sensorManager = sensorManager;
        Sensor defaultSensor = sensorManager.getDefaultSensor(10);
        this.linearAccelerationSensor = defaultSensor;
        if (defaultSensor == null) {
            this.useLinearAccelerationFallback = true;
        }
        Sensor defaultSensor2 = sensorManager.getDefaultSensor(11);
        this.rotationVectorSensor = defaultSensor2;
        if (defaultSensor2 == null) {
            this.useRotationVectorFallback = true;
        }
        if (this.useRotationVectorFallback) {
            this.accelerometerSensor = sensorManager.getDefaultSensor(1);
            Sensor defaultSensor3 = sensorManager.getDefaultSensor(2);
            this.magneticFieldSensor = defaultSensor3;
            if (this.accelerometerSensor == null) {
                this.accelerationAvailable = false;
                this.inclinationAvailable = false;
            }
            if (defaultSensor3 == null) {
                this.compassAvailable = false;
            }
        } else if (this.useLinearAccelerationFallback) {
            Sensor defaultSensor4 = sensorManager.getDefaultSensor(1);
            this.accelerometerSensor = defaultSensor4;
            if (defaultSensor4 == null) {
                this.accelerationAvailable = false;
            }
        }
        this.gpsSensor = new GpsStatusHandler(this);
    }

    public boolean compassAvailable() {
        return this.compassAvailable;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void updateMousePosition(float x, float y) {
        this.mouseDeltaX = x - this.mouseX;
        this.mouseDeltaY = y - this.mouseY;
        this.mouseX = x;
        this.mouseY = y;
    }

    public void resetMouseDelta() {
        this.mouseDeltaX = 0.0f;
        this.mouseDeltaY = 0.0f;
    }

    public static boolean gpsAvailable() {
        return gpsSensorAvailable() | networkGpsAvailable();
    }

    private static boolean gpsSensorAvailable() {
        return instance.locationManager.isProviderEnabled("gps");
    }

    private static boolean networkGpsAvailable() {
        return instance.locationManager.isProviderEnabled("network");
    }

    private static double startWeekWithMonday() {
        int i = Calendar.getInstance().get(7);
        return i != 1 ? i - 1 : 7;
    }

    public boolean accelerationAvailable() {
        return this.accelerationAvailable;
    }

    public boolean inclinationAvailable() {
        return this.inclinationAvailable;
    }

    public static SensorHandler getInstance(Context context) {
        if (instance == null) {
            instance = new SensorHandler(context);
        }
        return instance;
    }

    public void setAccelerationUnavailable() {
        this.accelerationAvailable = false;
    }

    public static void startSensorListener(Context context) {
        if (instance == null) {
            instance = new SensorHandler(context);
        }
        SensorHandler sensorHandler = instance;
        sensorHandler.sensorManager.unregisterListener((SensorEventListener) sensorHandler);
        registerListener(instance);
        VisualDetectionHandler.addListener(instance);
        SensorHandler sensorHandler2 = instance;
        SensorLoudness sensorLoudness = sensorHandler2.sensorLoudness;
        if (sensorLoudness != null) {
            sensorLoudness.registerListener(sensorHandler2);
        }
        SensorHandler sensorHandler3 = instance;
        LocationManager locationManager = sensorHandler3.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(sensorHandler3);
            SensorHandler sensorHandler4 = instance;
            sensorHandler4.gpsSensor.deregisterGNSSCallback(sensorHandler4.locationManager);
            SensorHandler sensorHandler5 = instance;
            sensorHandler5.gpsSensor.registerGNSSCallback(sensorHandler5.locationManager, context);
            if (gpsSensorAvailable()) {
                SensorHandler sensorHandler6 = instance;
                sensorHandler6.locationManager.requestLocationUpdates("gps", 0L, 0.0f, sensorHandler6);
            }
            if (networkGpsAvailable()) {
                SensorHandler sensorHandler7 = instance;
                sensorHandler7.locationManager.requestLocationUpdates("network", 0L, 0.0f, sensorHandler7);
            }
        }
    }

    public void setSensorLoudness(SensorLoudness sensorLoudness) {
        this.sensorLoudness = sensorLoudness;
    }

    public static void registerListener(SensorEventListener listener) {
        Sensor sensor;
        SensorHandler sensorHandler = instance;
        if (sensorHandler == null) {
            return;
        }
        if (!sensorHandler.useLinearAccelerationFallback) {
            sensorHandler.sensorManager.registerListener(listener, sensorHandler.linearAccelerationSensor, 1);
        }
        SensorHandler sensorHandler2 = instance;
        if (!sensorHandler2.useRotationVectorFallback) {
            sensorHandler2.sensorManager.registerListener(listener, sensorHandler2.rotationVectorSensor, 1);
        }
        SensorHandler sensorHandler3 = instance;
        if (sensorHandler3.useLinearAccelerationFallback || sensorHandler3.useRotationVectorFallback) {
            sensorHandler3.sensorManager.registerListener(listener, sensorHandler3.accelerometerSensor, 1);
        }
        SensorHandler sensorHandler4 = instance;
        if (!sensorHandler4.useRotationVectorFallback || (sensor = sensorHandler4.magneticFieldSensor) == null) {
            return;
        }
        sensorHandler4.sensorManager.registerListener(listener, sensor, 1);
    }

    public static void unregisterListener(SensorEventListener listener) {
        SensorHandler sensorHandler = instance;
        if (sensorHandler == null) {
            return;
        }
        sensorHandler.sensorManager.unregisterListener(listener);
    }

    public static void stopSensorListeners() {
        SensorHandler sensorHandler = instance;
        if (sensorHandler == null) {
            return;
        }
        sensorHandler.sensorManager.unregisterListener((SensorEventListener) sensorHandler);
        SensorHandler sensorHandler2 = instance;
        SensorLoudness sensorLoudness = sensorHandler2.sensorLoudness;
        if (sensorLoudness != null) {
            sensorLoudness.unregisterListener(sensorHandler2);
        }
        SensorHandler sensorHandler3 = instance;
        LocationManager locationManager = sensorHandler3.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(sensorHandler3);
            SensorHandler sensorHandler4 = instance;
            sensorHandler4.gpsSensor.deregisterGNSSCallback(sensorHandler4.locationManager);
        }
        VisualDetectionHandler.removeListener(instance);
    }

    public void setLastScrollAmount(float amount) {
        this.lastScrollAmount = amount;
    }

    public static Object getSensorValue(Sensors sensor) {
        ClipDescription primaryClipDescription;
        CharSequence text;
        SensorManagerInterface sensorManagerInterface = instance.sensorManager;
        Double dValueOf = Double.valueOf(0.0d);
        if (sensorManagerInterface == null) {
            return dValueOf;
        }
        float[] fArr = new float[16];
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[sensor.ordinal()]) {
            case 1:
                SensorHandler sensorHandler = instance;
                return Double.valueOf(getXAccordingToRotation(sensorHandler.linearAccelerationX, sensorHandler.linearAccelerationY));
            case 2:
                SensorHandler sensorHandler2 = instance;
                return Double.valueOf(getYAccordingToRotation(sensorHandler2.linearAccelerationX, sensorHandler2.linearAccelerationY));
            case 3:
                return Double.valueOf(instance.linearAccelerationZ);
            case 4:
                return calculateCompassDirection(fArr);
            case 5:
                return calculateXInclination(fArr);
            case 6:
                return calculateYInclination(fArr);
            case 7:
                return Double.valueOf((SystemClock.uptimeMillis() - timerReferenceValue) / 1000.0d);
            case 8:
                return Double.valueOf(Calendar.getInstance().get(1));
            case 9:
                return Double.valueOf(Calendar.getInstance().get(2) + 1);
            case 10:
                return Double.valueOf(Calendar.getInstance().get(5));
            case 11:
                return Double.valueOf(startWeekWithMonday());
            case 12:
                return Double.valueOf(Calendar.getInstance().get(11));
            case 13:
                return Double.valueOf(Calendar.getInstance().get(12));
            case 14:
                return Double.valueOf(Calendar.getInstance().get(13));
            case 15:
            case 16:
            case 17:
            case 18:
                LegoNXT legoNXT = (LegoNXT) BT_SERVICE.getDevice(BluetoothDevice.LEGO_NXT);
                if (legoNXT != null) {
                    return Double.valueOf(legoNXT.getSensorValue(sensor));
                }
                return dValueOf;
            case 19:
                return Integer.valueOf(Gdx.graphics.getFramesPerSecond());
            case 20:
            case 21:
            case 22:
            case 23:
                LegoEV3 legoEV3 = (LegoEV3) BT_SERVICE.getDevice(BluetoothDevice.LEGO_EV3);
                if (legoEV3 != null) {
                    return Double.valueOf(legoEV3.getSensorValue(sensor));
                }
                return dValueOf;
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                Phiro phiro = (Phiro) BT_SERVICE.getDevice(BluetoothDevice.PHIRO);
                if (phiro != null) {
                    return Double.valueOf(phiro.getSensorValue(sensor));
                }
                return dValueOf;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                return Double.valueOf(CastManager.getInstance().isButtonPressed(sensor) ? 1.0d : 0.0d);
            case 36:
                return Double.valueOf(TouchUtil.getLastTouchIndex());
            case 37:
                return Double.valueOf(TouchUtil.isTouching());
            case 38:
                return Double.valueOf(TouchUtil.getX(TouchUtil.getLastTouchIndex()));
            case 39:
                return Double.valueOf(TouchUtil.getY(TouchUtil.getLastTouchIndex()));
            case 40:
                return NfcHandler.getTagTechnologies();
            case 41:
                return Integer.valueOf(NfcHandler.getTagMemorySize());
            case 42:
                return Boolean.valueOf(NfcHandler.isTagWritable());
            case 43:
                return NfcHandler.getTagType();
            case 44:
                return Double.valueOf(instance.mouseX);
            case 45:
                return Double.valueOf(instance.mouseY);
            case 46:
                return Double.valueOf(Gdx.input.getDeltaX());
            case 47:
                return Double.valueOf(-Gdx.input.getDeltaY());
            case 48:
                SensorHandler sensorHandler3 = instance;
                float f = sensorHandler3.lastScrollAmount;
                sensorHandler3.lastScrollAmount = 0.0f;
                return Double.valueOf(f);
            case 49:
                return Double.valueOf(TouchUtil.getNumberOfCurrentTouches());
            case 50:
                return String.valueOf(NfcHandler.getLastNfcTagMessage());
            case 51:
                return String.valueOf(NfcHandler.getLastNfcTagId());
            case 52:
                return listeningLanguageSensor;
            case 53:
                return userLocaleTag;
            case 54:
                return Double.valueOf(Gdx.graphics.getWidth());
            case 55:
                return Double.valueOf(Gdx.graphics.getHeight());
            case 56:
                Intent intentRegisterReceiver = CatroidApplication.getAppContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                return Integer.valueOf((int) ((intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) * 100) / intentRegisterReceiver.getIntExtra("scale", -1)));
            case 57:
                return Objects.requireNonNull(VolumeManager.INSTANCE.getVolume());
            case 58:
                return LocalServer.INSTANCE.getIP();
            case 59:
                return LocalServer.INSTANCE.getPort();
            case 60:
                ClipboardManager clipboardManager = (ClipboardManager) CatroidApplication.getAppContext().getSystemService("clipboard");
                if (clipboardManager == null || !clipboardManager.hasPrimaryClip() || (primaryClipDescription = clipboardManager.getPrimaryClipDescription()) == null) {
                    return "";
                }
                if ((!primaryClipDescription.hasMimeType("text/plain") && !primaryClipDescription.hasMimeType("text/html")) || (text = clipboardManager.getPrimaryClip().getItemAt(0).getText()) == null) {
                    return "";
                }
                return text.toString();
            case 61:
                if (!StageActivity.isKeyboardVisible) {
                    return dValueOf;
                }
                int i = StageActivity.realKeyboardHeight;
                if (i > 0) {
                    return Double.valueOf(i);
                }
                StageActivity stageActivity = StageActivity.activeStageActivity.get();
                if (stageActivity != null) {
                    return Double.valueOf(stageActivity.getResources().getDisplayMetrics().heightPixels * 0.38f);
                }
                return Double.valueOf(800.0d);
            case 62:
                return Double.valueOf(((Float) Objects.requireNonNull(VolumeManager.INSTANCE.getFrequency())).floatValue());
            case 63:
                return Boolean.valueOf(NetworkUtils.isInternetAvailable());
            case 64:
                return MainMenuActivity.INSTANCE.getCpuArchitecture();
            default:
                return Objects.requireNonNull(instance.sensorValueMap.getOrDefault(sensor, dValueOf));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorHandler$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.SensorHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors;

        static {
            int[] iArr = new int[Sensors.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors = iArr;
            try {
                iArr[Sensors.X_ACCELERATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.Y_ACCELERATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.Z_ACCELERATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.COMPASS_DIRECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.X_INCLINATION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.Y_INCLINATION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.TIMER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.DATE_YEAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.DATE_MONTH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.DATE_DAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.DATE_WEEKDAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.TIME_HOUR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.TIME_MINUTE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.TIME_SECOND.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_1.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_2.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_3.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_4.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.FPS.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_1.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_2.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_3.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_4.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_BOTTOM_LEFT.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_BOTTOM_RIGHT.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_FRONT_LEFT.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_FRONT_RIGHT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_SIDE_LEFT.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_SIDE_RIGHT.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_A_PRESSED.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_B_PRESSED.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_DOWN_PRESSED.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_LEFT_PRESSED.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_RIGHT_PRESSED.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.GAMEPAD_UP_PRESSED.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.LAST_FINGER_INDEX.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.FINGER_TOUCHED.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.FINGER_X.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.FINGER_Y.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_TECH_LIST.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_MEMORY_SIZE.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_IS_WRITABLE.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_TAG_TYPE.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MOUSE_X.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MOUSE_Y.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MOUSE_DELTA_X.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MOUSE_DELTA_Y.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MOUSE_SCROLL.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NUMBER_CURRENT_TOUCHES.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_TAG_MESSAGE.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NFC_TAG_ID.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.SPEECH_RECOGNITION_LANGUAGE.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.USER_LANGUAGE.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.STAGE_WIDTH.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.STAGE_HEIGHT.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.BATTARY.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.MICRO.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.IP.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PORT.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.CLIPBOARD_TEXT.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.KEYBOARD_HEIGHT.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.FREQ.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.INTERNET.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.ARCH.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
        }
    }

    private static Double calculateCompassDirection(float[] rotationMatrixOut) {
        float[] fArr = new float[3];
        SensorHandler sensorHandler = instance;
        if (!sensorHandler.useRotationVectorFallback) {
            android.hardware.SensorManager.getRotationMatrixFromVector(sensorHandler.rotationMatrix, sensorHandler.rotationVector);
        }
        int iRotateOrientation = rotateOrientation();
        if (iRotateOrientation == 1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else if (iRotateOrientation == -1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else {
            android.hardware.SensorManager.getOrientation(instance.rotationMatrix, fArr);
        }
        return Double.valueOf(((double) fArr[0]) * 57.2957763671875d * (-1.0d));
    }

    private static Object calculateXInclination(float[] rotationMatrixOut) {
        double dAcos;
        SensorHandler sensorHandler = instance;
        if (sensorHandler.useRotationVectorFallback) {
            int iRotateOrientation = rotateOrientation();
            if (iRotateOrientation != 0) {
                dAcos = Math.acos(instance.accelerationXYZ[1] * iRotateOrientation);
            } else {
                dAcos = Math.acos(instance.accelerationXYZ[0]);
            }
            float f = ((float) dAcos) * 57.295776f;
            float f2 = 0.0f;
            if (f >= 90.0f && f <= 180.0f) {
                f2 = instance.signAccelerationZ > 0.0f ? -(f - 90.0f) : -((90.0f - f) + 180.0f);
            } else if (f >= 0.0f && f < 90.0f) {
                f2 = instance.signAccelerationZ > 0.0f ? 90.0f - f : f + 90.0f;
            }
            if (rotateOrientation() != 0) {
                f2 = -f2;
            }
            return Double.valueOf(f2);
        }
        float[] fArr = new float[3];
        android.hardware.SensorManager.getRotationMatrixFromVector(sensorHandler.rotationMatrix, sensorHandler.rotationVector);
        int iRotateOrientation2 = rotateOrientation();
        if (iRotateOrientation2 == 1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else if (iRotateOrientation2 == -1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else {
            android.hardware.SensorManager.getOrientation(instance.rotationMatrix, fArr);
        }
        return Double.valueOf(((double) fArr[2]) * 57.2957763671875d * (-1.0d));
    }

    private static Object calculateYInclination(float[] rotationMatrixOut) {
        double dAcos;
        SensorHandler sensorHandler = instance;
        float f = 0.0f;
        if (sensorHandler.useRotationVectorFallback) {
            int iRotateOrientation = rotateOrientation();
            if (iRotateOrientation != 0) {
                dAcos = Math.acos(instance.accelerationXYZ[0] * iRotateOrientation);
            } else {
                dAcos = Math.acos(instance.accelerationXYZ[1]);
            }
            float f2 = ((float) dAcos) * 57.295776f;
            if (f2 >= 90.0f && f2 <= 180.0f) {
                f = instance.signAccelerationZ > 0.0f ? -(f2 - 90.0f) : -((90.0f - f2) + 180.0f);
            } else if (f2 >= 0.0f && f2 < 90.0f) {
                f = instance.signAccelerationZ > 0.0f ? 90.0f - f2 : f2 + 90.0f;
            }
            return Double.valueOf(f);
        }
        float[] fArr = new float[3];
        android.hardware.SensorManager.getRotationMatrixFromVector(sensorHandler.rotationMatrix, sensorHandler.rotationVector);
        if (rotateOrientation() == 1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 2, 129, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else if (rotateOrientation() == -1) {
            android.hardware.SensorManager.remapCoordinateSystem(instance.rotationMatrix, 130, 1, rotationMatrixOut);
            android.hardware.SensorManager.getOrientation(rotationMatrixOut, fArr);
        } else {
            android.hardware.SensorManager.getOrientation(instance.rotationMatrix, fArr);
        }
        float f3 = fArr[2] * 57.295776f * (-1.0f);
        double d = fArr[1];
        if (Math.abs(f3) <= 90.0f) {
            return Double.valueOf(d * 57.2957763671875d * (-1.0d));
        }
        float f4 = (float) (d * 57.2957763671875d * (-1.0d));
        if (f4 > 0.0f) {
            return Double.valueOf(180.0d - ((double) f4));
        }
        return Double.valueOf((-180.0d) - ((double) f4));
    }

    public static String getListeningLanguageSensor() {
        return listeningLanguageSensor;
    }

    public static void setListeningLanguageSensor(String listeningLanguageTag) {
        listeningLanguageSensor = listeningLanguageTag;
        Log.d(TAG, "listening language sensor changed to: " + listeningLanguageSensor);
    }

    private static boolean isDeviceDefaultRotationLandscape() {
        int rotation = ((WindowManager) CatroidApplication.getAppContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW)).getDefaultDisplay().getRotation();
        Configuration configuration = CatroidApplication.getAppContext().getResources().getConfiguration();
        if (configuration.orientation == 2 && (rotation == 0 || rotation == 2)) {
            return true;
        }
        return configuration.orientation == 1 && (rotation == 1 || rotation == 3);
    }

    private static int rotateOrientation() {
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode() ^ isDeviceDefaultRotationLandscape()) {
            return ProjectManager.getInstance().isCurrentProjectLandscapeMode() ? 1 : -1;
        }
        return 0;
    }

    public static Position getPositionAccordingToRotation(Position original) {
        return new Position(getXAccordingToRotation(original.getX(), original.getY()), getYAccordingToRotation(original.getX(), original.getY()));
    }

    private static double getXAccordingToRotation(double x, double y) {
        int iRotateOrientation = rotateOrientation();
        return iRotateOrientation != 0 ? (-y) * ((double) iRotateOrientation) : x;
    }

    private static double getYAccordingToRotation(double x, double y) {
        int iRotateOrientation = rotateOrientation();
        return iRotateOrientation != 0 ? ((double) iRotateOrientation) * x : y;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == 1) {
            float[] fArr = (float[]) event.values.clone();
            this.accelerationXYZ = fArr;
            if (this.useLinearAccelerationFallback) {
                determinePseudoLinearAcceleration((float[]) fArr.clone());
            }
            float[] fArr2 = this.accelerationXYZ;
            float f = fArr2[0];
            float f2 = fArr2[1];
            float f3 = fArr2[2];
            double dSqrt = Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
            float[] fArr3 = this.accelerationXYZ;
            fArr3[0] = (float) (((double) fArr3[0]) / dSqrt);
            fArr3[1] = (float) (((double) fArr3[1]) / dSqrt);
            fArr3[2] = (float) (((double) fArr3[2]) / dSqrt);
            this.signAccelerationZ = Math.signum(event.values[2]);
            return;
        }
        if (type == 2) {
            android.hardware.SensorManager.getRotationMatrix(this.rotationMatrix, new float[9], this.accelerationXYZ, (float[]) event.values.clone());
        } else if (type == 10) {
            this.linearAccelerationX = event.values[0];
            this.linearAccelerationY = event.values[1];
            this.linearAccelerationZ = event.values[2];
        } else {
            if (type == 11) {
                this.rotationVector[0] = event.values[0];
                this.rotationVector[1] = event.values[1];
                this.rotationVector[2] = event.values[2];
                return;
            }
            Log.v(TAG, "Unhandled sensor type: " + event.sensor.getType());
        }
    }

    private void determinePseudoLinearAcceleration(float[] input) {
        float[] fArr = this.gravity;
        float f = (fArr[0] * 0.8f) + (input[0] * 0.19999999f);
        fArr[0] = f;
        float f2 = (fArr[1] * 0.8f) + (input[1] * 0.19999999f);
        fArr[1] = f2;
        float f3 = (fArr[2] * 0.8f) + (0.19999999f * input[2]);
        fArr[2] = f3;
        this.linearAccelerationX = (input[0] - f) * (-1.0f);
        this.linearAccelerationY = (input[1] - f2) * (-1.0f);
        this.linearAccelerationZ = (input[2] - f3) * (-1.0f);
    }

    @Override // org.catrobat.catroid.formulaeditor.SensorCustomEventListener
    public void onCustomSensorChanged(SensorCustomEvent event) {
        this.sensorValueMap.put(event.getSensor(), event.getValue());
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        if (Objects.equals(location.getProvider(), "gps") || !this.isGpsConnected) {
            this.sensorValueMap.put(Sensors.LATITUDE, Double.valueOf(location.getLatitude()));
            this.sensorValueMap.put(Sensors.LONGITUDE, Double.valueOf(location.getLongitude()));
            this.sensorValueMap.put(Sensors.LOCATION_ACCURACY, Float.valueOf(location.getAccuracy()));
            this.sensorValueMap.put(Sensors.ALTITUDE, Double.valueOf(location.hasAltitude() ? location.getAltitude() : 0.0d));
        }
        if (Objects.equals(location.getProvider(), "gps")) {
            instance.gpsSensor.setLastLocationAndGpsMillis(location, SystemClock.elapsedRealtime());
        }
    }

    public void setIsGpsConnected(boolean isGpsConnected) {
        this.isGpsConnected = isGpsConnected;
    }

    public static void destroy() {
        if (instance == null) {
            return;
        }
        stopSensorListeners();
        instance = null;
    }
}
