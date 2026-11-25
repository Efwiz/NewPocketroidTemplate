package org.jetbrains.kotlin.backend.common.serialization.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;

/* JADX INFO: compiled from: KlibMetadataMonolithicSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t¢\u0006\u0002\u0010\rJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0017H\u0002R\u0014\u0010\u000e\u001a\u00020\u000fX\u0094D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000fX\u0094D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataMonolithicSerializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "exportKDoc", "", "skipExpects", "includeOnlyModuleContent", "allowErrorTypes", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lcom/intellij/openapi/project/Project;ZZZZ)V", "TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "", "getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "()Ljava/lang/Integer;", "TOP_LEVEL_DECLARATION_COUNT_PER_FILE", "getTOP_LEVEL_DECLARATION_COUNT_PER_FILE", "serializeModule", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "serializePackageFragment", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "module", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataMonolithicSerializer extends KlibMetadataSerializer {
    private final int TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE;
    private final int TOP_LEVEL_DECLARATION_COUNT_PER_FILE;

    public /* synthetic */ KlibMetadataMonolithicSerializer(LanguageVersionSettings languageVersionSettings, BinaryVersion binaryVersion, Project project, boolean z, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageVersionSettings, binaryVersion, project, z, z2, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataMonolithicSerializer(LanguageVersionSettings languageVersionSettings, BinaryVersion metadataVersion, Project project, boolean z, boolean z2, boolean z3, boolean z4) {
        super(languageVersionSettings, metadataVersion, project, z, z2, z3, z4);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.TOP_LEVEL_DECLARATION_COUNT_PER_FILE = 128;
        this.TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE = 64;
    }

    private final List<ProtoBuf.PackageFragment> serializePackageFragment(FqName fqName, ModuleDescriptor module) {
        ArrayList arrayListPackageFragments;
        if (getIncludeOnlyModuleContent()) {
            arrayListPackageFragments = PackageFragmentProviderKt.packageFragments(KlibMetadataSerializerKt.getPackageFragmentProviderForModuleContentWithoutDependencies(module), fqName);
        } else {
            List<PackageFragmentDescriptor> fragments = module.getPackage(fqName).getFragments();
            ArrayList arrayList = new ArrayList();
            for (Object obj : fragments) {
                if (Intrinsics.areEqual(DescriptorUtilsKt.getModule((PackageFragmentDescriptor) obj), module)) {
                    arrayList.add(obj);
                }
            }
            arrayListPackageFragments = arrayList;
        }
        if (arrayListPackageFragments.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        DescriptorSerializer.Companion companion = DescriptorSerializer.INSTANCE;
        List list = arrayListPackageFragments;
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList2, MemberScopeKt.getDescriptorsFiltered$default(((PackageFragmentDescriptor) it.next()).getScope(), DescriptorKindFilter.CLASSIFIERS, null, 2, null));
        }
        List<? extends DeclarationDescriptor> listSort = companion.sort(arrayList2);
        DescriptorSerializer.Companion companion2 = DescriptorSerializer.INSTANCE;
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList3, MemberScopeKt.getDescriptorsFiltered$default(((PackageFragmentDescriptor) it2.next()).getScope(), DescriptorKindFilter.CALLABLES, null, 2, null));
        }
        return serializeDescriptors(fqName, listSort, companion2.sort(arrayList3));
    }

    public final SerializedMetadata serializeModule(ModuleDescriptor moduleDescriptor) {
        Object extension;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (FqName fqName : getPackagesFqNames(moduleDescriptor)) {
            List<ProtoBuf.PackageFragment> listSerializePackageFragment = serializePackageFragment(fqName, moduleDescriptor);
            if (!listSerializePackageFragment.isEmpty()) {
                String strAsString = fqName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                List<ProtoBuf.PackageFragment> list = listSerializePackageFragment;
                if ((list instanceof Collection) && list.isEmpty()) {
                    arrayList3.add(strAsString);
                    break;
                }
                Iterator<T> it = list.iterator();
                do {
                    if (it.hasNext()) {
                        extension = ((ProtoBuf.PackageFragment) it.next()).getExtension(KlibMetadataProtoBuf.isEmpty);
                        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                    } else {
                        arrayList3.add(strAsString);
                        break;
                    }
                } while (((Boolean) extension).booleanValue());
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList4.add(((ProtoBuf.PackageFragment) it2.next()).toByteArray());
                }
                arrayList.add(arrayList4);
                arrayList2.add(strAsString);
            }
        }
        byte[] byteArray = serializeHeader(moduleDescriptor, arrayList2, arrayList3).toByteArray();
        Intrinsics.checkNotNull(byteArray);
        return new SerializedMetadata(byteArray, arrayList, arrayList2);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer
    protected Integer getTOP_LEVEL_DECLARATION_COUNT_PER_FILE() {
        return Integer.valueOf(this.TOP_LEVEL_DECLARATION_COUNT_PER_FILE);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer
    protected Integer getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE() {
        return Integer.valueOf(this.TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE);
    }
}
