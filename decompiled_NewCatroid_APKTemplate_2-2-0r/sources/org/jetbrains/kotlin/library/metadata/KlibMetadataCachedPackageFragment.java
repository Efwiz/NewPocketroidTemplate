package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataCachedPackageFragment;", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataPackageFragment;", "byteArray", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "protoForNames", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "([BLorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/name/FqName;)V", "getProtoForNames", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataCachedPackageFragment extends KlibMetadataPackageFragment {
    private final ProtoBuf.PackageFragment protoForNames;

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KlibMetadataCachedPackageFragment(byte[] bArr, StorageManager storageManager, ModuleDescriptor moduleDescriptor, ProtoBuf.PackageFragment packageFragment, FqName fqName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        ProtoBuf.PackageFragment packageFragment2 = (i & 8) != 0 ? KlibMetadataDeserializationUtilsKt.parsePackageFragment(bArr) : packageFragment;
        this(bArr, storageManager, moduleDescriptor, packageFragment2, (i & 16) != 0 ? new FqName((String) packageFragment2.getExtension(KlibMetadataProtoBuf.fqName)) : fqName);
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment
    public ProtoBuf.PackageFragment getProtoForNames() {
        return this.protoForNames;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataCachedPackageFragment(byte[] byteArray, StorageManager storageManager, ModuleDescriptor module, ProtoBuf.PackageFragment protoForNames, FqName fqName) {
        super(fqName, storageManager, module, null);
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(protoForNames, "protoForNames");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.protoForNames = protoForNames;
    }
}
