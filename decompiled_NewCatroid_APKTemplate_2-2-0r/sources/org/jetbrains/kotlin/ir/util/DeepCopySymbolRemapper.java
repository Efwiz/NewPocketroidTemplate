package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
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
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFileSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrLocalDelegatedPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrReturnableBlockSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrScriptSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DeepCopySymbolRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Î\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u00106\u001a\u00020\b2\u0006\u00107\u001a\u00020\bH\u0016J\u0010\u00108\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0016J\u0010\u00109\u001a\u00020\u00102\u0006\u00107\u001a\u00020\u0010H\u0016J\u0010\u0010:\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u0013H\u0016J\u0010\u0010;\u001a\u00020\u00162\u0006\u00107\u001a\u00020\u0016H\u0016J\u0010\u0010<\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u0019H\u0016J\u0010\u0010=\u001a\u00020\u001c2\u0006\u00107\u001a\u00020\u001cH\u0016J\u0010\u0010>\u001a\u00020\u001f2\u0006\u00107\u001a\u00020\u001fH\u0016J\u0010\u0010?\u001a\u00020\"2\u0006\u00107\u001a\u00020\"H\u0016J\u0010\u0010@\u001a\u00020(2\u0006\u00107\u001a\u00020(H\u0016J\u0010\u0010A\u001a\u00020+2\u0006\u00107\u001a\u00020+H\u0016J\u0010\u0010B\u001a\u00020.2\u0006\u00107\u001a\u00020.H\u0016J\u0010\u0010C\u001a\u0002012\u0006\u00107\u001a\u000201H\u0016J\u0010\u0010D\u001a\u0002042\u0006\u00107\u001a\u000204H\u0016J\u0010\u0010E\u001a\u00020\b2\u0006\u00107\u001a\u00020\bH\u0016J\u0014\u0010F\u001a\u0004\u0018\u00010\b2\b\u00107\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010G\u001a\u00020H2\u0006\u00107\u001a\u00020HH\u0016J\u0010\u0010I\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0016J\u0010\u0010J\u001a\u00020\u00102\u0006\u00107\u001a\u00020\u0010H\u0016J\u0010\u0010K\u001a\u00020\u00162\u0006\u00107\u001a\u00020\u0016H\u0016J\u0010\u0010L\u001a\u00020M2\u0006\u00107\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020\u001f2\u0006\u00107\u001a\u00020\u001fH\u0016J\u0010\u0010O\u001a\u00020\"2\u0006\u00107\u001a\u00020\"H\u0016J\u0010\u0010P\u001a\u00020%2\u0006\u00107\u001a\u00020%H\u0016J\u0010\u0010Q\u001a\u00020(2\u0006\u00107\u001a\u00020(H\u0016J\u0010\u0010R\u001a\u00020\u001c2\u0006\u00107\u001a\u00020\u001cH\u0016J\u0010\u0010S\u001a\u00020+2\u0006\u00107\u001a\u00020+H\u0016J\u0010\u0010T\u001a\u00020U2\u0006\u00107\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u0002042\u0006\u00107\u001a\u000204H\u0016Jm\u0010W\u001a\u00020X\"\b\b\u0000\u0010Y*\u00020Z\"\b\b\u0001\u0010[*\u00020\\\"\u0016\b\u0002\u0010]\u0018\u0001*\u000e\u0012\u0004\u0012\u0002HY\u0012\u0004\u0012\u0002H[0^2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u0002H]\u0012\u0004\u0012\u0002H]0`2\u0006\u0010a\u001a\u0002H[2\u0012\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H]\u0012\u0004\u0012\u0002H]0cH\u0084\bø\u0001\u0000¢\u0006\u0002\u0010dJ\u0010\u0010e\u001a\u00020X2\u0006\u0010f\u001a\u00020gH\u0016J\u0010\u0010h\u001a\u00020X2\u0006\u0010f\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u00020X2\u0006\u0010k\u001a\u00020lH\u0016J\u0010\u0010m\u001a\u00020X2\u0006\u0010k\u001a\u00020nH\u0016J\u0010\u0010o\u001a\u00020X2\u0006\u0010p\u001a\u00020qH\u0016J\u0010\u0010r\u001a\u00020X2\u0006\u0010k\u001a\u00020sH\u0016J\u0010\u0010t\u001a\u00020X2\u0006\u0010k\u001a\u00020uH\u0016J\u0010\u0010v\u001a\u00020X2\u0006\u0010k\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020X2\u0006\u0010k\u001a\u00020yH\u0016J\u0010\u0010z\u001a\u00020X2\u0006\u0010k\u001a\u00020{H\u0016J\u0010\u0010|\u001a\u00020X2\u0006\u0010k\u001a\u00020}H\u0016J\u0010\u0010~\u001a\u00020X2\u0006\u0010k\u001a\u00020\u007fH\u0016J\u0012\u0010\u0080\u0001\u001a\u00020X2\u0007\u0010k\u001a\u00030\u0081\u0001H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020X2\u0007\u0010k\u001a\u00030\u0083\u0001H\u0016J\u0012\u0010\u0084\u0001\u001a\u00020X2\u0007\u0010k\u001a\u00030\u0085\u0001H\u0016J\u0012\u0010\u0086\u0001\u001a\u00020X2\u0007\u0010k\u001a\u00030\u0087\u0001H\u0016J\u0012\u0010\u0088\u0001\u001a\u00020X2\u0007\u0010k\u001a\u00030\u0089\u0001H\u0016J8\u0010\u008a\u0001\u001a\u0003H\u008b\u0001\"\n\b\u0000\u0010\u008b\u0001*\u00030\u008c\u0001*\u0011\u0012\u0005\u0012\u0003H\u008b\u0001\u0012\u0005\u0012\u0003H\u008b\u00010\u008d\u00012\u0007\u00107\u001a\u0003H\u008b\u0001H\u0002¢\u0006\u0003\u0010\u008e\u0001J8\u0010\u008f\u0001\u001a\u0003H\u008b\u0001\"\n\b\u0000\u0010\u008b\u0001*\u00030\u008c\u0001*\u0011\u0012\u0005\u0012\u0003H\u008b\u0001\u0012\u0005\u0012\u0003H\u008b\u00010\u008d\u00012\u0007\u00107\u001a\u0003H\u008b\u0001H\u0002¢\u0006\u0003\u0010\u008e\u0001R0\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR0\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0007j\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u0007j\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR0\u0010\u0012\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0007j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0013`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000bR0\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160\u0007j\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0016`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000bR0\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00190\u0007j\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u0019`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000bR0\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001c0\u0007j\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001c`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000bR0\u0010\u001e\u001a\u001e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f0\u0007j\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u000bR0\u0010!\u001a\u001e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0\u0007j\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000bR0\u0010$\u001a\u001e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0\u0007j\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000bR0\u0010'\u001a\u001e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020(0\u0007j\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020(`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u000bR0\u0010*\u001a\u001e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020+0\u0007j\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020+`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u000bR0\u0010-\u001a\u001e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.0\u0007j\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u000bR0\u00100\u001a\u001e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002010\u0007j\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u000201`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u000bR0\u00103\u001a\u001e\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u0002040\u0007j\u000e\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u000204`\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "descriptorsRemapper", "Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "(Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;)V", "classes", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lkotlin/collections/HashMap;", "getClasses", "()Ljava/util/HashMap;", "constructors", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getConstructors", "enumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getEnumEntries", "externalPackageFragments", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "getExternalPackageFragments", "fields", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getFields", "files", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getFiles", "functions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getFunctions", "localDelegatedProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getLocalDelegatedProperties", "properties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getProperties", "returnableBlocks", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "getReturnableBlocks", "scripts", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getScripts", "typeAliases", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getTypeAliases", "typeParameters", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getValueParameters", "variables", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getVariables", "getDeclaredClass", NativeSymbol.TYPE_NAME, TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "getDeclaredEnumEntry", "getDeclaredExternalPackageFragment", "getDeclaredField", "getDeclaredFile", "getDeclaredFunction", "getDeclaredLocalDelegatedProperty", "getDeclaredProperty", "getDeclaredScript", "getDeclaredTypeAlias", "getDeclaredTypeParameter", "getDeclaredValueParameter", "getDeclaredVariable", "getReferencedClass", "getReferencedClassOrNull", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getReferencedConstructor", "getReferencedEnumEntry", "getReferencedField", "getReferencedFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getReferencedLocalDelegatedProperty", "getReferencedProperty", "getReferencedReturnableBlock", "getReferencedScript", "getReferencedSimpleFunction", "getReferencedTypeAlias", "getReferencedValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getReferencedVariable", "remapSymbol", "", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "B", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "map", "", "owner", "createNewSymbol", "Lkotlin/Function1;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;Lkotlin/jvm/functions/Function1;)V", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getDeclared", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getReferenced", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeepCopySymbolRemapper implements SymbolRemapper, IrElementVisitorVoid {
    private final HashMap<IrClassSymbol, IrClassSymbol> classes;
    private final HashMap<IrConstructorSymbol, IrConstructorSymbol> constructors;
    private final DescriptorsRemapper descriptorsRemapper;
    private final HashMap<IrEnumEntrySymbol, IrEnumEntrySymbol> enumEntries;
    private final HashMap<IrExternalPackageFragmentSymbol, IrExternalPackageFragmentSymbol> externalPackageFragments;
    private final HashMap<IrFieldSymbol, IrFieldSymbol> fields;
    private final HashMap<IrFileSymbol, IrFileSymbol> files;
    private final HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> functions;
    private final HashMap<IrLocalDelegatedPropertySymbol, IrLocalDelegatedPropertySymbol> localDelegatedProperties;
    private final HashMap<IrPropertySymbol, IrPropertySymbol> properties;
    private final HashMap<IrReturnableBlockSymbol, IrReturnableBlockSymbol> returnableBlocks;
    private final HashMap<IrScriptSymbol, IrScriptSymbol> scripts;
    private final HashMap<IrTypeAliasSymbol, IrTypeAliasSymbol> typeAliases;
    private final HashMap<IrTypeParameterSymbol, IrTypeParameterSymbol> typeParameters;
    private final HashMap<IrValueParameterSymbol, IrValueParameterSymbol> valueParameters;
    private final HashMap<IrVariableSymbol, IrVariableSymbol> variables;

    /* JADX WARN: Multi-variable type inference failed */
    public DeepCopySymbolRemapper() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public DeepCopySymbolRemapper(DescriptorsRemapper descriptorsRemapper) {
        Intrinsics.checkNotNullParameter(descriptorsRemapper, "descriptorsRemapper");
        this.descriptorsRemapper = descriptorsRemapper;
        this.classes = new HashMap<>();
        this.scripts = new HashMap<>();
        this.constructors = new HashMap<>();
        this.enumEntries = new HashMap<>();
        this.externalPackageFragments = new HashMap<>();
        this.fields = new HashMap<>();
        this.files = new HashMap<>();
        this.functions = new HashMap<>();
        this.properties = new HashMap<>();
        this.returnableBlocks = new HashMap<>();
        this.typeParameters = new HashMap<>();
        this.valueParameters = new HashMap<>();
        this.variables = new HashMap<>();
        this.localDelegatedProperties = new HashMap<>();
        this.typeAliases = new HashMap<>();
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
    public void visitBreak(IrBreak irBreak) {
        IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
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
    public void visitContinue(IrContinue irContinue) {
        IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
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
    public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
        IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
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
    public void visitGetField(IrGetField irGetField) {
        IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
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
    public void visitReturn(IrReturn irReturn) {
        IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
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
    public void visitSetField(IrSetField irSetField) {
        IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
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
    public void visitSetValue(IrSetValue irSetValue) {
        IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
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
    public void visitThrow(IrThrow irThrow) {
        IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
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
    public void visitTry(IrTry irTry) {
        IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
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
    public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
        IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
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
    public void visitVararg(IrVararg irVararg) {
        IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
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
    public void visitWhen(IrWhen irWhen) {
        IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
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
    public void visitWhileLoop(IrWhileLoop irWhileLoop) {
        IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
    }

    public /* synthetic */ DeepCopySymbolRemapper(NullDescriptorsRemapper nullDescriptorsRemapper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? NullDescriptorsRemapper.INSTANCE : nullDescriptorsRemapper);
    }

    protected final HashMap<IrClassSymbol, IrClassSymbol> getClasses() {
        return this.classes;
    }

    protected final HashMap<IrScriptSymbol, IrScriptSymbol> getScripts() {
        return this.scripts;
    }

    protected final HashMap<IrConstructorSymbol, IrConstructorSymbol> getConstructors() {
        return this.constructors;
    }

    protected final HashMap<IrEnumEntrySymbol, IrEnumEntrySymbol> getEnumEntries() {
        return this.enumEntries;
    }

    protected final HashMap<IrExternalPackageFragmentSymbol, IrExternalPackageFragmentSymbol> getExternalPackageFragments() {
        return this.externalPackageFragments;
    }

    protected final HashMap<IrFieldSymbol, IrFieldSymbol> getFields() {
        return this.fields;
    }

    protected final HashMap<IrFileSymbol, IrFileSymbol> getFiles() {
        return this.files;
    }

    protected final HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> getFunctions() {
        return this.functions;
    }

    protected final HashMap<IrPropertySymbol, IrPropertySymbol> getProperties() {
        return this.properties;
    }

    protected final HashMap<IrReturnableBlockSymbol, IrReturnableBlockSymbol> getReturnableBlocks() {
        return this.returnableBlocks;
    }

    protected final HashMap<IrTypeParameterSymbol, IrTypeParameterSymbol> getTypeParameters() {
        return this.typeParameters;
    }

    protected final HashMap<IrValueParameterSymbol, IrValueParameterSymbol> getValueParameters() {
        return this.valueParameters;
    }

    protected final HashMap<IrVariableSymbol, IrVariableSymbol> getVariables() {
        return this.variables;
    }

    protected final HashMap<IrLocalDelegatedPropertySymbol, IrLocalDelegatedPropertySymbol> getLocalDelegatedProperties() {
        return this.localDelegatedProperties;
    }

    protected final HashMap<IrTypeAliasSymbol, IrTypeAliasSymbol> getTypeAliases() {
        return this.typeAliases;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6335visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        IrVisitorsKt.acceptChildrenVoid(element, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVisitorsKt.acceptChildrenVoid(expression, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final /* synthetic */ <D extends DeclarationDescriptor, B extends IrSymbolOwner, S extends IrBindableSymbol<? extends D, B>> void remapSymbol(Map<S, S> map, B owner, Function1<? super S, ? extends S> createNewSymbol) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(createNewSymbol, "createNewSymbol");
        IrSymbol symbol = owner.getSymbol();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.LATITUDE_SOUTH);
        IrBindableSymbol irBindableSymbol = (IrBindableSymbol) symbol;
        map.put(irBindableSymbol, createNewSymbol.invoke(irBindableSymbol));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitClass */
    public void mo6112visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrClassSymbol, IrClassSymbol> map = this.classes;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
        }
        IrClassSymbol irClassSymbol = (IrClassSymbol) symbol;
        map.put(irClassSymbol, new IrClassSymbolImpl(this.descriptorsRemapper.remapDeclaredClass(irClassSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrScriptSymbol, IrScriptSymbol> map = this.scripts;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrScriptSymbol");
        }
        IrScriptSymbol irScriptSymbol = (IrScriptSymbol) symbol;
        map.put(irScriptSymbol, new IrScriptSymbolImpl(this.descriptorsRemapper.remapDeclaredScript(irScriptSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrConstructorSymbol, IrConstructorSymbol> map = this.constructors;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
        }
        IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) symbol;
        map.put(irConstructorSymbol, new IrConstructorSymbolImpl(this.descriptorsRemapper.remapDeclaredConstructor(irConstructorSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrEnumEntrySymbol, IrEnumEntrySymbol> map = this.enumEntries;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol");
        }
        IrEnumEntrySymbol irEnumEntrySymbol = (IrEnumEntrySymbol) symbol;
        map.put(irEnumEntrySymbol, new IrEnumEntrySymbolImpl(this.descriptorsRemapper.remapDeclaredEnumEntry(irEnumEntrySymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExternalPackageFragment(IrExternalPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrExternalPackageFragmentSymbol, IrExternalPackageFragmentSymbol> map = this.externalPackageFragments;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol");
        }
        IrExternalPackageFragmentSymbol irExternalPackageFragmentSymbol = (IrExternalPackageFragmentSymbol) symbol;
        map.put(irExternalPackageFragmentSymbol, new IrExternalPackageFragmentSymbolImpl(this.descriptorsRemapper.remapDeclaredExternalPackageFragment(irExternalPackageFragmentSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrFieldSymbol, IrFieldSymbol> map = this.fields;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFieldSymbol");
        }
        IrFieldSymbol irFieldSymbol = (IrFieldSymbol) symbol;
        map.put(irFieldSymbol, new IrFieldSymbolImpl(this.descriptorsRemapper.remapDeclaredField(irFieldSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrFileSymbol, IrFileSymbol> map = this.files;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFileSymbol");
        }
        IrFileSymbol irFileSymbol = (IrFileSymbol) symbol;
        map.put(irFileSymbol, new IrFileSymbolImpl(this.descriptorsRemapper.remapDeclaredFilePackageFragment(irFileSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    public void visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> map = this.functions;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) symbol;
        map.put(irSimpleFunctionSymbol, new IrSimpleFunctionSymbolImpl(this.descriptorsRemapper.remapDeclaredSimpleFunction(irSimpleFunctionSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrPropertySymbol, IrPropertySymbol> map = this.properties;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
        }
        IrPropertySymbol irPropertySymbol = (IrPropertySymbol) symbol;
        map.put(irPropertySymbol, new IrPropertySymbolImpl(this.descriptorsRemapper.remapDeclaredProperty(irPropertySymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeParameter(IrTypeParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrTypeParameterSymbol, IrTypeParameterSymbol> map = this.typeParameters;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
        }
        IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) symbol;
        map.put(irTypeParameterSymbol, new IrTypeParameterSymbolImpl(this.descriptorsRemapper.remapDeclaredTypeParameter(irTypeParameterSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrValueParameterSymbol, IrValueParameterSymbol> map = this.valueParameters;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol");
        }
        IrValueParameterSymbol irValueParameterSymbol = (IrValueParameterSymbol) symbol;
        map.put(irValueParameterSymbol, new IrValueParameterSymbolImpl(this.descriptorsRemapper.remapDeclaredValueParameter(irValueParameterSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrVariableSymbol, IrVariableSymbol> map = this.variables;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrVariableSymbol");
        }
        IrVariableSymbol irVariableSymbol = (IrVariableSymbol) symbol;
        map.put(irVariableSymbol, new IrVariableSymbolImpl(this.descriptorsRemapper.remapDeclaredVariable(irVariableSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrLocalDelegatedPropertySymbol, IrLocalDelegatedPropertySymbol> map = this.localDelegatedProperties;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol");
        }
        IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol = (IrLocalDelegatedPropertySymbol) symbol;
        map.put(irLocalDelegatedPropertySymbol, new IrLocalDelegatedPropertySymbolImpl(this.descriptorsRemapper.remapDeclaredLocalDelegatedProperty(irLocalDelegatedPropertySymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrTypeAliasSymbol, IrTypeAliasSymbol> map = this.typeAliases;
        IrSymbol symbol = declaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol");
        }
        IrTypeAliasSymbol irTypeAliasSymbol = (IrTypeAliasSymbol) symbol;
        map.put(irTypeAliasSymbol, new IrTypeAliasSymbolImpl(this.descriptorsRemapper.remapDeclaredTypeAlias(irTypeAliasSymbol.getDescriptor())));
        IrVisitorsKt.acceptChildrenVoid(declaration, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrReturnableBlock) {
            HashMap<IrReturnableBlockSymbol, IrReturnableBlockSymbol> map = this.returnableBlocks;
            IrSymbol symbol = ((IrSymbolOwner) expression).getSymbol();
            if (symbol == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol");
            }
            map.put((IrReturnableBlockSymbol) symbol, new IrReturnableBlockSymbolImpl(null, 1, null));
        }
        IrVisitorsKt.acceptChildrenVoid(expression, this);
    }

    private final <T extends IrSymbol> T getDeclared(Map<T, ? extends T> map, T t) {
        T t2 = map.get(t);
        if (t2 != null) {
            return t2;
        }
        throw new IllegalArgumentException("Non-remapped symbol " + t);
    }

    private final <T extends IrSymbol> T getReferenced(Map<T, ? extends T> map, T t) {
        T t2 = map.get(t);
        if (t2 != null) {
            t = t2;
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getDeclaredClass(IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrClassSymbol) getDeclared(this.classes, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrScriptSymbol getDeclaredScript(IrScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrScriptSymbol) getDeclared(this.scripts, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrSimpleFunctionSymbol getDeclaredFunction(IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrSimpleFunctionSymbol) getDeclared(this.functions, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrPropertySymbol getDeclaredProperty(IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrPropertySymbol) getDeclared(this.properties, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFieldSymbol getDeclaredField(IrFieldSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrFieldSymbol) getDeclared(this.fields, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFileSymbol getDeclaredFile(IrFileSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrFileSymbol) getDeclared(this.files, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrConstructorSymbol getDeclaredConstructor(IrConstructorSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrConstructorSymbol) getDeclared(this.constructors, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrEnumEntrySymbol getDeclaredEnumEntry(IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrEnumEntrySymbol) getDeclared(this.enumEntries, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrExternalPackageFragmentSymbol getDeclaredExternalPackageFragment(IrExternalPackageFragmentSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrExternalPackageFragmentSymbol) getDeclared(this.externalPackageFragments, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrVariableSymbol getDeclaredVariable(IrVariableSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrVariableSymbol) getDeclared(this.variables, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrTypeParameterSymbol getDeclaredTypeParameter(IrTypeParameterSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrTypeParameterSymbol) getDeclared(this.typeParameters, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrValueParameterSymbol getDeclaredValueParameter(IrValueParameterSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrValueParameterSymbol) getDeclared(this.valueParameters, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrLocalDelegatedPropertySymbol getDeclaredLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrLocalDelegatedPropertySymbol) getDeclared(this.localDelegatedProperties, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrTypeAliasSymbol getDeclaredTypeAlias(IrTypeAliasSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrTypeAliasSymbol) getDeclared(this.typeAliases, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getReferencedClass(IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrClassSymbol) getReferenced(this.classes, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrScriptSymbol getReferencedScript(IrScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrScriptSymbol) getReferenced(this.scripts, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getReferencedClassOrNull(IrClassSymbol symbol) {
        if (symbol != null) {
            return (IrClassSymbol) getReferenced(this.classes, symbol);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrEnumEntrySymbol getReferencedEnumEntry(IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrEnumEntrySymbol) getReferenced(this.enumEntries, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrVariableSymbol getReferencedVariable(IrVariableSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrVariableSymbol) getReferenced(this.variables, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrLocalDelegatedPropertySymbol getReferencedLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrLocalDelegatedPropertySymbol) getReferenced(this.localDelegatedProperties, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFieldSymbol getReferencedField(IrFieldSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrFieldSymbol) getReferenced(this.fields, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrConstructorSymbol getReferencedConstructor(IrConstructorSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrConstructorSymbol) getReferenced(this.constructors, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrSimpleFunctionSymbol getReferencedSimpleFunction(IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrSimpleFunctionSymbol) getReferenced(this.functions, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrPropertySymbol getReferencedProperty(IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrPropertySymbol) getReferenced(this.properties, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrValueSymbol getReferencedValue(IrValueSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof IrValueParameterSymbol) {
            return (IrValueSymbol) getReferenced(this.valueParameters, symbol);
        }
        if (symbol instanceof IrVariableSymbol) {
            return (IrValueSymbol) getReferenced(this.variables, symbol);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFunctionSymbol getReferencedFunction(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof IrSimpleFunctionSymbol) {
            return (IrFunctionSymbol) getReferenced(this.functions, symbol);
        }
        if (symbol instanceof IrConstructorSymbol) {
            return (IrFunctionSymbol) getReferenced(this.constructors, symbol);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrReturnableBlockSymbol getReferencedReturnableBlock(IrReturnableBlockSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrReturnableBlockSymbol) getReferenced(this.returnableBlocks, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof IrClassSymbol) {
            return (IrClassifierSymbol) getReferenced(this.classes, symbol);
        }
        if (symbol instanceof IrScriptSymbol) {
            return (IrClassifierSymbol) getReferenced(this.scripts, symbol);
        }
        if (symbol instanceof IrTypeParameterSymbol) {
            return (IrClassifierSymbol) getReferenced(this.typeParameters, symbol);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrTypeAliasSymbol getReferencedTypeAlias(IrTypeAliasSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrTypeAliasSymbol) getReferenced(this.typeAliases, symbol);
    }
}
