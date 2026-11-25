package org.jetbrains.kotlin.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Resolve.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/container/MethodBinding;", "", "method", "Ljava/lang/reflect/Method;", "argumentDescriptors", "", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "(Ljava/lang/reflect/Method;Ljava/util/List;)V", "getMethod", "()Ljava/lang/reflect/Method;", "invoke", "", "instance", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodBinding {
    private final List<ValueDescriptor> argumentDescriptors;
    private final Method method;

    /* JADX WARN: Multi-variable type inference failed */
    public MethodBinding(Method method, List<? extends ValueDescriptor> argumentDescriptors) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(argumentDescriptors, "argumentDescriptors");
        this.method = method;
        this.argumentDescriptors = argumentDescriptors;
    }

    public final Method getMethod() {
        return this.method;
    }

    public final void invoke(Object instance) throws Throwable {
        Intrinsics.checkNotNullParameter(instance, "instance");
        Object[] array = ResolveKt.computeArguments(this.argumentDescriptors).toArray(new Object[0]);
        try {
            this.method.invoke(instance, Arrays.copyOf(array, array.length));
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException == null) {
                throw e;
            }
            Intrinsics.checkNotNull(targetException);
            throw targetException;
        }
    }
}
