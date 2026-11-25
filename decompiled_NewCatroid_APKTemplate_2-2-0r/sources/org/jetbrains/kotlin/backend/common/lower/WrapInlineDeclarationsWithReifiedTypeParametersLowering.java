package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeSubstitutor;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: WrapInlineDeclarationsWithReifiedTypeParametersLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/WrapInlineDeclarationsWithReifiedTypeParametersLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WrapInlineDeclarationsWithReifiedTypeParametersLowering implements BodyLoweringPass {
    private final BackendContext context;

    public WrapInlineDeclarationsWithReifiedTypeParametersLowering(BackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrFactory getIrFactory() {
        return this.context.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, final IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        irBody.transformChildren(new IrElementTransformer<IrDeclarationParent>() { // from class: org.jetbrains.kotlin.backend.common.lower.WrapInlineDeclarationsWithReifiedTypeParametersLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
            public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBody2(IrBody irBody2, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBody(this, irBody2, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
            public IrBranch visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitCall2(IrCall irCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCall(this, irCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
            public IrCatch visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
            public IrStatement visitClass2(IrClass irClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClass(this, irClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
            public IrExpression visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            public IrExpression visitConst(IrConst<?> irConst, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
            public IrStatement visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
            public IrElement visitElement2(IrElement irElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
            public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
            public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
            public IrStatement visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitField(this, irField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
            public IrFile visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
            public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
            public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
            public IrExpression visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
            public IrStatement visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetValue2(IrSetValue irSetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
            public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
            public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
            public IrExpression visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
            public IrExpression visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
            public IrStatement visitVariable2(IrVariable irVariable, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclarationParent);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitDeclaration2(IrDeclarationBase declaration, IrDeclarationParent data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrDeclarationParent irDeclarationParent = declaration instanceof IrDeclarationParent ? (IrDeclarationParent) declaration : null;
                if (irDeclarationParent != null) {
                    data = irDeclarationParent;
                }
                return IrElementTransformer.DefaultImpls.visitDeclaration(this, declaration, data);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitFunctionReference2(final IrFunctionReference expression, IrDeclarationParent data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                expression.transformChildren(this, data);
                IrFunction owner = expression.getSymbol().getOwner();
                Boolean bool = null;
                final IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
                if (irSimpleFunction == null) {
                    return expression;
                }
                if (!IrUtilsKt.isInlineFunWithReifiedParameter(irSimpleFunction)) {
                    return expression;
                }
                Set<Map.Entry<IrTypeParameterSymbol, IrType>> setEntrySet = org.jetbrains.kotlin.ir.util.IrUtilsKt.getTypeSubstitutionMap(expression).entrySet();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
                Iterator<T> it = setEntrySet.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) entry.getKey();
                    Object obj = (IrType) entry.getValue();
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeArgument");
                    arrayList.add(TuplesKt.to(irTypeParameterSymbol, (IrTypeArgument) obj));
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add((IrTypeParameterSymbol) ((Pair) it2.next()).getFirst());
                }
                ArrayList arrayList4 = arrayList3;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    arrayList5.add((IrTypeArgument) ((Pair) it3.next()).getSecond());
                }
                IrTypeSubstitutor irTypeSubstitutor = new IrTypeSubstitutor(arrayList4, arrayList5, WrapInlineDeclarationsWithReifiedTypeParametersLowering.this.getContext().getIrBuiltIns(), false, 8, null);
                IrFactory irFactory = WrapInlineDeclarationsWithReifiedTypeParametersLowering.this.getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier(irSimpleFunction.getName() + "$wrap");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setReturnType(irTypeSubstitutor.substitute(irSimpleFunction.getReturnType()));
                DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                irFunctionBuilder.setVisibility(LOCAL);
                irFunctionBuilder.setOrigin(IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE);
                irFunctionBuilder.setStartOffset(-2);
                irFunctionBuilder.setEndOffset(-2);
                final IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                IrDeclaration irDeclaration = container;
                WrapInlineDeclarationsWithReifiedTypeParametersLowering wrapInlineDeclarationsWithReifiedTypeParametersLowering = WrapInlineDeclarationsWithReifiedTypeParametersLowering.this;
                if (data != null) {
                    irSimpleFunctionBuildFunction.setParent(data);
                    final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(wrapInlineDeclarationsWithReifiedTypeParametersLowering.getContext(), irSimpleFunctionBuildFunction.getSymbol(), -2, -2);
                    IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
                    boolean zBooleanValue = false;
                    if (extensionReceiverParameter != null) {
                        if (expression.getExtensionReceiver() == null) {
                            DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, extensionReceiverParameter.getName(), irTypeSubstitutor.substitute(extensionReceiverParameter.getType()), (IrDeclarationOrigin) null, 4, (Object) null);
                            bool = true;
                        }
                        if (bool != null) {
                            zBooleanValue = bool.booleanValue();
                        }
                    }
                    for (IrValueParameter irValueParameter : irSimpleFunction.getValueParameters()) {
                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, irValueParameter.getName(), irTypeSubstitutor.substitute(irValueParameter.getType()), (IrDeclarationOrigin) null, 4, (Object) null);
                    }
                    final boolean z = zBooleanValue;
                    irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(wrapInlineDeclarationsWithReifiedTypeParametersLowering.getIrFactory(), expression.getStartOffset(), expression.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.WrapInlineDeclarationsWithReifiedTypeParametersLowering$lower$1$visitFunctionReference$function$2$2
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
                            Pair pair;
                            Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                            List<IrStatement> statements = createBlockBody.getStatements();
                            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilder;
                            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunction.getSymbol());
                            IrFunctionReference irFunctionReference = expression;
                            IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
                            boolean z2 = z;
                            DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilderCreateIrBuilder;
                            IrExpression extensionReceiver = irFunctionReference.getExtensionReceiver();
                            if (extensionReceiver != null) {
                            }
                            IrExpression dispatchReceiver = irFunctionReference.getDispatchReceiver();
                            if (dispatchReceiver != null) {
                            }
                            if (z2) {
                                pair = TuplesKt.to(ExpressionHelpersKt.irGet(declarationIrBuilder3, (IrValueDeclaration) CollectionsKt.first((List) irSimpleFunction2.getValueParameters())), irSimpleFunction2.getValueParameters().subList(1, irSimpleFunction2.getValueParameters().size()));
                            } else {
                                pair = TuplesKt.to(irFunctionReference.getExtensionReceiver(), irSimpleFunction2.getValueParameters());
                            }
                            IrExpression irExpression = (IrExpression) pair.component1();
                            List list = (List) pair.component2();
                            irCall.setExtensionReceiver(irExpression);
                            irCall.setDispatchReceiver(irFunctionReference.getDispatchReceiver());
                            int i = 0;
                            for (Object obj2 : list) {
                                int i2 = i + 1;
                                if (i < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                irCall.putValueArgument(i, ExpressionHelpersKt.irGet(declarationIrBuilder3, (IrValueParameter) obj2));
                                i = i2;
                            }
                            int typeArgumentsCount = irFunctionReference.getTypeArgumentsCount();
                            for (int i3 = 0; i3 < typeArgumentsCount; i3++) {
                                irCall.putTypeArgument(i3, irFunctionReference.getTypeArgument(i3));
                            }
                            Unit unit = Unit.INSTANCE;
                            statements.add(ExpressionHelpersKt.irReturn(declarationIrBuilder2, irCall));
                        }
                    }));
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(WrapInlineDeclarationsWithReifiedTypeParametersLowering.this.getContext(), container.getSymbol(), 0, 0, 6, (Object) null);
                    IrFunctionReference irFunctionReference = expression;
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irFunctionReference.getStartOffset(), irFunctionReference.getEndOffset(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE, irFunctionReference.getType(), false, 64, null);
                    irBlockBuilder.unaryPlus(irSimpleFunctionBuildFunction);
                    irBlockBuilder.unaryPlus(IrFunctionReferenceImpl.INSTANCE.fromSymbolOwner(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getTypeParameters().size(), expression.getReflectionTarget(), (64 & 64) != 0 ? null : null));
                    return irBlockBuilder.getIrBlockBody();
                }
                throw new IllegalStateException(("Unable to get a proper parent while lower " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null) + " at " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }, container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null);
    }
}
