package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrTypeParameterBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrTypeParameterBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "isReified", "", "()Z", "setReified", "(Z)V", "superTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSuperTypes", "()Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "setVariance", "(Lorg/jetbrains/kotlin/types/Variance;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeParameterBuilder extends IrDeclarationBuilder {
    private boolean isReified;
    private int index = -1;
    private Variance variance = Variance.INVARIANT;
    private final List<IrType> superTypes = new SmartList();

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public final Variance getVariance() {
        return this.variance;
    }

    public final void setVariance(Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "<set-?>");
        this.variance = variance;
    }

    /* JADX INFO: renamed from: isReified, reason: from getter */
    public final boolean getIsReified() {
        return this.isReified;
    }

    public final void setReified(boolean z) {
        this.isReified = z;
    }

    public final List<IrType> getSuperTypes() {
        return this.superTypes;
    }

    public final void updateFrom(IrTypeParameter from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        this.index = from.getIndex();
        this.variance = from.getVariance();
        this.isReified = from.isReified();
    }
}
