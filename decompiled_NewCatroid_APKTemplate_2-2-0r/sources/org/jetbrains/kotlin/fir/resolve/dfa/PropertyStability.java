package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.types.SmartcastStability;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0000R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", "", "impliedSmartcastStability", "Lorg/jetbrains/kotlin/types/SmartcastStability;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/types/SmartcastStability;)V", "getImpliedSmartcastStability", "()Lorg/jetbrains/kotlin/types/SmartcastStability;", "combineWithReceiverStability", "receiverStability", "STABLE_VALUE", "LOCAL_VAR", "PROPERTY_WITH_GETTER", "ALIEN_PUBLIC_PROPERTY", "MUTABLE_PROPERTY", "DELEGATED_PROPERTY", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum PropertyStability {
    STABLE_VALUE(SmartcastStability.STABLE_VALUE),
    LOCAL_VAR(null),
    PROPERTY_WITH_GETTER(SmartcastStability.PROPERTY_WITH_GETTER),
    ALIEN_PUBLIC_PROPERTY(SmartcastStability.ALIEN_PUBLIC_PROPERTY),
    MUTABLE_PROPERTY(SmartcastStability.MUTABLE_PROPERTY),
    DELEGATED_PROPERTY(SmartcastStability.DELEGATED_PROPERTY);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final SmartcastStability impliedSmartcastStability;

    public static EnumEntries<PropertyStability> getEntries() {
        return $ENTRIES;
    }

    PropertyStability(SmartcastStability smartcastStability) {
        this.impliedSmartcastStability = smartcastStability;
    }

    public final SmartcastStability getImpliedSmartcastStability() {
        return this.impliedSmartcastStability;
    }

    public final PropertyStability combineWithReceiverStability(PropertyStability receiverStability) {
        if (receiverStability == null) {
            return this;
        }
        PropertyStability propertyStability = LOCAL_VAR;
        if (this == propertyStability) {
            if (receiverStability == STABLE_VALUE || receiverStability == propertyStability) {
                return this;
            }
            throw new IllegalArgumentException(("LOCAL_VAR can have only stable or local receiver, but got " + receiverStability).toString());
        }
        return (PropertyStability) ComparisonsKt.maxOf(this, receiverStability);
    }
}
