package org.jetbrains.kotlin.diagnostics;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: UnreachableCodeLightTreeHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\u0003¢\u0006\u0002\u0010\u0006J<\u0010\t\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\n*\u00060\u0004j\u0002`\u00052\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\f2\u0010\u0010\r\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\fJ \u0010\u000e\u001a\u00020\u000f*\u00060\u0004j\u0002`\u00052\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\fJ\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\n*\b\u0012\u0004\u0012\u00020\u00120\nJ0\u0010\u0013\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\n*\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\n2\u0010\u0010\u0014\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\fR\u001b\u0010\u0002\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/UnreachableCodeLightTreeHelper;", "", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "Lorg/jetbrains/kotlin/diagnostics/Node;", "(Lcom/intellij/util/diff/FlyweightCapableTreeStructure;)V", "getTree", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", "getLeavesOrReachableChildren", "", "reachable", "", "unreachable", "hasChildrenInSet", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "mergeAdjacentTextRanges", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "removeReachableElementsWithMeaninglessSiblings", "reachableElements", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnreachableCodeLightTreeHelper {
    private final FlyweightCapableTreeStructure<LighterASTNode> tree;

    public UnreachableCodeLightTreeHelper(FlyweightCapableTreeStructure<LighterASTNode> tree) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        this.tree = tree;
    }

    public final FlyweightCapableTreeStructure<LighterASTNode> getTree() {
        return this.tree;
    }

    public final boolean hasChildrenInSet(final LighterASTNode lighterASTNode, final Set<? extends LighterASTNode> set) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(set, "set");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        LightTreePositioningStrategiesKt.traverseDescendants(this.tree, lighterASTNode, new Function1<LighterASTNode, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.UnreachableCodeLightTreeHelper.hasChildrenInSet.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LighterASTNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!booleanRef.element && !Intrinsics.areEqual(it, lighterASTNode) && set.contains(it)) {
                    booleanRef.element = true;
                }
                return Boolean.valueOf(!booleanRef.element);
            }
        });
        return booleanRef.element;
    }

    public final List<LighterASTNode> getLeavesOrReachableChildren(LighterASTNode lighterASTNode, final Set<? extends LighterASTNode> reachable, final Set<? extends LighterASTNode> unreachable) {
        Intrinsics.checkNotNullParameter(lighterASTNode, "<this>");
        Intrinsics.checkNotNullParameter(reachable, "reachable");
        Intrinsics.checkNotNullParameter(unreachable, "unreachable");
        final ArrayList arrayList = new ArrayList();
        LightTreePositioningStrategiesKt.traverseDescendants(this.tree, lighterASTNode, new Function1<LighterASTNode, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.UnreachableCodeLightTreeHelper.getLeavesOrReachableChildren.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LighterASTNode element) {
                boolean z;
                Intrinsics.checkNotNullParameter(element, "element");
                if ((!reachable.contains(element) || this.hasChildrenInSet(element, unreachable)) && !LightTreeUtilsKt.getChildren(element, this.getTree()).isEmpty()) {
                    z = true;
                } else {
                    arrayList.add(element);
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        return arrayList;
    }

    public final List<LighterASTNode> removeReachableElementsWithMeaninglessSiblings(List<? extends LighterASTNode> list, Set<? extends LighterASTNode> reachableElements) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(reachableElements, "reachableElements");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int i = 0;
        for (LighterASTNode lighterASTNode : list) {
            int i2 = i + 1;
            if (reachableElements.contains(lighterASTNode)) {
                linkedHashSet.add(lighterASTNode);
                removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, linkedHashSet, i, -1);
                removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, linkedHashSet, i, 1);
            }
            i = i2;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!linkedHashSet.contains((LighterASTNode) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private static final void removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(List<? extends LighterASTNode> list, Set<LighterASTNode> set, int i, int i2) {
        int i3 = i + i2;
        if (i3 < 0 || i3 >= list.size()) {
            return;
        }
        LighterASTNode lighterASTNode = list.get(i3);
        if (LightTreePositioningStrategyKt.isFiller(lighterASTNode) || Intrinsics.areEqual(lighterASTNode.getTokenType(), KtTokens.COMMA)) {
            set.add(lighterASTNode);
            removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, set, i3, i2);
        }
    }

    public final List<TextRange> mergeAdjacentTextRanges(List<? extends TextRange> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        TextRange textRangeUnion = null;
        for (TextRange textRange : list) {
            if (textRangeUnion != null) {
                if (textRangeUnion.getEndOffset() == textRange.getStartOffset()) {
                    textRangeUnion = textRangeUnion.union(textRange);
                } else {
                    arrayList.add(textRangeUnion);
                }
            }
            textRangeUnion = textRange;
        }
        if (textRangeUnion != null) {
            arrayList.add(textRangeUnion);
        }
        return arrayList;
    }
}
