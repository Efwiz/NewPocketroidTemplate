package org.catrobat.catroid.content;

import java.io.Serializable;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LegoEV3Setting.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEV3Setting implements Setting {
    private static final long serialVersionUID = 1;
    private EV3Port[] portSensorMapping;

    public LegoEV3Setting(EV3Sensor.Sensor[] sensorMapping) {
        this.portSensorMapping = null;
        this.portSensorMapping = new EV3Port[4];
        int i = 0;
        while (true) {
            EV3Port[] eV3PortArr = this.portSensorMapping;
            if (i >= eV3PortArr.length) {
                return;
            }
            eV3PortArr[i] = new EV3Port(i, sensorMapping[i]);
            i++;
        }
    }

    public void updateMapping(EV3Sensor.Sensor[] sensorMapping) {
        int i = 0;
        while (true) {
            EV3Port[] eV3PortArr = this.portSensorMapping;
            if (i >= eV3PortArr.length) {
                return;
            }
            eV3PortArr[i].setNumber(i);
            this.portSensorMapping[i].setSensor(sensorMapping[i]);
            i++;
        }
    }

    public EV3Sensor.Sensor[] getSensorMapping() {
        EV3Sensor.Sensor[] sensorArr = new EV3Sensor.Sensor[4];
        int i = 0;
        while (true) {
            EV3Port[] eV3PortArr = this.portSensorMapping;
            if (i >= eV3PortArr.length) {
                return sensorArr;
            }
            sensorArr[i] = eV3PortArr[i].getSensor();
            i++;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LegoEV3Setting$EV3Port.class
      classes14.dex
     */
    public static class EV3Port implements Serializable {
        private int number;
        private EV3Sensor.Sensor sensor;

        public EV3Port(int number, EV3Sensor.Sensor sensor) {
            this.number = number;
            this.sensor = sensor;
        }

        public EV3Sensor.Sensor getSensor() {
            return this.sensor;
        }

        public void setSensor(EV3Sensor.Sensor sensor) {
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
