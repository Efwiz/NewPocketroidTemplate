package org.jetbrains.kotlin;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.parsing.KotlinExpressionParsing;
import org.jetbrains.kotlin.psi.stubs.elements.KtClassLiteralExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtCollectionLiteralExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstantExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStringTemplateExpressionElementType;

/* JADX INFO: compiled from: ElementTypeUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\n\u0010\u000b\u001a\u00020\u0005*\u00020\fJ\n\u0010\r\u001a\u00020\u000e*\u00020\u000fR\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ElementTypeUtils;", "", "()V", "expressionSet", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getKotlinBlockImbalanceCount", "", "seq", "", "getOperationSymbol", "", "isExpression", "", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ElementTypeUtils {
    public static final ElementTypeUtils INSTANCE = new ElementTypeUtils();
    private static final List<IElementType> expressionSet = CollectionsKt.listOf((Object[]) new IElementType[]{KtNodeTypes.REFERENCE_EXPRESSION, KtNodeTypes.DOT_QUALIFIED_EXPRESSION, KtNodeTypes.LAMBDA_EXPRESSION, KtNodeTypes.FUN});

    private ElementTypeUtils() {
    }

    @JvmStatic
    public static final int getKotlinBlockImbalanceCount(CharSequence seq) {
        IElementType tokenType;
        Intrinsics.checkNotNullParameter(seq, "seq");
        KotlinLexer kotlinLexer = new KotlinLexer();
        kotlinLexer.start(seq);
        if (kotlinLexer.getTokenType() != KtTokens.LBRACE) {
            return Integer.MIN_VALUE;
        }
        kotlinLexer.advance();
        int i = 1;
        while (!Intrinsics.areEqual(kotlinLexer.getTokenType(), KtTokens.EOF) && (tokenType = kotlinLexer.getTokenType()) != null) {
            if (i == 0) {
                return Integer.MIN_VALUE;
            }
            if (tokenType == KtTokens.LBRACE) {
                i++;
            } else if (tokenType == KtTokens.RBRACE) {
                i--;
            }
            kotlinLexer.advance();
        }
        return i;
    }

    public final IElementType getOperationSymbol(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        IElementType[] types = KotlinExpressionParsing.ALL_OPERATIONS.getTypes();
        Intrinsics.checkNotNullExpressionValue(types, "getTypes(...)");
        for (IElementType iElementType : types) {
            if ((iElementType instanceof KtSingleValueToken) && Intrinsics.areEqual(((KtSingleValueToken) iElementType).getValue(), str)) {
                return iElementType;
            }
        }
        if (Intrinsics.areEqual(str, "as?")) {
            KtToken AS_SAFE = KtTokens.AS_SAFE;
            Intrinsics.checkNotNullExpressionValue(AS_SAFE, "AS_SAFE");
            return AS_SAFE;
        }
        KtToken IDENTIFIER = KtTokens.IDENTIFIER;
        Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
        return IDENTIFIER;
    }

    public final boolean isExpression(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        IElementType tokenType = lighterASTNode.getTokenType();
        if (tokenType instanceof KtNodeType ? true : tokenType instanceof KtConstantExpressionElementType ? true : tokenType instanceof KtStringTemplateExpressionElementType ? true : tokenType instanceof KtClassLiteralExpressionElementType ? true : tokenType instanceof KtCollectionLiteralExpressionElementType) {
            return true;
        }
        return expressionSet.contains(tokenType);
    }
}
