package org.jetbrains.kotlin.serialization.deserialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DefaultTypeAttributeTranslator;
import org.jetbrains.kotlin.types.TypeAttributeTranslator;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BË\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e\u0012\b\b\u0002\u0010\u001f\u001a\u00020 \u0012\b\b\u0002\u0010!\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020$\u0012\b\b\u0002\u0010%\u001a\u00020&\u0012\u0006\u0010'\u001a\u00020(\u0012\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*\u0012\b\b\u0002\u0010,\u001a\u00020-¢\u0006\u0002\u0010.J8\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020f2\b\u0010g\u001a\u0004\u0018\u00010hJ\u0010\u0010i\u001a\u0004\u0018\u00010j2\u0006\u0010k\u001a\u00020lR\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u000b¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u000206¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010,\u001a\u00020-¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\bK\u0010LR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bW\u0010XR\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*¢\u0006\b\n\u0000\u001a\u0004\bY\u0010Z¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "annotationAndConstantLoader", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoader;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "localClassifierTypeSettings", "Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;", "errorReporter", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "fictitiousClassDescriptorFactories", "", "Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "contractDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "platformDependentDeclarationFilter", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "extensionRegistryLite", "Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "typeAttributeTranslators", "", "Lorg/jetbrains/kotlin/types/TypeAttributeTranslator;", "enumEntriesDeserializationSupport", "Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoader;Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Ljava/util/List;Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;)V", "getAdditionalClassPartsProvider", "()Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "getAnnotationAndConstantLoader", "()Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoader;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "classDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer;", "getClassDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer;", "getConfiguration", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "getContractDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "getEnumEntriesDeserializationSupport", "()Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "getErrorReporter", "()Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "getExtensionRegistryLite", "()Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "getFictitiousClassDescriptorFactories", "()Ljava/lang/Iterable;", "getFlexibleTypeDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "getKotlinTypeChecker", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getLocalClassifierTypeSettings", "()Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;", "getLookupTracker", "()Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getNotFoundClasses", "()Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "getPackageFragmentProvider", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "getPlatformDependentDeclarationFilter", "()Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getTypeAttributeTranslators", "()Ljava/util/List;", "createContext", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationContext;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "deserializeClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializationComponents {
    private final AdditionalClassPartsProvider additionalClassPartsProvider;
    private final AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> annotationAndConstantLoader;
    private final ClassDataFinder classDataFinder;
    private final ClassDeserializer classDeserializer;
    private final DeserializationConfiguration configuration;
    private final ContractDeserializer contractDeserializer;
    private final EnumEntriesDeserializationSupport enumEntriesDeserializationSupport;
    private final ErrorReporter errorReporter;
    private final ExtensionRegistryLite extensionRegistryLite;
    private final Iterable<ClassDescriptorFactory> fictitiousClassDescriptorFactories;
    private final FlexibleTypeDeserializer flexibleTypeDeserializer;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private final LocalClassifierTypeSettings localClassifierTypeSettings;
    private final LookupTracker lookupTracker;
    private final ModuleDescriptor moduleDescriptor;
    private final NotFoundClasses notFoundClasses;
    private final PackageFragmentProvider packageFragmentProvider;
    private final PlatformDependentDeclarationFilter platformDependentDeclarationFilter;
    private final SamConversionResolver samConversionResolver;
    private final StorageManager storageManager;
    private final List<TypeAttributeTranslator> typeAttributeTranslators;

    /* JADX WARN: Multi-variable type inference failed */
    public DeserializationComponents(StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, ClassDataFinder classDataFinder, AnnotationAndConstantLoader<? extends AnnotationDescriptor, ? extends ConstantValue<?>> annotationAndConstantLoader, PackageFragmentProvider packageFragmentProvider, LocalClassifierTypeSettings localClassifierTypeSettings, ErrorReporter errorReporter, LookupTracker lookupTracker, FlexibleTypeDeserializer flexibleTypeDeserializer, Iterable<? extends ClassDescriptorFactory> fictitiousClassDescriptorFactories, NotFoundClasses notFoundClasses, ContractDeserializer contractDeserializer, AdditionalClassPartsProvider additionalClassPartsProvider, PlatformDependentDeclarationFilter platformDependentDeclarationFilter, ExtensionRegistryLite extensionRegistryLite, NewKotlinTypeChecker kotlinTypeChecker, SamConversionResolver samConversionResolver, List<? extends TypeAttributeTranslator> typeAttributeTranslators, EnumEntriesDeserializationSupport enumEntriesDeserializationSupport) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(classDataFinder, "classDataFinder");
        Intrinsics.checkNotNullParameter(annotationAndConstantLoader, "annotationAndConstantLoader");
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        Intrinsics.checkNotNullParameter(localClassifierTypeSettings, "localClassifierTypeSettings");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(flexibleTypeDeserializer, "flexibleTypeDeserializer");
        Intrinsics.checkNotNullParameter(fictitiousClassDescriptorFactories, "fictitiousClassDescriptorFactories");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(contractDeserializer, "contractDeserializer");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Intrinsics.checkNotNullParameter(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        Intrinsics.checkNotNullParameter(extensionRegistryLite, "extensionRegistryLite");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(typeAttributeTranslators, "typeAttributeTranslators");
        Intrinsics.checkNotNullParameter(enumEntriesDeserializationSupport, "enumEntriesDeserializationSupport");
        this.storageManager = storageManager;
        this.moduleDescriptor = moduleDescriptor;
        this.configuration = configuration;
        this.classDataFinder = classDataFinder;
        this.annotationAndConstantLoader = annotationAndConstantLoader;
        this.packageFragmentProvider = packageFragmentProvider;
        this.localClassifierTypeSettings = localClassifierTypeSettings;
        this.errorReporter = errorReporter;
        this.lookupTracker = lookupTracker;
        this.flexibleTypeDeserializer = flexibleTypeDeserializer;
        this.fictitiousClassDescriptorFactories = fictitiousClassDescriptorFactories;
        this.notFoundClasses = notFoundClasses;
        this.contractDeserializer = contractDeserializer;
        this.additionalClassPartsProvider = additionalClassPartsProvider;
        this.platformDependentDeclarationFilter = platformDependentDeclarationFilter;
        this.extensionRegistryLite = extensionRegistryLite;
        this.kotlinTypeChecker = kotlinTypeChecker;
        this.samConversionResolver = samConversionResolver;
        this.typeAttributeTranslators = typeAttributeTranslators;
        this.enumEntriesDeserializationSupport = enumEntriesDeserializationSupport;
        this.classDeserializer = new ClassDeserializer(this);
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final DeserializationConfiguration getConfiguration() {
        return this.configuration;
    }

    public final ClassDataFinder getClassDataFinder() {
        return this.classDataFinder;
    }

    public final AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> getAnnotationAndConstantLoader() {
        return this.annotationAndConstantLoader;
    }

    public final PackageFragmentProvider getPackageFragmentProvider() {
        return this.packageFragmentProvider;
    }

    public final LocalClassifierTypeSettings getLocalClassifierTypeSettings() {
        return this.localClassifierTypeSettings;
    }

    public final ErrorReporter getErrorReporter() {
        return this.errorReporter;
    }

    public final LookupTracker getLookupTracker() {
        return this.lookupTracker;
    }

    public final FlexibleTypeDeserializer getFlexibleTypeDeserializer() {
        return this.flexibleTypeDeserializer;
    }

    public final Iterable<ClassDescriptorFactory> getFictitiousClassDescriptorFactories() {
        return this.fictitiousClassDescriptorFactories;
    }

    public final NotFoundClasses getNotFoundClasses() {
        return this.notFoundClasses;
    }

    public final ContractDeserializer getContractDeserializer() {
        return this.contractDeserializer;
    }

    public /* synthetic */ DeserializationComponents(StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration deserializationConfiguration, ClassDataFinder classDataFinder, AnnotationAndConstantLoader annotationAndConstantLoader, PackageFragmentProvider packageFragmentProvider, LocalClassifierTypeSettings localClassifierTypeSettings, ErrorReporter errorReporter, LookupTracker lookupTracker, FlexibleTypeDeserializer flexibleTypeDeserializer, Iterable iterable, NotFoundClasses notFoundClasses, ContractDeserializer contractDeserializer, AdditionalClassPartsProvider additionalClassPartsProvider, PlatformDependentDeclarationFilter platformDependentDeclarationFilter, ExtensionRegistryLite extensionRegistryLite, NewKotlinTypeChecker newKotlinTypeChecker, SamConversionResolver samConversionResolver, List list, EnumEntriesDeserializationSupport enumEntriesDeserializationSupport, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(storageManager, moduleDescriptor, deserializationConfiguration, classDataFinder, annotationAndConstantLoader, packageFragmentProvider, localClassifierTypeSettings, errorReporter, lookupTracker, flexibleTypeDeserializer, iterable, notFoundClasses, contractDeserializer, (i & 8192) != 0 ? AdditionalClassPartsProvider.None.INSTANCE : additionalClassPartsProvider, (i & 16384) != 0 ? PlatformDependentDeclarationFilter.All.INSTANCE : platformDependentDeclarationFilter, extensionRegistryLite, (65536 & i) != 0 ? NewKotlinTypeChecker.INSTANCE.getDefault() : newKotlinTypeChecker, samConversionResolver, (262144 & i) != 0 ? CollectionsKt.listOf(DefaultTypeAttributeTranslator.INSTANCE) : list, (i & 524288) != 0 ? EnumEntriesDeserializationSupport.Default.INSTANCE : enumEntriesDeserializationSupport);
    }

    public final AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        return this.additionalClassPartsProvider;
    }

    public final PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        return this.platformDependentDeclarationFilter;
    }

    public final ExtensionRegistryLite getExtensionRegistryLite() {
        return this.extensionRegistryLite;
    }

    public final NewKotlinTypeChecker getKotlinTypeChecker() {
        return this.kotlinTypeChecker;
    }

    public final SamConversionResolver getSamConversionResolver() {
        return this.samConversionResolver;
    }

    public final List<TypeAttributeTranslator> getTypeAttributeTranslators() {
        return this.typeAttributeTranslators;
    }

    public final EnumEntriesDeserializationSupport getEnumEntriesDeserializationSupport() {
        return this.enumEntriesDeserializationSupport;
    }

    public final ClassDeserializer getClassDeserializer() {
        return this.classDeserializer;
    }

    public final ClassDescriptor deserializeClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return ClassDeserializer.deserializeClass$default(this.classDeserializer, classId, null, 2, null);
    }

    public final DeserializationContext createContext(PackageFragmentDescriptor descriptor, NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, BinaryVersion metadataVersion, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        return new DeserializationContext(this, nameResolver, descriptor, typeTable, versionRequirementTable, metadataVersion, containerSource, null, CollectionsKt.emptyList());
    }
}
