package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ProvisionalFunctionExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ProvisionalFunctionExpressionLoweringContext;", "", "startOffset", "", "endOffset", "(Ljava/lang/Integer;Ljava/lang/Integer;)V", "getEndOffset", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getStartOffset", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProvisionalFunctionExpressionLoweringContext {
    private final Integer endOffset;
    private final Integer startOffset;

    /* JADX WARN: Multi-variable type inference failed */
    public ProvisionalFunctionExpressionLoweringContext() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public ProvisionalFunctionExpressionLoweringContext(Integer num, Integer num2) {
        this.startOffset = num;
        this.endOffset = num2;
    }

    public /* synthetic */ ProvisionalFunctionExpressionLoweringContext(Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num, (i & 2) != 0 ? null : num2);
    }

    public final Integer getStartOffset() {
        return this.startOffset;
    }

    public final Integer getEndOffset() {
        return this.endOffset;
    }
}
