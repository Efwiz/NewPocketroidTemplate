package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption;
import org.jetbrains.kotlin.compiler.plugin.CliOption;
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException;
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingCommandLineProcessor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingCommandLineProcessor;", "Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;", "()V", "pluginId", "", "getPluginId", "()Ljava/lang/String;", "pluginOptions", "", "Lorg/jetbrains/kotlin/compiler/plugin/CliOption;", "getPluginOptions", "()Ljava/util/List;", "processOption", "", "option", "Lorg/jetbrains/kotlin/compiler/plugin/AbstractCliOption;", "value", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingCommandLineProcessor implements CommandLineProcessor {
    private final String pluginId = "kotlin.scripting";
    private final List<CliOption> pluginOptions = CollectionsKt.listOf((Object[]) new CliOption[]{DISABLE_SCRIPTING_PLUGIN_OPTION, SCRIPT_DEFINITIONS_OPTION, SCRIPT_DEFINITIONS_CLASSPATH_OPTION, DISABLE_STANDARD_SCRIPT_DEFINITION_OPTION, DISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION, DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION, LEGACY_SCRIPT_TEMPLATES_OPTION, LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION});

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final CliOption DISABLE_SCRIPTING_PLUGIN_OPTION = new CliOption("disable", "true/false", "Disable scripting plugin", false, false);
    private static final CliOption SCRIPT_DEFINITIONS_OPTION = new CliOption("script-definitions", "<fully qualified class name[,]>", "Script definition classes", false, true);
    private static final CliOption SCRIPT_DEFINITIONS_CLASSPATH_OPTION = new CliOption("script-definitions-classpath", "<classpath entry[:]>", "Additional classpath for the script definitions", false, true);
    private static final CliOption DISABLE_STANDARD_SCRIPT_DEFINITION_OPTION = new CliOption("disable-standard-script", "true/false", "Disable standard kotlin script support", false, false);
    private static final CliOption DISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION = new CliOption("disable-script-definitions-from-classpath", "true/false", "Do not extract script definitions from the compilation classpath", false, false);
    private static final CliOption DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION = new CliOption("disable-script-definitions-autoloading", "true/false", "Do not automatically load compiler-supplied script definitions, like main-kts", false, false);
    private static final CliOption LEGACY_SCRIPT_TEMPLATES_OPTION = new CliOption("script-templates", "<fully qualified class name[,]>", "Script definition template classes", false, true);
    private static final CliOption LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION = new CliOption("script-resolver-environment", "<key=value[,]>", "Script resolver environment in key-value pairs (the value could be quoted and escaped)", false, true);

    /* JADX INFO: compiled from: ScriptingCommandLineProcessor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingCommandLineProcessor$Companion;", "", "()V", "DISABLE_SCRIPTING_PLUGIN_OPTION", "Lorg/jetbrains/kotlin/compiler/plugin/CliOption;", "getDISABLE_SCRIPTING_PLUGIN_OPTION", "()Lorg/jetbrains/kotlin/compiler/plugin/CliOption;", "DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION", "getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION", "DISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION", "getDISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION", "DISABLE_STANDARD_SCRIPT_DEFINITION_OPTION", "getDISABLE_STANDARD_SCRIPT_DEFINITION_OPTION", "LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION", "getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION", "LEGACY_SCRIPT_TEMPLATES_OPTION", "getLEGACY_SCRIPT_TEMPLATES_OPTION", "SCRIPT_DEFINITIONS_CLASSPATH_OPTION", "getSCRIPT_DEFINITIONS_CLASSPATH_OPTION", "SCRIPT_DEFINITIONS_OPTION", "getSCRIPT_DEFINITIONS_OPTION", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CliOption getDISABLE_SCRIPTING_PLUGIN_OPTION() {
            return ScriptingCommandLineProcessor.DISABLE_SCRIPTING_PLUGIN_OPTION;
        }

        public final CliOption getSCRIPT_DEFINITIONS_OPTION() {
            return ScriptingCommandLineProcessor.SCRIPT_DEFINITIONS_OPTION;
        }

        public final CliOption getSCRIPT_DEFINITIONS_CLASSPATH_OPTION() {
            return ScriptingCommandLineProcessor.SCRIPT_DEFINITIONS_CLASSPATH_OPTION;
        }

        public final CliOption getDISABLE_STANDARD_SCRIPT_DEFINITION_OPTION() {
            return ScriptingCommandLineProcessor.DISABLE_STANDARD_SCRIPT_DEFINITION_OPTION;
        }

        public final CliOption getDISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION() {
            return ScriptingCommandLineProcessor.DISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION;
        }

        public final CliOption getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION() {
            return ScriptingCommandLineProcessor.DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION;
        }

        public final CliOption getLEGACY_SCRIPT_TEMPLATES_OPTION() {
            return ScriptingCommandLineProcessor.LEGACY_SCRIPT_TEMPLATES_OPTION;
        }

        public final CliOption getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION() {
            return ScriptingCommandLineProcessor.LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION;
        }
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
    public String getPluginId() {
        return this.pluginId;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
    public List<CliOption> getPluginOptions() {
        return this.pluginOptions;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
    public void processOption(AbstractCliOption option, String value, CompilerConfiguration configuration) {
        Object next;
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        if (Intrinsics.areEqual(option, DISABLE_SCRIPTING_PLUGIN_OPTION)) {
            CompilerConfigurationKey<Boolean> disable_scripting_plugin_option = ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPTING_PLUGIN_OPTION();
            if (StringsKt.isBlank(value)) {
                value = null;
            }
            configuration.put(disable_scripting_plugin_option, Boolean.valueOf(value != null ? Boolean.parseBoolean(value) : true));
            return;
        }
        if (Intrinsics.areEqual(option, SCRIPT_DEFINITIONS_OPTION) ? true : Intrinsics.areEqual(option, LEGACY_SCRIPT_TEMPLATES_OPTION)) {
            List list = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_CLASSES());
            Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
            List mutableList = CollectionsKt.toMutableList((Collection) list);
            mutableList.addAll(StringsKt.split$default((CharSequence) value, new char[]{','}, false, 0, 6, (Object) null));
            configuration.put(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_CLASSES(), mutableList);
            return;
        }
        if (Intrinsics.areEqual(option, SCRIPT_DEFINITIONS_CLASSPATH_OPTION)) {
            List list2 = configuration.getList(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_CLASSPATH());
            Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
            List mutableList2 = CollectionsKt.toMutableList((Collection) list2);
            List listSplit$default = StringsKt.split$default((CharSequence) value, new char[]{File.pathSeparatorChar}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            mutableList2.addAll(arrayList);
            configuration.put(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS_CLASSPATH(), mutableList2);
            return;
        }
        if (Intrinsics.areEqual(option, DISABLE_STANDARD_SCRIPT_DEFINITION_OPTION)) {
            CompilerConfigurationKey<Boolean> compilerConfigurationKey = JVMConfigurationKeys.DISABLE_STANDARD_SCRIPT_DEFINITION;
            if (StringsKt.isBlank(value)) {
                value = null;
            }
            configuration.put(compilerConfigurationKey, Boolean.valueOf(value != null ? Boolean.parseBoolean(value) : true));
            return;
        }
        if (Intrinsics.areEqual(option, DISABLE_SCRIPT_DEFINITIONS_FROM_CLSSPATH_OPTION)) {
            CompilerConfigurationKey<Boolean> disable_script_definitions_from_classpath_option = ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPT_DEFINITIONS_FROM_CLASSPATH_OPTION();
            if (StringsKt.isBlank(value)) {
                value = null;
            }
            configuration.put(disable_script_definitions_from_classpath_option, Boolean.valueOf(value != null ? Boolean.parseBoolean(value) : true));
            return;
        }
        if (Intrinsics.areEqual(option, DISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION)) {
            CompilerConfigurationKey<Boolean> disable_script_definitions_autoloading_option = ScriptingConfigurationKeys.INSTANCE.getDISABLE_SCRIPT_DEFINITIONS_AUTOLOADING_OPTION();
            if (StringsKt.isBlank(value)) {
                value = null;
            }
            configuration.put(disable_script_definitions_autoloading_option, Boolean.valueOf(value != null ? Boolean.parseBoolean(value) : true));
            return;
        }
        if (Intrinsics.areEqual(option, LEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION)) {
            Map map = configuration.getMap(ScriptingConfigurationKeys.INSTANCE.getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION());
            Intrinsics.checkNotNullExpressionValue(map, "getMap(...)");
            Map mutableMap = MapsKt.toMutableMap(map);
            Regex regex = new Regex("(\\w+)=(?:\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|([^\\s]*))");
            Regex regex2 = new Regex("\\\\([\"\\\\,])");
            for (String str : SequencesKt.filter(SequencesKt.map(Regex.findAll$default(new Regex("(?:\\\\.|[^,\\\\]++)*"), value, 0, 2, null), new Function1<MatchResult, String>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCommandLineProcessor.processOption.6
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(MatchResult it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return it2.getValue();
                }
            }), new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCommandLineProcessor.processOption.7
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(String it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(!StringsKt.isBlank(it2));
                }
            })) {
                MatchResult matchResultMatchEntire = regex.matchEntire(str);
                if (matchResultMatchEntire == null || matchResultMatchEntire.getGroupValues().size() < 4 || StringsKt.isBlank(matchResultMatchEntire.getGroupValues().get(1))) {
                    throw new CliOptionProcessingException("Unable to parse script-resolver-environment argument " + str, null, 2, null);
                }
                String str2 = matchResultMatchEntire.getGroupValues().get(1);
                Iterator it2 = CollectionsKt.drop(matchResultMatchEntire.getGroupValues(), 2).iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                } while (!(((String) next).length() > 0));
                String str3 = (String) next;
                mutableMap.put(str2, str3 != null ? regex2.replace(str3, "$1") : null);
            }
            configuration.put(ScriptingConfigurationKeys.INSTANCE.getLEGACY_SCRIPT_RESOLVER_ENVIRONMENT_OPTION(), mutableMap);
            return;
        }
        throw new CliOptionProcessingException("Unknown option: " + option.getOptionName(), null, 2, null);
    }
}
