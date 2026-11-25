package org.jetbrains.kotlin.fir.lightTree.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: WhenEntry.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fJ\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\t\u0010\u0018\u001a\u00020\nHÆ\u0003J\t\u0010\u0019\u001a\u00020\nHÆ\u0003JA\u0010\u001a\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\u0006\u0010\u001f\u001a\u00020\u0004J\u0006\u0010 \u001a\u00020\u0004J\t\u0010!\u001a\u00020\"HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/WhenEntry;", "", "conditions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "firBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "isElse", "", "shouldBindSubject", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Lcom/intellij/lang/LighterASTNode;ZZ)V", "getConditions", "()Ljava/util/List;", "getFirBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "()Z", "getNode", "()Lcom/intellij/lang/LighterASTNode;", "getShouldBindSubject", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toFirWhenCondition", "toFirWhenConditionWithoutSubject", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WhenEntry {
    private final List<FirExpression> conditions;
    private final FirBlock firBlock;
    private final boolean isElse;
    private final LighterASTNode node;
    private final boolean shouldBindSubject;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ WhenEntry copy$default(WhenEntry whenEntry, List list, FirBlock firBlock, LighterASTNode lighterASTNode, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = whenEntry.conditions;
        }
        if ((i & 2) != 0) {
            firBlock = whenEntry.firBlock;
        }
        FirBlock firBlock2 = firBlock;
        if ((i & 4) != 0) {
            lighterASTNode = whenEntry.node;
        }
        LighterASTNode lighterASTNode2 = lighterASTNode;
        if ((i & 8) != 0) {
            z = whenEntry.isElse;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = whenEntry.shouldBindSubject;
        }
        return whenEntry.copy(list, firBlock2, lighterASTNode2, z3, z2);
    }

    public final List<FirExpression> component1() {
        return this.conditions;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FirBlock getFirBlock() {
        return this.firBlock;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final LighterASTNode getNode() {
        return this.node;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsElse() {
        return this.isElse;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getShouldBindSubject() {
        return this.shouldBindSubject;
    }

    public final WhenEntry copy(List<? extends FirExpression> conditions, FirBlock firBlock, LighterASTNode node, boolean isElse, boolean shouldBindSubject) {
        Intrinsics.checkNotNullParameter(conditions, "conditions");
        Intrinsics.checkNotNullParameter(firBlock, "firBlock");
        Intrinsics.checkNotNullParameter(node, "node");
        return new WhenEntry(conditions, firBlock, node, isElse, shouldBindSubject);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WhenEntry)) {
            return false;
        }
        WhenEntry whenEntry = (WhenEntry) other;
        return Intrinsics.areEqual(this.conditions, whenEntry.conditions) && Intrinsics.areEqual(this.firBlock, whenEntry.firBlock) && Intrinsics.areEqual(this.node, whenEntry.node) && this.isElse == whenEntry.isElse && this.shouldBindSubject == whenEntry.shouldBindSubject;
    }

    public int hashCode() {
        return (((((((this.conditions.hashCode() * 31) + this.firBlock.hashCode()) * 31) + this.node.hashCode()) * 31) + Boolean.hashCode(this.isElse)) * 31) + Boolean.hashCode(this.shouldBindSubject);
    }

    public String toString() {
        return "WhenEntry(conditions=" + this.conditions + ", firBlock=" + this.firBlock + ", node=" + this.node + ", isElse=" + this.isElse + ", shouldBindSubject=" + this.shouldBindSubject + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WhenEntry(List<? extends FirExpression> conditions, FirBlock firBlock, LighterASTNode node, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(conditions, "conditions");
        Intrinsics.checkNotNullParameter(firBlock, "firBlock");
        Intrinsics.checkNotNullParameter(node, "node");
        this.conditions = conditions;
        this.firBlock = firBlock;
        this.node = node;
        this.isElse = z;
        this.shouldBindSubject = z2;
    }

    public /* synthetic */ WhenEntry(List list, FirBlock firBlock, LighterASTNode lighterASTNode, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, firBlock, lighterASTNode, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }

    public final List<FirExpression> getConditions() {
        return this.conditions;
    }

    public final FirBlock getFirBlock() {
        return this.firBlock;
    }

    public final LighterASTNode getNode() {
        return this.node;
    }

    public final boolean isElse() {
        return this.isElse;
    }

    public final boolean getShouldBindSubject() {
        return this.shouldBindSubject;
    }

    public final FirExpression toFirWhenCondition() {
        if (this.conditions.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ConversionUtilsKt.buildBalancedOrExpressionTree$default(this.conditions, 0, 0, 6, null);
    }

    public final FirExpression toFirWhenConditionWithoutSubject() {
        if (this.conditions.size() == 0) {
            return FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No expression in condition with expression"), null, 4, null);
        }
        return ConversionUtilsKt.buildBalancedOrExpressionTree$default(this.conditions, 0, 0, 6, null);
    }
}
