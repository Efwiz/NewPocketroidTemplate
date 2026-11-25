package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AddFileToApkAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddFileToApkAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/AddFileToApkAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "apkPathFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getApkPathFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setApkPathFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "destPathFormula", "getDestPathFormula", "setDestPathFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sourcePathFormula", "getSourcePathFormula", "setSourcePathFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddFileToApkAction extends TemporalAction {
    private Formula apkPathFormula;
    private Formula destPathFormula;
    private Scope scope;
    private Formula sourcePathFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getApkPathFormula() {
        return this.apkPathFormula;
    }

    public final void setApkPathFormula(Formula formula) {
        this.apkPathFormula = formula;
    }

    public final Formula getSourcePathFormula() {
        return this.sourcePathFormula;
    }

    public final void setSourcePathFormula(Formula formula) {
        this.sourcePathFormula = formula;
    }

    public final Formula getDestPathFormula() {
        return this.destPathFormula;
    }

    public final void setDestPathFormula(Formula formula) {
        this.destPathFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        boolean zAddFileToApk;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        Formula formula = this.apkPathFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.sourcePathFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Formula formula3 = this.destPathFormula;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        String str = strInterpretString3 != null ? strInterpretString3 : "";
        if (strInterpretString.length() == 0 || strInterpretString2.length() == 0) {
            return;
        }
        File file = project.getFile(strInterpretString);
        File file2 = project.getFile(strInterpretString2);
        if (file == null || !file.exists() || file2 == null || !file2.exists()) {
            Log.e("AddFileAction", "APK or Source file not found");
            return;
        }
        if (file2.isDirectory()) {
            ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            zAddFileToApk = apkToolboxManager.addFolderToApk(absolutePath, file2, str);
        } else {
            ApkToolboxManager apkToolboxManager2 = ApkToolboxManager.INSTANCE;
            String absolutePath2 = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
            zAddFileToApk = apkToolboxManager2.addFileToApk(absolutePath2, file2, str);
        }
        if (zAddFileToApk) {
            return;
        }
        Log.e("AddFileAction", "Failed to add " + strInterpretString2 + " to APK");
    }
}
