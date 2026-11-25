package org.jetbrains.kotlin.fir.pipeline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: convertToIr.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\tHÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;", "", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "pluginContext", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;", "irActualizedResult", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;)V", "getComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "getIrActualizedResult", "()Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "getIrModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getPluginContext", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Fir2IrActualizedResult {
    private final Fir2IrComponents components;
    private final IrActualizedResult irActualizedResult;
    private final IrModuleFragment irModuleFragment;
    private final Fir2IrPluginContext pluginContext;

    public static /* synthetic */ Fir2IrActualizedResult copy$default(Fir2IrActualizedResult fir2IrActualizedResult, IrModuleFragment irModuleFragment, Fir2IrComponents fir2IrComponents, Fir2IrPluginContext fir2IrPluginContext, IrActualizedResult irActualizedResult, int i, Object obj) {
        if ((i & 1) != 0) {
            irModuleFragment = fir2IrActualizedResult.irModuleFragment;
        }
        if ((i & 2) != 0) {
            fir2IrComponents = fir2IrActualizedResult.components;
        }
        if ((i & 4) != 0) {
            fir2IrPluginContext = fir2IrActualizedResult.pluginContext;
        }
        if ((i & 8) != 0) {
            irActualizedResult = fir2IrActualizedResult.irActualizedResult;
        }
        return fir2IrActualizedResult.copy(irModuleFragment, fir2IrComponents, fir2IrPluginContext, irActualizedResult);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrModuleFragment getIrModuleFragment() {
        return this.irModuleFragment;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Fir2IrComponents getComponents() {
        return this.components;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Fir2IrPluginContext getPluginContext() {
        return this.pluginContext;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final IrActualizedResult getIrActualizedResult() {
        return this.irActualizedResult;
    }

    public final Fir2IrActualizedResult copy(IrModuleFragment irModuleFragment, Fir2IrComponents components, Fir2IrPluginContext pluginContext, IrActualizedResult irActualizedResult) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(pluginContext, "pluginContext");
        return new Fir2IrActualizedResult(irModuleFragment, components, pluginContext, irActualizedResult);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Fir2IrActualizedResult)) {
            return false;
        }
        Fir2IrActualizedResult fir2IrActualizedResult = (Fir2IrActualizedResult) other;
        return Intrinsics.areEqual(this.irModuleFragment, fir2IrActualizedResult.irModuleFragment) && Intrinsics.areEqual(this.components, fir2IrActualizedResult.components) && Intrinsics.areEqual(this.pluginContext, fir2IrActualizedResult.pluginContext) && Intrinsics.areEqual(this.irActualizedResult, fir2IrActualizedResult.irActualizedResult);
    }

    public int hashCode() {
        int iHashCode = ((((this.irModuleFragment.hashCode() * 31) + this.components.hashCode()) * 31) + this.pluginContext.hashCode()) * 31;
        IrActualizedResult irActualizedResult = this.irActualizedResult;
        return iHashCode + (irActualizedResult == null ? 0 : irActualizedResult.hashCode());
    }

    public String toString() {
        return "Fir2IrActualizedResult(irModuleFragment=" + this.irModuleFragment + ", components=" + this.components + ", pluginContext=" + this.pluginContext + ", irActualizedResult=" + this.irActualizedResult + ')';
    }

    public Fir2IrActualizedResult(IrModuleFragment irModuleFragment, Fir2IrComponents components, Fir2IrPluginContext pluginContext, IrActualizedResult irActualizedResult) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(pluginContext, "pluginContext");
        this.irModuleFragment = irModuleFragment;
        this.components = components;
        this.pluginContext = pluginContext;
        this.irActualizedResult = irActualizedResult;
    }

    public final IrModuleFragment getIrModuleFragment() {
        return this.irModuleFragment;
    }

    public final Fir2IrComponents getComponents() {
        return this.components;
    }

    public final Fir2IrPluginContext getPluginContext() {
        return this.pluginContext;
    }

    public final IrActualizedResult getIrActualizedResult() {
        return this.irActualizedResult;
    }
}
