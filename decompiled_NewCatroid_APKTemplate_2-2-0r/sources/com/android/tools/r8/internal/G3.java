package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder;
import com.android.tools.r8.references.MethodReference;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class G3 implements ArtProfileMethodRuleBuilder {
    final /* synthetic */ com.android.tools.r8.utils.N a;
    final /* synthetic */ H3 b;

    G3(H3 h3, com.android.tools.r8.utils.N n) {
        this.b = h3;
        this.a = n;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
    public final ArtProfileMethodRuleBuilder setMethodReference(MethodReference methodReference) {
        this.a.a(methodReference);
        return this;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
    public final ArtProfileMethodRuleBuilder setMethodRuleInfo(Consumer consumer) {
        C3.a aVarA = C3.a();
        consumer.accept(aVarA);
        C3 c3A = aVarA.a();
        try {
            OutputStreamWriter outputStreamWriter = this.b.a;
            if (c3A.isHot()) {
                outputStreamWriter.write(72);
            }
            if (c3A.isStartup()) {
                outputStreamWriter.write(83);
            }
            if (c3A.isPostStartup()) {
                outputStreamWriter.write(80);
            }
            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
