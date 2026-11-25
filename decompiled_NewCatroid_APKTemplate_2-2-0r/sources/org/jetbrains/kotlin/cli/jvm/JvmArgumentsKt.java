package org.jetbrains.kotlin.cli.jvm;

import com.reandroid.apk.UncompressedFiles;
import com.reandroid.arsc.chunk.SpecBlock;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMAssertionsMode;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmAbiStability;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmClosureGenerationScheme;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.config.JvmStringConcat;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleKt;
import org.jetbrains.kotlin.utils.KotlinPaths;

/* JADX INFO: compiled from: jvmArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0018\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0000\u001a\u00020\u0001H\u0002\u001a$\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u0012\u0010\u0011\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0015\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0016\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\u001a\n\u0010\u0017\u001a\u00020\u000b*\u00020\u0002\u001a\u0012\u0010\u0018\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\u001ag\u0010\u0019\u001a\u00020\u000b\"\b\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u00022\b\u0010\u001c\u001a\u0004\u0018\u0001H\u001a2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00100\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00100\u001e2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00100\u001e2\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010!\u001a\u001c\u0010\u0019\u001a\u00020\u000b*\u00020\u00022\b\u0010\u001c\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0012\u001a\u00020\u0013\u001a,\u0010#\u001a\u00020\u000b*\u00020\u00022\u0006\u0010$\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010\t2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0002\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0002\u001a\u0012\u0010)\u001a\u00020\r*\u00020*2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010+\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006,"}, d2 = {"messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getMessageCollector", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getJavaVersion", "", "parseBackendThreads", "stringValue", "", "addModularRootIfNotNull", "", "isModularJava", "", "moduleName", "file", "Ljava/io/File;", "configureAdvancedJvmOptions", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "configureContentRootsFromClassPath", "configureJavaModulesContentRoots", "configureJdkHome", "configureJdkHomeFromSystemProperty", "configureKlibPaths", "configureStandardLibs", "PathProvider", "", UncompressedFiles.NAME_paths, "stdlibPath", "Lkotlin/Function1;", "scriptRuntimePath", "reflectPath", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;)V", "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "handleClosureGenerationSchemeArgument", SpecBlock.NAME_flag, "value", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "Lorg/jetbrains/kotlin/config/JvmClosureGenerationScheme;", "registerJavacIfNeeded", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "setupJvmSpecificArguments", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmArgumentsKt {
    public static final void setupJvmSpecificArguments(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Integer intOrNull;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        compilerConfiguration.put(JVMConfigurationKeys.INCLUDE_RUNTIME, Boolean.valueOf(arguments.getIncludeRuntime()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_REFLECT, Boolean.valueOf(arguments.getNoReflect()));
        CompilerConfigurationKey<List<String>> compilerConfigurationKey = JVMConfigurationKeys.FRIEND_PATHS;
        String[] friendPaths = arguments.getFriendPaths();
        compilerConfiguration.putIfNotNull(compilerConfigurationKey, friendPaths != null ? ArraysKt.asList(friendPaths) : null);
        String jdkRelease = arguments.getJdkRelease();
        String jvmTarget = arguments.getJvmTarget();
        if (jdkRelease != null) {
            if (Intrinsics.areEqual(jdkRelease, CompilerOptions.VERSION_1_6)) {
                intOrNull = 6;
            } else {
                intOrNull = Intrinsics.areEqual(jdkRelease, CompilerOptions.VERSION_1_8) ? 8 : StringsKt.toIntOrNull(jdkRelease);
            }
            if (intOrNull == null || intOrNull.intValue() < 6) {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "Unknown JDK release version: " + jdkRelease, null, 4, null);
            } else {
                if (intOrNull.intValue() != getJavaVersion() || arguments.getJdkHome() != null) {
                    compilerConfiguration.put(JVMConfigurationKeys.JDK_RELEASE, intOrNull);
                }
                if (jvmTarget != null && !Intrinsics.areEqual(jvmTarget, jdkRelease)) {
                    MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "'-Xjdk-release=" + jdkRelease + "' option conflicts with '-jvm-target " + jvmTarget + "'. Please remove the '-jvm-target' option", null, 4, null);
                }
            }
        }
        if (Intrinsics.areEqual(jdkRelease, "6")) {
            jdkRelease = CompilerOptions.VERSION_1_6;
        } else if (Intrinsics.areEqual(jdkRelease, "8")) {
            jdkRelease = CompilerOptions.VERSION_1_8;
        } else if (jdkRelease == null) {
            jdkRelease = jvmTarget;
        }
        if (jdkRelease != null) {
            JvmTarget jvmTargetFromString = JvmTarget.INSTANCE.fromString(jdkRelease);
            if (jvmTargetFromString != null) {
                compilerConfiguration.put(JVMConfigurationKeys.JVM_TARGET, jvmTargetFromString);
                if (jvmTargetFromString == JvmTarget.JVM_1_6) {
                    MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "JVM target 1.6 is no longer supported. Please migrate to JVM target 1.8 or above", null, 4, null);
                }
            } else {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "Unknown JVM target version: " + jdkRelease + "\nSupported versions: " + ArraysKt.joinToString$default(JvmTarget.values(), (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<JvmTarget, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.setupJvmSpecificArguments.1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(JvmTarget it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getDescription();
                    }
                }, 31, (Object) null), null, 4, null);
            }
        }
        JvmDefaultMode jvmDefaultMode = (JvmDefaultMode) CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration).getFlag(JvmAnalysisFlags.getJvmDefaultMode());
        JvmTarget jvmTarget2 = (JvmTarget) compilerConfiguration.get(JVMConfigurationKeys.JVM_TARGET);
        if (jvmTarget2 == null) {
            jvmTarget2 = JvmTarget.DEFAULT;
        }
        Intrinsics.checkNotNull(jvmTarget2);
        if (jvmDefaultMode == JvmDefaultMode.ENABLE || jvmDefaultMode == JvmDefaultMode.ENABLE_WITH_DEFAULT_IMPLS) {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.WARNING, "'-Xjvm-default=" + jvmDefaultMode.getDescription() + "' is deprecated, please use '-Xjvm-default=all|all-compatibility'", null, 4, null);
        }
        String stringConcat = arguments.getStringConcat();
        if (stringConcat != null) {
            JvmStringConcat jvmStringConcatFromString = JvmStringConcat.INSTANCE.fromString(stringConcat);
            if (jvmStringConcatFromString != null) {
                compilerConfiguration.put(JVMConfigurationKeys.STRING_CONCAT, jvmStringConcatFromString);
                if (jvmTarget2.getMajorVersion() < JvmTarget.JVM_9.getMajorVersion() && jvmStringConcatFromString != JvmStringConcat.INLINE) {
                    MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.WARNING, "`-Xstring-concat=" + stringConcat + "` does nothing with JVM target `" + jvmTarget2.getDescription() + "`.", null, 4, null);
                }
            } else {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "Unknown `-Xstring-concat` mode: " + stringConcat + "\nSupported modes: " + ArraysKt.joinToString$default(JvmStringConcat.values(), (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<JvmStringConcat, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.setupJvmSpecificArguments.2
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(JvmStringConcat it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getDescription();
                    }
                }, 31, (Object) null), null, 4, null);
            }
        }
        String samConversions = arguments.getSamConversions();
        CompilerConfigurationKey<JvmClosureGenerationScheme> SAM_CONVERSIONS = JVMConfigurationKeys.SAM_CONVERSIONS;
        Intrinsics.checkNotNullExpressionValue(SAM_CONVERSIONS, "SAM_CONVERSIONS");
        handleClosureGenerationSchemeArgument(compilerConfiguration, "-Xsam-conversions", samConversions, SAM_CONVERSIONS);
        String lambdas = arguments.getLambdas();
        CompilerConfigurationKey<JvmClosureGenerationScheme> LAMBDAS = JVMConfigurationKeys.LAMBDAS;
        Intrinsics.checkNotNullExpressionValue(LAMBDAS, "LAMBDAS");
        handleClosureGenerationSchemeArgument(compilerConfiguration, "-Xlambdas", lambdas, LAMBDAS);
        CompilerConfigurationKey<List<String>> compilerConfigurationKey2 = JVMConfigurationKeys.ADDITIONAL_JAVA_MODULES;
        String[] additionalJavaModules = arguments.getAdditionalJavaModules();
        compilerConfiguration.addAll(compilerConfigurationKey2, additionalJavaModules != null ? ArraysKt.asList(additionalJavaModules) : null);
    }

    private static final void handleClosureGenerationSchemeArgument(CompilerConfiguration compilerConfiguration, String str, String str2, CompilerConfigurationKey<JvmClosureGenerationScheme> compilerConfigurationKey) {
        if (str2 == null) {
            return;
        }
        JvmClosureGenerationScheme jvmClosureGenerationSchemeFromString = JvmClosureGenerationScheme.INSTANCE.fromString(str2);
        if (jvmClosureGenerationSchemeFromString != null) {
            compilerConfiguration.put(compilerConfigurationKey, jvmClosureGenerationSchemeFromString);
        } else {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "Unknown `" + str + "` argument: " + str2 + "\n.Supported arguments: " + ArraysKt.joinToString$default(JvmClosureGenerationScheme.values(), (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<JvmClosureGenerationScheme, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.handleClosureGenerationSchemeArgument.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(JvmClosureGenerationScheme it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getDescription();
                }
            }, 31, (Object) null), null, 4, null);
        }
    }

    public static final boolean configureJdkHome(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (arguments.getNoJdk()) {
            compilerConfiguration.put(JVMConfigurationKeys.NO_JDK, true);
            if (arguments.getJdkHome() != null) {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.STRONG_WARNING, "The '-jdk-home' option is ignored because '-no-jdk' is specified", null, 4, null);
            }
            return true;
        }
        if (arguments.getJdkHome() != null) {
            String jdkHome = arguments.getJdkHome();
            Intrinsics.checkNotNull(jdkHome);
            File file = new File(jdkHome);
            if (!file.exists()) {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "JDK home directory does not exist: " + file, null, 4, null);
                return false;
            }
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.LOGGING, "Using JDK home directory " + file, null, 4, null);
            compilerConfiguration.put(JVMConfigurationKeys.JDK_HOME, file);
        } else {
            configureJdkHomeFromSystemProperty(compilerConfiguration);
        }
        return true;
    }

    public static final void configureJdkHomeFromSystemProperty(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        File file = new File(System.getProperty("java.home"));
        MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.LOGGING, "Using JDK home inferred from java.home: " + file, null, 4, null);
        compilerConfiguration.put(JVMConfigurationKeys.JDK_HOME, file);
    }

    public static final void configureJavaModulesContentRoots(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String javaModulePath = arguments.getJavaModulePath();
        List listSplit$default = javaModulePath != null ? StringsKt.split$default((CharSequence) javaModulePath, new char[]{File.pathSeparatorChar}, false, 0, 6, (Object) null) : null;
        if (listSplit$default == null) {
            listSplit$default = CollectionsKt.emptyList();
        }
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JvmModulePathRoot(new File((String) it.next())));
        }
    }

    public static final void configureContentRootsFromClassPath(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String classpath = arguments.getClasspath();
        List listSplit$default = classpath != null ? StringsKt.split$default((CharSequence) classpath, new char[]{File.pathSeparatorChar}, false, 0, 6, (Object) null) : null;
        if (listSplit$default == null) {
            listSplit$default = CollectionsKt.emptyList();
        }
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JvmClasspathRoot(new File((String) it.next())));
        }
    }

    public static final void configureStandardLibs(CompilerConfiguration compilerConfiguration, KotlinPaths kotlinPaths, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        configureStandardLibs(compilerConfiguration, kotlinPaths, new PropertyReference1() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.configureStandardLibs.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getStdlibPath()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((KotlinPaths) obj).getStdlibPath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "stdlibPath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KotlinPaths.class);
            }
        }, new PropertyReference1() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.configureStandardLibs.2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getScriptRuntimePath()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((KotlinPaths) obj).getScriptRuntimePath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "scriptRuntimePath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KotlinPaths.class);
            }
        }, new PropertyReference1() { // from class: org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt.configureStandardLibs.3
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getReflectPath()Ljava/io/File;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((KotlinPaths) obj).getReflectPath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "reflectPath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KotlinPaths.class);
            }
        }, arguments);
    }

    public static final <PathProvider> void configureStandardLibs(CompilerConfiguration compilerConfiguration, PathProvider pathprovider, Function1<? super PathProvider, ? extends File> stdlibPath, Function1<? super PathProvider, ? extends File> scriptRuntimePath, Function1<? super PathProvider, ? extends File> reflectPath, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(stdlibPath, "stdlibPath");
        Intrinsics.checkNotNullParameter(scriptRuntimePath, "scriptRuntimePath");
        Intrinsics.checkNotNullParameter(reflectPath, "reflectPath");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Integer num = (Integer) compilerConfiguration.get(JVMConfigurationKeys.JDK_RELEASE);
        boolean z = isModularJava(compilerConfiguration) && (num == null || num.intValue() >= 9);
        if (!arguments.getNoStdlib()) {
            boolean z2 = z;
            configureStandardLibs$addRoot(compilerConfiguration, z2, pathprovider, JavaModuleKt.KOTLIN_STDLIB_MODULE_NAME, "kotlin-stdlib.jar", stdlibPath, "'-no-stdlib'");
            configureStandardLibs$addRoot(compilerConfiguration, z2, pathprovider, "kotlin.script.runtime", "kotlin-script-runtime.jar", scriptRuntimePath, "'-no-stdlib'");
        }
        if (arguments.getNoReflect() || arguments.getNoStdlib()) {
            return;
        }
        configureStandardLibs$addRoot(compilerConfiguration, z, pathprovider, "kotlin.reflect", "kotlin-reflect.jar", reflectPath, "'-no-reflect' or '-no-stdlib'");
    }

    private static final <PathProvider> void configureStandardLibs$addRoot(CompilerConfiguration compilerConfiguration, boolean z, PathProvider pathprovider, String str, String str2, Function1<? super PathProvider, ? extends File> function1, String str3) {
        addModularRootIfNotNull(compilerConfiguration, z, str, UtilsKt.getLibraryFromHome(pathprovider, function1, str2, getMessageCollector(compilerConfiguration), str3));
    }

    public static final boolean isModularJava(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        File file = (File) compilerConfiguration.get(JVMConfigurationKeys.JDK_HOME);
        if (file != null) {
            return CoreJrtFileSystem.INSTANCE.isModularJdk(file);
        }
        return false;
    }

    public static final void addModularRootIfNotNull(CompilerConfiguration compilerConfiguration, boolean z, String moduleName, File file) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        if (file != null) {
            if (z) {
                compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JvmModulePathRoot(file));
                compilerConfiguration.add(JVMConfigurationKeys.ADDITIONAL_JAVA_MODULES, moduleName);
            } else {
                compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JvmClasspathRoot(file));
            }
        }
    }

    public static final boolean registerJavacIfNeeded(KotlinCoreEnvironment kotlinCoreEnvironment, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(kotlinCoreEnvironment, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (!arguments.getUseJavac()) {
            return true;
        }
        kotlinCoreEnvironment.getConfiguration().put(JVMConfigurationKeys.USE_JAVAC, true);
        if (arguments.getCompileJava()) {
            kotlinCoreEnvironment.getConfiguration().put(JVMConfigurationKeys.COMPILE_JAVA, true);
        }
        return KotlinCoreEnvironment.registerJavac$default(kotlinCoreEnvironment, null, null, arguments.getJavacArguments(), null, null, 27, null);
    }

    public static final void configureAdvancedJvmOptions(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        compilerConfiguration.put(JVMConfigurationKeys.PARAMETERS_METADATA, Boolean.valueOf(arguments.getJavaParameters()));
        boolean z = arguments.getUseK2() || CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration).getLanguageVersion().getUsesK2() || !arguments.getUseOldBackend();
        MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.LOGGING, "Using " + (z ? "JVM IR" : "old JVM") + " backend", null, 4, null);
        compilerConfiguration.put(JVMConfigurationKeys.IR, Boolean.valueOf(z));
        JvmAbiStability jvmAbiStabilityFromStringOrNull = JvmAbiStability.INSTANCE.fromStringOrNull(arguments.getAbiStability());
        if (arguments.getAbiStability() != null) {
            if (jvmAbiStabilityFromStringOrNull == null) {
                MessageCollector messageCollector = getMessageCollector(compilerConfiguration);
                CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
                StringBuilder sbAppend = new StringBuilder("Unknown ABI stability mode: ").append(arguments.getAbiStability()).append(", supported modes: ");
                JvmAbiStability[] jvmAbiStabilityArrValues = JvmAbiStability.values();
                ArrayList arrayList = new ArrayList(jvmAbiStabilityArrValues.length);
                for (JvmAbiStability jvmAbiStability : jvmAbiStabilityArrValues) {
                    arrayList.add(jvmAbiStability.getDescription());
                }
                MessageCollector.report$default(messageCollector, compilerMessageSeverity, sbAppend.append(arrayList).toString(), null, 4, null);
            } else if (!z && jvmAbiStabilityFromStringOrNull == JvmAbiStability.UNSTABLE) {
                MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.ERROR, "-Xabi-stability=unstable is not supported in the old JVM backend", null, 4, null);
            } else {
                compilerConfiguration.put(JVMConfigurationKeys.ABI_STABILITY, jvmAbiStabilityFromStringOrNull);
            }
        }
        compilerConfiguration.put(JVMConfigurationKeys.DO_NOT_CLEAR_BINDING_CONTEXT, Boolean.valueOf(arguments.getDoNotClearBindingContext()));
        compilerConfiguration.put(JVMConfigurationKeys.DISABLE_CALL_ASSERTIONS, Boolean.valueOf(arguments.getNoCallAssertions()));
        compilerConfiguration.put(JVMConfigurationKeys.DISABLE_RECEIVER_ASSERTIONS, Boolean.valueOf(arguments.getNoReceiverAssertions()));
        compilerConfiguration.put(JVMConfigurationKeys.DISABLE_PARAM_ASSERTIONS, Boolean.valueOf(arguments.getNoParamAssertions()));
        compilerConfiguration.put(JVMConfigurationKeys.DISABLE_OPTIMIZATION, Boolean.valueOf(arguments.getNoOptimize()));
        compilerConfiguration.put(JVMConfigurationKeys.EMIT_JVM_TYPE_ANNOTATIONS, Boolean.valueOf(arguments.getEmitJvmTypeAnnotations()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_OPTIMIZED_CALLABLE_REFERENCES, Boolean.valueOf(arguments.getNoOptimizedCallableReferences()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_KOTLIN_NOTHING_VALUE_EXCEPTION, Boolean.valueOf(arguments.getNoKotlinNothingValueException()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_RESET_JAR_TIMESTAMPS, Boolean.valueOf(arguments.getNoResetJarTimestamps()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_UNIFIED_NULL_CHECKS, Boolean.valueOf(arguments.getNoUnifiedNullChecks()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_SOURCE_DEBUG_EXTENSION, Boolean.valueOf(arguments.getNoSourceDebugExtension()));
        compilerConfiguration.put(JVMConfigurationKeys.SERIALIZE_IR, JvmSerializeIrMode.INSTANCE.fromString(arguments.getSerializeIr()));
        compilerConfiguration.put(JVMConfigurationKeys.VALIDATE_IR, Boolean.valueOf(arguments.getValidateIr()));
        compilerConfiguration.put(JVMConfigurationKeys.VALIDATE_BYTECODE, Boolean.valueOf(arguments.getValidateBytecode()));
        compilerConfiguration.put(JVMConfigurationKeys.LINK_VIA_SIGNATURES, Boolean.valueOf(arguments.getLinkViaSignatures()));
        compilerConfiguration.put(JVMConfigurationKeys.ENABLE_DEBUG_MODE, Boolean.valueOf(arguments.getEnableDebugMode()));
        compilerConfiguration.put(JVMConfigurationKeys.NO_NEW_JAVA_ANNOTATION_TARGETS, Boolean.valueOf(arguments.getNoNewJavaAnnotationTargets()));
        compilerConfiguration.put(JVMConfigurationKeys.OLD_INNER_CLASSES_LOGIC, Boolean.valueOf(arguments.getOldInnerClassesLogic()));
        compilerConfiguration.put(JVMConfigurationKeys.ENABLE_IR_INLINER, Boolean.valueOf(arguments.getEnableIrInliner()));
        JVMAssertionsMode jVMAssertionsModeFromStringOrNull = JVMAssertionsMode.INSTANCE.fromStringOrNull(arguments.getAssertionsMode());
        if (jVMAssertionsModeFromStringOrNull == null) {
            MessageCollector messageCollector2 = getMessageCollector(compilerConfiguration);
            CompilerMessageSeverity compilerMessageSeverity2 = CompilerMessageSeverity.ERROR;
            StringBuilder sbAppend2 = new StringBuilder("Unknown assertions mode: ").append(arguments.getAssertionsMode()).append(", supported modes: ");
            JVMAssertionsMode[] jVMAssertionsModeArrValues = JVMAssertionsMode.values();
            ArrayList arrayList2 = new ArrayList(jVMAssertionsModeArrValues.length);
            for (JVMAssertionsMode jVMAssertionsMode : jVMAssertionsModeArrValues) {
                arrayList2.add(jVMAssertionsMode.getDescription());
            }
            MessageCollector.report$default(messageCollector2, compilerMessageSeverity2, sbAppend2.append(arrayList2).toString(), null, 4, null);
        }
        CompilerConfigurationKey<JVMAssertionsMode> compilerConfigurationKey = JVMConfigurationKeys.ASSERTIONS_MODE;
        if (jVMAssertionsModeFromStringOrNull == null) {
            jVMAssertionsModeFromStringOrNull = JVMAssertionsMode.DEFAULT;
        }
        compilerConfiguration.put(compilerConfigurationKey, jVMAssertionsModeFromStringOrNull);
        compilerConfiguration.put(JVMConfigurationKeys.USE_TYPE_TABLE, Boolean.valueOf(arguments.getUseTypeTable()));
        compilerConfiguration.put(JVMConfigurationKeys.USE_PSI_CLASS_FILES_READING, Boolean.valueOf(arguments.getUseOldClassFilesReading()));
        compilerConfiguration.put(JVMConfigurationKeys.USE_FAST_JAR_FILE_SYSTEM, Boolean.valueOf(arguments.getUseFastJarFileSystem()));
        if (arguments.getUseOldClassFilesReading()) {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.INFO, "Using the old java class files reading implementation", null, 4, null);
        }
        if (arguments.getUseFastJarFileSystem()) {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.INFO, "Using fast Jar FS implementation", null, 4, null);
        }
        compilerConfiguration.put(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE, Boolean.valueOf(arguments.getAllowKotlinPackage()));
        compilerConfiguration.put(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME, Boolean.valueOf(arguments.getRenderInternalDiagnosticNames()));
        compilerConfiguration.put(JVMConfigurationKeys.USE_OLD_INLINE_CLASSES_MANGLING_SCHEME, Boolean.valueOf(arguments.getUseOldInlineClassesManglingScheme()));
        compilerConfiguration.put(JVMConfigurationKeys.ENABLE_JVM_PREVIEW, Boolean.valueOf(arguments.getEnableJvmPreview()));
        if (arguments.getEnableJvmPreview()) {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.INFO, "Using preview Java language features", null, 4, null);
        }
        String declarationsOutputPath = arguments.getDeclarationsOutputPath();
        if (declarationsOutputPath != null) {
            compilerConfiguration.put(JVMConfigurationKeys.DECLARATIONS_JSON_PATH, declarationsOutputPath);
        }
        int backendThreads = parseBackendThreads(arguments.getBackendThreads(), getMessageCollector(compilerConfiguration));
        if (backendThreads == 0) {
            backendThreads = Runtime.getRuntime().availableProcessors();
        }
        if (backendThreads > 1) {
            MessageCollector.report$default(getMessageCollector(compilerConfiguration), CompilerMessageSeverity.LOGGING, "Running backend in parallel with " + backendThreads + " threads", null, 4, null);
        }
        compilerConfiguration.put(CommonConfigurationKeys.PARALLEL_BACKEND_THREADS, Integer.valueOf(backendThreads));
    }

    private static final int parseBackendThreads(String str, MessageCollector messageCollector) {
        Integer intOrNull = StringsKt.toIntOrNull(str);
        if (intOrNull == null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Cannot parse -Xbackend-threads value: \"" + str + "\". Please use an integer number", null, 4, null);
            return 1;
        }
        if (intOrNull.intValue() < 0) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "-Xbackend-threads value cannot be negative", null, 4, null);
            return 1;
        }
        return intOrNull.intValue();
    }

    public static final void configureKlibPaths(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String klibLibraries = arguments.getKlibLibraries();
        if (klibLibraries == null) {
            return;
        }
        CompilerConfigurationKey<List<String>> compilerConfigurationKey = JVMConfigurationKeys.KLIB_PATHS;
        String pathSeparator = File.pathSeparator;
        Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
        List<String> listSplit = new Regex(pathSeparator).split(klibLibraries, 0);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit) {
            if (((String) obj).length() != 0) {
                arrayList.add(obj);
            }
        }
        compilerConfiguration.put(compilerConfigurationKey, arrayList);
    }

    private static final MessageCollector getMessageCollector(CompilerConfiguration compilerConfiguration) {
        Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        return (MessageCollector) notNull;
    }

    private static final int getJavaVersion() {
        String strSubstringAfter$default;
        Integer intOrNull;
        String property = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION);
        if (property == null || (strSubstringAfter$default = StringsKt.substringAfter$default(property, '.', (String) null, 2, (Object) null)) == null || (intOrNull = StringsKt.toIntOrNull(strSubstringAfter$default)) == null) {
            return 6;
        }
        return intOrNull.intValue();
    }
}
