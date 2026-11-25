package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: DirectoryBasedClassFinder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"isKotlinWithCompatibleAbiVersion", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "decompiler-to-file-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DirectoryBasedClassFinderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isKotlinWithCompatibleAbiVersion(VirtualFile virtualFile, JvmMetadataVersion jvmMetadataVersion) {
        ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData$default;
        ClsKotlinBinaryClassCache companion = ClsKotlinBinaryClassCache.INSTANCE.getInstance();
        return ClsKotlinBinaryClassCache.isKotlinJvmCompiledFile$default(companion, virtualFile, null, 2, null) && (kotlinBinaryClassHeaderData$default = ClsKotlinBinaryClassCache.getKotlinBinaryClassHeaderData$default(companion, virtualFile, null, 2, null)) != null && kotlinBinaryClassHeaderData$default.getMetadataVersion().isCompatible(jvmMetadataVersion);
    }
}
