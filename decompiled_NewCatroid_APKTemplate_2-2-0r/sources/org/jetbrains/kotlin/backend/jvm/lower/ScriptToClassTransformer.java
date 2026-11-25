package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmInnerClassesSupport;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
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
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
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
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¤\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B_\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\u0018\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00150\u00140\u0013¢\u0006\u0002\u0010\u0016J,\u0010/\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u000107H\u0002J6\u00108\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u0002032\u0006\u00109\u001a\u0002052\b\u00106\u001a\u0004\u0018\u0001072\b\u0010:\u001a\u0004\u0018\u00010\u0015H\u0002J6\u0010;\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u00022\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020=2\b\u0010?\u001a\u0004\u0018\u0001072\b\u0010:\u001a\u0004\u0018\u00010\u0015H\u0002J6\u0010@\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u0002032\u0006\u00109\u001a\u0002052\b\u0010?\u001a\u0004\u0018\u0001072\b\u0010:\u001a\u0004\u0018\u00010\u0015H\u0002J\u0010\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002J\u0010\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0002J\u0018\u0010I\u001a\u00020\u00062\u0006\u0010J\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010K\u001a\u00020L2\u0006\u00102\u001a\u00020L2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010M\u001a\u00020\u001b2\u0006\u0010J\u001a\u00020\u001b2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010N\u001a\u0002002\u0006\u00102\u001a\u00020O2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010P\u001a\u00020Q2\u0006\u0010J\u001a\u00020R2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010S\u001a\u00020H2\u0006\u0010G\u001a\u00020H2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010T\u001a\u0002002\u0006\u00102\u001a\u0002002\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010U\u001a\u00020F2\u0006\u0010J\u001a\u00020V2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010W\u001a\u0002002\u0006\u00102\u001a\u00020X2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010Y\u001a\u00020Z2\u0006\u0010J\u001a\u00020Z2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010[\u001a\u0002002\u0006\u00102\u001a\u00020\\2\u0006\u00101\u001a\u00020\u0002H\u0016J\u001c\u0010]\u001a\u0002002\n\u00102\u001a\u0006\u0012\u0002\b\u00030^2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010_\u001a\u00020`2\u0006\u0010J\u001a\u00020`2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010a\u001a\u00020Q2\u0006\u0010J\u001a\u00020\u00042\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010b\u001a\u00020c2\u0006\u0010J\u001a\u00020c2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020e2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010g\u001a\u00020h2\u0006\u0010J\u001a\u00020h2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010i\u001a\u00020j2\u0006\u00102\u001a\u00020j2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010k\u001a\u00020l2\u0006\u0010J\u001a\u00020l2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010m\u001a\u00020\u00152\u0006\u0010J\u001a\u00020\u00152\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010n\u001a\u00020o2\u0006\u00102\u001a\u00020o2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0018\u0010p\u001a\u00020q2\u0006\u0010J\u001a\u00020q2\u0006\u00101\u001a\u00020\u0002H\u0016J\u0014\u0010r\u001a\u00020B*\u00020s2\u0006\u00101\u001a\u00020\u0002H\u0002J\f\u0010t\u001a\u00020B*\u00020sH\u0002J\f\u0010u\u001a\u00020l*\u00020lH\u0002J\f\u0010v\u001a\u000205*\u000205H\u0002J&\u0010w\u001a\u0002Hx\"\n\b\u0000\u0010x\u0018\u0001*\u00020H*\u0002Hx2\u0006\u00101\u001a\u00020\u0002H\u0082\b¢\u0006\u0002\u0010yJ-\u0010w\u001a\b\u0012\u0004\u0012\u0002Hx0z\"\n\b\u0000\u0010x\u0018\u0001*\u00020H*\b\u0012\u0004\u0012\u0002Hx0z2\u0006\u00101\u001a\u00020\u0002H\u0082\bJ\u0014\u0010{\u001a\u00020|*\u00020}2\u0006\u00101\u001a\u00020\u0002H\u0002J$\u0010~\u001a\u0002Hx\"\b\b\u0000\u0010x*\u00020\u007f*\u0002Hx2\u0006\u00101\u001a\u00020\u0002H\u0002¢\u0006\u0003\u0010\u0080\u0001J\r\u0010\u0081\u0001\u001a\u00020|*\u00020sH\u0002R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\r0\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R#\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00150\u00140\u0013¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.¨\u0006\u0082\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptToClassTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptToClassTransformerContext;", "irScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "irScriptClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "capturingClasses", "", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "earlierScriptsField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "implicitReceiversFieldsWithParameters", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrScript;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Ljava/util/Set;Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/util/Collection;)V", "getCapturingClasses", "()Ljava/util/Set;", "capturingClassesConstructors", "", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getCapturingClassesConstructors", "()Ljava/util/Map;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getEarlierScriptsField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getImplicitReceiversFieldsWithParameters", "()Ljava/util/Collection;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "getIrScript", "()Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "getIrScriptClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "scriptClassReceiver", "getScriptClassReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getTypeRemapper", "()Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "getAccessCallForEarlierScript", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "data", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "maybeScriptType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "expressionOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getAccessCallForImplicitReceiver", "receiverType", "originalReceiverParameter", "getAccessCallForScriptInstance", "startOffset", "", "endOffset", "origin", "getDispatchReceiverExpression", "isValidNameForReceiver", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "unexpectedElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitClass", "declaration", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitConstructor", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitElement", "visitExpression", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitScript", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "spread", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "visitValueParameter", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "isCurrentScriptTopLevelDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "needsScriptReceiver", "remapSuperTypes", "remapType", "transform", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptToClassTransformerContext;)Lorg/jetbrains/kotlin/ir/IrElement;", "", "transformAnnotations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "transformFunctionChildren", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptToClassTransformerContext;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "transformParent", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptToClassTransformer implements IrElementTransformer<ScriptToClassTransformerContext> {
    private final Set<IrClassImpl> capturingClasses;
    private final Map<IrConstructor, IrClassImpl> capturingClassesConstructors;
    private final JvmBackendContext context;
    private final IrField earlierScriptsField;
    private final Collection<Pair<IrField, IrValueParameter>> implicitReceiversFieldsWithParameters;
    private final JvmInnerClassesSupport innerClassesSupport;
    private final IrScript irScript;
    private final IrClass irScriptClass;
    private final IrValueParameter scriptClassReceiver;
    private final TypeRemapper typeRemapper;

    /* JADX WARN: Multi-variable type inference failed */
    public ScriptToClassTransformer(IrScript irScript, IrClass irScriptClass, TypeRemapper typeRemapper, JvmBackendContext context, Set<? extends IrClassImpl> capturingClasses, JvmInnerClassesSupport innerClassesSupport, IrField irField, Collection<? extends Pair<? extends IrField, ? extends IrValueParameter>> implicitReceiversFieldsWithParameters) {
        Intrinsics.checkNotNullParameter(irScript, "irScript");
        Intrinsics.checkNotNullParameter(irScriptClass, "irScriptClass");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(capturingClasses, "capturingClasses");
        Intrinsics.checkNotNullParameter(innerClassesSupport, "innerClassesSupport");
        Intrinsics.checkNotNullParameter(implicitReceiversFieldsWithParameters, "implicitReceiversFieldsWithParameters");
        this.irScript = irScript;
        this.irScriptClass = irScriptClass;
        this.typeRemapper = typeRemapper;
        this.context = context;
        this.capturingClasses = capturingClasses;
        this.innerClassesSupport = innerClassesSupport;
        this.earlierScriptsField = irField;
        this.implicitReceiversFieldsWithParameters = implicitReceiversFieldsWithParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IrClassImpl irClassImpl : capturingClasses) {
            for (IrDeclaration irDeclaration : irClassImpl.getDeclarations()) {
                if (irDeclaration instanceof IrConstructor) {
                    linkedHashMap.put(irDeclaration, irClassImpl);
                }
            }
        }
        this.capturingClassesConstructors = linkedHashMap;
        IrValueParameter thisReceiver = this.irScript.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        thisReceiver.setType(new IrSimpleTypeImpl((IrClassifierSymbol) this.irScriptClass.getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
        this.scriptClassReceiver = thisReceiver.transform((IrElementTransformer<? super ScriptToClassTransformerContext>) this, new ScriptToClassTransformerContext(null, null, null, false, null, 16, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock irBlock, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBody(this, irBody, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitCall2(IrCall irCall, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCall(this, irCall, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ScriptToClassTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ScriptToClassTransformerContext) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrExpression visitComposite2(IrComposite irComposite, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ScriptToClassTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ScriptToClassTransformerContext) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConst(this, irConst, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
        return (IrExternalPackageFragment) visitExternalPackageFragment2(irExternalPackageFragment, (ScriptToClassTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitField(this, irField, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction irFunction, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ScriptToClassTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ScriptToClassTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitTry(this, irTry, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, scriptToClassTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, scriptToClassTransformerContext);
    }

    public final IrScript getIrScript() {
        return this.irScript;
    }

    public final IrClass getIrScriptClass() {
        return this.irScriptClass;
    }

    public final TypeRemapper getTypeRemapper() {
        return this.typeRemapper;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public final Set<IrClassImpl> getCapturingClasses() {
        return this.capturingClasses;
    }

    public final JvmInnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    public final IrField getEarlierScriptsField() {
        return this.earlierScriptsField;
    }

    public final Collection<Pair<IrField, IrValueParameter>> getImplicitReceiversFieldsWithParameters() {
        return this.implicitReceiversFieldsWithParameters;
    }

    private final IrType remapType(IrType irType) {
        return this.typeRemapper.remapType(irType);
    }

    public final Map<IrConstructor, IrClassImpl> getCapturingClassesConstructors() {
        return this.capturingClassesConstructors;
    }

    public final IrValueParameter getScriptClassReceiver() {
        return this.scriptClassReceiver;
    }

    private final void transformParent(IrDeclaration irDeclaration) {
        if (Intrinsics.areEqual(irDeclaration.getParent(), this.irScript)) {
            irDeclaration.setParent(this.irScriptClass);
        }
    }

    private final void transformAnnotations(IrMutableAnnotationContainer irMutableAnnotationContainer, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        List<IrConstructorCall> annotations = irMutableAnnotationContainer.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, scriptToClassTransformerContext);
            if (irElementTransform != null) {
                arrayList.add((IrConstructorCall) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            }
        }
        irMutableAnnotationContainer.setAnnotations(arrayList);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0031  */
    /* JADX WARN: Code duplicated, block: B:13:0x003a  */
    /* JADX WARN: Code duplicated, block: B:14:0x004e  */
    private final <T extends IrFunction> T transformFunctionChildren(T t, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        IrValueParameter irValueParameterCreateThisReceiverParameter;
        IrValueParameter irValueParameter;
        IrValueParameter owner;
        transformAnnotations(t, scriptToClassTransformerContext);
        TypeRemapper typeRemapper = this.typeRemapper;
        typeRemapper.enterScope(t);
        IrValueParameter dispatchReceiverParameter = t.getDispatchReceiverParameter();
        IrBody irBody = null;
        if (dispatchReceiverParameter == null) {
            if (isCurrentScriptTopLevelDeclaration(t, scriptToClassTransformerContext)) {
                irValueParameterCreateThisReceiverParameter = ScriptLoweringKt.createThisReceiverParameter(t, this.context, IrDeclarationOrigin.SCRIPT_THIS_RECEIVER.INSTANCE, this.scriptClassReceiver.getType());
            } else {
                irValueParameterCreateThisReceiverParameter = null;
            }
        } else {
            IrElement irElementTransform = dispatchReceiverParameter.transform((IrElementTransformer<? super ScriptToClassTransformerContext>) this, scriptToClassTransformerContext);
            if (irElementTransform != null) {
                irValueParameterCreateThisReceiverParameter = (IrValueParameter) irElementTransform;
                if (irValueParameterCreateThisReceiverParameter == null) {
                    if (isCurrentScriptTopLevelDeclaration(t, scriptToClassTransformerContext)) {
                        irValueParameterCreateThisReceiverParameter = ScriptLoweringKt.createThisReceiverParameter(t, this.context, IrDeclarationOrigin.SCRIPT_THIS_RECEIVER.INSTANCE, this.scriptClassReceiver.getType());
                    } else {
                        irValueParameterCreateThisReceiverParameter = null;
                    }
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        boolean z = (t instanceof IrConstructor) && (Intrinsics.areEqual(t.getParent(), this.irScript) || Intrinsics.areEqual(t.getParent(), this.irScriptClass));
        if (irValueParameterCreateThisReceiverParameter != null) {
            if (Intrinsics.areEqual(irValueParameterCreateThisReceiverParameter.getType(), this.scriptClassReceiver.getType())) {
                scriptToClassTransformerContext = new ScriptToClassTransformerContext(irValueParameterCreateThisReceiverParameter.getSymbol(), null, null, z, null, 16, null);
            } else {
                IrType type = irValueParameterCreateThisReceiverParameter.getType();
                IrValueParameterSymbol valueParameterForFieldReceiver = scriptToClassTransformerContext.getValueParameterForFieldReceiver();
                if (Intrinsics.areEqual(type, (valueParameterForFieldReceiver == null || (owner = valueParameterForFieldReceiver.getOwner()) == null) ? null : owner.getType())) {
                    scriptToClassTransformerContext = new ScriptToClassTransformerContext(null, scriptToClassTransformerContext.getFieldForScriptThis(), irValueParameterCreateThisReceiverParameter.getSymbol(), z, null, 16, null);
                }
            }
        }
        t.setDispatchReceiverParameter(irValueParameterCreateThisReceiverParameter);
        IrValueParameter extensionReceiverParameter = t.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            IrElement irElementTransform2 = extensionReceiverParameter.transform((IrElementTransformer<? super ScriptToClassTransformerContext>) this, scriptToClassTransformerContext);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter = (IrValueParameter) irElementTransform2;
        } else {
            irValueParameter = null;
        }
        t.setExtensionReceiverParameter(irValueParameter);
        t.setReturnType(remapType(t.getReturnType()));
        List<IrValueParameter> valueParameters = t.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform3 = ((IrElement) it.next()).transform(this, scriptToClassTransformerContext);
            if (irElementTransform3 != null) {
                arrayList.add((IrValueParameter) irElementTransform3);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        t.setValueParameters(arrayList);
        IrBody body = t.getBody();
        if (body != null) {
            IrElement irElementTransform4 = body.transform((IrElementTransformer<? super ScriptToClassTransformerContext>) this, scriptToClassTransformerContext);
            if (irElementTransform4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
            }
            irBody = (IrBody) irElementTransform4;
        }
        t.setBody(irBody);
        Unit unit = Unit.INSTANCE;
        typeRemapper.leaveScope();
        return t;
    }

    private final IrTypeParameter remapSuperTypes(IrTypeParameter irTypeParameter) {
        List<IrType> superTypes = irTypeParameter.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        irTypeParameter.setSuperTypes(arrayList);
        return irTypeParameter;
    }

    private final Void unexpectedElement(IrElement element) {
        throw new IllegalArgumentException("Unsupported element type: " + element);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement element, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(element);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public Void visitExternalPackageFragment2(IrExternalPackageFragment declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrStatement visitScript2(IrScript declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        transformParent(declaration);
        transformAnnotations(declaration, data);
        declaration.transformChildren(this, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrClass visitClass2(IrClass declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        declaration.setSuperTypes(arrayList);
        transformParent(declaration);
        IrClassImpl irClassImpl = declaration instanceof IrClassImpl ? (IrClassImpl) declaration : null;
        if (irClassImpl != null && this.capturingClasses.contains(irClassImpl)) {
            irClassImpl.setInner(true);
            IrFieldSymbol symbol = this.innerClassesSupport.getOuterThisField(irClassImpl).getSymbol();
            IrValueParameter thisReceiver = irClassImpl.getThisReceiver();
            data = new ScriptToClassTransformerContext(null, symbol, thisReceiver != null ? thisReceiver.getSymbol() : null, false, null, 16, null);
        }
        transformAnnotations(declaration, data);
        declaration.transformChildren(this, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrSimpleFunction visitSimpleFunction2(IrSimpleFunction declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        transformParent(declaration);
        transformFunctionChildren(declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrConstructor visitConstructor2(IrConstructor declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (this.capturingClassesConstructors.containsKey(declaration)) {
            declaration.setDispatchReceiverParameter(ScriptLoweringKt.createThisReceiverParameter(declaration, this.context, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, this.scriptClassReceiver.getType()));
        }
        transformParent(declaration);
        transformFunctionChildren(declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrVariable visitVariable2(IrVariable declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        declaration.setType(remapType(declaration.getType()));
        visitDeclaration2((IrDeclarationBase) declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrTypeParameter visitTypeParameter2(IrTypeParameter declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        remapSuperTypes(declaration);
        visitDeclaration2((IrDeclarationBase) declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrValueParameter visitValueParameter2(IrValueParameter declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        declaration.setType(remapType(declaration.getType()));
        IrType varargElementType = declaration.getVarargElementType();
        declaration.setVarargElementType(varargElementType != null ? remapType(varargElementType) : null);
        visitDeclaration2((IrDeclarationBase) declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrTypeAlias visitTypeAlias2(IrTypeAlias declaration, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        declaration.setExpandedType(remapType(declaration.getExpandedType()));
        visitDeclaration2((IrDeclarationBase) declaration, data);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrVararg visitVararg2(IrVararg expression, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        expression.setType(remapType(expression.getType()));
        expression.setVarargElementType(remapType(expression.getVarargElementType()));
        expression.transformChildren(this, data);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement spread, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        Intrinsics.checkNotNullParameter(data, "data");
        spread.transformChildren(this, data);
        return spread;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression expression, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        expression.setType(remapType(expression.getType()));
        expression.transformChildren(this, data);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrClassReference visitClassReference2(IrClassReference expression, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        expression.setType(remapType(expression.getType()));
        expression.setClassType(remapType(expression.getClassType()));
        expression.transformChildren(this, data);
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrTypeOperatorCall visitTypeOperator2(IrTypeOperatorCall expression, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        expression.setType(remapType(expression.getType()));
        expression.setTypeOperand(remapType(expression.getTypeOperand()));
        expression.transformChildren(this, data);
        return expression;
    }

    public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression, ScriptToClassTransformerContext data) {
        IrType type;
        IrSimpleFunction getter;
        IrValueParameter dispatchReceiverParameter;
        IrExpression accessCallForScriptInstance;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        int i = 0;
        while (true) {
            type = null;
            if (i >= typeArgumentsCount) {
                break;
            }
            IrType typeArgument = expression.getTypeArgument(i);
            if (typeArgument != null) {
                type = remapType(typeArgument);
            }
            expression.putTypeArgument(i, type);
            i++;
        }
        if (expression.getDispatchReceiver() == null) {
            IrSymbolOwner owner = expression.getSymbol().getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration != null && needsScriptReceiver(irDeclaration)) {
                IrSymbolOwner owner2 = expression.getSymbol().getOwner();
                if (owner2 instanceof IrFunction) {
                    IrValueParameter dispatchReceiverParameter2 = ((IrFunction) owner2).getDispatchReceiverParameter();
                    if (dispatchReceiverParameter2 != null) {
                        type = dispatchReceiverParameter2.getType();
                    }
                } else if ((owner2 instanceof IrProperty) && (getter = ((IrProperty) owner2).getGetter()) != null && (dispatchReceiverParameter = getter.getDispatchReceiverParameter()) != null) {
                    type = dispatchReceiverParameter.getType();
                }
                IrType irType = type;
                if (irType != null && !Intrinsics.areEqual(irType, this.scriptClassReceiver.getType())) {
                    accessCallForScriptInstance = getAccessCallForImplicitReceiver(data, expression, irType, expression.getOrigin(), null);
                } else {
                    accessCallForScriptInstance = getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expression.getOrigin(), null);
                }
                expression.setDispatchReceiver(accessCallForScriptInstance);
            }
        }
        IrElement irElementVisitMemberAccess = IrElementTransformer.DefaultImpls.visitMemberAccess(this, expression, data);
        Intrinsics.checkNotNull(irElementVisitMemberAccess, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        return (IrExpression) irElementVisitMemberAccess;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression expression, ScriptToClassTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (expression.getReceiver() == null) {
            IrDeclarationParent parent = expression.getSymbol().getOwner().getParent();
            if (Intrinsics.areEqual(parent, this.irScript) || Intrinsics.areEqual(parent, this.irScriptClass)) {
                expression.setReceiver(getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expression.getOrigin(), null));
            }
        }
        return IrElementTransformer.DefaultImpls.visitFieldAccess(this, expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitConstructorCall2(IrConstructorCall expression, ScriptToClassTransformerContext data) {
        IrType type;
        IrExpression dispatchReceiverExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (expression.getDispatchReceiver() == null) {
            IrValueParameter dispatchReceiverParameter = expression.getSymbol().getOwner().getDispatchReceiverParameter();
            if (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null) {
                Set<IrConstructor> setKeySet = this.capturingClassesConstructors.keySet();
                if (!(setKeySet instanceof Collection) || !setKeySet.isEmpty()) {
                    Iterator<T> it = setKeySet.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            type = null;
                            break;
                        }
                        if (Intrinsics.areEqual(((IrConstructor) it.next()).getSymbol(), expression.getSymbol())) {
                            type = this.scriptClassReceiver.getType();
                            break;
                        }
                    }
                } else {
                    type = null;
                    break;
                }
            }
            IrType irType = type;
            if (irType != null && (dispatchReceiverExpression = getDispatchReceiverExpression(data, expression, irType, expression.getOrigin(), null)) != null) {
                expression.setDispatchReceiver(dispatchReceiverExpression);
            }
        }
        IrElement irElementVisitConstructorCall = IrElementTransformer.DefaultImpls.visitConstructorCall(this, expression, data);
        Intrinsics.checkNotNull(irElementVisitConstructorCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        return (IrExpression) irElementVisitConstructorCall;
    }

    private final IrExpression getDispatchReceiverExpression(ScriptToClassTransformerContext data, IrDeclarationReference expression, IrType receiverType, IrStatementOrigin origin, IrValueParameter originalReceiverParameter) {
        if (Intrinsics.areEqual(receiverType, this.scriptClassReceiver.getType())) {
            return getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), origin, originalReceiverParameter);
        }
        return getAccessCallForImplicitReceiver(data, expression, receiverType, origin, originalReceiverParameter);
    }

    private final IrExpression getAccessCallForScriptInstance(ScriptToClassTransformerContext data, int startOffset, int endOffset, IrStatementOrigin origin, IrValueParameter originalReceiverParameter) {
        if (originalReceiverParameter != null && !Intrinsics.areEqual(originalReceiverParameter.getType(), this.scriptClassReceiver.getType())) {
            return null;
        }
        if (data.getFieldForScriptThis() != null) {
            IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(startOffset, endOffset, data.getFieldForScriptThis(), this.scriptClassReceiver.getType(), origin, null, 32, null);
            IrValueParameterSymbol valueParameterForFieldReceiver = data.getValueParameterForFieldReceiver();
            Intrinsics.checkNotNull(valueParameterForFieldReceiver);
            irGetFieldImpl.setReceiver(new IrGetValueImpl(startOffset, endOffset, valueParameterForFieldReceiver.getOwner().getType(), data.getValueParameterForFieldReceiver(), origin));
            return irGetFieldImpl;
        }
        if (data.getValueParameterForScriptThis() != null) {
            return new IrGetValueImpl(startOffset, endOffset, this.scriptClassReceiver.getType(), data.getValueParameterForScriptThis(), origin);
        }
        throw new IllegalStateException(("Unexpected script transformation state: " + data).toString());
    }

    private final IrExpression getAccessCallForImplicitReceiver(ScriptToClassTransformerContext data, IrDeclarationReference expression, IrType receiverType, IrStatementOrigin expressionOrigin, IrValueParameter originalReceiverParameter) {
        Object next;
        boolean zAreEqual;
        Iterator<T> it = this.implicitReceiversFieldsWithParameters.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Pair pair = (Pair) next;
            if (originalReceiverParameter != null) {
                zAreEqual = Intrinsics.areEqual(pair.getSecond(), originalReceiverParameter);
            } else {
                zAreEqual = Intrinsics.areEqual(((IrValueParameter) pair.getSecond()).getType(), receiverType);
            }
        } while (!zAreEqual);
        Pair pair2 = (Pair) next;
        if (pair2 != null) {
            IrField irField = (IrField) pair2.component1();
            IrValueParameter irValueParameter = (IrValueParameter) pair2.component2();
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
            if (data.isInScriptConstructor()) {
                return ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter.getType(), irValueParameter.getSymbol());
            }
            return ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expressionOrigin, null), irField, null, 4, null);
        }
        return getAccessCallForEarlierScript(data, expression, receiverType, expressionOrigin);
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0092  */
    /* JADX WARN: Code duplicated, block: B:39:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:42:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:46:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:49:0x0110  */
    /* JADX WARN: Code duplicated, block: B:52:0x016f  */
    /* JADX WARN: Code duplicated, block: B:54:0x0177  */
    /* JADX WARN: Code duplicated, block: B:55:0x017f  */
    /* JADX WARN: Code duplicated, block: B:57:0x0183  */
    /* JADX WARN: Code duplicated, block: B:58:0x0187  */
    /* JADX WARN: Code duplicated, block: B:60:0x018a  */
    /* JADX WARN: Code duplicated, block: B:61:0x018f  */
    /* JADX WARN: Code duplicated, block: B:64:0x0198  */
    /* JADX WARN: Code duplicated, block: B:66:0x019d  */
    /* JADX WARN: Code duplicated, block: B:67:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:69:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:75:0x00c5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x00a6 A[SYNTHETIC] */
    private final IrExpression getAccessCallForEarlierScript(ScriptToClassTransformerContext data, IrDeclarationReference expression, IrType maybeScriptType, IrStatementOrigin expressionOrigin) {
        IrClassifierSymbol classifierOrNull;
        int i;
        int i2;
        IrClassSymbol arrayClass;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        boolean z;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default;
        IrGetFieldImpl irGetFieldImplIrGetField$default;
        DeclarationIrBuilder declarationIrBuilder;
        IrCall irCall;
        IrClass irClass;
        IrDeclarationOrigin origin;
        IrClassSymbol targetClass;
        List<IrScriptSymbol> earlierScripts = this.irScript.getEarlierScripts();
        IrClass owner = null;
        if (earlierScripts == null || earlierScripts.isEmpty() || (classifierOrNull = IrTypesKt.getClassifierOrNull(maybeScriptType)) == null) {
            return null;
        }
        IrSymbolOwner owner2 = classifierOrNull.getOwner();
        boolean z2 = owner2 instanceof IrScript;
        if (z2) {
            List<IrScriptSymbol> earlierScripts2 = this.irScript.getEarlierScripts();
            Intrinsics.checkNotNull(earlierScripts2);
            Iterator<IrScriptSymbol> it = earlierScripts2.iterator();
            i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i2 = -1;
                    if (i2 >= 0) {
                        arrayClass = this.context.getIrBuiltIns().getArrayClass();
                        irSimpleFunctionSymbol = null;
                        z = false;
                        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(arrayClass)) {
                            if (!Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.GET)) {
                                if (!z) {
                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                }
                                z = true;
                                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                            }
                        }
                        if (z) {
                            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = irSimpleFunctionSymbol;
                            declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
                            if (data.isInScriptConstructor()) {
                                IrType defaultType = IrTypesKt.getDefaultType(arrayClass);
                                IrValueParameter earlierScriptsParameter = this.irScript.getEarlierScriptsParameter();
                                Intrinsics.checkNotNull(earlierScriptsParameter);
                                irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, defaultType, earlierScriptsParameter.getSymbol());
                            } else {
                                IrExpression accessCallForScriptInstance = getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expressionOrigin, null);
                                IrField irField = this.earlierScriptsField;
                                Intrinsics.checkNotNull(irField);
                                irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, accessCallForScriptInstance, irField, null, 4, null);
                            }
                            declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol3);
                            irCall.setDispatchReceiver(irGetFieldImplIrGetField$default);
                            irCall.putValueArgument(0, IrUtilsKt.toIrConst$default(Integer.valueOf(i2), ((IrValueParameter) CollectionsKt.first((List) irSimpleFunctionSymbol3.getOwner().getValueParameters())).getType(), 0, 0, 6, null));
                            if (z2) {
                                targetClass = ((IrScript) owner2).getTargetClass();
                                if (targetClass != null) {
                                    owner = targetClass.getOwner();
                                }
                            } else {
                                if (owner2 instanceof IrClass) {
                                    irClass = (IrClass) owner2;
                                } else {
                                    irClass = null;
                                }
                                if (irClass != null) {
                                    origin = irClass.getOrigin();
                                } else {
                                    origin = null;
                                }
                                if (Intrinsics.areEqual(origin, IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
                                    owner = (IrClass) owner2;
                                }
                            }
                            if (owner == null) {
                                return irCall;
                            }
                            return ExpressionHelpersKt.irImplicitCast(declarationIrBuilder, irCall, IrUtilsKt.getDefaultType(owner));
                        }
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                } else if (Intrinsics.areEqual(it.next(), classifierOrNull)) {
                    i2 = i;
                    if (i2 >= 0) {
                        arrayClass = this.context.getIrBuiltIns().getArrayClass();
                        irSimpleFunctionSymbol = null;
                        z = false;
                        while (r1.hasNext()) {
                            if (!Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.GET)) {
                                if (!z) {
                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                }
                                z = true;
                                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                            }
                        }
                        if (z) {
                            IrSimpleFunctionSymbol irSimpleFunctionSymbol4 = irSimpleFunctionSymbol;
                            declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
                            if (data.isInScriptConstructor()) {
                                IrType defaultType2 = IrTypesKt.getDefaultType(arrayClass);
                                IrValueParameter earlierScriptsParameter2 = this.irScript.getEarlierScriptsParameter();
                                Intrinsics.checkNotNull(earlierScriptsParameter2);
                                irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, defaultType2, earlierScriptsParameter2.getSymbol());
                            } else {
                                IrExpression accessCallForScriptInstance2 = getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expressionOrigin, null);
                                IrField irField2 = this.earlierScriptsField;
                                Intrinsics.checkNotNull(irField2);
                                irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, accessCallForScriptInstance2, irField2, null, 4, null);
                            }
                            declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol4);
                            irCall.setDispatchReceiver(irGetFieldImplIrGetField$default);
                            irCall.putValueArgument(0, IrUtilsKt.toIrConst$default(Integer.valueOf(i2), ((IrValueParameter) CollectionsKt.first((List) irSimpleFunctionSymbol4.getOwner().getValueParameters())).getType(), 0, 0, 6, null));
                            if (z2) {
                                targetClass = ((IrScript) owner2).getTargetClass();
                                if (targetClass != null) {
                                    owner = targetClass.getOwner();
                                }
                            } else {
                                if (owner2 instanceof IrClass) {
                                    irClass = (IrClass) owner2;
                                } else {
                                    irClass = null;
                                }
                                if (irClass != null) {
                                    origin = irClass.getOrigin();
                                } else {
                                    origin = null;
                                }
                                if (Intrinsics.areEqual(origin, IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
                                    owner = (IrClass) owner2;
                                }
                            }
                            if (owner == null) {
                                return irCall;
                            }
                            return ExpressionHelpersKt.irImplicitCast(declarationIrBuilder, irCall, IrUtilsKt.getDefaultType(owner));
                        }
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                } else {
                    i++;
                }
            }
        } else {
            IrClass irClass2 = owner2 instanceof IrClass ? (IrClass) owner2 : null;
            if (Intrinsics.areEqual(irClass2 != null ? irClass2.getOrigin() : null, IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
                List<IrScriptSymbol> earlierScripts3 = this.irScript.getEarlierScripts();
                Intrinsics.checkNotNull(earlierScripts3);
                Iterator<IrScriptSymbol> it2 = earlierScripts3.iterator();
                i = 0;
                while (true) {
                    if (!it2.hasNext()) {
                        i2 = -1;
                        if (i2 >= 0) {
                            arrayClass = this.context.getIrBuiltIns().getArrayClass();
                            irSimpleFunctionSymbol = null;
                            z = false;
                            while (r1.hasNext()) {
                                if (!Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.GET)) {
                                    if (!z) {
                                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                    }
                                    z = true;
                                    irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                                }
                            }
                            if (z) {
                                IrSimpleFunctionSymbol irSimpleFunctionSymbol5 = irSimpleFunctionSymbol;
                                declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
                                if (data.isInScriptConstructor()) {
                                    IrType defaultType3 = IrTypesKt.getDefaultType(arrayClass);
                                    IrValueParameter earlierScriptsParameter3 = this.irScript.getEarlierScriptsParameter();
                                    Intrinsics.checkNotNull(earlierScriptsParameter3);
                                    irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, defaultType3, earlierScriptsParameter3.getSymbol());
                                } else {
                                    IrExpression accessCallForScriptInstance3 = getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expressionOrigin, null);
                                    IrField irField3 = this.earlierScriptsField;
                                    Intrinsics.checkNotNull(irField3);
                                    irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, accessCallForScriptInstance3, irField3, null, 4, null);
                                }
                                declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                                irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol5);
                                irCall.setDispatchReceiver(irGetFieldImplIrGetField$default);
                                irCall.putValueArgument(0, IrUtilsKt.toIrConst$default(Integer.valueOf(i2), ((IrValueParameter) CollectionsKt.first((List) irSimpleFunctionSymbol5.getOwner().getValueParameters())).getType(), 0, 0, 6, null));
                                if (z2) {
                                    targetClass = ((IrScript) owner2).getTargetClass();
                                    if (targetClass != null) {
                                        owner = targetClass.getOwner();
                                    }
                                } else {
                                    if (owner2 instanceof IrClass) {
                                        irClass = (IrClass) owner2;
                                    } else {
                                        irClass = null;
                                    }
                                    if (irClass != null) {
                                        origin = irClass.getOrigin();
                                    } else {
                                        origin = null;
                                    }
                                    if (Intrinsics.areEqual(origin, IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
                                        owner = (IrClass) owner2;
                                    }
                                }
                                if (owner == null) {
                                    return irCall;
                                }
                                return ExpressionHelpersKt.irImplicitCast(declarationIrBuilder, irCall, IrUtilsKt.getDefaultType(owner));
                            }
                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                        }
                    } else if (Intrinsics.areEqual(it2.next().getOwner().getTargetClass(), classifierOrNull)) {
                        i2 = i;
                        if (i2 >= 0) {
                            arrayClass = this.context.getIrBuiltIns().getArrayClass();
                            irSimpleFunctionSymbol = null;
                            z = false;
                            while (r1.hasNext()) {
                                if (!Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.GET)) {
                                    if (!z) {
                                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                    }
                                    z = true;
                                    irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                                }
                            }
                            if (z) {
                                IrSimpleFunctionSymbol irSimpleFunctionSymbol6 = irSimpleFunctionSymbol;
                                declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
                                if (data.isInScriptConstructor()) {
                                    IrType defaultType4 = IrTypesKt.getDefaultType(arrayClass);
                                    IrValueParameter earlierScriptsParameter4 = this.irScript.getEarlierScriptsParameter();
                                    Intrinsics.checkNotNull(earlierScriptsParameter4);
                                    irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, defaultType4, earlierScriptsParameter4.getSymbol());
                                } else {
                                    IrExpression accessCallForScriptInstance4 = getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expressionOrigin, null);
                                    IrField irField4 = this.earlierScriptsField;
                                    Intrinsics.checkNotNull(irField4);
                                    irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, accessCallForScriptInstance4, irField4, null, 4, null);
                                }
                                declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                                irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol6);
                                irCall.setDispatchReceiver(irGetFieldImplIrGetField$default);
                                irCall.putValueArgument(0, IrUtilsKt.toIrConst$default(Integer.valueOf(i2), ((IrValueParameter) CollectionsKt.first((List) irSimpleFunctionSymbol6.getOwner().getValueParameters())).getType(), 0, 0, 6, null));
                                if (z2) {
                                    targetClass = ((IrScript) owner2).getTargetClass();
                                    if (targetClass != null) {
                                        owner = targetClass.getOwner();
                                    }
                                } else {
                                    if (owner2 instanceof IrClass) {
                                        irClass = (IrClass) owner2;
                                    } else {
                                        irClass = null;
                                    }
                                    if (irClass != null) {
                                        origin = irClass.getOrigin();
                                    } else {
                                        origin = null;
                                    }
                                    if (Intrinsics.areEqual(origin, IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
                                        owner = (IrClass) owner2;
                                    }
                                }
                                if (owner == null) {
                                    return irCall;
                                }
                                return ExpressionHelpersKt.irImplicitCast(declarationIrBuilder, irCall, IrUtilsKt.getDefaultType(owner));
                            }
                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue expression, ScriptToClassTransformerContext data) {
        IrExpression dispatchReceiverExpression;
        IrGetFieldImpl irGetFieldImplIrGetField$default;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrValueDeclaration owner = expression.getSymbol().getOwner();
        Object obj = null;
        IrVariable irVariable = owner instanceof IrVariable ? (IrVariable) owner : null;
        IrValueDeclaration owner2 = expression.getSymbol().getOwner();
        IrValueParameter irValueParameter = owner2 instanceof IrValueParameter ? (IrValueParameter) owner2 : null;
        if (irVariable == null || !this.irScript.getExplicitCallParameters().contains(irVariable)) {
            if (irValueParameter != null && ScriptLoweringKt.getNeedsReceiverProcessing(this.irScript) && isValidNameForReceiver(irValueParameter.getName()) && (dispatchReceiverExpression = getDispatchReceiverExpression(data, expression, irValueParameter.getType(), expression.getOrigin(), irValueParameter)) != null) {
                return IrElementTransformer.DefaultImpls.visitExpression(this, dispatchReceiverExpression, data);
            }
            return IrElementTransformer.DefaultImpls.visitGetValue(this, expression, data);
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, (Object) null);
        if (data.isInScriptConstructor()) {
            for (Object obj2 : ((IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(this.irScriptClass))).getValueParameters()) {
                IrValueParameter irValueParameter2 = (IrValueParameter) obj2;
                if (Intrinsics.areEqual(irValueParameter2.getOrigin(), IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE) && Intrinsics.areEqual(irValueParameter2.getName(), irVariable.getName())) {
                    obj = obj2;
                    break;
                }
            }
            IrValueParameter irValueParameter3 = (IrValueParameter) obj;
            if (irValueParameter3 == null) {
                throw new IllegalStateException(("script explicit parameter " + irVariable.getName().asString() + " not found").toString());
            }
            irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter3.getType(), irValueParameter3.getSymbol());
        } else {
            for (Object obj3 : this.irScriptClass.getDeclarations()) {
                IrDeclaration irDeclaration = (IrDeclaration) obj3;
                if ((irDeclaration instanceof IrField) && Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE) && Intrinsics.areEqual(((IrField) irDeclaration).getName(), irVariable.getName())) {
                    obj = obj3;
                    break;
                }
            }
            IrDeclaration irDeclaration2 = (IrDeclaration) obj;
            if (irDeclaration2 == null) {
                throw new IllegalStateException(("script explicit parameter " + irVariable.getName().asString() + " corresponding property not found").toString());
            }
            irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, getAccessCallForScriptInstance(data, expression.getStartOffset(), expression.getEndOffset(), expression.getOrigin(), null), (IrField) irDeclaration2, null, 4, null);
        }
        return IrElementTransformer.DefaultImpls.visitExpression(this, irGetFieldImplIrGetField$default, data);
    }

    private final boolean isValidNameForReceiver(Name name2) {
        if (!Intrinsics.areEqual(name2, SpecialNames.THIS)) {
            List<IrValueParameter> implicitReceiversParameters = this.irScript.getImplicitReceiversParameters();
            if (!(implicitReceiversParameters instanceof Collection) || !implicitReceiversParameters.isEmpty()) {
                Iterator<T> it = implicitReceiversParameters.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((IrValueParameter) it.next()).getName(), name2)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final boolean isCurrentScriptTopLevelDeclaration(IrDeclaration irDeclaration, ScriptToClassTransformerContext scriptToClassTransformerContext) {
        if (scriptToClassTransformerContext.getTopLevelDeclaration() == null) {
            return false;
        }
        if (!Intrinsics.areEqual(irDeclaration.getParent(), this.irScript) && !Intrinsics.areEqual(irDeclaration.getParent(), this.irScriptClass)) {
            return false;
        }
        if (irDeclaration instanceof IrFunction) {
            irDeclaration = JvmIrUtilsKt.getPropertyIfAccessor((IrFunction) irDeclaration);
        }
        return Intrinsics.areEqual(irDeclaration, scriptToClassTransformerContext.getTopLevelDeclaration());
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002d  */
    private final boolean needsScriptReceiver(IrDeclaration irDeclaration) {
        IrValueParameter dispatchReceiverParameter;
        IrSimpleFunction getter;
        if (irDeclaration instanceof IrFunction) {
            dispatchReceiverParameter = ((IrFunction) irDeclaration).getDispatchReceiverParameter();
        } else if (!(irDeclaration instanceof IrProperty) || (getter = ((IrProperty) irDeclaration).getGetter()) == null) {
            dispatchReceiverParameter = null;
        } else {
            if (Intrinsics.areEqual(getter.getOrigin(), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE)) {
                getter = null;
            }
            if (getter != null) {
                dispatchReceiverParameter = getter.getDispatchReceiverParameter();
            } else {
                dispatchReceiverParameter = null;
            }
        }
        return Intrinsics.areEqual(dispatchReceiverParameter != null ? dispatchReceiverParameter.getOrigin() : null, IrDeclarationOrigin.SCRIPT_THIS_RECEIVER.INSTANCE);
    }
}
