package org.jetbrains.kotlin.backend.common.bridges;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.bridges.FunctionHandle;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: bridges.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\n\b\u0001\u0010\u0002 \u0001*\u00020\u00032\u00020\u0004B#\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u0010\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0011\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\bHÆ\u0003J>\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u00002\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\bHÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0004HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", "Signature", "Function", "Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;", "", TypedValues.TransitionType.S_FROM, "to", "originalFunctions", "", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Set;)V", "getFrom", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getOriginalFunctions", "()Ljava/util/Set;", "getTo", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Set;)Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Bridge<Signature, Function extends FunctionHandle> {
    private final Signature from;
    private final Set<Function> originalFunctions;
    private final Signature to;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Bridge copy$default(Bridge bridge, Object obj, Object obj2, Set set, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = bridge.from;
        }
        if ((i & 2) != 0) {
            obj2 = bridge.to;
        }
        if ((i & 4) != 0) {
            set = bridge.originalFunctions;
        }
        return bridge.copy(obj, obj2, set);
    }

    public final Signature component1() {
        return this.from;
    }

    public final Signature component2() {
        return this.to;
    }

    public final Set<Function> component3() {
        return this.originalFunctions;
    }

    public final Bridge<Signature, Function> copy(Signature from, Signature to, Set<? extends Function> originalFunctions) {
        Intrinsics.checkNotNullParameter(originalFunctions, "originalFunctions");
        return new Bridge<>(from, to, originalFunctions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Bridge)) {
            return false;
        }
        Bridge bridge = (Bridge) other;
        return Intrinsics.areEqual(this.from, bridge.from) && Intrinsics.areEqual(this.to, bridge.to) && Intrinsics.areEqual(this.originalFunctions, bridge.originalFunctions);
    }

    public int hashCode() {
        Signature signature = this.from;
        int iHashCode = (signature == null ? 0 : signature.hashCode()) * 31;
        Signature signature2 = this.to;
        return ((iHashCode + (signature2 != null ? signature2.hashCode() : 0)) * 31) + this.originalFunctions.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Bridge(Signature signature, Signature signature2, Set<? extends Function> originalFunctions) {
        Intrinsics.checkNotNullParameter(originalFunctions, "originalFunctions");
        this.from = signature;
        this.to = signature2;
        this.originalFunctions = originalFunctions;
    }

    public final Signature getFrom() {
        return this.from;
    }

    public final Signature getTo() {
        return this.to;
    }

    public final Set<Function> getOriginalFunctions() {
        return this.originalFunctions;
    }

    public String toString() {
        return this.from + " -> " + this.to;
    }
}
