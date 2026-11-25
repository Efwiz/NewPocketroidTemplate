package com.android.tools.r8.internal;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1258Pc {
    static void a(Object obj) {
        Class<?> cls = obj.getClass();
        String strReplace = cls.getName().replace('.', '/');
        if (strReplace.startsWith("org/objectweb/asm/") && (strReplace.contains("Test$") || Pattern.matches("org/objectweb/asm/util/Trace(Annotation|Class|Field|Method|Module|RecordComponent|Signature)Visitor(\\$.*)?", strReplace) || Pattern.matches("org/objectweb/asm/util/Check(Annotation|Class|Field|Method|Module|RecordComponent|Signature)Adapter(\\$.*)?", strReplace))) {
            return;
        }
        InputStream resourceAsStream = cls.getClassLoader().getResourceAsStream(strReplace.concat(".class"));
        if (resourceAsStream == null) {
            throw new IllegalStateException("Bytecode not available, can't check class version");
        }
        try {
            DataInputStream dataInputStream = new DataInputStream(resourceAsStream);
            try {
                dataInputStream.readInt();
                int unsignedShort = dataInputStream.readUnsignedShort();
                dataInputStream.close();
                if (unsignedShort != 65535) {
                    throw new IllegalStateException("ASM9_EXPERIMENTAL can only be used by classes compiled with --enable-preview");
                }
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (IOException e) {
            throw new IllegalStateException("I/O error, can't check class version", e);
        }
    }
}
