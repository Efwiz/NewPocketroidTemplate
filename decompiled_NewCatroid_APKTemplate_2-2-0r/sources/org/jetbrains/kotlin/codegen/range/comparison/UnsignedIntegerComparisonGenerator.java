package org.jetbrains.kotlin.codegen.range.comparison;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: UnsignedIntegerComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0016\u0010\u0007\u001a\n \b*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/comparison/UnsignedIntegerComparisonGenerator;", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "comparedType", "Lorg/jetbrains/org/objectweb/asm/Type;", "compareMethodName", "", "(Lorg/jetbrains/org/objectweb/asm/Type;Ljava/lang/String;)V", "compareMethodDescriptor", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getComparedType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "compareAndJump", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "opcode", "", "jumpIfGreater", "jumpIfGreaterOrEqual", "jumpIfLess", "jumpIfLessOrEqual", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsignedIntegerComparisonGenerator implements ComparisonGenerator {
    private final String compareMethodDescriptor;
    private final String compareMethodName;
    private final Type comparedType;

    public UnsignedIntegerComparisonGenerator(Type comparedType, String compareMethodName) {
        Intrinsics.checkNotNullParameter(comparedType, "comparedType");
        Intrinsics.checkNotNullParameter(compareMethodName, "compareMethodName");
        this.comparedType = comparedType;
        this.compareMethodName = compareMethodName;
        this.compareMethodDescriptor = Type.getMethodDescriptor(Type.INT_TYPE, getComparedType(), getComparedType());
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public Type getComparedType() {
        return this.comparedType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfGreaterOrEqual(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        compareAndJump(v, label, 156);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfLessOrEqual(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        compareAndJump(v, label, 158);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfGreater(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        compareAndJump(v, label, 157);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfLess(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        compareAndJump(v, label, 155);
    }

    private final void compareAndJump(InstructionAdapter v, Label label, int opcode) {
        v.invokestatic("kotlin/UnsignedKt", this.compareMethodName, this.compareMethodDescriptor, false);
        v.visitJumpInsn(opcode, label);
    }
}
