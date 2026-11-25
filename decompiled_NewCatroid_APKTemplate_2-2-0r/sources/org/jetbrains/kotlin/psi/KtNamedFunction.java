package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.typeRefHelpers.TypeRefHelpersKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtNamedFunction extends KtTypeParameterListOwnerStub<KotlinFunctionStub> implements PsiModifiableCodeBlock, KtDeclarationWithInitializer, KtFunction {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtNamedFunction";
        } else {
            objArr[0] = "node";
        }
        if (i == 3) {
            objArr[1] = "getValueParameters";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getContextReceivers";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtNamedFunction";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement psiElement) {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNamedFunction(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNamedFunction(KotlinFunctionStub kotlinFunctionStub) {
        super(kotlinFunctionStub, KtStubElementTypes.FUNCTION);
        if (kotlinFunctionStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitNamedFunction(this, d);
    }

    public boolean hasTypeParameterListBeforeFunctionName() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            return kotlinFunctionStub.hasTypeParameterListBeforeFunctionName();
        }
        return hasTypeParameterListBeforeFunctionNameByTree();
    }

    private boolean hasTypeParameterListBeforeFunctionNameByTree() {
        KtTypeParameterList typeParameterList = mo7395getTypeParameterList();
        if (typeParameterList == null) {
            return false;
        }
        PsiElement nameIdentifier = mo7392getNameIdentifier();
        return nameIdentifier == null || nameIdentifier.getTextOffset() > typeParameterList.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBlockBody() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            return kotlinFunctionStub.hasBlockBody();
        }
        return mo7390getEqualsToken() == null;
    }

    @IfNotParsed
    public PsiElement getFunKeyword() {
        return findChildByType(KtTokens.FUN_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    /* JADX INFO: renamed from: getEqualsToken */
    public PsiElement mo7390getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        return (KtExpression) PsiTreeUtil.getNextSiblingOfType(mo7390getEqualsToken(), KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public KtParameterList getValueParameterList() {
        return (KtParameterList) getStubOrPsiChild(KtStubElementTypes.VALUE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        KtParameterList valueParameterList = getValueParameterList();
        List<KtParameter> parameters = valueParameterList != null ? valueParameterList.getParameters() : Collections.emptyList();
        if (parameters == null) {
            $$$reportNull$$$0(3);
        }
        return parameters;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtExpression getBodyExpression() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub == null || (kotlinFunctionStub.hasBody() && !getContainingKtFile().getIsCompiled())) {
            return (KtExpression) findChildByClass(KtExpression.class);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtBlockExpression getBodyBlockExpression() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null && (!kotlinFunctionStub.hasBlockBody() || !kotlinFunctionStub.hasBody() || getContainingKtFile().getIsCompiled())) {
            return null;
        }
        KtExpression ktExpression = (KtExpression) findChildByClass(KtExpression.class);
        if (ktExpression instanceof KtBlockExpression) {
            return (KtBlockExpression) ktExpression;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasBody() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            return kotlinFunctionStub.hasBody();
        }
        return getBodyExpression() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasDeclaredReturnType() {
        return mo7396getTypeReference() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getReceiverTypeReference */
    public KtTypeReference mo7393getReceiverTypeReference() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            if (!kotlinFunctionStub.isExtension()) {
                return null;
            }
            List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
            if (stubOrPsiChildrenAsList.isEmpty()) {
                return null;
            }
            return (KtTypeReference) stubOrPsiChildrenAsList.get(0);
        }
        return getReceiverTypeRefByTree();
    }

    private KtTypeReference getReceiverTypeRefByTree() {
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            IElementType elementType = firstChild.getNode().getElementType();
            if (elementType == KtTokens.LPAR || elementType == KtTokens.COLON) {
                return null;
            }
            if (firstChild instanceof KtTypeReference) {
                return (KtTypeReference) firstChild;
            }
        }
        return null;
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

    /* JADX WARN: Type inference failed for: r0v3, types: [boolean] */
    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference */
    public KtTypeReference mo7396getTypeReference() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
            ?? IsExtension = kotlinFunctionStub.isExtension();
            if (IsExtension >= stubOrPsiChildrenAsList.size()) {
                return null;
            }
            return (KtTypeReference) stubOrPsiChildrenAsList.get(IsExtension == true ? 1 : 0);
        }
        return TypeRefHelpersKt.getTypeReference(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: setTypeReference */
    public KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference) {
        return TypeRefHelpersKt.setTypeReference(this, getValueParameterList(), ktTypeReference);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public PsiElement getColon() {
        return findChildByType(KtTokens.COLON);
    }

    @Override // org.jetbrains.kotlin.psi.KtFunction
    public boolean isLocal() {
        PsiElement parent = getParent();
        return ((parent instanceof KtFile) || (parent instanceof KtClassBody) || (parent.getParent() instanceof KtScript)) ? false : true;
    }

    public boolean isAnonymous() {
        return getName() == null && isLocal();
    }

    public boolean isTopLevel() {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            return kotlinFunctionStub.isTopLevel();
        }
        return getParent() instanceof KtFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtContractEffectList getContractDescription() {
        return (KtContractEffectList) getStubOrPsiChild(KtStubElementTypes.CONTRACT_EFFECT_LIST);
    }

    public boolean mayHaveContract() {
        return mayHaveContract(true);
    }

    public boolean mayHaveContract(boolean z) {
        KotlinFunctionStub kotlinFunctionStub = (KotlinFunctionStub) getStub();
        if (kotlinFunctionStub != null) {
            return kotlinFunctionStub.mayHaveContract();
        }
        return KtPsiUtilKt.isContractPresentPsiCheck(this, z);
    }
}
