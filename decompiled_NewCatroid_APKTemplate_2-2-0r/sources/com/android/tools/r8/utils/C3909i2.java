package com.android.tools.r8.utils;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.origin.Origin;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.utils.i2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3909i2 implements ProgramResource {
    static final /* synthetic */ boolean e = true;
    private final Origin a;
    private final ProgramResource.Kind b;
    private byte[] c;
    private final Set d;

    private C3909i2(Origin origin, ProgramResource.Kind kind, byte[] bArr, Set set) {
        if (!e && bArr == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = kind;
        this.c = bArr;
        this.d = set;
    }

    public static C3909i2 a(Origin origin, ProgramResource.Kind kind, byte[] bArr, Set set) {
        return new C3909i2(origin, kind, bArr, set);
    }

    @Override // com.android.tools.r8.ProgramResource
    public final InputStream getByteStream() {
        return new ByteArrayInputStream(getBytes());
    }

    @Override // com.android.tools.r8.ProgramResource
    public final byte[] getBytes() {
        if (!e && this.c == null) {
            throw new AssertionError();
        }
        byte[] bArr = this.c;
        this.c = null;
        return bArr;
    }

    @Override // com.android.tools.r8.ProgramResource
    public final Set getClassDescriptors() {
        return this.d;
    }

    @Override // com.android.tools.r8.ProgramResource
    public final ProgramResource.Kind getKind() {
        return this.b;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return this.a;
    }
}
