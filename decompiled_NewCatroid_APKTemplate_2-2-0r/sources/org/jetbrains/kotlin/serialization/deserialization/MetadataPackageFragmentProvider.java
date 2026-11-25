package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;

/* JADX INFO: compiled from: MetadataPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPackageFragmentProvider;", "Lorg/jetbrains/kotlin/serialization/deserialization/AbstractDeserializedPackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "finder", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "metadataPartProvider", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "contractDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "typeAttributeTranslators", "Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;)V", "findPackage", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataPackageFragmentProvider extends AbstractDeserializedPackageFragmentProvider {
    private final MetadataPartProvider metadataPartProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataPackageFragmentProvider(StorageManager storageManager, KotlinMetadataFinder finder, ModuleDescriptor moduleDescriptor, NotFoundClasses notFoundClasses, MetadataPartProvider metadataPartProvider, ContractDeserializer contractDeserializer, NewKotlinTypeChecker kotlinTypeChecker, SamConversionResolver samConversionResolver, TypeAttributeTranslators typeAttributeTranslators) {
        super(storageManager, finder, moduleDescriptor);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(metadataPartProvider, "metadataPartProvider");
        Intrinsics.checkNotNullParameter(contractDeserializer, "contractDeserializer");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(typeAttributeTranslators, "typeAttributeTranslators");
        this.metadataPartProvider = metadataPartProvider;
        DeserializationConfiguration.Default r4 = DeserializationConfiguration.Default.INSTANCE;
        MetadataPackageFragmentProvider metadataPackageFragmentProvider = this;
        DeserializedClassDataFinder deserializedClassDataFinder = new DeserializedClassDataFinder(metadataPackageFragmentProvider);
        AnnotationAndConstantLoaderImpl annotationAndConstantLoaderImpl = new AnnotationAndConstantLoaderImpl(moduleDescriptor, notFoundClasses, BuiltInSerializerProtocol.INSTANCE);
        LocalClassifierTypeSettings.Default r8 = LocalClassifierTypeSettings.Default.INSTANCE;
        ErrorReporter DO_NOTHING = ErrorReporter.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
        setComponents(new DeserializationComponents(storageManager, moduleDescriptor, r4, deserializedClassDataFinder, annotationAndConstantLoaderImpl, metadataPackageFragmentProvider, r8, DO_NOTHING, LookupTracker.DO_NOTHING.INSTANCE, FlexibleTypeDeserializer.ThrowException.INSTANCE, CollectionsKt.emptyList(), notFoundClasses, contractDeserializer, AdditionalClassPartsProvider.None.INSTANCE, PlatformDependentDeclarationFilter.All.INSTANCE, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry(), kotlinTypeChecker, samConversionResolver, typeAttributeTranslators.getTranslators(), null, 524288, null));
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AbstractDeserializedPackageFragmentProvider
    protected DeserializedPackageFragment findPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getFinder().hasMetadataPackage(fqName)) {
            return new MetadataPackageFragment(fqName, getStorageManager(), getModuleDescriptor(), this.metadataPartProvider, getFinder());
        }
        return null;
    }
}
