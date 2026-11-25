package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Objects;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLambdaExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class LambdaExpressionElementType extends FunctionalExpressionElementType<PsiLambdaExpression> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 3:
                objArr[0] = "funExpr";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/LambdaExpressionElementType";
                break;
            case 6:
            case 8:
                objArr[0] = "parameterList";
                break;
            case 10:
                objArr[0] = "parameter";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/LambdaExpressionElementType";
        } else {
            objArr[1] = "getPresentableText";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "getPresentableText";
                break;
            case 4:
                break;
            case 5:
            case 6:
                objArr[2] = "getLambdaPresentableText";
                break;
            case 7:
            case 8:
                objArr[2] = "formatParameterList";
                break;
            case 9:
            case 10:
                objArr[2] = "formatParameter";
                break;
            default:
                objArr[2] = "createPsi";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public LambdaExpressionElementType() {
        super("LAMBDA_EXPRESSION");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiLambdaExpression createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiLambdaExpressionImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiLambdaExpression createPsi(FunctionalExpressionStub<PsiLambdaExpression> functionalExpressionStub) {
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiLambdaExpressionImpl(functionalExpressionStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new CompositeElement(this) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.LambdaExpressionElementType.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "child";
                } else {
                    objArr[0] = "newElement";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/LambdaExpressionElementType$1";
                if (i != 2) {
                    objArr[2] = "replaceChildInternal";
                } else {
                    objArr[2] = "getChildRole";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
            public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
                if (aSTNode == null) {
                    $$$reportNull$$$0(0);
                }
                if (treeElement == null) {
                    $$$reportNull$$$0(1);
                }
                super.replaceChildInternal(aSTNode, JavaSourceUtil.addParenthToReplacedChild(aSTNode, treeElement, getManager()));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
            public int getChildRole(ASTNode aSTNode) {
                if (aSTNode == null) {
                    $$$reportNull$$$0(2);
                }
                IElementType elementType = aSTNode.getElementType();
                if (elementType == JavaTokenType.ARROW) {
                    return 252;
                }
                if (elementType == JavaElementType.PARAMETER_LIST) {
                    return 14;
                }
                return elementType == JavaElementType.CODE_BLOCK ? 18 : 64;
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.FunctionalExpressionElementType
    protected String getPresentableText(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        String lambdaPresentableText = getLambdaPresentableText(lighterAST, (LighterASTNode) Objects.requireNonNull(LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaStubElementTypes.PARAMETER_LIST)));
        if (lambdaPresentableText == null) {
            $$$reportNull$$$0(4);
        }
        return lambdaPresentableText;
    }

    private static String getLambdaPresentableText(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(5);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(6);
        }
        StringBuilder sb = new StringBuilder(lighterASTNode.getEndOffset() - lighterASTNode.getStartOffset());
        formatParameterList(lighterAST, lighterASTNode, sb);
        sb.append(" -> {...}");
        return sb.toString();
    }

    private static void formatParameterList(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb) {
        if (lighterAST == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(8);
        }
        boolean z = false;
        boolean z2 = true;
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaTokenType.LPARENTH) {
                sb.append('(');
                z = true;
            } else if (tokenType == JavaStubElementTypes.PARAMETER) {
                if (!z2) {
                    sb.append(", ");
                }
                formatParameter(lighterAST, lighterASTNode2, sb);
                if (z2) {
                    z2 = false;
                }
            }
        }
        if (z) {
            sb.append(')');
        }
    }

    private static void formatParameter(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb) {
        if (lighterAST == null) {
            $$$reportNull$$$0(9);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(10);
        }
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaElementType.TYPE) {
                formatType(lighterAST, lighterASTNode2, sb);
                sb.append(StringUtil.SPACE);
            } else if (tokenType == JavaTokenType.IDENTIFIER) {
                sb.append(RecordUtil.intern(lighterAST.getCharTable(), lighterASTNode2));
            }
        }
    }

    private static void formatType(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb) {
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaElementType.JAVA_CODE_REFERENCE) {
                formatCodeReference(lighterAST, lighterASTNode2, sb);
            } else if (tokenType == JavaElementType.TYPE) {
                formatType(lighterAST, lighterASTNode2, sb);
            } else if (tokenType == JavaTokenType.QUEST) {
                sb.append("? ");
            } else if (ElementType.KEYWORD_BIT_SET.contains(tokenType)) {
                sb.append(RecordUtil.intern(lighterAST.getCharTable(), lighterASTNode2));
                if (!ElementType.PRIMITIVE_TYPE_BIT_SET.contains(tokenType)) {
                    sb.append(" ");
                }
            } else if (tokenType == JavaTokenType.ELLIPSIS) {
                sb.append("...");
            } else if (tokenType == JavaTokenType.RBRACKET) {
                sb.append("]");
            } else if (tokenType == JavaTokenType.LBRACKET) {
                sb.append("[");
            }
        }
    }

    private static void formatCodeReference(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb) {
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaTokenType.IDENTIFIER) {
                sb.append(RecordUtil.intern(lighterAST.getCharTable(), lighterASTNode2));
            } else if (tokenType == JavaElementType.REFERENCE_PARAMETER_LIST) {
                formatTypeParameters(lighterAST, lighterASTNode2, sb);
            }
        }
    }

    private static void formatTypeParameters(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb) {
        List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(lighterAST, lighterASTNode, JavaElementType.TYPE);
        if (childrenOfType.isEmpty()) {
            return;
        }
        sb.append('<');
        for (int i = 0; i < childrenOfType.size(); i++) {
            formatType(lighterAST, childrenOfType.get(i), sb);
            if (i != childrenOfType.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append('>');
    }
}
