package kotlin.script.experimental.jvmhost;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfigurationKeys;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import kotlin.script.experimental.util.PropertiesCollection;

/* JADX INFO: compiled from: obsoleteJvmScriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"&\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001*\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u001e\u0010\b\u001a\u00020\t*\u00020\n8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"baseClassLoader", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Ljava/lang/ClassLoader;", "Lkotlin/script/experimental/jvmhost/JvmScriptEvaluationConfigurationKeys;", "getBaseClassLoader$annotations", "(Lkotlin/script/experimental/jvmhost/JvmScriptEvaluationConfigurationKeys;)V", "getBaseClassLoader", "(Lkotlin/script/experimental/jvmhost/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "jvm", "Lkotlin/script/experimental/jvmhost/JvmScriptEvaluationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;", "getJvm$annotations", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)V", "getJvm", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/jvmhost/JvmScriptEvaluationConfigurationBuilder;", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ObsoleteJvmScriptEvaluationKt {
    @Deprecated(level = DeprecationLevel.ERROR, message = "use the same definitions from kotlin.script.experimental.jvm package")
    public static /* synthetic */ void getBaseClassLoader$annotations(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "use the same definitions from kotlin.script.experimental.jvm package")
    public static /* synthetic */ void getJvm$annotations(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
    }

    public static final PropertiesCollection.Key<ClassLoader> getBaseClassLoader(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return JvmScriptEvaluationKt.getBaseClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE));
    }

    public static final JvmScriptEvaluationConfigurationBuilder getJvm(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return new JvmScriptEvaluationConfigurationBuilder();
    }
}
