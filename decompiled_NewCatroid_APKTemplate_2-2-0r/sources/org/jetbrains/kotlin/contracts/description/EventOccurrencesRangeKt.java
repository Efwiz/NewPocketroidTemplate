package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EventOccurrencesRange.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\"\u0017\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0007"}, d2 = {"isInPlace", "", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)Z", "canBeRevisited", "canBeVisited", "isDefinitelyVisited", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EventOccurrencesRangeKt {
    public static final boolean isInPlace(EventOccurrencesRange eventOccurrencesRange) {
        return eventOccurrencesRange != null;
    }

    public static final boolean isDefinitelyVisited(EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(eventOccurrencesRange, "<this>");
        return eventOccurrencesRange == EventOccurrencesRange.EXACTLY_ONCE || eventOccurrencesRange == EventOccurrencesRange.AT_LEAST_ONCE || eventOccurrencesRange == EventOccurrencesRange.MORE_THAN_ONCE;
    }

    public static final boolean canBeVisited(EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(eventOccurrencesRange, "<this>");
        return eventOccurrencesRange != EventOccurrencesRange.ZERO;
    }

    public static final boolean canBeRevisited(EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(eventOccurrencesRange, "<this>");
        return eventOccurrencesRange == EventOccurrencesRange.UNKNOWN || eventOccurrencesRange == EventOccurrencesRange.AT_LEAST_ONCE || eventOccurrencesRange == EventOccurrencesRange.MORE_THAN_ONCE;
    }
}
