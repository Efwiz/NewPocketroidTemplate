package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.common.NewCatroidHttpManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/HttpAttachFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: HttpAttachFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/HttpAttachFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "mimeType", "getMimeType", "setMimeType", "parameterName", "getParameterName", "setParameterName", "requestId", "getRequestId", "setRequestId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HttpAttachFileAction extends TemporalAction {
    private Formula fileName;
    private Formula mimeType;
    private Formula parameterName;
    private Formula requestId;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRequestId() {
        return this.requestId;
    }

    public final void setRequestId(Formula formula) {
        this.requestId = formula;
    }

    public final Formula getFileName() {
        return this.fileName;
    }

    public final void setFileName(Formula formula) {
        this.fileName = formula;
    }

    public final Formula getParameterName() {
        return this.parameterName;
    }

    public final void setParameterName(Formula formula) {
        this.parameterName = formula;
    }

    public final Formula getMimeType() {
        return this.mimeType;
    }

    public final void setMimeType(Formula formula) {
        this.mimeType = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Formula formula = this.requestId;
        File file = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.fileName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        Formula formula3 = this.parameterName;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "file";
        }
        Formula formula4 = this.mimeType;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "application/octet-stream";
        }
        if (strInterpretString.length() == 0 || str.length() == 0) {
            return;
        }
        Scope scope = this.scope;
        if (scope != null && (project = scope.getProject()) != null) {
            file = project.getFile(str);
        }
        if (file != null && file.exists()) {
            NewCatroidHttpManager.INSTANCE.attachFile(strInterpretString, file, strInterpretString3, strInterpretString4);
        }
    }
}
