package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FixStackValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toFixStackValue", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FixStackValueKt {
    public static final FixStackValue toFixStackValue(Type type) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        switch (type.getSort()) {
            case 0:
                return null;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return FixStackValue.INT;
            case 6:
                return FixStackValue.FLOAT;
            case 7:
                return FixStackValue.LONG;
            case 8:
                return FixStackValue.DOUBLE;
            case 9:
            case 10:
            case 11:
                return FixStackValue.OBJECT;
            default:
                throw new AssertionError("Unexpected type: " + type);
        }
    }
}
