package org.jetbrains.kotlin.fir.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ImplicitReceiverUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0005\u0012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003HÆ\u0003J\u0013\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0005HÆ\u0003J\u0013\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005HÆ\u0003JA\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00052\u0012\b\u0002\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001b\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/ImplicitReceivers;", "", "implicitReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "implicitCompanionValues", "", "contextReceivers", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;Ljava/util/List;Ljava/util/List;)V", "getContextReceivers", "()Ljava/util/List;", "getImplicitCompanionValues", "getImplicitReceiverValue", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ImplicitReceivers {
    private final List<ContextReceiverValue<?>> contextReceivers;
    private final List<ImplicitReceiverValue<?>> implicitCompanionValues;
    private final ImplicitReceiverValue<?> implicitReceiverValue;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ImplicitReceivers copy$default(ImplicitReceivers implicitReceivers, ImplicitReceiverValue implicitReceiverValue, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            implicitReceiverValue = implicitReceivers.implicitReceiverValue;
        }
        if ((i & 2) != 0) {
            list = implicitReceivers.implicitCompanionValues;
        }
        if ((i & 4) != 0) {
            list2 = implicitReceivers.contextReceivers;
        }
        return implicitReceivers.copy(implicitReceiverValue, list, list2);
    }

    public final ImplicitReceiverValue<?> component1() {
        return this.implicitReceiverValue;
    }

    public final List<ImplicitReceiverValue<?>> component2() {
        return this.implicitCompanionValues;
    }

    public final List<ContextReceiverValue<?>> component3() {
        return this.contextReceivers;
    }

    public final ImplicitReceivers copy(ImplicitReceiverValue<?> implicitReceiverValue, List<? extends ImplicitReceiverValue<?>> implicitCompanionValues, List<? extends ContextReceiverValue<?>> contextReceivers) {
        Intrinsics.checkNotNullParameter(implicitCompanionValues, "implicitCompanionValues");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        return new ImplicitReceivers(implicitReceiverValue, implicitCompanionValues, contextReceivers);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImplicitReceivers)) {
            return false;
        }
        ImplicitReceivers implicitReceivers = (ImplicitReceivers) other;
        return Intrinsics.areEqual(this.implicitReceiverValue, implicitReceivers.implicitReceiverValue) && Intrinsics.areEqual(this.implicitCompanionValues, implicitReceivers.implicitCompanionValues) && Intrinsics.areEqual(this.contextReceivers, implicitReceivers.contextReceivers);
    }

    public int hashCode() {
        ImplicitReceiverValue<?> implicitReceiverValue = this.implicitReceiverValue;
        return ((((implicitReceiverValue == null ? 0 : implicitReceiverValue.hashCode()) * 31) + this.implicitCompanionValues.hashCode()) * 31) + this.contextReceivers.hashCode();
    }

    public String toString() {
        return "ImplicitReceivers(implicitReceiverValue=" + this.implicitReceiverValue + ", implicitCompanionValues=" + this.implicitCompanionValues + ", contextReceivers=" + this.contextReceivers + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ImplicitReceivers(ImplicitReceiverValue<?> implicitReceiverValue, List<? extends ImplicitReceiverValue<?>> implicitCompanionValues, List<? extends ContextReceiverValue<?>> contextReceivers) {
        Intrinsics.checkNotNullParameter(implicitCompanionValues, "implicitCompanionValues");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        this.implicitReceiverValue = implicitReceiverValue;
        this.implicitCompanionValues = implicitCompanionValues;
        this.contextReceivers = contextReceivers;
    }

    public final ImplicitReceiverValue<?> getImplicitReceiverValue() {
        return this.implicitReceiverValue;
    }

    public final List<ImplicitReceiverValue<?>> getImplicitCompanionValues() {
        return this.implicitCompanionValues;
    }

    public final List<ContextReceiverValue<?>> getContextReceivers() {
        return this.contextReceivers;
    }
}
