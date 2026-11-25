package org.jetbrains.kotlin.cfg;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: BreakableBlockInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J!\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0014\"\u00020\u0005H\u0004¢\u0006\u0002\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cfg/BreakableBlockInfo;", "Lorg/jetbrains/kotlin/cfg/BlockInfo;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "entryPoint", "Lorg/jetbrains/kotlin/cfg/Label;", "exitPoint", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;)V", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getEntryPoint", "()Lorg/jetbrains/kotlin/cfg/Label;", "getExitPoint", "referablePoints", "", "getReferablePoints", "()Ljava/util/Set;", "markReferablePoints", "", "labels", "", "([Lorg/jetbrains/kotlin/cfg/Label;)V", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BreakableBlockInfo extends BlockInfo {
    private final KtElement element;
    private final Label entryPoint;
    private final Label exitPoint;
    private final Set<Label> referablePoints;

    public BreakableBlockInfo(KtElement element, Label entryPoint, Label exitPoint) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(entryPoint, "entryPoint");
        Intrinsics.checkNotNullParameter(exitPoint, "exitPoint");
        this.element = element;
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        this.referablePoints = new HashSet();
        markReferablePoints(entryPoint, exitPoint);
    }

    public KtElement getElement() {
        return this.element;
    }

    public final Label getEntryPoint() {
        return this.entryPoint;
    }

    public final Label getExitPoint() {
        return this.exitPoint;
    }

    public final Set<Label> getReferablePoints() {
        return this.referablePoints;
    }

    protected final void markReferablePoints(Label... labels) {
        Intrinsics.checkNotNullParameter(labels, "labels");
        Collections.addAll(this.referablePoints, Arrays.copyOf(labels, labels.length));
    }
}
