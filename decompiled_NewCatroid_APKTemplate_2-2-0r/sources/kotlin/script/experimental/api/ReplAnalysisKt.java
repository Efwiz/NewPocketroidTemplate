package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replAnalysis.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"'\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\b\u001a\u0004\b\u000b\u0010\u0006*\u0016\u0010\r\"\b\u0012\u0004\u0012\u00020\u000e0\u00022\b\u0012\u0004\u0012\u00020\u000e0\u0002¨\u0006\u000f"}, d2 = {"analysisDiagnostics", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/sequences/Sequence;", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "Lkotlin/script/experimental/api/ReplAnalyzerResultKeys;", "getAnalysisDiagnostics", "(Lkotlin/script/experimental/api/ReplAnalyzerResultKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "analysisDiagnostics$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "renderedResultType", "", "getRenderedResultType", "renderedResultType$delegate", "ReplCompletionResult", "Lkotlin/script/experimental/api/SourceCodeCompletionVariant;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplAnalysisKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ReplAnalysisKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "analysisDiagnostics", "getAnalysisDiagnostics(Lkotlin/script/experimental/api/ReplAnalyzerResultKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ReplAnalysisKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "renderedResultType", "getRenderedResultType(Lkotlin/script/experimental/api/ReplAnalyzerResultKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate analysisDiagnostics$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) SequencesKt.emptySequence(), false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate renderedResultType$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);

    public static final PropertiesCollection.Key<Sequence<ScriptDiagnostic>> getAnalysisDiagnostics(ReplAnalyzerResultKeys replAnalyzerResultKeys) {
        Intrinsics.checkNotNullParameter(replAnalyzerResultKeys, "<this>");
        return analysisDiagnostics$delegate.getValue(replAnalyzerResultKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<String> getRenderedResultType(ReplAnalyzerResultKeys replAnalyzerResultKeys) {
        Intrinsics.checkNotNullParameter(replAnalyzerResultKeys, "<this>");
        return renderedResultType$delegate.getValue(replAnalyzerResultKeys, $$delegatedProperties[1]);
    }
}
