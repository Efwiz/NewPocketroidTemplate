package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/LegoSensorConfigInfoDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoSensorConfigInfoDialog extends DialogFragment {
    private static final String BUNDLE_KEY_SENSOR_TYPE = "legoSensorType";
    public static final String DIALOG_FRAGMENT_TAG = "LegoSensorConfigInfoDialog";

    public static LegoSensorConfigInfoDialog newInstance(int legoSensorType) {
        LegoSensorConfigInfoDialog legoSensorConfigInfoDialog = new LegoSensorConfigInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_SENSOR_TYPE, legoSensorType);
        legoSensorConfigInfoDialog.setArguments(bundle);
        return legoSensorConfigInfoDialog;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Enum[] legoNXTSensorMapping;
        String[] stringArray;
        int i;
        int i2;
        if (getArguments() == null) {
            dismiss();
        }
        final int i3 = getArguments().getInt(BUNDLE_KEY_SENSOR_TYPE, 0);
        if (i3 == 0) {
            legoNXTSensorMapping = SettingsFragment.getLegoNXTSensorMapping(getActivity());
            stringArray = getResources().getStringArray(R.array.nxt_sensor_chooser);
            i = R.string.lego_nxt_sensor_config_info_title;
            i2 = R.string.lego_nxt_sensor_config_info_text;
        } else if (i3 == 1) {
            legoNXTSensorMapping = SettingsFragment.getLegoEV3SensorMapping(getActivity());
            stringArray = getResources().getStringArray(R.array.ev3_sensor_chooser);
            i = R.string.lego_ev3_sensor_config_info_title;
            i2 = R.string.lego_ev3_sensor_config_info_text;
        } else {
            throw new IllegalArgumentException("LegoSensorConfigInfoDialog: Constructor called with invalid sensor type");
        }
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_lego_sensor_config_info, null);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.lego_sensor_config_info_disable_show_dialog);
        ((TextView) viewInflate.findViewById(R.id.lego_sensor_config_info_text)).setText(i2);
        ((TextView) viewInflate.findViewById(R.id.lego_sensor_config_info_port_1_mapping)).setText(stringArray[legoNXTSensorMapping[0].ordinal()]);
        ((TextView) viewInflate.findViewById(R.id.lego_sensor_config_info_port_2_mapping)).setText(stringArray[legoNXTSensorMapping[1].ordinal()]);
        ((TextView) viewInflate.findViewById(R.id.lego_sensor_config_info_port_3_mapping)).setText(stringArray[legoNXTSensorMapping[2].ordinal()]);
        ((TextView) viewInflate.findViewById(R.id.lego_sensor_config_info_port_4_mapping)).setText(stringArray[legoNXTSensorMapping[3].ordinal()]);
        return new AlertDialog.Builder(getActivity()).setTitle(i).setView(viewInflate).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.LegoSensorConfigInfoDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id2) {
                String str;
                if (checkBox.isChecked()) {
                    if (i3 == 0) {
                        str = SettingsFragment.SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED;
                    } else {
                        str = SettingsFragment.SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED;
                    }
                    PreferenceManager.getDefaultSharedPreferences(LegoSensorConfigInfoDialog.this.getActivity()).edit().putBoolean(str, true).apply();
                }
            }
        }).create();
    }
}
