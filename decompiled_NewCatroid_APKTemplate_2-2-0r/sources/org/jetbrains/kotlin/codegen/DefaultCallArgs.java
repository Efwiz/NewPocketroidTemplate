package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: DefaultCallArgs.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003J\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultCallArgs;", "", "size", "", "(I)V", "bits", "Ljava/util/BitSet;", "getSize", "()I", "generateOnStackIfNeeded", "", "callGenerator", "Lorg/jetbrains/kotlin/codegen/CallGenerator;", "isConstructor", "mark", "", "index", "toInts", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultCallArgs {
    private final BitSet bits;
    private final int size;

    public DefaultCallArgs(int i) {
        this.size = i;
        this.bits = new BitSet(i);
    }

    public final int getSize() {
        return this.size;
    }

    public final void mark(int index) {
        this.bits.set(index);
    }

    public final List<Integer> toInts() {
        if (this.bits.isEmpty() || this.size == 0) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(1);
        int i = this.size;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (i3 != 0 && i3 % 32 == 0) {
                arrayList.add(Integer.valueOf(i2));
                i2 = 0;
            }
            i2 |= this.bits.get(i3) ? 1 << (i3 % 32) : 0;
        }
        arrayList.add(Integer.valueOf(i2));
        return arrayList;
    }

    public final boolean generateOnStackIfNeeded(CallGenerator callGenerator, boolean isConstructor) {
        Intrinsics.checkNotNullParameter(callGenerator, "callGenerator");
        List<Integer> ints = toInts();
        if (!ints.isEmpty()) {
            Iterator<Integer> it = ints.iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                Type INT_TYPE = Type.INT_TYPE;
                Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                JvmKotlinType jvmKotlinType = new JvmKotlinType(INT_TYPE, null, 2, null);
                StackValue stackValueConstant = StackValue.constant(Integer.valueOf(iIntValue), Type.INT_TYPE);
                Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
                CallGenerator.putValueIfNeeded$default(callGenerator, jvmKotlinType, stackValueConstant, ValueKind.DEFAULT_MASK, 0, 8, null);
            }
            Type type = isConstructor ? AsmTypes.DEFAULT_CONSTRUCTOR_MARKER : AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNull(type);
            JvmKotlinType jvmKotlinType2 = new JvmKotlinType(type, null, 2, null);
            StackValue stackValueConstant2 = StackValue.constant(null, type);
            Intrinsics.checkNotNullExpressionValue(stackValueConstant2, "constant(...)");
            CallGenerator.putValueIfNeeded$default(callGenerator, jvmKotlinType2, stackValueConstant2, ValueKind.METHOD_HANDLE_IN_DEFAULT, 0, 8, null);
        }
        return !ints.isEmpty();
    }
}
