package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MappingComposeException;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2431l10 extends com.android.tools.r8.naming.mappinginformation.c {
    private final String a;
    private final String b;

    public C2431l10(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        return true;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) throws MappingComposeException {
        throw new MappingComposeException("Unable to compose unknown json mapping information");
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final C2431l10 j() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        return this.b;
    }

    public final String q() {
        return this.a;
    }

    public final String r() {
        return this.b;
    }

    public static void a(String str, C1333Rt c1333Rt, Consumer consumer) {
        consumer.accept(new C2431l10(str, c1333Rt.toString()));
    }
}
