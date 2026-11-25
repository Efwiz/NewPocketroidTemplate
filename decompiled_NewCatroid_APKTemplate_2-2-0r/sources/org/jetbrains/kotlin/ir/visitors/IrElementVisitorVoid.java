package org.jetbrains.kotlin.ir.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
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

/* JADX INFO: compiled from: IrElementVisitorVoid.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0098\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001a\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000fH\u0016J\u001a\u0010\u000e\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001a\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001a\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0017H\u0016J\u001a\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00172\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u0018\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0019H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00192\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0014\u0010\u001a\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0016J\u001e\u0010\u001a\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u001b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001a\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020 H\u0016J\u001a\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020 2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010!\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\"H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\"2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020$H\u0016J\u001a\u0010#\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020$2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0014\u0010%\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030&H\u0016J\u001e\u0010%\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030&2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010'\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020(H\u0016J\u001a\u0010'\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020(2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010)\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020*H\u0016J\u001a\u0010)\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020*2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010+\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020,H\u0016J\u001a\u0010+\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020,2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010-\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020.H\u0016J\u001a\u0010-\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020.2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010/\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u000200H\u0016J\u001a\u0010/\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u0002002\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00101\u001a\u00020\u00022\u0006\u0010\t\u001a\u000202H\u0016J\u001a\u00101\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00103\u001a\u00020\u00022\u0006\u0010\t\u001a\u000204H\u0016J\u001a\u00103\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00105\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u000206H\u0016J\u001a\u00105\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u0002062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00107\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u000208H\u0016J\u001a\u00107\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u0002082\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00109\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020:H\u0016J\u001a\u00109\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020:2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010;\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020<H\u0016J\u001a\u0010;\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020<2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010=\u001a\u00020\u00022\u0006\u0010>\u001a\u00020?H\u0016J\u001a\u0010=\u001a\u00020\u00022\u0006\u0010>\u001a\u00020?2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010@\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020AH\u0016J\u001a\u0010@\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020A2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010B\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020CH\u0016J\u001a\u0010B\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020C2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010D\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020EH\u0016J\u001a\u0010D\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020E2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010F\u001a\u00020\u00022\u0006\u0010G\u001a\u00020HH\u0016J\u001a\u0010F\u001a\u00020\u00022\u0006\u0010G\u001a\u00020H2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010I\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020JH\u0016J\u001a\u0010I\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020J2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010K\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020LH\u0016J\u001a\u0010K\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020L2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010M\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020NH\u0016J\u001a\u0010M\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020N2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010O\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020PH\u0016J\u001a\u0010O\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020P2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010Q\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020RH\u0016J\u001a\u0010Q\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020R2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010S\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020TH\u0016J\u001a\u0010S\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020T2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010U\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020VH\u0016J\u001a\u0010U\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020V2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010W\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020XH\u0016J\u001a\u0010W\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020X2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010Y\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020ZH\u0016J\u001a\u0010Y\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020Z2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010[\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\\H\u0016J\u001a\u0010[\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\\2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010]\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020^H\u0016J\u001a\u0010]\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020^2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010_\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020`H\u0016J\u001a\u0010_\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020`2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010a\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020bH\u0016J\u001a\u0010a\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010c\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020dH\u0016J\u001a\u0010c\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020d2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010e\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020fH\u0016J\u001a\u0010e\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010g\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020hH\u0016J\u001a\u0010g\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020h2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010i\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020jH\u0016J\u001a\u0010i\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020j2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010k\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020lH\u0016J\u001a\u0010k\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020l2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010m\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020nH\u0016J\u001a\u0010m\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010o\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020pH\u0016J\u001a\u0010o\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020p2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010q\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020rH\u0016J\u001a\u0010q\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010s\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020tH\u0016J\u001a\u0010s\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020t2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010u\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020vH\u0016J\u001a\u0010u\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020v2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010w\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020xH\u0016J\u001a\u0010w\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020x2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010y\u001a\u00020\u00022\u0006\u0010>\u001a\u00020zH\u0016J\u001a\u0010y\u001a\u00020\u00022\u0006\u0010>\u001a\u00020z2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0014\u0010{\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030|H\u0016J\u001e\u0010{\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030|2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010}\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020~H\u0016J\u001a\u0010}\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020~2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0011\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0080\u0001H\u0016J\u001b\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0080\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0081\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0082\u0001H\u0016J\u001c\u0010\u0081\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0082\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0083\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0084\u0001H\u0016J\u001c\u0010\u0083\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0084\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0085\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0086\u0001H\u0016J\u001c\u0010\u0085\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0086\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0087\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0088\u0001H\u0016J\u001c\u0010\u0087\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0088\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0089\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u008a\u0001H\u0016J\u001c\u0010\u0089\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u008a\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u008b\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u008c\u0001H\u0016J\u001c\u0010\u008b\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u008c\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u008d\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u008e\u0001H\u0016J\u001c\u0010\u008d\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u008e\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u008f\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0090\u0001H\u0016J\u001c\u0010\u008f\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030\u0090\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0091\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0092\u0001H\u0016J\u001c\u0010\u0091\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0092\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0013\u0010\u0093\u0001\u001a\u00020\u00022\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0016J\u001d\u0010\u0093\u0001\u001a\u00020\u00022\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0096\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0097\u0001H\u0016J\u001c\u0010\u0096\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0097\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u0098\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0099\u0001H\u0016J\u001c\u0010\u0098\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u0099\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u009a\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u009b\u0001H\u0016J\u001c\u0010\u009a\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u009b\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u009c\u0001\u001a\u00020\u00022\u0007\u0010\f\u001a\u00030\u009d\u0001H\u0016J\u001c\u0010\u009c\u0001\u001a\u00020\u00022\u0007\u0010\f\u001a\u00030\u009d\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u009e\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u009f\u0001H\u0016J\u001c\u0010\u009e\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030\u009f\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0013\u0010 \u0001\u001a\u00020\u00022\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0016J\u001d\u0010 \u0001\u001a\u00020\u00022\b\u0010¡\u0001\u001a\u00030¢\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010£\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¤\u0001H\u0016J\u001c\u0010£\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¤\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010¥\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030¦\u0001H\u0016J\u001c\u0010¥\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030¦\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010§\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¨\u0001H\u0016J\u001c\u0010§\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¨\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010©\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030ª\u0001H\u0016J\u001c\u0010©\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030ª\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010«\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¬\u0001H\u0016J\u001c\u0010«\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030¬\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010\u00ad\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030®\u0001H\u0016J\u001c\u0010\u00ad\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030®\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010¯\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030°\u0001H\u0016J\u001c\u0010¯\u0001\u001a\u00020\u00022\u0007\u0010\u0005\u001a\u00030°\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010±\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030²\u0001H\u0016J\u001c\u0010±\u0001\u001a\u00020\u00022\u0007\u0010\t\u001a\u00030²\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010³\u0001\u001a\u00020\u00022\u0007\u0010>\u001a\u00030´\u0001H\u0016J\u001c\u0010³\u0001\u001a\u00020\u00022\u0007\u0010>\u001a\u00030´\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016¨\u0006µ\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "visitConstantValue", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitDynamicExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;", "visitSpreadElement", "spread", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitSuspendableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "visitSuspensionPoint", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrElementVisitorVoid extends IrElementVisitor {
    void visitAnonymousInitializer(IrAnonymousInitializer declaration);

    void visitAnonymousInitializer(IrAnonymousInitializer declaration, Void data);

    void visitBlock(IrBlock expression);

    void visitBlock(IrBlock expression, Void data);

    void visitBlockBody(IrBlockBody body);

    void visitBlockBody(IrBlockBody body, Void data);

    void visitBody(IrBody body);

    void visitBody(IrBody body, Void data);

    void visitBranch(IrBranch branch);

    void visitBranch(IrBranch branch, Void data);

    void visitBreak(IrBreak jump);

    void visitBreak(IrBreak jump, Void data);

    void visitBreakContinue(IrBreakContinue jump);

    void visitBreakContinue(IrBreakContinue jump, Void data);

    void visitCall(IrCall expression);

    void visitCall(IrCall expression, Void data);

    void visitCallableReference(IrCallableReference<?> expression);

    void visitCallableReference(IrCallableReference<?> expression, Void data);

    void visitCatch(IrCatch aCatch);

    void visitCatch(IrCatch aCatch, Void data);

    /* JADX INFO: renamed from: visitClass */
    void mo6112visitClass(IrClass declaration);

    void visitClass(IrClass declaration, Void data);

    void visitClassReference(IrClassReference expression);

    void visitClassReference(IrClassReference expression, Void data);

    void visitComposite(IrComposite expression);

    void visitComposite(IrComposite expression, Void data);

    void visitConst(IrConst<?> expression);

    void visitConst(IrConst<?> expression, Void data);

    void visitConstantArray(IrConstantArray expression);

    void visitConstantArray(IrConstantArray expression, Void data);

    void visitConstantObject(IrConstantObject expression);

    void visitConstantObject(IrConstantObject expression, Void data);

    void visitConstantPrimitive(IrConstantPrimitive expression);

    void visitConstantPrimitive(IrConstantPrimitive expression, Void data);

    void visitConstantValue(IrConstantValue expression);

    void visitConstantValue(IrConstantValue expression, Void data);

    void visitConstructor(IrConstructor declaration);

    void visitConstructor(IrConstructor declaration, Void data);

    void visitConstructorCall(IrConstructorCall expression);

    void visitConstructorCall(IrConstructorCall expression, Void data);

    void visitContainerExpression(IrContainerExpression expression);

    void visitContainerExpression(IrContainerExpression expression, Void data);

    void visitContinue(IrContinue jump);

    void visitContinue(IrContinue jump, Void data);

    void visitDeclaration(IrDeclarationBase declaration);

    void visitDeclaration(IrDeclarationBase declaration, Void data);

    void visitDeclarationReference(IrDeclarationReference expression);

    void visitDeclarationReference(IrDeclarationReference expression, Void data);

    void visitDelegatingConstructorCall(IrDelegatingConstructorCall expression);

    void visitDelegatingConstructorCall(IrDelegatingConstructorCall expression, Void data);

    void visitDoWhileLoop(IrDoWhileLoop loop);

    void visitDoWhileLoop(IrDoWhileLoop loop, Void data);

    void visitDynamicExpression(IrDynamicExpression expression);

    void visitDynamicExpression(IrDynamicExpression expression, Void data);

    void visitDynamicMemberExpression(IrDynamicMemberExpression expression);

    void visitDynamicMemberExpression(IrDynamicMemberExpression expression, Void data);

    void visitDynamicOperatorExpression(IrDynamicOperatorExpression expression);

    void visitDynamicOperatorExpression(IrDynamicOperatorExpression expression, Void data);

    /* JADX INFO: renamed from: visitElement */
    void mo6335visitElement(IrElement element);

    void visitElement(IrElement element, Void data);

    void visitElseBranch(IrElseBranch branch);

    void visitElseBranch(IrElseBranch branch, Void data);

    void visitEnumConstructorCall(IrEnumConstructorCall expression);

    void visitEnumConstructorCall(IrEnumConstructorCall expression, Void data);

    void visitEnumEntry(IrEnumEntry declaration);

    void visitEnumEntry(IrEnumEntry declaration, Void data);

    void visitErrorCallExpression(IrErrorCallExpression expression);

    void visitErrorCallExpression(IrErrorCallExpression expression, Void data);

    void visitErrorDeclaration(IrErrorDeclaration declaration);

    void visitErrorDeclaration(IrErrorDeclaration declaration, Void data);

    void visitErrorExpression(IrErrorExpression expression);

    void visitErrorExpression(IrErrorExpression expression, Void data);

    void visitExpression(IrExpression expression);

    void visitExpression(IrExpression expression, Void data);

    void visitExpressionBody(IrExpressionBody body);

    void visitExpressionBody(IrExpressionBody body, Void data);

    void visitExternalPackageFragment(IrExternalPackageFragment declaration);

    void visitExternalPackageFragment(IrExternalPackageFragment declaration, Void data);

    void visitField(IrField declaration);

    void visitField(IrField declaration, Void data);

    void visitFieldAccess(IrFieldAccessExpression expression);

    void visitFieldAccess(IrFieldAccessExpression expression, Void data);

    void visitFile(IrFile declaration);

    void visitFile(IrFile declaration, Void data);

    void visitFunction(IrFunction declaration);

    void visitFunction(IrFunction declaration, Void data);

    void visitFunctionAccess(IrFunctionAccessExpression expression);

    void visitFunctionAccess(IrFunctionAccessExpression expression, Void data);

    void visitFunctionExpression(IrFunctionExpression expression);

    void visitFunctionExpression(IrFunctionExpression expression, Void data);

    void visitFunctionReference(IrFunctionReference expression);

    void visitFunctionReference(IrFunctionReference expression, Void data);

    void visitGetClass(IrGetClass expression);

    void visitGetClass(IrGetClass expression, Void data);

    void visitGetEnumValue(IrGetEnumValue expression);

    void visitGetEnumValue(IrGetEnumValue expression, Void data);

    void visitGetField(IrGetField expression);

    void visitGetField(IrGetField expression, Void data);

    void visitGetObjectValue(IrGetObjectValue expression);

    void visitGetObjectValue(IrGetObjectValue expression, Void data);

    void visitGetValue(IrGetValue expression);

    void visitGetValue(IrGetValue expression, Void data);

    void visitInstanceInitializerCall(IrInstanceInitializerCall expression);

    void visitInstanceInitializerCall(IrInstanceInitializerCall expression, Void data);

    void visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration);

    void visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration, Void data);

    void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression);

    void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression, Void data);

    void visitLoop(IrLoop loop);

    void visitLoop(IrLoop loop, Void data);

    void visitMemberAccess(IrMemberAccessExpression<?> expression);

    void visitMemberAccess(IrMemberAccessExpression<?> expression, Void data);

    void visitModuleFragment(IrModuleFragment declaration);

    void visitModuleFragment(IrModuleFragment declaration, Void data);

    void visitPackageFragment(IrPackageFragment declaration);

    void visitPackageFragment(IrPackageFragment declaration, Void data);

    void visitProperty(IrProperty declaration);

    void visitProperty(IrProperty declaration, Void data);

    void visitPropertyReference(IrPropertyReference expression);

    void visitPropertyReference(IrPropertyReference expression, Void data);

    void visitRawFunctionReference(IrRawFunctionReference expression);

    void visitRawFunctionReference(IrRawFunctionReference expression, Void data);

    void visitReturn(IrReturn expression);

    void visitReturn(IrReturn expression, Void data);

    void visitScript(IrScript declaration);

    void visitScript(IrScript declaration, Void data);

    void visitSetField(IrSetField expression);

    void visitSetField(IrSetField expression, Void data);

    void visitSetValue(IrSetValue expression);

    void visitSetValue(IrSetValue expression, Void data);

    void visitSimpleFunction(IrSimpleFunction declaration);

    void visitSimpleFunction(IrSimpleFunction declaration, Void data);

    void visitSingletonReference(IrGetSingletonValue expression);

    void visitSingletonReference(IrGetSingletonValue expression, Void data);

    void visitSpreadElement(IrSpreadElement spread);

    void visitSpreadElement(IrSpreadElement spread, Void data);

    void visitStringConcatenation(IrStringConcatenation expression);

    void visitStringConcatenation(IrStringConcatenation expression, Void data);

    void visitSuspendableExpression(IrSuspendableExpression expression);

    void visitSuspendableExpression(IrSuspendableExpression expression, Void data);

    void visitSuspensionPoint(IrSuspensionPoint expression);

    void visitSuspensionPoint(IrSuspensionPoint expression, Void data);

    void visitSyntheticBody(IrSyntheticBody body);

    void visitSyntheticBody(IrSyntheticBody body, Void data);

    void visitThrow(IrThrow expression);

    void visitThrow(IrThrow expression, Void data);

    void visitTry(IrTry aTry);

    void visitTry(IrTry aTry, Void data);

    void visitTypeAlias(IrTypeAlias declaration);

    void visitTypeAlias(IrTypeAlias declaration, Void data);

    void visitTypeOperator(IrTypeOperatorCall expression);

    void visitTypeOperator(IrTypeOperatorCall expression, Void data);

    void visitTypeParameter(IrTypeParameter declaration);

    void visitTypeParameter(IrTypeParameter declaration, Void data);

    void visitValueAccess(IrValueAccessExpression expression);

    void visitValueAccess(IrValueAccessExpression expression, Void data);

    void visitValueParameter(IrValueParameter declaration);

    void visitValueParameter(IrValueParameter declaration, Void data);

    void visitVararg(IrVararg expression);

    void visitVararg(IrVararg expression, Void data);

    void visitVariable(IrVariable declaration);

    void visitVariable(IrVariable declaration, Void data);

    void visitWhen(IrWhen expression);

    void visitWhen(IrWhen expression, Void data);

    void visitWhileLoop(IrWhileLoop loop);

    void visitWhileLoop(IrWhileLoop loop, Void data);

    /* JADX INFO: compiled from: IrElementVisitorVoid.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void visitElement(IrElementVisitorVoid irElementVisitorVoid, IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
        }

        public static void visitElement(IrElementVisitorVoid irElementVisitorVoid, IrElement element, Void r2) {
            Intrinsics.checkNotNullParameter(element, "element");
            irElementVisitorVoid.mo6335visitElement(element);
        }

        public static void visitDeclaration(IrElementVisitorVoid irElementVisitorVoid, IrDeclarationBase declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitDeclaration(IrElementVisitorVoid irElementVisitorVoid, IrDeclarationBase declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.mo6335visitElement(declaration);
        }

        public static void visitValueParameter(IrElementVisitorVoid irElementVisitorVoid, IrValueParameter declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitValueParameter(declaration);
        }

        public static void visitValueParameter(IrElementVisitorVoid irElementVisitorVoid, IrValueParameter declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitClass(IrElementVisitorVoid irElementVisitorVoid, IrClass declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.mo6112visitClass(declaration);
        }

        public static void visitClass(IrElementVisitorVoid irElementVisitorVoid, IrClass declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitAnonymousInitializer(IrElementVisitorVoid irElementVisitorVoid, IrAnonymousInitializer declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitAnonymousInitializer(declaration);
        }

        public static void visitAnonymousInitializer(IrElementVisitorVoid irElementVisitorVoid, IrAnonymousInitializer declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitTypeParameter(IrElementVisitorVoid irElementVisitorVoid, IrTypeParameter declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitTypeParameter(declaration);
        }

        public static void visitTypeParameter(IrElementVisitorVoid irElementVisitorVoid, IrTypeParameter declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitFunction(IrElementVisitorVoid irElementVisitorVoid, IrFunction declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitFunction(declaration);
        }

        public static void visitFunction(IrElementVisitorVoid irElementVisitorVoid, IrFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitConstructor(IrElementVisitorVoid irElementVisitorVoid, IrConstructor declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitConstructor(declaration);
        }

        public static void visitConstructor(IrElementVisitorVoid irElementVisitorVoid, IrConstructor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitFunction(declaration);
        }

        public static void visitEnumEntry(IrElementVisitorVoid irElementVisitorVoid, IrEnumEntry declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitEnumEntry(declaration);
        }

        public static void visitEnumEntry(IrElementVisitorVoid irElementVisitorVoid, IrEnumEntry declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitErrorDeclaration(IrElementVisitorVoid irElementVisitorVoid, IrErrorDeclaration declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitErrorDeclaration(declaration);
        }

        public static void visitErrorDeclaration(IrElementVisitorVoid irElementVisitorVoid, IrErrorDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitField(IrElementVisitorVoid irElementVisitorVoid, IrField declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitField(declaration);
        }

        public static void visitField(IrElementVisitorVoid irElementVisitorVoid, IrField declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitLocalDelegatedProperty(IrElementVisitorVoid irElementVisitorVoid, IrLocalDelegatedProperty declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitLocalDelegatedProperty(declaration);
        }

        public static void visitLocalDelegatedProperty(IrElementVisitorVoid irElementVisitorVoid, IrLocalDelegatedProperty declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitModuleFragment(IrElementVisitorVoid irElementVisitorVoid, IrModuleFragment declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitModuleFragment(declaration);
        }

        public static void visitModuleFragment(IrElementVisitorVoid irElementVisitorVoid, IrModuleFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.mo6335visitElement(declaration);
        }

        public static void visitProperty(IrElementVisitorVoid irElementVisitorVoid, IrProperty declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitProperty(declaration);
        }

        public static void visitProperty(IrElementVisitorVoid irElementVisitorVoid, IrProperty declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitScript(IrElementVisitorVoid irElementVisitorVoid, IrScript declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitScript(declaration);
        }

        public static void visitScript(IrElementVisitorVoid irElementVisitorVoid, IrScript declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitSimpleFunction(IrElementVisitorVoid irElementVisitorVoid, IrSimpleFunction declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitSimpleFunction(declaration);
        }

        public static void visitSimpleFunction(IrElementVisitorVoid irElementVisitorVoid, IrSimpleFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitFunction(declaration);
        }

        public static void visitTypeAlias(IrElementVisitorVoid irElementVisitorVoid, IrTypeAlias declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitTypeAlias(declaration);
        }

        public static void visitTypeAlias(IrElementVisitorVoid irElementVisitorVoid, IrTypeAlias declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitVariable(IrElementVisitorVoid irElementVisitorVoid, IrVariable declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitVariable(declaration);
        }

        public static void visitVariable(IrElementVisitorVoid irElementVisitorVoid, IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitDeclaration(declaration);
        }

        public static void visitPackageFragment(IrElementVisitorVoid irElementVisitorVoid, IrPackageFragment declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitPackageFragment(declaration);
        }

        public static void visitPackageFragment(IrElementVisitorVoid irElementVisitorVoid, IrPackageFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.mo6335visitElement(declaration);
        }

        public static void visitExternalPackageFragment(IrElementVisitorVoid irElementVisitorVoid, IrExternalPackageFragment declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitExternalPackageFragment(declaration);
        }

        public static void visitExternalPackageFragment(IrElementVisitorVoid irElementVisitorVoid, IrExternalPackageFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitPackageFragment(declaration);
        }

        public static void visitFile(IrElementVisitorVoid irElementVisitorVoid, IrFile declaration, Void r2) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitFile(declaration);
        }

        public static void visitFile(IrElementVisitorVoid irElementVisitorVoid, IrFile declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            irElementVisitorVoid.visitPackageFragment(declaration);
        }

        public static void visitExpression(IrElementVisitorVoid irElementVisitorVoid, IrExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitExpression(IrElementVisitorVoid irElementVisitorVoid, IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.mo6335visitElement(expression);
        }

        public static void visitBody(IrElementVisitorVoid irElementVisitorVoid, IrBody body, Void r2) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitBody(body);
        }

        public static void visitBody(IrElementVisitorVoid irElementVisitorVoid, IrBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.mo6335visitElement(body);
        }

        public static void visitExpressionBody(IrElementVisitorVoid irElementVisitorVoid, IrExpressionBody body, Void r2) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitExpressionBody(body);
        }

        public static void visitExpressionBody(IrElementVisitorVoid irElementVisitorVoid, IrExpressionBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitBody(body);
        }

        public static void visitBlockBody(IrElementVisitorVoid irElementVisitorVoid, IrBlockBody body, Void r2) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitBlockBody(body);
        }

        public static void visitBlockBody(IrElementVisitorVoid irElementVisitorVoid, IrBlockBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitBody(body);
        }

        public static void visitDeclarationReference(IrElementVisitorVoid irElementVisitorVoid, IrDeclarationReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitDeclarationReference(IrElementVisitorVoid irElementVisitorVoid, IrDeclarationReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitMemberAccess(IrElementVisitorVoid irElementVisitorVoid, IrMemberAccessExpression<?> expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitMemberAccess(expression);
        }

        public static void visitMemberAccess(IrElementVisitorVoid irElementVisitorVoid, IrMemberAccessExpression<?> expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitFunctionAccess(IrElementVisitorVoid irElementVisitorVoid, IrFunctionAccessExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionAccess(expression);
        }

        public static void visitFunctionAccess(IrElementVisitorVoid irElementVisitorVoid, IrFunctionAccessExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitMemberAccess(expression);
        }

        public static void visitConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrConstructorCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstructorCall(expression);
        }

        public static void visitConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionAccess(expression);
        }

        public static void visitSingletonReference(IrElementVisitorVoid irElementVisitorVoid, IrGetSingletonValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSingletonReference(expression);
        }

        public static void visitSingletonReference(IrElementVisitorVoid irElementVisitorVoid, IrGetSingletonValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitGetObjectValue(IrElementVisitorVoid irElementVisitorVoid, IrGetObjectValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitGetObjectValue(expression);
        }

        public static void visitGetObjectValue(IrElementVisitorVoid irElementVisitorVoid, IrGetObjectValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSingletonReference(expression);
        }

        public static void visitGetEnumValue(IrElementVisitorVoid irElementVisitorVoid, IrGetEnumValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitGetEnumValue(expression);
        }

        public static void visitGetEnumValue(IrElementVisitorVoid irElementVisitorVoid, IrGetEnumValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSingletonReference(expression);
        }

        public static void visitRawFunctionReference(IrElementVisitorVoid irElementVisitorVoid, IrRawFunctionReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitRawFunctionReference(expression);
        }

        public static void visitRawFunctionReference(IrElementVisitorVoid irElementVisitorVoid, IrRawFunctionReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitContainerExpression(IrElementVisitorVoid irElementVisitorVoid, IrContainerExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitContainerExpression(expression);
        }

        public static void visitContainerExpression(IrElementVisitorVoid irElementVisitorVoid, IrContainerExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitBlock(IrElementVisitorVoid irElementVisitorVoid, IrBlock expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitBlock(expression);
        }

        public static void visitBlock(IrElementVisitorVoid irElementVisitorVoid, IrBlock expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitContainerExpression(expression);
        }

        public static void visitComposite(IrElementVisitorVoid irElementVisitorVoid, IrComposite expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitComposite(expression);
        }

        public static void visitComposite(IrElementVisitorVoid irElementVisitorVoid, IrComposite expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitContainerExpression(expression);
        }

        public static void visitSyntheticBody(IrElementVisitorVoid irElementVisitorVoid, IrSyntheticBody body, Void r2) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitSyntheticBody(body);
        }

        public static void visitSyntheticBody(IrElementVisitorVoid irElementVisitorVoid, IrSyntheticBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            irElementVisitorVoid.visitBody(body);
        }

        public static void visitBreakContinue(IrElementVisitorVoid irElementVisitorVoid, IrBreakContinue jump, Void r2) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitBreakContinue(jump);
        }

        public static void visitBreakContinue(IrElementVisitorVoid irElementVisitorVoid, IrBreakContinue jump) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitExpression(jump);
        }

        public static void visitBreak(IrElementVisitorVoid irElementVisitorVoid, IrBreak jump, Void r2) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitBreak(jump);
        }

        public static void visitBreak(IrElementVisitorVoid irElementVisitorVoid, IrBreak jump) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitBreakContinue(jump);
        }

        public static void visitContinue(IrElementVisitorVoid irElementVisitorVoid, IrContinue jump, Void r2) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitContinue(jump);
        }

        public static void visitContinue(IrElementVisitorVoid irElementVisitorVoid, IrContinue jump) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            irElementVisitorVoid.visitBreakContinue(jump);
        }

        public static void visitCall(IrElementVisitorVoid irElementVisitorVoid, IrCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitCall(expression);
        }

        public static void visitCall(IrElementVisitorVoid irElementVisitorVoid, IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionAccess(expression);
        }

        public static void visitCallableReference(IrElementVisitorVoid irElementVisitorVoid, IrCallableReference<?> expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitCallableReference(expression);
        }

        public static void visitCallableReference(IrElementVisitorVoid irElementVisitorVoid, IrCallableReference<?> expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitMemberAccess(expression);
        }

        public static void visitFunctionReference(IrElementVisitorVoid irElementVisitorVoid, IrFunctionReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionReference(expression);
        }

        public static void visitFunctionReference(IrElementVisitorVoid irElementVisitorVoid, IrFunctionReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitCallableReference(expression);
        }

        public static void visitPropertyReference(IrElementVisitorVoid irElementVisitorVoid, IrPropertyReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitPropertyReference(expression);
        }

        public static void visitPropertyReference(IrElementVisitorVoid irElementVisitorVoid, IrPropertyReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitCallableReference(expression);
        }

        public static void visitLocalDelegatedPropertyReference(IrElementVisitorVoid irElementVisitorVoid, IrLocalDelegatedPropertyReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitLocalDelegatedPropertyReference(expression);
        }

        public static void visitLocalDelegatedPropertyReference(IrElementVisitorVoid irElementVisitorVoid, IrLocalDelegatedPropertyReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitCallableReference(expression);
        }

        public static void visitClassReference(IrElementVisitorVoid irElementVisitorVoid, IrClassReference expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitClassReference(expression);
        }

        public static void visitClassReference(IrElementVisitorVoid irElementVisitorVoid, IrClassReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitConst(IrElementVisitorVoid irElementVisitorVoid, IrConst<?> expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConst(expression);
        }

        public static void visitConst(IrElementVisitorVoid irElementVisitorVoid, IrConst<?> expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitConstantValue(IrElementVisitorVoid irElementVisitorVoid, IrConstantValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantValue(expression);
        }

        public static void visitConstantValue(IrElementVisitorVoid irElementVisitorVoid, IrConstantValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitConstantPrimitive(IrElementVisitorVoid irElementVisitorVoid, IrConstantPrimitive expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantPrimitive(expression);
        }

        public static void visitConstantPrimitive(IrElementVisitorVoid irElementVisitorVoid, IrConstantPrimitive expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantValue(expression);
        }

        public static void visitConstantObject(IrElementVisitorVoid irElementVisitorVoid, IrConstantObject expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantObject(expression);
        }

        public static void visitConstantObject(IrElementVisitorVoid irElementVisitorVoid, IrConstantObject expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantValue(expression);
        }

        public static void visitConstantArray(IrElementVisitorVoid irElementVisitorVoid, IrConstantArray expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantArray(expression);
        }

        public static void visitConstantArray(IrElementVisitorVoid irElementVisitorVoid, IrConstantArray expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitConstantValue(expression);
        }

        public static void visitDelegatingConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrDelegatingConstructorCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDelegatingConstructorCall(expression);
        }

        public static void visitDelegatingConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrDelegatingConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionAccess(expression);
        }

        public static void visitDynamicExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDynamicExpression(expression);
        }

        public static void visitDynamicExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitDynamicOperatorExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicOperatorExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDynamicOperatorExpression(expression);
        }

        public static void visitDynamicOperatorExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicOperatorExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDynamicExpression(expression);
        }

        public static void visitDynamicMemberExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicMemberExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDynamicMemberExpression(expression);
        }

        public static void visitDynamicMemberExpression(IrElementVisitorVoid irElementVisitorVoid, IrDynamicMemberExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDynamicExpression(expression);
        }

        public static void visitEnumConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrEnumConstructorCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitEnumConstructorCall(expression);
        }

        public static void visitEnumConstructorCall(IrElementVisitorVoid irElementVisitorVoid, IrEnumConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionAccess(expression);
        }

        public static void visitErrorExpression(IrElementVisitorVoid irElementVisitorVoid, IrErrorExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitErrorExpression(expression);
        }

        public static void visitErrorExpression(IrElementVisitorVoid irElementVisitorVoid, IrErrorExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitErrorCallExpression(IrElementVisitorVoid irElementVisitorVoid, IrErrorCallExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitErrorCallExpression(expression);
        }

        public static void visitErrorCallExpression(IrElementVisitorVoid irElementVisitorVoid, IrErrorCallExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitErrorExpression(expression);
        }

        public static void visitFieldAccess(IrElementVisitorVoid irElementVisitorVoid, IrFieldAccessExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFieldAccess(expression);
        }

        public static void visitFieldAccess(IrElementVisitorVoid irElementVisitorVoid, IrFieldAccessExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitGetField(IrElementVisitorVoid irElementVisitorVoid, IrGetField expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitGetField(expression);
        }

        public static void visitGetField(IrElementVisitorVoid irElementVisitorVoid, IrGetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFieldAccess(expression);
        }

        public static void visitSetField(IrElementVisitorVoid irElementVisitorVoid, IrSetField expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSetField(expression);
        }

        public static void visitSetField(IrElementVisitorVoid irElementVisitorVoid, IrSetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFieldAccess(expression);
        }

        public static void visitFunctionExpression(IrElementVisitorVoid irElementVisitorVoid, IrFunctionExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitFunctionExpression(expression);
        }

        public static void visitFunctionExpression(IrElementVisitorVoid irElementVisitorVoid, IrFunctionExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitGetClass(IrElementVisitorVoid irElementVisitorVoid, IrGetClass expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitGetClass(expression);
        }

        public static void visitGetClass(IrElementVisitorVoid irElementVisitorVoid, IrGetClass expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitInstanceInitializerCall(IrElementVisitorVoid irElementVisitorVoid, IrInstanceInitializerCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitInstanceInitializerCall(expression);
        }

        public static void visitInstanceInitializerCall(IrElementVisitorVoid irElementVisitorVoid, IrInstanceInitializerCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitLoop(IrElementVisitorVoid irElementVisitorVoid, IrLoop loop, Void r2) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitLoop(loop);
        }

        public static void visitLoop(IrElementVisitorVoid irElementVisitorVoid, IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitExpression(loop);
        }

        public static void visitWhileLoop(IrElementVisitorVoid irElementVisitorVoid, IrWhileLoop loop, Void r2) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitWhileLoop(loop);
        }

        public static void visitWhileLoop(IrElementVisitorVoid irElementVisitorVoid, IrWhileLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitLoop(loop);
        }

        public static void visitDoWhileLoop(IrElementVisitorVoid irElementVisitorVoid, IrDoWhileLoop loop, Void r2) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitDoWhileLoop(loop);
        }

        public static void visitDoWhileLoop(IrElementVisitorVoid irElementVisitorVoid, IrDoWhileLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            irElementVisitorVoid.visitLoop(loop);
        }

        public static void visitReturn(IrElementVisitorVoid irElementVisitorVoid, IrReturn expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitReturn(expression);
        }

        public static void visitReturn(IrElementVisitorVoid irElementVisitorVoid, IrReturn expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitStringConcatenation(IrElementVisitorVoid irElementVisitorVoid, IrStringConcatenation expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitStringConcatenation(expression);
        }

        public static void visitStringConcatenation(IrElementVisitorVoid irElementVisitorVoid, IrStringConcatenation expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitSuspensionPoint(IrElementVisitorVoid irElementVisitorVoid, IrSuspensionPoint expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSuspensionPoint(expression);
        }

        public static void visitSuspensionPoint(IrElementVisitorVoid irElementVisitorVoid, IrSuspensionPoint expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitSuspendableExpression(IrElementVisitorVoid irElementVisitorVoid, IrSuspendableExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSuspendableExpression(expression);
        }

        public static void visitSuspendableExpression(IrElementVisitorVoid irElementVisitorVoid, IrSuspendableExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitThrow(IrElementVisitorVoid irElementVisitorVoid, IrThrow expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitThrow(expression);
        }

        public static void visitThrow(IrElementVisitorVoid irElementVisitorVoid, IrThrow expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitTry(IrElementVisitorVoid irElementVisitorVoid, IrTry aTry, Void r2) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            irElementVisitorVoid.visitTry(aTry);
        }

        public static void visitTry(IrElementVisitorVoid irElementVisitorVoid, IrTry aTry) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            irElementVisitorVoid.visitExpression(aTry);
        }

        public static void visitCatch(IrElementVisitorVoid irElementVisitorVoid, IrCatch aCatch, Void r2) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            irElementVisitorVoid.visitCatch(aCatch);
        }

        public static void visitCatch(IrElementVisitorVoid irElementVisitorVoid, IrCatch aCatch) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            irElementVisitorVoid.mo6335visitElement(aCatch);
        }

        public static void visitTypeOperator(IrElementVisitorVoid irElementVisitorVoid, IrTypeOperatorCall expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitTypeOperator(expression);
        }

        public static void visitTypeOperator(IrElementVisitorVoid irElementVisitorVoid, IrTypeOperatorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitValueAccess(IrElementVisitorVoid irElementVisitorVoid, IrValueAccessExpression expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitValueAccess(expression);
        }

        public static void visitValueAccess(IrElementVisitorVoid irElementVisitorVoid, IrValueAccessExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitDeclarationReference(expression);
        }

        public static void visitGetValue(IrElementVisitorVoid irElementVisitorVoid, IrGetValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitGetValue(expression);
        }

        public static void visitGetValue(IrElementVisitorVoid irElementVisitorVoid, IrGetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitValueAccess(expression);
        }

        public static void visitSetValue(IrElementVisitorVoid irElementVisitorVoid, IrSetValue expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitSetValue(expression);
        }

        public static void visitSetValue(IrElementVisitorVoid irElementVisitorVoid, IrSetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitValueAccess(expression);
        }

        public static void visitVararg(IrElementVisitorVoid irElementVisitorVoid, IrVararg expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitVararg(expression);
        }

        public static void visitVararg(IrElementVisitorVoid irElementVisitorVoid, IrVararg expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitSpreadElement(IrElementVisitorVoid irElementVisitorVoid, IrSpreadElement spread, Void r2) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            irElementVisitorVoid.visitSpreadElement(spread);
        }

        public static void visitSpreadElement(IrElementVisitorVoid irElementVisitorVoid, IrSpreadElement spread) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            irElementVisitorVoid.mo6335visitElement(spread);
        }

        public static void visitWhen(IrElementVisitorVoid irElementVisitorVoid, IrWhen expression, Void r2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitWhen(expression);
        }

        public static void visitWhen(IrElementVisitorVoid irElementVisitorVoid, IrWhen expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            irElementVisitorVoid.visitExpression(expression);
        }

        public static void visitBranch(IrElementVisitorVoid irElementVisitorVoid, IrBranch branch, Void r2) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            irElementVisitorVoid.visitBranch(branch);
        }

        public static void visitBranch(IrElementVisitorVoid irElementVisitorVoid, IrBranch branch) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            irElementVisitorVoid.mo6335visitElement(branch);
        }

        public static void visitElseBranch(IrElementVisitorVoid irElementVisitorVoid, IrElseBranch branch, Void r2) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            irElementVisitorVoid.visitElseBranch(branch);
        }

        public static void visitElseBranch(IrElementVisitorVoid irElementVisitorVoid, IrElseBranch branch) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            irElementVisitorVoid.visitBranch(branch);
        }
    }
}
