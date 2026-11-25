package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.typeRefHelpers.TypeRefHelpersKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtProperty extends KtTypeParameterListOwnerStub<KotlinPropertyStub> implements PsiModifiableCodeBlock, KtVariableDeclaration {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) KtProperty.class);
    private static final TokenSet VAL_VAR_TOKEN_SET = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtProperty";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3) {
            objArr[1] = "getValueParameters";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getContextReceivers";
        } else if (i == 6) {
            objArr[1] = "getAccessors";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtProperty";
        } else {
            objArr[1] = "getValOrVarKeyword";
        }
        switch (i) {
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public KtParameterList getValueParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement psiElement) {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtProperty(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtProperty(KotlinPropertyStub kotlinPropertyStub) {
        super(kotlinPropertyStub, KtStubElementTypes.PROPERTY);
        if (kotlinPropertyStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitProperty(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtVariableDeclaration
    public boolean isVar() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            return kotlinPropertyStub.isVar();
        }
        return getNode().findChildByType(KtTokens.VAR_KEYWORD) != null;
    }

    public boolean isLocal() {
        return (isTopLevel() || isMember()) ? false : true;
    }

    public boolean isMember() {
        PsiElement parent = getParent();
        return (parent instanceof KtClassOrObject) || (parent instanceof KtClassBody) || ((parent instanceof KtBlockExpression) && (parent.getParent() instanceof KtScript));
    }

    public boolean isTopLevel() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            return kotlinPropertyStub.isTopLevel();
        }
        return getParent() instanceof KtFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        List<KtParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getReceiverTypeReference */
    public KtTypeReference mo7393getReceiverTypeReference() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            if (kotlinPropertyStub.isExtension()) {
                return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
            }
            return null;
        }
        return getReceiverTypeRefByTree();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public List<KtContextReceiver> getContextReceivers() {
        KtContextReceiverList ktContextReceiverList = (KtContextReceiverList) getStubOrPsiChild(KtStubElementTypes.CONTEXT_RECEIVER_LIST);
        if (ktContextReceiverList != null) {
            List<KtContextReceiver> listContextReceivers = ktContextReceiverList.contextReceivers();
            if (listContextReceivers == null) {
                $$$reportNull$$$0(4);
            }
            return listContextReceivers;
        }
        List<KtContextReceiver> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(5);
        }
        return listEmptyList;
    }

    private KtTypeReference getReceiverTypeRefByTree() {
        for (ASTNode firstChildNode = getNode().getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            IElementType elementType = firstChildNode.getElementType();
            if (elementType == KtTokens.COLON) {
                return null;
            }
            if (elementType == KtNodeTypes.TYPE_REFERENCE) {
                return (KtTypeReference) firstChildNode.getPsi();
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [boolean] */
    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference */
    public KtTypeReference mo7396getTypeReference() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            if (!kotlinPropertyStub.hasReturnTypeRef()) {
                return null;
            }
            List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
            ?? IsExtension = kotlinPropertyStub.isExtension();
            if (stubOrPsiChildrenAsList.size() <= IsExtension) {
                LOG.error("Invalid stub structure built for property:\n" + getText());
                return null;
            }
            return (KtTypeReference) stubOrPsiChildrenAsList.get(IsExtension == true ? 1 : 0);
        }
        return TypeRefHelpersKt.getTypeReference(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: setTypeReference */
    public KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference) {
        return TypeRefHelpersKt.setTypeReference(this, mo7392getNameIdentifier(), ktTypeReference);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public PsiElement getColon() {
        return findChildByType(KtTokens.COLON);
    }

    public PsiElement getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    public List<KtPropertyAccessor> getAccessors() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.PROPERTY_ACCESSOR);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(6);
        }
        return stubOrPsiChildrenAsList;
    }

    public KtPropertyAccessor getGetter() {
        for (KtPropertyAccessor ktPropertyAccessor : getAccessors()) {
            if (ktPropertyAccessor.isGetter()) {
                return ktPropertyAccessor;
            }
        }
        return null;
    }

    public KtPropertyAccessor getSetter() {
        for (KtPropertyAccessor ktPropertyAccessor : getAccessors()) {
            if (ktPropertyAccessor.isSetter()) {
                return ktPropertyAccessor;
            }
        }
        return null;
    }

    public KtBackingField getFieldDeclaration() {
        Iterator it = getStubOrPsiChildrenAsList(KtStubElementTypes.BACKING_FIELD).iterator();
        if (it.hasNext()) {
            return (KtBackingField) it.next();
        }
        return null;
    }

    public boolean hasDelegate() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            return kotlinPropertyStub.hasDelegate();
        }
        return getDelegate() != null;
    }

    public KtPropertyDelegate getDelegate() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub == null || kotlinPropertyStub.hasDelegate()) {
            return (KtPropertyDelegate) findChildByType(KtNodeTypes.PROPERTY_DELEGATE);
        }
        return null;
    }

    public boolean hasDelegateExpression() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            return kotlinPropertyStub.hasDelegateExpression();
        }
        return getDelegateExpression() != null;
    }

    public KtExpression getDelegateExpression() {
        KtPropertyDelegate delegate;
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if ((kotlinPropertyStub == null || kotlinPropertyStub.hasDelegateExpression()) && (delegate = getDelegate()) != null) {
            return delegate.getExpression();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub != null) {
            return kotlinPropertyStub.hasInitializer();
        }
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        KotlinPropertyStub kotlinPropertyStub = (KotlinPropertyStub) getStub();
        if (kotlinPropertyStub == null || (kotlinPropertyStub.hasInitializer() && !getContainingKtFile().getIsCompiled())) {
            return (KtExpression) PsiTreeUtil.getNextSiblingOfType(findChildByType(KtTokens.EQ), KtExpression.class);
        }
        return null;
    }

    public boolean hasDelegateExpressionOrInitializer() {
        return hasDelegateExpression() || hasInitializer();
    }

    public KtExpression setInitializer(KtExpression ktExpression) {
        PsiElement initializer = getInitializer();
        if (initializer == null) {
            if (ktExpression == null) {
                return null;
            }
            PsiElement psiElementMo7396getTypeReference = mo7396getTypeReference();
            if (psiElementMo7396getTypeReference == null) {
                psiElementMo7396getTypeReference = mo7392getNameIdentifier();
            }
            return (KtExpression) addAfter(ktExpression, addAfter(new KtPsiFactory(getProject()).createEQ(), psiElementMo7396getTypeReference));
        }
        if (ktExpression != null) {
            return (KtExpression) initializer.replace(ktExpression);
        }
        PsiElement nextSibling = initializer.getNextSibling();
        if (nextSibling != null && nextSibling.getNode() != null && nextSibling.getNode().getElementType() == KtTokens.SEMICOLON) {
            initializer = nextSibling;
        }
        deleteChildRange(findChildByType(KtTokens.EQ), initializer);
        return null;
    }

    public KtExpression getDelegateExpressionOrInitializer() {
        KtExpression delegateExpression = getDelegateExpression();
        return delegateExpression == null ? getInitializer() : delegateExpression;
    }

    @Override // org.jetbrains.kotlin.psi.KtValVarKeywordOwner
    public PsiElement getValOrVarKeyword() {
        PsiElement psiElementFindChildByType = findChildByType(VAL_VAR_TOKEN_SET);
        if (psiElementFindChildByType == null) {
            $$$reportNull$$$0(7);
        }
        return psiElementFindChildByType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    public boolean hasBody() {
        if (hasDelegateExpressionOrInitializer()) {
            return true;
        }
        KtPropertyAccessor getter = getGetter();
        if (getter != null && getter.hasBody()) {
            return true;
        }
        KtPropertyAccessor setter = getSetter();
        return setter != null && setter.hasBody();
    }
}
