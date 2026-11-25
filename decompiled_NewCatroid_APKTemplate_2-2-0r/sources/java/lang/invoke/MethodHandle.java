package java.lang.invoke;

import java.util.List;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/MethodHandle.class */
public abstract class MethodHandle {
    public MethodType type();

    public final Object invokeExact(Object... args) throws Throwable;

    public final Object invoke(Object... args) throws Throwable;

    public Object invokeWithArguments(Object... arguments) throws Throwable;

    public Object invokeWithArguments(List<?> arguments) throws Throwable;

    public MethodHandle asType(MethodType newType);

    public MethodHandle asCollector(Class<?> arrayType, int arrayLength);

    public MethodHandle asVarargsCollector(Class<?> arrayType);

    public boolean isVarargsCollector();

    public MethodHandle asFixedArity();

    public MethodHandle bindTo(Object x);
}
