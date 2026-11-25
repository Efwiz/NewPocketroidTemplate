package org.jetbrains.kotlin.diagnostics;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: LightTreePositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002\u001a<\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\r\u001aB\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010\u0015\u001a\u00020\r\u001a<\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010\u0015\u001a\u00020\r\u001a\f\u0010\u001a\u001a\u00020\u000b*\u00020\rH\u0000\u001a\u001a\u0010\u001b\u001a\u00020\r*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002\u001a\u001a\u0010\u001c\u001a\u00020\r*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0000\"\u001c\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001d"}, d2 = {"DOC_AND_COMMENT_TOKENS", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "FILLER_TOKENS", "startOffsetSkippingComments", "", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "getStartOffsetSkippingComments", "(Lorg/jetbrains/kotlin/KtLightSourceElement;)I", "hasSyntaxErrors", "", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "markElement", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "startOffset", "endOffset", "originalNode", "markRange", TypedValues.TransitionType.S_FROM, "to", "markSingleElement", "isFiller", "nonFillerFirstChildOrSelf", "nonFillerLastChildOrSelf", "frontend.common-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightTreePositioningStrategyKt {
    private static final Set<IElementType> DOC_AND_COMMENT_TOKENS = SetsKt.setOf((Object[]) new IElementType[]{KtTokens.WHITE_SPACE, KtTokens.IDENTIFIER, KtTokens.EOL_COMMENT, KtTokens.BLOCK_COMMENT, KtTokens.SHEBANG_COMMENT, KtTokens.DOC_COMMENT});
    private static final Set<IElementType> FILLER_TOKENS = SetsKt.setOf((Object[]) new IElementType[]{KtTokens.WHITE_SPACE, KtTokens.EOL_COMMENT, KtTokens.BLOCK_COMMENT, KtTokens.SHEBANG_COMMENT, KtTokens.DOC_COMMENT});

    public static /* synthetic */ List markElement$default(LighterASTNode lighterASTNode, int i, int i2, FlyweightCapableTreeStructure flyweightCapableTreeStructure, LighterASTNode lighterASTNode2, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            lighterASTNode2 = lighterASTNode;
        }
        return markElement(lighterASTNode, i, i2, flyweightCapableTreeStructure, lighterASTNode2);
    }

    public static final List<TextRange> markElement(LighterASTNode node, int i, int i2, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode originalNode) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(originalNode, "originalNode");
        return markRange(node, node, i, i2, tree, originalNode);
    }

    public static final List<TextRange> markRange(LighterASTNode from, LighterASTNode to, int i, int i2, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode originalNode) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(originalNode, "originalNode");
        return CollectionsKt.listOf(markSingleElement(from, to, i, i2, tree, originalNode));
    }

    public static final TextRange markSingleElement(LighterASTNode from, LighterASTNode to, int i, int i2, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode originalNode) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(originalNode, "originalNode");
        LighterASTNode lighterASTNodeNonFillerFirstChildOrSelf = nonFillerFirstChildOrSelf(from, tree);
        LighterASTNode lighterASTNodeNonFillerLastChildOrSelf = nonFillerLastChildOrSelf(to, tree);
        return new TextRange((tree.getStartOffset(lighterASTNodeNonFillerFirstChildOrSelf) - tree.getStartOffset(originalNode)) + i, (tree.getEndOffset(lighterASTNodeNonFillerLastChildOrSelf) - tree.getEndOffset(originalNode)) + i2);
    }

    private static final LighterASTNode nonFillerFirstChildOrSelf(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure) {
        Object next;
        Iterator<T> it = LightTreeUtilsKt.getChildren(lighterASTNode, flyweightCapableTreeStructure).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (isFiller((LighterASTNode) next));
        LighterASTNode lighterASTNode2 = (LighterASTNode) next;
        return lighterASTNode2 == null ? lighterASTNode : lighterASTNode2;
    }

    public static final LighterASTNode nonFillerLastChildOrSelf(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> tree) {
        LighterASTNode lighterASTNodePrevious;
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(tree, "tree");
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(lighterASTNode, tree);
        ListIterator<LighterASTNode> listIterator = children.listIterator(children.size());
        do {
            if (!listIterator.hasPrevious()) {
                lighterASTNodePrevious = null;
                break;
            }
            lighterASTNodePrevious = listIterator.previous();
        } while (isFiller(lighterASTNodePrevious));
        LighterASTNode lighterASTNode2 = lighterASTNodePrevious;
        return lighterASTNode2 == null ? lighterASTNode : lighterASTNode2;
    }

    public static final boolean isFiller(LighterASTNode lighterASTNode) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        return FILLER_TOKENS.contains(lighterASTNode.getTokenType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasSyntaxErrors(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure) {
        LighterASTNode lighterASTNodePrevious;
        if (Intrinsics.areEqual(lighterASTNode.getTokenType(), TokenType.ERROR_ELEMENT)) {
            return true;
        }
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(lighterASTNode, flyweightCapableTreeStructure);
        ListIterator<LighterASTNode> listIterator = children.listIterator(children.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                lighterASTNodePrevious = null;
                break;
            }
            lighterASTNodePrevious = listIterator.previous();
            IElementType tokenType = lighterASTNodePrevious.getTokenType();
            if (!(tokenType instanceof KtSingleValueToken) && !DOC_AND_COMMENT_TOKENS.contains(tokenType)) {
                break;
            }
        }
        LighterASTNode lighterASTNode2 = lighterASTNodePrevious;
        return lighterASTNode2 != null && hasSyntaxErrors(lighterASTNode2, flyweightCapableTreeStructure);
    }

    public static final int getStartOffsetSkippingComments(KtLightSourceElement ktLightSourceElement) {
        Intrinsics.checkNotNullParameter(ktLightSourceElement, "<this>");
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(ktLightSourceElement.getLighterASTNode(), ktLightSourceElement.getTreeStructure());
        ArrayList arrayList = new ArrayList();
        for (Object obj : children) {
            if (!FILLER_TOKENS.contains(((LighterASTNode) obj).getTokenType())) {
                break;
            }
            arrayList.add(obj);
        }
        int startOffset = ktLightSourceElement.getStartOffset();
        Iterator it = arrayList.iterator();
        int textLength = 0;
        while (it.hasNext()) {
            textLength += ((LighterASTNode) it.next()).getTextLength();
        }
        return startOffset + textLength;
    }
}
