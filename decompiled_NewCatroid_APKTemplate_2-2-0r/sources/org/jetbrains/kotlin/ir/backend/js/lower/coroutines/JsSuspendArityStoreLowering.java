package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: JsSuspendArityStoreLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u0012H\u0016R?\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006*\u00020\t2\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00068B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/JsSuspendArityStoreLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "suspendArityStore", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSuspendArityStore", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/util/Collection;", "setSuspendArityStore", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/Collection;)V", "suspendArityStore$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsSuspendArityStoreLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(JsSuspendArityStoreLowering.class), "suspendArityStore", "getSuspendArityStore(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/util/Collection;"))};

    /* JADX INFO: renamed from: suspendArityStore$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate suspendArityStore;

    public JsSuspendArityStoreLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.suspendArityStore = context.getMapping().getSuspendArityStore();
    }

    private final void setSuspendArityStore(IrClass irClass, Collection<? extends IrSimpleFunction> collection) {
        this.suspendArityStore.setValue(irClass, $$delegatedProperties[0], collection);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        SmartList smartListEmptyList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrClass)) {
            return null;
        }
        IrClass irClass = (IrClass) declaration;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        if (declarations.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : declarations) {
                if ((obj instanceof IrSimpleFunction) && ((IrSimpleFunction) obj).getIsSuspend()) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        setSuspendArityStore(irClass, smartListEmptyList);
        return null;
    }
}
