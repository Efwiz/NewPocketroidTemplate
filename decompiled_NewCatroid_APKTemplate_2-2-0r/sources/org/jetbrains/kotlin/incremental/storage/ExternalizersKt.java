package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a#\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005\u001a#\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t\u001a\n\u0010\n\u001a\u00020\u000b*\u00020\f\u001a+\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0010\u001a#\u0010\u0011\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u000f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0012\u001a\u0012\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u000b¨\u0006\u0015"}, d2 = {"fromByteArray", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "byteArray", "", "(Lcom/intellij/util/io/DataExternalizer;[B)Ljava/lang/Object;", "loadFromFile", "file", "Ljava/io/File;", "(Lcom/intellij/util/io/DataExternalizer;Ljava/io/File;)Ljava/lang/Object;", "readString", "", "Ljava/io/DataInput;", "saveToFile", "", "value", "(Lcom/intellij/util/io/DataExternalizer;Ljava/io/File;Ljava/lang/Object;)V", "toByteArray", "(Lcom/intellij/util/io/DataExternalizer;Ljava/lang/Object;)[B", "writeString", "Ljava/io/DataOutput;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExternalizersKt {
    public static final <T> void saveToFile(DataExternalizer<T> dataExternalizer, File file, T t) {
        Intrinsics.checkNotNullParameter(dataExternalizer, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        OutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream instanceof BufferedOutputStream ? (BufferedOutputStream) fileOutputStream : new BufferedOutputStream(fileOutputStream, 8192));
        try {
            dataExternalizer.save(dataOutputStream, t);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(dataOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(dataOutputStream, th);
                throw th2;
            }
        }
    }

    public static final <T> T loadFromFile(DataExternalizer<T> dataExternalizer, File file) {
        Intrinsics.checkNotNullParameter(dataExternalizer, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        InputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream instanceof BufferedInputStream ? (BufferedInputStream) fileInputStream : new BufferedInputStream(fileInputStream, 8192));
        try {
            T t = dataExternalizer.read(dataInputStream);
            CloseableKt.closeFinally(dataInputStream, null);
            return t;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(dataInputStream, th);
                throw th2;
            }
        }
    }

    public static final <T> byte[] toByteArray(DataExternalizer<T> dataExternalizer, T t) {
        Intrinsics.checkNotNullParameter(dataExternalizer, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream2 instanceof BufferedOutputStream ? (BufferedOutputStream) byteArrayOutputStream2 : new BufferedOutputStream(byteArrayOutputStream2, 8192));
        try {
            dataExternalizer.save(dataOutputStream, t);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(dataOutputStream, null);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            return byteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(dataOutputStream, th);
                throw th2;
            }
        }
    }

    public static final <T> T fromByteArray(DataExternalizer<T> dataExternalizer, byte[] byteArray) {
        Intrinsics.checkNotNullParameter(dataExternalizer, "<this>");
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream instanceof BufferedInputStream ? (BufferedInputStream) byteArrayInputStream : new BufferedInputStream(byteArrayInputStream, 8192));
        try {
            T t = dataExternalizer.read(dataInputStream);
            CloseableKt.closeFinally(dataInputStream, null);
            return t;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(dataInputStream, th);
                throw th2;
            }
        }
    }

    public static final void writeString(DataOutput dataOutput, String value) {
        Intrinsics.checkNotNullParameter(dataOutput, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringExternalizer.INSTANCE.save(dataOutput, value);
    }

    public static final String readString(DataInput dataInput) {
        Intrinsics.checkNotNullParameter(dataInput, "<this>");
        return StringExternalizer.INSTANCE.read(dataInput);
    }
}
