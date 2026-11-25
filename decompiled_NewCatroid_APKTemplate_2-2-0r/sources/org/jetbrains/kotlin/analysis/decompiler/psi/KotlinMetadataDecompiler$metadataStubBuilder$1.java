package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinMetadataStubBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: compiled from: KotlinMetadataDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class KotlinMetadataDecompiler$metadataStubBuilder$1 extends FunctionReference implements Function2<VirtualFile, byte[], KotlinMetadataStubBuilder.FileWithMetadata> {
    KotlinMetadataDecompiler$metadataStubBuilder$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "readFileSafely(Lcom/intellij/openapi/vfs/VirtualFile;[B)Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "readFileSafely";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(KotlinMetadataDecompiler.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final KotlinMetadataStubBuilder.FileWithMetadata invoke(VirtualFile p0, byte[] bArr) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((KotlinMetadataDecompiler) this.receiver).readFileSafely(p0, bArr);
    }
}
