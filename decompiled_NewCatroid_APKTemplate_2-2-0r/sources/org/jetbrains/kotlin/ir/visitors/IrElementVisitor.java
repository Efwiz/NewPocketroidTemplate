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

/* JADX INFO: compiled from: IrElementVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¨\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00002\u00020\u0003J\u001d\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\bJ\u001d\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010J\u001d\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0017J\u001d\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020 2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J!\u0010\"\u001a\u00028\u00002\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010(J\u001d\u0010)\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020*2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010+J\u001d\u0010,\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020-2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010.J\u001d\u0010/\u001a\u00028\u00002\u0006\u0010\n\u001a\u0002002\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00101J!\u00102\u001a\u00028\u00002\n\u0010\n\u001a\u0006\u0012\u0002\b\u0003032\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00104J\u001d\u00105\u001a\u00028\u00002\u0006\u0010\n\u001a\u0002062\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00107J\u001d\u00108\u001a\u00028\u00002\u0006\u0010\n\u001a\u0002092\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010:J\u001d\u0010;\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020<2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010=J\u001d\u0010>\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020?2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010@J\u001d\u0010A\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020B2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010CJ\u001d\u0010D\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020E2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010FJ\u001d\u0010G\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020H2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010IJ\u001d\u0010J\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020K2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010LJ\u001d\u0010M\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020N2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010OJ\u001d\u0010P\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020Q2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010RJ\u001d\u0010S\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020T2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010UJ\u001d\u0010V\u001a\u00028\u00002\u0006\u0010W\u001a\u00020X2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010YJ\u001d\u0010Z\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020[2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\\J\u001d\u0010]\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020^2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010_J\u001d\u0010`\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020a2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010bJ\u001d\u0010c\u001a\u00028\u00002\u0006\u0010d\u001a\u00020e2\u0006\u0010\u0007\u001a\u00028\u0001H&¢\u0006\u0002\u0010fJ\u001d\u0010g\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020h2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010iJ\u001d\u0010j\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020k2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010lJ\u001d\u0010m\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020n2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010oJ\u001d\u0010p\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020q2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010rJ\u001d\u0010s\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020t2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010uJ\u001d\u0010v\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020w2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010xJ\u001d\u0010y\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020z2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010{J\u001d\u0010|\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020}2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010~J\u001f\u0010\u007f\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030\u0080\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0081\u0001J \u0010\u0082\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030\u0083\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0001J \u0010\u0085\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u0086\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0087\u0001J \u0010\u0088\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030\u0089\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008a\u0001J \u0010\u008b\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030\u008c\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008d\u0001J \u0010\u008e\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u008f\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0090\u0001J \u0010\u0091\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u0092\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0093\u0001J \u0010\u0094\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u0095\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0096\u0001J \u0010\u0097\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u0098\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0099\u0001J \u0010\u009a\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u009b\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009c\u0001J \u0010\u009d\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u009e\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009f\u0001J \u0010 \u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030¡\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¢\u0001J \u0010£\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030¤\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¥\u0001J \u0010¦\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030§\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¨\u0001J \u0010©\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030ª\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010«\u0001J \u0010¬\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u00ad\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010®\u0001J \u0010¯\u0001\u001a\u00028\u00002\u0007\u0010W\u001a\u00030°\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010±\u0001J$\u0010²\u0001\u001a\u00028\u00002\u000b\u0010\n\u001a\u0007\u0012\u0002\b\u00030³\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010´\u0001J \u0010µ\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030¶\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010·\u0001J \u0010¸\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030¹\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010º\u0001J \u0010»\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030¼\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010½\u0001J \u0010¾\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030¿\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010À\u0001J \u0010Á\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Â\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ã\u0001J \u0010Ä\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Å\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Æ\u0001J \u0010Ç\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030È\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010É\u0001J \u0010Ê\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Ë\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ì\u0001J \u0010Í\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Î\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ï\u0001J \u0010Ð\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030Ñ\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ò\u0001J \u0010Ó\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Ô\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Õ\u0001J!\u0010Ö\u0001\u001a\u00028\u00002\b\u0010×\u0001\u001a\u00030Ø\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ù\u0001J \u0010Ú\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Û\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ü\u0001J \u0010Ý\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030Þ\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ß\u0001J \u0010à\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030á\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010â\u0001J \u0010ã\u0001\u001a\u00028\u00002\u0007\u0010\u000e\u001a\u00030ä\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010å\u0001J \u0010æ\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030ç\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010è\u0001J!\u0010é\u0001\u001a\u00028\u00002\b\u0010ê\u0001\u001a\u00030ë\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ì\u0001J \u0010í\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030î\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ï\u0001J \u0010ð\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030ñ\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ò\u0001J \u0010ó\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030ô\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010õ\u0001J \u0010ö\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030÷\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ø\u0001J \u0010ù\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030ú\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010û\u0001J \u0010ü\u0001\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030ý\u00012\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010þ\u0001J \u0010ÿ\u0001\u001a\u00028\u00002\u0007\u0010\u0005\u001a\u00030\u0080\u00022\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0081\u0002J \u0010\u0082\u0002\u001a\u00028\u00002\u0007\u0010\n\u001a\u00030\u0083\u00022\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0002J \u0010\u0085\u0002\u001a\u00028\u00002\u0007\u0010W\u001a\u00030\u0086\u00022\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0087\u0002¨\u0006\u0088\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "R", "D", "", "visitAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "data", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;Ljava/lang/Object;)Ljava/lang/Object;", "visitBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;Ljava/lang/Object;)Ljava/lang/Object;", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBranch;Ljava/lang/Object;)Ljava/lang/Object;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBreak;Ljava/lang/Object;)Ljava/lang/Object;", "visitBreakContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;Ljava/lang/Object;)Ljava/lang/Object;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCatch;Ljava/lang/Object;)Ljava/lang/Object;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "(Lorg/jetbrains/kotlin/ir/expressions/IrComposite;Ljava/lang/Object;)Ljava/lang/Object;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstantValue", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContinue;Ljava/lang/Object;)Ljava/lang/Object;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;Ljava/lang/Object;)Ljava/lang/Object;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitDynamicExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Object;)Ljava/lang/Object;", "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "(Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;Ljava/lang/Object;)Ljava/lang/Object;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;Ljava/lang/Object;)Ljava/lang/Object;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/lang/Object;)Ljava/lang/Object;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetField;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;Ljava/lang/Object;)Ljava/lang/Object;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/lang/Object;)Ljava/lang/Object;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;Ljava/lang/Object;)Ljava/lang/Object;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturn;Ljava/lang/Object;)Ljava/lang/Object;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "(Lorg/jetbrains/kotlin/ir/declarations/IrScript;Ljava/lang/Object;)Ljava/lang/Object;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetField;Ljava/lang/Object;)Ljava/lang/Object;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetSingletonValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitSpreadElement", "spread", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;Ljava/lang/Object;)Ljava/lang/Object;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;Ljava/lang/Object;)Ljava/lang/Object;", "visitSuspendableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitSuspensionPoint", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;Ljava/lang/Object;)Ljava/lang/Object;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;Ljava/lang/Object;)Ljava/lang/Object;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "(Lorg/jetbrains/kotlin/ir/expressions/IrThrow;Ljava/lang/Object;)Ljava/lang/Object;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTry;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitValueAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrValueAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "(Lorg/jetbrains/kotlin/ir/expressions/IrVararg;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrElementVisitor<R, D> {
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    R visitAnonymousInitializer2(IrAnonymousInitializer declaration, D data);

    /* JADX INFO: renamed from: visitBlock */
    R visitBlock2(IrBlock expression, D data);

    /* JADX INFO: renamed from: visitBlockBody */
    R visitBlockBody2(IrBlockBody body, D data);

    /* JADX INFO: renamed from: visitBody */
    R visitBody2(IrBody body, D data);

    /* JADX INFO: renamed from: visitBranch */
    R visitBranch2(IrBranch branch, D data);

    /* JADX INFO: renamed from: visitBreak */
    R visitBreak2(IrBreak jump, D data);

    /* JADX INFO: renamed from: visitBreakContinue */
    R visitBreakContinue2(IrBreakContinue jump, D data);

    /* JADX INFO: renamed from: visitCall */
    R visitCall2(IrCall expression, D data);

    R visitCallableReference(IrCallableReference<?> expression, D data);

    /* JADX INFO: renamed from: visitCatch */
    R visitCatch2(IrCatch aCatch, D data);

    /* JADX INFO: renamed from: visitClass */
    R visitClass2(IrClass declaration, D data);

    /* JADX INFO: renamed from: visitClassReference */
    R visitClassReference2(IrClassReference expression, D data);

    /* JADX INFO: renamed from: visitComposite */
    R visitComposite2(IrComposite expression, D data);

    R visitConst(IrConst<?> expression, D data);

    /* JADX INFO: renamed from: visitConstantArray */
    R visitConstantArray2(IrConstantArray expression, D data);

    /* JADX INFO: renamed from: visitConstantObject */
    R visitConstantObject2(IrConstantObject expression, D data);

    /* JADX INFO: renamed from: visitConstantPrimitive */
    R visitConstantPrimitive2(IrConstantPrimitive expression, D data);

    /* JADX INFO: renamed from: visitConstantValue */
    R visitConstantValue2(IrConstantValue expression, D data);

    /* JADX INFO: renamed from: visitConstructor */
    R visitConstructor2(IrConstructor declaration, D data);

    /* JADX INFO: renamed from: visitConstructorCall */
    R visitConstructorCall2(IrConstructorCall expression, D data);

    /* JADX INFO: renamed from: visitContainerExpression */
    R visitContainerExpression2(IrContainerExpression expression, D data);

    /* JADX INFO: renamed from: visitContinue */
    R visitContinue2(IrContinue jump, D data);

    /* JADX INFO: renamed from: visitDeclaration */
    R visitDeclaration2(IrDeclarationBase declaration, D data);

    /* JADX INFO: renamed from: visitDeclarationReference */
    R visitDeclarationReference2(IrDeclarationReference expression, D data);

    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    R visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, D data);

    /* JADX INFO: renamed from: visitDoWhileLoop */
    R visitDoWhileLoop2(IrDoWhileLoop loop, D data);

    /* JADX INFO: renamed from: visitDynamicExpression */
    R visitDynamicExpression2(IrDynamicExpression expression, D data);

    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    R visitDynamicMemberExpression2(IrDynamicMemberExpression expression, D data);

    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    R visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, D data);

    /* JADX INFO: renamed from: visitElement */
    R visitElement2(IrElement element, D data);

    /* JADX INFO: renamed from: visitElseBranch */
    R visitElseBranch2(IrElseBranch branch, D data);

    /* JADX INFO: renamed from: visitEnumConstructorCall */
    R visitEnumConstructorCall2(IrEnumConstructorCall expression, D data);

    /* JADX INFO: renamed from: visitEnumEntry */
    R visitEnumEntry2(IrEnumEntry declaration, D data);

    /* JADX INFO: renamed from: visitErrorCallExpression */
    R visitErrorCallExpression2(IrErrorCallExpression expression, D data);

    /* JADX INFO: renamed from: visitErrorDeclaration */
    R visitErrorDeclaration2(IrErrorDeclaration declaration, D data);

    /* JADX INFO: renamed from: visitErrorExpression */
    R visitErrorExpression2(IrErrorExpression expression, D data);

    /* JADX INFO: renamed from: visitExpression */
    R visitExpression2(IrExpression expression, D data);

    /* JADX INFO: renamed from: visitExpressionBody */
    R visitExpressionBody2(IrExpressionBody body, D data);

    /* JADX INFO: renamed from: visitExternalPackageFragment */
    R visitExternalPackageFragment2(IrExternalPackageFragment declaration, D data);

    /* JADX INFO: renamed from: visitField */
    R visitField2(IrField declaration, D data);

    /* JADX INFO: renamed from: visitFieldAccess */
    R visitFieldAccess2(IrFieldAccessExpression expression, D data);

    /* JADX INFO: renamed from: visitFile */
    R visitFile2(IrFile declaration, D data);

    /* JADX INFO: renamed from: visitFunction */
    R visitFunction2(IrFunction declaration, D data);

    /* JADX INFO: renamed from: visitFunctionAccess */
    R visitFunctionAccess2(IrFunctionAccessExpression expression, D data);

    /* JADX INFO: renamed from: visitFunctionExpression */
    R visitFunctionExpression2(IrFunctionExpression expression, D data);

    /* JADX INFO: renamed from: visitFunctionReference */
    R visitFunctionReference2(IrFunctionReference expression, D data);

    /* JADX INFO: renamed from: visitGetClass */
    R visitGetClass2(IrGetClass expression, D data);

    /* JADX INFO: renamed from: visitGetEnumValue */
    R visitGetEnumValue2(IrGetEnumValue expression, D data);

    /* JADX INFO: renamed from: visitGetField */
    R visitGetField2(IrGetField expression, D data);

    /* JADX INFO: renamed from: visitGetObjectValue */
    R visitGetObjectValue2(IrGetObjectValue expression, D data);

    /* JADX INFO: renamed from: visitGetValue */
    R visitGetValue2(IrGetValue expression, D data);

    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    R visitInstanceInitializerCall2(IrInstanceInitializerCall expression, D data);

    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    R visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, D data);

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    R visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference expression, D data);

    /* JADX INFO: renamed from: visitLoop */
    R visitLoop2(IrLoop loop, D data);

    R visitMemberAccess(IrMemberAccessExpression<?> expression, D data);

    /* JADX INFO: renamed from: visitModuleFragment */
    R visitModuleFragment2(IrModuleFragment declaration, D data);

    /* JADX INFO: renamed from: visitPackageFragment */
    R visitPackageFragment2(IrPackageFragment declaration, D data);

    /* JADX INFO: renamed from: visitProperty */
    R visitProperty2(IrProperty declaration, D data);

    /* JADX INFO: renamed from: visitPropertyReference */
    R visitPropertyReference2(IrPropertyReference expression, D data);

    /* JADX INFO: renamed from: visitRawFunctionReference */
    R visitRawFunctionReference2(IrRawFunctionReference expression, D data);

    /* JADX INFO: renamed from: visitReturn */
    R visitReturn2(IrReturn expression, D data);

    /* JADX INFO: renamed from: visitScript */
    R visitScript2(IrScript declaration, D data);

    /* JADX INFO: renamed from: visitSetField */
    R visitSetField2(IrSetField expression, D data);

    /* JADX INFO: renamed from: visitSetValue */
    R visitSetValue2(IrSetValue expression, D data);

    /* JADX INFO: renamed from: visitSimpleFunction */
    R visitSimpleFunction2(IrSimpleFunction declaration, D data);

    /* JADX INFO: renamed from: visitSingletonReference */
    R visitSingletonReference2(IrGetSingletonValue expression, D data);

    /* JADX INFO: renamed from: visitSpreadElement */
    R visitSpreadElement2(IrSpreadElement spread, D data);

    /* JADX INFO: renamed from: visitStringConcatenation */
    R visitStringConcatenation2(IrStringConcatenation expression, D data);

    /* JADX INFO: renamed from: visitSuspendableExpression */
    R visitSuspendableExpression2(IrSuspendableExpression expression, D data);

    /* JADX INFO: renamed from: visitSuspensionPoint */
    R visitSuspensionPoint2(IrSuspensionPoint expression, D data);

    /* JADX INFO: renamed from: visitSyntheticBody */
    R visitSyntheticBody2(IrSyntheticBody body, D data);

    /* JADX INFO: renamed from: visitThrow */
    R visitThrow2(IrThrow expression, D data);

    /* JADX INFO: renamed from: visitTry */
    R visitTry2(IrTry aTry, D data);

    /* JADX INFO: renamed from: visitTypeAlias */
    R visitTypeAlias2(IrTypeAlias declaration, D data);

    /* JADX INFO: renamed from: visitTypeOperator */
    R visitTypeOperator2(IrTypeOperatorCall expression, D data);

    /* JADX INFO: renamed from: visitTypeParameter */
    R visitTypeParameter2(IrTypeParameter declaration, D data);

    /* JADX INFO: renamed from: visitValueAccess */
    R visitValueAccess2(IrValueAccessExpression expression, D data);

    /* JADX INFO: renamed from: visitValueParameter */
    R visitValueParameter2(IrValueParameter declaration, D data);

    /* JADX INFO: renamed from: visitVararg */
    R visitVararg2(IrVararg expression, D data);

    /* JADX INFO: renamed from: visitVariable */
    R visitVariable2(IrVariable declaration, D data);

    /* JADX INFO: renamed from: visitWhen */
    R visitWhen2(IrWhen expression, D data);

    /* JADX INFO: renamed from: visitWhileLoop */
    R visitWhileLoop2(IrWhileLoop loop, D data);

    /* JADX INFO: compiled from: IrElementVisitor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDeclaration(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDeclarationBase declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitElement2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitValueParameter(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrValueParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitClass(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrClass declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitAnonymousInitializer(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrAnonymousInitializer declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitTypeParameter(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrTypeParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFunction(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstructor(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstructor declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitFunction2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitEnumEntry(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrEnumEntry declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitErrorDeclaration(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrErrorDeclaration declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitField(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrField declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitLocalDelegatedProperty(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrLocalDelegatedProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitModuleFragment(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrModuleFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitElement2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitProperty(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitScript(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrScript declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSimpleFunction(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSimpleFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitFunction2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitTypeAlias(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrTypeAlias declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitVariable(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrVariable declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitDeclaration2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitPackageFragment(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitElement2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitExternalPackageFragment(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrExternalPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitPackageFragment2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFile(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFile declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return irElementVisitor.visitPackageFragment2(declaration, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitElement2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBody(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementVisitor.visitElement2(body, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitExpressionBody(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrExpressionBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementVisitor.visitBody2(body, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBlockBody(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBlockBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementVisitor.visitBody2(body, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDeclarationReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDeclarationReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitMemberAccess(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrMemberAccessExpression<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFunctionAccess(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFunctionAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitMemberAccess(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstructorCall(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFunctionAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSingletonReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetSingletonValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitGetObjectValue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetObjectValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitSingletonReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitGetEnumValue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetEnumValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitSingletonReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitRawFunctionReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrRawFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitContainerExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrContainerExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBlock(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBlock expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitContainerExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitComposite(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrComposite expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitContainerExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSyntheticBody(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSyntheticBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return irElementVisitor.visitBody2(body, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBreakContinue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBreakContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementVisitor.visitExpression2(jump, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBreak(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBreak jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementVisitor.visitBreakContinue2(jump, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitContinue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return irElementVisitor.visitBreakContinue2(jump, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitCall(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFunctionAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitCallableReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrCallableReference<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitMemberAccess(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFunctionReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitCallableReference(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitPropertyReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitCallableReference(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitLocalDelegatedPropertyReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrLocalDelegatedPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitCallableReference(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitClassReference(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrClassReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConst(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConst<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstantValue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstantValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstantPrimitive(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstantPrimitive expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitConstantValue2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstantObject(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstantObject expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitConstantValue2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitConstantArray(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrConstantArray expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitConstantValue2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDelegatingConstructorCall(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDelegatingConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFunctionAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDynamicExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDynamicExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDynamicOperatorExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDynamicOperatorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDynamicExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDynamicMemberExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDynamicMemberExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDynamicExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitEnumConstructorCall(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrEnumConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFunctionAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitErrorExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrErrorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitErrorCallExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrErrorCallExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitErrorExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFieldAccess(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFieldAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitGetField(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFieldAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSetField(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitFieldAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitFunctionExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrFunctionExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitGetClass(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetClass expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitInstanceInitializerCall(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrInstanceInitializerCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitLoop(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementVisitor.visitExpression2(loop, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitWhileLoop(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementVisitor.visitLoop2(loop, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitDoWhileLoop(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrDoWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return irElementVisitor.visitLoop2(loop, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitReturn(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrReturn expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitStringConcatenation(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrStringConcatenation expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSuspensionPoint(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSuspensionPoint expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSuspendableExpression(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSuspendableExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitThrow(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrThrow expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitTry(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrTry aTry, D d) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            return irElementVisitor.visitExpression2(aTry, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitCatch(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrCatch aCatch, D d) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            return irElementVisitor.visitElement2(aCatch, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitTypeOperator(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrTypeOperatorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitValueAccess(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrValueAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitDeclarationReference2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitGetValue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrGetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitValueAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSetValue(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitValueAccess2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitVararg(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrVararg expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitSpreadElement(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrSpreadElement spread, D d) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            return irElementVisitor.visitElement2(spread, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitWhen(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrWhen expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return irElementVisitor.visitExpression2(expression, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitBranch(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return irElementVisitor.visitElement2(branch, d);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <R, D> R visitElseBranch(IrElementVisitor<? extends R, ? super D> irElementVisitor, IrElseBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return irElementVisitor.visitBranch2(branch, d);
        }
    }
}
