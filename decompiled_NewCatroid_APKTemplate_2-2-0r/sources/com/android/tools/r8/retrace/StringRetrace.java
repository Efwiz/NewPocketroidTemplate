package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.C2704oT;
import com.android.tools.r8.internal.NT;
import com.android.tools.r8.internal.YV;
import com.android.tools.r8.utils.U1;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.Y2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class StringRetrace extends Retrace<String, YV> {
    static final /* synthetic */ boolean f = true;

    StringRetrace(StackTraceLineParser stackTraceLineParser, MappingSupplier mappingSupplier, DiagnosticsHandler diagnosticsHandler, boolean z) {
        super(stackTraceLineParser, mappingSupplier, diagnosticsHandler, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, List list2, int i) {
        for (int i2 = 0; i2 < list2.size(); i2++) {
            String str = (String) list2.get(i2);
            if (i2 != 0 || i <= 0) {
                list.add(str);
            } else {
                int iIndexOf = str.indexOf("at ");
                if (iIndexOf < 0) {
                    iIndexOf = Math.max(Y2.b(str), 0);
                }
                list.add(str.substring(0, iIndexOf) + "<OR> " + str.substring(iIndexOf));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(List list, List list2, int i) {
        for (int i2 = 0; i2 < list2.size(); i2++) {
            String str = (String) list2.get(i2);
            if (i2 != 0 || i <= 0) {
                list.add(str);
            } else {
                int iIndexOf = str.indexOf("at ");
                if (iIndexOf < 0) {
                    iIndexOf = Math.max(Y2.b(str), 0);
                }
                list.add(str.substring(0, iIndexOf) + "<OR> " + str.substring(iIndexOf));
            }
        }
    }

    public static StringRetrace create(RetraceOptions retraceOptions) {
        return create(retraceOptions.getMappingSupplier(), retraceOptions.getDiagnosticsHandler(), retraceOptions.getRegularExpression(), retraceOptions.isVerbose());
    }

    public ResultWithContext<String> retrace(List<String> list, RetraceStackTraceContext retraceStackTraceContext) {
        ResultWithContext<List<List<String>>> resultWithContextRetraceStackTrace = retraceStackTrace(list, retraceStackTraceContext);
        final ArrayList arrayList = new ArrayList();
        Iterator<List<List<String>>> it = resultWithContextRetraceStackTrace.getLines().iterator();
        while (it.hasNext()) {
            V1.a(it.next(), new U1() { // from class: com.android.tools.r8.retrace.StringRetrace$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.U1
                public final void accept(Object obj, int i) {
                    StringRetrace.a(arrayList, (List) obj, i);
                }
            });
        }
        return C2704oT.a(arrayList, resultWithContextRetraceStackTrace.getContext());
    }

    public ResultWithContext<String> retraceParsed(List<YV> list, RetraceStackTraceContext retraceStackTraceContext) {
        ResultWithContext<List<List<String>>> resultWithContextRetraceStackTraceParsed = retraceStackTraceParsed(list, retraceStackTraceContext);
        final ArrayList arrayList = new ArrayList();
        Iterator<List<List<String>>> it = resultWithContextRetraceStackTraceParsed.getLines().iterator();
        while (it.hasNext()) {
            V1.a(it.next(), new U1() { // from class: com.android.tools.r8.retrace.StringRetrace$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.U1
                public final void accept(Object obj, int i) {
                    StringRetrace.b(arrayList, (List) obj, i);
                }
            });
        }
        return C2704oT.a(arrayList, resultWithContextRetraceStackTraceParsed.getContext());
    }

    public <E extends Throwable> void retraceSupplier(StreamSupplier<E> streamSupplier, Consumer<String> consumer) throws Throwable {
        RetraceStackTraceContext retraceStackTraceContextEmpty = RetraceStackTraceContext.empty();
        while (true) {
            String next = streamSupplier.getNext();
            if (next == null) {
                return;
            }
            ResultWithContext<String> resultWithContextRetrace = retrace(next, retraceStackTraceContextEmpty);
            RetraceStackTraceContext context = resultWithContextRetrace.getContext();
            resultWithContextRetrace.forEach(consumer);
            retraceStackTraceContextEmpty = context;
        }
    }

    public static StringRetrace create(MappingSupplier<?> mappingSupplier, DiagnosticsHandler diagnosticsHandler, String str, boolean z) {
        return new StringRetrace(StackTraceLineParser.createRegularExpressionParser(str), mappingSupplier, diagnosticsHandler, z);
    }

    private static void a(final Consumer consumer, final List list) {
        if (list.isEmpty()) {
            return;
        }
        final HashSet hashSet = new HashSet();
        V1.a(list, new U1() { // from class: com.android.tools.r8.retrace.StringRetrace$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                StringRetrace.a(hashSet, list, consumer, (List) obj, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, List list, final Consumer consumer, List list2, int i) {
        if (!f && list2.isEmpty()) {
            throw new AssertionError();
        }
        if (set.add((String) list2.get(0))) {
            final boolean z = list2 != list.get(0);
            list2.forEach(new Consumer() { // from class: com.android.tools.r8.retrace.StringRetrace$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StringRetrace.a(z, consumer, (String) obj);
                }
            });
        }
    }

    public ResultWithContext<String> retrace(String str, RetraceStackTraceContext retraceStackTraceContext) {
        ResultWithContext<List<String>> resultWithContextRetraceFrame = retraceFrame(str, retraceStackTraceContext);
        ArrayList arrayList = new ArrayList();
        a(new StringRetrace$$ExternalSyntheticLambda3(arrayList), resultWithContextRetraceFrame.getLines());
        return C2704oT.a(arrayList, resultWithContextRetraceFrame.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(boolean z, Consumer consumer, String str) {
        if (z) {
            int iA = NT.a(str);
            consumer.accept(str.substring(0, iA) + "<OR> " + str.substring(iA));
            return;
        }
        consumer.accept(str);
    }
}
