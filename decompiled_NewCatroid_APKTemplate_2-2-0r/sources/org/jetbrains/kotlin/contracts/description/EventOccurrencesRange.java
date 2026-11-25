package org.jetbrains.kotlin.contracts.description;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: EventOccurrencesRange.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0011B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0000H\u0086\u0002J\u0011\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000H\u0086\u0004J\u0011\u0010\n\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "", "left", "", "right", "(Ljava/lang/String;III)V", "contains", "", "other", "or", "plus", Namer.LONG_ZERO, "AT_MOST_ONCE", "EXACTLY_ONCE", "AT_LEAST_ONCE", "MORE_THAN_ONCE", GrsBaseInfo.CountryCodeSource.UNKNOWN, "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum EventOccurrencesRange {
    ZERO(0, 0),
    AT_MOST_ONCE(0, 1),
    EXACTLY_ONCE(1, 1),
    AT_LEAST_ONCE(1, 3),
    MORE_THAN_ONCE(2, 3),
    UNKNOWN(0, 3);

    private final int left;
    private final int right;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<EventOccurrencesRange> getEntries() {
        return $ENTRIES;
    }

    EventOccurrencesRange(int i, int i2) {
        this.left = i;
        this.right = i2;
    }

    /* JADX INFO: compiled from: EventOccurrencesRange.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange$Companion;", "", "()V", "fromRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "left", "", "right", "or", "x", "y", "plus", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final EventOccurrencesRange fromRange(int left, int right) {
            Pair pair = TuplesKt.to(Integer.valueOf(Math.min(left, 2)), Integer.valueOf(Math.min(right, 3)));
            if (Intrinsics.areEqual(pair, TuplesKt.to(0, 0))) {
                return EventOccurrencesRange.ZERO;
            }
            if (Intrinsics.areEqual(pair, TuplesKt.to(0, 1))) {
                return EventOccurrencesRange.AT_MOST_ONCE;
            }
            if (!Intrinsics.areEqual(pair, TuplesKt.to(0, 2)) && !Intrinsics.areEqual(pair, TuplesKt.to(0, 3))) {
                if (Intrinsics.areEqual(pair, TuplesKt.to(1, 1))) {
                    return EventOccurrencesRange.EXACTLY_ONCE;
                }
                if (!Intrinsics.areEqual(pair, TuplesKt.to(1, 2)) && !Intrinsics.areEqual(pair, TuplesKt.to(1, 3))) {
                    if (!Intrinsics.areEqual(pair, TuplesKt.to(2, 2)) && !Intrinsics.areEqual(pair, TuplesKt.to(2, 3)) && !Intrinsics.areEqual(pair, TuplesKt.to(3, 3))) {
                        throw new IllegalArgumentException();
                    }
                    return EventOccurrencesRange.MORE_THAN_ONCE;
                }
                return EventOccurrencesRange.AT_LEAST_ONCE;
            }
            return EventOccurrencesRange.UNKNOWN;
        }

        public final EventOccurrencesRange or(EventOccurrencesRange x, EventOccurrencesRange y) {
            Intrinsics.checkNotNullParameter(x, "x");
            Intrinsics.checkNotNullParameter(y, "y");
            return fromRange(Math.min(x.left, y.left), Math.max(x.right, y.right));
        }

        public final EventOccurrencesRange plus(EventOccurrencesRange x, EventOccurrencesRange y) {
            Intrinsics.checkNotNullParameter(x, "x");
            Intrinsics.checkNotNullParameter(y, "y");
            return fromRange(x.left + y.left, x.right + y.right);
        }
    }

    public final EventOccurrencesRange or(EventOccurrencesRange other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return INSTANCE.or(this, other);
    }

    public final EventOccurrencesRange plus(EventOccurrencesRange other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return INSTANCE.plus(this, other);
    }

    public final boolean contains(EventOccurrencesRange other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.left <= other.left && other.right <= this.right;
    }
}
