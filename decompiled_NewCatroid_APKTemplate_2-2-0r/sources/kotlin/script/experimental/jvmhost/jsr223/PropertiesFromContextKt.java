package kotlin.script.experimental.jvmhost.jsr223;

import java.util.HashMap;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptContext;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfigurationRefinementContext;
import kotlin.script.experimental.api.ScriptEvaluationKt;

/* JADX INFO: compiled from: propertiesFromContext.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\u0006\u0010\u0003\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"configureProvidedPropertiesFromJsr223Context", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "context", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationRefinementContext;", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertiesFromContextKt {
    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> configureProvidedPropertiesFromJsr223Context(ScriptConfigurationRefinementContext context) {
        final HashMap map;
        HashMap map2;
        Intrinsics.checkNotNullParameter(context, "context");
        Function0 function0 = (Function0) context.getCompilationConfiguration().get(KotlinJsr223HostConfigurationKt.getGetScriptContext(KotlinJsr223HostConfigurationKt.getJsr223(ScriptCompilationConfiguration.INSTANCE)));
        ScriptContext scriptContext = function0 != null ? (ScriptContext) function0.invoke() : null;
        if (scriptContext != null && Intrinsics.areEqual(context.getCompilationConfiguration().get(KotlinJsr223HostConfigurationKt.getImportAllBindings(KotlinJsr223HostConfigurationKt.getJsr223(ScriptCompilationConfiguration.INSTANCE))), (Object) true)) {
            Map map3 = (Map) context.getCompilationConfiguration().get(ScriptCompilationKt.getProvidedProperties(ScriptCompilationConfiguration.INSTANCE));
            if (map3 == null || (map = MapsKt.toMutableMap(map3)) == null) {
                map = new HashMap();
            }
            Map bindings = scriptContext.getBindings(200);
            if (bindings == null || (map2 = MapsKt.toMutableMap(bindings)) == null) {
                map2 = new HashMap();
            }
            Map bindings2 = scriptContext.getBindings(100);
            if (bindings2 != null) {
                map2.putAll(bindings2);
            }
            for (Map.Entry entry : map2.entrySet()) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                if (!map.containsKey(str) && (value == null || Reflection.getOrCreateKotlinClass(value.getClass()).getQualifiedName() != null)) {
                    Intrinsics.checkNotNull(str);
                    map.put(str, value == null ? new KotlinType((KClass<?>) Reflection.getOrCreateKotlinClass(Object.class), true) : new KotlinType(Reflection.getOrCreateKotlinClass(value.getClass()), false, 2, (DefaultConstructorMarker) null));
                }
            }
            return ErrorHandlingKt.asSuccess$default(new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{context.getCompilationConfiguration()}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.PropertiesFromContextKt.configureProvidedPropertiesFromJsr223Context.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    $receiver.invoke(ScriptCompilationKt.getProvidedProperties($receiver), map);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            }), null, 1, null);
        }
        return ErrorHandlingKt.asSuccess$default(context.getCompilationConfiguration(), null, 1, null);
    }

    public static final ResultWithDiagnostics<ScriptEvaluationConfiguration> configureProvidedPropertiesFromJsr223Context(ScriptEvaluationConfigurationRefinementContext context) {
        final HashMap map;
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        Function0 function0 = (Function0) context.getEvaluationConfiguration().get(KotlinJsr223HostConfigurationKt.getGetScriptContext(KotlinJsr223HostConfigurationKt.getJsr223(ScriptEvaluationConfiguration.INSTANCE)));
        ScriptContext scriptContext = function0 != null ? (ScriptContext) function0.invoke() : null;
        Map map2 = (Map) context.getCompiledScript().getCompilationConfiguration().get(ScriptCompilationKt.getProvidedProperties(ScriptCompilationConfiguration.INSTANCE));
        if (scriptContext != null && map2 != null && !map2.isEmpty()) {
            Map map3 = (Map) context.getEvaluationConfiguration().get(ScriptEvaluationKt.getProvidedProperties(ScriptEvaluationConfiguration.INSTANCE));
            if (map3 == null || (map = MapsKt.toMutableMap(map3)) == null) {
                map = new HashMap();
            }
            Bindings bindings = scriptContext.getBindings(100);
            Bindings bindings2 = scriptContext.getBindings(200);
            for (Map.Entry entry : map2.entrySet()) {
                if (!map.containsKey(entry.getKey())) {
                    if (bindings != null && bindings.containsKey(entry.getKey())) {
                        obj = bindings.get(entry.getKey());
                    } else {
                        if (bindings2 == null || !bindings2.containsKey(entry.getKey())) {
                            return new ResultWithDiagnostics.Failure(ErrorHandlingKt.asErrorDiagnostics$default("Property " + ((String) entry.getKey()) + " is not found in the bindings", 0, null, null, 7, null));
                        }
                        obj = bindings2.get(entry.getKey());
                    }
                    map.put(entry.getKey(), obj);
                }
            }
            return ErrorHandlingKt.asSuccess$default(new ScriptEvaluationConfiguration(new ScriptEvaluationConfiguration[]{context.getEvaluationConfiguration()}, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.PropertiesFromContextKt.configureProvidedPropertiesFromJsr223Context.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    $receiver.invoke(ScriptEvaluationKt.getProvidedProperties($receiver), map);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            }), null, 1, null);
        }
        return ErrorHandlingKt.asSuccess$default(context.getEvaluationConfiguration(), null, 1, null);
    }
}
