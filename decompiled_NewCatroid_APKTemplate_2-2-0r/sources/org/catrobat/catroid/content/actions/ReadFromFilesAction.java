package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadFromFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ReadFromFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/ReadFromFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "readFromFile", "", "file", "Ljava/io/File;", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReadFromFilesAction extends TemporalAction {
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
            Log.d("ReadFiles", "scope is not null");
            Formula formula = this.fileName;
            String strInterpretString = formula != null ? formula.interpretString(scope) : null;
            String str = "variable.txt";
            if (strInterpretString == null) {
                strInterpretString = "variable.txt";
            } else {
                Intrinsics.checkNotNull(strInterpretString);
            }
            Log.d("ReadFiles", "fileName: " + strInterpretString);
            Project project = scope.getProject();
            String strCheckExtension = project != null ? project.checkExtension(strInterpretString, "txt") : null;
            if (strCheckExtension != null) {
                Intrinsics.checkNotNull(strCheckExtension);
                str = strCheckExtension;
            }
            Log.d("ReadFiles", "fileName2: " + str);
            File file = project != null ? project.getFile(str) : null;
            UserVariable userVariable = this.variable;
            if (userVariable != null) {
                userVariable.setValue(0);
            }
            if (file != null) {
                Log.d("ReadFiles", "file is not null");
                UserVariable userVariable2 = this.variable;
                if (userVariable2 != null) {
                    userVariable2.setValue(readFromFile(file));
                }
                Log.d("ReadFiles", "filedata: " + readFromFile(file));
            }
        }
        Log.d("ReadFiles", "end");
    }

    public final String readFromFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return FilesKt.readText$default(file, null, 1, null);
    }
}
