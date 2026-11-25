package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SaveToInternalStorageAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SaveToInternalStorageAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SaveToInternalStorageAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "internalStoragePath", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInternalStoragePath", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInternalStoragePath", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "projectFileName", "getProjectFileName", "setProjectFileName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SaveToInternalStorageAction extends TemporalAction {
    private Formula internalStoragePath;
    private Formula projectFileName;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getProjectFileName() {
        return this.projectFileName;
    }

    public final void setProjectFileName(Formula formula) {
        this.projectFileName = formula;
    }

    public final Formula getInternalStoragePath() {
        return this.internalStoragePath;
    }

    public final void setInternalStoragePath(Formula formula) {
        this.internalStoragePath = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Context appContext;
        String str;
        File file;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null || (appContext = CatroidApplication.getAppContext()) == null) {
            return;
        }
        Formula formula = this.projectFileName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.internalStoragePath;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString;
        if (str2 == null || StringsKt.isBlank(str2) || (str = strInterpretString2) == null || StringsKt.isBlank(str) || (file = project.getFile(strInterpretString)) == null || !file.exists()) {
            return;
        }
        File file2 = new File(appContext.getFilesDir(), strInterpretString2);
        try {
            File parentFile = file2.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FilesKt.copyTo$default(file, file2, true, 0, 4, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
