package com.android.tools.r8.utils;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1303Qq;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class T {
    static final /* synthetic */ boolean c = true;
    private HashMap a = null;
    private final C3945p b;

    public T(C3945p c3945p) {
        this.b = c3945p;
    }

    public static String b(String str) {
        int iIndexOf = str.indexOf(";;");
        if (c || iIndexOf > 0) {
            return str.substring(0, iIndexOf);
        }
        throw new AssertionError();
    }

    public final String a(int i, String str) throws ResourceException {
        if (this.a == null) {
            this.a = new HashMap();
            a();
        }
        C1303Qq c1303Qq = (C1303Qq) this.a.get(str);
        if (c1303Qq == null) {
            return null;
        }
        return (String) c1303Qq.get(i);
    }

    private void a() throws ResourceException {
        final Q q = new Q(this);
        for (ProgramResourceProvider programResourceProvider : this.b.l()) {
            if (programResourceProvider instanceof ArchiveResourceProvider) {
                ((ArchiveResourceProvider) programResourceProvider).accept(new Consumer() { // from class: com.android.tools.r8.utils.T$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        T.a(q, (ProgramResource) obj);
                    }
                });
            } else {
                for (ProgramResource programResource : programResourceProvider.getProgramResources()) {
                    if (programResource.getKind() == ProgramResource.Kind.CF) {
                        try {
                            new C1031Ha(W2.a(programResource.getByteStream())).a(4, q);
                        } catch (IOException unused) {
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Q q, ProgramResource programResource) {
        if (programResource.getKind() != ProgramResource.Kind.CF) {
            return;
        }
        try {
            new C1031Ha(W2.a(programResource.getByteStream())).a(4, q);
        } catch (ResourceException | IOException unused) {
        }
    }

    public static String a(String str) {
        int iIndexOf = str.indexOf(";;");
        if (c || iIndexOf > 0) {
            return str.substring(iIndexOf + 2);
        }
        throw new AssertionError();
    }
}
