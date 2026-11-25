package com.android.tools.r8.tracereferences;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.origin.PathOrigin;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class g implements ClassFileResourceProvider {
    private final String a;
    private final ProgramResource b;

    g(PathOrigin pathOrigin, byte[] bArr) {
        String strA = TraceReferencesCommand.Builder.a(bArr);
        this.a = strA;
        this.b = ProgramResource.fromBytes(pathOrigin, ProgramResource.Kind.CF, bArr, AbstractC1048Hm.c(strA));
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final Set getClassDescriptors() {
        return AbstractC1048Hm.c(this.a);
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final ProgramResource getProgramResource(String str) {
        if (str.equals(this.a)) {
            return this.b;
        }
        return null;
    }
}
