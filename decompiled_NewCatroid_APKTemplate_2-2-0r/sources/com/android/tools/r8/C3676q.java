package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.File;

/* JADX INFO: renamed from: com.android.tools.r8.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3676q implements DataDirectoryResource {
    static final /* synthetic */ boolean c = true;
    private final File a;
    private final String b;

    private C3676q(String str, File file) {
        boolean z = c;
        if (!z && file == null) {
            throw new AssertionError();
        }
        if (!z && str == null) {
            throw new AssertionError();
        }
        this.a = file;
        this.b = str;
    }

    @Override // com.android.tools.r8.DataResource
    public final String getName() {
        return this.b;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return new PathOrigin(this.a.toPath());
    }
}
