package org.jetbrains.kotlin.ir.interpreter.preprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: IrInterpreterKCallableNamePreprocessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterKCallableNamePreprocessor;", "Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterPreprocessor;", "()V", "visitCall", "Lorg/jetbrains/kotlin/ir/IrElement;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterPreprocessorData;", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreterKCallableNamePreprocessor implements IrInterpreterPreprocessor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock irBlock, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBlock(this, irBlock, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBlockBody(this, irBlockBody, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBody(this, irBody, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBranch(this, irBranch, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBreak(this, irBreak, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrInterpreterPreprocessorData) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrInterpreterPreprocessorData) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitCallableReference(this, irCallableReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitCatch(this, irCatch, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrStatement visitClass2(IrClass irClass, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitClass(this, irClass, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitClassReference2(IrClassReference irClassReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitClassReference(this, irClassReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrExpression visitComposite2(IrComposite irComposite, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitComposite(this, irComposite, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (IrInterpreterPreprocessorData) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (IrInterpreterPreprocessorData) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConst(this, irConst, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstantArray(this, irConstantArray, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstantObject(this, irConstantObject, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstantValue(this, irConstantValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrStatement visitConstructor2(IrConstructor irConstructor, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstructor(this, irConstructor, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitContinue(this, irContinue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement irElement, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitElement(this, irElement, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitElseBranch(this, irElseBranch, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression irExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitExpression(this, irExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitField(this, irField, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile irFile, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFile(this, irFile, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction irFunction, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFunction(this, irFunction, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitGetClass(this, irGetClass, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitGetField(this, irGetField, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue irGetValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitGetValue(this, irGetValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitLoop(this, irLoop, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrInterpreterPreprocessorData) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrInterpreterPreprocessorData) obj);
    }

    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitProperty(this, irProperty, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitReturn(this, irReturn, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrStatement visitScript2(IrScript irScript, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitScript(this, irScript, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSetField(this, irSetField, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSetValue(this, irSetValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitThrow(this, irThrow, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitTry(this, irTry, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitValueParameter(this, irValueParameter, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrExpression visitVararg2(IrVararg irVararg, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitVararg(this, irVararg, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrStatement visitVariable2(IrVariable irVariable, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitVariable(this, irVariable, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitWhen(this, irWhen, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrInterpreterPreprocessorData irInterpreterPreprocessorData) {
        return IrInterpreterPreprocessor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irInterpreterPreprocessorData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitCall2(IrCall expression, IrInterpreterPreprocessorData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!INSTANCE.isKCallableNameCall(expression, data.getIrBuiltIns())) {
            return IrInterpreterPreprocessor.DefaultImpls.visitCall(this, expression, data);
        }
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        IrCallableReference irCallableReference = dispatchReceiver instanceof IrCallableReference ? (IrCallableReference) dispatchReceiver : null;
        if (irCallableReference == null) {
            return IrInterpreterPreprocessor.DefaultImpls.visitCall(this, expression, data);
        }
        IrExpression dispatchReceiver2 = irCallableReference.getDispatchReceiver();
        if (dispatchReceiver2 == null && (dispatchReceiver2 = irCallableReference.getExtensionReceiver()) == null) {
            return expression;
        }
        IrType type = irCallableReference.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
            Intrinsics.checkNotNull(typeOrNull);
            arrayList.add(typeOrNull);
        }
        ArrayList arrayList2 = arrayList;
        if (IrTypeUtilsKt.isKFunction(irCallableReference.getType())) {
            IrClass irClassKFunctionN = data.getIrBuiltIns().kFunctionN(arrayList2.size());
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(dispatchReceiver2.getType());
            spreadBuilder.addSpread(arrayList2.toArray(new IrType[0]));
            irCallableReference.setType(IrTypesKt.typeWith(irClassKFunctionN, (IrType[]) spreadBuilder.toArray(new IrType[spreadBuilder.size()])));
        }
        IrProperty irProperty = null;
        boolean z = false;
        for (IrProperty irProperty2 : IrUtilsKt.getProperties(data.getIrBuiltIns().getKCallableClass().getOwner())) {
            if (Intrinsics.areEqual(irProperty2.getName().asString(), "name")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irProperty = irProperty2;
                z = true;
            }
        }
        if (z) {
            IrSimpleFunction getter = irProperty.getGetter();
            Intrinsics.checkNotNull(getter);
            expression.setSymbol(getter.getSymbol());
            irCallableReference.setDispatchReceiver(null);
            irCallableReference.setExtensionReceiver(null);
            return ((dispatchReceiver2 instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) dispatchReceiver2).getSymbol().getOwner().getName(), SpecialNames.THIS)) ? expression : new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, CollectionsKt.listOf((Object[]) new IrExpression[]{dispatchReceiver2, expression}));
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    /* JADX INFO: compiled from: IrInterpreterKCallableNamePreprocessor.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005J\u0012\u0010\u0006\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterKCallableNamePreprocessor$Companion;", "", "()V", "isEnumName", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "isKCallableNameCall", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isKCallableNameCall(IrCall irCall, IrBuiltIns irBuiltIns) {
            Intrinsics.checkNotNullParameter(irCall, "<this>");
            Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
            if (!(irCall.getDispatchReceiver() instanceof IrCallableReference)) {
                return false;
            }
            IrSimpleFunction owner = irCall.getSymbol().getOwner();
            IrProperty property = UtilsKt.getProperty(owner);
            IrSimpleFunction irSimpleFunction = property != null ? property : owner;
            IrDeclarationParent parent = ((IrDeclarationBase) irSimpleFunction).getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null || !IrUtilsKt.isSubclassOf(irClass, irBuiltIns.getKCallableClass().getOwner())) {
                return false;
            }
            if (!(irSimpleFunction instanceof IrSimpleFunction) && !(irSimpleFunction instanceof IrProperty)) {
                throw new AssertionError("Should be IrSimpleFunction or IrProperty, got " + irSimpleFunction);
            }
            Name name2 = irSimpleFunction.getName();
            return Intrinsics.areEqual(name2.asString(), "name");
        }

        public final boolean isEnumName(IrCall irCall) {
            IrProperty property;
            Intrinsics.checkNotNullParameter(irCall, "<this>");
            IrSimpleFunction owner = irCall.getSymbol().getOwner();
            return owner.getExtensionReceiverParameter() == null && owner.getValueParameters().isEmpty() && (property = UtilsKt.getProperty(owner)) != null && (irCall.getDispatchReceiver() instanceof IrGetEnumValue) && Intrinsics.areEqual(property.getName().asString(), "name");
        }
    }
}
