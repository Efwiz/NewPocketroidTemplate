package com.android.tools.r8.internal;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.profile.art.ArtProfileBuilder;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3889e2;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class H3 implements ArtProfileBuilder {
    final /* synthetic */ OutputStreamWriter a;

    H3(OutputStreamWriter outputStreamWriter) {
        this.a = outputStreamWriter;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addClassRule(Consumer consumer) {
        consumer.accept(new F3(this));
        return this;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addHumanReadableArtProfile(TextInputStream textInputStream, Consumer consumer) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(textInputStream.getInputStream(), textInputStream.getCharset());
            try {
                char[] cArr = new char[1024];
                for (int i = inputStreamReader.read(cArr); i != -1; i = inputStreamReader.read(cArr)) {
                    this.a.write(cArr, 0, i);
                }
                I3.a(this.a, "");
                inputStreamReader.close();
                return this;
            } catch (Throwable th) {
                try {
                    inputStreamReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addMethodRule(Consumer consumer) {
        com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        consumer.accept(new G3(this, n));
        I3.a(this.a, C3889e2.a((MethodReference) n.a()));
        return this;
    }
}
