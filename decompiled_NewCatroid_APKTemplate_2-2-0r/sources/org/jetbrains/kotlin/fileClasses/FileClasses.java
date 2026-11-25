package org.jetbrains.kotlin.fileClasses;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"getFileClassInternalName", "", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassesProvider;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileClasses {
    @Deprecated(level = DeprecationLevel.ERROR, message = "Use JvmFileClassUtil.getFileClassInternalName instead.", replaceWith = @ReplaceWith(expression = "JvmFileClassUtil.getFileClassInternalName(file)", imports = {}))
    public static final String getFileClassInternalName(JvmFileClassesProvider jvmFileClassesProvider, KtFile file) {
        Intrinsics.checkNotNullParameter(jvmFileClassesProvider, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        return JvmFileClassUtil.getFileClassInternalName(file);
    }
}
