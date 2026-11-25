package org.jetbrains.kotlin.parsing;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeType;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.kdoc.lexer.KDocTokens;
import org.jetbrains.kotlin.kdoc.parser.KDocElementType;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocLink;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.stubs.elements.KtFileElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType;

/* JADX INFO: compiled from: KotlinParserDefinition.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\b\u0010\u0016\u001a\u00020\u0012H\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u0017¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/parsing/KotlinParserDefinition;", "Lorg/jetbrains/kotlin/com/intellij/lang/ParserDefinition;", "()V", "createElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "astNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "createFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "fileViewProvider", "Lorg/jetbrains/kotlin/com/intellij/psi/FileViewProvider;", "createLexer", "Lorg/jetbrains/kotlin/com/intellij/lexer/Lexer;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "createParser", "Lorg/jetbrains/kotlin/com/intellij/lang/PsiParser;", "getCommentTokens", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "getFileNodeType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IFileElementType;", "getStringLiteralElements", "getWhitespaceTokens", "spaceExistanceTypeBetweenTokens", "Lorg/jetbrains/kotlin/com/intellij/lang/ParserDefinition$SpaceRequirements;", "left", "right", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinParserDefinition implements ParserDefinition {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String STD_SCRIPT_SUFFIX = "kts";
    public static final String STD_SCRIPT_EXT = ".kts";

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public Lexer createLexer(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        return new KotlinLexer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiParser createParser(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        return new KotlinParser(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public IFileElementType getFileNodeType() {
        KtFileElementType INSTANCE2 = KtFileElementType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        return INSTANCE2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getWhitespaceTokens() {
        TokenSet WHITESPACES = KtTokens.WHITESPACES;
        Intrinsics.checkNotNullExpressionValue(WHITESPACES, "WHITESPACES");
        return WHITESPACES;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getCommentTokens() {
        TokenSet COMMENTS = KtTokens.COMMENTS;
        Intrinsics.checkNotNullExpressionValue(COMMENTS, "COMMENTS");
        return COMMENTS;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getStringLiteralElements() {
        TokenSet STRINGS = KtTokens.STRINGS;
        Intrinsics.checkNotNullExpressionValue(STRINGS, "STRINGS");
        return STRINGS;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiElement createElement(ASTNode astNode) {
        Intrinsics.checkNotNullParameter(astNode, "astNode");
        IElementType elementType = astNode.getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        if (elementType instanceof KtStubElementType) {
            KtElementImplStub ktElementImplStubCreatePsiFromAst = ((KtStubElementType) elementType).createPsiFromAst(astNode);
            Intrinsics.checkNotNullExpressionValue(ktElementImplStubCreatePsiFromAst, "createPsiFromAst(...)");
            return ktElementImplStubCreatePsiFromAst;
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.TYPE_CODE_FRAGMENT) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.EXPRESSION_CODE_FRAGMENT) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.BLOCK_CODE_FRAGMENT)) {
            return new ASTWrapperPsiElement(astNode);
        }
        if (elementType instanceof KDocElementType) {
            PsiElement psiElementCreatePsi = ((KDocElementType) elementType).createPsi(astNode);
            Intrinsics.checkNotNullExpressionValue(psiElementCreatePsi, "createPsi(...)");
            return psiElementCreatePsi;
        }
        if (Intrinsics.areEqual(elementType, KDocTokens.MARKDOWN_LINK)) {
            return new KDocLink(astNode);
        }
        KtElement ktElementCreatePsi = ((KtNodeType) elementType).createPsi(astNode);
        Intrinsics.checkNotNullExpressionValue(ktElementCreatePsi, "createPsi(...)");
        return ktElementCreatePsi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        Intrinsics.checkNotNullParameter(fileViewProvider, "fileViewProvider");
        return new KtFile(fileViewProvider, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    @Deprecated(message = "Deprecated in Java")
    public ParserDefinition.SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        IElementType elementType = right.getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        if (Intrinsics.areEqual(elementType, KtTokens.GET_KEYWORD) || Intrinsics.areEqual(elementType, KtTokens.SET_KEYWORD)) {
            return ParserDefinition.SpaceRequirements.MUST_LINE_BREAK;
        }
        IElementType elementType2 = left.getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType2, "getElementType(...)");
        if ((elementType2 instanceof KtKeywordToken) && (elementType instanceof KtKeywordToken)) {
            return ParserDefinition.SpaceRequirements.MUST;
        }
        PsiElement psi = right.getPsi();
        Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
        KtWhenEntry ktWhenEntry = (KtWhenEntry) PsiTreeUtil.getParentOfType(psi, KtWhenEntry.class, false);
        if (ktWhenEntry != null) {
            PsiElement psi2 = left.getPsi();
            Intrinsics.checkNotNullExpressionValue(psi2, "getPsi(...)");
            KtWhenEntry ktWhenEntry2 = (KtWhenEntry) PsiTreeUtil.getParentOfType(psi2, KtWhenEntry.class, false);
            if (ktWhenEntry2 != null && !Intrinsics.areEqual(ktWhenEntry2, ktWhenEntry) && !Intrinsics.areEqual(elementType2, KtTokens.SEMICOLON)) {
                return ParserDefinition.SpaceRequirements.MUST_LINE_BREAK;
            }
        }
        return ParserDefinition.SpaceRequirements.MAY;
    }

    /* JADX INFO: compiled from: KotlinParserDefinition.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/parsing/KotlinParserDefinition$Companion;", "", "()V", "STD_SCRIPT_EXT", "", "STD_SCRIPT_SUFFIX", "instance", "Lorg/jetbrains/kotlin/parsing/KotlinParserDefinition;", "getInstance", "()Lorg/jetbrains/kotlin/parsing/KotlinParserDefinition;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinParserDefinition getInstance() {
            ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(KotlinLanguage.INSTANCE);
            Intrinsics.checkNotNull(parserDefinitionForLanguage, "null cannot be cast to non-null type org.jetbrains.kotlin.parsing.KotlinParserDefinition");
            return (KotlinParserDefinition) parserDefinitionForLanguage;
        }
    }
}
