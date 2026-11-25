package org.jetbrains.kotlin.parsing;

import java.util.Arrays;
import java.util.HashSet;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinExpressionParsing extends AbstractKotlinParsing {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet ALLOW_NEWLINE_OPERATIONS;
    public static final TokenSet ALL_OPERATIONS;
    private static final TokenSet ARROW_COMMA_SET;
    private static final TokenSet ARROW_SET;
    private static final TokenSet COLON_IN_KEYWORD_SET;
    private static final TokenSet EQ_RPAR_SET;
    static final TokenSet EXPRESSION_FIRST;
    static final TokenSet EXPRESSION_FOLLOW;
    private static final TokenSet IN_KEYWORD_L_BRACE_RECOVERY_SET;
    private static final TokenSet IN_KEYWORD_L_BRACE_SET;
    private static final TokenSet IN_KEYWORD_R_PAR_COLON_SET;
    private static final TokenSet IN_KEYWORD_SET;
    private static final TokenSet L_PAR_L_BRACE_R_PAR_SET;
    public static final TokenSet STATEMENT_FIRST;
    private static final TokenSet STATEMENT_NEW_LINE_QUICK_RECOVERY_SET;
    private static final TokenSet TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA;
    private static final TokenSet TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA_RECOVERY;
    private static final TokenSet TRY_CATCH_RECOVERY_TOKEN_SET;
    private static final TokenSet TYPE_ARGUMENT_LIST_STOPPERS;
    private final KotlinParsing myKotlinParsing;
    private static final TokenSet WHEN_CONDITION_RECOVERY_SET = TokenSet.create(KtTokens.RBRACE, KtTokens.IN_KEYWORD, KtTokens.NOT_IN, KtTokens.IS_KEYWORD, KtTokens.NOT_IS, KtTokens.ELSE_KEYWORD);
    private static final TokenSet WHEN_CONDITION_RECOVERY_SET_WITH_ARROW = TokenSet.create(KtTokens.RBRACE, KtTokens.IN_KEYWORD, KtTokens.NOT_IN, KtTokens.IS_KEYWORD, KtTokens.NOT_IS, KtTokens.ELSE_KEYWORD, KtTokens.ARROW, KtTokens.DOT);
    private static final ImmutableMap<String, KtToken> KEYWORD_TEXTS = tokenSetToMap(KtTokens.KEYWORDS);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "marker";
        } else if (i == 2) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i != 3) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "modifierDetector";
        }
        objArr[1] = "org/jetbrains/kotlin/parsing/KotlinExpressionParsing";
        if (i == 1 || i == 2) {
            objArr[2] = "doneOrDrop";
        } else if (i != 3) {
            objArr[2] = "parseDoubleColonSuffix";
        } else {
            objArr[2] = "parseLocalDeclarationRest";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.parsing.AbstractKotlinParsing
    public /* bridge */ /* synthetic */ String currentContext() {
        return super.currentContext();
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.ARROW, KtTokens.COMMA, KtTokens.COLON);
        TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA = tokenSetCreate;
        TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA_RECOVERY = TokenSet.orSet(tokenSetCreate, KotlinParsing.PARAMETER_NAME_RECOVERY_SET);
        EQ_RPAR_SET = TokenSet.create(KtTokens.EQ, KtTokens.RPAR);
        ARROW_SET = TokenSet.create(KtTokens.ARROW);
        ARROW_COMMA_SET = TokenSet.create(KtTokens.ARROW, KtTokens.COMMA);
        IN_KEYWORD_R_PAR_COLON_SET = TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.RPAR, KtTokens.COLON);
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.LBRACE);
        IN_KEYWORD_L_BRACE_SET = tokenSetCreate2;
        IN_KEYWORD_L_BRACE_RECOVERY_SET = TokenSet.orSet(tokenSetCreate2, KotlinParsing.PARAMETER_NAME_RECOVERY_SET);
        COLON_IN_KEYWORD_SET = TokenSet.create(KtTokens.COLON, KtTokens.IN_KEYWORD);
        L_PAR_L_BRACE_R_PAR_SET = TokenSet.create(KtTokens.LPAR, KtTokens.LBRACE, KtTokens.RPAR);
        IN_KEYWORD_SET = TokenSet.create(KtTokens.IN_KEYWORD);
        TRY_CATCH_RECOVERY_TOKEN_SET = TokenSet.create(KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.FINALLY_KEYWORD, KtTokens.CATCH_KEYWORD);
        TYPE_ARGUMENT_LIST_STOPPERS = TokenSet.create(KtTokens.INTEGER_LITERAL, KtTokens.FLOAT_LITERAL, KtTokens.CHARACTER_LITERAL, KtTokens.OPEN_QUOTE, KtTokens.PACKAGE_KEYWORD, KtTokens.AS_KEYWORD, KtTokens.TYPE_ALIAS_KEYWORD, KtTokens.INTERFACE_KEYWORD, KtTokens.CLASS_KEYWORD, KtTokens.THIS_KEYWORD, KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD, KtTokens.FUN_KEYWORD, KtTokens.FOR_KEYWORD, KtTokens.NULL_KEYWORD, KtTokens.TRUE_KEYWORD, KtTokens.FALSE_KEYWORD, KtTokens.IS_KEYWORD, KtTokens.THROW_KEYWORD, KtTokens.RETURN_KEYWORD, KtTokens.BREAK_KEYWORD, KtTokens.CONTINUE_KEYWORD, KtTokens.OBJECT_KEYWORD, KtTokens.IF_KEYWORD, KtTokens.TRY_KEYWORD, KtTokens.ELSE_KEYWORD, KtTokens.WHILE_KEYWORD, KtTokens.DO_KEYWORD, KtTokens.WHEN_KEYWORD, KtTokens.RBRACKET, KtTokens.RBRACE, KtTokens.RPAR, KtTokens.PLUSPLUS, KtTokens.MINUSMINUS, KtTokens.EXCLEXCL, KtTokens.PLUS, KtTokens.MINUS, KtTokens.EXCL, KtTokens.DIV, KtTokens.PERC, KtTokens.LTEQ, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ, KtTokens.EQEQ, KtTokens.EXCLEQ, KtTokens.ANDAND, KtTokens.OROR, KtTokens.SAFE_ACCESS, KtTokens.ELVIS, KtTokens.SEMICOLON, KtTokens.RANGE, KtTokens.RANGE_UNTIL, KtTokens.EQ, KtTokens.MULTEQ, KtTokens.DIVEQ, KtTokens.PERCEQ, KtTokens.PLUSEQ, KtTokens.MINUSEQ, KtTokens.NOT_IN, KtTokens.NOT_IS, KtTokens.COLONCOLON, KtTokens.COLON);
        TokenSet tokenSetCreate3 = TokenSet.create(KtTokens.MINUS, KtTokens.PLUS, KtTokens.MINUSMINUS, KtTokens.PLUSPLUS, KtTokens.EXCL, KtTokens.EXCLEXCL, KtTokens.COLONCOLON, KtTokens.LPAR, KtTokens.TRUE_KEYWORD, KtTokens.FALSE_KEYWORD, KtTokens.OPEN_QUOTE, KtTokens.INTEGER_LITERAL, KtTokens.CHARACTER_LITERAL, KtTokens.FLOAT_LITERAL, KtTokens.NULL_KEYWORD, KtTokens.LBRACE, KtTokens.FUN_KEYWORD, KtTokens.THIS_KEYWORD, KtTokens.SUPER_KEYWORD, KtTokens.IF_KEYWORD, KtTokens.WHEN_KEYWORD, KtTokens.TRY_KEYWORD, KtTokens.OBJECT_KEYWORD, KtTokens.THROW_KEYWORD, KtTokens.RETURN_KEYWORD, KtTokens.CONTINUE_KEYWORD, KtTokens.BREAK_KEYWORD, KtTokens.FOR_KEYWORD, KtTokens.WHILE_KEYWORD, KtTokens.DO_KEYWORD, KtTokens.IDENTIFIER, KtTokens.AT, KtTokens.LBRACKET);
        EXPRESSION_FIRST = tokenSetCreate3;
        TokenSet tokenSetOrSet = TokenSet.orSet(tokenSetCreate3, TokenSet.create(KtTokens.FUN_KEYWORD, KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD, KtTokens.INTERFACE_KEYWORD, KtTokens.CLASS_KEYWORD, KtTokens.TYPE_ALIAS_KEYWORD), KtTokens.MODIFIER_KEYWORDS);
        STATEMENT_FIRST = tokenSetOrSet;
        STATEMENT_NEW_LINE_QUICK_RECOVERY_SET = TokenSet.orSet(TokenSet.andSet(tokenSetOrSet, TokenSet.andNot(KtTokens.KEYWORDS, TokenSet.create(KtTokens.IN_KEYWORD))), TokenSet.create(KtTokens.EOL_OR_SEMICOLON));
        EXPRESSION_FOLLOW = TokenSet.create(KtTokens.EOL_OR_SEMICOLON, KtTokens.ARROW, KtTokens.COMMA, KtTokens.RBRACE, KtTokens.RPAR, KtTokens.RBRACKET);
        ALLOW_NEWLINE_OPERATIONS = TokenSet.create(KtTokens.DOT, KtTokens.SAFE_ACCESS, KtTokens.COLON, KtTokens.AS_KEYWORD, KtTokens.AS_SAFE, KtTokens.ELVIS, KtTokens.ANDAND, KtTokens.OROR);
        HashSet hashSet = new HashSet();
        for (Precedence precedence : Precedence.values()) {
            hashSet.addAll(Arrays.asList(precedence.getOperations().getTypes()));
        }
        TokenSet tokenSetCreate4 = TokenSet.create((IElementType[]) hashSet.toArray(new IElementType[0]));
        ALL_OPERATIONS = tokenSetCreate4;
        HashSet hashSet2 = new HashSet(Arrays.asList(KtTokens.OPERATIONS.getTypes()));
        HashSet hashSet3 = new HashSet(Arrays.asList(tokenSetCreate4.getTypes()));
        if (hashSet2.size() > hashSet3.size()) {
            hashSet2.removeAll(hashSet3);
        }
        hashSet3.removeAll(hashSet2);
    }

    private static ImmutableMap<String, KtToken> tokenSetToMap(TokenSet tokenSet) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (IElementType iElementType : tokenSet.getTypes()) {
            builder.put(iElementType.toString(), (KtToken) iElementType);
        }
        return builder.build();
    }

    public enum Precedence {
        POSTFIX(KtTokens.PLUSPLUS, KtTokens.MINUSMINUS, KtTokens.EXCLEXCL, KtTokens.DOT, KtTokens.SAFE_ACCESS),
        PREFIX(KtTokens.MINUS, KtTokens.PLUS, KtTokens.MINUSMINUS, KtTokens.PLUSPLUS, KtTokens.EXCL) { // from class: org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence.1
            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence
            public void parseHigherPrecedence(KotlinExpressionParsing kotlinExpressionParsing) {
                throw new IllegalStateException("Don't call this method");
            }
        },
        AS(KtTokens.AS_KEYWORD, KtTokens.AS_SAFE) { // from class: org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence.2
            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence
            public IElementType parseRightHandSide(IElementType iElementType, KotlinExpressionParsing kotlinExpressionParsing) {
                kotlinExpressionParsing.myKotlinParsing.parseTypeRefWithoutIntersections();
                return KtNodeTypes.BINARY_WITH_TYPE;
            }

            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence
            public void parseHigherPrecedence(KotlinExpressionParsing kotlinExpressionParsing) {
                kotlinExpressionParsing.parsePrefixExpression();
            }
        },
        MULTIPLICATIVE(KtTokens.MUL, KtTokens.DIV, KtTokens.PERC),
        ADDITIVE(KtTokens.PLUS, KtTokens.MINUS),
        RANGE(KtTokens.RANGE, KtTokens.RANGE_UNTIL),
        SIMPLE_NAME(KtTokens.IDENTIFIER),
        ELVIS(KtTokens.ELVIS),
        IN_OR_IS(KtTokens.IN_KEYWORD, KtTokens.NOT_IN, KtTokens.IS_KEYWORD, KtTokens.NOT_IS) { // from class: org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence.3
            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing.Precedence
            public IElementType parseRightHandSide(IElementType iElementType, KotlinExpressionParsing kotlinExpressionParsing) {
                if (iElementType == KtTokens.IS_KEYWORD || iElementType == KtTokens.NOT_IS) {
                    kotlinExpressionParsing.myKotlinParsing.parseTypeRefWithoutIntersections();
                    return KtNodeTypes.IS_EXPRESSION;
                }
                return super.parseRightHandSide(iElementType, kotlinExpressionParsing);
            }
        },
        COMPARISON(KtTokens.LT, KtTokens.GT, KtTokens.LTEQ, KtTokens.GTEQ),
        EQUALITY(KtTokens.EQEQ, KtTokens.EXCLEQ, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ),
        CONJUNCTION(KtTokens.ANDAND),
        DISJUNCTION(KtTokens.OROR),
        ASSIGNMENT(KtTokens.EQ, KtTokens.PLUSEQ, KtTokens.MINUSEQ, KtTokens.MULTEQ, KtTokens.DIVEQ, KtTokens.PERCEQ);

        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Precedence higher;
        private final TokenSet operations;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/parsing/KotlinExpressionParsing$Precedence", "getOperations"));
        }

        static {
            Precedence[] precedenceArrValues = values();
            for (Precedence precedence : precedenceArrValues) {
                int iOrdinal = precedence.ordinal();
                precedence.higher = iOrdinal > 0 ? precedenceArrValues[iOrdinal - 1] : null;
            }
        }

        Precedence(IElementType... iElementTypeArr) {
            this.operations = TokenSet.create(iElementTypeArr);
        }

        public void parseHigherPrecedence(KotlinExpressionParsing kotlinExpressionParsing) {
            kotlinExpressionParsing.parseBinaryExpression(this.higher);
        }

        public IElementType parseRightHandSide(IElementType iElementType, KotlinExpressionParsing kotlinExpressionParsing) {
            parseHigherPrecedence(kotlinExpressionParsing);
            return KtNodeTypes.BINARY_EXPRESSION;
        }

        public final TokenSet getOperations() {
            TokenSet tokenSet = this.operations;
            if (tokenSet == null) {
                $$$reportNull$$$0(0);
            }
            return tokenSet;
        }
    }

    public KotlinExpressionParsing(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, KotlinParsing kotlinParsing) {
        this(semanticWhitespaceAwarePsiBuilder, kotlinParsing, true);
    }

    public KotlinExpressionParsing(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, KotlinParsing kotlinParsing, boolean z) {
        super(semanticWhitespaceAwarePsiBuilder, z);
        this.myKotlinParsing = kotlinParsing;
    }

    public void parseExpression() {
        if (!atSet(EXPRESSION_FIRST)) {
            error("Expecting an expression");
        } else {
            parseBinaryExpression(Precedence.ASSIGNMENT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseBinaryExpression(Precedence precedence) {
        PsiBuilder.Marker markerMark = mark();
        precedence.parseHigherPrecedence(this);
        while (!interruptedWithNewLine() && atSet(precedence.getOperations())) {
            IElementType iElementTypeTt = tt();
            parseOperationReference();
            markerMark.done(precedence.parseRightHandSide(iElementTypeTt, this));
            markerMark = markerMark.m6464precede();
        }
        markerMark.drop();
    }

    private void parseLabeledExpression() {
        PsiBuilder.Marker markerMark = mark();
        parseLabelDefinition();
        parsePrefixExpression();
        markerMark.done(KtNodeTypes.LABELED_EXPRESSION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parsePrefixExpression() {
        if (at(KtTokens.AT)) {
            if (parseLocalDeclaration(false, false)) {
                return;
            }
            PsiBuilder.Marker markerMark = mark();
            this.myKotlinParsing.parseAnnotations(KotlinParsing.AnnotationParsingMode.DEFAULT);
            parsePrefixExpression();
            markerMark.done(KtNodeTypes.ANNOTATED_EXPRESSION);
            return;
        }
        this.myBuilder.disableJoiningComplexTokens();
        if (isAtLabelDefinitionOrMissingIdentifier()) {
            this.myBuilder.restoreJoiningComplexTokensState();
            parseLabeledExpression();
        } else {
            if (atSet(Precedence.PREFIX.getOperations())) {
                PsiBuilder.Marker markerMark2 = mark();
                parseOperationReference();
                this.myBuilder.restoreJoiningComplexTokensState();
                parsePrefixExpression();
                markerMark2.done(KtNodeTypes.PREFIX_EXPRESSION);
                return;
            }
            this.myBuilder.restoreJoiningComplexTokensState();
            parsePostfixExpression();
        }
    }

    private boolean parseDoubleColonSuffix(PsiBuilder.Marker marker) {
        if (marker == null) {
            $$$reportNull$$$0(0);
        }
        if (!at(KtTokens.COLONCOLON)) {
            return false;
        }
        advance();
        if (at(KtTokens.CLASS_KEYWORD)) {
            advance();
            marker.done(KtNodeTypes.CLASS_LITERAL_EXPRESSION);
            return true;
        }
        parseSimpleNameExpression();
        if (at(KtTokens.LT)) {
            PsiBuilder.Marker markerMark = mark();
            if (this.myKotlinParsing.tryParseTypeArgumentList(TYPE_ARGUMENT_LIST_STOPPERS)) {
                markerMark.error("Type arguments are not allowed");
            } else {
                markerMark.rollbackTo();
            }
        }
        if (at(KtTokens.LPAR) && !this.myBuilder.newlineBeforeCurrentToken()) {
            PsiBuilder.Marker markerMark2 = mark();
            parseCallSuffix();
            markerMark2.error("This syntax is reserved for future use; to call a reference, enclose it in parentheses: (foo::bar)(args)");
        }
        marker.done(KtNodeTypes.CALLABLE_REFERENCE_EXPRESSION);
        return true;
    }

    private void skipQuestionMarksBeforeDoubleColon() {
        if (at(KtTokens.QUEST)) {
            int i = 1;
            while (lookahead(i) == KtTokens.QUEST) {
                i++;
            }
            if (lookahead(i) == KtTokens.COLONCOLON) {
                while (i > 0) {
                    advance();
                    i--;
                }
            }
        }
    }

    private void parsePostfixExpression() {
        PsiBuilder.Marker markerMark = mark();
        boolean doubleColonSuffix = at(KtTokens.COLONCOLON) ? parseDoubleColonSuffix(mark()) : parseAtomicExpression();
        while (!interruptedWithNewLine()) {
            if (at(KtTokens.LBRACKET)) {
                parseArrayAccess();
                markerMark.done(KtNodeTypes.ARRAY_ACCESS_EXPRESSION);
            } else if (parseCallSuffix()) {
                markerMark.done(KtNodeTypes.CALL_EXPRESSION);
            } else if (at(KtTokens.DOT) || at(KtTokens.SAFE_ACCESS)) {
                IElementType iElementType = at(KtTokens.DOT) ? KtNodeTypes.DOT_QUALIFIED_EXPRESSION : KtNodeTypes.SAFE_ACCESS_EXPRESSION;
                advance();
                if (!doubleColonSuffix) {
                    markerMark.drop();
                    markerMark = mark();
                    doubleColonSuffix = parseAtomicExpression();
                } else {
                    parseSelectorCallExpression();
                    markerMark.done(iElementType);
                }
            } else if (atSet(Precedence.POSTFIX.getOperations())) {
                parseOperationReference();
                markerMark.done(KtNodeTypes.POSTFIX_EXPRESSION);
            } else {
                skipQuestionMarksBeforeDoubleColon();
                if (!parseDoubleColonSuffix(markerMark)) {
                    break;
                }
            }
            markerMark = markerMark.m6464precede();
        }
        markerMark.drop();
    }

    private boolean parseCallSuffix() {
        if (parseCallWithClosure()) {
            return true;
        }
        if (at(KtTokens.LPAR)) {
            parseValueArgumentList();
            parseCallWithClosure();
            return true;
        }
        if (at(KtTokens.LT)) {
            PsiBuilder.Marker markerMark = mark();
            if (this.myKotlinParsing.tryParseTypeArgumentList(TYPE_ARGUMENT_LIST_STOPPERS)) {
                markerMark.done(KtNodeTypes.TYPE_ARGUMENT_LIST);
                if (!this.myBuilder.newlineBeforeCurrentToken() && at(KtTokens.LPAR)) {
                    parseValueArgumentList();
                }
                parseCallWithClosure();
                return true;
            }
            markerMark.rollbackTo();
        }
        return false;
    }

    private void parseSelectorCallExpression() {
        PsiBuilder.Marker markerMark = mark();
        parseAtomicExpression();
        if (!this.myBuilder.newlineBeforeCurrentToken() && parseCallSuffix()) {
            markerMark.done(KtNodeTypes.CALL_EXPRESSION);
        } else {
            markerMark.drop();
        }
    }

    private void parseOperationReference() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        markerMark.done(KtNodeTypes.OPERATION_REFERENCE);
    }

    protected boolean parseCallWithClosure() {
        boolean z = false;
        while (true) {
            PsiBuilder.Marker markerMark = mark();
            if (!parseAnnotatedLambda(false)) {
                markerMark.drop();
                return z;
            }
            markerMark.done(KtNodeTypes.LAMBDA_ARGUMENT);
            z = true;
        }
    }

    private boolean parseAnnotatedLambda(boolean z) {
        PsiBuilder.Marker markerMark = mark();
        boolean annotations = this.myKotlinParsing.parseAnnotations(KotlinParsing.AnnotationParsingMode.DEFAULT);
        PsiBuilder.Marker markerMark2 = mark();
        boolean zIsAtLabelDefinitionOrMissingIdentifier = isAtLabelDefinitionOrMissingIdentifier();
        if (zIsAtLabelDefinitionOrMissingIdentifier) {
            parseLabelDefinition();
        }
        if (!at(KtTokens.LBRACE)) {
            markerMark.rollbackTo();
            return false;
        }
        parseFunctionLiteral(z, true);
        doneOrDrop(markerMark2, KtNodeTypes.LABELED_EXPRESSION, zIsAtLabelDefinitionOrMissingIdentifier);
        doneOrDrop(markerMark, KtNodeTypes.ANNOTATED_EXPRESSION, annotations);
        return true;
    }

    private static void doneOrDrop(PsiBuilder.Marker marker, IElementType iElementType, boolean z) {
        if (marker == null) {
            $$$reportNull$$$0(1);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (z) {
            marker.done(iElementType);
        } else {
            marker.drop();
        }
    }

    boolean isAtLabelDefinitionOrMissingIdentifier() {
        return (at(KtTokens.IDENTIFIER) && this.myBuilder.rawLookup(1) == KtTokens.AT) || at(KtTokens.AT);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:21:0x002d A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:37:0x007a  */
    /* JADX WARN: Code duplicated, block: B:50:0x00af  */
    /* JADX WARN: Failed to find 'out' block for switch in B:18:0x0024. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:19:0x0027. Please report as an issue. */
    private boolean parseAtomicExpression() {
        boolean z;
        int tokenId = getTokenId();
        if (tokenId == 6) {
            parseOneTokenExpression(KtNodeTypes.INTEGER_CONSTANT);
        } else if (tokenId == 7) {
            parseOneTokenExpression(KtNodeTypes.FLOAT_CONSTANT);
        } else if (tokenId == 8) {
            parseOneTokenExpression(KtNodeTypes.CHARACTER_CONSTANT);
        } else if (tokenId == 10) {
            parseStringTemplate();
        } else if (tokenId == 46) {
            parseSimpleNameExpression();
        } else if (tokenId == 48) {
            parseCollectionLiteralExpression();
        } else if (tokenId == 50) {
            parseFunctionLiteral();
        } else if (tokenId == 52) {
            parseParenthesizedExpression();
        } else {
            switch (tokenId) {
                case 19:
                case 20:
                case 23:
                case 24:
                case 25:
                    z = parseLocalDeclaration(this.myBuilder.newlineBeforeCurrentToken(), false);
                    break;
                case 21:
                    parseThisExpression();
                    break;
                case 22:
                    parseSuperExpression();
                    break;
                case 26:
                    parseFor();
                    break;
                case 27:
                    parseOneTokenExpression(KtNodeTypes.NULL);
                    break;
                case 28:
                case 29:
                    parseOneTokenExpression(KtNodeTypes.BOOLEAN_CONSTANT);
                    break;
                default:
                    switch (tokenId) {
                        case 32:
                            parseThrow();
                            break;
                        case 33:
                            parseReturn();
                            break;
                        case 34:
                            parseJump(KtNodeTypes.BREAK);
                            break;
                        case 35:
                            parseJump(KtNodeTypes.CONTINUE);
                            break;
                        case 36:
                            parseObjectLiteral();
                            break;
                        case 37:
                            parseIf();
                            break;
                        case 38:
                            parseTry();
                            break;
                        default:
                            switch (tokenId) {
                                case 40:
                                    parseWhile();
                                    break;
                                case 41:
                                    parseDoWhile();
                                    break;
                                case 42:
                                    parseWhen();
                                    break;
                                case 43:
                                    if (parseLocalDeclaration(this.myBuilder.newlineBeforeCurrentToken(), false)) {
                                    }
                                default:
                                    break;
                            }
                            break;
                    }
                    break;
            }
            if (!z) {
                errorWithRecovery("Expecting an element", TokenSet.orSet(EXPRESSION_FOLLOW, TokenSet.create(KtTokens.LONG_TEMPLATE_ENTRY_END)));
            }
            return z;
        }
        if (!z) {
            errorWithRecovery("Expecting an element", TokenSet.orSet(EXPRESSION_FOLLOW, TokenSet.create(KtTokens.LONG_TEMPLATE_ENTRY_END)));
        }
        return z;
    }

    private void parseStringTemplate() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        while (!eof() && !at(KtTokens.CLOSING_QUOTE) && !at(KtTokens.DANGLING_NEWLINE)) {
            parseStringTemplateElement();
        }
        if (at(KtTokens.DANGLING_NEWLINE)) {
            errorAndAdvance("Expecting '\"'");
        } else {
            expect(KtTokens.CLOSING_QUOTE, "Expecting '\"'");
        }
        markerMark.done(KtNodeTypes.STRING_TEMPLATE);
    }

    private void parseStringTemplateElement() {
        if (at(KtTokens.REGULAR_STRING_PART)) {
            PsiBuilder.Marker markerMark = mark();
            advance();
            markerMark.done(KtNodeTypes.LITERAL_STRING_TEMPLATE_ENTRY);
            return;
        }
        if (at(KtTokens.ESCAPE_SEQUENCE)) {
            PsiBuilder.Marker markerMark2 = mark();
            advance();
            markerMark2.done(KtNodeTypes.ESCAPE_STRING_TEMPLATE_ENTRY);
            return;
        }
        if (at(KtTokens.SHORT_TEMPLATE_ENTRY_START)) {
            PsiBuilder.Marker markerMark3 = mark();
            advance();
            if (at(KtTokens.THIS_KEYWORD)) {
                PsiBuilder.Marker markerMark4 = mark();
                PsiBuilder.Marker markerMark5 = mark();
                advance();
                markerMark5.done(KtNodeTypes.REFERENCE_EXPRESSION);
                markerMark4.done(KtNodeTypes.THIS_EXPRESSION);
            } else {
                KtToken ktToken = KEYWORD_TEXTS.get(this.myBuilder.getTokenText());
                if (ktToken != null) {
                    this.myBuilder.remapCurrentToken(ktToken);
                    errorAndAdvance("Keyword cannot be used as a reference");
                } else {
                    PsiBuilder.Marker markerMark6 = mark();
                    expect(KtTokens.IDENTIFIER, "Expecting a name");
                    markerMark6.done(KtNodeTypes.REFERENCE_EXPRESSION);
                }
            }
            markerMark3.done(KtNodeTypes.SHORT_STRING_TEMPLATE_ENTRY);
            return;
        }
        if (at(KtTokens.LONG_TEMPLATE_ENTRY_START)) {
            PsiBuilder.Marker markerMark7 = mark();
            advance();
            while (!eof()) {
                int currentOffset = this.myBuilder.getCurrentOffset();
                parseExpression();
                if (_at(KtTokens.LONG_TEMPLATE_ENTRY_END)) {
                    advance();
                    break;
                } else {
                    error("Expecting '}'");
                    if (currentOffset == this.myBuilder.getCurrentOffset()) {
                        advance();
                    }
                }
            }
            markerMark7.done(KtNodeTypes.LONG_STRING_TEMPLATE_ENTRY);
            return;
        }
        errorAndAdvance("Unexpected token in a string template");
    }

    private void parseWhen() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        this.myBuilder.disableNewlines();
        if (at(KtTokens.LPAR)) {
            advanceAt(KtTokens.LPAR);
            PsiBuilder.Marker markerMark2 = mark();
            this.myKotlinParsing.parseAnnotationsList(EQ_RPAR_SET);
            if (at(KtTokens.VAL_KEYWORD) || at(KtTokens.VAR_KEYWORD)) {
                markerMark2.done(this.myKotlinParsing.parseProperty(KotlinParsing.DeclarationParsingMode.LOCAL));
                markerMark2.setCustomEdgeTokenBinders(PrecedingDocCommentsBinder.INSTANCE, TrailingCommentsBinder.INSTANCE);
            } else {
                markerMark2.drop();
                parseExpression();
            }
            expect(KtTokens.RPAR, "Expecting ')'");
        }
        this.myBuilder.restoreNewlinesState();
        this.myBuilder.enableNewlines();
        if (expect(KtTokens.LBRACE, "Expecting '{'")) {
            while (!eof() && !at(KtTokens.RBRACE)) {
                parseWhenEntry();
            }
            expect(KtTokens.RBRACE, "Expecting '}'");
        }
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.WHEN);
    }

    private void parseWhenEntry() {
        PsiBuilder.Marker markerMark = mark();
        if (at(KtTokens.ELSE_KEYWORD)) {
            advance();
            if (!at(KtTokens.ARROW)) {
                errorUntil("Expecting '->'", TokenSet.create(KtTokens.ARROW, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.EOL_OR_SEMICOLON));
            }
            if (at(KtTokens.ARROW)) {
                advance();
                if (atSet(WHEN_CONDITION_RECOVERY_SET)) {
                    error("Expecting an element");
                } else {
                    parseControlStructureBody();
                }
            } else if (at(KtTokens.LBRACE)) {
                parseControlStructureBody();
            } else if (!atSet(WHEN_CONDITION_RECOVERY_SET)) {
                errorAndAdvance("Expecting '->'");
            }
        } else {
            parseWhenEntryNotElse();
        }
        markerMark.done(KtNodeTypes.WHEN_ENTRY);
        consumeIf(KtTokens.SEMICOLON);
    }

    private void parseWhenEntryNotElse() {
        while (true) {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance("Expecting a when-condition");
            } else {
                parseWhenCondition();
                if (!at(KtTokens.COMMA)) {
                    break;
                }
                advance();
                if (at(KtTokens.ARROW)) {
                    break;
                }
            }
        }
        KtSingleValueToken ktSingleValueToken = KtTokens.ARROW;
        TokenSet tokenSet = WHEN_CONDITION_RECOVERY_SET;
        expect(ktSingleValueToken, "Expecting '->'", tokenSet);
        if (atSet(tokenSet)) {
            error("Expecting an element");
        } else {
            parseControlStructureBody();
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0041  */
    /* JADX WARN: Code duplicated, block: B:22:0x0055  */
    /* JADX WARN: Code duplicated, block: B:23:0x005b  */
    /* JADX WARN: Code duplicated, block: B:25:0x0064  */
    /* JADX WARN: Code duplicated, block: B:27:0x006f  */
    /* JADX WARN: Code duplicated, block: B:28:0x0075  */
    private void parseWhenCondition() {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        int tokenId = getTokenId();
        if (tokenId == 30) {
            advance();
            if (atSet(WHEN_CONDITION_RECOVERY_SET_WITH_ARROW)) {
                error("Expecting a type");
            } else {
                this.myKotlinParsing.parseTypeRef();
            }
            markerMark.done(KtNodeTypes.WHEN_CONDITION_IS_PATTERN);
        } else if (tokenId == 31) {
            PsiBuilder.Marker markerMark2 = mark();
            advance();
            markerMark2.done(KtNodeTypes.OPERATION_REFERENCE);
            if (atSet(WHEN_CONDITION_RECOVERY_SET_WITH_ARROW)) {
                error("Expecting an element");
            } else {
                parseExpression();
            }
            markerMark.done(KtNodeTypes.WHEN_CONDITION_IN_RANGE);
        } else if (tokenId == 39 || tokenId == 51 || tokenId == 54 || tokenId == 68) {
            error("Expecting an expression, is-condition or in-condition");
            markerMark.done(KtNodeTypes.WHEN_CONDITION_EXPRESSION);
        } else if (tokenId == 92) {
            PsiBuilder.Marker markerMark3 = mark();
            advance();
            markerMark3.done(KtNodeTypes.OPERATION_REFERENCE);
            if (atSet(WHEN_CONDITION_RECOVERY_SET_WITH_ARROW)) {
                error("Expecting an element");
            } else {
                parseExpression();
            }
            markerMark.done(KtNodeTypes.WHEN_CONDITION_IN_RANGE);
        } else if (tokenId == 93) {
            advance();
            if (atSet(WHEN_CONDITION_RECOVERY_SET_WITH_ARROW)) {
                error("Expecting a type");
            } else {
                this.myKotlinParsing.parseTypeRef();
            }
            markerMark.done(KtNodeTypes.WHEN_CONDITION_IS_PATTERN);
        } else {
            parseExpression();
            markerMark.done(KtNodeTypes.WHEN_CONDITION_EXPRESSION);
        }
        this.myBuilder.restoreNewlinesState();
    }

    private void parseArrayAccess() {
        parseAsCollectionLiteralExpression(KtNodeTypes.INDICES, false, "Expecting an index element");
    }

    private void parseCollectionLiteralExpression() {
        parseAsCollectionLiteralExpression(KtNodeTypes.COLLECTION_LITERAL_EXPRESSION, true, "Expecting an element");
    }

    private void parseAsCollectionLiteralExpression(IElementType iElementType, boolean z, String str) {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        advance();
        if (!z && at(KtTokens.RBRACKET)) {
            error(str);
        } else {
            parseInnerExpressions(str);
        }
        expect(KtTokens.RBRACKET, "Expecting ']'");
        this.myBuilder.restoreNewlinesState();
        markerMark.done(iElementType);
    }

    private void parseInnerExpressions(String str) {
        while (true) {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance(str);
            }
            if (at(KtTokens.RBRACKET)) {
                return;
            }
            parseExpression();
            if (!at(KtTokens.COMMA)) {
                return;
            } else {
                advance();
            }
        }
    }

    public void parseContractDescriptionBlock() {
        advance();
        parseContractEffectList();
    }

    private void parseContractEffectList() {
        PsiBuilder.Marker markerMark = mark();
        expect(KtTokens.LBRACKET, "Expecting '['");
        this.myBuilder.enableNewlines();
        parseContractEffects();
        expect(KtTokens.RBRACKET, "Expecting ']'");
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.CONTRACT_EFFECT_LIST);
    }

    private void parseContractEffects() {
        while (true) {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance("Expecting a contract effect");
            }
            if (at(KtTokens.RBRACKET)) {
                return;
            }
            PsiBuilder.Marker markerMark = mark();
            parseExpression();
            markerMark.done(KtNodeTypes.CONTRACT_EFFECT);
            if (!at(KtTokens.COMMA)) {
                return;
            } else {
                advance();
            }
        }
    }

    public void parseSimpleNameExpression() {
        PsiBuilder.Marker markerMark = mark();
        expect(KtTokens.IDENTIFIER, "Expecting an identifier");
        markerMark.done(KtNodeTypes.REFERENCE_EXPRESSION);
    }

    private boolean parseLocalDeclaration(boolean z, boolean z2) {
        PsiBuilder.Marker markerMark = mark();
        KotlinParsing.ModifierDetector modifierDetector = new KotlinParsing.ModifierDetector();
        this.myKotlinParsing.parseModifierList(modifierDetector, TokenSet.EMPTY);
        IElementType localDeclarationRest = parseLocalDeclarationRest(modifierDetector, z, z2);
        boolean z3 = false;
        if (localDeclarationRest != null) {
            if (localDeclarationRest != KtNodeTypes.PROPERTY && localDeclarationRest != KtNodeTypes.DESTRUCTURING_DECLARATION) {
                z3 = true;
            }
            closeDeclarationWithCommentBinders(markerMark, localDeclarationRest, z3);
            return true;
        }
        markerMark.rollbackTo();
        return false;
    }

    private void parseFunctionLiteral() {
        parseFunctionLiteral(false, true);
    }

    public void parseFunctionLiteral(boolean z, boolean z2) {
        boolean zRollbackOrDropAt;
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        this.myBuilder.enableNewlines();
        advance();
        IElementType iElementTypeTt = tt();
        boolean z3 = true;
        if (iElementTypeTt == KtTokens.ARROW) {
            mark().done(KtNodeTypes.VALUE_PARAMETER_LIST);
            advance();
        } else if (iElementTypeTt == KtTokens.IDENTIFIER || iElementTypeTt == KtTokens.COLON || iElementTypeTt == KtTokens.LPAR) {
            PsiBuilder.Marker markerMark3 = mark();
            IElementType iElementTypeLookahead = lookahead(1);
            if (iElementTypeLookahead != KtTokens.COMMA && iElementTypeLookahead != KtTokens.COLON) {
                z3 = false;
            }
            parseFunctionLiteralParameterList();
            if (z3) {
                zRollbackOrDropAt = rollbackOrDrop(markerMark3, KtTokens.ARROW, "An -> is expected", KtTokens.RBRACE);
            } else {
                zRollbackOrDropAt = rollbackOrDropAt(markerMark3, KtTokens.ARROW);
            }
            z3 = zRollbackOrDropAt;
        } else {
            z3 = false;
        }
        if (!z3 && z) {
            markerMark2.drop();
            parseStatements();
            expect(KtTokens.RBRACE, "Expecting '}'");
            markerMark.done(KtNodeTypes.BLOCK);
            this.myBuilder.restoreNewlinesState();
            return;
        }
        if (z2 && this.isLazy) {
            this.myKotlinParsing.advanceBalancedBlock();
            markerMark2.done(KtNodeTypes.FUNCTION_LITERAL);
            markerMark.collapse(KtNodeTypes.LAMBDA_EXPRESSION);
        } else {
            PsiBuilder.Marker markerMark4 = mark();
            parseStatements();
            markerMark4.done(KtNodeTypes.BLOCK);
            markerMark4.setCustomEdgeTokenBinders(KotlinWhitespaceAndCommentsBindersKt.PRECEDING_ALL_COMMENTS_BINDER, KotlinWhitespaceAndCommentsBindersKt.TRAILING_ALL_COMMENTS_BINDER);
            expect(KtTokens.RBRACE, "Expecting '}'");
            markerMark2.done(KtNodeTypes.FUNCTION_LITERAL);
            markerMark.done(KtNodeTypes.LAMBDA_EXPRESSION);
        }
        this.myBuilder.restoreNewlinesState();
    }

    private boolean rollbackOrDropAt(PsiBuilder.Marker marker, IElementType iElementType) {
        if (at(iElementType)) {
            advance();
            marker.drop();
            return true;
        }
        marker.rollbackTo();
        return false;
    }

    private boolean rollbackOrDrop(PsiBuilder.Marker marker, KtToken ktToken, String str, IElementType iElementType) {
        if (at(ktToken)) {
            advance();
            marker.drop();
            return true;
        }
        if (at(iElementType)) {
            marker.drop();
            expect(ktToken, str);
            return true;
        }
        marker.rollbackTo();
        return false;
    }

    private void parseFunctionLiteralParameterList() {
        PsiBuilder.Marker markerMark = mark();
        while (!eof() && !at(KtTokens.ARROW)) {
            PsiBuilder.Marker markerMark2 = mark();
            if (at(KtTokens.COLON)) {
                error("Expecting parameter name");
            } else if (at(KtTokens.LPAR)) {
                PsiBuilder.Marker markerMark3 = mark();
                this.myKotlinParsing.parseMultiDeclarationName(TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA, TOKEN_SET_TO_FOLLOW_AFTER_DESTRUCTURING_DECLARATION_IN_LAMBDA_RECOVERY);
                markerMark3.done(KtNodeTypes.DESTRUCTURING_DECLARATION);
            } else {
                expect(KtTokens.IDENTIFIER, "Expecting parameter name", ARROW_SET);
            }
            if (at(KtTokens.COLON)) {
                advance();
                this.myKotlinParsing.parseTypeRef(ARROW_COMMA_SET);
            }
            markerMark2.done(KtNodeTypes.VALUE_PARAMETER);
            if (!at(KtTokens.ARROW)) {
                if (at(KtTokens.COMMA)) {
                    advance();
                } else {
                    error("Expecting '->' or ','");
                    break;
                }
            } else {
                break;
            }
        }
        markerMark.done(KtNodeTypes.VALUE_PARAMETER_LIST);
    }

    public void parseStatements() {
        parseStatements(false);
    }

    public void parseStatements(boolean z) {
        while (at(KtTokens.SEMICOLON)) {
            advance();
        }
        while (!eof() && !at(KtTokens.RBRACE)) {
            TokenSet tokenSet = STATEMENT_FIRST;
            if (!atSet(tokenSet)) {
                errorAndAdvance("Expecting an element");
            }
            if (atSet(tokenSet)) {
                parseStatement(z);
            }
            if (at(KtTokens.SEMICOLON)) {
                while (at(KtTokens.SEMICOLON)) {
                    advance();
                }
            } else {
                if (at(KtTokens.RBRACE)) {
                    return;
                }
                if (!z && !this.myBuilder.newlineBeforeCurrentToken()) {
                    if (atSet(STATEMENT_NEW_LINE_QUICK_RECOVERY_SET)) {
                        error("Unexpected tokens (use ';' to separate expressions on the same line)");
                    } else {
                        errorUntil("Unexpected tokens (use ';' to separate expressions on the same line)", TokenSet.create(KtTokens.EOL_OR_SEMICOLON, KtTokens.LBRACE, KtTokens.RBRACE));
                    }
                }
            }
        }
    }

    private void parseStatement(boolean z) {
        if (parseLocalDeclaration(false, z)) {
            return;
        }
        if (!atSet(EXPRESSION_FIRST)) {
            errorAndAdvance("Expecting a statement");
        } else {
            if (z) {
                PsiBuilder.Marker markerMark = mark();
                parseBlockLevelExpression();
                markerMark.done(KtNodeTypes.SCRIPT_INITIALIZER);
                return;
            }
            parseBlockLevelExpression();
        }
    }

    private void parseBlockLevelExpression() {
        if (at(KtTokens.AT)) {
            PsiBuilder.Marker markerMark = mark();
            this.myKotlinParsing.parseAnnotations(KotlinParsing.AnnotationParsingMode.DEFAULT);
            if (!this.myBuilder.newlineBeforeCurrentToken()) {
                markerMark.rollbackTo();
                parseExpression();
                return;
            } else {
                parseBlockLevelExpression();
                markerMark.done(KtNodeTypes.ANNOTATED_EXPRESSION);
                return;
            }
        }
        parseExpression();
    }

    private IElementType parseLocalDeclarationRest(KotlinParsing.ModifierDetector modifierDetector, boolean z, boolean z2) {
        IElementType iElementTypeLookahead;
        if (modifierDetector == null) {
            $$$reportNull$$$0(3);
        }
        IElementType iElementTypeTt = tt();
        if (z) {
            if (iElementTypeTt != KtTokens.FUN_KEYWORD) {
                return null;
            }
            return this.myKotlinParsing.parseFunction(true);
        }
        if (iElementTypeTt == KtTokens.OBJECT_KEYWORD && ((iElementTypeLookahead = lookahead(1)) == KtTokens.COLON || iElementTypeLookahead == KtTokens.LBRACE)) {
            return null;
        }
        return this.myKotlinParsing.parseCommonDeclaration(modifierDetector, KotlinParsing.NameParsingMode.REQUIRED, z2 ? KotlinParsing.DeclarationParsingMode.SCRIPT_TOPLEVEL : KotlinParsing.DeclarationParsingMode.LOCAL);
    }

    private void parseDoWhile() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        if (!at(KtTokens.WHILE_KEYWORD)) {
            parseLoopBody();
        }
        if (expect(KtTokens.WHILE_KEYWORD, "Expecting 'while' followed by a post-condition")) {
            parseCondition();
        }
        markerMark.done(KtNodeTypes.DO_WHILE);
    }

    private void parseWhile() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        parseCondition();
        parseLoopBody();
        markerMark.done(KtNodeTypes.WHILE);
    }

    private void parseFor() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        if (expect(KtTokens.LPAR, "Expecting '(' to open a loop range", EXPRESSION_FIRST)) {
            this.myBuilder.disableNewlines();
            if (!at(KtTokens.RPAR)) {
                PsiBuilder.Marker markerMark2 = mark();
                if (!at(KtTokens.IN_KEYWORD)) {
                    this.myKotlinParsing.parseModifierList(IN_KEYWORD_R_PAR_COLON_SET);
                }
                if (at(KtTokens.VAL_KEYWORD) || at(KtTokens.VAR_KEYWORD)) {
                    advance();
                }
                if (at(KtTokens.LPAR)) {
                    PsiBuilder.Marker markerMark3 = mark();
                    this.myKotlinParsing.parseMultiDeclarationName(IN_KEYWORD_L_BRACE_SET, IN_KEYWORD_L_BRACE_RECOVERY_SET);
                    markerMark3.done(KtNodeTypes.DESTRUCTURING_DECLARATION);
                } else {
                    expect(KtTokens.IDENTIFIER, "Expecting a variable name", COLON_IN_KEYWORD_SET);
                    if (at(KtTokens.COLON)) {
                        advance();
                        this.myKotlinParsing.parseTypeRef(IN_KEYWORD_SET);
                    }
                }
                markerMark2.done(KtNodeTypes.VALUE_PARAMETER);
                if (expect(KtTokens.IN_KEYWORD, "Expecting 'in'", L_PAR_L_BRACE_R_PAR_SET)) {
                    PsiBuilder.Marker markerMark4 = mark();
                    parseExpression();
                    markerMark4.done(KtNodeTypes.LOOP_RANGE);
                }
            } else {
                error("Expecting a variable name");
            }
            expectNoAdvance(KtTokens.RPAR, "Expecting ')'");
            this.myBuilder.restoreNewlinesState();
        }
        parseLoopBody();
        markerMark.done(KtNodeTypes.FOR);
    }

    private void parseControlStructureBody() {
        if (parseAnnotatedLambda(true)) {
            return;
        }
        parseBlockLevelExpression();
    }

    private void parseLoopBody() {
        PsiBuilder.Marker markerMark = mark();
        if (!at(KtTokens.SEMICOLON)) {
            parseControlStructureBody();
        }
        markerMark.done(KtNodeTypes.BODY);
    }

    private void parseTry() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        this.myKotlinParsing.parseBlock();
        boolean z = false;
        while (at(KtTokens.CATCH_KEYWORD)) {
            PsiBuilder.Marker markerMark2 = mark();
            advance();
            TokenSet tokenSet = TRY_CATCH_RECOVERY_TOKEN_SET;
            if (atSet(tokenSet)) {
                error("Expecting exception variable declaration");
            } else {
                PsiBuilder.Marker markerMark3 = mark();
                expect(KtTokens.LPAR, "Expecting '('", tokenSet);
                if (!atSet(tokenSet)) {
                    this.myKotlinParsing.parseValueParameter(true);
                    if (at(KtTokens.COMMA)) {
                        advance();
                    }
                    expect(KtTokens.RPAR, "Expecting ')'", tokenSet);
                } else {
                    error("Expecting exception variable declaration");
                }
                markerMark3.done(KtNodeTypes.VALUE_PARAMETER_LIST);
            }
            if (at(KtTokens.LBRACE)) {
                this.myKotlinParsing.parseBlock();
            } else {
                error("Expecting a block: { ... }");
            }
            markerMark2.done(KtNodeTypes.CATCH);
            z = true;
        }
        if (at(KtTokens.FINALLY_KEYWORD)) {
            PsiBuilder.Marker markerMark4 = mark();
            advance();
            this.myKotlinParsing.parseBlock();
            markerMark4.done(KtNodeTypes.FINALLY);
            z = true;
        }
        if (!z) {
            error("Expecting 'catch' or 'finally'");
        }
        markerMark.done(KtNodeTypes.TRY);
    }

    private void parseIf() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        parseCondition();
        PsiBuilder.Marker markerMark2 = mark();
        if (!at(KtTokens.ELSE_KEYWORD) && !at(KtTokens.SEMICOLON)) {
            parseControlStructureBody();
        }
        if (at(KtTokens.SEMICOLON) && lookahead(1) == KtTokens.ELSE_KEYWORD) {
            advance();
        }
        markerMark2.done(KtNodeTypes.THEN);
        if (at(KtTokens.ELSE_KEYWORD) && lookahead(1) != KtTokens.ARROW) {
            advance();
            PsiBuilder.Marker markerMark3 = mark();
            if (!at(KtTokens.SEMICOLON)) {
                parseControlStructureBody();
            }
            markerMark3.done(KtNodeTypes.ELSE);
        }
        markerMark.done(KtNodeTypes.IF);
    }

    private void parseCondition() {
        this.myBuilder.disableNewlines();
        if (expect(KtTokens.LPAR, "Expecting a condition in parentheses '(...)'", EXPRESSION_FIRST)) {
            PsiBuilder.Marker markerMark = mark();
            parseExpression();
            markerMark.done(KtNodeTypes.CONDITION);
            expect(KtTokens.RPAR, "Expecting ')");
        }
        this.myBuilder.restoreNewlinesState();
    }

    private void parseJump(IElementType iElementType) {
        PsiBuilder.Marker markerMark = mark();
        advance();
        parseLabelReferenceWithNoWhitespace();
        markerMark.done(iElementType);
    }

    private void parseReturn() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        parseLabelReferenceWithNoWhitespace();
        if (atSet(EXPRESSION_FIRST) && !at(KtTokens.EOL_OR_SEMICOLON)) {
            parseExpression();
        }
        markerMark.done(KtNodeTypes.RETURN);
    }

    private void parseLabelReferenceWithNoWhitespace() {
        if (!at(KtTokens.AT) || this.myBuilder.newlineBeforeCurrentToken()) {
            return;
        }
        if (KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(this.myBuilder.rawLookup(-1))) {
            error("There should be no space or comments before '@' in label reference");
        }
        parseLabelReference();
    }

    void parseLabelDefinition() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        if (at(KtTokens.AT)) {
            errorAndAdvance("Expecting identifier before '@' in label definition");
            markerMark.drop();
            markerMark2.drop();
        } else {
            advance();
            advance();
            markerMark2.done(KtNodeTypes.LABEL);
            markerMark.done(KtNodeTypes.LABEL_QUALIFIER);
        }
    }

    private void parseLabelReference() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        if (this.myBuilder.rawLookup(1) != KtTokens.IDENTIFIER) {
            errorAndAdvance("Label must be named");
            markerMark.drop();
            markerMark2.drop();
        } else {
            advance();
            advance();
            markerMark2.done(KtNodeTypes.LABEL);
            markerMark.done(KtNodeTypes.LABEL_QUALIFIER);
        }
    }

    private void parseThrow() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        parseExpression();
        markerMark.done(KtNodeTypes.THROW);
    }

    private void parseParenthesizedExpression() {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        advance();
        if (at(KtTokens.RPAR)) {
            error("Expecting an expression");
        } else {
            parseExpression();
        }
        expect(KtTokens.RPAR, "Expecting ')'");
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.PARENTHESIZED);
    }

    private void parseThisExpression() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        advance();
        markerMark2.done(KtNodeTypes.REFERENCE_EXPRESSION);
        parseLabelReferenceWithNoWhitespace();
        markerMark.done(KtNodeTypes.THIS_EXPRESSION);
    }

    private void parseSuperExpression() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        advance();
        markerMark2.done(KtNodeTypes.REFERENCE_EXPRESSION);
        if (at(KtTokens.LT)) {
            PsiBuilder.Marker markerMark3 = mark();
            this.myBuilder.disableNewlines();
            advance();
            this.myKotlinParsing.parseTypeRef();
            if (at(KtTokens.GT)) {
                advance();
                markerMark3.drop();
            } else {
                markerMark3.rollbackTo();
            }
            this.myBuilder.restoreNewlinesState();
        }
        parseLabelReferenceWithNoWhitespace();
        markerMark.done(KtNodeTypes.SUPER_EXPRESSION);
    }

    public void parseValueArgumentList() {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        if (expect(KtTokens.LPAR, "Expecting an argument list", EXPRESSION_FOLLOW)) {
            if (!at(KtTokens.RPAR)) {
                while (true) {
                    if (at(KtTokens.COMMA)) {
                        errorAndAdvance("Expecting an argument");
                    } else {
                        parseValueArgument();
                        if (at(KtTokens.COLON) && lookahead(1) == KtTokens.IDENTIFIER) {
                            errorAndAdvance("Unexpected type specification", 2);
                        }
                        if (!at(KtTokens.COMMA)) {
                            if (!atSet(EXPRESSION_FIRST)) {
                                break;
                            } else {
                                error("Expecting ','");
                            }
                        } else {
                            advance();
                            if (at(KtTokens.RPAR)) {
                                break;
                            }
                        }
                    }
                }
            }
            expect(KtTokens.RPAR, "Expecting ')'", EXPRESSION_FOLLOW);
        }
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.VALUE_ARGUMENT_LIST);
    }

    private void parseValueArgument() {
        PsiBuilder.Marker markerMark = mark();
        if (at(KtTokens.IDENTIFIER) && lookahead(1) == KtTokens.EQ) {
            PsiBuilder.Marker markerMark2 = mark();
            PsiBuilder.Marker markerMark3 = mark();
            advance();
            markerMark3.done(KtNodeTypes.REFERENCE_EXPRESSION);
            markerMark2.done(KtNodeTypes.VALUE_ARGUMENT_NAME);
            advance();
        }
        if (at(KtTokens.MUL)) {
            advance();
        }
        parseExpression();
        markerMark.done(KtNodeTypes.VALUE_ARGUMENT);
    }

    public void parseObjectLiteral() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        this.myKotlinParsing.parseObject(KotlinParsing.NameParsingMode.PROHIBITED, false);
        markerMark2.done(KtNodeTypes.OBJECT_DECLARATION);
        markerMark.done(KtNodeTypes.OBJECT_LITERAL);
    }

    private void parseOneTokenExpression(IElementType iElementType) {
        PsiBuilder.Marker markerMark = mark();
        advance();
        markerMark.done(iElementType);
    }

    @Override // org.jetbrains.kotlin.parsing.AbstractKotlinParsing
    protected KotlinParsing create(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        return this.myKotlinParsing.create(semanticWhitespaceAwarePsiBuilder);
    }

    private boolean interruptedWithNewLine() {
        return !ALLOW_NEWLINE_OPERATIONS.contains(tt()) && this.myBuilder.newlineBeforeCurrentToken();
    }
}
