package org.jetbrains.kotlin.load.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.PackagePartClassUtils;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.JvmPackagePartProto;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.scopes.ChainedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;

/* JADX INFO: compiled from: IncrementalPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002./BC\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\u001e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00162\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0016J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020%0'2\u0006\u0010\"\u001a\u00020\u0016H\u0017J*\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\u0006\u0010\"\u001a\u00020\u00162\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020,0*H\u0016J\u0010\u0010-\u001a\u00020,2\u0006\u0010\"\u001a\u00020\u0016H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0016\u0012\b\u0012\u00060\u0017R\u00020\u00000\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "deserializationComponents", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "incrementalCache", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "target", "Lorg/jetbrains/kotlin/modules/TargetId;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;Lorg/jetbrains/kotlin/modules/TargetId;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;)V", "getDeserializationComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "fqNameToPackageFragment", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalPackageFragment;", "getIncrementalCache", "()Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getTarget", "()Lorg/jetbrains/kotlin/modules/TargetId;", "collectPackageFragments", "", "fqName", "packageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackageFragments", "", "getSubPackagesOf", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "isEmpty", "IncrementalMultifileClassPackageFragment", "IncrementalPackageFragment", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalPackageFragmentProvider implements PackageFragmentProviderOptimized {
    private final DeserializationComponents deserializationComponents;
    private final Map<FqName, IncrementalPackageFragment> fqNameToPackageFragment;
    private final IncrementalCache incrementalCache;
    private final KotlinClassFinder kotlinClassFinder;
    private final ModuleDescriptor moduleDescriptor;
    private final StorageManager storageManager;
    private final TargetId target;

    public IncrementalPackageFragmentProvider(Collection<? extends KtFile> sourceFiles, ModuleDescriptor moduleDescriptor, StorageManager storageManager, DeserializationComponents deserializationComponents, IncrementalCache incrementalCache, TargetId target, KotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(deserializationComponents, "deserializationComponents");
        Intrinsics.checkNotNullParameter(incrementalCache, "incrementalCache");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.moduleDescriptor = moduleDescriptor;
        this.storageManager = storageManager;
        this.deserializationComponents = deserializationComponents;
        this.incrementalCache = incrementalCache;
        this.target = target;
        this.kotlinClassFinder = kotlinClassFinder;
        List<KtFile> filesWithCallables = PackagePartClassUtils.getFilesWithCallables(sourceFiles);
        HashSet hashSet = new HashSet();
        Iterator<T> it = filesWithCallables.iterator();
        while (it.hasNext()) {
            hashSet.add(((KtFile) it.next()).getPackageFqName());
        }
        this.fqNameToPackageFragment = CollectionsKt.keysToMap(hashSet, new IncrementalPackageFragmentProvider$fqNameToPackageFragment$2(this));
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final DeserializationComponents getDeserializationComponents() {
        return this.deserializationComponents;
    }

    public final IncrementalCache getIncrementalCache() {
        return this.incrementalCache;
    }

    public final TargetId getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        CollectionsKt.addIfNotNull(packageFragments, this.fqNameToPackageFragment.get(fqName));
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return !this.fqNameToPackageFragment.containsKey(fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return kotlin.collections.CollectionsKt.listOfNotNull(this.fqNameToPackageFragment.get(fqName));
    }

    /* JADX INFO: compiled from: IncrementalPackageFragmentProvider.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\b\u0018\u00010\fR\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalPackageFragment;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;Lorg/jetbrains/kotlin/name/FqName;)V", "target", "Lorg/jetbrains/kotlin/modules/TargetId;", "getTarget", "()Lorg/jetbrains/kotlin/modules/TargetId;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getPackageFragmentForMultifileClass", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;", "multifileClassFqName", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class IncrementalPackageFragment extends PackageFragmentDescriptorImpl {
        final /* synthetic */ IncrementalPackageFragmentProvider this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IncrementalPackageFragment(IncrementalPackageFragmentProvider incrementalPackageFragmentProvider, FqName fqName) {
            super(incrementalPackageFragmentProvider.getModuleDescriptor(), fqName);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            this.this$0 = incrementalPackageFragmentProvider;
        }

        public final TargetId getTarget() {
            return this.this$0.getTarget();
        }

        public final IncrementalMultifileClassPackageFragment getPackageFragmentForMultifileClass(FqName multifileClassFqName) {
            Intrinsics.checkNotNullParameter(multifileClassFqName, "multifileClassFqName");
            JvmClassName jvmClassNameByFqNameWithoutInnerClasses = JvmClassName.byFqNameWithoutInnerClasses(multifileClassFqName);
            Intrinsics.checkNotNullExpressionValue(jvmClassNameByFqNameWithoutInnerClasses, "byFqNameWithoutInnerClasses(...)");
            IncrementalCache incrementalCache = this.this$0.getIncrementalCache();
            String internalName = jvmClassNameByFqNameWithoutInnerClasses.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            Collection<String> stableMultifileFacadeParts = incrementalCache.getStableMultifileFacadeParts(internalName);
            if (stableMultifileFacadeParts == null) {
                return null;
            }
            IncrementalPackageFragmentProvider incrementalPackageFragmentProvider = this.this$0;
            FqName fqNameParent = multifileClassFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            return new IncrementalMultifileClassPackageFragment(incrementalPackageFragmentProvider, jvmClassNameByFqNameWithoutInnerClasses, stableMultifileFacadeParts, fqNameParent);
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
        public MemberScope getMemberScope() {
            return MemberScope.Empty.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: IncrementalPackageFragmentProvider.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0011\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "facadeName", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "partsInternalNames", "", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;Ljava/util/Collection;Lorg/jetbrains/kotlin/name/FqName;)V", "getFacadeName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "memberScope", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getPartsInternalNames", "()Ljava/util/Collection;", "getMemberScope", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class IncrementalMultifileClassPackageFragment extends PackageFragmentDescriptorImpl {
        private final JvmClassName facadeName;
        private final NotNullLazyValue<MemberScope> memberScope;
        private final Collection<String> partsInternalNames;
        final /* synthetic */ IncrementalPackageFragmentProvider this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IncrementalMultifileClassPackageFragment(final IncrementalPackageFragmentProvider incrementalPackageFragmentProvider, JvmClassName facadeName, Collection<String> partsInternalNames, final FqName packageFqName) {
            super(incrementalPackageFragmentProvider.getModuleDescriptor(), packageFqName);
            Intrinsics.checkNotNullParameter(facadeName, "facadeName");
            Intrinsics.checkNotNullParameter(partsInternalNames, "partsInternalNames");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            this.this$0 = incrementalPackageFragmentProvider;
            this.facadeName = facadeName;
            this.partsInternalNames = partsInternalNames;
            this.memberScope = incrementalPackageFragmentProvider.getStorageManager().createLazyValue(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment$memberScope$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final MemberScope invoke() {
                    DeserializedPackageMemberScope deserializedPackageMemberScope;
                    JvmMetadataVersion metadataVersion;
                    KotlinClassHeader classHeader;
                    ChainedMemberScope.Companion companion = ChainedMemberScope.Companion;
                    String str = "Member scope for incremental compilation: union of multifile class parts data for " + this.this$0.getFacadeName();
                    Collection<String> partsInternalNames2 = this.this$0.getPartsInternalNames();
                    IncrementalPackageFragmentProvider incrementalPackageFragmentProvider2 = incrementalPackageFragmentProvider;
                    IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment incrementalMultifileClassPackageFragment = this.this$0;
                    FqName fqName = packageFqName;
                    ArrayList arrayList = new ArrayList();
                    for (String str2 : partsInternalNames2) {
                        JvmPackagePartProto packagePartData = incrementalPackageFragmentProvider2.getIncrementalCache().getPackagePartData(str2);
                        if (packagePartData != null) {
                            Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(packagePartData.getData(), packagePartData.getStrings());
                            JvmNameResolver jvmNameResolverComponent1 = packageDataFrom.component1();
                            ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
                            JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(str2);
                            Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
                            KotlinClassFinder kotlinClassFinder = incrementalPackageFragmentProvider2.kotlinClassFinder;
                            ClassId classId = ClassId.topLevel(jvmClassNameByInternalName.getFqNameForTopLevelClassMaybeWithDollars());
                            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                            KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(kotlinClassFinder, classId, DeserializationHelpersKt.jvmMetadataVersionOrDefault(incrementalPackageFragmentProvider2.getDeserializationComponents().getConfiguration()));
                            if (kotlinJvmBinaryClassFindKotlinClass == null || (classHeader = kotlinJvmBinaryClassFindKotlinClass.getClassHeader()) == null || (metadataVersion = classHeader.getMetadataVersion()) == null) {
                                metadataVersion = JvmMetadataVersion.INSTANCE;
                            }
                            JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
                            deserializedPackageMemberScope = new DeserializedPackageMemberScope(incrementalMultifileClassPackageFragment, packageComponent2, jvmNameResolver, metadataVersion, new JvmPackagePartSource(jvmClassNameByInternalName, incrementalMultifileClassPackageFragment.getFacadeName(), packageComponent2, jvmNameResolver, null, false, null, kotlinJvmBinaryClassFindKotlinClass, 112, null), incrementalPackageFragmentProvider2.getDeserializationComponents(), "scope for IncrementalMultifileClassPackageFragment for facadeName=" + incrementalMultifileClassPackageFragment.getFacadeName() + ", packageFqName=" + fqName + ", part " + jvmClassNameByInternalName, new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment$memberScope$1$1$1$1
                                @Override // kotlin.jvm.functions.Function0
                                public final Collection<? extends Name> invoke() {
                                    return kotlin.collections.CollectionsKt.emptyList();
                                }
                            });
                        } else {
                            deserializedPackageMemberScope = null;
                        }
                        DeserializedPackageMemberScope deserializedPackageMemberScope2 = deserializedPackageMemberScope;
                        if (deserializedPackageMemberScope2 != null) {
                            arrayList.add(deserializedPackageMemberScope2);
                        }
                    }
                    return companion.create(str, arrayList);
                }
            });
        }

        public final JvmClassName getFacadeName() {
            return this.facadeName;
        }

        public final Collection<String> getPartsInternalNames() {
            return this.partsInternalNames;
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
        public MemberScope getMemberScope() {
            return this.memberScope.invoke();
        }
    }
}
