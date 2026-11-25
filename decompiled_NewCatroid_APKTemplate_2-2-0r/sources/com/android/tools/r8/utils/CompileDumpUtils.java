package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC3408y3;
import com.android.tools.r8.profile.art.ArtProfileConsumer;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import com.android.tools.r8.startup.StartupProfileProvider;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
class CompileDumpUtils {
    static ArtProfileProvider createArtProfileProviderFromDumpFile(Path path) {
        return com.android.tools.r8.internal.I3.a(path);
    }

    static ArtProfileConsumer createResidualArtProfileConsumerFromDumpFile(Path path) {
        return AbstractC3408y3.a(path);
    }

    static StartupProfileProvider createStartupProfileProviderFromDumpFile(Path path) {
        return new C3907i0(path);
    }
}
