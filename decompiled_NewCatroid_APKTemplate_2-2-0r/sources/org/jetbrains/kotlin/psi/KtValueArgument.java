package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.KotlinValueArgumentStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtValueArgument extends KtElementImplStub<KotlinValueArgumentStub<? extends KtValueArgument>> implements ValueArgument {
    private static final TokenSet STRING_TEMPLATE_EXPRESSIONS_TYPES = TokenSet.create(KtStubElementTypes.STRING_TEMPLATE);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtValueArgument";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtValueArgument";
        } else {
            objArr[1] = "asElement";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public KtElement asElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public boolean isExternal() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgument(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgument(KotlinValueArgumentStub<KtValueArgument> kotlinValueArgumentStub) {
        super(kotlinValueArgumentStub, KtStubElementTypes.VALUE_ARGUMENT);
        if (kotlinValueArgumentStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    protected KtValueArgument(KotlinValueArgumentStub<? extends KtValueArgument> kotlinValueArgumentStub, IStubElementType iStubElementType) {
        super(kotlinValueArgumentStub, iStubElementType);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitArgument(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    @IfNotParsed
    public KtExpression getArgumentExpression() {
        KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        if (kotlinPlaceHolderStub != null) {
            KtExpression[] ktExpressionArr = (KtExpression[]) kotlinPlaceHolderStub.getChildrenByType(KtStubElementTypes.CONSTANT_EXPRESSIONS_TYPES, KtExpression.EMPTY_ARRAY);
            if (ktExpressionArr.length != 0) {
                return ktExpressionArr[0];
            }
        }
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    public KtStringTemplateExpression getStringTemplateExpression() {
        KtExpression ktExpression;
        KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        if (kotlinPlaceHolderStub != null) {
            KtExpression[] ktExpressionArr = (KtExpression[]) kotlinPlaceHolderStub.getChildrenByType(STRING_TEMPLATE_EXPRESSIONS_TYPES, KtExpression.EMPTY_ARRAY);
            ktExpression = ktExpressionArr.length != 0 ? ktExpressionArr[0] : null;
        } else {
            ktExpression = (KtExpression) findChildByClass(KtExpression.class);
        }
        if (ktExpression instanceof KtStringTemplateExpression) {
            return (KtStringTemplateExpression) ktExpression;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public KtValueArgumentName getArgumentName() {
        return (KtValueArgumentName) getStubOrPsiChild(KtStubElementTypes.VALUE_ARGUMENT_NAME);
    }

    public PsiElement getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public boolean isNamed() {
        return getArgumentName() != null;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public LeafPsiElement getSpreadElement() {
        ASTNode aSTNodeFindChildByType;
        KotlinValueArgumentStub kotlinValueArgumentStub = (KotlinValueArgumentStub) getStub();
        if ((kotlinValueArgumentStub == null || kotlinValueArgumentStub.isSpread()) && (aSTNodeFindChildByType = getNode().findChildByType(KtTokens.MUL)) != null) {
            return (LeafPsiElement) aSTNodeFindChildByType.getPsi();
        }
        return null;
    }

    public boolean isSpread() {
        KotlinValueArgumentStub kotlinValueArgumentStub = (KotlinValueArgumentStub) getStub();
        if (kotlinValueArgumentStub != null) {
            return kotlinValueArgumentStub.isSpread();
        }
        return getSpreadElement() != null;
    }
}
