package com.android.tools.r8;

import com.android.tools.r8.origin.ArchiveEntryOrigin;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: renamed from: com.android.tools.r8.w, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C4000w implements DataEntryResource {
    static final /* synthetic */ boolean c = true;
    private final ZipFile a;
    private final ZipEntry b;

    private C4000w(ZipFile zipFile, ZipEntry zipEntry) {
        boolean z = c;
        if (!z && zipFile == null) {
            throw new AssertionError();
        }
        if (!z && zipEntry == null) {
            throw new AssertionError();
        }
        this.a = zipFile;
        this.b = zipEntry;
    }

    @Override // com.android.tools.r8.DataEntryResource
    public final InputStream getByteStream() throws ResourceException {
        try {
            return this.a.getInputStream(this.b);
        } catch (IOException e) {
            throw new ResourceException(getOrigin(), e);
        }
    }

    @Override // com.android.tools.r8.DataResource
    public final String getName() {
        return this.b.getName();
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return new ArchiveEntryOrigin(this.b.getName(), new PathOrigin(Paths.get(this.a.getName(), new String[0])));
    }
}
