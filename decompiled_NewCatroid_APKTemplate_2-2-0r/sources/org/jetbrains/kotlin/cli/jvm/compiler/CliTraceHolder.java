package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.jvm.JvmCodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;

/* JADX INFO: compiled from: CliTrace.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J0\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\bH\u0016R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmCodeAnalyzerInitializer;", "()V", "<set-?>", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "bindingContext", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "Lorg/jetbrains/kotlin/config/JvmTarget;", "jvmTarget", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "languageVersionSettings", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "module", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "createTrace", "Lorg/jetbrains/kotlin/resolve/BindingTraceContext;", "initialize", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "codeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliTraceHolder extends JvmCodeAnalyzerInitializer {
    private BindingContext bindingContext;
    private JvmTarget jvmTarget;
    private LanguageVersionSettings languageVersionSettings;
    private ModuleDescriptor module;

    public final BindingContext getBindingContext() {
        BindingContext bindingContext = this.bindingContext;
        if (bindingContext != null) {
            return bindingContext;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bindingContext");
        return null;
    }

    public final ModuleDescriptor getModule() {
        ModuleDescriptor moduleDescriptor = this.module;
        if (moduleDescriptor != null) {
            return moduleDescriptor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("module");
        return null;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
        if (languageVersionSettings != null) {
            return languageVersionSettings;
        }
        Intrinsics.throwUninitializedPropertyAccessException("languageVersionSettings");
        return null;
    }

    public final JvmTarget getJvmTarget() {
        JvmTarget jvmTarget = this.jvmTarget;
        if (jvmTarget != null) {
            return jvmTarget;
        }
        Intrinsics.throwUninitializedPropertyAccessException("jvmTarget");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.JvmCodeAnalyzerInitializer
    public void initialize(BindingTrace trace, ModuleDescriptor module, KotlinCodeAnalyzer codeAnalyzer, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(codeAnalyzer, "codeAnalyzer");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        this.bindingContext = context;
        this.module = module;
        this.languageVersionSettings = languageVersionSettings;
        this.jvmTarget = jvmTarget;
        if (!(trace instanceof CliBindingTrace)) {
            throw new IllegalArgumentException("Shared trace is expected to be subclass of CliBindingTrace class");
        }
        ((CliBindingTrace) trace).setKotlinCodeAnalyzer(codeAnalyzer);
    }

    @Override // org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer
    public BindingTraceContext createTrace() {
        return new NoScopeRecordCliBindingTrace();
    }
}
