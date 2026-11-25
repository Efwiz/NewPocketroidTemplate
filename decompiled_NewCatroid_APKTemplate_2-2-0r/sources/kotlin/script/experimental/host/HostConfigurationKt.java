package kotlin.script.experimental.host;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.api.ScriptEvaluationContextData;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: hostConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a1\u0010\t\u001a\u00020\u0017*\u00020\u00182%\u0010\u0019\u001a!\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\nj\u0002`\u0010\u001a\u001e\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u001a*\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e\u001a\"\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u001a*\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u001a\u001a%\u0010 \u001a\u00020\u000b*\u0004\u0018\u00010\u000b2\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00170\n¢\u0006\u0002\b\"\u001a\u0014\u0010#\u001a\u00020\u000b*\u0004\u0018\u00010\u000b2\u0006\u0010$\u001a\u00020\u000b\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"D\u0010\t\u001a'\u0012#\u0012!\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\nj\u0002`\u00100\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0011\u0010\u0006\"%\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0015\u0010\u0006*@\u0010%\"\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\n2\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\n¨\u0006&"}, d2 = {"configurationDependencies", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/ScriptDependency;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "getConfigurationDependencies", "(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "configurationDependencies$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "getEvaluationContext", "Lkotlin/Function1;", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "Lkotlin/ParameterName;", "name", "hostConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "Lkotlin/script/experimental/host/GetEvaluationContext;", "getGetEvaluationContext", "getEvaluationContext$delegate", "getScriptingClass", "Lkotlin/script/experimental/host/GetScriptingClass;", "getGetScriptingClass", "getScriptingClass$delegate", "", "Lkotlin/script/experimental/host/ScriptingHostConfiguration$Builder;", "handler", "Lkotlin/reflect/KClass;", ModuleXmlParser.TYPE, "Lkotlin/script/experimental/api/KotlinType;", "context", "", "contextClass", "with", "body", "Lkotlin/ExtensionFunctionType;", "withDefaultsFrom", "defaults", "GetEvaluationContext", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HostConfigurationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(HostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "configurationDependencies", "getConfigurationDependencies(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(HostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "getScriptingClass", "getGetScriptingClass(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(HostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "getEvaluationContext", "getGetEvaluationContext(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate configurationDependencies$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate getScriptingClass$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate getEvaluationContext$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);

    public static final ScriptingHostConfiguration with(ScriptingHostConfiguration scriptingHostConfiguration, Function1<? super ScriptingHostConfiguration.Builder, Unit> body) {
        ScriptingHostConfiguration scriptingHostConfiguration2;
        Intrinsics.checkNotNullParameter(body, "body");
        if (scriptingHostConfiguration == null) {
            scriptingHostConfiguration2 = new ScriptingHostConfiguration(body);
        } else {
            scriptingHostConfiguration2 = new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{scriptingHostConfiguration}, body);
        }
        return !Intrinsics.areEqual(scriptingHostConfiguration2, scriptingHostConfiguration) ? scriptingHostConfiguration2 : scriptingHostConfiguration;
    }

    public static final ScriptingHostConfiguration withDefaultsFrom(ScriptingHostConfiguration scriptingHostConfiguration, ScriptingHostConfiguration defaults) {
        Intrinsics.checkNotNullParameter(defaults, "defaults");
        if (Intrinsics.areEqual(scriptingHostConfiguration, defaults) || defaults.isEmpty()) {
            if (scriptingHostConfiguration != null) {
                return scriptingHostConfiguration;
            }
        } else if (scriptingHostConfiguration != null && !scriptingHostConfiguration.isEmpty()) {
            return new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{defaults, scriptingHostConfiguration}, null, 2, null);
        }
        return defaults;
    }

    public static final PropertiesCollection.Key<List<ScriptDependency>> getConfigurationDependencies(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return configurationDependencies$delegate.getValue(scriptingHostConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<GetScriptingClass> getGetScriptingClass(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return getScriptingClass$delegate.getValue(scriptingHostConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<Function1<ScriptingHostConfiguration, ScriptEvaluationContextData>> getGetEvaluationContext(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return getEvaluationContext$delegate.getValue(scriptingHostConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final void getEvaluationContext(ScriptingHostConfiguration.Builder builder, Function1<? super ScriptingHostConfiguration, ScriptEvaluationContextData> handler) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(handler, "handler");
        builder.put(getGetEvaluationContext(ScriptingHostConfiguration.INSTANCE), handler);
    }

    public static final KClass<?> getScriptingClass(ScriptingHostConfiguration scriptingHostConfiguration, KotlinType type, KClass<?> contextClass) {
        Intrinsics.checkNotNullParameter(scriptingHostConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        GetScriptingClass getScriptingClass = (GetScriptingClass) scriptingHostConfiguration.get(getGetScriptingClass(ScriptingHostConfiguration.INSTANCE));
        if (getScriptingClass == null) {
            throw new IllegalArgumentException("Expecting 'getScriptingClass' property in the scripting host configuration: unable to load scripting class " + type);
        }
        return getScriptingClass.invoke(type, contextClass, scriptingHostConfiguration);
    }

    public static final KClass<?> getScriptingClass(ScriptingHostConfiguration scriptingHostConfiguration, KotlinType type, Object context) {
        Intrinsics.checkNotNullParameter(scriptingHostConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        return getScriptingClass(scriptingHostConfiguration, type, (KClass<?>) Reflection.getOrCreateKotlinClass(context.getClass()));
    }
}
