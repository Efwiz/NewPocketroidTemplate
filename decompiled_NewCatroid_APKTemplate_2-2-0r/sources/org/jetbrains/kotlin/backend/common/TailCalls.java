package org.jetbrains.kotlin.backend.common;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: TailRecursionCallsCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/TailCalls;", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "fromManyFunctions", "", "(Ljava/util/Set;Z)V", "getFromManyFunctions", "()Z", "getIr", "()Ljava/util/Set;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TailCalls {
    private final boolean fromManyFunctions;
    private final Set<IrCall> ir;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TailCalls copy$default(TailCalls tailCalls, Set set, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            set = tailCalls.ir;
        }
        if ((i & 2) != 0) {
            z = tailCalls.fromManyFunctions;
        }
        return tailCalls.copy(set, z);
    }

    public final Set<IrCall> component1() {
        return this.ir;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getFromManyFunctions() {
        return this.fromManyFunctions;
    }

    public final TailCalls copy(Set<? extends IrCall> ir, boolean fromManyFunctions) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        return new TailCalls(ir, fromManyFunctions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TailCalls)) {
            return false;
        }
        TailCalls tailCalls = (TailCalls) other;
        return Intrinsics.areEqual(this.ir, tailCalls.ir) && this.fromManyFunctions == tailCalls.fromManyFunctions;
    }

    public int hashCode() {
        return (this.ir.hashCode() * 31) + Boolean.hashCode(this.fromManyFunctions);
    }

    public String toString() {
        return "TailCalls(ir=" + this.ir + ", fromManyFunctions=" + this.fromManyFunctions + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TailCalls(Set<? extends IrCall> ir, boolean z) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.ir = ir;
        this.fromManyFunctions = z;
    }

    public final boolean getFromManyFunctions() {
        return this.fromManyFunctions;
    }

    public final Set<IrCall> getIr() {
        return this.ir;
    }
}
