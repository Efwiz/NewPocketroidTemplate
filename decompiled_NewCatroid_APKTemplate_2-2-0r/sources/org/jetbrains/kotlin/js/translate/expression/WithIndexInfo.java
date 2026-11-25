package org.jetbrains.kotlin.js.translate.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: compiled from: LoopTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/WithIndexInfo;", "", "receiverType", "Lorg/jetbrains/kotlin/js/translate/expression/WithIndexReceiverType;", "index", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "value", "range", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/translate/expression/WithIndexReceiverType;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "getIndex", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getRange", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getReceiverType", "()Lorg/jetbrains/kotlin/js/translate/expression/WithIndexReceiverType;", "getValue", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WithIndexInfo {
    private final VariableDescriptor index;
    private final JsExpression range;
    private final WithIndexReceiverType receiverType;
    private final VariableDescriptor value;

    public WithIndexInfo(WithIndexReceiverType receiverType, VariableDescriptor variableDescriptor, VariableDescriptor variableDescriptor2, JsExpression range) {
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        Intrinsics.checkNotNullParameter(range, "range");
        this.receiverType = receiverType;
        this.index = variableDescriptor;
        this.value = variableDescriptor2;
        this.range = range;
    }

    public final WithIndexReceiverType getReceiverType() {
        return this.receiverType;
    }

    public final VariableDescriptor getIndex() {
        return this.index;
    }

    public final VariableDescriptor getValue() {
        return this.value;
    }

    public final JsExpression getRange() {
        return this.range;
    }
}
