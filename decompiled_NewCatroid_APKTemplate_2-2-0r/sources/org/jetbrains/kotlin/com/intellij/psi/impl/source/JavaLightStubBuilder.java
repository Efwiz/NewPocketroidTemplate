package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.lang.impl.TokenSequence;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JavaLightStubBuilder extends LightStubBuilder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = org.eclipse.jgit.lib.Constants.TYPE_TREE;
                break;
            case 2:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaLightStubBuilder";
                break;
            case 3:
            case 7:
                objArr[0] = "parent";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
                objArr[0] = "node";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaLightStubBuilder";
        } else {
            objArr[1] = "createStubForFile";
        }
        switch (i) {
            case 2:
                break;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
                objArr[2] = "skipChildProcessingWhenBuildingStubs";
                break;
            case 5:
            case 9:
                objArr[2] = "isCodeBlockWithoutStubs";
                break;
            default:
                objArr[2] = "createStubForFile";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder
    protected StubElement<?> createStubForFile(PsiFile psiFile, LighterAST lighterAST) {
        String referenceText;
        LighterASTNode lighterASTNodeFirstChildOfType;
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        if (!(psiFile instanceof PsiJavaFile)) {
            StubElement<?> stubElementCreateStubForFile = super.createStubForFile(psiFile, lighterAST);
            if (stubElementCreateStubForFile == null) {
                $$$reportNull$$$0(2);
            }
            return stubElementCreateStubForFile;
        }
        LighterASTNode lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(lighterAST, lighterAST.getRoot(), JavaElementType.PACKAGE_STATEMENT);
        if (lighterASTNodeFirstChildOfType2 != null && (lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNodeFirstChildOfType2, JavaElementType.JAVA_CODE_REFERENCE)) != null) {
            referenceText = JavaSourceUtil.getReferenceText(lighterAST, lighterASTNodeFirstChildOfType);
        } else {
            referenceText = "";
        }
        return new PsiJavaFileStubImpl((PsiJavaFile) psiFile, referenceText, null, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder, org.jetbrains.kotlin.com.intellij.psi.StubBuilder
    public boolean skipChildProcessingWhenBuildingStubs(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(4);
        }
        IElementType elementType = aSTNode.getElementType();
        IElementType elementType2 = aSTNode2.getElementType();
        if (checkByTypes(elementType, elementType2)) {
            return true;
        }
        if (elementType2 == JavaElementType.CODE_BLOCK) {
            return isCodeBlockWithoutStubs(aSTNode2);
        }
        return false;
    }

    private static boolean isCodeBlockWithoutStubs(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        CodeBlockVisitor codeBlockVisitor = new CodeBlockVisitor();
        if (TreeUtil.isCollapsedChameleon(aSTNode)) {
            TokenSequence tokenSequencePerformLexing = TokenSequence.performLexing(aSTNode.getChars(), JavaParserDefinition.createLexer(PsiUtil.getLanguageLevel(aSTNode.getPsi())));
            for (int i = 0; i < tokenSequencePerformLexing.getTokenCount(); i++) {
                codeBlockVisitor.visit(tokenSequencePerformLexing.getTokenType(i));
            }
        } else {
            ((TreeElement) aSTNode).acceptTree(codeBlockVisitor);
        }
        return codeBlockVisitor.result;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder
    protected boolean skipChildProcessingWhenBuildingStubs(LighterAST lighterAST, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2) {
        if (lighterAST == null) {
            $$$reportNull$$$0(6);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode2 == null) {
            $$$reportNull$$$0(8);
        }
        return checkByTypes(lighterASTNode.getTokenType(), lighterASTNode2.getTokenType()) || isCodeBlockWithoutStubs(lighterASTNode2);
    }

    public static boolean isCodeBlockWithoutStubs(LighterASTNode lighterASTNode) {
        if (lighterASTNode == null) {
            $$$reportNull$$$0(9);
        }
        if (lighterASTNode.getTokenType() != JavaElementType.CODE_BLOCK || !(lighterASTNode instanceof LighterLazyParseableNode)) {
            return false;
        }
        CodeBlockVisitor codeBlockVisitor = new CodeBlockVisitor();
        ((LighterLazyParseableNode) lighterASTNode).accept(codeBlockVisitor);
        return codeBlockVisitor.result;
    }

    private static boolean checkByTypes(IElementType iElementType, IElementType iElementType2) {
        if (ElementType.IMPORT_STATEMENT_BASE_BIT_SET.contains(iElementType) || iElementType2 == JavaElementType.RECEIVER_PARAMETER) {
            return true;
        }
        if (iElementType2 != JavaElementType.PARAMETER || iElementType == JavaElementType.PARAMETER_LIST) {
            return (iElementType2 == JavaElementType.PARAMETER_LIST && iElementType == JavaElementType.LAMBDA_EXPRESSION) || iElementType2 == JavaDocElementType.DOC_COMMENT;
        }
        return true;
    }

    private static class CodeBlockVisitor extends RecursiveTreeElementWalkingVisitor implements LighterLazyParseableNode.Visitor {
        private static final TokenSet BLOCK_ELEMENTS = TokenSet.create(JavaElementType.CLASS, JavaElementType.ANONYMOUS_CLASS, JavaTokenType.ARROW, JavaTokenType.DOUBLE_COLON, JavaTokenType.AT);
        private IElementType last;
        private IElementType preLast;
        private boolean result;
        private boolean seenLParen;
        private boolean seenModifier;
        private boolean seenNew;

        private CodeBlockVisitor() {
            this.result = true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
        protected void visitNode(TreeElement treeElement) {
            if (BLOCK_ELEMENTS.contains(treeElement.getElementType())) {
                this.result = false;
                stopWalking();
            } else {
                super.visitNode(treeElement);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode.Visitor
        public boolean visit(IElementType iElementType) {
            if (ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(iElementType)) {
                return true;
            }
            if (iElementType == JavaTokenType.AT || iElementType == JavaTokenType.ARROW || iElementType == JavaTokenType.DOUBLE_COLON) {
                this.result = false;
                return false;
            }
            if (iElementType == JavaTokenType.NEW_KEYWORD) {
                this.seenNew = true;
            } else if (this.seenNew && iElementType == JavaTokenType.SEMICOLON) {
                this.seenNew = false;
                this.seenLParen = false;
            } else {
                if (this.seenNew && iElementType == JavaTokenType.LBRACE && this.seenLParen) {
                    this.result = false;
                    return false;
                }
                if (this.seenNew && iElementType == JavaTokenType.LPARENTH) {
                    this.seenLParen = true;
                } else if (ElementType.MODIFIER_BIT_SET.contains(iElementType)) {
                    this.seenModifier = true;
                } else {
                    if ((iElementType == JavaTokenType.CLASS_KEYWORD && (this.last != JavaTokenType.DOT || this.preLast != JavaTokenType.IDENTIFIER || this.seenModifier)) || iElementType == JavaTokenType.ENUM_KEYWORD || iElementType == JavaTokenType.INTERFACE_KEYWORD) {
                        this.result = false;
                        return false;
                    }
                    if (this.preLast == JavaTokenType.IDENTIFIER && this.last == JavaTokenType.IDENTIFIER && (iElementType == JavaTokenType.LPARENTH || iElementType == JavaTokenType.LT)) {
                        this.result = false;
                        return false;
                    }
                }
            }
            this.preLast = this.last;
            this.last = iElementType;
            return true;
        }
    }
}
