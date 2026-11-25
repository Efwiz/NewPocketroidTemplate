package org.jetbrains.kotlin.constant;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J5\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0016¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0002H\u0002J\b\u0010\u0011\u001a\u00020\rH\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/constant/CharValue;", "Lorg/jetbrains/kotlin/constant/IntegerValueConstant;", "", "value", "(C)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getPrintablePart", "", "c", "isPrintableUnicode", "", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CharValue extends IntegerValueConstant<Character> {
    public CharValue(char c) {
        super(Character.valueOf(c));
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitCharValue(this, data);
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public String toString() {
        String str = String.format("\\u%04X ('%s')", Arrays.copyOf(new Object[]{Integer.valueOf(getValue().charValue()), getPrintablePart(getValue().charValue())}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    private final String getPrintablePart(char c) {
        if (c == '\b') {
            return "\\b";
        }
        if (c == '\t') {
            return "\\t";
        }
        if (c == '\n') {
            return "\\n";
        }
        if (c == '\f') {
            return "\\f";
        }
        if (c == '\r') {
            return "\\r";
        }
        return isPrintableUnicode(c) ? String.valueOf(c) : TypeDescription.Generic.OfWildcardType.SYMBOL;
    }

    private final boolean isPrintableUnicode(char c) {
        byte type = (byte) Character.getType(c);
        return (type == 0 || type == 13 || type == 14 || type == 15 || type == 16 || type == 18 || type == 19) ? false : true;
    }
}
