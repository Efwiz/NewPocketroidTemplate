package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPatternVariableImpl extends CompositePsiElement implements PsiPatternVariable, Constants {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 2:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            case 2:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPatternVariableImpl";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getNameIdentifier";
                break;
            case 2:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPatternVariableImpl";
                break;
            case 3:
                objArr[1] = "getPattern";
                break;
            case 4:
                objArr[1] = "getType";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[1] = "getDeclarationScope";
                break;
            case 12:
                objArr[1] = "getTypeElement";
                break;
            case 13:
                objArr[1] = "getName";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 2:
                objArr[2] = "accept";
                break;
            case 14:
                objArr[2] = "hasModifierProperty";
                break;
            default:
                objArr[2] = "setName";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            case 2:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    public PsiPatternVariableImpl() {
        super(PATTERN_VARIABLE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiIdentifier setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return (PsiIdentifier) PsiImplUtil.setName(mo7392getNameIdentifier(), str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) PsiTreeUtil.getChildOfType(this, PsiIdentifier.class);
        if (psiIdentifier == null) {
            Logger.getInstance((Class<?>) PsiPatternVariableImpl.class).error("Pattern without identifier", new Attachment("File content", getContainingFile().getText()));
        }
        PsiIdentifier psiIdentifier2 = (PsiIdentifier) Objects.requireNonNull(psiIdentifier);
        if (psiIdentifier2 == null) {
            $$$reportNull$$$0(1);
        }
        return psiIdentifier2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPatternVariable(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable
    public PsiPattern getPattern() {
        PsiPattern psiPattern = (PsiPattern) getLightParent();
        if (psiPattern == null) {
            $$$reportNull$$$0(3);
        }
        return psiPattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        PsiType type = JavaSharedImplUtil.getType(getTypeElement(), mo7392getNameIdentifier());
        if (type == null) {
            $$$reportNull$$$0(4);
        }
        return type;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0076  */
    /* JADX WARN: Code duplicated, block: B:40:0x0082 A[LOOP:3: B:38:0x007a->B:40:0x0082, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:43:0x008d  */
    /* JADX WARN: Code duplicated, block: B:45:0x0092  */
    /* JADX WARN: Code duplicated, block: B:48:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x009a  */
    /* JADX WARN: Code duplicated, block: B:51:0x00a0 A[LOOP:2: B:51:0x00a0->B:53:0x00a8, LOOP_START, PHI: r0
      0x00a0: PHI (r0v7 org.jetbrains.kotlin.com.intellij.psi.PsiElement) = (r0v3 org.jetbrains.kotlin.com.intellij.psi.PsiElement), (r0v9 org.jetbrains.kotlin.com.intellij.psi.PsiElement) binds: [B:47:0x0096, B:53:0x00a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:53:0x00a8 A[LOOP:2: B:51:0x00a0->B:53:0x00a8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:56:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:58:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:62:0x00c5 A[LOOP:1: B:60:0x00bd->B:62:0x00c5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:65:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:67:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:83:0x007a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x0096 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:0x00bd A[SYNTHETIC] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: getDeclarationScope */
    public PsiElement getMethod() {
        PsiElement psiElement;
        PsiElement lightParent;
        PsiElement lightParent2;
        PsiElement lightParent3;
        PsiElement parent = getPattern().getLightParent();
        if (!(parent instanceof PsiInstanceOfExpression) && !(parent instanceof PsiCaseLabelElementList) && !(parent instanceof PsiPattern)) {
            if (parent == null) {
                $$$reportNull$$$0(5);
            }
            return parent;
        }
        PsiElement lightParent4 = parent.getLightParent();
        boolean z = false;
        while (true) {
            PsiElement psiElement2 = lightParent4;
            psiElement = parent;
            parent = psiElement2;
            if (!(parent instanceof PsiParenthesizedExpression)) {
                if ((parent instanceof PsiConditionalExpression) && psiElement == ((PsiConditionalExpression) parent).getCondition()) {
                    if (parent == null) {
                        $$$reportNull$$$0(6);
                    }
                    return parent;
                }
                if ((parent instanceof PsiPrefixExpression) && ((PsiPrefixExpression) parent).getOperationTokenType().equals(EXCL)) {
                    z = !z;
                } else if (parent instanceof PsiPolyadicExpression) {
                    IElementType operationTokenType = ((PsiPolyadicExpression) parent).getOperationTokenType();
                    if ((!operationTokenType.equals(ANDAND) || z) && (!operationTokenType.equals(OROR) || !z)) {
                        if (parent instanceof PsiIfStatement) {
                            while (parent.getLightParent() instanceof PsiLabeledStatement) {
                                parent = parent.getLightParent();
                            }
                            lightParent = parent.getLightParent();
                            if (lightParent == null) {
                                $$$reportNull$$$0(7);
                            }
                            return lightParent;
                        }
                        if (!(parent instanceof PsiConditionalLoopStatement)) {
                            if (parent instanceof PsiSwitchLabelStatementBase) {
                                while (parent.getLightParent() instanceof PsiLabeledStatement) {
                                    parent = parent.getLightParent();
                                }
                                lightParent3 = parent.getLightParent();
                                if (lightParent3 == null) {
                                    $$$reportNull$$$0(10);
                                }
                                return lightParent3;
                            }
                            if (!(parent instanceof PsiPattern) && !(parent instanceof PsiCaseLabelElementList) && (!(psiElement instanceof PsiPattern) || !(parent instanceof PsiInstanceOfExpression))) {
                                break;
                            }
                        } else {
                            if (!z) {
                                if (parent == null) {
                                    $$$reportNull$$$0(8);
                                }
                                return parent;
                            }
                            while (parent.getLightParent() instanceof PsiLabeledStatement) {
                                parent = parent.getLightParent();
                            }
                            lightParent2 = parent.getLightParent();
                            if (lightParent2 == null) {
                                $$$reportNull$$$0(9);
                            }
                            return lightParent2;
                        }
                    }
                } else {
                    if (parent instanceof PsiIfStatement) {
                        while (parent.getLightParent() instanceof PsiLabeledStatement) {
                            parent = parent.getLightParent();
                        }
                        lightParent = parent.getLightParent();
                        if (lightParent == null) {
                            $$$reportNull$$$0(7);
                        }
                        return lightParent;
                    }
                    if (!(parent instanceof PsiConditionalLoopStatement)) {
                        if (!z) {
                            if (parent == null) {
                                $$$reportNull$$$0(8);
                            }
                            return parent;
                        }
                        while (parent.getLightParent() instanceof PsiLabeledStatement) {
                            parent = parent.getLightParent();
                        }
                        lightParent2 = parent.getLightParent();
                        if (lightParent2 == null) {
                            $$$reportNull$$$0(9);
                        }
                        return lightParent2;
                    }
                    if (parent instanceof PsiSwitchLabelStatementBase) {
                        while (parent.getLightParent() instanceof PsiLabeledStatement) {
                            parent = parent.getLightParent();
                        }
                        lightParent3 = parent.getLightParent();
                        if (lightParent3 == null) {
                            $$$reportNull$$$0(10);
                        }
                        return lightParent3;
                    }
                    if (!(parent instanceof PsiPattern)) {
                        continue;
                    }
                }
            }
            lightParent4 = parent.getLightParent();
        }
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable, org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) Objects.requireNonNull((PsiTypeElement) PsiTreeUtil.getChildOfType(this, PsiTypeElement.class));
        if (psiTypeElement == null) {
            $$$reportNull$$$0(12);
        }
        return psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        String text = mo7392getNameIdentifier().getText();
        if (text == null) {
            $$$reportNull$$$0(13);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return mo7392getNameIdentifier().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return (PsiModifierList) findPsiChildByType(JavaElementType.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getMethod());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        PsiPattern pattern = getPattern();
        if ((pattern instanceof PsiTypeTestPattern) && (pattern.getLightParent() instanceof PsiInstanceOfExpression)) {
            pattern.replace(getTypeElement());
        } else {
            super.delete();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPatternVariable:" + get$name();
    }
}
