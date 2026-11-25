package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConversionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J)\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/CalleeAndReceiver;", "", "reference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isImplicitInvoke", "", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Z)V", "()Z", "getReceiverExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CalleeAndReceiver {
    private final boolean isImplicitInvoke;
    private final FirExpression receiverExpression;
    private final FirNamedReference reference;

    public static /* synthetic */ CalleeAndReceiver copy$default(CalleeAndReceiver calleeAndReceiver, FirNamedReference firNamedReference, FirExpression firExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            firNamedReference = calleeAndReceiver.reference;
        }
        if ((i & 2) != 0) {
            firExpression = calleeAndReceiver.receiverExpression;
        }
        if ((i & 4) != 0) {
            z = calleeAndReceiver.isImplicitInvoke;
        }
        return calleeAndReceiver.copy(firNamedReference, firExpression, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirNamedReference getReference() {
        return this.reference;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FirExpression getReceiverExpression() {
        return this.receiverExpression;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsImplicitInvoke() {
        return this.isImplicitInvoke;
    }

    public final CalleeAndReceiver copy(FirNamedReference reference, FirExpression receiverExpression, boolean isImplicitInvoke) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return new CalleeAndReceiver(reference, receiverExpression, isImplicitInvoke);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CalleeAndReceiver)) {
            return false;
        }
        CalleeAndReceiver calleeAndReceiver = (CalleeAndReceiver) other;
        return Intrinsics.areEqual(this.reference, calleeAndReceiver.reference) && Intrinsics.areEqual(this.receiverExpression, calleeAndReceiver.receiverExpression) && this.isImplicitInvoke == calleeAndReceiver.isImplicitInvoke;
    }

    public int hashCode() {
        int iHashCode = this.reference.hashCode() * 31;
        FirExpression firExpression = this.receiverExpression;
        return ((iHashCode + (firExpression == null ? 0 : firExpression.hashCode())) * 31) + Boolean.hashCode(this.isImplicitInvoke);
    }

    public String toString() {
        return "CalleeAndReceiver(reference=" + this.reference + ", receiverExpression=" + this.receiverExpression + ", isImplicitInvoke=" + this.isImplicitInvoke + ')';
    }

    public CalleeAndReceiver(FirNamedReference reference, FirExpression firExpression, boolean z) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        this.reference = reference;
        this.receiverExpression = firExpression;
        this.isImplicitInvoke = z;
    }

    public /* synthetic */ CalleeAndReceiver(FirNamedReference firNamedReference, FirExpression firExpression, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firNamedReference, (i & 2) != 0 ? null : firExpression, (i & 4) != 0 ? false : z);
    }

    public final FirNamedReference getReference() {
        return this.reference;
    }

    public final FirExpression getReceiverExpression() {
        return this.receiverExpression;
    }

    public final boolean isImplicitInvoke() {
        return this.isImplicitInvoke;
    }
}
