package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.codeInsight.CodeInsightUtilCore;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.ContributedReferenceHost;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiLiteralStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.injected.StringLiteralEscaper;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;
import org.jetbrains.kotlin.com.intellij.util.text.LiteralFormatUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLiteralExpressionImpl extends JavaStubPsiElement<PsiLiteralStub> implements ContributedReferenceHost, PsiLanguageInjectionHost, PsiLiteralExpression {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet NUMERIC_LITERALS = TokenSet.orSet(ElementType.INTEGER_LITERALS, ElementType.REAL_LITERALS);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "node";
                break;
            case 2:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLiteralExpressionImpl";
                break;
            case 3:
                objArr[0] = "chars";
                break;
            case 4:
                objArr[0] = "outChars";
                break;
            case 5:
                objArr[0] = "visitor";
                break;
            case 7:
                objArr[0] = "text";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLiteralExpressionImpl";
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 2:
            case 6:
                break;
            case 3:
            case 4:
                objArr[2] = "parseStringCharacters";
                break;
            case 5:
                objArr[2] = "accept";
                break;
            case 7:
                objArr[2] = "updateText";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLiteralExpressionImpl(PsiLiteralStub psiLiteralStub) {
        super(psiLiteralStub, JavaStubElementTypes.LITERAL_EXPRESSION);
        if (psiLiteralStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLiteralExpressionImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] childrenAsPsiElements = ((CompositeElement) getNode()).getChildrenAsPsiElements((TokenSet) null, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(2);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        IElementType literalElementType = getLiteralElementType();
        if (literalElementType == JavaTokenType.INTEGER_LITERAL) {
            return PsiType.INT;
        }
        if (literalElementType == JavaTokenType.LONG_LITERAL) {
            return PsiType.LONG;
        }
        if (literalElementType == JavaTokenType.FLOAT_LITERAL) {
            return PsiType.FLOAT;
        }
        if (literalElementType == JavaTokenType.DOUBLE_LITERAL) {
            return PsiType.DOUBLE;
        }
        if (literalElementType == JavaTokenType.CHARACTER_LITERAL) {
            return PsiType.CHAR;
        }
        if (ElementType.STRING_LITERALS.contains(literalElementType)) {
            PsiFile containingFile = getContainingFile();
            return PsiType.getJavaLangString(containingFile.getManager(), ResolveScopeManager.getElementResolveScope(containingFile));
        }
        if (literalElementType == JavaTokenType.TRUE_KEYWORD || literalElementType == JavaTokenType.FALSE_KEYWORD) {
            return PsiType.BOOLEAN;
        }
        if (literalElementType == JavaTokenType.NULL_KEYWORD) {
            return PsiType.NULL;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression
    public boolean isTextBlock() {
        return getLiteralElementType() == JavaTokenType.TEXT_BLOCK_LITERAL;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IElementType getLiteralElementType() {
        PsiLiteralStub psiLiteralStub = (PsiLiteralStub) getGreenStub();
        if (psiLiteralStub != null) {
            return psiLiteralStub.getLiteralType();
        }
        return getNode().getFirstChildNode().getElementType();
    }

    public String getCanonicalText() {
        return NUMERIC_LITERALS.contains(getLiteralElementType()) ? LiteralFormatUtil.removeUnderscores(getText()) : getText();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        PsiLiteralStub psiLiteralStub = (PsiLiteralStub) getGreenStub();
        if (psiLiteralStub != null) {
            return psiLiteralStub.getLiteralText();
        }
        return super.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLiteralValue
    public Object getValue() {
        IElementType literalElementType = getLiteralElementType();
        if (literalElementType == JavaTokenType.TRUE_KEYWORD) {
            return Boolean.TRUE;
        }
        if (literalElementType == JavaTokenType.FALSE_KEYWORD) {
            return Boolean.FALSE;
        }
        if (literalElementType == JavaTokenType.STRING_LITERAL) {
            return internedParseStringCharacters(PsiLiteralUtil.getStringLiteralContent(this));
        }
        if (literalElementType == JavaTokenType.TEXT_BLOCK_LITERAL) {
            return internedParseStringCharacters(PsiLiteralUtil.getTextBlockText(this));
        }
        String lowerCase = NUMERIC_LITERALS.contains(literalElementType) ? StringUtil.toLowerCase(getCanonicalText()) : getCanonicalText();
        int length = lowerCase.length();
        if (literalElementType == JavaTokenType.INTEGER_LITERAL) {
            return PsiLiteralUtil.parseInteger(lowerCase);
        }
        if (literalElementType == JavaTokenType.LONG_LITERAL) {
            return PsiLiteralUtil.parseLong(lowerCase);
        }
        if (literalElementType == JavaTokenType.FLOAT_LITERAL) {
            return PsiLiteralUtil.parseFloat(lowerCase);
        }
        if (literalElementType == JavaTokenType.DOUBLE_LITERAL) {
            return PsiLiteralUtil.parseDouble(lowerCase);
        }
        if (literalElementType != JavaTokenType.CHARACTER_LITERAL || length == 1 || !StringUtil.endsWithChar(lowerCase, '\'')) {
            return null;
        }
        String strSubstring = lowerCase.substring(1, length - 1);
        StringBuilder sb = new StringBuilder();
        if (parseStringCharacters(strSubstring, sb, null) && sb.length() == 1) {
            return Character.valueOf(sb.charAt(0));
        }
        return null;
    }

    @Deprecated
    public String getInnerText() {
        return PsiLiteralUtil.getStringLiteralContent(this);
    }

    private static String internedParseStringCharacters(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (parseStringCharacters(str, sb, null)) {
            return sb.toString();
        }
        return null;
    }

    public static boolean parseStringCharacters(String str, StringBuilder sb, int[] iArr) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (sb == null) {
            $$$reportNull$$$0(4);
        }
        return CodeInsightUtilCore.parseStringCharacters(str, sb, iArr);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitLiteralExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiLiteralExpression:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public boolean isValidHost() {
        return ElementType.TEXT_LITERALS.contains(getLiteralElementType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] contributedReferences;
        IElementType literalElementType = getLiteralElementType();
        if (ElementType.STRING_LITERALS.contains(literalElementType) || literalElementType == JavaTokenType.INTEGER_LITERAL) {
            contributedReferences = PsiReferenceService.getService().getContributedReferences(this);
        } else {
            contributedReferences = PsiReference.EMPTY_ARRAY;
        }
        if (contributedReferences == null) {
            $$$reportNull$$$0(6);
        }
        return contributedReferences;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public PsiLanguageInjectionHost updateText(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        ((LeafElement) getNode().getFirstChildNode()).replaceWithText(str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public LiteralTextEscaper<PsiLiteralExpressionImpl> createLiteralTextEscaper() {
        return new StringLiteralEscaper(this);
    }
}
