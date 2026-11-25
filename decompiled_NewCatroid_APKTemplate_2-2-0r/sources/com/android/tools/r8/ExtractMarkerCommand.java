package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.C3979v2;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ExtractMarkerCommand {
    static final String g = String.join("\n", AbstractC1639am.a("Usage: extractmarker [options] <input-files>", " where <input-files> are D8 supported input/output files and options are:", "  --help                  # Print this message."));
    private final boolean a;
    private final DiagnosticsHandler b;
    private final MarkerInfoConsumer c;
    private final List d;
    private final List e;
    private final List f;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private boolean a = false;
        private final ArrayList b = new ArrayList();
        private final ArrayList c = new ArrayList();
        private final ArrayList d = new ArrayList();
        private MarkerInfoConsumer e;
        private DiagnosticsHandler f;

        Builder(DiagnosticsHandler diagnosticsHandler) {
            this.f = diagnosticsHandler;
        }

        public Builder addClassProgramData(byte[] bArr, Origin origin) {
            this.d.add(new C3979v2(origin, bArr));
            return this;
        }

        public Builder addDexProgramData(byte[] bArr, Origin origin) {
            this.c.add(new C3979v2(origin, bArr));
            return this;
        }

        public Builder addProgramFiles(Path... pathArr) {
            return addProgramFiles(Arrays.asList(pathArr));
        }

        public ExtractMarkerCommand build() {
            return isPrintHelp() ? new ExtractMarkerCommand(isPrintHelp()) : new ExtractMarkerCommand(this.f, this.e, this.b, this.c, this.d);
        }

        public boolean isPrintHelp() {
            return this.a;
        }

        public Builder setMarkerInfoConsumer(MarkerInfoConsumer markerInfoConsumer) {
            this.e = markerInfoConsumer;
            return this;
        }

        public Builder setPrintHelp(boolean z) {
            this.a = z;
            return this;
        }

        public Builder addProgramFiles(Collection<Path> collection) {
            this.b.addAll(collection);
            return this;
        }
    }

    private ExtractMarkerCommand(DiagnosticsHandler diagnosticsHandler, MarkerInfoConsumer markerInfoConsumer, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
        this.a = false;
        this.b = diagnosticsHandler;
        this.c = markerInfoConsumer;
        this.d = arrayList;
        this.e = arrayList2;
        this.f = arrayList3;
    }

    private static void a(String[] strArr, Builder builder) {
        for (String str : strArr) {
            String strTrim = str.trim();
            if (strTrim.equals("--help")) {
                builder.setPrintHelp(true);
            } else {
                if (strTrim.startsWith("--")) {
                    throw new C1367Tb("Unknown option: " + strTrim);
                }
                builder.addProgramFiles(Paths.get(strTrim, new String[0]));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(BiConsumer biConsumer, C3979v2 c3979v2) {
        biConsumer.accept((byte[]) c3979v2.b(), (Origin) c3979v2.a());
    }

    public static Builder builder() {
        return builder(new G());
    }

    public static Builder parse(String[] strArr) {
        Builder builder = builder();
        a(strArr, builder);
        return builder;
    }

    public void forEachEntry(final BiConsumer<Path, Origin> biConsumer, final BiConsumer<byte[], Origin> biConsumer2, final BiConsumer<byte[], Origin> biConsumer3) {
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.ExtractMarkerCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ExtractMarkerCommand.a(biConsumer, (Path) obj);
            }
        });
        this.e.forEach(new Consumer() { // from class: com.android.tools.r8.ExtractMarkerCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ExtractMarkerCommand.a(biConsumer2, (C3979v2) obj);
            }
        });
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.ExtractMarkerCommand$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ExtractMarkerCommand.b(biConsumer3, (C3979v2) obj);
            }
        });
    }

    public DiagnosticsHandler getDiagnosticsHandler() {
        return this.b;
    }

    public MarkerInfoConsumer getMarkerInfoConsumer() {
        return this.c;
    }

    public boolean isPrintHelp() {
        return this.a;
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    private ExtractMarkerCommand(boolean z) {
        this.a = z;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, Path path) {
        biConsumer.accept(path, new PathOrigin(path));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C3979v2 c3979v2) {
        biConsumer.accept((byte[]) c3979v2.b(), (Origin) c3979v2.a());
    }
}
