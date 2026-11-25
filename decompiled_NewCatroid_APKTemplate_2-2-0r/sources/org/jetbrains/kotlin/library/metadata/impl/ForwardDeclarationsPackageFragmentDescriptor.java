package org.jetbrains.kotlin.library.metadata.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u0010\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/ForwardDeclarationsPackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "supertypeName", "Lorg/jetbrains/kotlin/name/Name;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "isExpect", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/ClassKind;Z)V", "memberScope", "org/jetbrains/kotlin/library/metadata/impl/ForwardDeclarationsPackageFragmentDescriptor$memberScope$1", "Lorg/jetbrains/kotlin/library/metadata/impl/ForwardDeclarationsPackageFragmentDescriptor$memberScope$1;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForwardDeclarationsPackageFragmentDescriptor extends PackageFragmentDescriptorImpl {
    private final ForwardDeclarationsPackageFragmentDescriptor$memberScope$1 memberScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForwardDeclarationsPackageFragmentDescriptor(StorageManager storageManager, ModuleDescriptor module, FqName fqName, Name supertypeName, ClassKind classKind, boolean z) {
        super(module, fqName);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(supertypeName, "supertypeName");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        this.memberScope = new ForwardDeclarationsPackageFragmentDescriptor$memberScope$1(storageManager, supertypeName, this, classKind, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public MemberScope getMemberScope() {
        return this.memberScope;
    }
}
