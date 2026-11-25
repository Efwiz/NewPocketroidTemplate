package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0000H&\u0082\u0001\u0005\u0004\u0005\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "", "()V", "asReversed", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ComparableRangeInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/FloatingPointRangeHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/IterableHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/WithIndexHeaderInfo;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class HeaderInfo {
    public /* synthetic */ HeaderInfo(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract HeaderInfo asReversed();

    private HeaderInfo() {
    }
}
