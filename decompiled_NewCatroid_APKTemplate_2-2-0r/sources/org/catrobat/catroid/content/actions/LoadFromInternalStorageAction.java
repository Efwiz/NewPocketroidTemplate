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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LoadFromInternalStorageAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoadFromInternalStorageAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/LoadFromInternalStorageAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "internalStoragePath", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInternalStoragePath", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInternalStoragePath", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoadFromInternalStorageAction extends TemporalAction {
    private Formula internalStoragePath;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
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
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null || (appContext = CatroidApplication.getAppContext()) == null) {
            return;
        }
        Formula formula = this.internalStoragePath;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || StringsKt.isBlank(str)) {
            return;
        }
        File file = new File(appContext.getFilesDir(), strInterpretString);
        if (file.exists()) {
            File file2 = new File(project.getFilesDir(), file.getName());
            try {
                project.getFilesDir().mkdirs();
                FilesKt.copyTo$default(file, file2, true, 0, 4, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
