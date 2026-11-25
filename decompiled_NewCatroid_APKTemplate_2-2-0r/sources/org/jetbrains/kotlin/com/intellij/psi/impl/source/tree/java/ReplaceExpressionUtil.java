package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.ExpressionParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class ReplaceExpressionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ReplaceExpressionUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "oldExpr", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ReplaceExpressionUtil", "isNeedParenthesis"));
    }

    public static boolean isNeedParenthesis(ASTNode aSTNode, ASTNode aSTNode2) {
        IElementType operationTokenType;
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        ASTNode treeParent = aSTNode.getTreeParent();
        if (!ElementType.EXPRESSION_BIT_SET.contains(treeParent.getElementType())) {
            return false;
        }
        int expressionPriority = getExpressionPriority(aSTNode2);
        int expressionPriority2 = getExpressionPriority(treeParent);
        if (expressionPriority != -1 && expressionPriority2 != -1) {
            if (expressionPriority > expressionPriority2) {
                return false;
            }
            IElementType elementType = treeParent.getElementType();
            if (elementType == JavaElementType.ASSIGNMENT_EXPRESSION) {
                return expressionPriority < expressionPriority2 || ((CompositeElement) treeParent).getChildRole(aSTNode) == 61;
            }
            if (elementType == JavaElementType.CONDITIONAL_EXPRESSION) {
                int childRole = ((CompositeElement) treeParent).getChildRole(aSTNode);
                if (childRole == 112) {
                    return false;
                }
                return expressionPriority < expressionPriority2 || childRole != 113;
            }
            if (elementType != JavaElementType.BINARY_EXPRESSION && elementType != JavaElementType.POLYADIC_EXPRESSION) {
                if (elementType == JavaElementType.POSTFIX_EXPRESSION) {
                    return true;
                }
                if (elementType == JavaElementType.INSTANCE_OF_EXPRESSION || elementType == JavaElementType.PREFIX_EXPRESSION || elementType == JavaElementType.TYPE_CAST_EXPRESSION || elementType == JavaElementType.REFERENCE_EXPRESSION || elementType == JavaElementType.METHOD_REF_EXPRESSION) {
                    return expressionPriority < expressionPriority2;
                }
                if (elementType == JavaElementType.ARRAY_ACCESS_EXPRESSION) {
                    int childRole2 = ((CompositeElement) treeParent).getChildRole(aSTNode);
                    return (childRole2 == 76 || childRole2 == 89 || expressionPriority >= expressionPriority2) ? false : true;
                }
                if (elementType != JavaElementType.METHOD_CALL_EXPRESSION && elementType != JavaElementType.NEW_EXPRESSION && elementType != JavaElementType.ARRAY_INITIALIZER_EXPRESSION && elementType != JavaElementType.PARENTH_EXPRESSION && elementType != JavaElementType.LITERAL_EXPRESSION && elementType != JavaElementType.THIS_EXPRESSION && elementType != JavaElementType.SUPER_EXPRESSION && elementType != JavaElementType.CLASS_OBJECT_ACCESS_EXPRESSION && elementType != JavaElementType.LAMBDA_EXPRESSION && elementType != JavaElementType.SWITCH_EXPRESSION) {
                    LOG.assertTrue(false);
                }
                return false;
            }
            if (expressionPriority < expressionPriority2) {
                return true;
            }
            IElementType operationTokenType2 = ((PsiPolyadicExpression) SourceTreeToPsiMap.treeElementToPsi(treeParent)).getOperationTokenType();
            IElementType elementType2 = aSTNode2.getElementType();
            if (((CompositeElement) treeParent).getChildRole(aSTNode) == 61) {
                return false;
            }
            if ((elementType2 != JavaElementType.BINARY_EXPRESSION && elementType2 != JavaElementType.POLYADIC_EXPRESSION) || ((operationTokenType = ((PsiPolyadicExpression) aSTNode2).getOperationTokenType()) != JavaTokenType.DIV && operationTokenType != JavaTokenType.PERC)) {
                return (operationTokenType2 == JavaTokenType.PLUS || operationTokenType2 == JavaTokenType.ASTERISK || operationTokenType2 == JavaTokenType.ANDAND || operationTokenType2 == JavaTokenType.OROR) ? false : true;
            }
        }
        return true;
    }

    private static int getExpressionPriority(ASTNode aSTNode) {
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.ASSIGNMENT_EXPRESSION) {
            return 0;
        }
        if (elementType == JavaElementType.CONDITIONAL_EXPRESSION) {
            return 1;
        }
        if (elementType == JavaElementType.BINARY_EXPRESSION || elementType == JavaElementType.POLYADIC_EXPRESSION) {
            IElementType operationTokenType = ((PsiPolyadicExpression) SourceTreeToPsiMap.treeElementToPsi(aSTNode)).getOperationTokenType();
            if (operationTokenType == JavaTokenType.OROR) {
                return 2;
            }
            if (operationTokenType == JavaTokenType.ANDAND) {
                return 3;
            }
            if (operationTokenType == JavaTokenType.OR) {
                return 4;
            }
            if (operationTokenType == JavaTokenType.XOR) {
                return 5;
            }
            if (operationTokenType == JavaTokenType.AND) {
                return 6;
            }
            if (operationTokenType == JavaTokenType.EQEQ || operationTokenType == JavaTokenType.NE) {
                return 7;
            }
            if (operationTokenType != JavaTokenType.LT && operationTokenType != JavaTokenType.GT && operationTokenType != JavaTokenType.LE && operationTokenType != JavaTokenType.GE) {
                if (ExpressionParser.SHIFT_OPS.contains(operationTokenType)) {
                    return 9;
                }
                if (ExpressionParser.ADDITIVE_OPS.contains(operationTokenType)) {
                    return 10;
                }
                if (ExpressionParser.MULTIPLICATIVE_OPS.contains(operationTokenType)) {
                    return 11;
                }
            }
            return 8;
        }
        if (elementType == JavaElementType.INSTANCE_OF_EXPRESSION) {
            return 8;
        }
        if (elementType == JavaElementType.PREFIX_EXPRESSION || elementType == JavaElementType.TYPE_CAST_EXPRESSION) {
            return 12;
        }
        if (elementType == JavaElementType.POSTFIX_EXPRESSION || elementType == JavaElementType.SWITCH_EXPRESSION) {
            return 13;
        }
        return (elementType == JavaElementType.LITERAL_EXPRESSION || elementType == JavaElementType.REFERENCE_EXPRESSION || elementType == JavaElementType.THIS_EXPRESSION || elementType == JavaElementType.SUPER_EXPRESSION || elementType == JavaElementType.PARENTH_EXPRESSION || elementType == JavaElementType.METHOD_CALL_EXPRESSION || elementType == JavaElementType.CLASS_OBJECT_ACCESS_EXPRESSION || elementType == JavaElementType.NEW_EXPRESSION || elementType == JavaElementType.ARRAY_ACCESS_EXPRESSION || elementType == JavaElementType.ARRAY_INITIALIZER_EXPRESSION || elementType == JavaElementType.JAVA_CODE_REFERENCE || elementType == JavaElementType.METHOD_REF_EXPRESSION || elementType == JavaElementType.LAMBDA_EXPRESSION || elementType == JavaElementType.EMPTY_EXPRESSION) ? 14 : -1;
    }
}
