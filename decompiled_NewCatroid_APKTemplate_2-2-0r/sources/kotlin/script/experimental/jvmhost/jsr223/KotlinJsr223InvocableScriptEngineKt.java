package kotlin.script.experimental.jvmhost.jsr223;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import org.jetbrains.kotlin.utils.ParametersMapKt;

/* JADX INFO: compiled from: KotlinJsr223InvocableScriptEngine.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010 \n\u0000\u001aF\u0010\u0000\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003\u0018\u00010\u0001*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\bH\u0002¨\u0006\t"}, d2 = {"findMapping", "Lkotlin/Pair;", "Lkotlin/reflect/KFunction;", "", "Lkotlin/reflect/KParameter;", "", "", "args", "", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinJsr223InvocableScriptEngineKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair<KFunction<?>, Map<KParameter, Object>> findMapping(Iterable<? extends KFunction<?>> iterable, List<? extends Object> list) {
        for (KFunction<?> kFunction : iterable) {
            Map<KParameter, Object> mapTryCreateCallableMapping = ParametersMapKt.tryCreateCallableMapping(kFunction, list);
            if (mapTryCreateCallableMapping != null) {
                return TuplesKt.to(kFunction, mapTryCreateCallableMapping);
            }
        }
        return null;
    }
}
