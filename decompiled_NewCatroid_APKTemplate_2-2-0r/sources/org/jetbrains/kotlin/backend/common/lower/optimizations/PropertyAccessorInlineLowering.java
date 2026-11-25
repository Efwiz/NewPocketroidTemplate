package org.jetbrains.kotlin.backend.common.lower.optimizations;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: PropertyAccessorInlineLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\n\u0010\u000f\u001a\u00020\f*\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/optimizations/PropertyAccessorInlineLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isSafeToInline", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "accessContainer", "isSafeToInlineInClosedWorld", "AccessorInliner", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PropertyAccessorInlineLowering implements BodyLoweringPass {
    private final CommonBackendContext context;

    public PropertyAccessorInlineLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final boolean isSafeToInlineInClosedWorld(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        if (!IrUtilsKt.isTopLevel(irProperty) && irProperty.getModality() != Modality.FINAL && !Intrinsics.areEqual(irProperty.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            IrDeclarationParent parent = irProperty.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            if (((IrClass) parent).getModality() != Modality.FINAL) {
                return false;
            }
        }
        return true;
    }

    public boolean isSafeToInline(IrProperty irProperty, IrDeclaration accessContainer) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(accessContainer, "accessContainer");
        return isSafeToInlineInClosedWorld(irProperty);
    }

    /* JADX INFO: compiled from: PropertyAccessorInlineLowering.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\"\u0010\u0015\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/optimizations/PropertyAccessorInlineLowering$AccessorInliner;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/backend/common/lower/optimizations/PropertyAccessorInlineLowering;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "canBeInlined", "", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isSimpleGetter", "backingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "isSimpleSetter", "tryInlineSimpleGetter", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "tryInlineSimpleSetter", "visitCall", "expression", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AccessorInliner extends IrElementTransformerVoid {
        private final IrDeclaration container;
        final /* synthetic */ PropertyAccessorInlineLowering this$0;
        private final IrType unitType;

        public AccessorInliner(PropertyAccessorInlineLowering propertyAccessorInlineLowering, IrDeclaration container) {
            Intrinsics.checkNotNullParameter(container, "container");
            this.this$0 = propertyAccessorInlineLowering;
            this.container = container;
            this.unitType = propertyAccessorInlineLowering.context.getIrBuiltIns().getUnitType();
        }

        public final IrDeclaration getContainer() {
            return this.container;
        }

        private final boolean canBeInlined(IrSimpleFunction callee) {
            IrProperty owner;
            IrPropertySymbol correspondingPropertySymbol = callee.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || !this.this$0.isSafeToInline(owner, this.container)) {
                return false;
            }
            IrDeclarationParent parent = owner.getParent();
            if (parent instanceof IrClass) {
                IrClass irClass = (IrClass) parent;
                if (irClass.getIsExpect() || owner.getIsExpect() || (irClass.getParent() instanceof IrExternalPackageFragment) || this.this$0.context.getInlineClassesUtils().isClassInlineLike(irClass)) {
                    return false;
                }
            }
            return !IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            IrProperty owner;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            IrSimpleFunction owner2 = expression.getSymbol().getOwner();
            if (!canBeInlined(owner2)) {
                return expression;
            }
            do {
                DescriptorsRemapper descriptorsRemapper = null;
                Object[] objArr = 0;
                if (owner2.getIsFakeOverride()) {
                    owner2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(owner2, false, null, 3, null);
                } else {
                    if (!canBeInlined(owner2)) {
                        return expression;
                    }
                    IrPropertySymbol correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
                        return expression;
                    }
                    IrField backingField = owner.getBackingField();
                    if (backingField == null) {
                        return expression;
                    }
                    if (owner.getIsConst()) {
                        IrExpressionBody initializer = backingField.getInitializer();
                        if (initializer == null) {
                            throw new IllegalStateException("Constant property has to have a backing field with initializer".toString());
                        }
                        IrExpression expression2 = initializer.getExpression();
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(expression2, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(expression2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                        if (irElementPatchDeclarationParents != null) {
                            IrExpression irExpression = (IrExpression) irElementPatchDeclarationParents;
                            IrExpression dispatchReceiver = expression.getDispatchReceiver();
                            if (dispatchReceiver == null || org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.isPure$default(dispatchReceiver, true, false, null, 6, null)) {
                                return irExpression;
                            }
                            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.context, expression.getSymbol(), expression.getStartOffset(), expression.getEndOffset());
                            IrCall irCall = expression;
                            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), irCall.getStartOffset(), irCall.getEndOffset(), null, irCall.getType(), false, 64, null);
                            irBlockBuilder.unaryPlus(dispatchReceiver);
                            irBlockBuilder.unaryPlus(irExpression);
                            return irBlockBuilder.getIrBlockBody();
                        }
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    }
                    if (owner.getGetter() == owner2) {
                        IrExpression irExpressionTryInlineSimpleGetter = tryInlineSimpleGetter(expression, owner2, backingField);
                        return irExpressionTryInlineSimpleGetter == null ? expression : irExpressionTryInlineSimpleGetter;
                    }
                    if (owner.getSetter() == owner2) {
                        IrExpression irExpressionTryInlineSimpleSetter = tryInlineSimpleSetter(expression, owner2, backingField);
                        return irExpressionTryInlineSimpleSetter == null ? expression : irExpressionTryInlineSimpleSetter;
                    }
                    return expression;
                }
            } while (owner2 != null);
            return expression;
        }

        private final IrExpression tryInlineSimpleGetter(IrCall call, IrSimpleFunction callee, IrField backingField) {
            if (!isSimpleGetter(callee, backingField)) {
                return null;
            }
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.context, call.getSymbol(), call.getStartOffset(), call.getEndOffset());
            IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(call.getStartOffset(), call.getEndOffset(), backingField.getSymbol(), backingField.getType(), call.getDispatchReceiver(), call.getOrigin(), null, 64, null);
            if (!Intrinsics.areEqual(backingField.getType(), call.getType())) {
                return ExpressionHelpersKt.irImplicitCast(declarationIrBuilderCreateIrBuilder, irGetFieldImpl, call.getType());
            }
            return irGetFieldImpl;
        }

        private final boolean isSimpleGetter(IrSimpleFunction callee, IrField backingField) {
            IrStatement irStatement;
            IrBody body = callee.getBody();
            if (body == null || (irStatement = (IrStatement) CollectionsKt.singleOrNull((List) ((IrBlockBody) body).getStatements())) == null) {
                return false;
            }
            IrReturn irReturn = irStatement instanceof IrReturn ? (IrReturn) irStatement : null;
            if (irReturn == null) {
                return false;
            }
            IrExpression value = irReturn.getValue();
            IrGetField irGetField = value instanceof IrGetField ? (IrGetField) value : null;
            if (irGetField == null || irGetField.getSymbol() != backingField.getSymbol()) {
                return false;
            }
            IrExpression receiver = irGetField.getReceiver();
            if (receiver != null) {
                return (receiver instanceof IrGetValue) && ((IrGetValue) receiver).getSymbol().getOwner() == callee.getDispatchReceiverParameter();
            }
            callee.getDispatchReceiverParameter();
            return true;
        }

        private final IrExpression tryInlineSimpleSetter(IrCall call, IrSimpleFunction callee, IrField backingField) {
            if (!isSimpleSetter(callee, backingField)) {
                return null;
            }
            IrExpression valueArgument = call.getValueArgument(0);
            if (valueArgument == null) {
                throw new IllegalStateException("Setter should have a value argument".toString());
            }
            return new IrSetFieldImpl(call.getStartOffset(), call.getEndOffset(), backingField.getSymbol(), call.getDispatchReceiver(), valueArgument, this.unitType, call.getOrigin(), null, 128, null);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x003b, code lost:
        
            if (org.jetbrains.kotlin.ir.types.IrTypePredicatesKt.isUnit(r2) == true) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final boolean isSimpleSetter(org.jetbrains.kotlin.ir.declarations.IrSimpleFunction r7, org.jetbrains.kotlin.ir.declarations.IrField r8) {
            /*
                r6 = this;
                org.jetbrains.kotlin.ir.expressions.IrBody r0 = r7.getBody()
                r1 = 0
                if (r0 == 0) goto La1
                org.jetbrains.kotlin.ir.expressions.IrBlockBody r0 = (org.jetbrains.kotlin.ir.expressions.IrBlockBody) r0
                java.util.List r2 = r0.getStatements()
                int r2 = r2.size()
                r3 = 0
                r4 = 1
                if (r2 == r4) goto L3f
                r5 = 2
                if (r2 == r5) goto L19
                goto L3e
            L19:
                java.util.List r2 = r0.getStatements()
                java.lang.Object r2 = r2.get(r4)
                boolean r5 = r2 instanceof org.jetbrains.kotlin.ir.expressions.IrReturn
                if (r5 == 0) goto L28
                org.jetbrains.kotlin.ir.expressions.IrReturn r2 = (org.jetbrains.kotlin.ir.expressions.IrReturn) r2
                goto L29
            L28:
                r2 = r3
            L29:
                if (r2 == 0) goto L3e
                org.jetbrains.kotlin.ir.expressions.IrExpression r2 = r2.getValue()
                if (r2 == 0) goto L3e
                org.jetbrains.kotlin.ir.types.IrType r2 = r2.getType()
                if (r2 == 0) goto L3e
                boolean r2 = org.jetbrains.kotlin.ir.types.IrTypePredicatesKt.isUnit(r2)
                if (r2 != r4) goto L3e
                goto L3f
            L3e:
                return r1
            L3f:
                java.util.List r0 = r0.getStatements()
                java.lang.Object r0 = r0.get(r1)
                org.jetbrains.kotlin.ir.IrStatement r0 = (org.jetbrains.kotlin.ir.IrStatement) r0
                boolean r2 = r0 instanceof org.jetbrains.kotlin.ir.expressions.IrSetField
                if (r2 == 0) goto L50
                org.jetbrains.kotlin.ir.expressions.IrSetField r0 = (org.jetbrains.kotlin.ir.expressions.IrSetField) r0
                goto L51
            L50:
                r0 = r3
            L51:
                if (r0 != 0) goto L54
                return r1
            L54:
                org.jetbrains.kotlin.ir.symbols.IrFieldSymbol r2 = r0.getSymbol()
                org.jetbrains.kotlin.ir.symbols.IrFieldSymbol r8 = r8.getSymbol()
                if (r2 == r8) goto L5f
                return r1
            L5f:
                org.jetbrains.kotlin.ir.expressions.IrExpression r8 = r0.getValue()
                boolean r2 = r8 instanceof org.jetbrains.kotlin.ir.expressions.IrGetValue
                if (r2 == 0) goto L6a
                r3 = r8
                org.jetbrains.kotlin.ir.expressions.IrGetValue r3 = (org.jetbrains.kotlin.ir.expressions.IrGetValue) r3
            L6a:
                if (r3 != 0) goto L6d
                return r1
            L6d:
                java.util.List r8 = r7.getValueParameters()
                java.lang.Object r8 = kotlin.collections.CollectionsKt.single(r8)
                org.jetbrains.kotlin.ir.declarations.IrValueParameter r8 = (org.jetbrains.kotlin.ir.declarations.IrValueParameter) r8
                org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol r8 = r8.getSymbol()
                org.jetbrains.kotlin.ir.symbols.IrValueSymbol r2 = r3.getSymbol()
                if (r2 == r8) goto L82
                return r1
            L82:
                org.jetbrains.kotlin.ir.expressions.IrExpression r8 = r0.getReceiver()
                if (r8 != 0) goto L8c
                r7.getDispatchReceiverParameter()
                return r4
            L8c:
                boolean r0 = r8 instanceof org.jetbrains.kotlin.ir.expressions.IrGetValue
                if (r0 == 0) goto La1
                org.jetbrains.kotlin.ir.expressions.IrGetValue r8 = (org.jetbrains.kotlin.ir.expressions.IrGetValue) r8
                org.jetbrains.kotlin.ir.symbols.IrValueSymbol r8 = r8.getSymbol()
                org.jetbrains.kotlin.ir.declarations.IrValueDeclaration r8 = r8.getOwner()
                org.jetbrains.kotlin.ir.declarations.IrValueParameter r7 = r7.getDispatchReceiverParameter()
                if (r8 != r7) goto La1
                r1 = r4
            La1:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.common.lower.optimizations.PropertyAccessorInlineLowering.AccessorInliner.isSimpleSetter(org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.ir.declarations.IrField):boolean");
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new AccessorInliner(this, container));
    }
}
