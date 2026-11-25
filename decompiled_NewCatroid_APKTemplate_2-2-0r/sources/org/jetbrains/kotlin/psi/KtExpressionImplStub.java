package org.jetbrains.kotlin.psi;

import kotlin.jvm.functions.Function1;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtExpressionImplStub<T extends StubElement<?>> extends KtElementImplStub<T> implements KtExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
            case 6:
                objArr[0] = "newElement";
                break;
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtExpressionImplStub";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 5) {
            objArr[1] = SchemaSymbols.ATTVAL_REPLACE;
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtExpressionImplStub";
        } else {
            objArr[1] = "rawReplace";
        }
        if (i == 3) {
            objArr[2] = "accept";
        } else if (i == 4) {
            objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
        } else if (i != 5) {
            if (i == 6) {
                objArr[2] = "rawReplace";
            } else if (i != 7) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtExpressionImplStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtExpressionImplStub(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitExpression(this, d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        PsiElement psiElementReplaceExpression = KtExpressionImpl.INSTANCE.replaceExpression(this, psiElement, true, new Function1() { // from class: org.jetbrains.kotlin.psi.KtExpressionImplStub$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.rawReplace((PsiElement) obj);
            }
        });
        if (psiElementReplaceExpression == null) {
            $$$reportNull$$$0(5);
        }
        return psiElementReplaceExpression;
    }

    public PsiElement rawReplace(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        PsiElement psiElementReplace = super.replace(psiElement);
        if (psiElementReplace == null) {
            $$$reportNull$$$0(7);
        }
        return psiElementReplace;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        T stub = getStub();
        if (stub != null) {
            return stub.getParentStub().getPsi();
        }
        return super.getLightParent();
    }
}
