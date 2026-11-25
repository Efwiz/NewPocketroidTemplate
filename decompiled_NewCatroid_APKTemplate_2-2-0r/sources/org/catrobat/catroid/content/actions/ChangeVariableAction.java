package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.multiplayer.MultiplayerInterface;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ChangeVariableAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ChangeVariableAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/ChangeVariableAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "changeVariable", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getChangeVariable", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setChangeVariable", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "", "delta", "", "getMultiplayerDevice", "Lorg/catrobat/catroid/devices/multiplayer/MultiplayerInterface;", "updateUserVariable", "", "originalValue", "", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChangeVariableAction extends Action {
    private Formula changeVariable;
    private Scope scope;
    private UserVariable userVariable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getChangeVariable() {
        return this.changeVariable;
    }

    public final void setChangeVariable(Formula formula) {
        this.changeVariable = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        UserVariable userVariable = this.userVariable;
        Double doubleOrNull = null;
        Object value = userVariable != null ? userVariable.getValue() : null;
        Double d = value instanceof Double ? (Double) value : null;
        if (d != null) {
            double dDoubleValue = d.doubleValue();
            Formula formula = this.changeVariable;
            Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
            if (objInterpretObject == null) {
                objInterpretObject = Double.valueOf(0.0d);
            }
            Double d2 = objInterpretObject instanceof Double ? (Double) objInterpretObject : null;
            if (d2 == null) {
                String str = objInterpretObject instanceof String ? (String) objInterpretObject : null;
                if (str != null) {
                    doubleOrNull = StringsKt.toDoubleOrNull(str);
                }
            } else {
                doubleOrNull = d2;
            }
            if (doubleOrNull != null) {
                updateUserVariable(dDoubleValue, doubleOrNull.doubleValue());
            }
        }
        return true;
    }

    private final void updateUserVariable(double originalValue, double value) {
        MultiplayerInterface multiplayerDevice;
        Double dValueOf = Double.valueOf(originalValue);
        if (Double.isNaN(dValueOf.doubleValue())) {
            dValueOf = null;
        }
        double dDoubleValue = dValueOf != null ? dValueOf.doubleValue() : 0.0d;
        Double dValueOf2 = Double.valueOf(value);
        if (Double.isNaN(dValueOf2.doubleValue())) {
            dValueOf2 = null;
        }
        double dDoubleValue2 = dValueOf2 != null ? dValueOf2.doubleValue() : 0.0d;
        UserVariable userVariable = this.userVariable;
        if (userVariable != null) {
            userVariable.setValue(Double.valueOf(dDoubleValue + dDoubleValue2));
        }
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        UserVariable userVariable2 = this.userVariable;
        if (currentProject.getMultiplayerVariable(userVariable2 != null ? userVariable2.getName() : null) == null || (multiplayerDevice = getMultiplayerDevice()) == null) {
            return;
        }
        multiplayerDevice.sendChangedMultiplayerVariables(this.userVariable);
    }

    public final MultiplayerInterface getMultiplayerDevice() {
        return (MultiplayerInterface) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.MULTIPLAYER);
    }
}
