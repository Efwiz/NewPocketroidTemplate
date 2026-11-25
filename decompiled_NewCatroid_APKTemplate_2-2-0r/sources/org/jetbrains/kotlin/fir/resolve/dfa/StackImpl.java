package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: Stack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0019\u0012\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0004\"\u00028\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0016J\r\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\r\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/StackImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", CompoundEntry.NAME_values, "", "([Ljava/lang/Object;)V", "size", "", "getSize", "()I", "stack", "", DevModeOverwritingStrategies.ALL, "", "pop", "()Ljava/lang/Object;", "push", "", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "reset", "top", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StackImpl<T> extends Stack<T> {
    private final List<T> stack;

    public StackImpl(T... values) {
        Intrinsics.checkNotNullParameter(values, "values");
        this.stack = CollectionsKt.mutableListOf(Arrays.copyOf(values, values.length));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public T top() {
        List<T> list = this.stack;
        return list.get(list.size() - 1);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public T pop() {
        List<T> list = this.stack;
        return list.remove(list.size() - 1);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public void push(T value) {
        this.stack.add(value);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public int getSize() {
        return this.stack.size();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public void reset() {
        this.stack.clear();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Stack
    public List<T> all() {
        return CollectionsKt.asReversedMutable(this.stack);
    }
}
