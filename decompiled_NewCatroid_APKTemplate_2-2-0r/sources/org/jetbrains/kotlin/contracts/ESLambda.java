package org.jetbrains.kotlin.contracts;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.structure.AbstractESValue;
import org.jetbrains.kotlin.psi.KtLambdaExpression;

/* JADX INFO: compiled from: ESDataFlowValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0016¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ESLambda;", "Lorg/jetbrains/kotlin/contracts/model/structure/AbstractESValue;", "lambda", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "(Lorg/jetbrains/kotlin/psi/KtLambdaExpression;)V", "getLambda", "()Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESLambda extends AbstractESValue {
    private final KtLambdaExpression lambda;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ESLambda(KtLambdaExpression lambda) {
        super(null);
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        this.lambda = lambda;
    }

    public final KtLambdaExpression getLambda() {
        return this.lambda;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLambda(this);
    }
}
