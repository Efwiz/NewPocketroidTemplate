package org.jetbrains.kotlin.resolve.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: LazyTypeAliasDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 92\u00020\u00012\u00020\u0002:\u00019B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\n\u0010.\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u0010/\u001a\u00020!H\u0016J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020*0)H\u0014J0\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020*0)2\f\u00104\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u00105\u001a\b\u0012\u0004\u0012\u00020!0 J$\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020*0)2\u0006\u0010+\u001a\u00020!2\u0006\u0010\"\u001a\u00020!J\b\u0010&\u001a\u00020'H\u0016J\u0010\u00106\u001a\u00020\u00022\u0006\u00107\u001a\u000208H\u0016R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017X\u0082.¢\u0006\u0002\n\u0000R!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00198VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0)0 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010$R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082.¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classDescriptorImpl", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "constructors", "", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "getConstructors", "()Ljava/util/Collection;", "constructors$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "defaultTypeImpl", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/types/SimpleType;", "expandedType", "getExpandedType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "expandedTypeImpl", "isActual", "", "lazyTypeConstructorParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "underlyingType", "getUnderlyingType", "underlyingTypeImpl", "computeClassDescriptor", "getDefaultType", "getTypeConstructorTypeParameters", "initialize", "", "declaredTypeParameters", "lazyUnderlyingType", "lazyExpandedType", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyTypeAliasDescriptor extends AbstractTypeAliasDescriptor implements TypeAliasDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyTypeAliasDescriptor.class), "constructors", "getConstructors()Ljava/util/Collection;"))};

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private NullableLazyValue<? extends ClassDescriptor> classDescriptorImpl;

    /* JADX INFO: renamed from: constructors$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue constructors;
    private NotNullLazyValue<? extends SimpleType> defaultTypeImpl;
    private NotNullLazyValue<? extends SimpleType> expandedTypeImpl;
    private final boolean isActual;
    private final NotNullLazyValue<List<TypeParameterDescriptor>> lazyTypeConstructorParameters;
    private final BindingTrace trace;
    private NotNullLazyValue<? extends SimpleType> underlyingTypeImpl;

    @JvmStatic
    public static final LazyTypeAliasDescriptor create(StorageManager storageManager, BindingTrace bindingTrace, DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, SourceElement sourceElement, DescriptorVisibility descriptorVisibility) {
        return INSTANCE.create(storageManager, bindingTrace, declarationDescriptor, annotations, name2, sourceElement, descriptorVisibility);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyTypeAliasDescriptor(StorageManager storageManager, BindingTrace trace, DeclarationDescriptor containingDeclaration, Annotations annotations, Name name2, SourceElement sourceElement, DescriptorVisibility visibility) {
        super(storageManager, containingDeclaration, annotations, name2, sourceElement, visibility);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        this.trace = trace;
        this.constructors = storageManager.createLazyValue(new Function0<Collection<? extends TypeAliasConstructorDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor$constructors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends TypeAliasConstructorDescriptor> invoke() {
                return this.this$0.getTypeAliasConstructors();
            }
        });
        SourceElement source = getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtTypeAlias ktTypeAlias = psi instanceof KtTypeAlias ? (KtTypeAlias) psi : null;
        boolean z = false;
        if (ktTypeAlias != null && PsiUtilsKt.hasActualModifier(ktTypeAlias)) {
            z = true;
        }
        this.isActual = z;
        this.lazyTypeConstructorParameters = storageManager.createRecursionTolerantLazyValue(new Function0<List<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor$lazyTypeConstructorParameters$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TypeParameterDescriptor> invoke() {
                return TypeParameterUtilsKt.computeConstructorTypeParameters(this.this$0);
            }
        }, CollectionsKt.emptyList());
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor, org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public Collection<TypeAliasConstructorDescriptor> getConstructors() {
        return (Collection) StorageKt.getValue(this.constructors, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getUnderlyingType() {
        NotNullLazyValue<? extends SimpleType> notNullLazyValue = this.underlyingTypeImpl;
        if (notNullLazyValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("underlyingTypeImpl");
            notNullLazyValue = null;
        }
        return notNullLazyValue.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public SimpleType getExpandedType() {
        NotNullLazyValue<? extends SimpleType> notNullLazyValue = this.expandedTypeImpl;
        if (notNullLazyValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expandedTypeImpl");
            notNullLazyValue = null;
        }
        return notNullLazyValue.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
    public ClassDescriptor getClassDescriptor() {
        NullableLazyValue<? extends ClassDescriptor> nullableLazyValue = this.classDescriptorImpl;
        if (nullableLazyValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("classDescriptorImpl");
            nullableLazyValue = null;
        }
        return nullableLazyValue.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        NotNullLazyValue<? extends SimpleType> notNullLazyValue = this.defaultTypeImpl;
        if (notNullLazyValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("defaultTypeImpl");
            notNullLazyValue = null;
        }
        return notNullLazyValue.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isActual, reason: from getter */
    public boolean getIsActual() {
        return this.isActual;
    }

    public final void initialize(List<? extends TypeParameterDescriptor> declaredTypeParameters, NotNullLazyValue<? extends SimpleType> lazyUnderlyingType, NotNullLazyValue<? extends SimpleType> lazyExpandedType) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        Intrinsics.checkNotNullParameter(lazyUnderlyingType, "lazyUnderlyingType");
        Intrinsics.checkNotNullParameter(lazyExpandedType, "lazyExpandedType");
        super.initialize(declaredTypeParameters);
        this.underlyingTypeImpl = lazyUnderlyingType;
        this.expandedTypeImpl = lazyExpandedType;
        this.defaultTypeImpl = getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.initialize.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                return LazyTypeAliasDescriptor.this.computeDefaultType();
            }
        });
        this.classDescriptorImpl = getStorageManager().createRecursionTolerantNullableLazyValue(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.initialize.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return LazyTypeAliasDescriptor.this.computeClassDescriptor();
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor computeClassDescriptor() {
        if (KotlinTypeKt.isError(getUnderlyingType())) {
            return null;
        }
        ClassifierDescriptor this$0 = getUnderlyingType().getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            return (ClassDescriptor) this$0;
        }
        if (this$0 instanceof TypeAliasDescriptor) {
            return ((TypeAliasDescriptor) this$0).getClassDescriptor();
        }
        return null;
    }

    public final void initialize(List<? extends TypeParameterDescriptor> declaredTypeParameters, final SimpleType underlyingType, final SimpleType expandedType) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        Intrinsics.checkNotNullParameter(underlyingType, "underlyingType");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        initialize(declaredTypeParameters, getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.initialize.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                return underlyingType;
            }
        }), getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.initialize.4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                return expandedType;
            }
        }));
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public TypeAliasDescriptor substitute(final TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return this;
        }
        StorageManager storageManager = getStorageManager();
        BindingTrace bindingTrace = this.trace;
        DeclarationDescriptor containingDeclaration = getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        Annotations annotations = getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "<get-annotations>(...)");
        Name name2 = getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        SourceElement source = getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        LazyTypeAliasDescriptor lazyTypeAliasDescriptor = new LazyTypeAliasDescriptor(storageManager, bindingTrace, containingDeclaration, annotations, name2, source, getVisibilityImpl());
        lazyTypeAliasDescriptor.initialize(getDeclaredTypeParameters(), getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.substitute.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                KotlinType kotlinTypeSubstitute = substitutor.substitute(this.getUnderlyingType(), Variance.INVARIANT);
                Intrinsics.checkNotNull(kotlinTypeSubstitute);
                return TypeSubstitutionKt.asSimpleType(kotlinTypeSubstitute);
            }
        }), getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyTypeAliasDescriptor.substitute.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                KotlinType kotlinTypeSubstitute = substitutor.substitute(this.getExpandedType(), Variance.INVARIANT);
                Intrinsics.checkNotNull(kotlinTypeSubstitute);
                return TypeSubstitutionKt.asSimpleType(kotlinTypeSubstitute);
            }
        }));
        return lazyTypeAliasDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeAliasDescriptor
    protected List<TypeParameterDescriptor> getTypeConstructorTypeParameters() {
        return this.lazyTypeConstructorParameters.invoke();
    }

    /* JADX INFO: compiled from: LazyTypeAliasDescriptor.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J@\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyTypeAliasDescriptor$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyTypeAliasDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final LazyTypeAliasDescriptor create(StorageManager storageManager, BindingTrace trace, DeclarationDescriptor containingDeclaration, Annotations annotations, Name name2, SourceElement sourceElement, DescriptorVisibility visibility) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            return new LazyTypeAliasDescriptor(storageManager, trace, containingDeclaration, annotations, name2, sourceElement, visibility);
        }
    }
}
