package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: JvmLateinitLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmLateinitLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Transformer", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmLateinitLowering implements FileLoweringPass {
    private final JvmBackendContext context;

    public JvmLateinitLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Transformer transformer = new Transformer(this.context);
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, transformer);
        Iterator<IrVariable> it = transformer.getLateinitVariables().iterator();
        while (it.hasNext()) {
            it.next().setLateinit(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmLateinitLowering.kt */
    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\rH\u0016J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\f\u0010\u001e\u001a\u00020\u001f*\u00020\rH\u0002J\f\u0010 \u001a\u00020\u001f*\u00020!H\u0002J\u0014\u0010\"\u001a\u00020\u0011*\u00020#2\u0006\u0010$\u001a\u00020%H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmLateinitLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lateinitVariables", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getLateinitVariables", "()Ljava/util/List;", "transformGetter", "", "backingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitField", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitVariable", "isLateinitBackingField", "", "isRealLateinit", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "throwUninitializedPropertyAccessException", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "name", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Transformer extends IrElementTransformerVoid {
        private final JvmBackendContext backendContext;
        private final List<IrVariable> lateinitVariables;

        public Transformer(JvmBackendContext backendContext) {
            Intrinsics.checkNotNullParameter(backendContext, "backendContext");
            this.backendContext = backendContext;
            this.lateinitVariables = new ArrayList();
        }

        public final List<IrVariable> getLateinitVariables() {
            return this.lateinitVariables;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitField(IrField declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (isLateinitBackingField(declaration)) {
                declaration.getInitializer();
                declaration.setType(IrTypesKt.makeNullable(declaration.getType()));
            }
            transformChildrenVoid(declaration);
            return declaration;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitVariable(IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
            if (declaration.getIsLateinit()) {
                declaration.setType(IrTypesKt.makeNullable(declaration.getType()));
                declaration.setVar(true);
                declaration.setInitializer(IrConstImpl.INSTANCE.constNull(declaration.getStartOffset(), declaration.getEndOffset(), this.backendContext.getIrBuiltIns().getNothingNType()));
                this.lateinitVariables.add(declaration);
            }
            return declaration;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null && isRealLateinit(owner) && Intrinsics.areEqual(declaration, owner.getGetter())) {
                IrField backingField = owner.getBackingField();
                Intrinsics.checkNotNull(backingField);
                transformGetter(backingField, declaration);
                return declaration;
            }
            transformChildrenVoid(declaration);
            return declaration;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetValue(IrGetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueDeclaration owner = expression.getSymbol().getOwner();
            if (!(owner instanceof IrVariable) || !((IrVariable) owner).getIsLateinit()) {
                return expression;
            }
            JvmBackendContext jvmBackendContext = this.backendContext;
            IrDeclarationParent parent = owner.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(jvmBackendContext, ((IrSymbolOwner) parent).getSymbol(), expression.getStartOffset(), expression.getEndOffset());
            IrType type = expression.getType();
            IrExpression irExpressionIrEqualsNull = ExpressionHelpersKt.irEqualsNull(declarationIrBuilderCreateIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, owner));
            JvmBackendContext jvmBackendContext2 = this.backendContext;
            String strAsString = owner.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilderCreateIrBuilder, type, irExpressionIrEqualsNull, jvmBackendContext2.throwUninitializedPropertyAccessException(declarationIrBuilderCreateIrBuilder, strAsString), ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, owner), null, 16, null);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetField(IrGetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            if (isLateinitBackingField(expression.getSymbol().getOwner())) {
                expression.setType(IrTypesKt.makeNullable(expression.getType()));
            }
            return expression;
        }

        private final boolean isLateinitBackingField(IrField irField) {
            IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            return owner != null && isRealLateinit(owner);
        }

        private final boolean isRealLateinit(IrProperty irProperty) {
            return irProperty.getIsLateinit() && !irProperty.getIsFakeOverride();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            IrSimpleFunction owner;
            IrSimpleFunction irSimpleFunction;
            IrPropertySymbol correspondingPropertySymbol;
            IrProperty owner2;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            if (!Symbols.INSTANCE.isLateinitIsInitializedPropertyGetter(expression.getSymbol())) {
                return expression;
            }
            IrExpression extensionReceiver = expression.getExtensionReceiver();
            Intrinsics.checkNotNull(extensionReceiver);
            IrExpression irExpression = extensionReceiver;
            IrContainerExpression irContainerExpression = null;
            while (irExpression instanceof IrContainerExpression) {
                irContainerExpression = (IrContainerExpression) irExpression;
                Object objLast = CollectionsKt.last((List<? extends Object>) irContainerExpression.getStatements());
                Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                irExpression = (IrExpression) objLast;
            }
            IrPropertyReference irPropertyReference = irExpression instanceof IrPropertyReference ? (IrPropertyReference) irExpression : null;
            if (irPropertyReference == null) {
                throw new AssertionError("Property reference expected: " + RenderIrElementKt.render$default(irExpression, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            IrSimpleFunctionSymbol getter = irPropertyReference.getGetter();
            if (getter == null || (owner = getter.getOwner()) == null || (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(owner, false, null, 3, null)) == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner2 = correspondingPropertySymbol.getOwner()) == null) {
                throw new AssertionError("isInitialized cannot be invoked on " + RenderIrElementKt.render$default(irExpression, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            if (!owner2.getIsLateinit()) {
                throw new IllegalArgumentException(("isInitialized invoked on non-lateinit property " + RenderIrElementKt.render$default(owner2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            IrField backingField = owner2.getBackingField();
            if (backingField != null) {
                IrPropertyReference irPropertyReference2 = (IrPropertyReference) irExpression;
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.backendContext, irPropertyReference2.getSymbol(), expression.getStartOffset(), expression.getEndOffset());
                IrExpression irExpressionIrNotEquals = ExpressionHelpersKt.irNotEquals(declarationIrBuilderCreateIrBuilder, ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder, irPropertyReference2.getDispatchReceiver(), backingField, null, 4, null), ExpressionHelpersKt.irNull(declarationIrBuilderCreateIrBuilder));
                if (irContainerExpression == null) {
                    return irExpressionIrNotEquals;
                }
                irContainerExpression.getStatements().set(irContainerExpression.getStatements().size() - 1, irExpressionIrNotEquals);
                return extensionReceiver;
            }
            throw new AssertionError("Lateinit property is supposed to have a backing field");
        }

        private final void transformGetter(final IrField backingField, final IrFunction getter) {
            IrTypePredicatesKt.isPrimitiveType$default(backingField.getType(), false, 1, null);
            final int startOffset = getter.getStartOffset();
            final int endOffset = getter.getEndOffset();
            getter.setBody(IrFactoryHelpersKt.createBlockBody(this.backendContext.getIrFactory(), startOffset, endOffset, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmLateinitLowering$Transformer$transformGetter$2
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
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.backendContext, getter.getSymbol(), startOffset, endOffset);
                    IrFunction irFunction = getter;
                    IrField irField = backingField;
                    JvmLateinitLowering.Transformer transformer = this.this$0;
                    Scope scope = declarationIrBuilderCreateIrBuilder.getScope();
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                    IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
                    IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(scope, (IrExpression) ExpressionHelpersKt.irGetField(declarationIrBuilder, dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(declarationIrBuilder, dispatchReceiverParameter) : null, irField, IrTypesKt.makeNullable(irField.getType())), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                    irVariableCreateTmpVariable$default.setParent(irFunction);
                    createBlockBody.getStatements().add(irVariableCreateTmpVariable$default);
                    IrType nothingType = declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getNothingType();
                    IrVariable irVariable = irVariableCreateTmpVariable$default;
                    IrExpression irExpressionIrNotEquals = ExpressionHelpersKt.irNotEquals(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable), ExpressionHelpersKt.irNull(declarationIrBuilder));
                    IrReturnImpl irReturnImplIrReturn = ExpressionHelpersKt.irReturn(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable));
                    String strAsString = irField.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    createBlockBody.getStatements().add(ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, nothingType, irExpressionIrNotEquals, irReturnImplIrReturn, transformer.throwUninitializedPropertyAccessException(declarationIrBuilder, strAsString), null, 16, null));
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression throwUninitializedPropertyAccessException(IrBuilderWithScope irBuilderWithScope, String str) {
            return this.backendContext.throwUninitializedPropertyAccessException(irBuilderWithScope, str);
        }
    }
}
