package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirLoop;

/* JADX INFO: compiled from: FirLoopTarget.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\u00020\u0002X\u0094.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirLoopTarget;", "Lorg/jetbrains/kotlin/fir/FirAbstractTarget;", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "labelName", "", "(Ljava/lang/String;)V", "_labeledElement", "get_labeledElement", "()Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "set_labeledElement", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoop;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLoopTarget extends FirAbstractTarget<FirLoop> {
    protected FirLoop _labeledElement;

    public FirLoopTarget(String str) {
        super(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.FirAbstractTarget
    public FirLoop get_labeledElement() {
        FirLoop firLoop = this._labeledElement;
        if (firLoop != null) {
            return firLoop;
        }
        Intrinsics.throwUninitializedPropertyAccessException("_labeledElement");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.FirAbstractTarget
    public void set_labeledElement(FirLoop firLoop) {
        Intrinsics.checkNotNullParameter(firLoop, "<set-?>");
        this._labeledElement = firLoop;
    }
}
