package org.jetbrains.kotlin.fir.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValueForClass;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: ImplicitReceiverUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001Ba\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\b\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\u0010\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0005¢\u0006\u0002\u0010\u000eR\u0017\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/TowerElementsForClass;", "", "thisReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "contextReceivers", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValueForClass;", "staticScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "companionReceiver", "companionStaticScope", "superClassesStaticsAndCompanionReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "implicitCompanionValues", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Ljava/util/List;Ljava/util/List;)V", "getCompanionReceiver", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "getCompanionStaticScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getContextReceivers", "()Ljava/util/List;", "getImplicitCompanionValues", "getStaticScope", "getSuperClassesStaticsAndCompanionReceivers", "getThisReceiver", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerElementsForClass {
    private final ImplicitReceiverValue<?> companionReceiver;
    private final FirScope companionStaticScope;
    private final List<ContextReceiverValueForClass> contextReceivers;
    private final List<ImplicitReceiverValue<?>> implicitCompanionValues;
    private final FirScope staticScope;
    private final List<FirTowerDataElement> superClassesStaticsAndCompanionReceivers;
    private final ImplicitReceiverValue<?> thisReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    public TowerElementsForClass(ImplicitReceiverValue<?> thisReceiver, List<ContextReceiverValueForClass> contextReceivers, FirScope firScope, ImplicitReceiverValue<?> implicitReceiverValue, FirScope firScope2, List<FirTowerDataElement> superClassesStaticsAndCompanionReceivers, List<? extends ImplicitReceiverValue<?>> implicitCompanionValues) {
        Intrinsics.checkNotNullParameter(thisReceiver, "thisReceiver");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        Intrinsics.checkNotNullParameter(superClassesStaticsAndCompanionReceivers, "superClassesStaticsAndCompanionReceivers");
        Intrinsics.checkNotNullParameter(implicitCompanionValues, "implicitCompanionValues");
        this.thisReceiver = thisReceiver;
        this.contextReceivers = contextReceivers;
        this.staticScope = firScope;
        this.companionReceiver = implicitReceiverValue;
        this.companionStaticScope = firScope2;
        this.superClassesStaticsAndCompanionReceivers = superClassesStaticsAndCompanionReceivers;
        this.implicitCompanionValues = implicitCompanionValues;
    }

    public final ImplicitReceiverValue<?> getThisReceiver() {
        return this.thisReceiver;
    }

    public final List<ContextReceiverValueForClass> getContextReceivers() {
        return this.contextReceivers;
    }

    public final FirScope getStaticScope() {
        return this.staticScope;
    }

    public final ImplicitReceiverValue<?> getCompanionReceiver() {
        return this.companionReceiver;
    }

    public final FirScope getCompanionStaticScope() {
        return this.companionStaticScope;
    }

    public final List<FirTowerDataElement> getSuperClassesStaticsAndCompanionReceivers() {
        return this.superClassesStaticsAndCompanionReceivers;
    }

    public final List<ImplicitReceiverValue<?>> getImplicitCompanionValues() {
        return this.implicitCompanionValues;
    }
}
