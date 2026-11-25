package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class DeclarationParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JavaParser myParser;
    private static final TokenSet AFTER_END_DECLARATION_SET = TokenSet.create(JavaElementType.FIELD, JavaElementType.METHOD);
    private static final TokenSet BEFORE_LBRACE_ELEMENTS_SET = TokenSet.create(JavaTokenType.IDENTIFIER, JavaTokenType.COMMA, JavaTokenType.EXTENDS_KEYWORD, JavaTokenType.IMPLEMENTS_KEYWORD, JavaTokenType.LPARENTH);
    private static final TokenSet APPEND_TO_METHOD_SET = TokenSet.create(JavaTokenType.IDENTIFIER, JavaTokenType.COMMA, JavaTokenType.THROWS_KEYWORD);
    private static final TokenSet PARAM_LIST_STOPPERS = TokenSet.create(JavaTokenType.RPARENTH, JavaTokenType.LBRACE, JavaTokenType.ARROW);
    private static final TokenSet METHOD_PARAM_LIST_STOPPERS = TokenSet.create(JavaTokenType.RPARENTH, JavaTokenType.LBRACE, JavaTokenType.ARROW, JavaTokenType.SEMICOLON);
    private static final TokenSet TYPE_START = TokenSet.orSet(ElementType.PRIMITIVE_TYPE_BIT_SET, TokenSet.create(JavaTokenType.IDENTIFIER, JavaTokenType.AT, JavaTokenType.VAR_KEYWORD));
    private static final TokenSet RESOURCE_EXPRESSIONS = TokenSet.create(JavaElementType.REFERENCE_EXPRESSION, JavaElementType.THIS_EXPRESSION, JavaElementType.METHOD_CALL_EXPRESSION, JavaElementType.NEW_EXPRESSION);

    public enum Context {
        FILE,
        CLASS,
        CODE_BLOCK,
        ANNOTATION_INTERFACE
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "builder";
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/DeclarationParser";
        } else {
            objArr[0] = "javaParser";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "parseModifierList";
        } else if (i == 4) {
            objArr[1] = "parseMethodBody";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/DeclarationParser";
        } else {
            objArr[1] = "parseAnnotation";
        }
        if (i == 1) {
            objArr[2] = "parse";
        } else if (i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public DeclarationParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public void parseClassBodyWithBraces(PsiBuilder psiBuilder, boolean z, boolean z2) {
        psiBuilder.advanceLexer();
        PsiBuilder psiBuilderBraceMatchingBuilder = JavaParserUtil.braceMatchingBuilder(psiBuilder);
        if (z2) {
            parseEnumConstants(psiBuilderBraceMatchingBuilder);
        }
        parseClassBodyDeclarations(psiBuilderBraceMatchingBuilder, z);
        JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RBRACE, "expected.rbrace");
    }

    private PsiBuilder.Marker parseClassFromKeyword(PsiBuilder psiBuilder, PsiBuilder.Marker marker, boolean z, Context context) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (isRecordToken(psiBuilder, tokenType)) {
            if (psiBuilder.lookAhead(1) != JavaTokenType.IDENTIFIER) {
                marker.drop();
                return null;
            }
            IElementType iElementTypeLookAhead = psiBuilder.lookAhead(2);
            if (context == Context.CODE_BLOCK && iElementTypeLookAhead != JavaTokenType.LPARENTH && iElementTypeLookAhead != JavaTokenType.LT) {
                PsiBuilderUtil.advance(psiBuilder, 2);
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lt.or.lparen", new Object[0]));
                marker.drop();
                return null;
            }
            psiBuilder.remapCurrentToken(JavaTokenType.RECORD_KEYWORD);
            tokenType = JavaTokenType.RECORD_KEYWORD;
        }
        psiBuilder.advanceLexer();
        boolean z2 = tokenType == JavaTokenType.ENUM_KEYWORD;
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            marker.drop();
            return null;
        }
        ReferenceParser referenceParser = this.myParser.getReferenceParser();
        referenceParser.parseTypeParameters(psiBuilder);
        if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
            parseElementList(psiBuilder, ListType.RECORD_COMPONENTS);
        }
        referenceParser.parseReferenceList(psiBuilder, JavaTokenType.EXTENDS_KEYWORD, JavaElementType.EXTENDS_LIST, JavaTokenType.COMMA);
        referenceParser.parseReferenceList(psiBuilder, JavaTokenType.IMPLEMENTS_KEYWORD, JavaElementType.IMPLEMENTS_LIST, JavaTokenType.COMMA);
        if (psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER && PsiKeyword.PERMITS.equals(psiBuilder.getTokenText())) {
            psiBuilder.remapCurrentToken(JavaTokenType.PERMITS_KEYWORD);
        }
        if (psiBuilder.getTokenType() == JavaTokenType.PERMITS_KEYWORD) {
            referenceParser.parseReferenceList(psiBuilder, JavaTokenType.PERMITS_KEYWORD, JavaElementType.PERMITS_LIST, JavaTokenType.COMMA);
        }
        if (psiBuilder.getTokenType() != JavaTokenType.LBRACE) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            while (BEFORE_LBRACE_ELEMENTS_SET.contains(psiBuilder.getTokenType())) {
                psiBuilder.advanceLexer();
            }
            markerM6463mark.error(JavaPsiBundle.message("expected.lbrace", new Object[0]));
        }
        if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
            parseClassBodyWithBraces(psiBuilder, z, z2);
        }
        if (context == Context.FILE) {
            boolean z3 = false;
            while (psiBuilder.getTokenType() != null && psiBuilder.getTokenType() != JavaTokenType.RBRACE) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                PsiBuilder.Marker marker2 = parse(psiBuilder, Context.CLASS);
                if (marker2 != null && AFTER_END_DECLARATION_SET.contains(JavaParserUtil.exprType(marker2))) {
                    if (!z3) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.class.or.interface", new Object[0]), marker2);
                    }
                    markerM6463mark2.drop();
                    z3 = true;
                } else {
                    markerM6463mark2.rollbackTo();
                    break;
                }
            }
            if (z3) {
                JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.RBRACE, "expected.rbrace");
            }
        }
        JavaParserUtil.done(marker, JavaElementType.CLASS);
        return marker;
    }

    private void parseEnumConstants(PsiBuilder psiBuilder) {
        IElementType iElementTypeLookAhead;
        boolean z = true;
        while (psiBuilder.getTokenType() != null && !PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON)) {
            if (psiBuilder.getTokenType() == JavaTokenType.PRIVATE_KEYWORD || psiBuilder.getTokenType() == JavaTokenType.PROTECTED_KEYWORD) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.semicolon", new Object[0]));
                return;
            }
            if (parseEnumConstant(psiBuilder) == null && psiBuilder.getTokenType() == JavaTokenType.COMMA && z && (iElementTypeLookAhead = psiBuilder.lookAhead(1)) != JavaTokenType.SEMICOLON && iElementTypeLookAhead != JavaTokenType.RBRACE) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            }
            int i = 0;
            while (psiBuilder.getTokenType() == JavaTokenType.COMMA) {
                if (i > 0) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                }
                psiBuilder.advanceLexer();
                i++;
            }
            if (i == 0 && psiBuilder.getTokenType() != null && psiBuilder.getTokenType() != JavaTokenType.SEMICOLON) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.comma.or.semicolon", new Object[0]));
                return;
            }
            z = false;
        }
    }

    public PsiBuilder.Marker parseEnumConstant(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        parseModifierList(psiBuilder);
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
                this.myParser.getExpressionParser().parseArgumentList(psiBuilder);
            } else {
                JavaParserUtil.emptyElement(psiBuilder, JavaElementType.EXPRESSION_LIST);
            }
            if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                parseClassBodyWithBraces(psiBuilder, false, false);
                JavaParserUtil.done(markerM6463mark2, JavaElementType.ENUM_CONSTANT_INITIALIZER);
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.ENUM_CONSTANT);
            return markerM6463mark;
        }
        markerM6463mark.rollbackTo();
        return null;
    }

    public void parseClassBodyDeclarations(PsiBuilder psiBuilder, boolean z) {
        PsiBuilder.Marker markerM6463mark;
        PsiBuilder.Marker marker;
        Context context = z ? Context.ANNOTATION_INTERFACE : Context.CLASS;
        loop0: while (true) {
            markerM6463mark = null;
            while (true) {
                IElementType tokenType = psiBuilder.getTokenType();
                if (tokenType == null || tokenType == JavaTokenType.RBRACE) {
                    break loop0;
                }
                if (tokenType == JavaTokenType.SEMICOLON) {
                    if (markerM6463mark != null) {
                        markerM6463mark.error(JavaPsiBundle.message("unexpected.token", new Object[0]));
                        markerM6463mark = null;
                    }
                    psiBuilder.advanceLexer();
                } else {
                    marker = parse(psiBuilder, context);
                    if (marker == null) {
                        if (markerM6463mark == null) {
                            markerM6463mark = psiBuilder.m6463mark();
                        }
                        if (this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, true, false, false) == null) {
                            psiBuilder.advanceLexer();
                        }
                    } else if (markerM6463mark != null) {
                        break;
                    }
                }
            }
            markerM6463mark.errorBefore(JavaPsiBundle.message("unexpected.token", new Object[0]), marker);
        }
        if (markerM6463mark != null) {
            markerM6463mark.error(JavaPsiBundle.message("unexpected.token", new Object[0]));
        }
    }

    public PsiBuilder.Marker parse(PsiBuilder psiBuilder, Context context) {
        boolean z;
        int i;
        ReferenceParser.TypeInfo typeInfo;
        int i2;
        boolean z2;
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == null) {
            return null;
        }
        if (tokenType == JavaTokenType.LBRACE) {
            if (context == Context.FILE || context == Context.CODE_BLOCK) {
                return null;
            }
        } else if (!isRecordToken(psiBuilder, tokenType) && !isSealedToken(psiBuilder, tokenType) && !isNonSealedToken(psiBuilder, tokenType)) {
            if (TYPE_START.contains(tokenType) && tokenType != JavaTokenType.AT) {
                if (context == Context.FILE) {
                    return null;
                }
            } else {
                if (tokenType instanceof ILazyParseableElementType) {
                    psiBuilder.advanceLexer();
                    return null;
                }
                if (!ElementType.MODIFIER_BIT_SET.contains(tokenType) && !ElementType.CLASS_KEYWORD_BIT_SET.contains(tokenType) && tokenType != JavaTokenType.AT && (context == Context.CODE_BLOCK || tokenType != JavaTokenType.LT)) {
                    return null;
                }
            }
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        int currentOffset = psiBuilder.getCurrentOffset();
        Pair<PsiBuilder.Marker, Boolean> modifierList = parseModifierList(psiBuilder);
        PsiBuilder.Marker marker = modifierList.first;
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.AT)) {
            if (psiBuilder.getTokenType() == JavaTokenType.INTERFACE_KEYWORD) {
                PsiBuilder.Marker classFromKeyword = parseClassFromKeyword(psiBuilder, markerM6463mark, true, context);
                return classFromKeyword != null ? classFromKeyword : marker;
            }
            markerM6463mark.rollbackTo();
            return null;
        }
        if (ElementType.CLASS_KEYWORD_BIT_SET.contains(psiBuilder.getTokenType()) || isRecordToken(psiBuilder, psiBuilder.getTokenType())) {
            PsiBuilder.Marker classFromKeyword2 = parseClassFromKeyword(psiBuilder, markerM6463mark, false, context);
            return classFromKeyword2 != null ? classFromKeyword2 : marker;
        }
        PsiBuilder.Marker typeParameters = (psiBuilder.getTokenType() != JavaTokenType.LT || context == Context.CODE_BLOCK) ? null : this.myParser.getReferenceParser().parseTypeParameters(psiBuilder);
        if (context == Context.FILE) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.class.or.interface", new Object[0]), typeParameters);
            markerM6463mark.drop();
            return marker;
        }
        if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
            if (context == Context.CODE_BLOCK) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier.or.type", new Object[0]), null);
                markerM6463mark.drop();
                return marker;
            }
            PsiBuilder.Marker codeBlock = this.myParser.getStatementParser().parseCodeBlock(psiBuilder);
            if (typeParameters != null) {
                typeParameters.m6464precede().errorBefore(JavaPsiBundle.message("unexpected.token", new Object[0]), codeBlock);
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.CLASS_INITIALIZER);
            return markerM6463mark;
        }
        if (TYPE_START.contains(psiBuilder.getTokenType())) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            ReferenceParser.TypeInfo typeInfo2 = this.myParser.getReferenceParser().parseTypeInfo(psiBuilder, context == Context.CODE_BLOCK ? 133 : 5);
            if (typeInfo2 == null) {
                markerM6463mark2.rollbackTo();
            } else {
                if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH || psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                    if (context == Context.CODE_BLOCK) {
                        markerM6463mark.rollbackTo();
                        return null;
                    }
                    markerM6463mark2.rollbackTo();
                    if (typeParameters == null) {
                        JavaParserUtil.emptyElement(psiBuilder, JavaElementType.TYPE_PARAMETER_LIST);
                    }
                    parseAnnotations(psiBuilder);
                    if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
                        z2 = false;
                    } else {
                        PsiBuilder.Marker markerM6463mark3 = psiBuilder.m6463mark();
                        psiBuilder.advanceLexer();
                        z2 = false;
                        markerM6463mark3.error(JavaPsiBundle.message("expected.identifier", new Object[0]));
                    }
                    if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH) {
                        return parseMethodFromLeftParenth(psiBuilder, markerM6463mark, z2, true);
                    }
                    if (psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
                        JavaParserUtil.emptyElement(psiBuilder, JavaElementType.THROWS_LIST);
                        return parseMethodBody(psiBuilder, markerM6463mark, z2);
                    }
                    markerM6463mark.rollbackTo();
                    return null;
                }
                markerM6463mark2.drop();
            }
            typeInfo = typeInfo2;
            i = 0;
            z = true;
        } else {
            z = true;
            i = 0;
            typeInfo = null;
        }
        if (typeInfo == null) {
            (typeParameters != null ? typeParameters.m6464precede() : psiBuilder.m6463mark()).error(JavaPsiBundle.message("expected.identifier.or.type", new Object[i]));
            markerM6463mark.drop();
            return marker;
        }
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            if (context != Context.CODE_BLOCK || Boolean.FALSE.equals(modifierList.second) || (typeInfo.isPrimitive && psiBuilder.getTokenType() != JavaTokenType.DOT)) {
                if (typeParameters != null) {
                    i2 = 0;
                    typeParameters.m6464precede().errorBefore(JavaPsiBundle.message("unexpected.token", new Object[0]), typeInfo.marker);
                } else {
                    i2 = 0;
                }
                psiBuilder.error(JavaPsiBundle.message("expected.identifier", new Object[i2]));
                markerM6463mark.drop();
                return marker;
            }
            markerM6463mark.rollbackTo();
            return null;
        }
        if (psiBuilder.getTokenType() == JavaTokenType.LPARENTH && (context == Context.CLASS || context == Context.ANNOTATION_INTERFACE)) {
            if (typeParameters == null) {
                JavaParserUtil.emptyElement(typeInfo.marker, JavaElementType.TYPE_PARAMETER_LIST);
            }
            return parseMethodFromLeftParenth(psiBuilder, markerM6463mark, context == Context.ANNOTATION_INTERFACE ? z : false, false);
        }
        if (typeParameters != null) {
            typeParameters.m6464precede().errorBefore(JavaPsiBundle.message("unexpected.token", new Object[0]), typeInfo.marker);
        }
        return parseFieldOrLocalVariable(psiBuilder, markerM6463mark, currentOffset, context);
    }

    static boolean isRecordToken(PsiBuilder psiBuilder, IElementType iElementType) {
        if (iElementType == JavaTokenType.IDENTIFIER && PsiKeyword.RECORD.equals(psiBuilder.getTokenText()) && psiBuilder.lookAhead(1) == JavaTokenType.IDENTIFIER) {
            return JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_16);
        }
        return false;
    }

    private static boolean isSealedToken(PsiBuilder psiBuilder, IElementType iElementType) {
        return JavaLexer.isSealedAvailable(JavaParserUtil.getLanguageLevel(psiBuilder)) && iElementType == JavaTokenType.IDENTIFIER && "sealed".equals(psiBuilder.getTokenText());
    }

    private static boolean isNonSealedToken(PsiBuilder psiBuilder, IElementType iElementType) {
        if (!JavaLexer.isSealedAvailable(JavaParserUtil.getLanguageLevel(psiBuilder)) || iElementType != JavaTokenType.IDENTIFIER || !"non".equals(psiBuilder.getTokenText()) || psiBuilder.lookAhead(1) != JavaTokenType.MINUS || psiBuilder.lookAhead(2) != JavaTokenType.IDENTIFIER) {
            return false;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilderUtil.advance(psiBuilder, 2);
        boolean zEquals = "sealed".equals(psiBuilder.getTokenText());
        markerM6463mark.rollbackTo();
        return zEquals;
    }

    public Pair<PsiBuilder.Marker, Boolean> parseModifierList(PsiBuilder psiBuilder) {
        Pair<PsiBuilder.Marker, Boolean> modifierList = parseModifierList(psiBuilder, ElementType.MODIFIER_BIT_SET);
        if (modifierList == null) {
            $$$reportNull$$$0(2);
        }
        return modifierList;
    }

    public Pair<PsiBuilder.Marker, Boolean> parseModifierList(PsiBuilder psiBuilder, TokenSet tokenSet) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean z = true;
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == null) {
                break;
            }
            if (isSealedToken(psiBuilder, tokenType)) {
                psiBuilder.remapCurrentToken(JavaTokenType.SEALED_KEYWORD);
                tokenType = JavaTokenType.SEALED_KEYWORD;
            }
            if (isNonSealedToken(psiBuilder, tokenType)) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                PsiBuilderUtil.advance(psiBuilder, 3);
                markerM6463mark2.collapse(JavaTokenType.NON_SEALED_KEYWORD);
            } else if (tokenSet.contains(tokenType)) {
                psiBuilder.advanceLexer();
            } else {
                if (tokenType != JavaTokenType.AT || ElementType.KEYWORD_BIT_SET.contains(psiBuilder.lookAhead(1))) {
                    break;
                }
                parseAnnotation(psiBuilder);
            }
            z = false;
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.MODIFIER_LIST);
        Pair<PsiBuilder.Marker, Boolean> pairCreate = Pair.create(markerM6463mark, Boolean.valueOf(z));
        if (pairCreate == null) {
            $$$reportNull$$$0(3);
        }
        return pairCreate;
    }

    private PsiBuilder.Marker parseMethodFromLeftParenth(PsiBuilder psiBuilder, PsiBuilder.Marker marker, boolean z, boolean z2) {
        parseParameterList(psiBuilder);
        eatBrackets(psiBuilder, z2 ? "expected.semicolon" : null);
        this.myParser.getReferenceParser().parseReferenceList(psiBuilder, JavaTokenType.THROWS_KEYWORD, JavaElementType.THROWS_LIST, JavaTokenType.COMMA);
        if (z && PsiBuilderUtil.expect(psiBuilder, JavaTokenType.DEFAULT_KEYWORD)) {
            parseAnnotationValue(psiBuilder);
        }
        return parseMethodBody(psiBuilder, marker, z);
    }

    private PsiBuilder.Marker parseMethodBody(PsiBuilder psiBuilder, PsiBuilder.Marker marker, boolean z) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType != JavaTokenType.SEMICOLON && tokenType != JavaTokenType.LBRACE) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            CharSequence originalText = psiBuilder.getOriginalText();
            loop0: do {
                for (int currentOffset = psiBuilder.getCurrentOffset() - 1; currentOffset >= 0; currentOffset--) {
                    char cCharAt = originalText.charAt(currentOffset);
                    if (cCharAt == '\n') {
                        break loop0;
                    }
                    if (cCharAt != ' ' && cCharAt != '\t') {
                        break;
                    }
                }
            } while (PsiBuilderUtil.expect(psiBuilder, APPEND_TO_METHOD_SET));
            markerM6463mark.error(JavaPsiBundle.message("expected.lbrace.or.semicolon", new Object[0]));
        }
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON) && psiBuilder.getTokenType() == JavaTokenType.LBRACE) {
            this.myParser.getStatementParser().parseCodeBlock(psiBuilder);
        }
        JavaParserUtil.done(marker, z ? JavaElementType.ANNOTATION_METHOD : JavaElementType.METHOD);
        if (marker == null) {
            $$$reportNull$$$0(4);
        }
        return marker;
    }

    public void parseParameterList(PsiBuilder psiBuilder) {
        parseElementList(psiBuilder, ListType.METHOD);
    }

    public void parseResourceList(PsiBuilder psiBuilder) {
        parseElementList(psiBuilder, ListType.RESOURCE);
    }

    public void parseLambdaParameterList(PsiBuilder psiBuilder, boolean z) {
        parseElementList(psiBuilder, z ? ListType.LAMBDA_TYPED : ListType.LAMBDA_UNTYPED);
    }

    private enum ListType {
        METHOD,
        RESOURCE,
        LAMBDA_TYPED,
        LAMBDA_UNTYPED,
        RECORD_COMPONENTS;

        IElementType getNodeType() {
            if (this == RESOURCE) {
                return JavaElementType.RESOURCE_LIST;
            }
            if (this == RECORD_COMPONENTS) {
                return JavaElementType.RECORD_HEADER;
            }
            return JavaElementType.PARAMETER_LIST;
        }

        TokenSet getStopperTypes() {
            return this == METHOD ? DeclarationParser.METHOD_PARAM_LIST_STOPPERS : DeclarationParser.PARAM_LIST_STOPPERS;
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0173  */
    /* JADX WARN: Code duplicated, block: B:111:0x00fc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:112:0x00e9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:115:0x00d3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:66:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:67:0x00f4  */
    private void parseElementList(PsiBuilder psiBuilder, ListType listType) {
        IElementType tokenType;
        String str;
        String str2;
        String str3;
        String str4;
        PsiBuilder.Marker marker;
        PsiBuilder.Marker parameter;
        boolean z = true;
        boolean z2 = listType == ListType.LAMBDA_TYPED || listType == ListType.LAMBDA_UNTYPED;
        boolean z3 = listType == ListType.RESOURCE;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean zExpect = PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LPARENTH);
        IElementType iElementType = z3 ? JavaTokenType.SEMICOLON : JavaTokenType.COMMA;
        String str5 = z3 ? "expected.semicolon" : "expected.comma";
        String str6 = "expected.parameter";
        String str7 = z3 ? "expected.resource" : "expected.parameter";
        PsiBuilder.Marker marker2 = null;
        boolean z4 = true;
        boolean z5 = false;
        String strMessage = null;
        PsiBuilder.Marker markerM6463mark2 = null;
        while (true) {
            tokenType = psiBuilder.getTokenType();
            if (tokenType == null || listType.getStopperTypes().contains(tokenType)) {
                break;
            }
            if (z5) {
                if (psiBuilder.getTokenType() == iElementType) {
                    if (markerM6463mark2 != null) {
                        markerM6463mark2.error(strMessage);
                        markerM6463mark2 = null;
                    }
                    psiBuilder.advanceLexer();
                    z5 = false;
                } else {
                    str = str7;
                    if (markerM6463mark2 == null) {
                        str2 = str;
                    } else if (psiBuilder.getTokenType() == iElementType) {
                        str7 = str;
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(str7, new Object[0]));
                        psiBuilder.advanceLexer();
                        if (!z4 && z3) {
                            z4 = false;
                        }
                    } else {
                        str2 = str;
                        markerM6463mark2 = psiBuilder.m6463mark();
                        if (z5) {
                            str3 = str5;
                        } else {
                            str3 = str2;
                        }
                        strMessage = JavaPsiBundle.message(str3, new Object[0]);
                    }
                    String str8 = strMessage;
                    PsiBuilder.Marker marker3 = markerM6463mark2;
                    String str9 = str2;
                    if (this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, true, false, false) == null && psiBuilder.getTokenType() != null) {
                        psiBuilder.advanceLexer();
                    }
                    strMessage = str8;
                    markerM6463mark2 = marker3;
                    str7 = str9;
                }
            } else {
                if (listType == ListType.RECORD_COMPONENTS) {
                    str4 = strMessage;
                    marker = markerM6463mark2;
                    str = str7;
                    parameter = parseParameterOrRecordComponent(psiBuilder, true, false, false, false);
                } else {
                    str4 = strMessage;
                    marker = markerM6463mark2;
                    str = str7;
                    if (z3) {
                        parameter = parseResource(psiBuilder);
                    } else if (z2) {
                        parameter = parseLambdaParameter(psiBuilder, listType == ListType.LAMBDA_TYPED);
                    } else {
                        parameter = parseParameter(psiBuilder, true, false, false);
                    }
                }
                if (parameter != null) {
                    markerM6463mark2 = marker;
                    strMessage = str4;
                    if (markerM6463mark2 != null) {
                        markerM6463mark2.errorBefore(strMessage, parameter);
                        markerM6463mark2 = null;
                    }
                    z5 = true;
                    z4 = false;
                    str7 = str;
                } else {
                    strMessage = str4;
                    markerM6463mark2 = marker;
                    if (markerM6463mark2 == null) {
                        str2 = str;
                    } else if (psiBuilder.getTokenType() == iElementType) {
                        str7 = str;
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(str7, new Object[0]));
                        psiBuilder.advanceLexer();
                        if (!z4) {
                        }
                    } else {
                        str2 = str;
                        markerM6463mark2 = psiBuilder.m6463mark();
                        if (z5) {
                            str3 = str5;
                        } else {
                            str3 = str2;
                        }
                        strMessage = JavaPsiBundle.message(str3, new Object[0]);
                    }
                    String str10 = strMessage;
                    PsiBuilder.Marker marker4 = markerM6463mark2;
                    String str11 = str2;
                    if (this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, true, false, false) == null) {
                        psiBuilder.advanceLexer();
                    }
                    strMessage = str10;
                    markerM6463mark2 = marker4;
                    str7 = str11;
                }
            }
        }
        if (z5 || ((z4 || z3) && (!z4 || !z3))) {
            z = false;
        }
        if (z) {
            if (!z2) {
                str6 = "expected.identifier.or.type";
            }
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message(str6, new Object[0]));
        }
        if (tokenType == JavaTokenType.RPARENTH) {
            if (markerM6463mark2 != null) {
                markerM6463mark2.error(strMessage);
            } else {
                marker2 = markerM6463mark2;
            }
            psiBuilder.advanceLexer();
        } else {
            if (!z || z3) {
                if (markerM6463mark2 != null) {
                    markerM6463mark2.error(strMessage);
                }
                if (zExpect) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rparen", new Object[0]));
                }
            }
            if (markerM6463mark2 != null) {
                markerM6463mark2.error(strMessage);
            }
            JavaParserUtil.done(markerM6463mark, listType.getNodeType());
        }
        markerM6463mark2 = marker2;
        if (markerM6463mark2 != null) {
            markerM6463mark2.error(strMessage);
        }
        JavaParserUtil.done(markerM6463mark, listType.getNodeType());
    }

    public PsiBuilder.Marker parseParameter(PsiBuilder psiBuilder, boolean z, boolean z2, boolean z3) {
        return parseParameterOrRecordComponent(psiBuilder, z, z2, z3, true);
    }

    public PsiBuilder.Marker parseParameterOrRecordComponent(PsiBuilder psiBuilder, boolean z, boolean z2, boolean z3, boolean z4) {
        int i = z ? 2 : 0;
        if (z2) {
            i |= 16;
        }
        if (z3) {
            i |= 128;
        }
        return parseListElement(psiBuilder, true, i, z4 ? JavaElementType.PARAMETER : JavaElementType.RECORD_COMPONENT);
    }

    public PsiBuilder.Marker parseResource(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker marker = this.myParser.getExpressionParser().parse(psiBuilder);
        if (marker != null && RESOURCE_EXPRESSIONS.contains(JavaParserUtil.exprType(marker)) && psiBuilder.getTokenType() != JavaTokenType.IDENTIFIER) {
            markerM6463mark.done(JavaElementType.RESOURCE_EXPRESSION);
            return markerM6463mark;
        }
        markerM6463mark.rollbackTo();
        return parseListElement(psiBuilder, true, 128, JavaElementType.RESOURCE_VARIABLE);
    }

    public PsiBuilder.Marker parseLambdaParameter(PsiBuilder psiBuilder, boolean z) {
        return parseListElement(psiBuilder, z, JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_11) ? 130 : 2, JavaElementType.PARAMETER);
    }

    private PsiBuilder.Marker parseListElement(PsiBuilder psiBuilder, boolean z, int i, IElementType iElementType) {
        ReferenceParser.TypeInfo typeInfo;
        IElementType tokenType;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        Pair<PsiBuilder.Marker, Boolean> modifierList = parseModifierList(psiBuilder);
        String str = null;
        if (z) {
            typeInfo = this.myParser.getReferenceParser().parseTypeInfo(psiBuilder, i | 5);
            if (typeInfo == null) {
                if (Boolean.TRUE.equals(modifierList.second)) {
                    markerM6463mark.rollbackTo();
                    return null;
                }
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.type", new Object[0]));
                JavaParserUtil.emptyElement(psiBuilder, JavaElementType.TYPE);
            }
        } else {
            typeInfo = null;
        }
        if (z && ((tokenType = psiBuilder.getTokenType()) == JavaTokenType.THIS_KEYWORD || (tokenType == JavaTokenType.IDENTIFIER && psiBuilder.lookAhead(1) == JavaTokenType.DOT))) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            PsiBuilder.Marker marker = this.myParser.getExpressionParser().parse(psiBuilder);
            if (marker != null && JavaParserUtil.exprType(marker) == JavaElementType.THIS_EXPRESSION) {
                markerM6463mark2.drop();
                JavaParserUtil.done(markerM6463mark, JavaElementType.RECEIVER_PARAMETER);
                return markerM6463mark;
            }
            markerM6463mark2.rollbackTo();
        }
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            if (iElementType == JavaElementType.PARAMETER || iElementType == JavaElementType.RECORD_COMPONENT) {
                if (typeInfo != null && typeInfo.isVarArg) {
                    str = "expected.rparen";
                }
                eatBrackets(psiBuilder, str);
                JavaParserUtil.done(markerM6463mark, iElementType);
                return markerM6463mark;
            }
            if (JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.EQ, "expected.eq") && this.myParser.getExpressionParser().parse(psiBuilder) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.RESOURCE_VARIABLE);
            return markerM6463mark;
        }
        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
        markerM6463mark.drop();
        return modifierList.first;
    }

    private PsiBuilder.Marker parseFieldOrLocalVariable(PsiBuilder psiBuilder, PsiBuilder.Marker marker, int i, Context context) {
        IElementType iElementType;
        boolean z;
        boolean z2;
        boolean z3;
        if (context == Context.CLASS || context == Context.ANNOTATION_INTERFACE) {
            iElementType = JavaElementType.FIELD;
        } else if (context == Context.CODE_BLOCK) {
            iElementType = JavaElementType.LOCAL_VARIABLE;
        } else {
            marker.drop();
            return null;
        }
        PsiBuilder.Marker markerM6463mark = marker;
        boolean z4 = false;
        while (true) {
            z = true;
            if (!eatBrackets(psiBuilder, null)) {
                z4 = true;
            }
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.EQ)) {
                z2 = true;
            } else {
                if (this.myParser.getExpressionParser().parse(psiBuilder) == null) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.expression", new Object[0]));
                    z3 = true;
                    z4 = true;
                    z2 = true;
                    break;
                }
                z2 = false;
            }
            if (psiBuilder.getTokenType() != JavaTokenType.COMMA) {
                z3 = true;
                break;
            }
            JavaParserUtil.done(markerM6463mark, iElementType);
            psiBuilder.advanceLexer();
            if (psiBuilder.getTokenType() != JavaTokenType.IDENTIFIER) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                z3 = false;
                z4 = true;
                z = false;
                break;
            }
            markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
        }
        if (psiBuilder.getTokenType() == JavaTokenType.SEMICOLON && z) {
            psiBuilder.advanceLexer();
        } else {
            if (!psiBuilder.eof() && z2) {
                CharSequence originalText = psiBuilder.getOriginalText();
                int currentOffset = psiBuilder.getCurrentOffset();
                int iShiftBackward = CharArrayUtil.shiftBackward(originalText, currentOffset - 1, "\n\r \t");
                int iShiftBackwardUntil = CharArrayUtil.shiftBackwardUntil(originalText, currentOffset, "\n\r");
                if (i < iShiftBackwardUntil && iShiftBackwardUntil < iShiftBackward) {
                    int iShiftForward = CharArrayUtil.shiftForward(originalText, iShiftBackwardUntil, "\n\r \t");
                    marker.rollbackTo();
                    return parse(JavaParserUtil.stoppingBuilder(psiBuilder, iShiftForward), context);
                }
            }
            if (!z4) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.semicolon", new Object[0]));
            }
        }
        if (z3) {
            JavaParserUtil.done(markerM6463mark, iElementType);
        }
        return marker;
    }

    private boolean eatBrackets(PsiBuilder psiBuilder, String str) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType != JavaTokenType.LBRACKET && tokenType != JavaTokenType.AT) {
            return true;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        int i = 0;
        while (true) {
            parseAnnotations(psiBuilder);
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LBRACKET)) {
                break;
            }
            int i2 = i + 1;
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RBRACKET)) {
                i = i2;
                break;
            }
            i += 2;
        }
        if (i == 0) {
            markerM6463mark.rollbackTo();
            return true;
        }
        if (str != null) {
            markerM6463mark.error(JavaPsiBundle.message(str, new Object[0]));
        } else {
            markerM6463mark.drop();
        }
        boolean z = i % 2 == 0;
        if (!z) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rbracket", new Object[0]));
        }
        return z;
    }

    public PsiBuilder.Marker parseAnnotations(PsiBuilder psiBuilder) {
        PsiBuilder.Marker marker = null;
        while (psiBuilder.getTokenType() == JavaTokenType.AT) {
            PsiBuilder.Marker annotation = parseAnnotation(psiBuilder);
            if (marker == null) {
                marker = annotation;
            }
        }
        return marker;
    }

    public PsiBuilder.Marker parseAnnotation(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        if ((psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER ? this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, false, false, false) : null) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.class.reference", new Object[0]));
        }
        parseAnnotationParameterList(psiBuilder);
        JavaParserUtil.done(markerM6463mark, JavaElementType.ANNOTATION);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(5);
        }
        return markerM6463mark;
    }

    private void parseAnnotationParameterList(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LPARENTH)) {
            JavaParserUtil.done(markerM6463mark, JavaElementType.ANNOTATION_PARAMETER_LIST);
            return;
        }
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
            JavaParserUtil.done(markerM6463mark, JavaElementType.ANNOTATION_PARAMETER_LIST);
            return;
        }
        boolean annotationParameter = parseAnnotationParameter(psiBuilder, true);
        boolean z = false;
        boolean z2 = false;
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.parameter", new Object[0]));
                break;
            }
            if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RPARENTH)) {
                break;
            }
            if (tokenType == JavaTokenType.COMMA) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                PsiBuilder.Marker markerM6463mark3 = psiBuilder.m6463mark();
                psiBuilder.advanceLexer();
                boolean annotationParameter2 = parseAnnotationParameter(psiBuilder, false);
                if (!annotationParameter && annotationParameter2 && !z2) {
                    markerM6463mark2.errorBefore(JavaPsiBundle.message("annotation.name.is.missing", new Object[0]), markerM6463mark3);
                    z2 = true;
                } else {
                    markerM6463mark2.drop();
                }
                markerM6463mark3.drop();
            } else if (!z) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.comma.or.rparen", new Object[0]));
                psiBuilder.advanceLexer();
                z = true;
            } else {
                parseAnnotationParameter(psiBuilder, false);
                z = false;
            }
        }
        JavaParserUtil.done(markerM6463mark, JavaElementType.ANNOTATION_PARAMETER_LIST);
    }

    private boolean parseAnnotationParameter(PsiBuilder psiBuilder, boolean z) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (z) {
            parseAnnotationValue(psiBuilder);
            if (psiBuilder.getTokenType() != JavaTokenType.EQ) {
                JavaParserUtil.done(markerM6463mark, JavaElementType.NAME_VALUE_PAIR);
                return false;
            }
            markerM6463mark.rollbackTo();
            markerM6463mark = psiBuilder.m6463mark();
        }
        boolean zExpectOrError = JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.IDENTIFIER, "expected.identifier");
        JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.EQ, "expected.eq");
        parseAnnotationValue(psiBuilder);
        JavaParserUtil.done(markerM6463mark, JavaElementType.NAME_VALUE_PAIR);
        return zExpectOrError;
    }

    public void parseAnnotationValue(PsiBuilder psiBuilder) {
        if (doParseAnnotationValue(psiBuilder) == null) {
            psiBuilder.m6463mark().error(JavaPsiBundle.message("expected.value", new Object[0]));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiBuilder.Marker doParseAnnotationValue(PsiBuilder psiBuilder) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == JavaTokenType.AT) {
            return parseAnnotation(psiBuilder);
        }
        if (tokenType == JavaTokenType.LBRACE) {
            return this.myParser.getExpressionParser().parseArrayInitializer(psiBuilder, JavaElementType.ANNOTATION_ARRAY_INITIALIZER, new Function() { // from class: org.jetbrains.kotlin.com.intellij.lang.java.parser.DeclarationParser$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.doParseAnnotationValue((PsiBuilder) obj);
                }
            }, "expected.value");
        }
        return this.myParser.getExpressionParser().parseConditional(psiBuilder, 0);
    }
}
