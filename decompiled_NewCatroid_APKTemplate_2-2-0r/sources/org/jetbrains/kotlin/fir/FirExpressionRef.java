package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: FirExpressionRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0007\b\u0007¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\tR\u001c\u0010\u0005\u001a\u00028\u0000X\u0086.¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirExpressionRef;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "", "()V", "value", "getValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "bind", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpressionRef<T extends FirExpression> {
    public T value;

    @FirContractViolation
    public FirExpressionRef() {
    }

    public final T getValue() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        Intrinsics.throwUninitializedPropertyAccessException("value");
        return null;
    }

    public final void setValue(T t) {
        Intrinsics.checkNotNullParameter(t, "<set-?>");
        this.value = t;
    }

    public final void bind(T value) {
        Intrinsics.checkNotNullParameter(value, "value");
        setValue(value);
    }
}
