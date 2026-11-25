package org.jetbrains.kotlin.cli.common.repl;

import java.util.List;
import javax.script.ScriptEngineFactory;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: compiled from: KotlinJsr223JvmScriptEngineFactoryBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J1\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u000e\"\u00020\u0004H\u0016¢\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0016J\u0012\u0010\u0012\u001a\u00020\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0016J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004H\u0016J!\u0010\u0017\u001a\u00020\u00042\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u000e\"\u00020\u0004H\u0016¢\u0006\u0002\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineFactoryBase;", "Ljavax/script/ScriptEngineFactory;", "()V", "getEngineName", "", "getEngineVersion", "getExtensions", "", "getLanguageName", "getLanguageVersion", "getMethodCallSyntax", "obj", "m", "args", "", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getMimeTypes", "getNames", "getOutputStatement", "toDisplay", "getParameter", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "getProgram", "statements", "([Ljava/lang/String;)Ljava/lang/String;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinJsr223JvmScriptEngineFactoryBase implements ScriptEngineFactory {
    public String getLanguageName() {
        return IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME;
    }

    public String getLanguageVersion() {
        String VERSION = KotlinCompilerVersion.VERSION;
        Intrinsics.checkNotNullExpressionValue(VERSION, "VERSION");
        return VERSION;
    }

    public String getEngineName() {
        return IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME;
    }

    public String getEngineVersion() {
        String VERSION = KotlinCompilerVersion.VERSION;
        Intrinsics.checkNotNullExpressionValue(VERSION, "VERSION");
        return VERSION;
    }

    public List<String> getExtensions() {
        return CollectionsKt.listOf("kts");
    }

    public List<String> getMimeTypes() {
        return CollectionsKt.listOf("text/x-kotlin");
    }

    public List<String> getNames() {
        return CollectionsKt.listOf(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    public String getOutputStatement(String toDisplay) {
        return "print(\"" + toDisplay + "\")";
    }

    public String getMethodCallSyntax(String obj, String m, String... args) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(m, "m");
        Intrinsics.checkNotNullParameter(args, "args");
        return obj + '.' + m + '(' + ArraysKt.joinToString$default(args, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null) + ')';
    }

    public String getProgram(String... statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
        StringBuilder sb = new StringBuilder();
        Intrinsics.checkNotNull(property);
        return sb.append(ArraysKt.joinToString$default(statements, property, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).append(property).toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public Object getParameter(String key) {
        if (key != null) {
            switch (key.hashCode()) {
                case -1073020410:
                    if (key.equals("javax.script.engine_version")) {
                        return getEngineVersion();
                    }
                    break;
                case -1047659667:
                    if (key.equals("javax.script.engine")) {
                        return getEngineName();
                    }
                    break;
                case -917703229:
                    if (key.equals("javax.script.language")) {
                        return getLanguageName();
                    }
                    break;
                case -852670884:
                    if (key.equals("javax.script.language_version")) {
                        return getLanguageVersion();
                    }
                    break;
                case -125973898:
                    if (key.equals("javax.script.name")) {
                        return getEngineName();
                    }
                    break;
            }
        }
        return null;
    }
}
