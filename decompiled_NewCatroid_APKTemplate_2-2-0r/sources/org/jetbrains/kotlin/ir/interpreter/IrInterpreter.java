package org.jetbrains.kotlin.ir.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
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
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterAssertionError;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterError;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterTimeOutError;
import org.jetbrains.kotlin.ir.interpreter.proxy.CommonProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.Proxy;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.Complex;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KClassState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IrInterpreter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000à\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bB\u000f\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB#\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\u000eJ\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001eH\u0002J\b\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$H\u0002J\u001a\u0010\"\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(J\u0010\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020\u0007H\u0002J\u0010\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u000200H\u0002J\u0010\u00101\u001a\u00020!2\u0006\u00102\u001a\u000203H\u0002J\u0010\u00104\u001a\u00020!2\u0006\u00105\u001a\u000206H\u0002J\u0014\u00107\u001a\u00020!2\n\u0010&\u001a\u0006\u0012\u0002\b\u000308H\u0002J\u0010\u00109\u001a\u00020!2\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u00020!2\u0006\u0010=\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u00020!2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010D\u001a\u00020!2\u0006\u0010=\u001a\u00020EH\u0002J\u0010\u0010F\u001a\u00020!2\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010I\u001a\u00020!2\u0006\u0010J\u001a\u00020KH\u0002J\u0010\u0010L\u001a\u00020!2\u0006\u0010M\u001a\u00020NH\u0002J\u0010\u0010O\u001a\u00020!2\u0006\u0010&\u001a\u00020PH\u0002J\u0010\u0010Q\u001a\u00020!2\u0006\u0010R\u001a\u00020SH\u0002J\u0010\u0010T\u001a\u00020!2\u0006\u0010&\u001a\u00020UH\u0002J\u0010\u0010V\u001a\u00020!2\u0006\u0010&\u001a\u00020WH\u0002J\u0010\u0010X\u001a\u00020!2\u0006\u0010&\u001a\u00020YH\u0002J\u0010\u0010Z\u001a\u00020!2\u0006\u0010&\u001a\u00020[H\u0002J\u0010\u0010\\\u001a\u00020!2\u0006\u0010]\u001a\u00020^H\u0002J\u0010\u0010_\u001a\u00020!2\u0006\u0010&\u001a\u00020`H\u0002J\u0010\u0010a\u001a\u00020!2\u0006\u0010&\u001a\u00020bH\u0002J\u0010\u0010c\u001a\u00020!2\u0006\u0010&\u001a\u00020dH\u0002J\u0010\u0010e\u001a\u00020!2\u0006\u0010&\u001a\u00020fH\u0002J\u0010\u0010g\u001a\u00020!2\u0006\u0010&\u001a\u00020hH\u0002J\u0010\u0010i\u001a\u00020!2\u0006\u0010#\u001a\u00020jH\u0002J\u0010\u0010k\u001a\u00020!2\u0006\u0010&\u001a\u00020lH\u0002J\u0010\u0010m\u001a\u00020!2\u0006\u0010n\u001a\u00020oH\u0002J\u0010\u0010p\u001a\u00020!2\u0006\u0010&\u001a\u00020qH\u0002J\u0010\u0010r\u001a\u00020!2\u0006\u0010s\u001a\u00020tH\u0002J\u0010\u0010u\u001a\u00020!2\u0006\u0010v\u001a\u00020wH\u0002J\u0010\u0010x\u001a\u00020!2\u0006\u0010B\u001a\u00020yH\u0002J0\u0010z\u001a\u00020\u001e2\u0006\u00102\u001a\u0002032\u0019\u0010{\u001a\u0015\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u00010|¢\u0006\u0002\b}H\u0000¢\u0006\u0002\b~J\r\u0010\u007f\u001a\u00020!*\u00030\u0080\u0001H\u0002J\u000e\u0010\u0081\u0001\u001a\u00020!*\u00030\u0082\u0001H\u0002R \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u0083\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "bodyMap", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Ljava/util/Map;)V", "irModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;Ljava/util/Map;)V", "getBodyMap$ir_interpreter", "()Ljava/util/Map;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "getCallStack", "()Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "commandCount", "", "getEnvironment$ir_interpreter", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "extractResultAndAssertThatStackIsEmpty", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "getUnitState", "incrementAndCheckCommands", "", "interpret", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "interpretBlock", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "interpretBody", "body", "interpretBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "interpretCall", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "interpretClassReference", "classReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "interpretConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "interpretConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "interpretConstructorCall", "constructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "interpretDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "interpretDoWhile", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "interpretEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "interpretEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "interpretField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "interpretFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "interpretFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "interpretFunctionReference", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "interpretGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "interpretGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "interpretGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "interpretGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "interpretPropertyReference", "propertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "interpretReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "interpretSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "interpretSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "interpretStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "interpretThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "interpretTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "interpretTypeOperatorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "interpretValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "interpretVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "interpretVariable", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "interpretWhen", "whenExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "interpretWhile", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "withNewCallStack", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "withNewCallStack$ir_interpreter", "handle", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "tryResetFunctionBody", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreter {
    private final Map<IdSignature, IrBody> bodyMap;
    private final CallInterceptor callInterceptor;
    private int commandCount;
    private final IrInterpreterEnvironment environment;

    /* JADX INFO: compiled from: IrInterpreter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[UnsignedType.values().length];
            try {
                iArr[UnsignedType.UBYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UnsignedType.USHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UnsignedType.UINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[UnsignedType.ULONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[IrTypeOperator.values().length];
            try {
                iArr2[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[IrTypeOperator.CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[IrTypeOperator.SAFE_CAST.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[IrTypeOperator.INSTANCEOF.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[IrTypeOperator.SAM_CONVERSION.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrInterpreter(IrInterpreterEnvironment environment, Map<IdSignature, ? extends IrBody> bodyMap) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(bodyMap, "bodyMap");
        this.environment = environment;
        this.bodyMap = bodyMap;
        this.callInterceptor = new DefaultCallInterceptor(this);
    }

    public /* synthetic */ IrInterpreter(IrInterpreterEnvironment irInterpreterEnvironment, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irInterpreterEnvironment, (Map<IdSignature, ? extends IrBody>) ((i & 2) != 0 ? MapsKt.emptyMap() : map));
    }

    public final Map<IdSignature, IrBody> getBodyMap$ir_interpreter() {
        return this.bodyMap;
    }

    /* JADX INFO: renamed from: getEnvironment$ir_interpreter, reason: from getter */
    public final IrInterpreterEnvironment getEnvironment() {
        return this.environment;
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.environment.getIrBuiltIns();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallStack getCallStack() {
        return this.environment.getCallStack();
    }

    public /* synthetic */ IrInterpreter(IrBuiltIns irBuiltIns, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irBuiltIns, (Map<IdSignature, ? extends IrBody>) ((i & 2) != 0 ? MapsKt.emptyMap() : map));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IrInterpreter(IrBuiltIns irBuiltIns, Map<IdSignature, ? extends IrBody> bodyMap) {
        this(new IrInterpreterEnvironment(irBuiltIns, null, 2, 0 == true ? 1 : 0), bodyMap);
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(bodyMap, "bodyMap");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrInterpreter(IrModuleFragment irModule) {
        this(new IrInterpreterEnvironment(irModule), (Map<IdSignature, ? extends IrBody>) MapsKt.emptyMap());
        Intrinsics.checkNotNullParameter(irModule, "irModule");
    }

    private final void incrementAndCheckCommands() {
        int i = this.commandCount + 1;
        this.commandCount = i;
        if (i >= this.environment.getConfiguration().getMaxCommands()) {
            ExceptionUtilsKt.handleUserException(new InterpreterTimeOutError(), this.environment);
        }
    }

    private final State getUnitState() {
        Complex complex = this.environment.getMapOfObjects$ir_interpreter().get(getIrBuiltIns().getUnitClass());
        Intrinsics.checkNotNull(complex);
        return complex;
    }

    private final void handle(Instruction instruction) throws InterpreterError {
        if (instruction instanceof CompoundInstruction) {
            InstructionsUnfolderKt.unfoldInstruction(instruction.getElement(), this.environment);
            return;
        }
        if (instruction instanceof SimpleInstruction) {
            interpret(((SimpleInstruction) instruction).getElement());
            Unit unit = Unit.INSTANCE;
            incrementAndCheckCommands();
        } else if (instruction instanceof CustomInstruction) {
            ((CustomInstruction) instruction).getEvaluate().invoke();
        }
    }

    public static /* synthetic */ IrExpression interpret$default(IrInterpreter irInterpreter, IrExpression irExpression, IrFile irFile, int i, Object obj) {
        if ((i & 2) != 0) {
            irFile = null;
        }
        return irInterpreter.interpret(irExpression, irFile);
    }

    public final IrExpression interpret(IrExpression expression, IrFile file) throws InterpreterError {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.commandCount = 0;
        IrExpression irExpression = expression;
        getCallStack().newFrame(irExpression, file);
        InstructionKt.pushCompoundInstruction(getCallStack(), irExpression);
        while (!getCallStack().hasNoInstructions()) {
            handle(getCallStack().popInstruction());
        }
        return this.environment.stateToIrExpression$ir_interpreter(extractResultAndAssertThatStackIsEmpty(), expression);
    }

    public final State withNewCallStack$ir_interpreter(IrCall call, Function1<? super IrInterpreter, ? extends Object> init) throws InterpreterError {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(init, "init");
        IrInterpreter irInterpreter = new IrInterpreter(this.environment.copyWithNewCallStack(), this.bodyMap);
        irInterpreter.getCallStack().newFrame(call.getSymbol().getOwner());
        init.invoke(irInterpreter);
        while (!irInterpreter.getCallStack().hasNoInstructions()) {
            irInterpreter.handle(irInterpreter.getCallStack().popInstruction());
        }
        return irInterpreter.extractResultAndAssertThatStackIsEmpty();
    }

    private final State extractResultAndAssertThatStackIsEmpty() {
        State statePopState = getCallStack().popState();
        getCallStack().peekState();
        getCallStack().dropFrame();
        return statePopState;
    }

    private final void interpret(IrElement element) throws InterpreterAssertionError {
        if (element instanceof IrSimpleFunction) {
            interpretFunction((IrSimpleFunction) element);
            return;
        }
        if (element instanceof IrConstructor) {
            interpretConstructor((IrConstructor) element);
            return;
        }
        if (element instanceof IrCall) {
            interpretCall((IrCall) element);
            return;
        }
        if (element instanceof IrConstructorCall) {
            interpretConstructorCall((IrFunctionAccessExpression) element);
            return;
        }
        if (element instanceof IrEnumConstructorCall) {
            interpretEnumConstructorCall((IrEnumConstructorCall) element);
            return;
        }
        if (element instanceof IrDelegatingConstructorCall) {
            interpretDelegatingConstructorCall((IrDelegatingConstructorCall) element);
            return;
        }
        if (element instanceof IrInstanceInitializerCall) {
            getCallStack().pushState(getUnitState());
            return;
        }
        if (element instanceof IrValueParameter) {
            interpretValueParameter((IrValueParameter) element);
            return;
        }
        if (element instanceof IrField) {
            interpretField((IrField) element);
            return;
        }
        if (element instanceof IrBody) {
            interpretBody((IrBody) element);
            return;
        }
        if (element instanceof IrBlock) {
            interpretBlock((IrBlock) element);
            return;
        }
        if (element instanceof IrReturn) {
            interpretReturn((IrReturn) element);
            return;
        }
        if (element instanceof IrSetField) {
            interpretSetField((IrSetField) element);
            return;
        }
        if (element instanceof IrGetField) {
            interpretGetField((IrGetField) element);
            return;
        }
        if (element instanceof IrGetObjectValue) {
            interpretGetObjectValue((IrGetObjectValue) element);
            return;
        }
        if (element instanceof IrGetEnumValue) {
            interpretGetEnumValue((IrGetEnumValue) element);
            return;
        }
        if (element instanceof IrEnumEntry) {
            interpretEnumEntry((IrEnumEntry) element);
            return;
        }
        if (element instanceof IrConst) {
            interpretConst((IrConst) element);
            return;
        }
        if (element instanceof IrVariable) {
            interpretVariable((IrVariable) element);
            return;
        }
        if (element instanceof IrSetValue) {
            interpretSetValue((IrSetValue) element);
            return;
        }
        if (element instanceof IrTypeOperatorCall) {
            interpretTypeOperatorCall((IrTypeOperatorCall) element);
            return;
        }
        if (element instanceof IrBranch) {
            interpretBranch((IrBranch) element);
            return;
        }
        if (element instanceof IrWhileLoop) {
            interpretWhile((IrWhileLoop) element);
            return;
        }
        if (element instanceof IrDoWhileLoop) {
            interpretDoWhile((IrDoWhileLoop) element);
            return;
        }
        if (element instanceof IrWhen) {
            interpretWhen((IrWhen) element);
            return;
        }
        if (element instanceof IrVararg) {
            interpretVararg((IrVararg) element);
            return;
        }
        if (element instanceof IrTry) {
            interpretTry((IrTry) element);
            return;
        }
        if (element instanceof IrThrow) {
            interpretThrow((IrThrow) element);
            return;
        }
        if (element instanceof IrStringConcatenation) {
            interpretStringConcatenation((IrStringConcatenation) element);
            return;
        }
        if (element instanceof IrFunctionExpression) {
            interpretFunctionExpression((IrFunctionExpression) element);
            return;
        }
        if (element instanceof IrFunctionReference) {
            interpretFunctionReference((IrFunctionReference) element);
            return;
        }
        if (element instanceof IrPropertyReference) {
            interpretPropertyReference((IrPropertyReference) element);
            return;
        }
        if (element instanceof IrClassReference) {
            interpretClassReference((IrClassReference) element);
        } else if (element instanceof IrGetClass) {
            interpretGetClass((IrGetClass) element);
        } else {
            throw new NotImplementedError("An operation is not implemented: " + (element.getClass() + " not supported for interpretation"));
        }
    }

    private final void tryResetFunctionBody(IrFunction irFunction) {
        IdSignature signature = irFunction.getSymbol().getSignature();
        if (signature == null || this.bodyMap.get(signature) == null) {
            return;
        }
        irFunction.setBody(null);
    }

    private final void interpretFunction(IrSimpleFunction function) {
        IrSimpleFunction irSimpleFunction = function;
        tryResetFunctionBody(irSimpleFunction);
        if (UtilsKt.checkCast(irSimpleFunction, this.environment)) {
            getCallStack().dropFrameAndCopyResult();
        }
    }

    private final void interpretValueParameter(final IrValueParameter valueParameter) {
        IrDeclarationParent parent = valueParameter.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        final IrFunction irFunction = (IrFunction) parent;
        State statePopState = getCallStack().popState();
        if (StateKt.checkNullability(statePopState, valueParameter.getType(), this.environment, new Function0<Throwable>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretValueParameter.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Throwable invoke() {
                if (IrInterpreter.interpretValueParameter$isReceiver(irFunction, valueParameter)) {
                    return new NullPointerException();
                }
                return new IllegalArgumentException("Parameter specified as non-null is null: method " + (UtilsKt.getCapitalizedFileName(irFunction) + '.' + UtilsKt.getFqName(irFunction)) + ", parameter " + valueParameter.getName());
            }
        }) == null) {
            return;
        }
        getCallStack().pushState(statePopState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean interpretValueParameter$isReceiver(IrFunction irFunction, IrValueParameter irValueParameter) {
        return Intrinsics.areEqual(irFunction.getDispatchReceiverParameter(), irValueParameter) || Intrinsics.areEqual(irFunction.getExtensionReceiverParameter(), irValueParameter);
    }

    private static final State interpretCall$getThisOrSuperReceiver(State state, IrFunction irFunction) {
        if (!(state instanceof Common)) {
            return state;
        }
        Common common = (Common) state;
        if (common.getSuperWrapperClass() == null || !(irFunction.getParent() instanceof IrClass)) {
            return state;
        }
        Wrapper superWrapperClass = common.getSuperWrapperClass();
        Intrinsics.checkNotNull(superWrapperClass);
        return IrUtilsKt.isSubclassOf(superWrapperClass.getIrClass(), IrUtilsKt.getParentAsClass(irFunction)) ? common.getSuperWrapperClass() : state;
    }

    private final void interpretCall(IrCall call) {
        final IrFunction owner;
        IrClass parentClassOrNull;
        IrSimpleFunction owner2 = call.getSymbol().getOwner();
        List<IrValueParameter> valueParameters = owner2.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(getCallStack().popState());
        }
        List listReversed = CollectionsKt.reversed(arrayList);
        IrSimpleFunction irSimpleFunction = owner2;
        State statePopState = UtilsKt.getExtensionReceiver(irSimpleFunction) != null ? getCallStack().popState() : null;
        State statePopState2 = UtilsKt.getDispatchReceiver(irSimpleFunction) != null ? getCallStack().popState() : null;
        if (statePopState2 == null || (owner = statePopState2.getIrFunctionByIrCall(call)) == null) {
            owner = call.getSymbol().getOwner();
        }
        int i = 0;
        List<? extends State> listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new State[]{interpretCall$getThisOrSuperReceiver(statePopState2, owner), statePopState}), (Iterable) listReversed);
        Map<IrTypeParameterSymbol, KTypeState> mapLoadReifiedTypeArguments = UtilsKt.loadReifiedTypeArguments(this.environment, call);
        getCallStack().newFrame(owner);
        InstructionKt.pushSimpleInstruction(getCallStack(), owner);
        if (statePopState2 instanceof StateWithClosure) {
            getCallStack().loadUpValues((StateWithClosure) statePopState2);
        }
        if (statePopState instanceof StateWithClosure) {
            getCallStack().loadUpValues((StateWithClosure) statePopState);
        }
        IrFunction irFunction = owner;
        if (AdditionalIrUtilsKt.isLocal(irFunction)) {
            getCallStack().copyUpValuesFromPreviousFrame();
        }
        IrValueParameterSymbol dispatchReceiver = UtilsKt.getDispatchReceiver(owner);
        if (dispatchReceiver != null) {
            getCallStack().storeState(dispatchReceiver, statePopState2);
        }
        IrValueParameterSymbol extensionReceiver = UtilsKt.getExtensionReceiver(owner);
        if (extensionReceiver != null) {
            getCallStack().storeState(extensionReceiver, statePopState);
        }
        for (Object obj : owner.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            getCallStack().storeState(((IrValueParameter) obj).getSymbol(), (State) listReversed.get(i));
            i = i2;
        }
        getCallStack().storeState(owner.getSymbol(), new KTypeState(call.getType(), this.environment.getKTypeClass$ir_interpreter().getOwner()));
        for (Map.Entry<IrTypeParameterSymbol, KTypeState> entry : mapLoadReifiedTypeArguments.entrySet()) {
            getCallStack().storeState(entry.getKey(), entry.getValue());
        }
        if ((statePopState2 instanceof Complex) && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction)) != null && parentClassOrNull.getIsInner()) {
            Complex.loadOuterClassesInto$default((Complex) statePopState2, getCallStack(), null, 2, null);
        }
        this.callInterceptor.interceptCall(call, owner, listPlus, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretCall.5
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
                InstructionKt.pushCompoundInstruction(IrInterpreter.this.getCallStack(), owner);
            }
        });
    }

    private final void interpretField(IrField field) {
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(field).getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        State stateLoadState = getCallStack().loadState(thisReceiver.getSymbol());
        IrPropertySymbol correspondingPropertySymbol = field.getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        stateLoadState.setField(correspondingPropertySymbol, getCallStack().popState());
    }

    private final void interpretBody(IrBody body) {
        if (IrUtilsKt.getStatements(body).isEmpty()) {
            getCallStack().pushState(getUnitState());
        }
    }

    private final void interpretBlock(IrBlock block) {
        getCallStack().dropSubFrame();
        if (block.getStatements().isEmpty()) {
            getCallStack().pushState(getUnitState());
        }
    }

    private final void interpretConstructor(IrConstructor constructor) {
        CallStack callStack = getCallStack();
        CallStack callStack2 = getCallStack();
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(constructor).getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        callStack.pushState(callStack2.loadState(thisReceiver.getSymbol()));
        getCallStack().dropFrameAndCopyResult();
    }

    private final void interpretConstructorCall(IrFunctionAccessExpression constructorCall) {
        Complex complexLoadState;
        IrValueSymbol thisReceiver;
        IrValueDeclaration owner;
        List<IrStatement> statements;
        final IrFunction owner2 = constructorCall.getSymbol().getOwner();
        List<IrValueParameter> valueParameters = owner2.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(getCallStack().popState());
        }
        List<? extends State> listReversed = CollectionsKt.reversed(arrayList);
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner2);
        IrValueParameter dispatchReceiverParameter = owner2.getDispatchReceiverParameter();
        IrType type = null;
        IrValueParameterSymbol symbol = dispatchReceiverParameter != null ? dispatchReceiverParameter.getSymbol() : null;
        if (constructorCall instanceof IrConstructorCall) {
            Complex exceptionState = UtilsKt.isSubclassOfThrowable(parentAsClass) ? new ExceptionState(parentAsClass, this.environment) : new Common(parentAsClass);
            if (IrUtilsKt.isObject(parentAsClass)) {
                this.environment.getMapOfObjects$ir_interpreter().put(parentAsClass.getSymbol(), exceptionState);
            }
            complexLoadState = exceptionState;
        } else {
            complexLoadState = getCallStack().loadState(UtilsKt.getThisReceiver(constructorCall));
        }
        IrClass irClass = parentAsClass;
        if (AdditionalIrUtilsKt.isLocal(irClass)) {
            CallStack callStack = getCallStack();
            Intrinsics.checkNotNull(complexLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure");
            callStack.storeUpValues((StateWithClosure) complexLoadState);
        }
        State statePopState = symbol != null ? getCallStack().popState() : null;
        IrType typeIfReified = UtilsKt.getTypeIfReified(constructorCall.getType(), getCallStack());
        getCallStack().newFrame(owner2);
        InstructionKt.pushSimpleInstruction(getCallStack(), owner2);
        if (AdditionalIrUtilsKt.isLocal(irClass)) {
            CallStack callStack2 = getCallStack();
            Intrinsics.checkNotNull(complexLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure");
            callStack2.loadUpValues((StateWithClosure) complexLoadState);
        }
        getCallStack().storeState(UtilsKt.getThisReceiver(constructorCall), complexLoadState);
        int i = 0;
        for (Object obj : owner2.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            getCallStack().storeState(((IrValueParameter) obj).getSymbol(), listReversed.get(i));
            i = i2;
        }
        getCallStack().storeState(owner2.getSymbol(), new KTypeState(typeIfReified, this.environment.getKTypeClass$ir_interpreter().getOwner()));
        IrBody body = owner2.getBody();
        IrStatement irStatement = (body == null || (statements = IrUtilsKt.getStatements(body)) == null) ? null : (IrStatement) CollectionsKt.getOrNull(statements, 0);
        if (irStatement == null) {
            thisReceiver = null;
        } else if (irStatement instanceof IrTypeOperatorCall) {
            IrExpression argument = ((IrTypeOperatorCall) irStatement).getArgument();
            Intrinsics.checkNotNull(argument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression");
            thisReceiver = UtilsKt.getThisReceiver((IrFunctionAccessExpression) argument);
        } else if (irStatement instanceof IrFunctionAccessExpression) {
            thisReceiver = UtilsKt.getThisReceiver((IrFunctionAccessExpression) irStatement);
        } else {
            if (!(irStatement instanceof IrBlock)) {
                throw new NotImplementedError("An operation is not implemented: " + (irStatement.getClass() + " is not supported as first statement in constructor call"));
            }
            Object objLast = CollectionsKt.last((List<? extends Object>) ((IrBlock) irStatement).getStatements());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression");
            thisReceiver = UtilsKt.getThisReceiver((IrFunctionAccessExpression) objLast);
        }
        if (thisReceiver != null) {
            getCallStack().storeState(thisReceiver, complexLoadState);
        }
        if (statePopState != null) {
            IrValueParameter thisReceiver2 = IrUtilsKt.getParentAsClass(irClass).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver2);
            Pair<? extends IrSymbol, ? extends State> pair = TuplesKt.to(thisReceiver2.getSymbol(), statePopState);
            Intrinsics.checkNotNull(complexLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Complex");
            Complex complex = (Complex) complexLoadState;
            complex.setOuterClass(pair);
            if (thisReceiver != null && (owner = thisReceiver.getOwner()) != null) {
                type = owner.getType();
            }
            if (!Intrinsics.areEqual(type, symbol.getOwner().getType())) {
                getCallStack().storeState(symbol, statePopState);
                complex.loadOuterClassesInto(getCallStack(), UtilsKt.getThisReceiver(constructorCall));
            }
        }
        this.callInterceptor.interceptConstructor(constructorCall, listReversed, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretConstructorCall.3
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
                InstructionKt.pushCompoundInstruction(IrInterpreter.this.getCallStack(), owner2);
            }
        });
        getCallStack().pushState(getUnitState());
    }

    private final void interpretDelegatingConstructorCall(IrDelegatingConstructorCall constructorCall) {
        if (Intrinsics.areEqual(constructorCall.getSymbol().getOwner().getParent(), getIrBuiltIns().getAnyClass().getOwner())) {
            return;
        }
        interpretConstructorCall(constructorCall);
    }

    private final void interpretEnumConstructorCall(IrEnumConstructorCall constructorCall) {
        interpretConstructorCall(constructorCall);
    }

    private static final IrType interpretConst$getSignedType(IrInterpreter irInterpreter, IrType irType) {
        UnsignedType unsignedType = IrTypePredicatesKt.getUnsignedType(irType);
        int i = unsignedType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[unsignedType.ordinal()];
        if (i == 1) {
            return irInterpreter.getIrBuiltIns().getByteType();
        }
        if (i == 2) {
            return irInterpreter.getIrBuiltIns().getShortType();
        }
        if (i == 3) {
            return irInterpreter.getIrBuiltIns().getIntType();
        }
        if (i != 4) {
            return null;
        }
        return irInterpreter.getIrBuiltIns().getLongType();
    }

    private final void interpretConst(IrConst<?> expression) {
        IrType irTypeInterpretConst$getSignedType = interpretConst$getSignedType(this, expression.getType());
        if (irTypeInterpretConst$getSignedType != null) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
            Intrinsics.checkNotNull(classOrNull);
            IrConstructor owner = ((IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(classOrNull))).getOwner();
            IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, owner.getReturnType(), owner.getSymbol(), null, 4, null);
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, IrUtilsKt.toIrConst$default(expression.getValue(), irTypeInterpretConst$getSignedType, 0, 0, 6, null));
            InstructionKt.pushCompoundInstruction(getCallStack(), irConstructorCallImplFromSymbolOwner$default);
            return;
        }
        getCallStack().pushState(UtilsKt.toPrimitive(expression));
    }

    private final void interpretVariable(IrVariable variable) {
        getCallStack().storeState(variable.getSymbol(), getCallStack().popState());
        getCallStack().pushState(getUnitState());
    }

    private final void interpretSetValue(IrSetValue expression) {
        getCallStack().rewriteState(expression.getSymbol(), getCallStack().popState());
        getCallStack().pushState(getUnitState());
    }

    private final void interpretReturn(IrReturn expression) {
        getCallStack().returnFromFrameWithResult(expression);
    }

    private final void interpretWhile(IrWhileLoop loop) {
        boolean zAsBoolean = StateKt.asBoolean(getCallStack().popState());
        getCallStack().dropSubFrame();
        if (zAsBoolean) {
            IrWhileLoop irWhileLoop = loop;
            getCallStack().newSubFrame(irWhileLoop);
            InstructionKt.pushSimpleInstruction(getCallStack(), irWhileLoop);
            InstructionKt.pushCompoundInstruction(getCallStack(), loop.getCondition());
            InstructionKt.pushCompoundInstruction(getCallStack(), loop.getBody());
        }
    }

    private final void interpretDoWhile(IrDoWhileLoop loop) {
        boolean zAsBoolean = StateKt.asBoolean(getCallStack().popState());
        getCallStack().dropSubFrame();
        if (zAsBoolean) {
            IrDoWhileLoop irDoWhileLoop = loop;
            getCallStack().newSubFrame(irDoWhileLoop);
            InstructionKt.pushSimpleInstruction(getCallStack(), irDoWhileLoop);
            InstructionKt.pushCompoundInstruction(getCallStack(), loop.getCondition());
            InstructionKt.pushCompoundInstruction(getCallStack(), loop.getBody());
        }
    }

    private final void interpretWhen(IrWhen whenExpression) {
        getCallStack().dropSubFrame();
        getCallStack().pushState(getUnitState());
    }

    private final void interpretBranch(IrBranch branch) {
        if (StateKt.asBoolean(getCallStack().popState())) {
            getCallStack().dropSubFrame();
            InstructionKt.pushCompoundInstruction(getCallStack(), branch.getResult());
        }
    }

    private final void interpretSetField(IrSetField expression) {
        IrExpression receiver = expression.getReceiver();
        Intrinsics.checkNotNull(receiver, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrDeclarationReference");
        IrSymbol symbol = ((IrDeclarationReference) receiver).getSymbol();
        IrPropertySymbol correspondingPropertySymbol = expression.getSymbol().getOwner().getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        getCallStack().loadState(symbol).setField(correspondingPropertySymbol, getCallStack().popState());
        getCallStack().pushState(getUnitState());
    }

    private final void interpretGetField(IrGetField expression) throws InterpreterAssertionError {
        IrClass parentClassOrNull;
        IrField backingField;
        IrExpressionBody initializer;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        IrExpression receiver = expression.getReceiver();
        IrDeclarationReference irDeclarationReference = receiver instanceof IrDeclarationReference ? (IrDeclarationReference) receiver : null;
        IrSymbol symbol = irDeclarationReference != null ? irDeclarationReference.getSymbol() : null;
        IrField owner2 = expression.getSymbol().getOwner();
        if (Intrinsics.areEqual(owner2.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && owner2.getIsStatic()) {
            IrExpressionBody initializer2 = owner2.getInitializer();
            IrExpression expression2 = initializer2 != null ? initializer2.getExpression() : null;
            if (expression2 instanceof IrConst) {
                InstructionKt.pushSimpleInstruction(getCallStack(), expression2);
                return;
            } else {
                this.callInterceptor.interceptJavaStaticField(expression);
                return;
            }
        }
        boolean z = true;
        if (Intrinsics.areEqual(owner2.getOrigin(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE) && (correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) {
            CallStack callStack = getCallStack();
            IrExpressionBody initializer3 = owner2.getInitializer();
            InstructionKt.pushCompoundInstruction(callStack, initializer3 != null ? initializer3.getExpression() : null);
            return;
        }
        if (UtilsKt.accessesTopLevelOrObjectField(expression)) {
            IrPropertySymbol correspondingPropertySymbol2 = owner2.getCorrespondingPropertySymbol();
            IrProperty owner3 = correspondingPropertySymbol2 != null ? correspondingPropertySymbol2.getOwner() : null;
            if (owner3 == null || !owner3.getIsConst()) {
                if (!(((owner3 == null || (backingField = owner3.getBackingField()) == null || (initializer = backingField.getInitializer()) == null) ? null : initializer.getExpression()) instanceof IrConst) && (owner3 == null || (parentClassOrNull = IrUtilsKt.getParentClassOrNull(owner3)) == null || !UtilsKt.hasAnnotation(parentClassOrNull, UtilsKt.getCompileTimeAnnotation()))) {
                    z = false;
                }
            }
            if (z) {
                CallStack callStack2 = getCallStack();
                IrExpressionBody initializer4 = owner2.getInitializer();
                InstructionKt.pushCompoundInstruction(callStack2, initializer4 != null ? initializer4.getExpression() : null);
                return;
            }
            throw new InterpreterAssertionError("Cannot interpret get method on top level non const properties".toString());
        }
        CallStack callStack3 = getCallStack();
        Intrinsics.checkNotNull(symbol);
        State stateLoadState = callStack3.loadState(symbol);
        IrPropertySymbol correspondingPropertySymbol3 = owner2.getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol3);
        State field = stateLoadState.getField(correspondingPropertySymbol3);
        CallStack callStack4 = getCallStack();
        Intrinsics.checkNotNull(field);
        callStack4.pushState(field);
    }

    private final void interpretGetObjectValue(final IrGetObjectValue expression) {
        this.callInterceptor.interceptGetObjectValue(expression, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretGetObjectValue.1
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
                IrClass owner = expression.getSymbol().getOwner();
                boolean z = UtilsKt.hasAnnotation(owner, UtilsKt.getCompileTimeAnnotation()) || this.getEnvironment().getConfiguration().getCreateNonCompileTimeObjects();
                if (SequencesKt.none(IrUtilsKt.getConstructors(owner)) || !z) {
                    Common common = new Common(owner);
                    this.getEnvironment().getMapOfObjects$ir_interpreter().put(owner.getSymbol(), common);
                    this.getCallStack().pushState(common);
                } else {
                    IrConstructor irConstructor = (IrConstructor) SequencesKt.first(IrUtilsKt.getConstructors(owner));
                    InstructionKt.pushCompoundInstruction(this.getCallStack(), IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, irConstructor.getReturnType(), irConstructor.getSymbol(), null, 4, null));
                }
            }
        });
    }

    private final void interpretGetEnumValue(IrGetEnumValue expression) {
        CallStack callStack = getCallStack();
        Complex complex = this.environment.getMapOfEnums$ir_interpreter().get(expression.getSymbol());
        Intrinsics.checkNotNull(complex);
        callStack.pushState(complex);
    }

    private final void interpretEnumEntry(final IrEnumEntry enumEntry) {
        this.callInterceptor.interceptEnumEntry(enumEntry, new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretEnumEntry.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws InterpreterError {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws InterpreterError {
                IrExpression expression;
                Common state = UtilsKt.toState(enumEntry, this.getIrBuiltIns());
                this.getEnvironment().getMapOfEnums$ir_interpreter().put(enumEntry.getSymbol(), state);
                IrExpressionBody initializerExpression = enumEntry.getInitializerExpression();
                if (initializerExpression == null || (expression = initializerExpression.getExpression()) == null) {
                    throw new InterpreterError("Initializer at enum entry " + UtilsKt.getFqName(enumEntry) + " is null");
                }
                IrEnumConstructorCall irEnumConstructorCall = expression instanceof IrEnumConstructorCall ? (IrEnumConstructorCall) expression : null;
                if (irEnumConstructorCall == null) {
                    List<IrStatement> statements = ((IrBlock) expression).getStatements();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : statements) {
                        if (obj instanceof IrEnumConstructorCall) {
                            arrayList.add(obj);
                        }
                    }
                    irEnumConstructorCall = (IrEnumConstructorCall) CollectionsKt.single((List) arrayList);
                }
                this.getCallStack().newSubFrame(enumEntry);
                InstructionKt.pushCompoundInstruction(this.getCallStack(), enumEntry);
                InstructionKt.pushCompoundInstruction(this.getCallStack(), expression);
                this.getCallStack().storeState(UtilsKt.getThisReceiver(irEnumConstructorCall), state);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void interpretTypeOperatorCall(final IrTypeOperatorCall expression) {
        IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(expression.getTypeOperand());
        IrSymbolOwner owner = classifierOrFail.getOwner();
        DescriptorsRemapper descriptorsRemapper = null;
        Object[] objArr = 0;
        IrTypeParameter irTypeParameter = owner instanceof IrTypeParameter ? (IrTypeParameter) owner : null;
        int i = 1;
        Object[] objArr2 = (classifierOrFail.getOwner() instanceof IrTypeParameter) && !(irTypeParameter != null && irTypeParameter.getIsReified()) == true;
        IrType typeIfReified = UtilsKt.getTypeIfReified(expression.getTypeOperand(), getCallStack());
        KFunctionState kFunctionStatePopState = getCallStack().popState();
        switch (WhenMappings.$EnumSwitchMapping$1[expression.getOperator().ordinal()]) {
            case 1:
                getCallStack().pushState(getUnitState());
                return;
            case 2:
            case 3:
                if (StateKt.isNull(kFunctionStatePopState) && !IrTypeUtilsKt.isNullable(typeIfReified)) {
                    ExceptionUtilsKt.handleUserException(new NullPointerException(), this.environment);
                    return;
                } else if (objArr2 == false && !StateKt.isSubtypeOf(kFunctionStatePopState, typeIfReified)) {
                    ExceptionUtilsKt.handleUserException(new ClassCastException(UtilsKt.getFqName(kFunctionStatePopState.getIrClass()) + " cannot be cast to " + RenderIrElementKt.render$default(typeIfReified, (DumpIrTreeOptions) null, 1, (Object) null)), this.environment);
                    return;
                } else {
                    getCallStack().pushState(kFunctionStatePopState);
                    return;
                }
            case 4:
                if (objArr2 == false && !StateKt.isSubtypeOf(kFunctionStatePopState, typeIfReified)) {
                    getCallStack().pushState(this.environment.convertToState$ir_interpreter(null, getIrBuiltIns().getNothingNType()));
                    return;
                } else {
                    getCallStack().pushState(kFunctionStatePopState);
                    return;
                }
            case 5:
                getCallStack().pushState(this.environment.convertToState$ir_interpreter(Boolean.valueOf(objArr2 == true || StateKt.isSubtypeOf(kFunctionStatePopState, typeIfReified)), getIrBuiltIns().getBooleanType()));
                return;
            case 6:
                getCallStack().pushState(this.environment.convertToState$ir_interpreter(Boolean.valueOf(!(objArr2 == true || StateKt.isSubtypeOf(kFunctionStatePopState, typeIfReified))), getIrBuiltIns().getBooleanType()));
                return;
            case 7:
                if (!StateKt.isNull(kFunctionStatePopState) || IrTypeUtilsKt.isNullable(typeIfReified)) {
                    getCallStack().pushState(kFunctionStatePopState);
                    return;
                } else {
                    ExceptionUtilsKt.handleUserException(new NullPointerException(), this.environment);
                    return;
                }
            case 8:
                if (!StateKt.isNull(kFunctionStatePopState)) {
                    if (!(kFunctionStatePopState instanceof KFunctionState)) {
                        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(typeIfReified);
                        Intrinsics.checkNotNull(classOrNull);
                        IrClass owner2 = classOrNull.getOwner();
                        IrFunction singleAbstractMethod = UtilsKt.getSingleAbstractMethod(owner2);
                        List<IrDeclaration> declarations = kFunctionStatePopState.getIrClass().getDeclarations();
                        ArrayList<IrFunction> arrayList = new ArrayList();
                        for (Object obj : declarations) {
                            if (obj instanceof IrFunction) {
                                arrayList.add(obj);
                            }
                        }
                        for (IrFunction irFunction : arrayList) {
                            if (Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.INVOKE) && irFunction.getValueParameters().size() == singleAbstractMethod.getValueParameters().size()) {
                                IrClass parentAsClass = IrUtilsKt.getParentAsClass(UtilsKt.getLastOverridden(irFunction));
                                IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
                                Intrinsics.checkNotNull(dispatchReceiverParameter);
                                Pair pair = TuplesKt.to(dispatchReceiverParameter.getSymbol(), kFunctionStatePopState);
                                IrFunction irFunction2 = irFunction;
                                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, i, objArr == true ? 1 : 0);
                                IrVisitorsKt.acceptVoid(irFunction2, deepCopySymbolRemapper);
                                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irFunction2.transform(new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), null), owner2);
                                if (irElementPatchDeclarationParents != null) {
                                    IrFunction irFunction3 = (IrFunction) irElementPatchDeclarationParents;
                                    irFunction3.setDispatchReceiverParameter(null);
                                    KFunctionState kFunctionState = new KFunctionState(irFunction3, parentAsClass, this.environment, (Map<IrSymbol, State>) MapsKt.mutableMapOf(pair));
                                    kFunctionState.setFunInterface(typeIfReified);
                                    kFunctionStatePopState = kFunctionState;
                                } else {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
                                }
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                    ((KFunctionState) kFunctionStatePopState).setFunInterface(typeIfReified);
                }
                getCallStack().pushState(kFunctionStatePopState);
                return;
            default:
                ExceptionUtilsKt.stop(new Function0<Object>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter.interpretTypeOperatorCall.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return "Type operator " + expression.getOperator() + " is not supported for interpretation";
                    }
                });
                throw null;
        }
    }

    private static final List<Object> interpretVararg$arrayToList(Object obj) {
        Iterable list;
        if (obj instanceof byte[]) {
            list = ArraysKt.toList((byte[]) obj);
        } else if (obj instanceof char[]) {
            list = ArraysKt.toList((char[]) obj);
        } else if (obj instanceof short[]) {
            list = ArraysKt.toList((short[]) obj);
        } else if (obj instanceof int[]) {
            list = ArraysKt.toList((int[]) obj);
        } else if (obj instanceof long[]) {
            list = ArraysKt.toList((long[]) obj);
        } else if (obj instanceof float[]) {
            list = ArraysKt.toList((float[]) obj);
        } else if (obj instanceof double[]) {
            list = ArraysKt.toList((double[]) obj);
        } else if (obj instanceof boolean[]) {
            list = ArraysKt.toList((boolean[]) obj);
        } else {
            list = obj instanceof Object[] ? ArraysKt.toList((Object[]) obj) : CollectionsKt.listOf(obj);
        }
        return CollectionsKt.reversed(list);
    }

    private final void interpretVararg(IrVararg expression) {
        Primitive<?> primitiveStateArray;
        List<Object> listListOf;
        List<IrVarargElement> elements = expression.getElements();
        ArrayList arrayList = new ArrayList();
        for (IrVarargElement irVarargElement : elements) {
            State statePopState = getCallStack().popState();
            if (statePopState instanceof Wrapper) {
                listListOf = CollectionsKt.listOf(((Wrapper) statePopState).getValue());
            } else if (statePopState instanceof Primitive) {
                if (IrTypePredicatesKt.isArray(expression.getVarargElementType()) || UtilsKt.isPrimitiveArray(expression.getVarargElementType())) {
                    listListOf = CollectionsKt.listOf(statePopState);
                } else {
                    listListOf = interpretVararg$arrayToList(((Primitive) statePopState).getValue());
                }
            } else if (statePopState instanceof Common) {
                if (UtilsKt.isUnsignedArray(IrUtilsKt.getDefaultType(statePopState.getIrClass()))) {
                    Object objSingle = CollectionsKt.single(statePopState.getFields().values());
                    Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
                    listListOf = interpretVararg$arrayToList(((Primitive) objSingle).getValue());
                } else {
                    listListOf = CollectionsKt.listOf(CommonProxy.Companion.asProxy$ir_interpreter$default(CommonProxy.INSTANCE, (Common) statePopState, this.callInterceptor, null, 2, null));
                }
            } else {
                listListOf = CollectionsKt.listOf(statePopState);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        List listReversed = CollectionsKt.reversed(arrayList);
        if (UtilsKt.isUnsignedArray(expression.getType())) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
            Intrinsics.checkNotNull(classOrNull);
            IrClass owner = classOrNull.getOwner();
            List<IrDeclaration> declarations = owner.getDeclarations();
            ArrayList<IrProperty> arrayList2 = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrProperty) {
                    arrayList2.add(obj);
                }
            }
            for (IrProperty irProperty : arrayList2) {
                if (Intrinsics.areEqual(irProperty.getName().asString(), "storage")) {
                    List list = listReversed;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Object value : list) {
                        if (value instanceof Proxy) {
                            Object objSingle2 = CollectionsKt.single(((Proxy) value).getState().getFields().values());
                            Intrinsics.checkNotNull(objSingle2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
                            value = ((Primitive) objSingle2).getValue();
                        }
                        arrayList3.add(value);
                    }
                    IrField backingField = irProperty.getBackingField();
                    Intrinsics.checkNotNull(backingField);
                    Primitive<?> primitiveStateArray2 = UtilsKt.toPrimitiveStateArray(arrayList3, backingField.getType());
                    Common common = new Common(owner);
                    common.setField(irProperty.getSymbol(), primitiveStateArray2);
                    primitiveStateArray = common;
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        primitiveStateArray = UtilsKt.toPrimitiveStateArray(listReversed, expression.getType());
        getCallStack().pushState(primitiveStateArray);
    }

    private final void interpretTry(IrTry element) {
        final State statePeekState = getCallStack().peekState();
        getCallStack().dropSubFrame();
        if (statePeekState instanceof ExceptionState) {
            getCallStack().pushInstruction(new CustomInstruction(new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreter$interpretTry$checkUnhandledException$1
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
                    this.this$0.getCallStack().pushState(statePeekState);
                    this.this$0.getCallStack().dropFramesUntilTryCatch();
                }
            }));
        }
        IrExpression finallyExpression = element.getFinallyExpression();
        if (finallyExpression != null) {
            InstructionsUnfolderKt.handleAndDropResult(finallyExpression, getCallStack());
        }
    }

    private final void interpretThrow(IrThrow expression) {
        getCallStack().dropFramesUntilTryCatch();
    }

    private final void interpretStringConcatenation(IrStringConcatenation expression) {
        String string;
        ArrayList arrayList = new ArrayList();
        int size = expression.getArguments().size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList2 = arrayList;
            State statePopState = getCallStack().popState();
            if (statePopState instanceof Primitive) {
                string = String.valueOf(((Primitive) statePopState).getValue());
            } else {
                string = statePopState instanceof Wrapper ? ((Wrapper) statePopState).getValue().toString() : statePopState.toString();
            }
            arrayList2.add(string);
        }
        getCallStack().pushState(this.environment.convertToState$ir_interpreter(CollectionsKt.joinToString$default(CollectionsKt.reversed(arrayList), "", null, null, 0, null, null, 62, null), expression.getType()));
    }

    private final void interpretFunctionExpression(IrFunctionExpression expression) {
        IrSimpleFunction function = expression.getFunction();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
        Intrinsics.checkNotNull(classOrNull);
        KFunctionState kFunctionState = new KFunctionState(function, classOrNull.getOwner(), this.environment, null, 8, null);
        if (AdditionalIrUtilsKt.isLocal(expression.getFunction())) {
            getCallStack().storeUpValues(kFunctionState);
        }
        getCallStack().pushState(kFunctionState);
    }

    private final void interpretFunctionReference(IrFunctionReference reference) {
        Pair pair;
        IrFunction owner = reference.getSymbol().getOwner();
        Pair pair2 = null;
        State statePopState = (UtilsKt.getDispatchReceiver(owner) == null || reference.getDispatchReceiver() == null) ? null : getCallStack().popState();
        State statePopState2 = (UtilsKt.getExtensionReceiver(owner) == null || reference.getExtensionReceiver() == null) ? null : getCallStack().popState();
        IrInterpreterEnvironment irInterpreterEnvironment = this.environment;
        if (statePopState != null) {
            IrValueParameterSymbol dispatchReceiver = UtilsKt.getDispatchReceiver(owner);
            Intrinsics.checkNotNull(dispatchReceiver);
            pair = new Pair(dispatchReceiver, statePopState);
        } else {
            pair = null;
        }
        if (statePopState2 != null) {
            IrValueParameterSymbol extensionReceiver = UtilsKt.getExtensionReceiver(owner);
            Intrinsics.checkNotNull(extensionReceiver);
            pair2 = new Pair(extensionReceiver, statePopState2);
        }
        KFunctionState kFunctionState = new KFunctionState(reference, irInterpreterEnvironment, (Pair<? extends IrSymbol, ? extends State>) pair, (Pair<? extends IrSymbol, ? extends State>) pair2);
        if (AdditionalIrUtilsKt.isLocal(owner)) {
            getCallStack().storeUpValues(kFunctionState);
        }
        getCallStack().pushState(kFunctionState);
    }

    private final void interpretPropertyReference(IrPropertyReference propertyReference) {
        IrSimpleFunction owner;
        List<IrTypeParameter> typeParameters;
        IrSimpleFunction owner2;
        List<IrTypeParameter> typeParameters2;
        IrSimpleFunctionSymbol getter = propertyReference.getGetter();
        State statePopState = null;
        IrSimpleFunction owner3 = getter != null ? getter.getOwner() : null;
        State statePopState2 = (owner3 == null || UtilsKt.getDispatchReceiver(owner3) == null || propertyReference.getDispatchReceiver() == null) ? null : getCallStack().popState();
        if (owner3 != null && UtilsKt.getExtensionReceiver(owner3) != null && propertyReference.getExtensionReceiver() != null) {
            statePopState = getCallStack().popState();
        }
        if (statePopState2 == null) {
            statePopState2 = statePopState;
        }
        KPropertyState kPropertyState = new KPropertyState(propertyReference, statePopState2);
        IrSimpleFunctionSymbol getter2 = propertyReference.getGetter();
        if (getter2 != null && (owner2 = getter2.getOwner()) != null && (typeParameters2 = owner2.getTypeParameters()) != null) {
            interpretPropertyReference$addToFields(typeParameters2, propertyReference, this, kPropertyState);
        }
        IrSimpleFunctionSymbol setter = propertyReference.getSetter();
        if (setter != null && (owner = setter.getOwner()) != null && (typeParameters = owner.getTypeParameters()) != null) {
            interpretPropertyReference$addToFields(typeParameters, propertyReference, this, kPropertyState);
        }
        getCallStack().pushState(kPropertyState);
    }

    private static final void interpretPropertyReference$addToFields(List<? extends IrTypeParameter> list, IrPropertyReference irPropertyReference, IrInterpreter irInterpreter, KPropertyState kPropertyState) {
        Iterator<Integer> it = RangesKt.until(0, irPropertyReference.getTypeArgumentsCount()).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            IrType typeArgument = irPropertyReference.getTypeArgument(iNextInt);
            if (typeArgument != null) {
                kPropertyState.setField(list.get(iNextInt).getSymbol(), new KTypeState(typeArgument, irInterpreter.environment.getKTypeClass$ir_interpreter().getOwner()));
            }
        }
    }

    private final void interpretClassReference(IrClassReference classReference) {
        if (classReference.getSymbol() instanceof IrTypeParameterSymbol) {
            State stateLoadState = getCallStack().loadState(classReference.getSymbol());
            Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
            CallStack callStack = getCallStack();
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(((KTypeState) stateLoadState).getIrType());
            Intrinsics.checkNotNull(classOrNull);
            IrClass owner = classOrNull.getOwner();
            IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(classReference.getType());
            Intrinsics.checkNotNull(classOrNull2);
            callStack.pushState(new KClassState(owner, classOrNull2.getOwner()));
            return;
        }
        getCallStack().pushState(new KClassState(classReference));
    }

    private final void interpretGetClass(IrGetClass expression) {
        IrClass irClass = getCallStack().popState().getIrClass();
        CallStack callStack = getCallStack();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
        Intrinsics.checkNotNull(classOrNull);
        callStack.pushState(new KClassState(irClass, classOrNull.getOwner()));
    }
}
