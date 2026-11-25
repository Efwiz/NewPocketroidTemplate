package org.catrobat.catroid.content.actions;

import android.content.Intent;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LaunchProjectAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LaunchProjectAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/actions/LaunchProjectAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "projectNameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getProjectNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setProjectNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "unzip", "", "zipFile", "Ljava/io/File;", "targetDirectory", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LaunchProjectAction extends TemporalAction {
    private Formula projectNameFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getProjectNameFormula() {
        return this.projectNameFormula;
    }

    public final void setProjectNameFormula(Formula formula) {
        this.projectNameFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        File file;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (stageActivity == null || currentProject == null) {
            return;
        }
        Formula formula = this.projectNameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            Log.e("LaunchProjectAction", "Project file name is empty.");
            return;
        }
        boolean z = true;
        if (!StringsKt.endsWith(strInterpretString, Constants.ZIP_EXTENSION, true) && !StringsKt.endsWith(strInterpretString, Constants.CATROBAT_EXTENSION, true)) {
            z = false;
        }
        if (z) {
            File file2 = currentProject.getFile(strInterpretString);
            if (file2 == null || !file2.exists()) {
                Log.e("LaunchProjectAction", "Project archive not found in project files: " + strInterpretString);
                return;
            }
            File file3 = new File(stageActivity.getCacheDir(), "sub_projects");
            if (!file3.exists()) {
                file3.mkdirs();
            }
            String strSubstringBeforeLast$default = StringsKt.substringBeforeLast$default(strInterpretString, '.', (String) null, 2, (Object) null);
            file = new File(file3, strSubstringBeforeLast$default);
            if (!file.exists()) {
                Log.i("LaunchProjectAction", "Unpacking '" + strInterpretString + "' to cache...");
                try {
                    unzip(file2, file);
                } catch (IOException e) {
                    Log.e("LaunchProjectAction", "Failed to unzip project.", e);
                    return;
                }
            } else {
                Log.i("LaunchProjectAction", "Project '" + strSubstringBeforeLast$default + "' already in cache. Skipping unpack.");
            }
        } else {
            file = new File(currentProject.getFilesDir(), strInterpretString);
        }
        if (!file.exists() || !file.isDirectory()) {
            Log.e("LaunchProjectAction", "Project directory to launch does not exist: " + file.getAbsolutePath());
            return;
        }
        ProjectManager.pushProjectHistory();
        Intent intent = new Intent(stageActivity, (Class<?>) StageActivity.class);
        intent.putExtra(StageActivity.EXTRA_PROJECT_PATH, file.getAbsolutePath());
        stageActivity.startActivity(intent);
    }

    private final void unzip(File zipFile, File targetDirectory) throws IOException {
        InputStream fileInputStream = new FileInputStream(zipFile);
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream instanceof BufferedInputStream ? (BufferedInputStream) fileInputStream : new BufferedInputStream(fileInputStream, 8192));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            for (ZipEntry nextEntry = zipInputStream2.getNextEntry(); nextEntry != null; nextEntry = zipInputStream2.getNextEntry()) {
                File file = new File(targetDirectory, nextEntry.getName());
                if (nextEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    new File(file.getParent()).mkdirs();
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
