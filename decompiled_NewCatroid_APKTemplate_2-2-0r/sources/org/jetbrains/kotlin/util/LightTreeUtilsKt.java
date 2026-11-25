package org.jetbrains.kotlin.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: compiled from: LightTreeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¨\u0006\u0005"}, d2 = {"getChildren", "", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightTreeUtilsKt {
    public static final List<LighterASTNode> getChildren(LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> tree) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Ref<LighterASTNode[]> ref = new Ref<>();
        if (tree.getChildren(lighterASTNode, ref) <= 0) {
            return CollectionsKt.emptyList();
        }
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
        return ArraysKt.filterNotNull(lighterASTNodeArr);
    }
}
