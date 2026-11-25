package com.android.tools.r8.utils;

import java.nio.file.Path;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class M0 extends N0 {
    final /* synthetic */ Path a;

    M0(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.utils.O0
    public final Path b() {
        return this.a.resolve(ArchiveStreamFactory.DUMP + System.nanoTime() + Constants.ZIP_EXTENSION);
    }

    @Override // com.android.tools.r8.utils.O0
    public final boolean d() {
        return false;
    }
}
