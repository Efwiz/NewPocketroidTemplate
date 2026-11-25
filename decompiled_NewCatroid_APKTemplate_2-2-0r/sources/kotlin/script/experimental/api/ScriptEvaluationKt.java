package kotlin.script.experimental.api;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u00100\u001a\u000201*\u000202\u001a$\u00103\u001a\b\u0012\u0004\u0012\u00020504*\u0002052\u0006\u00106\u001a\u0002072\n\b\u0002\u00108\u001a\u0004\u0018\u000109\u001a(\u0010\u001c\u001a\u000201*\u0002022\u001c\u0010:\u001a\u0018\u0012\u0004\u0012\u00020<\u0012\n\u0012\b\u0012\u0004\u0012\u000205040;j\u0002`=\u001a*\u0010 \u001a\u000201\"\u0004\b\u0000\u0010>*\u0002022\u0018\u0010?\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H>0@\u0012\u0004\u0012\u0002H>0;\u001a%\u0010A\u001a\u000205*\u0004\u0018\u0001052\u0017\u0010B\u001a\u0013\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u0002010;¢\u0006\u0002\bC\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\u0007\u001a\u0004\b\u000b\u0010\u0005\"%\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u000f\u0010\u0005\"+\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0007\u001a\u0004\b\u0012\u0010\u0005\"-\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0007\u001a\u0004\b\u0015\u0010\u0005\"3\u0010\u0017\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0006\u0012\u0004\u0018\u00010\n0\u00180\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0007\u001a\u0004\b\u001a\u0010\u0005\"+\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0007\u001a\u0004\b\u001e\u0010\u0005\")\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0007\u001a\u0004\b\"\u0010\u0005\"%\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u0007\u001a\u0004\b&\u0010\u0005\";\u0010(\u001a\u0018\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030*\u0012\u0004\u0012\u00020+0)0\u0001*\u00020\u00038FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b/\u0010\u0007\u0012\u0004\b,\u0010-\u001a\u0004\b.\u0010\u0005*.\u0010D\"\u0014\u0012\u0004\u0012\u00020<\u0012\n\u0012\b\u0012\u0004\u0012\u000205040;2\u0014\u0012\u0004\u0012\u00020<\u0012\n\u0012\b\u0012\u0004\u0012\u000205040;¨\u0006E"}, d2 = {"compilationConfiguration", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;", "getCompilationConfiguration", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "compilationConfiguration$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "constructorArgs", "", "", "getConstructorArgs", "constructorArgs$delegate", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getHostConfiguration", "hostConfiguration$delegate", "implicitReceivers", "getImplicitReceivers", "implicitReceivers$delegate", "previousSnippets", "getPreviousSnippets", "previousSnippets$delegate", "providedProperties", "", "", "getProvidedProperties", "providedProperties$delegate", "refineConfigurationBeforeEvaluate", "Lkotlin/script/experimental/api/RefineEvaluationConfigurationData;", "getRefineConfigurationBeforeEvaluate", "refineConfigurationBeforeEvaluate$delegate", "scriptExecutionWrapper", "Lkotlin/script/experimental/api/ScriptExecutionWrapper;", "getScriptExecutionWrapper", "scriptExecutionWrapper$delegate", "scriptsInstancesSharing", "", "getScriptsInstancesSharing", "scriptsInstancesSharing$delegate", "scriptsInstancesSharingMap", "", "Lkotlin/reflect/KClass;", "Lkotlin/script/experimental/api/EvaluationResult;", "getScriptsInstancesSharingMap$annotations", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)V", "getScriptsInstancesSharingMap", "scriptsInstancesSharingMap$delegate", "enableScriptsInstancesSharing", "", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", "refineBeforeEvaluation", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "script", "Lkotlin/script/experimental/api/CompiledScript;", "contextData", "Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "handler", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationRefinementContext;", "Lkotlin/script/experimental/api/RefineScriptEvaluationConfigurationHandler;", ExifInterface.GPS_DIRECTION_TRUE, "wrapper", "Lkotlin/Function0;", "with", "body", "Lkotlin/ExtensionFunctionType;", "RefineScriptEvaluationConfigurationHandler", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptEvaluationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "implicitReceivers", "getImplicitReceivers(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "providedProperties", "getProvidedProperties(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "compilationConfiguration", "getCompilationConfiguration(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "constructorArgs", "getConstructorArgs(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "previousSnippets", "getPreviousSnippets(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "scriptsInstancesSharingMap", "getScriptsInstancesSharingMap(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "scriptsInstancesSharing", "getScriptsInstancesSharing(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "hostConfiguration", "getHostConfiguration(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "refineConfigurationBeforeEvaluate", "getRefineConfigurationBeforeEvaluate(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "scriptExecutionWrapper", "getScriptExecutionWrapper(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate implicitReceivers$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate providedProperties$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate compilationConfiguration$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate constructorArgs$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate previousSnippets$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptsInstancesSharingMap$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptsInstancesSharing$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) false, false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate hostConfiguration$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate refineConfigurationBeforeEvaluate$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptExecutionWrapper$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);

    @Deprecated(level = DeprecationLevel.ERROR, message = "use scriptsInstancesSharing flag instead")
    public static /* synthetic */ void getScriptsInstancesSharingMap$annotations(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
    }

    public static final ScriptEvaluationConfiguration with(ScriptEvaluationConfiguration scriptEvaluationConfiguration, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> body) {
        ScriptEvaluationConfiguration scriptEvaluationConfiguration2;
        Intrinsics.checkNotNullParameter(body, "body");
        if (scriptEvaluationConfiguration == null) {
            scriptEvaluationConfiguration2 = new ScriptEvaluationConfiguration(body);
        } else {
            scriptEvaluationConfiguration2 = new ScriptEvaluationConfiguration(new ScriptEvaluationConfiguration[]{scriptEvaluationConfiguration}, body);
        }
        return !Intrinsics.areEqual(scriptEvaluationConfiguration2, scriptEvaluationConfiguration) ? scriptEvaluationConfiguration2 : scriptEvaluationConfiguration;
    }

    public static final PropertiesCollection.Key<List<Object>> getImplicitReceivers(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return implicitReceivers$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<Map<String, Object>> getProvidedProperties(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return providedProperties$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<ScriptCompilationConfiguration> getCompilationConfiguration(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return compilationConfiguration$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final PropertiesCollection.Key<List<Object>> getConstructorArgs(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return constructorArgs$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[3]);
    }

    public static final PropertiesCollection.Key<List<Object>> getPreviousSnippets(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return previousSnippets$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[4]);
    }

    public static final PropertiesCollection.Key<Map<KClass<?>, EvaluationResult>> getScriptsInstancesSharingMap(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return scriptsInstancesSharingMap$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[5]);
    }

    public static final PropertiesCollection.Key<Boolean> getScriptsInstancesSharing(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return scriptsInstancesSharing$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[6]);
    }

    public static final PropertiesCollection.Key<ScriptingHostConfiguration> getHostConfiguration(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return hostConfiguration$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[7]);
    }

    public static final PropertiesCollection.Key<List<RefineEvaluationConfigurationData>> getRefineConfigurationBeforeEvaluate(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return refineConfigurationBeforeEvaluate$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[8]);
    }

    public static final PropertiesCollection.Key<ScriptExecutionWrapper<?>> getScriptExecutionWrapper(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return scriptExecutionWrapper$delegate.getValue(scriptEvaluationConfigurationKeys, $$delegatedProperties[9]);
    }

    public static final <T> void scriptExecutionWrapper(ScriptEvaluationConfiguration.Builder builder, final Function1<? super Function0<? extends T>, ? extends T> wrapper) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(wrapper, "wrapper");
        builder.put(getScriptExecutionWrapper(ScriptEvaluationConfiguration.INSTANCE), new ScriptExecutionWrapper<T>() { // from class: kotlin.script.experimental.api.ScriptEvaluationKt.scriptExecutionWrapper.3
            @Override // kotlin.script.experimental.api.ScriptExecutionWrapper
            public T invoke(Function0<? extends T> block) {
                Intrinsics.checkNotNullParameter(block, "block");
                return wrapper.invoke(block);
            }
        });
    }

    public static final void enableScriptsInstancesSharing(ScriptEvaluationConfiguration.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        builder.invoke(builder, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.api.ScriptEvaluationKt.enableScriptsInstancesSharing.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder2) {
                invoke2(builder2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptEvaluationConfiguration.Builder invoke) {
                Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                invoke.invoke((PropertiesCollection.Key<boolean>) ScriptEvaluationKt.getScriptsInstancesSharing(invoke), true);
            }
        });
    }

    public static final void refineConfigurationBeforeEvaluate(ScriptEvaluationConfiguration.Builder builder, Function1<? super ScriptEvaluationConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptEvaluationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(handler, "handler");
        builder.append(getRefineConfigurationBeforeEvaluate(ScriptEvaluationConfiguration.INSTANCE), new RefineEvaluationConfigurationData(handler));
    }

    public static /* synthetic */ ResultWithDiagnostics refineBeforeEvaluation$default(ScriptEvaluationConfiguration scriptEvaluationConfiguration, CompiledScript compiledScript, ScriptEvaluationContextData scriptEvaluationContextData, int i, Object obj) {
        if ((i & 2) != 0) {
            scriptEvaluationContextData = null;
        }
        return refineBeforeEvaluation(scriptEvaluationConfiguration, compiledScript, scriptEvaluationContextData);
    }

    public static final ResultWithDiagnostics<ScriptEvaluationConfiguration> refineBeforeEvaluation(ScriptEvaluationConfiguration scriptEvaluationConfiguration, CompiledScript script, ScriptEvaluationContextData scriptEvaluationContextData) {
        Function1 function1;
        Intrinsics.checkNotNullParameter(scriptEvaluationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) scriptEvaluationConfiguration.get(getHostConfiguration(ScriptEvaluationConfiguration.INSTANCE));
        ScriptEvaluationContextData scriptEvaluationContextDataMerge = ScriptDataKt.merge((scriptingHostConfiguration == null || (function1 = (Function1) scriptingHostConfiguration.get(HostConfigurationKt.getGetEvaluationContext(ScriptingHostConfiguration.INSTANCE))) == null) ? null : (ScriptEvaluationContextData) function1.invoke(scriptingHostConfiguration), scriptEvaluationContextData);
        ScriptEvaluationConfiguration scriptEvaluationConfiguration2 = scriptEvaluationConfiguration;
        List list = (List) scriptEvaluationConfiguration2.get(getRefineConfigurationBeforeEvaluate(ScriptEvaluationConfiguration.INSTANCE));
        if (list != null) {
            Iterator it = list.iterator();
            PropertiesCollection propertiesCollection = scriptEvaluationConfiguration2;
            while (it.hasNext()) {
                ResultWithDiagnostics<ScriptEvaluationConfiguration> resultWithDiagnosticsInvoke = ((RefineEvaluationConfigurationData) it.next()).getHandler().invoke(new ScriptEvaluationConfigurationRefinementContext(script, (ScriptEvaluationConfiguration) propertiesCollection, scriptEvaluationContextDataMerge));
                if (!(resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success)) {
                    if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsInvoke;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                propertiesCollection = (PropertiesCollection) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue();
            }
            if (propertiesCollection != null) {
                scriptEvaluationConfiguration2 = propertiesCollection;
            }
        }
        return ErrorHandlingKt.asSuccess$default(scriptEvaluationConfiguration2, null, 1, null);
    }
}
