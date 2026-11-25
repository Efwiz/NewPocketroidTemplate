package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: CollectClassDefaultConstructorsLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\fH\u0016J\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0007H\u0002J\f\u0010\u0010\u001a\u00020\u000f*\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CollectClassDefaultConstructorsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "defaultConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getDefaultConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "couldContainDefaultConstructor", "", "isDefaultConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectClassDefaultConstructorsLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public CollectClassDefaultConstructorsLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (couldContainDefaultConstructor(irClass)) {
                WeakHashMap<IrClass, IrConstructor> classToItsDefaultConstructor = this.context.getMapping().getClassToItsDefaultConstructor();
                IrConstructor defaultConstructor = getDefaultConstructor(irClass);
                if (defaultConstructor == null) {
                    return null;
                }
                classToItsDefaultConstructor.put(declaration, defaultConstructor);
            }
        }
        return null;
    }

    private final boolean couldContainDefaultConstructor(IrClass irClass) {
        return (!IrUtilsKt.isClass(irClass) || irClass.getIsValue() || irClass.getIsExpect() || irClass.getModality() == Modality.ABSTRACT || irClass.getModality() == Modality.SEALED) ? false : true;
    }

    private final IrConstructor getDefaultConstructor(IrClass irClass) {
        IrConstructor irConstructor = null;
        boolean z = false;
        IrConstructor irConstructor2 = null;
        for (IrConstructor irConstructor3 : IrUtilsKt.getConstructors(irClass)) {
            IrConstructor irConstructor4 = irConstructor3;
            if (Intrinsics.areEqual(irConstructor4.getVisibility(), DescriptorVisibilities.PUBLIC) && isDefaultConstructor(irConstructor4)) {
                if (z) {
                    return irConstructor;
                }
                z = true;
                irConstructor2 = irConstructor3;
            }
        }
        if (z) {
            irConstructor = irConstructor2;
        }
        return irConstructor;
    }

    private final boolean isDefaultConstructor(IrFunction irFunction) {
        if (!irFunction.getValueParameters().isEmpty()) {
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                Iterator<T> it = valueParameters.iterator();
                while (it.hasNext()) {
                    if (((IrValueParameter) it.next()).getDefaultValue() == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
