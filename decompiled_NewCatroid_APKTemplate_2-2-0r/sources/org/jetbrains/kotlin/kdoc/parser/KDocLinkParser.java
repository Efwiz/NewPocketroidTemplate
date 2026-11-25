package org.jetbrains.kotlin.kdoc.parser;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: KDocLinkParser.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/parser/KDocLinkParser;", "Lorg/jetbrains/kotlin/com/intellij/lang/PsiParser;", "()V", "isName", "", "tokenType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "parse", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "root", "builder", "Lorg/jetbrains/kotlin/com/intellij/lang/PsiBuilder;", "parseQualifiedName", "", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KDocLinkParser implements PsiParser {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final ASTNode parseMarkdownLink(IElementType iElementType, ASTNode aSTNode) {
        return INSTANCE.parseMarkdownLink(iElementType, aSTNode);
    }

    /* JADX INFO: compiled from: KDocLinkParser.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/parser/KDocLinkParser$Companion;", "", "()V", "parseMarkdownLink", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "root", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "chameleon", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ASTNode parseMarkdownLink(IElementType root, ASTNode chameleon) {
            Intrinsics.checkNotNullParameter(root, "root");
            Intrinsics.checkNotNullParameter(chameleon, "chameleon");
            Project project = chameleon.getTreeParent().getPsi().getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon, new KotlinLexer(), root.getLanguage(), chameleon.getText());
            Intrinsics.checkNotNullExpressionValue(psiBuilderCreateBuilder, "createBuilder(...)");
            ASTNode firstChildNode = new KDocLinkParser().parse(root, psiBuilderCreateBuilder).getFirstChildNode();
            Intrinsics.checkNotNullExpressionValue(firstChildNode, "getFirstChildNode(...)");
            return firstChildNode;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiParser
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(builder, "builder");
        PsiBuilder.Marker markerM6463mark = builder.m6463mark();
        Intrinsics.checkNotNullExpressionValue(markerM6463mark, "mark(...)");
        boolean zAreEqual = Intrinsics.areEqual(builder.getTokenType(), KtTokens.LBRACKET);
        if (zAreEqual) {
            builder.advanceLexer();
        }
        parseQualifiedName(builder);
        if (zAreEqual) {
            if (!builder.eof() && !Intrinsics.areEqual(builder.getTokenType(), KtTokens.RBRACKET)) {
                builder.error("Closing bracket expected");
                while (!builder.eof() && !Intrinsics.areEqual(builder.getTokenType(), KtTokens.RBRACKET)) {
                    builder.advanceLexer();
                }
            }
            if (Intrinsics.areEqual(builder.getTokenType(), KtTokens.RBRACKET)) {
                builder.advanceLexer();
            }
        } else if (!builder.eof()) {
            builder.error("Expression expected");
            while (!builder.eof()) {
                builder.advanceLexer();
            }
        }
        markerM6463mark.done(root);
        ASTNode treeBuilt = builder.getTreeBuilt();
        Intrinsics.checkNotNullExpressionValue(treeBuilt, "getTreeBuilt(...)");
        return treeBuilt;
    }

    private final void parseQualifiedName(PsiBuilder builder) {
        PsiBuilder.Marker markerM6463mark = builder.m6463mark();
        Intrinsics.checkNotNullExpressionValue(markerM6463mark, "mark(...)");
        while (isName(builder.getTokenType())) {
            builder.advanceLexer();
            markerM6463mark.done(KDocElementTypes.KDOC_NAME);
            if (!Intrinsics.areEqual(builder.getTokenType(), KtTokens.DOT)) {
                return;
            }
            markerM6463mark = markerM6463mark.m6464precede();
            Intrinsics.checkNotNullExpressionValue(markerM6463mark, "precede(...)");
            builder.advanceLexer();
        }
        markerM6463mark.drop();
        builder.error("Identifier expected");
    }

    private final boolean isName(IElementType tokenType) {
        return Intrinsics.areEqual(tokenType, KtTokens.IDENTIFIER) || KtTokens.KEYWORDS.contains(tokenType);
    }
}
