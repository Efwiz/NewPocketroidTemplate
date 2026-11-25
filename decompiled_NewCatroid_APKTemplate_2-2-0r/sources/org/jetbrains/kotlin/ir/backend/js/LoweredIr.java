package org.jetbrains.kotlin.ir.backend.js;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: compiler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/LoweredIr;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "mainModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "allModules", "", "moduleFragmentToUniqueName", "", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/List;Ljava/util/Map;)V", "getAllModules", "()Ljava/util/List;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "getMainModule", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragmentToUniqueName", "()Ljava/util/Map;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoweredIr {
    private final List<IrModuleFragment> allModules;
    private final JsIrBackendContext context;
    private final IrModuleFragment mainModule;
    private final Map<IrModuleFragment, String> moduleFragmentToUniqueName;

    /* JADX WARN: Multi-variable type inference failed */
    public LoweredIr(JsIrBackendContext context, IrModuleFragment mainModule, List<? extends IrModuleFragment> allModules, Map<IrModuleFragment, String> moduleFragmentToUniqueName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        Intrinsics.checkNotNullParameter(moduleFragmentToUniqueName, "moduleFragmentToUniqueName");
        this.context = context;
        this.mainModule = mainModule;
        this.allModules = allModules;
        this.moduleFragmentToUniqueName = moduleFragmentToUniqueName;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final IrModuleFragment getMainModule() {
        return this.mainModule;
    }

    public final List<IrModuleFragment> getAllModules() {
        return this.allModules;
    }

    public final Map<IrModuleFragment, String> getModuleFragmentToUniqueName() {
        return this.moduleFragmentToUniqueName;
    }
}
