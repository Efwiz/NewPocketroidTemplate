package org.jetbrains.kotlin.backend.common.lower.loops;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0000H&j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;", "", "(Ljava/lang/String;I)V", "asReversed", "DECREASING", "INCREASING", GrsBaseInfo.CountryCodeSource.UNKNOWN, "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ProgressionDirection {
    DECREASING { // from class: org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection.DECREASING
        @Override // org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection
        public ProgressionDirection asReversed() {
            return ProgressionDirection.INCREASING;
        }
    },
    INCREASING { // from class: org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection.INCREASING
        @Override // org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection
        public ProgressionDirection asReversed() {
            return ProgressionDirection.DECREASING;
        }
    },
    UNKNOWN { // from class: org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection.UNKNOWN
        @Override // org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection
        public ProgressionDirection asReversed() {
            return this;
        }
    };

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* synthetic */ ProgressionDirection(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static EnumEntries<ProgressionDirection> getEntries() {
        return $ENTRIES;
    }

    public abstract ProgressionDirection asReversed();
}
