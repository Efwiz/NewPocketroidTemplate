package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3874b3;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3065tW {
    private boolean a = AbstractC3874b3.a("com.android.tools.r8.startup.minimalstartupdex", true);
    private boolean b = AbstractC3874b3.a("com.android.tools.r8.startup.boundaryoptimizations", false);
    private boolean c = AbstractC3874b3.a("com.android.tools.r8.startup.completenesscheck", false);
    private boolean d = AbstractC3874b3.a("com.android.tools.r8.startup.layout", true);
    private Collection e = (Collection) AbstractC3874b3.a(new Function() { // from class: com.android.tools.r8.internal.tW$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return C3065tW.a((String) obj);
        }
    }, new Supplier() { // from class: com.android.tools.r8.internal.tW$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return Collections.emptyList();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(String str) {
        return AbstractC1639am.c(BW.a(Paths.get(str, new String[0])));
    }

    public C3065tW b(boolean z) {
        this.b = z;
        return this;
    }

    public final boolean c() {
        return this.a;
    }

    public final boolean d() {
        return this.b;
    }

    public final boolean e() {
        return this.c;
    }

    public final boolean f() {
        return this.d;
    }

    public C3065tW g() {
        return c(true);
    }

    public final boolean b() {
        Collection collection = this.e;
        return (collection == null || collection.isEmpty()) ? false : true;
    }

    public C3065tW c(boolean z) {
        this.c = z;
        return this;
    }

    public C3065tW a(boolean z) {
        this.a = z;
        return this;
    }

    public final Collection a() {
        return this.e;
    }

    public final void a(List list) {
        this.e = list;
    }
}
