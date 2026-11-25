package org.jetbrains.kotlin.library.metadata;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataModuleDescriptorFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J6\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH&J2\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J*\u0010%\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J<\u0010&\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\u0019\u001a\u00020\u001aH&JR\u0010'\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010*\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006+À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "", "descriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "getDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "getFlexibleTypeDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "packageFragmentsFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "getPackageFragmentsFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "createCachedPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "byteArrays", "", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "createDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "packageAccessHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "createDescriptorAndNewBuiltIns", "createDescriptorOptionalBuiltIns", "createPackageFragmentProvider", "packageFragmentNames", "", "compositePackageFragmentAddend", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KlibMetadataModuleDescriptorFactory {
    PackageFragmentProvider createCachedPackageFragmentProvider(List<byte[]> byteArrays, StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, LookupTracker lookupTracker);

    ModuleDescriptorImpl createDescriptorOptionalBuiltIns(KotlinLibrary library, LanguageVersionSettings languageVersionSettings, StorageManager storageManager, KotlinBuiltIns builtIns, PackageAccessHandler packageAccessHandler, LookupTracker lookupTracker);

    PackageFragmentProvider createPackageFragmentProvider(KotlinLibrary library, PackageAccessHandler packageAccessHandler, List<String> packageFragmentNames, StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, PackageFragmentProvider compositePackageFragmentAddend, LookupTracker lookupTracker);

    KlibModuleDescriptorFactory getDescriptorFactory();

    FlexibleTypeDeserializer getFlexibleTypeDeserializer();

    KlibMetadataDeserializedPackageFragmentsFactory getPackageFragmentsFactory();

    default ModuleDescriptorImpl createDescriptor(KotlinLibrary library, LanguageVersionSettings languageVersionSettings, StorageManager storageManager, KotlinBuiltIns builtIns, PackageAccessHandler packageAccessHandler) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        return createDescriptorOptionalBuiltIns(library, languageVersionSettings, storageManager, builtIns, packageAccessHandler, LookupTracker.DO_NOTHING.INSTANCE);
    }

    default ModuleDescriptorImpl createDescriptorAndNewBuiltIns(KotlinLibrary library, LanguageVersionSettings languageVersionSettings, StorageManager storageManager, PackageAccessHandler packageAccessHandler) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        return createDescriptorOptionalBuiltIns(library, languageVersionSettings, storageManager, null, packageAccessHandler, LookupTracker.DO_NOTHING.INSTANCE);
    }
}
