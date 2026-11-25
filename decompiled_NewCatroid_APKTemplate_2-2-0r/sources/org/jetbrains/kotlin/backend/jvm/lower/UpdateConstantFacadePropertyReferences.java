package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: GenerateMultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/UpdateConstantFacadePropertyReferences;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "shouldGeneratePartHierarchy", "", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Z)V", "getReplacementFacadeClassOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irClass", "lower", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UpdateConstantFacadePropertyReferences implements ClassLoweringPass {
    private final JvmBackendContext context;
    private final boolean shouldGeneratePartHierarchy;

    public UpdateConstantFacadePropertyReferences(JvmBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.shouldGeneratePartHierarchy = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        final IrClass replacementFacadeClassOrNull = getReplacementFacadeClassOrNull(irClass);
        if (replacementFacadeClassOrNull == null) {
            return;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irClass, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.UpdateConstantFacadePropertyReferences.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitClass(IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitClassReference(IrClassReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                return new IrClassReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), IrUtilsKt.getDefaultType(replacementFacadeClassOrNull), replacementFacadeClassOrNull.getSymbol(), IrUtilsKt.getDefaultType(replacementFacadeClassOrNull));
            }
        });
    }

    private final IrClass getReplacementFacadeClassOrNull(IrClass irClass) {
        IrFunction owner;
        IrField backingField;
        IrSimpleFunction owner2;
        IrPropertySymbol correspondingPropertySymbol;
        if (!Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE) && !Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE)) {
            return null;
        }
        IrAttributeContainer attributeOwnerId = irClass.getAttributeOwnerId();
        if (attributeOwnerId instanceof IrPropertyReference) {
            IrSimpleFunctionSymbol getter = ((IrPropertyReference) attributeOwnerId).getGetter();
            owner = (getter == null || (owner2 = getter.getOwner()) == null || (correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol()) == null) ? null : correspondingPropertySymbol.getOwner();
        } else {
            owner = attributeOwnerId instanceof IrFunctionReference ? ((IrFunctionReference) attributeOwnerId).getSymbol().getOwner() : null;
        }
        if (owner == null) {
            return null;
        }
        IrDeclarationParent parent = ((IrDeclarationBase) owner).getParent();
        IrClass irClass2 = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass2 == null) {
            return null;
        }
        IrClass irClass3 = this.context.getMultifileFacadeClassForPart().get(irClass2.getAttributeOwnerId());
        if (this.shouldGeneratePartHierarchy || ((owner instanceof IrProperty) && (backingField = ((IrProperty) owner).getBackingField()) != null && GenerateMultifileFacadesKt.shouldMoveToFacade(backingField))) {
            return irClass3;
        }
        return null;
    }
}
