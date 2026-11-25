package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.checkers.CheckerContext;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: CallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B=\b\u0007\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020)8F¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "Lorg/jetbrains/kotlin/resolve/checkers/CheckerContext;", "resolutionContext", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "getDataFlowValueFactory", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "getDeprecationResolver", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "isAnnotationContext", "", "()Z", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getMissingSupertypesResolver", "()Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getResolutionContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallCheckerContext implements CheckerContext {
    private final KotlinCallComponents callComponents;
    private final DeprecationResolver deprecationResolver;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final ResolutionContext<?> resolutionContext;
    private final BindingTrace trace;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallCheckerContext(ResolutionContext<?> resolutionContext, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, MissingSupertypesResolver missingSupertypesResolver, KotlinCallComponents callComponents) {
        this(resolutionContext, deprecationResolver, moduleDescriptor, missingSupertypesResolver, callComponents, null, 32, null);
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
    }

    public CallCheckerContext(ResolutionContext<?> resolutionContext, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, MissingSupertypesResolver missingSupertypesResolver, KotlinCallComponents callComponents, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.resolutionContext = resolutionContext;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.callComponents = callComponents;
        this.trace = trace;
    }

    public final ResolutionContext<?> getResolutionContext() {
        return this.resolutionContext;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public DeprecationResolver getDeprecationResolver() {
        return this.deprecationResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final MissingSupertypesResolver getMissingSupertypesResolver() {
        return this.missingSupertypesResolver;
    }

    public final KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ CallCheckerContext(ResolutionContext resolutionContext, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, MissingSupertypesResolver missingSupertypesResolver, KotlinCallComponents kotlinCallComponents, BindingTrace trace, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 32) != 0) {
            trace = resolutionContext.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
        }
        this(resolutionContext, deprecationResolver, moduleDescriptor, missingSupertypesResolver, kotlinCallComponents, trace);
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public BindingTrace getTrace() {
        return this.trace;
    }

    public final LexicalScope getScope() {
        LexicalScope scope = this.resolutionContext.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        return scope;
    }

    public final DataFlowInfo getDataFlowInfo() {
        DataFlowInfo dataFlowInfo = this.resolutionContext.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        return dataFlowInfo;
    }

    public final boolean isAnnotationContext() {
        return this.resolutionContext.isAnnotationContext;
    }

    public final DataFlowValueFactory getDataFlowValueFactory() {
        DataFlowValueFactory dataFlowValueFactory = this.resolutionContext.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        return dataFlowValueFactory;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.CheckerContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = this.resolutionContext.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        return languageVersionSettings;
    }
}
