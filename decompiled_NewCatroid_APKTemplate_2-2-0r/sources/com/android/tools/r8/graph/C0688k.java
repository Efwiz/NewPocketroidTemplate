package com.android.tools.r8.graph;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.dex.C0563k$$ExternalSyntheticLambda21;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.graph.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0688k implements DataResourceProvider.Visitor {
    private final FeatureSplit a;
    final /* synthetic */ C0695l.a b;

    public C0688k(C0695l.a aVar, FeatureSplit featureSplit) {
        this.b = aVar;
        this.a = featureSplit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(String str) {
        int iIndexOf = str.indexOf(35);
        return iIndexOf > -1 ? str.substring(0, iIndexOf) : str;
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataDirectoryResource dataDirectoryResource) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(FeatureSplit featureSplit) {
        return new ArrayList();
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataEntryResource dataEntryResource) {
        try {
            String name2 = dataEntryResource.getName();
            if (name2.startsWith("META-INF/services/")) {
                String strSubstring = name2.substring(18);
                if (C3970u0.E(strSubstring)) {
                    C0767v2 c0767v2E = this.b.a.a().e(C3970u0.H(strSubstring));
                    if (this.b.a.q() && this.b.a.f().d(c0767v2E) == null) {
                        String str = "Unexpected reference to missing service class: META-INF/services/" + c0767v2E.r0() + ".";
                        Origin origin = dataEntryResource.getOrigin();
                        if (!this.b.a.u().a(c0767v2E)) {
                            this.b.b.e.warning(new StringDiagnostic(str, origin));
                        }
                    }
                    a(new String(V5.a(dataEntryResource.getByteStream()), Charset.defaultCharset()), dataEntryResource.getOrigin(), c0767v2E, (List) ((Map) this.b.c.computeIfAbsent(c0767v2E, new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C0688k.a((C0767v2) obj);
                        }
                    })).computeIfAbsent(this.a, new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C0688k.a((FeatureSplit) obj);
                        }
                    }));
                }
            }
        } catch (ResourceException | IOException e) {
            throw new C1367Tb(e.getMessage(), e);
        }
    }

    private void a(String str, final Origin origin, final C0767v2 c0767v2, final List list) {
        Stream map = com.android.tools.r8.utils.Y2.f(str).stream().map(new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((String) obj).trim();
            }
        }).map(new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((String) obj);
            }
        }).filter(new Predicate() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0688k.a((String) obj);
            }
        }).filter(new Predicate() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3970u0.E((String) obj);
            }
        }).map(new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3970u0.H((String) obj);
            }
        });
        final C0722p1 c0722p1A = this.b.a.a();
        Objects.requireNonNull(c0722p1A);
        Stream streamFilter = map.map(new Function() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return c0722p1A.e((String) obj);
            }
        }).filter(new Predicate() { // from class: com.android.tools.r8.graph.k$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0767v2, origin, list, (C0767v2) obj);
            }
        });
        Objects.requireNonNull(list);
        streamFilter.forEach(new C0563k$$ExternalSyntheticLambda21(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str) {
        return !str.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0767v2 c0767v2, Origin origin, List list, C0767v2 c0767v3) {
        if (!c0767v3.O0()) {
            String str = "Unexpected service implementation found in META-INF/services/" + c0767v2.r0() + ": " + c0767v3.r0() + ".";
            if (this.b.a.u().a(c0767v3)) {
                return false;
            }
            this.b.b.e.warning(new StringDiagnostic(str, origin));
            return false;
        }
        if (this.b.a.q() && this.b.a.f().d(c0767v3) == null) {
            String str2 = "Unexpected reference to missing service implementation class in META-INF/services/" + c0767v2.r0() + ": " + c0767v3.r0() + ".";
            if (!this.b.a.u().a(c0767v3)) {
                this.b.b.e.warning(new StringDiagnostic(str2, origin));
            }
        }
        return !list.contains(c0767v3);
    }
}
