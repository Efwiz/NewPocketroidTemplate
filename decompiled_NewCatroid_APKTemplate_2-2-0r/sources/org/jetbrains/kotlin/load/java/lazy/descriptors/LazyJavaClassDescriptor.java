package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.MappingUtilKt;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.ScopesHolderForClass;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.FakePureImplementationsProvider;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.UtilsKt;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.lazy.ContextKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotationsKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributes;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributesKt;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.InnerClassesScopeWrapper;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: LazyJavaClassDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 X2\u00020\u00012\u00020\u0002:\u0002XYB)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u001f\u00101\u001a\u00020\u00002\u0006\u00102\u001a\u0002032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0000¢\u0006\u0002\b4J\n\u00105\u001a\u0004\u0018\u00010\nH\u0016J\u000e\u00106\u001a\b\u0012\u0004\u0012\u0002070\u0013H\u0016J\u000e\u00108\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0016J\n\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020\u001cH\u0016J\b\u0010<\u001a\u00020\u001eH\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020\n0>H\u0016J\b\u0010?\u001a\u00020@H\u0016J\b\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u00020@H\u0016J\b\u0010D\u001a\u00020)H\u0016J\u0010\u0010D\u001a\u00020)2\u0006\u0010E\u001a\u00020FH\u0014J\n\u0010G\u001a\u0004\u0018\u000107H\u0016J\u0010\u0010H\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010IH\u0016J\b\u0010J\u001a\u00020KH\u0016J\b\u0010L\u001a\u00020\u0018H\u0016J\b\u0010M\u001a\u00020\u0018H\u0016J\b\u0010N\u001a\u00020\u0018H\u0016J\b\u0010O\u001a\u00020\u0018H\u0016J\b\u0010P\u001a\u00020\u0018H\u0016J\b\u0010Q\u001a\u00020\u0018H\u0016J\b\u0010R\u001a\u00020\u0018H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010S\u001a\u00020\u0018H\u0016J\b\u0010T\u001a\u00020\u0018H\u0016J\b\u0010U\u001a\u00020VH\u0016J\u0006\u0010W\u001a\u00020\u0018R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b!\u0010\"R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010,\u001a\u00060-R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorBase;", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "outerContext", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "jClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "additionalSupertypeClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "c", "declaredParameters", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "innerClassesScope", "Lorg/jetbrains/kotlin/resolve/scopes/InnerClassesScopeWrapper;", "isInner", "", "getJClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "moduleAnnotations", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getModuleAnnotations", "()Ljava/util/List;", "moduleAnnotations$delegate", "Lkotlin/Lazy;", "getOuterContext", "()Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "scopeHolder", "Lorg/jetbrains/kotlin/descriptors/ScopesHolderForClass;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassMemberScope;", "staticScope", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope;", "typeConstructor", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassDescriptor$LazyJavaClassTypeConstructor;", "unsubstitutedMemberScope", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "javaResolverCache", "Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;", "copy$descriptors_jvm", "getCompanionObjectDescriptor", "getConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getDeclaredTypeParameters", "getDefaultFunctionTypeForSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "getKind", "getModality", "getSealedSubclasses", "", "getStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedInnerClassesScope", "getUnsubstitutedMemberScope", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isFun", "isInline", "isRecord", "isValue", "toString", "", "wasScopeContentRequested", "Companion", "LazyJavaClassTypeConstructor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaClassDescriptor extends ClassDescriptorBase implements JavaClassDescriptor {
    private static final Set<String> PUBLIC_METHOD_NAMES_IN_OBJECT = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode", "getClass", "wait", "notify", "notifyAll", "toString"});
    private final ClassDescriptor additionalSupertypeClassDescriptor;
    private final Annotations annotations;
    private final LazyJavaResolverContext c;
    private final NotNullLazyValue<List<TypeParameterDescriptor>> declaredParameters;
    private final InnerClassesScopeWrapper innerClassesScope;
    private final boolean isInner;
    private final JavaClass jClass;
    private final ClassKind kind;
    private final Modality modality;

    /* JADX INFO: renamed from: moduleAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy moduleAnnotations;
    private final LazyJavaResolverContext outerContext;
    private final ScopesHolderForClass<LazyJavaClassMemberScope> scopeHolder;
    private final LazyJavaStaticClassScope staticScope;
    private final LazyJavaClassTypeConstructor typeConstructor;
    private final LazyJavaClassMemberScope unsubstitutedMemberScope;
    private final Visibility visibility;

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public ClassDescriptor mo6346getCompanionObjectDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject */
    public boolean getIsCompanionObject() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return false;
    }

    public /* synthetic */ LazyJavaClassDescriptor(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaClass javaClass, ClassDescriptor classDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, declarationDescriptor, javaClass, (i & 8) != 0 ? null : classDescriptor);
    }

    public final LazyJavaResolverContext getOuterContext() {
        return this.outerContext;
    }

    public final JavaClass getJClass() {
        return this.jClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaClassDescriptor(LazyJavaResolverContext outerContext, DeclarationDescriptor containingDeclaration, JavaClass jClass, ClassDescriptor classDescriptor) {
        ClassKind classKind;
        Modality modalityConvertFromFlags;
        super(outerContext.getStorageManager(), containingDeclaration, jClass.getName(), outerContext.getComponents().getSourceElementFactory().source(jClass), false);
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        this.outerContext = outerContext;
        this.jClass = jClass;
        this.additionalSupertypeClassDescriptor = classDescriptor;
        LazyJavaResolverContext lazyJavaResolverContextChildForClassOrPackage$default = ContextKt.childForClassOrPackage$default(outerContext, this, jClass, 0, 4, null);
        this.c = lazyJavaResolverContextChildForClassOrPackage$default;
        LazyJavaClassDescriptor lazyJavaClassDescriptor = this;
        lazyJavaResolverContextChildForClassOrPackage$default.getComponents().getJavaResolverCache().recordClass(jClass, lazyJavaClassDescriptor);
        jClass.getLightClassOriginKind();
        this.moduleAnnotations = LazyKt.lazy(new Function0<List<? extends JavaAnnotation>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor$moduleAnnotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends JavaAnnotation> invoke() {
                ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) this.this$0);
                if (classId != null) {
                    return this.this$0.getOuterContext().getComponents().getJavaModuleResolver().getAnnotationsForModuleOwnerOfClass(classId);
                }
                return null;
            }
        });
        if (jClass.mo7261isAnnotationType()) {
            classKind = ClassKind.ANNOTATION_CLASS;
        } else if (jClass.mo7265isInterface()) {
            classKind = ClassKind.INTERFACE;
        } else {
            classKind = jClass.mo7263isEnum() ? ClassKind.ENUM_CLASS : ClassKind.CLASS;
        }
        this.kind = classKind;
        if (jClass.mo7261isAnnotationType() || jClass.mo7263isEnum()) {
            modalityConvertFromFlags = Modality.FINAL;
        } else {
            modalityConvertFromFlags = Modality.INSTANCE.convertFromFlags(jClass.isSealed(), jClass.isSealed() || jClass.mo7260isAbstract() || jClass.mo7265isInterface(), !jClass.mo7264isFinal());
        }
        this.modality = modalityConvertFromFlags;
        this.visibility = jClass.getVisibility();
        this.isInner = (jClass.getOuterClass() == null || jClass.mo7267isStatic()) ? false : true;
        this.typeConstructor = new LazyJavaClassTypeConstructor();
        LazyJavaClassMemberScope lazyJavaClassMemberScope = new LazyJavaClassMemberScope(lazyJavaResolverContextChildForClassOrPackage$default, lazyJavaClassDescriptor, jClass, classDescriptor != null, null, 16, null);
        this.unsubstitutedMemberScope = lazyJavaClassMemberScope;
        this.scopeHolder = ScopesHolderForClass.INSTANCE.create(lazyJavaClassDescriptor, lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager(), lazyJavaResolverContextChildForClassOrPackage$default.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner(), new Function1<KotlinTypeRefiner, LazyJavaClassMemberScope>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor$scopeHolder$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LazyJavaClassMemberScope invoke(KotlinTypeRefiner it) {
                Intrinsics.checkNotNullParameter(it, "it");
                LazyJavaResolverContext lazyJavaResolverContext = this.this$0.c;
                LazyJavaClassDescriptor lazyJavaClassDescriptor2 = this.this$0;
                return new LazyJavaClassMemberScope(lazyJavaResolverContext, lazyJavaClassDescriptor2, lazyJavaClassDescriptor2.getJClass(), this.this$0.additionalSupertypeClassDescriptor != null, this.this$0.unsubstitutedMemberScope);
            }
        });
        this.innerClassesScope = new InnerClassesScopeWrapper(lazyJavaClassMemberScope);
        this.staticScope = new LazyJavaStaticClassScope(lazyJavaResolverContextChildForClassOrPackage$default, jClass, this);
        this.annotations = LazyJavaAnnotationsKt.resolveAnnotations(lazyJavaResolverContextChildForClassOrPackage$default, jClass);
        this.declaredParameters = lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager().createLazyValue(new Function0<List<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor$declaredParameters$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TypeParameterDescriptor> invoke() {
                List<JavaTypeParameter> typeParameters = this.this$0.getJClass().mo7258getTypeParameters();
                LazyJavaClassDescriptor lazyJavaClassDescriptor2 = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                for (JavaTypeParameter javaTypeParameter : typeParameters) {
                    TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = lazyJavaClassDescriptor2.c.getTypeParameterResolver().resolveTypeParameter(javaTypeParameter);
                    if (typeParameterDescriptorResolveTypeParameter == null) {
                        throw new AssertionError("Parameter " + javaTypeParameter + " surely belongs to class " + lazyJavaClassDescriptor2.getJClass() + ", so it must be resolved");
                    }
                    arrayList.add(typeParameterDescriptorResolveTypeParameter);
                }
                return arrayList;
            }
        });
    }

    public final List<JavaAnnotation> getModuleAnnotations() {
        return (List) this.moduleAnnotations.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
    public boolean isRecord() {
        return this.jClass.mo7266isRecord();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        if (Intrinsics.areEqual(this.visibility, DescriptorVisibilities.PRIVATE) && this.jClass.getOuterClass() == null) {
            DescriptorVisibility descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNull(descriptorVisibility);
            return descriptorVisibility;
        }
        return UtilsKt.toDescriptorVisibility(this.visibility);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner, reason: from getter */
    public boolean getIsInner() {
        return this.isInner;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public LazyJavaClassMemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return (LazyJavaClassMemberScope) this.scopeHolder.getScope(kotlinTypeRefiner);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getUnsubstitutedInnerClassesScope() {
        return this.innerClassesScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getStaticScope() {
        return this.staticScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public LazyJavaClassMemberScope getUnsubstitutedMemberScope() {
        MemberScope unsubstitutedMemberScope = super.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNull(unsubstitutedMemberScope, "null cannot be cast to non-null type org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope");
        return (LazyJavaClassMemberScope) unsubstitutedMemberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ClassConstructorDescriptor> getConstructors() {
        return this.unsubstitutedMemberScope.getConstructors$descriptors_jvm().invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.declaredParameters.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return this.c.getComponents().getSamConversionResolver().resolveFunctionTypeIfSamInterface(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        if (this.kind != ClassKind.INTERFACE) {
            return true;
        }
        boolean z = false;
        for (JavaMethod javaMethod : this.jClass.mo7255getMethods()) {
            if (javaMethod.mo7260isAbstract() && javaMethod.mo7258getTypeParameters().isEmpty() && !PUBLIC_METHOD_NAMES_IN_OBJECT.contains(javaMethod.getName().getIdentifier())) {
                if (z) {
                    return true;
                }
                z = true;
            }
        }
        Collection<JavaMethod> collectionMo7255getMethods = this.jClass.mo7255getMethods();
        if (!(collectionMo7255getMethods instanceof Collection) || !collectionMo7255getMethods.isEmpty()) {
            for (JavaMethod javaMethod2 : collectionMo7255getMethods) {
                if (!javaMethod2.mo7260isAbstract() && javaMethod2.mo7258getTypeParameters().isEmpty()) {
                    return false;
                }
            }
        }
        List supertypes = this.typeConstructor.mo7438getSupertypes();
        if (!(supertypes instanceof Collection) || !supertypes.isEmpty()) {
            Iterator it = supertypes.iterator();
            while (it.hasNext()) {
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = ((KotlinType) it.next()).getConstructor().getThis$0();
                LazyJavaClassDescriptor lazyJavaClassDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof LazyJavaClassDescriptor ? (LazyJavaClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
                if (lazyJavaClassDescriptor != null && lazyJavaClassDescriptor.isDefinitelyNotSamInterface()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean wasScopeContentRequested() {
        return getUnsubstitutedMemberScope().wasContentRequested() || this.staticScope.wasContentRequested();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        if (this.modality == Modality.SEALED) {
            JavaTypeAttributes attributes$default = JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null);
            Collection<JavaClassifierType> permittedTypes = this.jClass.getPermittedTypes();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = permittedTypes.iterator();
            while (it.hasNext()) {
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = this.c.getTypeResolver().transformJavaType((JavaClassifierType) it.next(), attributes$default).getConstructor().getThis$0();
                ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
                if (classDescriptor != null) {
                    arrayList.add(classDescriptor);
                }
            }
            return CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor$getSealedSubclasses$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t).asString(), DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t2).asString());
                }
            });
        }
        return CollectionsKt.emptyList();
    }

    public String toString() {
        return "Lazy Java class " + DescriptorUtilsKt.getFqNameUnsafe(this);
    }

    /* JADX INFO: compiled from: LazyJavaClassDescriptor.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\rH\u0002J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassDescriptor$LazyJavaClassTypeConstructor;", "Lorg/jetbrains/kotlin/types/AbstractClassTypeConstructor;", "(Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassDescriptor;)V", "parameters", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "computeSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getParameters", "getPurelyImplementedSupertype", "getPurelyImplementsFqNameFromAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "isDenotable", "", "toString", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class LazyJavaClassTypeConstructor extends AbstractClassTypeConstructor {
        private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        public LazyJavaClassTypeConstructor() {
            super(LazyJavaClassDescriptor.this.c.getStorageManager());
            this.parameters = LazyJavaClassDescriptor.this.c.getStorageManager().createLazyValue(new Function0<List<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor$LazyJavaClassTypeConstructor$parameters$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends TypeParameterDescriptor> invoke() {
                    return TypeParameterUtilsKt.computeConstructorTypeParameters(lazyJavaClassDescriptor);
                }
            });
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            return this.parameters.invoke();
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            Collection<JavaClassifierType> supertypes = LazyJavaClassDescriptor.this.getJClass().getSupertypes();
            ArrayList arrayList = new ArrayList(supertypes.size());
            ArrayList arrayList2 = new ArrayList(0);
            KotlinType purelyImplementedSupertype = getPurelyImplementedSupertype();
            Iterator<JavaClassifierType> it = supertypes.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                JavaClassifierType next = it.next();
                KotlinType kotlinTypeEnhanceSuperType = LazyJavaClassDescriptor.this.c.getComponents().getSignatureEnhancement().enhanceSuperType(LazyJavaClassDescriptor.this.c.getTypeResolver().transformJavaType(next, JavaTypeAttributesKt.toAttributes$default(TypeUsage.SUPERTYPE, false, false, null, 7, null)), LazyJavaClassDescriptor.this.c);
                if (kotlinTypeEnhanceSuperType.getConstructor().getThis$0() instanceof NotFoundClasses.MockClassDescriptor) {
                    arrayList2.add(next);
                }
                if (!Intrinsics.areEqual(kotlinTypeEnhanceSuperType.getConstructor(), purelyImplementedSupertype != null ? purelyImplementedSupertype.getConstructor() : null) && !KotlinBuiltIns.isAnyOrNullableAny(kotlinTypeEnhanceSuperType)) {
                    arrayList.add(kotlinTypeEnhanceSuperType);
                }
            }
            ArrayList arrayList3 = arrayList;
            ClassDescriptor classDescriptor = LazyJavaClassDescriptor.this.additionalSupertypeClassDescriptor;
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, classDescriptor != null ? MappingUtilKt.createMappedTypeParametersSubstitution(classDescriptor, LazyJavaClassDescriptor.this).buildSubstitutor().substitute(classDescriptor.getDefaultType(), Variance.INVARIANT) : null);
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, purelyImplementedSupertype);
            if (!arrayList2.isEmpty()) {
                ErrorReporter errorReporter = LazyJavaClassDescriptor.this.c.getComponents().getErrorReporter();
                ClassDescriptor classDescriptorMo7434getDeclarationDescriptor = getThis$0();
                ArrayList<JavaType> arrayList4 = arrayList2;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                for (JavaType javaType : arrayList4) {
                    Intrinsics.checkNotNull(javaType, "null cannot be cast to non-null type org.jetbrains.kotlin.load.java.structure.JavaClassifierType");
                    arrayList5.add(((JavaClassifierType) javaType).getPresentableText());
                }
                errorReporter.reportIncompleteHierarchy(classDescriptorMo7434getDeclarationDescriptor, arrayList5);
            }
            return !arrayList3.isEmpty() ? CollectionsKt.toList(arrayList) : CollectionsKt.listOf(LazyJavaClassDescriptor.this.c.getModule().getBuiltIns().getAnyType());
        }

        private final KotlinType getPurelyImplementedSupertype() {
            FqName purelyImplementedInterface;
            ArrayList arrayList;
            FqName purelyImplementsFqNameFromAnnotation = getPurelyImplementsFqNameFromAnnotation();
            if (purelyImplementsFqNameFromAnnotation == null || purelyImplementsFqNameFromAnnotation.isRoot() || !purelyImplementsFqNameFromAnnotation.startsWith(StandardNames.BUILT_INS_PACKAGE_NAME)) {
                purelyImplementsFqNameFromAnnotation = null;
            }
            if (purelyImplementsFqNameFromAnnotation == null) {
                purelyImplementedInterface = FakePureImplementationsProvider.INSTANCE.getPurelyImplementedInterface(DescriptorUtilsKt.getFqNameSafe(LazyJavaClassDescriptor.this));
                if (purelyImplementedInterface == null) {
                    return null;
                }
            } else {
                purelyImplementedInterface = purelyImplementsFqNameFromAnnotation;
            }
            ClassDescriptor classDescriptorResolveTopLevelClass = DescriptorUtilsKt.resolveTopLevelClass(LazyJavaClassDescriptor.this.c.getModule(), purelyImplementedInterface, NoLookupLocation.FROM_JAVA_LOADER);
            if (classDescriptorResolveTopLevelClass == null) {
                return null;
            }
            int size = classDescriptorResolveTopLevelClass.getTypeConstructor().getParameters().size();
            List<TypeParameterDescriptor> parameters = LazyJavaClassDescriptor.this.getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            int size2 = parameters.size();
            if (size2 == size) {
                List<TypeParameterDescriptor> list = parameters;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new TypeProjectionImpl(Variance.INVARIANT, ((TypeParameterDescriptor) it.next()).getDefaultType()));
                }
                arrayList = arrayList2;
            } else {
                if (size2 != 1 || size <= 1 || purelyImplementsFqNameFromAnnotation != null) {
                    return null;
                }
                TypeProjectionImpl typeProjectionImpl = new TypeProjectionImpl(Variance.INVARIANT, ((TypeParameterDescriptor) CollectionsKt.single((List) parameters)).getDefaultType());
                IntRange intRange = new IntRange(1, size);
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
                Iterator<Integer> it2 = intRange.iterator();
                while (it2.hasNext()) {
                    ((IntIterator) it2).nextInt();
                    arrayList3.add(typeProjectionImpl);
                }
                arrayList = arrayList3;
            }
            return KotlinTypeFactory.simpleNotNullType(TypeAttributes.INSTANCE.getEmpty(), classDescriptorResolveTopLevelClass, arrayList);
        }

        private final FqName getPurelyImplementsFqNameFromAnnotation() {
            String value;
            Annotations annotations = LazyJavaClassDescriptor.this.getAnnotations();
            FqName PURELY_IMPLEMENTS_ANNOTATION = JvmAnnotationNames.PURELY_IMPLEMENTS_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(PURELY_IMPLEMENTS_ANNOTATION, "PURELY_IMPLEMENTS_ANNOTATION");
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = annotations.mo6752findAnnotation(PURELY_IMPLEMENTS_ANNOTATION);
            if (annotationDescriptorMo6752findAnnotation == null) {
                return null;
            }
            Object objSingleOrNull = CollectionsKt.singleOrNull(annotationDescriptorMo6752findAnnotation.getAllValueArguments().values());
            StringValue stringValue = objSingleOrNull instanceof StringValue ? (StringValue) objSingleOrNull : null;
            if (stringValue == null || (value = stringValue.getValue()) == null || !FqNamesUtilKt.isValidJavaFqName(value)) {
                return null;
            }
            return new FqName(value);
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return LazyJavaClassDescriptor.this.c.getComponents().getSupertypeLoopChecker();
        }

        @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassDescriptor getThis$0() {
            return LazyJavaClassDescriptor.this;
        }

        public String toString() {
            String strAsString = LazyJavaClassDescriptor.this.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
    }

    public final LazyJavaClassDescriptor copy$descriptors_jvm(JavaResolverCache javaResolverCache, ClassDescriptor additionalSupertypeClassDescriptor) {
        Intrinsics.checkNotNullParameter(javaResolverCache, "javaResolverCache");
        LazyJavaResolverContext lazyJavaResolverContext = this.c;
        LazyJavaResolverContext lazyJavaResolverContextReplaceComponents = ContextKt.replaceComponents(lazyJavaResolverContext, lazyJavaResolverContext.getComponents().replace(javaResolverCache));
        DeclarationDescriptor containingDeclaration = getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return new LazyJavaClassDescriptor(lazyJavaResolverContextReplaceComponents, containingDeclaration, this.jClass, additionalSupertypeClassDescriptor);
    }
}
