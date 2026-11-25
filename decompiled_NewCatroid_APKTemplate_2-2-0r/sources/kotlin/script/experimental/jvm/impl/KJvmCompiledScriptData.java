package kotlin.script.experimental.jvm.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledScript.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 '2\u00020\u0001:\u0001'BE\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R(\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001d¨\u0006("}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData;", "Ljava/io/Serializable;", "sourceLocationId", "", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "scriptClassFQName", "resultField", "Lkotlin/Pair;", "Lkotlin/script/experimental/api/KotlinType;", "otherScripts", "", "Lkotlin/script/experimental/api/CompiledScript;", "(Ljava/lang/String;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Ljava/lang/String;Lkotlin/Pair;Ljava/util/List;)V", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "setCompilationConfiguration", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;)V", "getOtherScripts", "()Ljava/util/List;", "setOtherScripts", "(Ljava/util/List;)V", "getResultField", "()Lkotlin/Pair;", "setResultField", "(Lkotlin/Pair;)V", "getScriptClassFQName", "()Ljava/lang/String;", "setScriptClassFQName", "(Ljava/lang/String;)V", "getSourceLocationId", "setSourceLocationId", "readObject", "", "inputStream", "Ljava/io/ObjectInputStream;", "writeObject", "outputStream", "Ljava/io/ObjectOutputStream;", "Companion", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KJvmCompiledScriptData implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 5;
    private ScriptCompilationConfiguration compilationConfiguration;
    private List<? extends CompiledScript> otherScripts;
    private Pair<String, KotlinType> resultField;
    private String scriptClassFQName;
    private String sourceLocationId;

    public KJvmCompiledScriptData(String str, ScriptCompilationConfiguration compilationConfiguration, String scriptClassFQName, Pair<String, KotlinType> pair, List<? extends CompiledScript> otherScripts) {
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        Intrinsics.checkNotNullParameter(scriptClassFQName, "scriptClassFQName");
        Intrinsics.checkNotNullParameter(otherScripts, "otherScripts");
        this.sourceLocationId = str;
        this.compilationConfiguration = compilationConfiguration;
        this.scriptClassFQName = scriptClassFQName;
        this.resultField = pair;
        this.otherScripts = otherScripts;
    }

    public final String getSourceLocationId() {
        return this.sourceLocationId;
    }

    public final void setSourceLocationId(String str) {
        this.sourceLocationId = str;
    }

    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.compilationConfiguration;
    }

    public final void setCompilationConfiguration(ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<set-?>");
        this.compilationConfiguration = scriptCompilationConfiguration;
    }

    public final String getScriptClassFQName() {
        return this.scriptClassFQName;
    }

    public final void setScriptClassFQName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.scriptClassFQName = str;
    }

    public final Pair<String, KotlinType> getResultField() {
        return this.resultField;
    }

    public final void setResultField(Pair<String, KotlinType> pair) {
        this.resultField = pair;
    }

    public /* synthetic */ KJvmCompiledScriptData(String str, ScriptCompilationConfiguration scriptCompilationConfiguration, String str2, Pair pair, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, scriptCompilationConfiguration, str2, pair, (i & 16) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<CompiledScript> getOtherScripts() {
        return this.otherScripts;
    }

    public final void setOtherScripts(List<? extends CompiledScript> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.otherScripts = list;
    }

    private final void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(this.compilationConfiguration);
        outputStream.writeObject(this.sourceLocationId);
        outputStream.writeObject(this.otherScripts);
        outputStream.writeObject(this.scriptClassFQName);
        outputStream.writeObject(this.resultField);
    }

    private final void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        Object object = inputStream.readObject();
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.script.experimental.api.ScriptCompilationConfiguration");
        this.compilationConfiguration = (ScriptCompilationConfiguration) object;
        this.sourceLocationId = (String) inputStream.readObject();
        Object object2 = inputStream.readObject();
        Intrinsics.checkNotNull(object2, "null cannot be cast to non-null type kotlin.collections.List<kotlin.script.experimental.api.CompiledScript>");
        this.otherScripts = (List) object2;
        Object object3 = inputStream.readObject();
        Intrinsics.checkNotNull(object3, "null cannot be cast to non-null type kotlin.String");
        this.scriptClassFQName = (String) object3;
        this.resultField = (Pair) inputStream.readObject();
    }

    /* JADX INFO: compiled from: KJvmCompiledScript.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledScriptData$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
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
