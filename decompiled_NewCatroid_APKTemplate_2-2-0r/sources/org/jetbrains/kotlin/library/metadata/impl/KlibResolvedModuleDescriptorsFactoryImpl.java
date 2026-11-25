package org.jetbrains.kotlin.library.metadata.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibResolvedModuleDescriptorsFactory;
import org.jetbrains.kotlin.library.metadata.KotlinResolvedModuleDescriptors;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;
import org.jetbrains.kotlin.library.metadata.SyntheticModulesOrigin;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.util.UtilKt;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\u0018\u0000 &2\u00020\u0001:\u0001&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J4\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J*\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010\u0017\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019Jj\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0$2\u0006\u0010%\u001a\u00020\u0019H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/KlibResolvedModuleDescriptorsFactoryImpl;", "Lorg/jetbrains/kotlin/library/metadata/KlibResolvedModuleDescriptorsFactory;", "moduleDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "(Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;)V", "getModuleDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "createDescriptorOptionalBuiltsIns", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "packageAccessHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "name", "Lorg/jetbrains/kotlin/name/Name;", "moduleOrigin", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "createForwardDeclarationsModule", "isExpect", "", "createResolved", "Lorg/jetbrains/kotlin/library/metadata/KotlinResolvedModuleDescriptors;", "resolvedLibraries", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "friendModuleFiles", "", "Lorg/jetbrains/kotlin/konan/file/File;", "refinesModuleFiles", "includedLibraryFiles", "additionalDependencyModules", "", "isForMetadataCompilation", "Companion", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibResolvedModuleDescriptorsFactoryImpl implements KlibResolvedModuleDescriptorsFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Name FORWARD_DECLARATIONS_MODULE_NAME;
    private final KlibMetadataModuleDescriptorFactory moduleDescriptorFactory;

    /* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class EntriesMappings {
        public static final /* synthetic */ EnumEntries<NativeForwardDeclarationKind> entries$0 = EnumEntriesKt.enumEntries(NativeForwardDeclarationKind.values());
    }

    public KlibResolvedModuleDescriptorsFactoryImpl(KlibMetadataModuleDescriptorFactory moduleDescriptorFactory) {
        Intrinsics.checkNotNullParameter(moduleDescriptorFactory, "moduleDescriptorFactory");
        this.moduleDescriptorFactory = moduleDescriptorFactory;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibResolvedModuleDescriptorsFactory
    public KlibMetadataModuleDescriptorFactory getModuleDescriptorFactory() {
        return this.moduleDescriptorFactory;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.library.metadata.KlibResolvedModuleDescriptorsFactory
    public KotlinResolvedModuleDescriptors createResolved(KotlinLibraryResolveResult resolvedLibraries, final StorageManager storageManager, KotlinBuiltIns builtIns, final LanguageVersionSettings languageVersionSettings, final Set<File> friendModuleFiles, final Set<File> refinesModuleFiles, final Set<File> includedLibraryFiles, Iterable<ModuleDescriptorImpl> additionalDependencyModules, boolean isForMetadataCompilation) {
        LinkedHashSet linkedHashSet;
        LinkedHashSet linkedHashSet2;
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(friendModuleFiles, "friendModuleFiles");
        Intrinsics.checkNotNullParameter(refinesModuleFiles, "refinesModuleFiles");
        Intrinsics.checkNotNullParameter(includedLibraryFiles, "includedLibraryFiles");
        Intrinsics.checkNotNullParameter(additionalDependencyModules, "additionalDependencyModules");
        final ArrayList<ModuleDescriptorImpl> arrayList = new ArrayList();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = builtIns;
        final LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        final LinkedHashSet linkedHashSet4 = new LinkedHashSet();
        final Set linkedHashSet5 = new LinkedHashSet();
        LinkedHashSet linkedHashSet6 = linkedHashSet4;
        LinkedHashSet linkedHashSet7 = linkedHashSet3;
        resolvedLibraries.forEach(new Function2<KotlinLibrary, PackageAccessHandler, Unit>() { // from class: org.jetbrains.kotlin.library.metadata.impl.KlibResolvedModuleDescriptorsFactoryImpl.createResolved.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinLibrary kotlinLibrary, PackageAccessHandler packageAccessHandler) {
                invoke2(kotlinLibrary, packageAccessHandler);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(final KotlinLibrary library, final PackageAccessHandler packageAccessHandler) {
                Intrinsics.checkNotNullParameter(library, "library");
                Intrinsics.checkNotNullParameter(packageAccessHandler, "packageAccessHandler");
                String str = "Loading " + library.getLibraryName();
                final KlibResolvedModuleDescriptorsFactoryImpl klibResolvedModuleDescriptorsFactoryImpl = KlibResolvedModuleDescriptorsFactoryImpl.this;
                final LanguageVersionSettings languageVersionSettings2 = languageVersionSettings;
                final StorageManager storageManager2 = storageManager;
                final Ref.ObjectRef<KotlinBuiltIns> objectRef2 = objectRef;
                final List<ModuleDescriptorImpl> list = arrayList;
                final Set<File> set = refinesModuleFiles;
                final Set<ModuleDescriptorImpl> set2 = linkedHashSet4;
                final Set<File> set3 = friendModuleFiles;
                final Set<ModuleDescriptorImpl> set4 = linkedHashSet3;
                final Set<File> set5 = includedLibraryFiles;
                final Set<ModuleDescriptorImpl> set6 = linkedHashSet5;
                UtilKt.profile(str, new Function0<Unit>() { // from class: org.jetbrains.kotlin.library.metadata.impl.KlibResolvedModuleDescriptorsFactoryImpl.createResolved.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Type inference failed for: r2v1, types: [T, org.jetbrains.kotlin.builtins.KotlinBuiltIns] */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltsIns = klibResolvedModuleDescriptorsFactoryImpl.createDescriptorOptionalBuiltsIns(library, languageVersionSettings2, storageManager2, objectRef2.element, packageAccessHandler);
                        objectRef2.element = moduleDescriptorImplCreateDescriptorOptionalBuiltsIns.getBuiltIns();
                        list.add(moduleDescriptorImplCreateDescriptorOptionalBuiltsIns);
                        if (set.contains(library.getLibraryFile())) {
                            set2.add(moduleDescriptorImplCreateDescriptorOptionalBuiltsIns);
                        }
                        if (set3.contains(library.getLibraryFile())) {
                            set4.add(moduleDescriptorImplCreateDescriptorOptionalBuiltsIns);
                        }
                        if (set5.contains(library.getLibraryFile())) {
                            set6.add(moduleDescriptorImplCreateDescriptorOptionalBuiltsIns);
                        }
                    }
                });
            }
        });
        ModuleDescriptorImpl moduleDescriptorImplCreateForwardDeclarationsModule = createForwardDeclarationsModule((KotlinBuiltIns) objectRef.element, storageManager, isForMetadataCompilation);
        for (ModuleDescriptorImpl moduleDescriptorImpl : arrayList) {
            Set<ModuleDescriptorImpl> mutableSet = CollectionsKt.toMutableSet(additionalDependencyModules);
            Set set = linkedHashSet5;
            if (set.contains(moduleDescriptorImpl)) {
                linkedHashSet2 = linkedHashSet7;
                mutableSet.addAll(linkedHashSet2);
                linkedHashSet = linkedHashSet6;
                mutableSet.addAll(linkedHashSet);
            } else {
                linkedHashSet = linkedHashSet6;
                linkedHashSet2 = linkedHashSet7;
            }
            moduleDescriptorImpl.setDependencies(CollectionsKt.plus((Collection<? extends ModuleDescriptorImpl>) CollectionsKt.plus((Collection) arrayList, (Iterable) additionalDependencyModules), moduleDescriptorImplCreateForwardDeclarationsModule), mutableSet);
            linkedHashSet5 = set;
            linkedHashSet7 = linkedHashSet2;
            linkedHashSet6 = linkedHashSet;
        }
        return new KotlinResolvedModuleDescriptors(arrayList, moduleDescriptorImplCreateForwardDeclarationsModule, linkedHashSet7, linkedHashSet6);
    }

    public final ModuleDescriptorImpl createForwardDeclarationsModule(KotlinBuiltIns builtIns, StorageManager storageManager, boolean isExpect) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltsIns = createDescriptorOptionalBuiltsIns(FORWARD_DECLARATIONS_MODULE_NAME, storageManager, builtIns, SyntheticModulesOrigin.INSTANCE);
        EnumEntries<NativeForwardDeclarationKind> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enumEntries, 10));
        Iterator<NativeForwardDeclarationKind> it = enumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(createForwardDeclarationsModule$createPackage(storageManager, moduleDescriptorImplCreateDescriptorOptionalBuiltsIns, isExpect, it.next()));
        }
        moduleDescriptorImplCreateDescriptorOptionalBuiltsIns.initialize(new PackageFragmentProviderImpl(arrayList));
        moduleDescriptorImplCreateDescriptorOptionalBuiltsIns.setDependencies(moduleDescriptorImplCreateDescriptorOptionalBuiltsIns);
        return moduleDescriptorImplCreateDescriptorOptionalBuiltsIns;
    }

    private static final ForwardDeclarationsPackageFragmentDescriptor createForwardDeclarationsModule$createPackage(StorageManager storageManager, ModuleDescriptorImpl moduleDescriptorImpl, boolean z, NativeForwardDeclarationKind nativeForwardDeclarationKind) {
        return new ForwardDeclarationsPackageFragmentDescriptor(storageManager, moduleDescriptorImpl, nativeForwardDeclarationKind.getPackageFqName(), nativeForwardDeclarationKind.getSuperClassName(), nativeForwardDeclarationKind.getClassKind(), z);
    }

    private final ModuleDescriptorImpl createDescriptorOptionalBuiltsIns(Name name2, StorageManager storageManager, KotlinBuiltIns builtIns, KlibModuleOrigin moduleOrigin) {
        if (builtIns != null) {
            return KlibModuleDescriptorFactory.createDescriptor$default(getModuleDescriptorFactory().getDescriptorFactory(), name2, storageManager, builtIns, moduleOrigin, null, 16, null);
        }
        return KlibModuleDescriptorFactory.createDescriptorAndNewBuiltIns$default(getModuleDescriptorFactory().getDescriptorFactory(), name2, storageManager, moduleOrigin, null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ModuleDescriptorImpl createDescriptorOptionalBuiltsIns(KotlinLibrary library, LanguageVersionSettings languageVersionSettings, StorageManager storageManager, KotlinBuiltIns builtIns, PackageAccessHandler packageAccessHandler) {
        if (builtIns != null) {
            return getModuleDescriptorFactory().createDescriptor(library, languageVersionSettings, storageManager, builtIns, packageAccessHandler);
        }
        return getModuleDescriptorFactory().createDescriptorAndNewBuiltIns(library, languageVersionSettings, storageManager, packageAccessHandler);
    }

    /* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/KlibResolvedModuleDescriptorsFactoryImpl$Companion;", "", "()V", "FORWARD_DECLARATIONS_MODULE_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getFORWARD_DECLARATIONS_MODULE_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Name getFORWARD_DECLARATIONS_MODULE_NAME() {
            return KlibResolvedModuleDescriptorsFactoryImpl.FORWARD_DECLARATIONS_MODULE_NAME;
        }
    }

    static {
        Name nameSpecial = Name.special("<forward declarations>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        FORWARD_DECLARATIONS_MODULE_NAME = nameSpecial;
    }
}
