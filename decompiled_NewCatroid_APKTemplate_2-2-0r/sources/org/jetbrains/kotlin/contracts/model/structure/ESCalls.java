package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0016¢\u0006\u0002\u0010\u0016J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESCalls;", "Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;", "callable", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lorg/jetbrains/kotlin/contracts/model/ESValue;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "getCallable", "()Lorg/jetbrains/kotlin/contracts/model/ESValue;", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isImplies", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "(Lorg/jetbrains/kotlin/contracts/model/ESEffect;)Ljava/lang/Boolean;", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ESCalls extends SimpleEffect {
    private final ESValue callable;
    private final EventOccurrencesRange kind;

    public static /* synthetic */ ESCalls copy$default(ESCalls eSCalls, ESValue eSValue, EventOccurrencesRange eventOccurrencesRange, int i, Object obj) {
        if ((i & 1) != 0) {
            eSValue = eSCalls.callable;
        }
        if ((i & 2) != 0) {
            eventOccurrencesRange = eSCalls.kind;
        }
        return eSCalls.copy(eSValue, eventOccurrencesRange);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ESValue getCallable() {
        return this.callable;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final EventOccurrencesRange getKind() {
        return this.kind;
    }

    public final ESCalls copy(ESValue callable, EventOccurrencesRange kind) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new ESCalls(callable, kind);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ESCalls)) {
            return false;
        }
        ESCalls eSCalls = (ESCalls) other;
        return Intrinsics.areEqual(this.callable, eSCalls.callable) && this.kind == eSCalls.kind;
    }

    public int hashCode() {
        return (this.callable.hashCode() * 31) + this.kind.hashCode();
    }

    public String toString() {
        return "ESCalls(callable=" + this.callable + ", kind=" + this.kind + ')';
    }

    public ESCalls(ESValue callable, EventOccurrencesRange kind) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.callable = callable;
        this.kind = kind;
    }

    public final ESValue getCallable() {
        return this.callable;
    }

    public final EventOccurrencesRange getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESEffect
    public Boolean isImplies(ESEffect other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (!(other instanceof ESCalls)) {
            return null;
        }
        ESCalls eSCalls = (ESCalls) other;
        if (Intrinsics.areEqual(this.callable, eSCalls.callable)) {
            return Boolean.valueOf(this.kind == eSCalls.kind);
        }
        return null;
    }
}
