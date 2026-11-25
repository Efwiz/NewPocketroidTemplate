package org.jetbrains.kotlin.cli.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.common.arguments.ArgumentParseErrors;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.CommonToolArguments;
import org.jetbrains.kotlin.cli.common.arguments.InternalArgument;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.ManualLanguageFeatureSetting;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.HmppCliModule;
import org.jetbrains.kotlin.config.HmppCliModuleStructure;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.KotlinPathsFromHomeDir;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: arguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u0012\u0010\r\u001a\u00020\t*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u000e\u001a\u0014\u0010\u000f\u001a\u00020\t*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u000eH\u0002\u001a*\u0010\u0010\u001a\u00020\t*\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0016\b\u0002\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0012\u001a\u0012\u0010\u0015\u001a\u00020\t*\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"KOTLIN_HOME_PROPERTY", "", "computeKotlinPaths", "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "switchToFallbackModeIfNecessary", "", "buildHmppModuleStructure", "Lorg/jetbrains/kotlin/config/HmppCliModuleStructure;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "reportArgumentParseProblems", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "reportUnsafeInternalArgumentsIfAny", "setupCommonArguments", "createMetadataVersion", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "setupLanguageVersionSettings", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsKt {
    public static final String KOTLIN_HOME_PROPERTY = "kotlin.home";

    public static /* synthetic */ void setupCommonArguments$default(CompilerConfiguration compilerConfiguration, CommonCompilerArguments commonCompilerArguments, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        setupCommonArguments(compilerConfiguration, commonCompilerArguments, function1);
    }

    public static final void setupCommonArguments(CompilerConfiguration compilerConfiguration, CommonCompilerArguments arguments, Function1<? super int[], ? extends BinaryVersion> function1) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        compilerConfiguration.put(CommonConfigurationKeys.DISABLE_INLINE, Boolean.valueOf(arguments.getNoInline()));
        compilerConfiguration.put(CommonConfigurationKeys.USE_FIR_EXTENDED_CHECKERS, Boolean.valueOf(arguments.getUseFirExtendedCheckers()));
        compilerConfiguration.put(CommonConfigurationKeys.EXPECT_ACTUAL_LINKER, Boolean.valueOf(arguments.getExpectActualLinker()));
        compilerConfiguration.putIfNotNull(CLIConfigurationKeys.INTELLIJ_PLUGIN_ROOT, arguments.getIntellijPluginRoot());
        compilerConfiguration.put(CommonConfigurationKeys.REPORT_OUTPUT_FILES, Boolean.valueOf(arguments.getReportOutputFiles()));
        compilerConfiguration.put(CommonConfigurationKeys.INCREMENTAL_COMPILATION, Boolean.valueOf(UtilsKt.incrementalCompilationIsEnabled(arguments)));
        compilerConfiguration.put(CommonConfigurationKeys.ALLOW_ANY_SCRIPTS_IN_SOURCE_ROOTS, Boolean.valueOf(arguments.getAllowAnyScriptsInSourceRoots()));
        compilerConfiguration.put(CommonConfigurationKeys.IGNORE_CONST_OPTIMIZATION_ERRORS, Boolean.valueOf(arguments.getIgnoreConstOptimizationErrors()));
        String metadataVersion = arguments.getMetadataVersion();
        if (metadataVersion != null) {
            Object versionArray = BinaryVersion.INSTANCE.parseVersionArray(metadataVersion);
            if (versionArray == null) {
                messageCollector.report(CompilerMessageSeverity.ERROR, "Invalid metadata version: " + metadataVersion, null);
            } else {
                if (function1 == null) {
                    throw new IllegalStateException("Unable to create metadata version: missing argument");
                }
                compilerConfiguration.put(CommonConfigurationKeys.METADATA_VERSION, function1.invoke(versionArray));
            }
        }
        switchToFallbackModeIfNecessary(arguments, messageCollector);
        setupLanguageVersionSettings(compilerConfiguration, arguments);
        compilerConfiguration.put(CommonConfigurationKeys.USE_FIR, Boolean.valueOf(arguments.getUseK2() || CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration).getLanguageVersion().getUsesK2()));
        compilerConfiguration.put(CommonConfigurationKeys.USE_LIGHT_TREE, Boolean.valueOf(arguments.getUseFirLT()));
        HmppCliModuleStructure hmppCliModuleStructureBuildHmppModuleStructure = buildHmppModuleStructure(compilerConfiguration, arguments);
        if (hmppCliModuleStructureBuildHmppModuleStructure != null) {
            compilerConfiguration.put(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE, hmppCliModuleStructureBuildHmppModuleStructure);
        }
    }

    private static final void switchToFallbackModeIfNecessary$warn(CommonCompilerArguments commonCompilerArguments, MessageCollector messageCollector, String str) {
        if (commonCompilerArguments.getSuppressVersionWarnings()) {
            return;
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, str, null, 4, null);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0032  */
    /* JADX WARN: Code duplicated, block: B:29:0x0051  */
    private static final void switchToFallbackModeIfNecessary(CommonCompilerArguments commonCompilerArguments, MessageCollector messageCollector) {
        boolean z;
        boolean z2;
        String str;
        boolean z3;
        boolean zStartsWith$default;
        if (commonCompilerArguments instanceof K2JVMCompilerArguments) {
            if (commonCompilerArguments.getUseK2()) {
                z = true;
            } else {
                String languageVersion = commonCompilerArguments.getLanguageVersion();
                if (languageVersion != null) {
                    zStartsWith$default = StringsKt.startsWith$default((CharSequence) languageVersion, '2', false, 2, (Object) null);
                } else {
                    zStartsWith$default = LanguageVersion.LATEST_STABLE.compareTo(LanguageVersion.KOTLIN_2_0) >= 0;
                }
                if (zStartsWith$default) {
                    z = true;
                } else {
                    z = false;
                }
            }
            String[] pluginOptions = commonCompilerArguments.getPluginOptions();
            if (pluginOptions != null) {
                int length = pluginOptions.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z3 = false;
                        break;
                    } else {
                        if (StringsKt.startsWith$default(pluginOptions[i], "plugin:org.jetbrains.kotlin.kapt3", false, 2, (Object) null)) {
                            z3 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (z3) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z && z2) {
                K2JVMCompilerArguments k2JVMCompilerArguments = (K2JVMCompilerArguments) commonCompilerArguments;
                if (!k2JVMCompilerArguments.getUseKapt4()) {
                    switchToFallbackModeIfNecessary$warn(commonCompilerArguments, messageCollector, "Kapt currently doesn't support language version 2.0+. Falling back to 1.9.");
                    commonCompilerArguments.setLanguageVersion(LanguageVersion.KOTLIN_1_9.getVersionString());
                    String apiVersion = commonCompilerArguments.getApiVersion();
                    if (apiVersion != null && StringsKt.startsWith$default(apiVersion, "2", false, 2, (Object) null)) {
                        commonCompilerArguments.setApiVersion(ApiVersion.KOTLIN_1_9.getVersionString());
                    }
                    commonCompilerArguments.setUseK2(false);
                    commonCompilerArguments.setSkipMetadataVersionCheck(true);
                    commonCompilerArguments.setSkipPrereleaseCheck(true);
                    k2JVMCompilerArguments.setAllowUnstableDependencies(true);
                    return;
                }
            }
            if (((K2JVMCompilerArguments) commonCompilerArguments).getUseKapt4()) {
                if (z) {
                    str = "K2 kapt is an experimental feature. Use with caution.";
                } else {
                    str = "-Xuse-kapt4 flag can be only used with language version 2.0+.";
                }
                switchToFallbackModeIfNecessary$warn(commonCompilerArguments, messageCollector, str);
            }
        }
    }

    public static final void setupLanguageVersionSettings(CompilerConfiguration compilerConfiguration, CommonCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        CommonConfigurationKeysKt.setLanguageVersionSettings(compilerConfiguration, arguments.toLanguageVersionSettings((MessageCollector) notNull));
    }

    public static final KotlinPaths computeKotlinPaths(MessageCollector messageCollector, CommonCompilerArguments arguments) {
        File file;
        KotlinPathsFromHomeDir kotlinPathsFromHomeDir;
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String property = System.getProperty(KOTLIN_HOME_PROPERTY);
        if (arguments.getKotlinHome() != null) {
            String kotlinHome = arguments.getKotlinHome();
            Intrinsics.checkNotNull(kotlinHome);
            file = new File(kotlinHome);
        } else {
            file = property != null ? new File(property) : null;
        }
        if (file == null) {
            kotlinPathsFromHomeDir = PathUtil.getKotlinPathsForCompiler();
        } else if (file.isDirectory()) {
            kotlinPathsFromHomeDir = new KotlinPathsFromHomeDir(file);
        } else {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Kotlin home does not exist or is not a directory: " + file, null);
            kotlinPathsFromHomeDir = null;
        }
        if (kotlinPathsFromHomeDir == null) {
            return null;
        }
        messageCollector.report(CompilerMessageSeverity.LOGGING, "Using Kotlin home directory " + kotlinPathsFromHomeDir.getHomePath(), null);
        return kotlinPathsFromHomeDir;
    }

    public static final void reportArgumentParseProblems(MessageCollector messageCollector, CommonToolArguments arguments) {
        Intrinsics.checkNotNullParameter(messageCollector, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        ArgumentParseErrors errors = arguments.getErrors();
        if (errors == null) {
            return;
        }
        Iterator<String> it = errors.getUnknownExtraFlags().iterator();
        while (it.hasNext()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Flag is not supported by this version of the compiler: " + it.next(), null, 4, null);
        }
        Iterator<String> it2 = errors.getExtraArgumentsPassedInObsoleteForm().iterator();
        while (it2.hasNext()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Advanced option value is passed in an obsolete form. Please use the '=' character to specify the value: " + it2.next() + "=...", null, 4, null);
        }
        for (Map.Entry<String, String> entry : errors.getDuplicateArguments().entrySet()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Argument " + entry.getKey() + " is passed multiple times. Only the last value will be used: " + entry.getValue(), null, 4, null);
        }
        for (Map.Entry<String, String> entry2 : errors.getDeprecatedArguments().entrySet()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Argument " + entry2.getKey() + " is deprecated. Please use " + entry2.getValue() + " instead", null, 4, null);
        }
        Iterator<String> it3 = errors.getArgfileErrors().iterator();
        while (it3.hasNext()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, it3.next(), null, 4, null);
        }
        reportUnsafeInternalArgumentsIfAny(messageCollector, arguments);
        Iterator<String> it4 = errors.getInternalArgumentsParsingProblems().iterator();
        while (it4.hasNext()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, it4.next(), null, 4, null);
        }
    }

    private static final void reportUnsafeInternalArgumentsIfAny(MessageCollector messageCollector, CommonToolArguments commonToolArguments) {
        List<InternalArgument> internalArguments = commonToolArguments.getInternalArguments();
        ArrayList arrayList = new ArrayList();
        for (Object obj : internalArguments) {
            InternalArgument internalArgument = (InternalArgument) obj;
            if (internalArgument instanceof ManualLanguageFeatureSetting) {
                ManualLanguageFeatureSetting manualLanguageFeatureSetting = (ManualLanguageFeatureSetting) internalArgument;
                if (manualLanguageFeatureSetting.getLanguageFeature().getKind() != LanguageFeature.Kind.BUG_FIX || manualLanguageFeatureSetting.getState() != LanguageFeature.State.ENABLED) {
                }
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "ATTENTION!\nThis build uses unsafe internal compiler arguments:\n" + CollectionsKt.joinToString$default(arrayList2, "\n", "\n", "\n\n", 0, null, new Function1<InternalArgument, CharSequence>() { // from class: org.jetbrains.kotlin.cli.common.ArgumentsKt$reportUnsafeInternalArgumentsIfAny$unsafeArgumentsString$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(InternalArgument it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getStringRepresentation();
            }
        }, 24, null) + "This mode is not recommended for production use,\nas no stability/compatibility guarantees are given on\ncompiler or generated code. Use it at your own risk!\n", null, 4, null);
    }

    private static final HmppCliModuleStructure buildHmppModuleStructure(CompilerConfiguration compilerConfiguration, CommonCompilerArguments commonCompilerArguments) {
        int i;
        boolean z;
        Pair pair;
        int i2;
        String[] fragments = commonCompilerArguments.getFragments();
        String[] fragmentSources = commonCompilerArguments.getFragmentSources();
        String[] fragmentRefines = commonCompilerArguments.getFragmentRefines();
        Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        if (fragments == null) {
            if (fragmentRefines != null) {
                buildHmppModuleStructure$reportError(messageCollector, "-Xfragment-refines flag can not be used without -Xfragments");
            }
            return null;
        }
        if (!CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration).getLanguageVersion().getUsesK2()) {
            buildHmppModuleStructure$reportWarning(messageCollector, "-Xfragments flag is not supported for language version < 2.0");
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(fragments.length), 16));
        for (String str : fragments) {
            linkedHashMap.put(str, new LinkedHashSet());
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        if (fragmentSources == null) {
            fragmentSources = new String[0];
        }
        int length = fragmentSources.length;
        int i3 = 0;
        while (true) {
            i = 1;
            if (i3 >= length) {
                break;
            }
            String str2 = fragmentSources[i3];
            List listSplit$default = StringsKt.split$default((CharSequence) str2, new String[]{":"}, false, 2, 2, (Object) null);
            if (listSplit$default.size() < 2) {
                buildHmppModuleStructure$reportError(messageCollector, "Incorrect syntax for -Xfragment-sources argument. `<module name>:<source file>` expected but got `" + str2 + '`');
            } else {
                String str3 = (String) listSplit$default.get(0);
                String str4 = (String) listSplit$default.get(1);
                Object obj = linkedHashMap2.get(str3);
                if (obj == null) {
                    buildHmppModuleStructure$reportError(messageCollector, "Passed " + str2 + ", but fragment `" + str3 + "` of source file " + str4 + " is not specified in -Xfragments");
                } else {
                    ((Set) obj).add(str4);
                }
            }
            i3++;
        }
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            arrayList.add(new HmppCliModule((String) entry.getKey(), (Set) entry.getValue()));
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i4 = 0;
        boolean z2 = false;
        while (i4 < size) {
            HmppCliModule hmppCliModule = (HmppCliModule) arrayList2.get(i4);
            i4++;
            int size2 = arrayList2.size();
            int i5 = i4;
            while (i5 < size2) {
                HmppCliModule hmppCliModule2 = (HmppCliModule) arrayList2.get(i5);
                Set setIntersect = CollectionsKt.intersect(hmppCliModule.getSources(), hmppCliModule2.getSources());
                if (!setIntersect.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    if (setIntersect.size() == i) {
                        sb.append("File '" + ((String) CollectionsKt.single(setIntersect)) + '\'');
                    } else {
                        sb.append("Files ");
                        sb.append(CollectionsKt.joinToString$default(setIntersect, ", ", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.cli.common.ArgumentsKt$buildHmppModuleStructure$message$1$1
                            @Override // kotlin.jvm.functions.Function1
                            public final CharSequence invoke(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return FormatHelper.QUOTE + it + '\'';
                            }
                        }, 30, null));
                    }
                    sb.append(" can be a part of only one module, but is listed as a source for both `" + hmppCliModule.getName() + "` and `" + hmppCliModule2.getName() + "`, please check you -Xfragment-sources options.");
                    String string = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    buildHmppModuleStructure$reportError(messageCollector, string);
                    z2 = true;
                }
                i5++;
                i = 1;
            }
        }
        for (String str5 : commonCompilerArguments.getFreeArgs()) {
            if (!StringsKt.endsWith$default(str5, SuffixConstants.SUFFIX_STRING_java, false, 2, (Object) null)) {
                ArrayList arrayList3 = arrayList2;
                if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                    Iterator it = arrayList3.iterator();
                    do {
                        if (it.hasNext()) {
                        }
                    } while (!((HmppCliModule) it.next()).getSources().contains(str5));
                }
                buildHmppModuleStructure$reportError(messageCollector, "Source '" + str5 + "' does not belong to any module");
                z2 = true;
                break;
            }
        }
        if (z2) {
            return null;
        }
        if (arrayList2.size() == 1) {
            if (fragmentRefines != null) {
                if (!(fragmentRefines.length == 0)) {
                    buildHmppModuleStructure$reportError(messageCollector, "-Xfragment-refines flag is specified but there is only one module declared");
                }
            }
            return new HmppCliModuleStructure(arrayList2, MapsKt.emptyMap());
        }
        ArrayList arrayList4 = arrayList2;
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : arrayList4) {
            HmppCliModule hmppCliModule3 = (HmppCliModule) obj2;
            if ((arrayList4 instanceof Collection) && arrayList4.isEmpty()) {
                i2 = 0;
            } else {
                Iterator it2 = arrayList4.iterator();
                i2 = 0;
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(((HmppCliModule) it2.next()).getName(), hmppCliModule3.getName()) && (i2 = i2 + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (i2 > 1) {
                arrayList5.add(obj2);
            }
        }
        ArrayList arrayList6 = arrayList5;
        if (!arrayList6.isEmpty()) {
            buildHmppModuleStructure$reportError(messageCollector, "There are multiple modules with same name(s): " + CollectionsKt.joinToString$default(CollectionsKt.distinct(arrayList6), ", ", null, null, 0, null, new Function1<HmppCliModule, CharSequence>() { // from class: org.jetbrains.kotlin.cli.common.ArgumentsKt.buildHmppModuleStructure.2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(HmppCliModule it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return it3.getName();
                }
            }, 30, null));
            return null;
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList4, 10)), 16));
        for (Object obj3 : arrayList4) {
            linkedHashMap3.put(((HmppCliModule) obj3).getName(), obj3);
        }
        String[] strArr = fragmentRefines == null ? new String[0] : fragmentRefines;
        ArrayList<Pair> arrayList7 = new ArrayList();
        for (String str6 : strArr) {
            List listSplit$default2 = StringsKt.split$default((CharSequence) str6, new String[]{":"}, false, 0, 6, (Object) null);
            if (listSplit$default2.size() != 2) {
                buildHmppModuleStructure$reportError(messageCollector, "Incorrect syntax for -Xfragment-refines argument. Expected <fromModuleName>:<onModuleName> but got `" + fragmentRefines + '`');
                pair = null;
            } else {
                String str7 = (String) listSplit$default2.get(0);
                String str8 = (String) listSplit$default2.get(1);
                HmppCliModule hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule = buildHmppModuleStructure$lambda$15$findModule(linkedHashMap3, str6, messageCollector, str7);
                HmppCliModule hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule2 = buildHmppModuleStructure$lambda$15$findModule(linkedHashMap3, str6, messageCollector, str8);
                pair = (hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule == null || hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule2 == null) ? null : TuplesKt.to(hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule, hmppCliModuleBuildHmppModuleStructure$lambda$15$findModule2);
            }
            if (pair != null) {
                arrayList7.add(pair);
            }
        }
        final LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        for (Pair pair2 : arrayList7) {
            HmppCliModule hmppCliModule4 = (HmppCliModule) pair2.getFirst();
            Object obj4 = linkedHashMap4.get(hmppCliModule4);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap4.put(hmppCliModule4, obj4);
            }
            ((List) obj4).add((HmppCliModule) pair2.getSecond());
        }
        List list = DFS.topologicalOrder(arrayList4, new DFS.Neighbors() { // from class: org.jetbrains.kotlin.cli.common.ArgumentsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj5) {
                return ArgumentsKt.buildHmppModuleStructure$lambda$18(linkedHashMap4, (HmppCliModule) obj5);
            }
        });
        Intrinsics.checkNotNullExpressionValue(list, "topologicalOrder(...)");
        List listAsReversedMutable = CollectionsKt.asReversedMutable(list);
        int i6 = 0;
        for (Object obj5 : listAsReversedMutable) {
            int i7 = i6 + 1;
            if (i6 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            HmppCliModule hmppCliModule5 = (HmppCliModule) obj5;
            List listEmptyList = (List) linkedHashMap4.get(hmppCliModule5);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            List listSubList = listAsReversedMutable.subList(0, i6);
            List list2 = listEmptyList;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it3 = list2.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z = false;
                        break;
                    }
                    if (!listSubList.contains((HmppCliModule) it3.next())) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (z) {
                buildHmppModuleStructure$reportError(messageCollector, "There is a cycle in dependencies of module `" + hmppCliModule5.getName() + '`');
            }
            i6 = i7;
        }
        return new HmppCliModuleStructure(listAsReversedMutable, linkedHashMap4);
    }

    private static final void buildHmppModuleStructure$reportError(MessageCollector messageCollector, String str) {
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, str, null, 4, null);
    }

    private static final void buildHmppModuleStructure$reportWarning(MessageCollector messageCollector, String str) {
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, str, null, 4, null);
    }

    private static final HmppCliModule buildHmppModuleStructure$lambda$15$findModule(Map<String, HmppCliModule> map, String str, MessageCollector messageCollector, String str2) {
        HmppCliModule hmppCliModule = map.get(str2);
        if (hmppCliModule == null) {
            buildHmppModuleStructure$reportError(messageCollector, "`-Xfragment-refines=" + str + "` Fragment `" + str2 + "` not found in -Xfragments arguments");
        }
        return hmppCliModule;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable buildHmppModuleStructure$lambda$18(Map dependenciesMap, HmppCliModule hmppCliModule) {
        Intrinsics.checkNotNullParameter(dependenciesMap, "$dependenciesMap");
        List listEmptyList = (List) dependenciesMap.get(hmppCliModule);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }
}
