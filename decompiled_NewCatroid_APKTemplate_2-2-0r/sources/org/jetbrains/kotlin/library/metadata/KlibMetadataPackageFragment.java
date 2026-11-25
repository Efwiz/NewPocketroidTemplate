package org.jetbrains.kotlin.library.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\b&\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\b\u0010-\u001a\u00020\fH\u0016J\u000e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0018J\u0010\u00101\u001a\u0002022\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u00103\u001a\b\u0012\u0004\u0012\u00020\u001804H\u0002R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0011\u001a\u00020\u00128VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u0010\u001a\u0004\b$\u0010%R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020(X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010*¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataPackageFragment;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "_memberScope", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedPackageMemberScope;", "get_memberScope", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedPackageMemberScope;", "_memberScope$delegate", "Lkotlin/Lazy;", "classDataFinder", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataClassDataFinder;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataClassDataFinder;", "classDataFinder$delegate", "classifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "()Ljava/util/Set;", "classifierNames$delegate", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "setComponents", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;)V", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "nameResolver$delegate", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "protoForNames", "getProtoForNames", "getMemberScope", "hasTopLevelClassifier", "", "name", "initialize", "", "loadClassNames", "", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KlibMetadataPackageFragment extends DeserializedPackageFragment {

    /* JADX INFO: renamed from: _memberScope$delegate, reason: from kotlin metadata */
    private final Lazy _memberScope;

    /* JADX INFO: renamed from: classDataFinder$delegate, reason: from kotlin metadata */
    private final Lazy classDataFinder;

    /* JADX INFO: renamed from: classifierNames$delegate, reason: from kotlin metadata */
    private final Lazy classifierNames;
    public DeserializationComponents components;

    /* JADX INFO: renamed from: nameResolver$delegate, reason: from kotlin metadata */
    private final Lazy nameResolver;

    public abstract ProtoBuf.PackageFragment getProtoForNames();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataPackageFragment(FqName fqName, StorageManager storageManager, ModuleDescriptor module, final DeserializedContainerSource deserializedContainerSource) {
        super(fqName, storageManager, module);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.nameResolver = LazyKt.lazy(new Function0<NameResolverImpl>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment$nameResolver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final NameResolverImpl invoke() {
                ProtoBuf.StringTable strings = this.this$0.getProtoForNames().getStrings();
                Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                ProtoBuf.QualifiedNameTable qualifiedNames = this.this$0.getProtoForNames().getQualifiedNames();
                Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                return new NameResolverImpl(strings, qualifiedNames);
            }
        });
        this.classDataFinder = LazyKt.lazy(new Function0<KlibMetadataClassDataFinder>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment$classDataFinder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KlibMetadataClassDataFinder invoke() {
                return new KlibMetadataClassDataFinder(this.this$0.getProtoForNames(), this.this$0.getNameResolver(), deserializedContainerSource);
            }
        });
        this._memberScope = LazyKt.lazy(new Function0<DeserializedPackageMemberScope>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment$_memberScope$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeserializedPackageMemberScope invoke() {
                KlibMetadataPackageFragment klibMetadataPackageFragment = this.this$0;
                KlibMetadataPackageFragment klibMetadataPackageFragment2 = klibMetadataPackageFragment;
                ProtoBuf.Package r2 = klibMetadataPackageFragment.getProto().getPackage();
                Intrinsics.checkNotNullExpressionValue(r2, "getPackage(...)");
                NameResolverImpl nameResolver = this.this$0.getNameResolver();
                KlibMetadataVersion klibMetadataVersion = KlibMetadataVersion.INSTANCE;
                DeserializedContainerSource deserializedContainerSource2 = deserializedContainerSource;
                DeserializationComponents components = this.this$0.getComponents();
                String str = "scope for " + this.this$0;
                final KlibMetadataPackageFragment klibMetadataPackageFragment3 = this.this$0;
                return new DeserializedPackageMemberScope(klibMetadataPackageFragment2, r2, nameResolver, klibMetadataVersion, deserializedContainerSource2, components, str, new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment$_memberScope$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Collection<? extends Name> invoke() {
                        return klibMetadataPackageFragment3.loadClassNames();
                    }
                });
            }
        });
        this.classifierNames = LazyKt.lazy(new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataPackageFragment$classifierNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.addAll(this.this$0.loadClassNames());
                List<ProtoBuf.TypeAlias> typeAliasList = this.this$0.getProtoForNames().getPackage().getTypeAliasList();
                Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
                KlibMetadataPackageFragment klibMetadataPackageFragment = this.this$0;
                Iterator<T> it = typeAliasList.iterator();
                while (it.hasNext()) {
                    linkedHashSet.add(NameResolverUtilKt.getName(klibMetadataPackageFragment.getNameResolver(), ((ProtoBuf.TypeAlias) it.next()).getName()));
                }
                return linkedHashSet;
            }
        });
    }

    public final DeserializationComponents getComponents() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents != null) {
            return deserializationComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    public final void setComponents(DeserializationComponents deserializationComponents) {
        Intrinsics.checkNotNullParameter(deserializationComponents, "<set-?>");
        this.components = deserializationComponents;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public void initialize(DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        setComponents(components);
    }

    public ProtoBuf.PackageFragment getProto() {
        return getProtoForNames();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NameResolverImpl getNameResolver() {
        return (NameResolverImpl) this.nameResolver.getValue();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public KlibMetadataClassDataFinder getClassDataFinder() {
        return (KlibMetadataClassDataFinder) this.classDataFinder.getValue();
    }

    private final DeserializedPackageMemberScope get_memberScope() {
        return (DeserializedPackageMemberScope) this._memberScope.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    /* JADX INFO: renamed from: getMemberScope */
    public DeserializedPackageMemberScope getScope() {
        return get_memberScope();
    }

    private final Set<Name> getClassifierNames() {
        return (Set) this.classifierNames.getValue();
    }

    public final boolean hasTopLevelClassifier(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getClassifierNames().contains(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<Name> loadClassNames() {
        List<Integer> listEmptyList = (List) getProtoForNames().getExtension(KlibMetadataProtoBuf.className);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Integer num : listEmptyList) {
            NameResolverImpl nameResolver = getNameResolver();
            Intrinsics.checkNotNull(num);
            ClassId classId = NameResolverUtilKt.getClassId(nameResolver, num.intValue());
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            if (classId.isNestedClass()) {
                shortClassName = null;
            }
            if (shortClassName != null) {
                arrayList.add(shortClassName);
            }
        }
        return arrayList;
    }
}
