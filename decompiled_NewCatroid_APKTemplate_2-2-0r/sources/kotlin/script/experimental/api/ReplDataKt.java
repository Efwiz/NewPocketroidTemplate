package kotlin.script.experimental.api;

import com.reandroid.apkeditor.refactor.RefactorUtil;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\";\u0010\u0003\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0005j\u0002`\t0\u0004*\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\"\u0015\u0010\u000f\u001a\u00020\u0010*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"%\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0004*\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0015\u0010\f*.\u0010\u0019\"\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00052\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0005¨\u0006\u001a"}, d2 = {"REPL_SNIPPET_FIRST_GEN", "", "REPL_SNIPPET_FIRST_NO", "makeSnippetIdentifier", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lkotlin/script/experimental/api/ReplSnippetId;", "", "Lkotlin/script/experimental/api/MakeSnippetIdentifier;", "Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationKeys;", "getMakeSnippetIdentifier", "(Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "makeSnippetIdentifier$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "repl", "Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getRepl", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationBuilder;", "resultFieldPrefix", "getResultFieldPrefix", "resultFieldPrefix$delegate", "makeDefaultSnippetIdentifier", "snippetId", "MakeSnippetIdentifier", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplDataKt {
    public static final int REPL_SNIPPET_FIRST_GEN = 1;
    public static final int REPL_SNIPPET_FIRST_NO = 1;
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ReplDataKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "resultFieldPrefix", "getResultFieldPrefix(Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ReplDataKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "makeSnippetIdentifier", "getMakeSnippetIdentifier(Lkotlin/script/experimental/api/ReplScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate resultFieldPrefix$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) RefactorUtil.RES_DIR, false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate makeSnippetIdentifier$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) new Function2<ScriptCompilationConfiguration, ReplSnippetId, String>() { // from class: kotlin.script.experimental.api.ReplDataKt$makeSnippetIdentifier$2
        @Override // kotlin.jvm.functions.Function2
        public final String invoke(ScriptCompilationConfiguration scriptCompilationConfiguration, ReplSnippetId snippetId) {
            Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(snippetId, "snippetId");
            return ReplDataKt.makeDefaultSnippetIdentifier(snippetId);
        }
    }, false, 2, (Object) null);

    public static final ReplScriptCompilationConfigurationBuilder getRepl(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return new ReplScriptCompilationConfigurationBuilder();
    }

    public static final PropertiesCollection.Key<String> getResultFieldPrefix(ReplScriptCompilationConfigurationKeys replScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(replScriptCompilationConfigurationKeys, "<this>");
        return resultFieldPrefix$delegate.getValue(replScriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<Function2<ScriptCompilationConfiguration, ReplSnippetId, String>> getMakeSnippetIdentifier(ReplScriptCompilationConfigurationKeys replScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(replScriptCompilationConfigurationKeys, "<this>");
        return makeSnippetIdentifier$delegate.getValue(replScriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final String makeDefaultSnippetIdentifier(ReplSnippetId snippetId) {
        Intrinsics.checkNotNullParameter(snippetId, "snippetId");
        return "Line_" + snippetId.getNo() + (snippetId.getGeneration() > 1 ? "_gen_" + snippetId.getGeneration() : "");
    }
}
