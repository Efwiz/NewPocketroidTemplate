package org.jetbrains.kotlin.serialization.deserialization;

import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: AbstractDeserializedPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\u001dH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001b\u001a\u00020\u0013H$J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00140!2\u0006\u0010\u001b\u001a\u00020\u0013H\u0017J*\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00130#2\u0006\u0010\u001b\u001a\u00020\u00132\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'0%H\u0016J\u0010\u0010(\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u0013H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/AbstractDeserializedPackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "finder", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "setComponents", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;)V", "getFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "fragments", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "collectPackageFragments", "", "fqName", "packageFragments", "", "findPackage", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "getPackageFragments", "", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "isEmpty", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDeserializedPackageFragmentProvider implements PackageFragmentProviderOptimized {
    protected DeserializationComponents components;
    private final KotlinMetadataFinder finder;
    private final MemoizedFunctionToNullable<FqName, PackageFragmentDescriptor> fragments;
    private final ModuleDescriptor moduleDescriptor;
    private final StorageManager storageManager;

    protected abstract DeserializedPackageFragment findPackage(FqName fqName);

    public AbstractDeserializedPackageFragmentProvider(StorageManager storageManager, KotlinMetadataFinder finder, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.storageManager = storageManager;
        this.finder = finder;
        this.moduleDescriptor = moduleDescriptor;
        this.fragments = storageManager.createMemoizedFunctionWithNullableValues(new Function1<FqName, PackageFragmentDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.AbstractDeserializedPackageFragmentProvider$fragments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PackageFragmentDescriptor invoke(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                DeserializedPackageFragment deserializedPackageFragmentFindPackage = this.this$0.findPackage(fqName);
                if (deserializedPackageFragmentFindPackage != null) {
                    deserializedPackageFragmentFindPackage.initialize(this.this$0.getComponents());
                } else {
                    deserializedPackageFragmentFindPackage = null;
                }
                return deserializedPackageFragmentFindPackage;
            }
        });
    }

    protected final StorageManager getStorageManager() {
        return this.storageManager;
    }

    protected final KotlinMetadataFinder getFinder() {
        return this.finder;
    }

    protected final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    protected final DeserializationComponents getComponents() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents != null) {
            return deserializationComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    protected final void setComponents(DeserializationComponents deserializationComponents) {
        Intrinsics.checkNotNullParameter(deserializationComponents, "<set-?>");
        this.components = deserializationComponents;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        CollectionsKt.addIfNotNull(packageFragments, this.fragments.invoke(fqName));
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        DeclarationDescriptor declarationDescriptorFindPackage;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (this.fragments.isComputed(fqName)) {
            declarationDescriptorFindPackage = (PackageFragmentDescriptor) this.fragments.invoke(fqName);
        } else {
            declarationDescriptorFindPackage = findPackage(fqName);
        }
        return declarationDescriptorFindPackage == null;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return kotlin.collections.CollectionsKt.listOfNotNull(this.fragments.invoke(fqName));
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return SetsKt.emptySet();
    }
}
