package org.jetbrains.kotlin.backend.common.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: LateinitLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LateinitUsageLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LateinitUsageLowering implements BodyLoweringPass {
    private final CommonBackendContext backendContext;

    public LateinitUsageLowering(CommonBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final CommonBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        IrBody irBody2 = irBody;
        IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.LateinitUsageLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitVariable(IrVariable declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
                if (!declaration.getIsLateinit()) {
                    return declaration;
                }
                IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(declaration.getParent(), declaration.getStartOffset(), declaration.getEndOffset(), declaration.getOrigin(), declaration.getName(), IrTypesKt.makeNullable(declaration.getType()), (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : true, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
                irVariableBuildVariable.setInitializer(IrConstImpl.INSTANCE.constNull(declaration.getStartOffset(), declaration.getEndOffset(), this.getBackendContext().getIrBuiltIns().getNothingNType()));
                linkedHashMap.put(declaration, irVariableBuildVariable);
                return irVariableBuildVariable;
            }
        });
        IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.LateinitUsageLowering.lower.2
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrVariable irVariable = linkedHashMap.get(expression.getSymbol().getOwner());
                if (irVariable == null) {
                    return expression;
                }
                IrDeclarationParent parent = irVariable.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.getBackendContext(), ((IrSymbolOwner) parent).getSymbol(), expression.getStartOffset(), expression.getEndOffset());
                LateinitUsageLowering lateinitUsageLowering = this;
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                IrType type = expression.getType();
                IrVariable irVariable2 = irVariable;
                IrExpression irExpressionIrEqualsNull = ExpressionHelpersKt.irEqualsNull(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable2));
                CommonBackendContext backendContext = lateinitUsageLowering.getBackendContext();
                String strAsString = irVariable.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, type, irExpressionIrEqualsNull, backendContext.throwUninitializedPropertyAccessException(declarationIrBuilder, strAsString), ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable2), null, 16, null);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetValue(IrSetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrVariable irVariable = linkedHashMap.get(expression.getSymbol().getOwner());
                return irVariable == null ? expression : new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irVariable.getSymbol(), expression.getValue(), expression.getOrigin());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetField(IrGetField expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrField irField = this.getBackendContext().getMapping().getLateInitFieldToNullableField().get(expression.getSymbol().getOwner());
                return irField == null ? expression : new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irField.getSymbol(), irField.getType(), expression.getReceiver(), expression.getOrigin(), expression.getSuperQualifierSymbol());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetField(IrSetField expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrField irField = this.getBackendContext().getMapping().getLateInitFieldToNullableField().get(expression.getSymbol().getOwner());
                return irField == null ? expression : new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irField.getSymbol(), expression.getReceiver(), expression.getValue(), expression.getType(), expression.getOrigin(), expression.getSuperQualifierSymbol());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                IrField backingField;
                IrSimpleFunction owner;
                IrSimpleFunction irSimpleFunction;
                IrPropertySymbol correspondingPropertySymbol;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                if (!Symbols.INSTANCE.isLateinitIsInitializedPropertyGetter(expression.getSymbol())) {
                    return expression;
                }
                IrExpression extensionReceiver = expression.getExtensionReceiver();
                Intrinsics.checkNotNull(extensionReceiver);
                LateinitUsageLowering lateinitUsageLowering = this;
                IrExpression irExpression = extensionReceiver;
                IrContainerExpression irContainerExpression = null;
                while (irExpression instanceof IrContainerExpression) {
                    irContainerExpression = (IrContainerExpression) irExpression;
                    Object objLast = CollectionsKt.last((List<? extends Object>) irContainerExpression.getStatements());
                    Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    irExpression = (IrExpression) objLast;
                }
                if (!(irExpression instanceof IrPropertyReference)) {
                    throw new IllegalArgumentException(("isInitialized cannot be invoked on " + RenderIrElementKt.render$default(irExpression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
                IrPropertyReference irPropertyReference = (IrPropertyReference) irExpression;
                IrSimpleFunctionSymbol getter = irPropertyReference.getGetter();
                IrProperty owner2 = (getter == null || (owner = getter.getOwner()) == null || (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(owner, false, null, 3, null)) == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null) ? null : correspondingPropertySymbol.getOwner();
                if (owner2 == null || !owner2.getIsLateinit()) {
                    throw new IllegalArgumentException(("isInitialized invoked on non-lateinit property " + (owner2 != null ? RenderIrElementKt.render$default(owner2, (DumpIrTreeOptions) null, 1, (Object) null) : null)).toString());
                }
                if (owner2 == null || (backingField = owner2.getBackingField()) == null) {
                    throw new IllegalStateException("Lateinit property is supposed to have a backing field".toString());
                }
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(lateinitUsageLowering.getBackendContext(), irPropertyReference.getSymbol(), expression.getStartOffset(), expression.getEndOffset());
                IrExpression irExpressionIrNotEquals = ExpressionHelpersKt.irNotEquals(declarationIrBuilderCreateIrBuilder, ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder, irPropertyReference.getDispatchReceiver(), LateinitLoweringKt.buildOrGetNullableField(lateinitUsageLowering.getBackendContext(), backingField), null, 4, null), ExpressionHelpersKt.irNull(declarationIrBuilderCreateIrBuilder));
                if (irContainerExpression == null) {
                    return irExpressionIrNotEquals;
                }
                irContainerExpression.getStatements().set(irContainerExpression.getStatements().size() - 1, irExpressionIrNotEquals);
                return extensionReceiver;
            }
        });
    }
}
