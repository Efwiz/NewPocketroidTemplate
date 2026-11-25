package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.MutableReference;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: ES6PrimaryConstructorOptimizationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"CREATE_EXTERNAL_THIS_CONSTRUCTOR_PARAMETERS", "", "shouldBeConvertedToPlainConstructor", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ES6PrimaryConstructorOptimizationLoweringKt {
    public static final int CREATE_EXTERNAL_THIS_CONSTRUCTOR_PARAMETERS = 2;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldBeConvertedToPlainConstructor(IrFunction irFunction, JsIrBackendContext jsIrBackendContext) {
        MutableReference<Boolean> mutableReference;
        IrFunction irFunction2 = irFunction;
        return ES6ConstructorLoweringKt.isEs6PrimaryConstructorReplacement(irFunction2) && (mutableReference = jsIrBackendContext.getMapping().getEsClassToPossibilityForOptimization().get(IrUtilsKt.getParentAsClass(irFunction2))) != null && mutableReference.getValue().booleanValue();
    }
}
