package org.jetbrains.kotlin.backend.common.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: InlineClassDeclarationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$1$1 extends Lambda implements Function1<IrBlockBody, Unit> {
    final /* synthetic */ IrBody $body;
    final /* synthetic */ Ref.ObjectRef<IrDelegatingConstructorCall> $delegatingCtorCall;
    final /* synthetic */ IrSimpleFunction $initFunction;
    final /* synthetic */ IrConstructor $irConstructor;
    final /* synthetic */ IrClass $klass;
    final /* synthetic */ Ref.ObjectRef<IrSetField> $setMemberField;
    final /* synthetic */ InlineClassLowering this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$1$1(IrConstructor irConstructor, InlineClassLowering inlineClassLowering, IrSimpleFunction irSimpleFunction, IrBody irBody, Ref.ObjectRef<IrDelegatingConstructorCall> objectRef, IrClass irClass, Ref.ObjectRef<IrSetField> objectRef2) {
        super(1);
        this.$irConstructor = irConstructor;
        this.this$0 = inlineClassLowering;
        this.$initFunction = irSimpleFunction;
        this.$body = irBody;
        this.$delegatingCtorCall = objectRef;
        this.$klass = irClass;
        this.$setMemberField = objectRef2;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
        invoke2(irBlockBody);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrBlockBody createBlockBody) {
        Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
        final IrValueParameterSymbol symbol = ((IrValueParameter) CollectionsKt.single((List) this.$irConstructor.getValueParameters())).getSymbol();
        List<IrStatement> statements = createBlockBody.getStatements();
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), this.$initFunction.getSymbol(), 0, 0, 6, (Object) null);
        final IrSimpleFunction irSimpleFunction = this.$initFunction;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrBody irBody = this.$body;
        Ref.ObjectRef<IrDelegatingConstructorCall> objectRef = this.$delegatingCtorCall;
        final IrConstructor irConstructor = this.$irConstructor;
        final IrClass irClass = this.$klass;
        final Ref.ObjectRef<IrSetField> objectRef2 = this.$setMemberField;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction2.getStartOffset(), irSimpleFunction2.getEndOffset());
        Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) irBody;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(irBlockBody, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBlockBody.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunction);
        if (irElementPatchDeclarationParents == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        }
        Iterator<T> it = ((IrBlockBody) irElementPatchDeclarationParents).getStatements().iterator();
        while (it.hasNext()) {
            final Ref.ObjectRef<IrDelegatingConstructorCall> objectRef3 = objectRef;
            final IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            Ref.ObjectRef<IrDelegatingConstructorCall> objectRef4 = objectRef;
            IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
            irBlockBodyBuilder3.unaryPlus(IrElementsKt.transformStatement((IrStatement) it.next(), new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$1$1$1$1$1
                /* JADX WARN: Type inference failed for: r12v6, types: [T, org.jetbrains.kotlin.ir.IrElement] */
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    Ref.ObjectRef<IrDelegatingConstructorCall> objectRef5 = objectRef3;
                    IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
                    IrConstructor irConstructor2 = irConstructor;
                    DeepCopySymbolRemapper deepCopySymbolRemapper3 = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irDelegatingConstructorCall, deepCopySymbolRemapper3);
                    DeepCopySymbolRemapper deepCopySymbolRemapper4 = deepCopySymbolRemapper3;
                    IrElement irElementPatchDeclarationParents2 = PatchDeclarationParentsKt.patchDeclarationParents(irDelegatingConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper4, new DeepCopyTypeRemapper(deepCopySymbolRemapper4)), (Object) null), irConstructor2);
                    if (irElementPatchDeclarationParents2 != null) {
                        objectRef5.element = (IrDelegatingConstructorCall) irElementPatchDeclarationParents2;
                        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder2;
                        return new IrBlockBuilder(irBlockBodyBuilder4.getContext(), irBlockBodyBuilder4.getScope(), irBlockBodyBuilder4.getStartOffset(), irBlockBodyBuilder4.getEndOffset(), null, null, false, 64, null).getIrBlockBody();
                    }
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall");
                }

                /* JADX WARN: Type inference failed for: r2v5, types: [T, org.jetbrains.kotlin.ir.IrElement] */
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitSetField(IrSetField expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    boolean zAreEqual = Intrinsics.areEqual(expression.getSymbol().getOwner().getParent(), irClass);
                    if (zAreEqual) {
                        Ref.ObjectRef<IrSetField> objectRef5 = objectRef2;
                        IrSetField irSetField = expression;
                        IrConstructor irConstructor2 = irConstructor;
                        DeepCopySymbolRemapper deepCopySymbolRemapper3 = new DeepCopySymbolRemapper(null, 1, null);
                        IrVisitorsKt.acceptVoid(irSetField, deepCopySymbolRemapper3);
                        DeepCopySymbolRemapper deepCopySymbolRemapper4 = deepCopySymbolRemapper3;
                        IrElement irElementPatchDeclarationParents2 = PatchDeclarationParentsKt.patchDeclarationParents(irSetField.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper4, new DeepCopyTypeRemapper(deepCopySymbolRemapper4)), (Object) null), irConstructor2);
                        if (irElementPatchDeclarationParents2 != null) {
                            objectRef5.element = (IrSetField) irElementPatchDeclarationParents2;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrSetField");
                        }
                    }
                    transformChildrenVoid(expression);
                    if (zAreEqual) {
                        return expression.getValue();
                    }
                    return expression;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitGetField(IrGetField expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    transformChildrenVoid(expression);
                    if (Intrinsics.areEqual(expression.getSymbol().getOwner().getParent(), irClass)) {
                        return ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunction.getValueParameters()));
                    }
                    return expression;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitGetValue(IrGetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    transformChildrenVoid(expression);
                    if (Intrinsics.areEqual(expression.getSymbol().getOwner().getParent(), irClass)) {
                        return InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$1$1.invoke$lambda$1$unboxedInlineClassValue(irBlockBodyBuilder2, irSimpleFunction, irClass);
                    }
                    if (Intrinsics.areEqual(expression.getSymbol(), symbol)) {
                        return ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunction.getValueParameters()));
                    }
                    return expression;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitSetValue(IrSetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    transformChildrenVoid(expression);
                    if (Intrinsics.areEqual(expression.getSymbol(), symbol)) {
                        return ExpressionHelpersKt.irSet$default(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunction.getValueParameters()), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null);
                    }
                    return expression;
                }
            }));
            irBlockBodyBuilder = irBlockBodyBuilder3;
            objectRef = objectRef4;
        }
        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder;
        irBlockBodyBuilder4.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder4, invoke$lambda$1$unboxedInlineClassValue(irBlockBodyBuilder4, irSimpleFunction, irClass)));
        CollectionsKt.addAll(statements, irBlockBodyBuilder4.getIrBlockBody().getStatements());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrTypeOperatorCallImpl invoke$lambda$1$unboxedInlineClassValue(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction, IrClass irClass) {
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        return ExpressionHelpersKt.irReinterpretCast(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunction.getValueParameters())), IrUtilsKt.getDefaultType(irClass));
    }
}
