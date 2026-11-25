package org.jetbrains.kotlin.constant;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J5\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/constant/NullValue;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "", "()V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullValue extends ConstantValue {
    public static final NullValue INSTANCE = new NullValue();

    private NullValue() {
        super(null, null);
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitNullValue(this, data);
    }
}
