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
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/Invert;", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "condition", "(Lorg/jetbrains/kotlin/codegen/BranchedValue;)V", "getCondition", "()Lorg/jetbrains/kotlin/codegen/BranchedValue;", "condJump", "", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfFalse", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Invert extends BranchedValue {
    private final BranchedValue condition;

    /* JADX WARN: Illegal instructions before constructor call */
    public Invert(BranchedValue condition) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
        super(condition, null, BOOLEAN_TYPE, 153);
        this.condition = condition;
    }

    public final BranchedValue getCondition() {
        return this.condition;
    }

    @Override // org.jetbrains.kotlin.codegen.BranchedValue
    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        this.condition.condJump(jumpLabel, v, !jumpIfFalse);
    }
}
