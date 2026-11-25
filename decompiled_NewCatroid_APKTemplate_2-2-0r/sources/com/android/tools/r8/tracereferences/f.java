package com.android.tools.r8.tracereferences;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class f implements ProgramResourceProvider {
    ProgramResource a;

    f(Path path) {
        this.a = ProgramResource.fromFile(ProgramResource.Kind.DEX, path);
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return Collections.singletonList(this.a);
    }
}
