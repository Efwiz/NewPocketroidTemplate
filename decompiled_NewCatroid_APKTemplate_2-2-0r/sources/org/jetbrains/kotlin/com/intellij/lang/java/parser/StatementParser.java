package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class StatementParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JavaParser myParser;
    private static final TokenSet YIELD_STMT_INDICATOR_TOKENS = TokenSet.create(JavaTokenType.PLUS, JavaTokenType.MINUS, JavaTokenType.EXCL, JavaTokenType.SUPER_KEYWORD, JavaTokenType.THIS_KEYWORD, JavaTokenType.TRUE_KEYWORD, JavaTokenType.FALSE_KEYWORD, JavaTokenType.NULL_KEYWORD, JavaTokenType.STRING_LITERAL, JavaTokenType.INTEGER_LITERAL, JavaTokenType.DOUBLE_LITERAL, JavaTokenType.FLOAT_LITERAL, JavaTokenType.LONG_LITERAL, JavaTokenType.CHARACTER_LITERAL, JavaTokenType.IDENTIFIER, JavaTokenType.SWITCH_KEYWORD, JavaTokenType.NEW_KEYWORD, JavaTokenType.LPARENTH, JavaTokenType.RBRACE, JavaTokenType.SEMICOLON, JavaTokenType.CASE_KEYWORD);
    private static final TokenSet TRY_CLOSERS_SET = TokenSet.create(JavaTokenType.CATCH_KEYWORD, JavaTokenType.FINALLY_KEYWORD);

    private enum BraceMode {
        TILL_FIRST,
        TILL_LAST
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 32:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 27:
            case 30:
            case 31:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 32:
                i2 = 2;
                break;
            case 27:
            case 30:
            case 31:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 27:
            case 30:
                objArr[0] = "builder";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 32:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/StatementParser";
                break;
            case 31:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            default:
                objArr[0] = "javaParser";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "parseIfStatement";
                break;
            case 8:
                objArr[1] = "parseWhileStatement";
                break;
            case 9:
                objArr[1] = "parseForStatement";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[1] = "parseForLoopFromInitializer";
                break;
            case 14:
                objArr[1] = "parseForEachFromColon";
                break;
            case 15:
                objArr[1] = "parseDoWhileStatement";
                break;
            case 16:
                objArr[1] = "parseSwitchStatement";
                break;
            case 17:
            case 18:
            case 19:
                objArr[1] = "parseCaseLabel";
                break;
            case 20:
                objArr[1] = "parseBreakStatement";
                break;
            case 21:
                objArr[1] = "parseYieldStatement";
                break;
            case 22:
                objArr[1] = "parseContinueStatement";
                break;
            case 23:
                objArr[1] = "parseReturnStatement";
                break;
            case 24:
                objArr[1] = "parseThrowStatement";
                break;
            case 25:
                objArr[1] = "parseSynchronizedStatement";
                break;
            case 26:
                objArr[1] = "parseTryStatement";
                break;
            case 27:
            case 30:
            case 31:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/StatementParser";
                break;
            case 28:
                objArr[1] = "parseAssertStatement";
                break;
            case 29:
                objArr[1] = "parseBlockStatement";
                break;
            case 32:
                objArr[1] = "parseExprInParenthWithBlock";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "parseCodeBlock";
                break;
            case 3:
                objArr[2] = "parseCodeBlockDeep";
                break;
            case 4:
                objArr[2] = "parseStatements";
                break;
            case 5:
                objArr[2] = "parseStatement";
                break;
            case 6:
                objArr[2] = "isStmtYieldToken";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 32:
                break;
            case 27:
                objArr[2] = "parseCatchBlock";
                break;
            case 30:
            case 31:
                objArr[2] = "parseExprInParenthWithBlock";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 32:
                throw new IllegalStateException(str2);
            case 27:
            case 30:
            case 31:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public StatementParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public PsiBuilder.Marker parseCodeBlock(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        return parseCodeBlock(psiBuilder, false);
    }

    public PsiBuilder.Marker parseCodeBlock(PsiBuilder psiBuilder, boolean z) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(2);
        }
        if (psiBuilder.getTokenType() != JavaTokenType.LBRACE) {
            return null;
        }
        return (z && JavaParserUtil.isParseStatementCodeBlocksDeep(psiBuilder)) ? parseCodeBlockDeep(psiBuilder, false) : PsiBuilderUtil.parseBlockLazy(psiBuilder, JavaTokenType.LBRACE, JavaTokenType.RBRACE, JavaElementType.CODE_BLOCK);
    }

    public PsiBuilder.Marker parseCodeBlockDeep(PsiBuilder psiBuilder, boolean z) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(3);
        }
        if (psiBuilder.getTokenType() != JavaTokenType.LBRACE) {
            return null;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        parseStatements(psiBuilder, z ? BraceMode.TILL_LAST : BraceMode.TILL_FIRST);
        boolean zExpectOrError = JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RBRACE, "expected.rbrace");
        psiBuilder.getTokenType();
        JavaParserUtil.done(markerM6463mark, JavaElementType.CODE_BLOCK);
        if (!zExpectOrError) {
            markerM6463mark.setCustomEdgeTokenBinders(null, WhitespacesBinders.GREEDY_RIGHT_BINDER);
        }
        return markerM6463mark;
    }

    public void parseStatements(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(4);
        }
        parseStatements(psiBuilder, null);
    }

    private void parseStatements(PsiBuilder psiBuilder, BraceMode braceMode) {
        while (psiBuilder.getTokenType() != null) {
            if (parseStatement(psiBuilder) == null) {
                IElementType tokenType = psiBuilder.getTokenType();
                if (tokenType == JavaTokenType.RBRACE) {
                    if (braceMode == BraceMode.TILL_FIRST) {
                        return;
                    }
                    if (braceMode == BraceMode.TILL_LAST && psiBuilder.lookAhead(1) == null) {
                        return;
                    }
                }
                PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
                psiBuilder.advanceLexer();
                if (tokenType == JavaTokenType.ELSE_KEYWORD) {
                    markerM6463mark.error(JavaPsiBundle.message("else.without.if", new Object[0]));
                } else if (tokenType == JavaTokenType.CATCH_KEYWORD) {
                    markerM6463mark.error(JavaPsiBundle.message("catch.without.try", new Object[0]));
                } else if (tokenType == JavaTokenType.FINALLY_KEYWORD) {
                    markerM6463mark.error(JavaPsiBundle.message("finally.without.try", new Object[0]));
                } else {
                    markerM6463mark.error(JavaPsiBundle.message("unexpected.token", new Object[0]));
                }
            }
        }
    }

    public PsiBuilder.Marker parseStatement(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(5);
        }
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == JavaTokenType.IF_KEYWORD) {
            return parseIfStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.WHILE_KEYWORD) {
            return parseWhileStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.FOR_KEYWORD) {
            return parseForStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.DO_KEYWORD) {
            return parseDoWhileStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.SWITCH_KEYWORD) {
            return parseSwitchStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.CASE_KEYWORD || tokenType == JavaTokenType.DEFAULT_KEYWORD) {
            return parseSwitchLabelStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.BREAK_KEYWORD) {
            return parseBreakStatement(psiBuilder);
        }
        if (isStmtYieldToken(psiBuilder, tokenType)) {
            return parseYieldStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.CONTINUE_KEYWORD) {
            return parseContinueStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.RETURN_KEYWORD) {
            return parseReturnStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.THROW_KEYWORD) {
            return parseThrowStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.SYNCHRONIZED_KEYWORD) {
            return parseSynchronizedStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.TRY_KEYWORD) {
            return parseTryStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.ASSERT_KEYWORD) {
            return parseAssertStatement(psiBuilder);
        }
        if (tokenType == JavaTokenType.LBRACE) {
            return parseBlockStatement(psiBuilder);
        }
        if (tokenType instanceof ILazyParseableElementType) {
            psiBuilder.advanceLexer();
            return null;
        }
        if (tokenType == JavaTokenType.SEMICOLON) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            JavaParserUtil.done(markerM6463mark, JavaElementType.EMPTY_STATEMENT);
            return markerM6463mark;
        }
        if (tokenType == JavaTokenType.IDENTIFIER || tokenType == JavaTokenType.AT) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            skipQualifiedName(psiBuilder);
            IElementType tokenType2 = psiBuilder.getTokenType();
            IElementType iElementTypeLookAhead = psiBuilder.lookAhead(1);
            markerM6463mark2.rollbackTo();
            if (tokenType2 == JavaTokenType.LT || (tokenType2 == JavaTokenType.DOT && iElementTypeLookAhead == JavaTokenType.AT)) {
                PsiBuilder.Marker markerM6463mark3 = psiBuilder.m6463mark();
                if (this.myParser.getDeclarationParser().parse(psiBuilder, DeclarationParser.Context.CODE_BLOCK) != null) {
                    JavaParserUtil.done(markerM6463mark3, JavaElementType.DECLARATION_STATEMENT);
                    return markerM6463mark3;
                }
                PsiBuilder.Marker type = this.myParser.getReferenceParser().parseType(psiBuilder, 0);
                if (type == null || psiBuilder.getTokenType() != JavaTokenType.DOUBLE_COLON) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                    if (type == null) {
                        psiBuilder.advanceLexer();
                    }
                    JavaParserUtil.done(markerM6463mark3, JavaElementType.DECLARATION_STATEMENT);
                    return markerM6463mark3;
                }
                markerM6463mark3.rollbackTo();
            }
        }
        PsiBuilder.Marker markerM6463mark4 = psiBuilder.m6463mark();
        PsiBuilder.Marker marker = this.myParser.getExpressionParser().parse(psiBuilder);
        if (marker != null) {
            PsiBuilder.Marker markerM6464precede = marker.m6464precede();
            PsiBuilder.Marker markerM6464precede2 = markerM6464precede.m6464precede();
            int i = 1;
            while (psiBuilder.getTokenType() == JavaTokenType.COMMA) {
                PsiBuilder.Marker markerM6463mark5 = psiBuilder.m6463mark();
                psiBuilder.advanceLexer();
                if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
                    markerM6463mark5.rollbackTo();
                    break;
                }
                markerM6463mark5.drop();
                i++;
            }
            if (i > 1) {
                markerM6463mark4.drop();
                JavaParserUtil.done(markerM6464precede, JavaElementType.EXPRESSION_LIST);
                JavaParserUtil.semicolon(psiBuilder);
                JavaParserUtil.done(markerM6464precede2, JavaElementType.EXPRESSION_LIST_STATEMENT);
                return markerM6464precede2;
            }
            if (JavaParserUtil.exprType(marker) != JavaElementType.REFERENCE_EXPRESSION) {
                PsiBuilderUtil.drop(markerM6464precede, markerM6463mark4);
                JavaParserUtil.semicolon(psiBuilder);
                JavaParserUtil.done(markerM6464precede2, JavaElementType.EXPRESSION_STATEMENT);
                return markerM6464precede2;
            }
            markerM6463mark4.rollbackTo();
        } else {
            markerM6463mark4.drop();
        }
        PsiBuilder.Marker marker2 = this.myParser.getDeclarationParser().parse(psiBuilder, DeclarationParser.Context.CODE_BLOCK);
        if (marker2 != null) {
            PsiBuilder.Marker markerM6464precede3 = marker2.m6464precede();
            JavaParserUtil.done(markerM6464precede3, JavaElementType.DECLARATION_STATEMENT);
            return markerM6464precede3;
        }
        if (psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER && psiBuilder.lookAhead(1) == JavaTokenType.COLON) {
            PsiBuilder.Marker markerM6463mark6 = psiBuilder.m6463mark();
            PsiBuilderUtil.advance(psiBuilder, 2);
            parseStatement(psiBuilder);
            JavaParserUtil.done(markerM6463mark6, JavaElementType.LABELED_STATEMENT);
            return markerM6463mark6;
        }
        if (marker == null) {
            return null;
        }
        PsiBuilder.Marker markerM6463mark7 = psiBuilder.m6463mark();
        this.myParser.getExpressionParser().parse(psiBuilder);
        JavaParserUtil.semicolon(psiBuilder);
        JavaParserUtil.done(markerM6463mark7, JavaElementType.EXPRESSION_STATEMENT);
        return markerM6463mark7;
    }

    private static boolean isStmtYieldToken(PsiBuilder psiBuilder, IElementType iElementType) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(6);
        }
        if (iElementType != JavaTokenType.IDENTIFIER || !PsiKeyword.YIELD.equals(psiBuilder.getTokenText()) || !JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_14)) {
            return false;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == null || YIELD_STMT_INDICATOR_TOKENS.contains(tokenType)) {
            markerM6463mark.rollbackTo();
            return true;
        }
        if (JavaTokenType.PLUSPLUS.equals(tokenType) || JavaTokenType.MINUSMINUS.equals(tokenType)) {
            psiBuilder.advanceLexer();
            boolean z = !psiBuilder.getTokenType().equals(JavaTokenType.SEMICOLON);
            markerM6463mark.rollbackTo();
            return z;
        }
        markerM6463mark.rollbackTo();
        return false;
    }

    private static void skipQualifiedName(PsiBuilder psiBuilder) {
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            while (psiBuilder.getTokenType() == JavaTokenType.DOT && psiBuilder.lookAhead(1) == JavaTokenType.IDENTIFIER) {
                PsiBuilderUtil.advance(psiBuilder, 2);
            }
        }
    }

    private PsiBuilder.Marker parseIfStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (parseExprInParenth(psiBuilder)) {
            if (parseStatement(psiBuilder) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
            } else if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.ELSE_KEYWORD) && parseStatement(psiBuilder) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
            }
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.IF_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(7);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseWhileStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker exprInParenthWithBlock = parseExprInParenthWithBlock(psiBuilder, JavaElementType.WHILE_STATEMENT, false);
        if (exprInParenthWithBlock == null) {
            $$$reportNull$$$0(8);
        }
        return exprInParenthWithBlock;
    }

    private PsiBuilder.Marker parseForStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LPARENTH)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lparen", new Object[0]));
            JavaParserUtil.done(markerM6463mark, JavaElementType.FOR_STATEMENT);
            if (markerM6463mark == null) {
                $$$reportNull$$$0(9);
            }
            return markerM6463mark;
        }
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        PsiBuilder.Marker parameter = this.myParser.getDeclarationParser().parseParameter(psiBuilder, false, false, true);
        if (parameter == null || JavaParserUtil.exprType(parameter) != JavaElementType.PARAMETER || psiBuilder.getTokenType() != JavaTokenType.COLON) {
            markerM6463mark2.rollbackTo();
            return parseForLoopFromInitializer(psiBuilder, markerM6463mark);
        }
        markerM6463mark2.drop();
        return parseForEachFromColon(psiBuilder, markerM6463mark);
    }

    private PsiBuilder.Marker parseForLoopFromInitializer(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        if (parseStatement(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                JavaParserUtil.done(marker, JavaElementType.FOR_STATEMENT);
                if (marker == null) {
                    $$$reportNull$$$0(10);
                }
                return marker;
            }
        } else {
            boolean z = (getLastToken(psiBuilder) != JavaTokenType.SEMICOLON ? !JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.SEMICOLON, "expected.semicolon") : false) & (this.myParser.getExpressionParser().parse(psiBuilder) == null);
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON)) {
                if (!z) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.semicolon", new Object[0]));
                }
                if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                    JavaParserUtil.done(marker, JavaElementType.FOR_STATEMENT);
                    if (marker == null) {
                        $$$reportNull$$$0(11);
                    }
                    return marker;
                }
            } else {
                parseForUpdateExpressions(psiBuilder);
                if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
                    JavaParserUtil.done(marker, JavaElementType.FOR_STATEMENT);
                    if (marker == null) {
                        $$$reportNull$$$0(12);
                    }
                    return marker;
                }
            }
        }
        if (parseStatement(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
        }
        JavaParserUtil.done(marker, JavaElementType.FOR_STATEMENT);
        if (marker == null) {
            $$$reportNull$$$0(13);
        }
        return marker;
    }

    private static IElementType getLastToken(PsiBuilder psiBuilder) {
        int i = -1;
        while (true) {
            TokenSet tokenSet = ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET;
            IElementType iElementTypeRawLookup = psiBuilder.rawLookup(i);
            if (!tokenSet.contains(iElementTypeRawLookup)) {
                return iElementTypeRawLookup;
            }
            i--;
        }
    }

    private void parseForUpdateExpressions(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6464precede;
        PsiBuilder.Marker marker = this.myParser.getExpressionParser().parse(psiBuilder);
        if (marker == null) {
            return;
        }
        if (psiBuilder.getTokenType() != JavaTokenType.COMMA) {
            markerM6464precede = marker.m6464precede();
            JavaParserUtil.done(markerM6464precede, JavaElementType.EXPRESSION_STATEMENT);
        } else {
            PsiBuilder.Marker markerM6464precede2 = marker.m6464precede();
            PsiBuilder.Marker markerM6464precede3 = markerM6464precede2.m6464precede();
            do {
                psiBuilder.advanceLexer();
                if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                }
            } while (psiBuilder.getTokenType() == JavaTokenType.COMMA);
            JavaParserUtil.done(markerM6464precede2, JavaElementType.EXPRESSION_LIST);
            JavaParserUtil.done(markerM6464precede3, JavaElementType.EXPRESSION_LIST_STATEMENT);
            markerM6464precede = markerM6464precede3;
        }
        markerM6464precede.setCustomEdgeTokenBinders(null, WhitespacesBinders.DEFAULT_RIGHT_BINDER);
    }

    private PsiBuilder.Marker parseForEachFromColon(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();
        if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
        }
        if (JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RPARENTH, "expected.rparen") && parseStatement(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
        }
        JavaParserUtil.done(marker, JavaElementType.FOREACH_STATEMENT);
        if (marker == null) {
            $$$reportNull$$$0(14);
        }
        return marker;
    }

    private PsiBuilder.Marker parseDoWhileStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (parseStatement(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.statement", new Object[0]));
        } else if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.WHILE_KEYWORD)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.while", new Object[0]));
        } else if (parseExprInParenth(psiBuilder)) {
            JavaParserUtil.semicolon(psiBuilder);
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.DO_WHILE_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(15);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseSwitchStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker exprInParenthWithBlock = parseExprInParenthWithBlock(psiBuilder, JavaElementType.SWITCH_STATEMENT, true);
        if (exprInParenthWithBlock == null) {
            $$$reportNull$$$0(16);
        }
        return exprInParenthWithBlock;
    }

    Pair<PsiBuilder.Marker, Boolean> parseCaseLabel(PsiBuilder psiBuilder) {
        if (psiBuilder.getTokenType() == JavaTokenType.DEFAULT_KEYWORD) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            JavaParserUtil.done(markerM6463mark, JavaElementType.DEFAULT_CASE_LABEL_ELEMENT);
            Pair<PsiBuilder.Marker, Boolean> pairCreate = Pair.create(markerM6463mark, false);
            if (pairCreate == null) {
                $$$reportNull$$$0(17);
            }
            return pairCreate;
        }
        if (this.myParser.getPatternParser().isPattern(psiBuilder)) {
            Pair<PsiBuilder.Marker, Boolean> pairCreate2 = Pair.create(this.myParser.getPatternParser().parsePattern(psiBuilder), false);
            if (pairCreate2 == null) {
                $$$reportNull$$$0(18);
            }
            return pairCreate2;
        }
        Pair<PsiBuilder.Marker, Boolean> pairCreate3 = Pair.create(this.myParser.getExpressionParser().parseAssignment(psiBuilder, BitUtil.set(0, 1, true)), true);
        if (pairCreate3 == null) {
            $$$reportNull$$$0(19);
        }
        return pairCreate3;
    }

    private PsiBuilder.Marker parseSwitchLabelStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean z = psiBuilder.getTokenType() == JavaTokenType.CASE_KEYWORD;
        psiBuilder.advanceLexer();
        if (z) {
            boolean zIsAtLeast = JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_17);
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            do {
                if (parseCaseLabel(psiBuilder).first == null) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(zIsAtLeast ? "expected.case.label.element" : "expected.expression", new Object[0]));
                }
            } while (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.COMMA));
            JavaParserUtil.done(markerM6463mark2, JavaElementType.CASE_LABEL_ELEMENT_LIST);
        }
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.ARROW)) {
            if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                PsiBuilder.Marker markerM6463mark3 = psiBuilder.m6463mark();
                parseCodeBlock(psiBuilder, true);
                markerM6463mark3.done(JavaElementType.BLOCK_STATEMENT);
                if (psiBuilder.getTokenType() == JavaTokenType.SEMICOLON) {
                    PsiBuilder.Marker markerM6463mark4 = psiBuilder.m6463mark();
                    while (psiBuilder.getTokenType() == JavaTokenType.SEMICOLON) {
                        psiBuilder.advanceLexer();
                    }
                    markerM6463mark4.error(JavaPsiBundle.message("expected.switch.label", new Object[0]));
                }
            } else if (psiBuilder.getTokenType() == JavaTokenType.THROW_KEYWORD) {
                parseThrowStatement(psiBuilder);
            } else {
                PsiBuilder.Marker marker = this.myParser.getExpressionParser().parse(psiBuilder);
                if (marker != null) {
                    PsiBuilder.Marker markerM6464precede = marker.m6464precede();
                    JavaParserUtil.semicolon(psiBuilder);
                    markerM6464precede.done(JavaElementType.EXPRESSION_STATEMENT);
                } else {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.switch.rule", new Object[0]));
                    PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON);
                }
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.SWITCH_LABELED_RULE);
        } else {
            JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.COLON, "expected.colon");
            JavaParserUtil.done(markerM6463mark, JavaElementType.SWITCH_LABEL_STATEMENT);
        }
        return markerM6463mark;
    }

    private static PsiBuilder.Marker parseBreakStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER);
        JavaParserUtil.semicolon(psiBuilder);
        JavaParserUtil.done(markerM6463mark, JavaElementType.BREAK_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(20);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseYieldStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.remapCurrentToken(JavaTokenType.YIELD_KEYWORD);
        psiBuilder.advanceLexer();
        if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
        } else {
            JavaParserUtil.semicolon(psiBuilder);
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.YIELD_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(21);
        }
        return markerM6463mark;
    }

    private static PsiBuilder.Marker parseContinueStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER);
        JavaParserUtil.semicolon(psiBuilder);
        JavaParserUtil.done(markerM6463mark, JavaElementType.CONTINUE_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(22);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseReturnStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        this.myParser.getExpressionParser().parse(psiBuilder);
        JavaParserUtil.semicolon(psiBuilder);
        JavaParserUtil.done(markerM6463mark, JavaElementType.RETURN_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(23);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseThrowStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
        } else {
            JavaParserUtil.semicolon(psiBuilder);
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.THROW_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(24);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseSynchronizedStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker exprInParenthWithBlock = parseExprInParenthWithBlock(psiBuilder, JavaElementType.SYNCHRONIZED_STATEMENT, true);
        if (exprInParenthWithBlock == null) {
            $$$reportNull$$$0(25);
        }
        return exprInParenthWithBlock;
    }

    private PsiBuilder.Marker parseTryStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        boolean z = psiBuilder.getTokenType() == JavaTokenType.LPARENTH;
        if (z) {
            this.myParser.getDeclarationParser().parseResourceList(psiBuilder);
        }
        if (parseCodeBlock(psiBuilder, true) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lbrace", new Object[0]));
        } else if (!z && !TRY_CLOSERS_SET.contains(psiBuilder.getTokenType())) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.catch.or.finally", new Object[0]));
        } else {
            while (psiBuilder.getTokenType() == JavaTokenType.CATCH_KEYWORD && parseCatchBlock(psiBuilder)) {
            }
            if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.FINALLY_KEYWORD) && parseCodeBlock(psiBuilder, true) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lbrace", new Object[0]));
            }
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.TRY_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(26);
        }
        return markerM6463mark;
    }

    public boolean parseCatchBlock(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(27);
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LPARENTH)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lparen", new Object[0]));
            JavaParserUtil.done(markerM6463mark, JavaElementType.CATCH_SECTION);
            return false;
        }
        if (this.myParser.getDeclarationParser().parseParameter(psiBuilder, false, true, false) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.parameter", new Object[0]));
        }
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
            JavaParserUtil.done(markerM6463mark, JavaElementType.CATCH_SECTION);
            return false;
        }
        if (parseCodeBlock(psiBuilder, true) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lbrace", new Object[0]));
            JavaParserUtil.done(markerM6463mark, JavaElementType.CATCH_SECTION);
            return false;
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.CATCH_SECTION);
        return true;
    }

    private PsiBuilder.Marker parseAssertStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.boolean.expression", new Object[0]));
        } else if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.COLON) && this.myParser.getExpressionParser().parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
        } else {
            JavaParserUtil.semicolon(psiBuilder);
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.ASSERT_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(28);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseBlockStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        parseCodeBlock(psiBuilder, true);
        JavaParserUtil.done(markerM6463mark, JavaElementType.BLOCK_STATEMENT);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(29);
        }
        return markerM6463mark;
    }

    public PsiBuilder.Marker parseExprInParenthWithBlock(PsiBuilder psiBuilder, IElementType iElementType, boolean z) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(30);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(31);
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (parseExprInParenth(psiBuilder)) {
            if ((z ? parseCodeBlock(psiBuilder, true) : parseStatement(psiBuilder)) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(z ? "expected.lbrace" : "expected.statement", new Object[0]));
            }
        }
        JavaParserUtil.done(markerM6463mark, iElementType);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(32);
        }
        return markerM6463mark;
    }

    private boolean parseExprInParenth(PsiBuilder psiBuilder) {
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LPARENTH)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lparen", new Object[0]));
            return false;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (this.myParser.getExpressionParser().parse(psiBuilder) == null || psiBuilder.getTokenType() == JavaTokenType.SEMICOLON) {
            markerM6463mark.rollbackTo();
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            if (psiBuilder.getTokenType() != JavaTokenType.RPARENTH) {
                return false;
            }
        } else {
            markerM6463mark.drop();
            if (psiBuilder.getTokenType() != JavaTokenType.RPARENTH) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
                return false;
            }
        }
        psiBuilder.advanceLexer();
        return true;
    }
}
