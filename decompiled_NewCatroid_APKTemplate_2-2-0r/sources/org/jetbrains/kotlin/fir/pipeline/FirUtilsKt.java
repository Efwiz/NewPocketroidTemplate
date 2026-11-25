package org.jetbrains.kotlin.fir.pipeline;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.KtSourceFileLinesMappingKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.lightTree.LightTree2Fir;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl;
import org.jetbrains.kotlin.fir.session.SourcesToPathsMapper;
import org.jetbrains.kotlin.fir.session.SourcesToPathsMapperKt;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: firUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b\u001a@\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0007\u001a\u00020\b2\u001a\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\r\u001a$\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00052\u0006\u0010\u0007\u001a\u00020\b\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\n\u001aH\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005*\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00162\u001c\b\u0002\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u0018¨\u0006\u0019"}, d2 = {"buildResolveAndCheckFirFromKtFiles", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "ktFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "buildResolveAndCheckFirViaLightTree", "", "Lorg/jetbrains/kotlin/KtSourceFile;", "countFilesAndLines", "Lkotlin/reflect/KFunction2;", "", "", "resolveAndCheckFir", "firFiles", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "buildFirFromKtFiles", "buildFirViaLightTree", "files", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "reportFilesAndLines", "Lkotlin/Function2;", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirUtilsKt {
    public static /* synthetic */ List buildFirViaLightTree$default(FirSession firSession, Collection collection, DiagnosticReporter diagnosticReporter, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            diagnosticReporter = null;
        }
        if ((i & 4) != 0) {
            function2 = null;
        }
        return buildFirViaLightTree(firSession, collection, diagnosticReporter, function2);
    }

    public static final List<FirFile> buildFirViaLightTree(FirSession firSession, Collection<? extends KtSourceFile> files, DiagnosticReporter diagnosticReporter, Function2<? super Integer, ? super Integer, Unit> function2) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        FirProvider firProvider = FirProviderKt.getFirProvider(firSession);
        Intrinsics.checkNotNull(firProvider, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl");
        FirProviderImpl firProviderImpl = (FirProviderImpl) firProvider;
        SourcesToPathsMapper sourcesToPathsMapper = SourcesToPathsMapperKt.getSourcesToPathsMapper(firSession);
        LightTree2Fir lightTree2Fir = new LightTree2Fir(firSession, firProviderImpl.getKotlinScopeProvider(), diagnosticReporter);
        int linesCount = 0;
        boolean z = function2 != null;
        Collection<? extends KtSourceFile> collection = files;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KtSourceFile ktSourceFile : collection) {
            InputStreamReader inputStreamReader = new InputStreamReader(ktSourceFile.getContentsAsStream(), Charsets.UTF_8);
            try {
                Pair<CharSequence, KtSourceFileLinesMapping> sourceFileWithMapping = KtSourceFileLinesMappingKt.readSourceFileWithMapping(inputStreamReader);
                CloseableKt.closeFinally(inputStreamReader, null);
                CharSequence charSequenceComponent1 = sourceFileWithMapping.component1();
                KtSourceFileLinesMapping ktSourceFileLinesMappingComponent2 = sourceFileWithMapping.component2();
                if (z) {
                    linesCount += ktSourceFileLinesMappingComponent2.getLinesCount();
                }
                FirFile firFileBuildFirFile = lightTree2Fir.buildFirFile(charSequenceComponent1, ktSourceFile, ktSourceFileLinesMappingComponent2);
                firProviderImpl.recordFile(firFileBuildFirFile);
                KtSourceElement source = firFileBuildFirFile.getSource();
                Intrinsics.checkNotNull(source);
                String path = ktSourceFile.getPath();
                if (path == null) {
                    path = ktSourceFile.getName();
                }
                sourcesToPathsMapper.registerFileSource(source, path);
                arrayList.add(firFileBuildFirFile);
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
        if (function2 != null) {
            function2.invoke(Integer.valueOf(files.size()), Integer.valueOf(linesCount));
        }
        return arrayList2;
    }

    public static final List<FirFile> buildFirFromKtFiles(FirSession firSession, Collection<? extends KtFile> ktFiles) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        FirProvider firProvider = FirProviderKt.getFirProvider(firSession);
        Intrinsics.checkNotNull(firProvider, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl");
        FirProviderImpl firProviderImpl = (FirProviderImpl) firProvider;
        PsiRawFirBuilder psiRawFirBuilder = new PsiRawFirBuilder(firSession, firProviderImpl.getKotlinScopeProvider(), null, 4, null);
        Collection<? extends KtFile> collection = ktFiles;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            FirFile firFileBuildFirFile = psiRawFirBuilder.buildFirFile((KtFile) it.next());
            firProviderImpl.recordFile(firFileBuildFirFile);
            arrayList.add(firFileBuildFirFile);
        }
        return arrayList;
    }

    public static final ModuleCompilerAnalyzedOutput buildResolveAndCheckFirFromKtFiles(FirSession session, List<? extends KtFile> ktFiles, BaseDiagnosticsCollector diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        return resolveAndCheckFir(session, buildFirFromKtFiles(session, ktFiles), diagnosticsReporter);
    }

    public static final ModuleCompilerAnalyzedOutput resolveAndCheckFir(FirSession session, List<? extends FirFile> firFiles, BaseDiagnosticsCollector diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(firFiles, "firFiles");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Pair<ScopeSession, List<FirFile>> pairRunResolution = AnalyseKt.runResolution(session, firFiles);
        ScopeSession scopeSessionComponent1 = pairRunResolution.component1();
        List<FirFile> listComponent2 = pairRunResolution.component2();
        AnalyseKt.runCheckers(session, scopeSessionComponent1, listComponent2, diagnosticsReporter);
        return new ModuleCompilerAnalyzedOutput(session, scopeSessionComponent1, listComponent2);
    }

    public static final ModuleCompilerAnalyzedOutput buildResolveAndCheckFirViaLightTree(FirSession session, Collection<? extends KtSourceFile> ktFiles, BaseDiagnosticsCollector diagnosticsReporter, KFunction<Unit> kFunction) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        return resolveAndCheckFir(session, buildFirViaLightTree(session, ktFiles, diagnosticsReporter, (Function2) kFunction), diagnosticsReporter);
    }
}
