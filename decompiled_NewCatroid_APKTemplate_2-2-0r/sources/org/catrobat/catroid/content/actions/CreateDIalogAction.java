package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import org.catrobat.catroid.content.NewDialogManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateDIalogAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateDIalogAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateDIalogAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", CrashHianalyticsData.MESSAGE, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getMessage", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setMessage", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "name", "getName", "setName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "title", "getTitle", "setTitle", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateDIalogAction extends TemporalAction {
    private Formula message;
    private Formula name;
    private Scope scope;
    private Formula title;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    public final Formula getTitle() {
        return this.title;
    }

    public final void setTitle(Formula formula) {
        this.title = formula;
    }

    public final Formula getMessage() {
        return this.message;
    }

    public final void setMessage(Formula formula) {
        this.message = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.name;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "myDialog";
        }
        Formula formula2 = this.title;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Formula formula3 = this.message;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        NewDialogManager.INSTANCE.createEmptyDialog(strInterpretString, strInterpretString2, strInterpretString3 != null ? strInterpretString3 : "");
    }
}
