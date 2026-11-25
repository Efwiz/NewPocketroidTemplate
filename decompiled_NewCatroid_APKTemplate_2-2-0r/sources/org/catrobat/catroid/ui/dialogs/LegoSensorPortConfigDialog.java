package org.catrobat.catroid.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/LegoSensorPortConfigDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class LegoSensorPortConfigDialog extends AlertDialog {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/LegoSensorPortConfigDialog$OnClickListener.class
      classes14.dex
     */
    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialog, int selectedPort, Enum selectedSensor);
    }

    private LegoSensorPortConfigDialog(Context context) {
        super(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/LegoSensorPortConfigDialog$Builder.class
      classes14.dex
     */
    public static class Builder extends AlertDialog.Builder {
        private SensorInfo sensorInfo;
        private Map<Integer, SensorInfo> sensorInfoMap;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/LegoSensorPortConfigDialog$Builder$SensorInfo.class
          classes14.dex
         */
        private class SensorInfo {
            Enum sensor;
            int titleResId;

            SensorInfo(int titleResId, Enum sensor) {
                this.titleResId = titleResId;
                this.sensor = sensor;
            }
        }

        SensorInfo getSensorInfo(int selectedItem, int type) {
            SensorInfo sensorInfo = this.sensorInfoMap.get(Integer.valueOf(selectedItem));
            return sensorInfo != null ? sensorInfo : new SensorInfo(R.string.nxt_sensor_not_found, type == 0 ? NXTSensor.Sensor.NO_SENSOR : EV3Sensor.Sensor.NO_SENSOR);
        }

        public Builder(Context context, int legoType, int selectedItem) {
            Enum[] legoEV3SensorMapping;
            super(context);
            this.sensorInfoMap = ImmutableMap.builder().put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_touch), new SensorInfo(R.string.nxt_sensor_touch, NXTSensor.Sensor.TOUCH)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_sound), new SensorInfo(R.string.nxt_sensor_sound, NXTSensor.Sensor.SOUND)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light), new SensorInfo(R.string.nxt_sensor_light, NXTSensor.Sensor.LIGHT_INACTIVE)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light_active), new SensorInfo(R.string.nxt_sensor_light_active, NXTSensor.Sensor.LIGHT_ACTIVE)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_ultrasonic), new SensorInfo(R.string.nxt_sensor_ultrasonic, NXTSensor.Sensor.ULTRASONIC)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_touch), new SensorInfo(R.string.ev3_sensor_touch, EV3Sensor.Sensor.TOUCH)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_infrared), new SensorInfo(R.string.ev3_sensor_infrared, EV3Sensor.Sensor.INFRARED)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color), new SensorInfo(R.string.ev3_sensor_color, EV3Sensor.Sensor.COLOR)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_ambient), new SensorInfo(R.string.ev3_sensor_color_ambient, EV3Sensor.Sensor.COLOR_AMBIENT)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_reflected), new SensorInfo(R.string.ev3_sensor_color_reflected, EV3Sensor.Sensor.COLOR_REFLECT)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_hitechnic_color), new SensorInfo(R.string.ev3_sensor_hitechnic_color, EV3Sensor.Sensor.HT_NXT_COLOR)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_c), new SensorInfo(R.string.ev3_sensor_nxt_temperature_c, EV3Sensor.Sensor.NXT_TEMPERATURE_C)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_f), new SensorInfo(R.string.ev3_sensor_nxt_temperature_f, EV3Sensor.Sensor.NXT_TEMPERATURE_F)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_light), new SensorInfo(R.string.ev3_sensor_nxt_light, EV3Sensor.Sensor.NXT_LIGHT)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_light_active), new SensorInfo(R.string.ev3_sensor_nxt_light_active, EV3Sensor.Sensor.NXT_LIGHT_ACTIVE)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_sound), new SensorInfo(R.string.ev3_sensor_nxt_sound, EV3Sensor.Sensor.NXT_SOUND)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_ultrasonic), new SensorInfo(R.string.ev3_sensor_nxt_ultrasonic, EV3Sensor.Sensor.NXT_ULTRASONIC)).build();
            this.sensorInfo = getSensorInfo(selectedItem, legoType);
            if (legoType == 0) {
                legoEV3SensorMapping = SettingsFragment.getLegoNXTSensorMapping(context);
            } else {
                legoEV3SensorMapping = SettingsFragment.getLegoEV3SensorMapping(context);
            }
            String[] stringArray = context.getResources().getStringArray(R.array.port_chooser);
            String[] strArr = new String[stringArray.length];
            String[] stringArray2 = context.getResources().getStringArray(legoType == 0 ? R.array.nxt_sensor_chooser : R.array.ev3_sensor_chooser);
            for (int i = 0; i < stringArray.length; i++) {
                strArr[i] = stringArray[i] + ": " + stringArray2[legoEV3SensorMapping[i].ordinal()];
            }
            setTitle(context.getString(R.string.lego_sensor_port_config_dialog_title, context.getString(this.sensorInfo.titleResId)));
            setSingleChoiceItems(strArr, -1, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.Builder.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int selectedPort) {
                    ((AlertDialog) dialog).getButton(-1).setEnabled(true);
                }
            });
        }

        public Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
            setPositiveButton(text, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.Builder.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    listener.onPositiveButtonClick(dialog, ((AlertDialog) dialog).getListView().getCheckedItemPosition(), Builder.this.sensorInfo.sensor);
                }
            });
            return this;
        }

        @Override // androidx.appcompat.app.AlertDialog.Builder
        public AlertDialog create() {
            final AlertDialog alertDialogCreate = super.create();
            alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.Builder.3
                @Override // android.content.DialogInterface.OnShowListener
                public void onShow(DialogInterface dialog) {
                    alertDialogCreate.getButton(-1).setEnabled(false);
                }
            });
            return alertDialogCreate;
        }
    }
}
