package org.jetbrains.kotlin.fir.analysis;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: LighterTreeElementFinderByType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B9\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\tH\u0002R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/LighterTreeElementFinderByType;", "", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "index", "", ConfigConstants.CONFIG_KEY_DEPTH, "reverse", "", "(Lcom/intellij/util/diff/FlyweightCapableTreeStructure;Ljava/util/Collection;IIZ)V", "find", "node", "visitNode", "currentDepth", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LighterTreeElementFinderByType {
    private final int depth;
    private int index;
    private final boolean reverse;
    private final FlyweightCapableTreeStructure<LighterASTNode> tree;
    private Collection<? extends IElementType> types;

    public LighterTreeElementFinderByType(FlyweightCapableTreeStructure<LighterASTNode> tree, Collection<? extends IElementType> types, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(types, "types");
        this.tree = tree;
        this.types = types;
        this.index = i;
        this.depth = i2;
        this.reverse = z;
    }

    public final LighterASTNode find(LighterASTNode node) {
        if (node == null) {
            return null;
        }
        return visitNode(node, 0);
    }

    private final LighterASTNode visitNode(LighterASTNode node, int currentDepth) {
        if (currentDepth != 0 && this.types.contains(node.getTokenType())) {
            int i = this.index;
            if (i == 0) {
                return node;
            }
            this.index = i - 1;
        }
        if (currentDepth == this.depth) {
            return null;
        }
        Iterator<LighterASTNode> it = (this.reverse ? CollectionsKt.asReversed(LightTreeUtilsKt.getChildren(node, this.tree)) : LightTreeUtilsKt.getChildren(node, this.tree)).iterator();
        while (it.hasNext()) {
            LighterASTNode lighterASTNodeVisitNode = visitNode(it.next(), currentDepth + 1);
            if (lighterASTNodeVisitNode != null) {
                return lighterASTNodeVisitNode;
            }
        }
        return null;
    }
}
