package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.util.function.Supplier;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodReferenceExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class MethodReferenceElementType extends FunctionalExpressionElementType<PsiMethodReferenceExpression> {
    private static final NotNullLazyValue<TokenSet> EXCLUDE_FROM_PRESENTABLE_TEXT = NotNullLazyValue.lazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.MethodReferenceElementType$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return TokenSet.orSet(ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET, TokenSet.create(JavaElementType.REFERENCE_PARAMETER_LIST));
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = Constants.TYPE_TREE;
        } else if (i == 3) {
            objArr[0] = "funExpr";
        } else if (i != 4) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/MethodReferenceElementType";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/MethodReferenceElementType";
        } else {
            objArr[1] = "getPresentableText";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "getPresentableText";
        } else if (i != 4) {
            objArr[2] = "createPsi";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public MethodReferenceElementType() {
        super("METHOD_REF_EXPRESSION");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiMethodReferenceExpression createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiMethodReferenceExpressionImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiMethodReferenceExpression createPsi(FunctionalExpressionStub<PsiMethodReferenceExpression> functionalExpressionStub) {
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiMethodReferenceExpressionImpl(functionalExpressionStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new CompositeElement(this) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.MethodReferenceElementType.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "child";
                } else {
                    objArr[0] = "newElement";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/MethodReferenceElementType$1";
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
                if (elementType == JavaTokenType.DOUBLE_COLON) {
                    return 253;
                }
                if (elementType == JavaTokenType.IDENTIFIER) {
                    return 53;
                }
                return elementType == JavaElementType.REFERENCE_EXPRESSION ? 119 : 64;
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
        String filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode, EXCLUDE_FROM_PRESENTABLE_TEXT.getValue());
        if (filteredString == null) {
            $$$reportNull$$$0(4);
        }
        return filteredString;
    }
}
