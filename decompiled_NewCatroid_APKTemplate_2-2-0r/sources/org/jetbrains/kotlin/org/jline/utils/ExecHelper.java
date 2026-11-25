package org.jetbrains.kotlin.org.jline.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class ExecHelper {
    public static String exec(boolean z, String... strArr) throws Throwable {
        Objects.requireNonNull(strArr);
        try {
            Log.trace("Running: ", strArr);
            ProcessBuilder processBuilder = new ProcessBuilder(strArr);
            if (z) {
                processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            }
            Process processStart = processBuilder.start();
            String strWaitAndCapture = waitAndCapture(processStart);
            Log.trace("Result: ", strWaitAndCapture);
            if (processStart.exitValue() == 0) {
                return strWaitAndCapture;
            }
            if (strWaitAndCapture.endsWith("\n")) {
                strWaitAndCapture = strWaitAndCapture.substring(0, strWaitAndCapture.length() - 1);
            }
            throw new IOException("Error executing '" + String.join(" ", strArr) + "': " + strWaitAndCapture);
        } catch (InterruptedException e) {
            throw ((IOException) new InterruptedIOException("Command interrupted").initCause(e));
        }
    }

    public static String waitAndCapture(Process process) throws Throwable {
        OutputStream outputStream;
        InputStream errorStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStream outputStream2 = null;
        try {
            InputStream inputStream = process.getInputStream();
            while (true) {
                try {
                    int i = inputStream.read();
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(i);
                } catch (Throwable th) {
                    th = th;
                    outputStream = null;
                    errorStream = null;
                }
                outputStream2 = inputStream;
                close(outputStream2, outputStream, errorStream);
                throw th;
            }
            errorStream = process.getErrorStream();
            while (true) {
                try {
                    int i2 = errorStream.read();
                    if (i2 != -1) {
                        byteArrayOutputStream.write(i2);
                    } else {
                        outputStream2 = process.getOutputStream();
                        process.waitFor();
                        close(inputStream, outputStream2, errorStream);
                        return byteArrayOutputStream.toString();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStream2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            outputStream = null;
            errorStream = null;
        }
    }

    private static void close(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception unused) {
                }
            }
        }
    }
}
