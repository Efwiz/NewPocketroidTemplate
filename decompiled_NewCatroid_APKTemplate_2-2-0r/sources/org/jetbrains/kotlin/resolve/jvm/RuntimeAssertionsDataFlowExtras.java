package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionsDataFlowExtras;", "Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$DataFlowExtras;", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "canBeNull", "", "getCanBeNull", "()Z", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "getDataFlowValue", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "dataFlowValue$delegate", "Lkotlin/Lazy;", "presentableText", "", "getPresentableText", "()Ljava/lang/String;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionsDataFlowExtras implements RuntimeAssertionInfo.DataFlowExtras {
    private final ResolutionContext<?> c;

    /* JADX INFO: renamed from: dataFlowValue$delegate, reason: from kotlin metadata */
    private final Lazy dataFlowValue;
    private final KtExpression expression;
    private final KotlinType expressionType;

    public RuntimeAssertionsDataFlowExtras(ResolutionContext<?> c, KotlinType expressionType, KtExpression expression) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.c = c;
        this.expressionType = expressionType;
        this.expression = expression;
        this.dataFlowValue = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<DataFlowValue>() { // from class: org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionsDataFlowExtras$dataFlowValue$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DataFlowValue invoke() {
                return this.this$0.c.dataFlowValueFactory.createDataFlowValue(this.this$0.expression, this.this$0.expressionType, this.this$0.c);
            }
        });
    }

    private final DataFlowValue getDataFlowValue() {
        return (DataFlowValue) this.dataFlowValue.getValue();
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo.DataFlowExtras
    public boolean getCanBeNull() {
        return this.c.dataFlowInfo.getStableNullability(getDataFlowValue()).canBeNull();
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo.DataFlowExtras
    public String getPresentableText() {
        return RuntimeAssertionsKt.getTextForRuntimeAssertionInfo(this.expression);
    }
}
