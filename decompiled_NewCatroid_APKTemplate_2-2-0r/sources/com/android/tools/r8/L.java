package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L implements GlobalSyntheticsResourceProvider {
    private final Path a;
    private final PathOrigin b;

    public L(Path path) {
        this.a = path;
        this.b = new PathOrigin(path);
    }

    @Override // com.android.tools.r8.GlobalSyntheticsResourceProvider
    public final InputStream getByteStream() throws ResourceException {
        try {
            return Files.newInputStream(this.a, new OpenOption[0]);
        } catch (IOException e) {
            throw new ResourceException(this.b, e);
        }
    }

    @Override // com.android.tools.r8.GlobalSyntheticsResourceProvider
    public final Origin getOrigin() {
        return this.b;
    }
}
