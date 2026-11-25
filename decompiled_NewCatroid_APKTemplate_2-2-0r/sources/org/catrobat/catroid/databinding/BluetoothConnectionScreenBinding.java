package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BluetoothConnectionScreenBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BluetoothConnectionScreenBinding implements ViewBinding {
    public final TextView bluetoothAddress;
    public final TextView bluetoothDevice;
    private final LinearLayout rootView;

    private BluetoothConnectionScreenBinding(LinearLayout rootView, TextView bluetoothAddress, TextView bluetoothDevice) {
        this.rootView = rootView;
        this.bluetoothAddress = bluetoothAddress;
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BluetoothConnectionScreenBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BluetoothConnectionScreenBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.bluetooth_connection_screen, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BluetoothConnectionScreenBinding bind(View rootView) {
        int i = R.id.bluetooth_address;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.bluetooth_address);
        if (textView != null) {
            i = R.id.bluetooth_device;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.bluetooth_device);
            if (textView2 != null) {
                return new BluetoothConnectionScreenBinding((LinearLayout) rootView, textView, textView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
