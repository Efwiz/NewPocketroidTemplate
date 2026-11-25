package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IKeywordElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaLightTreeUtil {
    /* JADX WARN: Code duplicated, block: B:22:0x0031  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 3) {
            objArr[0] = "node";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaLightTreeUtil";
        } else if (i == 8) {
            objArr[0] = "node";
        } else if (i == 11) {
            objArr[0] = "modifierKeyword";
        } else if (i != 13) {
            objArr[0] = org.eclipse.jgit.lib.Constants.TYPE_TREE;
        } else {
            objArr[0] = "node";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaLightTreeUtil";
        } else {
            objArr[1] = "getExpressionChildren";
        }
        switch (i) {
            case 1:
                objArr[2] = "getNameIdentifierText";
                break;
            case 2:
            case 3:
                objArr[2] = "getExpressionChildren";
                break;
            case 4:
                break;
            case 5:
                objArr[2] = "findExpressionChild";
                break;
            case 6:
                objArr[2] = "skipParenthesesCastsDown";
                break;
            case 7:
            case 8:
                objArr[2] = "isPrimitiveCast";
                break;
            case 9:
                objArr[2] = "skipParenthesesDown";
                break;
            case 10:
            case 11:
                objArr[2] = "hasExplicitModifier";
                break;
            case 12:
            case 13:
                objArr[2] = "isNullLiteralExpression";
                break;
            default:
                objArr[2] = "getArgList";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public static List<LighterASTNode> getArgList(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(0);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.ANONYMOUS_CLASS);
        if (lighterASTNodeFirstChildOfType != null) {
            lighterASTNode = lighterASTNodeFirstChildOfType;
        }
        LighterASTNode lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.EXPRESSION_LIST);
        if (lighterASTNodeFirstChildOfType2 == null) {
            return null;
        }
        return getExpressionChildren(lighterAST, lighterASTNodeFirstChildOfType2);
    }

    public static String getNameIdentifierText(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaTokenType.IDENTIFIER);
        if (lighterASTNodeFirstChildOfType != null) {
            return RecordUtil.intern(lighterAST.getCharTable(), lighterASTNodeFirstChildOfType);
        }
        return null;
    }

    public static List<LighterASTNode> getExpressionChildren(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(lighterAST, lighterASTNode, ElementType.EXPRESSION_BIT_SET);
        if (childrenOfType == null) {
            $$$reportNull$$$0(4);
        }
        return childrenOfType;
    }

    public static LighterASTNode findExpressionChild(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(5);
        }
        return LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, ElementType.EXPRESSION_BIT_SET);
    }

    public static LighterASTNode skipParenthesesCastsDown(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(6);
        }
        while (lighterASTNode != null) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if ((tokenType != JavaElementType.PARENTH_EXPRESSION && tokenType != JavaElementType.TYPE_CAST_EXPRESSION) || (tokenType == JavaElementType.TYPE_CAST_EXPRESSION && isPrimitiveCast(lighterAST, lighterASTNode))) {
                break;
            }
            lighterASTNode = findExpressionChild(lighterAST, lighterASTNode);
        }
        return lighterASTNode;
    }

    public static boolean isPrimitiveCast(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        LighterASTNode lighterASTNode2;
        if (lighterAST == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(8);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.TYPE);
        return (lighterASTNodeFirstChildOfType == null || (lighterASTNode2 = (LighterASTNode) ContainerUtil.getOnlyItem(lighterAST.getChildren(lighterASTNodeFirstChildOfType))) == null || !(lighterASTNode2.getTokenType() instanceof IKeywordElementType)) ? false : true;
    }

    public static LighterASTNode skipParenthesesDown(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(9);
        }
        while (lighterASTNode != null && lighterASTNode.getTokenType() == JavaElementType.PARENTH_EXPRESSION) {
            lighterASTNode = findExpressionChild(lighterAST, lighterASTNode);
        }
        return lighterASTNode;
    }

    public static boolean hasExplicitModifier(LighterAST lighterAST, LighterASTNode lighterASTNode, IElementType iElementType) {
        if (lighterAST == null) {
            $$$reportNull$$$0(10);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(11);
        }
        return LightTreeUtil.firstChildOfType(lighterAST, LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.MODIFIER_LIST), iElementType) != null;
    }

    public static boolean isNullLiteralExpression(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(12);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(13);
        }
        return lighterASTNode.getTokenType() == JavaElementType.LITERAL_EXPRESSION && lighterAST.getChildren(lighterASTNode).get(0).getTokenType() == JavaTokenType.NULL_KEYWORD;
    }
}
