package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.MappingPartitionMetadata;
import com.android.tools.r8.retrace.internal.RetracePartitionException;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import java.util.Collection;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface CD extends MappingPartitionMetadata {
    public static final byte[] a = {Opcodes.OPC_tableswitch, Opcodes.OPC_jsr};

    String a(ClassReference classReference);

    default Collection<String> a() {
        return null;
    }

    MapVersion b();

    default boolean c() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002e  */
    static CD a(byte[] bArr, MapVersion mapVersion, DiagnosticsHandler diagnosticsHandler) {
        if (bArr == null) {
            return new AD(mapVersion);
        }
        if (bArr.length > 2) {
            if (bArr.length >= 2) {
                int i = 0;
                while (true) {
                    byte[] bArr2 = a;
                    if (i < 2) {
                        if (bArr[i] != bArr2[i]) {
                            break;
                        }
                        i++;
                    } else if (AbstractC0968Es.a((byte) 0, (byte) 0, bArr[2], bArr[3]) == EnumC3490zD.c.a()) {
                        int iA = AbstractC0968Es.a((byte) 0, (byte) 0, bArr[4], bArr[5]);
                        return new BD(MapVersion.fromName(new String(bArr, 6, iA)), new C3422yE(bArr, iA + 6, bArr.length));
                    }
                }
                if (EnumC3490zD.b.a() == AbstractC0968Es.a((byte) 0, (byte) 0, bArr[0], bArr[1])) {
                    return new AD(MapVersion.fromName(new String(bArr, 2, bArr.length - 2)));
                }
            } else if (EnumC3490zD.b.a() == AbstractC0968Es.a((byte) 0, (byte) 0, bArr[0], bArr[1])) {
                return new AD(MapVersion.fromName(new String(bArr, 2, bArr.length - 2)));
            }
        }
        RetracePartitionException retracePartitionException = new RetracePartitionException("Unknown map partition strategy for metadata");
        diagnosticsHandler.error(new ExceptionDiagnostic(retracePartitionException));
        throw retracePartitionException;
    }
}
