package org.jetbrains.kotlin.types;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.CacheWithNotNullValues;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefinerKt;
import org.jetbrains.kotlin.types.checker.Ref;
import org.jetbrains.kotlin.types.checker.TypeRefinementSupport;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: KotlinTypeRefinerImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 02\u00020\u0001:\u00010B\u001f\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fH\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0017J-\u0010\u0018\u001a\u0002H\u0019\"\b\b\u0000\u0010\u0019*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00112\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001cH\u0017¢\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0017J\u0010\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u000eH\u0017J\u0012\u0010!\u001a\u0004\u0018\u00010\f2\u0006\u0010\"\u001a\u00020#H\u0017J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0%2\u0006\u0010\u001a\u001a\u00020\u0011H\u0017J\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020'H\u0017J\f\u0010(\u001a\u00020\u0007*\u00020\u000eH\u0002J\f\u0010)\u001a\u00020\u0007*\u00020\u000fH\u0002J\u000e\u0010*\u001a\u00020\u0007*\u0004\u0018\u00010#H\u0002J\f\u0010+\u001a\u00020\u0007*\u00020\u000eH\u0002J\f\u0010,\u001a\u00020\u0007*\u00020\u000fH\u0002J\f\u0010-\u001a\u00020\u0007*\u00020\u000fH\u0002J\u0014\u0010.\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010/\u001a\u00020\u0001H\u0002R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00070\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/types/KotlinTypeRefinerImpl;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "isStandalone", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Z)V", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "isRefinementNeededForTypeConstructorCache", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "refinedTypeCache", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "scopes", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "doRefineType", ModuleXmlParser.TYPE, "findClassAcrossModuleDependencies", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getOrPutScopeForClass", ExifInterface.LATITUDE_SOUTH, "classDescriptor", "compute", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isRefinementNeededForModule", "isRefinementNeededForTypeConstructor", "typeConstructor", "refineDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "refineSupertypes", "", "refineType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "areThereExpectSupertypes", "canBeCached", "isEnumEntryOrEnum", "isRefinementNeededForTypeConstructorNoCache", "needsRefinement", "needsRefinementHackForKtij24195", "refineWithRespectToAbbreviatedTypes", "refiner", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeRefinerImpl extends KotlinTypeRefiner {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CacheWithNotNullValues<ClassifierDescriptor, Boolean> isRefinementNeededForTypeConstructorCache;
    private boolean isStandalone;
    private final ModuleDescriptor moduleDescriptor;
    private final CacheWithNotNullValues<TypeConstructor, KotlinType> refinedTypeCache;
    private final CacheWithNotNullValues<ClassDescriptor, MemberScope> scopes;

    public /* synthetic */ KotlinTypeRefinerImpl(ModuleDescriptor moduleDescriptor, StorageManager storageManager, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, storageManager, z);
    }

    public KotlinTypeRefinerImpl(ModuleDescriptor moduleDescriptor, StorageManager storageManager) {
        Ref ref;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.moduleDescriptor = moduleDescriptor;
        this.refinedTypeCache = storageManager.createCacheWithNotNullValues();
        this.isRefinementNeededForTypeConstructorCache = storageManager.createCacheWithNotNullValues();
        this.scopes = storageManager.createCacheWithNotNullValues();
        if (this.isStandalone || (ref = (Ref) moduleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY())) == null) {
            return;
        }
        ref.setValue(new TypeRefinementSupport.Enabled(this));
    }

    private KotlinTypeRefinerImpl(ModuleDescriptor moduleDescriptor, StorageManager storageManager, boolean z) {
        this(moduleDescriptor, storageManager);
        this.isStandalone = z;
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypeRefiner
    public KotlinType refineType(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        KotlinType kotlinType = (KotlinType) type;
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (Intrinsics.areEqual(this$0 != null ? DescriptorUtilsKt.getModule(this$0) : null, this.moduleDescriptor)) {
            return kotlinType;
        }
        if (needsRefinementHackForKtij24195(kotlinType)) {
            return doRefineType(((AbbreviatedType) type).getAbbreviation());
        }
        return needsRefinement(kotlinType) ? doRefineType(kotlinType) : kotlinType;
    }

    private final KotlinType doRefineType(final KotlinType type) {
        if (canBeCached(type)) {
            return KotlinTypeRefinerImplKt.restoreAdditionalTypeInformation(this.refinedTypeCache.computeIfAbsent(type.getConstructor(), new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.types.KotlinTypeRefinerImpl$doRefineType$cached$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinType invoke() {
                    KotlinTypeRefinerImpl kotlinTypeRefinerImpl = this.this$0;
                    ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
                    Intrinsics.checkNotNull(this$0);
                    SimpleType defaultType = this$0.getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                    return kotlinTypeRefinerImpl.refineWithRespectToAbbreviatedTypes(defaultType, this.this$0);
                }
            }), type);
        }
        return refineWithRespectToAbbreviatedTypes(type, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType refineWithRespectToAbbreviatedTypes(KotlinType kotlinType, KotlinTypeRefiner kotlinTypeRefiner) {
        KotlinType kotlinTypeRefine;
        while (true) {
            kotlinTypeRefine = kotlinType.refine(kotlinTypeRefiner);
            if (!(kotlinTypeRefine instanceof AbbreviatedType) || Intrinsics.areEqual(kotlinTypeRefine, kotlinType)) {
                break;
            }
            kotlinType = kotlinTypeRefine;
        }
        return kotlinTypeRefine;
    }

    private final boolean needsRefinement(KotlinType kotlinType) {
        return isRefinementNeededForTypeConstructor(kotlinType.getConstructor());
    }

    private final boolean canBeCached(KotlinType kotlinType) {
        return kotlinType.getHasNotTrivialRefinementFactory() && kotlinType.getConstructor().getThis$0() != null;
    }

    private final boolean needsRefinementHackForKtij24195(KotlinType kotlinType) {
        ClassId classId;
        if (!(kotlinType instanceof AbbreviatedType)) {
            return false;
        }
        AbbreviatedType abbreviatedType = (AbbreviatedType) kotlinType;
        return (abbreviatedType.getAbbreviation().getConstructor().getThis$0() instanceof TypeAliasDescriptor) && (classId = DescriptorUtilsKt.getClassId(abbreviatedType.getExpandedType().getConstructor().getThis$0())) != null && FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.moduleDescriptor, classId) == null;
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public Collection<KotlinType> refineSupertypes(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KotlinType kotlinType : collection) {
            Intrinsics.checkNotNull(kotlinType);
            arrayList.add(refineType((KotlinTypeMarker) kotlinType));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public ClassifierDescriptor refineDescriptor(DeclarationDescriptor descriptor) {
        ClassId classId;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor instanceof ClassifierDescriptorWithTypeParameters) && (classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) descriptor)) != null) {
            return FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.moduleDescriptor, classId);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public ClassDescriptor findClassAcrossModuleDependencies(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return FindClassInModuleKt.findClassAcrossModuleDependencies(this.moduleDescriptor, classId);
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public boolean isRefinementNeededForModule(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        return this.moduleDescriptor != moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public boolean isRefinementNeededForTypeConstructor(final TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        ClassifierDescriptor this$0 = typeConstructor.getThis$0();
        if (this$0 == null) {
            return isRefinementNeededForTypeConstructorNoCache(typeConstructor);
        }
        return this.isRefinementNeededForTypeConstructorCache.computeIfAbsent(this$0, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.types.KotlinTypeRefinerImpl.isRefinementNeededForTypeConstructor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(KotlinTypeRefinerImpl.this.isRefinementNeededForTypeConstructorNoCache(typeConstructor));
            }
        }).booleanValue();
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
    public <S extends MemberScope> S getOrPutScopeForClass(ClassDescriptor classDescriptor, Function0<? extends S> compute) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(compute, "compute");
        MemberScope memberScopeComputeIfAbsent = this.scopes.computeIfAbsent(classDescriptor, compute);
        Intrinsics.checkNotNull(memberScopeComputeIfAbsent, "null cannot be cast to non-null type S of org.jetbrains.kotlin.types.KotlinTypeRefinerImpl.getOrPutScopeForClass");
        return (S) memberScopeComputeIfAbsent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isRefinementNeededForTypeConstructorNoCache(TypeConstructor typeConstructor) {
        return isEnumEntryOrEnum(typeConstructor.getThis$0()) || areThereExpectSupertypes(typeConstructor);
    }

    private final boolean isEnumEntryOrEnum(DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            return false;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
        return classDescriptor.getKind() == ClassKind.ENUM_CLASS || KotlinBuiltIns.isEnum(classDescriptor);
    }

    private final boolean areThereExpectSupertypes(TypeConstructor typeConstructor) {
        final kotlin.jvm.internal.Ref.BooleanRef booleanRef = new kotlin.jvm.internal.Ref.BooleanRef();
        List listListOf = CollectionsKt.listOf(typeConstructor);
        final AnonymousClass1 anonymousClass1 = new PropertyReference1() { // from class: org.jetbrains.kotlin.types.KotlinTypeRefinerImpl.areThereExpectSupertypes.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllDependentTypeConstructors(Lorg/jetbrains/kotlin/types/TypeConstructor;)Ljava/util/Collection;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return KotlinTypeRefinerImplKt.getAllDependentTypeConstructors((TypeConstructor) obj);
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allDependentTypeConstructors";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinPackage(KotlinTypeRefinerImplKt.class, "descriptors");
            }
        };
        DFS.dfs(listListOf, new DFS.Neighbors() { // from class: org.jetbrains.kotlin.types.KotlinTypeRefinerImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return KotlinTypeRefinerImpl.areThereExpectSupertypes$lambda$1(anonymousClass1, (TypeConstructor) obj);
            }
        }, new DFS.VisitedWithSet(), new DFS.AbstractNodeHandler<TypeConstructor, Unit>() { // from class: org.jetbrains.kotlin.types.KotlinTypeRefinerImpl.areThereExpectSupertypes.2
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7473result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7473result();
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(TypeConstructor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (KotlinTypeRefinerImplKt.isExpectClass(current)) {
                    ClassifierDescriptor this$0 = current.getThis$0();
                    if (!Intrinsics.areEqual(this$0 != null ? DescriptorUtilsKt.getModule(this$0) : null, KotlinTypeRefinerImpl.this.moduleDescriptor)) {
                        booleanRef.element = true;
                        return false;
                    }
                }
                return true;
            }
        });
        return booleanRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable areThereExpectSupertypes$lambda$1(KProperty1 tmp0, TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Iterable) tmp0.invoke(typeConstructor);
    }

    /* JADX INFO: compiled from: KotlinTypeRefinerImpl.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/KotlinTypeRefinerImpl$Companion;", "", "()V", "createStandaloneInstanceFor", "Lorg/jetbrains/kotlin/types/KotlinTypeRefinerImpl;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinTypeRefinerImpl createStandaloneInstanceFor(ModuleDescriptor moduleDescriptor) {
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
            Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
            return new KotlinTypeRefinerImpl(moduleDescriptor, NO_LOCKS, true, null);
        }
    }
}
