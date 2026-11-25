package org.jetbrains.kotlin.backend.common.serialization.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.metadata.KlibMetadataVersion;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;

/* JADX INFO: compiled from: KlibMetadataIncrementalSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t¢\u0006\u0002\u0010\fJ$\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u001bR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000eX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataIncrementalSerializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "exportKDoc", "", "skipExpects", "allowErrorTypes", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;Lcom/intellij/openapi/project/Project;ZZZ)V", "TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "", "getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "()Ljava/lang/Void;", "TOP_LEVEL_DECLARATION_COUNT_PER_FILE", "getTOP_LEVEL_DECLARATION_COUNT_PER_FILE", "serializePackageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "scope", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataIncrementalSerializer extends KlibMetadataSerializer {
    private final Void TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE;
    private final Void TOP_LEVEL_DECLARATION_COUNT_PER_FILE;

    public /* synthetic */ KlibMetadataIncrementalSerializer(LanguageVersionSettings languageVersionSettings, KlibMetadataVersion klibMetadataVersion, Project project, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageVersionSettings, klibMetadataVersion, project, z, z2, (i & 32) != 0 ? false : z3);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer
    public /* bridge */ /* synthetic */ Integer getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE() {
        return (Integer) getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer
    public /* bridge */ /* synthetic */ Integer getTOP_LEVEL_DECLARATION_COUNT_PER_FILE() {
        return (Integer) getTOP_LEVEL_DECLARATION_COUNT_PER_FILE();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataIncrementalSerializer(LanguageVersionSettings languageVersionSettings, KlibMetadataVersion metadataVersion, Project project, boolean z, boolean z2, boolean z3) {
        super(languageVersionSettings, metadataVersion, project, z, z2, false, z3, 32, null);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(project, "project");
    }

    public final ProtoBuf.PackageFragment serializePackageFragment(ModuleDescriptor module, Collection<? extends DeclarationDescriptor> scope, FqName fqName) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ArrayList arrayList = new ArrayList();
        for (Object obj : scope) {
            if (Intrinsics.areEqual(DescriptorUtilsKt.getModule((DeclarationDescriptor) obj), module)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (obj2 instanceof ClassifierDescriptor) {
                arrayList3.add(obj2);
            }
        }
        List<? extends DeclarationDescriptor> listSortedWith = CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataIncrementalSerializer$serializePackageFragment$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(DescriptorUtilsKt.getFqNameSafe((ClassifierDescriptor) t).asString(), DescriptorUtilsKt.getFqNameSafe((ClassifierDescriptor) t2).asString());
            }
        });
        DescriptorSerializer.Companion companion = DescriptorSerializer.INSTANCE;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : arrayList2) {
            if (obj3 instanceof CallableDescriptor) {
                arrayList4.add(obj3);
            }
        }
        return (ProtoBuf.PackageFragment) CollectionsKt.single((List) serializeDescriptors(fqName, listSortedWith, companion.sort(arrayList4)));
    }

    protected Void getTOP_LEVEL_DECLARATION_COUNT_PER_FILE() {
        return this.TOP_LEVEL_DECLARATION_COUNT_PER_FILE;
    }

    protected Void getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE() {
        return this.TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE;
    }
}
