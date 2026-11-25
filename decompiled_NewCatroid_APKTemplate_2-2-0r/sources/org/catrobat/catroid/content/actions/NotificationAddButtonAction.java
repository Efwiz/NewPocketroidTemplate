package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NotificationAddButtonAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NotificationAddButtonAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020+H\u0016J\u0010\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020/H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\b¨\u00060"}, d2 = {"Lorg/catrobat/catroid/content/actions/NotificationAddButtonAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "actionId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getActionId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setActionId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "autoClose", "", "getAutoClose", "()Z", "setAutoClose", "(Z)V", "behavior", "", "getBehavior", "()I", "setBehavior", "(I)V", "hasInput", "getHasInput", "setHasInput", "hint", "getHint", "setHint", "iconFile", "getIconFile", "setIconFile", "notifId", "getNotifId", "setNotifId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text", "getText", "setText", "reset", "", "restart", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotificationAddButtonAction extends TemporalAction {
    private Formula actionId;
    private boolean autoClose;
    private int behavior;
    private boolean hasInput;
    private Formula hint;
    private Formula iconFile;
    private Formula notifId;
    private Scope scope;
    private Formula text;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNotifId() {
        return this.notifId;
    }

    public final void setNotifId(Formula formula) {
        this.notifId = formula;
    }

    public final Formula getActionId() {
        return this.actionId;
    }

    public final void setActionId(Formula formula) {
        this.actionId = formula;
    }

    public final Formula getText() {
        return this.text;
    }

    public final void setText(Formula formula) {
        this.text = formula;
    }

    public final Formula getIconFile() {
        return this.iconFile;
    }

    public final void setIconFile(Formula formula) {
        this.iconFile = formula;
    }

    public final Formula getHint() {
        return this.hint;
    }

    public final void setHint(Formula formula) {
        this.hint = formula;
    }

    public final int getBehavior() {
        return this.behavior;
    }

    public final void setBehavior(int i) {
        this.behavior = i;
    }

    public final boolean getHasInput() {
        return this.hasInput;
    }

    public final void setHasInput(boolean z) {
        this.hasInput = z;
    }

    public final boolean getAutoClose() {
        return this.autoClose;
    }

    public final void setAutoClose(boolean z) {
        this.autoClose = z;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x0090  */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        Project project;
        File file;
        Formula formula = this.notifId;
        String absolutePath = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        String strCleanStringId = NewCatroidNotificationManager.cleanStringId(strInterpretString);
        Formula formula2 = this.actionId;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        String strCleanStringId2 = NewCatroidNotificationManager.cleanStringId(strInterpretString2);
        if (strCleanStringId.length() == 0 || strCleanStringId2.length() == 0) {
            return;
        }
        Formula formula3 = this.text;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        String str2 = strInterpretString3 == null ? "" : strInterpretString3;
        Formula formula4 = this.iconFile;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "";
        }
        Formula formula5 = this.hint;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        String str3 = strInterpretString5 == null ? "" : strInterpretString5;
        if (strInterpretString4.length() <= 0) {
            str = "";
        } else {
            Scope scope = this.scope;
            if (scope != null && (project = scope.getProject()) != null && (file = project.getFile(strInterpretString4)) != null) {
                absolutePath = file.getAbsolutePath();
            }
            if (absolutePath == null) {
                str = "";
            } else {
                str = absolutePath;
            }
        }
        NewCatroidNotificationManager.INSTANCE.addActionButton(strCleanStringId, strCleanStringId2, str2, str, this.behavior, this.hasInput, str3, this.autoClose);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.notifId = null;
        this.actionId = null;
        this.text = null;
        this.iconFile = null;
        this.hint = null;
        this.behavior = 0;
        this.hasInput = false;
        this.autoClose = false;
    }
}
