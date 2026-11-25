package org.jetbrains.kotlin.psi;

import kotlin.jvm.functions.Function1;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.ContributedReferenceHost;
import org.jetbrains.kotlin.com.intellij.psi.ElementManipulators;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtStringTemplateExpression extends KtElementImplStub<KotlinPlaceHolderStub<KtStringTemplateExpression>> implements ContributedReferenceHost, PsiLanguageInjectionHost, KtExpression {
    private static final TokenSet CLOSE_QUOTE_TOKEN_SET = TokenSet.create(KtTokens.CLOSING_QUOTE);
    private static final TokenSet STRING_ENTRIES_TYPES = TokenSet.create(KtStubElementTypes.LONG_STRING_TEMPLATE_ENTRY, KtStubElementTypes.SHORT_STRING_TEMPLATE_ENTRY, KtStubElementTypes.LITERAL_STRING_TEMPLATE_ENTRY, KtStubElementTypes.ESCAPE_STRING_TEMPLATE_ENTRY);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "newElement";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtStringTemplateExpression";
        } else if (i != 5) {
            objArr[0] = "node";
        } else {
            objArr[0] = "text";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtStringTemplateExpression";
        } else {
            objArr[1] = "getEntries";
        }
        if (i == 2) {
            objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
        } else if (i == 3) {
            objArr[2] = "accept";
        } else if (i != 4) {
            if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "updateText";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateExpression(KotlinPlaceHolderStub<KtStringTemplateExpression> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.STRING_TEMPLATE);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX INFO: renamed from: lambda$replace$0$org-jetbrains-kotlin-psi-KtStringTemplateExpression, reason: not valid java name */
    /* synthetic */ PsiElement m7400x8bc0831e(PsiElement psiElement) {
        return super.replace(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return KtExpressionImpl.INSTANCE.replaceExpression(this, psiElement, true, new Function1() { // from class: org.jetbrains.kotlin.psi.KtStringTemplateExpression$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7400x8bc0831e((PsiElement) obj);
            }
        });
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitStringTemplateExpression(this, d);
    }

    public KtStringTemplateEntry[] getEntries() {
        KtStringTemplateEntry[] ktStringTemplateEntryArr = (KtStringTemplateEntry[]) getStubOrPsiChildren(STRING_ENTRIES_TYPES, KtStringTemplateEntry.EMPTY_ARRAY);
        if (ktStringTemplateEntryArr == null) {
            $$$reportNull$$$0(4);
        }
        return ktStringTemplateEntryArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public boolean isValidHost() {
        return getNode().getChildren(CLOSE_QUOTE_TOKEN_SET).length != 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public PsiLanguageInjectionHost updateText(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        KtExpression ktExpressionCreateExpressionIfPossible = new KtPsiFactory(getProject()).createExpressionIfPossible(str);
        return ktExpressionCreateExpressionIfPossible instanceof KtStringTemplateExpression ? (KtStringTemplateExpression) replace(ktExpressionCreateExpressionIfPossible) : (PsiLanguageInjectionHost) ElementManipulators.handleContentChange(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new KotlinStringLiteralTextEscaper(this);
    }

    public boolean hasInterpolation() {
        for (PsiElement psiElement : getChildren()) {
            if ((psiElement instanceof KtSimpleNameStringTemplateEntry) || (psiElement instanceof KtBlockStringTemplateEntry)) {
                return true;
            }
        }
        return false;
    }
}
