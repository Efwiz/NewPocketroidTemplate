package org.jetbrains.kotlin.serialization.deserialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.scopes.ChainedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: MetadataPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0016\u001a\u00020\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0015H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPackageFragment;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "metadataPartProvider", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "finder", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;)V", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataClassDataFinder;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/MetadataClassDataFinder;", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "memberScope", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "computeMemberScope", "getMemberScope", "hasTopLevelClass", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "initialize", "", "Companion", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataPackageFragment extends DeserializedPackageFragment {
    public static final String DOT_METADATA_FILE_EXTENSION = ".kotlin_metadata";
    public static final String METADATA_FILE_EXTENSION = "kotlin_metadata";
    private final MetadataClassDataFinder classDataFinder;
    private DeserializationComponents components;
    private final KotlinMetadataFinder finder;
    private final NotNullLazyValue<MemberScope> memberScope;
    private final MetadataPartProvider metadataPartProvider;

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public boolean hasTopLevelClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataPackageFragment(FqName fqName, StorageManager storageManager, ModuleDescriptor module, MetadataPartProvider metadataPartProvider, KotlinMetadataFinder finder) {
        super(fqName, storageManager, module);
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(metadataPartProvider, "metadataPartProvider");
        Intrinsics.checkNotNullParameter(finder, "finder");
        this.metadataPartProvider = metadataPartProvider;
        this.finder = finder;
        this.classDataFinder = new MetadataClassDataFinder(finder);
        this.memberScope = storageManager.createLazyValue(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment$memberScope$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                return this.this$0.computeMemberScope();
            }
        });
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public MetadataClassDataFinder getClassDataFinder() {
        return this.classDataFinder;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public void initialize(DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MemberScope computeMemberScope() throws IOException {
        DeserializationComponents deserializationComponents;
        DeserializationComponents deserializationComponents2;
        MetadataPartProvider metadataPartProvider = this.metadataPartProvider;
        String strAsString = getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<String> listFindMetadataPackageParts = metadataPartProvider.findMetadataPackageParts(strAsString);
        ArrayList arrayList = new ArrayList();
        for (String str : listFindMetadataPackageParts) {
            InputStream inputStreamFindMetadata = this.finder.findMetadata(new ClassId(getFqName(), Name.identifier(str)));
            if (inputStreamFindMetadata != null) {
                Triple<ProtoBuf.PackageFragment, NameResolverImpl, BuiltInsBinaryVersion> proto = MetadataUtilKt.readProto(inputStreamFindMetadata);
                ProtoBuf.PackageFragment packageFragmentComponent1 = proto.component1();
                NameResolverImpl nameResolverImplComponent2 = proto.component2();
                BuiltInsBinaryVersion builtInsBinaryVersionComponent3 = proto.component3();
                MetadataPackageFragment metadataPackageFragment = this;
                ProtoBuf.Package r12 = packageFragmentComponent1.getPackage();
                Intrinsics.checkNotNullExpressionValue(r12, "getPackage(...)");
                NameResolverImpl nameResolverImpl = nameResolverImplComponent2;
                BuiltInsBinaryVersion builtInsBinaryVersion = builtInsBinaryVersionComponent3;
                DeserializationComponents deserializationComponents3 = this.components;
                if (deserializationComponents3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("components");
                    deserializationComponents2 = null;
                } else {
                    deserializationComponents2 = deserializationComponents3;
                }
                arrayList.add(new DeserializedPackageMemberScope(metadataPackageFragment, r12, nameResolverImpl, builtInsBinaryVersion, null, deserializationComponents2, "scope with top-level callables and type aliases (no classes) for package part " + str + " of " + this, new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment.computeMemberScope.1
                    @Override // kotlin.jvm.functions.Function0
                    public final Collection<? extends Name> invoke() {
                        return CollectionsKt.emptyList();
                    }
                }));
            }
        }
        ProtoBuf.Package defaultInstance = ProtoBuf.Package.getDefaultInstance();
        ProtoBuf.StringTable defaultInstance2 = ProtoBuf.StringTable.getDefaultInstance();
        Intrinsics.checkNotNullExpressionValue(defaultInstance2, "getDefaultInstance(...)");
        ProtoBuf.QualifiedNameTable defaultInstance3 = ProtoBuf.QualifiedNameTable.getDefaultInstance();
        Intrinsics.checkNotNullExpressionValue(defaultInstance3, "getDefaultInstance(...)");
        NameResolverImpl nameResolverImpl2 = new NameResolverImpl(defaultInstance2, defaultInstance3);
        BuiltInsBinaryVersion builtInsBinaryVersion2 = BuiltInsBinaryVersion.INSTANCE;
        DeserializationComponents deserializationComponents4 = this.components;
        if (deserializationComponents4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
            deserializationComponents = null;
        } else {
            deserializationComponents = deserializationComponents4;
        }
        arrayList.add(new DeserializedPackageMemberScope(defaultInstance, nameResolverImpl2, builtInsBinaryVersion2, deserializationComponents, "scope for all classes of " + this, AnonymousClass3.INSTANCE) { // from class: org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment.computeMemberScope.2
            @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public boolean definitelyDoesNotContainName(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return false;
            }

            @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
            public Set<Name> getClassifierNames() {
                return null;
            }

            @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope, org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
            protected Set<Name> getNonDeclaredClassifierNames() {
                return null;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(MetadataPackageFragment.this, defaultInstance, nameResolverImpl2, builtInsBinaryVersion2, null, deserializationComponents, str, anonymousClass3);
                Intrinsics.checkNotNull(defaultInstance);
            }

            @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope, org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberScope
            protected boolean hasClass(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return MetadataPackageFragment.this.hasTopLevelClass(name2);
            }
        });
        return ChainedMemberScope.INSTANCE.create(".kotlin_metadata parts scope of " + this, arrayList);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment$computeMemberScope$3, reason: invalid class name */
    /* JADX INFO: compiled from: MetadataPackageFragmentProvider.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/name/Name;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass3 extends Lambda implements Function0<Collection<? extends Name>> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Collection<? extends Name> invoke() {
            return CollectionsKt.emptyList();
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    /* JADX INFO: renamed from: getMemberScope */
    public MemberScope getScope() {
        return this.memberScope.invoke();
    }
}
