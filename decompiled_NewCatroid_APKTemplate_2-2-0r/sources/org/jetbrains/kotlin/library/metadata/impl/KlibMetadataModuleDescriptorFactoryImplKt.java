package org.jetbrains.kotlin.library.metadata.impl;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.RequiredUnresolvedLibrary;
import org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibModuleOriginKt;

/* JADX INFO: compiled from: KlibMetadataModuleDescriptorFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isStdlibModule", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "(Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;)Z", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataModuleDescriptorFactoryImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isStdlibModule(ModuleDescriptorImpl moduleDescriptorImpl) {
        KotlinLibrary library;
        List<RequiredUnresolvedLibrary> unresolvedDependencies;
        KlibModuleOrigin klibModuleOrigin = KlibModuleOriginKt.getKlibModuleOrigin(moduleDescriptorImpl);
        DeserializedKlibModuleOrigin deserializedKlibModuleOrigin = klibModuleOrigin instanceof DeserializedKlibModuleOrigin ? (DeserializedKlibModuleOrigin) klibModuleOrigin : null;
        if (deserializedKlibModuleOrigin == null || (library = deserializedKlibModuleOrigin.getLibrary()) == null || (unresolvedDependencies = KotlinLibraryKt.getUnresolvedDependencies(library)) == null) {
            return false;
        }
        return unresolvedDependencies.isEmpty();
    }
}
