package kotlin.script.experimental.api;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0013\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H¦@¢\u0006\u0002\u0010\u0018R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\"\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/CompiledScript;", "", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "otherScripts", "", "getOtherScripts", "()Ljava/util/List;", "resultField", "Lkotlin/Pair;", "", "Lkotlin/script/experimental/api/KotlinType;", "getResultField", "()Lkotlin/Pair;", "sourceLocationId", "getSourceLocationId", "()Ljava/lang/String;", "getClass", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/reflect/KClass;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompiledScript {
    Object getClass(ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends KClass<?>>> continuation);

    ScriptCompilationConfiguration getCompilationConfiguration();

    default Pair<String, KotlinType> getResultField() {
        return null;
    }

    default String getSourceLocationId() {
        return null;
    }

    default List<CompiledScript> getOtherScripts() {
        return CollectionsKt.emptyList();
    }
}
