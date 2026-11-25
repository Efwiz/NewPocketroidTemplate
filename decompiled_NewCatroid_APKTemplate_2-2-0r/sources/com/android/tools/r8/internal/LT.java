package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceUnknownJsonMappingInformationResult;
import com.android.tools.r8.retrace.RetraceUnknownMappingInformationElement;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class LT implements RetraceUnknownJsonMappingInformationResult {
    private final List a;

    private LT(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RetraceUnknownMappingInformationElement a(C2431l10 c2431l10) {
        return new MT(this, c2431l10);
    }

    @Override // com.android.tools.r8.retrace.RetraceUnknownJsonMappingInformationResult
    public final Stream stream() {
        return this.a.stream().map(new Function() { // from class: com.android.tools.r8.internal.LT$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C2431l10) obj);
            }
        });
    }

    static LT a(List list) {
        final C1507Xl c1507XlG = AbstractC1639am.g();
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.LT$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LT.a(c1507XlG, (com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
        return new LT(c1507XlG.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1507Xl c1507Xl, com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        if (eVar instanceof C2431l10) {
            c1507Xl.a(eVar.j());
        }
    }
}
