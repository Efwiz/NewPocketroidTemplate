package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESOperator;
import org.jetbrains.kotlin.contracts.model.functors.AndFunctor;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J!\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESAnd;", "Lorg/jetbrains/kotlin/contracts/model/ESOperator;", "left", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "right", "(Lorg/jetbrains/kotlin/contracts/model/ESExpression;Lorg/jetbrains/kotlin/contracts/model/ESExpression;)V", "functor", "Lorg/jetbrains/kotlin/contracts/model/functors/AndFunctor;", "getFunctor", "()Lorg/jetbrains/kotlin/contracts/model/functors/AndFunctor;", "getLeft", "()Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "getRight", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESAnd implements ESOperator {
    private final AndFunctor functor;
    private final ESExpression left;
    private final ESExpression right;

    public ESAnd(ESExpression left, ESExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.left = left;
        this.right = right;
        this.functor = new AndFunctor();
    }

    public final ESExpression getLeft() {
        return this.left;
    }

    public final ESExpression getRight() {
        return this.right;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESOperator
    public AndFunctor getFunctor() {
        return this.functor;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnd(this);
    }
}
