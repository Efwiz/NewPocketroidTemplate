package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SignApkAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SignApkAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/SignApkAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "aliasFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAliasFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAliasFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "inputApkFormula", "getInputApkFormula", "setInputApkFormula", "keystoreFormula", "getKeystoreFormula", "setKeystoreFormula", "outputApkFormula", "getOutputApkFormula", "setOutputApkFormula", "passwordFormula", "getPasswordFormula", "setPasswordFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignApkAction extends TemporalAction {
    private Formula aliasFormula;
    private Formula inputApkFormula;
    private Formula keystoreFormula;
    private Formula outputApkFormula;
    private Formula passwordFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getInputApkFormula() {
        return this.inputApkFormula;
    }

    public final void setInputApkFormula(Formula formula) {
        this.inputApkFormula = formula;
    }

    public final Formula getOutputApkFormula() {
        return this.outputApkFormula;
    }

    public final void setOutputApkFormula(Formula formula) {
        this.outputApkFormula = formula;
    }

    public final Formula getKeystoreFormula() {
        return this.keystoreFormula;
    }

    public final void setKeystoreFormula(Formula formula) {
        this.keystoreFormula = formula;
    }

    public final Formula getPasswordFormula() {
        return this.passwordFormula;
    }

    public final void setPasswordFormula(Formula formula) {
        this.passwordFormula = formula;
    }

    public final Formula getAliasFormula() {
        return this.aliasFormula;
    }

    public final void setAliasFormula(Formula formula) {
        this.aliasFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws Throwable {
        Scope scope;
        Project project;
        Project project2;
        Project project3;
        Formula formula = this.inputApkFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "game-unsigned.apk";
        }
        Formula formula2 = this.outputApkFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "game-signed.apk";
        }
        Formula formula3 = this.keystoreFormula;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "";
        }
        Formula formula4 = this.passwordFormula;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "";
        }
        Formula formula5 = this.aliasFormula;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        String str = strInterpretString5 != null ? strInterpretString5 : "";
        Context appContext = CatroidApplication.getAppContext();
        Scope scope2 = this.scope;
        File file = (scope2 == null || (project3 = scope2.getProject()) == null) ? null : project3.getFile(strInterpretString);
        Scope scope3 = this.scope;
        File file2 = (scope3 == null || (project2 = scope3.getProject()) == null) ? null : project2.getFile(strInterpretString2);
        File file3 = (strInterpretString3.length() <= 0 || (scope = this.scope) == null || (project = scope.getProject()) == null) ? null : project.getFile(strInterpretString3);
        if (file != null && file2 != null) {
            ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
            Intrinsics.checkNotNull(appContext);
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            String absolutePath2 = file2.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
            apkToolboxManager.signApk(appContext, absolutePath, absolutePath2, file3 != null ? file3.getAbsolutePath() : null, str.length() > 0 ? str : null, strInterpretString4.length() > 0 ? strInterpretString4 : null);
            return;
        }
        Log.e("SignApkAction", "Input or Output file path is invalid");
    }
}
