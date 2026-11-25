package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.MavenComparableVersion;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
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
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
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
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ApiVersionIsAtLeastEvaluationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u001fB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\rH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003H\u0016J\u0018\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\f*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u000e¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ApiVersionIsAtLeastEvaluationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/backend/jvm/lower/ApiVersionIsAtLeastEvaluationLowering$Data;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "apiVersion", "Lorg/jetbrains/kotlin/config/MavenComparableVersion;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "isApiVersionIsAtLeast", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "isInInlineFunInKotlinRuntime", "currentFunction", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "data", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Data", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersionIsAtLeastEvaluationLowering implements FileLoweringPass, IrElementTransformer<Data> {
    private final MavenComparableVersion apiVersion;
    private final JvmBackendContext context;

    public ApiVersionIsAtLeastEvaluationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.apiVersion = context.getState().getLanguageVersionSettings().getApiVersion().getVersion();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Data data) {
        return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitBody(this, irBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, Data data) {
        return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, Data data) {
        return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, Data data) {
        return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Data) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, Data data) {
        return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrStatement visitClass2(IrClass irClass, Data data) {
        return IrElementTransformer.DefaultImpls.visitClass(this, irClass, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitClassReference2(IrClassReference irClassReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrExpression visitComposite2(IrComposite irComposite, Data data) {
        return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Data) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, Data data) {
        return IrElementTransformer.DefaultImpls.visitConst(this, irConst, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrStatement visitConstructor2(IrConstructor irConstructor, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, Data data) {
        return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, Data data) {
        return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement irElement, Data data) {
        return IrElementTransformer.DefaultImpls.visitElement(this, irElement, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, Data data) {
        return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, Data data) {
        return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression irExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, Data data) {
        return IrElementTransformer.DefaultImpls.visitField(this, irField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile irFile, Data data) {
        return IrElementTransformer.DefaultImpls.visitFile(this, irFile, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue irGetValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Data data) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Data) obj);
    }

    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, Data data) {
        return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, Data data) {
        return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrStatement visitScript2(IrScript irScript, Data data) {
        return IrElementTransformer.DefaultImpls.visitScript(this, irScript, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, Data data) {
        return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Data data) {
        return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, Data data) {
        return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Data data) {
        return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Data data) {
        return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, Data data) {
        return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, Data data) {
        return IrElementTransformer.DefaultImpls.visitTry(this, irTry, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, Data data) {
        return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrExpression visitVararg2(IrVararg irVararg, Data data) {
        return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrStatement visitVariable2(IrVariable irVariable, Data data) {
        return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, Data data) {
        return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, data);
    }

    /* JADX INFO: compiled from: ApiVersionIsAtLeastEvaluationLowering.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ApiVersionIsAtLeastEvaluationLowering$Data;", "", "currentFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isInsideInlinedBlock", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Z)V", "getCurrentFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Data {
        private final IrFunction currentFunction;
        private final boolean isInsideInlinedBlock;

        public static /* synthetic */ Data copy$default(Data data, IrFunction irFunction, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                irFunction = data.currentFunction;
            }
            if ((i & 2) != 0) {
                z = data.isInsideInlinedBlock;
            }
            return data.copy(irFunction, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrFunction getCurrentFunction() {
            return this.currentFunction;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsInsideInlinedBlock() {
            return this.isInsideInlinedBlock;
        }

        public final Data copy(IrFunction currentFunction, boolean isInsideInlinedBlock) {
            return new Data(currentFunction, isInsideInlinedBlock);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Data)) {
                return false;
            }
            Data data = (Data) other;
            return Intrinsics.areEqual(this.currentFunction, data.currentFunction) && this.isInsideInlinedBlock == data.isInsideInlinedBlock;
        }

        public int hashCode() {
            IrFunction irFunction = this.currentFunction;
            return ((irFunction == null ? 0 : irFunction.hashCode()) * 31) + Boolean.hashCode(this.isInsideInlinedBlock);
        }

        public String toString() {
            return "Data(currentFunction=" + this.currentFunction + ", isInsideInlinedBlock=" + this.isInsideInlinedBlock + ')';
        }

        public Data(IrFunction irFunction, boolean z) {
            this.currentFunction = irFunction;
            this.isInsideInlinedBlock = z;
        }

        public final IrFunction getCurrentFunction() {
            return this.currentFunction;
        }

        public final boolean isInsideInlinedBlock() {
            return this.isInsideInlinedBlock;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(this, new Data(null, false));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock expression, Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return IrElementTransformer.DefaultImpls.visitBlock(this, expression, Data.copy$default(data, null, data.isInsideInlinedBlock() || (expression instanceof IrInlinedFunctionBlock), 1, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction declaration, Data data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        return IrElementTransformer.DefaultImpls.visitFunction(this, declaration, Data.copy$default(data, declaration, false, 2, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitCall2(IrCall expression, Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!data.isInsideInlinedBlock() || !isApiVersionIsAtLeast(expression.getSymbol().getOwner()) || isInInlineFunInKotlinRuntime(data.getCurrentFunction())) {
            IrElement irElementVisitCall = IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
            Intrinsics.checkNotNull(irElementVisitCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return (IrExpression) irElementVisitCall;
        }
        IrCall irCall = expression;
        Integer intConstArgumentOrNull = JvmIrUtilsKt.getIntConstArgumentOrNull(irCall, 0);
        if (intConstArgumentOrNull == null) {
            IrElement irElementVisitCall2 = IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
            Intrinsics.checkNotNull(irElementVisitCall2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return (IrExpression) irElementVisitCall2;
        }
        int iIntValue = intConstArgumentOrNull.intValue();
        Integer intConstArgumentOrNull2 = JvmIrUtilsKt.getIntConstArgumentOrNull(irCall, 1);
        if (intConstArgumentOrNull2 == null) {
            IrElement irElementVisitCall3 = IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
            Intrinsics.checkNotNull(irElementVisitCall3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return (IrExpression) irElementVisitCall3;
        }
        int iIntValue2 = intConstArgumentOrNull2.intValue();
        Integer intConstArgumentOrNull3 = JvmIrUtilsKt.getIntConstArgumentOrNull(irCall, 2);
        if (intConstArgumentOrNull3 == null) {
            IrElement irElementVisitCall4 = IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
            Intrinsics.checkNotNull(irElementVisitCall4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return (IrExpression) irElementVisitCall4;
        }
        int iIntValue3 = intConstArgumentOrNull3.intValue();
        IrFunction currentFunction = data.getCurrentFunction();
        if (currentFunction == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, currentFunction.getSymbol(), 0, 0, 6, null);
        return this.apiVersion.compareTo(new MavenComparableVersion(new StringBuilder().append(iIntValue).append('.').append(iIntValue2).append('.').append(iIntValue3).toString())) >= 0 ? ExpressionHelpersKt.irTrue(jvmIrBuilderCreateJvmIrBuilder$default) : ExpressionHelpersKt.irFalse(jvmIrBuilderCreateJvmIrBuilder$default);
    }

    private final boolean isInInlineFunInKotlinRuntime(IrFunction currentFunction) {
        return currentFunction != null && currentFunction.getIsInline() && IrUtilsKt.getPackageFragment((IrDeclaration) currentFunction).getPackageFqName().startsWith(StandardNames.BUILT_INS_PACKAGE_NAME);
    }

    private final boolean isApiVersionIsAtLeast(IrFunction irFunction) {
        return Intrinsics.areEqual(irFunction.getName().asString(), "apiVersionIsAtLeast") && Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) irFunction).getPackageFqName(), StandardNames.KOTLIN_INTERNAL_FQ_NAME) && IrUtilsKt.isFacadeClass(irFunction.getParent()) && irFunction.getValueParameters().size() == 3 && IrTypePredicatesKt.isInt(irFunction.getValueParameters().get(0).getType()) && IrTypePredicatesKt.isInt(irFunction.getValueParameters().get(1).getType()) && IrTypePredicatesKt.isInt(irFunction.getValueParameters().get(2).getType()) && irFunction.getDispatchReceiverParameter() == null && irFunction.getExtensionReceiverParameter() == null;
    }
}
