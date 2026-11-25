package org.jetbrains.kotlin.cli.jvm;

import java.io.File;
import java.util.EnumSet;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.FilteringMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleBuilder;
import org.jetbrains.kotlin.cli.common.modules.ModuleChunk;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.CompileEnvironmentUtil;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;

/* JADX INFO: compiled from: K2JVMCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005\u001a\u001c\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f¨\u0006\r"}, d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V", "configureModuleChunk", "Lorg/jetbrains/kotlin/cli/common/modules/ModuleChunk;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "buildFile", "Ljava/io/File;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JVMCompilerKt {
    public static final ModuleChunk configureModuleChunk(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments, File file) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String destination = arguments.getDestination();
        File file2 = destination != null ? new File(destination) : null;
        if (file != null) {
            Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
            MessageCollector messageCollector = (MessageCollector) notNull;
            if (file2 != null) {
                configureModuleChunk$strongWarning(messageCollector, "The '-d' option with a directory destination is ignored because '-Xbuild-file' is specified");
            }
            if (arguments.getJavaSourceRoots() != null) {
                configureModuleChunk$strongWarning(messageCollector, "The '-Xjava-source-roots' option is ignored because '-Xbuild-file' is specified");
            }
            if (arguments.getJavaPackagePrefix() != null) {
                configureModuleChunk$strongWarning(messageCollector, "The '-Xjava-package-prefix' option is ignored because '-Xbuild-file' is specified");
            }
            JvmArgumentsKt.configureContentRootsFromClassPath(compilerConfiguration, arguments);
            final EnumSet<CompilerMessageSeverity> enumSet = CompilerMessageSeverity.VERBOSE;
            FilteringMessageCollector filteringMessageCollector = new FilteringMessageCollector(messageCollector, new Predicate() { // from class: org.jetbrains.kotlin.cli.jvm.K2JVMCompilerKt$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return enumSet.contains((CompilerMessageSeverity) obj);
                }
            });
            compilerConfiguration.put(JVMConfigurationKeys.MODULE_XML_FILE, file);
            ModuleChunk moduleChunkLoadModuleChunk = CompileEnvironmentUtil.loadModuleChunk(file, filteringMessageCollector);
            Intrinsics.checkNotNull(moduleChunkLoadModuleChunk);
            return moduleChunkLoadModuleChunk;
        }
        if (file2 != null) {
            String path = file2.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            if (StringsKt.endsWith$default(path, ".jar", false, 2, (Object) null)) {
                compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_JAR, file2);
            } else {
                compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_DIRECTORY, file2);
            }
        }
        String str = (String) compilerConfiguration.get(CommonConfigurationKeys.MODULE_NAME);
        if (str == null) {
            str = "main";
        }
        String path2 = file2 != null ? file2.getPath() : null;
        if (path2 == null) {
            path2 = ".";
        }
        ModuleBuilder moduleBuilder = new ModuleBuilder(str, path2, ModuleXmlParser.TYPE_PRODUCTION);
        CliCompilerUtilsKt.configureFromArgs(moduleBuilder, arguments);
        return new ModuleChunk(CollectionsKt.listOf(moduleBuilder));
    }

    private static final void configureModuleChunk$strongWarning(MessageCollector messageCollector, String str) {
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, str, null, 4, null);
    }

    public static final void main(String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        K2JVMCompiler.INSTANCE.main(args);
    }
}
