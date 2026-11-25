package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: StrictBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StrictBasicValue extends BasicValue {
    public static final StrictBasicValue UNINITIALIZED_VALUE = new StrictBasicValue(null);
    public static final StrictBasicValue INT_VALUE = new StrictBasicValue(Type.INT_TYPE);
    public static final StrictBasicValue FLOAT_VALUE = new StrictBasicValue(Type.FLOAT_TYPE);
    public static final StrictBasicValue LONG_VALUE = new StrictBasicValue(Type.LONG_TYPE);
    public static final StrictBasicValue DOUBLE_VALUE = new StrictBasicValue(Type.DOUBLE_TYPE);
    public static final StrictBasicValue BOOLEAN_VALUE = new StrictBasicValue(Type.BOOLEAN_TYPE);
    public static final StrictBasicValue CHAR_VALUE = new StrictBasicValue(Type.CHAR_TYPE);
    public static final StrictBasicValue BYTE_VALUE = new StrictBasicValue(Type.BYTE_TYPE);
    public static final StrictBasicValue SHORT_VALUE = new StrictBasicValue(Type.SHORT_TYPE);
    public static final StrictBasicValue REFERENCE_VALUE = new StrictBasicValue(Type.getObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME));
    public static final StrictBasicValue NULL_VALUE = new StrictBasicValue(Type.getObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME));

    public StrictBasicValue(Type type) {
        super(type);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(other.getClass(), getClass()) || !super.equals(other)) {
            return false;
        }
        StrictBasicValue strictBasicValue = (StrictBasicValue) other;
        StrictBasicValue strictBasicValue2 = NULL_VALUE;
        if (this == strictBasicValue2) {
            return other == strictBasicValue2;
        }
        if (other == strictBasicValue2) {
            return this == strictBasicValue2;
        }
        return Intrinsics.areEqual(getType(), strictBasicValue.getType());
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        Type type = getType();
        if (type != null) {
            return type.hashCode();
        }
        return 0;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public String toString() {
        String string;
        if (this == REFERENCE_VALUE) {
            string = "R";
        } else if (this == NULL_VALUE) {
            string = "null";
        } else if (this == UNINITIALIZED_VALUE) {
            string = ".";
        } else {
            string = super.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        }
        return getClass().getSimpleName() + '(' + string + ')';
    }
}
