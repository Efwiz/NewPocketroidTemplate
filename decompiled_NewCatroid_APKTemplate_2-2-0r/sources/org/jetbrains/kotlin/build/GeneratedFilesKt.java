package org.jetbrains.kotlin.build;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: generatedFiles.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isModuleMappingFile", "", "Ljava/io/File;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GeneratedFilesKt {
    public static final boolean isModuleMappingFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return Intrinsics.areEqual(FilesKt.getExtension(file), "kotlin_module") && Intrinsics.areEqual(file.getParentFile().getName(), "META-INF");
    }
}
