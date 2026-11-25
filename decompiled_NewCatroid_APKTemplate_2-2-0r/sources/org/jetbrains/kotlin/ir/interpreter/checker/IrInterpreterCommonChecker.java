package org.jetbrains.kotlin.ir.interpreter.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
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
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
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
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.preprocessor.IrInterpreterKCallableNamePreprocessor;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IrInterpreterCommonChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000à\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001d\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u001d2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010$J!\u0010%\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030&2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020)2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001d\u0010*\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020+2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010,J\u001d\u0010-\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020.2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010/J\u001d\u00100\u001a\u00020\u00072\u0006\u0010\b\u001a\u0002012\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u00102J\u001d\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u0002052\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u00106J\u001d\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u00109J\u001d\u0010:\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020;2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020>2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010?J\u001d\u0010@\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010BJ\u001d\u0010C\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020D2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020G2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010HJ\u001d\u0010I\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020J2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010KJ\u001d\u0010L\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020M2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010NJ\u001d\u0010O\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020P2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010QJ\u001d\u0010R\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020S2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010TJ\u001d\u0010U\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020V2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010WJ\u001d\u0010X\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020Y2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010ZJ\u001d\u0010[\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\\2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010]J\u001d\u0010^\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020_2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010`J\u001d\u0010a\u001a\u00020\u00072\u0006\u0010b\u001a\u00020c2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010dJ\u001e\u0010e\u001a\u00020\u00072\f\u0010f\u001a\b\u0012\u0004\u0012\u00020h0g2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001d\u0010i\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020j2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010kJ\u001d\u0010l\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020m2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010nJ\u001d\u0010o\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020p2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010qJ\u001d\u0010r\u001a\u00020\u00072\u0006\u0010s\u001a\u00020t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010uJ\u001d\u0010v\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020w2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010xJ\u0018\u0010y\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020)2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001d\u0010z\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020{2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010|J\u001e\u0010}\u001a\u00020\u00072\u0006\u0010~\u001a\u00020\u007f2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0003\u0010\u0080\u0001J \u0010\u0081\u0001\u001a\u00020\u00072\u0007\u0010\b\u001a\u00030\u0082\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0003\u0010\u0083\u0001J \u0010\u0084\u0001\u001a\u00020\u00072\u0007\u00104\u001a\u00030\u0085\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0003\u0010\u0086\u0001J \u0010\u0087\u0001\u001a\u00020\u0007*\u00020\u00052\u0010\b\u0004\u0010\u0088\u0001\u001a\t\u0012\u0004\u0012\u00020\u00070\u0089\u0001H\u0082\bJ\u0014\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020h0g*\u00030\u008b\u0001H\u0002J\r\u0010\u008c\u0001\u001a\u00020\u0007*\u00020\u001dH\u0002J\u0011\u0010\u008d\u0001\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030&H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u008e\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCommonChecker;", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "()V", "visitedStack", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "visitBlock", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "data", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitBodyIfNeeded", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBranch;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBreak;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "(Lorg/jetbrains/kotlin/ir/expressions/IrComposite;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContinue;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetField;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturn;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetField;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitSpreadElement", "spread", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitStatements", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "(Lorg/jetbrains/kotlin/ir/expressions/IrThrow;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTry;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitValueArguments", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "(Lorg/jetbrains/kotlin/ir/expressions/IrVararg;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitVariable", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;)Ljava/lang/Boolean;", "asVisited", "block", "Lkotlin/Function0;", "getInnerDeclarations", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "isGetterToConstVal", "isNaN", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreterCommonChecker implements IrInterpreterChecker {
    private final List<IrElement> visitedStack = new ArrayList();

    /* JADX INFO: compiled from: IrInterpreterCommonChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.INSTANCEOF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IrTypeOperator.SAM_CONVERSION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[IrTypeOperator.CAST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public Boolean visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public Boolean visitBlockBody2(IrBlockBody irBlockBody, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitBlockBody(this, irBlockBody, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public Boolean visitBreakContinue2(IrBreakContinue irBreakContinue, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitBreakContinue(this, irBreakContinue, irInterpreterCheckerData);
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public Boolean visitCallableReference2(IrCallableReference<?> irCallableReference, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitCallableReference(this, irCallableReference, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Boolean visitCallableReference(IrCallableReference irCallableReference, IrInterpreterCheckerData irInterpreterCheckerData) {
        return visitCallableReference2((IrCallableReference<?>) irCallableReference, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public Boolean visitCatch2(IrCatch irCatch, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitCatch(this, irCatch, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public Boolean visitClass2(IrClass irClass, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitClass(this, irClass, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Boolean visitConst(IrConst irConst, IrInterpreterCheckerData irInterpreterCheckerData) {
        return visitConst2((IrConst<?>) irConst, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public Boolean visitConstantArray2(IrConstantArray irConstantArray, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitConstantArray(this, irConstantArray, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public Boolean visitConstantObject2(IrConstantObject irConstantObject, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitConstantObject(this, irConstantObject, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public Boolean visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public Boolean visitConstantValue2(IrConstantValue irConstantValue, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitConstantValue(this, irConstantValue, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public Boolean visitConstructor2(IrConstructor irConstructor, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitConstructor(this, irConstructor, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public Boolean visitContainerExpression2(IrContainerExpression irContainerExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitContainerExpression(this, irContainerExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public Boolean visitDeclaration2(IrDeclarationBase irDeclarationBase, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitDeclaration(this, irDeclarationBase, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public Boolean visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public Boolean visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public Boolean visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public Boolean visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public Boolean visitElseBranch2(IrElseBranch irElseBranch, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitElseBranch(this, irElseBranch, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public Boolean visitEnumEntry2(IrEnumEntry irEnumEntry, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitEnumEntry(this, irEnumEntry, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public Boolean visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public Boolean visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public Boolean visitErrorExpression2(IrErrorExpression irErrorExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitErrorExpression(this, irErrorExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public Boolean visitExpression2(IrExpression irExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitExpression(this, irExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public Boolean visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public Boolean visitField2(IrField irField, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitField(this, irField, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public Boolean visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public Boolean visitFile2(IrFile irFile, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitFile(this, irFile, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public Boolean visitFunction2(IrFunction irFunction, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitFunction(this, irFunction, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public Boolean visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public Boolean visitGetClass2(IrGetClass irGetClass, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitGetClass(this, irGetClass, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public Boolean visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public Boolean visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public Boolean visitLoop2(IrLoop irLoop, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitLoop(this, irLoop, irInterpreterCheckerData);
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public Boolean visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Boolean visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public Boolean visitModuleFragment2(IrModuleFragment irModuleFragment, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitModuleFragment(this, irModuleFragment, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public Boolean visitPackageFragment2(IrPackageFragment irPackageFragment, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitPackageFragment(this, irPackageFragment, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public Boolean visitProperty2(IrProperty irProperty, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitProperty(this, irProperty, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public Boolean visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public Boolean visitScript2(IrScript irScript, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitScript(this, irScript, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public Boolean visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public Boolean visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public Boolean visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public Boolean visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public Boolean visitTypeAlias2(IrTypeAlias irTypeAlias, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitTypeAlias(this, irTypeAlias, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public Boolean visitTypeParameter2(IrTypeParameter irTypeParameter, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitTypeParameter(this, irTypeParameter, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public Boolean visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public Boolean visitValueParameter2(IrValueParameter irValueParameter, IrInterpreterCheckerData irInterpreterCheckerData) {
        return IrInterpreterChecker.DefaultImpls.visitValueParameter(this, irValueParameter, irInterpreterCheckerData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public Boolean visitElement2(IrElement element, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrStatement> getInnerDeclarations(IrDeclarationParent irDeclarationParent) {
        List<IrDeclaration> declarations;
        IrDeclarationContainer irDeclarationContainer = irDeclarationParent instanceof IrDeclarationContainer ? (IrDeclarationContainer) irDeclarationParent : null;
        if (irDeclarationContainer != null && (declarations = irDeclarationContainer.getDeclarations()) != null) {
            return declarations;
        }
        IrStatementContainer irStatementContainer = irDeclarationParent instanceof IrStatementContainer ? (IrStatementContainer) irDeclarationParent : null;
        return irStatementContainer != null ? irStatementContainer.getStatements() : CollectionsKt.emptyList();
    }

    private final boolean visitStatements(List<? extends IrStatement> statements, IrInterpreterCheckerData data) {
        List<? extends IrStatement> list = statements;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((Boolean) ((IrStatement) it.next()).accept(this, data)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    private final boolean visitConstructor(IrFunctionAccessExpression expression, IrInterpreterCheckerData data) {
        IrFunction owner = expression.getSymbol().getOwner();
        if (!data.getMode().canEvaluateFunction(owner) || !visitValueArguments(expression, data) || !visitBodyIfNeeded(owner, data)) {
            return false;
        }
        List<IrDeclaration> declarations = IrUtilsKt.getParentAsClass(owner).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrAnonymousInitializer) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((IrAnonymousInitializer) it.next()).accept(this, data)).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean visitBodyIfNeeded(IrFunction irFunction, IrInterpreterCheckerData data) {
        if (!data.getMode().mustCheckBodyOf(irFunction)) {
            return true;
        }
        this.visitedStack.add(irFunction);
        IrBody body = irFunction.getBody();
        boolean zBooleanValue = body != null ? ((Boolean) body.accept(this, data)).booleanValue() : true;
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return zBooleanValue;
    }

    private final boolean isGetterToConstVal(IrCall irCall) {
        IrProperty owner;
        IrPropertySymbol correspondingPropertySymbol = irCall.getSymbol().getOwner().getCorrespondingPropertySymbol();
        return (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || !owner.getIsConst()) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00bc  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public Boolean visitCall2(IrCall expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        boolean zVisitBodyIfNeeded = false;
        if (data.getInterpreterConfiguration().getInlineConstVal() || !isGetterToConstVal(expression) || Intrinsics.areEqual(data.getIrFile(), IrUtilsKt.getFileOrNull(owner))) {
            if (UtilsKt.isAccessToNotNullableObject(expression.getDispatchReceiver()) && isGetterToConstVal(expression)) {
                zVisitBodyIfNeeded = visitBodyIfNeeded(owner, data);
            } else if (data.getMode().canEvaluateExpression(expression)) {
                IrSimpleFunction irSimpleFunction = owner;
                if (data.getMode().canEvaluateFunction(irSimpleFunction)) {
                    if (IrInterpreterKCallableNamePreprocessor.INSTANCE.isKCallableNameCall(expression, data.getIrBuiltIns()) || IrInterpreterKCallableNamePreprocessor.INSTANCE.isEnumName(expression)) {
                        zVisitBodyIfNeeded = true;
                    } else {
                        IrExpression dispatchReceiver = expression.getDispatchReceiver();
                        boolean zBooleanValue = dispatchReceiver != null ? ((Boolean) dispatchReceiver.accept(this, data)).booleanValue() : true;
                        IrExpression extensionReceiver = expression.getExtensionReceiver();
                        boolean zBooleanValue2 = extensionReceiver != null ? ((Boolean) extensionReceiver.accept(this, data)).booleanValue() : true;
                        if (zBooleanValue && zBooleanValue2 && visitValueArguments(expression, data) && visitBodyIfNeeded(irSimpleFunction, data)) {
                            zVisitBodyIfNeeded = true;
                        }
                    }
                }
            }
        }
        return Boolean.valueOf(zVisitBodyIfNeeded);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public Boolean visitVariable2(IrVariable declaration, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression initializer = declaration.getInitializer();
        return Boolean.valueOf(initializer != null ? ((Boolean) initializer.accept(this, data)).booleanValue() : true);
    }

    private final boolean visitValueArguments(IrFunctionAccessExpression expression, IrInterpreterCheckerData data) {
        IntRange intRangeUntil = RangesKt.until(0, expression.getValueArgumentsCount());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(expression.getValueArgument(((IntIterator) it).nextInt()));
        }
        ArrayList<IrExpression> arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            for (IrExpression irExpression : arrayList2) {
                if (irExpression != null && !((Boolean) irExpression.accept(this, data)).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public Boolean visitBody2(IrBody body, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(visitStatements(IrUtilsKt.getStatements(body), data));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public Boolean visitExpressionBody2(IrExpressionBody body, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(data, "data");
        return (Boolean) body.getExpression().accept(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public Boolean visitBlock2(IrBlock expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!data.getMode().canEvaluateBlock(expression)) {
            return false;
        }
        if (expression instanceof IrReturnableBlock) {
            Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) expression.getStatements());
            IrInlinedFunctionBlock irInlinedFunctionBlock = objSingleOrNull instanceof IrInlinedFunctionBlock ? (IrInlinedFunctionBlock) objSingleOrNull : null;
            if (irInlinedFunctionBlock != null) {
                return (Boolean) irInlinedFunctionBlock.getInlineCall().accept(this, data);
            }
        }
        return Boolean.valueOf(visitStatements(expression.getStatements(), data));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public Boolean visitComposite2(IrComposite expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getMode().canEvaluateComposite(expression)) {
            return Boolean.valueOf(visitStatements(expression.getStatements(), data));
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public Boolean visitSyntheticBody2(IrSyntheticBody body, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(body.getKind() == IrSyntheticBodyKind.ENUM_VALUES || body.getKind() == IrSyntheticBodyKind.ENUM_VALUEOF);
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public Boolean visitConst2(IrConst<?> expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public Boolean visitVararg2(IrVararg expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrVarargElement> elements = expression.getElements();
        boolean z = false;
        if (!(elements instanceof Collection) || !elements.isEmpty()) {
            Iterator<T> it = elements.iterator();
            while (it.hasNext()) {
                if (((Boolean) ((IrVarargElement) it.next()).accept(this, data)).booleanValue()) {
                    z = true;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public Boolean visitSpreadElement2(IrSpreadElement spread, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        Intrinsics.checkNotNullParameter(data, "data");
        return (Boolean) spread.getExpression().accept(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public Boolean visitStringConcatenation2(IrStringConcatenation expression, IrInterpreterCheckerData data) {
        boolean zBooleanValue;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrExpression> arguments = expression.getArguments();
        boolean z = true;
        if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
            for (IrExpression irExpression : arguments) {
                if (irExpression instanceof IrGetObjectValue) {
                    List<IrDeclaration> declarations = ((IrGetObjectValue) irExpression).getSymbol().getOwner().getDeclarations();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : declarations) {
                        if (obj instanceof IrSimpleFunction) {
                            arrayList.add(obj);
                        }
                    }
                    Object obj2 = null;
                    boolean z2 = false;
                    for (Object obj3 : arrayList) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj3;
                        if (Intrinsics.areEqual(irSimpleFunction.getName().asString(), "toString") && irSimpleFunction.getValueParameters().isEmpty() && irSimpleFunction.getExtensionReceiverParameter() == null) {
                            if (z2) {
                                throw new IllegalArgumentException("Collection contains more than one matching element.");
                            }
                            z2 = true;
                            obj2 = obj3;
                        }
                    }
                    if (z2) {
                        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) obj2;
                        zBooleanValue = data.getMode().canEvaluateFunction(irSimpleFunction2) && visitBodyIfNeeded(irSimpleFunction2, data);
                    } else {
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                } else {
                    zBooleanValue = ((Boolean) irExpression.accept(this, data)).booleanValue();
                }
                if (!zBooleanValue) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public Boolean visitGetObjectValue2(IrGetObjectValue expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(data.getMode().canEvaluateExpression(expression));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public Boolean visitGetEnumValue2(IrGetEnumValue expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (!data.getMode().canEvaluateEnumValue(expression)) {
            return false;
        }
        if (this.visitedStack.contains(expression)) {
            return true;
        }
        this.visitedStack.add(expression);
        IrExpressionBody initializerExpression = expression.getSymbol().getOwner().getInitializerExpression();
        if (initializerExpression != null && ((Boolean) initializerExpression.accept(this, data)).booleanValue()) {
            z = true;
        }
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public Boolean visitGetValue2(IrGetValue expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(this.visitedStack.contains(expression.getSymbol().getOwner().getParent()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public Boolean visitSetValue2(IrSetValue expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return (Boolean) expression.getValue().accept(this, data);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0062  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public Boolean visitGetField2(IrGetField expression, IrInterpreterCheckerData data) {
        boolean z;
        IrSimpleFunction getter;
        IrExpressionBody initializer;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrField owner = expression.getSymbol().getOwner();
        IrPropertySymbol correspondingPropertySymbol = owner.getCorrespondingPropertySymbol();
        Object obj = null;
        IrProperty owner2 = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        IrField irField = owner;
        String fqName = UtilsKt.getFqName(irField);
        this.visitedStack.add(irField);
        boolean zContains = true;
        if (Intrinsics.areEqual(fqName, "java.lang.Boolean.FALSE") || Intrinsics.areEqual(fqName, "java.lang.Boolean.TRUE")) {
            z = zContains;
        } else {
            z = false;
            if (visitGetField$isJavaStaticWithPrimitiveOrString(owner)) {
                IrExpressionBody initializer2 = owner.getInitializer();
                if (initializer2 == null || !((Boolean) initializer2.accept(this, data)).booleanValue()) {
                    zContains = false;
                }
                z = zContains;
            } else if (expression.getReceiver() == null) {
                if (owner2 == null || !owner2.getIsConst() || (initializer = owner.getInitializer()) == null || !((Boolean) initializer.accept(this, data)).booleanValue()) {
                    zContains = false;
                }
                z = zContains;
            } else {
                if (!Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE) || owner2 == null || !owner2.getIsConst()) {
                    List innerDeclarations = getInnerDeclarations(owner.getParent());
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : innerDeclarations) {
                        if (obj2 instanceof IrProperty) {
                            arrayList.add(obj2);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    Object obj3 = null;
                    boolean z2 = false;
                    while (true) {
                        if (!it.hasNext()) {
                            if (!z2) {
                                break;
                            }
                            obj = obj3;
                            break;
                        }
                        Object next = it.next();
                        if (Intrinsics.areEqual((IrProperty) next, owner2)) {
                            if (z2) {
                                break;
                            }
                            obj3 = next;
                            z2 = true;
                        }
                    }
                    IrProperty irProperty = (IrProperty) obj;
                    if (irProperty != null && (getter = irProperty.getGetter()) != null) {
                        zContains = this.visitedStack.contains(getter);
                    }
                } else {
                    IrExpression receiver = expression.getReceiver();
                    boolean z3 = (receiver != null ? ((Boolean) receiver.accept(this, data)).booleanValue() : true) || UtilsKt.isAccessToNotNullableObject(expression.getReceiver());
                    IrExpressionBody initializer3 = owner.getInitializer();
                    boolean zBooleanValue = initializer3 != null ? ((Boolean) initializer3.accept(this, data)).booleanValue() : false;
                    if (!z3 || !zBooleanValue) {
                        zContains = false;
                    }
                }
                z = zContains;
            }
        }
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return Boolean.valueOf(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean visitGetField$isJavaStaticWithPrimitiveOrString(IrField irField) {
        if (Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && irField.getIsStatic() && irField.getIsFinal()) {
            return IrTypePredicatesKt.isPrimitiveType$default(irField.getType(), false, 1, null) || IrTypePredicatesKt.isStringClassType(irField.getType());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public Boolean visitSetField2(IrSetField expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (UtilsKt.accessesTopLevelOrObjectField(expression)) {
            return false;
        }
        IrPropertySymbol correspondingPropertySymbol = expression.getSymbol().getOwner().getCorrespondingPropertySymbol();
        Object obj = null;
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        List<IrStatement> innerDeclarations = getInnerDeclarations(expression.getSymbol().getOwner().getParent());
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : innerDeclarations) {
            if (obj2 instanceof IrProperty) {
                arrayList.add(obj2);
            }
        }
        boolean z2 = false;
        for (Object obj3 : arrayList) {
            if (Intrinsics.areEqual((IrProperty) obj3, owner)) {
                if (z2) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                obj = obj3;
                z2 = true;
            }
        }
        if (!z2) {
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        IrSimpleFunction setter = ((IrProperty) obj).getSetter();
        if (setter == null) {
            return false;
        }
        if (this.visitedStack.contains(setter) && ((Boolean) expression.getValue().accept(this, data)).booleanValue()) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public Boolean visitConstructorCall2(IrConstructorCall expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(visitConstructor(expression, data));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public Boolean visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (IrTypePredicatesKt.isAny(expression.getSymbol().getOwner().getReturnType())) {
            return true;
        }
        return Boolean.valueOf(visitConstructor(expression, data));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public Boolean visitEnumConstructorCall2(IrEnumConstructorCall expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(visitConstructor(expression, data));
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:41:0x00df  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:50:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:76:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x0103 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00cc A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:39:0x00d2, please report this as an issue */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public Boolean visitInstanceInitializerCall2(IrInstanceInitializerCall expression, IrInterpreterCheckerData data) {
        boolean z;
        ArrayList arrayList;
        Iterator it;
        IrField backingField;
        IrExpression expression2;
        IrGetValue irGetValue;
        IrExpressionBody initializer;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrClass owner = expression.getClassSymbol().getOwner();
        List<IrDeclaration> declarations = owner.getDeclarations();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrProperty) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        List<IrDeclaration> declarations2 = owner.getDeclarations();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : declarations2) {
            if (obj2 instanceof IrAnonymousInitializer) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj3 : arrayList4) {
            if (!((IrAnonymousInitializer) obj3).getIsStatic()) {
                arrayList5.add(obj3);
            }
        }
        ArrayList arrayList6 = arrayList5;
        if ((arrayList6 instanceof Collection) && arrayList6.isEmpty()) {
            arrayList = arrayList3;
            if (arrayList instanceof Collection) {
                it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        backingField = ((IrProperty) it.next()).getBackingField();
                        if (backingField != null) {
                            expression2 = null;
                        } else {
                            expression2 = null;
                        }
                        if (expression2 instanceof IrGetValue) {
                            irGetValue = (IrGetValue) expression2;
                        } else {
                            irGetValue = null;
                        }
                        if (Intrinsics.areEqual(irGetValue != null ? irGetValue.getOrigin() : null, IrStatementOrigin.INITIALIZE_PROPERTY_FROM_PARAMETER.INSTANCE)) {
                            z = false;
                        }
                    }
                }
            } else {
                it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        backingField = ((IrProperty) it.next()).getBackingField();
                        if (backingField != null) {
                            expression2 = null;
                        } else {
                            expression2 = null;
                        }
                        if (expression2 instanceof IrGetValue) {
                            irGetValue = (IrGetValue) expression2;
                        } else {
                            irGetValue = null;
                        }
                        if (Intrinsics.areEqual(irGetValue != null ? irGetValue.getOrigin() : null, IrStatementOrigin.INITIALIZE_PROPERTY_FROM_PARAMETER.INSTANCE)) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        } else {
            Iterator it2 = arrayList6.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (!((Boolean) ((IrAnonymousInitializer) it2.next()).getBody().accept(this, data)).booleanValue()) {
                    }
                } else {
                    arrayList = arrayList3;
                    if ((arrayList instanceof Collection) || !arrayList.isEmpty()) {
                        it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                backingField = ((IrProperty) it.next()).getBackingField();
                                if (backingField != null || (initializer = backingField.getInitializer()) == null) {
                                    expression2 = null;
                                } else {
                                    expression2 = initializer.getExpression();
                                }
                                if (expression2 instanceof IrGetValue) {
                                    irGetValue = (IrGetValue) expression2;
                                } else {
                                    irGetValue = null;
                                }
                                if (Intrinsics.areEqual(irGetValue != null ? irGetValue.getOrigin() : null, IrStatementOrigin.INITIALIZE_PROPERTY_FROM_PARAMETER.INSTANCE) && expression2 != null && !((Boolean) expression2.accept(this, data)).booleanValue()) {
                                }
                            }
                        }
                    }
                    z = true;
                }
                z = false;
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public Boolean visitFunctionReference2(IrFunctionReference expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (!data.getMode().canEvaluateCallableReference(expression)) {
            return false;
        }
        IrFunction owner = expression.getSymbol().getOwner();
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        boolean zBooleanValue = dispatchReceiver != null ? ((Boolean) dispatchReceiver.accept(this, data)).booleanValue() : true;
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        boolean zBooleanValue2 = extensionReceiver != null ? ((Boolean) extensionReceiver.accept(this, data)).booleanValue() : true;
        if (!data.getMode().canEvaluateFunction(owner)) {
            return false;
        }
        boolean zVisitBodyIfNeeded = visitBodyIfNeeded(owner, data);
        if (zBooleanValue && zBooleanValue2 && zVisitBodyIfNeeded) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public Boolean visitFunctionExpression2(IrFunctionExpression expression, IrInterpreterCheckerData data) {
        IrBody body;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!data.getMode().canEvaluateFunctionExpression(expression) || (body = expression.getFunction().getBody()) == null) {
            return false;
        }
        this.visitedStack.add(expression.getFunction());
        boolean zBooleanValue = ((Boolean) body.accept(this, data)).booleanValue();
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return Boolean.valueOf(zBooleanValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public Boolean visitTypeOperator2(IrTypeOperatorCall expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean zBooleanValue = false;
        switch (WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(expression.getTypeOperand());
                IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
                if ((owner instanceof IrTypeParameter) && !this.visitedStack.contains(((IrTypeParameter) owner).getParent())) {
                    return false;
                }
                zBooleanValue = ((Boolean) expression.getArgument().accept(this, data)).booleanValue();
                break;
                break;
        }
        return Boolean.valueOf(zBooleanValue);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public Boolean visitWhen2(IrWhen expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (!data.getMode().canEvaluateExpression(expression)) {
            return false;
        }
        List<IrBranch> branches = expression.getBranches();
        if ((branches instanceof Collection) && branches.isEmpty()) {
            z = true;
        } else {
            Iterator<T> it = branches.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((IrBranch) it.next()).accept(this, data)).booleanValue()) {
                }
            }
            z = true;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public Boolean visitBranch2(IrBranch branch, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        Intrinsics.checkNotNullParameter(data, "data");
        IrInterpreterCommonChecker irInterpreterCommonChecker = this;
        return Boolean.valueOf(((Boolean) branch.getCondition().accept(irInterpreterCommonChecker, data)).booleanValue() && ((Boolean) branch.getResult().accept(irInterpreterCommonChecker, data)).booleanValue());
    }

    /* JADX WARN: Code duplicated, block: B:10:0x003d  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public Boolean visitWhileLoop2(IrWhileLoop loop, IrInterpreterCheckerData data) {
        boolean z;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        this.visitedStack.add(loop);
        IrInterpreterCommonChecker irInterpreterCommonChecker = this;
        if (((Boolean) loop.getCondition().accept(irInterpreterCommonChecker, data)).booleanValue()) {
            IrExpression body = loop.getBody();
            z = body != null ? ((Boolean) body.accept(irInterpreterCommonChecker, data)).booleanValue() : true;
        }
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x003d  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public Boolean visitDoWhileLoop2(IrDoWhileLoop loop, IrInterpreterCheckerData data) {
        boolean z;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        this.visitedStack.add(loop);
        IrInterpreterCommonChecker irInterpreterCommonChecker = this;
        if (((Boolean) loop.getCondition().accept(irInterpreterCommonChecker, data)).booleanValue()) {
            IrExpression body = loop.getBody();
            z = body != null ? ((Boolean) body.accept(irInterpreterCommonChecker, data)).booleanValue() : true;
        }
        List<IrElement> list = this.visitedStack;
        list.remove(CollectionsKt.getLastIndex(list));
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public Boolean visitTry2(IrTry aTry, IrInterpreterCheckerData data) {
        IrExpression finallyExpression;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (!data.getMode().canEvaluateExpression(aTry)) {
            return false;
        }
        IrInterpreterCommonChecker irInterpreterCommonChecker = this;
        if (!((Boolean) aTry.getTryResult().accept(irInterpreterCommonChecker, data)).booleanValue()) {
            return false;
        }
        if (aTry.getFinallyExpression() != null && (finallyExpression = aTry.getFinallyExpression()) != null && !((Boolean) finallyExpression.accept(irInterpreterCommonChecker, data)).booleanValue()) {
            return false;
        }
        List<IrCatch> catches = aTry.getCatches();
        if ((catches instanceof Collection) && catches.isEmpty()) {
            z = true;
        } else {
            Iterator<T> it = catches.iterator();
            while (it.hasNext()) {
                if (!((Boolean) ((IrCatch) it.next()).getResult().accept(irInterpreterCommonChecker, data)).booleanValue()) {
                }
            }
            z = true;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public Boolean visitBreak2(IrBreak jump, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(this.visitedStack.contains(jump.getLoop()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public Boolean visitContinue2(IrContinue jump, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(this.visitedStack.contains(jump.getLoop()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public Boolean visitReturn2(IrReturn expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (this.visitedStack.contains(expression.getReturnTargetSymbol().getOwner())) {
            return (Boolean) expression.getValue().accept(this, data);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public Boolean visitThrow2(IrThrow expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getMode().canEvaluateExpression(expression)) {
            return (Boolean) expression.getValue().accept(this, data);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public Boolean visitPropertyReference2(IrPropertyReference expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = false;
        if (!data.getMode().canEvaluateCallableReference(expression)) {
            return false;
        }
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        boolean zBooleanValue = dispatchReceiver != null ? ((Boolean) dispatchReceiver.accept(this, data)).booleanValue() : true;
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        boolean zBooleanValue2 = extensionReceiver != null ? ((Boolean) extensionReceiver.accept(this, data)).booleanValue() : true;
        IrSimpleFunctionSymbol getter = expression.getGetter();
        boolean zCanEvaluateFunction = getter != null ? data.getMode().canEvaluateFunction(getter.getOwner()) : true;
        if (zBooleanValue && zBooleanValue2 && zCanEvaluateFunction) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public Boolean visitClassReference2(IrClassReference expression, IrInterpreterCheckerData data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(data.getMode().canEvaluateClassReference(expression));
    }
}
