package org.catrobat.catroid.ui.dialogs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.SensorLoudness;
import org.catrobat.catroid.utils.NumberFormats;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/FormulaEditorComputeDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaEditorComputeDialog extends AlertDialog implements SensorEventListener {
    private TextView computeTextView;
    private Context context;
    private Formula formulaToCompute;
    private Scope scope;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public FormulaEditorComputeDialog(Context context, Scope scope) {
        super(context);
        this.formulaToCompute = null;
        this.context = context;
        this.scope = scope;
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            setContentView(R.layout.dialog_formulaeditor_compute_landscape);
            this.computeTextView = (TextView) findViewById(R.id.formula_editor_compute_dialog_textview_landscape_mode);
        } else {
            setContentView(R.layout.dialog_formulaeditor_compute);
            this.computeTextView = (TextView) findViewById(R.id.formula_editor_compute_dialog_textview);
        }
        TextView textView = this.computeTextView;
        if (textView != null) {
            textView.setTextColor(-1);
        }
        showFormulaResult(this.scope, new ShowTextUtils.AndroidStringProvider(this.context));
    }

    public void setFormula(Formula formula) {
        this.formulaToCompute = formula;
        Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
        formula.addRequiredResources(resourcesSet);
        if (resourcesSet.contains(24)) {
            SensorHandler.getInstance(getContext()).setSensorLoudness(new SensorLoudness());
        }
        if (resourcesSet.contains(2)) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.LEGO_NXT, getContext());
        }
        if (resourcesSet.contains(20)) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.LEGO_EV3, getContext());
        }
        if (resourcesSet.contains(6)) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.ARDUINO, getContext());
        }
        if (resourcesSet.contains(10)) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(BluetoothDevice.PHIRO, getContext());
        }
        if (formula.containsElement(FormulaElement.ElementType.SENSOR)) {
            SensorHandler.startSensorListener(this.context);
            SensorHandler.registerListener(this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onStop() {
        SensorHandler.unregisterListener(this);
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).pause();
        super.onStop();
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }

    private void showFormulaResult(Scope scope, Formula.StringProvider stringProvider) {
        if (this.computeTextView == null) {
            return;
        }
        setDialogTextView(NumberFormats.trimTrailingCharacters(this.formulaToCompute.getUserFriendlyString(stringProvider, scope)));
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        showFormulaResult(this.scope, new ShowTextUtils.AndroidStringProvider(this.context));
    }

    private void setDialogTextView(final String newString) {
        this.computeTextView.post(new Runnable() { // from class: org.catrobat.catroid.ui.dialogs.FormulaEditorComputeDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5668x35b6283f(newString);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setDialogTextView$0$org-catrobat-catroid-ui-dialogs-FormulaEditorComputeDialog, reason: not valid java name */
    /* synthetic */ void m5668x35b6283f(String str) {
        this.computeTextView.setText(str);
        ViewGroup.LayoutParams layoutParams = this.computeTextView.getLayoutParams();
        int lineCount = this.computeTextView.getLineCount() * this.computeTextView.getLineHeight();
        layoutParams.width = -1;
        layoutParams.height = lineCount + ((int) (((double) lineCount) * 0.5d));
        this.computeTextView.setLayoutParams(layoutParams);
    }
}
