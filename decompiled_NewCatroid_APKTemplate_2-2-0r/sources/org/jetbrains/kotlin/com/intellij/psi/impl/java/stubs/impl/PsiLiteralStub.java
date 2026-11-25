package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLiteralStub extends StubBase<PsiLiteralExpressionImpl> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String myLiteralText;
    private volatile IElementType myLiteralType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiLiteralStub";
        } else {
            objArr[0] = "literalText";
        }
        if (i == 1) {
            objArr[1] = "getLiteralText";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiLiteralStub";
        } else {
            objArr[1] = "getLiteralType";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLiteralStub(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.LITERAL_EXPRESSION);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myLiteralText = str;
    }

    public String getLiteralText() {
        String str = this.myLiteralText;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    public IElementType getLiteralType() {
        IElementType tokenType = this.myLiteralType;
        if (tokenType == null) {
            JavaLexer javaLexer = new JavaLexer(LanguageLevel.HIGHEST);
            javaLexer.start(this.myLiteralText);
            tokenType = javaLexer.getTokenType();
            this.myLiteralType = tokenType;
        }
        if (tokenType == null) {
            $$$reportNull$$$0(2);
        }
        return tokenType;
    }
}
