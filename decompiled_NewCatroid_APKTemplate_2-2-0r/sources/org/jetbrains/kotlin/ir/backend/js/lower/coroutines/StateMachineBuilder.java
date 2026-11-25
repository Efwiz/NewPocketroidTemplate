package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.lower.FINALLY_EXPRESSION;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000â\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\f\u0012\u0006\u0010\u0010\u001a\u00020\f\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014\u0012\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00180\u0014¢\u0006\u0002\u0010\u0019J\b\u0010H\u001a\u00020IH\u0002J\u0010\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020\u0018H\u0002J\u0010\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020\u001fH\u0002J\b\u0010O\u001a\u00020\u001fH\u0002J\b\u0010P\u001a\u00020,H\u0002J\u0018\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\u00162\u0006\u0010T\u001a\u00020\u0015H\u0002J\b\u0010U\u001a\u00020BH\u0002J \u0010V\u001a\u00020I2\u0006\u0010W\u001a\u00020\u001f2\u0006\u0010X\u001a\u00020\u001f2\u0006\u0010Y\u001a\u00020\u0015H\u0002J\u0012\u0010Z\u001a\u00020I2\b\b\u0002\u0010[\u001a\u00020#H\u0002J\u001a\u0010\\\u001a\u00020I2\u0006\u0010N\u001a\u00020\u001f2\b\b\u0002\u0010]\u001a\u000204H\u0002J \u0010^\u001a\u00020I2\u0006\u0010N\u001a\u00020\u001f2\u0006\u0010[\u001a\u00020#2\u0006\u0010]\u001a\u000204H\u0002J\b\u0010_\u001a\u00020`H\u0002J\u0006\u0010a\u001a\u00020IJ\u0010\u0010b\u001a\u0002042\u0006\u0010c\u001a\u00020\u0016H\u0002J\u0018\u0010d\u001a\u00020R2\u0006\u0010S\u001a\u00020\u00162\u0006\u0010T\u001a\u00020\u0015H\u0002J\b\u0010e\u001a\u000204H\u0002J\b\u0010f\u001a\u00020\u0016H\u0002J\u0010\u0010g\u001a\u00020I2\u0006\u0010N\u001a\u00020\u001fH\u0002J\b\u0010h\u001a\u00020IH\u0002J\b\u0010i\u001a\u00020`H\u0002J\u0010\u0010j\u001a\u00020I2\u0006\u0010k\u001a\u00020\nH\u0002J\u0010\u0010l\u001a\u00020I2\u0006\u0010N\u001a\u00020\u001fH\u0002J\u001a\u0010m\u001a\u0002022\u0006\u0010n\u001a\u00020\u00152\b\b\u0002\u0010o\u001a\u00020pH\u0002J%\u0010q\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160r2\u000e\u0010s\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160rH\u0002¢\u0006\u0002\u0010tJ\u001c\u0010u\u001a\u00020I2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00180\u0014H\u0002J0\u0010w\u001a\u00020I2\u0006\u0010k\u001a\u00020\n2\u001e\u0010v\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020I0xH\u0002J\u0010\u0010y\u001a\u00020I2\u0006\u0010h\u001a\u00020\u001fH\u0002J\u0010\u0010z\u001a\u00020I2\u0006\u0010{\u001a\u00020|H\u0016J\u0010\u0010}\u001a\u00020I2\u0006\u0010c\u001a\u00020`H\u0016J\u0010\u0010~\u001a\u00020I2\u0006\u0010c\u001a\u00020\u007fH\u0016J\u0012\u0010\u0080\u0001\u001a\u00020I2\u0007\u0010{\u001a\u00030\u0081\u0001H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020I2\u0007\u0010k\u001a\u00030\u0083\u0001H\u0016J\u001e\u0010\u0084\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0085\u00012\n\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001H\u0016J\u0012\u0010\u0088\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0089\u0001H\u0016J\u0012\u0010\u008a\u0001\u001a\u00020I2\u0007\u0010\u008b\u0001\u001a\u00020\u0004H\u0016J\u0012\u0010\u008c\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u008d\u0001H\u0016J\u0012\u0010\u008e\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u008f\u0001H\u0016J\u0016\u0010\u0090\u0001\u001a\u00020I2\u000b\u0010c\u001a\u0007\u0012\u0002\b\u00030\u0091\u0001H\u0016J\u0012\u0010\u0092\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0093\u0001H\u0016J\u0012\u0010\u0094\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0095\u0001H\u0016J\u0012\u0010\u0096\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0097\u0001H\u0016J\u0012\u0010\u0098\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u0099\u0001H\u0016J\u0012\u0010\u009a\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030\u009b\u0001H\u0016J\u0013\u0010\u009c\u0001\u001a\u00020I2\b\u0010\u009d\u0001\u001a\u00030\u009e\u0001H\u0016J\u0012\u0010\u009f\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030 \u0001H\u0016J\u0012\u0010¡\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030¢\u0001H\u0016J\u0012\u0010£\u0001\u001a\u00020I2\u0007\u0010¤\u0001\u001a\u000202H\u0016J\u0012\u0010¥\u0001\u001a\u00020I2\u0007\u0010c\u001a\u00030¦\u0001H\u0016J\u0012\u0010§\u0001\u001a\u00020I2\u0007\u0010k\u001a\u00030¨\u0001H\u0016J\u001c\u0010©\u0001\u001a\u00030ª\u00012\u0006\u0010c\u001a\u00020\u00162\b\u0010«\u0001\u001a\u00030¬\u0001H\u0002J\u0014\u0010J\u001a\u00020I*\u00020#2\u0006\u0010K\u001a\u00020\u0018H\u0002R\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R*\u00105\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020706j\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u000207`8X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010:\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b;\u0010'R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00180\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010<\u001a\u00020=8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010A\u001a\u001e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020B06j\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020B`8X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010D\u001a\u00020E8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bF\u0010G¨\u0006\u00ad\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/StateMachineBuilder;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "suspendableNodes", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "function", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "rootLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "exceptionSymbolGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "exceptionSymbolSetter", "exStateSymbolGetter", "exStateSymbolSetter", "stateSymbolSetter", "thisSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getSuspendResultAsType", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setSuspendResultValue", "Lorg/jetbrains/kotlin/ir/IrStatement;", "(Ljava/util/Set;Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "anyN", "booleanNotSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "catchBlockStack", "", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "currentBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "currentState", "entryState", "getEntryState", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "eqeqeqSymbol", "getFunction", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "globalCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "getGlobalCatch", "()Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "setGlobalCatch", "(Lorg/jetbrains/kotlin/ir/expressions/IrCatch;)V", "globalExceptionVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "hasExceptions", "", "loopMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/LoopBounds;", "Lkotlin/collections/HashMap;", "nothing", "rootExceptionTrap", "getRootExceptionTrap", "thisReceiver", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "getThisReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "tryLoopStack", "tryStateMap", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/TryState;", "unit", "unitValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "getUnitValue", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "addExceptionEdge", "", "addStatement", "statement", "buildDispatchBlock", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCompositeImpl;", "target", "buildExceptionTrapState", "buildGlobalCatch", "buildIsCheck", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTypeOperatorCallImpl;", "value", "toType", "buildTryState", "buildUnboxingState", "unboxState", "continueState", "expectedType", "doContinue", "block", "doDispatch", "andContinue", "doDispatchImpl", "exceptionState", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "finalizeStateMachine", "hasResultingValue", "expression", "implicitCast", "isBlockEnded", "lastExpression", "maybeDoDispatch", "newState", "pendingException", "resetExceptionStateIfNeeded", "loop", "setupExceptionState", "tempVar", ModuleXmlParser.TYPE, "name", "", "transformArguments", "", "arguments", "([Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "transformLastExpression", "transformer", "transformLoop", "Lkotlin/Function3;", "updateState", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitCall", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDoWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "data", "", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "declaration", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "wrap", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrSetValueImpl;", "variable", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StateMachineBuilder implements IrElementVisitorVoid {
    private final IrType anyN;
    private final IrSimpleFunctionSymbol booleanNotSymbol;
    private final List<SuspendState> catchBlockStack;
    private final JsCommonBackendContext context;
    private IrContainerExpression currentBlock;
    private SuspendState currentState;
    private final SuspendState entryState;
    private final IrSimpleFunctionSymbol eqeqeqSymbol;
    private final IrSimpleFunction exStateSymbolGetter;
    private final IrSimpleFunction exStateSymbolSetter;
    private final IrSimpleFunction exceptionSymbolGetter;
    private final IrSimpleFunction exceptionSymbolSetter;
    private final IrFunctionSymbol function;
    private final Function1<IrType, IrExpression> getSuspendResultAsType;
    public IrCatch globalCatch;
    private final IrVariable globalExceptionVar;
    private boolean hasExceptions;
    private final HashMap<IrLoop, LoopBounds> loopMap;
    private final IrType nothing;
    private final SuspendState rootExceptionTrap;
    private final IrLoop rootLoop;
    private final Function1<IrExpression, IrStatement> setSuspendResultValue;
    private final IrSimpleFunction stateSymbolSetter;
    private final Set<IrElement> suspendableNodes;
    private final IrValueParameterSymbol thisSymbol;
    private final List<IrExpression> tryLoopStack;
    private final HashMap<IrExpression, TryState> tryStateMap;
    private final IrType unit;

    /* JADX WARN: Multi-variable type inference failed */
    public StateMachineBuilder(Set<IrElement> suspendableNodes, JsCommonBackendContext context, IrFunctionSymbol function, IrLoop rootLoop, IrSimpleFunction exceptionSymbolGetter, IrSimpleFunction exceptionSymbolSetter, IrSimpleFunction exStateSymbolGetter, IrSimpleFunction exStateSymbolSetter, IrSimpleFunction stateSymbolSetter, IrValueParameterSymbol thisSymbol, Function1<? super IrType, ? extends IrExpression> getSuspendResultAsType, Function1<? super IrExpression, ? extends IrStatement> setSuspendResultValue) {
        Intrinsics.checkNotNullParameter(suspendableNodes, "suspendableNodes");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(rootLoop, "rootLoop");
        Intrinsics.checkNotNullParameter(exceptionSymbolGetter, "exceptionSymbolGetter");
        Intrinsics.checkNotNullParameter(exceptionSymbolSetter, "exceptionSymbolSetter");
        Intrinsics.checkNotNullParameter(exStateSymbolGetter, "exStateSymbolGetter");
        Intrinsics.checkNotNullParameter(exStateSymbolSetter, "exStateSymbolSetter");
        Intrinsics.checkNotNullParameter(stateSymbolSetter, "stateSymbolSetter");
        Intrinsics.checkNotNullParameter(thisSymbol, "thisSymbol");
        Intrinsics.checkNotNullParameter(getSuspendResultAsType, "getSuspendResultAsType");
        Intrinsics.checkNotNullParameter(setSuspendResultValue, "setSuspendResultValue");
        this.suspendableNodes = suspendableNodes;
        this.context = context;
        this.function = function;
        this.rootLoop = rootLoop;
        this.exceptionSymbolGetter = exceptionSymbolGetter;
        this.exceptionSymbolSetter = exceptionSymbolSetter;
        this.exStateSymbolGetter = exStateSymbolGetter;
        this.exStateSymbolSetter = exStateSymbolSetter;
        this.stateSymbolSetter = stateSymbolSetter;
        this.thisSymbol = thisSymbol;
        this.getSuspendResultAsType = getSuspendResultAsType;
        this.setSuspendResultValue = setSuspendResultValue;
        this.loopMap = new HashMap<>();
        IrType unitType = context.getIrBuiltIns().getUnitType();
        this.unit = unitType;
        this.anyN = context.getIrBuiltIns().getAnyNType();
        this.nothing = context.getIrBuiltIns().getNothingType();
        this.booleanNotSymbol = context.getIrBuiltIns().getBooleanNotSymbol();
        this.eqeqeqSymbol = context.getIrBuiltIns().getEqeqeqSymbol();
        SuspendState suspendState = new SuspendState(unitType);
        this.entryState = suspendState;
        SuspendState suspendStateBuildExceptionTrapState = buildExceptionTrapState();
        this.rootExceptionTrap = suspendStateBuildExceptionTrapState;
        this.globalExceptionVar = JsIrBuilder.INSTANCE.buildVar(exceptionSymbolGetter.getReturnType(), function.getOwner(), (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : "e", (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : null, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        this.currentState = suspendState;
        this.currentBlock = suspendState.getEntryBlock();
        this.catchBlockStack = CollectionsKt.mutableListOf(suspendStateBuildExceptionTrapState);
        this.tryStateMap = new HashMap<>();
        this.tryLoopStack = new ArrayList();
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
    public void visitBlockBody(IrBlockBody irBlockBody) {
        IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
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
    public void visitComposite(IrComposite irComposite, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        visitConst((IrConst<?>) irConst, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConst(IrConst<?> irConst) {
        IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
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
    public void visitConstructorCall(IrConstructorCall irConstructorCall) {
        IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
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
    public void visitContainerExpression(IrContainerExpression irContainerExpression) {
        IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
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
    public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
        IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
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

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
        visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
        return Unit.INSTANCE;
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
    public void visitGetValue(IrGetValue irGetValue) {
        IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
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

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    public final IrFunctionSymbol getFunction() {
        return this.function;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrGetValueImpl getThisReceiver() {
        return JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, this.thisSymbol, null, 2, null);
    }

    public final SuspendState getEntryState() {
        return this.entryState;
    }

    public final SuspendState getRootExceptionTrap() {
        return this.rootExceptionTrap;
    }

    public final IrCatch getGlobalCatch() {
        IrCatch irCatch = this.globalCatch;
        if (irCatch != null) {
            return irCatch;
        }
        Intrinsics.throwUninitializedPropertyAccessException("globalCatch");
        return null;
    }

    public final void setGlobalCatch(IrCatch irCatch) {
        Intrinsics.checkNotNullParameter(irCatch, "<set-?>");
        this.globalCatch = irCatch;
    }

    public final void finalizeStateMachine() {
        setGlobalCatch(buildGlobalCatch());
        if (!(CollectionsKt.lastOrNull((List) this.currentBlock.getStatements()) instanceof IrReturn)) {
            addStatement(new IrReturnImpl(IrUtilsKt.getPreviousOffset(this.rootLoop.getEndOffset()), IrUtilsKt.getPreviousOffset(this.rootLoop.getEndOffset()), this.nothing, this.function, getUnitValue()));
        }
        if (this.hasExceptions) {
            return;
        }
        this.entryState.getSuccessors().add(this.rootExceptionTrap);
    }

    private final IrCatch buildGlobalCatch() {
        IrVariable irVariable = this.globalExceptionVar;
        IrVariableSymbol symbol = irVariable.getSymbol();
        IrBlockImpl irBlockImplBuildBlock = JsIrBuilder.INSTANCE.buildBlock(this.unit);
        if (this.hasExceptions) {
            IrBlockImpl irBlockImplBuildBlock2 = JsIrBuilder.INSTANCE.buildBlock(this.unit);
            IrBlockImpl irBlockImplBuildBlock3 = JsIrBuilder.INSTANCE.buildBlock(this.unit);
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.eqeqeqSymbol, null, null, null, null, 30, null);
            irCallBuildCall$default.putValueArgument(0, exceptionState());
            irCallBuildCall$default.putValueArgument(1, new IrDispatchPoint(this.rootExceptionTrap));
            irBlockImplBuildBlock.getStatements().add(JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, this.unit, irCallBuildCall$default, irBlockImplBuildBlock2, irBlockImplBuildBlock3, 0, 0, 0, 0, 240, null));
            IrVariableSymbol irVariableSymbol = symbol;
            irBlockImplBuildBlock2.getStatements().add(JsIrBuilder.INSTANCE.buildThrow(this.nothing, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableSymbol, null, 2, null)));
            List<IrStatement> statements = irBlockImplBuildBlock3.getStatements();
            IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.stateSymbolSetter.getSymbol(), this.unit, null, null, null, 28, null);
            irCallBuildCall$default2.setDispatchReceiver(getThisReceiver());
            irCallBuildCall$default2.putValueArgument(0, exceptionState());
            statements.add(irCallBuildCall$default2);
            List<IrStatement> statements2 = irBlockImplBuildBlock3.getStatements();
            IrCall irCallBuildCall$default3 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.exceptionSymbolSetter.getSymbol(), this.unit, null, null, null, 28, null);
            irCallBuildCall$default3.setDispatchReceiver(getThisReceiver());
            irCallBuildCall$default3.putValueArgument(0, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableSymbol, null, 2, null));
            statements2.add(irCallBuildCall$default3);
        } else {
            irBlockImplBuildBlock.getStatements().add(JsIrBuilder.INSTANCE.buildThrow(this.nothing, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null)));
        }
        return JsIrBuilder.INSTANCE.buildCatch(irVariable, irBlockImplBuildBlock);
    }

    private final SuspendState buildExceptionTrapState() {
        SuspendState suspendState = new SuspendState(this.unit);
        suspendState.getEntryBlock().getStatements().add(JsIrBuilder.INSTANCE.buildThrow(this.nothing, pendingException()));
        return suspendState;
    }

    private final void newState() {
        SuspendState suspendState = new SuspendState(this.unit);
        doDispatch$default(this, suspendState, false, 2, null);
        updateState(suspendState);
    }

    private final void updateState(SuspendState newState) {
        this.currentState = newState;
        this.currentBlock = newState.getEntryBlock();
    }

    private final IrExpression lastExpression() {
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) this.currentBlock.getStatements());
        IrExpression irExpression = objLastOrNull instanceof IrExpression ? (IrExpression) objLastOrNull : null;
        return irExpression == null ? getUnitValue() : irExpression;
    }

    private final void addStatement(IrContainerExpression irContainerExpression, IrStatement irStatement) {
        irContainerExpression.getStatements().add(irStatement);
    }

    private final void addStatement(IrStatement statement) {
        addStatement(this.currentBlock, statement);
    }

    private final boolean isBlockEnded() {
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) this.currentBlock.getStatements());
        IrExpression irExpression = objLastOrNull instanceof IrExpression ? (IrExpression) objLastOrNull : null;
        if (irExpression == null) {
            return false;
        }
        return IrTypePredicatesKt.isNothing(irExpression.getType());
    }

    private final void maybeDoDispatch(SuspendState target) {
        if (isBlockEnded()) {
            return;
        }
        doDispatch$default(this, target, false, 2, null);
    }

    private final void doDispatch(SuspendState target, boolean andContinue) {
        doDispatchImpl(target, this.currentBlock, andContinue);
    }

    static /* synthetic */ void doDispatch$default(StateMachineBuilder stateMachineBuilder, SuspendState suspendState, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        stateMachineBuilder.doDispatch(suspendState, z);
    }

    private final void doDispatchImpl(SuspendState target, IrContainerExpression block, boolean andContinue) {
        IrDispatchPoint irDispatchPoint = new IrDispatchPoint(target);
        this.currentState.getSuccessors().add(target);
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.stateSymbolSetter.getSymbol(), this.unit, null, null, null, 28, null);
        irCallBuildCall$default.setDispatchReceiver(getThisReceiver());
        irCallBuildCall$default.putValueArgument(0, irDispatchPoint);
        Unit unit = Unit.INSTANCE;
        addStatement(block, irCallBuildCall$default);
        if (andContinue) {
            doContinue(block);
        }
    }

    static /* synthetic */ void doContinue$default(StateMachineBuilder stateMachineBuilder, IrContainerExpression irContainerExpression, int i, Object obj) {
        if ((i & 1) != 0) {
            irContainerExpression = stateMachineBuilder.currentBlock;
        }
        stateMachineBuilder.doContinue(irContainerExpression);
    }

    private final void doContinue(IrContainerExpression block) {
        addStatement(block, JsIrBuilder.INSTANCE.buildContinue(this.nothing, this.rootLoop));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void transformLastExpression(Function1<? super IrExpression, ? extends IrStatement> transformer) {
        IrStatement irStatementInvoke = transformer.invoke(lastExpression());
        List<IrStatement> statements = this.currentBlock.getStatements();
        List<IrStatement> list = statements;
        if (list.isEmpty()) {
            list.add(irStatementInvoke);
        } else {
            statements.set(CollectionsKt.getLastIndex(statements), irStatementInvoke);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCompositeImpl buildDispatchBlock(SuspendState target) {
        IrCompositeImpl irCompositeImplBuildComposite$default = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, this.unit, null, 2, null);
        doDispatchImpl(target, irCompositeImplBuildComposite$default, true);
        return irCompositeImplBuildComposite$default;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6335visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (this.suspendableNodes.contains(element)) {
            IrVisitorsKt.acceptChildrenVoid(element, this);
        } else {
            addStatement((IrStatement) element);
        }
    }

    private final void transformLoop(IrLoop loop, Function3<? super IrLoop, ? super SuspendState, ? super SuspendState, Unit> transformer) {
        if (!this.suspendableNodes.contains(loop)) {
            addStatement(loop);
            return;
        }
        newState();
        SuspendState suspendState = this.currentState;
        SuspendState suspendState2 = new SuspendState(this.unit);
        this.loopMap.put(loop, new LoopBounds(suspendState, suspendState2));
        UtilsKt.push(this.tryLoopStack, loop);
        transformer.invoke(loop, suspendState, suspendState2);
        this.loopMap.remove(loop);
        updateState(suspendState2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhileLoop(IrWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        transformLoop(loop, new Function3<IrLoop, SuspendState, SuspendState, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitWhileLoop.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(IrLoop irLoop, SuspendState suspendState, SuspendState suspendState2) {
                invoke2(irLoop, suspendState, suspendState2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrLoop l, SuspendState head, final SuspendState exit) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(head, "head");
                Intrinsics.checkNotNullParameter(exit, "exit");
                IrVisitorsKt.acceptVoid(l.getCondition(), StateMachineBuilder.this);
                StateMachineBuilder stateMachineBuilder = StateMachineBuilder.this;
                final StateMachineBuilder stateMachineBuilder2 = StateMachineBuilder.this;
                stateMachineBuilder.transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitWhileLoop.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrStatement invoke(IrExpression it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, stateMachineBuilder2.booleanNotSymbol, null, null, null, null, 30, null);
                        irCallBuildCall$default.setDispatchReceiver(it);
                        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, stateMachineBuilder2.unit, irCallBuildCall$default, stateMachineBuilder2.buildDispatchBlock(exit), null, 0, 0, 0, 0, 248, null);
                    }
                });
                IrExpression body = l.getBody();
                if (body != null) {
                    IrVisitorsKt.acceptVoid(body, StateMachineBuilder.this);
                }
                StateMachineBuilder.doDispatch$default(StateMachineBuilder.this, head, false, 2, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDoWhileLoop(IrDoWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        transformLoop(loop, new Function3<IrLoop, SuspendState, SuspendState, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitDoWhileLoop.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(IrLoop irLoop, SuspendState suspendState, SuspendState suspendState2) {
                invoke2(irLoop, suspendState, suspendState2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrLoop l, final SuspendState head, SuspendState exit) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(head, "head");
                Intrinsics.checkNotNullParameter(exit, "exit");
                IrExpression body = l.getBody();
                if (body != null) {
                    IrVisitorsKt.acceptVoid(body, StateMachineBuilder.this);
                }
                IrVisitorsKt.acceptVoid(l.getCondition(), StateMachineBuilder.this);
                StateMachineBuilder stateMachineBuilder = StateMachineBuilder.this;
                final StateMachineBuilder stateMachineBuilder2 = StateMachineBuilder.this;
                stateMachineBuilder.transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitDoWhileLoop.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrStatement invoke(IrExpression it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, stateMachineBuilder2.unit, it, stateMachineBuilder2.buildDispatchBlock(head), null, 0, 0, 0, 0, 248, null);
                    }
                });
                StateMachineBuilder.doDispatch$default(StateMachineBuilder.this, exit, false, 2, null);
            }
        });
    }

    private final IrTypeOperatorCallImpl implicitCast(IrExpression value, IrType toType) {
        return JsIrBuilder.INSTANCE.buildImplicitCast(value, toType);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitCall(this, expression);
        if (IrUtilsKt.isSuspend(expression)) {
            IrExpression irExpressionLastExpression = lastExpression();
            IrType returnType = expression.getSymbol().getOwner().getReturnType();
            boolean z = this.context.getInlineClassesUtils().getInlinedClass(returnType) != null;
            SuspendState suspendState = new SuspendState(this.unit);
            SuspendState suspendState2 = z ? new SuspendState(this.unit) : null;
            final IrDispatchPoint irDispatchPoint = new IrDispatchPoint(suspendState2 == null ? suspendState : suspendState2);
            if (suspendState2 != null) {
                this.currentState.getSuccessors().add(suspendState2);
            }
            this.currentState.getSuccessors().add(suspendState);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitCall.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, StateMachineBuilder.this.stateSymbolSetter.getSymbol(), StateMachineBuilder.this.unit, null, null, null, 28, null);
                    StateMachineBuilder stateMachineBuilder = StateMachineBuilder.this;
                    IrDispatchPoint irDispatchPoint2 = irDispatchPoint;
                    irCallBuildCall$default.setDispatchReceiver(stateMachineBuilder.getThisReceiver());
                    irCallBuildCall$default.putValueArgument(0, irDispatchPoint2);
                    return irCallBuildCall$default;
                }
            });
            addStatement(this.setSuspendResultValue.invoke(irExpressionLastExpression));
            IrReturnImpl irReturnImplBuildReturn = JsIrBuilder.INSTANCE.buildReturn(this.function, this.getSuspendResultAsType.invoke(this.anyN), this.nothing);
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.eqeqeqSymbol, null, null, null, null, 30, null);
            irCallBuildCall$default.putValueArgument(0, this.getSuspendResultAsType.invoke(this.anyN));
            irCallBuildCall$default.putValueArgument(1, JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIr().getSymbols().getCoroutineSuspendedGetter(), null, null, null, null, 30, null));
            addStatement(JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, this.unit, irCallBuildCall$default, JsIrBuilder.INSTANCE.buildBlock(this.unit, CollectionsKt.listOf(irReturnImplBuildReturn)), null, 0, 0, 0, 0, 248, null));
            if (z) {
                IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.stateSymbolSetter.getSymbol(), this.unit, null, null, null, 28, null);
                irCallBuildCall$default2.setDispatchReceiver(getThisReceiver());
                irCallBuildCall$default2.putValueArgument(0, new IrDispatchPoint(suspendState));
                addStatement(irCallBuildCall$default2);
            }
            doContinue$default(this, null, 1, null);
            if (suspendState2 != null) {
                buildUnboxingState(suspendState2, suspendState, returnType);
            }
            updateState(suspendState);
            addStatement(this.getSuspendResultAsType.invoke(expression.getType()));
        }
    }

    private final void buildUnboxingState(SuspendState unboxState, SuspendState continueState, IrType expectedType) {
        unboxState.getSuccessors().add(continueState);
        updateState(unboxState);
        IrVariable irVariableBuildVar = JsIrBuilder.INSTANCE.buildVar(expectedType, this.function.getOwner(), (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : "unboxed", (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : this.getSuspendResultAsType.invoke(this.anyN), (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        addStatement(irVariableBuildVar);
        addStatement(this.setSuspendResultValue.invoke(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null)));
        doDispatch$default(this, continueState, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreak(IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        LoopBounds loopBounds = this.loopMap.get(jump.getLoop());
        Intrinsics.checkNotNull(loopBounds);
        SuspendState exitState = loopBounds.getExitState();
        resetExceptionStateIfNeeded(jump.getLoop());
        doDispatch$default(this, exitState, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitContinue(IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        LoopBounds loopBounds = this.loopMap.get(jump.getLoop());
        Intrinsics.checkNotNull(loopBounds);
        SuspendState headState = loopBounds.getHeadState();
        resetExceptionStateIfNeeded(jump.getLoop());
        doDispatch$default(this, headState, false, 2, null);
    }

    private final void resetExceptionStateIfNeeded(IrLoop loop) {
        IrExpression irExpression;
        SuspendState catchState;
        Iterator it = CollectionsKt.asReversedMutable(this.tryLoopStack).iterator();
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                irExpression = null;
                break;
            }
            irExpression = (IrExpression) it.next();
            boolean z3 = irExpression instanceof IrTry;
            if (z3) {
                z2 = !z;
            }
            if (irExpression == loop) {
                z = true;
            }
            if (z && z3) {
                break;
            }
        }
        if (z2) {
            TryState tryState = this.tryStateMap.get(irExpression);
            if (tryState == null || (catchState = tryState.getCatchState()) == null) {
                catchState = this.rootExceptionTrap;
            }
            setupExceptionState(catchState);
        }
    }

    private final IrSetValueImpl wrap(IrExpression expression, IrVariableSymbol variable) {
        return JsIrBuilder.INSTANCE.buildSetVariable(variable, expression, this.unit);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitComposite(IrComposite expression) {
        SuspendState suspendState;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (Intrinsics.areEqual(expression.getOrigin(), FINALLY_EXPRESSION.INSTANCE) && (suspendState = (SuspendState) UtilsKt.peek(this.catchBlockStack)) != null) {
            setupExceptionState(suspendState);
        }
        IrElementVisitorVoid.DefaultImpls.visitComposite(this, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhen(IrWhen expression) {
        ArrayList branches;
        IrVariableSymbol symbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
            return;
        }
        SuspendState suspendState = new SuspendState(expression.getType());
        if (hasResultingValue(expression)) {
            IrVariable irVariableTempVar = tempVar(expression.getType(), "WHEN_RESULT");
            symbol = irVariableTempVar.getSymbol();
            addStatement(irVariableTempVar);
            List<IrBranch> branches2 = expression.getBranches();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(branches2, 10));
            for (IrBranch irBranch : branches2) {
                IrSetValueImpl irSetValueImplWrap = wrap(irBranch.getResult(), symbol);
                if (this.suspendableNodes.contains(irBranch.getResult())) {
                    this.suspendableNodes.add(irSetValueImplWrap);
                }
                arrayList.add(IrUtilsKt.isElseBranch(irBranch) ? new IrElseBranchImpl(irBranch.getStartOffset(), irBranch.getEndOffset(), irBranch.getCondition(), irSetValueImplWrap) : new IrBranchImpl(irBranch.getStartOffset(), irBranch.getEndOffset(), irBranch.getCondition(), irSetValueImplWrap));
            }
            branches = arrayList;
        } else {
            branches = expression.getBranches();
            symbol = null;
        }
        for (IrBranch irBranch2 : branches) {
            if (!IrUtilsKt.isElseBranch(irBranch2)) {
                StateMachineBuilder stateMachineBuilder = this;
                IrVisitorsKt.acceptVoid(irBranch2.getCondition(), stateMachineBuilder);
                final IrCompositeImpl irCompositeImplBuildComposite$default = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, irBranch2.getResult().getType(), null, 2, null);
                final IrCompositeImpl irCompositeImplBuildComposite$default2 = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, expression.getType(), null, 2, null);
                SuspendState suspendState2 = this.currentState;
                transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitWhen.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrStatement invoke(IrExpression it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, StateMachineBuilder.this.unit, it, irCompositeImplBuildComposite$default, irCompositeImplBuildComposite$default2, 0, 0, 0, 0, 240, null);
                    }
                });
                this.currentBlock = irCompositeImplBuildComposite$default;
                IrVisitorsKt.acceptVoid(irBranch2.getResult(), stateMachineBuilder);
                if (!isBlockEnded()) {
                    doDispatch$default(this, suspendState, false, 2, null);
                }
                this.currentState = suspendState2;
                this.currentBlock = irCompositeImplBuildComposite$default2;
            } else {
                IrVisitorsKt.acceptVoid(irBranch2.getResult(), this);
                if (!isBlockEnded()) {
                    doDispatch$default(this, suspendState, false, 2, null);
                    break;
                }
                break;
            }
        }
        maybeDoDispatch(suspendState);
        updateState(suspendState);
        if (symbol != null) {
            addStatement(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null));
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetValue(final IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
        } else {
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitSetValue.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrSetValue irSetValue = expression;
                    irSetValue.setValue(it);
                    return irSetValue;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeOperator(final IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
        } else {
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitTypeOperator.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrTypeOperatorCall irTypeOperatorCall = expression;
                    irTypeOperatorCall.setArgument(it);
                    return irTypeOperatorCall;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVariable(final IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.suspendableNodes.contains(declaration)) {
            addStatement(declaration);
        } else {
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitVariable.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrVariable irVariable = declaration;
                    irVariable.setInitializer(it);
                    return irVariable;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetField(final IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
        } else {
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitGetField.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrGetField irGetField = expression;
                    irGetField.setReceiver(it);
                    return irGetField;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicMemberExpression(final IrDynamicMemberExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
        } else {
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitDynamicMemberExpression.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrDynamicMemberExpression irDynamicMemberExpression = expression;
                    irDynamicMemberExpression.setReceiver(it);
                    return irDynamicMemberExpression;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDynamicOperatorExpression(IrDynamicOperatorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
            return;
        }
        IrExpression[] irExpressionArrTransformArguments = transformArguments((IrExpression[]) expression.getArguments().toArray(new IrExpression[0]));
        int size = expression.getArguments().size();
        for (int i = 0; i < size; i++) {
            List<IrExpression> arguments = expression.getArguments();
            IrExpression irExpression = irExpressionArrTransformArguments[i];
            Intrinsics.checkNotNull(irExpression);
            arguments.set(i, irExpression);
        }
        addStatement(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetClass(final IrGetClass expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
        } else {
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitGetClass.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrStatement invoke(IrExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrGetClass irGetClass = expression;
                    irGetClass.setArgument(it);
                    return irGetClass;
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVararg(IrVararg expression) {
        IrElement irSpreadElementImpl;
        IrExpression expression2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<IrVarargElement> elements = expression.getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        int i = 0;
        int i2 = 0;
        for (Object obj : elements) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrVarargElement irVarargElement = (IrVarargElement) obj;
            if (irVarargElement instanceof IrSpreadElement) {
                linkedHashSet.add(Integer.valueOf(i2));
                expression2 = ((IrSpreadElement) irVarargElement).getExpression();
            } else {
                Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                expression2 = (IrExpression) irVarargElement;
            }
            arrayList.add(expression2);
            i2 = i3;
        }
        IrExpression[] irExpressionArrTransformArguments = transformArguments((IrExpression[]) arrayList.toArray(new IrExpression[0]));
        ArrayList arrayList2 = new ArrayList(irExpressionArrTransformArguments.length);
        int length = irExpressionArrTransformArguments.length;
        int i4 = 0;
        while (i < length) {
            IrExpression irExpression = irExpressionArrTransformArguments[i];
            int i5 = i4 + 1;
            if (irExpression == null) {
                throw new IllegalArgumentException("Required value was null.".toString());
            }
            if (linkedHashSet.contains(Integer.valueOf(i4))) {
                irSpreadElementImpl = new IrSpreadElementImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression);
            } else {
                irSpreadElementImpl = irExpression;
            }
            arrayList2.add(irSpreadElementImpl);
            i++;
            i4 = i5;
        }
        addStatement(new IrVarargImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getVarargElementType(), CollectionsKt.toList(arrayList2)));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitMemberAccess(IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addExceptionEdge();
            addStatement(expression);
            return;
        }
        IrExpression[] irExpressionArr = new IrExpression[expression.getValueArgumentsCount() + 2];
        irExpressionArr[0] = expression.getDispatchReceiver();
        irExpressionArr[1] = expression.getExtensionReceiver();
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            irExpressionArr[i + 2] = expression.getValueArgument(i);
        }
        IrExpression[] irExpressionArrTransformArguments = transformArguments(irExpressionArr);
        expression.setDispatchReceiver(irExpressionArrTransformArguments[0]);
        expression.setExtensionReceiver(irExpressionArrTransformArguments[1]);
        int valueArgumentsCount2 = expression.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount2; i2++) {
            expression.putValueArgument(i2, irExpressionArrTransformArguments[i2 + 2]);
        }
        addExceptionEdge();
        addStatement(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
            return;
        }
        IrExpression[] irExpressionArrTransformArguments = transformArguments(new IrExpression[]{expression.getReceiver(), expression.getValue()});
        IrExpression irExpression = irExpressionArrTransformArguments[0];
        IrExpression irExpression2 = irExpressionArrTransformArguments[1];
        Intrinsics.checkNotNull(irExpression2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        addStatement(new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getSymbol(), irExpression, irExpression2, this.unit, expression.getOrigin(), expression.getSuperQualifierSymbol()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.suspendableNodes.contains(expression)) {
            addStatement(expression);
            return;
        }
        IrExpression[] irExpressionArr = new IrExpression[expression.getArguments().size()];
        int i = 0;
        for (Object obj : expression.getArguments()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irExpressionArr[i] = (IrExpression) obj;
            i = i2;
        }
        IrExpression[] irExpressionArrTransformArguments = transformArguments(irExpressionArr);
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType type = expression.getType();
        ArrayList arrayList = new ArrayList(irExpressionArrTransformArguments.length);
        for (IrExpression irExpression : irExpressionArrTransformArguments) {
            Intrinsics.checkNotNull(irExpression);
            arrayList.add(irExpression);
        }
        addStatement(new IrStringConcatenationImpl(startOffset, endOffset, type, arrayList));
    }

    private final IrGetObjectValueImpl getUnitValue() {
        return JsIrBuilder.INSTANCE.buildGetObjectValue(this.unit, this.context.getIrBuiltIns().getUnitClass());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitReturn(final IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVisitorsKt.acceptChildrenVoid(expression, this);
        if (expression.getReturnTargetSymbol() instanceof IrReturnableBlockSymbol) {
            return;
        }
        transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitReturn.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrStatement invoke(IrExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrReturn irReturn = expression;
                irReturn.setValue(it);
                return irReturn;
            }
        });
    }

    private final void addExceptionEdge() {
        this.hasExceptions = true;
        Collection successors = this.currentState.getSuccessors();
        Object objPeek = UtilsKt.peek(this.catchBlockStack);
        Intrinsics.checkNotNull(objPeek);
        successors.add(objPeek);
    }

    private final boolean hasResultingValue(IrExpression expression) {
        IrType type = expression.getType();
        return !(IrTypePredicatesKt.isNothing(type) || IrTypePredicatesKt.isUnit(type));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitThrow(final IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVisitorsKt.acceptChildrenVoid(expression, this);
        addExceptionEdge();
        transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.visitThrow.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrStatement invoke(IrExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrThrow irThrow = expression;
                irThrow.setValue(it);
                return irThrow;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTry(IrTry aTry) {
        IrSetValueImpl tryResult;
        IrSetValueImpl result;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        if (aTry.getFinallyExpression() != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TryState tryStateBuildTryState = buildTryState();
        Object objPeek = UtilsKt.peek(this.catchBlockStack);
        Intrinsics.checkNotNull(objPeek);
        SuspendState suspendState = (SuspendState) objPeek;
        this.tryStateMap.put(aTry, tryStateBuildTryState);
        UtilsKt.push(this.catchBlockStack, tryStateBuildTryState.getCatchState());
        UtilsKt.push(this.tryLoopStack, aTry);
        SuspendState suspendState2 = new SuspendState(this.unit);
        IrVariable irVariableTempVar = hasResultingValue(aTry) ? tempVar(aTry.getType(), "TRY_RESULT") : null;
        if (irVariableTempVar != null) {
            addStatement(irVariableTempVar);
        }
        setupExceptionState(tryStateBuildTryState.getCatchState());
        if (irVariableTempVar != null) {
            IrSetValueImpl irSetValueImplBuildSetVariable = JsIrBuilder.INSTANCE.buildSetVariable(irVariableTempVar.getSymbol(), aTry.getTryResult(), this.unit);
            if (this.suspendableNodes.contains(irSetValueImplBuildSetVariable.getValue())) {
                this.suspendableNodes.add(irSetValueImplBuildSetVariable);
            }
            tryResult = irSetValueImplBuildSetVariable;
        } else {
            tryResult = aTry.getTryResult();
        }
        StateMachineBuilder stateMachineBuilder = this;
        IrVisitorsKt.acceptVoid(tryResult, stateMachineBuilder);
        if (!isBlockEnded()) {
            setupExceptionState(suspendState);
            doDispatch$default(this, suspendState2, false, 2, null);
        }
        addExceptionEdge();
        this.tryStateMap.remove(aTry);
        UtilsKt.pop(this.catchBlockStack);
        updateState(tryStateBuildTryState.getCatchState());
        setupExceptionState(suspendState);
        boolean z = true;
        for (IrCatch irCatch : aTry.getCatches()) {
            IrType type = irCatch.getCatchParameter().getType();
            boolean z2 = type instanceof IrDynamicType;
            IrTypeOperatorCallImpl irTypeOperatorCallImplPendingException = pendingException();
            if (!z2) {
                irTypeOperatorCallImplPendingException = implicitCast(irTypeOperatorCallImplPendingException, type);
            }
            IrVariable catchParameter = irCatch.getCatchParameter();
            catchParameter.setInitializer(irTypeOperatorCallImplPendingException);
            if (irVariableTempVar != null) {
                IrSetValueImpl irSetValueImplBuildSetVariable2 = JsIrBuilder.INSTANCE.buildSetVariable(irVariableTempVar.getSymbol(), irCatch.getResult(), this.unit);
                if (this.suspendableNodes.contains(irSetValueImplBuildSetVariable2.getValue())) {
                    this.suspendableNodes.add(irSetValueImplBuildSetVariable2);
                }
                result = irSetValueImplBuildSetVariable2;
            } else {
                result = irCatch.getResult();
            }
            if (z2) {
                addStatement(catchParameter);
                IrVisitorsKt.acceptVoid(result, stateMachineBuilder);
                maybeDoDispatch(suspendState2);
                z = false;
            } else {
                IrTypeOperatorCallImpl irTypeOperatorCallImplBuildIsCheck = buildIsCheck(pendingException(), type);
                IrCompositeImpl irCompositeImplBuildComposite$default = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, result.getType(), null, 2, null);
                IrCompositeImpl irCompositeImplBuildComposite$default2 = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, result.getType(), null, 2, null);
                IrWhen irWhenBuildIfElse$default = JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, result.getType(), irTypeOperatorCallImplBuildIsCheck, irCompositeImplBuildComposite$default, irCompositeImplBuildComposite$default2, 0, 0, 0, 0, 240, null);
                IrContainerExpression irContainerExpression = this.currentBlock;
                this.currentBlock = irCompositeImplBuildComposite$default;
                addStatement(catchParameter);
                IrVisitorsKt.acceptVoid(result, stateMachineBuilder);
                maybeDoDispatch(suspendState2);
                this.currentBlock = irContainerExpression;
                addStatement(irWhenBuildIfElse$default);
                this.currentBlock = irCompositeImplBuildComposite$default2;
            }
        }
        if (z) {
            addExceptionEdge();
            addStatement(JsIrBuilder.INSTANCE.buildThrow(this.nothing, pendingException()));
        }
        this.currentState.getSuccessors().add(suspendState);
        updateState(suspendState2);
        setupExceptionState(suspendState);
        if (irVariableTempVar != null) {
            addStatement(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableTempVar.getSymbol(), null, 2, null));
        }
    }

    private final void setupExceptionState(SuspendState target) {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.exStateSymbolSetter.getSymbol(), this.unit, null, null, null, 28, null);
        irCallBuildCall$default.setDispatchReceiver(getThisReceiver());
        irCallBuildCall$default.putValueArgument(0, new IrDispatchPoint(target));
        addStatement(irCallBuildCall$default);
    }

    private final IrCall exceptionState() {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.exStateSymbolGetter.getSymbol(), null, null, null, null, 30, null);
        irCallBuildCall$default.setDispatchReceiver(getThisReceiver());
        return irCallBuildCall$default;
    }

    private final IrCall pendingException() {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.exceptionSymbolGetter.getSymbol(), null, null, null, null, 30, null);
        irCallBuildCall$default.setDispatchReceiver(getThisReceiver());
        return irCallBuildCall$default;
    }

    private final TryState buildTryState() {
        return new TryState(this.currentState, new SuspendState(this.unit));
    }

    private final IrTypeOperatorCallImpl buildIsCheck(IrExpression value, IrType toType) {
        return JsIrBuilder.INSTANCE.buildTypeOperator(this.context.getIrBuiltIns().getBooleanType(), IrTypeOperator.INSTANCEOF, value, toType);
    }

    private final IrVariable tempVar(IrType type, String name2) {
        return JsIrBuilder.INSTANCE.buildVar(type, this.function.getOwner(), (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : name2, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : null, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression[] transformArguments(IrExpression[] arguments) {
        int i = 0;
        for (IrExpression irExpression : arguments) {
            if (irExpression != null && this.suspendableNodes.contains(irExpression)) {
                i++;
            }
        }
        IrExpression[] irExpressionArr = new IrExpression[arguments.length];
        int length = arguments.length;
        for (int i2 = 0; i2 < length; i2++) {
            IrExpression irExpressionBuildGetValue$default = arguments[i2];
            if (!org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.isPure$default(irExpressionBuildGetValue$default, false, false, null, 6, null)) {
                if (irExpressionBuildGetValue$default == null) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                DescriptorsRemapper descriptorsRemapper = null;
                Object[] objArr = 0;
                if (i > 0) {
                    if (this.suspendableNodes.contains(irExpressionBuildGetValue$default)) {
                        i--;
                    }
                    IrVisitorsKt.acceptVoid(irExpressionBuildGetValue$default, this);
                    final IrVariable irVariableTempVar = tempVar(irExpressionBuildGetValue$default.getType(), "ARGUMENT");
                    transformLastExpression(new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.StateMachineBuilder.transformArguments.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrStatement invoke(IrExpression it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            IrVariable irVariable = irVariableTempVar;
                            irVariable.setInitializer(it);
                            return irVariable;
                        }
                    });
                    irExpressionBuildGetValue$default = JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableTempVar.getSymbol(), null, 2, null);
                } else {
                    IrExpression irExpression2 = irExpressionBuildGetValue$default;
                    IrFunction owner = this.function.getOwner();
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
                    IrVisitorsKt.acceptVoid(irExpression2, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), owner);
                    if (irElementPatchDeclarationParents != null) {
                        irExpressionBuildGetValue$default = (IrExpression) irElementPatchDeclarationParents;
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    }
                }
            }
            irExpressionArr[i2] = irExpressionBuildGetValue$default;
        }
        return irExpressionArr;
    }
}
