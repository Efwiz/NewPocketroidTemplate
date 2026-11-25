package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.ir.backend.jvm.JvmLibraryResolverKt;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataFactories;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.NullFlexibleTypeDeserializer;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\u001a&\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0002\u001a.\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00060\r2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u000f"}, d2 = {"jvmFactories", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "getJvmFactories", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "getKlibModules", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "klibList", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "dependencyModule", "getModuleDescriptorByLibrary", "current", "mapping", "", "", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TopDownAnalyzerFacadeForJVMKt {
    private static final KlibMetadataFactories jvmFactories = new KlibMetadataFactories(new Function1<StorageManager, KotlinBuiltIns>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVMKt$jvmFactories$1
        @Override // kotlin.jvm.functions.Function1
        public final KotlinBuiltIns invoke(StorageManager storageManager) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            return new JvmBuiltIns(storageManager, JvmBuiltIns.Kind.FROM_DEPENDENCIES);
        }
    }, NullFlexibleTypeDeserializer.INSTANCE);

    public static final KlibMetadataFactories getJvmFactories() {
        return jvmFactories;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<ModuleDescriptorImpl> getKlibModules(List<? extends KotlinLibrary> list, ModuleDescriptorImpl moduleDescriptorImpl) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<? extends KotlinLibrary> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (KotlinLibrary kotlinLibrary : list2) {
            String libraryName = kotlinLibrary.getLibraryName();
            Object moduleDescriptorByLibrary = linkedHashMap.get(libraryName);
            if (moduleDescriptorByLibrary == null) {
                moduleDescriptorByLibrary = getModuleDescriptorByLibrary(kotlinLibrary, linkedHashMap, moduleDescriptorImpl);
                linkedHashMap.put(libraryName, moduleDescriptorByLibrary);
            }
            arrayList.add((ModuleDescriptorImpl) moduleDescriptorByLibrary);
        }
        return arrayList;
    }

    private static final ModuleDescriptorImpl getModuleDescriptorByLibrary(KotlinLibrary kotlinLibrary, Map<String, ModuleDescriptorImpl> map, ModuleDescriptorImpl moduleDescriptorImpl) {
        KlibMetadataModuleDescriptorFactory defaultDeserializedDescriptorFactory = jvmFactories.getDefaultDeserializedDescriptorFactory();
        LanguageVersionSettingsImpl languageVersionSettingsImpl = LanguageVersionSettingsImpl.DEFAULT;
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltIns = defaultDeserializedDescriptorFactory.createDescriptorOptionalBuiltIns(kotlinLibrary, languageVersionSettingsImpl, NO_LOCKS, null, null, LookupTracker.DO_NOTHING.INSTANCE);
        List<String> listPropertyList$default = PropertiesKt.propertyList$default(kotlinLibrary.getManifestProperties(), KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, null, true, 2, null);
        ArrayList arrayList = new ArrayList();
        for (String str : listPropertyList$default) {
            ModuleDescriptorImpl moduleDescriptorImpl2 = map.get(str);
            if (moduleDescriptorImpl2 == null) {
                JvmLibraryResolverKt.getJvmLibrariesProvidedByDefault().contains(str);
                moduleDescriptorImpl2 = null;
            }
            if (moduleDescriptorImpl2 != null) {
                arrayList.add(moduleDescriptorImpl2);
            }
        }
        moduleDescriptorImplCreateDescriptorOptionalBuiltIns.setDependencies(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf(moduleDescriptorImplCreateDescriptorOptionalBuiltIns), (Iterable) arrayList), (Iterable) CollectionsKt.listOfNotNull(moduleDescriptorImpl)));
        return moduleDescriptorImplCreateDescriptorOptionalBuiltIns;
    }
}
