package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ZipProjectFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ZipProjectFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J \u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J&\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u001e2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020 0&2\u0006\u0010'\u001a\u00020\u001eH\u0002J \u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/content/actions/ZipProjectFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "filesToZip", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFilesToZip", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFilesToZip", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "zipFileName", "getZipFileName", "setZipFileName", "act", "delta", "", "reset", "", "restart", "runAsyncZip", "zipDirectory", "dir", "Ljava/io/File;", "basePath", "", "zos", "Ljava/util/zip/ZipOutputStream;", "zipFiles", ProjectUploadActivityKt.PROJECT_DIR, "fileNames", "", "zipFile", "zipSingleFile", "file", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ZipProjectFilesAction extends Action {
    private Formula filesToZip;
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

    public final Formula getFilesToZip() {
        return this.filesToZip;
    }

    public final void setFilesToZip(Formula formula) {
        this.filesToZip = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncZip();
        }
        return this.finished;
    }

    private final void runAsyncZip() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.ZipProjectFilesAction.runAsyncZip.1
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
                        Scope scope = ZipProjectFilesAction.this.getScope();
                        File filesDir = (scope == null || (project2 = scope.getProject()) == null) ? null : project2.getFilesDir();
                        if (filesDir != null) {
                            Formula zipFileName = ZipProjectFilesAction.this.getZipFileName();
                            String strInterpretString = zipFileName != null ? zipFileName.interpretString(ZipProjectFilesAction.this.getScope()) : null;
                            if (strInterpretString == null) {
                                strInterpretString = "archive.zip";
                            }
                            Scope scope2 = ZipProjectFilesAction.this.getScope();
                            File file = (scope2 == null || (project = scope2.getProject()) == null) ? null : project.getFile(strInterpretString);
                            if (file != null) {
                                Formula filesToZip = ZipProjectFilesAction.this.getFilesToZip();
                                String strInterpretString2 = filesToZip != null ? filesToZip.interpretString(ZipProjectFilesAction.this.getScope()) : null;
                                if (strInterpretString2 == null) {
                                    strInterpretString2 = "";
                                }
                                List listSplit$default = StringsKt.split$default((CharSequence) strInterpretString2, new String[]{","}, false, 0, 6, (Object) null);
                                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                                Iterator it = listSplit$default.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                                }
                                ArrayList arrayList2 = new ArrayList();
                                for (Object obj : arrayList) {
                                    if (((String) obj).length() > 0) {
                                        arrayList2.add(obj);
                                    }
                                }
                                ZipProjectFilesAction.this.zipFiles(filesDir, arrayList2, file);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    ZipProjectFilesAction.this.finished = true;
                }
            }
        }, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zipFiles(File projectDir, List<String> fileNames, File zipFile) {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        try {
            ZipOutputStream zipOutputStream2 = zipOutputStream;
            Iterator<String> it = fileNames.iterator();
            while (it.hasNext()) {
                File file = new File(projectDir, it.next());
                if (file.exists()) {
                    if (file.isDirectory()) {
                        String name2 = file.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        zipDirectory(file, name2, zipOutputStream2);
                    } else {
                        zipSingleFile(file, "", zipOutputStream2);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipOutputStream, th);
                throw th2;
            }
        }
    }

    private final void zipDirectory(File dir, String basePath, ZipOutputStream zos) {
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            String name2 = basePath.length() == 0 ? file.getName() : basePath + "/" + file.getName();
            if (file.isDirectory()) {
                Intrinsics.checkNotNull(file);
                Intrinsics.checkNotNull(name2);
                zipDirectory(file, name2, zos);
            } else {
                Intrinsics.checkNotNull(file);
                zipSingleFile(file, basePath, zos);
            }
        }
    }

    private final void zipSingleFile(File file, String basePath, ZipOutputStream zos) {
        String name2;
        if (basePath.length() == 0) {
            name2 = file.getName();
        } else {
            name2 = basePath + "/" + file.getName();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            zos.putNextEntry(new ZipEntry(name2));
            ByteStreamsKt.copyTo$default(fileInputStream, zos, 0, 2, null);
            zos.closeEntry();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileInputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
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
        this.filesToZip = null;
    }
}
