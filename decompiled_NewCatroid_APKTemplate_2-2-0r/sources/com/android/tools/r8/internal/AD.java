package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.references.ClassReference;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AD implements CD {
    private final MapVersion b;

    private AD(MapVersion mapVersion) {
        this.b = mapVersion;
    }

    @Override // com.android.tools.r8.internal.CD
    public final String a(ClassReference classReference) {
        return classReference.getTypeName();
    }

    @Override // com.android.tools.r8.internal.CD
    public final MapVersion b() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.MappingPartitionMetadata
    public final byte[] getBytes() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeShort(EnumC3490zD.b.a());
            dataOutputStream.writeBytes(this.b.getName());
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AD a(MapVersion mapVersion) {
        return new AD(mapVersion);
    }
}
