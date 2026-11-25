package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.backend.wasm.utils.AnnotationsKt;
import org.jetbrains.kotlin.backend.wasm.utils.DisjointUnions;
import org.jetbrains.kotlin.backend.wasm.utils.UtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.lower.PrimaryConstructorLowering;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.DumpKotlinLikeKt;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmI16;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmI64;
import org.jetbrains.kotlin.wasm.ir.WasmI8;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmImmediateKind;
import org.jetbrains.kotlin.wasm.ir.WasmLocal;
import org.jetbrains.kotlin.wasm.ir.WasmOp;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmTypeDeclaration;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: BodyGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000®\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020*H\u0002J\u0018\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020*2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020$2\u0006\u00100\u001a\u000201H\u0002J\u0015\u00102\u001a\u00020$2\u0006\u0010,\u001a\u00020*H\u0000¢\u0006\u0002\b3J\u0018\u00104\u001a\u00020$2\u0006\u00105\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002J\u000e\u00106\u001a\u00020$2\u0006\u00107\u001a\u000208J \u00109\u001a\u00020$2\u0006\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020.2\u0006\u0010&\u001a\u00020'H\u0002J \u0010<\u001a\u00020$2\u0006\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020.2\u0006\u0010&\u001a\u00020'H\u0002J \u0010=\u001a\u00020$2\u0006\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020.2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010>\u001a\u00020$2\u0006\u0010)\u001a\u00020?H\u0002J\u001d\u0010@\u001a\u00020$2\u0006\u0010,\u001a\u00020*2\u0006\u0010A\u001a\u00020.H\u0000¢\u0006\u0002\bBJ\u0018\u0010C\u001a\u00020D2\u0006\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020.H\u0002J \u0010E\u001a\u00020$2\u0006\u0010F\u001a\u00020.2\u0006\u0010A\u001a\u00020.2\u0006\u0010&\u001a\u00020'H\u0002J\u0018\u0010G\u001a\u00020D2\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020KH\u0002J\u0018\u0010L\u001a\u00020$2\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020KH\u0002J\u0018\u0010M\u001a\u00020D2\u0006\u00100\u001a\u0002012\u0006\u0010N\u001a\u00020OH\u0002J\u0018\u0010P\u001a\u00020D2\u0006\u00100\u001a\u0002012\u0006\u0010N\u001a\u00020OH\u0002J\u0010\u0010Q\u001a\u00020$2\u0006\u0010\f\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020$2\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020$2\u0006\u0010,\u001a\u00020WH\u0016J\u0014\u0010X\u001a\u00020$2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030YH\u0016J\u0010\u0010Z\u001a\u00020$2\u0006\u0010,\u001a\u00020[H\u0016J\u0010\u0010\\\u001a\u00020$2\u0006\u0010,\u001a\u00020]H\u0016J\u0010\u0010^\u001a\u00020$2\u0006\u0010T\u001a\u00020_H\u0016J\u0010\u0010`\u001a\u00020$2\u0006\u0010,\u001a\u00020aH\u0016J\u0010\u0010b\u001a\u00020$2\u0006\u0010c\u001a\u00020dH\u0016J\u0010\u0010e\u001a\u00020$2\u0006\u0010f\u001a\u00020gH\u0016J\u0010\u0010h\u001a\u00020$2\u0006\u0010,\u001a\u00020iH\u0002J\u0010\u0010j\u001a\u00020$2\u0006\u0010,\u001a\u00020kH\u0016J\u0010\u0010l\u001a\u00020$2\u0006\u0010,\u001a\u00020mH\u0016J\u0010\u0010n\u001a\u00020$2\u0006\u0010,\u001a\u00020iH\u0016J\u0010\u0010o\u001a\u00020$2\u0006\u0010,\u001a\u00020pH\u0016J\u0010\u0010q\u001a\u00020$2\u0006\u0010,\u001a\u00020rH\u0016J\u0010\u0010s\u001a\u00020$2\u0006\u0010,\u001a\u00020tH\u0016J\u0010\u0010u\u001a\u00020$2\u0006\u0010v\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020$2\u0006\u0010,\u001a\u00020yH\u0016J\u0010\u0010z\u001a\u00020$2\u0006\u0010,\u001a\u00020IH\u0016J\u0010\u0010{\u001a\u00020$2\u0006\u0010|\u001a\u00020}H\u0016J\u0010\u0010~\u001a\u00020$2\u0006\u0010,\u001a\u00020\u007fH\u0016J\u0012\u0010\u0080\u0001\u001a\u00020$2\u0007\u0010c\u001a\u00030\u0081\u0001H\u0016J\u000b\u0010\u0082\u0001\u001a\u00020$*\u00020\rJ\r\u0010\u0083\u0001\u001a\u00020'*\u00020gH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u001b\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0084\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/BodyGenerator;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "context", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "functionContext", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmFunctionCodegenContext;", "hierarchyDisjointUnions", "Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmFunctionCodegenContext;Lorg/jetbrains/kotlin/backend/wasm/utils/DisjointUnions;)V", "backendContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "body", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "getBody", "()Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "getFunctionContext", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmFunctionCodegenContext;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "unitGetInstance", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getUnitGetInstance", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "unitGetInstance$delegate", "Lkotlin/Lazy;", "unitInstanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getUnitInstanceField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "unitInstanceField$delegate", "wasmSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "generateAnyParameters", "", "klassSymbol", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "generateAsStatement", "statement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "generateBox", "expression", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "generateCall", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "generateExpression", "generateExpression$backend_wasm", "generateInstanceFieldAccess", "field", "generateObjectCreationPrefixIfNeeded", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "generateRefCast", "fromType", "toType", "generateRefNullCast", "generateRefTest", "generateStatement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "generateWithExpectedType", "expectedType", "generateWithExpectedType$backend_wasm", "isDownCastAlwaysSuccessInRuntime", "", "recoverToExpectedType", "actualType", "tryGenerateConstVarargArray", "irVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "wasmArrayType", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$GcType;", "tryGenerateVarargArray", "tryToGenerateIntrinsicCall", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "tryToGenerateWasmOpIntrinsicCall", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitFunctionReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitReturn", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "visitVariable", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "buildGetUnit", "getSourceLocation", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BodyGenerator implements IrElementVisitorVoid {
    private final WasmBackendContext backendContext;
    private final WasmExpressionBuilder body;
    private final WasmModuleCodegenContext context;
    private final WasmFunctionCodegenContext functionContext;
    private final DisjointUnions<IrClassSymbol> hierarchyDisjointUnions;
    private final IrBuiltIns irBuiltIns;

    /* JADX INFO: renamed from: unitGetInstance$delegate, reason: from kotlin metadata */
    private final Lazy unitGetInstance;

    /* JADX INFO: renamed from: unitInstanceField$delegate, reason: from kotlin metadata */
    private final Lazy unitInstanceField;
    private final WasmSymbols wasmSymbols;

    /* JADX INFO: compiled from: BodyGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[WasmImmediateKind.values().length];
            try {
                iArr2[WasmImmediateKind.MEM_ARG.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[WasmImmediateKind.STRUCT_TYPE_IDX.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[WasmImmediateKind.HEAP_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[WasmImmediateKind.TYPE_IDX.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[WasmImmediateKind.MEMORY_IDX.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public BodyGenerator(WasmModuleCodegenContext context, WasmFunctionCodegenContext functionContext, DisjointUnions<IrClassSymbol> hierarchyDisjointUnions) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(functionContext, "functionContext");
        Intrinsics.checkNotNullParameter(hierarchyDisjointUnions, "hierarchyDisjointUnions");
        this.context = context;
        this.functionContext = functionContext;
        this.hierarchyDisjointUnions = hierarchyDisjointUnions;
        this.body = functionContext.getBodyGen();
        WasmBackendContext backendContext = context.getBackendContext();
        this.backendContext = backendContext;
        this.wasmSymbols = backendContext.getWasmSymbols();
        this.irBuiltIns = backendContext.getIrBuiltIns();
        this.unitGetInstance = LazyKt.lazy(new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.BodyGenerator$unitGetInstance$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                return MiscKt.findUnitGetInstanceFunction(this.this$0.backendContext);
            }
        });
        this.unitInstanceField = LazyKt.lazy(new Function0<IrField>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.BodyGenerator$unitInstanceField$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                return MiscKt.findUnitInstanceField(this.this$0.backendContext);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
        visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
        IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
        visitBlock(irBlock, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBlock(IrBlock irBlock) {
        IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBlock(IrBlock irBlock, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
        visitBlockBody(irBlockBody, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
        visitBody(irBody, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBody(IrBody irBody) {
        IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBody(IrBody irBody, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
        visitBranch(irBranch, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBranch(IrBranch irBranch) {
        IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBranch(IrBranch irBranch, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
        visitBreak(irBreak, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreak(IrBreak irBreak, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
        visitBreakContinue(irBreakContinue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreakContinue(IrBreakContinue irBreakContinue) {
        IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
        visitCall(irCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCall(IrCall irCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCallableReference(IrCallableReference<?> irCallableReference) {
        IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
        visitCatch(irCatch, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCatch(IrCatch irCatch) {
        IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCatch(IrCatch irCatch, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
        visitClass(irClass, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitClass */
    public void mo6112visitClass(IrClass irClass) {
        IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitClass(IrClass irClass, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
        visitClassReference(irClassReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitClassReference(IrClassReference irClassReference) {
        IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitClassReference(IrClassReference irClassReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
        visitComposite(irComposite, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitComposite(IrComposite irComposite) {
        IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitComposite(IrComposite irComposite, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        visitConst((IrConst<?>) irConst, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConst(IrConst<?> irConst, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
        visitConstantArray(irConstantArray, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantArray(IrConstantArray irConstantArray) {
        IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
        visitConstantObject(irConstantObject, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantObject(IrConstantObject irConstantObject) {
        IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
        visitConstantPrimitive(irConstantPrimitive, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
        IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
        visitConstantValue(irConstantValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantValue(IrConstantValue irConstantValue) {
        IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
        visitConstructor(irConstructor, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructor(IrConstructor irConstructor) {
        IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructor(IrConstructor irConstructor, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
        visitConstructorCall(irConstructorCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
        visitContainerExpression(irContainerExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
        visitContinue(irContinue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitContinue(IrContinue irContinue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
        visitDeclaration(irDeclarationBase, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
        IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
        visitDeclarationReference(irDeclarationReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
        IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
        visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
        visitDoWhileLoop(irDoWhileLoop, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
        visitDynamicExpression(irDynamicExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
        visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
        visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
        visitElement(irElement, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitElement(IrElement irElement, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
        visitElseBranch(irElseBranch, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitElseBranch(IrElseBranch irElseBranch) {
        IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
        visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
        IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
        visitEnumEntry(irEnumEntry, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumEntry(IrEnumEntry irEnumEntry) {
        IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
        visitErrorCallExpression(irErrorCallExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
        IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
        visitErrorDeclaration(irErrorDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
        IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
        visitErrorExpression(irErrorExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorExpression(IrErrorExpression irErrorExpression) {
        IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
        visitExpression(irExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExpression(IrExpression irExpression) {
        IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExpression(IrExpression irExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
        visitExpressionBody(irExpressionBody, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExpressionBody(IrExpressionBody irExpressionBody) {
        IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
        visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
        IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
        visitField(irField, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitField(IrField irField) {
        IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitField(IrField irField, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
        visitFieldAccess(irFieldAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
        IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
        visitFile(irFile, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFile(IrFile irFile) {
        IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFile(IrFile irFile, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
        visitFunction(irFunction, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunction(IrFunction irFunction) {
        IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunction(IrFunction irFunction, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
        visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
        visitFunctionExpression(irFunctionExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
        visitFunctionReference(irFunctionReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionReference(IrFunctionReference irFunctionReference) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
        visitGetClass(irGetClass, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetClass(IrGetClass irGetClass) {
        IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetClass(IrGetClass irGetClass, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
        visitGetEnumValue(irGetEnumValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
        IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
        visitGetField(irGetField, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetField(IrGetField irGetField, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
        visitGetObjectValue(irGetObjectValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
        IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
        visitGetValue(irGetValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetValue(IrGetValue irGetValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
        visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
        IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
        visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
        IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
        visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
        IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
        visitLoop(irLoop, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLoop(IrLoop irLoop) {
        IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLoop(IrLoop irLoop, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
        visitModuleFragment(irModuleFragment, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitModuleFragment(IrModuleFragment irModuleFragment) {
        IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
        visitPackageFragment(irPackageFragment, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitPackageFragment(IrPackageFragment irPackageFragment) {
        IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
        visitProperty(irProperty, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitProperty(IrProperty irProperty) {
        IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitProperty(IrProperty irProperty, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
        visitPropertyReference(irPropertyReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitPropertyReference(IrPropertyReference irPropertyReference) {
        IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
        visitRawFunctionReference(irRawFunctionReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
        IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
        visitReturn(irReturn, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitReturn(IrReturn irReturn, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
        visitScript(irScript, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitScript(IrScript irScript) {
        IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitScript(IrScript irScript, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
        visitSetField(irSetField, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetField(IrSetField irSetField, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
        visitSetValue(irSetValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetValue(IrSetValue irSetValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
        visitSimpleFunction(irSimpleFunction, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
        IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
        visitSingletonReference(irGetSingletonValue, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
        IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
        visitSpreadElement(irSpreadElement, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSpreadElement(IrSpreadElement irSpreadElement) {
        IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
        visitStringConcatenation(irStringConcatenation, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
        IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
        visitSuspendableExpression(irSuspendableExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
        IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
        visitSuspensionPoint(irSuspensionPoint, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
        IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
        visitSyntheticBody(irSyntheticBody, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
        IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
        visitThrow(irThrow, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitThrow(IrThrow irThrow, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
        visitTry(irTry, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTry(IrTry irTry, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
        visitTypeAlias(irTypeAlias, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeAlias(IrTypeAlias irTypeAlias) {
        IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
        visitTypeOperator(irTypeOperatorCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
        visitTypeParameter(irTypeParameter, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeParameter(IrTypeParameter irTypeParameter) {
        IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
        visitValueAccess(irValueAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
        IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
        visitValueParameter(irValueParameter, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueParameter(IrValueParameter irValueParameter) {
        IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
        visitVararg(irVararg, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVararg(IrVararg irVararg, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
        visitVariable(irVariable, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVariable(IrVariable irVariable, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
        visitWhen(irWhen, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhen(IrWhen irWhen, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
        visitWhileLoop(irWhileLoop, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
    }

    public final WasmModuleCodegenContext getContext() {
        return this.context;
    }

    public final WasmFunctionCodegenContext getFunctionContext() {
        return this.functionContext;
    }

    public final WasmExpressionBuilder getBody() {
        return this.body;
    }

    private final IrSimpleFunction getUnitGetInstance() {
        return (IrSimpleFunction) this.unitGetInstance.getValue();
    }

    private final IrField getUnitInstanceField() {
        return (IrField) this.unitInstanceField.getValue();
    }

    public final void buildGetUnit(WasmExpressionBuilder wasmExpressionBuilder) {
        Intrinsics.checkNotNullParameter(wasmExpressionBuilder, "<this>");
        wasmExpressionBuilder.buildGetGlobal(this.context.referenceGlobalField(getUnitInstanceField().getSymbol()), SourceLocation.INSTANCE.NoLocation("GET_UNIT"));
    }

    public final void generateExpression$backend_wasm(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVisitorsKt.acceptVoid(expression, this);
        if (IrTypePredicatesKt.isNothing(expression.getType())) {
            LocationUtilsKt.buildUnreachableAfterNothingType(this.body);
        }
    }

    private final void generateAsStatement(IrExpression statement) {
        generateExpression$backend_wasm(statement);
        if (Intrinsics.areEqual(statement.getType(), this.wasmSymbols.getVoidType())) {
            return;
        }
        this.body.buildDrop(getSourceLocation(statement));
    }

    private final void generateStatement(IrStatement statement) {
        if (statement instanceof IrExpression) {
            generateAsStatement((IrExpression) statement);
        } else {
            if (!(statement instanceof IrVariable)) {
                throw new IllegalStateException(("Unsupported node type: " + Reflection.getOrCreateKotlinClass(statement.getClass()).getSimpleName()).toString());
            }
            IrVisitorsKt.acceptVoid(statement, this);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6335visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new IllegalStateException(("Unexpected element of type " + Reflection.getOrCreateKotlinClass(element.getClass())).toString());
    }

    private final boolean tryGenerateConstVarargArray(IrVararg irVararg, WasmImmediate.GcType wasmArrayType) {
        IrConstKind kind;
        Pair<? extends List<Long>, ? extends WasmType> pair;
        if (irVararg.getElements().isEmpty()) {
            return false;
        }
        IrVarargElement irVarargElement = irVararg.getElements().get(0);
        IrConst irConst = irVarargElement instanceof IrConst ? (IrConst) irVarargElement : null;
        if (irConst != null && (kind = irConst.getKind()) != null && !Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE) && !Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
            List<IrVarargElement> elements = irVararg.getElements();
            if (!(elements instanceof Collection) || !elements.isEmpty()) {
                for (IrVarargElement irVarargElement2 : elements) {
                    if (!(irVarargElement2 instanceof IrConst) || !Intrinsics.areEqual(((IrConst) irVarargElement2).getKind(), kind)) {
                        return false;
                    }
                }
            }
            List<IrVarargElement> elements2 = irVararg.getElements();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
            for (IrVarargElement irVarargElement3 : elements2) {
                Intrinsics.checkNotNull(irVarargElement3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
                Object value = ((IrConst) irVarargElement3).getValue();
                Intrinsics.checkNotNull(value);
                arrayList.add(value);
            }
            ArrayList arrayList2 = arrayList;
            IrType varargElementType = irVararg.getVarargElementType();
            if (!Intrinsics.areEqual(varargElementType, this.irBuiltIns.getByteType())) {
                if (!Intrinsics.areEqual(varargElementType, this.irBuiltIns.getBooleanType())) {
                    if (!Intrinsics.areEqual(varargElementType, this.irBuiltIns.getIntType())) {
                        if (!Intrinsics.areEqual(varargElementType, this.irBuiltIns.getShortType())) {
                            if (Intrinsics.areEqual(varargElementType, this.irBuiltIns.getLongType())) {
                                ArrayList arrayList3 = arrayList2;
                                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                                for (Object obj : arrayList3) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                    arrayList4.add(Long.valueOf(((Long) obj).longValue()));
                                }
                                pair = TuplesKt.to(arrayList4, WasmI64.INSTANCE);
                            }
                        } else {
                            ArrayList arrayList5 = arrayList2;
                            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
                            for (Object obj2 : arrayList5) {
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                arrayList6.add(Long.valueOf(((Short) obj2).shortValue()));
                            }
                            pair = TuplesKt.to(arrayList6, WasmI16.INSTANCE);
                        }
                    } else {
                        ArrayList arrayList7 = arrayList2;
                        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
                        for (Object obj3 : arrayList7) {
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
                            arrayList8.add(Long.valueOf(((Integer) obj3).intValue()));
                        }
                        pair = TuplesKt.to(arrayList8, WasmI32.INSTANCE);
                    }
                } else {
                    ArrayList arrayList9 = arrayList2;
                    ArrayList arrayList10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList9, 10));
                    for (Object obj4 : arrayList9) {
                        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Boolean");
                        arrayList10.add(Long.valueOf(((Boolean) obj4).booleanValue() ? 1L : 0L));
                    }
                    pair = TuplesKt.to(arrayList10, WasmI8.INSTANCE);
                }
            } else {
                ArrayList arrayList11 = arrayList2;
                ArrayList arrayList12 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList11, 10));
                for (Object obj5 : arrayList11) {
                    Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Byte");
                    arrayList12.add(Long.valueOf(((Byte) obj5).byteValue()));
                }
                pair = TuplesKt.to(arrayList12, WasmI8.INSTANCE);
            }
            WasmSymbol<Integer> wasmSymbolReferenceConstantArray = this.context.referenceConstantArray(pair);
            SourceLocation sourceLocation = getSourceLocation(irVararg);
            this.body.buildConstI32(0, sourceLocation);
            this.body.buildConstI32(irVararg.getElements().size(), sourceLocation);
            this.body.buildInstr(WasmOp.ARRAY_NEW_DATA, sourceLocation, wasmArrayType, new WasmImmediate.DataIdx(wasmSymbolReferenceConstantArray));
            return true;
        }
        return false;
    }

    private final void tryGenerateVarargArray(IrVararg irVararg, WasmImmediate.GcType wasmArrayType) {
        for (IrVarargElement irVarargElement : irVararg.getElements()) {
            if (!(irVarargElement instanceof IrExpression)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            generateExpression$backend_wasm((IrExpression) irVarargElement);
        }
        this.body.buildInstr(WasmOp.ARRAY_NEW_FIXED, getSourceLocation(irVararg), wasmArrayType, new WasmImmediate.ConstI32(irVararg.getElements().size()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVararg(IrVararg expression) {
        WasmImmediate.GcType gcType;
        Object next;
        IrClass runtimeClass;
        IrClassSymbol symbol;
        WasmSymbol<WasmTypeDeclaration> wasmSymbolReferenceGcType;
        IrClass irClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClass irClass2 = IrTypesKt.getClass(expression.getType());
        Intrinsics.checkNotNull(irClass2);
        Iterator it = SequencesKt.mapNotNull(IrUtilsKt.getConstructors(irClass2), new Function1<IrConstructor, IrType>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.BodyGenerator$visitVararg$wasmArrayType$1
            @Override // kotlin.jvm.functions.Function1
            public final IrType invoke(IrConstructor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) it2.getValueParameters());
                if (irValueParameter != null) {
                    return irValueParameter.getType();
                }
                return null;
            }
        }).iterator();
        do {
            gcType = null;
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                irClass = IrTypesKt.getClass((IrType) next);
            }
        } while ((irClass != null ? AnnotationsKt.getWasmArrayAnnotation(irClass) : null) == null);
        IrType irType = (IrType) next;
        if (irType != null && (runtimeClass = TypeTransformerKt.getRuntimeClass(irType, this.irBuiltIns)) != null && (symbol = runtimeClass.getSymbol()) != null && (wasmSymbolReferenceGcType = this.context.referenceGcType(symbol)) != null) {
            gcType = new WasmImmediate.GcType(wasmSymbolReferenceGcType);
        }
        if (gcType == null) {
            throw new IllegalStateException("Check failed.".toString());
        }
        SourceLocation sourceLocation = getSourceLocation(expression);
        generateAnyParameters(irClass2.getSymbol(), sourceLocation);
        if (!tryGenerateConstVarargArray(expression, gcType)) {
            tryGenerateVarargArray(expression, gcType);
        }
        this.body.buildStructNew(this.context.referenceGcType(TypeTransformerKt.getRuntimeClass(expression.getType(), this.irBuiltIns).getSymbol()), sourceLocation);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        generateExpression$backend_wasm(expression.getValue());
        if (this.context.getBackendContext().getConfiguration().getBoolean(JSConfigurationKeys.WASM_USE_TRAPS_INSTEAD_OF_EXCEPTIONS)) {
            this.body.buildUnreachable(SourceLocation.INSTANCE.NoLocation("Unreachable is inserted instead of a `throw` instruction"));
        } else {
            this.body.buildThrow(this.functionContext.getTagIdx(), getSourceLocation(expression));
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        UtilsKt.isCanonical(aTry, this.irBuiltIns);
        if (this.context.getBackendContext().getConfiguration().getBoolean(JSConfigurationKeys.WASM_USE_TRAPS_INSTEAD_OF_EXCEPTIONS)) {
            generateExpression$backend_wasm(aTry.getTryResult());
            return;
        }
        this.body.buildTry(null, this.context.transformBlockResultType(aTry.getType()));
        generateExpression$backend_wasm(aTry.getTryResult());
        this.body.buildCatch(this.functionContext.getTagIdx());
        IrVariableSymbol symbol = ((IrCatch) CollectionsKt.single((List) aTry.getCatches())).getCatchParameter().getSymbol();
        IrVariableSymbol irVariableSymbol = symbol;
        this.functionContext.defineLocal(irVariableSymbol);
        this.body.buildSetLocal(this.functionContext.referenceLocal(irVariableSymbol), getSourceLocation(symbol.getOwner()));
        generateExpression$backend_wasm(((IrCatch) CollectionsKt.single((List) aTry.getCatches())).getResult());
        this.body.buildEnd();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()];
        if (i == 1) {
            generateExpression$backend_wasm(expression.getArgument());
        } else {
            if (i != 2) {
                return;
            }
            generateAsStatement(expression.getArgument());
            buildGetUnit(this.body);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConst(IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        DeclarationGeneratorKt.generateConstExpression(expression, this.body, this.context, getSourceLocation(expression));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField owner = expression.getSymbol().getOwner();
        IrExpression receiver = expression.getReceiver();
        SourceLocation sourceLocation = getSourceLocation(expression);
        if (receiver != null) {
            generateExpression$backend_wasm(receiver);
            if (this.backendContext.getInlineClassesUtils().isClassInlineLike(IrUtilsKt.getParentAsClass(owner))) {
                return;
            }
            generateInstanceFieldAccess(owner, sourceLocation);
            return;
        }
        this.body.buildGetGlobal(this.context.referenceGlobalField(owner.getSymbol()), sourceLocation);
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("type: " + RenderIrElementKt.render$default(owner.getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
    }

    private final void generateInstanceFieldAccess(IrField field, SourceLocation location) {
        WasmOp wasmOp;
        IrType type = field.getType();
        if (Intrinsics.areEqual(type, this.irBuiltIns.getCharType())) {
            wasmOp = WasmOp.STRUCT_GET_U;
        } else {
            if (Intrinsics.areEqual(type, this.irBuiltIns.getBooleanType()) ? true : Intrinsics.areEqual(type, this.irBuiltIns.getByteType()) ? true : Intrinsics.areEqual(type, this.irBuiltIns.getShortType())) {
                wasmOp = WasmOp.STRUCT_GET_S;
            } else {
                wasmOp = WasmOp.STRUCT_GET;
            }
        }
        this.body.buildInstr(wasmOp, location, new WasmImmediate.GcType(this.context.referenceGcType(IrUtilsKt.getParentAsClass(field).getSymbol())), new WasmImmediate.StructFieldIdx(this.context.getStructFieldRef(field)));
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("name: " + field.getName().asString() + ", type: " + RenderIrElementKt.render$default(field.getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField owner = expression.getSymbol().getOwner();
        IrExpression receiver = expression.getReceiver();
        SourceLocation sourceLocation = getSourceLocation(expression);
        if (receiver != null) {
            generateExpression$backend_wasm(receiver);
            generateExpression$backend_wasm(expression.getValue());
            this.body.buildStructSet(this.context.referenceGcType(IrUtilsKt.getParentAsClass(owner).getSymbol()), this.context.getStructFieldRef(owner), sourceLocation);
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("name: " + owner.getName() + ", type: " + RenderIrElementKt.render$default(owner.getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
        } else {
            generateExpression$backend_wasm(expression.getValue());
            this.body.buildSetGlobal(this.context.referenceGlobalField(expression.getSymbol()), sourceLocation);
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("type: " + RenderIrElementKt.render$default(owner.getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
        }
        buildGetUnit(this.body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetValue(IrGetValue expression) {
        WasmLocal wasmLocalReferenceLocal;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueSymbol symbol = expression.getSymbol();
        IrValueDeclaration owner = symbol.getOwner();
        WasmExpressionBuilder wasmExpressionBuilder = this.body;
        if (MiscKt.isDispatchReceiver(owner)) {
            wasmLocalReferenceLocal = this.functionContext.referenceLocal(0);
        } else {
            wasmLocalReferenceLocal = this.functionContext.referenceLocal(symbol);
        }
        wasmExpressionBuilder.buildGetLocal(wasmLocalReferenceLocal, getSourceLocation(expression));
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("type: " + RenderIrElementKt.render$default(owner.getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        generateExpression$backend_wasm(expression.getValue());
        this.body.buildSetLocal(this.functionContext.referenceLocal(expression.getSymbol()), getSourceLocation(expression));
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("type: " + RenderIrElementKt.render$default(expression.getSymbol().getOwner().getType(), (DumpIrTreeOptions) null, 1, (Object) null)));
        buildGetUnit(this.body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        generateCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner());
        IrClassSymbol symbol = parentAsClass.getSymbol();
        if (this.backendContext.getInlineClassesUtils().isClassInlineLike(parentAsClass)) {
            throw new IllegalArgumentException("All inline class constructor calls must be lowered to static function calls".toString());
        }
        WasmSymbol<WasmTypeDeclaration> wasmSymbolReferenceGcType = this.context.referenceGcType(symbol);
        SourceLocation sourceLocation = getSourceLocation(expression);
        if (AnnotationsKt.getWasmArrayAnnotation(parentAsClass) != null) {
            if (expression.getValueArgumentsCount() != 1) {
                throw new IllegalArgumentException("@WasmArrayOf constructs must have exactly one argument".toString());
            }
            IrExpression valueArgument = expression.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            generateExpression$backend_wasm(valueArgument);
            this.body.buildInstr(WasmOp.ARRAY_NEW_DEFAULT, sourceLocation, new WasmImmediate.GcType(wasmSymbolReferenceGcType));
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("@WasmArrayOf ctor call: " + IrUtilsKt.getFqNameWhenAvailable(parentAsClass)));
            return;
        }
        if (AnnotationsKt.hasWasmPrimitiveConstructorAnnotation(expression.getSymbol().getOwner())) {
            generateAnyParameters(symbol, sourceLocation);
            int valueArgumentsCount = expression.getValueArgumentsCount();
            for (int i = 0; i < valueArgumentsCount; i++) {
                IrExpression valueArgument2 = expression.getValueArgument(i);
                Intrinsics.checkNotNull(valueArgument2);
                generateExpression$backend_wasm(valueArgument2);
            }
            this.body.buildStructNew(wasmSymbolReferenceGcType, sourceLocation);
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("@WasmPrimitiveConstructor ctor call: " + IrUtilsKt.getFqNameWhenAvailable(parentAsClass)));
            return;
        }
        this.body.buildRefNull(WasmHeapType.Simple.None.INSTANCE, sourceLocation);
        generateCall(expression);
    }

    private final void generateAnyParameters(IrClassSymbol klassSymbol, SourceLocation location) {
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("Any parameters"));
        this.body.buildGetGlobal(this.context.referenceGlobalVTable(klassSymbol), location);
        if (ClassInfoKt.hasInterfaceSuperClass(klassSymbol.getOwner())) {
            this.body.buildGetGlobal(this.context.referenceGlobalClassITable(klassSymbol), location);
        } else {
            this.body.buildRefNull(WasmHeapType.Simple.Struct.INSTANCE, location);
        }
        this.body.buildConstI32Symbol(this.context.referenceTypeId(klassSymbol), location);
        this.body.buildConstI32(0, location);
        this.body.commentGroupEnd();
    }

    public final void generateObjectCreationPrefixIfNeeded(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(constructor);
        if (Intrinsics.areEqual(constructor.getOrigin(), PrimaryConstructorLowering.SYNTHETIC_PRIMARY_CONSTRUCTOR.INSTANCE) || UtilsKt.isAbstractOrSealed(parentAsClass)) {
            return;
        }
        WasmFunctionCodegenContext wasmFunctionCodegenContext = this.functionContext;
        IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        WasmLocal wasmLocalReferenceLocal = wasmFunctionCodegenContext.referenceLocal(thisReceiver.getSymbol());
        int i = 0;
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("Object creation prefix"));
        SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("Constructor preamble");
        this.body.buildGetLocal(wasmLocalReferenceLocal, sourceLocationNoLocation);
        this.body.buildInstr(WasmOp.REF_IS_NULL, sourceLocationNoLocation, new WasmImmediate[0]);
        WasmExpressionBuilder.buildIf$default(this.body, "this_init", null, 2, null);
        generateAnyParameters(parentAsClass.getSymbol(), sourceLocationNoLocation);
        for (Object obj : ClassInfoKt.allFields(parentAsClass, this.backendContext.getIrBuiltIns())) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrField irField = (IrField) obj;
            if (i > 1) {
                DeclarationGeneratorKt.generateDefaultInitializerForType(this.context.transformType(irField.getType()), this.body);
            }
            i = i2;
        }
        this.body.buildStructNew(this.context.referenceGcType(parentAsClass.getSymbol()), sourceLocationNoLocation);
        this.body.buildSetLocal(wasmLocalReferenceLocal, sourceLocationNoLocation);
        this.body.buildEnd();
        this.body.commentGroupEnd();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (IrTypePredicatesKt.isAny(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(this.functionContext.getIrFunction())))) {
            buildGetUnit(this.body);
        } else {
            this.body.buildGetLocal(this.functionContext.referenceLocal(0), SourceLocation.INSTANCE.NoLocation("Get implicit dispatch receiver"));
            generateCall(expression);
        }
    }

    private final void generateBox(IrExpression expression, IrType type) {
        IrClassSymbol symbol = TypeTransformerKt.getRuntimeClass(type, this.irBuiltIns).getSymbol();
        SourceLocation sourceLocation = getSourceLocation(expression);
        generateAnyParameters(symbol, sourceLocation);
        generateExpression$backend_wasm(expression);
        this.body.buildStructNew(this.context.referenceGcType(symbol), sourceLocation);
        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("box"));
    }

    /* JADX WARN: Code duplicated, block: B:72:0x02c7  */
    private final void generateCall(IrFunctionAccessExpression call) {
        SourceLocation sourceLocation = getSourceLocation(call);
        if (Intrinsics.areEqual(call.getSymbol(), this.wasmSymbols.getBoxIntrinsic())) {
            IrExpression valueArgument = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            IrType typeArgument = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument);
            generateBox(valueArgument, typeArgument);
            return;
        }
        if (!((Boolean) this.backendContext.getConfiguration().getNotNull(JSConfigurationKeys.WASM_ENABLE_ARRAY_RANGE_CHECKS)).booleanValue() && Intrinsics.areEqual(call.getSymbol(), this.wasmSymbols.getRangeCheck())) {
            buildGetUnit(this.body);
            return;
        }
        if (!((Boolean) this.backendContext.getConfiguration().getNotNull(JSConfigurationKeys.WASM_ENABLE_ASSERTS)).booleanValue() && CollectionsKt.contains(this.wasmSymbols.getAssertFuncs(), call.getSymbol())) {
            buildGetUnit(this.body);
            return;
        }
        IrFunction realOverrideTarget = IrResolveUtilsKt.getRealOverrideTarget(call.getSymbol().getOwner());
        IrExpression dispatchReceiver = call.getDispatchReceiver();
        if (dispatchReceiver != null) {
            generateExpression$backend_wasm(dispatchReceiver);
        }
        IrExpression extensionReceiver = call.getExtensionReceiver();
        if (extensionReceiver != null) {
            generateExpression$backend_wasm(extensionReceiver);
        }
        int valueArgumentsCount = call.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument2 = call.getValueArgument(i);
            if (valueArgument2 == null) {
                DeclarationGeneratorKt.generateDefaultInitializerForType(this.context.transformType(realOverrideTarget.getValueParameters().get(i).getType()), this.body);
            } else {
                generateExpression$backend_wasm(valueArgument2);
            }
        }
        if (tryToGenerateIntrinsicCall(call, realOverrideTarget)) {
            if (IrTypePredicatesKt.isUnit(realOverrideTarget.getReturnType())) {
                buildGetUnit(this.body);
                return;
            }
            return;
        }
        if (AnnotationsKt.hasWasmPrimitiveConstructorAnnotation(realOverrideTarget.getSymbol().getOwner())) {
            return;
        }
        boolean z = (call instanceof IrCall) && ((IrCall) call).getSuperQualifierSymbol() != null;
        if (realOverrideTarget instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) realOverrideTarget;
            if (IrUtilsKt.isOverridable(irSimpleFunction) && !z) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(realOverrideTarget);
                int i2 = -1;
                if (IrUtilsKt.isInterface(parentAsClass)) {
                    IrClassSymbol symbol = parentAsClass.getSymbol();
                    if (this.hierarchyDisjointUnions.contains(symbol)) {
                        IrExpression dispatchReceiver2 = call.getDispatchReceiver();
                        Intrinsics.checkNotNull(dispatchReceiver2);
                        generateExpression$backend_wasm(dispatchReceiver2);
                        this.body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("interface call: " + IrUtilsKt.getFqNameWhenAvailable(realOverrideTarget)));
                        this.body.buildStructGet(this.context.referenceGcType(this.irBuiltIns.getAnyClass()), new WasmSymbol<>(1), sourceLocation);
                        WasmSymbol<WasmTypeDeclaration> wasmSymbolReferenceClassITableGcType = this.context.referenceClassITableGcType(symbol);
                        this.body.buildRefCastStatic(wasmSymbolReferenceClassITableGcType, sourceLocation);
                        this.body.buildStructGet(wasmSymbolReferenceClassITableGcType, this.context.referenceClassITableInterfaceSlot(symbol), sourceLocation);
                        Iterator<VirtualMethodMetadata> it = this.context.getInterfaceMetadata(symbol).getMethods().iterator();
                        int i3 = 0;
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(it.next().getFunction(), realOverrideTarget)) {
                                i2 = i3;
                                break;
                            }
                            i3++;
                        }
                        this.body.buildStructGet(this.context.referenceVTableGcType(symbol), new WasmSymbol<>(Integer.valueOf(i2)), sourceLocation);
                        this.body.buildInstr(WasmOp.CALL_REF, sourceLocation, new WasmImmediate.TypeIdx(this.context.referenceFunctionType(irSimpleFunction.getSymbol())));
                        this.body.commentGroupEnd();
                    } else {
                        LocationUtilsKt.buildUnreachableForVerifier(this.body);
                    }
                } else {
                    Iterator<VirtualMethodMetadata> it2 = this.context.getClassMetadata(parentAsClass.getSymbol()).getVirtualMethods().iterator();
                    int i4 = 0;
                    while (it2.hasNext()) {
                        if (Intrinsics.areEqual(it2.next().getFunction(), realOverrideTarget)) {
                            i2 = i4;
                            break;
                        }
                        i4++;
                    }
                    IrExpression dispatchReceiver3 = call.getDispatchReceiver();
                    Intrinsics.checkNotNull(dispatchReceiver3);
                    generateExpression$backend_wasm(dispatchReceiver3);
                    this.body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("virtual call: " + IrUtilsKt.getFqNameWhenAvailable(realOverrideTarget)));
                    generateRefCast(dispatchReceiver3.getType(), IrUtilsKt.getDefaultType(parentAsClass), sourceLocation);
                    this.body.buildStructGet(this.context.referenceGcType(parentAsClass.getSymbol()), new WasmSymbol<>(0), sourceLocation);
                    this.body.buildStructGet(this.context.referenceVTableGcType(parentAsClass.getSymbol()), new WasmSymbol<>(Integer.valueOf(i2)), sourceLocation);
                    this.body.buildInstr(WasmOp.CALL_REF, sourceLocation, new WasmImmediate.TypeIdx(this.context.referenceFunctionType(irSimpleFunction.getSymbol())));
                    this.body.commentGroupEnd();
                }
            } else {
                this.body.buildCall(this.context.referenceFunction(realOverrideTarget.getSymbol()), sourceLocation);
            }
        } else {
            this.body.buildCall(this.context.referenceFunction(realOverrideTarget.getSymbol()), sourceLocation);
        }
        if (!IrTypePredicatesKt.isUnit(realOverrideTarget.getReturnType()) || (realOverrideTarget instanceof IrConstructor)) {
            return;
        }
        buildGetUnit(this.body);
    }

    private final void generateRefNullCast(IrType fromType, IrType toType, SourceLocation location) {
        if (isDownCastAlwaysSuccessInRuntime(fromType, toType)) {
            return;
        }
        this.body.buildRefCastNullStatic(this.context.referenceGcType(TypeTransformerKt.getRuntimeClass(toType, this.irBuiltIns).getSymbol()), location);
    }

    private final void generateRefCast(IrType fromType, IrType toType, SourceLocation location) {
        if (isDownCastAlwaysSuccessInRuntime(fromType, toType)) {
            return;
        }
        this.body.buildRefCastStatic(this.context.referenceGcType(TypeTransformerKt.getRuntimeClass(toType, this.irBuiltIns).getSymbol()), location);
    }

    private final void generateRefTest(IrType fromType, IrType toType, SourceLocation location) {
        if (!isDownCastAlwaysSuccessInRuntime(fromType, toType)) {
            this.body.buildRefTestStatic(this.context.referenceGcType(TypeTransformerKt.getRuntimeClass(toType, this.irBuiltIns).getSymbol()), location);
        } else {
            this.body.buildDrop(location);
            this.body.buildConstI32(1, location);
        }
    }

    private final boolean isDownCastAlwaysSuccessInRuntime(IrType fromType, IrType toType) {
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(fromType);
        if (erasedUpperBound == null || !IrTypeUtilsKt.isSubtypeOfClass(erasedUpperBound.getSymbol(), this.backendContext.getWasmSymbols().getWasmAnyRefClass())) {
            return IrUtilsKt.isSubclassOf(TypeTransformerKt.getRuntimeClass(fromType, this.irBuiltIns), TypeTransformerKt.getRuntimeClass(toType, this.irBuiltIns));
        }
        return false;
    }

    private final boolean tryToGenerateIntrinsicCall(IrFunctionAccessExpression call, IrFunction function) {
        if (tryToGenerateWasmOpIntrinsicCall(call, function)) {
            return true;
        }
        SourceLocation sourceLocation = getSourceLocation(call);
        IrFunctionSymbol symbol = function.getSymbol();
        if (Intrinsics.areEqual(symbol, this.wasmSymbols.getWasmTypeId())) {
            IrType typeArgument = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument);
            IrClass irClass = IrTypesKt.getClass(typeArgument);
            if (irClass == null) {
                throw new IllegalStateException("No class given for wasmTypeId intrinsic".toString());
            }
            this.body.buildConstI32Symbol(this.context.referenceTypeId(irClass.getSymbol()), sourceLocation);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getWasmIsInterface())) {
            IrType typeArgument2 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument2);
            IrClass irClass2 = IrTypesKt.getClass(typeArgument2);
            if (irClass2 == null) {
                throw new IllegalStateException("No interface given for wasmIsInterface intrinsic".toString());
            }
            IrUtilsKt.isInterface(irClass2);
            if (this.hierarchyDisjointUnions.contains(irClass2.getSymbol())) {
                WasmSymbol<WasmTypeDeclaration> wasmSymbolReferenceClassITableGcType = this.context.referenceClassITableGcType(irClass2.getSymbol());
                WasmLocal wasmLocalReferenceLocal = this.functionContext.referenceLocal(SyntheticLocalType.IS_INTERFACE_PARAMETER);
                this.body.buildSetLocal(wasmLocalReferenceLocal, sourceLocation);
                WasmExpressionBuilder wasmExpressionBuilder = this.body;
                WasmI32 wasmI32 = WasmI32.INSTANCE;
                wasmExpressionBuilder.setNumberOfNestedBlocks(wasmExpressionBuilder.getNumberOfNestedBlocks() + 1);
                wasmExpressionBuilder.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(wasmI32));
                int numberOfNestedBlocks = wasmExpressionBuilder.getNumberOfNestedBlocks();
                WasmExpressionBuilder wasmExpressionBuilder2 = this.body;
                wasmExpressionBuilder2.setNumberOfNestedBlocks(wasmExpressionBuilder2.getNumberOfNestedBlocks() + 1);
                wasmExpressionBuilder2.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(null));
                int numberOfNestedBlocks2 = wasmExpressionBuilder2.getNumberOfNestedBlocks();
                this.body.buildGetLocal(wasmLocalReferenceLocal, sourceLocation);
                this.body.buildStructGet(this.context.referenceGcType(this.irBuiltIns.getAnyClass()), new WasmSymbol<>(1), sourceLocation);
                WasmLocal wasmLocalReferenceLocal2 = this.functionContext.referenceLocal(SyntheticLocalType.TMP_FOR_BR_ON_CAST_EMULATION);
                this.body.buildInstr(WasmOp.LOCAL_TEE, sourceLocation, new WasmImmediate.LocalIdx(wasmLocalReferenceLocal2));
                WasmSymbol<WasmTypeDeclaration> wasmSymbol = wasmSymbolReferenceClassITableGcType;
                this.body.buildRefTestStatic(wasmSymbol, sourceLocation);
                this.body.buildInstr(WasmOp.I32_EQZ, sourceLocation, new WasmImmediate[0]);
                this.body.buildBrIf(numberOfNestedBlocks2, sourceLocation);
                this.body.buildGetLocal(wasmLocalReferenceLocal2, sourceLocation);
                this.body.buildRefCastStatic(wasmSymbol, sourceLocation);
                this.body.buildStructGet(wasmSymbolReferenceClassITableGcType, this.context.referenceClassITableInterfaceSlot(irClass2.getSymbol()), sourceLocation);
                this.body.buildInstr(WasmOp.REF_IS_NULL, sourceLocation, new WasmImmediate[0]);
                this.body.buildInstr(WasmOp.I32_EQZ, sourceLocation, new WasmImmediate[0]);
                this.body.buildBr(numberOfNestedBlocks, sourceLocation);
                wasmExpressionBuilder2.buildEnd();
                this.body.buildConstI32(0, sourceLocation);
                wasmExpressionBuilder.buildEnd();
            } else {
                this.body.buildDrop(sourceLocation);
                this.body.buildConstI32(0, sourceLocation);
            }
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getRefCastNull())) {
            IrExpression valueArgument = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            IrType type = valueArgument.getType();
            IrType typeArgument3 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument3);
            generateRefNullCast(type, typeArgument3, sourceLocation);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getRefTest())) {
            IrExpression valueArgument2 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument2);
            IrType type2 = valueArgument2.getType();
            IrType typeArgument4 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument4);
            generateRefTest(type2, typeArgument4, sourceLocation);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getUnboxIntrinsic())) {
            IrType typeArgument5 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument5);
            if (IrTypePredicatesKt.isNothing(typeArgument5)) {
                LocationUtilsKt.buildUnreachableAfterNothingType(this.body);
                return true;
            }
            IrType typeArgument6 = call.getTypeArgument(1);
            Intrinsics.checkNotNull(typeArgument6);
            IrClass inlinedClass = this.backendContext.getInlineClassesUtils().getInlinedClass(typeArgument6);
            Intrinsics.checkNotNull(inlinedClass);
            IrField inlineClassBackingField = InlineClassesKt.getInlineClassBackingField(inlinedClass);
            generateRefCast(typeArgument5, typeArgument6, sourceLocation);
            generateInstanceFieldAccess(inlineClassBackingField, sourceLocation);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getUnsafeGetScratchRawMemory())) {
            this.body.buildConstI32Symbol(this.context.getScratchMemAddr(), sourceLocation);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getReturnArgumentIfItIsKotlinAny())) {
            WasmExpressionBuilder wasmExpressionBuilder3 = this.body;
            wasmExpressionBuilder3.setNumberOfNestedBlocks(wasmExpressionBuilder3.getNumberOfNestedBlocks() + 1);
            wasmExpressionBuilder3.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(null));
            int numberOfNestedBlocks3 = wasmExpressionBuilder3.getNumberOfNestedBlocks();
            this.body.buildGetLocal(this.functionContext.referenceLocal(0), sourceLocation);
            this.body.buildInstr(WasmOp.EXTERN_INTERNALIZE, sourceLocation, new WasmImmediate[0]);
            WasmLocal wasmLocalReferenceLocal3 = this.functionContext.referenceLocal(SyntheticLocalType.TMP_FOR_BR_ON_CAST_EMULATION);
            this.body.buildInstr(WasmOp.LOCAL_TEE, sourceLocation, new WasmImmediate.LocalIdx(wasmLocalReferenceLocal3));
            WasmSymbol<WasmTypeDeclaration> wasmSymbolReferenceGcType = this.context.referenceGcType(this.backendContext.getIrBuiltIns().getAnyClass());
            this.body.buildRefTestStatic(wasmSymbolReferenceGcType, sourceLocation);
            this.body.buildInstr(WasmOp.I32_EQZ, sourceLocation, new WasmImmediate[0]);
            this.body.buildBrIf(numberOfNestedBlocks3, sourceLocation);
            this.body.buildGetLocal(wasmLocalReferenceLocal3, sourceLocation);
            this.body.buildRefCastStatic(wasmSymbolReferenceGcType, sourceLocation);
            this.body.buildInstr(WasmOp.RETURN, sourceLocation, new WasmImmediate[0]);
            wasmExpressionBuilder3.buildEnd();
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getWasmArrayCopy())) {
            WasmModuleCodegenContext wasmModuleCodegenContext = this.context;
            IrType typeArgument7 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument7);
            WasmImmediate.GcType gcType = new WasmImmediate.GcType(wasmModuleCodegenContext.referenceGcType(TypeTransformerKt.getRuntimeClass(typeArgument7, this.irBuiltIns).getSymbol()));
            this.body.buildInstr(WasmOp.ARRAY_COPY, sourceLocation, gcType, gcType);
        } else if (Intrinsics.areEqual(symbol, this.wasmSymbols.getStringGetPoolSize())) {
            this.body.buildConstI32Symbol(this.context.getStringPoolSize(), sourceLocation);
        } else {
            if (!Intrinsics.areEqual(symbol, this.wasmSymbols.getWasmArrayNewData0())) {
                return false;
            }
            WasmModuleCodegenContext wasmModuleCodegenContext2 = this.context;
            IrType typeArgument8 = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument8);
            this.body.buildInstr(WasmOp.ARRAY_NEW_DATA, sourceLocation, new WasmImmediate.GcType(wasmModuleCodegenContext2.referenceGcType(TypeTransformerKt.getRuntimeClass(typeArgument8, this.irBuiltIns).getSymbol())), new WasmImmediate.DataIdx(0));
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        Iterator<T> it = body.getStatements().iterator();
        while (it.hasNext()) {
            generateStatement((IrStatement) it.next());
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitContainerExpression(IrContainerExpression expression) {
        String strAsString;
        Name name2;
        FqName fqNameWhenAvailable;
        IrMemberWithContainerSource irMemberWithContainerSource;
        Intrinsics.checkNotNullParameter(expression, "expression");
        List<IrStatement> statements = expression.getStatements();
        if (statements.isEmpty()) {
            if (Intrinsics.areEqual(expression.getType(), this.irBuiltIns.getUnitType())) {
                buildGetUnit(this.body);
                return;
            }
            return;
        }
        boolean z = expression instanceof IrReturnableBlock;
        int i = 0;
        if (z) {
            IrReturnableBlock irReturnableBlock = (IrReturnableBlock) expression;
            IrMemberWithContainerSource inlineFunction = IrInlineUtilsKt.getInlineFunction(irReturnableBlock.getSymbol().getOwner());
            String strAsString2 = null;
            IrSimpleFunction irSimpleFunction = inlineFunction instanceof IrSimpleFunction ? (IrSimpleFunction) inlineFunction : null;
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction != null ? irSimpleFunction.getCorrespondingPropertySymbol() : null;
            IrMemberWithContainerSource irMemberWithContainerSource2 = (correspondingPropertySymbol == null || (irMemberWithContainerSource = (IrProperty) correspondingPropertySymbol.getOwner()) == null) ? inlineFunction : irMemberWithContainerSource;
            if (irMemberWithContainerSource2 == null || (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irMemberWithContainerSource2)) == null || (strAsString = fqNameWhenAvailable.asString()) == null) {
                if (irMemberWithContainerSource2 != null && (name2 = ((IrPossiblyExternalDeclaration) irMemberWithContainerSource2).getName()) != null) {
                    strAsString2 = name2.asString();
                }
                strAsString = strAsString2 == null ? "<UNKNOWN>" : strAsString2;
            }
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("Inlined call of `" + strAsString + '`'));
            this.functionContext.defineNonLocalReturnLevel(irReturnableBlock.getSymbol(), this.body.buildBlock(this.context.transformBlockResultType(expression.getType())));
        }
        for (Object obj : statements) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (i != CollectionsKt.getLastIndex(statements)) {
                generateStatement(irStatement);
            } else if (irStatement instanceof IrExpression) {
                generateWithExpectedType$backend_wasm((IrExpression) irStatement, expression.getType());
            } else {
                generateStatement(irStatement);
                if (!Intrinsics.areEqual(expression.getType(), this.wasmSymbols.getVoidType())) {
                    buildGetUnit(this.body);
                }
            }
            i = i2;
        }
        if (z) {
            this.body.buildEnd();
            this.body.commentGroupEnd();
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreak(IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.areEqual(jump.getType(), this.irBuiltIns.getNothingType());
        this.body.buildBr(this.functionContext.referenceLoopLevel(jump.getLoop(), LoopLabelType.BREAK), getSourceLocation(jump));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitContinue(IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.areEqual(jump.getType(), this.irBuiltIns.getNothingType());
        this.body.buildBr(this.functionContext.referenceLoopLevel(jump.getLoop(), LoopLabelType.CONTINUE), getSourceLocation(jump));
    }

    private final void visitFunctionReturn(IrReturn expression) {
        IrType irTypeReturnType = org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.returnType(expression.getReturnTargetSymbol().getOwner(), this.backendContext);
        if (Intrinsics.areEqual(expression.getReturnTargetSymbol().getOwner(), getUnitGetInstance())) {
            generateExpression$backend_wasm(expression.getValue());
        } else if (Intrinsics.areEqual(irTypeReturnType, this.irBuiltIns.getUnitType())) {
            generateAsStatement(expression.getValue());
        } else {
            generateWithExpectedType$backend_wasm(expression.getValue(), irTypeReturnType);
        }
        if (this.functionContext.getIrFunction() instanceof IrConstructor) {
            this.body.buildGetLocal(this.functionContext.referenceLocal(0), SourceLocation.INSTANCE.NoLocation("Get implicit dispatch receiver"));
        }
        this.body.buildInstr(WasmOp.RETURN, getSourceLocation(expression), new WasmImmediate[0]);
    }

    public final void generateWithExpectedType$backend_wasm(IrExpression expression, IrType expectedType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        IrType type = expression.getType();
        if (Intrinsics.areEqual(expectedType, this.wasmSymbols.getVoidType())) {
            generateAsStatement(expression);
            return;
        }
        if (IrTypePredicatesKt.isUnit(expectedType) && !IrTypePredicatesKt.isUnit(type)) {
            generateAsStatement(expression);
            buildGetUnit(this.body);
        } else {
            generateExpression$backend_wasm(expression);
            recoverToExpectedType(type, expectedType, getSourceLocation(expression));
        }
    }

    private final void recoverToExpectedType(IrType actualType, IrType expectedType, SourceLocation location) {
        WasmHeapType.Simple.None none;
        if (IrTypePredicatesKt.isNothing(expectedType)) {
            LocationUtilsKt.buildUnreachableAfterNothingType(this.body);
            return;
        }
        if (IrTypePredicatesKt.isNothing(actualType)) {
            return;
        }
        if (IrTypePredicatesKt.isNullableNothing(actualType) && IrTypeUtilsKt.isNullable(expectedType)) {
            IrClass irClass = IrTypesKt.getClass(expectedType);
            if (irClass == null || !irClass.getIsExternal()) {
                return;
            }
            this.body.buildDrop(location);
            this.body.buildRefNull(WasmHeapType.Simple.NoExtern.INSTANCE, location);
            return;
        }
        if (IrTypeUtilsKt.isNullable(actualType) && IrTypePredicatesKt.isNullableNothing(expectedType)) {
            IrClass irClass2 = IrTypesKt.getClass(expectedType);
            if (irClass2 != null && irClass2.getIsExternal()) {
                none = WasmHeapType.Simple.NoExtern.INSTANCE;
            } else {
                none = WasmHeapType.Simple.None.INSTANCE;
            }
            this.body.buildInstr(WasmOp.REF_CAST_NULL, location, new WasmImmediate.HeapType(none));
            return;
        }
        IrClass runtimeClass = TypeTransformerKt.getRuntimeClass(expectedType, this.irBuiltIns);
        if (runtimeClass.getIsExternal()) {
            return;
        }
        IrClass runtimeClass2 = TypeTransformerKt.getRuntimeClass(actualType, this.irBuiltIns);
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(runtimeClass);
        IrSimpleType defaultType2 = IrUtilsKt.getDefaultType(runtimeClass2);
        if (Intrinsics.areEqual(defaultType, defaultType2)) {
            return;
        }
        IrSimpleType irSimpleType = defaultType;
        if (IrTypePredicatesKt.isNothing(irSimpleType) && !IrTypePredicatesKt.isNothing(defaultType2)) {
            LocationUtilsKt.buildUnreachableAfterNothingType(this.body);
            return;
        }
        if (IrUtilsKt.isSubclassOf(runtimeClass2, runtimeClass)) {
            return;
        }
        boolean z = IrTypePredicatesKt.isPrimitiveType$default(irSimpleType, false, 1, null) && !IrTypeUtilsKt.isNullable(expectedType);
        IrSimpleType irSimpleType2 = defaultType2;
        if (z != (IrTypePredicatesKt.isPrimitiveType$default(irSimpleType2, false, 1, null) && !IrTypeUtilsKt.isNullable(actualType))) {
            LocationUtilsKt.buildUnreachableForVerifier(this.body);
            return;
        }
        if (z) {
            return;
        }
        if (IrUtilsKt.isSubclassOf(runtimeClass, runtimeClass2)) {
            if (IrTypeUtilsKt.isNullable(expectedType)) {
                generateRefNullCast(irSimpleType2, irSimpleType, location);
            } else {
                generateRefCast(irSimpleType2, irSimpleType, location);
            }
            this.body.buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString("to make verifier happy"));
            return;
        }
        LocationUtilsKt.buildUnreachableForVerifier(this.body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrReturnTargetSymbol returnTargetSymbol = expression.getReturnTargetSymbol();
        IrReturnableBlockSymbol irReturnableBlockSymbol = returnTargetSymbol instanceof IrReturnableBlockSymbol ? (IrReturnableBlockSymbol) returnTargetSymbol : null;
        if (irReturnableBlockSymbol != null) {
            generateWithExpectedType$backend_wasm(expression.getValue(), irReturnableBlockSymbol.getOwner().getType());
            this.body.buildBr(this.functionContext.referenceNonLocalReturnLevel(irReturnableBlockSymbol), getSourceLocation(expression));
        } else {
            visitFunctionReturn(expression);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (OptimisedWhenGeneratorKt.tryGenerateOptimisedWhen(this, expression, this.context.getBackendContext().getWasmSymbols())) {
            return;
        }
        WasmType wasmTypeTransformBlockResultType = this.context.transformBlockResultType(expression.getType());
        Iterator<IrBranch> it = expression.getBranches().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                if (wasmTypeTransformBlockResultType == null) {
                    break;
                }
                Intrinsics.areEqual(expression.getType(), this.irBuiltIns.getNothingType());
                if (IrTypePredicatesKt.isUnit(expression.getType())) {
                    buildGetUnit(this.body);
                    break;
                }
                throw new IllegalStateException(("'When' without else branch and non Unit type: " + DumpKotlinLikeKt.dumpKotlinLike(expression.getType())).toString());
            }
            IrBranch next = it.next();
            if (!IrUtilsKt.isElseBranch(next)) {
                generateExpression$backend_wasm(next.getCondition());
                this.body.buildIf(null, wasmTypeTransformBlockResultType);
                generateWithExpectedType$backend_wasm(next.getResult(), expression.getType());
                this.body.buildElse();
                i++;
            } else {
                generateWithExpectedType$backend_wasm(next.getResult(), expression.getType());
                break;
            }
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.body.buildEnd();
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDoWhileLoop(IrDoWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        String label = loop.getLabel();
        WasmExpressionBuilder wasmExpressionBuilder = this.body;
        wasmExpressionBuilder.setNumberOfNestedBlocks(wasmExpressionBuilder.getNumberOfNestedBlocks() + 1);
        wasmExpressionBuilder.buildInstr(WasmOp.LOOP, SourceLocation.INSTANCE.NoLocation("LOOP"), new WasmImmediate.BlockType.Value(null));
        int numberOfNestedBlocks = wasmExpressionBuilder.getNumberOfNestedBlocks();
        WasmExpressionBuilder wasmExpressionBuilder2 = this.body;
        String str = "BREAK_" + label;
        wasmExpressionBuilder2.setNumberOfNestedBlocks(wasmExpressionBuilder2.getNumberOfNestedBlocks() + 1);
        wasmExpressionBuilder2.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(null));
        int numberOfNestedBlocks2 = wasmExpressionBuilder2.getNumberOfNestedBlocks();
        WasmExpressionBuilder wasmExpressionBuilder3 = this.body;
        String str2 = "CONTINUE_" + label;
        wasmExpressionBuilder3.setNumberOfNestedBlocks(wasmExpressionBuilder3.getNumberOfNestedBlocks() + 1);
        wasmExpressionBuilder3.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(null));
        int numberOfNestedBlocks3 = wasmExpressionBuilder3.getNumberOfNestedBlocks();
        IrDoWhileLoop irDoWhileLoop = loop;
        this.functionContext.defineLoopLevel(irDoWhileLoop, LoopLabelType.BREAK, numberOfNestedBlocks2);
        this.functionContext.defineLoopLevel(irDoWhileLoop, LoopLabelType.CONTINUE, numberOfNestedBlocks3);
        IrExpression body = loop.getBody();
        if (body != null) {
            generateAsStatement(body);
        }
        wasmExpressionBuilder3.buildEnd();
        generateExpression$backend_wasm(loop.getCondition());
        this.body.buildBrIf(numberOfNestedBlocks, getSourceLocation(loop.getCondition()));
        wasmExpressionBuilder2.buildEnd();
        wasmExpressionBuilder.buildEnd();
        buildGetUnit(this.body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhileLoop(IrWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        String label = loop.getLabel();
        WasmExpressionBuilder wasmExpressionBuilder = this.body;
        wasmExpressionBuilder.setNumberOfNestedBlocks(wasmExpressionBuilder.getNumberOfNestedBlocks() + 1);
        wasmExpressionBuilder.buildInstr(WasmOp.LOOP, SourceLocation.INSTANCE.NoLocation("LOOP"), new WasmImmediate.BlockType.Value(null));
        int numberOfNestedBlocks = wasmExpressionBuilder.getNumberOfNestedBlocks();
        WasmExpressionBuilder wasmExpressionBuilder2 = this.body;
        String str = "BREAK_" + label;
        wasmExpressionBuilder2.setNumberOfNestedBlocks(wasmExpressionBuilder2.getNumberOfNestedBlocks() + 1);
        wasmExpressionBuilder2.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(null));
        int numberOfNestedBlocks2 = wasmExpressionBuilder2.getNumberOfNestedBlocks();
        IrWhileLoop irWhileLoop = loop;
        this.functionContext.defineLoopLevel(irWhileLoop, LoopLabelType.BREAK, numberOfNestedBlocks2);
        this.functionContext.defineLoopLevel(irWhileLoop, LoopLabelType.CONTINUE, numberOfNestedBlocks);
        generateExpression$backend_wasm(loop.getCondition());
        SourceLocation sourceLocation = getSourceLocation(loop.getCondition());
        this.body.buildInstr(WasmOp.I32_EQZ, sourceLocation, new WasmImmediate[0]);
        this.body.buildBrIf(numberOfNestedBlocks2, sourceLocation);
        IrExpression body = loop.getBody();
        if (body != null) {
            generateAsStatement(body);
        }
        this.body.buildBr(numberOfNestedBlocks, SourceLocation.INSTANCE.NoLocation("Continue in the loop"));
        wasmExpressionBuilder2.buildEnd();
        wasmExpressionBuilder.buildEnd();
        buildGetUnit(this.body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.functionContext.defineLocal(declaration.getSymbol());
        if (declaration.getInitializer() == null) {
            return;
        }
        IrExpression initializer = declaration.getInitializer();
        Intrinsics.checkNotNull(initializer);
        generateExpression$backend_wasm(initializer);
        this.body.buildSetLocal(this.functionContext.referenceLocal(declaration.getSymbol()), getSourceLocation(declaration));
    }

    private final boolean tryToGenerateWasmOpIntrinsicCall(IrFunctionAccessExpression call, IrFunction function) {
        WasmImmediate.MemArg memArg;
        IrFunction irFunction = function;
        if (AnnotationsKt.hasWasmNoOpCastAnnotation(irFunction)) {
            return true;
        }
        String wasmOpAnnotation = AnnotationsKt.getWasmOpAnnotation(irFunction);
        if (wasmOpAnnotation == null) {
            return false;
        }
        SourceLocation sourceLocation = getSourceLocation(call);
        WasmOp wasmOpValueOf = WasmOp.valueOf(wasmOpAnnotation);
        int size = wasmOpValueOf.getImmediates().size();
        if (size == 0) {
            this.body.buildInstr(wasmOpValueOf, sourceLocation, new WasmImmediate[0]);
        } else if (size == 1) {
            WasmImmediate[] wasmImmediateArr = new WasmImmediate[1];
            WasmImmediateKind wasmImmediateKind = wasmOpValueOf.getImmediates().get(0);
            int i = WhenMappings.$EnumSwitchMapping$1[wasmImmediateKind.ordinal()];
            if (i == 1) {
                memArg = new WasmImmediate.MemArg(0, 0, null);
            } else if (i == 2) {
                memArg = new WasmImmediate.GcType(tryToGenerateWasmOpIntrinsicCall$getReferenceGcType(function, call, this));
            } else if (i == 3) {
                memArg = new WasmImmediate.HeapType(new WasmHeapType.Type(tryToGenerateWasmOpIntrinsicCall$getReferenceGcType(function, call, this)));
            } else if (i == 4) {
                memArg = new WasmImmediate.TypeIdx(tryToGenerateWasmOpIntrinsicCall$getReferenceGcType(function, call, this));
            } else if (i == 5) {
                memArg = new WasmImmediate.MemoryIdx(0);
            } else {
                throw new IllegalStateException(("Immediate " + wasmImmediateKind + " is unsupported").toString());
            }
            wasmImmediateArr[0] = memArg;
            this.body.buildInstr(wasmOpValueOf, sourceLocation, (WasmImmediate[]) Arrays.copyOf(wasmImmediateArr, 1));
        } else {
            throw new IllegalStateException(("Op " + wasmOpAnnotation + " is unsupported").toString());
        }
        return true;
    }

    private static final WasmSymbol<WasmTypeDeclaration> tryToGenerateWasmOpIntrinsicCall$getReferenceGcType(IrFunction irFunction, IrFunctionAccessExpression irFunctionAccessExpression, BodyGenerator bodyGenerator) {
        IrType typeArgument;
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null || (typeArgument = dispatchReceiverParameter.getType()) == null) {
            typeArgument = irFunctionAccessExpression.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument);
        }
        WasmModuleCodegenContext wasmModuleCodegenContext = bodyGenerator.context;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(typeArgument);
        Intrinsics.checkNotNull(classOrNull);
        return wasmModuleCodegenContext.referenceGcType(classOrNull);
    }

    private final SourceLocation getSourceLocation(IrElement irElement) {
        IrFile fileOrNull = IrUtilsKt.getFileOrNull(this.functionContext.getIrFunction());
        return LocationUtilsKt.getSourceLocation(irElement, fileOrNull != null ? fileOrNull.getFileEntry() : null);
    }
}
