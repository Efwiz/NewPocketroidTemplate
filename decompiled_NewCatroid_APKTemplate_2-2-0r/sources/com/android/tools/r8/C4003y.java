package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C0906Cq;
import com.android.tools.r8.internal.C2091gb;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3945p;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;
import org.jf.dexlib2.dexbacked.raw.ItemType;

/* JADX INFO: renamed from: com.android.tools.r8.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C4003y {

    /* JADX INFO: renamed from: com.android.tools.r8.y$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends BaseCommand {
        static final String e = String.join("\n", AbstractC1639am.a("Usage: dexsegments [options] <input-files>", " where <input-files> are dex files", "  --version               # Print the version of r8.", "  --help                  # Print this message."));

        /* JADX INFO: renamed from: com.android.tools.r8.y$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class C0024a extends BaseCommand.Builder<a, C0024a> {
            @Override // com.android.tools.r8.BaseCommand.Builder
            protected final BaseCommand c() {
                return isPrintHelp() ? new a(isPrintHelp()) : new a(a().a());
            }

            @Override // com.android.tools.r8.BaseCommand.Builder
            final BaseCommand.Builder d() {
                return this;
            }
        }

        private a(C3945p c3945p) {
            super(c3945p);
        }

        public static C0024a c() {
            return new C0024a();
        }

        @Override // com.android.tools.r8.BaseCommand
        final A1 b() {
            return new A1();
        }

        private a(boolean z) {
            super(z, false);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.y$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b {
        private int a = 0;

        b() {
        }

        public int a() {
            return this.a;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Map<Integer, b> a(a aVar) throws Throwable {
        if (aVar.isPrintHelp()) {
            System.out.println(a.e);
            return null;
        }
        C3945p c3945pA = aVar.a();
        C0906Cq c0906Cq = new C0906Cq();
        int[] iArr = {8197, 0, ItemType.DEBUG_INFO_ITEM, 4, 4098, 1, 4096, 3, 5, 2, 8194, 8192, 4097, ItemType.ANNOTATION_DIRECTORY_ITEM, 8196, 4099, 6};
        for (int i = 0; i < 17; i++) {
            c0906Cq.computeIfAbsent(Integer.valueOf(iArr[i]), new Function() { // from class: com.android.tools.r8.y$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C4003y.a((Integer) obj);
                }
            });
        }
        C2091gb c2091gbC = C2091gb.c();
        try {
            for (ProgramResource programResource : c3945pA.d()) {
                if (programResource.getKind() == ProgramResource.Kind.DEX) {
                    for (com.android.tools.r8.dex.D d : com.android.tools.r8.dex.B.a(programResource.getOrigin(), (InputStream) c2091gbC.a(programResource.getByteStream()))) {
                        ((b) c0906Cq.computeIfAbsent(Integer.valueOf(d.a), new Function() { // from class: com.android.tools.r8.y$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return C4003y.b((Integer) obj);
                            }
                        })).a += d.b;
                    }
                }
            }
            c2091gbC.close();
            return c0906Cq;
        } catch (Throwable th) {
            try {
                c2091gbC.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ b b(Integer num) {
        return new b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ b a(Integer num) {
        return new b();
    }
}
