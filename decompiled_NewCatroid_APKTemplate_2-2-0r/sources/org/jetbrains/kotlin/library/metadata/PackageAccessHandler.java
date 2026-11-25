package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: KlibMetadataDeserializationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\tH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "", "loadModuleHeader", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "loadPackageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "packageFqName", "", "partName", "loadPackageFragmentByteArray", "byteArray", "", "markNeededForLink", "", "fqName", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageAccessHandler {
    default void markNeededForLink(KotlinLibrary library, String fqName) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
    }

    default KlibMetadataProtoBuf.Header loadModuleHeader(KotlinLibrary library) {
        Intrinsics.checkNotNullParameter(library, "library");
        return KlibMetadataDeserializationUtilsKt.parseModuleHeader(library.getModuleHeaderData());
    }

    default ProtoBuf.PackageFragment loadPackageFragment(KotlinLibrary library, String packageFqName, String partName) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        return loadPackageFragmentByteArray(library.packageMetadata(packageFqName, partName));
    }

    default ProtoBuf.PackageFragment loadPackageFragmentByteArray(byte[] byteArray) {
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        return KlibMetadataDeserializationUtilsKt.parsePackageFragment(byteArray);
    }
}
