package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.parsing.KotlinParser;
import org.jetbrains.kotlin.psi.KtBlockExpression;

/* JADX INFO: compiled from: BlockExpressionElementType.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00172\u00020\u00012\u00020\u0002:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J*\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0013H\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/BlockExpressionElementType;", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IErrorCounterReparseableElementType;", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/ICompositeElementType;", "()V", "createCompositeNode", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "createNode", "text", "", "getErrorsCount", "", "seq", "fileLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "isParsable", "", "parent", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "buffer", "parseContents", "chameleon", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BlockExpressionElementType extends IErrorCounterReparseableElementType implements ICompositeElementType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public BlockExpressionElementType() {
        super("BLOCK", KotlinLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public KtBlockExpression createCompositeNode() {
        return new KtBlockExpression(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
    public KtBlockExpression createNode(CharSequence text) {
        return new KtBlockExpression(text);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementTypeBase
    public boolean isParsable(ASTNode parent, CharSequence buffer, Language fileLanguage, Project project) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNullParameter(fileLanguage, "fileLanguage");
        Intrinsics.checkNotNullParameter(project, "project");
        if (Intrinsics.areEqual(fileLanguage, KotlinLanguage.INSTANCE)) {
            Companion companion = INSTANCE;
            if (companion.isAllowedParentNode(parent) && companion.isReparseableBlock(buffer) && super.isParsable(buffer, fileLanguage, project)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType
    public int getErrorsCount(CharSequence seq, Language fileLanguage, Project project) {
        Intrinsics.checkNotNullParameter(seq, "seq");
        Intrinsics.checkNotNullParameter(fileLanguage, "fileLanguage");
        Intrinsics.checkNotNullParameter(project, "project");
        return ElementTypeUtils.getKotlinBlockImbalanceCount(seq);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
    public ASTNode parseContents(ASTNode chameleon) {
        Intrinsics.checkNotNullParameter(chameleon, "chameleon");
        Project project = chameleon.getPsi().getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon, (Lexer) null, KotlinLanguage.INSTANCE, chameleon.getChars());
        Intrinsics.checkNotNullExpressionValue(psiBuilderCreateBuilder, "createBuilder(...)");
        ASTNode firstChildNode = KotlinParser.parseBlockExpression(psiBuilderCreateBuilder).getFirstChildNode();
        Intrinsics.checkNotNullExpressionValue(firstChildNode, "getFirstChildNode(...)");
        return firstChildNode;
    }

    /* JADX INFO: compiled from: BlockExpressionElementType.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/BlockExpressionElementType$Companion;", "", "()V", "isAllowedParentNode", "", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "isReparseableBlock", "blockText", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isAllowedParentNode(ASTNode node) {
            return (node == null || Intrinsics.areEqual(KtNodeTypes.SCRIPT, node.getElementType()) || Intrinsics.areEqual(KtNodeTypes.FUNCTION_LITERAL, node.getElementType()) || Intrinsics.areEqual(KtNodeTypes.BLOCK_CODE_FRAGMENT, node.getElementType())) ? false : true;
        }

        private static final boolean isReparseableBlock$advanceWhitespacesCheckIsEndOrArrow(KotlinLexer kotlinLexer) {
            kotlinLexer.advance();
            while (kotlinLexer.getTokenType() != null && !Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.EOF) && !Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.ARROW)) {
                if (!Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.WHITE_SPACE)) {
                    return false;
                }
                kotlinLexer.advance();
            }
            return true;
        }

        public final boolean isReparseableBlock(CharSequence blockText) {
            Intrinsics.checkNotNullParameter(blockText, "blockText");
            KotlinLexer kotlinLexer = new KotlinLexer();
            kotlinLexer.start(blockText);
            if (!Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.LBRACE) || isReparseableBlock$advanceWhitespacesCheckIsEndOrArrow(kotlinLexer)) {
                return false;
            }
            if (!Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.COLON) && !Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.IDENTIFIER) && !Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.LPAR)) {
                return true;
            }
            boolean zAreEqual = Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.LPAR);
            if (isReparseableBlock$advanceWhitespacesCheckIsEndOrArrow(kotlinLexer)) {
                return false;
            }
            boolean z = Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.COMMA) || Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.COLON);
            while (!Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.LBRACE)) {
                if (Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.RBRACE)) {
                    return !z;
                }
                if (zAreEqual && Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.RPAR)) {
                    return !isReparseableBlock$advanceWhitespacesCheckIsEndOrArrow(kotlinLexer);
                }
                if (isReparseableBlock$advanceWhitespacesCheckIsEndOrArrow(kotlinLexer)) {
                    return false;
                }
            }
            return true;
        }
    }
}
