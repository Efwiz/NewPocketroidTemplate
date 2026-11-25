package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PackageViewDescriptorFactory;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplPackageViewDescriptorFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplPackageViewDescriptorFactory;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory;", "()V", "compute", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplPackageViewDescriptorFactory implements PackageViewDescriptorFactory {
    public static final ReplPackageViewDescriptorFactory INSTANCE = new ReplPackageViewDescriptorFactory();

    private ReplPackageViewDescriptorFactory() {
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PackageViewDescriptorFactory
    public PackageViewDescriptor compute(ModuleDescriptorImpl module, FqName fqName, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        return new ReplPackageViewDescriptor(module, fqName, storageManager);
    }
}
