package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: compiled from: DeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Lorg/jetbrains/kotlin/resolve/checkers/CheckerContext;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;)V", "getDeprecationResolver", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getExpectActualTracker", "()Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getMissingSupertypesResolver", "()Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationCheckerContext implements CheckerContext {
    private final DeprecationResolver deprecationResolver;
    private final ExpectActualTracker expectActualTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final BindingTrace trace;

    public DeclarationCheckerContext(BindingTrace trace, LanguageVersionSettings languageVersionSettings, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, ExpectActualTracker expectActualTracker, MissingSupertypesResolver missingSupertypesResolver) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        this.trace = trace;
        this.languageVersionSettings = languageVersionSettings;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.expectActualTracker = expectActualTracker;
        this.missingSupertypesResolver = missingSupertypesResolver;
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

    public final ExpectActualTracker getExpectActualTracker() {
        return this.expectActualTracker;
    }

    public final MissingSupertypesResolver getMissingSupertypesResolver() {
        return this.missingSupertypesResolver;
    }
}
