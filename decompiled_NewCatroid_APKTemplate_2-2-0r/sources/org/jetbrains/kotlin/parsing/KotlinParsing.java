package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinParsing extends AbstractKotlinParsing {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet ACCESSOR_FIRST_OR_PROPERTY_END;
    private static final TokenSet ANNOTATION_TARGETS;
    private static final TokenSet BLOCK_DOC_COMMENT_SET;
    private static final TokenSet CLASS_INTERFACE_SET;
    private static final TokenSet CLASS_NAME_RECOVERY_SET;
    private static final TokenSet COLON_COMMA_LBRACE_RBRACE_TYPE_REF_FIRST_SET;
    private static final TokenSet COMMA_COLON_GT_SET;
    private static final TokenSet COMMA_COLON_RPAR_SET;
    private static final TokenSet COMMA_RPAR_COLON_EQ_SET;
    private static final TokenSet COMMA_SEMICOLON_RBRACE_SET;
    private static final TokenSet DECLARATION_FIRST;
    private static final TokenSet EOL_OR_SEMICOLON_RBRACE_SET;
    private static final TokenSet FUNCTION_NAME_FOLLOW_SET;
    private static final TokenSet FUNCTION_NAME_RECOVERY_SET;
    private static final TokenSet IDENTIFIER_EQ_COLON_SEMICOLON_SET;
    private static final TokenSet IDENTIFIER_RBRACKET_LBRACKET_SET;
    private static final TokenSet IMPORT_RECOVERY_SET;
    private static final TokenSet LAMBDA_VALUE_PARAMETER_FIRST;
    private static final TokenSet LBRACE_RBRACE_SET;
    private static final TokenSet LBRACE_RBRACE_TYPE_REF_FIRST_SET;
    private static final TokenSet LBRACKET_LBRACE_RBRACE_LPAR_SET;
    private static final TokenSet LPAR_LBRACE_COLON_CONSTRUCTOR_KEYWORD_SET;
    private static final TokenSet LPAR_VALUE_PARAMETERS_FOLLOW_SET;
    private static final TokenSet LT_EQ_SEMICOLON_TOP_LEVEL_DECLARATION_FIRST_SET;
    private static final TokenSet NO_MODIFIER_BEFORE_FOR_VALUE_PARAMETER;
    private static final TokenSet PACKAGE_NAME_RECOVERY_SET;
    public static final TokenSet PARAMETER_NAME_RECOVERY_SET;
    private static final TokenSet PROPERTY_NAME_FOLLOW_FUNCTION_OR_PROPERTY_RECOVERY_SET;
    private static final TokenSet PROPERTY_NAME_FOLLOW_MULTI_DECLARATION_RECOVERY_SET;
    private static final TokenSet PROPERTY_NAME_FOLLOW_SET;
    private static final TokenSet RECEIVER_TYPE_TERMINATORS;
    private static final TokenSet RPAR_COLON_LBRACE_EQ_SET;
    private static final TokenSet RPAR_IDENTIFIER_COLON_LBRACE_EQ_SET;
    private static final TokenSet SEMICOLON_SET;
    private static final TokenSet SOFT_KEYWORDS_AT_MEMBER_START;
    private static final TokenSet TOP_LEVEL_DECLARATION_FIRST;
    private static final TokenSet TOP_LEVEL_DECLARATION_FIRST_SEMICOLON_SET;
    private static final TokenSet TYPE_PARAMETER_GT_RECOVERY_SET;
    private static final TokenSet TYPE_REF_FIRST;
    private static final TokenSet VALUE_ARGS_RECOVERY_SET;
    private static final TokenSet VALUE_PARAMETERS_FOLLOW_SET;
    private static final TokenSet VALUE_PARAMETER_FIRST;
    private static final TokenSet definitelyOutOfReceiverSet;
    private final FirstBefore lastDotAfterReceiverLParPattern;
    private final LastBefore lastDotAfterReceiverNotLParPattern;
    private final KotlinExpressionParsing myExpressionParsing;
    private static final TokenSet GT_COMMA_COLON_SET = TokenSet.create(KtTokens.GT, KtTokens.COMMA, KtTokens.COLON);
    private static final Logger LOG = Logger.getInstance((Class<?>) KotlinParsing.class);

    public enum NameParsingMode {
        REQUIRED,
        ALLOWED,
        PROHIBITED
    }

    private enum ParseEnumEntryResult {
        FAILED,
        NO_DELIMITER,
        COMMA_DELIMITER,
        SEMICOLON_DELIMITER
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 15 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 15 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nameParsingModeForObject";
                break;
            case 2:
                objArr[0] = "declarationParsingMode";
                break;
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 12:
                objArr[0] = "noModifiersBefore";
                break;
            case 6:
            case 9:
            case 13:
                objArr[0] = "modifierKeywords";
                break;
            case 7:
            case 10:
                objArr[0] = "annotationParsingMode";
                break;
            case 14:
                objArr[0] = "modifierDetector";
                break;
            case 15:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/parsing/KotlinParsing";
                break;
            case 16:
                objArr[0] = "typeElementMarker";
                break;
            default:
                objArr[0] = "detector";
                break;
        }
        if (i == 15) {
            objArr[1] = "parseFunction";
        } else if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/parsing/KotlinParsing";
        } else {
            objArr[1] = "parseNullableTypeSuffix";
        }
        switch (i) {
            case 3:
            case 5:
                objArr[2] = "parseModifierList";
                break;
            case 4:
                objArr[2] = "parseAnnotationsList";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "doParseModifierListBody";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "doParseModifierList";
                break;
            case 12:
            case 13:
                objArr[2] = "tryParseModifier";
                break;
            case 14:
                objArr[2] = "parseMemberDeclarationRest";
                break;
            case 15:
            case 17:
                break;
            case 16:
                objArr[2] = "parseNullableTypeSuffix";
                break;
            default:
                objArr[2] = "parseCommonDeclaration";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 15 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.parsing.AbstractKotlinParsing
    public /* bridge */ /* synthetic */ String currentContext() {
        return super.currentContext();
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.TYPE_ALIAS_KEYWORD, KtTokens.INTERFACE_KEYWORD, KtTokens.CLASS_KEYWORD, KtTokens.OBJECT_KEYWORD, KtTokens.FUN_KEYWORD, KtTokens.VAL_KEYWORD, KtTokens.PACKAGE_KEYWORD);
        TOP_LEVEL_DECLARATION_FIRST = tokenSetCreate;
        TOP_LEVEL_DECLARATION_FIRST_SEMICOLON_SET = TokenSet.orSet(tokenSetCreate, TokenSet.create(KtTokens.SEMICOLON));
        LT_EQ_SEMICOLON_TOP_LEVEL_DECLARATION_FIRST_SET = TokenSet.orSet(TokenSet.create(KtTokens.LT, KtTokens.EQ, KtTokens.SEMICOLON), tokenSetCreate);
        DECLARATION_FIRST = TokenSet.orSet(tokenSetCreate, TokenSet.create(KtTokens.INIT_KEYWORD, KtTokens.GET_KEYWORD, KtTokens.SET_KEYWORD, KtTokens.CONSTRUCTOR_KEYWORD));
        CLASS_NAME_RECOVERY_SET = TokenSet.orSet(TokenSet.create(KtTokens.LT, KtTokens.LPAR, KtTokens.COLON, KtTokens.LBRACE), tokenSetCreate);
        TYPE_PARAMETER_GT_RECOVERY_SET = TokenSet.create(KtTokens.WHERE_KEYWORD, KtTokens.LPAR, KtTokens.COLON, KtTokens.LBRACE, KtTokens.GT);
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.COLON, KtTokens.EQ, KtTokens.COMMA, KtTokens.RPAR, KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);
        PARAMETER_NAME_RECOVERY_SET = tokenSetCreate2;
        PACKAGE_NAME_RECOVERY_SET = TokenSet.create(KtTokens.DOT, KtTokens.EOL_OR_SEMICOLON);
        IMPORT_RECOVERY_SET = TokenSet.create(KtTokens.AS_KEYWORD, KtTokens.DOT, KtTokens.EOL_OR_SEMICOLON);
        TokenSet tokenSetCreate3 = TokenSet.create(KtTokens.LBRACKET, KtTokens.IDENTIFIER, KtTokens.LPAR, KtTokens.HASH, KtTokens.DYNAMIC_KEYWORD);
        TYPE_REF_FIRST = tokenSetCreate3;
        LBRACE_RBRACE_TYPE_REF_FIRST_SET = TokenSet.orSet(TokenSet.create(KtTokens.LBRACE, KtTokens.RBRACE), tokenSetCreate3);
        COLON_COMMA_LBRACE_RBRACE_TYPE_REF_FIRST_SET = TokenSet.orSet(TokenSet.create(KtTokens.COLON, KtTokens.COMMA, KtTokens.LBRACE, KtTokens.RBRACE), tokenSetCreate3);
        RECEIVER_TYPE_TERMINATORS = TokenSet.create(KtTokens.DOT, KtTokens.SAFE_ACCESS);
        VALUE_PARAMETER_FIRST = TokenSet.orSet(TokenSet.create(KtTokens.IDENTIFIER, KtTokens.LBRACKET, KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD), TokenSet.andNot(KtTokens.MODIFIER_KEYWORDS, TokenSet.create(KtTokens.FUN_KEYWORD)));
        LAMBDA_VALUE_PARAMETER_FIRST = TokenSet.orSet(TokenSet.create(KtTokens.IDENTIFIER, KtTokens.LBRACKET), TokenSet.andNot(KtTokens.MODIFIER_KEYWORDS, TokenSet.create(KtTokens.FUN_KEYWORD)));
        SOFT_KEYWORDS_AT_MEMBER_START = TokenSet.create(KtTokens.CONSTRUCTOR_KEYWORD, KtTokens.INIT_KEYWORD);
        ANNOTATION_TARGETS = TokenSet.create(KtTokens.FILE_KEYWORD, KtTokens.FIELD_KEYWORD, KtTokens.GET_KEYWORD, KtTokens.SET_KEYWORD, KtTokens.PROPERTY_KEYWORD, KtTokens.RECEIVER_KEYWORD, KtTokens.PARAM_KEYWORD, KtTokens.SETPARAM_KEYWORD, KtTokens.DELEGATE_KEYWORD);
        BLOCK_DOC_COMMENT_SET = TokenSet.create(KtTokens.BLOCK_COMMENT, KtTokens.DOC_COMMENT);
        SEMICOLON_SET = TokenSet.create(KtTokens.SEMICOLON);
        COMMA_COLON_GT_SET = TokenSet.create(KtTokens.COMMA, KtTokens.COLON, KtTokens.GT);
        IDENTIFIER_RBRACKET_LBRACKET_SET = TokenSet.create(KtTokens.IDENTIFIER, KtTokens.RBRACKET, KtTokens.LBRACKET);
        TokenSet tokenSetCreate4 = TokenSet.create(KtTokens.LBRACE, KtTokens.RBRACE);
        LBRACE_RBRACE_SET = tokenSetCreate4;
        COMMA_SEMICOLON_RBRACE_SET = TokenSet.create(KtTokens.COMMA, KtTokens.SEMICOLON, KtTokens.RBRACE);
        VALUE_ARGS_RECOVERY_SET = TokenSet.create(KtTokens.LBRACE, KtTokens.SEMICOLON, KtTokens.RPAR, KtTokens.EOL_OR_SEMICOLON, KtTokens.RBRACE);
        TokenSet tokenSetCreate5 = TokenSet.create(KtTokens.COLON, KtTokens.EQ, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.SEMICOLON, KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD, KtTokens.FUN_KEYWORD, KtTokens.CLASS_KEYWORD);
        PROPERTY_NAME_FOLLOW_SET = tokenSetCreate5;
        PROPERTY_NAME_FOLLOW_MULTI_DECLARATION_RECOVERY_SET = TokenSet.orSet(tokenSetCreate5, tokenSetCreate2);
        PROPERTY_NAME_FOLLOW_FUNCTION_OR_PROPERTY_RECOVERY_SET = TokenSet.orSet(tokenSetCreate5, tokenSetCreate4, tokenSetCreate);
        IDENTIFIER_EQ_COLON_SEMICOLON_SET = TokenSet.create(KtTokens.IDENTIFIER, KtTokens.EQ, KtTokens.COLON, KtTokens.SEMICOLON);
        COMMA_RPAR_COLON_EQ_SET = TokenSet.create(KtTokens.COMMA, KtTokens.RPAR, KtTokens.COLON, KtTokens.EQ);
        ACCESSOR_FIRST_OR_PROPERTY_END = TokenSet.orSet(KtTokens.MODIFIER_KEYWORDS, TokenSet.create(KtTokens.AT, KtTokens.GET_KEYWORD, KtTokens.SET_KEYWORD, KtTokens.FIELD_KEYWORD, KtTokens.EOL_OR_SEMICOLON, KtTokens.RBRACE));
        RPAR_IDENTIFIER_COLON_LBRACE_EQ_SET = TokenSet.create(KtTokens.RPAR, KtTokens.IDENTIFIER, KtTokens.COLON, KtTokens.LBRACE, KtTokens.EQ);
        COMMA_COLON_RPAR_SET = TokenSet.create(KtTokens.COMMA, KtTokens.COLON, KtTokens.RPAR);
        RPAR_COLON_LBRACE_EQ_SET = TokenSet.create(KtTokens.RPAR, KtTokens.COLON, KtTokens.LBRACE, KtTokens.EQ);
        LBRACKET_LBRACE_RBRACE_LPAR_SET = TokenSet.create(KtTokens.LBRACKET, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.LPAR);
        FUNCTION_NAME_FOLLOW_SET = TokenSet.create(KtTokens.LT, KtTokens.LPAR, KtTokens.RPAR, KtTokens.COLON, KtTokens.EQ);
        FUNCTION_NAME_RECOVERY_SET = TokenSet.orSet(TokenSet.create(KtTokens.LT, KtTokens.LPAR, KtTokens.RPAR, KtTokens.COLON, KtTokens.EQ), tokenSetCreate4, tokenSetCreate);
        TokenSet tokenSetCreate6 = TokenSet.create(KtTokens.EQ, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.SEMICOLON, KtTokens.RPAR);
        VALUE_PARAMETERS_FOLLOW_SET = tokenSetCreate6;
        LPAR_VALUE_PARAMETERS_FOLLOW_SET = TokenSet.orSet(TokenSet.create(KtTokens.LPAR), tokenSetCreate6);
        LPAR_LBRACE_COLON_CONSTRUCTOR_KEYWORD_SET = TokenSet.create(KtTokens.LPAR, KtTokens.LBRACE, KtTokens.COLON, KtTokens.CONSTRUCTOR_KEYWORD);
        definitelyOutOfReceiverSet = TokenSet.orSet(TokenSet.create(KtTokens.EQ, KtTokens.COLON, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.BY_KEYWORD), tokenSetCreate);
        EOL_OR_SEMICOLON_RBRACE_SET = TokenSet.create(KtTokens.EOL_OR_SEMICOLON, KtTokens.RBRACE);
        CLASS_INTERFACE_SET = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD);
        NO_MODIFIER_BEFORE_FOR_VALUE_PARAMETER = TokenSet.create(KtTokens.COMMA, KtTokens.COLON, KtTokens.EQ, KtTokens.RPAR);
    }

    static KotlinParsing createForTopLevel(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        return new KotlinParsing(semanticWhitespaceAwarePsiBuilder, true, true);
    }

    static KotlinParsing createForTopLevelNonLazy(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        return new KotlinParsing(semanticWhitespaceAwarePsiBuilder, true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static KotlinParsing createForByClause(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, boolean z) {
        return new KotlinParsing(new SemanticWhitespaceAwarePsiBuilderForByClause(semanticWhitespaceAwarePsiBuilder), false, z);
    }

    private KotlinParsing(final SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, boolean z, boolean z2) {
        super(semanticWhitespaceAwarePsiBuilder, z2);
        TokenSet tokenSet = RECEIVER_TYPE_TERMINATORS;
        this.lastDotAfterReceiverLParPattern = new FirstBefore(new AbstractKotlinParsing.AtSet(this, tokenSet), new AbstractTokenStreamPredicate() { // from class: org.jetbrains.kotlin.parsing.KotlinParsing.1
            @Override // org.jetbrains.kotlin.parsing.TokenStreamPredicate
            public boolean matching(boolean z3) {
                if (z3 && KotlinParsing.this.atSet(KotlinParsing.definitelyOutOfReceiverSet)) {
                    return true;
                }
                return (!z3 || KotlinParsing.this.at(KtTokens.QUEST) || KotlinParsing.this.at(KtTokens.LPAR) || KotlinParsing.this.at(KtTokens.RPAR)) ? false : true;
            }
        });
        this.lastDotAfterReceiverNotLParPattern = new LastBefore(new AbstractKotlinParsing.AtSet(this, tokenSet), new AbstractTokenStreamPredicate() { // from class: org.jetbrains.kotlin.parsing.KotlinParsing.2
            @Override // org.jetbrains.kotlin.parsing.TokenStreamPredicate
            public boolean matching(boolean z3) {
                if (z3 && (KotlinParsing.this.atSet(KotlinParsing.definitelyOutOfReceiverSet) || KotlinParsing.this.at(KtTokens.LPAR))) {
                    return true;
                }
                if (!z3 || !KotlinParsing.this.at(KtTokens.IDENTIFIER)) {
                    return false;
                }
                IElementType iElementTypeLookahead = KotlinParsing.this.lookahead(1);
                return (iElementTypeLookahead == KtTokens.LT || iElementTypeLookahead == KtTokens.DOT || iElementTypeLookahead == KtTokens.SAFE_ACCESS || iElementTypeLookahead == KtTokens.QUEST) ? false : true;
            }
        });
        this.myExpressionParsing = z ? new KotlinExpressionParsing(semanticWhitespaceAwarePsiBuilder, this, z2) : new KotlinExpressionParsing(semanticWhitespaceAwarePsiBuilder, this, z2) { // from class: org.jetbrains.kotlin.parsing.KotlinParsing.3
            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing
            protected boolean parseCallWithClosure() {
                if (((SemanticWhitespaceAwarePsiBuilderForByClause) semanticWhitespaceAwarePsiBuilder).getStackSize() > 0) {
                    return super.parseCallWithClosure();
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.parsing.KotlinExpressionParsing, org.jetbrains.kotlin.parsing.AbstractKotlinParsing
            protected KotlinParsing create(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder2) {
                return KotlinParsing.createForByClause(semanticWhitespaceAwarePsiBuilder2, this.isLazy);
            }
        };
    }

    void parseFile() {
        PsiBuilder.Marker markerMark = mark();
        parsePreamble();
        while (!eof()) {
            parseTopLevelDeclaration();
        }
        checkUnclosedBlockComment();
        markerMark.done(KtNodeTypes.KT_FILE);
    }

    private void checkUnclosedBlockComment() {
        if (BLOCK_DOC_COMMENT_SET.contains(this.myBuilder.rawLookup(-1))) {
            CharSequence charSequenceSubSequence = this.myBuilder.getOriginalText().subSequence(this.myBuilder.rawTokenTypeStart(-1), this.myBuilder.rawTokenTypeStart(0));
            if (charSequenceSubSequence.length() <= 2 || !charSequenceSubSequence.subSequence(charSequenceSubSequence.length() - 2, charSequenceSubSequence.length()).toString().equals("*/")) {
                PsiBuilder.Marker markerMark = this.myBuilder.m6463mark();
                markerMark.error("Unclosed comment");
                markerMark.setCustomEdgeTokenBinders(WhitespacesBinders.GREEDY_RIGHT_BINDER, null);
            }
        }
    }

    void parseTypeCodeFragment() {
        PsiBuilder.Marker markerMark = mark();
        parseTypeRef();
        checkForUnexpectedSymbols();
        markerMark.done(KtNodeTypes.TYPE_CODE_FRAGMENT);
    }

    void parseExpressionCodeFragment() {
        PsiBuilder.Marker markerMark = mark();
        this.myExpressionParsing.parseExpression();
        checkForUnexpectedSymbols();
        markerMark.done(KtNodeTypes.EXPRESSION_CODE_FRAGMENT);
    }

    void parseBlockCodeFragment() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        if (at(KtTokens.PACKAGE_KEYWORD) || at(KtTokens.IMPORT_KEYWORD)) {
            PsiBuilder.Marker markerMark3 = mark();
            parsePreamble();
            markerMark3.error("Package directive and imports are forbidden in code fragments");
        }
        this.myExpressionParsing.parseStatements();
        checkForUnexpectedSymbols();
        markerMark2.done(KtNodeTypes.BLOCK);
        markerMark.done(KtNodeTypes.BLOCK_CODE_FRAGMENT);
    }

    void parseLambdaExpression() {
        this.myExpressionParsing.parseFunctionLiteral(false, false);
    }

    void parseBlockExpression() {
        parseBlock(false);
    }

    void parseScript() {
        PsiBuilder.Marker markerMark = mark();
        parsePreamble();
        PsiBuilder.Marker markerMark2 = mark();
        PsiBuilder.Marker markerMark3 = mark();
        this.myExpressionParsing.parseStatements(true);
        checkForUnexpectedSymbols();
        markerMark3.done(KtNodeTypes.BLOCK);
        markerMark3.setCustomEdgeTokenBinders(KotlinWhitespaceAndCommentsBindersKt.PRECEDING_ALL_BINDER, KotlinWhitespaceAndCommentsBindersKt.TRAILING_ALL_BINDER);
        markerMark2.done(KtNodeTypes.SCRIPT);
        markerMark2.setCustomEdgeTokenBinders(KotlinWhitespaceAndCommentsBindersKt.PRECEDING_ALL_BINDER, KotlinWhitespaceAndCommentsBindersKt.TRAILING_ALL_BINDER);
        markerMark.done(KtNodeTypes.KT_FILE);
    }

    private void checkForUnexpectedSymbols() {
        while (!eof()) {
            errorAndAdvance("Unexpected symbol");
        }
    }

    private void parsePreamble() {
        PsiBuilder.Marker markerMark = mark();
        parseFileAnnotationList(AnnotationParsingMode.FILE_ANNOTATIONS_BEFORE_PACKAGE);
        PsiBuilder.Marker markerMark2 = mark();
        parseModifierList(TokenSet.EMPTY);
        if (at(KtTokens.PACKAGE_KEYWORD)) {
            advance();
            parsePackageName();
            markerMark.drop();
            consumeIf(KtTokens.SEMICOLON);
            markerMark2.done(KtNodeTypes.PACKAGE_DIRECTIVE);
        } else {
            markerMark.rollbackTo();
            parseFileAnnotationList(AnnotationParsingMode.FILE_ANNOTATIONS_WHEN_PACKAGE_OMITTED);
            PsiBuilder.Marker markerMark3 = mark();
            markerMark3.done(KtNodeTypes.PACKAGE_DIRECTIVE);
            markerMark3.setCustomEdgeTokenBinders(BindFirstShebangWithWhitespaceOnly.INSTANCE, null);
        }
        parseImportDirectives();
    }

    private void parsePackageName() {
        PsiBuilder.Marker markerMark = mark();
        boolean z = true;
        while (!this.myBuilder.newlineBeforeCurrentToken()) {
            if (at(KtTokens.DOT)) {
                advance();
                markerMark.error("Package name must be a '.'-separated identifier list");
                markerMark = mark();
            } else {
                PsiBuilder.Marker markerMark2 = mark();
                boolean zExpect = expect(KtTokens.IDENTIFIER, "Package name must be a '.'-separated identifier list", PACKAGE_NAME_RECOVERY_SET);
                if (zExpect) {
                    markerMark2.done(KtNodeTypes.REFERENCE_EXPRESSION);
                } else {
                    markerMark2.drop();
                }
                if (!z) {
                    PsiBuilder.Marker markerM6464precede = markerMark.m6464precede();
                    markerMark.done(KtNodeTypes.DOT_QUALIFIED_EXPRESSION);
                    markerMark = markerM6464precede;
                }
                if (at(KtTokens.DOT)) {
                    advance();
                    if (!z || zExpect) {
                        z = false;
                    } else {
                        markerMark.drop();
                        markerMark = mark();
                    }
                } else {
                    markerMark.drop();
                }
            }
        }
        errorWithRecovery("Package name must be a '.'-separated identifier list placed on a single line", PACKAGE_NAME_RECOVERY_SET);
        markerMark.drop();
    }

    private void parseImportDirective() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        if (closeImportWithErrorIfNewline(markerMark, null, "Expecting qualified name")) {
            return;
        }
        if (!at(KtTokens.IDENTIFIER)) {
            PsiBuilder.Marker markerMark2 = mark();
            skipUntil(TokenSet.create(KtTokens.EOL_OR_SEMICOLON));
            markerMark2.error("Expecting qualified name");
            markerMark.done(KtNodeTypes.IMPORT_DIRECTIVE);
            consumeIf(KtTokens.SEMICOLON);
            return;
        }
        PsiBuilder.Marker markerMark3 = mark();
        PsiBuilder.Marker markerMark4 = mark();
        advance();
        markerMark4.done(KtNodeTypes.REFERENCE_EXPRESSION);
        while (at(KtTokens.DOT) && lookahead(1) != KtTokens.MUL) {
            advance();
            if (closeImportWithErrorIfNewline(markerMark, null, "Import must be placed on a single line")) {
                markerMark3.drop();
                return;
            }
            PsiBuilder.Marker markerMark5 = mark();
            if (expect(KtTokens.IDENTIFIER, "Qualified name must be a '.'-separated identifier list", IMPORT_RECOVERY_SET)) {
                markerMark5.done(KtNodeTypes.REFERENCE_EXPRESSION);
            } else {
                markerMark5.drop();
            }
            PsiBuilder.Marker markerM6464precede = markerMark3.m6464precede();
            markerMark3.done(KtNodeTypes.DOT_QUALIFIED_EXPRESSION);
            markerMark3 = markerM6464precede;
        }
        markerMark3.drop();
        if (at(KtTokens.DOT)) {
            advance();
            advance();
            if (at(KtTokens.AS_KEYWORD)) {
                PsiBuilder.Marker markerMark6 = mark();
                advance();
                if (closeImportWithErrorIfNewline(markerMark, null, "Expecting identifier")) {
                    markerMark6.drop();
                    return;
                } else {
                    consumeIf(KtTokens.IDENTIFIER);
                    markerMark6.done(KtNodeTypes.IMPORT_ALIAS);
                    markerMark6.m6464precede().error("Cannot rename all imported items to one identifier");
                }
            }
        }
        if (at(KtTokens.AS_KEYWORD)) {
            PsiBuilder.Marker markerMark7 = mark();
            advance();
            if (closeImportWithErrorIfNewline(markerMark, markerMark7, "Expecting identifier")) {
                return;
            }
            expect(KtTokens.IDENTIFIER, "Expecting identifier", SEMICOLON_SET);
            markerMark7.done(KtNodeTypes.IMPORT_ALIAS);
        }
        consumeIf(KtTokens.SEMICOLON);
        markerMark.done(KtNodeTypes.IMPORT_DIRECTIVE);
        markerMark.setCustomEdgeTokenBinders(null, TrailingCommentsBinder.INSTANCE);
    }

    private boolean closeImportWithErrorIfNewline(PsiBuilder.Marker marker, PsiBuilder.Marker marker2, String str) {
        if (!this.myBuilder.newlineBeforeCurrentToken()) {
            return false;
        }
        if (marker2 != null) {
            marker2.done(KtNodeTypes.IMPORT_ALIAS);
        }
        error(str);
        marker.done(KtNodeTypes.IMPORT_DIRECTIVE);
        return true;
    }

    private void parseImportDirectives() {
        PsiBuilder.Marker markerMark = mark();
        if (!at(KtTokens.IMPORT_KEYWORD)) {
            markerMark.setCustomEdgeTokenBinders(DoNotBindAnything.INSTANCE, null);
        }
        while (at(KtTokens.IMPORT_KEYWORD)) {
            parseImportDirective();
        }
        markerMark.done(KtNodeTypes.IMPORT_LIST);
    }

    private void parseTopLevelDeclaration() {
        if (at(KtTokens.SEMICOLON)) {
            advance();
            return;
        }
        PsiBuilder.Marker markerMark = mark();
        if (at(KtTokens.CONTEXT_KEYWORD)) {
            parseContextReceiverList();
        }
        ModifierDetector modifierDetector = new ModifierDetector();
        parseModifierList(modifierDetector, TokenSet.EMPTY);
        IElementType commonDeclaration = parseCommonDeclaration(modifierDetector, NameParsingMode.REQUIRED, DeclarationParsingMode.MEMBER_OR_TOPLEVEL);
        if (commonDeclaration == null && at(KtTokens.LBRACE)) {
            error("Expecting a top level declaration");
            parseBlock();
            commonDeclaration = KtNodeTypes.FUN;
        }
        if (commonDeclaration == null && at(KtTokens.IMPORT_KEYWORD)) {
            error("imports are only allowed in the beginning of file");
            parseImportDirectives();
            markerMark.drop();
        } else if (commonDeclaration == null) {
            errorAndAdvance("Expecting a top level declaration");
            markerMark.drop();
        } else {
            closeDeclarationWithCommentBinders(markerMark, commonDeclaration, true);
        }
    }

    public IElementType parseCommonDeclaration(ModifierDetector modifierDetector, NameParsingMode nameParsingMode, DeclarationParsingMode declarationParsingMode) {
        if (modifierDetector == null) {
            $$$reportNull$$$0(0);
        }
        if (nameParsingMode == null) {
            $$$reportNull$$$0(1);
        }
        if (declarationParsingMode == null) {
            $$$reportNull$$$0(2);
        }
        int tokenId = getTokenId();
        if (tokenId != 19) {
            if (tokenId != 20) {
                if (tokenId == 36) {
                    parseObject(nameParsingMode, true);
                    return KtNodeTypes.OBJECT_DECLARATION;
                }
                if (tokenId != 43) {
                    if (tokenId != 46) {
                        switch (tokenId) {
                            case 23:
                            case 24:
                                return parseProperty(declarationParsingMode);
                            case 25:
                                return parseFunction();
                            default:
                                return null;
                        }
                    }
                    if (modifierDetector.isEnumDetected() && declarationParsingMode.canBeEnumUsedAsSoftKeyword) {
                        return parseClass(true, false);
                    }
                    return null;
                }
            }
            return parseClass(modifierDetector.isEnumDetected(), true);
        }
        return parseTypeAlias();
    }

    boolean parseModifierList(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(3);
        }
        return parseModifierList(null, tokenSet);
    }

    void parseAnnotationsList(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(4);
        }
        doParseModifierList(null, TokenSet.EMPTY, AnnotationParsingMode.DEFAULT, tokenSet);
    }

    boolean parseModifierList(Consumer<IElementType> consumer, TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(5);
        }
        return doParseModifierList(consumer, KtTokens.MODIFIER_KEYWORDS, AnnotationParsingMode.DEFAULT, tokenSet);
    }

    private void parseFunctionTypeValueParameterModifierList() {
        doParseModifierList(null, KtTokens.RESERVED_VALUE_PARAMETER_MODIFIER_KEYWORDS, AnnotationParsingMode.NO_ANNOTATIONS, NO_MODIFIER_BEFORE_FOR_VALUE_PARAMETER);
    }

    private void parseTypeModifierList() {
        doParseModifierList(null, KtTokens.TYPE_MODIFIER_KEYWORDS, AnnotationParsingMode.TYPE_CONTEXT, TokenSet.EMPTY);
    }

    private void parseTypeArgumentModifierList() {
        doParseModifierList(null, KtTokens.TYPE_ARGUMENT_MODIFIER_KEYWORDS, AnnotationParsingMode.NO_ANNOTATIONS, COMMA_COLON_GT_SET);
    }

    private boolean doParseModifierListBody(Consumer<IElementType> consumer, TokenSet tokenSet, AnnotationParsingMode annotationParsingMode, TokenSet tokenSet2) {
        if (tokenSet == null) {
            $$$reportNull$$$0(6);
        }
        if (annotationParsingMode == null) {
            $$$reportNull$$$0(7);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(8);
        }
        boolean z = true;
        while (!eof()) {
            if (at(KtTokens.AT) && annotationParsingMode.allowAnnotations) {
                PsiBuilder.Marker markerMark = mark();
                if (!parseAnnotationOrList(annotationParsingMode) && !annotationParsingMode.withSignificantWhitespaceBeforeArguments) {
                    markerMark.rollbackTo();
                    doParseModifierListBody(consumer, tokenSet, AnnotationParsingMode.WITH_SIGNIFICANT_WHITESPACE_BEFORE_ARGUMENTS, tokenSet2);
                    return false;
                }
                markerMark.drop();
            } else if (!tryParseModifier(consumer, tokenSet2, tokenSet)) {
                return z;
            }
            z = false;
        }
        return z;
    }

    private boolean doParseModifierList(Consumer<IElementType> consumer, TokenSet tokenSet, AnnotationParsingMode annotationParsingMode, TokenSet tokenSet2) {
        if (tokenSet == null) {
            $$$reportNull$$$0(9);
        }
        if (annotationParsingMode == null) {
            $$$reportNull$$$0(10);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(11);
        }
        PsiBuilder.Marker markerMark = mark();
        boolean zDoParseModifierListBody = doParseModifierListBody(consumer, tokenSet, annotationParsingMode, tokenSet2);
        if (zDoParseModifierListBody) {
            markerMark.drop();
        } else {
            markerMark.done(KtNodeTypes.MODIFIER_LIST);
        }
        return !zDoParseModifierListBody;
    }

    private boolean tryParseModifier(Consumer<IElementType> consumer, TokenSet tokenSet, TokenSet tokenSet2) {
        if (tokenSet == null) {
            $$$reportNull$$$0(12);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(13);
        }
        PsiBuilder.Marker markerMark = mark();
        if (atSet(tokenSet2)) {
            IElementType iElementTypeLookahead = lookahead(1);
            if (at(KtTokens.FUN_KEYWORD) && iElementTypeLookahead != KtTokens.INTERFACE_KEYWORD) {
                markerMark.rollbackTo();
                return false;
            }
            if (iElementTypeLookahead != null && !tokenSet.contains(iElementTypeLookahead)) {
                IElementType iElementTypeTt = tt();
                if (consumer != null) {
                    consumer.consume(iElementTypeTt);
                }
                advance();
                markerMark.collapse(iElementTypeTt);
                return true;
            }
        }
        markerMark.rollbackTo();
        return false;
    }

    private void parseContextReceiverList() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        if (at(KtTokens.LPAR)) {
            advance();
            while (true) {
                if (at(KtTokens.COMMA)) {
                    errorAndAdvance("Expecting a type reference");
                }
                parseContextReceiver();
                if (at(KtTokens.RPAR)) {
                    advance();
                    break;
                } else if (at(KtTokens.COMMA)) {
                    advance();
                } else if (!at(KtTokens.RPAR)) {
                    error("Expecting comma or ')'");
                    break;
                }
            }
            markerMark.done(KtNodeTypes.CONTEXT_RECEIVER_LIST);
            return;
        }
        errorWithRecovery("Expecting context receivers", TokenSet.EMPTY);
        markerMark.drop();
    }

    private void parseContextReceiver() {
        PsiBuilder.Marker markerMark = mark();
        if (this.myExpressionParsing.isAtLabelDefinitionOrMissingIdentifier()) {
            this.myExpressionParsing.parseLabelDefinition();
        }
        parseTypeRef();
        markerMark.done(KtNodeTypes.CONTEXT_RECEIVER);
    }

    private void parseFileAnnotationList(AnnotationParsingMode annotationParsingMode) {
        if (!annotationParsingMode.isFileAnnotationParsingMode) {
            LOG.error("expected file annotation parsing mode, but:" + annotationParsingMode);
        }
        PsiBuilder.Marker markerMark = mark();
        if (parseAnnotations(annotationParsingMode)) {
            markerMark.done(KtNodeTypes.FILE_ANNOTATION_LIST);
        } else {
            markerMark.drop();
        }
    }

    boolean parseAnnotations(AnnotationParsingMode annotationParsingMode) {
        if (!parseAnnotationOrList(annotationParsingMode)) {
            return false;
        }
        while (parseAnnotationOrList(annotationParsingMode)) {
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x003d  */
    /* JADX WARN: Code duplicated, block: B:19:0x0042  */
    /* JADX WARN: Code duplicated, block: B:21:0x0046  */
    /* JADX WARN: Code duplicated, block: B:23:0x004b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x004d  */
    /* JADX WARN: Code duplicated, block: B:26:0x0057  */
    /* JADX WARN: Code duplicated, block: B:27:0x005b  */
    /* JADX WARN: Code duplicated, block: B:28:0x005f  */
    private boolean parseAnnotationOrList(AnnotationParsingMode annotationParsingMode) {
        boolean z = false;
        if (!at(KtTokens.AT)) {
            return false;
        }
        IElementType iElementTypeRawLookup = this.myBuilder.rawLookup(1);
        if ((iElementTypeRawLookup == KtTokens.IDENTIFIER || ANNOTATION_TARGETS.contains(iElementTypeRawLookup)) && lookahead(2) == KtTokens.COLON) {
            iElementTypeRawLookup = lookahead(3);
        } else {
            if (lookahead(1) == KtTokens.COLON) {
                iElementTypeRawLookup = lookahead(2);
            }
            if (iElementTypeRawLookup == KtTokens.IDENTIFIER) {
                return parseAnnotation(annotationParsingMode);
            }
            if (iElementTypeRawLookup == KtTokens.LBRACKET) {
                return parseAnnotationList(annotationParsingMode);
            }
            if (z) {
                if (lookahead(1) == KtTokens.COLON) {
                    errorAndAdvance("Expected annotation identifier after ':'", 2);
                } else {
                    errorAndAdvance("Expected annotation identifier after ':'", 3);
                }
            } else {
                errorAndAdvance("Expected annotation identifier after '@'", 1);
            }
            return true;
        }
        z = true;
        if (iElementTypeRawLookup == KtTokens.IDENTIFIER) {
            return parseAnnotation(annotationParsingMode);
        }
        if (iElementTypeRawLookup == KtTokens.LBRACKET) {
            return parseAnnotationList(annotationParsingMode);
        }
        if (z) {
            if (lookahead(1) == KtTokens.COLON) {
                errorAndAdvance("Expected annotation identifier after ':'", 2);
            } else {
                errorAndAdvance("Expected annotation identifier after ':'", 3);
            }
        } else {
            errorAndAdvance("Expected annotation identifier after '@'", 1);
        }
        return true;
    }

    private boolean parseAnnotationList(AnnotationParsingMode annotationParsingMode) {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        advance();
        if (!parseAnnotationTargetIfNeeded(annotationParsingMode)) {
            markerMark.rollbackTo();
            this.myBuilder.restoreNewlinesState();
            return false;
        }
        advance();
        if (at(KtTokens.IDENTIFIER) || at(KtTokens.AT)) {
            while (true) {
                if (!at(KtTokens.IDENTIFIER) && !at(KtTokens.AT)) {
                    break;
                }
                if (at(KtTokens.AT)) {
                    errorAndAdvance("No '@' needed in annotation list");
                } else {
                    parseAnnotation(AnnotationParsingMode.DEFAULT);
                    while (at(KtTokens.COMMA)) {
                        errorAndAdvance("No commas needed to separate annotations");
                    }
                }
            }
        } else {
            error("Expecting a list of annotations");
        }
        expect(KtTokens.RBRACKET, "Expecting ']' to close the annotation list");
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.ANNOTATION);
        return true;
    }

    private boolean parseAnnotationTargetIfNeeded(AnnotationParsingMode annotationParsingMode) {
        if (at(KtTokens.COLON)) {
            errorAndAdvance("Expected annotation target before ':'");
            return true;
        }
        KtKeywordToken ktKeywordTokenAtTargetKeyword = atTargetKeyword();
        if (annotationParsingMode == AnnotationParsingMode.FILE_ANNOTATIONS_WHEN_PACKAGE_OMITTED && (ktKeywordTokenAtTargetKeyword != KtTokens.FILE_KEYWORD || lookahead(1) != KtTokens.COLON)) {
            return false;
        }
        if (lookahead(1) == KtTokens.COLON && ktKeywordTokenAtTargetKeyword == null && at(KtTokens.IDENTIFIER)) {
            errorAndAdvance("Expected annotation target before ':'");
            advance();
            return true;
        }
        if (ktKeywordTokenAtTargetKeyword == null && annotationParsingMode.isFileAnnotationParsingMode) {
            parseAnnotationTarget(KtTokens.FILE_KEYWORD);
        } else if (ktKeywordTokenAtTargetKeyword != null) {
            parseAnnotationTarget(ktKeywordTokenAtTargetKeyword);
        }
        return true;
    }

    private void parseAnnotationTarget(KtKeywordToken ktKeywordToken) {
        String str = "Expecting \"" + ktKeywordToken.getValue() + KtTokens.COLON.getValue() + "\" prefix for " + ktKeywordToken.getValue() + " annotations";
        PsiBuilder.Marker markerMark = mark();
        if (!expect(ktKeywordToken, str)) {
            markerMark.drop();
        } else {
            markerMark.done(KtNodeTypes.ANNOTATION_TARGET);
        }
        expect(KtTokens.COLON, str, IDENTIFIER_RBRACKET_LBRACKET_SET);
    }

    private KtKeywordToken atTargetKeyword() {
        for (IElementType iElementType : ANNOTATION_TARGETS.getTypes()) {
            if (at(iElementType)) {
                return (KtKeywordToken) iElementType;
            }
        }
        return null;
    }

    private boolean parseAnnotation(AnnotationParsingMode annotationParsingMode) {
        PsiBuilder.Marker markerMark = mark();
        boolean zAt = at(KtTokens.AT);
        if (zAt) {
            advance();
        }
        if (zAt && !parseAnnotationTargetIfNeeded(annotationParsingMode)) {
            markerMark.rollbackTo();
            return false;
        }
        PsiBuilder.Marker markerMark2 = mark();
        PsiBuilder.Marker markerMark3 = mark();
        parseUserType();
        markerMark3.done(KtNodeTypes.TYPE_REFERENCE);
        markerMark2.done(KtNodeTypes.CONSTRUCTOR_CALLEE);
        parseTypeArgumentList();
        boolean z = at(KtTokens.LPAR) && KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(this.myBuilder.rawLookup(-1)) && annotationParsingMode.withSignificantWhitespaceBeforeArguments;
        if (at(KtTokens.LPAR) && !z) {
            this.myExpressionParsing.parseValueArgumentList();
            if (annotationParsingMode.typeContext && (getLastToken() != KtTokens.RPAR || at(KtTokens.ARROW))) {
                markerMark.done(KtNodeTypes.ANNOTATION_ENTRY);
                return false;
            }
        }
        markerMark.done(KtNodeTypes.ANNOTATION_ENTRY);
        return true;
    }

    private boolean isNextRawTokenCommentOrWhitespace() {
        return KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(this.myBuilder.rawLookup(1));
    }

    private IElementType parseClassOrObject(boolean z, NameParsingMode nameParsingMode, boolean z2, boolean z3, boolean z4) {
        if (z4) {
            advance();
        } else {
            error("'class' keyword is expected after 'enum'");
        }
        if (nameParsingMode == NameParsingMode.REQUIRED) {
            expect(KtTokens.IDENTIFIER, "Name expected", CLASS_NAME_RECOVERY_SET);
        } else if (at(KtTokens.IDENTIFIER)) {
            if (nameParsingMode == NameParsingMode.PROHIBITED) {
                errorAndAdvance("An object expression cannot bind a name");
            } else {
                advance();
            }
        }
        boolean typeParameterList = parseTypeParameterList(TYPE_PARAMETER_GT_RECOVERY_SET);
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        boolean modifierList = parseModifierList(TokenSet.EMPTY);
        if (modifierList && !atSet(LPAR_LBRACE_COLON_CONSTRUCTOR_KEYWORD_SET)) {
            markerMark.rollbackTo();
            return z ? KtNodeTypes.OBJECT_DECLARATION : KtNodeTypes.CLASS;
        }
        markerMark.drop();
        boolean zAt = at(KtTokens.CONSTRUCTOR_KEYWORD);
        if (zAt) {
            advance();
        }
        if (at(KtTokens.LPAR)) {
            parseValueParameterList(false, true, LBRACE_RBRACE_SET);
            markerMark2.done(KtNodeTypes.PRIMARY_CONSTRUCTOR);
        } else if (modifierList || zAt) {
            markerMark2.done(KtNodeTypes.PRIMARY_CONSTRUCTOR);
            if (zAt) {
                error("Expecting primary constructor parameter list");
            } else {
                error("Expecting 'constructor' keyword");
            }
        } else {
            markerMark2.drop();
        }
        if (at(KtTokens.COLON)) {
            advance();
            parseDelegationSpecifierList();
        }
        AbstractKotlinParsing.OptionalMarker optionalMarker = new AbstractKotlinParsing.OptionalMarker(z);
        parseTypeConstraintsGuarded(typeParameterList);
        optionalMarker.error("Where clause is not allowed for objects");
        if (at(KtTokens.LBRACE)) {
            if (z3) {
                parseEnumClassBody();
            } else {
                parseClassBody();
            }
        } else if (!z2) {
            PsiBuilder.Marker markerMark3 = mark();
            error("Expecting a class body");
            markerMark3.done(KtNodeTypes.CLASS_BODY);
        }
        return z ? KtNodeTypes.OBJECT_DECLARATION : KtNodeTypes.CLASS;
    }

    private IElementType parseClass(boolean z, boolean z2) {
        return parseClassOrObject(false, NameParsingMode.REQUIRED, true, z, z2);
    }

    void parseObject(NameParsingMode nameParsingMode, boolean z) {
        parseClassOrObject(true, nameParsingMode, z, false, true);
    }

    private void parseEnumClassBody() {
        if (at(KtTokens.LBRACE)) {
            PsiBuilder.Marker markerMark = mark();
            this.myBuilder.enableNewlines();
            advance();
            if (!parseEnumEntries() && !at(KtTokens.RBRACE)) {
                error("Expecting ';' after the last enum entry or '}' to close enum class body");
            }
            parseMembers();
            expect(KtTokens.RBRACE, "Expecting '}' to close enum class body");
            this.myBuilder.restoreNewlinesState();
            markerMark.done(KtNodeTypes.CLASS_BODY);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.parsing.KotlinParsing$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult;

        static {
            int[] iArr = new int[ParseEnumEntryResult.values().length];
            $SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult = iArr;
            try {
                iArr[ParseEnumEntryResult.FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult[ParseEnumEntryResult.NO_DELIMITER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult[ParseEnumEntryResult.COMMA_DELIMITER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult[ParseEnumEntryResult.SEMICOLON_DELIMITER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private boolean parseEnumEntries() {
        while (!eof() && !at(KtTokens.RBRACE)) {
            int i = AnonymousClass4.$SwitchMap$org$jetbrains$kotlin$parsing$KotlinParsing$ParseEnumEntryResult[parseEnumEntry().ordinal()];
            if (i == 1) {
                if (!at(KtTokens.SEMICOLON)) {
                    break;
                }
                advance();
                return true;
            }
            if (i == 2) {
                return false;
            }
            if (i == 4) {
                return true;
            }
        }
        return false;
    }

    private ParseEnumEntryResult parseEnumEntry() {
        PsiBuilder.Marker markerMark = mark();
        parseModifierList(COMMA_SEMICOLON_RBRACE_SET);
        if (!atSet(SOFT_KEYWORDS_AT_MEMBER_START) && at(KtTokens.IDENTIFIER)) {
            advance();
            if (at(KtTokens.LPAR)) {
                PsiBuilder.Marker markerMark2 = mark();
                PsiBuilder.Marker markerMark3 = mark();
                PsiBuilder.Marker markerMark4 = mark();
                PsiBuilder.Marker markerMark5 = mark();
                PsiBuilder.Marker markerMark6 = mark();
                mark().done(KtNodeTypes.ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION);
                markerMark6.done(KtNodeTypes.USER_TYPE);
                markerMark5.done(KtNodeTypes.TYPE_REFERENCE);
                markerMark4.done(KtNodeTypes.CONSTRUCTOR_CALLEE);
                this.myExpressionParsing.parseValueArgumentList();
                markerMark3.done(KtNodeTypes.SUPER_TYPE_CALL_ENTRY);
                markerMark2.done(KtNodeTypes.INITIALIZER_LIST);
            }
            if (at(KtTokens.LBRACE)) {
                parseClassBody();
            }
            boolean zAt = at(KtTokens.COMMA);
            if (zAt) {
                advance();
            }
            boolean zAt2 = at(KtTokens.SEMICOLON);
            if (zAt2) {
                advance();
            }
            closeDeclarationWithCommentBinders(markerMark, KtNodeTypes.ENUM_ENTRY, true);
            if (zAt2) {
                return ParseEnumEntryResult.SEMICOLON_DELIMITER;
            }
            return zAt ? ParseEnumEntryResult.COMMA_DELIMITER : ParseEnumEntryResult.NO_DELIMITER;
        }
        markerMark.rollbackTo();
        return ParseEnumEntryResult.FAILED;
    }

    private void parseClassBody() {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.enableNewlines();
        if (expect(KtTokens.LBRACE, "Expecting a class body")) {
            parseMembers();
            expect(KtTokens.RBRACE, "Missing '}");
        }
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.CLASS_BODY);
    }

    private void parseMembers() {
        while (!eof() && !at(KtTokens.RBRACE)) {
            parseMemberDeclaration();
        }
    }

    private void parseMemberDeclaration() {
        if (at(KtTokens.SEMICOLON)) {
            advance();
            return;
        }
        PsiBuilder.Marker markerMark = mark();
        if (at(KtTokens.CONTEXT_KEYWORD)) {
            parseContextReceiverList();
        }
        ModifierDetector modifierDetector = new ModifierDetector();
        parseModifierList(modifierDetector, TokenSet.EMPTY);
        IElementType memberDeclarationRest = parseMemberDeclarationRest(modifierDetector);
        if (memberDeclarationRest == null) {
            errorWithRecovery("Expecting member declaration", TokenSet.EMPTY);
            markerMark.drop();
        } else {
            closeDeclarationWithCommentBinders(markerMark, memberDeclarationRest, true);
        }
    }

    private IElementType parseMemberDeclarationRest(ModifierDetector modifierDetector) {
        if (modifierDetector == null) {
            $$$reportNull$$$0(14);
        }
        IElementType commonDeclaration = parseCommonDeclaration(modifierDetector, modifierDetector.isCompanionDetected() ? NameParsingMode.ALLOWED : NameParsingMode.REQUIRED, DeclarationParsingMode.MEMBER_OR_TOPLEVEL);
        if (commonDeclaration != null) {
            return commonDeclaration;
        }
        if (at(KtTokens.INIT_KEYWORD)) {
            advance();
            if (at(KtTokens.LBRACE)) {
                parseBlock();
            } else {
                mark().error("Expecting '{' after 'init'");
            }
            return KtNodeTypes.CLASS_INITIALIZER;
        }
        if (at(KtTokens.CONSTRUCTOR_KEYWORD)) {
            parseSecondaryConstructor();
            return KtNodeTypes.SECONDARY_CONSTRUCTOR;
        }
        if (!at(KtTokens.LBRACE)) {
            return commonDeclaration;
        }
        error("Expecting member declaration");
        parseBlock();
        return KtNodeTypes.FUN;
    }

    private void parseSecondaryConstructor() {
        PsiBuilder.Marker markerMark;
        advance();
        if (at(KtTokens.LPAR)) {
            parseValueParameterList(false, true, VALUE_ARGS_RECOVERY_SET);
        } else {
            errorWithRecovery("Expecting '('", TokenSet.orSet(VALUE_ARGS_RECOVERY_SET, TokenSet.create(KtTokens.COLON)));
        }
        if (at(KtTokens.COLON)) {
            advance();
            PsiBuilder.Marker markerMark2 = mark();
            if (at(KtTokens.THIS_KEYWORD) || at(KtTokens.SUPER_KEYWORD)) {
                parseThisOrSuper();
                this.myExpressionParsing.parseValueArgumentList();
            } else {
                error("Expecting a 'this' or 'super' constructor call");
                if (at(KtTokens.LPAR)) {
                    markerMark = null;
                } else {
                    markerMark = mark();
                    advance();
                }
                this.myExpressionParsing.parseValueArgumentList();
                if (markerMark != null) {
                    if (at(KtTokens.LBRACE)) {
                        markerMark.drop();
                    } else {
                        markerMark.rollbackTo();
                    }
                }
            }
            markerMark2.done(KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL);
        } else {
            PsiBuilder.Marker markerMark3 = mark();
            mark().done(KtNodeTypes.CONSTRUCTOR_DELEGATION_REFERENCE);
            markerMark3.done(KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL);
        }
        if (at(KtTokens.LBRACE)) {
            parseBlock();
        }
    }

    private void parseThisOrSuper() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        markerMark.done(KtNodeTypes.CONSTRUCTOR_DELEGATION_REFERENCE);
    }

    private IElementType parseTypeAlias() {
        advance();
        expect(KtTokens.IDENTIFIER, "Type name expected", LT_EQ_SEMICOLON_TOP_LEVEL_DECLARATION_FIRST_SET);
        parseTypeParameterList(TYPE_PARAMETER_GT_RECOVERY_SET);
        if (at(KtTokens.WHERE_KEYWORD)) {
            PsiBuilder.Marker markerMark = mark();
            parseTypeConstraints();
            markerMark.error("Type alias parameters can't have bounds");
        }
        expect(KtTokens.EQ, "Expecting '='", TOP_LEVEL_DECLARATION_FIRST_SEMICOLON_SET);
        parseTypeRef();
        consumeIf(KtTokens.SEMICOLON);
        return KtNodeTypes.TYPEALIAS;
    }

    enum DeclarationParsingMode {
        MEMBER_OR_TOPLEVEL(false, true, true),
        LOCAL(true, false, false),
        SCRIPT_TOPLEVEL(true, true, false);

        public final boolean accessorsAllowed;
        public final boolean canBeEnumUsedAsSoftKeyword;
        public final boolean destructuringAllowed;

        DeclarationParsingMode(boolean z, boolean z2, boolean z3) {
            this.destructuringAllowed = z;
            this.accessorsAllowed = z2;
            this.canBeEnumUsedAsSoftKeyword = z3;
        }
    }

    public IElementType parseProperty(DeclarationParsingMode declarationParsingMode) {
        boolean z;
        advance();
        boolean z2 = at(KtTokens.LT) && parseTypeParameterList(IDENTIFIER_EQ_COLON_SEMICOLON_SET);
        this.myBuilder.disableJoiningComplexTokens();
        PsiBuilder.Marker markerMark = mark();
        TokenSet tokenSet = PROPERTY_NAME_FOLLOW_SET;
        boolean receiverType = parseReceiverType("property", tokenSet);
        boolean zAt = at(KtTokens.LPAR);
        errorIf(markerMark, zAt && receiverType, "Receiver type is not allowed on a destructuring declaration");
        boolean zEol = eol();
        PsiBuilder.Marker markerMark2 = mark();
        if (zAt) {
            PsiBuilder.Marker markerMark3 = mark();
            parseMultiDeclarationName(tokenSet, PROPERTY_NAME_FOLLOW_MULTI_DECLARATION_RECOVERY_SET);
            errorIf(markerMark3, !declarationParsingMode.destructuringAllowed, "Destructuring declarations are only allowed for local variables/values");
        } else {
            parseFunctionOrPropertyName(receiverType, "property", tokenSet, PROPERTY_NAME_FOLLOW_FUNCTION_OR_PROPERTY_RECOVERY_SET, true);
        }
        this.myBuilder.restoreJoiningComplexTokensState();
        if (at(KtTokens.COLON)) {
            PsiBuilder.Marker markerMark4 = mark();
            advance();
            parseTypeRef();
            errorIf(markerMark4, zAt, "Type annotations are not allowed on destructuring declarations");
            z = false;
        } else {
            z = true;
        }
        parseTypeConstraintsGuarded(z2);
        if (!parsePropertyDelegateOrAssignment() && zEol && z && !receiverType) {
            markerMark2.rollbackTo();
            error("Expecting property name or receiver type");
            return KtNodeTypes.PROPERTY;
        }
        markerMark2.drop();
        if (declarationParsingMode.accessorsAllowed) {
            this.myBuilder.enableNewlines();
            boolean z3 = consumeIf(KtTokens.SEMICOLON) && this.myBuilder.newlineBeforeCurrentToken();
            this.myBuilder.restoreNewlinesState();
            if (!z3) {
                PropertyComponentKind.Collector collector = new PropertyComponentKind.Collector();
                PropertyComponentKind propertyComponent = parsePropertyComponent(collector);
                while (propertyComponent != null) {
                    collector.collect(propertyComponent);
                    propertyComponent = parsePropertyComponent(collector);
                }
                if (!atSet(EOL_OR_SEMICOLON_RBRACE_SET)) {
                    if (getLastToken() != KtTokens.SEMICOLON) {
                        errorUntil("Property getter or setter expected", TokenSet.orSet(DECLARATION_FIRST, TokenSet.create(KtTokens.EOL_OR_SEMICOLON, KtTokens.LBRACE, KtTokens.RBRACE)));
                    }
                } else {
                    consumeIf(KtTokens.SEMICOLON);
                }
            }
        }
        return zAt ? KtNodeTypes.DESTRUCTURING_DECLARATION : KtNodeTypes.PROPERTY;
    }

    private boolean parsePropertyDelegateOrAssignment() {
        if (at(KtTokens.BY_KEYWORD)) {
            parsePropertyDelegate();
            return true;
        }
        if (!at(KtTokens.EQ)) {
            return false;
        }
        advance();
        this.myExpressionParsing.parseExpression();
        return true;
    }

    private void parsePropertyDelegate() {
        PsiBuilder.Marker markerMark = mark();
        advance();
        this.myExpressionParsing.parseExpression();
        markerMark.done(KtNodeTypes.PROPERTY_DELEGATE);
    }

    public void parseMultiDeclarationName(TokenSet tokenSet, TokenSet tokenSet2) {
        this.myBuilder.disableNewlines();
        advance();
        if (!atSet(tokenSet)) {
            do {
                if (at(KtTokens.COMMA)) {
                    errorAndAdvance("Expecting a name");
                } else if (at(KtTokens.RPAR)) {
                    error("Expecting a name");
                    break;
                }
                PsiBuilder.Marker markerMark = mark();
                parseModifierList(COMMA_RPAR_COLON_EQ_SET);
                expect(KtTokens.IDENTIFIER, "Expecting a name", tokenSet2);
                if (at(KtTokens.COLON)) {
                    advance();
                    parseTypeRef(tokenSet);
                }
                markerMark.done(KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY);
                if (!at(KtTokens.COMMA)) {
                    break;
                } else {
                    advance();
                }
            } while (!at(KtTokens.RPAR));
        }
        expect(KtTokens.RPAR, "Expecting ')'", tokenSet);
        this.myBuilder.restoreNewlinesState();
    }

    private enum PropertyComponentKind {
        GET,
        SET,
        FIELD;

        static class Collector {
            private final boolean[] collected = {false, false, false};

            Collector() {
            }

            public void collect(PropertyComponentKind propertyComponentKind) {
                this.collected[propertyComponentKind.ordinal()] = true;
            }

            public boolean contains(PropertyComponentKind propertyComponentKind) {
                return this.collected[propertyComponentKind.ordinal()];
            }
        }
    }

    private PropertyComponentKind parsePropertyComponent(PropertyComponentKind.Collector collector) {
        PropertyComponentKind propertyComponentKind;
        PsiBuilder.Marker markerMark = mark();
        parseModifierList(TokenSet.EMPTY);
        if (at(KtTokens.GET_KEYWORD)) {
            propertyComponentKind = PropertyComponentKind.GET;
        } else if (at(KtTokens.SET_KEYWORD)) {
            propertyComponentKind = PropertyComponentKind.SET;
        } else if (at(KtTokens.FIELD_KEYWORD)) {
            propertyComponentKind = PropertyComponentKind.FIELD;
        } else {
            markerMark.rollbackTo();
            return null;
        }
        if (collector.contains(propertyComponentKind)) {
            markerMark.rollbackTo();
            return null;
        }
        advance();
        if (!at(KtTokens.LPAR) && propertyComponentKind != PropertyComponentKind.FIELD) {
            TokenSet tokenSet = ACCESSOR_FIRST_OR_PROPERTY_END;
            if (atSet(tokenSet)) {
                closeDeclarationWithCommentBinders(markerMark, KtNodeTypes.PROPERTY_ACCESSOR, true);
                return propertyComponentKind;
            }
            errorUntil("Accessor body expected", TokenSet.orSet(tokenSet, TokenSet.create(KtTokens.LBRACE, KtTokens.LPAR, KtTokens.EQ)));
        }
        this.myBuilder.disableNewlines();
        if (propertyComponentKind != PropertyComponentKind.FIELD) {
            expect(KtTokens.LPAR, "Expecting '('", RPAR_IDENTIFIER_COLON_LBRACE_EQ_SET);
            if (propertyComponentKind == PropertyComponentKind.SET) {
                PsiBuilder.Marker markerMark2 = mark();
                PsiBuilder.Marker markerMark3 = mark();
                parseModifierList(COMMA_COLON_RPAR_SET);
                expect(KtTokens.IDENTIFIER, "Expecting parameter name", RPAR_COLON_LBRACE_EQ_SET);
                if (at(KtTokens.COLON)) {
                    advance();
                    parseTypeRef();
                }
                markerMark3.done(KtNodeTypes.VALUE_PARAMETER);
                if (at(KtTokens.COMMA)) {
                    advance();
                }
                markerMark2.done(KtNodeTypes.VALUE_PARAMETER_LIST);
            }
            if (!at(KtTokens.RPAR)) {
                errorUntil("Expecting ')'", TokenSet.create(KtTokens.RPAR, KtTokens.COLON, KtTokens.LBRACE, KtTokens.RBRACE, KtTokens.EQ, KtTokens.EOL_OR_SEMICOLON));
            }
            if (at(KtTokens.RPAR)) {
                advance();
            }
        }
        this.myBuilder.restoreNewlinesState();
        if (at(KtTokens.COLON)) {
            advance();
            parseTypeRef();
        }
        if (propertyComponentKind != PropertyComponentKind.FIELD) {
            parseFunctionContract();
            parseFunctionBody();
        } else if (at(KtTokens.EQ)) {
            advance();
            this.myExpressionParsing.parseExpression();
            consumeIf(KtTokens.SEMICOLON);
        }
        if (propertyComponentKind == PropertyComponentKind.FIELD) {
            closeDeclarationWithCommentBinders(markerMark, KtNodeTypes.BACKING_FIELD, true);
        } else {
            closeDeclarationWithCommentBinders(markerMark, KtNodeTypes.PROPERTY_ACCESSOR, true);
        }
        return propertyComponentKind;
    }

    private IElementType parseFunction() {
        IElementType function = parseFunction(false);
        if (function == null) {
            $$$reportNull$$$0(15);
        }
        return function;
    }

    IElementType parseFunction(boolean z) {
        boolean z2;
        advance();
        if (at(KtTokens.RBRACE)) {
            error("Function body expected");
            return KtNodeTypes.FUN;
        }
        boolean z3 = true;
        if (at(KtTokens.LT)) {
            parseTypeParameterList(LBRACKET_LBRACE_RBRACE_LPAR_SET);
            z2 = true;
        } else {
            z2 = false;
        }
        this.myBuilder.disableJoiningComplexTokens();
        TokenSet tokenSet = FUNCTION_NAME_FOLLOW_SET;
        boolean receiverType = parseReceiverType("function", tokenSet);
        if (at(KtTokens.IDENTIFIER) && z) {
            this.myBuilder.restoreJoiningComplexTokensState();
            return null;
        }
        parseFunctionOrPropertyName(receiverType, "function", tokenSet, FUNCTION_NAME_RECOVERY_SET, false);
        this.myBuilder.restoreJoiningComplexTokensState();
        if (at(KtTokens.LT)) {
            PsiBuilder.Marker markerMark = mark();
            parseTypeParameterList(LPAR_VALUE_PARAMETERS_FOLLOW_SET);
            if (z2) {
                int iRawTokenIndex = this.myBuilder.rawTokenIndex();
                markerMark.rollbackTo();
                PsiBuilder.Marker markerMark2 = mark();
                advance(iRawTokenIndex - this.myBuilder.rawTokenIndex());
                markerMark2.error("Only one type parameter list is allowed for a function");
            } else {
                markerMark.drop();
            }
        } else {
            z3 = z2;
        }
        if (at(KtTokens.LPAR)) {
            parseValueParameterList(false, false, VALUE_PARAMETERS_FOLLOW_SET);
        } else {
            error("Expecting '('");
        }
        if (at(KtTokens.COLON)) {
            advance();
            parseTypeRef();
        }
        boolean functionContract = parseFunctionContract();
        parseTypeConstraintsGuarded(z3);
        if (!functionContract) {
            parseFunctionContract();
        }
        if (at(KtTokens.SEMICOLON)) {
            advance();
        } else if (at(KtTokens.EQ) || at(KtTokens.LBRACE)) {
            parseFunctionBody();
        }
        return KtNodeTypes.FUN;
    }

    private boolean parseReceiverType(String str, TokenSet tokenSet) {
        PsiBuilder.Marker markerMark = mark();
        boolean annotations = parseAnnotations(AnnotationParsingMode.DEFAULT);
        int iLastDotAfterReceiver = lastDotAfterReceiver();
        boolean z = iLastDotAfterReceiver != -1;
        if (!annotations) {
            markerMark.drop();
        } else if (z) {
            markerMark.rollbackTo();
        } else {
            markerMark.error("Annotations are not allowed in this position");
        }
        if (!z) {
            return false;
        }
        createTruncatedBuilder(iLastDotAfterReceiver).parseTypeRefWithoutIntersections();
        if (atSet(RECEIVER_TYPE_TERMINATORS)) {
            advance();
        } else {
            errorWithRecovery("Expecting '.' before a " + str + " name", tokenSet);
        }
        return true;
    }

    private int lastDotAfterReceiver() {
        AbstractTokenStreamPattern abstractTokenStreamPattern = at(KtTokens.LPAR) ? this.lastDotAfterReceiverLParPattern : this.lastDotAfterReceiverNotLParPattern;
        abstractTokenStreamPattern.reset();
        return matchTokenStreamPredicate(abstractTokenStreamPattern);
    }

    private void parseFunctionOrPropertyName(boolean z, String str, TokenSet tokenSet, TokenSet tokenSet2, boolean z2) {
        if ((z2 || !atSet(tokenSet)) && !expect(KtTokens.IDENTIFIER)) {
            errorWithRecovery("Expecting " + str + " name" + (!z ? " or receiver type" : ""), tokenSet2);
        }
    }

    private void parseFunctionBody() {
        if (at(KtTokens.LBRACE)) {
            parseBlock();
        } else {
            if (at(KtTokens.EQ)) {
                advance();
                this.myExpressionParsing.parseExpression();
                consumeIf(KtTokens.SEMICOLON);
                return;
            }
            error("Expecting function body");
        }
    }

    void parseBlock() {
        parseBlock(true);
    }

    private void parseBlock(boolean z) {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.enableNewlines();
        boolean z2 = z && expect(KtTokens.LBRACE, "Expecting '{' to open a block") && this.isLazy;
        if (z2) {
            advanceBalancedBlock();
        } else {
            this.myExpressionParsing.parseStatements();
            expect(KtTokens.RBRACE, "Expecting '}'");
        }
        this.myBuilder.restoreNewlinesState();
        if (z2) {
            markerMark.collapse(KtNodeTypes.BLOCK);
        } else {
            markerMark.done(KtNodeTypes.BLOCK);
        }
    }

    public void advanceBalancedBlock() {
        int i = 1;
        while (!eof()) {
            if (_at(KtTokens.LBRACE)) {
                i++;
            } else if (_at(KtTokens.RBRACE)) {
                i--;
            }
            advance();
            if (i == 0) {
                return;
            }
        }
    }

    private void parseDelegationSpecifierList() {
        PsiBuilder.Marker markerMark = mark();
        while (true) {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance("Expecting a delegation specifier");
            } else {
                parseDelegationSpecifier();
                if (at(KtTokens.COMMA)) {
                    advance();
                } else {
                    markerMark.done(KtNodeTypes.SUPER_TYPE_LIST);
                    return;
                }
            }
        }
    }

    private void parseDelegationSpecifier() {
        PsiBuilder.Marker markerMark = mark();
        PsiBuilder.Marker markerMark2 = mark();
        parseTypeRef();
        if (at(KtTokens.BY_KEYWORD)) {
            markerMark2.drop();
            advance();
            createForByClause(this.myBuilder, this.isLazy).myExpressionParsing.parseExpression();
            markerMark.done(KtNodeTypes.DELEGATED_SUPER_TYPE_ENTRY);
            return;
        }
        if (at(KtTokens.LPAR)) {
            markerMark2.done(KtNodeTypes.CONSTRUCTOR_CALLEE);
            this.myExpressionParsing.parseValueArgumentList();
            markerMark.done(KtNodeTypes.SUPER_TYPE_CALL_ENTRY);
        } else {
            markerMark2.drop();
            markerMark.done(KtNodeTypes.SUPER_TYPE_ENTRY);
        }
    }

    private boolean parseTypeParameterList(TokenSet tokenSet) {
        if (!at(KtTokens.LT)) {
            return false;
        }
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        advance();
        do {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance("Expecting type parameter declaration");
            }
            parseTypeParameter();
            if (!at(KtTokens.COMMA)) {
                break;
            }
            advance();
        } while (!at(KtTokens.GT));
        expect(KtTokens.GT, "Missing '>'", tokenSet);
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.TYPE_PARAMETER_LIST);
        return true;
    }

    private void parseTypeConstraintsGuarded(boolean z) {
        errorIf(mark(), parseTypeConstraints() && !z, "Type constraints are not allowed when no type parameters declared");
    }

    private boolean parseTypeConstraints() {
        if (!at(KtTokens.WHERE_KEYWORD)) {
            return false;
        }
        parseTypeConstraintList();
        return true;
    }

    private void parseTypeConstraintList() {
        advance();
        PsiBuilder.Marker markerMark = mark();
        while (true) {
            if (at(KtTokens.COMMA)) {
                errorAndAdvance("Type constraint expected");
            }
            parseTypeConstraint();
            if (at(KtTokens.COMMA)) {
                advance();
            } else {
                markerMark.done(KtNodeTypes.TYPE_CONSTRAINT_LIST);
                return;
            }
        }
    }

    private void parseTypeConstraint() {
        PsiBuilder.Marker markerMark = mark();
        parseAnnotations(AnnotationParsingMode.DEFAULT);
        PsiBuilder.Marker markerMark2 = mark();
        if (expect(KtTokens.IDENTIFIER, "Expecting type parameter name", COLON_COMMA_LBRACE_RBRACE_TYPE_REF_FIRST_SET)) {
            markerMark2.done(KtNodeTypes.REFERENCE_EXPRESSION);
        } else {
            markerMark2.drop();
        }
        expect(KtTokens.COLON, "Expecting ':' before the upper bound", LBRACE_RBRACE_TYPE_REF_FIRST_SET);
        parseTypeRef();
        markerMark.done(KtNodeTypes.TYPE_CONSTRAINT);
    }

    private boolean parseFunctionContract() {
        if (!at(KtTokens.CONTRACT_KEYWORD)) {
            return false;
        }
        this.myExpressionParsing.parseContractDescriptionBlock();
        return true;
    }

    private void parseTypeParameter() {
        if (atSet(TYPE_PARAMETER_GT_RECOVERY_SET)) {
            error("Type parameter declaration expected");
            return;
        }
        PsiBuilder.Marker markerMark = mark();
        parseModifierList(GT_COMMA_COLON_SET);
        expect(KtTokens.IDENTIFIER, "Type parameter name expected", TokenSet.EMPTY);
        if (at(KtTokens.COLON)) {
            advance();
            parseTypeRef();
        }
        markerMark.done(KtNodeTypes.TYPE_PARAMETER);
    }

    void parseTypeRef() {
        parseTypeRef(TokenSet.EMPTY);
    }

    void parseTypeRefWithoutIntersections() {
        parseTypeRef(TokenSet.EMPTY, false);
    }

    void parseTypeRef(TokenSet tokenSet) {
        parseTypeRef(tokenSet, true);
    }

    private void parseTypeRef(TokenSet tokenSet, boolean z) {
        parseTypeRefContents(tokenSet, z).done(KtNodeTypes.TYPE_REFERENCE);
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0134  */
    /* JADX WARN: Code duplicated, block: B:54:0x0164  */
    /* JADX WARN: Code duplicated, block: B:55:0x0168  */
    private PsiBuilder.Marker parseTypeRefContents(TokenSet tokenSet, boolean z) {
        boolean z2;
        boolean z3;
        PsiBuilder.Marker nullableTypeSuffix;
        boolean z4;
        PsiBuilder.Marker markerM6464precede;
        PsiBuilder.Marker markerMark = mark();
        parseTypeModifierList();
        IElementType iElementTypeLookahead = lookahead(1);
        IElementType iElementTypeLookahead2 = lookahead(2);
        boolean z5 = at(KtTokens.CONTEXT_KEYWORD) && iElementTypeLookahead == KtTokens.LPAR;
        PsiBuilder.Marker markerMark2 = mark();
        if (z5) {
            parseContextReceiverList();
        }
        PsiBuilder.Marker markerMark3 = mark();
        if (at(KtTokens.IDENTIFIER) && ((iElementTypeLookahead != KtTokens.DOT || iElementTypeLookahead2 != KtTokens.IDENTIFIER) && iElementTypeLookahead != KtTokens.LT && at(KtTokens.DYNAMIC_KEYWORD))) {
            PsiBuilder.Marker markerMark4 = mark();
            advance();
            markerMark4.done(KtNodeTypes.DYNAMIC_TYPE);
        } else {
            if (at(KtTokens.IDENTIFIER) || at(KtTokens.PACKAGE_KEYWORD) || atParenthesizedMutableForPlatformTypes(0)) {
                parseUserType();
            } else if (at(KtTokens.LPAR)) {
                PsiBuilder.Marker markerMark5 = mark();
                advance();
                parseTypeRefContents(TokenSet.EMPTY, true).drop();
                if (at(KtTokens.RPAR) && lookahead(1) != KtTokens.ARROW) {
                    advance();
                    markerMark5.drop();
                    z2 = false;
                } else {
                    markerMark5.rollbackTo();
                    parseFunctionType(markerMark2.m6464precede());
                    z2 = true;
                }
                z3 = true;
            } else {
                errorWithRecovery("Type expected", TokenSet.orSet(TOP_LEVEL_DECLARATION_FIRST, TokenSet.create(KtTokens.EQ, KtTokens.COMMA, KtTokens.GT, KtTokens.RBRACKET, KtTokens.DOT, KtTokens.RPAR, KtTokens.RBRACE, KtTokens.LBRACE, KtTokens.SEMICOLON), tokenSet));
                z2 = false;
                z3 = false;
            }
            this.myBuilder.disableJoiningComplexTokens();
            nullableTypeSuffix = parseNullableTypeSuffix(markerMark3);
            this.myBuilder.restoreJoiningComplexTokensState();
            if (z || !at(KtTokens.AND)) {
                z4 = false;
            } else {
                PsiBuilder.Marker markerM6464precede2 = nullableTypeSuffix.m6464precede();
                PsiBuilder.Marker markerM6464precede3 = nullableTypeSuffix.m6464precede();
                nullableTypeSuffix.done(KtNodeTypes.TYPE_REFERENCE);
                advance();
                parseTypeRef(tokenSet, true);
                markerM6464precede3.done(KtNodeTypes.INTERSECTION_TYPE);
                nullableTypeSuffix = markerM6464precede2;
                z4 = true;
            }
            if (z3 && at(KtTokens.DOT) && !z4 && !z2) {
                markerM6464precede = markerMark2.m6464precede();
                PsiBuilder.Marker markerM6464precede4 = nullableTypeSuffix.m6464precede();
                PsiBuilder.Marker markerM6464precede5 = markerM6464precede4.m6464precede();
                markerM6464precede4.done(KtNodeTypes.TYPE_REFERENCE);
                markerM6464precede5.done(KtNodeTypes.FUNCTION_TYPE_RECEIVER);
                advance();
                if (at(KtTokens.LPAR)) {
                    parseFunctionType(markerM6464precede);
                } else {
                    markerM6464precede.drop();
                    error("Expecting function type");
                }
                z2 = true;
            }
            if (z5 && !z2) {
                errorWithRecovery("Function type expected expected", TokenSet.orSet(TOP_LEVEL_DECLARATION_FIRST, TokenSet.create(KtTokens.EQ, KtTokens.COMMA, KtTokens.GT, KtTokens.RBRACKET, KtTokens.DOT, KtTokens.RPAR, KtTokens.RBRACE, KtTokens.LBRACE, KtTokens.SEMICOLON), tokenSet));
            }
            nullableTypeSuffix.drop();
            markerMark2.drop();
            return markerMark;
        }
        z3 = true;
        z2 = false;
        this.myBuilder.disableJoiningComplexTokens();
        nullableTypeSuffix = parseNullableTypeSuffix(markerMark3);
        this.myBuilder.restoreJoiningComplexTokensState();
        if (z) {
            z4 = false;
        } else {
            z4 = false;
        }
        if (z3) {
            markerM6464precede = markerMark2.m6464precede();
            PsiBuilder.Marker markerM6464precede6 = nullableTypeSuffix.m6464precede();
            PsiBuilder.Marker markerM6464precede7 = markerM6464precede6.m6464precede();
            markerM6464precede6.done(KtNodeTypes.TYPE_REFERENCE);
            markerM6464precede7.done(KtNodeTypes.FUNCTION_TYPE_RECEIVER);
            advance();
            if (at(KtTokens.LPAR)) {
                parseFunctionType(markerM6464precede);
            } else {
                markerM6464precede.drop();
                error("Expecting function type");
            }
            z2 = true;
        }
        if (z5) {
            errorWithRecovery("Function type expected expected", TokenSet.orSet(TOP_LEVEL_DECLARATION_FIRST, TokenSet.create(KtTokens.EQ, KtTokens.COMMA, KtTokens.GT, KtTokens.RBRACKET, KtTokens.DOT, KtTokens.RPAR, KtTokens.RBRACE, KtTokens.LBRACE, KtTokens.SEMICOLON), tokenSet));
        }
        nullableTypeSuffix.drop();
        markerMark2.drop();
        return markerMark;
    }

    private PsiBuilder.Marker parseNullableTypeSuffix(PsiBuilder.Marker marker) {
        if (marker == null) {
            $$$reportNull$$$0(16);
        }
        while (at(KtTokens.QUEST) && this.myBuilder.rawLookup(1) != KtTokens.COLON) {
            PsiBuilder.Marker markerM6464precede = marker.m6464precede();
            advance();
            marker.done(KtNodeTypes.NULLABLE_TYPE);
            marker = markerM6464precede;
        }
        if (marker == null) {
            $$$reportNull$$$0(17);
        }
        return marker;
    }

    private void parseUserType() {
        PsiBuilder.Marker markerMark = mark();
        if (at(KtTokens.PACKAGE_KEYWORD)) {
            PsiBuilder.Marker markerMark2 = mark();
            advance();
            markerMark2.error("Expecting an element");
            expect(KtTokens.DOT, "Expecting '.'", TokenSet.create(KtTokens.IDENTIFIER, KtTokens.LBRACE, KtTokens.RBRACE));
        }
        PsiBuilder.Marker markerMark3 = mark();
        while (true) {
            recoverOnParenthesizedWordForPlatformTypes(0, "Mutable", true);
            if (expect(KtTokens.IDENTIFIER, "Expecting type name", TokenSet.orSet(KotlinExpressionParsing.EXPRESSION_FIRST, KotlinExpressionParsing.EXPRESSION_FOLLOW, DECLARATION_FIRST))) {
                markerMark3.done(KtNodeTypes.REFERENCE_EXPRESSION);
                parseTypeArgumentList();
                recoverOnPlatformTypeSuffix();
                if (!at(KtTokens.DOT) || (lookahead(1) == KtTokens.LPAR && !atParenthesizedMutableForPlatformTypes(1))) {
                    break;
                }
                PsiBuilder.Marker markerM6464precede = markerMark.m6464precede();
                markerMark.done(KtNodeTypes.USER_TYPE);
                advance();
                markerMark3 = mark();
                markerMark = markerM6464precede;
            } else {
                markerMark3.drop();
                break;
            }
        }
        markerMark.done(KtNodeTypes.USER_TYPE);
    }

    private boolean atParenthesizedMutableForPlatformTypes(int i) {
        return recoverOnParenthesizedWordForPlatformTypes(i, "Mutable", false);
    }

    private boolean recoverOnParenthesizedWordForPlatformTypes(int i, String str, boolean z) {
        if (lookahead(i) != KtTokens.LPAR || lookahead(i + 1) != KtTokens.IDENTIFIER || lookahead(i + 2) != KtTokens.RPAR || lookahead(i + 3) != KtTokens.IDENTIFIER) {
            return false;
        }
        PsiBuilder.Marker markerMark = mark();
        advance(i);
        advance();
        if (!str.equals(this.myBuilder.getTokenText())) {
            markerMark.rollbackTo();
            return false;
        }
        advance();
        advance();
        if (z) {
            markerMark.error("Unexpected tokens");
            return true;
        }
        markerMark.rollbackTo();
        return true;
    }

    private void recoverOnPlatformTypeSuffix() {
        if (at(KtTokens.EXCL)) {
            PsiBuilder.Marker markerMark = mark();
            advance();
            markerMark.error("Unexpected token");
        }
    }

    private void parseTypeArgumentList() {
        if (at(KtTokens.LT)) {
            PsiBuilder.Marker markerMark = mark();
            tryParseTypeArgumentList(TokenSet.EMPTY);
            markerMark.done(KtNodeTypes.TYPE_ARGUMENT_LIST);
        }
    }

    boolean tryParseTypeArgumentList(TokenSet tokenSet) {
        this.myBuilder.disableNewlines();
        advance();
        do {
            PsiBuilder.Marker markerMark = mark();
            recoverOnParenthesizedWordForPlatformTypes(0, "out", true);
            parseTypeArgumentModifierList();
            if (at(KtTokens.MUL)) {
                advance();
            } else {
                parseTypeRef(tokenSet);
            }
            markerMark.done(KtNodeTypes.TYPE_PROJECTION);
            if (!at(KtTokens.COMMA)) {
                break;
            }
            advance();
        } while (!at(KtTokens.GT));
        boolean zAt = at(KtTokens.GT);
        if (!zAt) {
            error("Expecting a '>'");
        } else {
            advance();
        }
        this.myBuilder.restoreNewlinesState();
        return zAt;
    }

    private void parseFunctionType(PsiBuilder.Marker marker) {
        parseFunctionTypeContents(marker).done(KtNodeTypes.FUNCTION_TYPE);
    }

    private PsiBuilder.Marker parseFunctionTypeContents(PsiBuilder.Marker marker) {
        parseValueParameterList(true, true, TokenSet.EMPTY);
        expect(KtTokens.ARROW, "Expecting '->' to specify return type of a function type", TYPE_REF_FIRST);
        parseTypeRef();
        return marker;
    }

    private void parseValueParameterList(boolean z, boolean z2, TokenSet tokenSet) {
        PsiBuilder.Marker markerMark = mark();
        this.myBuilder.disableNewlines();
        advance();
        if (!at(KtTokens.RPAR) && !atSet(tokenSet)) {
            while (true) {
                if (at(KtTokens.COMMA)) {
                    errorAndAdvance("Expecting a parameter declaration");
                } else if (!at(KtTokens.RPAR)) {
                    break;
                } else {
                    break;
                }
                if (z) {
                    if (!tryParseValueParameter(z2)) {
                        PsiBuilder.Marker markerMark2 = mark();
                        parseFunctionTypeValueParameterModifierList();
                        parseTypeRef();
                        closeDeclarationWithCommentBinders(markerMark2, KtNodeTypes.VALUE_PARAMETER, false);
                    }
                } else {
                    parseValueParameter(z2);
                }
                if (at(KtTokens.COMMA)) {
                    advance();
                } else if (at(KtTokens.COLON)) {
                    continue;
                } else {
                    if (!at(KtTokens.RPAR)) {
                        error("Expecting comma or ')'");
                    }
                    if (!atSet(z ? LAMBDA_VALUE_PARAMETER_FIRST : VALUE_PARAMETER_FIRST)) {
                        break;
                    }
                }
            }
        }
        expect(KtTokens.RPAR, "Expecting ')'", tokenSet);
        this.myBuilder.restoreNewlinesState();
        markerMark.done(KtNodeTypes.VALUE_PARAMETER_LIST);
    }

    private boolean tryParseValueParameter(boolean z) {
        return parseValueParameter(true, z);
    }

    public void parseValueParameter(boolean z) {
        parseValueParameter(false, z);
    }

    private boolean parseValueParameter(boolean z, boolean z2) {
        PsiBuilder.Marker markerMark = mark();
        parseModifierList(NO_MODIFIER_BEFORE_FOR_VALUE_PARAMETER);
        if (at(KtTokens.VAR_KEYWORD) || at(KtTokens.VAL_KEYWORD)) {
            advance();
        }
        if (!parseFunctionParameterRest(z2) && z) {
            markerMark.rollbackTo();
            return false;
        }
        closeDeclarationWithCommentBinders(markerMark, KtNodeTypes.VALUE_PARAMETER, false);
        return true;
    }

    private boolean parseFunctionParameterRest(boolean z) {
        boolean z2 = false;
        if ((at(KtTokens.IDENTIFIER) && lookahead(1) == KtTokens.LT) || at(KtTokens.COLON)) {
            error("Parameter name expected");
            if (at(KtTokens.COLON)) {
                advance();
                z2 = true;
            }
            parseTypeRef();
        } else {
            KtToken ktToken = KtTokens.IDENTIFIER;
            TokenSet tokenSet = PARAMETER_NAME_RECOVERY_SET;
            expect(ktToken, "Parameter name expected", tokenSet);
            if (at(KtTokens.COLON)) {
                advance();
                if (at(KtTokens.IDENTIFIER) && lookahead(1) == KtTokens.COLON) {
                    error("Type reference expected");
                    return false;
                }
                parseTypeRef();
            } else if (z) {
                errorWithRecovery("Parameters must have type annotation", tokenSet);
            }
            z2 = true;
        }
        if (at(KtTokens.EQ)) {
            advance();
            this.myExpressionParsing.parseExpression();
        }
        return z2;
    }

    @Override // org.jetbrains.kotlin.parsing.AbstractKotlinParsing
    protected KotlinParsing create(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        return createForTopLevel(semanticWhitespaceAwarePsiBuilder);
    }

    static class ModifierDetector implements Consumer<IElementType> {
        private boolean enumDetected = false;
        private boolean companionDetected = false;

        ModifierDetector() {
        }

        @Override // org.jetbrains.kotlin.parsing.Consumer
        public void consume(IElementType iElementType) {
            if (iElementType == KtTokens.ENUM_KEYWORD) {
                this.enumDetected = true;
            } else if (iElementType == KtTokens.COMPANION_KEYWORD) {
                this.companionDetected = true;
            }
        }

        public boolean isEnumDetected() {
            return this.enumDetected;
        }

        public boolean isCompanionDetected() {
            return this.companionDetected;
        }
    }

    enum AnnotationParsingMode {
        DEFAULT(false, true, false, false),
        FILE_ANNOTATIONS_BEFORE_PACKAGE(true, true, false, false),
        FILE_ANNOTATIONS_WHEN_PACKAGE_OMITTED(true, true, false, false),
        TYPE_CONTEXT(false, true, true, false),
        WITH_SIGNIFICANT_WHITESPACE_BEFORE_ARGUMENTS(false, true, true, true),
        NO_ANNOTATIONS(false, false, false, false);

        final boolean allowAnnotations;
        final boolean isFileAnnotationParsingMode;
        final boolean typeContext;
        final boolean withSignificantWhitespaceBeforeArguments;

        AnnotationParsingMode(boolean z, boolean z2, boolean z3, boolean z4) {
            this.isFileAnnotationParsingMode = z;
            this.allowAnnotations = z2;
            this.typeContext = z3;
            this.withSignificantWhitespaceBeforeArguments = z4;
        }
    }
}
