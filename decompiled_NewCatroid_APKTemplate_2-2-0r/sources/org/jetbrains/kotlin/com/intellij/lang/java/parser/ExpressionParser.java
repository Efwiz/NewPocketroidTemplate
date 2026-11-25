package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import java.util.function.Function;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ExpressionParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final TokenSet ADDITIVE_OPS;
    private static final TokenSet AND_OPS;
    private static final TokenSet ARGS_LIST_CONTINUE;
    private static final TokenSet ARGS_LIST_END;
    private static final TokenSet CONDITIONAL_AND_OPS;
    private static final TokenSet CONDITIONAL_OR_OPS;
    private static final TokenSet EQUALITY_OPS;
    private static final TokenSet ID_OR_SUPER;
    public static final TokenSet MULTIPLICATIVE_OPS;
    private static final TokenSet OR_OPS;
    private static final TokenSet POSTFIX_OPS;
    private static final TokenSet PREFIX_OPS;
    private static final TokenSet PREF_ARITHMETIC_OPS;
    public static final TokenSet SHIFT_OPS;
    private static final TokenSet THIS_OR_SUPER;
    private static final TokenSet TYPE_START;
    private static final TokenSet XOR_OPS;
    private final JavaParser myParser;
    public static final TokenSet ASSIGNMENT_OPS = TokenSet.create(JavaTokenType.EQ, JavaTokenType.ASTERISKEQ, JavaTokenType.DIVEQ, JavaTokenType.PERCEQ, JavaTokenType.PLUSEQ, JavaTokenType.MINUSEQ, JavaTokenType.LTLTEQ, JavaTokenType.GTGTEQ, JavaTokenType.GTGTGTEQ, JavaTokenType.ANDEQ, JavaTokenType.OREQ, JavaTokenType.XOREQ);
    private static final TokenSet RELATIONAL_OPS = TokenSet.create(JavaTokenType.LT, JavaTokenType.GT, JavaTokenType.LE, JavaTokenType.GE);

    private enum BreakPoint {
        P1,
        P2,
        P4
    }

    private enum ExprType {
        CONDITIONAL_OR,
        CONDITIONAL_AND,
        OR,
        XOR,
        AND,
        EQUALITY,
        RELATIONAL,
        SHIFT,
        ADDITIVE,
        MULTIPLICATIVE,
        UNARY,
        TYPE
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0013  */
    /* JADX WARN: Code duplicated, block: B:27:0x0051  */
    /* JADX WARN: Code duplicated, block: B:6:0x0009  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 4:
                objArr[0] = "builder";
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ExpressionParser";
                break;
            case 5:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 6:
                objArr[0] = "elementParser";
                break;
            case 7:
                objArr[0] = "missingElementKey";
                break;
            default:
                objArr[0] = "javaParser";
                break;
        }
        if (i != 3) {
            switch (i) {
                case 8:
                    objArr[1] = "parseArrayInitializer";
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    objArr[1] = "parseNew";
                    break;
                case 15:
                    objArr[1] = "parseMethodReference";
                    break;
                case 16:
                    objArr[1] = "parseArgumentList";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ExpressionParser";
                    break;
            }
        } else {
            objArr[1] = "parseArrayInitializer";
        }
        switch (i) {
            case 1:
                objArr[2] = "parse";
                break;
            case 2:
                objArr[2] = "parseCaseLabel";
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "parseArrayInitializer";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(JavaTokenType.PLUSPLUS, JavaTokenType.MINUSMINUS);
        POSTFIX_OPS = tokenSetCreate;
        TokenSet tokenSetOrSet = TokenSet.orSet(tokenSetCreate, TokenSet.create(JavaTokenType.PLUS, JavaTokenType.MINUS));
        PREF_ARITHMETIC_OPS = tokenSetOrSet;
        PREFIX_OPS = TokenSet.orSet(tokenSetOrSet, TokenSet.create(JavaTokenType.TILDE, JavaTokenType.EXCL));
        CONDITIONAL_OR_OPS = TokenSet.create(JavaTokenType.OROR);
        CONDITIONAL_AND_OPS = TokenSet.create(JavaTokenType.ANDAND);
        OR_OPS = TokenSet.create(JavaTokenType.OR);
        XOR_OPS = TokenSet.create(JavaTokenType.XOR);
        AND_OPS = TokenSet.create(JavaTokenType.AND);
        EQUALITY_OPS = TokenSet.create(JavaTokenType.EQEQ, JavaTokenType.NE);
        SHIFT_OPS = TokenSet.create(JavaTokenType.LTLT, JavaTokenType.GTGT, JavaTokenType.GTGTGT);
        ADDITIVE_OPS = TokenSet.create(JavaTokenType.PLUS, JavaTokenType.MINUS);
        MULTIPLICATIVE_OPS = TokenSet.create(JavaTokenType.ASTERISK, JavaTokenType.DIV, JavaTokenType.PERC);
        ARGS_LIST_END = TokenSet.create(JavaTokenType.RPARENTH, JavaTokenType.RBRACE, JavaTokenType.RBRACKET);
        ARGS_LIST_CONTINUE = TokenSet.create(JavaTokenType.IDENTIFIER, TokenType.BAD_CHARACTER, JavaTokenType.COMMA, JavaTokenType.INTEGER_LITERAL, JavaTokenType.STRING_LITERAL);
        THIS_OR_SUPER = TokenSet.create(JavaTokenType.THIS_KEYWORD, JavaTokenType.SUPER_KEYWORD);
        ID_OR_SUPER = TokenSet.create(JavaTokenType.IDENTIFIER, JavaTokenType.SUPER_KEYWORD);
        TYPE_START = TokenSet.orSet(ElementType.PRIMITIVE_TYPE_BIT_SET, TokenSet.create(JavaTokenType.IDENTIFIER, JavaTokenType.AT));
    }

    public ExpressionParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public PsiBuilder.Marker parse(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        return parseAssignment(psiBuilder);
    }

    PsiBuilder.Marker parseAssignment(PsiBuilder psiBuilder) {
        return parseAssignment(psiBuilder, 0);
    }

    PsiBuilder.Marker parseAssignment(PsiBuilder psiBuilder, int i) {
        PsiBuilder.Marker conditional = parseConditional(psiBuilder, i);
        if (conditional == null) {
            return null;
        }
        IElementType gtTokenType = getGtTokenType(psiBuilder);
        if (gtTokenType != null && ASSIGNMENT_OPS.contains(gtTokenType)) {
            conditional = conditional.m6464precede();
            advanceGtToken(psiBuilder, gtTokenType);
            if (parse(psiBuilder) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            }
            conditional.done(JavaElementType.ASSIGNMENT_EXPRESSION);
        }
        return conditional;
    }

    PsiBuilder.Marker parseConditionalAnd(PsiBuilder psiBuilder, int i) {
        return parseExpression(psiBuilder, ExprType.CONDITIONAL_AND, i);
    }

    public PsiBuilder.Marker parseConditional(PsiBuilder psiBuilder, int i) {
        PsiBuilder.Marker expression = parseExpression(psiBuilder, ExprType.CONDITIONAL_OR, i);
        if (expression == null) {
            return null;
        }
        if (psiBuilder.getTokenType() != JavaTokenType.QUEST) {
            return expression;
        }
        PsiBuilder.Marker markerM6464precede = expression.m6464precede();
        psiBuilder.advanceLexer();
        if (parse(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            markerM6464precede.done(JavaElementType.CONDITIONAL_EXPRESSION);
            return markerM6464precede;
        }
        if (psiBuilder.getTokenType() != JavaTokenType.COLON) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.colon", new Object[0]));
            markerM6464precede.done(JavaElementType.CONDITIONAL_EXPRESSION);
            return markerM6464precede;
        }
        psiBuilder.advanceLexer();
        if (parseConditional(psiBuilder, i) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            markerM6464precede.done(JavaElementType.CONDITIONAL_EXPRESSION);
            return markerM6464precede;
        }
        markerM6464precede.done(JavaElementType.CONDITIONAL_EXPRESSION);
        return markerM6464precede;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.lang.java.parser.ExpressionParser$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType;

        static {
            int[] iArr = new int[ExprType.values().length];
            $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType = iArr;
            try {
                iArr[ExprType.CONDITIONAL_OR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.CONDITIONAL_AND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.OR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.XOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.AND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.EQUALITY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.RELATIONAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.SHIFT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.ADDITIVE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.MULTIPLICATIVE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.UNARY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[ExprType.TYPE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private PsiBuilder.Marker parseExpression(PsiBuilder psiBuilder, ExprType exprType, int i) {
        switch (AnonymousClass1.$SwitchMap$com$intellij$lang$java$parser$ExpressionParser$ExprType[exprType.ordinal()]) {
            case 1:
                return parseBinary(psiBuilder, ExprType.CONDITIONAL_AND, CONDITIONAL_OR_OPS, i);
            case 2:
                return parseBinary(psiBuilder, ExprType.OR, CONDITIONAL_AND_OPS, i);
            case 3:
                return parseBinary(psiBuilder, ExprType.XOR, OR_OPS, i);
            case 4:
                return parseBinary(psiBuilder, ExprType.AND, XOR_OPS, i);
            case 5:
                return parseBinary(psiBuilder, ExprType.EQUALITY, AND_OPS, i);
            case 6:
                return parseBinary(psiBuilder, ExprType.RELATIONAL, EQUALITY_OPS, i);
            case 7:
                return parseRelational(psiBuilder, i);
            case 8:
                return parseBinary(psiBuilder, ExprType.ADDITIVE, SHIFT_OPS, i);
            case 9:
                return parseBinary(psiBuilder, ExprType.MULTIPLICATIVE, ADDITIVE_OPS, i);
            case 10:
                return parseBinary(psiBuilder, ExprType.UNARY, MULTIPLICATIVE_OPS, i);
            case 11:
                return parseUnary(psiBuilder, i);
            case 12:
                return this.myParser.getReferenceParser().parseType(psiBuilder, 5);
            default:
                return null;
        }
    }

    private PsiBuilder.Marker parseBinary(PsiBuilder psiBuilder, ExprType exprType, TokenSet tokenSet, int i) {
        PsiBuilder.Marker expression = parseExpression(psiBuilder, exprType, i);
        if (expression == null) {
            return null;
        }
        IElementType gtTokenType = getGtTokenType(psiBuilder);
        IElementType iElementType = gtTokenType;
        int i2 = 1;
        while (gtTokenType != null && tokenSet.contains(gtTokenType)) {
            advanceGtToken(psiBuilder, gtTokenType);
            PsiBuilder.Marker expression2 = parseExpression(psiBuilder, exprType, i);
            i2++;
            IElementType gtTokenType2 = getGtTokenType(psiBuilder);
            if (gtTokenType2 == null || !tokenSet.contains(gtTokenType2) || gtTokenType2 != iElementType || expression2 == null) {
                expression = expression.m6464precede();
                if (expression2 == null) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                }
                expression.done(i2 > 2 ? JavaElementType.POLYADIC_EXPRESSION : JavaElementType.BINARY_EXPRESSION);
                if (expression2 == null) {
                    break;
                }
                i2 = 1;
                iElementType = gtTokenType2;
            }
            gtTokenType = gtTokenType2;
        }
        return expression;
    }

    private PsiBuilder.Marker parseRelational(PsiBuilder psiBuilder, int i) {
        IElementType iElementType;
        boolean z;
        PsiBuilder.Marker expression = parseExpression(psiBuilder, ExprType.SHIFT, i);
        if (expression == null) {
            return null;
        }
        while (true) {
            IElementType gtTokenType = getGtTokenType(psiBuilder);
            if (gtTokenType == null) {
                break;
            }
            if (RELATIONAL_OPS.contains(gtTokenType)) {
                iElementType = JavaElementType.BINARY_EXPRESSION;
                z = false;
            } else {
                if (gtTokenType != JavaTokenType.INSTANCEOF_KEYWORD) {
                    break;
                }
                iElementType = JavaElementType.INSTANCE_OF_EXPRESSION;
                z = true;
            }
            expression = expression.m6464precede();
            advanceGtToken(psiBuilder, gtTokenType);
            if (z) {
                if (!this.myParser.getPatternParser().isPattern(psiBuilder)) {
                    if (parseExpression(psiBuilder, ExprType.TYPE, i) == null) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.type", new Object[0]));
                    }
                    expression.done(iElementType);
                    return expression;
                }
                this.myParser.getPatternParser().parsePrimaryPattern(psiBuilder);
            } else if (parseExpression(psiBuilder, ExprType.SHIFT, i) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                expression.done(iElementType);
                return expression;
            }
            expression.done(iElementType);
        }
        return expression;
    }

    private PsiBuilder.Marker parseUnary(PsiBuilder psiBuilder, int i) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (PREFIX_OPS.contains(tokenType)) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            if (parseUnary(psiBuilder, i) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            }
            markerM6463mark.done(JavaElementType.PREFIX_EXPRESSION);
            return markerM6463mark;
        }
        if (tokenType == JavaTokenType.LPARENTH) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            ReferenceParser.TypeInfo typeInfo = this.myParser.getReferenceParser().parseTypeInfo(psiBuilder, 101);
            if (typeInfo == null || !PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                markerM6463mark2.rollbackTo();
                return parsePostfix(psiBuilder, i);
            }
            if (PREF_ARITHMETIC_OPS.contains(psiBuilder.getTokenType()) && !typeInfo.isPrimitive) {
                markerM6463mark2.rollbackTo();
                return parsePostfix(psiBuilder, i);
            }
            if (parseUnary(psiBuilder, i) == null) {
                if (!typeInfo.isParameterized) {
                    markerM6463mark2.rollbackTo();
                    return parsePostfix(psiBuilder, i);
                }
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            }
            markerM6463mark2.done(JavaElementType.TYPE_CAST_EXPRESSION);
            return markerM6463mark2;
        }
        if (tokenType == JavaTokenType.SWITCH_KEYWORD) {
            return this.myParser.getStatementParser().parseExprInParenthWithBlock(psiBuilder, JavaElementType.SWITCH_EXPRESSION, true);
        }
        return parsePostfix(psiBuilder, i);
    }

    private PsiBuilder.Marker parsePostfix(PsiBuilder psiBuilder, int i) {
        PsiBuilder.Marker primary = parsePrimary(psiBuilder, null, -1, i);
        if (primary == null) {
            return null;
        }
        while (POSTFIX_OPS.contains(psiBuilder.getTokenType())) {
            primary = primary.m6464precede();
            psiBuilder.advanceLexer();
            primary.done(JavaElementType.POSTFIX_EXPRESSION);
        }
        return primary;
    }

    private PsiBuilder.Marker parsePrimary(PsiBuilder psiBuilder, BreakPoint breakPoint, int i, int i2) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker primaryExpressionStart = parsePrimaryExpressionStart(psiBuilder, i2);
        if (primaryExpressionStart == null) {
            markerM6463mark.drop();
            return null;
        }
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == JavaTokenType.DOT) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                int currentOffset = psiBuilder.getCurrentOffset();
                psiBuilder.advanceLexer();
                IElementType tokenType2 = psiBuilder.getTokenType();
                if (tokenType2 == JavaTokenType.AT) {
                    this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
                    tokenType2 = psiBuilder.getTokenType();
                }
                IElementType iElementType = tokenType2;
                if (iElementType == JavaTokenType.CLASS_KEYWORD && JavaParserUtil.exprType(primaryExpressionStart) == JavaElementType.REFERENCE_EXPRESSION) {
                    if (breakPoint == BreakPoint.P1 && psiBuilder.getCurrentOffset() == i) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                        PsiBuilderUtil.drop(markerM6463mark, markerM6463mark2);
                        return primaryExpressionStart;
                    }
                    PsiBuilder.Marker markerM6464precede = markerM6463mark.m6464precede();
                    int currentOffset2 = psiBuilder.getCurrentOffset();
                    markerM6463mark.rollbackTo();
                    PsiBuilder.Marker classAccessOrMethodReference = parseClassAccessOrMethodReference(psiBuilder);
                    if (classAccessOrMethodReference == null || psiBuilder.getCurrentOffset() < currentOffset2) {
                        markerM6464precede.rollbackTo();
                        return parsePrimary(psiBuilder, BreakPoint.P1, currentOffset2, i2);
                    }
                    primaryExpressionStart = classAccessOrMethodReference;
                    markerM6463mark = markerM6464precede;
                } else if (iElementType == JavaTokenType.NEW_KEYWORD) {
                    markerM6463mark2.drop();
                    primaryExpressionStart = parseNew(psiBuilder, primaryExpressionStart);
                } else if (iElementType == JavaTokenType.SUPER_KEYWORD && psiBuilder.lookAhead(1) == JavaTokenType.LPARENTH) {
                    markerM6463mark2.drop();
                    primaryExpressionStart = primaryExpressionStart.m6464precede();
                    psiBuilder.m6463mark().done(JavaElementType.REFERENCE_PARAMETER_LIST);
                    psiBuilder.advanceLexer();
                    primaryExpressionStart.done(JavaElementType.REFERENCE_EXPRESSION);
                } else if (THIS_OR_SUPER.contains(iElementType) && JavaParserUtil.exprType(primaryExpressionStart) == JavaElementType.REFERENCE_EXPRESSION) {
                    if (breakPoint == BreakPoint.P2 && psiBuilder.getCurrentOffset() == i) {
                        markerM6463mark2.rollbackTo();
                        markerM6463mark.drop();
                        return primaryExpressionStart;
                    }
                    PsiBuilder.Marker markerM6464precede2 = markerM6463mark.m6464precede();
                    int currentOffset3 = psiBuilder.getCurrentOffset();
                    markerM6463mark.rollbackTo();
                    PsiBuilder.Marker javaCodeReference = this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, false, true, false, false);
                    if (javaCodeReference == null || psiBuilder.getTokenType() != JavaTokenType.DOT || psiBuilder.getCurrentOffset() != currentOffset) {
                        markerM6464precede2.rollbackTo();
                        return parsePrimary(psiBuilder, BreakPoint.P2, currentOffset3, i2);
                    }
                    psiBuilder.advanceLexer();
                    if (psiBuilder.getTokenType() != iElementType) {
                        markerM6464precede2.rollbackTo();
                        return parsePrimary(psiBuilder, BreakPoint.P2, currentOffset3, i2);
                    }
                    psiBuilder.advanceLexer();
                    PsiBuilder.Marker markerM6464precede3 = javaCodeReference.m6464precede();
                    markerM6464precede3.done(iElementType == JavaTokenType.THIS_KEYWORD ? JavaElementType.THIS_EXPRESSION : JavaElementType.SUPER_EXPRESSION);
                    primaryExpressionStart = markerM6464precede3;
                    markerM6463mark = markerM6464precede2;
                } else {
                    primaryExpressionStart = primaryExpressionStart.m6464precede();
                    this.myParser.getReferenceParser().parseReferenceParameterList(psiBuilder, false, false);
                    if (!PsiBuilderUtil.expect(psiBuilder, ID_OR_SUPER)) {
                        markerM6463mark2.rollbackTo();
                        psiBuilder.advanceLexer();
                        this.myParser.getReferenceParser().parseReferenceParameterList(psiBuilder, false, false);
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                        primaryExpressionStart.done(JavaElementType.REFERENCE_EXPRESSION);
                        markerM6463mark.drop();
                        return primaryExpressionStart;
                    }
                    markerM6463mark2.drop();
                    primaryExpressionStart.done(JavaElementType.REFERENCE_EXPRESSION);
                }
            } else if (tokenType == JavaTokenType.LPARENTH) {
                if (JavaParserUtil.exprType(primaryExpressionStart) != JavaElementType.REFERENCE_EXPRESSION) {
                    markerM6463mark.drop();
                    return primaryExpressionStart;
                }
                primaryExpressionStart = primaryExpressionStart.m6464precede();
                parseArgumentList(psiBuilder);
                primaryExpressionStart.done(JavaElementType.METHOD_CALL_EXPRESSION);
            } else if (tokenType == JavaTokenType.LBRACKET) {
                if (breakPoint == BreakPoint.P4) {
                    markerM6463mark.drop();
                    return primaryExpressionStart;
                }
                psiBuilder.advanceLexer();
                if (psiBuilder.getTokenType() == JavaTokenType.RBRACKET && JavaParserUtil.exprType(primaryExpressionStart) == JavaElementType.REFERENCE_EXPRESSION) {
                    int currentOffset4 = psiBuilder.getCurrentOffset();
                    PsiBuilder.Marker markerM6464precede4 = markerM6463mark.m6464precede();
                    markerM6463mark.rollbackTo();
                    PsiBuilder.Marker classAccessOrMethodReference2 = parseClassAccessOrMethodReference(psiBuilder);
                    if (classAccessOrMethodReference2 == null || psiBuilder.getCurrentOffset() <= currentOffset4) {
                        markerM6464precede4.rollbackTo();
                        return parsePrimary(psiBuilder, BreakPoint.P4, -1, i2);
                    }
                    primaryExpressionStart = classAccessOrMethodReference2;
                    markerM6463mark = markerM6464precede4;
                } else {
                    primaryExpressionStart = primaryExpressionStart.m6464precede();
                    if (parse(psiBuilder) == null) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                        primaryExpressionStart.done(JavaElementType.ARRAY_ACCESS_EXPRESSION);
                        markerM6463mark.drop();
                        return primaryExpressionStart;
                    }
                    if (psiBuilder.getTokenType() != JavaTokenType.RBRACKET) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rbracket", new Object[0]));
                        primaryExpressionStart.done(JavaElementType.ARRAY_ACCESS_EXPRESSION);
                        markerM6463mark.drop();
                        return primaryExpressionStart;
                    }
                    psiBuilder.advanceLexer();
                    primaryExpressionStart.done(JavaElementType.ARRAY_ACCESS_EXPRESSION);
                }
            } else {
                if (tokenType == JavaTokenType.DOUBLE_COLON) {
                    return parseMethodReference(psiBuilder, markerM6463mark);
                }
                markerM6463mark.drop();
                return primaryExpressionStart;
            }
        }
    }

    private PsiBuilder.Marker parsePrimaryExpressionStart(PsiBuilder psiBuilder, int i) {
        PsiBuilder.Marker annotations;
        PsiBuilder.Marker markerM6463mark;
        IElementType iElementType;
        PsiBuilder.Marker markerM6463mark2;
        PsiBuilder.Marker classAccessOrMethodReference;
        PsiBuilder.Marker lambdaAfterParenth;
        IElementType tokenType = psiBuilder.getTokenType();
        if (ElementType.ALL_LITERALS.contains(tokenType)) {
            PsiBuilder.Marker markerM6463mark3 = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            markerM6463mark3.done(JavaElementType.LITERAL_EXPRESSION);
            return markerM6463mark3;
        }
        if (tokenType == JavaTokenType.LBRACE) {
            return parseArrayInitializer(psiBuilder);
        }
        if (tokenType == JavaTokenType.NEW_KEYWORD) {
            return parseNew(psiBuilder, null);
        }
        if (tokenType == JavaTokenType.LPARENTH) {
            if (!BitUtil.isSet(i, 1) && (lambdaAfterParenth = parseLambdaAfterParenth(psiBuilder)) != null) {
                return lambdaAfterParenth;
            }
            PsiBuilder.Marker markerM6463mark4 = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            PsiBuilder.Marker marker = parse(psiBuilder);
            if (marker == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            }
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH) && marker != null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
            }
            markerM6463mark4.done(JavaElementType.PARENTH_EXPRESSION);
            return markerM6463mark4;
        }
        if (TYPE_START.contains(tokenType)) {
            PsiBuilder.Marker markerM6463mark5 = psiBuilder.m6463mark();
            ReferenceParser.TypeInfo typeInfo = this.myParser.getReferenceParser().parseTypeInfo(psiBuilder, 0);
            if (typeInfo != null) {
                boolean z = typeInfo.isPrimitive || typeInfo.isArray;
                if ((z || (!typeInfo.hasErrors && typeInfo.isParameterized)) && (classAccessOrMethodReference = parseClassAccessOrMethodReference(psiBuilder, markerM6463mark5, z)) != null) {
                    return classAccessOrMethodReference;
                }
            }
            markerM6463mark5.rollbackTo();
        }
        if (tokenType == JavaTokenType.AT) {
            annotations = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            tokenType = psiBuilder.getTokenType();
        } else {
            annotations = null;
        }
        if (tokenType == JavaTokenType.VAR_KEYWORD) {
            tokenType = JavaTokenType.IDENTIFIER;
            psiBuilder.remapCurrentToken(tokenType);
        }
        if (tokenType == JavaTokenType.IDENTIFIER) {
            if (!BitUtil.isSet(i, 1) && psiBuilder.lookAhead(1) == JavaTokenType.ARROW) {
                return parseLambdaExpression(psiBuilder, false);
            }
            if (annotations != null) {
                PsiBuilder.Marker markerM6464precede = annotations.m6464precede();
                markerM6464precede.doneBefore(JavaElementType.REFERENCE_PARAMETER_LIST, annotations);
                markerM6463mark2 = markerM6464precede.m6464precede();
            } else {
                markerM6463mark2 = psiBuilder.m6463mark();
                psiBuilder.m6463mark().done(JavaElementType.REFERENCE_PARAMETER_LIST);
            }
            psiBuilder.advanceLexer();
            markerM6463mark2.done(JavaElementType.REFERENCE_EXPRESSION);
            return markerM6463mark2;
        }
        if (annotations != null) {
            annotations.rollbackTo();
            tokenType = psiBuilder.getTokenType();
        }
        if (tokenType == JavaTokenType.LT) {
            markerM6463mark = psiBuilder.m6463mark();
            if (!this.myParser.getReferenceParser().parseReferenceParameterList(psiBuilder, false, false)) {
                markerM6463mark.rollbackTo();
                return null;
            }
            tokenType = psiBuilder.getTokenType();
            if (!THIS_OR_SUPER.contains(tokenType)) {
                markerM6463mark.rollbackTo();
                return null;
            }
        } else {
            markerM6463mark = null;
        }
        if (!THIS_OR_SUPER.contains(tokenType)) {
            return null;
        }
        if (markerM6463mark == null) {
            markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.m6463mark().done(JavaElementType.REFERENCE_PARAMETER_LIST);
        }
        psiBuilder.advanceLexer();
        if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
            iElementType = JavaElementType.REFERENCE_EXPRESSION;
        } else if (tokenType == JavaTokenType.THIS_KEYWORD) {
            iElementType = JavaElementType.THIS_EXPRESSION;
        } else {
            iElementType = JavaElementType.SUPER_EXPRESSION;
        }
        markerM6463mark.done(iElementType);
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseArrayInitializer(PsiBuilder psiBuilder) {
        PsiBuilder.Marker arrayInitializer = parseArrayInitializer(psiBuilder, JavaElementType.ARRAY_INITIALIZER_EXPRESSION, new Function() { // from class: org.jetbrains.kotlin.com.intellij.lang.java.parser.ExpressionParser$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.parse((PsiBuilder) obj);
            }
        }, "expected.expression");
        if (arrayInitializer == null) {
            $$$reportNull$$$0(3);
        }
        return arrayInitializer;
    }

    public PsiBuilder.Marker parseArrayInitializer(PsiBuilder psiBuilder, IElementType iElementType, Function<? super PsiBuilder, PsiBuilder.Marker> function, String str) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(4);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(5);
        }
        if (function == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        boolean z = true;
        while (true) {
            if (psiBuilder.getTokenType() == JavaTokenType.RBRACE) {
                psiBuilder.advanceLexer();
                break;
            }
            if (psiBuilder.getTokenType() == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rbrace", new Object[0]));
                break;
            }
            if (function.apply(psiBuilder) == null) {
                if (psiBuilder.getTokenType() == JavaTokenType.COMMA) {
                    if (z && psiBuilder.lookAhead(1) == JavaTokenType.RBRACE) {
                        PsiBuilderUtil.advance(psiBuilder, 2);
                        break;
                    }
                    psiBuilder.error(JavaPsiBundle.message(str, new Object[0]));
                } else if (psiBuilder.getTokenType() != JavaTokenType.RBRACE) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rbrace", new Object[0]));
                    break;
                }
            }
            IElementType tokenType = psiBuilder.getTokenType();
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.COMMA) && tokenType != JavaTokenType.RBRACE) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.comma", new Object[0]));
            }
            z = false;
        }
        markerM6463mark.done(iElementType);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(8);
        }
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseNew(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        PsiBuilder.Marker javaCodeReference;
        PsiBuilder.Marker markerM6464precede = marker != null ? marker.m6464precede() : psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        this.myParser.getReferenceParser().parseReferenceParameterList(psiBuilder, false, true);
        PsiBuilder.Marker annotations = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == JavaTokenType.IDENTIFIER) {
            PsiBuilderUtil.rollbackTo(annotations);
            javaCodeReference = this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, true, true, true);
            if (javaCodeReference == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
                if (markerM6464precede == null) {
                    $$$reportNull$$$0(9);
                }
                return markerM6464precede;
            }
        } else if (ElementType.PRIMITIVE_TYPE_BIT_SET.contains(tokenType)) {
            psiBuilder.advanceLexer();
            javaCodeReference = null;
        } else {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
            if (markerM6464precede == null) {
                $$$reportNull$$$0(10);
            }
            return markerM6464precede;
        }
        if (javaCodeReference != null && psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
            parseArgumentList(psiBuilder);
            if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                PsiBuilder.Marker markerM6464precede2 = javaCodeReference.m6464precede();
                this.myParser.getDeclarationParser().parseClassBodyWithBraces(psiBuilder, false, false);
                markerM6464precede2.done(JavaElementType.ANONYMOUS_CLASS);
            }
            markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
            if (markerM6464precede == null) {
                $$$reportNull$$$0(11);
            }
            return markerM6464precede;
        }
        PsiBuilder.Marker annotations2 = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        if (psiBuilder.getTokenType() != JavaTokenType.LBRACKET) {
            PsiBuilderUtil.rollbackTo(annotations2);
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(javaCodeReference == null ? "expected.lbracket" : "expected.lparen.or.lbracket", new Object[0]));
            markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
            if (markerM6464precede == null) {
                $$$reportNull$$$0(12);
            }
            return markerM6464precede;
        }
        int i = 0;
        int i2 = 0;
        do {
            PsiBuilder.Marker annotations3 = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            if (psiBuilder.getTokenType() != JavaTokenType.LBRACKET) {
                PsiBuilderUtil.rollbackTo(annotations3);
                if (i2 == 0) {
                    if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                        parseArrayInitializer(psiBuilder);
                    } else {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.array.initializer", new Object[0]));
                    }
                }
                markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
                if (markerM6464precede == null) {
                    $$$reportNull$$$0(14);
                }
                return markerM6464precede;
            }
            psiBuilder.advanceLexer();
            if (i == i2 && parse(psiBuilder) != null) {
                i2++;
            }
            i++;
        } while (JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RBRACKET, "expected.rbracket"));
        markerM6464precede.done(JavaElementType.NEW_EXPRESSION);
        if (markerM6464precede == null) {
            $$$reportNull$$$0(13);
        }
        return markerM6464precede;
    }

    private PsiBuilder.Marker parseClassAccessOrMethodReference(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean zContains = ElementType.PRIMITIVE_TYPE_BIT_SET.contains(psiBuilder.getTokenType());
        if (this.myParser.getReferenceParser().parseType(psiBuilder, 0) == null) {
            markerM6463mark.drop();
            return null;
        }
        PsiBuilder.Marker classAccessOrMethodReference = parseClassAccessOrMethodReference(psiBuilder, markerM6463mark, zContains);
        if (classAccessOrMethodReference == null) {
            markerM6463mark.rollbackTo();
        }
        return classAccessOrMethodReference;
    }

    private PsiBuilder.Marker parseClassAccessOrMethodReference(PsiBuilder psiBuilder, PsiBuilder.Marker marker, boolean z) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == JavaTokenType.DOT) {
            return parseClassObjectAccess(psiBuilder, marker, z);
        }
        if (tokenType == JavaTokenType.DOUBLE_COLON) {
            return parseMethodReference(psiBuilder, marker);
        }
        return null;
    }

    private static PsiBuilder.Marker parseClassObjectAccess(PsiBuilder psiBuilder, PsiBuilder.Marker marker, boolean z) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if (psiBuilder.getTokenType() == JavaTokenType.CLASS_KEYWORD) {
            markerM6463mark.drop();
            psiBuilder.advanceLexer();
        } else {
            if (!z) {
                return null;
            }
            markerM6463mark.rollbackTo();
            psiBuilder.error(JavaPsiBundle.message("class.literal.expected", new Object[0]));
        }
        marker.done(JavaElementType.CLASS_OBJECT_ACCESS_EXPRESSION);
        return marker;
    }

    private PsiBuilder.Marker parseMethodReference(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();
        this.myParser.getReferenceParser().parseReferenceParameterList(psiBuilder, false, false);
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER) && !PsiBuilderUtil.expect(psiBuilder, JavaTokenType.NEW_KEYWORD)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
        }
        marker.done(JavaElementType.METHOD_REF_EXPRESSION);
        if (marker == null) {
            $$$reportNull$$$0(15);
        }
        return marker;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0079  */
    private PsiBuilder.Marker parseLambdaAfterParenth(PsiBuilder psiBuilder) {
        IElementType tokenType;
        boolean z = true;
        IElementType iElementTypeLookAhead = psiBuilder.lookAhead(1);
        IElementType iElementTypeLookAhead2 = psiBuilder.lookAhead(2);
        boolean z2 = false;
        if (iElementTypeLookAhead != JavaTokenType.RPARENTH || iElementTypeLookAhead2 != JavaTokenType.ARROW) {
            if (iElementTypeLookAhead == JavaTokenType.AT || ElementType.MODIFIER_BIT_SET.contains(iElementTypeLookAhead) || ElementType.PRIMITIVE_TYPE_BIT_SET.contains(iElementTypeLookAhead)) {
                z2 = true;
            } else if (iElementTypeLookAhead != JavaTokenType.IDENTIFIER) {
                z = false;
            } else if (iElementTypeLookAhead2 != JavaTokenType.COMMA && (iElementTypeLookAhead2 != JavaTokenType.RPARENTH || psiBuilder.lookAhead(3) != JavaTokenType.ARROW)) {
                if (iElementTypeLookAhead2 == JavaTokenType.ARROW) {
                    z = false;
                } else {
                    PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
                    psiBuilder.advanceLexer();
                    if (this.myParser.getReferenceParser().parseTypeInfo(psiBuilder, 7) != null && ((tokenType = psiBuilder.getTokenType()) == JavaTokenType.IDENTIFIER || tokenType == JavaTokenType.THIS_KEYWORD || (tokenType == JavaTokenType.RPARENTH && psiBuilder.lookAhead(1) == JavaTokenType.ARROW))) {
                        z2 = true;
                    }
                    markerM6463mark.rollbackTo();
                    boolean z3 = z2;
                    z2 = true;
                    z = z3;
                }
            }
        }
        if (z) {
            return parseLambdaExpression(psiBuilder, z2);
        }
        return null;
    }

    private PsiBuilder.Marker parseLambdaExpression(PsiBuilder psiBuilder, boolean z) {
        PsiBuilder.Marker codeBlock;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        this.myParser.getDeclarationParser().parseLambdaParameterList(psiBuilder, z);
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.ARROW)) {
            markerM6463mark.rollbackTo();
            return null;
        }
        if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
            codeBlock = this.myParser.getStatementParser().parseCodeBlock(psiBuilder);
        } else {
            codeBlock = parse(psiBuilder);
        }
        if (codeBlock == null) {
            psiBuilder.error(JavaPsiBundle.message("expected.lbrace", new Object[0]));
        }
        markerM6463mark.done(JavaElementType.LAMBDA_EXPRESSION);
        return markerM6463mark;
    }

    public PsiBuilder.Marker parseArgumentList(PsiBuilder psiBuilder) {
        boolean z;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        boolean z2 = true;
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if ((z2 && (ARGS_LIST_END.contains(tokenType) || psiBuilder.eof())) || (!z2 && !ARGS_LIST_CONTINUE.contains(tokenType))) {
                break;
            }
            if (z2) {
                z = false;
            } else if (psiBuilder.getTokenType() == JavaTokenType.COMMA) {
                psiBuilder.advanceLexer();
                z = false;
            } else {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.comma.or.rparen", new Object[0]));
                emptyExpression(psiBuilder);
                z = true;
            }
            if (parse(psiBuilder) == null) {
                if (!z) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                    emptyExpression(psiBuilder);
                }
                if (!ARGS_LIST_CONTINUE.contains(psiBuilder.getTokenType())) {
                    break;
                }
                if (psiBuilder.getTokenType() != JavaTokenType.COMMA && !psiBuilder.eof()) {
                    psiBuilder.advanceLexer();
                }
            }
            z2 = false;
        }
        boolean zExpectOrError = JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RPARENTH, "expected.rparen");
        markerM6463mark.done(JavaElementType.EXPRESSION_LIST);
        if (!zExpectOrError) {
            markerM6463mark.setCustomEdgeTokenBinders(null, WhitespacesBinders.GREEDY_RIGHT_BINDER);
        }
        if (markerM6463mark == null) {
            $$$reportNull$$$0(16);
        }
        return markerM6463mark;
    }

    private static void emptyExpression(PsiBuilder psiBuilder) {
        JavaParserUtil.emptyElement(psiBuilder, JavaElementType.EMPTY_EXPRESSION);
    }

    private static IElementType getGtTokenType(PsiBuilder psiBuilder) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType != JavaTokenType.GT) {
            return tokenType;
        }
        if (psiBuilder.rawLookup(1) != JavaTokenType.GT) {
            return psiBuilder.rawLookup(1) == JavaTokenType.EQ ? JavaTokenType.GE : tokenType;
        }
        if (psiBuilder.rawLookup(2) == JavaTokenType.GT) {
            if (psiBuilder.rawLookup(3) == JavaTokenType.EQ) {
                return JavaTokenType.GTGTGTEQ;
            }
            return JavaTokenType.GTGTGT;
        }
        if (psiBuilder.rawLookup(2) == JavaTokenType.EQ) {
            return JavaTokenType.GTGTEQ;
        }
        return JavaTokenType.GTGT;
    }

    private static void advanceGtToken(PsiBuilder psiBuilder, IElementType iElementType) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (iElementType == JavaTokenType.GTGTGTEQ) {
            PsiBuilderUtil.advance(psiBuilder, 4);
        } else if (iElementType == JavaTokenType.GTGTGT || iElementType == JavaTokenType.GTGTEQ) {
            PsiBuilderUtil.advance(psiBuilder, 3);
        } else if (iElementType == JavaTokenType.GTGT || iElementType == JavaTokenType.GE) {
            PsiBuilderUtil.advance(psiBuilder, 2);
        } else {
            markerM6463mark.drop();
            psiBuilder.advanceLexer();
            return;
        }
        markerM6463mark.collapse(iElementType);
    }
}
