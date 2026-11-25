package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/UnzipProjectFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: UnzipProjectFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/UnzipProjectFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "finished", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "zipFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getZipFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setZipFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "act", "delta", "", "reset", "", "restart", "runAsyncUnzip", "unzipFile", "zipFile", "Ljava/io/File;", "targetDir", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnzipProjectFilesAction extends Action {
    private volatile boolean finished;
    private Scope scope;
    private boolean started;
    private Formula zipFileName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getZipFileName() {
        return this.zipFileName;
    }

    public final void setZipFileName(Formula formula) {
        this.zipFileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncUnzip();
        }
        return this.finished;
    }

    private final void runAsyncUnzip() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.UnzipProjectFilesAction.runAsyncUnzip.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Project project;
                Project project2;
                try {
                    try {
                        Scope scope = UnzipProjectFilesAction.this.getScope();
                        File file = null;
                        File filesDir = (scope == null || (project2 = scope.getProject()) == null) ? null : project2.getFilesDir();
                        if (filesDir != null) {
                            Formula zipFileName = UnzipProjectFilesAction.this.getZipFileName();
                            String strInterpretString = zipFileName != null ? zipFileName.interpretString(UnzipProjectFilesAction.this.getScope()) : null;
                            if (strInterpretString == null) {
                                strInterpretString = "archive.zip";
                            }
                            Scope scope2 = UnzipProjectFilesAction.this.getScope();
                            if (scope2 != null && (project = scope2.getProject()) != null) {
                                file = project.getFile(strInterpretString);
                            }
                            if (file != null) {
                                if (file.exists()) {
                                    UnzipProjectFilesAction.this.unzipFile(file, filesDir);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    UnzipProjectFilesAction.this.finished = true;
                }
            }
        }, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unzipFile(File zipFile, File targetDir) {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            ZipEntry nextEntry = zipInputStream2.getNextEntry();
            while (nextEntry != null) {
                File file = new File(targetDir, nextEntry.getName());
                String canonicalPath = file.getCanonicalPath();
                Intrinsics.checkNotNullExpressionValue(canonicalPath, "getCanonicalPath(...)");
                String canonicalPath2 = targetDir.getCanonicalPath();
                Intrinsics.checkNotNullExpressionValue(canonicalPath2, "getCanonicalPath(...)");
                if (!StringsKt.startsWith$default(canonicalPath, canonicalPath2, false, 2, (Object) null)) {
                    nextEntry = zipInputStream2.getNextEntry();
                } else {
                    if (nextEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
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
                    nextEntry = zipInputStream2.getNextEntry();
                }
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

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.zipFileName = null;
    }
}
