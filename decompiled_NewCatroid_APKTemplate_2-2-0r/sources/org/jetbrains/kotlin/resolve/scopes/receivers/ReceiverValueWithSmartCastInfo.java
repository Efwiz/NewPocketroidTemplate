package org.jetbrains.kotlin.resolve.scopes.receivers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "typesFromSmartCasts", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "isStable", "", "originalBaseType", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;Ljava/util/Set;ZLorg/jetbrains/kotlin/types/KotlinType;)V", "allOriginalTypes", "getAllOriginalTypes", "()Ljava/util/Set;", "()Z", "getReceiverValue", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getTypesFromSmartCasts", "hasTypesFromSmartCasts", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverValueWithSmartCastInfo implements DetailedReceiver {
    private final Set<KotlinType> allOriginalTypes;
    private final boolean isStable;
    private final ReceiverValue receiverValue;
    private final Set<KotlinType> typesFromSmartCasts;

    /* JADX WARN: Multi-variable type inference failed */
    public ReceiverValueWithSmartCastInfo(ReceiverValue receiverValue, Set<? extends KotlinType> typesFromSmartCasts, boolean z, KotlinType originalBaseType) {
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        Intrinsics.checkNotNullParameter(typesFromSmartCasts, "typesFromSmartCasts");
        Intrinsics.checkNotNullParameter(originalBaseType, "originalBaseType");
        this.receiverValue = receiverValue;
        this.typesFromSmartCasts = typesFromSmartCasts;
        this.isStable = z;
        this.allOriginalTypes = SetsKt.plus(typesFromSmartCasts, originalBaseType);
    }

    public final ReceiverValue getReceiverValue() {
        return this.receiverValue;
    }

    public final Set<KotlinType> getTypesFromSmartCasts() {
        return this.typesFromSmartCasts;
    }

    /* JADX INFO: renamed from: isStable, reason: from getter */
    public final boolean getIsStable() {
        return this.isStable;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ReceiverValueWithSmartCastInfo(ReceiverValue receiverValue, Set set, boolean z, KotlinType kotlinType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 8) != 0) {
            kotlinType = receiverValue.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "getType(...)");
        }
        this(receiverValue, set, z, kotlinType);
    }

    public final Set<KotlinType> getAllOriginalTypes() {
        return this.allOriginalTypes;
    }

    public final boolean hasTypesFromSmartCasts() {
        return !this.typesFromSmartCasts.isEmpty();
    }

    public String toString() {
        return this.receiverValue.toString();
    }
}
