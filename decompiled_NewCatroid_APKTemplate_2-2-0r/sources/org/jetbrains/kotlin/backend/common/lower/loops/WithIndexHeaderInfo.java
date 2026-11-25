package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0001H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/WithIndexHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "nestedInfo", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;)V", "getNestedInfo", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "asReversed", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WithIndexHeaderInfo extends HeaderInfo {
    private final HeaderInfo nestedInfo;

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo
    public HeaderInfo asReversed() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WithIndexHeaderInfo(HeaderInfo nestedInfo) {
        super(null);
        Intrinsics.checkNotNullParameter(nestedInfo, "nestedInfo");
        this.nestedInfo = nestedInfo;
    }

    public final HeaderInfo getNestedInfo() {
        return this.nestedInfo;
    }
}
