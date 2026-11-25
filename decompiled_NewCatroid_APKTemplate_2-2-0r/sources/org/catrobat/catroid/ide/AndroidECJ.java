package org.catrobat.catroid.ide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/AndroidECJ.class
  classes14.dex
 */
/* JADX INFO: compiled from: AndroidECJ.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ide/AndroidECJ;", "", "()V", "compileDirectory", "", "", "", "srcDir", "Ljava/io/File;", "libraries", "", "bootJar", "stubsJar", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AndroidECJ {
    public static final AndroidECJ INSTANCE = new AndroidECJ();

    private AndroidECJ() {
    }

    public final Map<String, byte[]> compileDirectory(File srcDir, List<? extends File> libraries, File bootJar, File stubsJar) throws IOException {
        Intrinsics.checkNotNullParameter(srcDir, "srcDir");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(bootJar, "bootJar");
        Intrinsics.checkNotNullParameter(stubsJar, "stubsJar");
        File fileCreateTempFile = File.createTempFile("necat_ecj", "");
        fileCreateTempFile.delete();
        fileCreateTempFile.mkdirs();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        ArrayList arrayList = new ArrayList();
        arrayList.add("-1.8");
        arrayList.add("-d");
        String absolutePath = fileCreateTempFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        arrayList.add(absolutePath);
        arrayList.add("-proc:none");
        arrayList.add("-nowarn");
        ArrayList arrayList2 = new ArrayList();
        if (bootJar.exists()) {
            String absolutePath2 = bootJar.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
            arrayList2.add(absolutePath2);
        }
        if (stubsJar.exists()) {
            String absolutePath3 = stubsJar.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
            arrayList2.add(absolutePath3);
        }
        ArrayList arrayList3 = arrayList2;
        if (!arrayList3.isEmpty()) {
            arrayList.add("-bootclasspath");
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            arrayList.add(CollectionsKt.joinToString$default(arrayList2, pathSeparator, null, null, 0, null, null, 62, null));
        }
        ArrayList arrayList4 = new ArrayList();
        arrayList4.addAll(arrayList3);
        for (File file : libraries) {
            if (file.exists()) {
                String absolutePath4 = file.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath4, "getAbsolutePath(...)");
                arrayList4.add(absolutePath4);
            }
        }
        if (!arrayList4.isEmpty()) {
            arrayList.add("-classpath");
            String pathSeparator2 = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator2, "pathSeparator");
            arrayList.add(CollectionsKt.joinToString$default(arrayList4, pathSeparator2, null, null, 0, null, null, 62, null));
        }
        Iterator it = SequencesKt.filter(FilesKt.walkTopDown(srcDir), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.ide.AndroidECJ.compileDirectory.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2.isFile() && Intrinsics.areEqual(FilesKt.getExtension(it2), SuffixConstants.EXTENSION_java));
            }
        }).iterator();
        while (it.hasNext()) {
            String absolutePath5 = ((File) it.next()).getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath5, "getAbsolutePath(...)");
            arrayList.add(absolutePath5);
        }
        if (!BatchCompiler.compile((String[]) arrayList.toArray(new String[0]), new PrintWriter(System.out), printWriter, (CompilationProgress) null)) {
            throw new RuntimeException("ECJ Compilation Failed:\n" + byteArrayOutputStream);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Intrinsics.checkNotNull(fileCreateTempFile);
        for (File file2 : SequencesKt.filter(FilesKt.walkTopDown(fileCreateTempFile), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.ide.AndroidECJ.compileDirectory.4
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2.isFile() && Intrinsics.areEqual(FilesKt.getExtension(it2), "class"));
            }
        })) {
            String path = FilesKt.relativeTo(file2, fileCreateTempFile).getPath();
            Intrinsics.checkNotNull(path);
            linkedHashMap.put(path, FilesKt.readBytes(file2));
        }
        FilesKt.deleteRecursively(fileCreateTempFile);
        return linkedHashMap;
    }
}
