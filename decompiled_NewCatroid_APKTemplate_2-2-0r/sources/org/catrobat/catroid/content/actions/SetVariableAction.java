package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.multiplayer.MultiplayerInterface;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.common.Conversions;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetVariableAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetVariableAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0014\u001a\u00020\u00122\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u0015\u001a\u00020\u00122\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetVariableAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "changeVariable", "Lorg/catrobat/catroid/formulaeditor/Formula;", "multiplayerDevice", "Lorg/catrobat/catroid/devices/multiplayer/MultiplayerInterface;", "getMultiplayerDevice", "()Lorg/catrobat/catroid/devices/multiplayer/MultiplayerInterface;", "scope", "Lorg/catrobat/catroid/content/Scope;", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "checkValueForDoubleConversion", "", "value", "", "handleMultiplayerVariable", "", "setChangeVariable", "setScope", "setUserVariable", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetVariableAction extends TemporalAction {
    private Formula changeVariable;
    private Scope scope;
    private UserVariable userVariable;

    /* JADX WARN: Code duplicated, block: B:32:0x0056  */
    /* JADX WARN: Code duplicated, block: B:34:0x005a  */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Object objValueOf;
        UserVariable userVariable;
        FormulaElement root;
        FormulaElement root2;
        if (this.userVariable == null) {
            return;
        }
        Formula formula = this.changeVariable;
        if (formula == null || (objValueOf = formula.interpretObject(this.scope)) == null) {
            objValueOf = Double.valueOf(0.0d);
        }
        Formula formula2 = this.changeVariable;
        if (formula2 != null && formula2 != null && (root2 = formula2.getRoot()) != null && root2.isBoolean(this.scope)) {
            objValueOf = Boolean.valueOf(!(((Double) objValueOf).doubleValue() == 0.0d));
        }
        Formula formula3 = this.changeVariable;
        if (formula3 == null) {
            userVariable = this.userVariable;
            if ((userVariable != null ? userVariable.hashCode() : 0) != 1613638780 && checkValueForDoubleConversion(objValueOf)) {
                try {
                    objValueOf = Conversions.convertArgumentToDouble(objValueOf);
                } catch (NumberFormatException e) {
                    Log.d(getClass().getSimpleName(), "Couldn't parse String", e);
                }
            }
        } else {
            if (((formula3 == null || (root = formula3.getRoot()) == null) ? null : root.getElementType()) != FormulaElement.ElementType.STRING) {
                userVariable = this.userVariable;
                if ((userVariable != null ? userVariable.hashCode() : 0) != 1613638780) {
                    objValueOf = Conversions.convertArgumentToDouble(objValueOf);
                }
            }
        }
        UserVariable userVariable2 = this.userVariable;
        if (userVariable2 != null) {
            userVariable2.setValue(objValueOf);
        }
        handleMultiplayerVariable();
    }

    public final MultiplayerInterface getMultiplayerDevice() {
        return (MultiplayerInterface) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.MULTIPLAYER);
    }

    private final void handleMultiplayerVariable() {
        MultiplayerInterface multiplayerDevice;
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        UserVariable userVariable = this.userVariable;
        if (currentProject.getMultiplayerVariable(userVariable != null ? userVariable.getName() : null) == null || (multiplayerDevice = getMultiplayerDevice()) == null) {
            return;
        }
        multiplayerDevice.sendChangedMultiplayerVariables(this.userVariable);
    }

    private final boolean checkValueForDoubleConversion(Object value) {
        return (value instanceof String) && Conversions.convertArgumentToDouble(value) != null;
    }

    public final void setUserVariable(UserVariable userVariable) {
        if (userVariable == null) {
            return;
        }
        this.userVariable = userVariable;
    }

    public final void setChangeVariable(Formula changeVariable) {
        this.changeVariable = changeVariable;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }
}
