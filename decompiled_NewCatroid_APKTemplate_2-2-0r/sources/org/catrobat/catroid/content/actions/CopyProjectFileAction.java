package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CopyProjectFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CopyProjectFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/CopyProjectFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "newFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNewFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNewFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sourceFileName", "getSourceFileName", "setSourceFileName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyProjectFileAction extends TemporalAction {
    private Formula newFileName;
    private Scope scope;
    private Formula sourceFileName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSourceFileName() {
        return this.sourceFileName;
    }

    public final void setSourceFileName(Formula formula) {
        this.sourceFileName = formula;
    }

    public final Formula getNewFileName() {
        return this.newFileName;
    }

    public final void setNewFileName(Formula formula) {
        this.newFileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        String str;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        Context appContext = CatroidApplication.getAppContext();
        Formula formula = this.sourceFileName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.newFileName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString;
        if (str2 == null || str2.length() == 0 || (str = strInterpretString2) == null || str.length() == 0) {
            Toast.makeText(appContext, "Имена файлов не могут быть пустыми", 0).show();
            return;
        }
        try {
            File file = project.getFile(strInterpretString);
            if (file.exists() && !file.isDirectory()) {
                File file2 = project.getFile(strInterpretString2);
                Intrinsics.checkNotNull(file);
                Intrinsics.checkNotNull(file2);
                FilesKt.copyTo$default(file, file2, true, 0, 4, null);
                return;
            }
            Toast.makeText(appContext, "Исходный файл не найден: " + strInterpretString, 0).show();
        } catch (IOException e) {
            Log.e("CopyProjectFileAction", "Ошибка при копировании файла '" + strInterpretString + "' в '" + strInterpretString2 + FormatHelper.QUOTE, e);
            Toast.makeText(appContext, "Ошибка копирования файла", 0).show();
        }
    }
}
