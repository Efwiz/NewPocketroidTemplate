package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DeviceListBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DeviceListBinding implements ViewBinding {
    public final LinearLayout bluetoothPairedSection;
    public final FloatingActionButton bluetoothScan;
    public final ProgressBar deviceListProgressBar;
    public final ListView newDevices;
    public final ListView pairedDevices;
    private final RelativeLayout rootView;

    private DeviceListBinding(RelativeLayout rootView, LinearLayout bluetoothPairedSection, FloatingActionButton bluetoothScan, ProgressBar deviceListProgressBar, ListView newDevices, ListView pairedDevices) {
        this.rootView = rootView;
        this.bluetoothPairedSection = bluetoothPairedSection;
        this.bluetoothScan = bluetoothScan;
        this.deviceListProgressBar = deviceListProgressBar;
        this.newDevices = newDevices;
        this.pairedDevices = pairedDevices;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DeviceListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DeviceListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.device_list, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DeviceListBinding bind(View rootView) {
        int i = R.id.bluetooth_paired_section;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.bluetooth_paired_section);
        if (linearLayout != null) {
            i = R.id.bluetooth_scan;
            FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.bluetooth_scan);
            if (floatingActionButton != null) {
                i = R.id.device_list_progress_bar;
                ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.device_list_progress_bar);
                if (progressBar != null) {
                    i = R.id.new_devices;
                    ListView listView = (ListView) ViewBindings.findChildViewById(rootView, R.id.new_devices);
                    if (listView != null) {
                        i = R.id.paired_devices;
                        ListView listView2 = (ListView) ViewBindings.findChildViewById(rootView, R.id.paired_devices);
                        if (listView2 != null) {
                            return new DeviceListBinding((RelativeLayout) rootView, linearLayout, floatingActionButton, progressBar, listView, listView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
