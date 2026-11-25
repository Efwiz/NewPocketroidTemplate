package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinMetadataStubBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KotlinBuiltInDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinBuiltInDecompiler;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinMetadataDecompiler;", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "()V", "readFile", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "bytes", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinBuiltInDecompiler extends KotlinMetadataDecompiler<BuiltInsBinaryVersion> {
    public KotlinBuiltInDecompiler() {
        super(KotlinBuiltInFileType.INSTANCE, new Function0<SerializerExtensionProtocol>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinBuiltInDecompiler.1
            @Override // kotlin.jvm.functions.Function0
            public final SerializerExtensionProtocol invoke() {
                return BuiltInSerializerProtocol.INSTANCE;
            }
        }, FlexibleTypeDeserializer.ThrowException.INSTANCE, new Function0<BuiltInsBinaryVersion>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinBuiltInDecompiler.2
            @Override // kotlin.jvm.functions.Function0
            public final BuiltInsBinaryVersion invoke() {
                return BuiltInsBinaryVersion.INSTANCE;
            }
        }, new Function0<BuiltInsBinaryVersion>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinBuiltInDecompiler.3
            @Override // kotlin.jvm.functions.Function0
            public final BuiltInsBinaryVersion invoke() {
                return BuiltInsBinaryVersion.INVALID_VERSION;
            }
        }, 95);
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDecompiler
    public KotlinMetadataStubBuilder.FileWithMetadata readFile(byte[] bytes, VirtualFile file) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(file, "file");
        return BuiltInDefinitionFile.INSTANCE.read(bytes, file);
    }
}
