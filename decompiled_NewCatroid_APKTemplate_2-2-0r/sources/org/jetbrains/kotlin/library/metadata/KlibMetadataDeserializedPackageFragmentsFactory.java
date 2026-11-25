package org.jetbrains.kotlin.library.metadata;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataDeserializedPackageFragmentsFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&JF\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "", "createCachedPackageFragments", "", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataPackageFragment;", "packageFragments", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "createDeserializedPackageFragments", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "packageFragmentNames", "", "packageAccessedHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KlibMetadataDeserializedPackageFragmentsFactory {
    List<KlibMetadataPackageFragment> createCachedPackageFragments(List<byte[]> packageFragments, ModuleDescriptor moduleDescriptor, StorageManager storageManager);

    List<KlibMetadataPackageFragment> createDeserializedPackageFragments(KotlinLibrary library, List<String> packageFragmentNames, ModuleDescriptor moduleDescriptor, PackageAccessHandler packageAccessedHandler, StorageManager storageManager, DeserializationConfiguration configuration);
}
