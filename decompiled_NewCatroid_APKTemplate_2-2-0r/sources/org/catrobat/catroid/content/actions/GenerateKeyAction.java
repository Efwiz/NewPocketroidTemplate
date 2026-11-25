package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.SshConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GenerateKeyAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: GenerateKeyAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/GenerateKeyAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "aliasFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAliasFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAliasFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "commonNameFormula", "getCommonNameFormula", "setCommonNameFormula", "filenameFormula", "getFilenameFormula", "setFilenameFormula", "passwordFormula", "getPasswordFormula", "setPasswordFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GenerateKeyAction extends TemporalAction {
    private Formula aliasFormula;
    private Formula commonNameFormula;
    private Formula filenameFormula;
    private Formula passwordFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFilenameFormula() {
        return this.filenameFormula;
    }

    public final void setFilenameFormula(Formula formula) {
        this.filenameFormula = formula;
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

    public final Formula getCommonNameFormula() {
        return this.commonNameFormula;
    }

    public final void setCommonNameFormula(Formula formula) {
        this.commonNameFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Formula formula = this.filenameFormula;
        File file = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "release.jks";
        }
        Formula formula2 = this.passwordFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "123456";
        }
        Formula formula3 = this.aliasFormula;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "key0";
        }
        Formula formula4 = this.commonNameFormula;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = SshConstants.USER;
        }
        Scope scope = this.scope;
        if (scope != null && (project = scope.getProject()) != null) {
            file = project.getFile(strInterpretString);
        }
        if (file != null) {
            ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            apkToolboxManager.generateKeyStore(absolutePath, strInterpretString3, strInterpretString2, strInterpretString4);
        }
    }
}
