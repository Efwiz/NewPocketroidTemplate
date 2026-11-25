package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;

/* JADX INFO: compiled from: LateinitLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/NullableFieldsDeclarationLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "withLocalDeclarations", "", "getWithLocalDeclarations", "()Z", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "transformGetter", "", "backingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableFieldsDeclarationLowering implements DeclarationTransformer {
    private final CommonBackendContext backendContext;

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    public NullableFieldsDeclarationLowering(CommonBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final CommonBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) declaration;
            if (!LateinitLoweringKt.isRealLateinit(irProperty)) {
                return null;
            }
            CommonBackendContext commonBackendContext = this.backendContext;
            IrField backingField = irProperty.getBackingField();
            Intrinsics.checkNotNull(backingField);
            irProperty.setBackingField(LateinitLoweringKt.buildOrGetNullableField(commonBackendContext, backingField));
            return null;
        }
        if (!(declaration instanceof IrSimpleFunction) || (correspondingPropertySymbol = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || !Intrinsics.areEqual(declaration, owner.getGetter()) || !LateinitLoweringKt.isRealLateinit(owner)) {
            return null;
        }
        CommonBackendContext commonBackendContext2 = this.backendContext;
        IrField backingField2 = owner.getBackingField();
        Intrinsics.checkNotNull(backingField2);
        transformGetter(LateinitLoweringKt.buildOrGetNullableField(commonBackendContext2, backingField2), (IrFunction) declaration);
        return null;
    }

    private final void transformGetter(final IrField backingField, final IrFunction getter) {
        IrTypePredicatesKt.isPrimitiveType$default(backingField.getType(), false, 1, null);
        final int startOffset = getter.getStartOffset();
        final int endOffset = getter.getEndOffset();
        getter.setBody(IrFactoryHelpersKt.createBlockBody(this.backendContext.getIrFactory(), startOffset, endOffset, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.NullableFieldsDeclarationLowering.transformGetter.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(NullableFieldsDeclarationLowering.this.getBackendContext(), getter.getSymbol(), startOffset, endOffset);
                IrFunction irFunction = getter;
                IrField irField = backingField;
                NullableFieldsDeclarationLowering nullableFieldsDeclarationLowering = NullableFieldsDeclarationLowering.this;
                Scope scope = declarationIrBuilderCreateIrBuilder.getScope();
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
                IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(scope, (IrExpression) ExpressionHelpersKt.irGetField$default(declarationIrBuilder, dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(declarationIrBuilder, dispatchReceiverParameter) : null, irField, null, 4, null), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                irVariableCreateTmpVariable$default.setParent(irFunction);
                createBlockBody.getStatements().add(irVariableCreateTmpVariable$default);
                IrType nothingType = declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getNothingType();
                IrVariable irVariable = irVariableCreateTmpVariable$default;
                IrExpression irExpressionIrNotEquals = ExpressionHelpersKt.irNotEquals(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable), ExpressionHelpersKt.irNull(declarationIrBuilder));
                IrReturnImpl irReturnImplIrReturn = ExpressionHelpersKt.irReturn(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable));
                CommonBackendContext backendContext = nullableFieldsDeclarationLowering.getBackendContext();
                String strAsString = irField.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                createBlockBody.getStatements().add(ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, nothingType, irExpressionIrNotEquals, irReturnImplIrReturn, backendContext.throwUninitializedPropertyAccessException(declarationIrBuilder, strAsString), null, 16, null));
            }
        }));
    }
}
