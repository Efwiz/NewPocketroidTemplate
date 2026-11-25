package org.jetbrains.kotlin.codegen.range.comparison;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: FloatingPointComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/comparison/FloatingPointComparisonGenerator;", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "comparedType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "getComparedType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "jumpIfGreater", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfGreaterOrEqual", "jumpIfLess", "jumpIfLessOrEqual", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FloatingPointComparisonGenerator implements ComparisonGenerator {
    private final Type comparedType;

    public FloatingPointComparisonGenerator(Type comparedType) {
        Intrinsics.checkNotNullParameter(comparedType, "comparedType");
        this.comparedType = comparedType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public Type getComparedType() {
        return this.comparedType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfGreaterOrEqual(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        v.cmpg(getComparedType());
        v.ifge(label);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfLessOrEqual(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        v.cmpl(getComparedType());
        v.ifle(label);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfGreater(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        v.cmpg(getComparedType());
        v.ifgt(label);
    }

    @Override // org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator
    public void jumpIfLess(InstructionAdapter v, Label label) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(label, "label");
        v.cmpl(getComparedType());
        v.iflt(label);
    }
}
