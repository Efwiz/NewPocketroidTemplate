package org.jetbrains.org.objectweb.asm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes6.dex */
final class Constants {
    static void checkAsmExperimental(Object obj) {
        Class<?> cls = obj.getClass();
        String strReplace = cls.getName().replace('.', '/');
        if (isWhitelisted(strReplace)) {
            return;
        }
        checkIsPreview(cls.getClassLoader().getResourceAsStream(strReplace + ".class"));
    }

    static boolean isWhitelisted(String str) {
        if (str.startsWith("org/jetbrains/org/objectweb/asm/")) {
            return str.contains("Test$") || Pattern.matches("org/jetbrains/org/objectweb/asm/util/Trace(Annotation|Class|Field|Method|Module|RecordComponent|Signature)Visitor(\\$.*)?", str) || Pattern.matches("org/jetbrains/org/objectweb/asm/util/Check(Annotation|Class|Field|Method|Module|RecordComponent|Signature)Adapter(\\$.*)?", str);
        }
        return false;
    }

    static void checkIsPreview(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalStateException("Bytecode not available, can't check class version");
        }
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            try {
                dataInputStream.readInt();
                int unsignedShort = dataInputStream.readUnsignedShort();
                dataInputStream.close();
                if (unsignedShort != 65535) {
                    throw new IllegalStateException("ASM9_EXPERIMENTAL can only be used by classes compiled with --enable-preview");
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        dataInputStream.close();
                    } catch (Throwable unused) {
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("I/O error, can't check class version", e);
        }
    }
}
