package com.android.tools.r8.internal;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.profile.art.ArtProfileBuilder;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import com.android.tools.r8.utils.C3912j0;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E3 implements ArtProfileProvider {
    final /* synthetic */ Path a;

    E3(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileProvider
    public final void getArtProfile(ArtProfileBuilder artProfileBuilder) {
        try {
            artProfileBuilder.addHumanReadableArtProfile(new com.android.tools.r8.utils.J3(this.a), C3912j0.b());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return new PathOrigin(this.a);
    }
}
