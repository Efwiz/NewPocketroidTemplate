package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3868a2;
import com.android.tools.r8.utils.C3873b2;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.y3;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ExtractMarker {
    private static void a(MarkerInfoConsumer markerInfoConsumer, O2 o2, Origin origin, final C3945p.a aVar) {
        final ArrayList arrayList = new ArrayList();
        try {
            R0.a(o2, new R0.a() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.R0.a
                public final void run() {
                    ExtractMarker.a(arrayList, aVar);
                }
            });
            arrayList.sort(Comparator.comparing(new ExtractMarker$$ExternalSyntheticLambda1()));
            markerInfoConsumer.acceptMarkerInfo(new C3868a2(V1.a((Collection) arrayList, new Function() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new C3873b2((com.android.tools.r8.dex.U) obj);
                }
            }), origin));
        } catch (CompilationFailedException e) {
            throw new E(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(MarkerInfoConsumer markerInfoConsumer, O2 o2, byte[] bArr, Origin origin) {
        a(markerInfoConsumer, o2, origin, C3945p.b().a(bArr, origin));
    }

    public static void main(final String[] strArr) throws Exception {
        R0.a(new S0() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                ExtractMarker.a(strArr);
            }
        });
    }

    public static void run(ExtractMarkerCommand extractMarkerCommand) throws CompilationFailedException {
        final MarkerInfoConsumer markerInfoConsumer = extractMarkerCommand.getMarkerInfoConsumer();
        final O2 o2 = new O2(extractMarkerCommand.getDiagnosticsHandler());
        try {
            extractMarkerCommand.forEachEntry(new BiConsumer() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ExtractMarker.a(markerInfoConsumer, o2, (Path) obj, (Origin) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ExtractMarker.a(markerInfoConsumer, o2, (byte[]) obj, (Origin) obj2);
                }
            }, new BiConsumer() { // from class: com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ExtractMarker.b(markerInfoConsumer, o2, (byte[]) obj, (Origin) obj2);
                }
            });
        } catch (E e) {
            throw e.a;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, C3945p.a aVar) {
        C3945p c3945pA = aVar.a();
        A1 a1 = new A1();
        a1.v1 = true;
        a1.c(EnumC3891f.P);
        list.addAll(new C0408c(c3945pA, a1, new y3("ExtractMarker")).a().e.h());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(MarkerInfoConsumer markerInfoConsumer, O2 o2, Path path, Origin origin) {
        a(markerInfoConsumer, o2, origin, C3945p.b().d(path));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(MarkerInfoConsumer markerInfoConsumer, O2 o2, byte[] bArr, Origin origin) {
        a(markerInfoConsumer, o2, origin, C3945p.b().b(bArr, origin));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(String[] strArr) throws CompilationFailedException {
        ExtractMarkerCommand extractMarkerCommandBuild = ExtractMarkerCommand.parse(strArr).setMarkerInfoConsumer(new F(System.out)).build();
        if (extractMarkerCommandBuild.isPrintHelp()) {
            System.out.println(ExtractMarkerCommand.g);
        } else {
            run(extractMarkerCommandBuild);
        }
    }
}
