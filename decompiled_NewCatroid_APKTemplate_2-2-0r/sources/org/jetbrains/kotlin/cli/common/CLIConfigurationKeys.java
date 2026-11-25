package org.jetbrains.kotlin.cli.common;

import java.io.File;
import java.util.List;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;

/* JADX INFO: loaded from: classes3.dex */
public class CLIConfigurationKeys {
    public static final CompilerConfigurationKey<List<ContentRoot>> CONTENT_ROOTS = CompilerConfigurationKey.create("content roots");
    public static final CompilerConfigurationKey<MessageCollector> MESSAGE_COLLECTOR_KEY = CompilerConfigurationKey.create("message collector");
    public static final CompilerConfigurationKey<MessageCollector> ORIGINAL_MESSAGE_COLLECTOR_KEY = CompilerConfigurationKey.create("original message collector");
    public static final CompilerConfigurationKey<Boolean> RENDER_DIAGNOSTIC_INTERNAL_NAME = CompilerConfigurationKey.create("render diagnostic internal name");
    public static final CompilerConfigurationKey<Boolean> ALLOW_KOTLIN_PACKAGE = CompilerConfigurationKey.create("allow kotlin package");
    public static final CompilerConfigurationKey<CommonCompilerPerformanceManager> PERF_MANAGER = CompilerConfigurationKey.create("performance manager");
    public static final CompilerConfigurationKey<String> INTELLIJ_PLUGIN_ROOT = CompilerConfigurationKey.create("intellij plugin root");
    public static final CompilerConfigurationKey<File> METADATA_DESTINATION_DIRECTORY = CompilerConfigurationKey.create("metadata destination directory");
    public static final CompilerConfigurationKey<PhaseConfig> PHASE_CONFIG = CompilerConfigurationKey.create("phase configuration");
    public static final CompilerConfigurationKey<PhaseConfigurationService> FLEXIBLE_PHASE_CONFIG = CompilerConfigurationKey.create("flexible phase configuration");
    public static final CompilerConfigurationKey<Integer> REPEAT_COMPILE_MODULES = CompilerConfigurationKey.create("debug key for profiling, repeats compileModules");
    public static final CompilerConfigurationKey<File> PATH_TO_KOTLIN_COMPILER_JAR = CompilerConfigurationKey.create("jar of Kotlin compiler in Kotlin plugin");

    private CLIConfigurationKeys() {
    }
}
