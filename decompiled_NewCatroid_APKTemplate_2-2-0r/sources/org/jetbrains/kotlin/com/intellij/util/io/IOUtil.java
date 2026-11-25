package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes3.dex */
public final class IOUtil {
    public static final boolean BYTE_BUFFERS_USE_NATIVE_BYTE_ORDER = Boolean.parseBoolean(System.getProperty("idea.bytebuffers.use.native.byte.order", "true"));
    private static final ThreadLocalCachedValue<byte[]> ourReadWriteBuffersCache = new ThreadLocalCachedValue<byte[]>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOUtil.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/io/IOUtil$1", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue
        public byte[] create() {
            byte[] bArrAllocReadWriteUTFBuffer = IOUtil.allocReadWriteUTFBuffer();
            if (bArrAllocReadWriteUTFBuffer == null) {
                $$$reportNull$$$0(0);
            }
            return bArrAllocReadWriteUTFBuffer;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 27 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 27 ? 3 : 2];
        switch (i) {
            case 3:
                objArr[0] = "text";
                break;
            case 4:
            case 6:
            case 7:
            case 10:
                objArr[0] = "storage";
                break;
            case 5:
            case 8:
                objArr[0] = "value";
                break;
            case 9:
            case 11:
                objArr[0] = "buffer";
                break;
            case 12:
            case 13:
                objArr[0] = "str";
                break;
            case 14:
            case 15:
            case 18:
            case 20:
                objArr[0] = "file";
                break;
            case 16:
            default:
                objArr[0] = "stream";
                break;
            case 17:
            case 19:
            case 21:
                objArr[0] = "factoryComputable";
                break;
            case 22:
                objArr[0] = "cleanupCallback";
                break;
            case 23:
                objArr[0] = "out";
                break;
            case 24:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 25:
            case 28:
                objArr[0] = "in";
                break;
            case 26:
                objArr[0] = "collectionGenerator";
                break;
            case 27:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/IOUtil";
                break;
            case 29:
                objArr[0] = K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG;
                break;
        }
        if (i != 27) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/IOUtil";
        } else {
            objArr[1] = "readStringCollection";
        }
        switch (i) {
            case 1:
                objArr[2] = "writeString";
                break;
            case 2:
            case 3:
                objArr[2] = "writeUTFTruncated";
                break;
            case 4:
            case 5:
                objArr[2] = "writeUTF";
                break;
            case 6:
                objArr[2] = "readUTF";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "writeUTFFast";
                break;
            case 10:
            case 11:
                objArr[2] = "readUTFFast";
                break;
            case 12:
            case 13:
                objArr[2] = "isAscii";
                break;
            case 14:
            case 15:
                objArr[2] = "deleteAllFilesStartingWith";
                break;
            case 16:
                objArr[2] = "syncStream";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[2] = "openCleanOrResetBroken";
                break;
            case 23:
            case 24:
                objArr[2] = "writeStringList";
                break;
            case 25:
            case 26:
                objArr[2] = "readStringCollection";
                break;
            case 27:
                break;
            case 28:
                objArr[2] = "readStringList";
                break;
            case 29:
                objArr[2] = "closeSafe";
                break;
            default:
                objArr[2] = "readString";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 27) {
            throw new IllegalStateException(str2);
        }
    }

    public static /* synthetic */ ArrayList $r8$lambda$9HVUbdzzZZl5VSSwgpLXOWVRssk(int i) {
        return new ArrayList(i);
    }

    public static boolean isAscii(char c) {
        return c < 128;
    }

    private IOUtil() {
    }

    public static String readString(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(0);
        }
        try {
            int i = dataInput.readInt();
            if (i == -1) {
                return null;
            }
            if (i == 0) {
                return "";
            }
            int i2 = i * 2;
            byte[] bArr = new byte[i2];
            dataInput.readFully(bArr);
            return new String(bArr, 0, i2, StandardCharsets.UTF_16BE);
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    public static void writeString(String str, DataOutput dataOutput) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            dataOutput.writeInt(-1);
            return;
        }
        dataOutput.writeInt(str.length());
        if (str.isEmpty()) {
            return;
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[charArray.length * 2];
        int i = 0;
        int i2 = 0;
        while (i < charArray.length) {
            char c = charArray[i];
            bArr[i2] = (byte) ((c >>> '\b') & 255);
            bArr[i2 + 1] = (byte) (c & 255);
            i++;
            i2 += 2;
        }
        dataOutput.write(bArr);
    }

    public static void writeUTFTruncated(DataOutput dataOutput, String str) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (str.length() > 16383) {
            dataOutput.writeUTF(str.substring(0, Lua.MASK_NOT_Bx));
        } else {
            dataOutput.writeUTF(str);
        }
    }

    public static void writeUTF(DataOutput dataOutput, String str) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        writeUTFFast(ourReadWriteBuffersCache.getValue(), dataOutput, str);
    }

    public static String readUTF(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(6);
        }
        return readUTFFast(ourReadWriteBuffersCache.getValue(), dataInput);
    }

    public static byte[] allocReadWriteUTFBuffer() {
        return new byte[256];
    }

    public static void writeUTFFast(byte[] bArr, DataOutput dataOutput, String str) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (bArr == null) {
            $$$reportNull$$$0(9);
        }
        int length = str.length();
        if (length < 255) {
            bArr[0] = (byte) length;
            int i = 0;
            while (true) {
                if (i < length) {
                    char cCharAt = str.charAt(i);
                    if (cCharAt >= 128) {
                        break;
                    }
                    i++;
                    bArr[i] = (byte) cCharAt;
                } else {
                    dataOutput.write(bArr, 0, length + 1);
                    return;
                }
            }
        }
        dataOutput.writeByte(-1);
        try {
            dataOutput.writeUTF(str);
        } catch (UTFDataFormatException unused) {
            dataOutput.writeUTF("LONGER_THAN_64K");
            writeString(str, dataOutput);
        }
    }

    public static String readUTFFast(byte[] bArr, DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(10);
        }
        if (bArr == null) {
            $$$reportNull$$$0(11);
        }
        int i = dataInput.readByte() & 255;
        if (i == 255) {
            String utf = dataInput.readUTF();
            return "LONGER_THAN_64K".equals(utf) ? readString(dataInput) : utf;
        }
        if (i == 0) {
            return "";
        }
        dataInput.readFully(bArr, 0, i);
        return new String(bArr, 0, i, StandardCharsets.ISO_8859_1);
    }

    public static boolean isAscii(String str) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        return isAscii((CharSequence) str);
    }

    public static boolean isAscii(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(13);
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) >= 128) {
                return false;
            }
        }
        return true;
    }

    public static boolean deleteAllFilesStartingWith(Path path) {
        if (path == null) {
            $$$reportNull$$$0(14);
        }
        final String string = path.getFileName().toString();
        Path parent = path.getParent();
        boolean z = true;
        if (parent == null) {
            return true;
        }
        try {
            Stream<Path> list = Files.list(parent);
            try {
                List list2 = (List) list.filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOUtil$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Path) obj).getFileName().toString().startsWith(string);
                    }
                }).collect(Collectors.toList());
                if (list != null) {
                    list.close();
                }
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    try {
                        Files.deleteIfExists((Path) it.next());
                    } catch (IOException unused) {
                        z = false;
                    }
                }
                return z;
            } catch (Throwable th) {
                if (list != null) {
                    try {
                        list.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (NoSuchFileException unused2) {
            return true;
        } catch (IOException unused3) {
            return false;
        }
    }

    public static boolean deleteAllFilesStartingWith(File file) {
        if (file == null) {
            $$$reportNull$$$0(15);
        }
        final String name2 = file.getName();
        File parentFile = file.getParentFile();
        File[] fileArrListFiles = parentFile != null ? parentFile.listFiles(new FileFilter() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOUtil$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                return file2.getName().startsWith(name2);
            }
        }) : null;
        boolean zDelete = true;
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                zDelete &= FileUtilRt.delete(file2);
            }
        }
        return zDelete;
    }

    public static void syncStream(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            $$$reportNull$$$0(16);
        }
        outputStream.flush();
        try {
            Field declaredField = FilterOutputStream.class.getDeclaredField("out");
            declaredField.setAccessible(true);
            while (outputStream instanceof FilterOutputStream) {
                Object obj = declaredField.get(outputStream);
                if (!(obj instanceof OutputStream)) {
                    break;
                } else {
                    outputStream = (OutputStream) obj;
                }
            }
            if (outputStream instanceof FileOutputStream) {
                ((FileOutputStream) outputStream).getFD().sync();
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T openCleanOrResetBroken(ThrowableComputable<T, ? extends IOException> throwableComputable, Path path) throws IOException {
        if (throwableComputable == null) {
            $$$reportNull$$$0(17);
        }
        if (path == null) {
            $$$reportNull$$$0(18);
        }
        return (T) openCleanOrResetBroken(throwableComputable, path.toFile());
    }

    public static <T> T openCleanOrResetBroken(ThrowableComputable<T, ? extends IOException> throwableComputable, final File file) throws IOException {
        if (throwableComputable == null) {
            $$$reportNull$$$0(19);
        }
        if (file == null) {
            $$$reportNull$$$0(20);
        }
        return (T) openCleanOrResetBroken(throwableComputable, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOUtil$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                IOUtil.deleteAllFilesStartingWith(file);
            }
        });
    }

    public static <T> T openCleanOrResetBroken(ThrowableComputable<T, ? extends IOException> throwableComputable, Runnable runnable) throws IOException {
        if (throwableComputable == null) {
            $$$reportNull$$$0(21);
        }
        if (runnable == null) {
            $$$reportNull$$$0(22);
        }
        try {
            return throwableComputable.compute();
        } catch (IOException unused) {
            runnable.run();
            return throwableComputable.compute();
        }
    }

    public static void writeStringList(DataOutput dataOutput, Collection<String> collection) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(23);
        }
        if (collection == null) {
            $$$reportNull$$$0(24);
        }
        DataInputOutputUtil.writeINT(dataOutput, collection.size());
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            writeUTF(dataOutput, it.next());
        }
    }

    public static <C extends Collection<String>> C readStringCollection(DataInput dataInput, IntFunction<? extends C> intFunction) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(25);
        }
        if (intFunction == null) {
            $$$reportNull$$$0(26);
        }
        int i = DataInputOutputUtil.readINT(dataInput);
        C cApply = intFunction.apply(i);
        for (int i2 = 0; i2 < i; i2++) {
            cApply.add(readUTF(dataInput));
        }
        if (cApply == null) {
            $$$reportNull$$$0(27);
        }
        return cApply;
    }

    public static List<String> readStringList(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(28);
        }
        return (List) readStringCollection(dataInput, new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return IOUtil.$r8$lambda$9HVUbdzzZZl5VSSwgpLXOWVRssk(i);
            }
        });
    }

    public static void closeSafe(Logger logger, Closeable... closeableArr) {
        if (logger == null) {
            $$$reportNull$$$0(29);
        }
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    logger.error((Throwable) e);
                }
            }
        }
    }
}
