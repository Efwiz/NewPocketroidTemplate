package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadValue;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: FastStoreLoadAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011J\u0016\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\u0005H\u0086\u0002¢\u0006\u0002\u0010\u0014J\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\"\u0010\u0017\u001a\u00020\u00182\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011J\u001e\u0010\u0019\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u001bR\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadFrame;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "", "maxLocals", "", "(I)V", "locals", "", "[Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "getMaxLocals", "()I", "execute", "", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "interpreter", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadInterpreter;", "get", "index", "(I)Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", ConfigConstants.CONFIG_INIT_SECTION, "other", "merge", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "newValue", "(ILorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StoreLoadFrame<V extends StoreLoadValue> {
    private final StoreLoadValue[] locals;
    private final int maxLocals;

    public StoreLoadFrame(int i) {
        this.maxLocals = i;
        this.locals = new StoreLoadValue[i];
    }

    public final int getMaxLocals() {
        return this.maxLocals;
    }

    public final V get(int index) {
        V v = (V) this.locals[index];
        Intrinsics.checkNotNull(v, "null cannot be cast to non-null type V of org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadFrame");
        return v;
    }

    public final void set(int index, V newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        this.locals[index] = newValue;
    }

    public final StoreLoadFrame<V> init(StoreLoadFrame<V> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        StoreLoadValue[] storeLoadValueArr = other.locals;
        StoreLoadValue[] storeLoadValueArr2 = this.locals;
        System.arraycopy(storeLoadValueArr, 0, storeLoadValueArr2, 0, storeLoadValueArr2.length);
        return this;
    }

    public final void execute(AbstractInsnNode insn, StoreLoadInterpreter<V> interpreter) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        int opcode = insn.getOpcode();
        if (54 <= opcode && opcode < 59) {
            VarInsnNode varInsnNode = (VarInsnNode) insn;
            this.locals[varInsnNode.var] = interpreter.store(varInsnNode);
        } else if (21 <= opcode && opcode < 26) {
            VarInsnNode varInsnNode2 = (VarInsnNode) insn;
            interpreter.load(varInsnNode2, get(varInsnNode2.var));
        } else if (opcode == 132) {
            IincInsnNode iincInsnNode = (IincInsnNode) insn;
            interpreter.iinc(iincInsnNode, get(iincInsnNode.var));
        }
    }

    public final boolean merge(StoreLoadFrame<V> other, StoreLoadInterpreter<V> interpreter) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        int length = this.locals.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            StoreLoadValue storeLoadValue = get(i);
            StoreLoadValue storeLoadValueMerge = interpreter.merge(storeLoadValue, other.get(i));
            if (!Intrinsics.areEqual(storeLoadValueMerge, storeLoadValue)) {
                set(i, storeLoadValueMerge);
                z = true;
            }
        }
        return z;
    }
}
