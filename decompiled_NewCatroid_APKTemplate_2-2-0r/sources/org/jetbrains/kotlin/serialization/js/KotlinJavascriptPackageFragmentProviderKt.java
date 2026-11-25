package org.jetbrains.kotlin.serialization.js;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.ContractDeserializerImpl;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: kotlinJavascriptPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aD\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0015"}, d2 = {"fqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getFqName", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;)Lorg/jetbrains/kotlin/name/FqName;", "createKotlinJavascriptPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "header", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "packageFragmentProtos", "", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "js.serializer"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptPackageFragmentProviderKt {
    public static final PackageFragmentProvider createKotlinJavascriptPackageFragmentProvider(StorageManager storageManager, ModuleDescriptor module, JsProtoBuf.Header header, List<ProtoBuf.PackageFragment> packageFragmentProtos, JsMetadataVersion metadataVersion, DeserializationConfiguration configuration, LookupTracker lookupTracker) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(packageFragmentProtos, "packageFragmentProtos");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        List<ProtoBuf.PackageFragment> list = packageFragmentProtos;
        ArrayList arrayList = new ArrayList();
        for (ProtoBuf.PackageFragment packageFragment : list) {
            FqName fqName = getFqName(packageFragment);
            KotlinJavascriptPackageFragment kotlinJavascriptPackageFragment = fqName != null ? new KotlinJavascriptPackageFragment(fqName, storageManager, module, packageFragment, header, metadataVersion, configuration) : null;
            if (kotlinJavascriptPackageFragment != null) {
                arrayList.add(kotlinJavascriptPackageFragment);
            }
        }
        ArrayList arrayList2 = arrayList;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            FqName fqName2 = getFqName((ProtoBuf.PackageFragment) it.next());
            if (fqName2 != null) {
                linkedHashSet.add(fqName2);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        ArrayList<FqName> arrayList3 = new ArrayList();
        Iterator it2 = linkedHashSet2.iterator();
        while (it2.hasNext()) {
            FqName fqNameParentOrNull = FqNamesUtilKt.parentOrNull((FqName) it2.next());
            if (fqNameParentOrNull != null) {
                arrayList3.add(fqNameParentOrNull);
            }
        }
        for (FqName fqNameParent : arrayList3) {
            while (!fqNameParent.isRoot() && linkedHashSet2.add(fqNameParent)) {
                arrayList2.add(new EmptyPackageFragmentDescriptor(module, fqNameParent));
                fqNameParent = fqNameParent.parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            }
        }
        PackageFragmentProviderImpl packageFragmentProviderImpl = new PackageFragmentProviderImpl(arrayList2);
        NotFoundClasses notFoundClasses = new NotFoundClasses(storageManager, module);
        EnumEntriesDeserializationSupport enumEntriesDeserializationSupport = new EnumEntriesDeserializationSupport() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptPackageFragmentProviderKt$createKotlinJavascriptPackageFragmentProvider$enumEntriesDeserializationSupport$1
            @Override // org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport
            public Boolean canSynthesizeEnumEntries() {
                return false;
            }
        };
        PackageFragmentProviderImpl packageFragmentProviderImpl2 = packageFragmentProviderImpl;
        DeserializedClassDataFinder deserializedClassDataFinder = new DeserializedClassDataFinder(packageFragmentProviderImpl2);
        AnnotationAndConstantLoaderImpl annotationAndConstantLoaderImpl = new AnnotationAndConstantLoaderImpl(module, notFoundClasses, JsSerializerProtocol.INSTANCE);
        LocalClassifierTypeSettings.Default r7 = LocalClassifierTypeSettings.Default.INSTANCE;
        ErrorReporter DO_NOTHING = ErrorReporter.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
        DeserializationComponents deserializationComponents = new DeserializationComponents(storageManager, module, configuration, deserializedClassDataFinder, annotationAndConstantLoaderImpl, packageFragmentProviderImpl2, r7, DO_NOTHING, lookupTracker, DynamicTypeDeserializer.INSTANCE, CollectionsKt.emptyList(), notFoundClasses, new ContractDeserializerImpl(configuration, storageManager), null, PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE, JsSerializerProtocol.INSTANCE.getExtensionRegistry(), null, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), null, enumEntriesDeserializationSupport, 335872, null);
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : arrayList2) {
            if (obj instanceof KotlinJavascriptPackageFragment) {
                arrayList4.add(obj);
            }
        }
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            ((KotlinJavascriptPackageFragment) it3.next()).initialize(deserializationComponents);
        }
        return packageFragmentProviderImpl2;
    }

    private static final FqName getFqName(ProtoBuf.PackageFragment packageFragment) {
        ProtoBuf.StringTable strings = packageFragment.getStrings();
        Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
        ProtoBuf.QualifiedNameTable qualifiedNames = packageFragment.getQualifiedNames();
        Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
        NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
        if (packageFragment.hasPackage()) {
            Object extension = packageFragment.getPackage().getExtension(JsProtoBuf.packageFqName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            return new FqName(nameResolverImpl.getPackageFqName(((Number) extension).intValue()));
        }
        if (packageFragment.getClass_Count() <= 0) {
            return null;
        }
        List<? extends ProtoBuf.ClassOrBuilder> class_OrBuilderList = packageFragment.getClass_OrBuilderList();
        Intrinsics.checkNotNullExpressionValue(class_OrBuilderList, "getClass_OrBuilderList(...)");
        return NameResolverUtilKt.getClassId(nameResolverImpl, ((ProtoBuf.ClassOrBuilder) CollectionsKt.first((List) class_OrBuilderList)).getFqName()).getPackageFqName();
    }
}
