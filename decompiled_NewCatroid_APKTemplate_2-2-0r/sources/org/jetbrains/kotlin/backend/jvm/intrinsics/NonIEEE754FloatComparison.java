package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.NumberCompare;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/NonIEEE754FloatComparison;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "op", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "a", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;)V", "numberCompareOpcode", "", "discard", "", "invokeStaticComparison", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NonIEEE754FloatComparison extends BooleanValue {
    private final MaterialValue a;
    private final MaterialValue b;
    private final int numberCompareOpcode;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NonIEEE754FloatComparison(IElementType op, MaterialValue a, MaterialValue b) {
        super(a.getCodegen());
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        this.a = a;
        this.b = b;
        this.numberCompareOpcode = NumberCompare.INSTANCE.getNumberCompareOpcode(op);
    }

    private final void invokeStaticComparison(Type type) {
        if (Intrinsics.areEqual(type, Type.FLOAT_TYPE)) {
            getMv().invokestatic("java/lang/Float", "compare", "(FF)I", false);
        } else {
            if (!Intrinsics.areEqual(type, Type.DOUBLE_TYPE)) {
                throw new UnsupportedOperationException();
            }
            getMv().invokestatic("java/lang/Double", "compare", "(DD)I", false);
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfFalse(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        invokeStaticComparison(this.a.getType());
        getMv().visitJumpInsn(this.numberCompareOpcode, target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfTrue(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        invokeStaticComparison(this.a.getType());
        InstructionAdapter mv = getMv();
        Integer num = BranchedValue.INSTANCE.getNegatedOperations().get(Integer.valueOf(this.numberCompareOpcode));
        Intrinsics.checkNotNull(num);
        mv.visitJumpInsn(num.intValue(), target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
        this.b.discard();
        this.a.discard();
    }
}
