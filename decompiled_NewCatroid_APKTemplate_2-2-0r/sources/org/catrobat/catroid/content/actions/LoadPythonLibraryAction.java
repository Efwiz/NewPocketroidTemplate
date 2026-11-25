package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LoadPythonLibraryAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoadPythonLibraryAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/actions/LoadPythonLibraryAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "unzip", "", "zipFile", "Ljava/io/File;", "targetDirectory", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoadPythonLibraryAction extends TemporalAction {
    private Formula fileName;
    private Scope scope;

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

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Formula formula = this.fileName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        Scope scope = this.scope;
        File file = (scope == null || (project = scope.getProject()) == null) ? null : project.getFile(strInterpretString);
        if (file == null || !file.exists()) {
            Log.e("LoadPythonLibrary", "Library file not found: " + strInterpretString);
            return;
        }
        PythonEngine pythonEngine = MainMenuActivity.pythonEngine;
        if (pythonEngine == null) {
            Log.e("LoadPythonLibrary", "PythonEngine not available.");
            return;
        }
        File file2 = new File(CatroidApplication.getAppContext().getFilesDir(), "pylibs_unpacked");
        file2.mkdirs();
        File file3 = new File(file2, file.getName());
        if (!file3.exists()) {
            Log.i("LoadPythonLibrary", "Unpacking '" + file.getName() + "' to '" + file3.getAbsolutePath() + "'...");
            try {
                unzip(file, file3);
                Log.i("LoadPythonLibrary", "Unpacking finished successfully.");
            } catch (IOException e) {
                Log.e("LoadPythonLibrary", "Failed to unpack library", e);
                return;
            }
        } else {
            Log.d("LoadPythonLibrary", "Library '" + file.getName() + "' already unpacked. Skipping.");
        }
        String absolutePath = file3.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        String strReplace$default = StringsKt.replace$default(absolutePath, FormatHelper.QUOTE, "\\'", false, 4, (Object) null);
        PythonEngine.runScriptAsync$default(pythonEngine, "import sys\nif '" + strReplace$default + "' not in sys.path:\n  sys.path.append('" + strReplace$default + "')", null, 2, null);
        Log.d("LoadPythonLibrary", "Task to add unpacked library '" + file.getName() + "' to sys.path has been queued.");
    }

    private final void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                File file = new File(targetDirectory, nextEntry.getName());
                if (nextEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    String parent = file.getParent();
                    Intrinsics.checkNotNull(parent);
                    new File(parent).mkdirs();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        ByteStreamsKt.copyTo$default(zipInputStream2, fileOutputStream, 0, 2, null);
                        CloseableKt.closeFinally(fileOutputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileOutputStream, th);
                            throw th2;
                        }
                    }
                }
                zipInputStream2.closeEntry();
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(zipInputStream, th3);
                throw th4;
            }
        }
    }
}
