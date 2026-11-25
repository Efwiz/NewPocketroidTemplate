package org.jetbrains.kotlin.library.metadata.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.BuiltInKlibMetadataDeserializedPackageFragment;
import org.jetbrains.kotlin.library.metadata.KlibDeserializedContainerSource;
import org.jetbrains.kotlin.library.metadata.KlibMetadataCachedPackageFragment;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragment;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;
import org.jetbrains.kotlin.library.metadata.SimplePackageAccessHandler;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataDeserializedPackageFragmentsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016JF\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/KlibMetadataDeserializedPackageFragmentsFactoryImpl;", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "()V", "createCachedPackageFragments", "", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataCachedPackageFragment;", "packageFragments", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "createDeserializedPackageFragments", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragment;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "packageFragmentNames", "", "packageAccessedHandler", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KlibMetadataDeserializedPackageFragmentsFactoryImpl implements KlibMetadataDeserializedPackageFragmentsFactory {
    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory
    public List<KlibMetadataDeserializedPackageFragment> createDeserializedPackageFragments(KotlinLibrary library, List<String> packageFragmentNames, ModuleDescriptor moduleDescriptor, PackageAccessHandler packageAccessedHandler, StorageManager storageManager, DeserializationConfiguration configuration) {
        KlibDeserializedContainerSource klibDeserializedContainerSource;
        KlibMetadataDeserializedPackageFragment klibMetadataDeserializedPackageFragment;
        KotlinLibrary library2 = library;
        Intrinsics.checkNotNullParameter(library2, "library");
        Intrinsics.checkNotNullParameter(packageFragmentNames, "packageFragmentNames");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        KlibMetadataProtoBuf.Header headerLoadModuleHeader = (packageAccessedHandler == null ? SimplePackageAccessHandler.INSTANCE : packageAccessedHandler).loadModuleHeader(library2);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = packageFragmentNames.iterator();
        while (it.hasNext()) {
            FqName fqName = new FqName((String) it.next());
            KlibDeserializedContainerSource klibDeserializedContainerSource2 = new KlibDeserializedContainerSource(library2, headerLoadModuleHeader, configuration, fqName);
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Set<String> setPackageMetadataParts = library2.packageMetadataParts(strAsString);
            boolean z = moduleDescriptor.getBuiltIns().getBuiltInsModule() == moduleDescriptor;
            Set<String> set = setPackageMetadataParts;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
            for (String str : set) {
                if (z) {
                    klibDeserializedContainerSource = klibDeserializedContainerSource2;
                    klibMetadataDeserializedPackageFragment = new BuiltInKlibMetadataDeserializedPackageFragment(fqName, library, packageAccessedHandler, storageManager, moduleDescriptor, str, klibDeserializedContainerSource2);
                } else {
                    klibDeserializedContainerSource = klibDeserializedContainerSource2;
                    klibMetadataDeserializedPackageFragment = new KlibMetadataDeserializedPackageFragment(fqName, library, packageAccessedHandler, storageManager, moduleDescriptor, str, klibDeserializedContainerSource);
                }
                arrayList2.add(klibMetadataDeserializedPackageFragment);
                arrayList2 = arrayList2;
                klibDeserializedContainerSource2 = klibDeserializedContainerSource;
            }
            CollectionsKt.addAll(arrayList, arrayList2);
            library2 = library;
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory
    public List<KlibMetadataCachedPackageFragment> createCachedPackageFragments(List<byte[]> packageFragments, ModuleDescriptor moduleDescriptor, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        List<byte[]> list = packageFragments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new KlibMetadataCachedPackageFragment((byte[]) it.next(), storageManager, moduleDescriptor, null, null, 24, null));
        }
        return arrayList;
    }
}
