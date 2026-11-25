package com.android.tools.r8.tracereferences;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.PathOrigin;
import java.nio.file.Path;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class e implements ProgramResourceProvider {
    final /* synthetic */ Path a;
    final /* synthetic */ byte[] b;
    final /* synthetic */ String c;

    e(Path path, byte[] bArr, String str) {
        this.a = path;
        this.b = bArr;
        this.c = str;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return AbstractC1639am.c(ProgramResource.fromBytes(new PathOrigin(this.a), ProgramResource.Kind.CF, this.b, AbstractC1048Hm.c(this.c)));
    }
}
