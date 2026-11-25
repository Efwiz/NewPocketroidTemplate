package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Equals.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"equalsMethodDescriptor", "", "getEqualsMethodDescriptor", "()Ljava/lang/String;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EqualsKt {
    private static final String equalsMethodDescriptor;

    public static final String getEqualsMethodDescriptor() {
        return equalsMethodDescriptor;
    }

    static {
        String methodDescriptor = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getType((Class<?>) Object.class));
        Intrinsics.checkNotNullExpressionValue(methodDescriptor, "getMethodDescriptor(...)");
        equalsMethodDescriptor = methodDescriptor;
    }
}
