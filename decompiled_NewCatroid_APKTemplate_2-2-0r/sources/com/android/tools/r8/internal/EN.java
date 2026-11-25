package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class EN {
    private Consumer a = new Consumer() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda3
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            EN.a((String) obj);
        }
    };
    private Consumer b = new Consumer() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda4
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            this.f$0.b((String) obj);
        }
    };
    private Consumer c = new Consumer() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda5
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            EN.c((String) obj);
        }
    };
    private Runnable d = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda6
        @Override // java.lang.Runnable
        public final void run() {
            EN.a();
        }
    };
    private Runnable e = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda6
        @Override // java.lang.Runnable
        public final void run() {
            EN.a();
        }
    };
    private Runnable f = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda6
        @Override // java.lang.Runnable
        public final void run() {
            EN.a();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static void a() {
        throw new AssertionError("Unhandled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str) {
        this.a.accept(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(String str) {
        a();
        throw null;
    }

    public final void d(String str) {
        String strTrim = str.trim();
        if (strTrim.equals("-dontobfuscate")) {
            this.f.run();
            return;
        }
        if (strTrim.equals("-dontoptimize")) {
            this.e.run();
            return;
        }
        if (strTrim.equals("-dontshrink")) {
            this.d.run();
            return;
        }
        if (strTrim.startsWith("-print")) {
            this.c.accept(str);
        } else if (strTrim.startsWith("#")) {
            this.b.accept(str);
        } else {
            this.a.accept(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
        a();
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(EN en) {
        en.a.accept("-dontoptimize");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(EN en) {
        en.a.accept("-dontobfuscate");
    }

    public static EN a(Consumer<String> consumer) {
        final EN en = new EN();
        en.a = consumer;
        en.d = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EN.a(this.f$0);
            }
        };
        en.e = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EN.b(this.f$0);
            }
        };
        en.f = new Runnable() { // from class: com.android.tools.r8.internal.EN$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EN.c(this.f$0);
            }
        };
        return en.b(en.a);
    }

    public EN b(Consumer<String> consumer) {
        this.c = consumer;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(EN en) {
        en.a.accept("-dontshrink");
    }
}
