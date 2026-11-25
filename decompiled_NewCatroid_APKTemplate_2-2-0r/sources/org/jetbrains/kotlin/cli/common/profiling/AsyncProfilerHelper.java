package org.jetbrains.kotlin.cli.common.profiling;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AsyncProfilerWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u0016\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/profiling/AsyncProfilerHelper;", "", "()V", "instance", "Lorg/jetbrains/kotlin/cli/common/profiling/AsyncProfilerReflected;", "getInstance", "libPath", "", "loadAsyncProfilerClass", "Ljava/lang/Class;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AsyncProfilerHelper {
    public static final AsyncProfilerHelper INSTANCE = new AsyncProfilerHelper();
    private static AsyncProfilerReflected instance;

    private AsyncProfilerHelper() {
    }

    public final AsyncProfilerReflected getInstance(String libPath) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        AsyncProfilerReflected asyncProfilerReflected = instance;
        if (asyncProfilerReflected != null) {
            return asyncProfilerReflected;
        }
        Class<?> clsLoadAsyncProfilerClass = loadAsyncProfilerClass(libPath);
        MethodHandle methodHandleFindStatic = MethodHandles.lookup().findStatic(clsLoadAsyncProfilerClass, "getInstance", MethodType.methodType(clsLoadAsyncProfilerClass, (Class<?>) String.class));
        AsyncProfilerReflected asyncProfilerReflected2 = new AsyncProfilerReflected(MethodHandles.lookup().findVirtual(clsLoadAsyncProfilerClass, "execute", MethodType.methodType((Class<?>) String.class, (Class<?>) String.class)), methodHandleFindStatic.invokeWithArguments(libPath), MethodHandles.lookup().findVirtual(clsLoadAsyncProfilerClass, "stop", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findVirtual(clsLoadAsyncProfilerClass, "getVersion", MethodType.methodType(String.class))) { // from class: org.jetbrains.kotlin.cli.common.profiling.AsyncProfilerHelper.getInstance.2
            private final MethodHandle boundExecute;
            private final MethodHandle boundGetVersion;
            private final MethodHandle boundStop;

            {
                this.boundExecute = methodHandle.bindTo(obj);
                this.boundStop = methodHandle.bindTo(obj);
                this.boundGetVersion = methodHandle.bindTo(obj);
            }

            @Override // org.jetbrains.kotlin.cli.common.profiling.AsyncProfilerReflected
            public String execute(String command) throws Throwable {
                Intrinsics.checkNotNullParameter(command, "command");
                Object objInvokeWithArguments = this.boundExecute.invokeWithArguments(command);
                Intrinsics.checkNotNull(objInvokeWithArguments, "null cannot be cast to non-null type kotlin.String");
                return (String) objInvokeWithArguments;
            }

            @Override // org.jetbrains.kotlin.cli.common.profiling.AsyncProfilerReflected
            public void stop() throws Throwable {
                this.boundStop.invokeWithArguments(new Object[0]);
            }

            @Override // org.jetbrains.kotlin.cli.common.profiling.AsyncProfilerReflected
            public String getVersion() throws Throwable {
                Object objInvokeWithArguments = this.boundGetVersion.invokeWithArguments(new Object[0]);
                Intrinsics.checkNotNull(objInvokeWithArguments, "null cannot be cast to non-null type kotlin.String");
                return (String) objInvokeWithArguments;
            }
        };
        instance = asyncProfilerReflected2;
        return asyncProfilerReflected2;
    }

    private final Class<?> loadAsyncProfilerClass(String libPath) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("one.profiler.AsyncProfiler");
            Intrinsics.checkNotNull(cls);
            return cls;
        } catch (ClassNotFoundException e) {
            if (libPath == null) {
                throw e;
            }
            File parentFile = new File(libPath).getParentFile();
            if (!parentFile.isDirectory()) {
                Intrinsics.checkNotNull(parentFile);
                throw new IllegalStateException(parentFile.toString());
            }
            Intrinsics.checkNotNull(parentFile);
            File fileResolve = FilesKt.resolve(parentFile, "async-profiler.jar");
            if (!fileResolve.exists()) {
                throw new IllegalStateException(("To use async-profiler, either add it to the compiler classpath, or put async-profiler.jar at this path: " + fileResolve).toString());
            }
            Class<?> clsLoadClass = new URLClassLoader(new URL[]{fileResolve.toURI().toURL()}, null).loadClass("one.profiler.AsyncProfiler");
            Intrinsics.checkNotNull(clsLoadClass);
            return clsLoadClass;
        }
    }
}
