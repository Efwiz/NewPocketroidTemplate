package kotlin.script.experimental.jvmhost.jsr223;

import javax.script.ScriptContext;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfigurationKeys;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.host.ScriptingHostConfigurationKeys;
import kotlin.script.experimental.util.PropertiesCollection;

/* JADX INFO: compiled from: KotlinJsr223HostConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"-\u0010\u0000\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"-\u0010\u0000\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00020\u0001*\u00020\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\u0005\u0010\n\"-\u0010\u0000\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00020\u0001*\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\u0005\u0010\r\"%\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0011\u0010\u0006\"\u0015\u0010\u0013\u001a\u00020\u0014*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0015\u0010\u0013\u001a\u00020\u0018*\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u001a\"\u0015\u0010\u0013\u001a\u00020\u001b*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u001d¨\u0006\u001e"}, d2 = {"getScriptContext", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/Function0;", "Ljavax/script/ScriptContext;", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationKeys;", "getGetScriptContext", "(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getScriptContext$delegate$1", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223EvaluationConfigurationKeys;", "(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223EvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getScriptContext$delegate$2", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223HostConfigurationKeys;", "(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223HostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getScriptContext$delegate", "importAllBindings", "", "getImportAllBindings", "importAllBindings$delegate", "jsr223", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getJsr223", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationBuilder;", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223EvaluationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/jvmhost/jsr223/Jsr223EvaluationConfigurationBuilder;", "Lkotlin/script/experimental/jvmhost/jsr223/Jsr223HostConfigurationBuilder;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/jvmhost/jsr223/Jsr223HostConfigurationBuilder;", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinJsr223HostConfigurationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KotlinJsr223HostConfigurationKt.class, "kotlin-scripting-jvm-host-unshaded"), "getScriptContext", "getGetScriptContext(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223HostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KotlinJsr223HostConfigurationKt.class, "kotlin-scripting-jvm-host-unshaded"), "getScriptContext", "getGetScriptContext(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KotlinJsr223HostConfigurationKt.class, "kotlin-scripting-jvm-host-unshaded"), "importAllBindings", "getImportAllBindings(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223CompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KotlinJsr223HostConfigurationKt.class, "kotlin-scripting-jvm-host-unshaded"), "getScriptContext", "getGetScriptContext(Lkotlin/script/experimental/jvmhost/jsr223/Jsr223EvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate getScriptContext$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate getScriptContext$delegate$1 = PropertiesCollection.INSTANCE.key((Function1) new Function1<PropertiesCollection, Function0<? extends ScriptContext>>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223HostConfigurationKt$getScriptContext$4
        @Override // kotlin.jvm.functions.Function1
        public final Function0<ScriptContext> invoke(PropertiesCollection key) {
            Intrinsics.checkNotNullParameter(key, "$this$key");
            ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) key.get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
            if (scriptingHostConfiguration != null) {
                return (Function0) scriptingHostConfiguration.get(KotlinJsr223HostConfigurationKt.getGetScriptContext(KotlinJsr223HostConfigurationKt.getJsr223(ScriptingHostConfiguration.INSTANCE)));
            }
            return null;
        }
    }, true);
    private static final PropertiesCollection.PropertyKeyDelegate importAllBindings$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) false, false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate getScriptContext$delegate$2 = PropertiesCollection.INSTANCE.key((Function1) new Function1<PropertiesCollection, Function0<? extends ScriptContext>>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223HostConfigurationKt$getScriptContext$7
        @Override // kotlin.jvm.functions.Function1
        public final Function0<ScriptContext> invoke(PropertiesCollection key) {
            Intrinsics.checkNotNullParameter(key, "$this$key");
            ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) key.get(ScriptEvaluationKt.getHostConfiguration(ScriptEvaluationConfiguration.INSTANCE));
            if (scriptingHostConfiguration != null) {
                return (Function0) scriptingHostConfiguration.get(KotlinJsr223HostConfigurationKt.getGetScriptContext(KotlinJsr223HostConfigurationKt.getJsr223(ScriptingHostConfiguration.INSTANCE)));
            }
            return null;
        }
    }, true);

    public static final Jsr223HostConfigurationBuilder getJsr223(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return new Jsr223HostConfigurationBuilder();
    }

    public static final PropertiesCollection.Key<Function0<ScriptContext>> getGetScriptContext(Jsr223HostConfigurationKeys jsr223HostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jsr223HostConfigurationKeys, "<this>");
        return getScriptContext$delegate.getValue(jsr223HostConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final Jsr223CompilationConfigurationBuilder getJsr223(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return new Jsr223CompilationConfigurationBuilder();
    }

    public static final PropertiesCollection.Key<Function0<ScriptContext>> getGetScriptContext(Jsr223CompilationConfigurationKeys jsr223CompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jsr223CompilationConfigurationKeys, "<this>");
        return getScriptContext$delegate$1.getValue(jsr223CompilationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<Boolean> getImportAllBindings(Jsr223CompilationConfigurationKeys jsr223CompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jsr223CompilationConfigurationKeys, "<this>");
        return importAllBindings$delegate.getValue(jsr223CompilationConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final Jsr223EvaluationConfigurationBuilder getJsr223(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return new Jsr223EvaluationConfigurationBuilder();
    }

    public static final PropertiesCollection.Key<Function0<ScriptContext>> getGetScriptContext(Jsr223EvaluationConfigurationKeys jsr223EvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jsr223EvaluationConfigurationKeys, "<this>");
        return getScriptContext$delegate$2.getValue(jsr223EvaluationConfigurationKeys, $$delegatedProperties[3]);
    }
}
