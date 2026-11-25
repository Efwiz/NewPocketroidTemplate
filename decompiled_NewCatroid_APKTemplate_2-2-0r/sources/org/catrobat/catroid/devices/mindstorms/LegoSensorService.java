package org.catrobat.catroid.devices.mindstorms;

import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Command;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandType;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensorMode;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensorType;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PausableScheduledThreadPoolExecutor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensorService.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoSensorService implements CatroidService, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int SENSOR_UPDATER_THREAD_COUNT = 2;
    private static final String TAG = "LegoSensorService";
    private SharedPreferences preferences;
    private List<OnSensorChangedListener> sensorChangedListeners = new ArrayList();
    private LegoSensorFactory sensorFactory;
    private SensorRegistry sensorRegistry;
    private PausableScheduledThreadPoolExecutor sensorScheduler;
    private int sensorType;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensorService$OnSensorChangedListener.class
      classes14.dex
     */
    public interface OnSensorChangedListener {
        void onSensorChanged();
    }

    public LegoSensorService(int sensorType, MindstormsConnection connection, SharedPreferences preferences) {
        if (sensorType != 0 && sensorType != 1) {
            throw new IllegalArgumentException("Trying to construct LegoSensorService with invalid sensorType!");
        }
        this.sensorType = sensorType;
        this.preferences = preferences;
        this.sensorRegistry = new SensorRegistry();
        this.sensorFactory = new LegoSensorFactory(connection);
        this.sensorScheduler = new PausableScheduledThreadPoolExecutor(2);
        this.preferences.registerOnSharedPreferenceChangeListener(this);
        this.sensorScheduler.pause();
    }

    public LegoSensor createSensor(int port) {
        if (port < 0 || port > 3) {
            throw new IllegalArgumentException("Trying to create sensor with invalid port number!");
        }
        Enum<?> sensorByPort = getSensorByPort(port);
        if (sensorByPort == EV3Sensor.Sensor.NO_SENSOR || sensorByPort == NXTSensor.Sensor.NO_SENSOR) {
            this.sensorRegistry.remove(port);
            return null;
        }
        LegoSensor legoSensorCreate = this.sensorFactory.create(sensorByPort, port);
        this.sensorRegistry.add(legoSensorCreate);
        return legoSensorCreate;
    }

    private Enum getSensorByPort(int port) {
        int i = this.sensorType;
        if (i == 0) {
            return NXTSensor.Sensor.getSensorFromSensorCode(this.preferences.getString(SettingsFragment.NXT_SENSORS[port], null));
        }
        if (i == 1) {
            return EV3Sensor.Sensor.getSensorFromSensorCode(this.preferences.getString(SettingsFragment.EV3_SENSORS[port], null));
        }
        throw new IllegalArgumentException();
    }

    public void pauseSensorUpdate() {
        this.sensorScheduler.pause();
    }

    public void resumeSensorUpdate() {
        this.sensorScheduler.resume();
    }

    public void destroy() {
        this.sensorScheduler.shutdown();
        this.preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    public void deactivateAllSensors(MindstormsConnection connection) {
        int i = this.sensorType;
        if (i == 0) {
            deactivateAllNxtSensors(connection);
        } else if (i == 1) {
            deactivateAllEv3Sensors(connection);
        }
    }

    private void deactivateAllNxtSensors(MindstormsConnection connection) {
        for (int i = 0; i < 4; i++) {
            Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_INPUT_MODE, false);
            command.append((byte) i);
            command.append(NXTSensorType.NO_SENSOR.getByte());
            command.append(NXTSensorMode.RAW.getByte());
            try {
                connection.send(command);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void deactivateAllEv3Sensors(MindstormsConnection connection) {
        EV3Command eV3Command = new EV3Command(connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_INPUT_DEVICE);
        connection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandByteCode.INPUT_DEVICE_STOP_ALL.getByte());
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, -1);
        try {
            connection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void registerOnSensorChangedListener(OnSensorChangedListener listener) {
        this.sensorChangedListeners.add(listener);
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String preference) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(SettingsFragment.EV3_SENSORS));
        arrayList.addAll(Arrays.asList(SettingsFragment.NXT_SENSORS));
        if (arrayList.contains(preference)) {
            for (OnSensorChangedListener onSensorChangedListener : this.sensorChangedListeners) {
                if (onSensorChangedListener != null) {
                    onSensorChangedListener.onSensorChanged();
                }
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensorService$SensorRegistry.class
      classes14.dex
     */
    private class SensorRegistry {
        private static final int INITIAL_DELAY = 500;
        private SparseArray<ScheduledFuture> registeredSensors;

        private SensorRegistry() {
            this.registeredSensors = new SparseArray<>();
        }

        public synchronized void add(final LegoSensor sensor) {
            remove(sensor.getConnectedPort());
            this.registeredSensors.put(sensor.getConnectedPort(), LegoSensorService.this.sensorScheduler.scheduleWithFixedDelay(new Runnable() { // from class: org.catrobat.catroid.devices.mindstorms.LegoSensorService.SensorRegistry.1
                @Override // java.lang.Runnable
                public void run() {
                    sensor.updateLastSensorValue();
                }
            }, 500L, sensor.getUpdateInterval(), TimeUnit.MILLISECONDS));
        }

        public synchronized void remove(int port) {
            ScheduledFuture scheduledFuture = this.registeredSensors.get(port);
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.registeredSensors.remove(port);
        }
    }
}
