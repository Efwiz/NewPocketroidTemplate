package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ScopesHolderForClass;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.lazy.ContextKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.InnerClassesScopeWrapper;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.ClassTypeConstructorImpl;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: SyntheticJavaClassDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 V2\u00020\u00012\u00020\u0002:\u0002VWB\u008d\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015\u0012\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001c¢\u0006\u0002\u0010\u001fJ\n\u00103\u001a\u0004\u0018\u00010\bH\u0016J\u000e\u00104\u001a\b\u0012\u0004\u0012\u0002050\u0015H\u0016J\b\u00106\u001a\u000207H\u0016J\u000e\u00108\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\n\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020\nH\u0016J\b\u0010<\u001a\u00020\fH\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0016J\b\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020AH\u0016J\b\u0010B\u001a\u00020CH\u0016J\b\u0010D\u001a\u00020AH\u0016J\b\u0010E\u001a\u00020+H\u0016J\u0010\u0010E\u001a\u00020+2\u0006\u0010F\u001a\u00020GH\u0014J\n\u0010H\u001a\u0004\u0018\u000105H\u0016J\u0010\u0010I\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010JH\u0016J\b\u0010K\u001a\u00020\u000eH\u0016J\b\u0010L\u001a\u00020\u0010H\u0016J\b\u0010M\u001a\u00020\u0010H\u0016J\b\u0010N\u001a\u00020\u0010H\u0016J\b\u0010O\u001a\u00020\u0010H\u0016J\b\u0010P\u001a\u00020\u0010H\u0016J\b\u0010Q\u001a\u00020\u0010H\u0016J\b\u0010R\u001a\u00020\u0010H\u0016J\b\u0010S\u001a\u00020\u0010H\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010T\u001a\u00020\u0010H\u0016J\b\u0010U\u001a\u00020\u001dH\u0016R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001f\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001c¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00060(R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/SyntheticJavaClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractClassDescriptor;", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "outerContext", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "name", "Lorg/jetbrains/kotlin/name/Name;", "outerClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isInner", "", "isRecord", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "declaredTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "sealedSubclasses", "", "supertypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "attributes", "", "", "", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassKind;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZZLorg/jetbrains/kotlin/descriptors/annotations/Annotations;Ljava/util/List;Ljava/util/Collection;Ljava/util/List;Ljava/util/Map;)V", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAttributes", "()Ljava/util/Map;", "c", "innerClassesScope", "Lorg/jetbrains/kotlin/resolve/scopes/InnerClassesScopeWrapper;", "jClass", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/SyntheticJavaClassDescriptor$FakeJavaClass;", "scopeHolder", "Lorg/jetbrains/kotlin/descriptors/ScopesHolderForClass;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassMemberScope;", "sourceElement", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "staticScope", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope;", "typeConstructor", "Lorg/jetbrains/kotlin/types/ClassTypeConstructorImpl;", "unsubstitutedMemberScope", "getCompanionObjectDescriptor", "getConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDeclaredTypeParameters", "getDefaultFunctionTypeForSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "getKind", "getModality", "getSealedSubclasses", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedInnerClassesScope", "getUnsubstitutedMemberScope", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "getVisibility", "isActual", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isExternal", "isFun", "isInline", "isValue", "toString", "Companion", "FakeJavaClass", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticJavaClassDescriptor extends AbstractClassDescriptor implements JavaClassDescriptor {
    private static final Set<String> PUBLIC_METHOD_NAMES_IN_OBJECT = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode", "getClass", "wait", "notify", "notifyAll", "toString"});
    private final Annotations annotations;
    private final Map<String, Object> attributes;
    private final LazyJavaResolverContext c;
    private final ClassKind classKind;
    private final List<TypeParameterDescriptor> declaredTypeParameters;
    private final InnerClassesScopeWrapper innerClassesScope;
    private final boolean isInner;
    private final boolean isRecord;
    private final FakeJavaClass jClass;
    private final Modality modality;
    private final ClassDescriptor outerClass;
    private final LazyJavaResolverContext outerContext;
    private final ScopesHolderForClass<LazyJavaClassMemberScope> scopeHolder;
    private final Collection<ClassDescriptor> sealedSubclasses;
    private final JavaSourceElement sourceElement;
    private final LazyJavaStaticClassScope staticScope;
    private final ClassTypeConstructorImpl typeConstructor;
    private final LazyJavaClassMemberScope unsubstitutedMemberScope;
    private final DescriptorVisibility visibility;

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
    /* JADX INFO: renamed from: isActual */
    public boolean getIsActual() {
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

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
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

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    public final Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SyntheticJavaClassDescriptor(LazyJavaResolverContext outerContext, Name name2, ClassDescriptor outerClass, ClassKind classKind, Modality modality, DescriptorVisibility visibility, boolean z, boolean z2, Annotations annotations, List<? extends TypeParameterDescriptor> declaredTypeParameters, Collection<? extends ClassDescriptor> sealedSubclasses, List<? extends KotlinType> supertypes, Map<String, ? extends Object> attributes) {
        super(outerContext.getStorageManager(), name2);
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(outerClass, "outerClass");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        Intrinsics.checkNotNullParameter(sealedSubclasses, "sealedSubclasses");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.outerContext = outerContext;
        this.outerClass = outerClass;
        this.classKind = classKind;
        this.modality = modality;
        this.visibility = visibility;
        this.isInner = z;
        this.isRecord = z2;
        this.annotations = annotations;
        this.declaredTypeParameters = declaredTypeParameters;
        this.sealedSubclasses = sealedSubclasses;
        this.attributes = attributes;
        FakeJavaClass fakeJavaClass = new FakeJavaClass();
        this.jClass = fakeJavaClass;
        LazyJavaResolverContext lazyJavaResolverContextChildForClassOrPackage$default = ContextKt.childForClassOrPackage$default(outerContext, this, null, 0, 6, null);
        this.c = lazyJavaResolverContextChildForClassOrPackage$default;
        SyntheticJavaClassDescriptor syntheticJavaClassDescriptor = this;
        this.typeConstructor = new ClassTypeConstructorImpl(syntheticJavaClassDescriptor, declaredTypeParameters, supertypes, lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager());
        LazyJavaClassMemberScope lazyJavaClassMemberScope = new LazyJavaClassMemberScope(lazyJavaResolverContextChildForClassOrPackage$default, syntheticJavaClassDescriptor, fakeJavaClass, true, null, 16, null);
        this.unsubstitutedMemberScope = lazyJavaClassMemberScope;
        this.scopeHolder = ScopesHolderForClass.INSTANCE.create(syntheticJavaClassDescriptor, lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager(), lazyJavaResolverContextChildForClassOrPackage$default.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner(), new Function1<KotlinTypeRefiner, LazyJavaClassMemberScope>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.SyntheticJavaClassDescriptor$scopeHolder$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LazyJavaClassMemberScope invoke(KotlinTypeRefiner it) {
                Intrinsics.checkNotNullParameter(it, "it");
                LazyJavaResolverContext lazyJavaResolverContext = this.this$0.c;
                SyntheticJavaClassDescriptor syntheticJavaClassDescriptor2 = this.this$0;
                return new LazyJavaClassMemberScope(lazyJavaResolverContext, syntheticJavaClassDescriptor2, syntheticJavaClassDescriptor2.jClass, true, this.this$0.unsubstitutedMemberScope);
            }
        });
        this.innerClassesScope = new InnerClassesScopeWrapper(lazyJavaClassMemberScope);
        this.staticScope = new LazyJavaStaticClassScope(lazyJavaResolverContextChildForClassOrPackage$default, fakeJavaClass, this);
        this.sourceElement = lazyJavaResolverContextChildForClassOrPackage$default.getComponents().getSourceElementFactory().source(fakeJavaClass);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getKind, reason: from getter */
    public ClassKind getClassKind() {
        return this.classKind;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor
    /* JADX INFO: renamed from: isRecord, reason: from getter */
    public boolean getIsRecord() {
        return this.isRecord;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.visibility;
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

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.declaredTypeParameters;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return this.c.getComponents().getSamConversionResolver().resolveFunctionTypeIfSamInterface(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        if (this.classKind != ClassKind.INTERFACE) {
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
                ClassifierDescriptor this$0 = ((KotlinType) it.next()).getConstructor().getThis$0();
                SyntheticJavaClassDescriptor syntheticJavaClassDescriptor = this$0 instanceof SyntheticJavaClassDescriptor ? (SyntheticJavaClassDescriptor) this$0 : null;
                if (syntheticJavaClassDescriptor != null && syntheticJavaClassDescriptor.isDefinitelyNotSamInterface()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        return this.sealedSubclasses;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public DeclarationDescriptor getContainingModule() {
        return this.outerClass;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return this.sourceElement;
    }

    public String toString() {
        return "Lazy Java class " + DescriptorUtilsKt.getFqNameUnsafe(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SyntheticJavaClassDescriptor.kt */
    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010?\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010@\u001a\u0004\u0018\u00010\u00012\u0006\u0010(\u001a\u00020\u0013H\u0016J\b\u0010A\u001a\u00020\u0016H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0007R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0017R\u0014\u0010\u0019\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u0014\u0010\u001b\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0014\u0010\u001c\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R\u0014\u0010\u001d\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0017R\u0014\u0010\u001e\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0017R\u0014\u0010\u001f\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0017R\u0014\u0010 \u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0017R\u0016\u0010!\u001a\u0004\u0018\u00010\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0007R\u0014\u0010(\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0016\u0010+\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u0007R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u0007R\u001a\u00104\u001a\b\u0012\u0004\u0012\u00020/0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u0010\u0007R\u001a\u00106\u001a\b\u0012\u0004\u0012\u000208078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0014\u0010;\u001a\u00020<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/SyntheticJavaClassDescriptor$FakeJavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lorg/jetbrains/kotlin/load/java/lazy/descriptors/SyntheticJavaClassDescriptor;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "constructors", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "isAbstract", "", "()Z", "isAnnotationType", "isDeprecatedInJavaDoc", "isEnum", "isFinal", "isFromSource", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findAnnotation", "findInnerClass", "hasDefaultConstructor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class FakeJavaClass implements JavaClass {
        @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
        /* JADX INFO: renamed from: findAnnotation */
        public JavaAnnotation mo7251findAnnotation(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public JavaClass findInnerClass(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public LightClassOriginKind getLightClassOriginKind() {
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public JavaClass getOuterClass() {
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: hasDefaultConstructor */
        public boolean mo7259hasDefaultConstructor() {
            return false;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
        /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
        public boolean getIsDeprecatedInJavaDoc() {
            return false;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
        public boolean isFromSource() {
            return false;
        }

        public FakeJavaClass() {
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
        public Name getName() {
            Name name2 = SyntheticJavaClassDescriptor.this.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return name2;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
        /* JADX INFO: renamed from: getAnnotations */
        public Collection<JavaAnnotation> mo7252getAnnotations() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
        /* JADX INFO: renamed from: isAbstract */
        public boolean mo7260isAbstract() {
            return SyntheticJavaClassDescriptor.this.modality == Modality.ABSTRACT;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
        /* JADX INFO: renamed from: isStatic */
        public boolean mo7267isStatic() {
            return !SyntheticJavaClassDescriptor.this.isInner;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
        /* JADX INFO: renamed from: isFinal */
        public boolean mo7264isFinal() {
            return SyntheticJavaClassDescriptor.this.modality == Modality.FINAL;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
        public Visibility getVisibility() {
            return SyntheticJavaClassDescriptor.this.visibility.getDelegate();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
        /* JADX INFO: renamed from: getTypeParameters */
        public List<JavaTypeParameter> mo7258getTypeParameters() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public FqName getFqName() {
            return DescriptorUtilsKt.getFqNameSafe(SyntheticJavaClassDescriptor.this);
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public Collection<JavaClassifierType> getSupertypes() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public Collection<Name> getInnerClassNames() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: isInterface */
        public boolean mo7265isInterface() {
            return SyntheticJavaClassDescriptor.this.classKind == ClassKind.INTERFACE;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: isAnnotationType */
        public boolean mo7261isAnnotationType() {
            return SyntheticJavaClassDescriptor.this.classKind == ClassKind.ANNOTATION_CLASS;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: isEnum */
        public boolean mo7263isEnum() {
            return SyntheticJavaClassDescriptor.this.classKind == ClassKind.ENUM_CLASS;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: isRecord */
        public boolean mo7266isRecord() {
            return SyntheticJavaClassDescriptor.this.isRecord;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public boolean isSealed() {
            return SyntheticJavaClassDescriptor.this.modality == Modality.SEALED;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        public Collection<JavaClassifierType> getPermittedTypes() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: getMethods */
        public Collection<JavaMethod> mo7255getMethods() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: getFields */
        public Collection<JavaField> mo7254getFields() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: getConstructors */
        public Collection<JavaConstructor> mo7253getConstructors() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
        /* JADX INFO: renamed from: getRecordComponents */
        public Collection<JavaRecordComponent> mo7257getRecordComponents() {
            return CollectionsKt.emptyList();
        }
    }
}
