package org.jetbrains.kotlin.scripting.configuration;

import java.io.File;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingConfigurationKeys.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R%\u0010\f\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R+\u0010\u0010\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0012 \u0013*\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00110\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0007R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0007R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0007R+\u0010\u001a\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u001b \u0013*\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u00110\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0007¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/scripting/configuration/ScriptingConfigurationKeys;", "", "()V", "DISABLE_SCRIPTING_PLUGIN_OPTION", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", "getDISABLE_SCRIPTING_PLUGIN_OPTION", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION", "getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION", "DISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION", "getDISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION", "LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION", "", "", "getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION", "SCRIPT_DEFINITIONS", "", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getSCRIPT_DEFINITIONS", "SCRIPT_DEFINITIONS_CLASSES", "getSCRIPT_DEFINITIONS_CLASSES", "SCRIPT_DEFINITIONS_CLASSPATH", "Ljava/io/File;", "getSCRIPT_DEFINITIONS_CLASSPATH", "SCRIPT_DEFINITIONS_SOURCES", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", "getSCRIPT_DEFINITIONS_SOURCES", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingConfigurationKeys {
    private static final CompilerConfigurationKey<Boolean> DISABLE_SCRIPTING_PLUGIN_OPTION;
    private static final CompilerConfigurationKey<Boolean> DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION;
    private static final CompilerConfigurationKey<Boolean> DISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION;
    public static final ScriptingConfigurationKeys INSTANCE = new ScriptingConfigurationKeys();
    private static final CompilerConfigurationKey<Map<String, Object>> LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION;
    private static final CompilerConfigurationKey<List<ScriptDefinition>> SCRIPT_DEFINITIONS;
    private static final CompilerConfigurationKey<List<String>> SCRIPT_DEFINITIONS_CLASSES;
    private static final CompilerConfigurationKey<List<File>> SCRIPT_DEFINITIONS_CLASSPATH;
    private static final CompilerConfigurationKey<List<ScriptDefinitionsSource>> SCRIPT_DEFINITIONS_SOURCES;

    private ScriptingConfigurationKeys() {
    }

    static {
        CompilerConfigurationKey<List<ScriptDefinition>> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("script definitions");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        SCRIPT_DEFINITIONS = compilerConfigurationKeyCreate;
        CompilerConfigurationKey<List<ScriptDefinitionsSource>> compilerConfigurationKeyCreate2 = CompilerConfigurationKey.create("script definitions sources");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate2, "create(...)");
        SCRIPT_DEFINITIONS_SOURCES = compilerConfigurationKeyCreate2;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate3 = CompilerConfigurationKey.create("Disable scripting plugin");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate3, "create(...)");
        DISABLE_SCRIPTING_PLUGIN_OPTION = compilerConfigurationKeyCreate3;
        CompilerConfigurationKey<List<String>> compilerConfigurationKeyCreate4 = CompilerConfigurationKey.create("Script definition classes");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate4, "create(...)");
        SCRIPT_DEFINITIONS_CLASSES = compilerConfigurationKeyCreate4;
        CompilerConfigurationKey<List<File>> compilerConfigurationKeyCreate5 = CompilerConfigurationKey.create("Additional classpath for the script definitions");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate5, "create(...)");
        SCRIPT_DEFINITIONS_CLASSPATH = compilerConfigurationKeyCreate5;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate6 = CompilerConfigurationKey.create("Do not extract script definitions from the compilation classpath");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate6, "create(...)");
        DISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION = compilerConfigurationKeyCreate6;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate7 = CompilerConfigurationKey.create("Do not automatically load compiler-supplied script definitions, like main-kts");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate7, "create(...)");
        DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION = compilerConfigurationKeyCreate7;
        CompilerConfigurationKey<Map<String, Object>> compilerConfigurationKeyCreate8 = CompilerConfigurationKey.create("Script resolver environment");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate8, "create(...)");
        LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION = compilerConfigurationKeyCreate8;
    }

    public final CompilerConfigurationKey<List<ScriptDefinition>> getSCRIPT_DEFINITIONS() {
        return SCRIPT_DEFINITIONS;
    }

    public final CompilerConfigurationKey<List<ScriptDefinitionsSource>> getSCRIPT_DEFINITIONS_SOURCES() {
        return SCRIPT_DEFINITIONS_SOURCES;
    }

    public final CompilerConfigurationKey<Boolean> getDISABLE_SCRIPTING_PLUGIN_OPTION() {
        return DISABLE_SCRIPTING_PLUGIN_OPTION;
    }

    public final CompilerConfigurationKey<List<String>> getSCRIPT_DEFINITIONS_CLASSES() {
        return SCRIPT_DEFINITIONS_CLASSES;
    }

    public final CompilerConfigurationKey<List<File>> getSCRIPT_DEFINITIONS_CLASSPATH() {
        return SCRIPT_DEFINITIONS_CLASSPATH;
    }

    public final CompilerConfigurationKey<Boolean> getDISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION() {
        return DISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION;
    }

    public final CompilerConfigurationKey<Boolean> getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION() {
        return DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION;
    }

    public final CompilerConfigurationKey<Map<String, Object>> getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION() {
        return LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION;
    }
}
