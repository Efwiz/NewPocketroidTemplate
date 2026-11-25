package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESOperator;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.functors.EqualsFunctor;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J!\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0012H\u0016¢\u0006\u0002\u0010\u0013R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESEqual;", "Lorg/jetbrains/kotlin/contracts/model/ESOperator;", "left", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "right", "isNegated", "", "(Lorg/jetbrains/kotlin/contracts/model/ESValue;Lorg/jetbrains/kotlin/contracts/model/ESValue;Z)V", "functor", "Lorg/jetbrains/kotlin/contracts/model/functors/EqualsFunctor;", "getFunctor", "()Lorg/jetbrains/kotlin/contracts/model/functors/EqualsFunctor;", "getLeft", "()Lorg/jetbrains/kotlin/contracts/model/ESValue;", "getRight", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESEqual implements ESOperator {
    private final EqualsFunctor functor;
    private final ESValue left;
    private final ESValue right;

    public ESEqual(ESValue left, ESValue right, boolean z) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.left = left;
        this.right = right;
        this.functor = new EqualsFunctor(z);
    }

    public final ESValue getLeft() {
        return this.left;
    }

    public final ESValue getRight() {
        return this.right;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESOperator
    public EqualsFunctor getFunctor() {
        return this.functor;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitEqual(this);
    }
}
