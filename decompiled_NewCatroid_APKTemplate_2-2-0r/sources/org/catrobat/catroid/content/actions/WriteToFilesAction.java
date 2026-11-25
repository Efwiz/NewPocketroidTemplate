package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteToFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteToFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\u0016\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteToFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "contextt", "Landroid/content/Context;", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "writeToFile", "file", "Ljava/io/File;", FirebaseAnalytics.Param.CONTENT, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteToFilesAction extends TemporalAction {
    private Context contextt;
    private Formula fileName;
    private Scope scope;
    private UserVariable variable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFileName() {
        return this.fileName;
    }

    public final void setFileName(Formula formula) {
        this.fileName = formula;
    }

    public final UserVariable getVariable() {
        return this.variable;
    }

    public final void setVariable(UserVariable userVariable) {
        this.variable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope = this.scope;
        if (scope != null) {
            Formula formula = this.fileName;
            String strInterpretString = formula != null ? formula.interpretString(scope) : null;
            String str = "variable.txt";
            if (strInterpretString == null) {
                strInterpretString = "variable.txt";
            } else {
                Intrinsics.checkNotNull(strInterpretString);
            }
            Project project = scope.getProject();
            String strCheckExtension = project != null ? project.checkExtension(strInterpretString, "txt") : null;
            if (strCheckExtension != null) {
                Intrinsics.checkNotNull(strCheckExtension);
                str = strCheckExtension;
            }
            File file = new File(project != null ? project.getFilesDir() : null, str);
            UserVariable userVariable = this.variable;
            writeToFile(file, String.valueOf(userVariable != null ? userVariable.getValue() : null));
        }
    }

    public final void writeToFile(File file, String content) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(content, "content");
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FilesKt.writeText(file, content, Charsets.UTF_8);
        } catch (IOException e) {
            System.out.println((Object) ("Произошла ошибка при записи в файл " + file.getPath() + ": " + e.getMessage()));
            ErrorLog.INSTANCE.log("Произошла ошибка при записи в файл " + file.getPath() + ": " + e.getMessage());
        }
    }
}
