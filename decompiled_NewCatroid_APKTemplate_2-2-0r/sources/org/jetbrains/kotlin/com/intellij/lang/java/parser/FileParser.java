package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.AbstractBundle;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class FileParser {
    protected static final TokenSet IMPORT_LIST_STOPPER_SET = TokenSet.orSet(ElementType.MODIFIER_BIT_SET, TokenSet.create(JavaTokenType.CLASS_KEYWORD, JavaTokenType.INTERFACE_KEYWORD, JavaTokenType.ENUM_KEYWORD, JavaTokenType.AT));
    private final JavaParser myParser;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "builder";
                break;
            case 3:
                objArr[0] = "importListStopper";
                break;
            case 4:
            case 7:
                objArr[0] = "bundle";
                break;
            case 5:
            case 8:
                objArr[0] = "errorMessageKey";
                break;
            case 6:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/FileParser";
                break;
            default:
                objArr[0] = "javaParser";
                break;
        }
        if (i == 6) {
            objArr[1] = "parseImportList";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/FileParser";
        } else {
            objArr[1] = "error";
        }
        switch (i) {
            case 1:
                objArr[2] = "parse";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "parseFile";
                break;
            case 6:
            case 9:
                break;
            case 7:
            case 8:
                objArr[2] = "error";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public FileParser(JavaParser javaParser) {
        if (javaParser == null) {
            $$$reportNull$$$0(0);
        }
        this.myParser = javaParser;
    }

    public void parse(PsiBuilder psiBuilder) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        parseFile(psiBuilder, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.lang.java.parser.FileParser$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FileParser.stopImportListParsing((PsiBuilder) obj);
            }
        }, JavaPsiBundle.INSTANCE, "expected.class.or.interface");
    }

    public void parseFile(PsiBuilder psiBuilder, Predicate<? super PsiBuilder> predicate, AbstractBundle abstractBundle, String str) {
        if (psiBuilder == null) {
            $$$reportNull$$$0(2);
        }
        if (predicate == null) {
            $$$reportNull$$$0(3);
        }
        if (abstractBundle == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        parsePackageStatement(psiBuilder);
        Pair<PsiBuilder.Marker, Boolean> importList = parseImportList(psiBuilder, predicate);
        PsiBuilder.Marker markerM6463mark = null;
        Boolean boolValueOf = null;
        PsiBuilder.Marker marker = null;
        while (!psiBuilder.eof()) {
            if (psiBuilder.getTokenType() == JavaTokenType.SEMICOLON) {
                psiBuilder.advanceLexer();
            } else {
                PsiBuilder.Marker initial = this.myParser.getModuleParser().parse(psiBuilder);
                if (initial == null) {
                    initial = parseInitial(psiBuilder);
                }
                if (initial != null) {
                    if (markerM6463mark != null) {
                        markerM6463mark.errorBefore(error(abstractBundle, str), initial);
                        markerM6463mark = null;
                    }
                    if (boolValueOf == null) {
                        boolValueOf = Boolean.valueOf(JavaParserUtil.exprType(initial) != JavaElementType.MODIFIER_LIST);
                        if (boolValueOf.booleanValue()) {
                            marker = initial;
                        }
                    }
                } else {
                    if (markerM6463mark == null) {
                        markerM6463mark = psiBuilder.m6463mark();
                    }
                    psiBuilder.advanceLexer();
                    if (boolValueOf == null) {
                        boolValueOf = false;
                    }
                }
            }
        }
        if (markerM6463mark != null) {
            markerM6463mark.error(error(abstractBundle, str));
        }
        if (importList.second.booleanValue() && boolValueOf == Boolean.TRUE) {
            importList.first.setCustomEdgeTokenBinders(JavaParserUtil.PRECEDING_COMMENT_BINDER, null);
            marker.setCustomEdgeTokenBinders(JavaParserUtil.SPECIAL_PRECEDING_COMMENT_BINDER, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean stopImportListParsing(PsiBuilder psiBuilder) {
        IElementType tokenType = psiBuilder.getTokenType();
        if (IMPORT_LIST_STOPPER_SET.contains(tokenType) || DeclarationParser.isRecordToken(psiBuilder, tokenType)) {
            return true;
        }
        if (tokenType != JavaTokenType.IDENTIFIER) {
            return false;
        }
        String tokenText = psiBuilder.getTokenText();
        return "open".equals(tokenText) || "module".equals(tokenText);
    }

    protected PsiBuilder.Marker parseInitial(PsiBuilder psiBuilder) {
        return this.myParser.getDeclarationParser().parse(psiBuilder, DeclarationParser.Context.FILE);
    }

    private void parsePackageStatement(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.PACKAGE_KEYWORD)) {
            PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
            this.myParser.getDeclarationParser().parseAnnotations(psiBuilder);
            JavaParserUtil.done(markerM6463mark2, JavaElementType.MODIFIER_LIST);
            if (!PsiBuilderUtil.expect(psiBuilder, JavaTokenType.PACKAGE_KEYWORD)) {
                markerM6463mark.rollbackTo();
                return;
            }
        }
        if (this.myParser.getReferenceParser().parseJavaCodeReference(psiBuilder, true, false, false, false) == null) {
            markerM6463mark.error(JavaPsiBundle.message("expected.class.or.interface", new Object[0]));
        } else {
            JavaParserUtil.semicolon(psiBuilder);
            JavaParserUtil.done(markerM6463mark, JavaElementType.PACKAGE_STATEMENT);
        }
    }

    protected Pair<PsiBuilder.Marker, Boolean> parseImportList(PsiBuilder psiBuilder, Predicate<? super PsiBuilder> predicate) {
        PsiBuilder.Marker markerM6463mark;
        PsiBuilder.Marker importStatement;
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        boolean z = true;
        loop0: while (true) {
            markerM6463mark = null;
            while (true) {
                if (psiBuilder.eof() || predicate.test(psiBuilder)) {
                    break loop0;
                }
                if (psiBuilder.getTokenType() == JavaTokenType.SEMICOLON) {
                    psiBuilder.advanceLexer();
                } else {
                    importStatement = parseImportStatement(psiBuilder);
                    if (importStatement != null) {
                        z = false;
                        if (markerM6463mark != null) {
                            break;
                        }
                    } else {
                        if (markerM6463mark == null) {
                            markerM6463mark = psiBuilder.m6463mark();
                        }
                        psiBuilder.advanceLexer();
                    }
                }
            }
            markerM6463mark.errorBefore(JavaPsiBundle.message("unexpected.token", new Object[0]), importStatement);
        }
        if (markerM6463mark != null) {
            markerM6463mark.rollbackTo();
        }
        if (z) {
            PsiBuilder.Marker markerM6464precede = markerM6463mark2.m6464precede();
            markerM6463mark2.rollbackTo();
            markerM6463mark2 = markerM6464precede;
        }
        JavaParserUtil.done(markerM6463mark2, JavaElementType.IMPORT_LIST);
        Pair<PsiBuilder.Marker, Boolean> pairCreate = Pair.create(markerM6463mark2, Boolean.valueOf(z));
        if (pairCreate == null) {
            $$$reportNull$$$0(6);
        }
        return pairCreate;
    }

    protected PsiBuilder.Marker parseImportStatement(PsiBuilder psiBuilder) {
        if (psiBuilder.getTokenType() != JavaTokenType.IMPORT_KEYWORD) {
            return null;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        boolean zExpect = PsiBuilderUtil.expect(psiBuilder, JavaTokenType.STATIC_KEYWORD);
        IElementType iElementType = zExpect ? JavaElementType.IMPORT_STATIC_STATEMENT : JavaElementType.IMPORT_STATEMENT;
        if (this.myParser.getReferenceParser().parseImportCodeReference(psiBuilder, zExpect)) {
            JavaParserUtil.semicolon(psiBuilder);
        }
        JavaParserUtil.done(markerM6463mark, iElementType);
        return markerM6463mark;
    }

    private static String error(AbstractBundle abstractBundle, String str) {
        if (abstractBundle == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        String strM6457xe250ffe0 = abstractBundle.m6457xe250ffe0(str, new Object[0]);
        if (strM6457xe250ffe0 == null) {
            $$$reportNull$$$0(9);
        }
        return strM6457xe250ffe0;
    }
}
