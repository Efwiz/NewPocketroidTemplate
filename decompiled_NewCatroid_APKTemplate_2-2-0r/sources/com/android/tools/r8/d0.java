package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.X0;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface d0 extends Resource {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements d0 {
        static final /* synthetic */ boolean d = true;
        private final Path a;
        private final Charset b;
        private final PathOrigin c;

        private a(Path path, Charset charset) {
            boolean z = d;
            if (!z && path == null) {
                throw new AssertionError();
            }
            if (!z && charset == null) {
                throw new AssertionError();
            }
            this.a = path;
            this.b = charset;
            this.c = new PathOrigin(path);
        }

        @Override // com.android.tools.r8.d0
        public final String a() throws ResourceException {
            try {
                return X0.a(this.a, this.b);
            } catch (IOException e) {
                throw new ResourceException(this.c, e);
            }
        }

        @Override // com.android.tools.r8.Resource
        public final Origin getOrigin() {
            return this.c;
        }
    }

    static d0 a(String str, Origin origin) {
        return new e0(origin, str);
    }

    String a() throws ResourceException;

    static a a(Path path) {
        return new a(path, StandardCharsets.UTF_8);
    }
}
