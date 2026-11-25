package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ReferenceParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet WILDCARD_KEYWORD_SET = TokenSet.create(JavaTokenType.EXTENDS_KEYWORD, JavaTokenType.SUPER_KEYWORD);
    private final JavaParser myParser;

    public static class TypeInfo {
        public boolean hasErrors;
        public boolean isArray;
        public boolean isParameterized;
        public boolean isPrimitive;
        public boolean isVarArg;
        public PsiBuilder.Marker marker;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ReferenceParser";
        } else {
            objArr[0] = "javaParser";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "parseTypeParameters";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ReferenceParser";
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

    public ReferenceParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public PsiBuilder.Marker parseType(PsiBuilder psiBuilder, int i) {
        TypeInfo typeInfo = parseTypeInfo(psiBuilder, i);
        if (typeInfo != null) {
            return typeInfo.marker;
        }
        return null;
    }

    public TypeInfo parseTypeInfo(PsiBuilder psiBuilder, int i) {
        IElementType iElementType;
        TypeInfo typeInfo = parseTypeInfo(psiBuilder, i, false);
        if (typeInfo != null) {
            if (BitUtil.isSet(i, 16)) {
                iElementType = JavaTokenType.OR;
            } else {
                iElementType = BitUtil.isSet(i, 32) ? JavaTokenType.AND : null;
            }
            if (iElementType != null && psiBuilder.getTokenType() == iElementType) {
                typeInfo.marker = typeInfo.marker.m6464precede();
                while (psiBuilder.getTokenType() == iElementType) {
                    psiBuilder.advanceLexer();
                    IElementType tokenType = psiBuilder.getTokenType();
                    if (tokenType != JavaTokenType.IDENTIFIER && tokenType != JavaTokenType.AT) {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                    }
                    parseTypeInfo(psiBuilder, i, false);
                }
                typeInfo.marker.done(JavaElementType.TYPE);
            }
        }
        return typeInfo;
    }

    private TypeInfo parseTypeInfo(PsiBuilder psiBuilder, int i, boolean z) {
        if (psiBuilder.getTokenType() == null) {
            return null;
        }
        TypeInfo typeInfo = new TypeInfo();
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker annotations = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        IElementType tokenType = psiBuilder.getTokenType();
        if (tokenType == JavaTokenType.IDENTIFIER && BitUtil.isSet(i, 128) && psiBuilder.lookAhead(1) != JavaTokenType.DOT && psiBuilder.lookAhead(1) != JavaTokenType.COLON && PsiKeyword.VAR.equals(psiBuilder.getTokenText()) && JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_10)) {
            tokenType = JavaTokenType.VAR_KEYWORD;
            psiBuilder.remapCurrentToken(tokenType);
        } else if (tokenType == JavaTokenType.VAR_KEYWORD && !BitUtil.isSet(i, 128)) {
            tokenType = JavaTokenType.IDENTIFIER;
            psiBuilder.remapCurrentToken(tokenType);
        }
        if (PsiBuilderUtil.expect(psiBuilder, ElementType.PRIMITIVE_TYPE_BIT_SET)) {
            typeInfo.isPrimitive = true;
        } else {
            if ((BitUtil.isSet(i, 4) || z) && (tokenType == JavaTokenType.QUEST || isKeywordAny(psiBuilder))) {
                if (tokenType == JavaTokenType.QUEST) {
                    psiBuilder.advanceLexer();
                } else {
                    dummy(psiBuilder);
                }
                completeWildcardType(psiBuilder, BitUtil.isSet(i, 4), markerM6463mark);
                typeInfo.marker = markerM6463mark;
                return typeInfo;
            }
            if (tokenType == JavaTokenType.IDENTIFIER) {
                parseJavaCodeReference(psiBuilder, BitUtil.isSet(i, 1), true, false, false, false, BitUtil.isSet(i, 8), typeInfo);
            } else {
                if (tokenType == JavaTokenType.VAR_KEYWORD) {
                    psiBuilder.advanceLexer();
                    markerM6463mark.done(JavaElementType.TYPE);
                    typeInfo.marker = markerM6463mark;
                    return typeInfo;
                }
                if (BitUtil.isSet(i, 8) && tokenType == JavaTokenType.GT) {
                    if (annotations == null) {
                        JavaParserUtil.emptyElement(psiBuilder, JavaElementType.DIAMOND_TYPE);
                    } else {
                        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                        typeInfo.hasErrors = true;
                    }
                    markerM6463mark.done(JavaElementType.TYPE);
                    typeInfo.marker = markerM6463mark;
                    return typeInfo;
                }
                markerM6463mark.drop();
                if (annotations == null || !BitUtil.isSet(i, 64)) {
                    return null;
                }
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.type", new Object[0]));
                typeInfo.marker = annotations;
                typeInfo.hasErrors = true;
                return typeInfo;
            }
        }
        markerM6463mark.done(JavaElementType.TYPE);
        while (true) {
            this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LBRACKET)) {
                markerM6463mark2.drop();
                break;
            }
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RBRACKET)) {
                markerM6463mark2.rollbackTo();
                break;
            }
            markerM6463mark2.drop();
            typeInfo.isArray = true;
        }
        if (BitUtil.isSet(i, 2) && psiBuilder.getTokenType() == JavaTokenType.ELLIPSIS) {
            psiBuilder.advanceLexer();
            typeInfo.isVarArg = true;
        }
        if (typeInfo.isVarArg || typeInfo.isArray) {
            markerM6463mark = markerM6463mark.m6464precede();
            markerM6463mark.done(JavaElementType.TYPE);
        }
        typeInfo.marker = markerM6463mark;
        return typeInfo;
    }

    private void completeWildcardType(PsiBuilder psiBuilder, boolean z, PsiBuilder.Marker marker) {
        if (PsiBuilderUtil.expect(psiBuilder, WILDCARD_KEYWORD_SET) && parseTypeInfo(psiBuilder, 1) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.type", new Object[0]));
        }
        if (z) {
            marker.done(JavaElementType.TYPE);
        } else {
            marker.error(JavaPsiBundle.message("error.message.wildcard.not.expected", new Object[0]));
        }
    }

    public PsiBuilder.Marker parseJavaCodeReference(PsiBuilder psiBuilder, boolean z, boolean z2, boolean z3, boolean z4) {
        return parseJavaCodeReference(psiBuilder, z, z2, false, false, z3, z4, new TypeInfo());
    }

    public boolean parseImportCodeReference(PsiBuilder psiBuilder, boolean z) {
        TypeInfo typeInfo = new TypeInfo();
        parseJavaCodeReference(psiBuilder, true, false, true, z, false, false, typeInfo);
        return !typeInfo.hasErrors;
    }

    private PsiBuilder.Marker parseJavaCodeReference(PsiBuilder psiBuilder, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, TypeInfo typeInfo) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            markerM6463mark.rollbackTo();
            if (z3) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            }
            typeInfo.hasErrors = true;
            return null;
        }
        if (z2) {
            typeInfo.isParameterized = parseReferenceParameterList(psiBuilder, true, z6);
        } else if (!z4) {
            JavaParserUtil.emptyElement(psiBuilder, JavaElementType.REFERENCE_PARAMETER_LIST);
        }
        while (psiBuilder.getTokenType() == JavaTokenType.DOT) {
            markerM6463mark.done(JavaElementType.JAVA_CODE_REFERENCE);
            if (z5 && !z6 && typeInfo.isParameterized) {
                return markerM6463mark;
            }
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            if (z3 && PsiBuilderUtil.expect(psiBuilder, JavaTokenType.ASTERISK)) {
                markerM6463mark2.drop();
                return markerM6463mark;
            }
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
                if (!z) {
                    markerM6463mark2.rollbackTo();
                    return markerM6463mark;
                }
                typeInfo.hasErrors = true;
                if (z3) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("import.statement.identifier.or.asterisk.expected.", new Object[0]));
                } else {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                }
                markerM6463mark2.drop();
                return markerM6463mark;
            }
            markerM6463mark2.drop();
            markerM6463mark = markerM6463mark.m6464precede();
            if (z2) {
                typeInfo.isParameterized = parseReferenceParameterList(psiBuilder, true, z6);
            } else {
                JavaParserUtil.emptyElement(psiBuilder, JavaElementType.REFERENCE_PARAMETER_LIST);
            }
        }
        markerM6463mark.done(z4 ? JavaElementType.IMPORT_STATIC_REFERENCE : JavaElementType.JAVA_CODE_REFERENCE);
        return markerM6463mark;
    }

    public boolean parseReferenceParameterList(PsiBuilder psiBuilder, boolean z, boolean z2) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean z3 = false;
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LT)) {
            markerM6463mark.done(JavaElementType.REFERENCE_PARAMETER_LIST);
            return false;
        }
        int i = BitUtil.set(BitUtil.set(1, 4, z), 8, z2);
        while (true) {
            if (parseTypeInfo(psiBuilder, i, true) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            } else {
                IElementType tokenType = psiBuilder.getTokenType();
                if (WILDCARD_KEYWORD_SET.contains(tokenType)) {
                    parseReferenceList(psiBuilder, tokenType, null, JavaTokenType.AND);
                }
            }
            if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.GT)) {
                z3 = true;
                break;
            }
            if (!JavaParserUtil.expectOrError(psiBuilder, JavaTokenType.COMMA, "expected.gt.or.comma")) {
                break;
            }
            i = BitUtil.set(i, 8, false);
        }
        markerM6463mark.done(JavaElementType.REFERENCE_PARAMETER_LIST);
        return z3;
    }

    public PsiBuilder.Marker parseTypeParameters(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LT)) {
            markerM6463mark.done(JavaElementType.TYPE_PARAMETER_LIST);
            if (markerM6463mark == null) {
                $$$reportNull$$$0(1);
            }
            return markerM6463mark;
        }
        do {
            if (parseTypeParameter(psiBuilder) == null) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.type.parameter", new Object[0]));
            }
        } while (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.COMMA));
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.GT)) {
            if (psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER) {
                if (psiBuilder.lookAhead(1) == JavaTokenType.GT) {
                    PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
                    psiBuilder.advanceLexer();
                    markerM6463mark2.error(JavaPsiBundle.message("unexpected.identifier", new Object[0]));
                    psiBuilder.advanceLexer();
                } else {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.gt", new Object[0]));
                }
            } else {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.gt", new Object[0]));
            }
        }
        markerM6463mark.done(JavaElementType.TYPE_PARAMETER_LIST);
        if (markerM6463mark == null) {
            $$$reportNull$$$0(2);
        }
        return markerM6463mark;
    }

    public PsiBuilder.Marker parseTypeParameter(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        if (isKeywordAny(psiBuilder)) {
            dummy(psiBuilder);
        }
        boolean zExpect = PsiBuilderUtil.expect(psiBuilder, JavaTokenType.QUEST);
        if (!zExpect && !PsiBuilderUtil.expect(psiBuilder, JavaTokenType.IDENTIFIER)) {
            markerM6463mark.rollbackTo();
            return null;
        }
        parseReferenceList(psiBuilder, JavaTokenType.EXTENDS_KEYWORD, JavaElementType.EXTENDS_BOUND_LIST, JavaTokenType.AND);
        if (!zExpect) {
            markerM6463mark.done(JavaElementType.TYPE_PARAMETER);
        } else {
            markerM6463mark.error(JavaPsiBundle.message("error.message.wildcard.not.expected", new Object[0]));
        }
        return markerM6463mark;
    }

    public boolean parseReferenceList(PsiBuilder psiBuilder, IElementType iElementType, IElementType iElementType2, IElementType iElementType3) {
        boolean z;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (PsiBuilderUtil.expect(psiBuilder, iElementType)) {
            do {
                if (parseJavaCodeReference(psiBuilder, false, true, false, false) == null) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                    z = true;
                } else {
                    z = false;
                }
            } while (PsiBuilderUtil.expect(psiBuilder, iElementType3));
        } else {
            z = false;
        }
        if (iElementType2 != null) {
            markerM6463mark.done(iElementType2);
        } else {
            markerM6463mark.error(JavaPsiBundle.message("bound.not.expected", new Object[0]));
        }
        return z;
    }

    private static boolean isKeywordAny(PsiBuilder psiBuilder) {
        return JavaParserUtil.getLanguageLevel(psiBuilder).isAtLeast(LanguageLevel.JDK_X) && "any".equals(psiBuilder.getTokenText());
    }

    private static void dummy(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        markerM6463mark.done(JavaElementType.DUMMY_ELEMENT);
    }
}
