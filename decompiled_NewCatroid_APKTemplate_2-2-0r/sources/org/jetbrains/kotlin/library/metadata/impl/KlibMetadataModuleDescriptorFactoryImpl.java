package org.jetbrains.kotlin.library.metadata.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.functions.FunctionInterfaceFactoryKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.ContractDeserializerImpl;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializerProtocol;
import org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;
import org.jetbrains.kotlin.platform.jvm.JvmPlatformKt;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataModuleDescriptorFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ6\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J<\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0016\u0010&\u001a\u00020'2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u001dJR\u0010)\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010%2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010,\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0016JF\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020'2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010,\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001a\u001a\u00020\u001bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/KlibMetadataModuleDescriptorFactoryImpl;", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "descriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "packageFragmentsFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "(Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;)V", "getDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "getFlexibleTypeDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "getPackageFragmentsFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "createCachedPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "byteArrays", "", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "createDescriptorOptionalBuiltIns", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "packageAccessHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "createForwardDeclarationHackPackagePartProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderImpl;", "module", "createPackageFragmentProvider", "packageFragmentNames", "", "compositePackageFragmentAddend", "initializePackageFragmentProvider", Constants.EXTRA_PROVIDER, "fragmentsToInitialize", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataModuleDescriptorFactoryImpl implements KlibMetadataModuleDescriptorFactory {
    private final KlibModuleDescriptorFactory descriptorFactory;
    private final FlexibleTypeDeserializer flexibleTypeDeserializer;
    private final KlibMetadataDeserializedPackageFragmentsFactory packageFragmentsFactory;

    /* JADX INFO: compiled from: KlibMetadataModuleDescriptorFactoryImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class EntriesMappings {
        public static final /* synthetic */ EnumEntries<NativeForwardDeclarationKind> entries$0 = EnumEntriesKt.enumEntries(NativeForwardDeclarationKind.values());
    }

    public KlibMetadataModuleDescriptorFactoryImpl(KlibModuleDescriptorFactory descriptorFactory, KlibMetadataDeserializedPackageFragmentsFactory packageFragmentsFactory, FlexibleTypeDeserializer flexibleTypeDeserializer) {
        Intrinsics.checkNotNullParameter(descriptorFactory, "descriptorFactory");
        Intrinsics.checkNotNullParameter(packageFragmentsFactory, "packageFragmentsFactory");
        Intrinsics.checkNotNullParameter(flexibleTypeDeserializer, "flexibleTypeDeserializer");
        this.descriptorFactory = descriptorFactory;
        this.packageFragmentsFactory = packageFragmentsFactory;
        this.flexibleTypeDeserializer = flexibleTypeDeserializer;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public KlibModuleDescriptorFactory getDescriptorFactory() {
        return this.descriptorFactory;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public KlibMetadataDeserializedPackageFragmentsFactory getPackageFragmentsFactory() {
        return this.packageFragmentsFactory;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public FlexibleTypeDeserializer getFlexibleTypeDeserializer() {
        return this.flexibleTypeDeserializer;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public ModuleDescriptorImpl createDescriptorOptionalBuiltIns(KotlinLibrary library, LanguageVersionSettings languageVersionSettings, StorageManager storageManager, KotlinBuiltIns builtIns, PackageAccessHandler packageAccessHandler, LookupTracker lookupTracker) {
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorAndNewBuiltIns$default;
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        KlibMetadataProtoBuf.Header moduleHeader = KlibMetadataDeserializationUtilsKt.parseModuleHeader(library.getModuleHeaderData());
        Name nameSpecial = Name.special(moduleHeader.getModuleName());
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        DeserializedKlibModuleOrigin deserializedKlibModuleOrigin = new DeserializedKlibModuleOrigin(library);
        if (builtIns != null) {
            moduleDescriptorImplCreateDescriptorAndNewBuiltIns$default = KlibModuleDescriptorFactory.createDescriptor$default(getDescriptorFactory(), nameSpecial, storageManager, builtIns, deserializedKlibModuleOrigin, null, 16, null);
        } else {
            moduleDescriptorImplCreateDescriptorAndNewBuiltIns$default = KlibModuleDescriptorFactory.createDescriptorAndNewBuiltIns$default(getDescriptorFactory(), nameSpecial, storageManager, deserializedKlibModuleOrigin, null, 8, null);
        }
        ModuleDescriptorImpl moduleDescriptorImpl = moduleDescriptorImplCreateDescriptorAndNewBuiltIns$default;
        CompilerDeserializationConfiguration compilerDeserializationConfiguration = new CompilerDeserializationConfiguration(languageVersionSettings);
        PackageFragmentProvider packageFragmentProviderFunctionInterfacePackageFragmentProvider = KlibMetadataModuleDescriptorFactoryImplKt.isStdlibModule(moduleDescriptorImpl) ? FunctionInterfaceFactoryKt.functionInterfacePackageFragmentProvider(storageManager, moduleDescriptorImpl) : null;
        ProtocolStringList packageFragmentNameList = moduleHeader.getPackageFragmentNameList();
        Intrinsics.checkNotNullExpressionValue(packageFragmentNameList, "getPackageFragmentNameList(...)");
        moduleDescriptorImpl.initialize(createPackageFragmentProvider(library, packageAccessHandler, packageFragmentNameList, storageManager, moduleDescriptorImpl, compilerDeserializationConfiguration, packageFragmentProviderFunctionInterfacePackageFragmentProvider, lookupTracker));
        return moduleDescriptorImpl;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public PackageFragmentProvider createCachedPackageFragmentProvider(List<byte[]> byteArrays, StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, LookupTracker lookupTracker) {
        Intrinsics.checkNotNullParameter(byteArrays, "byteArrays");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        List<KlibMetadataPackageFragment> listCreateCachedPackageFragments = getPackageFragmentsFactory().createCachedPackageFragments(byteArrays, moduleDescriptor, storageManager);
        return initializePackageFragmentProvider(new PackageFragmentProviderImpl(listCreateCachedPackageFragments), listCreateCachedPackageFragments, storageManager, moduleDescriptor, configuration, null, lookupTracker);
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory
    public PackageFragmentProvider createPackageFragmentProvider(KotlinLibrary library, PackageAccessHandler packageAccessHandler, List<String> packageFragmentNames, StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, PackageFragmentProvider compositePackageFragmentAddend, LookupTracker lookupTracker) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(packageFragmentNames, "packageFragmentNames");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        List<KlibMetadataPackageFragment> listCreateDeserializedPackageFragments = getPackageFragmentsFactory().createDeserializedPackageFragments(library, packageFragmentNames, moduleDescriptor, packageAccessHandler, storageManager, configuration);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = listCreateDeserializedPackageFragments.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((KlibMetadataPackageFragment) it.next()).getFqName());
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        ArrayList arrayList = new ArrayList();
        ArrayList<FqName> arrayList2 = new ArrayList();
        Iterator it2 = linkedHashSet2.iterator();
        while (it2.hasNext()) {
            FqName fqNameParentOrNull = FqNamesUtilKt.parentOrNull((FqName) it2.next());
            if (fqNameParentOrNull != null) {
                arrayList2.add(fqNameParentOrNull);
            }
        }
        for (FqName fqNameParent : arrayList2) {
            while (!fqNameParent.isRoot() && linkedHashSet2.add(fqNameParent)) {
                arrayList.add(new EmptyPackageFragmentDescriptor(moduleDescriptor, fqNameParent));
                fqNameParent = fqNameParent.parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            }
        }
        return initializePackageFragmentProvider(new PackageFragmentProviderImpl(CollectionsKt.plus((Collection) listCreateDeserializedPackageFragments, (Iterable) arrayList)), listCreateDeserializedPackageFragments, storageManager, moduleDescriptor, configuration, compositePackageFragmentAddend, lookupTracker);
    }

    public final PackageFragmentProvider initializePackageFragmentProvider(PackageFragmentProviderImpl provider, List<? extends DeserializedPackageFragment> fragmentsToInitialize, StorageManager storageManager, final ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, PackageFragmentProvider compositePackageFragmentAddend, LookupTracker lookupTracker) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(fragmentsToInitialize, "fragmentsToInitialize");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        NotFoundClasses notFoundClasses = new NotFoundClasses(storageManager, moduleDescriptor);
        AnnotationAndConstantLoaderImpl annotationAndConstantLoaderImpl = new AnnotationAndConstantLoaderImpl(moduleDescriptor, notFoundClasses, KlibMetadataSerializerProtocol.INSTANCE);
        EnumEntriesDeserializationSupport enumEntriesDeserializationSupport = new EnumEntriesDeserializationSupport() { // from class: org.jetbrains.kotlin.library.metadata.impl.KlibMetadataModuleDescriptorFactoryImpl$initializePackageFragmentProvider$enumEntriesDeserializationSupport$1
            @Override // org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport
            public Boolean canSynthesizeEnumEntries() {
                return Boolean.valueOf(JvmPlatformKt.isJvm(moduleDescriptor.getPlatform()));
            }
        };
        PackageFragmentProviderImpl packageFragmentProviderImpl = provider;
        LocalClassifierTypeSettings.Default r16 = LocalClassifierTypeSettings.Default.INSTANCE;
        ErrorReporter DO_NOTHING = ErrorReporter.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
        DeserializationComponents deserializationComponents = new DeserializationComponents(storageManager, moduleDescriptor, configuration, new DeserializedClassDataFinder(packageFragmentProviderImpl), annotationAndConstantLoaderImpl, packageFragmentProviderImpl, r16, DO_NOTHING, lookupTracker, getFlexibleTypeDeserializer(), CollectionsKt.emptyList(), notFoundClasses, new ContractDeserializerImpl(configuration, storageManager), null, null, KlibMetadataSerializerProtocol.INSTANCE.getExtensionRegistry(), null, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), null, enumEntriesDeserializationSupport, 352256, null);
        Iterator<T> it = fragmentsToInitialize.iterator();
        while (it.hasNext()) {
            ((DeserializedPackageFragment) it.next()).initialize(deserializationComponents);
        }
        return compositePackageFragmentAddend != null ? new CompositePackageFragmentProvider(CollectionsKt.listOf((Object[]) new PackageFragmentProvider[]{compositePackageFragmentAddend, provider}), "CompositeProvider@KlibMetadataModuleDescriptorFactory for " + moduleDescriptor) : packageFragmentProviderImpl;
    }

    private static final ForwardDeclarationsPackageFragmentDescriptor createForwardDeclarationHackPackagePartProvider$createPackage(StorageManager storageManager, ModuleDescriptorImpl moduleDescriptorImpl, NativeForwardDeclarationKind nativeForwardDeclarationKind) {
        return new ForwardDeclarationsPackageFragmentDescriptor(storageManager, moduleDescriptorImpl, nativeForwardDeclarationKind.getPackageFqName(), nativeForwardDeclarationKind.getSuperClassName(), nativeForwardDeclarationKind.getClassKind(), true);
    }

    public final PackageFragmentProviderImpl createForwardDeclarationHackPackagePartProvider(StorageManager storageManager, ModuleDescriptorImpl module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        EnumEntries<NativeForwardDeclarationKind> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enumEntries, 10));
        Iterator<NativeForwardDeclarationKind> it = enumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(createForwardDeclarationHackPackagePartProvider$createPackage(storageManager, module, it.next()));
        }
        return new PackageFragmentProviderImpl(arrayList);
    }
}
