package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightParameter;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightSimpleModifierList;
import org.jetbrains.kotlin.asJava.elements.LightParameter;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b \u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u00020\u0005B'\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u001c\u0010\u001c\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0004J\u0014\u0010!\u001a\u0004\u0018\u00010\u00072\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020+H\u0016J\n\u0010-\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010.\u001a\u00020/H\u0016J\n\u00100\u001a\u0004\u0018\u000101H\u0016J\b\u00102\u001a\u00020\u0017H&J\b\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u00020/H\u0016J\u0012\u00106\u001a\u00020#2\b\u00107\u001a\u0004\u0018\u00010+H\u0016J\b\u00108\u001a\u00020#H\u0016J\b\u00109\u001a\u00020#H&R\u0016\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameter;", "Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotationDescriptorBased;", "Lorg/jetbrains/kotlin/psi/KtParameter;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightParameter;", "name", "", "kotlinOrigin", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "ultraLightMethod", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtParameter;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;)V", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtParameter;", "lightModifierList", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightSimpleModifierList;", "getLightModifierList", "()Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightSimpleModifierList;", "lightModifierList$delegate", "Lkotlin/Lazy;", "psiTypeForNullabilityAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getPsiTypeForNullabilityAnnotation", "()Lcom/intellij/psi/PsiType;", "getSupport", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "computeParameterType", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "computeQualifiedNameForNullabilityAnnotation", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getNavigationElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getParent", "getText", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getType", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "hashCode", "isEquivalentTo", "another", "isValid", "isVarArgs", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtUltraLightParameter extends LightParameter implements KtUltraLightElementWithNullabilityAnnotationDescriptorBased<KtParameter, PsiParameter>, KtLightParameter {
    private final KtParameter kotlinOrigin;

    /* JADX INFO: renamed from: lightModifierList$delegate, reason: from kotlin metadata */
    private final Lazy lightModifierList;
    private final KtUltraLightSupport support;
    private final KtUltraLightMethod ultraLightMethod;

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public abstract PsiType getType();

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public abstract boolean getMyVarArgs();

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtParameter getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    protected final KtUltraLightSupport getSupport() {
        return this.support;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtUltraLightParameter(String name2, KtParameter ktParameter, KtUltraLightSupport support, KtUltraLightMethod ultraLightMethod) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(ultraLightMethod, "ultraLightMethod");
        PsiPrimitiveType NULL = PsiType.NULL;
        Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
        super(name2, NULL, ultraLightMethod, ultraLightMethod.getLanguage(), false, 16, null);
        this.kotlinOrigin = ktParameter;
        this.support = support;
        this.ultraLightMethod = ultraLightMethod;
        this.lightModifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightSimpleModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter$lightModifierList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightSimpleModifierList invoke() {
                return new KtUltraLightSimpleModifierList(this.this$0, SetsKt.emptySet());
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        KtParameter kotlinOrigin;
        return ((another instanceof KtParameter) && (kotlinOrigin = getKotlinOrigin()) != null && kotlinOrigin.isEquivalentTo(another)) || Intrinsics.areEqual(this, another);
    }

    private final KtUltraLightSimpleModifierList getLightModifierList() {
        return (KtUltraLightSimpleModifierList) this.lightModifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return getLightModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        KtParameter kotlinOrigin = getKotlinOrigin();
        if (kotlinOrigin != null) {
            return kotlinOrigin;
        }
        PsiElement navigationElement = getMethod().getNavigationElement();
        Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
        return navigationElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        KtParameter kotlinOrigin = getKotlinOrigin();
        SearchScope useScope = kotlinOrigin != null ? kotlinOrigin.getUseScope() : null;
        return useScope == null ? new LocalSearchScope(this) : useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        KtParameter kotlinOrigin = getKotlinOrigin();
        String text = kotlinOrigin != null ? kotlinOrigin.getText() : null;
        return text == null ? "" : text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        KtParameter kotlinOrigin = getKotlinOrigin();
        if (kotlinOrigin != null) {
            return kotlinOrigin.getTextRange();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        KtParameter kotlinOrigin = getKotlinOrigin();
        return kotlinOrigin != null ? kotlinOrigin.getTextOffset() : super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return getParentMethod().isValid();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotationDescriptorBased
    public String computeQualifiedNameForNullabilityAnnotation(KotlinType kotlinType) {
        if (getMyVarArgs() && kotlinType != null && KotlinBuiltIns.isArray(kotlinType)) {
            kotlinType = kotlinType.getArguments().get(0).getNullableAnyType();
        }
        return super.computeQualifiedNameForNullabilityAnnotation(kotlinType);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public PsiType getPsiTypeForNullabilityAnnotation() {
        return getType();
    }

    protected final PsiType computeParameterType(final KotlinType kotlinType, final CallableDescriptor containingDeclaration) {
        if (kotlinType == null) {
            PsiPrimitiveType NULL = PsiType.NULL;
            Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
            return NULL;
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return UltraLightUtilsKt.asPsiType(kotlinType, this.support, TypeMappingMode.DEFAULT, this);
        }
        if (containingDeclaration == null) {
            PsiPrimitiveType NULL2 = PsiType.NULL;
            Intrinsics.checkNotNullExpressionValue(NULL2, "NULL");
            return NULL2;
        }
        PsiType psiTypeMapType = UltraLightUtilsKt.mapType(this.support, kotlinType, this, new Function2<KotlinTypeMapper, JvmSignatureWriter, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter$computeParameterType$mappedType$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KotlinTypeMapper kotlinTypeMapper, JvmSignatureWriter jvmSignatureWriter) {
                invoke2(kotlinTypeMapper, jvmSignatureWriter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinTypeMapper typeMapper, JvmSignatureWriter sw) {
                Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                Intrinsics.checkNotNullParameter(sw, "sw");
                typeMapper.writeParameterType(sw, kotlinType, containingDeclaration);
            }
        });
        if (!this.ultraLightMethod.getCheckNeedToErasureParametersTypes()) {
            return psiTypeMapType;
        }
        PsiType psiTypeErasure = TypeConversionUtil.erasure(psiTypeMapType);
        Intrinsics.checkNotNullExpressionValue(psiTypeErasure, "erasure(...)");
        return psiTypeErasure;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = getMethod().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getParentMethod() {
        PsiParameterList parameterList = getMethod().getParameterList();
        Intrinsics.checkNotNullExpressionValue(parameterList, "getParameterList(...)");
        return parameterList;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof KtUltraLightParameter) && Intrinsics.areEqual(other.getClass(), getClass())) {
            KtUltraLightParameter ktUltraLightParameter = (KtUltraLightParameter) other;
            if (Intrinsics.areEqual(ktUltraLightParameter.getMyName(), getMyName())) {
                if (ktUltraLightParameter.getKotlinOrigin() != null) {
                    return Intrinsics.areEqual(ktUltraLightParameter.getKotlinOrigin(), getKotlinOrigin());
                }
                return getKotlinOrigin() == null && Intrinsics.areEqual(ktUltraLightParameter.ultraLightMethod, this.ultraLightMethod);
            }
        }
        return false;
    }

    public int hashCode() {
        return getMyName().hashCode();
    }
}
