package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.MutablePackageFragmentDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.EnumEntriesDeserializationSupportImpl;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: DeserializerForDecompilerBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0003H\u0004J\u0012\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(H\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/DeserializerForDecompilerBase;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/ResolverForDecompiler;", "directoryPackageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "deserializationComponents", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getDeserializationComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getDirectoryPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "enumEntriesDeserializationSupport", "Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "getEnumEntriesDeserializationSupport", "()Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "getPackageFragmentProvider", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "createDummyModule", "name", "", "createDummyPackageFragment", "Lorg/jetbrains/kotlin/descriptors/impl/MutablePackageFragmentDescriptor;", "fqName", "resolveTopLevelClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeserializerForDecompilerBase implements ResolverForDecompiler {
    private final FqName directoryPackageFqName;
    private final EnumEntriesDeserializationSupport enumEntriesDeserializationSupport;
    private final ModuleDescriptorImpl moduleDescriptor;
    private final PackageFragmentProvider packageFragmentProvider;
    private final StorageManager storageManager;

    protected abstract KotlinBuiltIns getBuiltIns();

    protected abstract DeserializationComponents getDeserializationComponents();

    public DeserializerForDecompilerBase(FqName directoryPackageFqName) {
        Intrinsics.checkNotNullParameter(directoryPackageFqName, "directoryPackageFqName");
        this.directoryPackageFqName = directoryPackageFqName;
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        this.storageManager = NO_LOCKS;
        ModuleDescriptorImpl moduleDescriptorImplCreateDummyModule = createDummyModule("module for building decompiled sources");
        this.moduleDescriptor = moduleDescriptorImplCreateDummyModule;
        PackageFragmentProviderOptimized packageFragmentProviderOptimized = new PackageFragmentProviderOptimized() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForDecompilerBase$packageFragmentProvider$1
            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
            public boolean isEmpty(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                return false;
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
            public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
                packageFragments.add(this.this$0.createDummyPackageFragment(fqName));
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
            public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                return CollectionsKt.listOf(this.this$0.createDummyPackageFragment(fqName));
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
            public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                throw new UnsupportedOperationException("This method is not supposed to be called.");
            }
        };
        this.packageFragmentProvider = packageFragmentProviderOptimized;
        this.enumEntriesDeserializationSupport = new EnumEntriesDeserializationSupportImpl(moduleDescriptorImplCreateDummyModule.getPlatform());
        moduleDescriptorImplCreateDummyModule.initialize(packageFragmentProviderOptimized);
        ModuleDescriptorImpl builtInsModule = moduleDescriptorImplCreateDummyModule.getBuiltIns().getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        moduleDescriptorImplCreateDummyModule.setDependencies(moduleDescriptorImplCreateDummyModule, builtInsModule);
    }

    public final FqName getDirectoryPackageFqName() {
        return this.directoryPackageFqName;
    }

    protected final StorageManager getStorageManager() {
        return this.storageManager;
    }

    protected final ModuleDescriptorImpl getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    protected final PackageFragmentProvider getPackageFragmentProvider() {
        return this.packageFragmentProvider;
    }

    protected final EnumEntriesDeserializationSupport getEnumEntriesDeserializationSupport() {
        return this.enumEntriesDeserializationSupport;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.psi.ResolverForDecompiler
    public ClassDescriptor resolveTopLevelClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return getDeserializationComponents().deserializeClass(classId);
    }

    protected final MutablePackageFragmentDescriptor createDummyPackageFragment(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return new MutablePackageFragmentDescriptor(this.moduleDescriptor, fqName);
    }

    private final ModuleDescriptorImpl createDummyModule(String name2) {
        Name nameSpecial = Name.special("<" + name2 + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return new ModuleDescriptorImpl(nameSpecial, this.storageManager, getBuiltIns(), null, null, null, 56, null);
    }
}
