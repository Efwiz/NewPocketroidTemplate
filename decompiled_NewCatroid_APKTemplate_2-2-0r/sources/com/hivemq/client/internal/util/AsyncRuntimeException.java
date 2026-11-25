package com.hivemq.client.internal.util;

import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AsyncRuntimeException extends RuntimeException {
    protected abstract AsyncRuntimeException copy();

    public static RuntimeException fillInStackTrace(RuntimeException runtimeException) {
        boolean z = runtimeException instanceof AsyncRuntimeException;
        RuntimeException runtimeException2 = runtimeException;
        if (z) {
            AsyncRuntimeException asyncRuntimeExceptionCopy = ((AsyncRuntimeException) runtimeException).copy();
            StackTraceElement[] stackTrace = asyncRuntimeExceptionCopy.getStackTrace();
            int i = 0;
            while (i < stackTrace.length) {
                StackTraceElement stackTraceElement = stackTrace[i];
                i++;
                if (stackTraceElement.getClassName().equals(AsyncRuntimeException.class.getCanonicalName()) && stackTraceElement.getMethodName().equals("fillInStackTrace")) {
                    break;
                }
            }
            asyncRuntimeExceptionCopy.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i, stackTrace.length));
            runtimeException2 = asyncRuntimeExceptionCopy;
        }
        return runtimeException2;
    }

    protected AsyncRuntimeException(String str) {
        super(str, null);
    }

    protected AsyncRuntimeException(String str, Throwable th) {
        super(str, th);
    }

    protected AsyncRuntimeException(Throwable th) {
        super(th);
    }

    protected AsyncRuntimeException(AsyncRuntimeException asyncRuntimeException) {
        super(asyncRuntimeException.getMessage(), asyncRuntimeException.getCause());
        super.fillInStackTrace();
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
