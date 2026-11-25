package org.catrobat.catroid.devices.arduino.phiro;

import android.util.Log;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import name.antonsmirnov.firmata.Firmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.serial.StreamingSerialAdapter;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/phiro/PhiroImpl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroImpl implements Phiro {
    private static final int MAX_PWM_PIN = 13;
    private static final int MAX_SENSOR_PIN = 5;
    private static final int MAX_VALUE = 255;
    private static final int MIN_PWM_PIN = 2;
    private static final int MIN_SENSOR_PIN = 0;
    private static final int MIN_VALUE = 0;
    private static final int PIN_LEFT_MOTOR_FORWARD_BACKWARD = 11;
    private static final int PIN_LEFT_MOTOR_SPEED = 10;
    private static final int PIN_RGB_BLUE_LEFT = 9;
    private static final int PIN_RGB_BLUE_RIGHT = 6;
    private static final int PIN_RGB_GREEN_LEFT = 8;
    private static final int PIN_RGB_GREEN_RIGHT = 5;
    private static final int PIN_RGB_RED_LEFT = 7;
    private static final int PIN_RGB_RED_RIGHT = 4;
    private static final int PIN_RIGHT_MOTOR_FORWARD_BACKWARD = 2;
    private static final int PIN_RIGHT_MOTOR_SPEED = 12;
    public static final int PIN_SENSOR_BOTTOM_LEFT = 3;
    public static final int PIN_SENSOR_BOTTOM_RIGHT = 2;
    public static final int PIN_SENSOR_FRONT_LEFT = 4;
    public static final int PIN_SENSOR_FRONT_RIGHT = 1;
    public static final int PIN_SENSOR_SIDE_LEFT = 5;
    public static final int PIN_SENSOR_SIDE_RIGHT = 0;
    private static final int PIN_SPEAKER_OUT = 3;
    private BluetoothConnection btConnection;
    private Firmata firmata;
    private PhiroListener phiroListener;
    private static final UUID PHIRO_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "PhiroImpl";
    private boolean isInitialized = false;
    private boolean isReportingSensorData = false;
    Timer timer = new Timer();
    TimerTask currentStopPlayToneTask = null;

    private int checkRBGValue(int rgbValue) {
        if (rgbValue > 255) {
            return 255;
        }
        if (rgbValue < 0) {
            return 0;
        }
        return rgbValue;
    }

    private int percentToSpeed(int percent) {
        if (percent <= 0) {
            return 0;
        }
        if (percent >= 100) {
            return 255;
        }
        return (int) (((double) percent) * 2.55d);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public synchronized void playTone(int selectedTone, int durationInSeconds) {
        sendAnalogFirmataMessage(3, selectedTone);
        TimerTask timerTask = this.currentStopPlayToneTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
        StopPlayToneTask stopPlayToneTask = new StopPlayToneTask();
        this.currentStopPlayToneTask = stopPlayToneTask;
        this.timer.schedule(stopPlayToneTask, durationInSeconds * 1000);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/phiro/PhiroImpl$StopPlayToneTask.class
      classes14.dex
     */
    private class StopPlayToneTask extends TimerTask {
        private StopPlayToneTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            PhiroImpl.this.sendAnalogFirmataMessage(3, 0);
        }
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void moveLeftMotorForward(int speedInPercent) {
        sendAnalogFirmataMessage(10, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(11, 0);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void moveLeftMotorBackward(int speedInPercent) {
        sendAnalogFirmataMessage(10, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(11, 255);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void moveRightMotorForward(int speedInPercent) {
        sendAnalogFirmataMessage(12, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(2, 255);
        sendAnalogFirmataMessage(13, 255);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void moveRightMotorBackward(int speedInPercent) {
        sendAnalogFirmataMessage(12, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(2, 0);
        sendAnalogFirmataMessage(13, 0);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void stopLeftMotor() {
        moveLeftMotorForward(0);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void stopRightMotor() {
        moveRightMotorForward(0);
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void stopAllMovements() {
        stopLeftMotor();
        stopRightMotor();
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void setLeftRGBLightColor(int red, int green, int blue) {
        int iCheckRBGValue = checkRBGValue(red);
        int iCheckRBGValue2 = checkRBGValue(green);
        int iCheckRBGValue3 = checkRBGValue(blue);
        sendFirmataMessage(new AnalogMessage(7, iCheckRBGValue));
        sendFirmataMessage(new AnalogMessage(8, iCheckRBGValue2));
        sendFirmataMessage(new AnalogMessage(9, iCheckRBGValue3));
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void setRightRGBLightColor(int red, int green, int blue) {
        int iCheckRBGValue = checkRBGValue(red);
        int iCheckRBGValue2 = checkRBGValue(green);
        int iCheckRBGValue3 = checkRBGValue(blue);
        sendFirmataMessage(new AnalogMessage(4, iCheckRBGValue));
        sendFirmataMessage(new AnalogMessage(5, iCheckRBGValue2));
        sendFirmataMessage(new AnalogMessage(6, iCheckRBGValue3));
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public String getName() {
        return "Phiro";
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public Class<? extends BluetoothDevice> getDeviceType() {
        return PHIRO;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void setConnection(BluetoothConnection connection) {
        this.btConnection = connection;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void disconnect() {
        if (this.firmata == null) {
            return;
        }
        try {
            resetPins();
            reportSensorData(false);
            this.firmata.clearListeners();
            this.firmata.getSerial().stop();
            this.isInitialized = false;
            this.firmata = null;
        } catch (SerialException unused) {
            Log.d(TAG, "Error stop phiro pro serial");
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public boolean isAlive() {
        Firmata firmata = this.firmata;
        if (firmata == null) {
            return false;
        }
        try {
            firmata.send(new ReportFirmwareVersionMessage());
            return true;
        } catch (SerialException unused) {
            return false;
        }
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public void reportFirmwareVersion() {
        Firmata firmata = this.firmata;
        if (firmata == null) {
            return;
        }
        try {
            firmata.send(new ReportFirmwareVersionMessage());
        } catch (SerialException unused) {
            Log.d(TAG, "Firmata Serial error, cannot send message.");
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/phiro/PhiroImpl$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.devices.arduino.phiro.PhiroImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors;

        static {
            int[] iArr = new int[Sensors.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors = iArr;
            try {
                iArr[Sensors.PHIRO_BOTTOM_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_BOTTOM_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_FRONT_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_FRONT_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_SIDE_LEFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.PHIRO_SIDE_RIGHT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // org.catrobat.catroid.devices.arduino.phiro.Phiro
    public int getSensorValue(Sensors sensor) {
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[sensor.ordinal()]) {
            case 1:
                return this.phiroListener.getBottomLeftSensor();
            case 2:
                return this.phiroListener.getBottomRightSensor();
            case 3:
                return this.phiroListener.getFrontLeftSensor();
            case 4:
                return this.phiroListener.getFrontRightSensor();
            case 5:
                return this.phiroListener.getSideLeftSensor();
            case 6:
                return this.phiroListener.getSideRightSensor();
            default:
                return 0;
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public UUID getBluetoothDeviceUUID() {
        return PHIRO_UUID;
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void initialise() {
        if (this.isInitialized) {
            return;
        }
        try {
            tryInitialize();
            this.isInitialized = true;
        } catch (IOException unused) {
            Log.d(TAG, "Error opening streams");
        } catch (SerialException unused2) {
            Log.d(TAG, "Error starting firmata serials");
        }
    }

    private void tryInitialize() throws SerialException, IOException {
        this.firmata = new Firmata(new StreamingSerialAdapter(this.btConnection.getInputStream(), this.btConnection.getOutputStream()));
        PhiroListener phiroListener = new PhiroListener();
        this.phiroListener = phiroListener;
        this.firmata.addListener(phiroListener);
        this.firmata.getSerial().start();
        for (int i = 2; i <= 13; i++) {
            sendFirmataMessage(new SetPinModeMessage(i, SetPinModeMessage.PIN_MODE.PWM.getMode()));
        }
        reportSensorData(true);
    }

    private void reportSensorData(boolean report) {
        if (this.isReportingSensorData == report) {
            return;
        }
        this.isReportingSensorData = report;
        for (int i = 0; i <= 5; i++) {
            sendFirmataMessage(new ReportAnalogPinMessage(i, report));
        }
    }

    private void resetPins() {
        stopAllMovements();
        setLeftRGBLightColor(0, 0, 0);
        setRightRGBLightColor(0, 0, 0);
        playTone(0, 0);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void start() {
        if (!this.isInitialized) {
            initialise();
        }
        reportSensorData(true);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void pause() {
        stopAllMovements();
        reportSensorData(false);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void destroy() {
        resetPins();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAnalogFirmataMessage(int pin, int value) {
        sendFirmataMessage(new AnalogMessage(pin, value));
    }

    private void sendFirmataMessage(Message message) {
        Firmata firmata = this.firmata;
        if (firmata == null) {
            return;
        }
        try {
            firmata.send(message);
        } catch (SerialException unused) {
            Log.d(TAG, "Firmata Serial error, cannot send message.");
        }
    }
}
