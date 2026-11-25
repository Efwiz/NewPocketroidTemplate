package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: LightClassConstructionContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/LightClassConstructionContext;", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/config/JvmTarget;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LightClassConstructionContext {
    private final BindingContext bindingContext;
    private final JvmTarget jvmTarget;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor module;

    public LightClassConstructionContext(BindingContext bindingContext, ModuleDescriptor module, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        this.bindingContext = bindingContext;
        this.module = module;
        this.languageVersionSettings = languageVersionSettings;
        this.jvmTarget = jvmTarget;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final JvmTarget getJvmTarget() {
        return this.jvmTarget;
    }
}
