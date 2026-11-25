package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: BoxedBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\u0001H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/TaintedBoxedValue;", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "boxedBasicValue", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/CleanBoxedValue;", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/CleanBoxedValue;)V", "descriptor", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "taint", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TaintedBoxedValue extends BoxedBasicValue {
    private final CleanBoxedValue boxedBasicValue;

    /* JADX WARN: Illegal instructions before constructor call */
    public TaintedBoxedValue(CleanBoxedValue boxedBasicValue) {
        Intrinsics.checkNotNullParameter(boxedBasicValue, "boxedBasicValue");
        Type type = boxedBasicValue.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        super(type);
        this.boxedBasicValue = boxedBasicValue;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue
    public BoxedValueDescriptor getDescriptor() {
        return this.boxedBasicValue.getDescriptor();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue
    public BoxedBasicValue taint() {
        return this;
    }
}
