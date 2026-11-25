package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FastJarFileSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0003H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000*$\b\u0002\u0010\u0006\"\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00072\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¨\u0006\n"}, d2 = {"IS_PRIOR_9_JRE", "", "prepareCleanerCallback", "Lkotlin/Function1;", "Ljava/nio/ByteBuffer;", "", "RandomAccessFileAndBuffer", "Lkotlin/Pair;", "Ljava/io/RandomAccessFile;", "Ljava/nio/MappedByteBuffer;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FastJarFileSystemKt {
    private static final boolean IS_PRIOR_9_JRE;

    static {
        String property = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION, "");
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        IS_PRIOR_9_JRE = StringsKt.startsWith$default(property, "1.", false, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Function1<ByteBuffer, Unit> prepareCleanerCallback() {
        Class<?> cls;
        Function1<ByteBuffer, Unit> function1;
        try {
            if (IS_PRIOR_9_JRE) {
                final Method method = Class.forName("java.nio.DirectByteBuffer").getMethod("cleaner", new Class[0]);
                method.setAccessible(true);
                final Method method2 = Class.forName("sun.misc.Cleaner").getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                method2.setAccessible(true);
                function1 = new Function1<ByteBuffer, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystemKt.prepareCleanerCallback.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ByteBuffer byteBuffer) throws IllegalAccessException, InvocationTargetException {
                        invoke2(byteBuffer);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ByteBuffer buffer) throws IllegalAccessException, InvocationTargetException {
                        Intrinsics.checkNotNullParameter(buffer, "buffer");
                        method2.invoke(method.invoke(buffer, new Object[0]), new Object[0]);
                    }
                };
            } else {
                try {
                    cls = Class.forName("sun.misc.Unsafe");
                } catch (Exception unused) {
                    cls = Class.forName("jdk.internal.misc.Unsafe");
                }
                final Method method3 = cls.getMethod("invokeCleaner", ByteBuffer.class);
                method3.setAccessible(true);
                Field declaredField = cls.getDeclaredField("theUnsafe");
                declaredField.setAccessible(true);
                final Object obj = declaredField.get(null);
                function1 = new Function1<ByteBuffer, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystemKt.prepareCleanerCallback.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ByteBuffer byteBuffer) throws IllegalAccessException, InvocationTargetException {
                        invoke2(byteBuffer);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ByteBuffer buffer) throws IllegalAccessException, InvocationTargetException {
                        Intrinsics.checkNotNullParameter(buffer, "buffer");
                        method3.invoke(obj, buffer);
                    }
                };
            }
            return function1;
        } catch (Exception unused2) {
            return null;
        }
    }
}
