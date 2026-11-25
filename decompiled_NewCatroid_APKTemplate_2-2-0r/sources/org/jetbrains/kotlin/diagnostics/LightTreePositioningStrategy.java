package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: compiled from: LightTreePositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0016J4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0016J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "", "()V", "isValid", "", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "startOffset", "", "endOffset", "markKtDiagnostic", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtSourceElement;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LightTreePositioningStrategy {
    public List<TextRange> markKtDiagnostic(KtSourceElement element, KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return mark(element.getLighterASTNode(), element.getStartOffset(), element.getEndOffset(), element.getTreeStructure());
    }

    public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(tree, "tree");
        return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
    }

    public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(tree, "tree");
        return !LightTreePositioningStrategyKt.hasSyntaxErrors(node, tree);
    }
}
