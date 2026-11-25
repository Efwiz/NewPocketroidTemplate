package org.jetbrains.kotlin.com.intellij.lang.java.parser;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter;
import org.jetbrains.kotlin.com.intellij.lang.impl.TokenSequence;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.lexer.TokenList;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.indexing.IndexingDataKeys;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaParserUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final TokenSet WS_COMMENTS = TokenSet.orSet(ElementType.JAVA_COMMENT_BIT_SET, TokenSet.WHITE_SPACE);
    private static final Key<LanguageLevel> LANG_LEVEL_KEY = Key.create("JavaParserUtil.LanguageLevel");
    private static final Key<Boolean> DEEP_PARSE_BLOCKS_IN_STATEMENTS = Key.create("JavaParserUtil.ParserExtender");
    private static final TokenSet PRECEDING_COMMENT_SET = TokenSet.orSet(TokenSet.create(JavaElementType.MODULE), ElementType.FULL_MEMBER_BIT_SET);
    private static final TokenSet TRAILING_COMMENT_SET = TokenSet.orSet(TokenSet.create(JavaElementType.PACKAGE_STATEMENT), ElementType.IMPORT_STATEMENT_BASE_BIT_SET, ElementType.FULL_MEMBER_BIT_SET, ElementType.JAVA_STATEMENT_BIT_SET);
    public static final WhitespacesAndCommentsBinder PRECEDING_COMMENT_BINDER = new PrecedingWhitespacesAndCommentsBinder(false);
    public static final WhitespacesAndCommentsBinder SPECIAL_PRECEDING_COMMENT_BINDER = new PrecedingWhitespacesAndCommentsBinder(true);
    public static final WhitespacesAndCommentsBinder TRAILING_COMMENT_BINDER = new TrailingWhitespacesAndCommentsBinder();

    @FunctionalInterface
    public interface ParserWrapper {
        void parse(PsiBuilder psiBuilder);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/JavaParserUtil";
                break;
            case 5:
            case 6:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 1) {
            objArr[1] = "obtainTokens";
        } else if (i == 2) {
            objArr[1] = "getLanguageLevel";
        } else if (i == 3 || i == 4) {
            objArr[1] = "createBuilder";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/parser/JavaParserUtil";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
            case 6:
                objArr[2] = "error";
                break;
            default:
                objArr[2] = "obtainTokens";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static TokenList obtainTokens(final PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        TokenList tokenList = (TokenList) CachedValuesManager.getCachedValue((PsiElement) psiFile, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                PsiFile psiFile2 = psiFile;
                return CachedValueProvider.Result.create(TokenSequence.performLexing(psiFile2.getViewProvider().getContents(), JavaParserDefinition.createLexer(PsiUtil.getLanguageLevel(psiFile2))), psiFile2);
            }
        });
        if (tokenList == null) {
            $$$reportNull$$$0(1);
        }
        return tokenList;
    }

    private static class PrecedingWhitespacesAndCommentsBinder implements WhitespacesAndCommentsBinder {
        private final boolean myAfterEmptyImport;

        PrecedingWhitespacesAndCommentsBinder(boolean z) {
            this.myAfterEmptyImport = z;
        }

        /* JADX WARN: Code duplicated, block: B:30:0x006c  */
        @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
        public int getEdgePosition(List<? extends IElementType> list, boolean z, WhitespacesAndCommentsBinder.TokenTextGetter tokenTextGetter) {
            if (list.size() == 0) {
                return 0;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size) == JavaDocElementType.DOC_COMMENT) {
                    return size;
                }
            }
            int size2 = list.size();
            for (int size3 = list.size() - 1; size3 >= 0; size3--) {
                IElementType iElementType = list.get(size3);
                if (TokenSet.WHITE_SPACE.contains(iElementType)) {
                    if (StringUtil.getLineBreakCount(tokenTextGetter.get(size3)) > 1) {
                        break;
                    }
                } else {
                    if (!ElementType.JAVA_PLAIN_COMMENT_BIT_SET.contains(iElementType)) {
                        break;
                    }
                    if (z || (size3 == 0 && this.myAfterEmptyImport)) {
                        size2 = size3;
                    } else if (size3 > 0) {
                        int i = size3 - 1;
                        if (TokenSet.WHITE_SPACE.contains(list.get(i)) && StringUtil.containsLineBreak(tokenTextGetter.get(i))) {
                            size2 = size3;
                        }
                    }
                }
            }
            return size2;
        }
    }

    private static class TrailingWhitespacesAndCommentsBinder implements WhitespacesAndCommentsBinder {
        private TrailingWhitespacesAndCommentsBinder() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
        public int getEdgePosition(List<? extends IElementType> list, boolean z, WhitespacesAndCommentsBinder.TokenTextGetter tokenTextGetter) {
            if (list.size() == 0) {
                return 0;
            }
            int i = 0;
            for (int i2 = 0; i2 < list.size(); i2++) {
                IElementType iElementType = list.get(i2);
                if (TokenSet.WHITE_SPACE.contains(iElementType)) {
                    if (StringUtil.containsLineBreak(tokenTextGetter.get(i2))) {
                        break;
                    }
                } else {
                    if (!ElementType.JAVA_PLAIN_COMMENT_BIT_SET.contains(iElementType)) {
                        break;
                    }
                    i = i2 + 1;
                }
            }
            return i;
        }
    }

    private JavaParserUtil() {
    }

    public static void setLanguageLevel(PsiBuilder psiBuilder, LanguageLevel languageLevel) {
        psiBuilder.putUserData(LANG_LEVEL_KEY, languageLevel);
    }

    public static LanguageLevel getLanguageLevel(PsiBuilder psiBuilder) {
        LanguageLevel languageLevel = (LanguageLevel) psiBuilder.getUserData(LANG_LEVEL_KEY);
        if (languageLevel == null) {
            $$$reportNull$$$0(2);
        }
        return languageLevel;
    }

    public static boolean isParseStatementCodeBlocksDeep(PsiBuilder psiBuilder) {
        return Boolean.TRUE.equals(psiBuilder.getUserData(DEEP_PARSE_BLOCKS_IN_STATEMENTS));
    }

    public static PsiBuilder createBuilder(ASTNode aSTNode) {
        CharSequence charSequence;
        PsiElement psi = aSTNode.getPsi();
        Project project = psi.getProject();
        CharSequence charSequence2 = (CharSequence) psi.getUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY);
        if (TreeUtil.isCollapsedChameleon(aSTNode) || charSequence2 == null) {
            CharSequence chars = aSTNode.getChars();
            charSequence = chars;
        } else {
            charSequence = charSequence2;
        }
        LanguageLevel languageLevel = PsiUtil.getLanguageLevel(psi);
        Lexer lexerCreateLexer = (!(psi instanceof PsiFile) || charSequence2 == null) ? JavaParserDefinition.createLexer(languageLevel) : obtainTokens((PsiFile) psi).asLexer();
        Language language = psi.getLanguage();
        if (!language.isKindOf(JavaLanguage.INSTANCE)) {
            language = JavaLanguage.INSTANCE;
        }
        PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder(project, aSTNode, lexerCreateLexer, language, charSequence);
        setLanguageLevel(psiBuilderCreateBuilder, languageLevel);
        if (psiBuilderCreateBuilder == null) {
            $$$reportNull$$$0(3);
        }
        return psiBuilderCreateBuilder;
    }

    public static PsiBuilder createBuilder(LighterLazyParseableNode lighterLazyParseableNode) {
        PsiFile containingFile = lighterLazyParseableNode.getContainingFile();
        Project project = containingFile.getProject();
        PsiBuilderFactory psiBuilderFactory = PsiBuilderFactory.getInstance();
        LanguageLevel languageLevel = PsiUtil.getLanguageLevel(containingFile);
        PsiBuilder psiBuilderCreateBuilder = psiBuilderFactory.createBuilder(project, lighterLazyParseableNode, JavaParserDefinition.createLexer(languageLevel), lighterLazyParseableNode.getTokenType().getLanguage(), lighterLazyParseableNode.getText());
        setLanguageLevel(psiBuilderCreateBuilder, languageLevel);
        if (psiBuilderCreateBuilder == null) {
            $$$reportNull$$$0(4);
        }
        return psiBuilderCreateBuilder;
    }

    public static ASTNode parseFragment(ASTNode aSTNode, ParserWrapper parserWrapper) {
        return parseFragment(aSTNode, parserWrapper, true, LanguageLevel.HIGHEST);
    }

    public static ASTNode parseFragment(ASTNode aSTNode, ParserWrapper parserWrapper, boolean z, LanguageLevel languageLevel) {
        PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder((aSTNode.getTreeParent() != null ? aSTNode.getTreeParent().getPsi() : aSTNode.getPsi()).getProject(), aSTNode, aSTNode.getElementType() == JavaDocElementType.DOC_COMMENT ? JavaParserDefinition.createDocLexer(languageLevel) : JavaParserDefinition.createLexer(languageLevel), aSTNode.getElementType().getLanguage(), aSTNode.getChars());
        setLanguageLevel(psiBuilderCreateBuilder, languageLevel);
        PsiBuilder.Marker markerM6463mark = psiBuilderCreateBuilder.m6463mark();
        parserWrapper.parse(psiBuilderCreateBuilder);
        if (!psiBuilderCreateBuilder.eof()) {
            if (!z) {
                throw new AssertionError("Unexpected token: '" + psiBuilderCreateBuilder.getTokenText() + FormatHelper.QUOTE);
            }
            PsiBuilder.Marker markerM6463mark2 = psiBuilderCreateBuilder.m6463mark();
            while (!psiBuilderCreateBuilder.eof()) {
                psiBuilderCreateBuilder.advanceLexer();
            }
            markerM6463mark2.error(JavaPsiBundle.message("unexpected.tokens", new Object[0]));
        }
        markerM6463mark.done(aSTNode.getElementType());
        return psiBuilderCreateBuilder.getTreeBuilt().getFirstChildNode();
    }

    public static void done(PsiBuilder.Marker marker, IElementType iElementType) {
        marker.done(iElementType);
        marker.setCustomEdgeTokenBinders(PRECEDING_COMMENT_SET.contains(iElementType) ? PRECEDING_COMMENT_BINDER : null, TRAILING_COMMENT_SET.contains(iElementType) ? TRAILING_COMMENT_BINDER : null);
    }

    public static IElementType exprType(PsiBuilder.Marker marker) {
        if (marker != null) {
            return ((LighterASTNode) marker).getTokenType();
        }
        return null;
    }

    public static void error(PsiBuilder psiBuilder, String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        psiBuilder.m6463mark().error(str);
    }

    public static void error(PsiBuilder psiBuilder, String str, PsiBuilder.Marker marker) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (marker == null) {
            error(psiBuilder, str);
        } else {
            marker.m6464precede().errorBefore(str, marker);
        }
    }

    public static boolean expectOrError(PsiBuilder psiBuilder, IElementType iElementType, String str) {
        if (PsiBuilderUtil.expect(psiBuilder, iElementType)) {
            return true;
        }
        error(psiBuilder, JavaPsiBundle.message(str, new Object[0]));
        return false;
    }

    public static void emptyElement(PsiBuilder psiBuilder, IElementType iElementType) {
        psiBuilder.m6463mark().done(iElementType);
    }

    public static void emptyElement(PsiBuilder.Marker marker, IElementType iElementType) {
        marker.m6464precede().doneBefore(iElementType, marker);
    }

    public static void semicolon(PsiBuilder psiBuilder) {
        expectOrError(psiBuilder, JavaTokenType.SEMICOLON, "expected.semicolon");
    }

    public static PsiBuilder braceMatchingBuilder(PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        int i = 1;
        while (!psiBuilder.eof()) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == JavaTokenType.LBRACE) {
                i++;
            } else if (tokenType == JavaTokenType.RBRACE) {
                i--;
            }
            if (i == 0) {
                break;
            }
            psiBuilder.advanceLexer();
        }
        int currentOffset = psiBuilder.getCurrentOffset();
        markerM6463mark.rollbackTo();
        return stoppingBuilder(psiBuilder, currentOffset);
    }

    public static PsiBuilder stoppingBuilder(PsiBuilder psiBuilder, final int i) {
        return new PsiBuilderAdapter(psiBuilder) { // from class: org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
            public IElementType getTokenType() {
                if (getCurrentOffset() < i) {
                    return super.getTokenType();
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
            public boolean eof() {
                return getCurrentOffset() >= i || super.eof();
            }
        };
    }
}
