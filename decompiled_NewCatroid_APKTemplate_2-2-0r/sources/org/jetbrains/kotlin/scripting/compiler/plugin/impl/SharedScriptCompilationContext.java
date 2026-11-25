package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: compilationContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "baseScriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "ignoredOptionsReportingState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;", "(Lcom/intellij/openapi/Disposable;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;)V", "getBaseScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getDisposable", "()Lcom/intellij/openapi/Disposable;", "getEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getIgnoredOptionsReportingState", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SharedScriptCompilationContext {
    private final ScriptCompilationConfiguration baseScriptCompilationConfiguration;
    private final Disposable disposable;
    private final KotlinCoreEnvironment environment;
    private final IgnoredOptionsReportingState ignoredOptionsReportingState;

    public SharedScriptCompilationContext(Disposable disposable, ScriptCompilationConfiguration baseScriptCompilationConfiguration, KotlinCoreEnvironment environment, IgnoredOptionsReportingState ignoredOptionsReportingState) {
        Intrinsics.checkNotNullParameter(baseScriptCompilationConfiguration, "baseScriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(ignoredOptionsReportingState, "ignoredOptionsReportingState");
        this.disposable = disposable;
        this.baseScriptCompilationConfiguration = baseScriptCompilationConfiguration;
        this.environment = environment;
        this.ignoredOptionsReportingState = ignoredOptionsReportingState;
    }

    public final Disposable getDisposable() {
        return this.disposable;
    }

    public final ScriptCompilationConfiguration getBaseScriptCompilationConfiguration() {
        return this.baseScriptCompilationConfiguration;
    }

    public final KotlinCoreEnvironment getEnvironment() {
        return this.environment;
    }

    public final IgnoredOptionsReportingState getIgnoredOptionsReportingState() {
        return this.ignoredOptionsReportingState;
    }
}
