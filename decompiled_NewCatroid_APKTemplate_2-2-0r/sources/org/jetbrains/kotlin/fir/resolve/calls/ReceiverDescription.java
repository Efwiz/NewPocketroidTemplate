package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverDescription;", "", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ReceiverDescription {
    private final FirExpression expression;
    private final ConeKotlinType type;

    public ReceiverDescription(FirExpression expression, ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        this.expression = expression;
        this.type = type;
    }

    public final FirExpression getExpression() {
        return this.expression;
    }

    public final ConeKotlinType getType() {
        return this.type;
    }
}
