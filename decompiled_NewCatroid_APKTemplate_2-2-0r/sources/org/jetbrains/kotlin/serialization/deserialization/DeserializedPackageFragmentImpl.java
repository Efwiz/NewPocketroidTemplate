package org.jetbrains.kotlin.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: DeserializedPackageFragmentImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\b\u0010\u001a\u001a\u00020\u0010H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u0017X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragmentImpl;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "_memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "_proto", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "getMemberScope", "initialize", "", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeserializedPackageFragmentImpl extends DeserializedPackageFragment {
    private MemberScope _memberScope;
    private ProtoBuf.PackageFragment _proto;
    private final ProtoBasedClassDataFinder classDataFinder;
    private final DeserializedContainerSource containerSource;
    private final BinaryVersion metadataVersion;
    private final NameResolverImpl nameResolver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializedPackageFragmentImpl(FqName fqName, StorageManager storageManager, ModuleDescriptor module, ProtoBuf.PackageFragment proto, BinaryVersion metadataVersion, DeserializedContainerSource deserializedContainerSource) {
        super(fqName, storageManager, module);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.metadataVersion = metadataVersion;
        this.containerSource = deserializedContainerSource;
        ProtoBuf.StringTable strings = proto.getStrings();
        Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
        ProtoBuf.QualifiedNameTable qualifiedNames = proto.getQualifiedNames();
        Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
        NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
        this.nameResolver = nameResolverImpl;
        this.classDataFinder = new ProtoBasedClassDataFinder(proto, nameResolverImpl, metadataVersion, new Function1<ClassId, SourceElement>() { // from class: org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragmentImpl$classDataFinder$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SourceElement invoke(ClassId it) {
                Intrinsics.checkNotNullParameter(it, "it");
                DeserializedContainerSource deserializedContainerSource2 = this.this$0.containerSource;
                if (deserializedContainerSource2 != null) {
                    return deserializedContainerSource2;
                }
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                return NO_SOURCE;
            }
        });
        this._proto = proto;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final NameResolverImpl getNameResolver() {
        return this.nameResolver;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public ProtoBasedClassDataFinder getClassDataFinder() {
        return this.classDataFinder;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public void initialize(DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        ProtoBuf.PackageFragment packageFragment = this._proto;
        if (packageFragment == null) {
            throw new IllegalStateException("Repeated call to DeserializedPackageFragmentImpl::initialize".toString());
        }
        this._proto = null;
        ProtoBuf.Package r4 = packageFragment.getPackage();
        Intrinsics.checkNotNullExpressionValue(r4, "getPackage(...)");
        this._memberScope = new DeserializedPackageMemberScope(this, r4, this.nameResolver, this.metadataVersion, this.containerSource, components, "scope of " + this, new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragmentImpl.initialize.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends Name> invoke() {
                Collection<ClassId> allClassIds = DeserializedPackageFragmentImpl.this.getClassDataFinder().getAllClassIds();
                ArrayList arrayList = new ArrayList();
                for (Object obj : allClassIds) {
                    ClassId classId = (ClassId) obj;
                    if (!classId.isNestedClass() && !ClassDeserializer.INSTANCE.getBLACK_LIST().contains(classId)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((ClassId) it.next()).getShortClassName());
                }
                return arrayList3;
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    /* JADX INFO: renamed from: getMemberScope */
    public MemberScope getScope() {
        MemberScope memberScope = this._memberScope;
        if (memberScope != null) {
            return memberScope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("_memberScope");
        return null;
    }
}
