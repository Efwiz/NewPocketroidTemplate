package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsKotlinBinaryClassCache;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DeserializerForClassfileDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"DeserializerForClassfileDecompiler", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/DeserializerForClassfileDecompiler;", "classFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "decompiler-to-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeserializerForClassfileDecompilerKt {
    public static final DeserializerForClassfileDecompiler DeserializerForClassfileDecompiler(VirtualFile classFile) {
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData$default = ClsKotlinBinaryClassCache.getKotlinBinaryClassHeaderData$default(ClsKotlinBinaryClassCache.INSTANCE.getInstance(), classFile, null, 2, null);
        if (kotlinBinaryClassHeaderData$default == null) {
            throw new IllegalStateException(("Decompiled data factory shouldn't be called on an unsupported file: " + classFile).toString());
        }
        FqName packageFqName = kotlinBinaryClassHeaderData$default.getClassId().getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        VirtualFile parent = classFile.getParent();
        Intrinsics.checkNotNull(parent);
        return new DeserializerForClassfileDecompiler(parent, packageFqName, kotlinBinaryClassHeaderData$default.getMetadataVersion());
    }
}
