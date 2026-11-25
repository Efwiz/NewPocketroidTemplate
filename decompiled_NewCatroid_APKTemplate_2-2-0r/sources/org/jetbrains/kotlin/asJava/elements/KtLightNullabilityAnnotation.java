package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValVarKeywordOwner;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeNullability;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: lightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0012\b\u0000\u0010\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\nH\u0016J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\nH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\n\u0010!\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020\u0007H\u0002J-\u0010%\u001a\u00020\u0010\"\n\b\u0001\u0010&*\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\n2\b\u0010'\u001a\u0004\u0018\u0001H&H\u0016¢\u0006\u0002\u0010(J\u0013\u0010)\u001a\u0004\u0018\u00010#*\u00020*H\u0000¢\u0006\u0002\b+R\u001d\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightNullabilityAnnotation;", "D", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "member", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;Lcom/intellij/psi/PsiElement;)V", "_qualifiedName", "", "get_qualifiedName", "()Ljava/lang/String;", "_qualifiedName$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "", "getKotlinOrigin", "()Ljava/lang/Void;", "getMember", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "findAttributeValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "attributeName", "findDeclaredAttributeValue", "fqNameMatches", "", "fqName", "getNameReferenceElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationParameterList;", "getQualifiedName", "getTargetType", "Lorg/jetbrains/kotlin/types/KotlinType;", "annotatedElement", "setDeclaredAttributeValue", ExifInterface.GPS_DIRECTION_TRUE, "value", "(Ljava/lang/String;Lcom/intellij/psi/PsiAnnotationMemberValue;)Ljava/lang/Void;", "getType", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getType$light_classes", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtLightNullabilityAnnotation<D extends KtLightElement<?, ? extends PsiModifierListOwner>> extends KtLightAbstractAnnotation {

    /* JADX INFO: renamed from: _qualifiedName$delegate, reason: from kotlin metadata */
    private final Lazy _qualifiedName;
    private final D member;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findAttributeValue(String attributeName) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationMemberValue findDeclaredAttributeValue(String attributeName) {
        return null;
    }

    public Void getKotlinOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiJavaCodeReferenceElement getNameReferenceElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public /* bridge */ /* synthetic */ KtElement getKotlinOrigin() {
        return (KtElement) getKotlinOrigin();
    }

    public final D getMember() {
        return this.member;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    /* JADX INFO: renamed from: setDeclaredAttributeValue */
    public /* bridge */ /* synthetic */ PsiAnnotationMemberValue mo6097setDeclaredAttributeValue(String str, PsiAnnotationMemberValue psiAnnotationMemberValue) {
        return (PsiAnnotationMemberValue) setDeclaredAttributeValue(str, psiAnnotationMemberValue);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightNullabilityAnnotation(D member, final PsiElement parent) {
        super(parent);
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.member = member;
        this._qualifiedName = ImplUtilsKt.lazyPub(new Function0<String>(this) { // from class: org.jetbrains.kotlin.asJava.elements.KtLightNullabilityAnnotation$_qualifiedName$2
            final /* synthetic */ KtLightNullabilityAnnotation<D> this$0;

            /* JADX INFO: compiled from: lightAnnotations.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[TypeNullability.values().length];
                    try {
                        iArr[TypeNullability.NOT_NULL.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[TypeNullability.NULLABLE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[TypeNullability.FLEXIBLE.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                KtElement kotlinOrigin;
                KtModifierList modifierList;
                KtLightElement member2 = this.this$0.getMember();
                if (!KtLightModifierListDescriptorBasedKt.isFromSources(member2)) {
                    member2 = null;
                }
                if (member2 == null || (kotlinOrigin = member2.getKotlinOrigin()) == null || !LightClassUtilsKt.fastCheckIsNullabilityApplied(this.this$0.getMember())) {
                    return null;
                }
                if ((kotlinOrigin instanceof KtClass) && ((KtClass) kotlinOrigin).isData()) {
                    return NotNull.class.getName();
                }
                if (kotlinOrigin instanceof KtObjectDeclaration) {
                    PsiElement lightParent = parent.getLightParent();
                    PsiMethod psiMethod = lightParent instanceof PsiMethod ? (PsiMethod) lightParent : null;
                    if (psiMethod == null || !psiMethod.isConstructor()) {
                        return NotNull.class.getName();
                    }
                    return null;
                }
                boolean z = kotlinOrigin instanceof KtValVarKeywordOwner;
                if (z && (this.this$0.getMember() instanceof KtLightMethod)) {
                    PsiElement originalElement = ((KtLightMethod) this.this$0.getMember()).getOriginalElement();
                    KtPropertyAccessor ktPropertyAccessor = originalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) originalElement : null;
                    if (ktPropertyAccessor != null && ktPropertyAccessor.isSetter()) {
                        return null;
                    }
                }
                if (!(kotlinOrigin instanceof KtNamedFunction) || (modifierList = ((KtNamedFunction) kotlinOrigin).getModifierList()) == null || !PsiUtilsKt.hasSuspendModifier(modifierList)) {
                    KotlinType targetType = this.this$0.getTargetType(kotlinOrigin);
                    if (targetType == null) {
                        return null;
                    }
                    if (KotlinBuiltIns.isPrimitiveType(targetType)) {
                        KtParameter ktParameter = kotlinOrigin instanceof KtParameter ? (KtParameter) kotlinOrigin : null;
                        if (ktParameter == null || !ktParameter.isVarArg()) {
                            if ((kotlinOrigin instanceof KtCallableDeclaration) && ((KtCallableDeclaration) kotlinOrigin).hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                                Object obj = LightAnnotationsKt.analyze(kotlinOrigin).get(BindingContext.DECLARATION_TO_DESCRIPTOR, kotlinOrigin);
                                CallableMemberDescriptor callableMemberDescriptor = obj instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) obj : null;
                                Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor != null ? callableMemberDescriptor.getOverriddenDescriptors() : null;
                                if (overriddenDescriptors != null) {
                                    Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
                                    if (!(collection instanceof Collection) || !collection.isEmpty()) {
                                        Iterator<T> it = collection.iterator();
                                        while (it.hasNext()) {
                                            if (!Intrinsics.areEqual(((CallableMemberDescriptor) it.next()).getReturnType(), targetType)) {
                                            }
                                        }
                                    }
                                }
                            }
                            return null;
                        }
                    }
                    if (TypeUtilsKt.isUnit(targetType) && !z) {
                        return null;
                    }
                    if (TypeUtilsKt.isTypeParameter(targetType)) {
                        if (!TypeUtils.hasNullableSuperType(targetType)) {
                            return NotNull.class.getName();
                        }
                        if (!targetType.getIsMarkedNullable()) {
                            return null;
                        }
                    }
                    int i = WhenMappings.$EnumSwitchMapping$0[TypeUtilsKt.nullability(targetType).ordinal()];
                    if (i == 1) {
                        return NotNull.class.getName();
                    }
                    if (i == 2) {
                        return Nullable.class.getName();
                    }
                    if (i == 3) {
                        return null;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                return Nullable.class.getName();
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation
    public boolean fqNameMatches(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (LightAnnotationsKt.isNullabilityAnnotation(fqName)) {
            return super.fqNameMatches(fqName);
        }
        return false;
    }

    public <T extends PsiAnnotationMemberValue> Void setDeclaredAttributeValue(String str, T t) {
        ImplUtilsKt.cannotModify(this);
        throw null;
    }

    private final String get_qualifiedName() {
        return (String) this._qualifiedName.getValue();
    }

    public String getQualifiedName() {
        return get_qualifiedName();
    }

    public final KotlinType getType$light_classes(KtTypeReference ktTypeReference) {
        Intrinsics.checkNotNullParameter(ktTypeReference, "<this>");
        return (KotlinType) LightAnnotationsKt.analyze(ktTypeReference).get(BindingContext.TYPE, ktTypeReference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType getTargetType(PsiElement annotatedElement) {
        List<TypeProjection> arguments;
        TypeProjection typeProjection;
        KotlinType type;
        KtExpression bodyExpression;
        KotlinType type2;
        KtTypeReference ktTypeReferenceMo7396getTypeReference;
        KotlinType type$light_classes;
        KotlinType type$light_classes2;
        if ((annotatedElement instanceof KtTypeReference) && (type$light_classes2 = getType$light_classes((KtTypeReference) annotatedElement)) != null) {
            return type$light_classes2;
        }
        if ((annotatedElement instanceof KtCallableDeclaration) && (ktTypeReferenceMo7396getTypeReference = ((KtCallableDeclaration) annotatedElement).mo7396getTypeReference()) != null && (type$light_classes = getType$light_classes(ktTypeReferenceMo7396getTypeReference)) != null) {
            return type$light_classes;
        }
        if ((annotatedElement instanceof KtNamedFunction) && (bodyExpression = ((KtNamedFunction) annotatedElement).getBodyExpression()) != null && (type2 = CallUtilKt.getType(bodyExpression, LightAnnotationsKt.analyze(bodyExpression))) != null) {
            return type2;
        }
        if (annotatedElement instanceof KtProperty) {
            KtProperty ktProperty = (KtProperty) annotatedElement;
            KtExpression initializer = ktProperty.getInitializer();
            if (initializer == null) {
                KtPropertyAccessor getter = ktProperty.getGetter();
                initializer = getter != null ? getter.getInitializer() : null;
            }
            if (initializer != null && (type = CallUtilKt.getType(initializer, LightAnnotationsKt.analyze(initializer))) != null) {
                return type;
            }
            KtExpression delegateExpression = ktProperty.getDelegateExpression();
            if (delegateExpression != null) {
                KotlinType type3 = CallUtilKt.getType(delegateExpression, LightAnnotationsKt.analyze(delegateExpression));
                KotlinType type4 = (type3 == null || (arguments = type3.getArguments()) == null || (typeProjection = (TypeProjection) CollectionsKt.firstOrNull((List) arguments)) == null) ? null : typeProjection.getType();
                if (type4 != null) {
                    return type4;
                }
            }
        }
        KtProperty ktProperty2 = (KtProperty) PsiTreeUtil.getParentOfType(annotatedElement, KtProperty.class, false);
        if (ktProperty2 == null) {
            return null;
        }
        KtTypeReference ktTypeReferenceMo7396getTypeReference2 = ktProperty2.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference2 != null) {
            Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference2);
            KotlinType type$light_classes3 = getType$light_classes(ktTypeReferenceMo7396getTypeReference2);
            if (type$light_classes3 != null) {
                return type$light_classes3;
            }
        }
        KtExpression initializer2 = ktProperty2.getInitializer();
        if (initializer2 == null) {
            KtPropertyAccessor getter2 = ktProperty2.getGetter();
            initializer2 = getter2 != null ? getter2.getInitializer() : null;
        }
        if (initializer2 != null) {
            return CallUtilKt.getType(initializer2, LightAnnotationsKt.analyze(initializer2));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
    public PsiAnnotationParameterList getParameterList() {
        return new KtLightEmptyAnnotationParameterList(this);
    }
}
