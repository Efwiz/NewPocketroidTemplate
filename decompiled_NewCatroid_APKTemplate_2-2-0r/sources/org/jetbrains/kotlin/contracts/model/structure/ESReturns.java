package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0017\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u0011J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESReturns;", "Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;", "value", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "(Lorg/jetbrains/kotlin/contracts/model/ESValue;)V", "getValue", "()Lorg/jetbrains/kotlin/contracts/model/ESValue;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isImplies", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "(Lorg/jetbrains/kotlin/contracts/model/ESEffect;)Ljava/lang/Boolean;", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ESReturns extends SimpleEffect {
    private final ESValue value;

    public static /* synthetic */ ESReturns copy$default(ESReturns eSReturns, ESValue eSValue, int i, Object obj) {
        if ((i & 1) != 0) {
            eSValue = eSReturns.value;
        }
        return eSReturns.copy(eSValue);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ESValue getValue() {
        return this.value;
    }

    public final ESReturns copy(ESValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return new ESReturns(value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ESReturns) && Intrinsics.areEqual(this.value, ((ESReturns) other).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "ESReturns(value=" + this.value + ')';
    }

    public ESReturns(ESValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final ESValue getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESEffect
    public Boolean isImplies(ESEffect other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (!(other instanceof ESReturns)) {
            return null;
        }
        ESValue eSValue = this.value;
        if (eSValue instanceof ESConstant) {
            ESReturns eSReturns = (ESReturns) other;
            ESValue eSValue2 = eSReturns.value;
            if (eSValue2 instanceof ESConstant) {
                if (ValuesKt.isWildcard(eSValue2)) {
                    return true;
                }
                return Boolean.valueOf(Intrinsics.areEqual(this.value, eSReturns.value));
            }
        }
        return Boolean.valueOf(Intrinsics.areEqual(eSValue, ((ESReturns) other).value));
    }
}
