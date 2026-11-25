package com.android.tools.r8;

import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3596j extends DexFilePerClassFileConsumer.DirectoryConsumer {
    C3596j(Path path, boolean z) {
        super(path, z);
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.DexFilePerClassFileConsumer
    public final boolean combineSyntheticClassesWithPrimaryClass() {
        return false;
    }
}
