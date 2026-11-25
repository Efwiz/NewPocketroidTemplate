package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Set;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class ClsEnumConstantImpl extends ClsFieldImpl implements PsiEnumConstant {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "buffer";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsEnumConstantImpl";
        } else if (i != 5) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "name";
        }
        if (i == 3) {
            objArr[1] = "resolveMethodGenerics";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsEnumConstantImpl";
        } else {
            objArr[1] = "getType";
        }
        if (i == 1) {
            objArr[2] = "appendMirrorText";
        } else if (i == 2) {
            objArr[2] = "setMirror";
        } else if (i != 3 && i != 4) {
            if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "hasModifierProperty";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public Object computeConstantValue(Set<PsiVariable> set) {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant, org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getInitializingClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstructorCall
    public PsiMethod resolveConstructor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsEnumConstantImpl(PsiFieldStub psiFieldStub) {
        super(psiFieldStub);
        if (psiFieldStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        appendText(mo6091getDocComment(), i, sb, "go_to_next_line_and_indent");
        appendText(mo6095getModifierList(), i, sb, "");
        appendText(mo7392getNameIdentifier(), i, sb);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        setMirrorCheckingType(treeElement, null);
        PsiField psiField = (PsiField) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirrorIfPresent(mo6091getDocComment(), psiField.mo6091getDocComment());
        setMirror(mo6095getModifierList(), psiField.mo6095getModifierList());
        setMirror(mo7392getNameIdentifier(), psiField.mo7392getNameIdentifier());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        JavaResolveResult javaResolveResult = JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getOrCreateInitializingClass() {
        throw new IncorrectOperationException("cannot create initializing class in cls enum constant");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(getProject()).createType(mo6092getContainingClass());
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return "public".equals(str) || "static".equals(str) || "final".equals(str);
    }
}
