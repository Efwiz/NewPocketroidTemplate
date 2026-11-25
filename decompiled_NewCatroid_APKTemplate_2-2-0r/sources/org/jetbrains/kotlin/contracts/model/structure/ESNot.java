package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESOperator;
import org.jetbrains.kotlin.contracts.model.functors.NotFunctor;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\u0016¢\u0006\u0002\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESNot;", "Lorg/jetbrains/kotlin/contracts/model/ESOperator;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpression;)V", "getArg", "()Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "functor", "Lorg/jetbrains/kotlin/contracts/model/functors/NotFunctor;", "getFunctor", "()Lorg/jetbrains/kotlin/contracts/model/functors/NotFunctor;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESNot implements ESOperator {
    private final ESExpression arg;
    private final NotFunctor functor;

    public ESNot(ESExpression arg) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        this.arg = arg;
        this.functor = new NotFunctor();
    }

    public final ESExpression getArg() {
        return this.arg;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESOperator
    public NotFunctor getFunctor() {
        return this.functor;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitNot(this);
    }
}
