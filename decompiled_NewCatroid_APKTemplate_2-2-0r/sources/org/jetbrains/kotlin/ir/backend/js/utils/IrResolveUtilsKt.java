package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;

/* JADX INFO: compiled from: IrResolveUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"realOverrideTarget", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getRealOverrideTarget", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrResolveUtilsKt {
    public static final IrFunction getRealOverrideTarget(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            return getRealOverrideTarget((IrSimpleFunction) irFunction);
        }
        if (irFunction instanceof IrConstructor) {
            return irFunction;
        }
        CompilationExceptionKt.compilationException("Unexpected declaration", (IrDeclaration) irFunction);
        throw null;
    }

    public static final IrSimpleFunction getRealOverrideTarget(IrSimpleFunction irSimpleFunction) {
        Object next;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Set setCollectRealOverrides$default = IrFakeOverrideUtilsKt.collectRealOverrides$default(irSimpleFunction, null, null, 3, null);
        Iterator it = setCollectRealOverrides$default.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((IrSimpleFunction) next).getModality() == Modality.ABSTRACT);
        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) next;
        if (irSimpleFunction2 != null || (irSimpleFunction2 = (IrSimpleFunction) CollectionsKt.firstOrNull(setCollectRealOverrides$default)) != null) {
            return irSimpleFunction2;
        }
        CompilationExceptionKt.compilationException("No real override target found", (IrDeclaration) irSimpleFunction);
        throw null;
    }
}
