package org.jetbrains.kotlin.ir.visitors;

import kotlin.Metadata;
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

/* JADX INFO: compiled from: IrElementTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009e\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002J\u001d\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001f2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010 J\u001d\u0010!\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\"2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010#J!\u0010$\u001a\u00020\u00032\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030%2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010&J\u001d\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020,2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020/2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00020\u000b2\u0006\u0010\f\u001a\u0002022\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00103J!\u00104\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u0003052\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00106J\u001d\u00107\u001a\u0002082\u0006\u0010\f\u001a\u0002092\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010:J\u001d\u0010;\u001a\u0002082\u0006\u0010\f\u001a\u00020<2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010=J\u001d\u0010>\u001a\u0002082\u0006\u0010\f\u001a\u00020?2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010@J\u001d\u0010A\u001a\u0002082\u0006\u0010\f\u001a\u0002082\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010BJ\u001d\u0010C\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020D2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020G2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010HJ\u001d\u0010I\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020J2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010KJ\u001d\u0010L\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020M2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010NJ\u001d\u0010O\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020P2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010QJ\u001d\u0010R\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020S2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010TJ\u001d\u0010U\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020V2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010WJ\u001d\u0010X\u001a\u00020\u000b2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010[J\u001d\u0010\\\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020]2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010^J\u001d\u0010_\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020`2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010aJ\u001d\u0010b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020c2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010dJ\u001d\u0010e\u001a\u00020\u00032\u0006\u0010f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010gJ\u001d\u0010h\u001a\u00020i2\u0006\u0010\u0018\u001a\u00020i2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010jJ\u001d\u0010k\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020l2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010mJ\u001d\u0010n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020o2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010pJ\u001d\u0010q\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020r2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010sJ\u001d\u0010t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020u2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010vJ\u001d\u0010w\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020x2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010yJ\u001d\u0010z\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010{J\u001d\u0010|\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020}2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010~J \u0010\u007f\u001a\u00030\u0080\u00012\u0007\u0010\u0006\u001a\u00030\u0080\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0081\u0001J \u0010\u0082\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0083\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0084\u0001J \u0010\u0085\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030\u0086\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0087\u0001J!\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0007\u0010\u0006\u001a\u00030\u0089\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008a\u0001J \u0010\u008b\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u008c\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008d\u0001J \u0010\u008e\u0001\u001a\u00020\u00032\u0007\u0010\f\u001a\u00030\u008f\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0090\u0001J \u0010\u0091\u0001\u001a\u00020\u00032\u0007\u0010\f\u001a\u00030\u0092\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0093\u0001J \u0010\u0094\u0001\u001a\u00020\u00032\u0007\u0010\f\u001a\u00030\u0095\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0096\u0001J \u0010\u0097\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030\u0098\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0099\u0001J \u0010\u009a\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030\u009b\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009c\u0001J \u0010\u009d\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030\u009e\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009f\u0001J \u0010 \u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030¡\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¢\u0001J \u0010£\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030¤\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¥\u0001J \u0010¦\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030§\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¨\u0001J \u0010©\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030ª\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010«\u0001J \u0010¬\u0001\u001a\u00020\u00032\u0007\u0010\f\u001a\u00030\u00ad\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010®\u0001J \u0010¯\u0001\u001a\u00020\u000b2\u0007\u0010Y\u001a\u00030°\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010±\u0001J$\u0010²\u0001\u001a\u00020\u00032\u000b\u0010\f\u001a\u0007\u0012\u0002\b\u00030³\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010´\u0001J!\u0010µ\u0001\u001a\u00030¶\u00012\u0007\u0010\u0006\u001a\u00030¶\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010·\u0001J \u0010¸\u0001\u001a\u00020\u00032\u0007\u0010\u0006\u001a\u00030¹\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010º\u0001J \u0010»\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030¼\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010½\u0001J \u0010¾\u0001\u001a\u00020\u00032\u0007\u0010\f\u001a\u00030¿\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010À\u0001J \u0010Á\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Â\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ã\u0001J \u0010Ä\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Å\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Æ\u0001J \u0010Ç\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030È\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010É\u0001J \u0010Ê\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Ë\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ì\u0001J \u0010Í\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Î\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ï\u0001J \u0010Ð\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030Ñ\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ò\u0001J \u0010Ó\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Ô\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Õ\u0001J\"\u0010Ö\u0001\u001a\u00030×\u00012\b\u0010Ø\u0001\u001a\u00030×\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ù\u0001J \u0010Ú\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Û\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ü\u0001J \u0010Ý\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030Þ\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ß\u0001J \u0010à\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030á\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010â\u0001J \u0010ã\u0001\u001a\u00020\u00102\u0007\u0010\u0011\u001a\u00030ä\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010å\u0001J \u0010æ\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030ç\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010è\u0001J!\u0010é\u0001\u001a\u00020\u000b2\b\u0010ê\u0001\u001a\u00030ë\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ì\u0001J \u0010í\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030î\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ï\u0001J \u0010ð\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030ñ\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ò\u0001J \u0010ó\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030ô\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010õ\u0001J \u0010ö\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030÷\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ø\u0001J \u0010ù\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030ú\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010û\u0001J \u0010ü\u0001\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030ý\u00012\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010þ\u0001J \u0010ÿ\u0001\u001a\u00020\u00052\u0007\u0010\u0006\u001a\u00030\u0080\u00022\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0081\u0002J \u0010\u0082\u0002\u001a\u00020\u000b2\u0007\u0010\f\u001a\u00030\u0083\u00022\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0084\u0002J \u0010\u0085\u0002\u001a\u00020\u000b2\u0007\u0010Y\u001a\u00030\u0086\u00022\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0087\u0002¨\u0006\u0088\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "D", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "Lorg/jetbrains/kotlin/ir/IrElement;", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", ConfigConstants.CONFIG_BRANCH_SECTION, "(Lorg/jetbrains/kotlin/ir/expressions/IrBranch;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBreak;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "aCatch", "(Lorg/jetbrains/kotlin/ir/expressions/IrCatch;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "(Lorg/jetbrains/kotlin/ir/expressions/IrComposite;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitConstantValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContinue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitDynamicExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "(Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturn;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "(Lorg/jetbrains/kotlin/ir/declarations/IrScript;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "spread", "(Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSuspendableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSuspensionPoint", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "(Lorg/jetbrains/kotlin/ir/expressions/IrThrow;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTry;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "(Lorg/jetbrains/kotlin/ir/expressions/IrVararg;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrStatement;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrElementTransformer<D> extends IrElementVisitor<IrElement, D> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    IrElement visitAnonymousInitializer2(IrAnonymousInitializer declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    IrElement visitBlock2(IrBlock expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    IrElement visitBlockBody2(IrBlockBody body, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    IrElement visitBody2(IrBody body, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    IrElement visitBranch2(IrBranch branch, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    IrElement visitBreak2(IrBreak jump, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    IrElement visitBreakContinue2(IrBreakContinue jump, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    IrElement visitCall2(IrCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    IrElement visitCallableReference(IrCallableReference<?> expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    IrElement visitCatch2(IrCatch aCatch, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    IrElement visitClass2(IrClass declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    IrElement visitClassReference2(IrClassReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    IrElement visitComposite2(IrComposite expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    IrElement visitConst(IrConst<?> expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    IrElement visitConstantArray2(IrConstantArray expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    IrElement visitConstantObject2(IrConstantObject expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    IrElement visitConstantPrimitive2(IrConstantPrimitive expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    IrElement visitConstantValue2(IrConstantValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    IrElement visitConstructor2(IrConstructor declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    IrElement visitConstructorCall2(IrConstructorCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    IrElement visitContainerExpression2(IrContainerExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    IrElement visitContinue2(IrContinue jump, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    IrElement visitDeclaration2(IrDeclarationBase declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    IrElement visitDeclarationReference2(IrDeclarationReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    IrElement visitDoWhileLoop2(IrDoWhileLoop loop, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    IrElement visitDynamicExpression2(IrDynamicExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    IrElement visitDynamicMemberExpression2(IrDynamicMemberExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    IrElement visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    IrElement visitElement2(IrElement element, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    IrElement visitElseBranch2(IrElseBranch branch, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    IrElement visitEnumConstructorCall2(IrEnumConstructorCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    IrElement visitEnumEntry2(IrEnumEntry declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    IrElement visitErrorCallExpression2(IrErrorCallExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    IrElement visitErrorDeclaration2(IrErrorDeclaration declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    IrElement visitErrorExpression2(IrErrorExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    IrElement visitExpression2(IrExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    IrElement visitExpressionBody2(IrExpressionBody body, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    IrElement visitExternalPackageFragment2(IrExternalPackageFragment declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    IrElement visitField2(IrField declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    IrElement visitFieldAccess2(IrFieldAccessExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    IrElement visitFile2(IrFile declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    IrElement visitFunction2(IrFunction declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    IrElement visitFunctionAccess2(IrFunctionAccessExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    IrElement visitFunctionExpression2(IrFunctionExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    IrElement visitFunctionReference2(IrFunctionReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    IrElement visitGetClass2(IrGetClass expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    IrElement visitGetEnumValue2(IrGetEnumValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    IrElement visitGetField2(IrGetField expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    IrElement visitGetObjectValue2(IrGetObjectValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    IrElement visitGetValue2(IrGetValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    IrElement visitInstanceInitializerCall2(IrInstanceInitializerCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    IrElement visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    IrElement visitLoop2(IrLoop loop, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    IrElement visitMemberAccess(IrMemberAccessExpression<?> expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    IrElement visitModuleFragment2(IrModuleFragment declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    IrElement visitPackageFragment2(IrPackageFragment declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    IrElement visitProperty2(IrProperty declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    IrElement visitPropertyReference2(IrPropertyReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    IrElement visitRawFunctionReference2(IrRawFunctionReference expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    IrElement visitReturn2(IrReturn expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    IrElement visitScript2(IrScript declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    IrElement visitSetField2(IrSetField expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    IrElement visitSetValue2(IrSetValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    IrElement visitSimpleFunction2(IrSimpleFunction declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    IrElement visitSingletonReference2(IrGetSingletonValue expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    IrElement visitSpreadElement2(IrSpreadElement spread, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    IrElement visitStringConcatenation2(IrStringConcatenation expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    IrElement visitSuspendableExpression2(IrSuspendableExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    IrElement visitSuspensionPoint2(IrSuspensionPoint expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    IrElement visitSyntheticBody2(IrSyntheticBody body, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    IrElement visitThrow2(IrThrow expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    IrElement visitTry2(IrTry aTry, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    IrElement visitTypeAlias2(IrTypeAlias declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    IrElement visitTypeOperator2(IrTypeOperatorCall expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    IrElement visitTypeParameter2(IrTypeParameter declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    IrElement visitValueAccess2(IrValueAccessExpression expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    IrElement visitValueParameter2(IrValueParameter declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    IrElement visitVararg2(IrVararg expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    IrElement visitVariable2(IrVariable declaration, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    IrElement visitWhen2(IrWhen expression, D data);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    IrElement visitWhileLoop2(IrWhileLoop loop, D data);

    /* JADX INFO: compiled from: IrElementTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <D> IrElement visitElement(IrElementTransformer<? super D> irElementTransformer, IrElement element, D d) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.transformChildren(irElementTransformer, d);
            return element;
        }

        public static <D> IrStatement visitDeclaration(IrElementTransformer<? super D> irElementTransformer, IrDeclarationBase declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.transformChildren(irElementTransformer, d);
            return declaration;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitValueParameter(IrElementTransformer<? super D> irElementTransformer, IrValueParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitClass(IrElementTransformer<? super D> irElementTransformer, IrClass declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitAnonymousInitializer(IrElementTransformer<? super D> irElementTransformer, IrAnonymousInitializer declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitTypeParameter(IrElementTransformer<? super D> irElementTransformer, IrTypeParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitFunction(IrElementTransformer<? super D> irElementTransformer, IrFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitConstructor(IrElementTransformer<? super D> irElementTransformer, IrConstructor declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitFunction2((IrFunction) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitEnumEntry(IrElementTransformer<? super D> irElementTransformer, IrEnumEntry declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitErrorDeclaration(IrElementTransformer<? super D> irElementTransformer, IrErrorDeclaration declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitField(IrElementTransformer<? super D> irElementTransformer, IrField declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitLocalDelegatedProperty(IrElementTransformer<? super D> irElementTransformer, IrLocalDelegatedProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        public static <D> IrModuleFragment visitModuleFragment(IrElementTransformer<? super D> irElementTransformer, IrModuleFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.transformChildren(irElementTransformer, d);
            return declaration;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitProperty(IrElementTransformer<? super D> irElementTransformer, IrProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitScript(IrElementTransformer<? super D> irElementTransformer, IrScript declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitSimpleFunction(IrElementTransformer<? super D> irElementTransformer, IrSimpleFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitFunction2((IrFunction) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitTypeAlias(IrElementTransformer<? super D> irElementTransformer, IrTypeAlias declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrStatement visitVariable(IrElementTransformer<? super D> irElementTransformer, IrVariable declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitDeclaration2((IrDeclarationBase) declaration, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitPackageFragment(IrElementTransformer<? super D> irElementTransformer, IrPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementTransformer.visitElement2((IrElement) declaration, (Object) d);
        }

        public static <D> IrExternalPackageFragment visitExternalPackageFragment(IrElementTransformer<? super D> irElementTransformer, IrExternalPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.transformChildren(irElementTransformer, d);
            return declaration;
        }

        public static <D> IrFile visitFile(IrElementTransformer<? super D> irElementTransformer, IrFile declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            declaration.transformChildren(irElementTransformer, d);
            return declaration;
        }

        public static <D> IrExpression visitExpression(IrElementTransformer<? super D> irElementTransformer, IrExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.transformChildren(irElementTransformer, d);
            return expression;
        }

        public static <D> IrBody visitBody(IrElementTransformer<? super D> irElementTransformer, IrBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            body.transformChildren(irElementTransformer, d);
            return body;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrBody visitExpressionBody(IrElementTransformer<? super D> irElementTransformer, IrExpressionBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementTransformer.visitBody2((IrBody) body, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrBody visitBlockBody(IrElementTransformer<? super D> irElementTransformer, IrBlockBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementTransformer.visitBody2((IrBody) body, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitDeclarationReference(IrElementTransformer<? super D> irElementTransformer, IrDeclarationReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitMemberAccess(IrElementTransformer<? super D> irElementTransformer, IrMemberAccessExpression<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitFunctionAccess(IrElementTransformer<? super D> irElementTransformer, IrFunctionAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitMemberAccess((IrMemberAccessExpression<?>) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitConstructorCall(IrElementTransformer<? super D> irElementTransformer, IrConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFunctionAccess2((IrFunctionAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitSingletonReference(IrElementTransformer<? super D> irElementTransformer, IrGetSingletonValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitGetObjectValue(IrElementTransformer<? super D> irElementTransformer, IrGetObjectValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitSingletonReference2((IrGetSingletonValue) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitGetEnumValue(IrElementTransformer<? super D> irElementTransformer, IrGetEnumValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitSingletonReference2((IrGetSingletonValue) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitRawFunctionReference(IrElementTransformer<? super D> irElementTransformer, IrRawFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitContainerExpression(IrElementTransformer<? super D> irElementTransformer, IrContainerExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitBlock(IrElementTransformer<? super D> irElementTransformer, IrBlock expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitContainerExpression2((IrContainerExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitComposite(IrElementTransformer<? super D> irElementTransformer, IrComposite expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitContainerExpression2((IrContainerExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrBody visitSyntheticBody(IrElementTransformer<? super D> irElementTransformer, IrSyntheticBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementTransformer.visitBody2((IrBody) body, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitBreakContinue(IrElementTransformer<? super D> irElementTransformer, IrBreakContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementTransformer.visitExpression2((IrExpression) jump, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitBreak(IrElementTransformer<? super D> irElementTransformer, IrBreak jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementTransformer.visitBreakContinue2((IrBreakContinue) jump, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitContinue(IrElementTransformer<? super D> irElementTransformer, IrContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementTransformer.visitBreakContinue2((IrBreakContinue) jump, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitCall(IrElementTransformer<? super D> irElementTransformer, IrCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFunctionAccess2((IrFunctionAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitCallableReference(IrElementTransformer<? super D> irElementTransformer, IrCallableReference<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitMemberAccess((IrMemberAccessExpression<?>) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitFunctionReference(IrElementTransformer<? super D> irElementTransformer, IrFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitCallableReference((IrCallableReference<?>) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitPropertyReference(IrElementTransformer<? super D> irElementTransformer, IrPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitCallableReference((IrCallableReference<?>) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitLocalDelegatedPropertyReference(IrElementTransformer<? super D> irElementTransformer, IrLocalDelegatedPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitCallableReference((IrCallableReference<?>) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitClassReference(IrElementTransformer<? super D> irElementTransformer, IrClassReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitConst(IrElementTransformer<? super D> irElementTransformer, IrConst<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        public static <D> IrConstantValue visitConstantValue(IrElementTransformer<? super D> irElementTransformer, IrConstantValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.transformChildren(irElementTransformer, d);
            return expression;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrConstantValue visitConstantPrimitive(IrElementTransformer<? super D> irElementTransformer, IrConstantPrimitive expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitConstantValue2((IrConstantValue) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrConstantValue visitConstantObject(IrElementTransformer<? super D> irElementTransformer, IrConstantObject expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitConstantValue2((IrConstantValue) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrConstantValue visitConstantArray(IrElementTransformer<? super D> irElementTransformer, IrConstantArray expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitConstantValue2((IrConstantValue) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitDelegatingConstructorCall(IrElementTransformer<? super D> irElementTransformer, IrDelegatingConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFunctionAccess2((IrFunctionAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitDynamicExpression(IrElementTransformer<? super D> irElementTransformer, IrDynamicExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitDynamicOperatorExpression(IrElementTransformer<? super D> irElementTransformer, IrDynamicOperatorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDynamicExpression2((IrDynamicExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitDynamicMemberExpression(IrElementTransformer<? super D> irElementTransformer, IrDynamicMemberExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDynamicExpression2((IrDynamicExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitEnumConstructorCall(IrElementTransformer<? super D> irElementTransformer, IrEnumConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFunctionAccess2((IrFunctionAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitErrorExpression(IrElementTransformer<? super D> irElementTransformer, IrErrorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitErrorCallExpression(IrElementTransformer<? super D> irElementTransformer, IrErrorCallExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitErrorExpression2((IrErrorExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitFieldAccess(IrElementTransformer<? super D> irElementTransformer, IrFieldAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitGetField(IrElementTransformer<? super D> irElementTransformer, IrGetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFieldAccess2((IrFieldAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitSetField(IrElementTransformer<? super D> irElementTransformer, IrSetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitFieldAccess2((IrFieldAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrElement visitFunctionExpression(IrElementTransformer<? super D> irElementTransformer, IrFunctionExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitGetClass(IrElementTransformer<? super D> irElementTransformer, IrGetClass expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitInstanceInitializerCall(IrElementTransformer<? super D> irElementTransformer, IrInstanceInitializerCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitLoop(IrElementTransformer<? super D> irElementTransformer, IrLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementTransformer.visitExpression2((IrExpression) loop, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitWhileLoop(IrElementTransformer<? super D> irElementTransformer, IrWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementTransformer.visitLoop2((IrLoop) loop, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitDoWhileLoop(IrElementTransformer<? super D> irElementTransformer, IrDoWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementTransformer.visitLoop2((IrLoop) loop, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitReturn(IrElementTransformer<? super D> irElementTransformer, IrReturn expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitStringConcatenation(IrElementTransformer<? super D> irElementTransformer, IrStringConcatenation expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitSuspensionPoint(IrElementTransformer<? super D> irElementTransformer, IrSuspensionPoint expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitSuspendableExpression(IrElementTransformer<? super D> irElementTransformer, IrSuspendableExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitThrow(IrElementTransformer<? super D> irElementTransformer, IrThrow expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitTry(IrElementTransformer<? super D> irElementTransformer, IrTry aTry, D d) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            return irElementTransformer.visitExpression2((IrExpression) aTry, (Object) d);
        }

        public static <D> IrCatch visitCatch(IrElementTransformer<? super D> irElementTransformer, IrCatch aCatch, D d) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            aCatch.transformChildren(irElementTransformer, d);
            return aCatch;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitTypeOperator(IrElementTransformer<? super D> irElementTransformer, IrTypeOperatorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitValueAccess(IrElementTransformer<? super D> irElementTransformer, IrValueAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitDeclarationReference2((IrDeclarationReference) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitGetValue(IrElementTransformer<? super D> irElementTransformer, IrGetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitValueAccess2((IrValueAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitSetValue(IrElementTransformer<? super D> irElementTransformer, IrSetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitValueAccess2((IrValueAccessExpression) expression, (Object) d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitVararg(IrElementTransformer<? super D> irElementTransformer, IrVararg expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        public static <D> IrSpreadElement visitSpreadElement(IrElementTransformer<? super D> irElementTransformer, IrSpreadElement spread, D d) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            spread.transformChildren(irElementTransformer, d);
            return spread;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <D> IrExpression visitWhen(IrElementTransformer<? super D> irElementTransformer, IrWhen expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementTransformer.visitExpression2((IrExpression) expression, (Object) d);
        }

        public static <D> IrBranch visitBranch(IrElementTransformer<? super D> irElementTransformer, IrBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            branch.transformChildren(irElementTransformer, d);
            return branch;
        }

        public static <D> IrElseBranch visitElseBranch(IrElementTransformer<? super D> irElementTransformer, IrElseBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            branch.transformChildren(irElementTransformer, d);
            return branch;
        }
    }
}
