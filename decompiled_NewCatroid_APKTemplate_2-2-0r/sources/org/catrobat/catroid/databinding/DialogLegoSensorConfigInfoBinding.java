package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogLegoSensorConfigInfoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogLegoSensorConfigInfoBinding implements ViewBinding {
    public final CheckBox legoSensorConfigInfoDisableShowDialog;
    public final TextView legoSensorConfigInfoPort1Mapping;
    public final TextView legoSensorConfigInfoPort2Mapping;
    public final TextView legoSensorConfigInfoPort3Mapping;
    public final TextView legoSensorConfigInfoPort4Mapping;
    public final TextView legoSensorConfigInfoText;
    private final ScrollView rootView;

    private DialogLegoSensorConfigInfoBinding(ScrollView rootView, CheckBox legoSensorConfigInfoDisableShowDialog, TextView legoSensorConfigInfoPort1Mapping, TextView legoSensorConfigInfoPort2Mapping, TextView legoSensorConfigInfoPort3Mapping, TextView legoSensorConfigInfoPort4Mapping, TextView legoSensorConfigInfoText) {
        this.rootView = rootView;
        this.legoSensorConfigInfoDisableShowDialog = legoSensorConfigInfoDisableShowDialog;
        this.legoSensorConfigInfoPort1Mapping = legoSensorConfigInfoPort1Mapping;
        this.legoSensorConfigInfoPort2Mapping = legoSensorConfigInfoPort2Mapping;
        this.legoSensorConfigInfoPort3Mapping = legoSensorConfigInfoPort3Mapping;
        this.legoSensorConfigInfoPort4Mapping = legoSensorConfigInfoPort4Mapping;
        this.legoSensorConfigInfoText = legoSensorConfigInfoText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogLegoSensorConfigInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogLegoSensorConfigInfoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_lego_sensor_config_info, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogLegoSensorConfigInfoBinding bind(View rootView) {
        int i = R.id.lego_sensor_config_info_disable_show_dialog;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_disable_show_dialog);
        if (checkBox != null) {
            i = R.id.lego_sensor_config_info_port_1_mapping;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_port_1_mapping);
            if (textView != null) {
                i = R.id.lego_sensor_config_info_port_2_mapping;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_port_2_mapping);
                if (textView2 != null) {
                    i = R.id.lego_sensor_config_info_port_3_mapping;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_port_3_mapping);
                    if (textView3 != null) {
                        i = R.id.lego_sensor_config_info_port_4_mapping;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_port_4_mapping);
                        if (textView4 != null) {
                            i = R.id.lego_sensor_config_info_text;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_sensor_config_info_text);
                            if (textView5 != null) {
                                return new DialogLegoSensorConfigInfoBinding((ScrollView) rootView, checkBox, textView, textView2, textView3, textView4, textView5);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
