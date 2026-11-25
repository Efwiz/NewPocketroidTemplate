package kotlin.script.experimental.jvm.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledScript.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 22\u00020\u00012\u00020\u0002:\u00012BQ\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fB\u0019\b\u0000\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u0012J\"\u0010$\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030&0%2\b\u0010'\u001a\u0004\u0018\u00010(H\u0096@¢\u0006\u0002\u0010)J\b\u0010*\u001a\u0004\u0018\u00010\u000eJ\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020,2\u0006\u00100\u001a\u000201H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0010\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\"\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\"¨\u00063"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "Lkotlin/script/experimental/api/CompiledScript;", "Ljava/io/Serializable;", "sourceLocationId", "", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "scriptClassFQName", "resultField", "Lkotlin/Pair;", "Lkotlin/script/experimental/api/KotlinType;", "otherScripts", "", "compiledModule", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", "(Ljava/lang/String;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Ljava/lang/String;Lkotlin/Pair;Ljava/util/List;Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;)V", "data", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData;", "(Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData;Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;)V", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompiledModule$kotlin_scripting_jvm", "()Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", "setCompiledModule$kotlin_scripting_jvm", "(Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;)V", "getData$kotlin_scripting_jvm", "()Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData;", "setData$kotlin_scripting_jvm", "(Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData;)V", "getOtherScripts", "()Ljava/util/List;", "getResultField", "()Lkotlin/Pair;", "getScriptClassFQName", "()Ljava/lang/String;", "getSourceLocationId", "getClass", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/reflect/KClass;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompiledModule", "readObject", "", "inputStream", "Ljava/io/ObjectInputStream;", "writeObject", "outputStream", "Ljava/io/ObjectOutputStream;", "Companion", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KJvmCompiledScript implements CompiledScript, Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 3;
    private KJvmCompiledModule compiledModule;
    private KJvmCompiledScriptData data;

    @Override // kotlin.script.experimental.api.CompiledScript
    public Object getClass(ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends KClass<?>>> continuation) {
        return getClass$suspendImpl(this, scriptEvaluationConfiguration, continuation);
    }

    public KJvmCompiledScript(KJvmCompiledScriptData data, KJvmCompiledModule kJvmCompiledModule) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.compiledModule = kJvmCompiledModule;
    }

    /* JADX INFO: renamed from: getData$kotlin_scripting_jvm, reason: from getter */
    public final KJvmCompiledScriptData getData() {
        return this.data;
    }

    public final void setData$kotlin_scripting_jvm(KJvmCompiledScriptData kJvmCompiledScriptData) {
        Intrinsics.checkNotNullParameter(kJvmCompiledScriptData, "<set-?>");
        this.data = kJvmCompiledScriptData;
    }

    public final KJvmCompiledModule getCompiledModule$kotlin_scripting_jvm() {
        return this.compiledModule;
    }

    public final void setCompiledModule$kotlin_scripting_jvm(KJvmCompiledModule kJvmCompiledModule) {
        this.compiledModule = kJvmCompiledModule;
    }

    public /* synthetic */ KJvmCompiledScript(String str, ScriptCompilationConfiguration scriptCompilationConfiguration, String str2, Pair pair, List list, KJvmCompiledModule kJvmCompiledModule, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, scriptCompilationConfiguration, str2, pair, (i & 16) != 0 ? CollectionsKt.emptyList() : list, kJvmCompiledModule);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KJvmCompiledScript(String str, ScriptCompilationConfiguration compilationConfiguration, String scriptClassFQName, Pair<String, KotlinType> pair, List<? extends CompiledScript> otherScripts, KJvmCompiledModule kJvmCompiledModule) {
        this(new KJvmCompiledScriptData(str, compilationConfiguration, scriptClassFQName, pair, otherScripts), kJvmCompiledModule);
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        Intrinsics.checkNotNullParameter(scriptClassFQName, "scriptClassFQName");
        Intrinsics.checkNotNullParameter(otherScripts, "otherScripts");
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public String getSourceLocationId() {
        return this.data.getSourceLocationId();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.data.getCompilationConfiguration();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public List<CompiledScript> getOtherScripts() {
        return this.data.getOtherScripts();
    }

    public final String getScriptClassFQName() {
        return this.data.getScriptClassFQName();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public Pair<String, KotlinType> getResultField() {
        return this.data.getResultField();
    }

    static /* synthetic */ Object getClass$suspendImpl(KJvmCompiledScript kJvmCompiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends KClass<?>>> continuation) {
        if (scriptEvaluationConfiguration == null) {
            try {
                scriptEvaluationConfiguration = new ScriptEvaluationConfiguration(null, 1, null);
            } catch (Throwable th) {
                return new ResultWithDiagnostics.Failure(new ScriptDiagnostic(-1, "Unable to instantiate class " + kJvmCompiledScript.data.getScriptClassFQName(), null, kJvmCompiledScript.getSourceLocationId(), null, th, 20, null));
            }
        }
        Class<?> clsLoadClass = KJvmCompiledScriptKt.getOrCreateActualClassloader(kJvmCompiledScript, scriptEvaluationConfiguration).loadClass(kJvmCompiledScript.data.getScriptClassFQName());
        Intrinsics.checkNotNullExpressionValue(clsLoadClass, "loadClass(...)");
        return ErrorHandlingKt.asSuccess$default(JvmClassMappingKt.getKotlinClass(clsLoadClass), null, 1, null);
    }

    public final KJvmCompiledModule getCompiledModule() {
        return this.compiledModule;
    }

    private final void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(this.data);
        outputStream.writeObject(this.compiledModule);
    }

    private final void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        Object object = inputStream.readObject();
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.script.experimental.jvm.impl.KJvmCompiledScriptData");
        this.data = (KJvmCompiledScriptData) object;
        this.compiledModule = (KJvmCompiledModule) inputStream.readObject();
    }

    /* JADX INFO: compiled from: KJvmCompiledScript.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void getSerialVersionUID$annotations() {
        }

        private Companion() {
        }
    }
}
