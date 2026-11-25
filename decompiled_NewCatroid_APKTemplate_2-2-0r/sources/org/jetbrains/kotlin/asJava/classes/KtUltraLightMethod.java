package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: ultraLightMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b \u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002:\u0001XB/\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0014J\b\u0010)\u001a\u00020*H$J\u0012\u0010+\u001a\u00020\u00102\b\u0010,\u001a\u0004\u0018\u00010-H\u0004J\u0012\u0010.\u001a\u00020/2\b\u0010,\u001a\u0004\u0018\u00010-H\u0004J\u0013\u00100\u001a\u00020\u00102\b\u00101\u001a\u0004\u0018\u000102H\u0096\u0002J\u000f\u00103\u001a\t\u0018\u00010\u0004¢\u0006\u0002\b4H\u0016J\u0015\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000406H\u0016¢\u0006\u0002\u00107J\u0016\u00108\u001a\b\u0012\u0004\u0012\u0002090'2\u0006\u0010:\u001a\u00020\u0010H\u0016J\u0015\u0010;\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000406H\u0016¢\u0006\u0002\u00107J\u001f\u0010;\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u0004062\b\u0010<\u001a\u0004\u0018\u00010=H\u0016¢\u0006\u0002\u0010>J\u001d\u0010;\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u0004062\u0006\u0010:\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010?J\n\u0010@\u001a\u0004\u0018\u00010AH\u0016J\n\u0010B\u001a\u0004\u0018\u00010CH\u0016J\r\u0010D\u001a\u00070E¢\u0006\u0002\bFH\u0016J\b\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020JH\u0016J\n\u0010K\u001a\u0004\u0018\u00010!H\u0016J\n\u0010L\u001a\u0004\u0018\u00010MH\u0016J\u0010\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020QH\u0016J\u0010\u0010R\u001a\u00020\u00102\u0006\u0010S\u001a\u00020JH\u0016J\b\u0010T\u001a\u00020\rH\u0016J\b\u0010U\u001a\u00020\u0010H\u0016J\b\u0010V\u001a\u00020\u0010H\u0016J\b\u0010W\u001a\u00020\u0010H\u0016R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u001a\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\f\u001a\u00020\rX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010 \u001a\u0004\u0018\u00010!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethodImpl;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotationDescriptorBased;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "methodIndex", "", "(Lcom/intellij/psi/PsiMethod;Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;I)V", "_deprecated", "", "get_deprecated", "()Z", "_deprecated$delegate", "Lkotlin/Lazy;", "_modifierList", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodModifierList;", "get_modifierList", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodModifierList;", "_modifierList$delegate", "checkNeedToErasureParametersTypes", "getCheckNeedToErasureParametersTypes", "getDelegate$light_classes", "()Lcom/intellij/psi/PsiMethod;", "getMethodIndex", "()I", "psiTypeForNullabilityAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getPsiTypeForNullabilityAnnotation", "()Lcom/intellij/psi/PsiType;", "getSupport", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "buildParametersForList", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "buildTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "computeCheckNeedToErasureParametersTypes", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "computeThrowsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", Namer.EQUALS_METHOD_NAME, "other", "", "findDeepestSuperMethod", "Lorg/jetbrains/annotations/Nullable;", "findDeepestSuperMethods", "", "()[Lcom/intellij/psi/PsiMethod;", "findSuperMethodSignaturesIncludingStatic", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod;", "checkAccess", "findSuperMethods", "parentClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)[Lcom/intellij/psi/PsiMethod;", "(Z)[Lcom/intellij/psi/PsiMethod;", "getDefaultValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getHierarchicalMethodSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "Lorg/jetbrains/annotations/NotNull;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getReturnType", "getReturnTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignature;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "hasModifierProperty", "name", "hashCode", "isConstructor", "isDeprecated", "isVarArgs", "KtUltraLightThrowsReferenceListBuilder", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtUltraLightMethod extends KtLightMethodImpl implements KtUltraLightElementWithNullabilityAnnotationDescriptorBased<KtDeclaration, PsiMethod> {

    /* JADX INFO: renamed from: _deprecated$delegate, reason: from kotlin metadata */
    private final Lazy _deprecated;

    /* JADX INFO: renamed from: _modifierList$delegate, reason: from kotlin metadata */
    private final Lazy _modifierList;
    private final PsiMethod delegate;
    private final int methodIndex;
    private final KtUltraLightSupport support;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    protected abstract PsiTypeParameterList buildTypeParameterList();

    public abstract boolean getCheckNeedToErasureParametersTypes();

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return null;
    }

    /* JADX INFO: renamed from: getDelegate$light_classes, reason: from getter */
    public final PsiMethod getDelegate() {
        return this.delegate;
    }

    protected final KtUltraLightSupport getSupport() {
        return this.support;
    }

    protected final int getMethodIndex() {
        return this.methodIndex;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightMethod(PsiMethod delegate, LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtUltraLightSupport support, KtLightClass containingClass, int i) {
        super(lightMemberOriginForDeclaration, containingClass);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.delegate = delegate;
        this.support = support;
        this.methodIndex = i;
        this._modifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightMethodModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod$_modifierList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightMethodModifierList invoke() {
                KtUltraLightSupport support2 = this.this$0.getSupport();
                KtUltraLightMethod ktUltraLightMethod = this.this$0;
                return new KtUltraLightMethodModifierList(support2, ktUltraLightMethod, ktUltraLightMethod.getDelegate());
            }
        });
        this._deprecated = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightMethod$_deprecated$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                KtDeclaration kotlinOrigin = this.this$0.getKotlinOrigin();
                return Boolean.valueOf(kotlinOrigin != null ? UltraLightUtilsKt.isDeprecated(kotlinOrigin, this.this$0.getSupport()) : false);
            }
        });
    }

    /* JADX INFO: compiled from: ultraLightMethods.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod$KtUltraLightThrowsReferenceListBuilder;", "Lorg/jetbrains/kotlin/asJava/classes/KotlinLightReferenceListBuilder;", "parentMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "(Lcom/intellij/psi/PsiMethod;)V", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getParent", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class KtUltraLightThrowsReferenceListBuilder extends KotlinLightReferenceListBuilder {
        private final PsiMethod parentMethod;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KtUltraLightThrowsReferenceListBuilder(PsiMethod parentMethod) {
            super(parentMethod.getManager(), parentMethod.getLanguage(), PsiReferenceList.Role.THROWS_LIST);
            Intrinsics.checkNotNullParameter(parentMethod, "parentMethod");
            this.parentMethod = parentMethod;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent, reason: from getter */
        public PsiMethod getParentMethod() {
            return this.parentMethod;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return this.parentMethod.getContainingFile();
        }
    }

    protected final PsiReferenceList computeThrowsList(FunctionDescriptor methodDescriptor) {
        KtUltraLightThrowsReferenceListBuilder ktUltraLightThrowsReferenceListBuilder = new KtUltraLightThrowsReferenceListBuilder(this);
        if (methodDescriptor != null) {
            Iterator<ClassDescriptor> it = FunctionCodegen.getThrownExceptions(methodDescriptor, LanguageVersionSettingsImpl.DEFAULT).iterator();
            while (it.hasNext()) {
                SimpleType defaultType = it.next().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                PsiType psiTypeAsPsiType = UltraLightUtilsKt.asPsiType(defaultType, this.support, TypeMappingMode.DEFAULT, ktUltraLightThrowsReferenceListBuilder);
                PsiClassType psiClassType = psiTypeAsPsiType instanceof PsiClassType ? (PsiClassType) psiTypeAsPsiType : null;
                if (psiClassType != null) {
                    ktUltraLightThrowsReferenceListBuilder.addReference(psiClassType);
                }
            }
        }
        return ktUltraLightThrowsReferenceListBuilder;
    }

    protected final boolean computeCheckNeedToErasureParametersTypes(FunctionDescriptor methodDescriptor) {
        if (methodDescriptor == null) {
            return false;
        }
        FunctionDescriptor functionDescriptor = methodDescriptor;
        SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo = BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(functionDescriptor);
        if (specialSignatureInfo != null && specialSignatureInfo.getValueParametersSignature() != null) {
            return true;
        }
        if (!DescriptorUtils.isOverride(functionDescriptor)) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = methodDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            List<TypeProjection> arguments = ((ValueParameterDescriptor) it.next()).getType().getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it2 = arguments.iterator();
                while (it2.hasNext()) {
                    if (((TypeProjection) it2.next()).isStarProjection()) {
                        Collection<? extends FunctionDescriptor> overriddenDescriptors = methodDescriptor.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : overriddenDescriptors) {
                            if (obj instanceof JavaMethodDescriptor) {
                                arrayList.add(obj);
                            }
                        }
                        ArrayList arrayList2 = arrayList;
                        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
                            return false;
                        }
                        Iterator it3 = arrayList2.iterator();
                        while (it3.hasNext()) {
                            List<ValueParameterDescriptor> valueParameters2 = ((JavaMethodDescriptor) it3.next()).getValueParameters();
                            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                            List<ValueParameterDescriptor> list2 = valueParameters2;
                            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                Iterator<T> it4 = list2.iterator();
                                while (it4.hasNext()) {
                                    if (((ValueParameterDescriptor) it4.next()).getType() instanceof RawType) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public PsiType getPsiTypeForNullabilityAnnotation() {
        return getReturnType();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return this.delegate.getReturnType();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    protected List<PsiParameter> buildParametersForList() {
        PsiParameter[] parameters = this.delegate.getParameterList().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        return ArraysKt.toList(parameters);
    }

    private final KtUltraLightMethodModifierList get_modifierList() {
        return (KtUltraLightMethodModifierList) this._modifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return get_modifierList().hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return get_modifierList();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
    public PsiAnnotationMemberValue getDefaultValue() {
        PsiMethod psiMethod = this.delegate;
        if (!(psiMethod instanceof PsiAnnotationMethod)) {
            psiMethod = null;
        }
        PsiAnnotationMethod psiAnnotationMethod = (PsiAnnotationMethod) psiMethod;
        if (psiAnnotationMethod != null) {
            return psiAnnotationMethod.getDefaultValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String str = this.delegate.getMyName();
        Intrinsics.checkNotNullExpressionValue(str, "getName(...)");
        return str;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return PsiImplUtil.isVarArgs(this);
    }

    private final boolean get_deprecated() {
        return ((Boolean) this._deprecated.getValue()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        Intrinsics.checkNotNullExpressionValue(hierarchicalMethodSignature, "getHierarchicalMethodSignature(...)");
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, checkAccess);
        Intrinsics.checkNotNullExpressionValue(listFindSuperMethodSignaturesIncludingStatic, "findSuperMethodSignaturesIncludingStatic(...)");
        return listFindSuperMethodSignaturesIncludingStatic;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] psiMethodArrFindDeepestSuperMethods = PsiSuperMethodImplUtil.findDeepestSuperMethods(this);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindDeepestSuperMethods, "findDeepestSuperMethods(...)");
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, substitutor);
        Intrinsics.checkNotNullExpressionValue(methodSignatureBackedByPsiMethodCreate, "create(...)");
        return methodSignatureBackedByPsiMethodCreate;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    public boolean equals(Object other) {
        return other == this || ((other instanceof KtUltraLightMethod) && ((KtUltraLightMethod) other).methodIndex == this.methodIndex && super.equals(other));
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl
    public int hashCode() {
        return (super.hashCode() * 31) + Integer.hashCode(this.methodIndex);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return get_deprecated();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMemberImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.delegate.mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethodImpl, org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return this.delegate.isConstructor();
    }
}
