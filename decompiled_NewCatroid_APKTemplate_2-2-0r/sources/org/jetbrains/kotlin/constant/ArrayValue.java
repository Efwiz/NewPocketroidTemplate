package org.jetbrains.kotlin.constant;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\u00020\u0001B\u0017\u0012\u0010\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\u0002¢\u0006\u0002\u0010\u0004J5\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/constant/ArrayValue;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "", "value", "(Ljava/util/List;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayValue extends ConstantValue<List<? extends ConstantValue<?>>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArrayValue(List<? extends ConstantValue<?>> value) {
        super(value, null);
        Intrinsics.checkNotNullParameter(value, "value");
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitArrayValue(this, data);
    }
}
