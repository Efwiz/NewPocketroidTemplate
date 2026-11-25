package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Stack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\u001a+\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0002\"\u0004\b\u0000\u0010\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00060\b\"\u0002H\u0006¢\u0006\u0002\u0010\t\u001a\u001d\u0010\n\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u0002¢\u0006\u0002\u0010\u000b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0019\u0010\u0004\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\f"}, d2 = {"isEmpty", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;)Z", "isNotEmpty", "stackOf", ExifInterface.GPS_DIRECTION_TRUE, CompoundEntry.NAME_values, "", "([Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "topOrNull", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;)Ljava/lang/Object;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StackKt {
    public static final <T> Stack<T> stackOf(T... values) {
        Intrinsics.checkNotNullParameter(values, "values");
        return new StackImpl(Arrays.copyOf(values, values.length));
    }

    public static final boolean isEmpty(Stack<?> stack) {
        Intrinsics.checkNotNullParameter(stack, "<this>");
        return stack.getSize() == 0;
    }

    public static final boolean isNotEmpty(Stack<?> stack) {
        Intrinsics.checkNotNullParameter(stack, "<this>");
        return stack.getSize() != 0;
    }

    public static final <T> T topOrNull(Stack<T> stack) {
        Intrinsics.checkNotNullParameter(stack, "<this>");
        if (stack.getSize() == 0) {
            return null;
        }
        return stack.top();
    }
}
