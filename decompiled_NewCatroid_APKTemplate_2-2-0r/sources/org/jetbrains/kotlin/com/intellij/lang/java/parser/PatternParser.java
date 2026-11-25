package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class PatternParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet PATTERN_MODIFIERS = TokenSet.create(JavaTokenType.FINAL_KEYWORD);
    private final JavaParser myParser;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/PatternParser";
        if (i == 2) {
            objArr[1] = "parsePrimaryPattern";
        } else if (i != 3) {
            objArr[1] = "parsePattern";
        } else {
            objArr[1] = "parseTypePattern";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public PatternParser(JavaParser javaParser) {
        this.myParser = javaParser;
    }

    public boolean isPattern(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        while (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
            psiBuilder.advanceLexer();
        }
        this.myParser.getDeclarationParser().parseModifierList(psiBuilder, PATTERN_MODIFIERS);
        boolean z = this.myParser.getReferenceParser().parseType(psiBuilder, 5) != null && psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER;
        markerM6463mark.rollbackTo();
        return z;
    }

    PsiBuilder.Marker parsePattern(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker primaryPattern = parsePrimaryPattern(psiBuilder);
        if (psiBuilder.getTokenType() != JavaTokenType.ANDAND) {
            markerM6463mark.drop();
            if (primaryPattern == null) {
                $$$reportNull$$$0(0);
            }
            return primaryPattern;
        }
        psiBuilder.advanceLexer();
        if (this.myParser.getExpressionParser().parseConditionalAnd(psiBuilder, 1) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.GUARDED_PATTERN);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(1);
        }
        return markerM6463mark;
    }

    PsiBuilder.Marker parsePrimaryPattern(PsiBuilder psiBuilder) {
        if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            parsePattern(psiBuilder);
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.PARENTHESIZED_PATTERN);
            if (markerM6463mark == null) {
                $$$reportNull$$$0(2);
            }
            return markerM6463mark;
        }
        return parseTypePattern(psiBuilder);
    }

    private PsiBuilder.Marker parseTypePattern(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        this.myParser.getDeclarationParser().parseModifierList(psiBuilder, PATTERN_MODIFIERS);
        this.myParser.getReferenceParser().parseType(psiBuilder, 5);
        PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER);
        JavaParserUtil.done(markerM6463mark2, JavaElementType.PATTERN_VARIABLE);
        JavaParserUtil.done(markerM6463mark, JavaElementType.TYPE_TEST_PATTERN);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(3);
        }
        return markerM6463mark;
    }
}
