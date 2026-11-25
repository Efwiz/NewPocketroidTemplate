package org.jetbrains.kotlin.fir.pipeline;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.collectors.FirDiagnosticsCollector;
import org.jetbrains.kotlin.fir.analysis.collectors.SimpleDiagnosticsCollector;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirTotalResolveProcessor;

/* JADX INFO: compiled from: analyse.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u00032\u0006\u0010\t\u001a\u00020\n\u001a*\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\f*\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003¨\u0006\r"}, d2 = {"runCheckers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "firFiles", "reporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "runResolution", "Lkotlin/Pair;", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnalyseKt {
    public static final Pair<ScopeSession, List<FirFile>> runResolution(FirSession firSession, List<? extends FirFile> firFiles) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(firFiles, "firFiles");
        FirTotalResolveProcessor firTotalResolveProcessor = new FirTotalResolveProcessor(firSession);
        firTotalResolveProcessor.process(firFiles);
        return TuplesKt.to(firTotalResolveProcessor.getScopeSession(), firFiles);
    }

    public static final Map<FirFile, List<KtDiagnostic>> runCheckers(FirSession firSession, ScopeSession scopeSession, List<? extends FirFile> firFiles, BaseDiagnosticsCollector reporter) {
        List<KtDiagnostic> listEmptyList;
        String path;
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(firFiles, "firFiles");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        SimpleDiagnosticsCollector simpleDiagnosticsCollectorCreate = FirDiagnosticsCollector.INSTANCE.create(firSession, scopeSession);
        simpleDiagnosticsCollectorCreate.collectDiagnosticsInSettings(reporter);
        for (FirFile firFile : firFiles) {
            try {
                simpleDiagnosticsCollectorCreate.collectDiagnostics(firFile, reporter);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(firFile.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile, th);
                throw null;
            }
        }
        List<? extends FirFile> list = firFiles;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            KtSourceFile sourceFile = ((FirFile) obj).getSourceFile();
            if (sourceFile == null || (path = sourceFile.getPath()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                listEmptyList = reporter.getDiagnosticsByFilePath().get(path);
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
            }
            linkedHashMap2.put(obj, listEmptyList);
        }
        return linkedHashMap;
    }
}
