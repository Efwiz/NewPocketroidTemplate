package org.catrobat.catroid.content.actions;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MoveDownloadsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MoveDownloadsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0003J\u0018\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0012H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0015R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/MoveDownloadsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "contextt", "Landroid/content/Context;", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "copyFileFromDownloads", "Ljava/io/File;", "context", "", "destinationDir", "copyFileToDir", "file", "dir", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MoveDownloadsAction extends TemporalAction {
    private Context contextt;
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
        Project project2;
        Scope scope = this.scope;
        String strCheckExtension = null;
        if (scope != null && (project2 = scope.getProject()) != null) {
            Formula formula = this.fileName;
            strCheckExtension = project2.checkExtension(formula != null ? formula.interpretString(this.scope) : null, "txt");
        }
        if (strCheckExtension == null) {
            strCheckExtension = "variable.txt";
        }
        Scope scope2 = this.scope;
        if (scope2 == null || (project = scope2.getProject()) == null) {
            return;
        }
        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), strCheckExtension);
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        File filesDir = project.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        copyFileFromDownloads(appContext, strCheckExtension, filesDir);
    }

    private final File copyFileToDir(File file, File dir) {
        File file2 = new File(dir, file.getName());
        FilesKt.copyTo$default(file, file2, true, 0, 4, null);
        return file2;
    }

    private final File copyFileFromDownloads(Context context, String fileName, File destinationDir) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri EXTERNAL_CONTENT_URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
        try {
            Cursor cursorQuery = contentResolver.query(EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, "_display_name = ?", new String[]{fileName}, "date_added DESC");
            if (cursorQuery != null) {
                Cursor cursor = cursorQuery;
                try {
                    Cursor cursor2 = cursor;
                    if (cursor2.moveToFirst()) {
                        Uri uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cursor2.getLong(cursor2.getColumnIndexOrThrow("_id")));
                        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
                        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uriWithAppendedId);
                        if (inputStreamOpenInputStream != null) {
                            InputStream inputStream = inputStreamOpenInputStream;
                            try {
                                InputStream inputStream2 = inputStream;
                                File file = new File(destinationDir, fileName);
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                try {
                                    Intrinsics.checkNotNull(inputStream2);
                                    ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream, 0, 2, null);
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    CloseableKt.closeFinally(inputStream, null);
                                    CloseableKt.closeFinally(cursor, null);
                                    return file;
                                } catch (Throwable th) {
                                    try {
                                        throw th;
                                    } catch (Throwable th2) {
                                        CloseableKt.closeFinally(fileOutputStream, th);
                                        throw th2;
                                    }
                                }
                            } catch (Throwable th3) {
                                try {
                                    throw th3;
                                } catch (Throwable th4) {
                                    CloseableKt.closeFinally(inputStream, th3);
                                    throw th4;
                                }
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor, null);
                } catch (Throwable th5) {
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        CloseableKt.closeFinally(cursor, th5);
                        throw th6;
                    }
                }
            }
            Log.w("MoveDownloadsAction", "File '" + fileName + "' not found in Downloads.");
            return null;
        } catch (IOException e) {
            Log.e("MoveDownloadsAction", "Error copying file from Downloads: " + e.getMessage());
            return null;
        }
    }
}
