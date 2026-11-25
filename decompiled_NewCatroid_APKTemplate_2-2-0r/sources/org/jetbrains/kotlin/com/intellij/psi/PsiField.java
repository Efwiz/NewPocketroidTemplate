package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmField;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiField extends JvmField, PsiDocCommentOwner, PsiJvmMember, PsiVariable {
    public static final PsiField[] EMPTY_ARRAY = new PsiField[0];
    public static final ArrayFactory<PsiField> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiField$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiField.lambda$static$0(i);
        }
    };

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiIdentifier mo7392getNameIdentifier();

    @Override // 
    PsiType getType();

    /* JADX INFO: renamed from: setInitializer */
    void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException;

    static /* synthetic */ PsiField[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiField[i];
    }
}
