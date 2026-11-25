package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ReplaceExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaSourceUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaSourceUtil.class);
    private static final TokenSet REF_FILTER = TokenSet.orSet(ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET, TokenSet.create(JavaElementType.ANNOTATION));

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 6 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 6 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "targetClass";
                break;
            case 2:
                objArr[0] = "ref";
                break;
            case 3:
            case 6:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSourceUtil";
                break;
            case 4:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 5:
                objArr[0] = "node";
                break;
            case 7:
            case 13:
            case 15:
                objArr[0] = "child";
                break;
            case 8:
                objArr[0] = "newChild";
                break;
            case 9:
                objArr[0] = "manager";
                break;
            case 12:
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 16:
                objArr[0] = "listTypes";
                break;
            default:
                objArr[0] = "reference";
                break;
        }
        if (i == 3 || i == 6) {
            objArr[1] = "getReferenceText";
        } else if (i == 10 || i == 11) {
            objArr[1] = "addParenthToReplacedChild";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSourceUtil";
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
                objArr[2] = "getReferenceText";
                break;
            case 3:
            case 6:
            case 10:
            case 11:
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "addParenthToReplacedChild";
                break;
            case 12:
            case 13:
                objArr[2] = "deleteSeparatingComma";
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "addSeparatingComma";
                break;
            default:
                objArr[2] = "fullyQualifyReference";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private JavaSourceUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void fullyQualifyReference(CompositeElement compositeElement, PsiClass psiClass) {
        int iLastIndexOf;
        TreeElement treeElement;
        if (compositeElement == 0) {
            $$$reportNull$$$0(0);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (((SourceJavaCodeReference) compositeElement).isQualified()) {
            PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
            if (psiClassMo6092getContainingClass == null) {
                return;
            }
            ASTNode aSTNodeFindChildByRole = compositeElement.findChildByRole(54);
            if (aSTNodeFindChildByRole instanceof SourceJavaCodeReference) {
                ((SourceJavaCodeReference) aSTNodeFindChildByRole).fullyQualify(psiClassMo6092getContainingClass);
                return;
            }
            return;
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName != null && (iLastIndexOf = qualifiedName.lastIndexOf(46)) > 0) {
            String strSubstring = qualifiedName.substring(0, iLastIndexOf);
            PsiManagerEx manager = compositeElement.getManager();
            PsiJavaParserFacade parserFacade = JavaPsiFacade.getInstance(manager.getProject()).getParserFacade();
            if (compositeElement instanceof PsiReferenceExpression) {
                treeElement = (TreeElement) parserFacade.createExpressionFromText(strSubstring, null).getNode();
            } else {
                treeElement = (TreeElement) parserFacade.createReferenceFromText(strSubstring, null).getNode();
            }
            if (treeElement != null) {
                LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.DOT, ".", 0, 1, SharedImplUtil.findCharTableByTree(treeElement), manager);
                treeElement.rawInsertAfterMe(leafElementCreateSingleLeafElement);
                compositeElement.addInternal(treeElement, leafElementCreateSingleLeafElement, null, Boolean.FALSE);
            }
        }
    }

    public static String getReferenceText(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(2);
        }
        final StringBuilder sb = new StringBuilder();
        ((TreeElement) psiJavaCodeReferenceElement.getNode()).acceptTree(new RecursiveTreeElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitLeaf(LeafElement leafElement) {
                if (JavaSourceUtil.REF_FILTER.contains(leafElement.getElementType())) {
                    return;
                }
                String text = leafElement.getText();
                if (sb.length() > 0 && !text.isEmpty() && Character.isJavaIdentifierPart(text.charAt(0))) {
                    StringBuilder sb2 = sb;
                    char cCharAt = sb2.charAt(sb2.length() - 1);
                    if (cCharAt == '?' || Character.isJavaIdentifierPart(cCharAt)) {
                        sb.append(" ");
                    }
                }
                sb.append(text);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
            public void visitComposite(CompositeElement compositeElement) {
                if (JavaSourceUtil.REF_FILTER.contains(compositeElement.getElementType())) {
                    return;
                }
                super.visitComposite(compositeElement);
            }
        });
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(3);
        }
        return string;
    }

    public static String getReferenceText(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(4);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(5);
        }
        String filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode, REF_FILTER);
        if (filteredString == null) {
            $$$reportNull$$$0(6);
        }
        return filteredString;
    }

    public static TreeElement addParenthToReplacedChild(ASTNode aSTNode, TreeElement treeElement, PsiManager psiManager) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(9);
        }
        if (!ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) || !ElementType.EXPRESSION_BIT_SET.contains(treeElement.getElementType()) || !ReplaceExpressionUtil.isNeedParenthesis(aSTNode, treeElement)) {
            if (treeElement == null) {
                $$$reportNull$$$0(10);
            }
            return treeElement;
        }
        CompositeElement compositeElementComposite = ASTFactory.composite(JavaElementType.PARENTH_EXPRESSION);
        TreeElement treeElement2 = (TreeElement) treeElement.clone();
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(treeElement);
        new DummyHolder(psiManager, compositeElementComposite, null, charTableFindCharTableByTree);
        compositeElementComposite.putUserData(CharTable.CHAR_TABLE_KEY, charTableFindCharTableByTree);
        compositeElementComposite.rawAddChildren(ASTFactory.leaf(JavaTokenType.LPARENTH, "("));
        compositeElementComposite.rawAddChildren(treeElement2);
        compositeElementComposite.rawAddChildren(ASTFactory.leaf(JavaTokenType.RPARENTH, Constant.AFTER_QUTO));
        try {
            compositeElementComposite = (CompositeElement) SourceTreeToPsiMap.psiToTreeNotNull(CodeStyleManager.getInstance(psiManager.getProject()).reformat(SourceTreeToPsiMap.treeToPsiNotNull(compositeElementComposite)));
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
        }
        treeElement.putUserData(CharTable.CHAR_TABLE_KEY, SharedImplUtil.findCharTableByTree(treeElement));
        treeElement2.getTreeParent().replaceChild(treeElement2, treeElement);
        TreeUtil.clearCaches(TreeUtil.getFileElement((TreeElement) compositeElementComposite));
        if (compositeElementComposite == null) {
            $$$reportNull$$$0(11);
        }
        return compositeElementComposite;
    }

    public static void deleteSeparatingComma(CompositeElement compositeElement, ASTNode aSTNode) {
        if (compositeElement == null) {
            $$$reportNull$$$0(12);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(13);
        }
        ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNode.getTreeNext());
        if (aSTNodeSkipWhitespaceAndComments != null && aSTNodeSkipWhitespaceAndComments.getElementType() == JavaTokenType.COMMA) {
            compositeElement.deleteChildInternal(aSTNodeSkipWhitespaceAndComments);
            return;
        }
        ASTNode aSTNodeSkipWhitespaceAndCommentsBack = PsiImplUtil.skipWhitespaceAndCommentsBack(aSTNode.getTreePrev());
        if (aSTNodeSkipWhitespaceAndCommentsBack == null || aSTNodeSkipWhitespaceAndCommentsBack.getElementType() != JavaTokenType.COMMA) {
            return;
        }
        compositeElement.deleteChildInternal(aSTNodeSkipWhitespaceAndCommentsBack);
    }

    public static void addSeparatingComma(CompositeElement compositeElement, ASTNode aSTNode, TokenSet tokenSet) {
        if (compositeElement == null) {
            $$$reportNull$$$0(14);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(15);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(16);
        }
        scanChildren(compositeElement, aSTNode, tokenSet, true);
        scanChildren(compositeElement, aSTNode, tokenSet, false);
    }

    private static void scanChildren(CompositeElement compositeElement, ASTNode aSTNode, TokenSet tokenSet, boolean z) {
        ASTNode treeNext = aSTNode;
        do {
            treeNext = z ? treeNext.getTreeNext() : treeNext.getTreePrev();
            if (treeNext == null || treeNext.getElementType() == JavaTokenType.COMMA) {
                return;
            }
        } while (!tokenSet.contains(treeNext.getElementType()));
        LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.COMMA, ",", 0, 1, SharedImplUtil.findCharTableByTree(compositeElement), compositeElement.getPsi().getManager());
        if (!z) {
            aSTNode = treeNext;
        }
        compositeElement.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, aSTNode, Boolean.FALSE);
    }
}
