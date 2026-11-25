package kotlin.script.experimental.jvm;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BasicJvmScriptEvaluator.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¨\u0006\u0004"}, d2 = {"getOrPrepareShared", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "classLoader", "Ljava/lang/ClassLoader;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BasicJvmScriptEvaluatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptEvaluationConfiguration getOrPrepareShared(ScriptEvaluationConfiguration scriptEvaluationConfiguration, final ClassLoader classLoader) {
        return scriptEvaluationConfiguration.get(JvmScriptEvaluationKt.getActualClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE))) != null ? scriptEvaluationConfiguration : ScriptEvaluationKt.with(scriptEvaluationConfiguration, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvm.BasicJvmScriptEvaluatorKt.getOrPrepareShared.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptEvaluationConfiguration.Builder with) {
                Intrinsics.checkNotNullParameter(with, "$this$with");
                with.invoke(JvmScriptEvaluationKt.getActualClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)), classLoader);
                if (Intrinsics.areEqual(with.get(ScriptEvaluationKt.getScriptsInstancesSharing(ScriptEvaluationConfiguration.INSTANCE)), (Object) true)) {
                    with.invoke((PropertiesCollection.Key<LinkedHashMap>) JvmScriptEvaluationKt.getScriptsInstancesSharingMap(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)), new LinkedHashMap());
                }
            }
        });
    }
}
