package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntegerZeroComparison;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "a", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;)V", "getA", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntegerZeroComparison extends BooleanValue {
    private final MaterialValue a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntegerZeroComparison(MaterialValue a) {
        super(a.getCodegen());
        Intrinsics.checkNotNullParameter(a, "a");
        this.a = a;
    }

    public final MaterialValue getA() {
        return this.a;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfFalse(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        getMv().visitJumpInsn(154, target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
    public void jumpIfTrue(Label target) {
        Intrinsics.checkNotNullParameter(target, "target");
        getMv().visitJumpInsn(153, target);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
    public void discard() {
        this.a.discard();
    }
}
