package org.jetbrains.kotlin.konan.file;

import androidx.exifinterface.media.ExifInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: File.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u001a\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u001a\n\u0010\n\u001a\u00020\u0005*\u00020\u000b\u001a\n\u0010\n\u001a\u00020\u0005*\u00020\u0007\u001a;\u0010\f\u001a\u0002H\r\"\n\b\u0000\u0010\u000e*\u0004\u0018\u00010\u000f\"\u0004\b\u0001\u0010\r*\u0002H\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\r0\u0011H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"bufferedReader", "Ljava/io/BufferedReader;", "errorStream", "Ljava/io/InputStream;", "createTempDir", "Lorg/jetbrains/kotlin/konan/file/File;", "name", "", "createTempFile", "suffix", "File", "Ljava/nio/file/Path;", "use", "R", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/lang/AutoCloseable;", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-util-io"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class FileKt {
    public static final File File(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new File(str);
    }

    public static final File File(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return new File(path);
    }

    public static /* synthetic */ File createTempFile$default(String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return createTempFile(str, str2);
    }

    public static final File createTempFile(String name2, String str) throws IOException {
        Intrinsics.checkNotNullParameter(name2, "name");
        Path pathCreateTempFile = Files.createTempFile(name2, str, new FileAttribute[0]);
        Intrinsics.checkNotNullExpressionValue(pathCreateTempFile, "createTempFile(name, suffix)");
        return File(pathCreateTempFile);
    }

    public static final File createTempDir(String name2) throws IOException {
        Intrinsics.checkNotNullParameter(name2, "name");
        Path pathCreateTempDirectory = Files.createTempDirectory(name2, new FileAttribute[0]);
        Intrinsics.checkNotNullExpressionValue(pathCreateTempDirectory, "createTempDirectory(name)");
        return File(pathCreateTempDirectory);
    }

    public static final BufferedReader bufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static final <T extends AutoCloseable, R> R use(T t, Function1<? super T, ? extends R> block) throws Exception {
        boolean z;
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            R rInvoke = block.invoke(t);
            InlineMarker.finallyStart(1);
            if (t != null) {
                t.close();
            }
            InlineMarker.finallyEnd(1);
            return rInvoke;
        } catch (Exception e) {
            if (t != null) {
                try {
                    try {
                        t.close();
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        InlineMarker.finallyStart(1);
                        if (!z && t != null) {
                            t.close();
                        }
                        InlineMarker.finallyEnd(1);
                        throw th;
                    }
                } catch (Exception unused) {
                }
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            z = false;
            InlineMarker.finallyStart(1);
            if (!z) {
                t.close();
            }
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }
}
