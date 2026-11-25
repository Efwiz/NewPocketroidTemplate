package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/SyntheticVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "fir", "Lorg/jetbrains/kotlin/fir/FirElement;", "variableIndexForDebug", "", "(Lorg/jetbrains/kotlin/fir/FirElement;I)V", "getFir", "()Lorg/jetbrains/kotlin/fir/FirElement;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticVariable extends DataFlowVariable {
    private final FirElement fir;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyntheticVariable(FirElement fir, int i) {
        super(i, null);
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.fir = fir;
    }

    public final FirElement getFir() {
        return this.fir;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.SyntheticVariable");
        return DfaVariablesKt.isEqualsTo(this.fir, ((SyntheticVariable) other).fir);
    }

    public int hashCode() {
        FirElement firElement = this.fir;
        if (firElement instanceof FirResolvedQualifier) {
            int iHashCode = ((FirResolvedQualifier) firElement).getPackageFqName().hashCode() * 31;
            ClassId classId = ((FirResolvedQualifier) this.fir).getClassId();
            return iHashCode + (classId != null ? classId.hashCode() : 0);
        }
        return firElement.hashCode();
    }
}
