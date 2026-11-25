package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: compiled from: ClassifierUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "Lorg/jetbrains/kotlin/resolve/checkers/CheckerContext;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "getDeprecationResolver", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassifierUsageCheckerContext implements CheckerContext {
    private final DeprecationResolver deprecationResolver;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final BindingTrace trace;

    public ClassifierUsageCheckerContext(BindingTrace trace, LanguageVersionSettings languageVersionSettings, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.trace = trace;
        this.languageVersionSettings = languageVersionSettings;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public BindingTrace getTrace() {
        return this.trace;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public DeprecationResolver getDeprecationResolver() {
        return this.deprecationResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }
}
