package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;

/* JADX INFO: compiled from: ArrayConstructor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/ArrayConstructor;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayConstructor extends IntrinsicMethod {
    public static final ArrayConstructor INSTANCE = new ArrayConstructor();

    private ArrayConstructor() {
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return new IntrinsicCallable(method.getOwner(), method.getValueParameterTypes()) { // from class: org.jetbrains.kotlin.codegen.intrinsics.ArrayConstructor.toCallable.1
        };
    }
}
