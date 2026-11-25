package org.catrobat.catroid.content;

import java.io.Serializable;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LegoNXTSetting.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNXTSetting implements Setting {
    private static final long serialVersionUID = 1;
    private NXTPort[] portSensorMapping;

    public LegoNXTSetting(NXTSensor.Sensor[] sensorMapping) {
        this.portSensorMapping = null;
        this.portSensorMapping = new NXTPort[4];
        int i = 0;
        while (true) {
            NXTPort[] nXTPortArr = this.portSensorMapping;
            if (i >= nXTPortArr.length) {
                return;
            }
            nXTPortArr[i] = new NXTPort(i, sensorMapping[i]);
            i++;
        }
    }

    public void updateMapping(NXTSensor.Sensor[] sensorMapping) {
        int i = 0;
        while (true) {
            NXTPort[] nXTPortArr = this.portSensorMapping;
            if (i >= nXTPortArr.length) {
                return;
            }
            nXTPortArr[i].setNumber(i);
            this.portSensorMapping[i].setSensor(sensorMapping[i]);
            i++;
        }
    }

    public NXTSensor.Sensor[] getSensorMapping() {
        NXTSensor.Sensor[] sensorArr = new NXTSensor.Sensor[4];
        int i = 0;
        while (true) {
            NXTPort[] nXTPortArr = this.portSensorMapping;
            if (i >= nXTPortArr.length) {
                return sensorArr;
            }
            sensorArr[i] = nXTPortArr[i].getSensor();
            i++;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LegoNXTSetting$NXTPort.class
      classes14.dex
     */
    public static class NXTPort implements Serializable {
        private int number;
        private NXTSensor.Sensor sensor;

        public NXTPort(int number, NXTSensor.Sensor sensor) {
            this.number = number;
            this.sensor = sensor;
        }

        public NXTSensor.Sensor getSensor() {
            return this.sensor;
        }

        public void setSensor(NXTSensor.Sensor sensor) {
            this.sensor = sensor;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
