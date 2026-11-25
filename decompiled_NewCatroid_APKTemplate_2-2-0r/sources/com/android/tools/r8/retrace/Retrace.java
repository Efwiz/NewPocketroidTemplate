package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.Version;
import com.android.tools.r8.internal.AbstractC2592n9;
import com.android.tools.r8.internal.C2542mW;
import com.android.tools.r8.internal.C2704oT;
import com.android.tools.r8.internal.C2780pT;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import com.android.tools.r8.utils.AbstractC3948p2;
import com.android.tools.r8.utils.C3943o2;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.G3;
import com.android.tools.r8.utils.N;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.U1;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.y3;
import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Retrace<T, ST extends StackTraceElementProxy<T, ST>> {
    public static final String USAGE_MESSAGE = Y2.b("Usage: retrace <proguard-map> [stack-trace-file] [--regex <regexp>, --verbose, --info, --quiet, --verify-mapping-file-hash]", "  where <proguard-map> is an r8 generated mapping file.");
    static final /* synthetic */ boolean e = true;
    private final StackTraceLineParser a;
    private final MappingSupplier b;
    private final DiagnosticsHandler c;
    protected final boolean d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder<T, ST extends StackTraceElementProxy<T, ST>> {
        private StackTraceLineParser a;
        private MappingSupplier b;
        private DiagnosticsHandler c;
        protected boolean d;

        public Retrace<T, ST> build() {
            return new Retrace<>(this.a, this.b, this.c, this.d);
        }

        public Builder<T, ST> setDiagnosticsHandler(DiagnosticsHandler diagnosticsHandler) {
            this.c = diagnosticsHandler;
            return this;
        }

        public Builder<T, ST> setMappingSupplier(MappingSupplier<?> mappingSupplier) {
            this.b = mappingSupplier;
            return this;
        }

        public Builder<T, ST> setStackTraceLineParser(StackTraceLineParser<T, ST> stackTraceLineParser) {
            this.a = stackTraceLineParser;
            return this;
        }

        public Builder<T, ST> setVerbose(boolean z) {
            this.d = z;
            return this;
        }
    }

    Retrace(StackTraceLineParser stackTraceLineParser, MappingSupplier mappingSupplier, DiagnosticsHandler diagnosticsHandler, boolean z) {
        this.a = stackTraceLineParser;
        this.b = mappingSupplier;
        this.c = diagnosticsHandler;
        this.d = z;
    }

    private static void a(String[] strArr, final DiagnosticsHandler diagnosticsHandler) {
        C3943o2 c3943o2 = new C3943o2(strArr);
        RetraceCommand.Builder builder = RetraceCommand.builder(diagnosticsHandler);
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            if (c3943o2.a() == null) {
                if (!z) {
                    ((d) diagnosticsHandler).error(new StringDiagnostic("Mapping file not specified"));
                    throw new C2780pT();
                }
                if (!z2) {
                    if (!z3) {
                        System.out.println("Waiting for stack-trace input...");
                    }
                    Scanner scanner = new Scanner(new InputStreamReader(System.in, AbstractC2592n9.a));
                    ArrayList arrayList = new ArrayList();
                    while (scanner.hasNext()) {
                        arrayList.add(scanner.nextLine());
                    }
                    builder.setStackTrace(arrayList);
                    break;
                }
                break;
            }
            String strB = null;
            if (AbstractC3948p2.a(c3943o2, "--help") != null || AbstractC3948p2.a(c3943o2, "--version") != null) {
                builder = null;
                break;
            }
            if (AbstractC3948p2.a(c3943o2, "--info") == null) {
                if (AbstractC3948p2.a(c3943o2, "--verbose") != null) {
                    builder.setVerbose(true);
                } else if (AbstractC3948p2.a(c3943o2, "--quiet") != null) {
                    z3 = true;
                } else {
                    String strA = c3943o2.a();
                    if (strA.equals("--regex") || strA.equals("r")) {
                        strB = c3943o2.b();
                        if (strB == null) {
                            throw new RuntimeException(String.format("Missing argument for '%s'.", strA));
                        }
                        c3943o2.b();
                    } else if (strA.startsWith("--regex") && strA.charAt(7) == '=') {
                        c3943o2.b();
                        strB = strA.substring(8);
                    }
                    if (strB == null || strB.isEmpty()) {
                        if (AbstractC3948p2.a(c3943o2, "--verify-mapping-file-hash") != null) {
                            builder.setVerifyMappingFileHash(true);
                        } else if (!z) {
                            String strA2 = c3943o2.a();
                            if (!Files.exists(Paths.get(strA2, new String[0]), new LinkOption[0])) {
                                ((d) diagnosticsHandler).error(new StringDiagnostic(String.format("Could not find mapping file '%s'.", strA2)));
                                throw new C2780pT();
                            }
                            builder.setMappingSupplier(ProguardMappingSupplier.builder().setProguardMapProducer(ProguardMapProducer.fromPath(Paths.get(strA2, new String[0]))).setAllowExperimental(System.getProperty("com.android.tools.r8.experimentalmapping") != null).setLoadAllDefinitions(false).build());
                            c3943o2.b();
                            z = true;
                        } else {
                            if (z2) {
                                d dVar = (d) diagnosticsHandler;
                                dVar.error(new StringDiagnostic(String.format("Too many arguments specified for builder at '%s'", c3943o2.a())));
                                dVar.error(new StringDiagnostic(USAGE_MESSAGE));
                                throw new C2780pT();
                            }
                            try {
                                builder.setStackTrace(Files.readAllLines(Paths.get(c3943o2.a(), new String[0]), AbstractC2592n9.a));
                                c3943o2.b();
                            } catch (IOException e2) {
                                ((d) diagnosticsHandler).error(new ExceptionDiagnostic(e2));
                                throw new C2780pT();
                            }
                        }
                        z2 = true;
                    } else {
                        builder.setRegularExpression(strB);
                    }
                }
            }
        }
        if (builder != null) {
            builder.setRetracedStackTraceConsumer(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Retrace.a(diagnosticsHandler, (List) obj);
                }
            });
            run(builder.build());
        } else if (Arrays.asList(strArr).contains("--version")) {
            System.out.println("Retrace " + Version.getVersionString());
        } else {
            if (!e && !Arrays.asList(strArr).contains("--help")) {
                throw new AssertionError();
            }
            System.out.println("Retrace " + Version.getVersionString());
            System.out.print(USAGE_MESSAGE);
        }
    }

    public static <T, ST extends StackTraceElementProxy<T, ST>> Builder<T, ST> builder() {
        return new Builder<>();
    }

    public static void main(final String... strArr) {
        try {
            new c() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda12
                @Override // com.android.tools.r8.retrace.c
                public final void run() throws RetraceFailedException {
                    Retrace.run(strArr);
                }
            }.run();
        } catch (C2780pT e2) {
            e = e2;
            throw new RuntimeException("Retrace failed", e);
        } catch (RetraceFailedException e3) {
            e = e3;
            throw new RuntimeException("Retrace failed", e);
        } catch (Throwable th) {
            throw new RuntimeException("Retrace failed with an internal error.", th);
        }
    }

    public static void run(RetraceCommand retraceCommand) {
        try {
            y3 y3VarA = y3.a(retraceCommand.printMemory());
            RetraceOptions options = retraceCommand.getOptions();
            MappingSupplier<?> mappingSupplier = options.getMappingSupplier();
            if (retraceCommand.getOptions().isVerifyMappingFileHash()) {
                mappingSupplier.verifyMappingFileHash(options.getDiagnosticsHandler());
                return;
            }
            final DiagnosticsHandler diagnosticsHandler = options.getDiagnosticsHandler();
            C2542mW c2542mW = new C2542mW(options.getRegularExpression());
            StackTraceSupplier stacktraceSupplier = retraceCommand.getStacktraceSupplier();
            RetraceStackTraceContext retraceStackTraceContextEmpty = RetraceStackTraceContext.empty();
            int i = 0;
            while (true) {
                List<String> list = stacktraceSupplier.get();
                if (list == null) {
                    if (retraceCommand.printTimes()) {
                        y3VarA.d();
                    }
                    mappingSupplier.getMapVersions(diagnosticsHandler).forEach(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda11
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Retrace.a(diagnosticsHandler, (com.android.tools.r8.naming.mappinginformation.b) obj);
                        }
                    });
                    return;
                }
                y3VarA.a("Parsing");
                ArrayList arrayList = new ArrayList();
                for (String str : list) {
                    if (str == null) {
                        diagnosticsHandler.error(RetraceInvalidStackTraceLineDiagnostics.createNull(i));
                        throw new C2780pT();
                    }
                    arrayList.add(c2542mW.parse(str));
                    i++;
                }
                y3VarA.c();
                y3VarA.a("Read proguard map");
                StringRetrace stringRetrace = new StringRetrace(c2542mW, mappingSupplier, diagnosticsHandler, options.isVerbose());
                y3VarA.c();
                y3VarA.a("Retracing");
                ResultWithContext<String> resultWithContextRetraceParsed = stringRetrace.retraceParsed(arrayList, retraceStackTraceContextEmpty);
                y3VarA.c();
                y3VarA.a("Report result");
                RetraceStackTraceContext context = resultWithContextRetraceParsed.getContext();
                if (!resultWithContextRetraceParsed.isEmpty() || list.isEmpty()) {
                    retraceCommand.getRetracedStackTraceConsumer().accept(resultWithContextRetraceParsed.getLines());
                }
                y3VarA.c();
                retraceStackTraceContextEmpty = context;
            }
        } catch (InvalidMappingFileException e2) {
            retraceCommand.getOptions().getDiagnosticsHandler().error(new ExceptionDiagnostic(e2));
            throw e2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResultWithContext<List<T>> retraceFrame(T t, RetraceStackTraceContext retraceStackTraceContext) {
        final HashMap map = new HashMap();
        final ArrayList arrayList = new ArrayList();
        final StackTraceElementProxy stackTraceElementProxy = this.a.parse(t);
        stackTraceElementProxy.registerUses(this.b, this.c);
        StackTraceElementProxyRetracer stackTraceElementProxyRetracerCreateDefault = StackTraceElementProxyRetracer.createDefault(this.b.createRetracer(this.c));
        final N n = new N(retraceStackTraceContext);
        stackTraceElementProxyRetracerCreateDefault.retrace(stackTraceElementProxy, retraceStackTraceContext).stream().forEach(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, map, stackTraceElementProxy, n, (RetraceStackTraceElementProxy) obj);
            }
        });
        Collections.sort(arrayList);
        final ArrayList arrayList2 = new ArrayList();
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Retrace.a(arrayList2, map, (RetraceStackTraceElementProxy) obj);
            }
        });
        return C2704oT.a(arrayList2, (RetraceStackTraceContext) n.a());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResultWithContext<T> retraceLine(T t, RetraceStackTraceContext retraceStackTraceContext) {
        final StackTraceElementProxy stackTraceElementProxy = this.a.parse(t);
        stackTraceElementProxy.registerUses(this.b, this.c);
        StackTraceElementProxyRetracer stackTraceElementProxyRetracerCreateDefault = StackTraceElementProxyRetracer.createDefault(this.b.createRetracer(this.c));
        final N n = new N(retraceStackTraceContext);
        return C2704oT.a((List) stackTraceElementProxyRetracerCreateDefault.retrace(stackTraceElementProxy, retraceStackTraceContext).stream().map(new Function() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(n, stackTraceElementProxy, (RetraceStackTraceElementProxy) obj);
            }
        }).collect(Collectors.toList()), (RetraceStackTraceContext) n.a());
    }

    public ResultWithContext<List<List<T>>> retraceStackTrace(List<T> list, RetraceStackTraceContext retraceStackTraceContext) {
        V1.a(list, new U1() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                this.f$0.a(obj, i);
            }
        });
        final StackTraceLineParser stackTraceLineParser = this.a;
        Objects.requireNonNull(stackTraceLineParser);
        return retraceStackTraceParsed(V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return stackTraceLineParser.parse(obj);
            }
        }), retraceStackTraceContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResultWithContext<List<List<T>>> retraceStackTraceParsed(List<ST> list, RetraceStackTraceContext retraceStackTraceContext) {
        final e eVar = new e(this.d);
        list.forEach(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((StackTraceElementProxy) obj);
            }
        });
        final StackTraceElementProxyRetracer stackTraceElementProxyRetracerCreateDefault = StackTraceElementProxyRetracer.createDefault(this.b.createRetracer(this.c));
        final ArrayList arrayList = new ArrayList();
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda7
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(stackTraceElementProxyRetracerCreateDefault, eVar, arrayList, (RetraceStackTraceContext) obj, (StackTraceElementProxy) obj2);
            }
        };
        Iterator<ST> it = list.iterator();
        Object objApply = retraceStackTraceContext;
        while (it.hasNext()) {
            objApply = biFunction.apply(objApply, it.next());
        }
        return C2704oT.a(arrayList, (RetraceStackTraceContext) objApply);
    }

    public static void run(String[] strArr) throws RetraceFailedException {
        String[] strArr2 = new String[strArr.length];
        boolean z = false;
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            if (str != null && str.length() >= 2) {
                if (str.charAt(0) == '-' && str.charAt(1) != '-') {
                    strArr2[i] = Parameters.DEFAULT_OPTION_PREFIXES + str;
                } else {
                    strArr2[i] = str;
                }
                if (strArr2[i].equals("--info")) {
                    z = true;
                }
            } else {
                strArr2[i] = str;
            }
        }
        d dVar = new d(z, new b());
        try {
            a(strArr2, dVar);
        } catch (Throwable th) {
            throw ((RetraceFailedException) R0.a(dVar, th, new G3() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.G3
                public final Object a(Object obj, Object obj2, Object obj3) {
                    return Retrace.a((String) obj, (Throwable) obj2, (Boolean) obj3);
                }
            }, C2780pT.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj, int i) {
        if (obj != null) {
            return;
        }
        this.c.error(RetraceInvalidStackTraceLineDiagnostics.createNull(i));
        throw new C2780pT();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(StackTraceElementProxy stackTraceElementProxy) {
        stackTraceElementProxy.registerUses(this.b, this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RetraceStackTraceContext a(StackTraceElementProxyRetracer stackTraceElementProxyRetracer, final e eVar, List list, RetraceStackTraceContext retraceStackTraceContext, final StackTraceElementProxy stackTraceElementProxy) {
        final ArrayList arrayList = new ArrayList();
        final N n = new N();
        final HashSet hashSet = new HashSet();
        final ArrayList arrayList2 = new ArrayList();
        RetraceStackTraceElementProxyResult<T, ST> retraceStackTraceElementProxyResultRetrace = stackTraceElementProxyRetracer.retrace(stackTraceElementProxy, retraceStackTraceContext);
        retraceStackTraceElementProxyResultRetrace.stream().forEach(new Consumer() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(hashSet, eVar, n, arrayList, arrayList2, stackTraceElementProxy, (RetraceStackTraceElementProxy) obj);
            }
        });
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (RetraceStackTraceElementProxy) ((C3979v2) obj).a();
            }
        }));
        list.add(V1.a((Collection) arrayList, new Function() { // from class: com.android.tools.r8.retrace.Retrace$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (List) ((C3979v2) obj).b();
            }
        }));
        if (arrayList2.isEmpty()) {
            return retraceStackTraceElementProxyResultRetrace.getResultContext();
        }
        return arrayList2.size() == 1 ? (RetraceStackTraceContext) arrayList2.get(0) : RetraceStackTraceContext.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, e eVar, N n, List list, List list2, StackTraceElementProxy stackTraceElementProxy, RetraceStackTraceElementProxy retraceStackTraceElementProxy) {
        if (retraceStackTraceElementProxy.isTopFrame() || !retraceStackTraceElementProxy.hasRetracedClass()) {
            if (set.add(eVar.c(retraceStackTraceElementProxy))) {
                n.a(new ArrayList());
                list.add(C3979v2.a(retraceStackTraceElementProxy, (List) n.a()));
                list2.add(retraceStackTraceElementProxy.getContext());
            } else {
                n.a((Object) null);
            }
        }
        if (n.c()) {
            ((List) n.a()).add(stackTraceElementProxy.toRetracedItem(retraceStackTraceElementProxy, this.d));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, Map map, StackTraceElementProxy stackTraceElementProxy, N n, RetraceStackTraceElementProxy retraceStackTraceElementProxy) {
        if (retraceStackTraceElementProxy.isTopFrame() || !retraceStackTraceElementProxy.hasRetracedClass()) {
            list.add(retraceStackTraceElementProxy);
            map.put(retraceStackTraceElementProxy, new ArrayList());
        }
        ((List) map.get(V1.b(list))).add(stackTraceElementProxy.toRetracedItem(retraceStackTraceElementProxy, this.d));
        n.a(retraceStackTraceElementProxy.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, Map map, RetraceStackTraceElementProxy retraceStackTraceElementProxy) {
        list.add((List) map.get(retraceStackTraceElementProxy));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object a(N n, StackTraceElementProxy stackTraceElementProxy, RetraceStackTraceElementProxy retraceStackTraceElementProxy) {
        n.a(retraceStackTraceElementProxy.getContext());
        return stackTraceElementProxy.toRetracedItem(retraceStackTraceElementProxy, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DiagnosticsHandler diagnosticsHandler, com.android.tools.r8.naming.mappinginformation.b bVar) {
        if (bVar.q().isUnknown()) {
            diagnosticsHandler.warning(RetraceUnknownMapVersionDiagnostic.create(bVar.r()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ RetraceFailedException a(String str, Throwable th, Boolean bool) {
        return new RetraceFailedException(str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DiagnosticsHandler diagnosticsHandler, List list) {
        try {
            PrintStream printStream = new PrintStream((OutputStream) System.out, true, AbstractC2592n9.a.name());
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    printStream.println((String) it.next());
                }
                printStream.close();
            } catch (Throwable th) {
                try {
                    printStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (UnsupportedEncodingException e2) {
            diagnosticsHandler.error(new StringDiagnostic(e2.getMessage()));
        }
    }
}
