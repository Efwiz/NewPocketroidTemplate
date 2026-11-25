package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.android.dex.DexFormat;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ide.AndroidECJ;
import org.catrobat.catroid.ide.IdeSettings;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CompileJavaToDexAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CompileJavaToDexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\u0010\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010\nR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\bR\u000e\u0010\u0016\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/CompileJavaToDexAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "destDexFile", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDestDexFile", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDestDexFile", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "errorVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "finished", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "srcFolder", "getSrcFolder", "setSrcFolder", "started", "act", "delta", "", "reset", "", "restart", "runAsyncCompile", "setErrorVariable", "variable", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompileJavaToDexAction extends Action {
    private Formula destDexFile;
    private UserVariable errorVariable;
    private volatile boolean finished;
    private Scope scope;
    private Formula srcFolder;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSrcFolder() {
        return this.srcFolder;
    }

    public final void setSrcFolder(Formula formula) {
        this.srcFolder = formula;
    }

    public final Formula getDestDexFile() {
        return this.destDexFile;
    }

    public final void setDestDexFile(Formula formula) {
        this.destDexFile = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCompile();
        }
        return this.finished;
    }

    private final void runAsyncCompile() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.CompileJavaToDexAction.runAsyncCompile.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:143:0x03d7  */
            /* JADX WARN: Code duplicated, block: B:159:0x0405  */
            /* JADX WARN: Code duplicated, block: B:160:0x0406 A[Catch: all -> 0x0418, TryCatch #4 {all -> 0x0418, blocks: (B:157:0x03fa, B:160:0x0406, B:163:0x040e), top: B:175:0x03fa }] */
            /* JADX WARN: Code duplicated, block: B:162:0x040c  */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                AnonymousClass1 anonymousClass1;
                UserVariable userVariable;
                String localizedMessage;
                List<File> listEmptyList;
                File[] fileArr;
                File[] fileArrListFiles;
                Project project;
                Context appContext = CatroidApplication.getAppContext();
                try {
                    Scope scope = CompileJavaToDexAction.this.getScope();
                    File filesDir = (scope == null || (project = scope.getProject()) == null) ? null : project.getFilesDir();
                    if (filesDir == null) {
                        CompileJavaToDexAction.this.finished = true;
                        return;
                    }
                    Formula srcFolder = CompileJavaToDexAction.this.getSrcFolder();
                    String strInterpretString = srcFolder != null ? srcFolder.interpretString(CompileJavaToDexAction.this.getScope()) : null;
                    if (strInterpretString == null) {
                        strInterpretString = "";
                    }
                    Formula destDexFile = CompileJavaToDexAction.this.getDestDexFile();
                    String strInterpretString2 = destDexFile != null ? destDexFile.interpretString(CompileJavaToDexAction.this.getScope()) : null;
                    if (strInterpretString2 == null) {
                        strInterpretString2 = DexFormat.DEX_IN_JAR_NAME;
                    }
                    File file = new File(filesDir, strInterpretString2);
                    File codeCacheDir = appContext.getCodeCacheDir();
                    File file2 = filesDir;
                    File file3 = new File(codeCacheDir, "compile_temp_src_" + System.currentTimeMillis());
                    file3.mkdirs();
                    File file4 = new File(codeCacheDir, "compile_temp_classes_" + System.currentTimeMillis());
                    file4.mkdirs();
                    File file5 = new File(codeCacheDir, "compile_temp_dex_" + System.currentTimeMillis());
                    file5.mkdirs();
                    if (StringsKt.trim((CharSequence) strInterpretString).toString().length() > 0) {
                        List listSplit$default = StringsKt.split$default((CharSequence) strInterpretString, new String[]{","}, false, 0, 6, (Object) null);
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
                        ArrayList arrayList3 = arrayList2;
                        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                        Iterator it2 = arrayList3.iterator();
                        while (it2.hasNext()) {
                            arrayList4.add(new File(file2, (String) it2.next()));
                        }
                        listEmptyList = arrayList4;
                    } else {
                        File[] fileArrListFiles2 = file2.listFiles();
                        if (fileArrListFiles2 != null) {
                            ArrayList arrayList5 = new ArrayList();
                            int length = fileArrListFiles2.length;
                            int i = 0;
                            while (i < length) {
                                File file6 = fileArrListFiles2[i];
                                if (file6.isFile()) {
                                    Intrinsics.checkNotNull(file6);
                                    fileArr = fileArrListFiles2;
                                    if (Intrinsics.areEqual(FilesKt.getExtension(file6), SuffixConstants.EXTENSION_java)) {
                                        arrayList5.add(file6);
                                    }
                                } else {
                                    fileArr = fileArrListFiles2;
                                }
                                i++;
                                fileArrListFiles2 = fileArr;
                            }
                            listEmptyList = arrayList5;
                        } else {
                            listEmptyList = CollectionsKt.emptyList();
                        }
                    }
                    if (listEmptyList.isEmpty()) {
                        UserVariable userVariable2 = CompileJavaToDexAction.this.errorVariable;
                        if (userVariable2 != null) {
                            userVariable2.setValue("Request failed: No .java files found to compile");
                        }
                        FilesKt.deleteRecursively(file3);
                        FilesKt.deleteRecursively(file4);
                        FilesKt.deleteRecursively(file5);
                        CompileJavaToDexAction.this.finished = true;
                        CompileJavaToDexAction.this.finished = true;
                        return;
                    }
                    for (File file7 : listEmptyList) {
                        if (file7.exists()) {
                            Intrinsics.checkNotNull(file7);
                            if (Intrinsics.areEqual(FilesKt.getExtension(file7), SuffixConstants.EXTENSION_java)) {
                                FilesKt.copyTo$default(file7, new File(file3, file7.getName()), true, 0, 4, null);
                            }
                        }
                    }
                    IdeSettings ideSettings = IdeSettings.INSTANCE;
                    Intrinsics.checkNotNull(appContext);
                    File androidJar = ideSettings.getAndroidJar(appContext, 34);
                    File file8 = new File(appContext.getFilesDir(), "core-lambda-stubs.jar");
                    if (!file8.exists()) {
                        try {
                            InputStream inputStreamOpen = appContext.getAssets().open("core-lambda-stubs.jar");
                            try {
                                InputStream inputStream = inputStreamOpen;
                                FileOutputStream fileOutputStream = new FileOutputStream(file8);
                                try {
                                    Intrinsics.checkNotNull(inputStream);
                                    ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    CloseableKt.closeFinally(inputStreamOpen, null);
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
                                    CloseableKt.closeFinally(inputStreamOpen, th3);
                                    throw th4;
                                }
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            anonymousClass1 = this;
                            try {
                                th.printStackTrace();
                                userVariable = CompileJavaToDexAction.this.errorVariable;
                                if (userVariable != null) {
                                    localizedMessage = th.getLocalizedMessage();
                                    if (localizedMessage == null) {
                                        localizedMessage = "Unknown compilation error";
                                    }
                                    userVariable.setValue(localizedMessage);
                                }
                            } finally {
                                CompileJavaToDexAction.this.finished = true;
                            }
                        }
                    }
                    List<? extends File> listMutableListOf = CollectionsKt.mutableListOf(androidJar);
                    Iterator it3 = CollectionsKt.listOf((Object[]) new String[]{"app-classes.jar", "gdx.jar", "xstream.jar", "koin-core.jar", "fragment.jar", "activity.jar", "core.jar"}).iterator();
                    while (it3.hasNext()) {
                        File file9 = new File(appContext.getFilesDir(), (String) it3.next());
                        if (file9.exists()) {
                            listMutableListOf.add(file9);
                        }
                    }
                    File file10 = new File(file2, "libs");
                    if (file10.exists() && (fileArrListFiles = file10.listFiles()) != null) {
                        ArrayList<File> arrayList6 = new ArrayList();
                        for (File file11 : fileArrListFiles) {
                            if (file11.isFile()) {
                                Intrinsics.checkNotNull(file11);
                                if (Intrinsics.areEqual(FilesKt.getExtension(file11), "jar")) {
                                    arrayList6.add(file11);
                                }
                            }
                        }
                        for (File file12 : arrayList6) {
                            Intrinsics.checkNotNull(file12);
                            listMutableListOf.add(file12);
                        }
                    }
                    Map<String, byte[]> mapCompileDirectory = AndroidECJ.INSTANCE.compileDirectory(file3, listMutableListOf, androidJar, file8);
                    if (!mapCompileDirectory.isEmpty()) {
                        for (Map.Entry<String, byte[]> entry : mapCompileDirectory.entrySet()) {
                            String key = entry.getKey();
                            byte[] value = entry.getValue();
                            File file13 = new File(file4, key);
                            File parentFile = file13.getParentFile();
                            if (parentFile != null) {
                                parentFile.mkdirs();
                            }
                            FilesKt.writeBytes(file13, value);
                        }
                        List list = SequencesKt.toList(SequencesKt.filter(FilesKt.walkTopDown(file4), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.content.actions.CompileJavaToDexAction$runAsyncCompile$1$classFiles$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(File it4) {
                                Intrinsics.checkNotNullParameter(it4, "it");
                                return Boolean.valueOf(it4.isFile() && Intrinsics.areEqual(FilesKt.getExtension(it4), "class"));
                            }
                        }));
                        D8Command.Builder builder = (D8Command.Builder) D8Command.builder().setMode(CompilationMode.DEBUG).setMinApiLevel(21).setIntermediate(false).setOutput(file5.toPath(), OutputMode.DexIndexed).addLibraryFiles(androidJar.toPath());
                        Iterator it4 = list.iterator();
                        while (it4.hasNext()) {
                            builder.addProgramFiles(((File) it4.next()).toPath());
                        }
                        D8.run((D8Command) builder.build());
                        File file14 = new File(file5, DexFormat.DEX_IN_JAR_NAME);
                        if (file14.exists()) {
                            File parentFile2 = file.getParentFile();
                            if (parentFile2 != null) {
                                parentFile2.mkdirs();
                            }
                            FilesKt.copyTo$default(file14, file, true, 0, 4, null);
                        }
                    }
                    anonymousClass1 = this;
                    try {
                        UserVariable userVariable3 = CompileJavaToDexAction.this.errorVariable;
                        if (userVariable3 != null) {
                            userVariable3.setValue("");
                        }
                        FilesKt.deleteRecursively(file3);
                        FilesKt.deleteRecursively(file4);
                        FilesKt.deleteRecursively(file5);
                    } catch (Throwable th6) {
                        th = th6;
                        th.printStackTrace();
                        userVariable = CompileJavaToDexAction.this.errorVariable;
                        if (userVariable != null) {
                            localizedMessage = th.getLocalizedMessage();
                            if (localizedMessage == null) {
                                localizedMessage = "Unknown compilation error";
                            }
                            userVariable.setValue(localizedMessage);
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                    anonymousClass1 = this;
                }
            }
        }, 30, null);
    }

    public final void setErrorVariable(UserVariable variable) {
        this.errorVariable = variable;
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
        this.srcFolder = null;
        this.destDexFile = null;
        this.errorVariable = null;
    }
}
