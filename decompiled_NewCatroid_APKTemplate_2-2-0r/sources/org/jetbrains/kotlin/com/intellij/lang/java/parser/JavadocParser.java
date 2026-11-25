package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavadocParser {
    private static final TokenSet TAG_VALUES_SET = TokenSet.create(JavaDocTokenType.DOC_TAG_VALUE_TOKEN, JavaDocTokenType.DOC_TAG_VALUE_COMMA, JavaDocTokenType.DOC_TAG_VALUE_DOT, JavaDocTokenType.DOC_TAG_VALUE_LPAREN, JavaDocTokenType.DOC_TAG_VALUE_RPAREN, JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN, JavaDocTokenType.DOC_TAG_VALUE_LT, JavaDocTokenType.DOC_TAG_VALUE_GT);
    private static final TokenSet INLINE_TAG_BORDERS_SET = TokenSet.create(JavaDocTokenType.DOC_INLINE_TAG_START, JavaDocTokenType.DOC_INLINE_TAG_END);
    private static final TokenSet SKIP_TOKENS = TokenSet.create(JavaDocTokenType.DOC_COMMENT_LEADING_ASTERISKS);
    private static final Set<String> REFERENCE_TAGS = ContainerUtil.set("@throws", "@exception", "@provides", "@uses");
    private static final Key<Integer> BRACE_SCOPE_KEY = Key.create("Javadoc.Parser.Brace.Scope");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "builder";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/JavadocParser";
        if (i == 1) {
            objArr[2] = "parseJavadocType";
        } else if (i != 2) {
            objArr[2] = "parseJavadocReference";
        } else {
            objArr[2] = "parseDocCommentText";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void parseJavadocReference(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(0);
        }
        JavaParser.INSTANCE.getReferenceParser().parseJavaCodeReference(psiBuilder, true, true, false, false);
        swallowTokens(psiBuilder);
    }

    public static void parseJavadocType(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        JavaParser.INSTANCE.getReferenceParser().parseType(psiBuilder, 7);
        swallowTokens(psiBuilder);
    }

    private static void swallowTokens(PsiBuilder psiBuilder) {
        while (!psiBuilder.eof()) {
            psiBuilder.advanceLexer();
        }
    }

    public static void parseDocCommentText(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(2);
        }
        psiBuilder.enforceCommentTokens(SKIP_TOKENS);
        while (!psiBuilder.eof()) {
            if (getTokenType(psiBuilder) == JavaDocTokenType.DOC_TAG_NAME) {
                parseTag(psiBuilder);
            } else {
                parseDataItem(psiBuilder, null, false);
            }
        }
    }

    private static void parseTag(PsiBuilder psiBuilder) {
        String tokenText = psiBuilder.getTokenText();
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        while (true) {
            IElementType tokenType = getTokenType(psiBuilder);
            if (tokenType == null || tokenType == JavaDocTokenType.DOC_TAG_NAME || tokenType == JavaDocTokenType.DOC_COMMENT_END) {
                break;
            } else {
                parseDataItem(psiBuilder, tokenText, false);
            }
        }
        markerM6463mark.done(JavaDocElementType.DOC_TAG);
    }

    private static void parseDataItem(PsiBuilder psiBuilder, String str, boolean z) {
        IElementType tokenType = getTokenType(psiBuilder);
        if (tokenType == JavaDocTokenType.DOC_INLINE_TAG_START) {
            int braceScope = getBraceScope(psiBuilder);
            if (braceScope > 0) {
                setBraceScope(psiBuilder, braceScope + 1);
                psiBuilder.remapCurrentToken(JavaDocTokenType.DOC_COMMENT_DATA);
                psiBuilder.advanceLexer();
                return;
            }
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            IElementType tokenType2 = getTokenType(psiBuilder);
            if (tokenType2 != JavaDocTokenType.DOC_TAG_NAME && tokenType2 != JavaDocTokenType.DOC_COMMENT_BAD_CHARACTER) {
                markerM6463mark.rollbackTo();
                psiBuilder.remapCurrentToken(JavaDocTokenType.DOC_COMMENT_DATA);
                psiBuilder.advanceLexer();
                return;
            }
            setBraceScope(psiBuilder, braceScope + 1);
            String tokenText = "";
            while (true) {
                IElementType tokenType3 = getTokenType(psiBuilder);
                if (tokenType3 == JavaDocTokenType.DOC_TAG_NAME) {
                    tokenText = psiBuilder.getTokenText();
                } else {
                    if (tokenType3 != null && tokenType3 != JavaDocTokenType.DOC_COMMENT_END) {
                        break;
                        break;
                    }
                    break;
                }
                parseDataItem(psiBuilder, tokenText, true);
                if (tokenType3 == JavaDocTokenType.DOC_INLINE_TAG_END) {
                    int braceScope2 = getBraceScope(psiBuilder);
                    if (braceScope2 > 0) {
                        braceScope2--;
                        setBraceScope(psiBuilder, braceScope2);
                    }
                    if (braceScope2 == 0) {
                        break;
                    }
                }
            }
            markerM6463mark.done(JavaDocElementType.DOC_INLINE_TAG);
            return;
        }
        if (TAG_VALUES_SET.contains(tokenType)) {
            if (("@see".equals(str) && !z) || (("@link".equals(str) && z) || (JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_1_4) && "@linkplain".equals(str) && z))) {
                parseSeeTagValue(psiBuilder, false);
                return;
            }
            if (!z && REFERENCE_TAGS.contains(str)) {
                PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                psiBuilder.remapCurrentToken(JavaDocElementType.DOC_REFERENCE_HOLDER);
                psiBuilder.advanceLexer();
                markerM6463mark2.done(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
                return;
            }
            if (!z && "@param".equals(str)) {
                parseParameterRef(psiBuilder);
                return;
            } else if (JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_1_5) && "@value".equals(str) && z) {
                parseSeeTagValue(psiBuilder, true);
                return;
            } else {
                parseSimpleTagValue(psiBuilder);
                return;
            }
        }
        remapAndAdvance(psiBuilder);
    }

    private static void parseSeeTagValue(PsiBuilder psiBuilder, boolean z) {
        IElementType tokenType = getTokenType(psiBuilder);
        if (tokenType == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
            parseMethodRef(psiBuilder, psiBuilder.m6463mark());
            return;
        }
        if (tokenType == JavaDocTokenType.DOC_TAG_VALUE_TOKEN) {
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            psiBuilder.remapCurrentToken(JavaDocElementType.DOC_REFERENCE_HOLDER);
            psiBuilder.advanceLexer();
            if (getTokenType(psiBuilder) == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
                parseMethodRef(psiBuilder, markerM6463mark);
                return;
            } else {
                if (z) {
                    markerM6463mark.rollbackTo();
                    psiBuilder.remapCurrentToken(JavaDocTokenType.DOC_TAG_VALUE_TOKEN);
                    parseMethodRef(psiBuilder, psiBuilder.m6463mark());
                    return;
                }
                markerM6463mark.drop();
                return;
            }
        }
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        markerM6463mark2.done(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
    }

    private static void parseMethodRef(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        if (getTokenType(psiBuilder) == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
            psiBuilder.advanceLexer();
        }
        if (getTokenType(psiBuilder) != JavaDocTokenType.DOC_TAG_VALUE_TOKEN) {
            marker.done(JavaDocElementType.DOC_METHOD_OR_FIELD_REF);
            return;
        }
        psiBuilder.advanceLexer();
        if (getTokenType(psiBuilder) == JavaDocTokenType.DOC_TAG_VALUE_LPAREN) {
            psiBuilder.advanceLexer();
            PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
            while (true) {
                TokenSet tokenSet = TAG_VALUES_SET;
                IElementType tokenType = getTokenType(psiBuilder);
                if (tokenSet.contains(tokenType)) {
                    if (tokenType == JavaDocTokenType.DOC_TAG_VALUE_TOKEN) {
                        psiBuilder.remapCurrentToken(JavaDocElementType.DOC_TYPE_HOLDER);
                        psiBuilder.advanceLexer();
                        while (true) {
                            TokenSet tokenSet2 = TAG_VALUES_SET;
                            IElementType tokenType2 = getTokenType(psiBuilder);
                            if (!tokenSet2.contains(tokenType2) || tokenType2 == JavaDocTokenType.DOC_TAG_VALUE_COMMA || tokenType2 == JavaDocTokenType.DOC_TAG_VALUE_RPAREN) {
                                break;
                            } else {
                                psiBuilder.advanceLexer();
                            }
                        }
                    } else {
                        if (tokenType == JavaDocTokenType.DOC_TAG_VALUE_RPAREN) {
                            markerM6463mark.done(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
                            psiBuilder.advanceLexer();
                            marker.done(JavaDocElementType.DOC_METHOD_OR_FIELD_REF);
                            return;
                        }
                        psiBuilder.advanceLexer();
                    }
                } else {
                    markerM6463mark.done(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
                    break;
                }
            }
        }
        marker.done(JavaDocElementType.DOC_METHOD_OR_FIELD_REF);
    }

    private static void parseParameterRef(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        while (TAG_VALUES_SET.contains(getTokenType(psiBuilder))) {
            psiBuilder.advanceLexer();
        }
        markerM6463mark.done(JavaDocElementType.DOC_PARAMETER_REF);
    }

    private static void parseSimpleTagValue(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        while (true) {
            IElementType tokenType = getTokenType(psiBuilder);
            if (tokenType == JavaDocTokenType.DOC_COMMENT_BAD_CHARACTER) {
                psiBuilder.remapCurrentToken(JavaDocTokenType.DOC_TAG_VALUE_TOKEN);
            } else if (!TAG_VALUES_SET.contains(tokenType)) {
                markerM6463mark.done(JavaDocElementType.DOC_TAG_VALUE_ELEMENT);
                return;
            }
            psiBuilder.advanceLexer();
        }
    }

    private static IElementType getTokenType(PsiBuilder psiBuilder) {
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType != JavaDocTokenType.DOC_SPACE) {
                return tokenType;
            }
            psiBuilder.remapCurrentToken(TokenType.WHITE_SPACE);
            psiBuilder.advanceLexer();
        }
    }

    private static int getBraceScope(PsiBuilder psiBuilder) {
        Integer num = (Integer) psiBuilder.getUserData(BRACE_SCOPE_KEY);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    private static void setBraceScope(PsiBuilder psiBuilder, int i) {
        psiBuilder.putUserData(BRACE_SCOPE_KEY, Integer.valueOf(i));
    }

    private static void remapAndAdvance(PsiBuilder psiBuilder) {
        if (INLINE_TAG_BORDERS_SET.contains(psiBuilder.getTokenType()) && getBraceScope(psiBuilder) != 1) {
            psiBuilder.remapCurrentToken(JavaDocTokenType.DOC_COMMENT_DATA);
        }
        psiBuilder.advanceLexer();
    }
}
