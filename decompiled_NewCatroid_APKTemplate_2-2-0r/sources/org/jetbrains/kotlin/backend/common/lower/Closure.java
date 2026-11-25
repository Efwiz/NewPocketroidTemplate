package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClosureAnnotator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/Closure;", "", "capturedValues", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "capturedTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Ljava/util/List;Ljava/util/List;)V", "getCapturedTypeParameters", "()Ljava/util/List;", "getCapturedValues", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Closure {
    private final List<IrTypeParameter> capturedTypeParameters;
    private final List<IrValueSymbol> capturedValues;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Closure copy$default(Closure closure, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = closure.capturedValues;
        }
        if ((i & 2) != 0) {
            list2 = closure.capturedTypeParameters;
        }
        return closure.copy(list, list2);
    }

    public final List<IrValueSymbol> component1() {
        return this.capturedValues;
    }

    public final List<IrTypeParameter> component2() {
        return this.capturedTypeParameters;
    }

    public final Closure copy(List<? extends IrValueSymbol> capturedValues, List<? extends IrTypeParameter> capturedTypeParameters) {
        Intrinsics.checkNotNullParameter(capturedValues, "capturedValues");
        Intrinsics.checkNotNullParameter(capturedTypeParameters, "capturedTypeParameters");
        return new Closure(capturedValues, capturedTypeParameters);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Closure)) {
            return false;
        }
        Closure closure = (Closure) other;
        return Intrinsics.areEqual(this.capturedValues, closure.capturedValues) && Intrinsics.areEqual(this.capturedTypeParameters, closure.capturedTypeParameters);
    }

    public int hashCode() {
        return (this.capturedValues.hashCode() * 31) + this.capturedTypeParameters.hashCode();
    }

    public String toString() {
        return "Closure(capturedValues=" + this.capturedValues + ", capturedTypeParameters=" + this.capturedTypeParameters + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Closure(List<? extends IrValueSymbol> capturedValues, List<? extends IrTypeParameter> capturedTypeParameters) {
        Intrinsics.checkNotNullParameter(capturedValues, "capturedValues");
        Intrinsics.checkNotNullParameter(capturedTypeParameters, "capturedTypeParameters");
        this.capturedValues = capturedValues;
        this.capturedTypeParameters = capturedTypeParameters;
    }

    public final List<IrTypeParameter> getCapturedTypeParameters() {
        return this.capturedTypeParameters;
    }

    public final List<IrValueSymbol> getCapturedValues() {
        return this.capturedValues;
    }
}
