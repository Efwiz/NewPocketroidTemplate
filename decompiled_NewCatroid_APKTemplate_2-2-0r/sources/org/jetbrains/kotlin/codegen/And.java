package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BranchedValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/And;", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "arg1", "Lorg/jetbrains/kotlin/codegen/StackValue;", "arg2", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;)V", "condJump", "", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfFalse", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class And extends BranchedValue {
    /* JADX WARN: Illegal instructions before constructor call */
    public And(StackValue arg1, StackValue arg2) {
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        CondJump condJump = BranchedValue.INSTANCE.condJump(arg1);
        CondJump condJump2 = BranchedValue.INSTANCE.condJump(arg2);
        Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
        super(condJump, condJump2, BOOLEAN_TYPE, 153);
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        Label label = new Label();
        StackValue arg1 = getArg1();
        Intrinsics.checkNotNull(arg1, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.CondJump");
        ((CondJump) arg1).condJump(jumpIfFalse ? jumpLabel : label, v, true);
        StackValue arg2 = getArg2();
        Intrinsics.checkNotNull(arg2, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.CondJump");
        ((CondJump) arg2).condJump(jumpLabel, v, jumpIfFalse);
        v.visitLabel(label);
    }
}
