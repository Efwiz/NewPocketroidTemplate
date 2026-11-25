package org.catrobat.catroid.devices.arduino;

import android.util.Log;
import java.io.IOException;
import java.util.UUID;
import name.antonsmirnov.firmata.Firmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.serial.StreamingSerialAdapter;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/ArduinoImpl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoImpl implements Arduino {
    public static final int NUMBER_OF_ANALOG_PINS = 6;
    public static final int NUMBER_OF_DIGITAL_PINS = 14;
    public static final int NUMBER_OF_DIGITAL_PORTS = 2;
    public static final int PINS_IN_A_PORT = 8;
    private ArduinoListener arduinoListener;
    private BluetoothConnection btConnection;
    private Firmata firmata;
    public static final int[] PWM_PINS = {3, 5, 6, 9, 10, 11};
    private static final UUID ARDUINO_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "ArduinoImpl";
    private boolean isReportingSensorData = false;
    private boolean isInitialized = false;

    public static boolean isValidAnalogPin(int analogPinNumber) {
        return analogPinNumber >= 0 && analogPinNumber < 6;
    }

    public static boolean isValidDigitalPin(int pin) {
        return pin >= 0 && pin < 14;
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void pause() {
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public String getName() {
        return "ARDUINO";
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void setConnection(BluetoothConnection connection) {
        this.btConnection = connection;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.ARDUINO;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void disconnect() {
        if (this.firmata == null) {
            return;
        }
        try {
            reportSensorData(false);
            this.firmata.clearListeners();
            this.firmata.getSerial().stop();
            this.isInitialized = false;
            this.firmata = null;
        } catch (SerialException unused) {
            Log.d(TAG, "Error stop Arduino serial");
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

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public UUID getBluetoothDeviceUUID() {
        return ARDUINO_UUID;
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
        ArduinoListener arduinoListener = new ArduinoListener();
        this.arduinoListener = arduinoListener;
        this.firmata.addListener(arduinoListener);
        this.firmata.getSerial().start();
        for (int i : PWM_PINS) {
            sendFirmataMessage(new SetPinModeMessage(i, SetPinModeMessage.PIN_MODE.PWM.getMode()));
        }
        reportSensorData(true);
    }

    private void reportSensorData(boolean report) {
        if (this.isReportingSensorData == report) {
            return;
        }
        this.isReportingSensorData = report;
        for (int i = 0; i < 6; i++) {
            sendFirmataMessage(new ReportAnalogPinMessage(i, report));
        }
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void start() {
        if (!this.isInitialized) {
            initialise();
        }
        reportSensorData(true);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void destroy() {
        reportSensorData(false);
    }

    @Override // org.catrobat.catroid.devices.arduino.Arduino
    public void setAnalogArduinoPin(int pin, int value) {
        sendAnalogFirmataMessage(pin, value);
    }

    @Override // org.catrobat.catroid.devices.arduino.Arduino
    public void setDigitalArduinoPin(int digitalPinNumber, int pinValue) {
        int portFromPin = getPortFromPin(digitalPinNumber);
        this.arduinoListener.setDigitalPinValue(digitalPinNumber, pinValue);
        sendDigitalFirmataMessage(portFromPin, digitalPinNumber, this.arduinoListener.getPortValue(portFromPin));
    }

    @Override // org.catrobat.catroid.devices.arduino.Arduino
    public double getDigitalArduinoPin(int digitalPinNumber) {
        sendFirmataMessage(new SetPinModeMessage(digitalPinNumber, SetPinModeMessage.PIN_MODE.INPUT.getMode()));
        int portFromPin = getPortFromPin(digitalPinNumber);
        sendFirmataMessage(new ReportDigitalPortMessage(portFromPin, true));
        try {
            Thread.sleep(100L);
        } catch (InterruptedException unused) {
            Log.d(TAG, "Error Arduino sensor thread sleep()");
        }
        double digitalPinValue = this.arduinoListener.getDigitalPinValue(digitalPinNumber);
        sendFirmataMessage(new ReportDigitalPortMessage(portFromPin, false));
        return digitalPinValue;
    }

    @Override // org.catrobat.catroid.devices.arduino.Arduino
    public double getAnalogArduinoPin(int analogPinNumber) {
        return this.arduinoListener.getAnalogPinValue(analogPinNumber);
    }

    public static int getPortFromPin(int pin) {
        return pin / 8;
    }

    public static int getIndexOfPinOnPort(int pin) {
        return pin % 8;
    }

    private void sendAnalogFirmataMessage(int pin, int value) {
        sendFirmataMessage(new SetPinModeMessage(pin, SetPinModeMessage.PIN_MODE.PWM.getMode()));
        sendFirmataMessage(new AnalogMessage(pin, value));
    }

    private void sendDigitalFirmataMessage(int port, int pin, int value) {
        sendFirmataMessage(new SetPinModeMessage(pin, SetPinModeMessage.PIN_MODE.OUTPUT.getMode()));
        sendFirmataMessage(new DigitalMessage(port, value));
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
