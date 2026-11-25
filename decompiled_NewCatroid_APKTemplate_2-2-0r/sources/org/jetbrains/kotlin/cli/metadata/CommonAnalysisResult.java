package org.jetbrains.kotlin.cli.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: CommonAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/CommonAnalysisResult;", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CommonAnalysisResult {
    private final BindingContext bindingContext;
    private final ModuleDescriptor moduleDescriptor;

    public static /* synthetic */ CommonAnalysisResult copy$default(CommonAnalysisResult commonAnalysisResult, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, int i, Object obj) {
        if ((i & 1) != 0) {
            moduleDescriptor = commonAnalysisResult.moduleDescriptor;
        }
        if ((i & 2) != 0) {
            bindingContext = commonAnalysisResult.bindingContext;
        }
        return commonAnalysisResult.copy(moduleDescriptor, bindingContext);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final CommonAnalysisResult copy(ModuleDescriptor moduleDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        return new CommonAnalysisResult(moduleDescriptor, bindingContext);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CommonAnalysisResult)) {
            return false;
        }
        CommonAnalysisResult commonAnalysisResult = (CommonAnalysisResult) other;
        return Intrinsics.areEqual(this.moduleDescriptor, commonAnalysisResult.moduleDescriptor) && Intrinsics.areEqual(this.bindingContext, commonAnalysisResult.bindingContext);
    }

    public int hashCode() {
        return (this.moduleDescriptor.hashCode() * 31) + this.bindingContext.hashCode();
    }

    public String toString() {
        return "CommonAnalysisResult(moduleDescriptor=" + this.moduleDescriptor + ", bindingContext=" + this.bindingContext + ')';
    }

    public CommonAnalysisResult(ModuleDescriptor moduleDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.moduleDescriptor = moduleDescriptor;
        this.bindingContext = bindingContext;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }
}
