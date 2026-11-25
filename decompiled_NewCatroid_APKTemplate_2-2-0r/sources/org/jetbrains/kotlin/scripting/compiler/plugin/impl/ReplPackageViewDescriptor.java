package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplPackageViewDescriptorFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0016\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplPackageViewDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/LazyPackageViewDescriptorImpl;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "cachedFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "fragments", "getFragments", "()Ljava/util/List;", "isEmpty", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplPackageViewDescriptor extends LazyPackageViewDescriptorImpl {
    private List<? extends PackageFragmentDescriptor> cachedFragments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReplPackageViewDescriptor(ModuleDescriptorImpl module, FqName fqName, StorageManager storageManager) {
        super(module, fqName, storageManager);
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl, org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public List<PackageFragmentDescriptor> getFragments() {
        List list = this.cachedFragments;
        if (list != null) {
            return list;
        }
        List<PackageFragmentDescriptor> listPackageFragments = PackageFragmentProviderKt.packageFragments(getModule().getPackageFragmentProvider(), getFqName());
        if (!listPackageFragments.isEmpty()) {
            this.cachedFragments = listPackageFragments;
        }
        return listPackageFragments;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.LazyPackageViewDescriptorImpl, org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public boolean isEmpty() {
        List<? extends PackageFragmentDescriptor> list = this.cachedFragments;
        return list != null ? list.isEmpty() : PackageFragmentProviderKt.isEmpty(getModule().getPackageFragmentProvider(), getFqName());
    }
}
