package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.HashSet;
import java.util.Set;
import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiConstantEvaluationHelperImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLocalVariableImpl extends CompositePsiElement implements PsiLocalVariable, PsiVariableEx {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiLocalVariableImpl.class);
    private volatile String myCachedName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLocalVariableImpl";
                break;
            case 3:
            case 7:
                objArr[0] = "name";
                break;
            case 8:
            case 9:
                objArr[0] = "child";
                break;
            case 10:
                objArr[0] = "visitor";
                break;
            case 11:
                objArr[0] = "processor";
                break;
            case 12:
                objArr[0] = "state";
                break;
            case 13:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1) {
            objArr[1] = "getNameIdentifier";
        } else if (i == 2) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "getType";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getTypeElement";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLocalVariableImpl";
        } else {
            objArr[1] = "getUseScope";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 14:
                break;
            case 3:
                objArr[2] = "setName";
                break;
            case 7:
                objArr[2] = "hasModifierProperty";
                break;
            case 8:
                objArr[2] = "deleteChildInternal";
                break;
            case 9:
                objArr[2] = "getChildRole";
                break;
            case 10:
                objArr[2] = "accept";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 6 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public PsiLocalVariableImpl() {
        this(JavaElementType.LOCAL_VARIABLE);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiLocalVariableImpl(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myCachedName = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public final PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) findChildByRoleAsPsiElement(9);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(1);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public final String getName() {
        String text = this.myCachedName;
        if (text == null) {
            text = mo7392getNameIdentifier().getText();
            this.myCachedName = text;
        }
        if (text == null) {
            $$$reportNull$$$0(2);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        JavaSharedImplUtil.setInitializer(this, psiExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public final PsiType getType() {
        PsiType type = JavaSharedImplUtil.getType(getTypeElement(), mo7392getNameIdentifier());
        if (type == null) {
            $$$reportNull$$$0(4);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) PsiTreeUtil.getChildOfType(this, PsiTypeElement.class);
        if (psiTypeElement != null) {
            if (psiTypeElement == null) {
                $$$reportNull$$$0(5);
            }
            return psiTypeElement;
        }
        PsiTypeElement psiTypeElement2 = (PsiTypeElement) PsiTreeUtil.getChildOfType((PsiLocalVariable) PsiTreeUtil.getChildOfType(getLightParent(), PsiLocalVariable.class), PsiTypeElement.class);
        if (psiTypeElement2 == null) {
            $$$reportNull$$$0(6);
        }
        return psiTypeElement2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        CompositeElement compositeElement;
        CompositeElement treeParent = getTreeParent();
        if (treeParent == null || (compositeElement = (CompositeElement) treeParent.findChildByType(JavaElementType.LOCAL_VARIABLE)) == null) {
            return null;
        }
        return (PsiModifierList) compositeElement.findChildByRoleAsPsiElement(8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return (PsiExpression) findChildByRoleAsPsiElement(21);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return computeConstantValue(new HashSet());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public Object computeConstantValue(Set<PsiVariable> set) {
        PsiExpression initializer;
        if (!hasModifierProperty("final")) {
            return null;
        }
        PsiType type = getType();
        if (((type instanceof PsiPrimitiveType) || type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) && (initializer = getInitializer()) != null) {
            return PsiConstantEvaluationHelperImpl.computeCastTo(initializer, getType(), set);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return mo7392getNameIdentifier().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        CompositeElement treeParent = getTreeParent();
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(treeParent);
        PsiElement[] declaredElements = psiElementTreeElementToPsi instanceof PsiDeclarationStatement ? ((PsiDeclarationStatement) psiElementTreeElementToPsi).getDeclaredElements() : PsiElement.EMPTY_ARRAY;
        if (declaredElements.length > 1) {
            PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
            PsiTypeElement typeElement = getTypeElement();
            int i = 1;
            CompositeElement compositeElement = treeParent;
            while (i < declaredElements.length) {
                ASTNode node = typeElement.copy().getNode();
                ASTNode node2 = psiModifierListMo6095getModifierList.copy().getNode();
                CompositeElement compositeElement2 = (CompositeElement) SourceTreeToPsiMap.psiToTreeNotNull(declaredElements[i]);
                ASTNode aSTNodeSkipWhitespaceAndCommentsBack = PsiImplUtil.skipWhitespaceAndCommentsBack(compositeElement2.getTreePrev());
                if (aSTNodeSkipWhitespaceAndCommentsBack != null && aSTNodeSkipWhitespaceAndCommentsBack.getElementType() == JavaTokenType.COMMA) {
                    PsiElement psi = aSTNodeSkipWhitespaceAndCommentsBack.getPsi();
                    while (true) {
                        PsiElement nextSibling = psi.getNextSibling();
                        if (!(nextSibling instanceof PsiWhiteSpace)) {
                            break;
                        } else {
                            psi = nextSibling;
                        }
                    }
                    CodeEditUtil.removeChildren(treeParent, aSTNodeSkipWhitespaceAndCommentsBack, psi.getNode());
                }
                CodeEditUtil.removeChild(treeParent, compositeElement2);
                CompositeElement compositeElementCreateCompositeElement = Factory.createCompositeElement(JavaElementType.DECLARATION_STATEMENT, SharedImplUtil.findCharTableByTree(treeParent), getManager());
                compositeElementCreateCompositeElement.addChild(compositeElement2, null);
                PsiModifierList psiModifierList = psiModifierListMo6095getModifierList;
                compositeElement2.addChild(Factory.createSingleLeafElement(TokenType.WHITE_SPACE, " ", 0, 1, charTableFindCharTableByTree, getManager()), compositeElement2.getFirstChildNode());
                compositeElement2.addChild(node, compositeElement2.getFirstChildNode());
                if (node2.getTextLength() > 0) {
                    compositeElement2.addChild(Factory.createSingleLeafElement(TokenType.WHITE_SPACE, " ", 0, 1, charTableFindCharTableByTree, getManager()), compositeElement2.getFirstChildNode());
                }
                compositeElement2.addChild(node2, compositeElement2.getFirstChildNode());
                SourceTreeToPsiMap.psiToTreeNotNull(declaredElements[i - 1]).addChild(Factory.createSingleLeafElement(JavaTokenType.SEMICOLON, ";", 0, 1, charTableFindCharTableByTree, getManager()), null);
                CodeEditUtil.addChild(treeParent.getTreeParent(), compositeElementCreateCompositeElement, compositeElement.getTreeNext());
                i++;
                compositeElement = compositeElementCreateCompositeElement;
                psiModifierListMo6095getModifierList = psiModifierList;
            }
        }
        JavaSharedImplUtil.normalizeBrackets(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        ASTNode aSTNodeFindChildByRole;
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (getChildRole(aSTNode) == 21 && (aSTNodeFindChildByRole = findChildByRole(20)) != null) {
            deleteChildInternal(aSTNodeFindChildByRole);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        switch (i) {
            case 8:
                return findChildByType(JavaElementType.MODIFIER_LIST);
            case 9:
                return findChildByType(JavaTokenType.IDENTIFIER);
            case 10:
                return findChildByType(JavaElementType.TYPE);
            default:
                switch (i) {
                    case 20:
                        return findChildByType(JavaTokenType.EQ);
                    case 21:
                        return findChildByType(ElementType.EXPRESSION_BIT_SET);
                    case 22:
                        return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
                    default:
                        return null;
                }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(9);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.MODIFIER_LIST) {
            return 8;
        }
        if (elementType == JavaElementType.TYPE) {
            return getChildRole(aSTNode, 10);
        }
        if (elementType == JavaTokenType.IDENTIFIER) {
            return getChildRole(aSTNode, 9);
        }
        if (elementType == JavaTokenType.EQ) {
            return getChildRole(aSTNode, 20);
        }
        if (elementType == JavaTokenType.SEMICOLON) {
            return getChildRole(aSTNode, 22);
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 21 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitLocalVariable(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(11);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(12);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement == null) {
            return true;
        }
        if (psiElement.getContext() instanceof JavaDummyHolder) {
            return psiScopeProcessor.execute(this, resolveState);
        }
        return (psiElement.getLightParent() == this && getChildRole(SourceTreeToPsiMap.psiElementToTree(psiElement)) == 21 && !psiScopeProcessor.execute(this, resolveState)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiLocalVariable:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        PsiElement parent = getLightParent();
        if (parent instanceof PsiDeclarationStatement) {
            return new LocalSearchScope(parent.getLightParent());
        }
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(14);
        }
        return elementUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.VARIABLE_ICON, ElementPresentationUtil.getFlags(this, false)));
    }
}
