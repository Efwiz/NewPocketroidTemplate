package com.android.tools.r8;

import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3870b;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class BaseCommand {
    static final /* synthetic */ boolean d = true;
    private final boolean a;
    private final boolean b;
    private final C3945p c;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class Builder<C extends BaseCommand, B extends Builder<C, B>> {
        private final O2 a;
        private boolean b;
        private boolean c;
        private final C3945p.a d;
        ArrayList e;

        Builder() {
            this(C3945p.b());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(com.android.tools.r8.utils.N n) {
            e();
            n.a(c());
            this.a.a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(Collection collection) {
            this.d.g(collection);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d(Collection collection) {
            try {
                this.d.h(collection);
            } catch (NoSuchFileException e) {
                this.a.error(new StringDiagnostic("Main-dex-list file does not exist", new PathOrigin(Paths.get(e.getFile(), new String[0]))));
            }
        }

        public B addClassProgramData(final byte[] bArr, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(bArr, origin);
                }
            });
            return (B) d();
        }

        public B addClasspathFiles(final Path... pathArr) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(pathArr);
                }
            });
            return (B) d();
        }

        public B addClasspathResourceProvider(final ClassFileResourceProvider classFileResourceProvider) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(classFileResourceProvider);
                }
            });
            return (B) d();
        }

        B addDexProgramData(final byte[] bArr, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(bArr, origin);
                }
            });
            return (B) d();
        }

        public B addLibraryFiles(Path... pathArr) {
            addLibraryFiles(Arrays.asList(pathArr));
            return (B) d();
        }

        public B addLibraryResourceProvider(final ClassFileResourceProvider classFileResourceProvider) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(classFileResourceProvider);
                }
            });
            return (B) d();
        }

        public B addMainDexClasses(final String... strArr) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(strArr);
                }
            });
            return (B) d();
        }

        public B addMainDexListFiles(final Path... pathArr) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(pathArr);
                }
            });
            return (B) d();
        }

        public B addProgramFiles(Path... pathArr) {
            addProgramFiles(Arrays.asList(pathArr));
            return (B) d();
        }

        public B addProgramResourceProvider(ProgramResourceProvider programResourceProvider) {
            this.d.a(programResourceProvider);
            return (B) d();
        }

        O2 b() {
            return this.a;
        }

        public final C build() throws CompilationFailedException {
            final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(null);
            R0.a(this.a, new R0.a() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda5
                @Override // com.android.tools.r8.utils.R0.a
                public final void run() {
                    this.f$0.a(n);
                }
            });
            return (C) n.a();
        }

        abstract BaseCommand c();

        abstract Builder d();

        void e() {
        }

        public void error(Diagnostic diagnostic) {
            this.a.error(diagnostic);
        }

        public RuntimeException fatalError(Diagnostic diagnostic) {
            this.a.a(diagnostic);
            throw null;
        }

        public boolean isPrintHelp() {
            return this.b;
        }

        public boolean isPrintVersion() {
            return this.c;
        }

        public B setPrintHelp(boolean z) {
            this.b = z;
            return (B) d();
        }

        public B setPrintVersion(boolean z) {
            this.c = z;
            return (B) d();
        }

        Builder(DiagnosticsHandler diagnosticsHandler) {
            this(C3945p.a(new O2(diagnosticsHandler)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(ClassFileResourceProvider classFileResourceProvider) {
            a().b(classFileResourceProvider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Path path = (Path) it.next();
                try {
                    this.d.d(path);
                    this.e.add(path);
                } catch (C1367Tb e) {
                    this.a.error(new ExceptionDiagnostic(e, new C0820h(path)));
                }
            }
        }

        Builder(C3945p.a aVar) {
            this.b = false;
            this.c = false;
            this.e = new ArrayList();
            this.d = aVar;
            this.a = aVar.c();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Path path = (Path) it.next();
                try {
                    this.d.c(path);
                } catch (C1367Tb e) {
                    this.a.error(new ExceptionDiagnostic(e, new C0591g(path)));
                }
            }
        }

        public B addClasspathFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(collection);
                }
            });
            return (B) d();
        }

        public B addLibraryFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(collection);
                }
            });
            return (B) d();
        }

        public B addMainDexClasses(final Collection<String> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.c(collection);
                }
            });
            return (B) d();
        }

        public B addProgramFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.e(collection);
                }
            });
            return (B) d();
        }

        C3945p.a a() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Path[] pathArr) {
            Arrays.stream(pathArr).forEach(new BaseCommand$Builder$$ExternalSyntheticLambda1(this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Collection collection) {
            collection.forEach(new BaseCommand$Builder$$ExternalSyntheticLambda1(this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final Path path) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(path);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(ClassFileResourceProvider classFileResourceProvider) {
            a().a(classFileResourceProvider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(byte[] bArr, Origin origin) {
            this.d.a(bArr, origin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(Path path) {
            a().a(path);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String[] strArr) {
            this.d.a(strArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(byte[] bArr, Origin origin) {
            this.d.b(bArr, origin);
        }

        public B addMainDexListFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.d(collection);
                }
            });
            return (B) d();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(boolean z) {
            this.d.a(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Path[] pathArr) {
            try {
                C3945p.a aVar = this.d;
                aVar.getClass();
                aVar.h(Arrays.asList(pathArr));
            } catch (NoSuchFileException e) {
                this.a.error(new StringDiagnostic("Main-dex-list file does not exist", new PathOrigin(Paths.get(e.getFile(), new String[0]))));
            }
        }

        final void a(Runnable runnable) {
            try {
                runnable.run();
            } catch (C1367Tb e) {
                this.a.error(e.c());
            } catch (C3870b unused) {
            }
        }

        final void b(final boolean z) {
            a(new Runnable() { // from class: com.android.tools.r8.BaseCommand$Builder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(z);
                }
            });
        }
    }

    BaseCommand(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
        this.c = null;
    }

    C3945p a() {
        return this.c;
    }

    abstract A1 b();

    public boolean isPrintHelp() {
        return this.a;
    }

    public boolean isPrintVersion() {
        return this.b;
    }

    BaseCommand(C3945p c3945p) {
        if (!d && c3945p == null) {
            throw new AssertionError();
        }
        this.c = c3945p;
        this.a = false;
        this.b = false;
    }
}
