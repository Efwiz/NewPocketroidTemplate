package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ModuleParser {
    private static final Set<String> STATEMENT_KEYWORDS = ContainerUtil.newHashSet(PsiKeyword.REQUIRES, PsiKeyword.EXPORTS, PsiKeyword.USES, PsiKeyword.PROVIDES);
    private final JavaParser myParser;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "builder";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ModuleParser";
        } else if (i != 3) {
            objArr[0] = "parser";
        } else {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/ModuleParser";
        } else {
            objArr[1] = "parsePackageStatement";
        }
        if (i == 1) {
            objArr[2] = "parse";
        } else if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "parseExtras";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public ModuleParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public PsiBuilder.Marker parse(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        PsiBuilder.Marker annotations = this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
        String tokenText = psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER ? psiBuilder.getTokenText() : null;
        if (!"open".equals(tokenText) && !"module".equals(tokenText)) {
            markerM6463mark.rollbackTo();
            return null;
        }
        PsiBuilder.Marker markerM6464precede = annotations != null ? annotations.m6464precede() : psiBuilder.m6463mark();
        if ("open".equals(tokenText)) {
            mapAndAdvance(psiBuilder, JavaTokenType.OPEN_KEYWORD);
            tokenText = psiBuilder.getTokenText();
        }
        JavaParserUtil.done(markerM6464precede, JavaElementType.MODIFIER_LIST);
        if ("module".equals(tokenText)) {
            mapAndAdvance(psiBuilder, JavaTokenType.MODULE_KEYWORD);
            if (parseName(psiBuilder) == null) {
                markerM6463mark.drop();
                if (psiBuilder.getTokenType() != null) {
                    parseExtras(psiBuilder, JavaPsiBundle.message("expected.module.declaration", new Object[0]));
                } else {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                }
                return markerM6463mark;
            }
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.LBRACE)) {
                if (psiBuilder.getTokenType() != null) {
                    parseExtras(psiBuilder, JavaPsiBundle.message("expected.module.declaration", new Object[0]));
                } else {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.lbrace", new Object[0]));
                }
            } else {
                parseModuleContent(psiBuilder);
            }
            JavaParserUtil.done(markerM6463mark, JavaElementType.MODULE);
            if (psiBuilder.getTokenType() != null) {
                parseExtras(psiBuilder, JavaPsiBundle.message("unexpected.tokens", new Object[0]));
            }
            return markerM6463mark;
        }
        markerM6463mark.drop();
        parseExtras(psiBuilder, JavaPsiBundle.message("expected.module.declaration", new Object[0]));
        return markerM6463mark;
    }

    private static PsiBuilder.Marker parseName(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        boolean z = true;
        boolean z2 = true;
        while (true) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType != JavaTokenType.IDENTIFIER) {
                if (tokenType != JavaTokenType.DOT) {
                    break;
                }
                if (z) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
                }
                z = true;
            } else {
                if (!z) {
                    JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.dot", new Object[0]));
                }
                z = false;
            }
            psiBuilder.advanceLexer();
            z2 = false;
        }
        if (!z2) {
            if (z) {
                JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
            }
            markerM6463mark.done(JavaElementType.MODULE_REFERENCE);
            return markerM6463mark;
        }
        markerM6463mark.drop();
        return null;
    }

    private void parseModuleContent(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark;
        PsiBuilder.Marker statement;
        loop0: while (true) {
            markerM6463mark = null;
            while (true) {
                IElementType tokenType = psiBuilder.getTokenType();
                if (tokenType == null || tokenType == JavaTokenType.RBRACE) {
                    break loop0;
                }
                if (tokenType == JavaTokenType.SEMICOLON) {
                    if (markerM6463mark != null) {
                        markerM6463mark.error(JavaPsiBundle.message("expected.module.statement", new Object[0]));
                        markerM6463mark = null;
                    }
                    psiBuilder.advanceLexer();
                } else {
                    statement = parseStatement(psiBuilder);
                    if (statement == null) {
                        if (markerM6463mark == null) {
                            markerM6463mark = psiBuilder.m6463mark();
                        }
                        psiBuilder.advanceLexer();
                    } else if (markerM6463mark != null) {
                        break;
                    }
                }
            }
            markerM6463mark.errorBefore(JavaPsiBundle.message("expected.module.statement", new Object[0]), statement);
        }
        if (markerM6463mark != null) {
            markerM6463mark.error(JavaPsiBundle.message("expected.module.statement", new Object[0]));
        }
        if (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.RBRACE) || markerM6463mark != null) {
            return;
        }
        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.rbrace", new Object[0]));
    }

    private PsiBuilder.Marker parseStatement(PsiBuilder psiBuilder) {
        String tokenText = psiBuilder.getTokenText();
        if (PsiKeyword.REQUIRES.equals(tokenText)) {
            return parseRequiresStatement(psiBuilder);
        }
        if (PsiKeyword.EXPORTS.equals(tokenText)) {
            return parseExportsStatement(psiBuilder);
        }
        if (PsiKeyword.OPENS.equals(tokenText)) {
            return parseOpensStatement(psiBuilder);
        }
        if (PsiKeyword.USES.equals(tokenText)) {
            return parseUsesStatement(psiBuilder);
        }
        if (PsiKeyword.PROVIDES.equals(tokenText)) {
            return parseProvidesStatement(psiBuilder);
        }
        return null;
    }

    private static PsiBuilder.Marker parseRequiresStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        mapAndAdvance(psiBuilder, JavaTokenType.REQUIRES_KEYWORD);
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        while (true) {
            if (!PsiBuilderUtil.expect(psiBuilder, ElementType.MODIFIER_BIT_SET)) {
                if (psiBuilder.getTokenType() != JavaTokenType.IDENTIFIER || !"transitive".equals(psiBuilder.getTokenText())) {
                    break;
                }
                mapAndAdvance(psiBuilder, JavaTokenType.TRANSITIVE_KEYWORD);
            }
        }
        JavaParserUtil.done(markerM6463mark2, JavaElementType.MODIFIER_LIST);
        if (parseNameRef(psiBuilder) != null) {
            JavaParserUtil.semicolon(psiBuilder);
        } else {
            PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON);
        }
        markerM6463mark.done(JavaElementType.REQUIRES_STATEMENT);
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseExportsStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        mapAndAdvance(psiBuilder, JavaTokenType.EXPORTS_KEYWORD);
        return parsePackageStatement(psiBuilder, markerM6463mark, JavaElementType.EXPORTS_STATEMENT);
    }

    private PsiBuilder.Marker parseOpensStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        mapAndAdvance(psiBuilder, JavaTokenType.OPENS_KEYWORD);
        return parsePackageStatement(psiBuilder, markerM6463mark, JavaElementType.OPENS_STATEMENT);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0040  */
    private PsiBuilder.Marker parsePackageStatement(PsiBuilder psiBuilder, PsiBuilder.Marker marker, IElementType iElementType) {
        PsiBuilder.Marker nameRef;
        if (parseClassOrPackageRef(psiBuilder) != null) {
            if ("to".equals(psiBuilder.getTokenText())) {
                mapAndAdvance(psiBuilder, JavaTokenType.TO_KEYWORD);
                do {
                    nameRef = parseNameRef(psiBuilder);
                } while (PsiBuilderUtil.expect(psiBuilder, JavaTokenType.COMMA));
                if (nameRef == null) {
                }
                marker.done(iElementType);
                if (marker == null) {
                    $$$reportNull$$$0(2);
                }
                return marker;
            }
            JavaParserUtil.semicolon(psiBuilder);
            marker.done(iElementType);
            if (marker == null) {
                $$$reportNull$$$0(2);
            }
            return marker;
        }
        JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.package.reference", new Object[0]));
        PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON);
        marker.done(iElementType);
        if (marker == null) {
            $$$reportNull$$$0(2);
        }
        return marker;
    }

    private PsiBuilder.Marker parseUsesStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        mapAndAdvance(psiBuilder, JavaTokenType.USES_KEYWORD);
        if (parseClassOrPackageRef(psiBuilder) != null) {
            JavaParserUtil.semicolon(psiBuilder);
        } else {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.class.reference", new Object[0]));
            PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON);
        }
        markerM6463mark.done(JavaElementType.USES_STATEMENT);
        return markerM6463mark;
    }

    private PsiBuilder.Marker parseProvidesStatement(PsiBuilder psiBuilder) {
        boolean z;
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        mapAndAdvance(psiBuilder, JavaTokenType.PROVIDES_KEYWORD);
        boolean referenceList = true;
        if (parseClassOrPackageRef(psiBuilder) == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.class.reference", new Object[0]));
            z = true;
        } else {
            z = false;
        }
        if ("with".equals(psiBuilder.getTokenText())) {
            psiBuilder.remapCurrentToken(JavaTokenType.WITH_KEYWORD);
            referenceList = this.myParser.getReferenceParser().parseReferenceList(psiBuilder, JavaTokenType.WITH_KEYWORD, JavaElementType.PROVIDES_WITH_LIST, JavaTokenType.COMMA);
        } else if (z) {
            referenceList = z;
        } else if (psiBuilder.getTokenType() == JavaTokenType.IDENTIFIER && !STATEMENT_KEYWORDS.contains(psiBuilder.getTokenText())) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            psiBuilder.advanceLexer();
            markerM6463mark2.error(JavaPsiBundle.message("expected.with", new Object[0]));
        } else {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.with", new Object[0]));
        }
        if (!referenceList) {
            JavaParserUtil.semicolon(psiBuilder);
        } else {
            PsiBuilderUtil.expect(psiBuilder, JavaTokenType.SEMICOLON);
        }
        markerM6463mark.done(JavaElementType.PROVIDES_STATEMENT);
        return markerM6463mark;
    }

    private static PsiBuilder.Marker parseNameRef(PsiBuilder psiBuilder) {
        PsiBuilder.Marker name2 = parseName(psiBuilder);
        if (name2 == null) {
            JavaParserUtil.error(psiBuilder, JavaPsiBundle.message("expected.identifier", new Object[0]));
        }
        return name2;
    }

    private static void mapAndAdvance(PsiBuilder psiBuilder, IElementType iElementType) {
        psiBuilder.remapCurrentToken(iElementType);
        psiBuilder.advanceLexer();
    }

    private static void parseExtras(PsiBuilder psiBuilder, String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        while (psiBuilder.getTokenType() != null) {
            psiBuilder.advanceLexer();
        }
        markerM6463mark.error(str);
    }

    private PsiBuilder.Marker parseClassOrPackageRef(PsiBuilder psiBuilder) {
        return this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, false, false, false);
    }
}
