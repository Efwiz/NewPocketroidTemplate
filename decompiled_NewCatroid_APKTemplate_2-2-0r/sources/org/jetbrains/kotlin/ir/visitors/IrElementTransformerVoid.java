package org.jetbrains.kotlin.ir.visitors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;

/* JADX INFO: compiled from: IrElementTransformerVoid.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000®\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0019H\u0016J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00192\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001b2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0014\u0010\u001c\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0016J\u001c\u0010\u001c\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u001d2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\"H\u0016J\u0018\u0010!\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\"2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020$H\u0016J\u0018\u0010#\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020$2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010%\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020&H\u0016J\u0018\u0010%\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020&2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0014\u0010'\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030(H\u0016J\u001c\u0010'\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030(2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010)\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020+H\u0016J\u0018\u0010)\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020+2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010,\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020-H\u0016J\u0018\u0010,\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020-2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010.\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020/H\u0016J\u0018\u0010.\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020/2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00100\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020*H\u0016J\u0018\u00100\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020*2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00101\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u000202H\u0016J\u0018\u00101\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u0002022\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00103\u001a\u00020\n2\u0006\u0010\u000b\u001a\u000204H\u0016J\u0018\u00103\u001a\u00020\n2\u0006\u0010\u000b\u001a\u0002042\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00105\u001a\u00020\n2\u0006\u0010\u000b\u001a\u000206H\u0016J\u0018\u00105\u001a\u00020\n2\u0006\u0010\u000b\u001a\u0002062\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00107\u001a\u00020\n2\u0006\u0010\u0016\u001a\u000208H\u0016J\u0018\u00107\u001a\u00020\n2\u0006\u0010\u0016\u001a\u0002082\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u00109\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020:H\u0016J\u0018\u00109\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020:2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010;\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020<H\u0016J\u0018\u0010;\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020<2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010=\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020>H\u0016J\u0018\u0010=\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020>2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020AH\u0016J\u0018\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020A2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010B\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020CH\u0016J\u0018\u0010B\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020C2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010D\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020EH\u0016J\u0018\u0010D\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020E2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010F\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020GH\u0016J\u0018\u0010F\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020G2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020IH\u0016J\u0018\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020I2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010K\u001a\u00020L2\u0006\u0010\u0014\u001a\u00020LH\u0016J\u0018\u0010K\u001a\u00020L2\u0006\u0010\u0014\u001a\u00020L2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010M\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020NH\u0016J\u0018\u0010M\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020N2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010O\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020PH\u0016J\u0018\u0010O\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020P2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010Q\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020RH\u0016J\u0018\u0010Q\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020R2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010S\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020TH\u0016J\u0018\u0010S\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020T2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010U\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020VH\u0016J\u0018\u0010U\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020V2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010W\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0018\u0010W\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010X\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020YH\u0016J\u0018\u0010X\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020Y2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010Z\u001a\u00020[2\u0006\u0010\u0006\u001a\u00020[H\u0016J\u0018\u0010Z\u001a\u00020[2\u0006\u0010\u0006\u001a\u00020[2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\\\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020]H\u0016J\u0018\u0010\\\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020]2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010^\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020_H\u0016J\u0018\u0010^\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020_2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010`\u001a\u00020a2\u0006\u0010\u0006\u001a\u00020aH\u0016J\u0018\u0010`\u001a\u00020a2\u0006\u0010\u0006\u001a\u00020a2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020cH\u0016J\u0018\u0010b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020c2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020eH\u0016J\u0018\u0010d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020e2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020gH\u0016J\u0018\u0010f\u001a\u00020I2\u0006\u0010\u000b\u001a\u00020g2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010h\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020iH\u0016J\u0018\u0010h\u001a\u00020I2\u0006\u0010\u000b\u001a\u00020i2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010j\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020kH\u0016J\u0018\u0010j\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020k2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010l\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020mH\u0016J\u0018\u0010l\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020m2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010n\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020oH\u0016J\u0018\u0010n\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020o2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010p\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020qH\u0016J\u0018\u0010p\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020q2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020sH\u0016J\u0018\u0010r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020s2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020uH\u0016J\u0018\u0010t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020u2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010v\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020wH\u0016J\u0018\u0010v\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020w2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010x\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020yH\u0016J\u0018\u0010x\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020y2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010z\u001a\u00020\n2\u0006\u0010@\u001a\u00020{H\u0016J\u0018\u0010z\u001a\u00020\n2\u0006\u0010@\u001a\u00020{2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0014\u0010|\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030}H\u0016J\u001c\u0010|\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030}2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0010\u0010~\u001a\u00020\u007f2\u0006\u0010\u0006\u001a\u00020\u007fH\u0016J\u0018\u0010~\u001a\u00020\u007f2\u0006\u0010\u0006\u001a\u00020\u007f2\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0013\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010\u0006\u001a\u00030\u0081\u0001H\u0016J\u001a\u0010\u0080\u0001\u001a\u00020I2\u0007\u0010\u0006\u001a\u00030\u0081\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0082\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0083\u0001H\u0016J\u001a\u0010\u0082\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0083\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0084\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0085\u0001H\u0016J\u001a\u0010\u0084\u0001\u001a\u00020I2\u0007\u0010\u000b\u001a\u00030\u0085\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0086\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0087\u0001H\u0016J\u001a\u0010\u0086\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0087\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0088\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0089\u0001H\u0016J\u001a\u0010\u0088\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0089\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u008a\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u008b\u0001H\u0016J\u001a\u0010\u008a\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u008b\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u008c\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u008d\u0001H\u0016J\u001a\u0010\u008c\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u008d\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u008e\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u008f\u0001H\u0016J\u001a\u0010\u008e\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u008f\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0090\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0091\u0001H\u0016J\u001a\u0010\u0090\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0091\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0092\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0093\u0001H\u0016J\u001a\u0010\u0092\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0093\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0014\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u0001H\u0016J\u001c\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0097\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0098\u0001H\u0016J\u001a\u0010\u0097\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u0098\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u0099\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u009a\u0001H\u0016J\u001a\u0010\u0099\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u009a\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u009b\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u009c\u0001H\u0016J\u001a\u0010\u009b\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030\u009c\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u009d\u0001\u001a\u00020\u000e2\u0007\u0010\u000f\u001a\u00030\u009e\u0001H\u0016J\u001a\u0010\u009d\u0001\u001a\u00020\u000e2\u0007\u0010\u000f\u001a\u00030\u009e\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\u009f\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030 \u0001H\u0016J\u001a\u0010\u009f\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030 \u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0013\u0010¡\u0001\u001a\u00020\n2\b\u0010¢\u0001\u001a\u00030£\u0001H\u0016J\u001b\u0010¡\u0001\u001a\u00020\n2\b\u0010¢\u0001\u001a\u00030£\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010¤\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030¥\u0001H\u0016J\u001a\u0010¤\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030¥\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010¦\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030§\u0001H\u0016J\u001a\u0010¦\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030§\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010¨\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030©\u0001H\u0016J\u001a\u0010¨\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030©\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010ª\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030«\u0001H\u0016J\u001a\u0010ª\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030«\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010¬\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u00ad\u0001H\u0016J\u001a\u0010¬\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u00ad\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010®\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030¯\u0001H\u0016J\u001a\u0010®\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030¯\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010°\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030±\u0001H\u0016J\u001a\u0010°\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030±\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010²\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030³\u0001H\u0016J\u001a\u0010²\u0001\u001a\u00020\n2\u0007\u0010\u000b\u001a\u00030³\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u0012\u0010´\u0001\u001a\u00020\n2\u0007\u0010@\u001a\u00030µ\u0001H\u0016J\u001a\u0010´\u0001\u001a\u00020\n2\u0007\u0010@\u001a\u00030µ\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0002J\u000e\u0010¶\u0001\u001a\u00030·\u0001*\u00020IH\u0004JA\u0010¸\u0001\u001a\u0003H¹\u0001\"\t\b\u0000\u0010¹\u0001*\u00020I*\u0003H¹\u00012\u001b\u0010\u000f\u001a\u0017\u0012\u0005\u0012\u0003H¹\u0001\u0012\u0005\u0012\u00030·\u00010º\u0001¢\u0006\u0003\b»\u0001H\u0084\bø\u0001\u0000¢\u0006\u0003\u0010¼\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006½\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "", "()V", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBody", "visitBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", ConfigConstants.CONFIG_BRANCH_SECTION, "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "visitCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "aCatch", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "visitConstantValue", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpression", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;", "visitSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "spread", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitSuspendableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "visitSuspensionPoint", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "transformChildrenVoid", "", "transformPostfix", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrElementTransformerVoid implements IrElementTransformer {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
    }

    public IrElement visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.transformChildren(this, null);
        return element;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElement visitElement2(IrElement element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return visitElement(element);
    }

    public IrModuleFragment visitModuleFragment(IrModuleFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrModuleFragment visitModuleFragment2(IrModuleFragment declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitModuleFragment(declaration);
    }

    public IrPackageFragment visitPackageFragment(IrPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElement visitPackageFragment2(IrPackageFragment declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitPackageFragment(declaration);
    }

    public IrFile visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrPackageFragment irPackageFragmentVisitPackageFragment = visitPackageFragment(declaration);
        Intrinsics.checkNotNull(irPackageFragmentVisitPackageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        return (IrFile) irPackageFragmentVisitPackageFragment;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrFile visitFile2(IrFile declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitFile(declaration);
    }

    public IrExternalPackageFragment visitExternalPackageFragment(IrExternalPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrPackageFragment irPackageFragmentVisitPackageFragment = visitPackageFragment(declaration);
        Intrinsics.checkNotNull(irPackageFragmentVisitPackageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment");
        return (IrExternalPackageFragment) irPackageFragmentVisitPackageFragment;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitExternalPackageFragment(declaration);
    }

    public IrStatement visitDeclaration(IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitDeclaration2(IrDeclarationBase declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    public IrStatement visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitScript2(IrScript declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitScript(declaration);
    }

    public IrStatement visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitClass2(IrClass declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitClass(declaration);
    }

    public IrStatement visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitFunction2(IrFunction declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitFunction(declaration);
    }

    public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitFunction(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitSimpleFunction2(IrSimpleFunction declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitSimpleFunction(declaration);
    }

    public IrStatement visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitFunction(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitConstructor2(IrConstructor declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitConstructor(declaration);
    }

    public IrStatement visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitProperty2(IrProperty declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitProperty(declaration);
    }

    public IrStatement visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitField2(IrField declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitField(declaration);
    }

    public IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitLocalDelegatedProperty(declaration);
    }

    public IrStatement visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitEnumEntry2(IrEnumEntry declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitEnumEntry(declaration);
    }

    public IrStatement visitAnonymousInitializer(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitAnonymousInitializer2(IrAnonymousInitializer declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitAnonymousInitializer(declaration);
    }

    public IrStatement visitTypeParameter(IrTypeParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitTypeParameter2(IrTypeParameter declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitTypeParameter(declaration);
    }

    public IrStatement visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitValueParameter2(IrValueParameter declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitValueParameter(declaration);
    }

    public IrStatement visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitVariable2(IrVariable declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitVariable(declaration);
    }

    public IrStatement visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitTypeAlias2(IrTypeAlias declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitTypeAlias(declaration);
    }

    public IrBody visitBody(IrBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        body.transformChildren(this, null);
        return body;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrBody visitBody2(IrBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitBody(body);
    }

    public IrBody visitExpressionBody(IrExpressionBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrBody visitExpressionBody2(IrExpressionBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitExpressionBody(body);
    }

    public IrBody visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrBody visitBlockBody2(IrBlockBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitBlockBody(body);
    }

    public IrBody visitSyntheticBody(IrSyntheticBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitBody(body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrBody visitSyntheticBody2(IrSyntheticBody body, Void data) {
        Intrinsics.checkNotNullParameter(body, "body");
        return visitSyntheticBody(body);
    }

    public IrExpression visitSuspendableExpression(IrSuspendableExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitSuspendableExpression2(IrSuspendableExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSuspendableExpression(expression);
    }

    public IrExpression visitSuspensionPoint(IrSuspensionPoint expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitSuspensionPoint2(IrSuspensionPoint expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSuspensionPoint(expression);
    }

    public IrExpression visitExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        expression.transformChildren(this, null);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitExpression2(IrExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    public IrExpression visitConst(IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    public final IrExpression visitConst(IrConst<?> expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConst(expression);
    }

    public IrConstantValue visitConstantValue(IrConstantValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        expression.transformChildren(this, null);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrConstantValue visitConstantValue2(IrConstantValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantValue(expression);
    }

    public IrConstantValue visitConstantObject(IrConstantObject expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrConstantValue visitConstantObject2(IrConstantObject expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantObject(expression);
    }

    public IrConstantValue visitConstantPrimitive(IrConstantPrimitive expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrConstantValue visitConstantPrimitive2(IrConstantPrimitive expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantPrimitive(expression);
    }

    public IrConstantValue visitConstantArray(IrConstantArray expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantValue(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrConstantValue visitConstantArray2(IrConstantArray expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstantArray(expression);
    }

    public IrExpression visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitVararg2(IrVararg expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitVararg(expression);
    }

    public IrSpreadElement visitSpreadElement(IrSpreadElement spread) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        spread.transformChildren(this, null);
        return spread;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrSpreadElement visitSpreadElement2(IrSpreadElement spread, Void data) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        return visitSpreadElement(spread);
    }

    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitContainerExpression2(IrContainerExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitContainerExpression(expression);
    }

    public IrExpression visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitContainerExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitBlock2(IrBlock expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitBlock(expression);
    }

    public IrExpression visitComposite(IrComposite expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitContainerExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitComposite2(IrComposite expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitComposite(expression);
    }

    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitStringConcatenation2(IrStringConcatenation expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitStringConcatenation(expression);
    }

    public IrExpression visitDeclarationReference(IrDeclarationReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDeclarationReference2(IrDeclarationReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    public IrExpression visitSingletonReference(IrGetSingletonValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitSingletonReference2(IrGetSingletonValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSingletonReference(expression);
    }

    public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSingletonReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitGetObjectValue2(IrGetObjectValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitGetObjectValue(expression);
    }

    public IrExpression visitGetEnumValue(IrGetEnumValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSingletonReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitGetEnumValue2(IrGetEnumValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitGetEnumValue(expression);
    }

    public IrExpression visitValueAccess(IrValueAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitValueAccess2(IrValueAccessExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitValueAccess(expression);
    }

    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitValueAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitGetValue2(IrGetValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitGetValue(expression);
    }

    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitValueAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitSetValue2(IrSetValue expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSetValue(expression);
    }

    public IrExpression visitFieldAccess(IrFieldAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitFieldAccess2(IrFieldAccessExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFieldAccess(expression);
    }

    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFieldAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitGetField2(IrGetField expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitGetField(expression);
    }

    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFieldAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitSetField2(IrSetField expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitSetField(expression);
    }

    public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    public final IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitMemberAccess(expression);
    }

    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitMemberAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitFunctionAccess2(IrFunctionAccessExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionAccess(expression);
    }

    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitCall2(IrCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitCall(expression);
    }

    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitConstructorCall2(IrConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitConstructorCall(expression);
    }

    public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDelegatingConstructorCall(expression);
    }

    public IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionAccess(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitEnumConstructorCall2(IrEnumConstructorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitEnumConstructorCall(expression);
    }

    public IrExpression visitGetClass(IrGetClass expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitGetClass2(IrGetClass expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitGetClass(expression);
    }

    public IrExpression visitCallableReference(IrCallableReference<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitMemberAccess(expression);
    }

    public final IrExpression visitCallableReference(IrCallableReference<?> expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitCallableReference(expression);
    }

    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitCallableReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElement visitFunctionReference2(IrFunctionReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionReference(expression);
    }

    public IrExpression visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitCallableReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElement visitPropertyReference2(IrPropertyReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitPropertyReference(expression);
    }

    public IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitCallableReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitLocalDelegatedPropertyReference(expression);
    }

    public IrExpression visitRawFunctionReference(IrRawFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitRawFunctionReference2(IrRawFunctionReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitRawFunctionReference(expression);
    }

    public IrExpression visitFunctionExpression(IrFunctionExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElement visitFunctionExpression2(IrFunctionExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitFunctionExpression(expression);
    }

    public IrExpression visitClassReference(IrClassReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDeclarationReference(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitClassReference2(IrClassReference expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitClassReference(expression);
    }

    public IrExpression visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitInstanceInitializerCall(expression);
    }

    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitTypeOperator2(IrTypeOperatorCall expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitTypeOperator(expression);
    }

    public IrExpression visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitWhen2(IrWhen expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitWhen(expression);
    }

    public IrBranch visitBranch(IrBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        branch.transformChildren(this, null);
        return branch;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrBranch visitBranch2(IrBranch branch, Void data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        return visitBranch(branch);
    }

    public IrElseBranch visitElseBranch(IrElseBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        branch.transformChildren(this, null);
        return branch;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrElseBranch visitElseBranch2(IrElseBranch branch, Void data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        return visitElseBranch(branch);
    }

    public IrExpression visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitExpression(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitLoop2(IrLoop loop, Void data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitLoop(loop);
    }

    public IrExpression visitWhileLoop(IrWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitLoop(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitWhileLoop2(IrWhileLoop loop, Void data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitWhileLoop(loop);
    }

    public IrExpression visitDoWhileLoop(IrDoWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitLoop(loop);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDoWhileLoop2(IrDoWhileLoop loop, Void data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitDoWhileLoop(loop);
    }

    public IrExpression visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        return visitExpression(aTry);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitTry2(IrTry aTry, Void data) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        return visitTry(aTry);
    }

    public IrCatch visitCatch(IrCatch aCatch) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        aCatch.transformChildren(this, null);
        return aCatch;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrCatch visitCatch2(IrCatch aCatch, Void data) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        return visitCatch(aCatch);
    }

    public IrExpression visitBreakContinue(IrBreakContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitExpression(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitBreakContinue2(IrBreakContinue jump, Void data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitBreakContinue(jump);
    }

    public IrExpression visitBreak(IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitBreakContinue(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitBreak2(IrBreak jump, Void data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitBreak(jump);
    }

    public IrExpression visitContinue(IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitBreakContinue(jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitContinue2(IrContinue jump, Void data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitContinue(jump);
    }

    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitReturn2(IrReturn expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitReturn(expression);
    }

    public IrExpression visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitThrow2(IrThrow expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitThrow(expression);
    }

    public IrExpression visitDynamicExpression(IrDynamicExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDynamicExpression2(IrDynamicExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDynamicExpression(expression);
    }

    public IrExpression visitDynamicOperatorExpression(IrDynamicOperatorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDynamicExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDynamicOperatorExpression(expression);
    }

    public IrExpression visitDynamicMemberExpression(IrDynamicMemberExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDynamicExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitDynamicMemberExpression(expression);
    }

    public IrStatement visitErrorDeclaration(IrErrorDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrStatement visitErrorDeclaration2(IrErrorDeclaration declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitErrorDeclaration(declaration);
    }

    public IrExpression visitErrorExpression(IrErrorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitErrorExpression2(IrErrorExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitErrorExpression(expression);
    }

    public IrExpression visitErrorCallExpression(IrErrorCallExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitErrorExpression(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public final IrExpression visitErrorCallExpression2(IrErrorCallExpression expression, Void data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitErrorCallExpression(expression);
    }

    protected final <T extends IrElement> T transformPostfix(T t, Function1<? super T, Unit> body) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        transformChildrenVoid(t);
        body.invoke(t);
        return t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void transformChildrenVoid(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        IrElementTransformerVoidKt.transformChildrenVoid(irElement, this);
    }
}
