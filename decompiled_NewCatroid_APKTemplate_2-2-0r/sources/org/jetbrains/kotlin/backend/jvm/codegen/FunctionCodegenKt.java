package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.BOUND_RECEIVER_PARAMETER;
import org.jetbrains.kotlin.backend.common.lower.BOUND_VALUE_PARAMETER;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: FunctionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\f\u0010\b\u001a\u00020\t*\u00020\nH\u0002¨\u0006\u000b"}, d2 = {"generateParameterNames", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isSyntheticMarkerParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionCodegenKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSyntheticMarkerParameter(IrValueParameter irValueParameter) {
        return Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.DEFAULT_CONSTRUCTOR_MARKER.INSTANCE) || Intrinsics.areEqual(irValueParameter.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_MARKER_PARAMETER.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateParameterNames(IrFunction irFunction, MethodVisitor methodVisitor, GenerationState generationState) {
        if (irFunction.getExtensionReceiverParameter() != null) {
            methodVisitor.visitParameter(JvmIrUtilsKt.extensionReceiverName(irFunction, generationState), 0);
        }
        for (IrValueParameter irValueParameter : irFunction.getValueParameters()) {
            int i = 32768;
            if (!Intrinsics.areEqual(irValueParameter.getOrigin(), JvmLoweredDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE) && !Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER.INSTANCE)) {
                i = 4096;
                if (!Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE) && !Intrinsics.areEqual(irValueParameter.getOrigin(), BOUND_VALUE_PARAMETER.INSTANCE) && !Intrinsics.areEqual(irValueParameter.getOrigin(), BOUND_RECEIVER_PARAMETER.INSTANCE) && !irValueParameter.getOrigin().getIsSynthetic()) {
                    i = 0;
                }
            }
            methodVisitor.visitParameter(irValueParameter.getName().asString(), i);
        }
    }
}
