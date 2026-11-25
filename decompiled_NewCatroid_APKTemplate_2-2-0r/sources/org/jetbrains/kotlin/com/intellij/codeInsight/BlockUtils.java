package org.jetbrains.kotlin.com.intellij.codeInsight;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public final class BlockUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "block";
        } else if (i == 2) {
            objArr[0] = "parentBlock";
        } else if (i != 3) {
            objArr[0] = "statement";
        } else {
            objArr[0] = "orig";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/BlockUtils";
        if (i == 1 || i == 2) {
            objArr[2] = "containsConflictingDeclarations";
        } else if (i != 3) {
            objArr[2] = "expandSingleStatementToBlockStatement";
        } else {
            objArr[2] = "inlineCodeBlock";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static <T extends PsiStatement> T expandSingleStatementToBlockStatement(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (t instanceof PsiBlockStatement) {
            return t;
        }
        PsiBlockStatement psiBlockStatement = (PsiBlockStatement) JavaPsiFacade.getElementFactory(t.getProject()).createStatementFromText("{\n}", t);
        psiBlockStatement.getCodeBlock().add(t);
        PsiBlockStatement psiBlockStatement2 = (PsiBlockStatement) t.replace(psiBlockStatement);
        PsiElement nextSibling = psiBlockStatement2.getNextSibling();
        if ((nextSibling instanceof PsiWhiteSpace) && PsiUtil.isJavaToken(nextSibling.getNextSibling(), JavaTokenType.ELSE_KEYWORD)) {
            nextSibling.delete();
        }
        return (T) psiBlockStatement2.getCodeBlock().getStatements()[0];
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0047  */
    /* JADX WARN: Code duplicated, block: B:23:0x004d  */
    /* JADX WARN: Code duplicated, block: B:24:0x0056  */
    public static void unwrapTryBlock(PsiTryStatement psiTryStatement) {
        boolean z;
        PsiCodeBlock tryBlock;
        PsiElement firstBodyElement;
        PsiElement lastBodyElement;
        PsiUtilCore.ensureValid(psiTryStatement);
        PsiCodeBlock tryBlock2 = psiTryStatement.getTryBlock();
        if (tryBlock2 == null) {
            return;
        }
        PsiElement parent = psiTryStatement.getMyParent();
        if (parent instanceof PsiStatement) {
            PsiStatement[] statements = tryBlock2.getStatements();
            if (statements.length != 1 || (statements[0] instanceof PsiDeclarationStatement)) {
                psiTryStatement = (PsiTryStatement) expandSingleStatementToBlockStatement(psiTryStatement);
            } else {
                z = true;
            }
            tryBlock = psiTryStatement.getTryBlock();
            firstBodyElement = tryBlock.getFirstBodyElement();
            if (z) {
                firstBodyElement = skip(firstBodyElement, true);
            }
            if (z) {
                lastBodyElement = skip(tryBlock.getLastBodyElement(), false);
            } else {
                lastBodyElement = tryBlock.getLastBodyElement();
            }
            psiTryStatement.getMyParent().addRangeBefore(firstBodyElement, lastBodyElement, psiTryStatement);
            psiTryStatement.delete();
        }
        if (!(parent instanceof PsiCodeBlock)) {
            return;
        }
        if (containsConflictingDeclarations(tryBlock2, (PsiCodeBlock) parent)) {
            psiTryStatement = (PsiTryStatement) expandSingleStatementToBlockStatement(psiTryStatement);
        }
        z = false;
        tryBlock = psiTryStatement.getTryBlock();
        firstBodyElement = tryBlock.getFirstBodyElement();
        if (z) {
            firstBodyElement = skip(firstBodyElement, true);
        }
        if (z) {
            lastBodyElement = skip(tryBlock.getLastBodyElement(), false);
        } else {
            lastBodyElement = tryBlock.getLastBodyElement();
        }
        psiTryStatement.getMyParent().addRangeBefore(firstBodyElement, lastBodyElement, psiTryStatement);
        psiTryStatement.delete();
    }

    private static PsiElement skip(PsiElement psiElement, boolean z) {
        if (psiElement instanceof PsiWhiteSpace) {
            return z ? psiElement.getNextSibling() : psiElement.getPrevSibling();
        }
        return psiElement;
    }

    public static boolean containsConflictingDeclarations(PsiCodeBlock psiCodeBlock, PsiCodeBlock psiCodeBlock2) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(1);
        }
        if (psiCodeBlock2 == null) {
            $$$reportNull$$$0(2);
        }
        PsiStatement[] statements = psiCodeBlock.getStatements();
        if (statements.length == 0) {
            return false;
        }
        final int endOffset = psiCodeBlock.getTextRange().getEndOffset();
        List list = (List) SyntaxTraverser.psiTraverser(psiCodeBlock2).filter(PsiCodeBlock.class).filter((Condition<? super C>) new Condition() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.BlockUtils$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return BlockUtils.lambda$containsConflictingDeclarations$0(endOffset, (PsiCodeBlock) obj);
            }
        }).addAllTo(new SmartList());
        PsiResolveHelper resolveHelper = JavaPsiFacade.getInstance(psiCodeBlock.getProject()).getResolveHelper();
        for (PsiStatement psiStatement : statements) {
            if (psiStatement instanceof PsiDeclarationStatement) {
                for (PsiElement psiElement : ((PsiDeclarationStatement) psiStatement).getDeclaredElements()) {
                    if (psiElement instanceof PsiLocalVariable) {
                        String myName = ((PsiLocalVariable) psiElement).getMyName();
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            final PsiVariable psiVariableResolveAccessibleReferencedVariable = resolveHelper.resolveAccessibleReferencedVariable(myName, (PsiCodeBlock) it.next());
                            if (psiVariableResolveAccessibleReferencedVariable instanceof PsiLocalVariable) {
                                return true;
                            }
                            if (psiVariableResolveAccessibleReferencedVariable instanceof PsiField) {
                                Iterator it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (!SyntaxTraverser.psiTraverser((PsiCodeBlock) it2.next()).filter(PsiReferenceExpression.class).filter((Condition<? super C>) new Condition() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.BlockUtils$$ExternalSyntheticLambda1
                                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                                        public final boolean value(Object obj) {
                                            return BlockUtils.lambda$containsConflictingDeclarations$1(psiVariableResolveAccessibleReferencedVariable, (PsiReferenceExpression) obj);
                                        }
                                    }).isEmpty()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    static /* synthetic */ boolean lambda$containsConflictingDeclarations$0(int i, PsiCodeBlock psiCodeBlock) {
        return psiCodeBlock.getTextRange().getEndOffset() > i;
    }

    static /* synthetic */ boolean lambda$containsConflictingDeclarations$1(PsiVariable psiVariable, PsiReferenceExpression psiReferenceExpression) {
        return psiReferenceExpression.resolve() == psiVariable;
    }
}
