package org.jetbrains.kotlin.ir.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterAssertionError;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterError;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.Complex;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: InstructionsUnfolder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0094\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u001c\u0010\u000f\u001a\u00020\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0012\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0017\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u001c\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010!\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020'2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020-2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u0010.\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020/2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u00100\u001a\u00020\u00012\u0006\u0010\r\u001a\u0002012\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u00102\u001a\u00020\u00012\u0006\u00103\u001a\u0002042\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u00105\u001a\u00020\u00012\u0006\u00103\u001a\u0002062\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u00107\u001a\u00020\u00012\u0006\u00103\u001a\u0002082\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u001a\u0010<\u001a\u00020\u00012\b\u0010\r\u001a\u0004\u0018\u00010=2\u0006\u0010#\u001a\u00020$H\u0000\u001a\u0018\u0010>\u001a\u00020\u00012\u0006\u0010?\u001a\u00020@2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010A\u001a\u00020\u00012\u0006\u00103\u001a\u00020B2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010C\u001a\u00020\u00012\u0006\u00103\u001a\u00020D2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010E\u001a\u00020\u00012\u0006\u00103\u001a\u00020F2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u001e\u0010G\u001a\u00020\u00012\f\u0010H\u001a\b\u0012\u0004\u0012\u00020J0I2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010K\u001a\u00020\u00012\u0006\u00103\u001a\u00020L2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u0010M\u001a\u00020\u00012\u0006\u00103\u001a\u00020N2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010O\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020P2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010Q\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020R2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010S\u001a\u00020\u00012\u0006\u00103\u001a\u00020T2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0018\u0010U\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020V2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010W\u001a\u00020\u00012\u0006\u0010X\u001a\u00020Y2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010Z\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020[2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\\\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020]2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0014\u0010^\u001a\u00020\u0001*\u00020_2\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006`"}, d2 = {"unfoldBlock", "", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "unfoldBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "unfoldBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "unfoldBreak", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "unfoldCallableReference", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "unfoldCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "unfoldClassReference", "classReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "unfoldComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "unfoldConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "unfoldContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "unfoldDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "unfoldEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "unfoldEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "unfoldField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "unfoldFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "unfoldFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "unfoldGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "unfoldGetEnumValue", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "unfoldGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "unfoldGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "unfoldInstanceInitializerCall", "instanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "unfoldInstruction", "Lorg/jetbrains/kotlin/ir/IrElement;", "unfoldPropertyReference", "propertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "unfoldReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "unfoldSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "unfoldSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "unfoldStatements", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "unfoldStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "unfoldThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "unfoldTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "unfoldTypeOperatorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "unfoldValueParameters", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "unfoldVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "unfoldVariable", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "unfoldWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "unfoldWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "handleAndDropResult", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InstructionsUnfolderKt {
    public static final void handleAndDropResult(IrExpression irExpression, final CallStack callStack) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(callStack, "callStack");
        callStack.pushInstruction(new CustomInstruction(new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.InstructionsUnfolderKt$handleAndDropResult$dropResult$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                callStack.popState();
            }
        }));
        InstructionKt.pushCompoundInstruction(callStack, irExpression);
    }

    public static final void unfoldInstruction(IrElement irElement, IrInterpreterEnvironment environment) throws InterpreterError {
        Intrinsics.checkNotNullParameter(environment, "environment");
        CallStack callStack = environment.getCallStack();
        if (irElement == null) {
            return;
        }
        if (irElement instanceof IrSimpleFunction) {
            unfoldFunction((IrSimpleFunction) irElement, environment);
            return;
        }
        if (irElement instanceof IrConstructor) {
            unfoldConstructor((IrConstructor) irElement, callStack);
            return;
        }
        if (irElement instanceof IrCall) {
            unfoldValueParameters((IrFunctionAccessExpression) irElement, environment);
            return;
        }
        if (irElement instanceof IrConstructorCall) {
            unfoldValueParameters((IrFunctionAccessExpression) irElement, environment);
            return;
        }
        if (irElement instanceof IrEnumConstructorCall) {
            unfoldEnumConstructorCall((IrEnumConstructorCall) irElement, environment);
            return;
        }
        if (irElement instanceof IrDelegatingConstructorCall) {
            unfoldValueParameters((IrFunctionAccessExpression) irElement, environment);
            return;
        }
        if (irElement instanceof IrInstanceInitializerCall) {
            unfoldInstanceInitializerCall((IrInstanceInitializerCall) irElement, callStack);
            return;
        }
        if (irElement instanceof IrField) {
            unfoldField((IrField) irElement, callStack);
            return;
        }
        if (irElement instanceof IrBody) {
            unfoldBody((IrBody) irElement, callStack);
            return;
        }
        if (irElement instanceof IrBlock) {
            unfoldBlock((IrBlock) irElement, callStack);
            return;
        }
        if (irElement instanceof IrReturn) {
            unfoldReturn((IrReturn) irElement, callStack);
            return;
        }
        if (irElement instanceof IrSetField) {
            unfoldSetField((IrSetField) irElement, callStack);
            return;
        }
        if (irElement instanceof IrGetField) {
            InstructionKt.pushSimpleInstruction(callStack, irElement);
            return;
        }
        if (irElement instanceof IrGetValue) {
            unfoldGetValue((IrGetValue) irElement, environment);
            return;
        }
        if (irElement instanceof IrGetObjectValue) {
            unfoldGetObjectValue((IrGetObjectValue) irElement, environment);
            return;
        }
        if (irElement instanceof IrGetEnumValue) {
            unfoldGetEnumValue((IrGetEnumValue) irElement, environment);
            return;
        }
        if (irElement instanceof IrEnumEntry) {
            unfoldEnumEntry((IrEnumEntry) irElement, environment);
            return;
        }
        if (irElement instanceof IrConst) {
            InstructionKt.pushSimpleInstruction(callStack, irElement);
            return;
        }
        if (irElement instanceof IrVariable) {
            unfoldVariable((IrVariable) irElement, callStack);
            return;
        }
        if (irElement instanceof IrSetValue) {
            unfoldSetValue((IrSetValue) irElement, callStack);
            return;
        }
        if (irElement instanceof IrTypeOperatorCall) {
            unfoldTypeOperatorCall((IrTypeOperatorCall) irElement, callStack);
            return;
        }
        if (irElement instanceof IrBranch) {
            unfoldBranch((IrBranch) irElement, callStack);
            return;
        }
        if (irElement instanceof IrWhileLoop) {
            unfoldWhileLoop((IrWhileLoop) irElement, callStack);
            return;
        }
        if (irElement instanceof IrDoWhileLoop) {
            unfoldDoWhileLoop((IrDoWhileLoop) irElement, callStack);
            return;
        }
        if (irElement instanceof IrWhen) {
            unfoldWhen((IrWhen) irElement, callStack);
            return;
        }
        if (irElement instanceof IrBreak) {
            unfoldBreak((IrBreak) irElement, callStack);
            return;
        }
        if (irElement instanceof IrContinue) {
            unfoldContinue((IrContinue) irElement, callStack);
            return;
        }
        if (irElement instanceof IrVararg) {
            unfoldVararg((IrVararg) irElement, callStack);
            return;
        }
        if (irElement instanceof IrSpreadElement) {
            InstructionKt.pushCompoundInstruction(callStack, ((IrSpreadElement) irElement).getExpression());
            return;
        }
        if (irElement instanceof IrTry) {
            unfoldTry((IrTry) irElement, callStack);
            return;
        }
        if (irElement instanceof IrCatch) {
            unfoldCatch((IrCatch) irElement, callStack);
            return;
        }
        if (irElement instanceof IrThrow) {
            unfoldThrow((IrThrow) irElement, callStack);
            return;
        }
        if (irElement instanceof IrStringConcatenation) {
            unfoldStringConcatenation((IrStringConcatenation) irElement, environment);
            return;
        }
        if (irElement instanceof IrFunctionExpression) {
            InstructionKt.pushSimpleInstruction(callStack, irElement);
            return;
        }
        if (irElement instanceof IrFunctionReference) {
            unfoldFunctionReference((IrFunctionReference) irElement, callStack);
            return;
        }
        if (irElement instanceof IrPropertyReference) {
            unfoldPropertyReference((IrPropertyReference) irElement, callStack);
            return;
        }
        if (irElement instanceof IrClassReference) {
            unfoldClassReference((IrClassReference) irElement, callStack);
            return;
        }
        if (irElement instanceof IrGetClass) {
            unfoldGetClass((IrGetClass) irElement, callStack);
        } else if (irElement instanceof IrComposite) {
            unfoldComposite((IrComposite) irElement, callStack);
        } else {
            throw new NotImplementedError("An operation is not implemented: " + (irElement.getClass() + " not supported"));
        }
    }

    private static final void unfoldFunction(IrSimpleFunction irSimpleFunction, IrInterpreterEnvironment irInterpreterEnvironment) throws InterpreterError {
        Unit unit;
        if (irInterpreterEnvironment.getCallStack().getStackCount() >= irInterpreterEnvironment.getConfiguration().getMaxStack()) {
            irInterpreterEnvironment.getCallStack().dropFrame();
            ExceptionUtilsKt.handleUserException(new StackOverflowError(), irInterpreterEnvironment);
            return;
        }
        IrBody body = irSimpleFunction.getBody();
        if (body != null) {
            InstructionKt.pushCompoundInstruction(irInterpreterEnvironment.getCallStack(), body);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            throw new InterpreterError("Ir function must be with body");
        }
    }

    private static final void unfoldConstructor(IrConstructor irConstructor, CallStack callStack) {
        Primitive<?> primitiveNullStateOfType;
        String fqName = UtilsKt.getFqName(irConstructor);
        if (Intrinsics.areEqual(fqName, "kotlin.Enum.<init>")) {
            return;
        }
        if (Intrinsics.areEqual(fqName, "kotlin.Throwable.<init>")) {
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
            IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            State stateLoadState = callStack.loadState(thisReceiver.getSymbol());
            List<IrDeclaration> declarations = parentAsClass.getDeclarations();
            ArrayList<IrProperty> arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrProperty) {
                    arrayList.add(obj);
                }
            }
            for (IrProperty irProperty : arrayList) {
                Iterator<T> it = irConstructor.getValueParameters().iterator();
                Object obj2 = null;
                boolean z = false;
                Object obj3 = null;
                while (true) {
                    if (!it.hasNext()) {
                        if (!z) {
                            break;
                        }
                        obj2 = obj3;
                        break;
                    } else {
                        Object next = it.next();
                        if (Intrinsics.areEqual(((IrValueParameter) next).getName(), irProperty.getName())) {
                            if (z) {
                                break;
                            }
                            z = true;
                            obj3 = next;
                        }
                    }
                }
                IrValueParameter irValueParameter = (IrValueParameter) obj2;
                if (irValueParameter == null || (primitiveNullStateOfType = callStack.loadState(irValueParameter.getSymbol())) == null) {
                    Primitive.Companion companion = Primitive.INSTANCE;
                    IrSimpleFunction getter = irProperty.getGetter();
                    Intrinsics.checkNotNull(getter);
                    primitiveNullStateOfType = companion.nullStateOfType(getter.getReturnType());
                }
                stateLoadState.setField(irProperty.getSymbol(), primitiveNullStateOfType);
            }
            return;
        }
        IrBody body = irConstructor.getBody();
        Intrinsics.checkNotNull(body);
        InstructionKt.pushCompoundInstruction(callStack, body);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r4v14, types: [java.lang.Object, org.jetbrains.kotlin.ir.declarations.IrValueParameter] */
    /* JADX WARN: Type inference failed for: r4v16, types: [org.jetbrains.kotlin.ir.declarations.IrVariable] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    private static final void unfoldValueParameters(IrFunctionAccessExpression irFunctionAccessExpression, IrInterpreterEnvironment irInterpreterEnvironment) {
        IrValueParameter irValueParameter;
        IrValueParameter irValueParameter2;
        CallStack callStack = irInterpreterEnvironment.getCallStack();
        IrFunction owner = irFunctionAccessExpression.getSymbol().getOwner();
        if (Intrinsics.areEqual(owner.getName().asString(), "<get-name>") && (irFunctionAccessExpression.getDispatchReceiver() instanceof IrGetEnumValue)) {
            InstructionKt.pushSimpleInstruction(callStack, irFunctionAccessExpression);
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            InstructionKt.pushSimpleInstruction(callStack, dispatchReceiverParameter);
            IrExpression dispatchReceiver = irFunctionAccessExpression.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrGetEnumValue");
            callStack.pushState(UtilsKt.toState(((IrGetEnumValue) dispatchReceiver).getSymbol().getOwner(), irInterpreterEnvironment.getIrBuiltIns()));
            return;
        }
        boolean z = false;
        IntRange intRangeUntil = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount());
        int i = 1;
        if (!(intRangeUntil instanceof Collection) || !((Collection) intRangeUntil).isEmpty()) {
            Iterator it = intRangeUntil.iterator();
            while (it.hasNext()) {
                if (irFunctionAccessExpression.getValueArgument(((IntIterator) it).nextInt()) == null) {
                    boolean z2 = irFunctionAccessExpression instanceof IrDelegatingConstructorCall;
                    IrFunctionSymbol cachedFunction$ir_interpreter$default = IrInterpreterEnvironment.getCachedFunction$ir_interpreter$default(irInterpreterEnvironment, irFunctionAccessExpression.getSymbol(), false, false, z2, 6, null);
                    DescriptorsRemapper descriptorsRemapper = null;
                    ?? r5 = 0;
                    ?? r6 = 0;
                    ?? r7 = 0;
                    ?? r8 = 0;
                    ?? r9 = 0;
                    if (cachedFunction$ir_interpreter$default != null) {
                        IrCall irCallCreateCall$default = IrTreeBuildUtilsKt.createCall$default(cachedFunction$ir_interpreter$default.getOwner(), null, 1, null);
                        IrTreeBuildUtilsKt.copyArgs(irInterpreterEnvironment.getIrBuiltIns(), irFunctionAccessExpression, irCallCreateCall$default);
                        InstructionKt.pushCompoundInstruction(callStack, irCallCreateCall$default);
                        return;
                    }
                    int valueArgumentsCount = irFunctionAccessExpression.getValueArgumentsCount();
                    ArrayList arrayList = new ArrayList(valueArgumentsCount);
                    for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
                        arrayList.add(null);
                    }
                    ArrayList arrayList2 = arrayList;
                    IrFunction functionThatContainsDefaults = UtilsKt.getFunctionThatContainsDefaults(irFunctionAccessExpression);
                    DescriptorVisibility visibility = irFunctionAccessExpression instanceof IrEnumConstructorCall ? true : z2 ? DescriptorVisibilities.LOCAL : functionThatContainsDefaults.getVisibility();
                    Intrinsics.checkNotNull(visibility);
                    Name nameIdentifier = Name.identifier(functionThatContainsDefaults.getName().asString() + "$default");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    IrSimpleFunction irSimpleFunctionCreateTempFunction = IrTreeBuildUtilsKt.createTempFunction(nameIdentifier, functionThatContainsDefaults.getReturnType(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE, visibility);
                    irSimpleFunctionCreateTempFunction.setParent(functionThatContainsDefaults.getParent());
                    IrValueParameter dispatchReceiverParameter2 = functionThatContainsDefaults.getDispatchReceiverParameter();
                    if (dispatchReceiverParameter2 != null) {
                        IrValueParameter irValueParameter3 = dispatchReceiverParameter2;
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, i, r9 == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(irValueParameter3, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irValueParameter3.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunctionCreateTempFunction);
                        if (irElementPatchDeclarationParents != null) {
                            irValueParameter = (IrValueParameter) irElementPatchDeclarationParents;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        }
                    } else {
                        irValueParameter = null;
                    }
                    irSimpleFunctionCreateTempFunction.setDispatchReceiverParameter(irValueParameter);
                    IrValueParameter extensionReceiverParameter = functionThatContainsDefaults.getExtensionReceiverParameter();
                    if (extensionReceiverParameter != null) {
                        IrValueParameter irValueParameter4 = extensionReceiverParameter;
                        DeepCopySymbolRemapper deepCopySymbolRemapper3 = new DeepCopySymbolRemapper(r8 == true ? 1 : 0, i, r7 == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(irValueParameter4, deepCopySymbolRemapper3);
                        DeepCopySymbolRemapper deepCopySymbolRemapper4 = deepCopySymbolRemapper3;
                        IrElement irElementPatchDeclarationParents2 = PatchDeclarationParentsKt.patchDeclarationParents(irValueParameter4.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper4, new DeepCopyTypeRemapper(deepCopySymbolRemapper4)), (Object) null), irSimpleFunctionCreateTempFunction);
                        if (irElementPatchDeclarationParents2 != null) {
                            irValueParameter2 = (IrValueParameter) irElementPatchDeclarationParents2;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        }
                    } else {
                        irValueParameter2 = null;
                    }
                    irSimpleFunctionCreateTempFunction.setExtensionReceiverParameter(irValueParameter2);
                    Iterator<Integer> it2 = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount()).iterator();
                    while (it2.hasNext()) {
                        int iNextInt = ((IntIterator) it2).nextInt();
                        IrValueParameter irValueParameter5 = functionThatContainsDefaults.getValueParameters().get(iNextInt);
                        IrValueParameter irValueParameter6 = irValueParameter5;
                        DeepCopySymbolRemapper deepCopySymbolRemapper5 = new DeepCopySymbolRemapper(r6 == true ? 1 : 0, i, r5 == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(irValueParameter6, deepCopySymbolRemapper5);
                        DeepCopySymbolRemapper deepCopySymbolRemapper6 = deepCopySymbolRemapper5;
                        IrElement irElementPatchDeclarationParents3 = PatchDeclarationParentsKt.patchDeclarationParents(irValueParameter6.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper6, new DeepCopyTypeRemapper(deepCopySymbolRemapper6)), (Object) null), irSimpleFunctionCreateTempFunction);
                        if (irElementPatchDeclarationParents3 != null) {
                            IrValueParameter irValueParameterCreateTempVariable = (IrValueParameter) irElementPatchDeclarationParents3;
                            irSimpleFunctionCreateTempFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionCreateTempFunction.getValueParameters(), irValueParameterCreateTempVariable));
                            if (irValueParameterCreateTempVariable.getDefaultValue() != null || AdditionalIrUtilsKt.isVararg(irValueParameterCreateTempVariable)) {
                                irValueParameterCreateTempVariable.setType(IrTypesKt.makeNullable(irValueParameterCreateTempVariable.getType()));
                                IrValueParameter irValueParameter7 = irValueParameterCreateTempVariable;
                                IrGetValue irGetValueCreateGetValue = IrTreeBuildUtilsKt.createGetValue(irValueParameter7);
                                IrConstructorCall defaultWithActualParameters = UtilsKt.getDefaultWithActualParameters(irValueParameter5, irSimpleFunctionCreateTempFunction, arrayList2);
                                if (defaultWithActualParameters == null) {
                                    IrBuiltIns irBuiltIns = irInterpreterEnvironment.getIrBuiltIns();
                                    IrType varargType = UtilsKt.getVarargType(irFunctionAccessExpression, iNextInt);
                                    Intrinsics.checkNotNull(varargType);
                                    defaultWithActualParameters = IrTreeBuildUtilsKt.emptyArrayConstructor(irBuiltIns, UtilsKt.getTypeIfReified(varargType, callStack));
                                }
                                irValueParameterCreateTempVariable = IrTreeBuildUtilsKt.createTempVariable(irValueParameter7);
                                IrGetValue irGetValue = irGetValueCreateGetValue;
                                irValueParameterCreateTempVariable.setInitializer(IrTreeBuildUtilsKt.irIfNullThenElse(irInterpreterEnvironment.getIrBuiltIns(), irGetValue, defaultWithActualParameters, irGetValue));
                                Unit unit = Unit.INSTANCE;
                            }
                            arrayList2.set(iNextInt, (IrDeclaration) irValueParameterCreateTempVariable);
                            z = false;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        }
                    }
                    IrFunctionAccessExpression irFunctionAccessExpressionShallowCopy$default = IrTreeBuildUtilsKt.shallowCopy$default(irFunctionAccessExpression, z, 1, null);
                    if (irFunctionAccessExpression.getDispatchReceiver() != null) {
                        IrValueParameter dispatchReceiverParameter3 = irSimpleFunctionCreateTempFunction.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter3);
                        irFunctionAccessExpressionShallowCopy$default.setDispatchReceiver(IrTreeBuildUtilsKt.createGetValue(dispatchReceiverParameter3));
                    }
                    if (irFunctionAccessExpression.getExtensionReceiver() != null) {
                        IrValueParameter extensionReceiverParameter2 = irSimpleFunctionCreateTempFunction.getExtensionReceiverParameter();
                        Intrinsics.checkNotNull(extensionReceiverParameter2);
                        irFunctionAccessExpressionShallowCopy$default.setExtensionReceiver(IrTreeBuildUtilsKt.createGetValue(extensionReceiverParameter2));
                    }
                    Iterator<Integer> it3 = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount()).iterator();
                    while (it3.hasNext()) {
                        int iNextInt2 = ((IntIterator) it3).nextInt();
                        IrValueDeclaration irValueDeclaration = (IrValueDeclaration) arrayList2.get(iNextInt2);
                        irFunctionAccessExpressionShallowCopy$default.putValueArgument(iNextInt2, irValueDeclaration != null ? IrTreeBuildUtilsKt.createGetValue(irValueDeclaration) : null);
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj : arrayList2) {
                        if (obj instanceof IrVariable) {
                            arrayList3.add(obj);
                        }
                    }
                    irSimpleFunctionCreateTempFunction.setBody(IrTreeBuildUtilsKt.wrapWithBlockBody(CollectionsKt.plus((Collection<? extends IrReturn>) arrayList3, IrTreeBuildUtilsKt.createReturn(irSimpleFunctionCreateTempFunction, irFunctionAccessExpressionShallowCopy$default))));
                    IrCall irCallCreateCall$default2 = IrTreeBuildUtilsKt.createCall$default(irInterpreterEnvironment.setCachedFunction$ir_interpreter(irFunctionAccessExpression.getSymbol(), (6 & 2) != 0 ? false : false, (6 & 4) != 0 ? false : false, (6 & 8) != 0 ? false : z2, irSimpleFunctionCreateTempFunction.getSymbol()).getOwner(), null, 1, null);
                    IrTreeBuildUtilsKt.copyArgs(irInterpreterEnvironment.getIrBuiltIns(), irFunctionAccessExpression, irCallCreateCall$default2);
                    InstructionKt.pushCompoundInstruction(callStack, irCallCreateCall$default2);
                    return;
                }
            }
        }
        InstructionKt.pushSimpleInstruction(callStack, irFunctionAccessExpression);
        Iterator<Integer> it4 = RangesKt.downTo(irFunctionAccessExpression.getValueArgumentsCount() - 1, 0).iterator();
        while (it4.hasNext()) {
            int iNextInt3 = ((IntIterator) it4).nextInt();
            unfoldValueParameters$schedule(owner.getValueParameters().get(iNextInt3), callStack, irFunctionAccessExpression.getValueArgument(iNextInt3));
        }
        IrExpression extensionReceiver = irFunctionAccessExpression.getExtensionReceiver();
        if (extensionReceiver != null) {
            IrValueParameter extensionReceiverParameter3 = owner.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter3);
            unfoldValueParameters$schedule(extensionReceiverParameter3, callStack, extensionReceiver);
        }
        IrExpression dispatchReceiver2 = irFunctionAccessExpression.getDispatchReceiver();
        if (dispatchReceiver2 != null) {
            IrValueParameter dispatchReceiverParameter4 = owner.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter4);
            unfoldValueParameters$schedule(dispatchReceiverParameter4, callStack, dispatchReceiver2);
        }
    }

    private static final void unfoldValueParameters$schedule(IrValueParameter irValueParameter, CallStack callStack, IrExpression irExpression) {
        InstructionKt.pushSimpleInstruction(callStack, irValueParameter);
        InstructionKt.pushCompoundInstruction(callStack, irExpression);
    }

    private static final void unfoldEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, IrInterpreterEnvironment irInterpreterEnvironment) {
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irEnumConstructorCall.getSymbol().getOwner());
        if (Intrinsics.areEqual(parentClassOrNull != null ? UtilsKt.getFqName(parentClassOrNull) : null, "kotlin.Enum")) {
            IrEnumConstructorCall irEnumConstructorCall2 = irEnumConstructorCall;
            int i = 0;
            IrFunctionAccessExpression irFunctionAccessExpressionShallowCopy$default = IrTreeBuildUtilsKt.shallowCopy$default(irEnumConstructorCall2, false, 1, null);
            State stateLoadState = irInterpreterEnvironment.getCallStack().loadState(UtilsKt.getThisReceiver(irEnumConstructorCall2));
            List<IrDeclaration> declarations = irInterpreterEnvironment.getIrBuiltIns().getEnumClass().getOwner().getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrProperty) {
                    arrayList.add(obj);
                }
            }
            for (Object obj2 : arrayList) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                State field = stateLoadState.getField(((IrProperty) obj2).getSymbol());
                Intrinsics.checkNotNull(field, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
                Primitive primitive = (Primitive) field;
                irFunctionAccessExpressionShallowCopy$default.putValueArgument(i, IrUtilsKt.toIrConst$default(primitive.getValue(), primitive.getType(), 0, 0, 6, null));
                i = i2;
            }
            unfoldValueParameters(irFunctionAccessExpressionShallowCopy$default, irInterpreterEnvironment);
            return;
        }
        unfoldValueParameters(irEnumConstructorCall, irInterpreterEnvironment);
    }

    private static final void unfoldInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, CallStack callStack) {
        IrExpressionBody initializer;
        IrValueParameterSymbol symbol;
        IrClass owner = irInstanceInitializerCall.getClassSymbol().getOwner();
        List<IrDeclaration> declarations = owner.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            IrDeclaration irDeclaration = (IrDeclaration) obj;
            if ((irDeclaration instanceof IrProperty) || (irDeclaration instanceof IrAnonymousInitializer)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        IrValueParameter thisReceiver = owner.getThisReceiver();
        State stateLoadState = (thisReceiver == null || (symbol = thisReceiver.getSymbol()) == null) ? null : callStack.loadState(symbol);
        InstructionKt.pushSimpleInstruction(callStack, irInstanceInitializerCall);
        for (IrDeclaration irDeclaration2 : CollectionsKt.reversed(arrayList2)) {
            if (irDeclaration2 instanceof IrAnonymousInitializer) {
                InstructionKt.pushCompoundInstruction(callStack, ((IrAnonymousInitializer) irDeclaration2).getBody());
            } else if (irDeclaration2 instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration2;
                IrField backingField = irProperty.getBackingField();
                if (((backingField == null || (initializer = backingField.getInitializer()) == null) ? null : initializer.getExpression()) != null) {
                    if ((stateLoadState != null ? stateLoadState.getField(irProperty.getSymbol()) : null) == null) {
                        InstructionKt.pushCompoundInstruction(callStack, irProperty.getBackingField());
                    }
                }
            }
        }
    }

    private static final void unfoldField(IrField irField, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irField);
        IrExpressionBody initializer = irField.getInitializer();
        InstructionKt.pushCompoundInstruction(callStack, initializer != null ? initializer.getExpression() : null);
    }

    private static final void unfoldBody(IrBody irBody, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irBody);
        unfoldStatements(IrUtilsKt.getStatements(irBody), callStack);
    }

    private static final void unfoldBlock(IrBlock irBlock, CallStack callStack) {
        IrFunction irFunction;
        IrFunction originalFunction;
        if (irBlock instanceof IrReturnableBlock) {
            IrFunction inlineFunction = IrInlineUtilsKt.getInlineFunction((IrReturnableBlock) irBlock);
            if (inlineFunction == null || (originalFunction = IrUtilsKt.getOriginalFunction(inlineFunction)) == null) {
                irFunction = null;
            } else {
                IrProperty property = UtilsKt.getProperty(originalFunction);
                irFunction = property != null ? property : originalFunction;
            }
            if (irFunction != null && UtilsKt.hasAnnotation((IrAnnotationContainer) irFunction, UtilsKt.getIntrinsicConstEvaluationAnnotation())) {
                Object objSingle = CollectionsKt.single((List<? extends Object>) irBlock.getStatements());
                Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock");
                InstructionKt.pushCompoundInstruction(callStack, ((IrInlinedFunctionBlock) objSingle).getInlineCall());
                return;
            }
        }
        IrBlock irBlock2 = irBlock;
        callStack.newSubFrame(irBlock2);
        InstructionKt.pushSimpleInstruction(callStack, irBlock2);
        unfoldStatements(irBlock.getStatements(), callStack);
    }

    private static final boolean unfoldStatements$isLastIndex(int i, List<? extends IrStatement> list) {
        return list.size() - 1 == i;
    }

    private static final void unfoldStatements(List<? extends IrStatement> list, CallStack callStack) {
        int size = list.size() - 1;
        if (size < 0) {
            return;
        }
        while (true) {
            int i = size - 1;
            IrStatement irStatement = list.get(size);
            if (irStatement instanceof IrClass) {
                if (!AdditionalIrUtilsKt.isLocal((IrDeclaration) irStatement)) {
                    throw new NotImplementedError("An operation is not implemented: Only local classes are supported");
                }
            } else if (irStatement instanceof IrFunction) {
                if (!AdditionalIrUtilsKt.isLocal((IrDeclaration) irStatement)) {
                    throw new NotImplementedError("An operation is not implemented: Only local functions are supported");
                }
            } else if (!(irStatement instanceof IrExpression) || unfoldStatements$isLastIndex(size, list)) {
                InstructionKt.pushCompoundInstruction(callStack, irStatement);
            } else {
                handleAndDropResult((IrExpression) irStatement, callStack);
            }
            if (i < 0) {
                return;
            } else {
                size = i;
            }
        }
    }

    private static final void unfoldReturn(IrReturn irReturn, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irReturn);
        InstructionKt.pushCompoundInstruction(callStack, irReturn.getValue());
    }

    private static final void unfoldSetField(IrSetField irSetField, CallStack callStack) throws InterpreterAssertionError {
        if (!UtilsKt.accessesTopLevelOrObjectField(irSetField)) {
            InstructionKt.pushSimpleInstruction(callStack, irSetField);
            InstructionKt.pushCompoundInstruction(callStack, irSetField.getValue());
            return;
        }
        throw new InterpreterAssertionError("Cannot interpret set method on top level properties".toString());
    }

    private static final void unfoldGetValue(IrGetValue irGetValue, IrInterpreterEnvironment irInterpreterEnvironment) {
        if (UtilsKt.isAccessToNotNullableObject(irGetValue)) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irGetValue.getType());
            IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
            Intrinsics.checkNotNull(owner);
            unfoldGetObjectValue(IrTreeBuildUtilsKt.createGetObject(owner), irInterpreterEnvironment);
            return;
        }
        irInterpreterEnvironment.getCallStack().pushState(irInterpreterEnvironment.getCallStack().loadState(irGetValue.getSymbol()));
    }

    private static final void unfoldGetObjectValue(IrGetObjectValue irGetObjectValue, IrInterpreterEnvironment irInterpreterEnvironment) {
        CallStack callStack = irInterpreterEnvironment.getCallStack();
        Complex complex = irInterpreterEnvironment.getMapOfObjects$ir_interpreter().get(irGetObjectValue.getSymbol().getOwner().getSymbol());
        if (complex == null) {
            InstructionKt.pushSimpleInstruction(callStack, irGetObjectValue);
        } else {
            callStack.pushState(complex);
        }
    }

    private static final void unfoldGetEnumValue(IrGetEnumValue irGetEnumValue, IrInterpreterEnvironment irInterpreterEnvironment) {
        CallStack callStack = irInterpreterEnvironment.getCallStack();
        Complex complex = irInterpreterEnvironment.getMapOfEnums$ir_interpreter().get(irGetEnumValue.getSymbol());
        if (complex != null) {
            callStack.pushState(complex);
            return;
        }
        InstructionKt.pushSimpleInstruction(callStack, irGetEnumValue);
        List<IrDeclaration> declarations = IrUtilsKt.getParentAsClass(irGetEnumValue.getSymbol().getOwner().getSymbol().getOwner()).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        Iterator it = CollectionsKt.reversed(arrayList).iterator();
        while (it.hasNext()) {
            InstructionKt.pushSimpleInstruction(callStack, (IrEnumEntry) it.next());
        }
    }

    private static final void unfoldEnumEntry(IrEnumEntry irEnumEntry, IrInterpreterEnvironment irInterpreterEnvironment) {
        irInterpreterEnvironment.getCallStack().popState();
        irInterpreterEnvironment.getCallStack().dropSubFrame();
    }

    private static final void unfoldVariable(IrVariable irVariable, CallStack callStack) {
        if (irVariable.getInitializer() == null) {
            callStack.storeState(irVariable.getSymbol(), (State) null);
            return;
        }
        InstructionKt.pushSimpleInstruction(callStack, irVariable);
        IrExpression initializer = irVariable.getInitializer();
        Intrinsics.checkNotNull(initializer);
        InstructionKt.pushCompoundInstruction(callStack, initializer);
    }

    private static final void unfoldSetValue(IrSetValue irSetValue, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irSetValue);
        InstructionKt.pushCompoundInstruction(callStack, irSetValue.getValue());
    }

    private static final void unfoldTypeOperatorCall(IrTypeOperatorCall irTypeOperatorCall, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irTypeOperatorCall);
        InstructionKt.pushCompoundInstruction(callStack, irTypeOperatorCall.getArgument());
    }

    private static final void unfoldBranch(IrBranch irBranch, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irBranch);
        InstructionKt.pushCompoundInstruction(callStack, irBranch.getCondition());
    }

    private static final void unfoldWhileLoop(IrWhileLoop irWhileLoop, CallStack callStack) {
        IrWhileLoop irWhileLoop2 = irWhileLoop;
        callStack.newSubFrame(irWhileLoop2);
        InstructionKt.pushSimpleInstruction(callStack, irWhileLoop2);
        InstructionKt.pushCompoundInstruction(callStack, irWhileLoop.getCondition());
    }

    private static final void unfoldDoWhileLoop(IrDoWhileLoop irDoWhileLoop, CallStack callStack) {
        IrDoWhileLoop irDoWhileLoop2 = irDoWhileLoop;
        callStack.newSubFrame(irDoWhileLoop2);
        InstructionKt.pushSimpleInstruction(callStack, irDoWhileLoop2);
        InstructionKt.pushCompoundInstruction(callStack, irDoWhileLoop.getCondition());
        InstructionKt.pushCompoundInstruction(callStack, irDoWhileLoop.getBody());
    }

    private static final void unfoldWhen(IrWhen irWhen, CallStack callStack) {
        IrWhen irWhen2 = irWhen;
        callStack.newSubFrame(irWhen2);
        InstructionKt.pushSimpleInstruction(callStack, irWhen2);
        Iterator it = CollectionsKt.reversed(irWhen.getBranches()).iterator();
        while (it.hasNext()) {
            InstructionKt.pushCompoundInstruction(callStack, (IrBranch) it.next());
        }
    }

    private static final void unfoldContinue(IrContinue irContinue, CallStack callStack) {
        callStack.unrollInstructionsForBreakContinue(irContinue);
    }

    private static final void unfoldBreak(IrBreak irBreak, CallStack callStack) {
        callStack.unrollInstructionsForBreakContinue(irBreak);
    }

    private static final void unfoldVararg(IrVararg irVararg, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irVararg);
        Iterator it = CollectionsKt.reversed(irVararg.getElements()).iterator();
        while (it.hasNext()) {
            InstructionKt.pushCompoundInstruction(callStack, (IrVarargElement) it.next());
        }
    }

    private static final void unfoldTry(IrTry irTry, CallStack callStack) {
        IrTry irTry2 = irTry;
        callStack.newSubFrame(irTry2);
        InstructionKt.pushSimpleInstruction(callStack, irTry2);
        InstructionKt.pushCompoundInstruction(callStack, irTry.getTryResult());
    }

    private static final void unfoldCatch(IrCatch irCatch, CallStack callStack) {
        State statePeekState = callStack.peekState();
        ExceptionState exceptionState = statePeekState instanceof ExceptionState ? (ExceptionState) statePeekState : null;
        if (exceptionState == null) {
            return;
        }
        ExceptionState exceptionState2 = exceptionState;
        if (StateKt.isSubtypeOf(exceptionState2, irCatch.getCatchParameter().getType())) {
            callStack.popState();
            IrElement currentFrameOwner$ir_interpreter = callStack.getCurrentFrameOwner$ir_interpreter();
            Intrinsics.checkNotNull(currentFrameOwner$ir_interpreter, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrTry");
            callStack.dropSubFrame();
            callStack.newSubFrame(irCatch);
            callStack.storeState(irCatch.getCatchParameter().getSymbol(), exceptionState2);
            InstructionKt.pushSimpleInstruction(callStack, (IrTry) currentFrameOwner$ir_interpreter);
            InstructionKt.pushCompoundInstruction(callStack, irCatch.getResult());
        }
    }

    private static final void unfoldThrow(IrThrow irThrow, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irThrow);
        InstructionKt.pushCompoundInstruction(callStack, irThrow.getValue());
    }

    private static final void unfoldStringConcatenation(IrStringConcatenation irStringConcatenation, final IrInterpreterEnvironment irInterpreterEnvironment) {
        final CallStack callStack = irInterpreterEnvironment.getCallStack();
        InstructionKt.pushSimpleInstruction(callStack, irStringConcatenation);
        Function0<Unit> function0 = new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.InstructionsUnfolderKt$unfoldStringConcatenation$explicitToStringCheck$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                State statePeekState = callStack.peekState();
                if (statePeekState instanceof Primitive) {
                    callStack.popState();
                    InstructionKt.pushSimpleInstruction(callStack, IrCallImpl.INSTANCE.fromSymbolOwner(-1, -1, StateKt.isNull(statePeekState) ? irInterpreterEnvironment.getIrBuiltIns().getExtensionToString() : irInterpreterEnvironment.getIrBuiltIns().getMemberToString()));
                    callStack.pushState(statePeekState);
                } else if (statePeekState instanceof Common) {
                    callStack.popState();
                    Common common = (Common) statePeekState;
                    if (IrTypeUtilsKt.isUnsigned(IrUtilsKt.getDefaultType(common.getIrClass()))) {
                        callStack.pushState(irInterpreterEnvironment.convertToState$ir_interpreter(UtilsKt.unsignedToString(statePeekState), irInterpreterEnvironment.getIrBuiltIns().getStringType()));
                    } else {
                        InstructionKt.pushSimpleInstruction(callStack, common.createToStringIrCall());
                        callStack.pushState(statePeekState);
                    }
                }
            }
        };
        for (IrExpression irExpression : CollectionsKt.reversed(irStringConcatenation.getArguments())) {
            callStack.pushInstruction(new CustomInstruction(function0));
            InstructionKt.pushCompoundInstruction(callStack, irExpression);
        }
    }

    private static final void unfoldComposite(IrComposite irComposite, CallStack callStack) {
        IrStatementOrigin origin = irComposite.getOrigin();
        boolean z = true;
        if (!(Intrinsics.areEqual(origin, IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE) ? true : Intrinsics.areEqual(origin, IrStatementOrigin.DO_WHILE_LOOP.INSTANCE)) && origin != null) {
            z = false;
        }
        if (!z) {
            throw new NotImplementedError("An operation is not implemented: " + (irComposite.getOrigin() + " not implemented"));
        }
        unfoldStatements(irComposite.getStatements(), callStack);
    }

    private static final void unfoldCallableReference(IrCallableReference<?> irCallableReference, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irCallableReference);
        Iterator<T> it = IrUtilsKt.getArgumentsWithIr(irCallableReference).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            IrExpression irExpression = (IrExpression) pair.component2();
            InstructionKt.pushSimpleInstruction(callStack, irValueParameter);
            InstructionKt.pushCompoundInstruction(callStack, irExpression);
        }
    }

    private static final void unfoldFunctionReference(IrFunctionReference irFunctionReference, CallStack callStack) {
        unfoldCallableReference(irFunctionReference, callStack);
    }

    private static final void unfoldPropertyReference(IrPropertyReference irPropertyReference, CallStack callStack) {
        if (irPropertyReference.getField() != null) {
            InstructionKt.pushSimpleInstruction(callStack, irPropertyReference);
        } else {
            unfoldCallableReference(irPropertyReference, callStack);
        }
    }

    private static final void unfoldClassReference(IrClassReference irClassReference, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irClassReference);
    }

    private static final void unfoldGetClass(IrGetClass irGetClass, CallStack callStack) {
        InstructionKt.pushSimpleInstruction(callStack, irGetClass);
        InstructionKt.pushCompoundInstruction(callStack, irGetClass.getArgument());
    }
}
