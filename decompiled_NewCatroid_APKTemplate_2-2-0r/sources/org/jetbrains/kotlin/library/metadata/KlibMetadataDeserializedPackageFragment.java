package org.jetbrains.kotlin.library.metadata;

import java.lang.ref.SoftReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010\u0019\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u0018X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragment;", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "packageAccessHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "partName", "", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "protoForNames", "getProtoForNames", "protoForNamesStorage", "Ljava/lang/ref/SoftReference;", "ensureStorage", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KlibMetadataDeserializedPackageFragment extends KlibMetadataPackageFragment {
    private final KotlinLibrary library;
    private final PackageAccessHandler packageAccessHandler;
    private final String partName;
    private SoftReference<ProtoBuf.PackageFragment> protoForNamesStorage;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataDeserializedPackageFragment(FqName fqName, KotlinLibrary library, PackageAccessHandler packageAccessHandler, StorageManager storageManager, ModuleDescriptor module, String partName, DeserializedContainerSource containerSource) {
        super(fqName, storageManager, module, containerSource);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(partName, "partName");
        Intrinsics.checkNotNullParameter(containerSource, "containerSource");
        this.library = library;
        this.packageAccessHandler = packageAccessHandler;
        this.partName = partName;
        this.protoForNamesStorage = new SoftReference<>(null);
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment
    public ProtoBuf.PackageFragment getProtoForNames() {
        return ensureStorage();
    }

    private final ProtoBuf.PackageFragment ensureStorage() {
        ProtoBuf.PackageFragment packageFragment = this.protoForNamesStorage.get();
        if (packageFragment != null) {
            return packageFragment;
        }
        SimplePackageAccessHandler simplePackageAccessHandler = this.packageAccessHandler;
        if (simplePackageAccessHandler == null) {
            simplePackageAccessHandler = SimplePackageAccessHandler.INSTANCE;
        }
        KotlinLibrary kotlinLibrary = this.library;
        String strAsString = getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        ProtoBuf.PackageFragment packageFragmentLoadPackageFragment = simplePackageAccessHandler.loadPackageFragment(kotlinLibrary, strAsString, this.partName);
        this.protoForNamesStorage = new SoftReference<>(packageFragmentLoadPackageFragment);
        return packageFragmentLoadPackageFragment;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment
    public ProtoBuf.PackageFragment getProto() {
        PackageAccessHandler packageAccessHandler = this.packageAccessHandler;
        if (packageAccessHandler != null) {
            KotlinLibrary kotlinLibrary = this.library;
            String strAsString = getFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            packageAccessHandler.markNeededForLink(kotlinLibrary, strAsString);
        }
        return getProtoForNames();
    }
}
