package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtAnnotationEntry extends KtElementImplStub<KotlinAnnotationEntryStub> implements KtCallElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;

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
                objArr[0] = "org/jetbrains/kotlin/psi/KtAnnotationEntry";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getValueArguments";
        } else if (i == 5) {
            objArr[1] = "getLambdaArguments";
        } else if (i == 6 || i == 7) {
            objArr[1] = "getTypeArguments";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtAnnotationEntry";
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotationEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotationEntry(KotlinAnnotationEntryStub kotlinAnnotationEntryStub) {
        super(kotlinAnnotationEntryStub, KtStubElementTypes.ANNOTATION_ENTRY);
        if (kotlinAnnotationEntryStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitAnnotationEntry(this, d);
    }

    @IfNotParsed
    public KtTypeReference getTypeReference() {
        KtConstructorCalleeExpression calleeExpression = getCalleeExpression();
        if (calleeExpression == null) {
            return null;
        }
        return calleeExpression.getTypeReference();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtConstructorCalleeExpression getCalleeExpression() {
        return (KtConstructorCalleeExpression) getStubOrPsiChild(KtStubElementTypes.CONSTRUCTOR_CALLEE);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtValueArgumentList getValueArgumentList() {
        if (((KotlinAnnotationEntryStub) getStub()) == null && getGreenStub() != 0) {
            return (KtValueArgumentList) findChildByType(KtNodeTypes.VALUE_ARGUMENT_LIST);
        }
        return (KtValueArgumentList) getStubOrPsiChild(KtStubElementTypes.VALUE_ARGUMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<? extends ValueArgument> getValueArguments() {
        KotlinAnnotationEntryStub kotlinAnnotationEntryStub = (KotlinAnnotationEntryStub) getStub();
        if (kotlinAnnotationEntryStub != null && !kotlinAnnotationEntryStub.hasValueArguments()) {
            List<? extends ValueArgument> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        KtValueArgumentList valueArgumentList = getValueArgumentList();
        List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : Collections.emptyList();
        if (arguments == null) {
            $$$reportNull$$$0(4);
        }
        return arguments;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtLambdaArgument> getLambdaArguments() {
        List<KtLambdaArgument> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(5);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtTypeProjection> getTypeArguments() {
        KtTypeArgumentList typeArgumentList = getTypeArgumentList();
        if (typeArgumentList == null) {
            List<KtTypeProjection> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(6);
            }
            return listEmptyList;
        }
        List<KtTypeProjection> arguments = typeArgumentList.getArguments();
        if (arguments == null) {
            $$$reportNull$$$0(7);
        }
        return arguments;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtTypeArgumentList getTypeArgumentList() {
        KtTypeReference typeReference = getTypeReference();
        if (typeReference == null) {
            return null;
        }
        KtTypeElement typeElement = typeReference.getTypeElement();
        if (typeElement instanceof KtUserType) {
            return ((KtUserType) typeElement).getTypeArgumentList();
        }
        return null;
    }

    public PsiElement getAtSymbol() {
        return findChildByType(KtTokens.AT);
    }

    public KtAnnotationUseSiteTarget getUseSiteTarget() {
        KtAnnotationUseSiteTarget ktAnnotationUseSiteTarget = (KtAnnotationUseSiteTarget) getStubOrPsiChild(KtStubElementTypes.ANNOTATION_TARGET);
        if (ktAnnotationUseSiteTarget != null) {
            return ktAnnotationUseSiteTarget;
        }
        PsiElement parentByStub = getParentByStub();
        return parentByStub instanceof KtAnnotation ? ((KtAnnotation) parentByStub).getUseSiteTarget() : ktAnnotationUseSiteTarget;
    }

    public Name getShortName() {
        String referencedName;
        KotlinAnnotationEntryStub kotlinAnnotationEntryStub = (KotlinAnnotationEntryStub) getStub();
        if (kotlinAnnotationEntryStub != null) {
            String shortName = kotlinAnnotationEntryStub.getShortName();
            if (shortName != null) {
                return Name.identifier(shortName);
            }
            return null;
        }
        KtTypeElement typeElement = getTypeReference().getTypeElement();
        if (!(typeElement instanceof KtUserType) || (referencedName = ((KtUserType) typeElement).getReferencedName()) == null) {
            return null;
        }
        return Name.identifier(referencedName);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }
}
