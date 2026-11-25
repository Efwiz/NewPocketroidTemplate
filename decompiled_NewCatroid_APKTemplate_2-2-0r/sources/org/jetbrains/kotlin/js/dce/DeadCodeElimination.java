package org.jetbrains.kotlin.js.dce;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectScope;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FixForwardNameReferencesKt;
import org.jetbrains.kotlin.js.parser.ParserUtilsKt;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapError;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParseResult;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParser;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSuccess;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder;
import org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer;
import org.jetbrains.kotlin.js.sourceMap.UtilsKt;
import org.jetbrains.kotlin.js.util.TextOutputImpl;

/* JADX INFO: compiled from: DeadCodeElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000  2\u00020\u0001:\u0001 B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tJ\u000e\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001fR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00070\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\u001a\u001a\f\u0012\b\u0012\u00060\u0019R\u00020\u000b0\u00182\u0010\u0010\u0017\u001a\f\u0012\b\u0012\u00060\u0019R\u00020\u000b0\u0018@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/DeadCodeElimination;", "", "printReachabilityInfo", "", "logConsumer", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/dce/DCELogLevel;", "", "", "(ZLkotlin/jvm/functions/Function2;)V", "context", "Lorg/jetbrains/kotlin/js/dce/Context;", "getContext", "()Lorg/jetbrains/kotlin/js/dce/Context;", "setContext", "(Lorg/jetbrains/kotlin/js/dce/Context;)V", "moduleMapping", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "getModuleMapping", "()Ljava/util/Map;", "reachableNames", "", "<set-?>", "", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "reachableNodes", "getReachableNodes", "()Ljava/lang/Iterable;", "apply", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Companion", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeadCodeElimination {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Context context;
    private final Function2<DCELogLevel, String, Unit> logConsumer;
    private final Map<JsBlock, String> moduleMapping;
    private final boolean printReachabilityInfo;
    private final Set<String> reachableNames;
    private Iterable<Context.Node> reachableNodes;

    /* JADX WARN: Multi-variable type inference failed */
    public DeadCodeElimination(boolean z, Function2<? super DCELogLevel, ? super String, Unit> logConsumer) {
        Intrinsics.checkNotNullParameter(logConsumer, "logConsumer");
        this.printReachabilityInfo = z;
        this.logConsumer = logConsumer;
        this.moduleMapping = new LinkedHashMap();
        this.reachableNames = new LinkedHashSet();
        this.reachableNodes = SetsKt.emptySet();
    }

    public final Map<JsBlock, String> getModuleMapping() {
        return this.moduleMapping;
    }

    public final Iterable<Context.Node> getReachableNodes() {
        return this.reachableNodes;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final void apply(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        Context context = new Context();
        this.context = context;
        Set<JsName> setCollectDefinedNames = CollectUtilsKt.collectDefinedNames(root);
        context.addNodesForLocalVars(setCollectDefinedNames);
        for (JsName jsName : setCollectDefinedNames) {
            Context.Node node = context.getNodes().get(jsName);
            Intrinsics.checkNotNull(node);
            Context.Node globalScope = context.getGlobalScope();
            String ident = jsName.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            node.alias(globalScope.member(ident));
        }
        Analyzer analyzer = new Analyzer(context);
        analyzer.getModuleMapping().putAll(this.moduleMapping);
        root.accept(analyzer);
        AnalysisResult analysisResult = analyzer.getAnalysisResult();
        Function2<DCELogLevel, String, Unit> function2 = this.logConsumer;
        if (!this.printReachabilityInfo) {
            function2 = null;
        }
        ReachabilityTracker reachabilityTracker = new ReachabilityTracker(context, analysisResult, function2);
        root.accept(reachabilityTracker);
        Iterator<String> it = this.reachableNames.iterator();
        while (it.hasNext()) {
            List listSplit$default = StringsKt.split$default((CharSequence) it.next(), new String[]{"."}, false, 0, 6, (Object) null);
            Context.Node globalScope2 = context.getGlobalScope();
            Iterator it2 = listSplit$default.iterator();
            while (it2.hasNext()) {
                globalScope2 = globalScope2.member((String) it2.next());
            }
            reachabilityTracker.reach(globalScope2);
        }
        this.reachableNodes = reachabilityTracker.getReachableNodes();
        new Eliminator(analyzer.getAnalysisResult()).accept(root);
    }

    /* JADX INFO: compiled from: DeadCodeElimination.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/DeadCodeElimination$Companion;", "", "()V", "run", "Lorg/jetbrains/kotlin/js/dce/DeadCodeEliminationResult;", "inputFiles", "", "Lorg/jetbrains/kotlin/js/dce/InputFile;", "rootReachableNames", "", "", "printReachabilityInfo", "", "logConsumer", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/dce/DCELogLevel;", "", "Reporter", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DeadCodeEliminationResult run(Collection<InputFile> inputFiles, Set<String> rootReachableNames, boolean printReachabilityInfo, Function2<? super DCELogLevel, ? super String, Unit> logConsumer) {
            Intrinsics.checkNotNullParameter(inputFiles, "inputFiles");
            Intrinsics.checkNotNullParameter(rootReachableNames, "rootReachableNames");
            Intrinsics.checkNotNullParameter(logConsumer, "logConsumer");
            JsProgram jsProgram = new JsProgram();
            DeadCodeElimination deadCodeElimination = new DeadCodeElimination(printReachabilityInfo, logConsumer);
            Collection<InputFile> collection = inputFiles;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            boolean z = false;
            for (InputFile inputFile : collection) {
                JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
                InputStreamReader inputStreamReader = new InputStreamReader(inputFile.getResource().getReader().invoke(), "UTF-8");
                try {
                    String text = TextStreamsKt.readText(inputStreamReader);
                    CloseableKt.closeFinally(inputStreamReader, null);
                    Reporter reporter = new Reporter(inputFile.getResource().getName(), logConsumer);
                    JsObjectScope scope = jsProgram.getScope();
                    Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
                    List<JsStatement> list = ParserUtilsKt.parse(text, reporter, scope, inputFile.getResource().getName());
                    if (list == null) {
                        Companion companion = DeadCodeElimination.INSTANCE;
                        z = true;
                    } else {
                        InputResource sourceMapResource = inputFile.getSourceMapResource();
                        SourceMapParseResult sourceMapParseResult = sourceMapResource != null ? SourceMapParser.INSTANCE.parse(TextStreamsKt.readText(new InputStreamReader(sourceMapResource.getReader().invoke(), "UTF-8"))) : null;
                        if (sourceMapParseResult instanceof SourceMapError) {
                            logConsumer.invoke(DCELogLevel.WARN, "Error parsing source map file " + inputFile.getSourceMapResource() + ": " + ((SourceMapError) sourceMapParseResult).getMessage());
                        } else if (sourceMapParseResult instanceof SourceMapSuccess) {
                            SourceMapLocationRemapper sourceMapLocationRemapper = new SourceMapLocationRemapper(((SourceMapSuccess) sourceMapParseResult).getValue(), null, 2, null);
                            Iterator<T> it = list.iterator();
                            while (it.hasNext()) {
                                sourceMapLocationRemapper.remap((JsStatement) it.next());
                            }
                        }
                        List<JsStatement> statements = jsCompositeBlock.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                        CollectionsKt.addAll(statements, list);
                        String moduleName = inputFile.getModuleName();
                        if (moduleName != null) {
                            deadCodeElimination.getModuleMapping().put(jsCompositeBlock, moduleName);
                        }
                    }
                    arrayList.add(jsCompositeBlock);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(inputStreamReader, th);
                        throw th2;
                    }
                }
            }
            ArrayList arrayList2 = arrayList;
            if (z) {
                return new DeadCodeEliminationResult(deadCodeElimination.getContext(), SetsKt.emptySet(), DeadCodeEliminationStatus.FAILED);
            }
            List<JsStatement> statements2 = jsProgram.getGlobalBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            ArrayList arrayList3 = arrayList2;
            CollectionsKt.addAll(statements2, arrayList3);
            JsCompositeBlock globalBlock = jsProgram.getGlobalBlock();
            Intrinsics.checkNotNullExpressionValue(globalBlock, "getGlobalBlock(...)");
            FixForwardNameReferencesKt.fixForwardNameReferences(globalBlock);
            CollectionsKt.addAll(deadCodeElimination.reachableNames, rootReachableNames);
            JsCompositeBlock globalBlock2 = jsProgram.getGlobalBlock();
            Intrinsics.checkNotNullExpressionValue(globalBlock2, "getGlobalBlock(...)");
            deadCodeElimination.apply(globalBlock2);
            for (Pair pair : CollectionsKt.zip(collection, arrayList3)) {
                InputFile inputFile2 = (InputFile) pair.component1();
                JsCompositeBlock jsCompositeBlock2 = (JsCompositeBlock) pair.component2();
                File file = new File(inputFile2.getOutputPath() + ".map");
                TextOutputImpl textOutputImpl = new TextOutputImpl();
                File file2 = new File(inputFile2.getOutputPath());
                SourceMap3Builder sourceMap3Builder = new SourceMap3Builder(file2, new DeadCodeElimination$Companion$run$sourceMapBuilder$1(textOutputImpl), "");
                File file3 = new File(inputFile2.getResource().getName());
                File parentFile = file3.exists() ? file3.getParentFile() : new File(".");
                SourceFilePathResolver sourceFilePathResolver = new SourceFilePathResolver(CollectionsKt.emptyList(), file2.getParentFile());
                Intrinsics.checkNotNull(parentFile);
                TextOutputImpl textOutputImpl2 = textOutputImpl;
                jsCompositeBlock2.accept(new JsToStringGenerationVisitor(textOutputImpl2, new SourceMapBuilderConsumer(parentFile, sourceMap3Builder, sourceFilePathResolver, true, true)));
                String strBuild = sourceMap3Builder.build();
                UtilsKt.addSourceMappingURL(textOutputImpl2, file2);
                file2.getParentFile().mkdirs();
                String string = textOutputImpl.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                FilesKt.writeText$default(file2, string, null, 2, null);
                if (inputFile2.getSourceMapResource() != null) {
                    FilesKt.writeText$default(file, strBuild, null, 2, null);
                }
            }
            return new DeadCodeEliminationResult(deadCodeElimination.getContext(), deadCodeElimination.getReachableNodes(), DeadCodeEliminationStatus.OK);
        }

        /* JADX INFO: compiled from: DeadCodeElimination.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/DeadCodeElimination$Companion$Reporter;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ErrorReporter;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "logConsumer", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/dce/DCELogLevel;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function2;)V", "error", CrashHianalyticsData.MESSAGE, "startPosition", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "endPosition", CompilerOptions.WARNING, "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class Reporter implements ErrorReporter {
            private final String fileName;
            private final Function2<DCELogLevel, String, Unit> logConsumer;

            /* JADX WARN: Multi-variable type inference failed */
            public Reporter(String fileName, Function2<? super DCELogLevel, ? super String, Unit> logConsumer) {
                Intrinsics.checkNotNullParameter(fileName, "fileName");
                Intrinsics.checkNotNullParameter(logConsumer, "logConsumer");
                this.fileName = fileName;
                this.logConsumer = logConsumer;
            }

            @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
            public void warning(String message, CodePosition startPosition, CodePosition endPosition) {
                Intrinsics.checkNotNullParameter(message, "message");
                Intrinsics.checkNotNullParameter(startPosition, "startPosition");
                Intrinsics.checkNotNullParameter(endPosition, "endPosition");
                this.logConsumer.invoke(DCELogLevel.WARN, "at " + this.fileName + " (" + (startPosition.getLine() + 1) + ", " + (startPosition.getOffset() + 1) + "): " + message);
            }

            @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
            /* JADX INFO: renamed from: error */
            public void mo6456error(String message, CodePosition startPosition, CodePosition endPosition) {
                Intrinsics.checkNotNullParameter(message, "message");
                Intrinsics.checkNotNullParameter(startPosition, "startPosition");
                Intrinsics.checkNotNullParameter(endPosition, "endPosition");
                this.logConsumer.invoke(DCELogLevel.ERROR, "at " + this.fileName + " (" + (startPosition.getLine() + 1) + ", " + (startPosition.getOffset() + 1) + "): " + message);
            }
        }
    }
}
