package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmMethod;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmReferenceType;
import org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMethod extends JvmMethod, PomRenameableTarget<PsiElement>, PsiDocCommentOwner, PsiMember, PsiModifierListOwner, PsiNameIdentifierOwner, PsiParameterListOwner, PsiTarget, PsiTypeParameterListOwner {
    public static final PsiMethod[] EMPTY_ARRAY = new PsiMethod[0];
    public static final ArrayFactory<PsiMethod> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiMethod$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiMethod.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiMethod";
        if (i != 1) {
            objArr[1] = "getParameters";
        } else {
            objArr[1] = "getThrowsTypes";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    @Deprecated
    PsiMethod findDeepestSuperMethod();

    PsiMethod[] findDeepestSuperMethods();

    List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z);

    PsiMethod[] findSuperMethods();

    PsiMethod[] findSuperMethods(PsiClass psiClass);

    PsiMethod[] findSuperMethods(boolean z);

    PsiCodeBlock getBody();

    HierarchicalMethodSignature getHierarchicalMethodSignature();

    /* JADX INFO: renamed from: getModifierList */
    PsiModifierList mo6095getModifierList();

    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiIdentifier mo7392getNameIdentifier();

    PsiParameterList getParameterList();

    @Override // 
    PsiType getReturnType();

    PsiTypeElement getReturnTypeElement();

    MethodSignature getSignature(PsiSubstitutor psiSubstitutor);

    PsiReferenceList getThrowsList();

    boolean isConstructor();

    boolean isVarArgs();

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    PsiElement setName(String str) throws IncorrectOperationException;

    static /* synthetic */ PsiMethod[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiMethod[i];
    }

    default boolean hasParameters() {
        return !getParameterList().isEmpty();
    }

    default JvmParameter[] getParameters() {
        PsiParameter[] parameters = getParameterList().getParameters();
        if (parameters == null) {
            $$$reportNull$$$0(0);
        }
        return parameters;
    }

    default JvmReferenceType[] getThrowsTypes() {
        PsiClassType[] referencedTypes = getThrowsList().getReferencedTypes();
        if (referencedTypes == null) {
            $$$reportNull$$$0(1);
        }
        return referencedTypes;
    }
}
