package org.jetbrains.kotlin.cli.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer;
import org.jetbrains.kotlin.analyzer.common.CommonPlatformAnalyzerServices;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRoot;
import org.jetbrains.kotlin.cli.jvm.config.K2MetadataConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SingleFileResolveKt;
import org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.impl.KlibMetadataModuleDescriptorFactoryImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.jvm.JvmCompilerDeserializationConfiguration;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: K2MetadataKlibSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u00012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\rH\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001aH\u0002J\u0012\u0010.\u001a\u0004\u0018\u00010,2\u0006\u0010*\u001a\u00020\rH\u0016J\u0018\u0010/\u001a\u0002002\u0006\u0010*\u001a\u00020\r2\u0006\u00101\u001a\u00020\u001dH\u0016R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u000fR\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001d0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000fR\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/KlibMetadataDependencyContainer;", "Lorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/DefaultBuiltIns;", "friendModuleInfos", "", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getFriendModuleInfos", "()Ljava/util/List;", "friendPaths", "", "", "klibMetadataModuleDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/impl/KlibMetadataModuleDescriptorFactoryImpl;", "getKlibMetadataModuleDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/impl/KlibMetadataModuleDescriptorFactoryImpl;", "klibMetadataModuleDescriptorFactory$delegate", "Lkotlin/Lazy;", "kotlinLibraries", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "moduleDescriptorsForKotlinLibraries", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "moduleInfos", "getModuleInfos", "moduleInfosImpl", "Lorg/jetbrains/kotlin/cli/metadata/KlibMetadataDependencyContainer$KlibModuleInfo;", "mutableDependenciesForAllModuleDescriptors", "", "mutableDependenciesForAllModules", "refinesModuleInfos", "getRefinesModuleInfos", "refinesPaths", "moduleDescriptorForModuleInfo", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleInfo", "packageFragmentProviderForKotlinLibrary", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "library", "packageFragmentProviderForModuleInfo", "registerDependencyForAllModules", "", "descriptorForModule", "KlibModuleInfo", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KlibMetadataDependencyContainer implements CommonDependenciesContainer {
    private final CompilerConfiguration configuration;
    private final List<ModuleInfo> friendModuleInfos;
    private final Set<String> friendPaths;

    /* JADX INFO: renamed from: klibMetadataModuleDescriptorFactory$delegate, reason: from kotlin metadata */
    private final Lazy klibMetadataModuleDescriptorFactory;
    private final List<KotlinLibrary> kotlinLibraries;
    private final Map<KotlinLibrary, ModuleDescriptorImpl> moduleDescriptorsForKotlinLibraries;
    private final List<KlibModuleInfo> moduleInfosImpl;
    private final List<ModuleDescriptorImpl> mutableDependenciesForAllModuleDescriptors;
    private final List<ModuleInfo> mutableDependenciesForAllModules;
    private final List<ModuleInfo> refinesModuleInfos;
    private final Set<String> refinesPaths;
    private final StorageManager storageManager;

    public KlibMetadataDependencyContainer(CompilerConfiguration configuration, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.configuration = configuration;
        this.storageManager = storageManager;
        List list = this.configuration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof JvmClasspathRoot) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JvmContentRoot) it.next()).getFile());
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayList3) {
            File file = (File) obj2;
            if (Intrinsics.areEqual(FilesKt.getExtension(file), "klib") || file.isDirectory()) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            String absolutePath = ((File) it2.next()).getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            arrayList6.add(SingleFileResolveKt.resolveSingleFileKlib$default(new org.jetbrains.kotlin.konan.file.File(absolutePath), null, null, 6, null));
        }
        ArrayList arrayList7 = arrayList6;
        this.kotlinLibraries = arrayList7;
        List list2 = (List) this.configuration.get(K2MetadataConfigurationKeys.INSTANCE.getFRIEND_PATHS());
        this.friendPaths = CollectionsKt.toSet(list2 == null ? CollectionsKt.emptyList() : list2);
        List list3 = (List) this.configuration.get(K2MetadataConfigurationKeys.INSTANCE.getREFINES_PATHS());
        this.refinesPaths = CollectionsKt.toSet(list3 == null ? CollectionsKt.emptyList() : list3);
        ArrayList arrayList8 = new ArrayList();
        ModuleDescriptorImpl builtInsModule = getBuiltIns().getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        arrayList8.add(builtInsModule);
        this.mutableDependenciesForAllModuleDescriptors = arrayList8;
        this.mutableDependenciesForAllModules = new ArrayList();
        Map<KotlinLibrary, ModuleDescriptorImpl> mapKeysToMap = org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(arrayList7, new Function1<KotlinLibrary, ModuleDescriptorImpl>() { // from class: org.jetbrains.kotlin.cli.metadata.KlibMetadataDependencyContainer$moduleDescriptorsForKotlinLibraries$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ModuleDescriptorImpl invoke(KotlinLibrary library) {
                Intrinsics.checkNotNullParameter(library, "library");
                Name nameSpecial = Name.special(KlibMetadataDeserializationUtilsKt.parseModuleHeader(library.getModuleHeaderData()).getModuleName());
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                return KlibModuleDescriptorFactory.createDescriptor$default(K2MetadataKlibSerializerKt.MetadataFactories.getDefaultDescriptorFactory(), nameSpecial, this.this$0.storageManager, this.this$0.getBuiltIns(), new DeserializedKlibModuleOrigin(library), null, 16, null);
            }
        });
        Collection<ModuleDescriptorImpl> collectionValues = mapKeysToMap.values();
        Iterator<T> it3 = collectionValues.iterator();
        while (it3.hasNext()) {
            ((ModuleDescriptorImpl) it3.next()).setDependencies(this.mutableDependenciesForAllModuleDescriptors);
        }
        this.mutableDependenciesForAllModuleDescriptors.addAll(collectionValues);
        this.moduleDescriptorsForKotlinLibraries = mapKeysToMap;
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList(mapKeysToMap.size());
        for (Map.Entry<KotlinLibrary, ModuleDescriptorImpl> entry : mapKeysToMap.entrySet()) {
            KotlinLibrary key = entry.getKey();
            Name name2 = entry.getValue().getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList10.add(new KlibModuleInfo(name2, key, this.mutableDependenciesForAllModules));
        }
        arrayList9.addAll(arrayList10);
        this.mutableDependenciesForAllModules.addAll(arrayList9);
        this.moduleInfosImpl = arrayList9;
        ArrayList arrayList11 = new ArrayList();
        for (Object obj3 : arrayList9) {
            if (this.friendPaths.contains(((KlibModuleInfo) obj3).getKotlinLibrary().getLibraryFile().getAbsolutePath())) {
                arrayList11.add(obj3);
            }
        }
        this.friendModuleInfos = arrayList11;
        List<KlibModuleInfo> list4 = this.moduleInfosImpl;
        ArrayList arrayList12 = new ArrayList();
        for (Object obj4 : list4) {
            if (this.refinesPaths.contains(((KlibModuleInfo) obj4).getKotlinLibrary().getLibraryFile().getAbsolutePath())) {
                arrayList12.add(obj4);
            }
        }
        this.refinesModuleInfos = arrayList12;
        this.klibMetadataModuleDescriptorFactory = LazyKt.lazy(new Function0<KlibMetadataModuleDescriptorFactoryImpl>() { // from class: org.jetbrains.kotlin.cli.metadata.KlibMetadataDependencyContainer$klibMetadataModuleDescriptorFactory$2
            @Override // kotlin.jvm.functions.Function0
            public final KlibMetadataModuleDescriptorFactoryImpl invoke() {
                return new KlibMetadataModuleDescriptorFactoryImpl(K2MetadataKlibSerializerKt.MetadataFactories.getDefaultDescriptorFactory(), K2MetadataKlibSerializerKt.MetadataFactories.getDefaultPackageFragmentsFactory(), K2MetadataKlibSerializerKt.MetadataFactories.getFlexibleTypeDeserializer());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DefaultBuiltIns getBuiltIns() {
        return DefaultBuiltIns.INSTANCE.getInstance();
    }

    /* JADX INFO: compiled from: K2MetadataKlibSerializer.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/KlibMetadataDependencyContainer$KlibModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "name", "Lorg/jetbrains/kotlin/name/Name;", "kotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "dependOnModules", "", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/library/KotlinLibrary;Ljava/util/List;)V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getKotlinLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "dependencies", "dependencyOnBuiltIns", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class KlibModuleInfo implements ModuleInfo {
        private final List<ModuleInfo> dependOnModules;
        private final KotlinLibrary kotlinLibrary;
        private final Name name;

        /* JADX WARN: Multi-variable type inference failed */
        public KlibModuleInfo(Name name2, KotlinLibrary kotlinLibrary, List<? extends ModuleInfo> dependOnModules) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(kotlinLibrary, "kotlinLibrary");
            Intrinsics.checkNotNullParameter(dependOnModules, "dependOnModules");
            this.name = name2;
            this.kotlinLibrary = kotlinLibrary;
            this.dependOnModules = dependOnModules;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public Name getName() {
            return this.name;
        }

        public final KotlinLibrary getKotlinLibrary() {
            return this.kotlinLibrary;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public List<ModuleInfo> dependencies() {
            return this.dependOnModules;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public ModuleInfo.DependencyOnBuiltIns dependencyOnBuiltIns() {
            return ModuleInfo.DependencyOnBuiltIns.LAST;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public TargetPlatform getPlatform() {
            return CommonPlatforms.INSTANCE.getDefaultCommonPlatform();
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public PlatformDependentAnalyzerServices getAnalyzerServices() {
            return CommonPlatformAnalyzerServices.INSTANCE;
        }
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public List<ModuleInfo> getModuleInfos() {
        return this.moduleInfosImpl;
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public List<ModuleInfo> getFriendModuleInfos() {
        return this.friendModuleInfos;
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public List<ModuleInfo> getRefinesModuleInfos() {
        return this.refinesModuleInfos;
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public ModuleDescriptor moduleDescriptorForModuleInfo(ModuleInfo moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        if (!getModuleInfos().contains(moduleInfo)) {
            throw new IllegalStateException(("Unknown module info " + moduleInfo).toString());
        }
        KlibModuleInfo klibModuleInfo = (KlibModuleInfo) moduleInfo;
        packageFragmentProviderForModuleInfo(moduleInfo);
        return (ModuleDescriptor) MapsKt.getValue(this.moduleDescriptorsForKotlinLibraries, klibModuleInfo.getKotlinLibrary());
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public void registerDependencyForAllModules(ModuleInfo moduleInfo, ModuleDescriptorImpl descriptorForModule) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        Intrinsics.checkNotNullParameter(descriptorForModule, "descriptorForModule");
        this.mutableDependenciesForAllModules.add(moduleInfo);
        this.mutableDependenciesForAllModuleDescriptors.add(descriptorForModule);
    }

    @Override // org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer
    public PackageFragmentProvider packageFragmentProviderForModuleInfo(ModuleInfo moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        if (getModuleInfos().contains(moduleInfo)) {
            return packageFragmentProviderForKotlinLibrary(((KlibModuleInfo) moduleInfo).getKotlinLibrary());
        }
        return null;
    }

    private final KlibMetadataModuleDescriptorFactoryImpl getKlibMetadataModuleDescriptorFactory() {
        return (KlibMetadataModuleDescriptorFactoryImpl) this.klibMetadataModuleDescriptorFactory.getValue();
    }

    private final PackageFragmentProvider packageFragmentProviderForKotlinLibrary(KotlinLibrary library) {
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration);
        ModuleDescriptorImpl moduleDescriptorImpl = (ModuleDescriptorImpl) MapsKt.getValue(this.moduleDescriptorsForKotlinLibraries, library);
        ProtocolStringList packageFragmentNameList = KlibMetadataDeserializationUtilsKt.parseModuleHeader(library.getModuleHeaderData()).getPackageFragmentNameList();
        KlibMetadataModuleDescriptorFactoryImpl klibMetadataModuleDescriptorFactory = getKlibMetadataModuleDescriptorFactory();
        Intrinsics.checkNotNull(packageFragmentNameList);
        PackageFragmentProvider packageFragmentProviderCreatePackageFragmentProvider = klibMetadataModuleDescriptorFactory.createPackageFragmentProvider(library, null, packageFragmentNameList, new LockBasedStorageManager("KlibMetadataPackageFragmentProvider"), moduleDescriptorImpl, new JvmCompilerDeserializationConfiguration(languageVersionSettings), null, LookupTracker.DO_NOTHING.INSTANCE);
        moduleDescriptorImpl.initialize(packageFragmentProviderCreatePackageFragmentProvider);
        return packageFragmentProviderCreatePackageFragmentProvider;
    }
}
