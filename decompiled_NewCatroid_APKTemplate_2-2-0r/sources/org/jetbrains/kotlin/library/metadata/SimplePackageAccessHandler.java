package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: KlibMetadataDeserializationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/SimplePackageAccessHandler;", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "()V", "loadPackageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "packageFqName", "", "partName", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimplePackageAccessHandler implements PackageAccessHandler {
    public static final SimplePackageAccessHandler INSTANCE = new SimplePackageAccessHandler();

    private SimplePackageAccessHandler() {
    }

    @Override // org.jetbrains.kotlin.library.metadata.PackageAccessHandler
    public ProtoBuf.PackageFragment loadPackageFragment(KotlinLibrary library, String packageFqName, String partName) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        return KlibMetadataDeserializationUtilsKt.parsePackageFragment(library.packageMetadata(packageFqName, partName));
    }
}
