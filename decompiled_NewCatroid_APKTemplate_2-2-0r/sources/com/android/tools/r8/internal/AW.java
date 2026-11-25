package com.android.tools.r8.internal;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.startup.StartupProfileBuilder;
import com.android.tools.r8.startup.StartupProfileProvider;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AW implements StartupProfileProvider {
    final /* synthetic */ Path a;
    final /* synthetic */ Consumer b;

    AW(Path path, Consumer consumer) {
        this.a = path;
        this.b = consumer;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return new PathOrigin(this.a);
    }

    @Override // com.android.tools.r8.startup.StartupProfileProvider
    public final void getStartupProfile(StartupProfileBuilder startupProfileBuilder) {
        try {
            startupProfileBuilder.addHumanReadableArtProfile(new com.android.tools.r8.utils.J3(this.a), this.b);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
