package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.references.ClassReference;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BD implements CD {
    private final MapVersion b;
    private final C3491zE c;

    private BD(MapVersion mapVersion, C3491zE c3491zE) {
        this.b = mapVersion;
        this.c = c3491zE;
    }

    public static BD a(MapVersion mapVersion, C3491zE c3491zE) {
        return new BD(mapVersion, c3491zE);
    }

    @Override // com.android.tools.r8.internal.CD
    public final MapVersion b() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.CD
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.retrace.MappingPartitionMetadata
    public final byte[] getBytes() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.write(CD.a);
            dataOutputStream.writeShort(EnumC3490zD.c.a());
            String name2 = this.b.getName();
            dataOutputStream.writeShort(name2.length());
            dataOutputStream.writeBytes(name2);
            dataOutputStream.write(this.c.b());
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.tools.r8.internal.CD
    public final String a(ClassReference classReference) {
        return classReference.getTypeName();
    }

    @Override // com.android.tools.r8.internal.CD
    public final Collection a() {
        return this.c.a();
    }
}
