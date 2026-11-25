package org.jetbrains.kotlin.descriptors.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: PackageViewDescriptorFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \n2\u00020\u0001:\u0002\n\u000bJ \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory;", "", "compute", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "Companion", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageViewDescriptorFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    PackageViewDescriptor compute(ModuleDescriptorImpl module, FqName fqName, StorageManager storageManager);

    /* JADX INFO: compiled from: PackageViewDescriptorFactory.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory$Default;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory;", "()V", "compute", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements PackageViewDescriptorFactory {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.PackageViewDescriptorFactory
        public PackageViewDescriptor compute(ModuleDescriptorImpl module, FqName fqName, StorageManager storageManager) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            return new LazyPackageViewDescriptorImpl(module, fqName, storageManager);
        }
    }

    /* JADX INFO: compiled from: PackageViewDescriptorFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory$Companion;", "", "()V", "CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageViewDescriptorFactory;", "getCAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ModuleCapability<PackageViewDescriptorFactory> CAPABILITY = new ModuleCapability<>("PackageViewDescriptorFactory");

        private Companion() {
        }

        public final ModuleCapability<PackageViewDescriptorFactory> getCAPABILITY() {
            return CAPABILITY;
        }
    }
}
