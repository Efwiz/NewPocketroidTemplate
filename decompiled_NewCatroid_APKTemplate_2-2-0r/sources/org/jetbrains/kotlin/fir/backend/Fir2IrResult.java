package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.descriptors.FirModuleDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: Fir2IrResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003H\u0086\u0002J\t\u0010\u0012\u001a\u00020\u0005H\u0086\u0002J\t\u0010\u0013\u001a\u00020\u000eH\u0086\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrResult;", "", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "moduleDescriptor", "Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;)V", "getComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "getIrModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "pluginContext", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;", "getPluginContext", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;", "component1", "component2", "component3", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrResult {
    private final Fir2IrComponents components;
    private final IrModuleFragment irModuleFragment;
    private final Fir2IrPluginContext pluginContext;

    public Fir2IrResult(IrModuleFragment irModuleFragment, Fir2IrComponents components, FirModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.irModuleFragment = irModuleFragment;
        this.components = components;
        this.pluginContext = new Fir2IrPluginContext(components, moduleDescriptor);
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
}
