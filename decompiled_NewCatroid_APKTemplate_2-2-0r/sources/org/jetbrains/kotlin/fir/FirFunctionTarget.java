package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;

/* JADX INFO: compiled from: FirFunctionTarget.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R$\u0010\t\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00028T@TX\u0094\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000eR\u0012\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirFunctionTarget;", "Lorg/jetbrains/kotlin/fir/FirAbstractTarget;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "labelName", "", "isLambda", "", "(Ljava/lang/String;Z)V", "value", "_labeledElement", "get_labeledElement", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "set_labeledElement", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)V", "()Z", "targetSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionTarget extends FirAbstractTarget<FirFunction> {
    private final boolean isLambda;
    private FirFunctionSymbol<?> targetSymbol;

    /* JADX INFO: renamed from: isLambda, reason: from getter */
    public final boolean getIsLambda() {
        return this.isLambda;
    }

    public FirFunctionTarget(String str, boolean z) {
        super(str);
        this.isLambda = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.FirAbstractTarget
    public FirFunction get_labeledElement() {
        FirFunctionSymbol<?> firFunctionSymbol = this.targetSymbol;
        if (firFunctionSymbol == null) {
            Intrinsics.throwUninitializedPropertyAccessException("targetSymbol");
            firFunctionSymbol = null;
        }
        return (FirFunction) firFunctionSymbol.getFir();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.FirAbstractTarget
    public void set_labeledElement(FirFunction value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.targetSymbol = value.getSymbol();
    }
}
