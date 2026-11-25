package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtLightParameter;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightSimpleModifierList;
import org.jetbrains.kotlin.asJava.elements.LightParameter;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003B\u001d\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010&\u001a\u00020'H\u0016J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0096\u0002J\b\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020'H\u0016J\b\u00101\u001a\u00020'H\u0016J\b\u00102\u001a\u00020\u001bH\u0016J\b\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u000206H\u0016J\u0012\u00107\u001a\u00020)2\b\u00108\u001a\u0004\u0018\u00010'H\u0016J\b\u00109\u001a\u00020)H\u0016J\b\u0010:\u001a\u00020)H\u0016R\u0016\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R#\u0010\u001a\u001a\n \u001c*\u0004\u0018\u00010\u001b0\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0019\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010 \u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u001eR\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSuspendContinuationParameter;", "Lorg/jetbrains/kotlin/asJava/elements/LightParameter;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightParameter;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotationDescriptorBased;", "Lorg/jetbrains/kotlin/psi/KtParameter;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "(Lorg/jetbrains/kotlin/psi/KtFunction;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;)V", "kotlinOrigin", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtParameter;", "ktType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKtType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "lightModifierList", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightSimpleModifierList;", "getLightModifierList", "()Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightSimpleModifierList;", "lightModifierList$delegate", "Lkotlin/Lazy;", "psiType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getPsiType", "()Lcom/intellij/psi/PsiType;", "psiType$delegate", "psiTypeForNullabilityAnnotation", "getPsiTypeForNullabilityAnnotation", "qualifiedNameForNullabilityAnnotation", "", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getNavigationElement", "getParent", "getType", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "hashCode", "", "isEquivalentTo", "another", "isValid", "isVarArgs", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightSuspendContinuationParameter extends LightParameter implements KtUltraLightElementWithNullabilityAnnotationDescriptorBased<KtParameter, PsiParameter>, KtLightParameter {
    private final KtParameter kotlinOrigin;
    private final KtFunction ktFunction;

    /* JADX INFO: renamed from: lightModifierList$delegate, reason: from kotlin metadata */
    private final Lazy lightModifierList;

    /* JADX INFO: renamed from: psiType$delegate, reason: from kotlin metadata */
    private final Lazy psiType;
    private final KtUltraLightSupport support;

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtUltraLightSuspendContinuationParameter(KtFunction ktFunction, KtUltraLightSupport support, final KtLightMethod method) {
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        PsiPrimitiveType NULL = PsiType.NULL;
        Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
        super(CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, NULL, method, method.getLanguage(), false, 16, null);
        this.ktFunction = ktFunction;
        this.support = support;
        this.psiType = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightSuspendContinuationParameter$psiType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiType invoke() {
                PsiType psiTypeAsPsiType;
                KotlinType ktType = this.this$0.getKtType();
                return (ktType == null || (psiTypeAsPsiType = UltraLightUtilsKt.asPsiType(ktType, this.this$0.support, TypeMappingMode.DEFAULT, method)) == null) ? PsiType.NULL : psiTypeAsPsiType;
            }
        });
        this.lightModifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightSimpleModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightSuspendContinuationParameter$lightModifierList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightSimpleModifierList invoke() {
                return new KtUltraLightSimpleModifierList(this.this$0, SetsKt.emptySet());
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        if (KtPsiUtilKt.isPrivate(this.ktFunction)) {
            return null;
        }
        return computeQualifiedNameForNullabilityAnnotation(getKtType());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public PsiType getPsiTypeForNullabilityAnnotation() {
        return getPsiType();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtParameter getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType getKtType() {
        KotlinType returnType;
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(this.ktFunction);
        FunctionDescriptor functionDescriptor = declarationDescriptorResolve instanceof FunctionDescriptor ? (FunctionDescriptor) declarationDescriptorResolve : null;
        if (functionDescriptor == null || (returnType = functionDescriptor.getReturnType()) == null) {
            return null;
        }
        return DescriptorUtilKt.getContinuationOfTypeOrAny(this.support.getModuleDescriptor(), returnType);
    }

    private final PsiType getPsiType() {
        return (PsiType) this.psiType.getValue();
    }

    private final KtUltraLightSimpleModifierList getLightModifierList() {
        return (KtUltraLightSimpleModifierList) this.lightModifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        PsiType psiType = getPsiType();
        Intrinsics.checkNotNullExpressionValue(psiType, "<get-psiType>(...)");
        return psiType;
    }

    public boolean equals(Object other) {
        return other == this || ((other instanceof KtUltraLightSuspendContinuationParameter) && ((KtUltraLightSuspendContinuationParameter) other).ktFunction == this.ktFunction);
    }

    public int hashCode() {
        return get$name().hashCode();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return getLightModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = this.ktFunction.getNavigationElement();
        Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
        return navigationElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = this.ktFunction.getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.ktFunction.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = this.ktFunction.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        PsiParameterList parameterList = getMethod().getParameterList();
        Intrinsics.checkNotNullExpressionValue(parameterList, "getParameterList(...)");
        return parameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return (another instanceof KtUltraLightSuspendContinuationParameter) && Intrinsics.areEqual(((KtUltraLightSuspendContinuationParameter) another).getPsiType(), getPsiType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new KtUltraLightSuspendContinuationParameter(this.ktFunction, this.support, getMethod());
    }
}
