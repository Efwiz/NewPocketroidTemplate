package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SmartCastResult.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastResult;", "", "resultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isCorrect", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Z)V", "()Z", "getResultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartCastResult {
    private final boolean isCorrect;
    private final KotlinType resultType;

    public SmartCastResult(KotlinType resultType, boolean z) {
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        this.resultType = resultType;
        this.isCorrect = z;
    }

    public final KotlinType getResultType() {
        return this.resultType;
    }

    /* JADX INFO: renamed from: isCorrect, reason: from getter */
    public final boolean getIsCorrect() {
        return this.isCorrect;
    }
}
