package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/BuiltInKlibMetadataDeserializedPackageFragment;", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragment;", "Lorg/jetbrains/kotlin/builtins/BuiltInsPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "packageAccessHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "partName", "", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "isFallback", "", "()Z", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInKlibMetadataDeserializedPackageFragment extends KlibMetadataDeserializedPackageFragment implements BuiltInsPackageFragment {
    @Override // org.jetbrains.kotlin.builtins.BuiltInsPackageFragment
    public boolean isFallback() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuiltInKlibMetadataDeserializedPackageFragment(FqName fqName, KotlinLibrary library, PackageAccessHandler packageAccessHandler, StorageManager storageManager, ModuleDescriptor module, String partName, DeserializedContainerSource containerSource) {
        super(fqName, library, packageAccessHandler, storageManager, module, partName, containerSource);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(partName, "partName");
        Intrinsics.checkNotNullParameter(containerSource, "containerSource");
    }
}
