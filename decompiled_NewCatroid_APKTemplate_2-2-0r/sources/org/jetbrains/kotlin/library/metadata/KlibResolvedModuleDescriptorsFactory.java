package org.jetbrains.kotlin.library.metadata;

import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001Jj\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0019H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001aÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibResolvedModuleDescriptorsFactory;", "", "moduleDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "getModuleDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "createResolved", "Lorg/jetbrains/kotlin/library/metadata/KotlinResolvedModuleDescriptors;", "resolvedLibraries", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "friendModuleFiles", "", "Lorg/jetbrains/kotlin/konan/file/File;", "refinesModuleFiles", "includedLibraryFiles", "additionalDependencyModules", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "isForMetadataCompilation", "", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KlibResolvedModuleDescriptorsFactory {
    KotlinResolvedModuleDescriptors createResolved(KotlinLibraryResolveResult resolvedLibraries, StorageManager storageManager, KotlinBuiltIns builtIns, LanguageVersionSettings languageVersionSettings, Set<File> friendModuleFiles, Set<File> refinesModuleFiles, Set<File> includedLibraryFiles, Iterable<ModuleDescriptorImpl> additionalDependencyModules, boolean isForMetadataCompilation);

    KlibMetadataModuleDescriptorFactory getModuleDescriptorFactory();
}
