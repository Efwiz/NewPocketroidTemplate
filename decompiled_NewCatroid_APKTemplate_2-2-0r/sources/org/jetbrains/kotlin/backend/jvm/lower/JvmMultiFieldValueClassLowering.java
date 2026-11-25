package org.jetbrains.kotlin.backend.jvm.lower;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.AccessType;
import org.jetbrains.kotlin.backend.jvm.IntermediateMfvcNode;
import org.jetbrains.kotlin.backend.jvm.IrPropertyOrIrField;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.LeafMfvcNode;
import org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements;
import org.jetbrains.kotlin.backend.jvm.MfvcNode;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeFactoryKt;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeInstanceKt;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeKt;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes;
import org.jetbrains.kotlin.backend.jvm.NameableMfvcNode;
import org.jetbrains.kotlin.backend.jvm.ReceiverBasedMfvcNodeInstance;
import org.jetbrains.kotlin.backend.jvm.RootMfvcNode;
import org.jetbrains.kotlin.backend.jvm.ValueDeclarationMfvcNodeInstance;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
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
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
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
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ü\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u0001:\u0006£\u0001¤\u0001¥\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0014J>\u00100\u001a\u00020,2\u0006\u0010/\u001a\u00020.2\u001e\u00101\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u000204\u0012\n\u0012\b\u0012\u0004\u0012\u0002040203022\f\u00105\u001a\b\u0012\u0004\u0012\u00020602H\u0002J<\u00107\u001a\u001e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:08j\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:`;2\u0016\u0010<\u001a\u0012\u0012\u0004\u0012\u0002090=j\b\u0012\u0004\u0012\u000209`>H\u0002J(\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u00020)2\u0006\u0010A\u001a\u00020)2\u0006\u0010-\u001a\u00020.2\u0006\u0010B\u001a\u00020\u001eH\u0014J \u0010C\u001a\u00020)2\u0006\u0010@\u001a\u00020)2\u0006\u0010/\u001a\u00020)2\u0006\u0010D\u001a\u00020EH\u0016J\b\u0010F\u001a\u00020GH\u0002J\u0010\u0010H\u001a\u00020,2\u0006\u0010I\u001a\u00020\u0006H\u0002J\u0010\u0010J\u001a\u00020,2\u0006\u0010K\u001a\u00020\u0006H\u0016J \u0010L\u001a\u00020M2\u0006\u0010I\u001a\u00020\u00062\u0006\u0010N\u001a\u00020&2\u0006\u0010O\u001a\u00020PH\u0002J8\u0010Q\u001a\b\u0012\u0004\u0012\u00020\n022\f\u0010R\u001a\b\u0012\u0004\u0012\u00020&0S2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:0U2\u0006\u0010I\u001a\u00020\u0006H\u0002J;\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020.2\u0006\u0010Y\u001a\u00020Z2!\u0010[\u001a\u001d\u0012\u0013\u0012\u00110)¢\u0006\f\b]\u0012\b\b^\u0012\u0004\b\b(_\u0012\u0004\u0012\u00020`0\\H\u0002J\u0010\u0010a\u001a\u00020,2\u0006\u0010b\u001a\u00020\nH\u0014J\b\u0010c\u001a\u00020,H\u0002J\u0010\u0010d\u001a\u00020,2\u0006\u0010K\u001a\u00020\u0006H\u0002J\u0010\u0010e\u001a\u00020,2\u0006\u0010f\u001a\u00020gH\u0002J\u001e\u0010h\u001a\b\u0012\u0004\u0012\u00020\n022\u0006\u0010i\u001a\u00020j2\u0006\u0010/\u001a\u00020)H\u0014J\u0010\u0010k\u001a\u00020,2\u0006\u0010l\u001a\u00020\u001cH\u0002J\u0010\u0010m\u001a\u00020n2\u0006\u0010Y\u001a\u00020\u001fH\u0016J\u0010\u0010o\u001a\u00020\u00062\u0006\u0010K\u001a\u00020\u0006H\u0016J\u0015\u0010p\u001a\u00020,2\u0006\u0010K\u001a\u00020\nH\u0010¢\u0006\u0002\bqJ\u0010\u0010r\u001a\u00020n2\u0006\u0010Y\u001a\u00020WH\u0016J\u0010\u0010s\u001a\u00020n2\u0006\u0010Y\u001a\u00020tH\u0016J\u0010\u0010u\u001a\u00020n2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0010\u0010v\u001a\u00020n2\u0006\u0010Y\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020n2\u0006\u0010Y\u001a\u00020yH\u0016J\u0010\u0010z\u001a\u00020n2\u0006\u0010{\u001a\u00020|H\u0002J\u0010\u0010}\u001a\u00020,2\u0006\u0010~\u001a\u000204H\u0016J\u0011\u0010\u007f\u001a\u00020n2\u0007\u0010Y\u001a\u00030\u0080\u0001H\u0016J\u0012\u0010\u0081\u0001\u001a\u00020n2\u0007\u0010Y\u001a\u00030\u0082\u0001H\u0016J\u0012\u0010\u0083\u0001\u001a\u00020n2\u0007\u0010Y\u001a\u00030\u0084\u0001H\u0016J\u0012\u0010\u0085\u0001\u001a\u00030\u0086\u00012\u0006\u0010K\u001a\u00020\u001cH\u0016JM\u0010\u0087\u0001\u001a\u0007\u0012\u0002\b\u00030\u0088\u0001*\u00030\u0089\u00012\u0006\u0010X\u001a\u00020.2\u000b\u0010-\u001a\u0007\u0012\u0002\b\u00030\u0088\u00012\u0006\u0010/\u001a\u00020.2\u001b\b\u0002\u0010\u008a\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u008b\u0001\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u0088\u00010\\H\u0002J \u0010\u008c\u0001\u001a\u00020n*\u00030\u008d\u00012\u0006\u0010Y\u001a\u00020n2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\u001a\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020n02*\u00030\u0089\u00012\u0006\u0010Y\u001a\u00020nJ \u0010\u0091\u0001\u001a\u00020,*\u00030\u0089\u00012\u0006\u0010Y\u001a\u00020n2\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0002J\u000f\u0010\u0094\u0001\u001a\u0004\u0018\u00010.*\u00020.H\u0002J\r\u0010\u0095\u0001\u001a\u00020\u001e*\u00020|H\u0002J\r\u0010\u0096\u0001\u001a\u00020\u001e*\u00020\u0006H\u0016J6\u0010\u0097\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010n02*\u00030\u0089\u00012\u000f\u0010\u0098\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010n022\r\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020602H\u0002JI\u0010\u009a\u0001\u001a\u00020,*\u0007\u0012\u0002\b\u00030\u0088\u00012\u0006\u0010A\u001a\u00020.2\u0006\u0010@\u001a\u00020.2%\u0010\u009b\u0001\u001a \u0012\u0015\u0012\u00130\u009c\u0001¢\u0006\r\b]\u0012\t\b^\u0012\u0005\b\b(\u009d\u0001\u0012\u0005\u0012\u00030\u008f\u00010\\H\u0002J\r\u0010\u009e\u0001\u001a\u00020,*\u00020`H\u0002J,\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020n02*\u00030\u008d\u00012\u0007\u0010 \u0001\u001a\u00020W2\r\u0010¡\u0001\u001a\b\u0012\u0004\u0012\u00020\u001c02H\u0002J\r\u0010¢\u0001\u001a\u00020,*\u00020gH\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00060\u0018R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u000e0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001d\u001a\u00020\u001e*\u00020\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010 R\u0018\u0010!\u001a\u00020\u001e*\u00020\"8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010#R\u001a\u0010$\u001a\u0004\u0018\u00010%*\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001a\u0010$\u001a\u0004\u0018\u00010%*\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010*¨\u0006¦\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "irCurrentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrCurrentClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irCurrentScope", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getIrCurrentScope", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "possibleExtraBoxUsageGenerated", "", "replacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "getReplacements", "()Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "specificMangle", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "getSpecificMangle", "()Lorg/jetbrains/kotlin/backend/jvm/lower/JvmValueClassAbstractLowering$SpecificMangle;", "valueDeclarationsRemapper", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$ValueDeclarationRemapper;", "variablesToAdd", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "isSpecializedMFVCEqEq", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)Z", "isTemporary", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)Z", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getProperty", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "addBindingsFor", "", "original", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "replacement", "addDefaultArgumentsIfNeeded", "old2newList", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parametersStructure", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "collectRegularClassMfvcPropertiesOrFieldsReplacement", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField;", "Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "Lkotlin/collections/LinkedHashMap;", "propertiesOrFields", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "createBridgeBody", FirebaseAnalytics.Param.SOURCE, "target", "inverted", "createBridgeDeclaration", "mangledName", "Lorg/jetbrains/kotlin/name/Name;", "getCurrentScopeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "handleNonSpecificNewClass", "irClass", "handleSpecificNewClass", "declaration", "makeInitializerReplacement", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "initializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "makeNewDeclarationsForRegularClass", "fieldsToRemove", "", "propertiesOrFieldsReplacement", "", "makeNewLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "originalFunction", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "makeBody", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "wrapper", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "postActionAfterTransformingClassDeclaration", "replacingDeclaration", "registerPossibleExtraBoxUsage", "replaceMfvcStaticFields", "replacePrimaryMultiFieldValueClassConstructor", "rootMfvcNode", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "transformSecondaryConstructorFlat", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "variablesSaver", "variable", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitClassNew", "visitClassNewDeclarationsWhenParallel", "visitClassNewDeclarationsWhenParallel$backend_jvm_lower", "visitContainerExpression", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionReference", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitLambda", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitParameter", "parameter", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "buildReplacement", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "makeMemberAccessExpression", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "castExpressionToNotNullTypeIfNeeded", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "flattenExpression", "flattenExpressionTo", "instance", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "getReplacement", "hasLambdaLikeOrigin", "isSpecificLoweringLogicApplicable", "makeNewArguments", "oldArguments", "structure", "passTypeArgumentsWithOffsets", "forCommonTypeParameters", "", "targetIndex", "removeAllExtraBoxes", "removeExtraSetVariablesFromExpressionList", "block", "variables", "replaceMfvcNotStaticFields", "MfvcNodeInstanceAccessor", "UNSAFE_MFVC_SET_ORIGIN", "ValueDeclarationRemapper", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmMultiFieldValueClassLowering extends JvmValueClassAbstractLowering {
    private final Set<IrDeclaration> possibleExtraBoxUsageGenerated;
    private final ValueDeclarationRemapper valueDeclarationsRemapper;
    private final Map<IrDeclarationParent, Set<IrVariable>> variablesToAdd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmMultiFieldValueClassLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.possibleExtraBoxUsageGenerated = new LinkedHashSet();
        this.valueDeclarationsRemapper = new ValueDeclarationRemapper();
        this.variablesToAdd = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\n\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u00002\u0006\u0010\b\u001a\u00020\tH¦\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor;", "", "()V", "instance", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "getInstance", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "Getter", "Setter", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor$Getter;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor$Setter;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class MfvcNodeInstanceAccessor {
        public /* synthetic */ MfvcNodeInstanceAccessor(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract MfvcNodeInstanceAccessor get(Name name2);

        public abstract MfvcNodeInstance getInstance();

        private MfvcNodeInstanceAccessor() {
        }

        /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\u0013\u0010\r\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0002J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor$Getter;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor;", "instance", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "(Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;)V", "getInstance", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Getter extends MfvcNodeInstanceAccessor {
            private final MfvcNodeInstance instance;

            public static /* synthetic */ Getter copy$default(Getter getter, MfvcNodeInstance mfvcNodeInstance, int i, Object obj) {
                if ((i & 1) != 0) {
                    mfvcNodeInstance = getter.instance;
                }
                return getter.copy(mfvcNodeInstance);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final MfvcNodeInstance getInstance() {
                return this.instance;
            }

            public final Getter copy(MfvcNodeInstance instance) {
                Intrinsics.checkNotNullParameter(instance, "instance");
                return new Getter(instance);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Getter) && Intrinsics.areEqual(this.instance, ((Getter) other).instance);
            }

            public int hashCode() {
                return this.instance.hashCode();
            }

            public String toString() {
                return "Getter(instance=" + this.instance + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Getter(MfvcNodeInstance instance) {
                super(null);
                Intrinsics.checkNotNullParameter(instance, "instance");
                this.instance = instance;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor
            public MfvcNodeInstance getInstance() {
                return this.instance;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor
            public Getter get(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                MfvcNodeInstance mfvcNodeInstance = getInstance().get(name2);
                if (mfvcNodeInstance != null) {
                    return new Getter(mfvcNodeInstance);
                }
                return null;
            }
        }

        /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0014\u001a\u00020\u0015H\u0096\u0002J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor$Setter;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor;", "instance", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", CompoundEntry.NAME_values, "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;Ljava/util/List;)V", "getInstance", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "getValues", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Setter extends MfvcNodeInstanceAccessor {
            private final MfvcNodeInstance instance;
            private final List<IrExpression> values;

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ Setter copy$default(Setter setter, MfvcNodeInstance mfvcNodeInstance, List list, int i, Object obj) {
                if ((i & 1) != 0) {
                    mfvcNodeInstance = setter.instance;
                }
                if ((i & 2) != 0) {
                    list = setter.values;
                }
                return setter.copy(mfvcNodeInstance, list);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final MfvcNodeInstance getInstance() {
                return this.instance;
            }

            public final List<IrExpression> component2() {
                return this.values;
            }

            public final Setter copy(MfvcNodeInstance instance, List<? extends IrExpression> values) {
                Intrinsics.checkNotNullParameter(instance, "instance");
                Intrinsics.checkNotNullParameter(values, "values");
                return new Setter(instance, values);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Setter)) {
                    return false;
                }
                Setter setter = (Setter) other;
                return Intrinsics.areEqual(this.instance, setter.instance) && Intrinsics.areEqual(this.values, setter.values);
            }

            public int hashCode() {
                return (this.instance.hashCode() * 31) + this.values.hashCode();
            }

            public String toString() {
                return "Setter(instance=" + this.instance + ", values=" + this.values + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Setter(MfvcNodeInstance instance, List<? extends IrExpression> values) {
                super(null);
                Intrinsics.checkNotNullParameter(instance, "instance");
                Intrinsics.checkNotNullParameter(values, "values");
                this.instance = instance;
                this.values = values;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor
            public MfvcNodeInstance getInstance() {
                return this.instance;
            }

            public final List<IrExpression> getValues() {
                return this.values;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor
            public Setter get(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                MfvcNodeInstance mfvcNodeInstance = getInstance().get(name2);
                if (mfvcNodeInstance == null) {
                    return null;
                }
                Object setter = getInstance();
                Intrinsics.checkNotNull(setter, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes");
                IntRange intRange = ((MfvcNodeWithSubnodes) setter).getSubnodeIndices().get(mfvcNodeInstance.getNode());
                Intrinsics.checkNotNull(intRange);
                return new Setter(mfvcNodeInstance, CollectionsKt.slice((List) this.values, intRange));
            }
        }
    }

    private final IrDeclaration getIrCurrentScope() {
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        IrElement irElement = currentScope.getIrElement();
        Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return (IrDeclaration) irElement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getIrCurrentClass() {
        ScopeWithIr currentClass = getCurrentClass();
        Intrinsics.checkNotNull(currentClass);
        IrElement irElement = currentClass.getIrElement();
        Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return (IrClass) irElement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerPossibleExtraBoxUsage() {
        this.possibleExtraBoxUsageGenerated.add(getIrCurrentScope());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JL\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000522\u0010\u0014\u001a.\u0012\u0004\u0012\u00020\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00050\u0017¢\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00050\u0015¢\u0006\u0002\b\u001bJ\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001d2\u0006\u0010\u0013\u001a\u00020\rH\u0002J\u001c\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001d2\u0006\u0010\u0013\u001a\u00020!H\u0002J\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\tJ\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%J\u0016\u0010\"\u001a\u00020#2\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(J.\u0010)\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00170*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\u0017H\u0002J\u0014\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00162\u0006\u0010\u0013\u001a\u00020!J\u0014\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00162\u0006\u0010\u0013\u001a\u00020-J\u001c\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00162\u0006\u0010\u0013\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u001c\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00162\u0006\u0010\u0013\u001a\u0002012\u0006\u0010/\u001a\u000200J\u001c\u00102\u001a\u0004\u0018\u00010\u0005*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u000203JF\u00104\u001a\u0004\u0018\u00010\u0005*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052.\u0010\u0014\u001a*\u0012\u0004\u0012\u00020\u0012\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(5\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0015¢\u0006\u0002\b\u001bH\u0002J.\u00106\u001a\u00020\u0005*\u00020\u00122\u0006\u00107\u001a\u0002082\u0017\u00109\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020#0:¢\u0006\u0002\b\u001bH\u0082\bJ*\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00050\u0017*\u00020\u00162\u0006\u0010<\u001a\u00020=2\u0006\u0010/\u001a\u0002002\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J\u0014\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00122\u0006\u0010\u0013\u001a\u00020?R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\u00020\f*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$ValueDeclarationRemapper;", "", "(Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering;)V", "expression2MfvcNodeInstanceAccessor", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$MfvcNodeInstanceAccessor;", "oldSymbol2MfvcNodeInstance", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/ValueDeclarationMfvcNodeInstance;", "oldValueSymbol2NewValueSymbol", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "getField", "(Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "handleFlattenedGetterExpressions", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "expression", "handler", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "", "Lkotlin/ParameterName;", "name", CompoundEntry.NAME_values, "Lkotlin/ExtensionFunctionType;", "makeTypeArgumentsFromField", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "makeTypeArgumentsFromFunction", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "registerReplacement", "", "original", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "replacement", "instance", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "splitExpressions", "Lkotlin/Pair;", "expressions", "addReplacement", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "safe", "", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "get", "Lorg/jetbrains/kotlin/name/Name;", "handleSavedExpression", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "irContainer", "oldBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "builder", "Lkotlin/Function1;", "makeFlattenedExpressionsWithGivenSafety", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "makeReplacement", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ValueDeclarationRemapper {
        private final Map<IrExpression, MfvcNodeInstanceAccessor> expression2MfvcNodeInstanceAccessor = new LinkedHashMap();
        private final Map<IrValueSymbol, ValueDeclarationMfvcNodeInstance> oldSymbol2MfvcNodeInstance = new LinkedHashMap();
        private final Map<IrValueSymbol, IrValueSymbol> oldValueSymbol2NewValueSymbol = new LinkedHashMap();

        public ValueDeclarationRemapper() {
        }

        public final void registerReplacement(IrValueDeclaration original, IrValueDeclaration replacement) {
            Intrinsics.checkNotNullParameter(original, "original");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            this.oldValueSymbol2NewValueSymbol.put(original.getSymbol(), replacement.getSymbol());
        }

        public final void registerReplacement(IrValueDeclaration original, ValueDeclarationMfvcNodeInstance replacement) {
            Intrinsics.checkNotNullParameter(original, "original");
            Intrinsics.checkNotNullParameter(replacement, "replacement");
            this.oldSymbol2MfvcNodeInstance.put(original.getSymbol(), replacement);
        }

        public final IrExpression makeReplacement(IrBuilderWithScope irBuilderWithScope, IrGetValue expression) {
            Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueSymbol irValueSymbol = this.oldValueSymbol2NewValueSymbol.get(expression.getSymbol());
            if (irValueSymbol == null) {
                ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = this.oldSymbol2MfvcNodeInstance.get(expression.getSymbol());
                if (valueDeclarationMfvcNodeInstance == null) {
                    return null;
                }
                IrExpression irExpressionMakeGetterExpression = valueDeclarationMfvcNodeInstance.makeGetterExpression(irBuilderWithScope, JvmMultiFieldValueClassLowering.this.getIrCurrentClass(), new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$makeReplacement$res$1(JvmMultiFieldValueClassLowering.this));
                this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeGetterExpression, new MfvcNodeInstanceAccessor.Getter(valueDeclarationMfvcNodeInstance));
                return irExpressionMakeGetterExpression;
            }
            return ExpressionHelpersKt.irGet(irBuilderWithScope, irValueSymbol.getOwner());
        }

        public final IrExpression addReplacement(IrBlockBuilder irBlockBuilder, IrSetValue expression, boolean z) {
            Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueSymbol irValueSymbol = this.oldValueSymbol2NewValueSymbol.get(expression.getSymbol());
            if (irValueSymbol != null) {
                IrSetValueImpl irSetValueImplIrSet$default = ExpressionHelpersKt.irSet$default(irBlockBuilder, irValueSymbol.getOwner(), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null);
                irBlockBuilder.unaryPlus(irSetValueImplIrSet$default);
                return irSetValueImplIrSet$default;
            }
            ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = this.oldSymbol2MfvcNodeInstance.get(expression.getSymbol());
            if (valueDeclarationMfvcNodeInstance == null) {
                return null;
            }
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            List<IrExpression> listMakeFlattenedExpressionsWithGivenSafety = makeFlattenedExpressionsWithGivenSafety(irBlockBuilder, valueDeclarationMfvcNodeInstance.getNode(), z, JvmMultiFieldValueClassLowering.this.castExpressionToNotNullTypeIfNeeded(irBlockBuilder2, expression.getValue(), valueDeclarationMfvcNodeInstance.getType()));
            ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance2 = valueDeclarationMfvcNodeInstance;
            IrExpression irExpressionMakeSetterExpressions = MfvcNodeInstanceKt.makeSetterExpressions(valueDeclarationMfvcNodeInstance2, irBlockBuilder2, listMakeFlattenedExpressionsWithGivenSafety);
            this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeSetterExpressions, new MfvcNodeInstanceAccessor.Setter(valueDeclarationMfvcNodeInstance2, listMakeFlattenedExpressionsWithGivenSafety));
            irBlockBuilder.unaryPlus(irExpressionMakeSetterExpressions);
            return irExpressionMakeSetterExpressions;
        }

        private final List<IrExpression> makeFlattenedExpressionsWithGivenSafety(IrBlockBuilder irBlockBuilder, MfvcNode mfvcNode, boolean z, IrExpression irExpression) {
            ArrayList arrayList;
            if (z) {
                Pair<List<IrExpression>, List<IrExpression>> pairSplitExpressions = splitExpressions(JvmMultiFieldValueClassLowering.this.flattenExpression(irBlockBuilder, irExpression));
                List<IrExpression> listComponent1 = pairSplitExpressions.component1();
                List<IrExpression> listComponent2 = pairSplitExpressions.component2();
                if (!(mfvcNode instanceof LeafMfvcNode)) {
                    if (mfvcNode instanceof MfvcNodeWithSubnodes) {
                        List<IrExpression> list = listComponent1;
                        List<LeafMfvcNode> leaves = ((MfvcNodeWithSubnodes) mfvcNode).getLeaves();
                        Iterator<T> it = list.iterator();
                        Iterator<T> it2 = leaves.iterator();
                        ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(leaves, 10)));
                        while (it.hasNext() && it2.hasNext()) {
                            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                            arrayList2.add(ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, (IrExpression) it.next(), MfvcNodeKt.getFullFieldName((LeafMfvcNode) it2.next()).asString(), null, false, null, 28, null));
                        }
                        arrayList = arrayList2;
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                } else {
                    List<IrExpression> list2 = listComponent1;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it3 = list2.iterator();
                    while (it3.hasNext()) {
                        arrayList3.add(ExpressionHelpersKt.irTemporary$default(irBlockBuilder, (IrExpression) it3.next(), null, null, false, null, 30, null));
                    }
                    arrayList = arrayList3;
                }
                ArrayList arrayList4 = arrayList;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                Iterator it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    arrayList5.add(ExpressionHelpersKt.irGet(irBlockBuilder, (IrVariable) it4.next()));
                }
                return CollectionsKt.plus((Collection) arrayList5, (Iterable) listComponent2);
            }
            return JvmMultiFieldValueClassLowering.this.flattenExpression(irBlockBuilder, irExpression);
        }

        private final IrField getField(IrFieldAccessExpression irFieldAccessExpression) {
            return irFieldAccessExpression.getSymbol().getOwner();
        }

        public final IrExpression addReplacement(IrBlockBuilder irBlockBuilder, IrGetField expression) {
            IrExpression irExpression;
            Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrGetField irGetField = expression;
            IrField field = getField(irGetField);
            IrExpression receiver = expression.getReceiver();
            if (receiver == null || (irExpression = get(receiver, irBlockBuilder, field.getName())) == null) {
                NameableMfvcNode mfvcFieldNode = JvmMultiFieldValueClassLowering.this.getReplacements().getMfvcFieldNode(field);
                if (mfvcFieldNode == null) {
                    return null;
                }
                ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstanceCreateInstanceFromBox = mfvcFieldNode.createInstanceFromBox(irBlockBuilder, makeTypeArgumentsFromField(irGetField), expression.getReceiver(), AccessType.UseFields, new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$1(JvmMultiFieldValueClassLowering.this));
                IrExpression irExpressionMakeGetterExpression = receiverBasedMfvcNodeInstanceCreateInstanceFromBox.makeGetterExpression(irBlockBuilder, JvmMultiFieldValueClassLowering.this.getIrCurrentClass(), new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$getterExpression$1(JvmMultiFieldValueClassLowering.this));
                this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeGetterExpression, new MfvcNodeInstanceAccessor.Getter(receiverBasedMfvcNodeInstanceCreateInstanceFromBox));
                irBlockBuilder.unaryPlus(irExpressionMakeGetterExpression);
                return irExpressionMakeGetterExpression;
            }
            irBlockBuilder.unaryPlus(irExpression);
            return irExpression;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0045  */
        public final IrExpression addReplacement(IrBlockBuilder irBlockBuilder, IrSetField expression, boolean z) {
            ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstanceCreateInstanceFromBox;
            IrExpression irExpression;
            Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrSetField irSetField = expression;
            IrField field = getField(irSetField);
            NameableMfvcNode mfvcFieldNode = JvmMultiFieldValueClassLowering.this.getReplacements().getMfvcFieldNode(field);
            if (mfvcFieldNode == null) {
                return null;
            }
            IrExpression receiver = expression.getReceiver();
            if (receiver != null && (irExpression = get(receiver, irBlockBuilder, field.getName())) != null) {
                MfvcNodeInstanceAccessor mfvcNodeInstanceAccessor = this.expression2MfvcNodeInstanceAccessor.get(irExpression);
                Intrinsics.checkNotNull(mfvcNodeInstanceAccessor, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor.Getter");
                receiverBasedMfvcNodeInstanceCreateInstanceFromBox = ((MfvcNodeInstanceAccessor.Getter) mfvcNodeInstanceAccessor).getInstance();
                if (receiverBasedMfvcNodeInstanceCreateInstanceFromBox == null) {
                    receiverBasedMfvcNodeInstanceCreateInstanceFromBox = mfvcFieldNode.createInstanceFromBox(irBlockBuilder, makeTypeArgumentsFromField(irSetField), expression.getReceiver(), AccessType.UseFields, new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$3(JvmMultiFieldValueClassLowering.this));
                }
            } else {
                receiverBasedMfvcNodeInstanceCreateInstanceFromBox = mfvcFieldNode.createInstanceFromBox(irBlockBuilder, makeTypeArgumentsFromField(irSetField), expression.getReceiver(), AccessType.UseFields, new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$3(JvmMultiFieldValueClassLowering.this));
            }
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            List<IrExpression> listMakeFlattenedExpressionsWithGivenSafety = makeFlattenedExpressionsWithGivenSafety(irBlockBuilder, mfvcFieldNode, z, JvmMultiFieldValueClassLowering.this.castExpressionToNotNullTypeIfNeeded(irBlockBuilder2, expression.getValue(), mfvcFieldNode.getType()));
            IrExpression irExpressionMakeSetterExpressions = MfvcNodeInstanceKt.makeSetterExpressions(receiverBasedMfvcNodeInstanceCreateInstanceFromBox, irBlockBuilder2, listMakeFlattenedExpressionsWithGivenSafety);
            this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeSetterExpressions, new MfvcNodeInstanceAccessor.Setter(receiverBasedMfvcNodeInstanceCreateInstanceFromBox, listMakeFlattenedExpressionsWithGivenSafety));
            irBlockBuilder.unaryPlus(irExpressionMakeSetterExpressions);
            return irExpressionMakeSetterExpressions;
        }

        public final IrExpression addReplacement(IrBlockBuilder irBlockBuilder, IrCall expression) {
            IrExpression irExpression;
            Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrSimpleFunction owner = expression.getSymbol().getOwner();
            IrProperty property = JvmMultiFieldValueClassLowering.this.getProperty(owner);
            if (property != null) {
                if (!AdditionalIrUtilsKt.isGetter(owner)) {
                    property = null;
                }
                if (property != null) {
                    IrExpression dispatchReceiver = expression.getDispatchReceiver();
                    if (dispatchReceiver != null && (irExpression = get(dispatchReceiver, irBlockBuilder, property.getName())) != null) {
                        irBlockBuilder.unaryPlus(irExpression);
                        return irExpression;
                    }
                    NameableMfvcNode mfvcPropertyNode = JvmMultiFieldValueClassLowering.this.getReplacements().getMfvcPropertyNode(property);
                    if (mfvcPropertyNode == null) {
                        return null;
                    }
                    ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstanceCreateInstanceFromBox = mfvcPropertyNode.createInstanceFromBox(irBlockBuilder, makeTypeArgumentsFromFunction(expression), dispatchReceiver, AccessType.ChooseEffective, new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$4(JvmMultiFieldValueClassLowering.this));
                    IrExpression irExpressionMakeGetterExpression = receiverBasedMfvcNodeInstanceCreateInstanceFromBox.makeGetterExpression(irBlockBuilder, JvmMultiFieldValueClassLowering.this.getIrCurrentClass(), new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$getterExpression$2(JvmMultiFieldValueClassLowering.this));
                    this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeGetterExpression, new MfvcNodeInstanceAccessor.Getter(receiverBasedMfvcNodeInstanceCreateInstanceFromBox));
                    irBlockBuilder.unaryPlus(irExpressionMakeGetterExpression);
                    return irExpressionMakeGetterExpression;
                }
            }
            return null;
        }

        private final Map<IrTypeParameterSymbol, IrType> makeTypeArgumentsFromField(IrFieldAccessExpression expression) {
            IrType type;
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            IrType type2 = expression.getSymbol().getOwner().getType();
            Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            mapCreateMapBuilder.putAll(MfvcNodeKt.makeTypeArgumentsFromType((IrSimpleType) type2));
            IrExpression receiver = expression.getReceiver();
            if (receiver != null && (type = receiver.getType()) != null) {
                Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                mapCreateMapBuilder.putAll(MfvcNodeKt.makeTypeArgumentsFromType((IrSimpleType) type));
            }
            return MapsKt.build(mapCreateMapBuilder);
        }

        private final Map<IrTypeParameterSymbol, IrType> makeTypeArgumentsFromFunction(IrCall expression) {
            IrType type;
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            IrType returnType = expression.getSymbol().getOwner().getReturnType();
            Intrinsics.checkNotNull(returnType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            mapCreateMapBuilder.putAll(MfvcNodeKt.makeTypeArgumentsFromType((IrSimpleType) returnType));
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            if (dispatchReceiver != null && (type = dispatchReceiver.getType()) != null) {
                Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                mapCreateMapBuilder.putAll(MfvcNodeKt.makeTypeArgumentsFromType((IrSimpleType) type));
            }
            return MapsKt.build(mapCreateMapBuilder);
        }

        private final IrExpression handleSavedExpression(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, Function2<? super IrBuilderWithScope, ? super MfvcNodeInstanceAccessor, ? extends IrExpression> function2) {
            IrContainerExpression irBlockBody;
            MfvcNodeInstanceAccessor mfvcNodeInstanceAccessor = this.expression2MfvcNodeInstanceAccessor.get(irExpression);
            if (mfvcNodeInstanceAccessor != null) {
                irBlockBody = function2.invoke(irBuilderWithScope, mfvcNodeInstanceAccessor);
                if (irBlockBody == null) {
                    return null;
                }
            } else {
                if (!(irExpression instanceof IrContainerExpression)) {
                    return null;
                }
                IrContainerExpression irContainerExpression = (IrContainerExpression) irExpression;
                IrStatement irStatement = (IrStatement) CollectionsKt.lastOrNull((List) irContainerExpression.getStatements());
                if (!(irStatement instanceof IrExpression)) {
                    return null;
                }
                if (IrExpressionsKt.isTransparentScope(irContainerExpression)) {
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), irContainerExpression.getOrigin(), null, true);
                    IrExpression irExpressionHandleSavedExpression = handleSavedExpression(irBlockBuilder, (IrExpression) irStatement, function2);
                    if (irExpressionHandleSavedExpression == null) {
                        return null;
                    }
                    Iterator it = JvmMultiFieldValueClassLoweringKt.subListWithoutLast(irContainerExpression.getStatements(), 1).iterator();
                    while (it.hasNext()) {
                        irBlockBuilder.unaryPlus((IrStatement) it.next());
                    }
                    irBlockBuilder.unaryPlus(irExpressionHandleSavedExpression);
                    irBlockBody = irBlockBuilder.getIrBlockBody();
                } else {
                    IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), irContainerExpression.getOrigin(), null, false, 64, null);
                    IrExpression irExpressionHandleSavedExpression2 = handleSavedExpression(irBlockBuilder2, (IrExpression) irStatement, function2);
                    if (irExpressionHandleSavedExpression2 == null) {
                        return null;
                    }
                    Iterator it2 = JvmMultiFieldValueClassLoweringKt.subListWithoutLast(irContainerExpression.getStatements(), 1).iterator();
                    while (it2.hasNext()) {
                        irBlockBuilder2.unaryPlus((IrStatement) it2.next());
                    }
                    irBlockBuilder2.unaryPlus(irExpressionHandleSavedExpression2);
                    irBlockBody = irBlockBuilder2.getIrBlockBody();
                }
            }
            return irBlockBody;
        }

        public final IrExpression get(IrExpression irExpression, final IrBuilderWithScope scope, final Name name2) {
            Intrinsics.checkNotNullParameter(irExpression, "<this>");
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(name2, "name");
            final JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = JvmMultiFieldValueClassLowering.this;
            return handleSavedExpression(scope, irExpression, new Function2<IrBuilderWithScope, MfvcNodeInstanceAccessor, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$get$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(IrBuilderWithScope handleSavedExpression, JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor accessor) {
                    IrExpression irExpressionMakeGetterExpression;
                    Intrinsics.checkNotNullParameter(handleSavedExpression, "$this$handleSavedExpression");
                    Intrinsics.checkNotNullParameter(accessor, "accessor");
                    JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor mfvcNodeInstanceAccessor = accessor.get(name2);
                    if (mfvcNodeInstanceAccessor == null) {
                        return null;
                    }
                    if (mfvcNodeInstanceAccessor instanceof JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor.Setter) {
                        irExpressionMakeGetterExpression = MfvcNodeInstanceKt.makeSetterExpressions(mfvcNodeInstanceAccessor.getInstance(), scope, ((JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor.Setter) mfvcNodeInstanceAccessor).getValues());
                    } else if (mfvcNodeInstanceAccessor instanceof JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor.Getter) {
                        irExpressionMakeGetterExpression = mfvcNodeInstanceAccessor.getInstance().makeGetterExpression(scope, jvmMultiFieldValueClassLowering.getIrCurrentClass(), new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$get$1$expression$1(jvmMultiFieldValueClassLowering));
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                    this.expression2MfvcNodeInstanceAccessor.put(irExpressionMakeGetterExpression, mfvcNodeInstanceAccessor);
                    return irExpressionMakeGetterExpression;
                }
            });
        }

        public final IrExpression handleFlattenedGetterExpressions(IrBuilderWithScope scope, IrExpression expression, final Function2<? super IrBlockBuilder, ? super List<? extends IrExpression>, ? extends IrExpression> handler) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(handler, "handler");
            final JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = JvmMultiFieldValueClassLowering.this;
            return handleSavedExpression(scope, expression, new Function2<IrBuilderWithScope, MfvcNodeInstanceAccessor, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$handleFlattenedGetterExpressions$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(IrBuilderWithScope handleSavedExpression, JvmMultiFieldValueClassLowering.MfvcNodeInstanceAccessor it) {
                    Intrinsics.checkNotNullParameter(handleSavedExpression, "$this$handleSavedExpression");
                    Intrinsics.checkNotNullParameter(it, "it");
                    Function2<IrBlockBuilder, List<? extends IrExpression>, IrExpression> function2 = handler;
                    JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering2 = jvmMultiFieldValueClassLowering;
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(handleSavedExpression.getContext(), handleSavedExpression.getScope(), handleSavedExpression.getStartOffset(), handleSavedExpression.getEndOffset(), null, null, false, 64, null);
                    irBlockBuilder.unaryPlus(function2.invoke(irBlockBuilder, it.getInstance().makeFlattenedGetterExpressions(irBlockBuilder, jvmMultiFieldValueClassLowering2.getIrCurrentClass(), new JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$handleFlattenedGetterExpressions$1$1$1(jvmMultiFieldValueClassLowering2))));
                    return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
                }
            });
        }

        public final void registerReplacement(IrExpression expression, MfvcNodeInstance instance) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(instance, "instance");
            this.expression2MfvcNodeInstanceAccessor.put(expression, new MfvcNodeInstanceAccessor.Getter(instance));
        }

        private final Pair<List<IrExpression>, List<IrExpression>> splitExpressions(List<? extends IrExpression> expressions) {
            ArrayList list;
            if (expressions.isEmpty()) {
                list = CollectionsKt.emptyList();
            } else {
                ListIterator<? extends IrExpression> listIterator = expressions.listIterator(expressions.size());
                while (listIterator.hasPrevious()) {
                    if (!MfvcNodeInstanceKt.isRepeatableGetter(listIterator.previous())) {
                        listIterator.next();
                        int size = expressions.size() - listIterator.nextIndex();
                        if (size == 0) {
                            list = CollectionsKt.emptyList();
                        } else {
                            ArrayList arrayList = new ArrayList(size);
                            while (listIterator.hasNext()) {
                                arrayList.add(listIterator.next());
                            }
                            list = arrayList;
                        }
                    }
                }
                list = CollectionsKt.toList(expressions);
            }
            return TuplesKt.to(expressions.subList(0, expressions.size() - list.size()), list);
        }
    }

    private final IrProperty getProperty(IrField irField) {
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null) {
            return correspondingPropertySymbol.getOwner();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrProperty getProperty(IrSimpleFunction irSimpleFunction) {
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null) {
            return correspondingPropertySymbol.getOwner();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public MemoizedMultiFieldValueClassReplacements getReplacements() {
        return getContext().getMultiFieldValueClassReplacements();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public boolean isSpecificLoweringLogicApplicable(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrDeclarationsKt.isMultiFieldValueClass(irClass);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected JvmValueClassAbstractLowering.SpecificMangle getSpecificMangle() {
        return JvmValueClassAbstractLowering.SpecificMangle.MultiField;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void variablesSaver(IrVariable variable) {
        Map<IrDeclarationParent, Set<IrVariable>> map = this.variablesToAdd;
        IrDeclarationParent parent = variable.getParent();
        LinkedHashSet linkedHashSet = map.get(parent);
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet();
            map.put(parent, linkedHashSet);
        }
        linkedHashSet.add(variable);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrClass visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (isSpecificLoweringLogicApplicable(declaration)) {
            handleSpecificNewClass(declaration);
        } else {
            handleNonSpecificNewClass(declaration);
        }
        TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.visitClassNew.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration memberDeclaration) {
                List<IrDeclaration> listTransformFunctionFlat;
                Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
                if (memberDeclaration instanceof IrFunction) {
                    JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = JvmMultiFieldValueClassLowering.this;
                    JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering2 = jvmMultiFieldValueClassLowering;
                    jvmMultiFieldValueClassLowering2.unsafeEnterScope(memberDeclaration);
                    listTransformFunctionFlat = jvmMultiFieldValueClassLowering.transformFunctionFlat((IrFunction) memberDeclaration);
                    jvmMultiFieldValueClassLowering2.unsafeLeaveScope();
                } else {
                    memberDeclaration.accept(JvmMultiFieldValueClassLowering.this, null);
                    listTransformFunctionFlat = null;
                }
                JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering3 = JvmMultiFieldValueClassLowering.this;
                Iterator<IrDeclaration> it = (listTransformFunctionFlat == null ? CollectionsKt.listOf(memberDeclaration) : listTransformFunctionFlat).iterator();
                while (it.hasNext()) {
                    jvmMultiFieldValueClassLowering3.postActionAfterTransformingClassDeclaration(it.next());
                }
                return listTransformFunctionFlat;
            }
        });
        return declaration;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public void visitClassNewDeclarationsWhenParallel$backend_jvm_lower(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        postActionAfterTransformingClassDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected void postActionAfterTransformingClassDeclaration(IrDeclaration replacingDeclaration) {
        Intrinsics.checkNotNullParameter(replacingDeclaration, "replacingDeclaration");
        IrBody irBody = null;
        if (replacingDeclaration instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) replacingDeclaration;
            IrBody body = irFunction.getBody();
            if (body != null) {
                JvmBackendContext context = getContext();
                Set<IrVariable> setEmptySet = this.variablesToAdd.get(replacingDeclaration);
                if (setEmptySet == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                IrBody irBodyMakeBodyWithAddedVariables = JvmMultiFieldValueClassLoweringKt.makeBodyWithAddedVariables(body, context, setEmptySet, irFunction.getSymbol());
                if (irBodyMakeBodyWithAddedVariables != null) {
                    if (this.possibleExtraBoxUsageGenerated.contains(replacingDeclaration)) {
                        removeAllExtraBoxes(irBodyMakeBodyWithAddedVariables);
                    }
                    irBody = irBodyMakeBodyWithAddedVariables;
                }
            }
            irFunction.setBody(irBody);
            return;
        }
        if (replacingDeclaration instanceof IrAnonymousInitializer) {
            IrAnonymousInitializer irAnonymousInitializer = (IrAnonymousInitializer) replacingDeclaration;
            IrBlockBody body2 = irAnonymousInitializer.getBody();
            JvmBackendContext context2 = getContext();
            Set<IrVariable> setEmptySet2 = this.variablesToAdd.get(replacingDeclaration.getParent());
            if (setEmptySet2 == null) {
                setEmptySet2 = SetsKt.emptySet();
            }
            IrBody irBodyMakeBodyWithAddedVariables2 = JvmMultiFieldValueClassLoweringKt.makeBodyWithAddedVariables(body2, context2, setEmptySet2, irAnonymousInitializer.getSymbol());
            if (this.possibleExtraBoxUsageGenerated.contains(replacingDeclaration)) {
                removeAllExtraBoxes(irBodyMakeBodyWithAddedVariables2);
            }
            Intrinsics.checkNotNull(irBodyMakeBodyWithAddedVariables2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
            irAnonymousInitializer.setBody((IrBlockBody) irBodyMakeBodyWithAddedVariables2);
            return;
        }
        if (replacingDeclaration instanceof IrField) {
            IrField irField = (IrField) replacingDeclaration;
            IrExpressionBody initializer = irField.getInitializer();
            if (initializer != null) {
                IrExpressionBody irExpressionBody = initializer;
                JvmBackendContext context3 = getContext();
                Set<IrVariable> setEmptySet3 = this.variablesToAdd.get(replacingDeclaration);
                if (setEmptySet3 == null) {
                    setEmptySet3 = SetsKt.emptySet();
                }
                IrBody irBodyMakeBodyWithAddedVariables3 = JvmMultiFieldValueClassLoweringKt.makeBodyWithAddedVariables(irExpressionBody, context3, setEmptySet3, irField.getSymbol());
                if (irBodyMakeBodyWithAddedVariables3 != null) {
                    if (this.possibleExtraBoxUsageGenerated.contains(replacingDeclaration)) {
                        removeAllExtraBoxes(irBodyMakeBodyWithAddedVariables3);
                    }
                    irBody = irBodyMakeBodyWithAddedVariables3;
                }
            }
            irField.setInitializer((IrExpressionBody) irBody);
        }
    }

    private final void handleNonSpecificNewClass(IrClass irClass) {
        IrField field;
        IrProperty property;
        IrField backingField;
        IrConstructor replacementForRegularClassConstructor;
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
        if (primaryConstructor != null && (replacementForRegularClassConstructor = getReplacements().getReplacementForRegularClassConstructor(primaryConstructor)) != null) {
            addBindingsFor(primaryConstructor, replacementForRegularClassConstructor);
        }
        LinkedHashSet<IrPropertyOrIrField> linkedHashSetCollectPropertiesOrFieldsAfterLowering = MfvcNodeFactoryKt.collectPropertiesOrFieldsAfterLowering(irClass, getContext());
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        Iterator<IrPropertyOrIrField> it = linkedHashSetCollectPropertiesOrFieldsAfterLowering.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            IrPropertyOrIrField next = it.next();
            IrPropertyOrIrField.Property property2 = next instanceof IrPropertyOrIrField.Property ? (IrPropertyOrIrField.Property) next : null;
            if (property2 != null && (property = property2.getProperty()) != null && (backingField = property.getBackingField()) != null) {
                mapCreateMapBuilder.put(property, backingField);
            }
        }
        Map mapBuild = MapsKt.build(mapCreateMapBuilder);
        LinkedHashMap<IrPropertyOrIrField, IntermediateMfvcNode> linkedHashMapCollectRegularClassMfvcPropertiesOrFieldsReplacement = collectRegularClassMfvcPropertiesOrFieldsReplacement(linkedHashSetCollectPropertiesOrFieldsAfterLowering);
        Set<IrPropertyOrIrField> setKeySet = linkedHashMapCollectRegularClassMfvcPropertiesOrFieldsReplacement.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        ArrayList arrayList = new ArrayList();
        for (IrPropertyOrIrField irPropertyOrIrField : setKeySet) {
            if (irPropertyOrIrField instanceof IrPropertyOrIrField.Field) {
                field = ((IrPropertyOrIrField.Field) irPropertyOrIrField).getField();
            } else {
                if (!(irPropertyOrIrField instanceof IrPropertyOrIrField.Property)) {
                    throw new NoWhenBranchMatchedException();
                }
                field = (IrField) mapBuild.get(((IrPropertyOrIrField.Property) irPropertyOrIrField).getProperty());
            }
            if (field != null) {
                arrayList.add(field);
            }
        }
        Set<? extends IrField> set = CollectionsKt.toSet(arrayList);
        if (!set.isEmpty() || !linkedHashMapCollectRegularClassMfvcPropertiesOrFieldsReplacement.isEmpty()) {
            JvmMultiFieldValueClassLoweringKt.replaceAll(irClass.getDeclarations(), makeNewDeclarationsForRegularClass(set, linkedHashMapCollectRegularClassMfvcPropertiesOrFieldsReplacement, irClass));
        }
        Iterator<? extends IrField> it2 = set.iterator();
        while (it2.hasNext()) {
            IrPropertySymbol correspondingPropertySymbol = it2.next().getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null) {
                owner.setBackingField(null);
            }
        }
    }

    private final LinkedHashMap<IrPropertyOrIrField, IntermediateMfvcNode> collectRegularClassMfvcPropertiesOrFieldsReplacement(LinkedHashSet<IrPropertyOrIrField> propertiesOrFields) {
        NameableMfvcNode mfvcPropertyNode;
        LinkedHashMap<IrPropertyOrIrField, IntermediateMfvcNode> linkedHashMap = new LinkedHashMap<>();
        for (IrPropertyOrIrField irPropertyOrIrField : propertiesOrFields) {
            if (irPropertyOrIrField instanceof IrPropertyOrIrField.Field) {
                mfvcPropertyNode = getReplacements().getMfvcFieldNode(((IrPropertyOrIrField.Field) irPropertyOrIrField).getField());
            } else {
                if (!(irPropertyOrIrField instanceof IrPropertyOrIrField.Property)) {
                    throw new NoWhenBranchMatchedException();
                }
                mfvcPropertyNode = getReplacements().getMfvcPropertyNode(((IrPropertyOrIrField.Property) irPropertyOrIrField).getProperty());
            }
            if (mfvcPropertyNode != null) {
                Intrinsics.checkNotNull(mfvcPropertyNode, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.IntermediateMfvcNode");
                linkedHashMap.put(irPropertyOrIrField, (IntermediateMfvcNode) mfvcPropertyNode);
            }
        }
        return linkedHashMap;
    }

    private final List<IrDeclaration> makeNewDeclarationsForRegularClass(Set<? extends IrField> fieldsToRemove, Map<IrPropertyOrIrField, IntermediateMfvcNode> propertiesOrFieldsReplacement, IrClass irClass) {
        List<IrField> fields;
        IntermediateMfvcNode intermediateMfvcNode;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if ((irDeclaration instanceof IrField) && fieldsToRemove.contains(irDeclaration)) {
                IrField irField = (IrField) irDeclaration;
                IrProperty property = getProperty(irField);
                if (property == null || (intermediateMfvcNode = propertiesOrFieldsReplacement.get(new IrPropertyOrIrField.Property(property))) == null || (fields = intermediateMfvcNode.getFields()) == null) {
                    IntermediateMfvcNode intermediateMfvcNode2 = propertiesOrFieldsReplacement.get(new IrPropertyOrIrField.Field(irField));
                    fields = intermediateMfvcNode2 != null ? intermediateMfvcNode2.getFields() : null;
                }
                if (fields != null) {
                    listCreateListBuilder.addAll(fields);
                    IrExpressionBody initializer = irField.getInitializer();
                    if (initializer != null) {
                        listCreateListBuilder.add(makeInitializerReplacement(irClass, irField, initializer));
                    }
                } else {
                    listCreateListBuilder.add(irDeclaration);
                }
            } else {
                listCreateListBuilder.add(irDeclaration);
            }
        }
        for (Map.Entry<IrPropertyOrIrField, IntermediateMfvcNode> entry : propertiesOrFieldsReplacement.entrySet()) {
            IrPropertyOrIrField key = entry.getKey();
            IntermediateMfvcNode value = entry.getValue();
            if (key instanceof IrPropertyOrIrField.Property) {
                List<IrSimpleFunction> allInnerUnboxMethods = value.getAllInnerUnboxMethods();
                ArrayList arrayList = new ArrayList();
                for (Object obj : allInnerUnboxMethods) {
                    if (Intrinsics.areEqual(((IrSimpleFunction) obj).getParent(), irClass)) {
                        arrayList.add(obj);
                    }
                }
                listCreateListBuilder.addAll(arrayList);
            }
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final IrAnonymousInitializer makeInitializerReplacement(IrClass irClass, IrField element, IrExpressionBody initializer) {
        IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer = getContext().getIrFactory().createAnonymousInitializer(-1, -1, IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE, new IrAnonymousInitializerSymbolImpl(null, 1, null), element.getIsStatic());
        IrClass irClass2 = irClass;
        irAnonymousInitializerCreateAnonymousInitializer.setParent(irClass2);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), irAnonymousInitializerCreateAnonymousInitializer.getSymbol(), 0, 0, 6, null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        if (element.getIsStatic()) {
            thisReceiver = null;
        }
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField(irBlockBodyBuilder2, thisReceiver != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver) : null, element, (IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(initializer.getExpression(), irClass2), UNSAFE_MFVC_SET_ORIGIN.INSTANCE));
        irAnonymousInitializerCreateAnonymousInitializer.setBody(irBlockBodyBuilder.getIrBlockBody());
        element.setInitializer(null);
        return irAnonymousInitializerCreateAnonymousInitializer;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public void handleSpecificNewClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        RootMfvcNode rootMfvcNodeInvoke = getReplacements().getGetRootMfvcNode().invoke(declaration);
        replaceMfvcNotStaticFields(rootMfvcNodeInvoke);
        List<IrDeclaration> declarations = declaration.getDeclarations();
        List<IrSimpleFunction> allUnboxMethods = rootMfvcNodeInvoke.getAllUnboxMethods();
        IrSimpleFunction[] irSimpleFunctionArr = new IrSimpleFunction[2];
        irSimpleFunctionArr[0] = rootMfvcNodeInvoke.getBoxMethod();
        IrSimpleFunction specializedEqualsMethod = rootMfvcNodeInvoke.getSpecializedEqualsMethod();
        if (!rootMfvcNodeInvoke.getCreatedNewSpecializedEqualsMethod()) {
            specializedEqualsMethod = null;
        }
        irSimpleFunctionArr[1] = specializedEqualsMethod;
        CollectionsKt.addAll(declarations, CollectionsKt.plus((Collection) allUnboxMethods, (Iterable) CollectionsKt.listOfNotNull((Object[]) irSimpleFunctionArr)));
        replacePrimaryMultiFieldValueClassConstructor(rootMfvcNodeInvoke);
        replaceMfvcStaticFields(declaration);
    }

    private final void replaceMfvcStaticFields(IrClass declaration) {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (IrField irField : SequencesKt.filter(IrUtilsKt.getFields(declaration), new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$replaceMfvcStaticFields$staticFieldMapping$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getIsStatic());
            }
        })) {
            NameableMfvcNode mfvcFieldNode = getReplacements().getMfvcFieldNode(irField);
            if (mfvcFieldNode != null) {
                List<IrField> fields = MfvcNodeKt.getFields(mfvcFieldNode);
                if (fields == null) {
                    fields = CollectionsKt.emptyList();
                }
                IrExpressionBody initializer = irField.getInitializer();
                IrAnonymousInitializer irAnonymousInitializerMakeInitializerReplacement = initializer != null ? makeInitializerReplacement(declaration, irField, initializer) : null;
                IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
                IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                if (owner != null) {
                    owner.setBackingField(null);
                }
                mapCreateMapBuilder.put(irField, CollectionsKt.plus((Collection) fields, (Iterable) CollectionsKt.listOfNotNull(irAnonymousInitializerMakeInitializerReplacement)));
            }
        }
        Map mapBuild = MapsKt.build(mapCreateMapBuilder);
        if (mapBuild.isEmpty()) {
            return;
        }
        List<IrDeclaration> declarations = declaration.getDeclarations();
        List<IrDeclaration> declarations2 = declaration.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (IrDeclaration irDeclaration : declarations2) {
            List listListOf = (List) mapBuild.get(irDeclaration);
            if (listListOf == null) {
                listListOf = CollectionsKt.listOf(irDeclaration);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        JvmMultiFieldValueClassLoweringKt.replaceAll(declarations, arrayList);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected List<IrDeclaration> transformSecondaryConstructorFlat(final IrConstructor constructor, IrSimpleFunction replacement) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        for (IrValueParameter irValueParameter : replacement.getValueParameters()) {
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue != null) {
            }
            visitParameter(irValueParameter);
        }
        UtilsKt.push(getAllScopes(), createScope(replacement));
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), replacement.getSymbol(), 0, 0, 6, null);
        final IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset());
        final IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder, null, "$this", replacement.getReturnType(), false, null, 25, null);
        IrBody body = constructor.getBody();
        if (body != null && (statements = IrUtilsKt.getStatements(body)) != null) {
            Iterator<T> it = statements.iterator();
            while (it.hasNext()) {
                irBlockBodyBuilder.unaryPlus(IrElementsKt.transformStatement((IrStatement) it.next(), new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$transformSecondaryConstructorFlat$1$1$1
                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrStatement visitClass(IrClass declaration) {
                        Intrinsics.checkNotNullParameter(declaration, "declaration");
                        return declaration;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrConstructor owner = expression.getSymbol().getOwner();
                        if (!Intrinsics.areEqual(AdditionalIrUtilsKt.getConstructedClass(owner), AdditionalIrUtilsKt.getConstructedClass(constructor))) {
                            if (!Intrinsics.areEqual(AdditionalIrUtilsKt.getConstructedClass(owner), irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyClass().getOwner())) {
                                throw new IllegalArgumentException(("Expected delegating constructor to the MFVC primary constructor or Any constructor but got: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                            }
                            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                            return new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irBlockBodyBuilder2.getStartOffset(), irBlockBodyBuilder2.getEndOffset(), null, null, false, 64, null).getIrBlockBody();
                        }
                        IrVariable irVariable = irVariableIrTemporary$default;
                        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder, owner);
                        IrUtilsKt.copyTypeAndValueArgumentsFrom$default(irFunctionAccessExpressionIrCall, expression, false, false, 6, null);
                        irVariable.setInitializer(irFunctionAccessExpressionIrCall);
                        IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
                        return new IrBlockBuilder(irBlockBodyBuilder3.getContext(), irBlockBodyBuilder3.getScope(), irBlockBodyBuilder3.getStartOffset(), irBlockBodyBuilder3.getEndOffset(), null, null, false, 64, null).getIrBlockBody();
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitGetValue(IrGetValue expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrValueDeclaration owner = expression.getSymbol().getOwner();
                        IrValueParameter thisReceiver = AdditionalIrUtilsKt.getConstructedClass(constructor).getThisReceiver();
                        Intrinsics.checkNotNull(thisReceiver);
                        return Intrinsics.areEqual(owner, thisReceiver) ? ExpressionHelpersKt.irGet(irBlockBodyBuilder, irVariableIrTemporary$default) : super.visitGetValue(expression);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitReturn(IrReturn expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        transformChildrenVoid(expression);
                        if (!Intrinsics.areEqual(expression.getReturnTargetSymbol(), constructor.getSymbol())) {
                            return expression;
                        }
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder2;
                        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder2;
                        int startOffset = expression.getStartOffset();
                        int endOffset = expression.getEndOffset();
                        IrVariable irVariable = irVariableIrTemporary$default;
                        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder4.getContext(), irBlockBodyBuilder4.getScope(), startOffset, endOffset, null, null, false, 64, null);
                        irBlockBuilder.unaryPlus(expression.getValue());
                        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariable));
                        return ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irBlockBuilder.getIrBlockBody());
                    }
                }));
            }
        }
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariableIrTemporary$default)));
        IrBlockBody irBlockBody = irBlockBodyBuilder.getIrBlockBody();
        addBindingsFor(constructor, replacement);
        replacement.setBody((IrBody) PatchDeclarationParentsKt.patchDeclarationParents(irBlockBody.transform((IrElementTransformer) this, (Object) null), replacement));
        UtilsKt.pop(getAllScopes());
        return CollectionsKt.listOf(replacement);
    }

    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$UNSAFE_MFVC_SET_ORIGIN;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class UNSAFE_MFVC_SET_ORIGIN implements IrStatementOrigin {
        public static final UNSAFE_MFVC_SET_ORIGIN INSTANCE = new UNSAFE_MFVC_SET_ORIGIN();

        private UNSAFE_MFVC_SET_ORIGIN() {
        }
    }

    private final void replaceMfvcNotStaticFields(RootMfvcNode rootMfvcNode) {
        List list = SequencesKt.toList(SequencesKt.filter(IrUtilsKt.getFields(rootMfvcNode.getMfvc()), new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$replaceMfvcNotStaticFields$fieldsToRemove$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!it.getIsStatic());
            }
        }));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            IrPropertySymbol correspondingPropertySymbol = ((IrField) it.next()).getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null) {
                owner.setBackingField(null);
            }
        }
        rootMfvcNode.getMfvc().getDeclarations().removeAll(list);
        List<IrDeclaration> declarations = rootMfvcNode.getMfvc().getDeclarations();
        List<IrField> fields = rootMfvcNode.getFields();
        if (fields == null) {
            fields = CollectionsKt.emptyList();
        }
        CollectionsKt.addAll(declarations, fields);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public IrSimpleFunction createBridgeDeclaration(IrSimpleFunction source, IrSimpleFunction replacement, Name mangledName) {
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list;
        MemoizedMultiFieldValueClassReplacements.RemappedParameter multiFieldValueClassMapping;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(mangledName, "mangledName");
        IrFactory irFactory = getContext().getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) source);
        irFunctionBuilder.setName(mangledName);
        irFunctionBuilder.setReturnType(source.getReturnType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        if (!irSimpleFunctionBuildFunction.getIsFakeOverride()) {
            list = getReplacements().getBindingNewFunctionToParameterTemplateStructure().get(replacement);
            if (list == null) {
                throw new IllegalStateException((RenderIrElementKt.render$default(replacement, (DumpIrTreeOptions) null, 1, (Object) null) + " must have MFVC structure").toString());
            }
        } else {
            boolean z = false;
            Iterator it = IrUtilsKt.allOverridden$default(replacement, false, 1, null).iterator();
            Object obj = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Object next = it.next();
                if (((IrSimpleFunction) next).getBody() != null) {
                    if (!z) {
                        z = true;
                        obj = next;
                    }
                }
                obj = null;
                break;
            }
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj;
            if (irSimpleFunction == null) {
                throw new IllegalStateException((RenderIrElementKt.render$default(irSimpleFunctionBuildFunction, (DumpIrTreeOptions) null, 1, (Object) null) + " is fake override and has no implementation").toString());
            }
            list = getReplacements().getBindingNewFunctionToParameterTemplateStructure().get(irSimpleFunction);
            if (list == null) {
                List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(irSimpleFunction);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10));
                Iterator<T> it2 = explicitParameters.iterator();
                while (it2.hasNext()) {
                    arrayList.add(new MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping((IrValueParameter) it2.next()));
                }
                list = arrayList;
            }
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        IrSimpleFunction irSimpleFunction3 = source;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction2, irSimpleFunction3, null, null, 6, null);
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(irSimpleFunction3, irSimpleFunction2);
        IrValueParameter dispatchReceiverParameter = source.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(dispatchReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, IrTypeUtilsKt.substitute(dispatchReceiverParameter.getType(), mapMakeTypeParameterSubstitutionMap), null, null, false, false, false, 8062, null));
        IrDeclarationParent parent = source.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && IrDeclarationsKt.isMultiFieldValueClass(irClass) && replacement.getDispatchReceiverParameter() != null) {
            StringBuilder sb = new StringBuilder("\n                        Ambiguous receivers:\n                        ");
            IrValueParameter dispatchReceiverParameter2 = source.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter2);
            StringBuilder sbAppend = sb.append(RenderIrElementKt.render$default(dispatchReceiverParameter2, (DumpIrTreeOptions) null, 1, (Object) null)).append("\n                        ");
            IrValueParameter dispatchReceiverParameter3 = replacement.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter3);
            throw new IllegalArgumentException(StringsKt.trimIndent(sbAppend.append(RenderIrElementKt.render$default(dispatchReceiverParameter3, (DumpIrTreeOptions) null, 1, (Object) null)).append("\n                        ").toString()).toString());
        }
        if (replacement.getExtensionReceiverParameter() != null) {
            StringBuilder sb2 = new StringBuilder("Static replacement must have no extension receiver but ");
            IrValueParameter extensionReceiverParameter = replacement.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            throw new IllegalArgumentException(sb2.append(RenderIrElementKt.render$default(extensionReceiverParameter, (DumpIrTreeOptions) null, 1, (Object) null)).append(" found").toString().toString());
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder(list.size());
        Ref.IntRef intRef = new Ref.IntRef();
        IrValueParameter dispatchReceiverParameter4 = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter4);
        listCreateListBuilder.add(new MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping(dispatchReceiverParameter4));
        ArrayList arrayList2 = new ArrayList();
        for (MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter : SequencesKt.drop(CollectionsKt.asSequence(list), listCreateListBuilder.size())) {
            if (remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping) {
                multiFieldValueClassMapping = new MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping(createBridgeDeclaration$lambda$32$lambda$31$copy(((MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping) remappedParameter).getValueParameter(), mapMakeTypeParameterSubstitutionMap, intRef, irSimpleFunctionBuildFunction));
            } else {
                if (!(remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping)) {
                    throw new NoWhenBranchMatchedException();
                }
                RootMfvcNode rootMfvcNode = ((MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) remappedParameter).getRootMfvcNode();
                List<IrValueParameter> valueParameters = remappedParameter.getValueParameters();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                Iterator<T> it3 = valueParameters.iterator();
                while (it3.hasNext()) {
                    arrayList3.add(createBridgeDeclaration$lambda$32$lambda$31$copy((IrValueParameter) it3.next(), mapMakeTypeParameterSubstitutionMap, intRef, irSimpleFunctionBuildFunction));
                }
                multiFieldValueClassMapping = new MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping(rootMfvcNode, mapMakeTypeParameterSubstitutionMap, arrayList3);
            }
            MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter2 = multiFieldValueClassMapping;
            listCreateListBuilder.add(remappedParameter2);
            arrayList2.addAll(remappedParameter2.getValueParameters());
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList2);
        getReplacements().getBindingNewFunctionToParameterTemplateStructure().put(irSimpleFunctionBuildFunction, CollectionsKt.build(listCreateListBuilder));
        irSimpleFunctionBuildFunction.setAnnotations(source.getAnnotations());
        irSimpleFunctionBuildFunction.setParent(source.getParent());
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, source);
        return irSimpleFunctionBuildFunction;
    }

    private static final IrValueParameter createBridgeDeclaration$lambda$32$lambda$31$copy(IrValueParameter irValueParameter, Map<IrTypeParameterSymbol, ? extends IrType> map, Ref.IntRef intRef, IrSimpleFunction irSimpleFunction) {
        IrType irTypeSubstitute = IrTypeUtilsKt.substitute(irValueParameter.getType(), map);
        int i = intRef.element;
        intRef.element = i + 1;
        return IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction, null, i, 0, 0, null, null, irTypeSubstitute, null, null, false, false, false, 8058, null);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected void createBridgeBody(final IrSimpleFunction source, IrSimpleFunction target, IrFunction original, boolean inverted) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(original, "original");
        UtilsKt.push(getAllScopes(), createScope(source));
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), source.getSymbol(), 0, 0, 6, null);
        final IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, null, false, 64, null);
        IrSimpleFunction irSimpleFunction = target;
        IrSimpleFunction irSimpleFunction2 = source;
        Map<IrValueParameter, IrExpression> mapMapFunctionMfvcStructures = getReplacements().mapFunctionMfvcStructures(irBlockBuilder, irSimpleFunction, irSimpleFunction2, new Function2<IrValueParameter, IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$createBridgeBody$1$1$parameters2arguments$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrValueParameter sourceParameter, IrType irType) {
                Intrinsics.checkNotNullParameter(sourceParameter, "sourceParameter");
                Intrinsics.checkNotNullParameter(irType, "<anonymous parameter 1>");
                return ExpressionHelpersKt.irGet(irBlockBuilder, sourceParameter);
            }
        });
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, irSimpleFunction);
        passTypeArgumentsWithOffsets(irFunctionAccessExpressionIrCall, irSimpleFunction, irSimpleFunction2, new Function1<Integer, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$createBridgeBody$1$1$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ IrType invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final IrType invoke(int i) {
                return IrTypesKt.getDefaultType(source.getTypeParameters().get(i));
            }
        });
        for (Map.Entry<IrValueParameter, IrExpression> entry : mapMapFunctionMfvcStructures.entrySet()) {
            IrValueParameter key = entry.getKey();
            IrExpression value = entry.getValue();
            if (value != null) {
                IrExpressionsKt.putArgument(irFunctionAccessExpressionIrCall, key, value);
            }
        }
        Unit unit = Unit.INSTANCE;
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBuilder2, irFunctionAccessExpressionIrCall));
        source.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, irBlockBuilder.getIrBlockBody()));
        UtilsKt.pop(getAllScopes());
    }

    private final void passTypeArgumentsWithOffsets(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction, IrFunction irFunction2, Function1<? super Integer, ? extends IrType> function1) {
        int iMin = Math.min(irFunction.getTypeParameters().size(), irFunction2.getTypeParameters().size());
        int size = irFunction.getTypeParameters().size() - iMin;
        int size2 = irFunction2.getTypeParameters().size() - iMin;
        if (size2 > 0) {
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irFunction2));
            IrSimpleType irSimpleType = defaultType;
            if (!MfvcNodeKt.needsMfvcFlattening(irSimpleType) || JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType).getTypeParameters().size() != size2) {
                throw new IllegalArgumentException(("Unexpected dispatcher receiver type: " + RenderIrElementKt.render$default(defaultType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        if (size > 0) {
            IrSimpleType defaultType2 = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irFunction2));
            IrSimpleType irSimpleType2 = defaultType2;
            if (!MfvcNodeKt.needsMfvcFlattening(irSimpleType2) || JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType2).getTypeParameters().size() != size) {
                throw new IllegalArgumentException(("Unexpected dispatcher receiver type: " + RenderIrElementKt.render$default(defaultType2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            int i = 0;
            for (Object obj : JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType2).getTypeParameters()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                irMemberAccessExpression.putTypeArgument(i, IrTypesKt.getDefaultType((IrTypeParameter) obj));
                i = i2;
            }
        }
        for (int i3 = 0; i3 < iMin; i3++) {
            irMemberAccessExpression.putTypeArgument(i3 + size, function1.invoke(Integer.valueOf(i3 + size2)));
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    protected void addBindingsFor(IrFunction original, IrFunction replacement) {
        ArrayList arrayListListOf;
        List listEmptyList;
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list = getReplacements().getBindingOldFunctionToParameterTemplateStructure().get(original);
        Intrinsics.checkNotNull(list);
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list2 = list;
        if (list2.size() != IrUtilsKt.getExplicitParameters(original).size()) {
            throw new IllegalArgumentException(("Wrong value parameters structure: " + list2).toString());
        }
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list3 = list2;
        Iterator<T> it = list3.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()).getValueParameters().size();
        }
        if (size != IrUtilsKt.getExplicitParameters(replacement).size()) {
            throw new IllegalArgumentException(("Wrong value parameters structure: " + list2).toString());
        }
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(original);
        int iValueOf = 0;
        int iCollectionSizeOrDefault = CollectionsKt.collectionSizeOrDefault(list3, 9);
        if (iCollectionSizeOrDefault == 0) {
            arrayListListOf = CollectionsKt.listOf(0);
        } else {
            ArrayList arrayList = new ArrayList(iCollectionSizeOrDefault + 1);
            arrayList.add(0);
            Iterator<T> it2 = list3.iterator();
            while (it2.hasNext()) {
                iValueOf = Integer.valueOf(iValueOf.intValue() + ((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it2.next()).getValueParameters().size());
                arrayList.add(iValueOf);
            }
            arrayListListOf = arrayList;
        }
        Iterator it3 = arrayListListOf.iterator();
        if (it3.hasNext()) {
            ArrayList arrayList2 = new ArrayList();
            Object next = it3.next();
            while (it3.hasNext()) {
                Object next2 = it3.next();
                arrayList2.add(CollectionsKt.slice((List) IrUtilsKt.getExplicitParameters(replacement), RangesKt.until(((Number) next).intValue(), ((Number) next2).intValue())));
                next = next2;
            }
            listEmptyList = arrayList2;
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<? extends Pair<? extends IrValueParameter, ? extends List<? extends IrValueParameter>>> listZip = CollectionsKt.zip(explicitParameters, listEmptyList);
        int size2 = listZip.size();
        for (int i = 0; i < size2; i++) {
            Pair<? extends IrValueParameter, ? extends List<? extends IrValueParameter>> pair = listZip.get(i);
            IrValueParameter irValueParameterComponent1 = pair.component1();
            List<? extends IrValueParameter> listComponent2 = pair.component2();
            MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter = list2.get(i);
            if (remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping) {
                this.valueDeclarationsRemapper.registerReplacement(irValueParameterComponent1, (IrValueDeclaration) CollectionsKt.single((List) listComponent2));
            } else if (remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) {
                MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping multiFieldValueClassMapping = (MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) remappedParameter;
                this.valueDeclarationsRemapper.registerReplacement(irValueParameterComponent1, MfvcNodeKt.createInstanceFromValueDeclarationsAndBoxType(multiFieldValueClassMapping.getRootMfvcNode(), multiFieldValueClassMapping.getBoxedType(), listComponent2));
            }
        }
        JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = this;
        jvmMultiFieldValueClassLowering.unsafeEnterScope(replacement);
        addDefaultArgumentsIfNeeded(replacement, listZip, list2);
        Unit unit = Unit.INSTANCE;
        jvmMultiFieldValueClassLowering.unsafeLeaveScope();
    }

    private final void addDefaultArgumentsIfNeeded(IrFunction replacement, List<? extends Pair<? extends IrValueParameter, ? extends List<? extends IrValueParameter>>> old2newList, List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> parametersStructure) {
        int size = old2newList.size();
        for (int i = 0; i < size; i++) {
            Pair<? extends IrValueParameter, ? extends List<? extends IrValueParameter>> pair = old2newList.get(i);
            IrValueParameter irValueParameterComponent1 = pair.component1();
            List<? extends IrValueParameter> listComponent2 = pair.component2();
            IrExpression irExpression = getReplacements().getOldMfvcDefaultArguments().get(irValueParameterComponent1);
            if (irExpression != null) {
                MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter = parametersStructure.get(i);
                if (remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) {
                    IrFactory irFactory = getContext().getIrFactory();
                    IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                    irFunctionBuilder.setReturnType(getContext().getIrBuiltIns().getUnitType());
                    Name nameIdentifier = Name.identifier("fake");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irFunctionBuilder.setName(nameIdentifier);
                    DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                    Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                    irFunctionBuilder.setVisibility(LOCAL);
                    IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                    IrFunction irFunction = replacement;
                    irSimpleFunctionBuildFunction.setParent(irFunction);
                    IrValueParameter irValueParameter = listComponent2.get(0);
                    JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
                    JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = this;
                    jvmMultiFieldValueClassLowering.unsafeEnterScope(irSimpleFunctionBuildFunction);
                    JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), null, null, false, 64, null);
                    RootMfvcNode rootMfvcNode = ((MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) remappedParameter).getRootMfvcNode();
                    IrType type = irValueParameterComponent1.getType();
                    Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    flattenExpressionTo(irBlockBuilder, irExpression, MfvcNodeKt.createInstanceFromValueDeclarationsAndBoxType(rootMfvcNode, (IrSimpleType) type, listComponent2));
                    irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, listComponent2.get(0)));
                    Unit unit = Unit.INSTANCE;
                    irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, irBlockBuilder.getIrBlockBody()));
                    postActionAfterTransformingClassDeclaration(irSimpleFunctionBuildFunction);
                    IrBody body = irSimpleFunctionBuildFunction.getBody();
                    IrBody irBody = body != null ? (IrBody) PatchDeclarationParentsKt.patchDeclarationParents(body, irFunction) : null;
                    Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
                    jvmMultiFieldValueClassLowering.unsafeLeaveScope();
                    irValueParameter.setDefaultValue((IrExpressionBody) irBody);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering
    public void visitParameter(IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        if (Intrinsics.areEqual(parameter.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER.INSTANCE) || parameter.getDefaultValue() == null) {
            return;
        }
        IrFactory irFactory = getContext().getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setReturnType(getContext().getIrBuiltIns().getUnitType());
        Name nameIdentifier = Name.identifier("fake");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parameter.getParent());
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
        JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = this;
        jvmMultiFieldValueClassLowering.unsafeEnterScope(irSimpleFunctionBuildFunction);
        IrExpressionBody defaultValue = parameter.getDefaultValue();
        Intrinsics.checkNotNull(defaultValue);
        irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, defaultValue.getExpression()).transform((IrElementTransformer<? super Object>) this, (Object) null));
        postActionAfterTransformingClassDeclaration(irSimpleFunctionBuildFunction);
        IrBody body = irSimpleFunctionBuildFunction.getBody();
        IrBody irBody = body != null ? (IrBody) PatchDeclarationParentsKt.patchDeclarationParents(body, parameter.getParent()) : null;
        Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
        jvmMultiFieldValueClassLowering.unsafeLeaveScope();
        parameter.setDefaultValue((IrExpressionBody) irBody);
    }

    private final void replacePrimaryMultiFieldValueClassConstructor(RootMfvcNode rootMfvcNode) {
        List<IrDeclaration> declarations = rootMfvcNode.getMfvc().getDeclarations();
        final C43081 c43081 = new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.replacePrimaryMultiFieldValueClassConstructor.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof IrConstructor) && ((IrConstructor) it).getIsPrimary());
            }
        };
        declarations.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return JvmMultiFieldValueClassLowering.replacePrimaryMultiFieldValueClassConstructor$lambda$56(c43081, obj);
            }
        });
        IrConstructor newPrimaryConstructor = rootMfvcNode.getNewPrimaryConstructor();
        MfvcNodeFactoryKt.throwWhenNotExternalIsNull(rootMfvcNode, newPrimaryConstructor);
        IrSimpleFunction primaryConstructorImpl = rootMfvcNode.getPrimaryConstructorImpl();
        MfvcNodeFactoryKt.throwWhenNotExternalIsNull(rootMfvcNode, primaryConstructorImpl);
        CollectionsKt.addAll(rootMfvcNode.getMfvc().getDeclarations(), CollectionsKt.listOf((Object[]) new IrFunction[]{newPrimaryConstructor, primaryConstructorImpl}));
        List<IrDeclaration> declarations2 = rootMfvcNode.getMfvc().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations2) {
            if (obj instanceof IrAnonymousInitializer) {
                arrayList.add(obj);
            }
        }
        ArrayList<IrAnonymousInitializer> arrayList2 = arrayList;
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(rootMfvcNode.getMfvc(), primaryConstructorImpl);
        if (!MfvcNodeFactoryKt.isKotlinExternalStub(rootMfvcNode.getMfvc())) {
            IrConstructor oldPrimaryConstructor = rootMfvcNode.getOldPrimaryConstructor();
            MfvcNodeFactoryKt.throwWhenNotExternalIsNull(rootMfvcNode, oldPrimaryConstructor);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(getContext(), primaryConstructorImpl.getSymbol(), 0, 0, 6, null);
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset());
            ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = new ValueDeclarationMfvcNodeInstance(rootMfvcNode, mapMakeTypeParameterSubstitutionMap, primaryConstructorImpl.getValueParameters());
            ValueDeclarationRemapper valueDeclarationRemapper = this.valueDeclarationsRemapper;
            IrValueParameter thisReceiver = AdditionalIrUtilsKt.getConstructedClass(oldPrimaryConstructor).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            valueDeclarationRemapper.registerReplacement(thisReceiver, valueDeclarationMfvcNodeInstance);
            for (IrAnonymousInitializer irAnonymousInitializer : arrayList2) {
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irBlockBodyBuilder2.getStartOffset(), irBlockBodyBuilder2.getEndOffset(), null, null, false, 64, null);
                Iterator<IrStatement> it = irAnonymousInitializer.getBody().getStatements().iterator();
                while (it.hasNext()) {
                    irBlockBuilder.unaryPlus((IrStatement) PatchDeclarationParentsKt.patchDeclarationParents(it.next(), rootMfvcNode.getPrimaryConstructorImpl()));
                }
                irBlockBodyBuilder.unaryPlus(irBlockBuilder.getIrBlockBody());
            }
            primaryConstructorImpl.setBody(irBlockBodyBuilder.getIrBlockBody());
        }
        List<IrDeclaration> declarations3 = rootMfvcNode.getMfvc().getDeclarations();
        final AnonymousClass3 anonymousClass3 = new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.replacePrimaryMultiFieldValueClassConstructor.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof IrAnonymousInitializer);
            }
        };
        declarations3.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return JvmMultiFieldValueClassLowering.replacePrimaryMultiFieldValueClassConstructor$lambda$59(anonymousClass3, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean replacePrimaryMultiFieldValueClassConstructor$lambda$56(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean replacePrimaryMultiFieldValueClassConstructor$lambda$59(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final boolean hasLambdaLikeOrigin(IrBlock irBlock) {
        return Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.LAMBDA.INSTANCE) || Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.ANONYMOUS_FUNCTION.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.JvmValueClassAbstractLowering, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrBlock) {
            IrBlock irBlock = (IrBlock) expression;
            if (hasLambdaLikeOrigin(irBlock)) {
                List<IrStatement> statements = expression.getStatements();
                if (!(statements instanceof Collection) || !statements.isEmpty()) {
                    Iterator<T> it = statements.iterator();
                    while (it.hasNext()) {
                        if (((IrStatement) it.next()) instanceof IrFunctionReference) {
                            return visitLambda(irBlock);
                        }
                    }
                }
            }
        }
        return super.visitContainerExpression(expression);
    }

    private final IrExpression visitLambda(IrBlock irBlock) {
        ArrayList arrayList = null;
        if (!hasLambdaLikeOrigin(irBlock) || irBlock.getStatements().size() != 2) {
            throw new IllegalArgumentException(("Illegal lambda: " + DumpIrTreeKt.dump$default(irBlock, null, 1, null)).toString());
        }
        List<IrStatement> statements = irBlock.getStatements();
        IrStatement irStatement = statements.get(0);
        IrStatement irStatement2 = statements.get(1);
        if (!(irStatement instanceof IrSimpleFunction) || !(irStatement2 instanceof IrFunctionReference) || !Intrinsics.areEqual(((IrFunctionReference) irStatement2).getSymbol().getOwner(), irStatement)) {
            throw new IllegalArgumentException(("Illegal lambda: " + DumpIrTreeKt.dump$default(irBlock, null, 1, null)).toString());
        }
        if (!Intrinsics.areEqual(irStatement, CollectionsKt.first((List) irBlock.getStatements()))) {
            throw new IllegalArgumentException(("Illegal lambda: " + DumpIrTreeKt.dump$default(irBlock, null, 1, null)).toString());
        }
        IrFunction irFunction = (IrFunction) irStatement;
        final IrFunction replacement = getReplacement(irFunction);
        if (replacement == null) {
            irBlock.getStatements().set(0, visitFunctionNew(irFunction));
            return irBlock;
        }
        List<IrDeclaration> listTransformFunctionFlat = transformFunctionFlat(irFunction);
        if (!Intrinsics.areEqual(listTransformFunctionFlat, CollectionsKt.listOf(replacement))) {
            StringBuilder sbAppend = new StringBuilder("Expected ").append(RenderIrElementKt.render$default(replacement, (DumpIrTreeOptions) null, 1, (Object) null)).append(", got ");
            if (listTransformFunctionFlat != null) {
                List<IrDeclaration> list = listTransformFunctionFlat;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(RenderIrElementKt.render$default((IrDeclaration) it.next(), (DumpIrTreeOptions) null, 1, (Object) null));
                }
                arrayList = arrayList2;
            }
            throw new IllegalArgumentException(sbAppend.append(arrayList).toString().toString());
        }
        return makeNewLambda(irFunction, (IrFunctionReference) irStatement2, new Function1<IrSimpleFunction, IrBody>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.visitLambda.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrBody invoke(IrSimpleFunction wrapper) {
                Intrinsics.checkNotNullParameter(wrapper, "wrapper");
                Set set = (Set) JvmMultiFieldValueClassLowering.this.variablesToAdd.get(replacement);
                if (set != null) {
                    JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = JvmMultiFieldValueClassLowering.this;
                    IrFunction irFunction2 = replacement;
                    jvmMultiFieldValueClassLowering.variablesToAdd.put(wrapper, set);
                }
                if (JvmMultiFieldValueClassLowering.this.possibleExtraBoxUsageGenerated.contains(replacement)) {
                    JvmMultiFieldValueClassLowering.this.possibleExtraBoxUsageGenerated.add(wrapper);
                    JvmMultiFieldValueClassLowering.this.possibleExtraBoxUsageGenerated.remove(replacement);
                }
                JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(JvmMultiFieldValueClassLowering.this.getContext(), wrapper.getSymbol(), 0, 0, 6, null);
                IrFunction irFunction3 = replacement;
                final JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering2 = JvmMultiFieldValueClassLowering.this;
                JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
                final IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), null, null, false, 64, null);
                List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(wrapper);
                Function1 function1 = new Function1<IrValueParameter, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitLambda$5$2$1$newArguments$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final List<IrDeclaration> invoke(IrValueParameter parameter) {
                        Intrinsics.checkNotNullParameter(parameter, "parameter");
                        if (!MfvcNodeKt.needsMfvcFlattening(parameter.getType())) {
                            return CollectionsKt.listOf(parameter);
                        }
                        parameter.setOrigin(JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER.INSTANCE);
                        RootMfvcNode rootMfvcNodeInvoke = jvmMultiFieldValueClassLowering2.getReplacements().getGetRootMfvcNode().invoke(JvmIrTypeUtilsKt.getErasedUpperBound(parameter.getType()));
                        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                        List<IrExpression> listMakeFlattenedGetterExpressions = MfvcNodeKt.createInstanceFromBox(rootMfvcNodeInvoke, irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, parameter), AccessType.ChooseEffective, new AnonymousClass1(jvmMultiFieldValueClassLowering2)).makeFlattenedGetterExpressions(irBlockBuilder, jvmMultiFieldValueClassLowering2.getIrCurrentClass(), new AnonymousClass2(jvmMultiFieldValueClassLowering2));
                        IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
                        JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering3 = jvmMultiFieldValueClassLowering2;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listMakeFlattenedGetterExpressions, 10));
                        int i = 0;
                        for (Object obj : listMakeFlattenedGetterExpressions) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            arrayList3.add(MfvcNodeInstanceKt.savableStandaloneVariableWithSetter$default(irBlockBuilder3, (IrExpression) obj, parameter.getName().asString() + '-' + MfvcNodeKt.getFullFieldName(rootMfvcNodeInvoke.getLeaves().get(i)), false, JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE.INSTANCE, false, new JvmMultiFieldValueClassLowering$visitLambda$5$2$1$newArguments$1$3$1(jvmMultiFieldValueClassLowering3), 20, null));
                            i = i2;
                        }
                        return arrayList3;
                    }

                    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitLambda$5$2$1$newArguments$1$1, reason: invalid class name */
                    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrVariable, Unit> {
                        AnonymousClass1(Object obj) {
                            super(1, obj);
                        }

                        @Override // kotlin.jvm.internal.CallableReference
                        public final String getSignature() {
                            return "variablesSaver(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V";
                        }

                        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                        public final String getName() {
                            return "variablesSaver";
                        }

                        @Override // kotlin.jvm.internal.CallableReference
                        public final KDeclarationContainer getOwner() {
                            return Reflection.getOrCreateKotlinClass(JvmMultiFieldValueClassLowering.class);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrVariable irVariable) {
                            invoke2(irVariable);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrVariable p0) {
                            Intrinsics.checkNotNullParameter(p0, "p0");
                            ((JvmMultiFieldValueClassLowering) this.receiver).variablesSaver(p0);
                        }
                    }

                    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitLambda$5$2$1$newArguments$1$2, reason: invalid class name */
                    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function0<Unit> {
                        AnonymousClass2(Object obj) {
                            super(0, obj);
                        }

                        @Override // kotlin.jvm.internal.CallableReference
                        public final String getSignature() {
                            return "registerPossibleExtraBoxUsage()V";
                        }

                        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                        public final String getName() {
                            return "registerPossibleExtraBoxUsage";
                        }

                        @Override // kotlin.jvm.internal.CallableReference
                        public final KDeclarationContainer getOwner() {
                            return Reflection.getOrCreateKotlinClass(JvmMultiFieldValueClassLowering.class);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            ((JvmMultiFieldValueClassLowering) this.receiver).registerPossibleExtraBoxUsage();
                        }
                    }
                };
                ArrayList arrayList3 = new ArrayList();
                Iterator<T> it2 = explicitParameters.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(arrayList3, (Iterable) function1.invoke(it2.next()));
                }
                irBlockBuilder.unaryPlus(IrInlineUtilsKt.inline(irFunction3, wrapper.getParent(), arrayList3));
                return ExpressionHelpersKt.irExprBody(jvmIrBuilder, irBlockBuilder.getIrBlockBody());
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(final IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        final IrFunction owner = expression.getSymbol().getOwner();
        final IrFunction replacement = getReplacement(owner);
        if (replacement == null) {
            return super.visitFunctionReference(expression);
        }
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), expression.getSymbol(), expression);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        buildReplacement(irBlockBuilder, owner, expression, replacement, new Function1<IrFunctionSymbol, IrMemberAccessExpression<?>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitFunctionReference$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrMemberAccessExpression<?> invoke(IrFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return (IrMemberAccessExpression) IrDeclarationsKt.copyAttributes(new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), replacement.getSymbol(), owner.getTypeParameters().size(), replacement.getValueParameters().size(), expression.getReflectionTarget(), expression.getOrigin()), expression);
            }
        });
        return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
    }

    private final IrFunction getReplacement(IrFunction irFunction) {
        IrSimpleFunction replacementFunction = getReplacements().getReplacementFunction(irFunction);
        if (replacementFunction != null) {
            return replacementFunction;
        }
        IrConstructor irConstructor = irFunction instanceof IrConstructor ? (IrConstructor) irFunction : null;
        return irConstructor != null ? getReplacements().getReplacementForRegularClassConstructor(irConstructor) : null;
    }

    private final IrContainerExpression makeNewLambda(IrFunction originalFunction, IrFunctionReference expression, Function1<? super IrSimpleFunction, ? extends IrBody> makeBody) {
        IrDeclarationParent currentDeclarationParent = getCurrentDeclarationParent();
        Intrinsics.checkNotNull(currentDeclarationParent);
        IrFactory irFactory = getContext().getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom(originalFunction);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setFakeOverride(false);
        irFunctionBuilder.setReturnType(originalFunction.getReturnType());
        irFunctionBuilder.setName(originalFunction.getName());
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(currentDeclarationParent);
        irSimpleFunctionBuildFunction.getTypeParameters().isEmpty();
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrFunction irFunction = originalFunction;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction, irFunction, null, null, 6, null);
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(irFunction, irSimpleFunction);
        if (originalFunction.getDispatchReceiverParameter() != null && originalFunction.getExtensionReceiverParameter() != null) {
            throw new IllegalArgumentException((RenderIrElementKt.render$default(originalFunction, (DumpIrTreeOptions) null, 1, (Object) null) + " has both a member and an extension receivers at the same time.\nReferences to such elements are not allowed").toString());
        }
        IrValueParameter dispatchReceiverParameter = originalFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null) {
            dispatchReceiverParameter = originalFunction.getExtensionReceiverParameter();
        }
        IrValueParameter irValueParameter = dispatchReceiverParameter;
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(irValueParameter != null ? IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter.getType(), mapMakeTypeParameterSubstitutionMap), null, null, false, false, false, 8062, null) : null);
        List<IrValueParameter> valueParameters = originalFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter2 = (IrValueParameter) obj;
            arrayList.add(IrUtilsKt.copyTo$default(irValueParameter2, irSimpleFunctionBuildFunction, null, i, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter2.getType(), mapMakeTypeParameterSubstitutionMap), null, null, false, false, false, 8058, null));
            i = i2;
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering = this;
        jvmMultiFieldValueClassLowering.unsafeEnterScope(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setBody(makeBody.invoke(irSimpleFunctionBuildFunction));
        postActionAfterTransformingClassDeclaration(irSimpleFunctionBuildFunction);
        Unit unit = Unit.INSTANCE;
        jvmMultiFieldValueClassLowering.unsafeLeaveScope();
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(-1, -1, expression.getType(), irSimpleFunctionBuildFunction.getSymbol(), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), expression.getReflectionTarget(), expression.getOrigin());
        IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionReferenceImpl, expression, 0, 2, null);
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = expression.getExtensionReceiver();
        }
        irFunctionReferenceImpl.setExtensionReceiver(dispatchReceiver != null ? dispatchReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
        IntRange indices = CollectionsKt.getIndices(irSimpleFunctionBuildFunction.getValueParameters());
        int valueArgumentsCount = expression.getValueArgumentsCount();
        ArrayList arrayList2 = new ArrayList(valueArgumentsCount);
        for (int i3 = 0; i3 < valueArgumentsCount; i3++) {
            arrayList2.add(expression.getValueArgument(i3));
        }
        for (Pair pair : CollectionsKt.zip(indices, arrayList2)) {
            int iIntValue = ((Number) pair.component1()).intValue();
            IrExpression irExpression = (IrExpression) pair.component2();
            irFunctionReferenceImpl.putValueArgument(iIntValue, irExpression != null ? irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
        }
        IrFunctionReferenceImpl irFunctionReferenceImpl2 = irFunctionReferenceImpl;
        IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl2, expression);
        Type localClassType = getContext().getLocalClassType(expression.getAttributeOwnerId());
        if (localClassType != null) {
            getContext().putLocalClassType(irFunctionReferenceImpl2, localClassType);
        }
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), IrStatementOrigin.LAMBDA.INSTANCE, null, false, 64, null);
        irBlockBuilder.unaryPlus(irSimpleFunctionBuildFunction);
        irBlockBuilder.unaryPlus(irFunctionReferenceImpl);
        return irBlockBuilder.getIrBlockBody();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(final IrFunctionAccessExpression expression) {
        IrConstructor replacementForRegularClassConstructor;
        Function1<IrConstructorSymbol, IrFunctionAccessExpression> function1;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunction owner = expression.getSymbol().getOwner();
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        IrElement irElement = currentScope.getIrElement();
        Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        IrDeclaration irDeclaration = (IrDeclaration) irElement;
        IrSimpleFunction replacementFunction = getReplacements().getReplacementFunction(owner);
        boolean z = owner instanceof IrConstructor;
        if (z) {
            IrConstructor irConstructor = (IrConstructor) owner;
            if (irConstructor.getIsPrimary() && IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(irConstructor)) && !Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE)) {
                JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), irDeclaration.getSymbol(), expression);
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
                RootMfvcNode rootMfvcNodeInvoke = getReplacements().getGetRootMfvcNode().invoke(AdditionalIrUtilsKt.getConstructedClass(irConstructor));
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrType constructedClassType = IrUtilsKt.getConstructedClassType(irConstructor);
                Intrinsics.checkNotNull(constructedClassType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                Name nameIdentifier = Name.identifier("constructor_tmp");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType = MfvcNodeKt.createInstanceFromValueDeclarationsAndBoxType(rootMfvcNodeInvoke, irBlockBuilder2, (IrSimpleType) constructedClassType, nameIdentifier, new JvmMultiFieldValueClassLowering$visitFunctionAccess$1$instance$1(this), false, JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE.INSTANCE);
                Iterator<IrValueDeclaration> it = valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType.getValueDeclarations().iterator();
                while (it.hasNext()) {
                    it.next().setOrigin(JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE);
                }
                ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType;
                flattenExpressionTo(irBlockBuilder, expression, valueDeclarationMfvcNodeInstance);
                IrExpression irExpressionMakeGetterExpression = valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType.makeGetterExpression(irBlockBuilder2, getIrCurrentClass(), new JvmMultiFieldValueClassLowering$visitFunctionAccess$1$getterExpression$1(this));
                this.valueDeclarationsRemapper.registerReplacement(irExpressionMakeGetterExpression, valueDeclarationMfvcNodeInstance);
                irBlockBuilder.unaryPlus(irExpressionMakeGetterExpression);
                return irBlockBuilder.getIrBlockBody();
            }
        }
        if (replacementFunction != null) {
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder2 = JvmIrBuilderKt.createJvmIrBuilder(getContext(), irDeclaration.getSymbol(), expression);
            IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder2.getContext(), jvmIrBuilderCreateJvmIrBuilder2.getScope(), jvmIrBuilderCreateJvmIrBuilder2.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder2.getEndOffset(), null, null, false, 64, null);
            buildReplacement$default(this, irBlockBuilder3, owner, expression, replacementFunction, null, 8, null);
            return IrUtilsKt.unwrapBlock(irBlockBuilder3.getIrBlockBody());
        }
        IrConstructor irConstructor2 = z ? (IrConstructor) owner : null;
        if (irConstructor2 == null || (replacementForRegularClassConstructor = getReplacements().getReplacementForRegularClassConstructor(irConstructor2)) == null) {
            return super.visitFunctionAccess(expression);
        }
        if (expression instanceof IrConstructorCall) {
            function1 = new Function1<IrConstructorSymbol, IrFunctionAccessExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitFunctionAccess$callFactory$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrFunctionAccessExpression invoke(IrConstructorSymbol constructorSymbol) {
                    Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
                    return IrConstructorCallImpl.INSTANCE.fromSymbolOwner(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), constructorSymbol, expression.getOrigin());
                }
            };
        } else if (expression instanceof IrDelegatingConstructorCall) {
            function1 = new Function1<IrConstructorSymbol, IrFunctionAccessExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitFunctionAccess$callFactory$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrFunctionAccessExpression invoke(IrConstructorSymbol constructorSymbol) {
                    Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
                    return IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(IrDelegatingConstructorCallImpl.INSTANCE, expression.getStartOffset(), expression.getEndOffset(), expression.getType(), constructorSymbol, 0, 0, 48, null);
                }
            };
        } else {
            if (!(expression instanceof IrEnumConstructorCall)) {
                throw new IllegalStateException(("Unknown constructor call type:\n" + DumpIrTreeKt.dump$default(expression, null, 1, null)).toString());
            }
            function1 = new Function1<IrConstructorSymbol, IrFunctionAccessExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitFunctionAccess$callFactory$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrFunctionAccessExpression invoke(IrConstructorSymbol constructorSymbol) {
                    Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
                    return new IrEnumConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), constructorSymbol, expression.getTypeArgumentsCount(), expression.getValueArgumentsCount());
                }
            };
        }
        final Function1<IrConstructorSymbol, IrFunctionAccessExpression> function2 = function1;
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder3 = JvmIrBuilderKt.createJvmIrBuilder(getContext(), irDeclaration.getSymbol(), expression);
        IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder3.getContext(), jvmIrBuilderCreateJvmIrBuilder3.getScope(), jvmIrBuilderCreateJvmIrBuilder3.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder3.getEndOffset(), null, null, false, 64, null);
        buildReplacement(irBlockBuilder4, owner, expression, replacementForRegularClassConstructor, new Function1<IrFunctionSymbol, IrMemberAccessExpression<?>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitFunctionAccess$3$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrMemberAccessExpression<?> invoke(IrFunctionSymbol it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return function2.invoke((IrConstructorSymbol) it2);
            }
        });
        return IrUtilsKt.unwrapBlock(irBlockBuilder4.getIrBlockBody());
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0228 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:107:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:72:0x0208  */
    /* JADX WARN: Code duplicated, block: B:75:0x021b  */
    /* JADX WARN: Code duplicated, block: B:81:0x023a A[LOOP:2: B:79:0x0234->B:81:0x023a, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:83:0x0254  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        IrBlockBuilder irBlockBuilder;
        List<IrExpression> listFlattenExpression;
        ArrayList arrayList;
        Iterator it;
        IrSimpleFunction specializedEqualsMethod;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrProperty property = getProperty(owner);
        if (property != null && AdditionalIrUtilsKt.isGetter(owner) && getReplacements().getMfvcPropertyNode(property) != null) {
            if (!owner.getValueParameters().isEmpty()) {
                throw new IllegalArgumentException(("Unexpected getter:\n" + DumpIrTreeKt.dump$default(owner, null, 1, null)).toString());
            }
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            expression.setDispatchReceiver(dispatchReceiver != null ? dispatchReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
            IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
            return this.valueDeclarationsRemapper.addReplacement(irBlockBuilder2, expression) == null ? expression : IrUtilsKt.unwrapBlock(irBlockBuilder2.getIrBlockBody());
        }
        if (isSpecializedMFVCEqEq(expression)) {
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder2 = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
            IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder2.getContext(), jvmIrBuilderCreateJvmIrBuilder2.getScope(), jvmIrBuilderCreateJvmIrBuilder2.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder2.getEndOffset(), null, null, false, 64, null);
            IrExpression valueArgument = expression.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            IrExpression valueArgument2 = expression.getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument2);
            IrClass erasedUpperBound = JvmIrTypeUtilsKt.getErasedUpperBound(valueArgument.getType());
            RootMfvcNode rootMfvcNodeOrNull = MfvcNodeKt.needsMfvcFlattening(valueArgument.getType()) ? getReplacements().getRootMfvcNodeOrNull(erasedUpperBound) : null;
            RootMfvcNode rootMfvcNodeOrNull2 = MfvcNodeKt.needsMfvcFlattening(valueArgument2.getType()) ? getReplacements().getRootMfvcNodeOrNull(JvmIrTypeUtilsKt.getErasedUpperBound(valueArgument2.getType())) : null;
            if (rootMfvcNodeOrNull == null) {
                if (rootMfvcNodeOrNull2 != null) {
                    if (!IrUtilsKt.isNullConst(valueArgument)) {
                        if (Intrinsics.areEqual(JvmIrTypeUtilsKt.getErasedUpperBound(valueArgument.getType()), JvmIrTypeUtilsKt.getErasedUpperBound(valueArgument2.getType())) && org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(valueArgument.getType())) {
                            IrBlockBuilder irBlockBuilder4 = irBlockBuilder3;
                            IrBlockBuilder irBlockBuilder5 = new IrBlockBuilder(irBlockBuilder4.getContext(), irBlockBuilder4.getScope(), irBlockBuilder4.getStartOffset(), irBlockBuilder4.getEndOffset(), null, null, false, 64, null);
                            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder5, valueArgument, null, null, false, null, 30, null);
                            IrBlockBuilder irBlockBuilder6 = irBlockBuilder5;
                            IrType booleanType = irBlockBuilder5.getContext().getIrBuiltIns().getBooleanType();
                            IrVariable irVariable = irVariableIrTemporary$default;
                            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder6, irVariable);
                            IrConstImpl<Boolean> irConstImplIrFalse = ExpressionHelpersKt.irFalse(irBlockBuilder6);
                            IrBlockBuilder irBlockBuilder7 = new IrBlockBuilder(irBlockBuilder6.getContext(), irBlockBuilder6.getScope(), irBlockBuilder6.getStartOffset(), irBlockBuilder6.getEndOffset(), null, null, false, 64, null);
                            IrBlockBuilder irBlockBuilder8 = irBlockBuilder7;
                            IrVariable irVariableIrTemporary$default2 = ExpressionHelpersKt.irTemporary$default(irBlockBuilder7, ExpressionHelpersKt.irImplicitCast(irBlockBuilder8, ExpressionHelpersKt.irGet(irBlockBuilder8, irVariable), IrTypesKt.makeNotNull(valueArgument.getType())), null, null, false, null, 30, null);
                            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder8, irBlockBuilder7.getContext().getIrBuiltIns().getEqeqSymbol());
                            IrExpressionsKt.copyTypeArgumentsFrom$default(irCall, expression, 0, 2, null);
                            irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder8, irVariableIrTemporary$default2));
                            irCall.putValueArgument(1, valueArgument2);
                            irBlockBuilder7.unaryPlus(irCall);
                            Unit unit = Unit.INSTANCE;
                            irBlockBuilder5.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder6, booleanType, irGetValueImplIrGet, irConstImplIrFalse, irBlockBuilder7.getIrBlockBody()));
                            irBlockBuilder = irBlockBuilder3;
                            irBlockBuilder.unaryPlus((IrStatement) irBlockBuilder5.getIrBlockBody().transform((IrElementTransformer) this, (Object) null));
                        } else {
                            return super.visitCall(expression);
                        }
                    } else {
                        List listFlatMapLeaves = MfvcNodeKt.flatMapLeaves(rootMfvcNodeOrNull2, new Function1<LeafMfvcNode, List<? extends Boolean>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitCall$lambda$96$$inlined$mapLeaves$1
                            @Override // kotlin.jvm.functions.Function1
                            public final List<Boolean> invoke(LeafMfvcNode it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return CollectionsKt.listOf(Boolean.valueOf(it2.getHasPureUnboxMethod()));
                            }
                        });
                        if ((listFlatMapLeaves instanceof Collection) && listFlatMapLeaves.isEmpty()) {
                            listFlattenExpression = flattenExpression(irBlockBuilder3, valueArgument2);
                            if ((listFlattenExpression.size() > 1) == MfvcNodeKt.needsMfvcFlattening(valueArgument2.getType())) {
                                throw new IllegalArgumentException(("Illegal flattening of " + DumpIrTreeKt.dump$default(valueArgument2, null, 1, null) + "\n\n" + CollectionsKt.joinToString$default(listFlattenExpression, "\n", null, null, 0, null, new Function1<IrExpression, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitCall$3$2$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final CharSequence invoke(IrExpression it2) {
                                        Intrinsics.checkNotNullParameter(it2, "it");
                                        return DumpIrTreeKt.dump$default(it2, null, 1, null);
                                    }
                                }, 30, null)).toString());
                            }
                            arrayList = new ArrayList();
                            for (Object obj : listFlattenExpression) {
                                if (!MfvcNodeInstanceKt.isRepeatableGetter((IrExpression) obj)) {
                                    arrayList.add(obj);
                                }
                            }
                            it = arrayList.iterator();
                            while (it.hasNext()) {
                                irBlockBuilder3.unaryPlus((IrExpression) it.next());
                            }
                        } else {
                            Iterator it2 = listFlatMapLeaves.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    listFlattenExpression = flattenExpression(irBlockBuilder3, valueArgument2);
                                    if ((listFlattenExpression.size() > 1) == MfvcNodeKt.needsMfvcFlattening(valueArgument2.getType())) {
                                        throw new IllegalArgumentException(("Illegal flattening of " + DumpIrTreeKt.dump$default(valueArgument2, null, 1, null) + "\n\n" + CollectionsKt.joinToString$default(listFlattenExpression, "\n", null, null, 0, null, new Function1<IrExpression, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$visitCall$3$2$1
                                            @Override // kotlin.jvm.functions.Function1
                                            public final CharSequence invoke(IrExpression it3) {
                                                Intrinsics.checkNotNullParameter(it3, "it");
                                                return DumpIrTreeKt.dump$default(it3, null, 1, null);
                                            }
                                        }, 30, null)).toString());
                                    }
                                    arrayList = new ArrayList();
                                    while (r1.hasNext()) {
                                        if (!MfvcNodeInstanceKt.isRepeatableGetter((IrExpression) obj)) {
                                            arrayList.add(obj);
                                        }
                                    }
                                    it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        irBlockBuilder3.unaryPlus((IrExpression) it.next());
                                    }
                                } else if (!((Boolean) it2.next()).booleanValue()) {
                                    irBlockBuilder3.unaryPlus(valueArgument2.transform((IrElementTransformer<? super Object>) this, (Object) null));
                                }
                            }
                        }
                        irBlockBuilder3.unaryPlus(ExpressionHelpersKt.irFalse(irBlockBuilder3));
                    }
                    return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
                }
                return super.visitCall(expression);
            }
            if (rootMfvcNodeOrNull2 != null) {
                if (!Intrinsics.areEqual(rootMfvcNodeOrNull, rootMfvcNodeOrNull2)) {
                    throw new IllegalArgumentException(("Different nodes: " + rootMfvcNodeOrNull + ", " + rootMfvcNodeOrNull2).toString());
                }
                specializedEqualsMethod = rootMfvcNodeOrNull.getSpecializedEqualsMethod();
            } else {
                boolean z = false;
                IrSimpleFunction irSimpleFunction = null;
                for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(erasedUpperBound)) {
                    if (IrUtilsKt.isEquals(irSimpleFunction2)) {
                        if (z) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z = true;
                        irSimpleFunction = irSimpleFunction2;
                    }
                }
                if (!z) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                specializedEqualsMethod = irSimpleFunction;
            }
            IrMemberAccessExpression irMemberAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder3, specializedEqualsMethod);
            irMemberAccessExpressionIrCall.setDispatchReceiver(valueArgument);
            irMemberAccessExpressionIrCall.putValueArgument(0, valueArgument2);
            irBlockBuilder3.unaryPlus((IrStatement) irMemberAccessExpressionIrCall.transform((IrElementTransformer) this, (Object) null));
            irBlockBuilder = irBlockBuilder3;
            return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
        }
        return super.visitCall(expression);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ IrMemberAccessExpression buildReplacement$default(JvmMultiFieldValueClassLowering jvmMultiFieldValueClassLowering, final IrBlockBuilder irBlockBuilder, IrFunction irFunction, final IrMemberAccessExpression irMemberAccessExpression, IrFunction irFunction2, Function1 function1, int i, Object obj) {
        if ((i & 8) != 0) {
            function1 = new Function1<IrFunctionSymbol, IrCall>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering.buildReplacement.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrCall invoke(IrFunctionSymbol callee) {
                    Intrinsics.checkNotNullParameter(callee, "callee");
                    IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                    IrFunction owner = callee.getOwner();
                    IrMemberAccessExpression<?> irMemberAccessExpression2 = irMemberAccessExpression;
                    IrCall irCall = irMemberAccessExpression2 instanceof IrCall ? (IrCall) irMemberAccessExpression2 : null;
                    return ExpressionHelpersKt.irCall$default(irBlockBuilder2, owner, (IrStatementOrigin) null, irCall != null ? irCall.getSuperQualifierSymbol() : null, 2, (Object) null);
                }
            };
        }
        return jvmMultiFieldValueClassLowering.buildReplacement(irBlockBuilder, irFunction, irMemberAccessExpression, irFunction2, function1);
    }

    private final IrMemberAccessExpression<?> buildReplacement(IrBlockBuilder irBlockBuilder, IrFunction irFunction, final IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction2, Function1<? super IrFunctionSymbol, ? extends IrMemberAccessExpression<?>> function1) {
        List<Pair<IrValueParameter, IrExpression>> listTypedArgumentList = typedArgumentList(irFunction, irMemberAccessExpression);
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list = getReplacements().getBindingOldFunctionToParameterTemplateStructure().get(irFunction);
        Intrinsics.checkNotNull(list);
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list2 = list;
        if (listTypedArgumentList.size() != list2.size()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Iterator<T> it = list2.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()).getValueParameters().size();
        }
        if (size != IrUtilsKt.getExplicitParametersCount(irFunction2)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List<Pair<IrValueParameter, IrExpression>> list3 = listTypedArgumentList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it2 = list3.iterator();
        while (it2.hasNext()) {
            arrayList.add((IrExpression) ((Pair) it2.next()).component2());
        }
        List<IrExpression> listMakeNewArguments = makeNewArguments(irBlockBuilder, arrayList, list2);
        IrMemberAccessExpression<?> irMemberAccessExpressionInvoke = function1.invoke(irFunction2.getSymbol());
        passTypeArgumentsWithOffsets(irMemberAccessExpressionInvoke, irFunction2, irFunction, new Function1<Integer, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$buildReplacement$resultExpression$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ IrType invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final IrType invoke(int i) {
                IrType typeArgument = irMemberAccessExpression.getTypeArgument(i);
                Intrinsics.checkNotNull(typeArgument);
                return typeArgument;
            }
        });
        for (Pair pair : CollectionsKt.zip(IrUtilsKt.getExplicitParameters(irFunction2), listMakeNewArguments)) {
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            IrExpression irExpression = (IrExpression) pair.component2();
            if (irExpression != null) {
                IrExpressionsKt.putArgument(irMemberAccessExpressionInvoke, irFunction2, irValueParameter, irExpression);
            }
        }
        irBlockBuilder.unaryPlus(irMemberAccessExpressionInvoke);
        return irMemberAccessExpressionInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int size = expression.getArguments().size();
        for (int i = 0; i < size; i++) {
            IrExpression irExpression = expression.getArguments().get(i);
            if (MfvcNodeKt.needsMfvcFlattening(irExpression.getType())) {
                List<IrExpression> arguments = expression.getArguments();
                JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
                boolean z = false;
                IrSimpleFunction irSimpleFunction = null;
                for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(JvmIrTypeUtilsKt.getErasedUpperBound(irExpression.getType()))) {
                    if (IrUtilsKt.isToString(irSimpleFunction2)) {
                        if (z) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        irSimpleFunction = irSimpleFunction2;
                        z = true;
                    }
                }
                if (z) {
                    IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                    if (!irSimpleFunction3.getTypeParameters().isEmpty()) {
                        throw new IllegalArgumentException(("Bad toString: " + RenderIrElementKt.render$default(irSimpleFunction3, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilderCreateJvmIrBuilder, irSimpleFunction3);
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(irExpression);
                    arguments.set(i, irFunctionAccessExpressionIrCall);
                } else {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            }
        }
        return super.visitStringConcatenation(expression);
    }

    private final List<IrExpression> makeNewArguments(IrBlockBuilder irBlockBuilder, List<? extends IrExpression> list, List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> list2) {
        ArrayList arrayListFlattenExpression;
        List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> list3 = list2;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()).getValueParameters().size()));
        }
        List listZip = CollectionsKt.zip(list, arrayList);
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        for (Object obj : listZip) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair = (Pair) obj;
            IrExpression irExpression = (IrExpression) pair.component1();
            int iIntValue = ((Number) pair.component2()).intValue();
            if (irExpression == null) {
                ArrayList arrayList3 = new ArrayList(iIntValue);
                for (int i3 = 0; i3 < iIntValue; i3++) {
                    arrayList3.add(null);
                }
                arrayListFlattenExpression = arrayList3;
            } else if (iIntValue == 1) {
                arrayListFlattenExpression = CollectionsKt.listOf(irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null));
            } else {
                MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter = list2.get(i);
                Intrinsics.checkNotNull(remappedParameter, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping");
                arrayListFlattenExpression = flattenExpression(irBlockBuilder, castExpressionToNotNullTypeIfNeeded(irBlockBuilder, irExpression, ((MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) remappedParameter).getBoxedType()));
                if (arrayListFlattenExpression.size() != iIntValue) {
                    throw new IllegalArgumentException(("Expected " + iIntValue + " arguments but got " + arrayListFlattenExpression.size()).toString());
                }
            }
            CollectionsKt.addAll(arrayList2, arrayListFlattenExpression);
            i = i2;
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression castExpressionToNotNullTypeIfNeeded(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrType irType) {
        if (Intrinsics.areEqual(irType, irExpression.getType())) {
            return irExpression;
        }
        return Intrinsics.areEqual(irType, IrTypesKt.makeNotNull(irExpression.getType())) ? ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, irExpression, irType) : ExpressionHelpersKt.irAs(irBuilderWithScope, irExpression, irType);
    }

    private final List<IrExpression> removeExtraSetVariablesFromExpressionList(IrBuilderWithScope irBuilderWithScope, IrContainerExpression irContainerExpression, List<? extends IrVariable> list) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Set set = CollectionsKt.toSet(list);
        ArrayList arrayList = new ArrayList();
        List mutableList = CollectionsKt.toMutableList((Collection) list);
        removeExtraSetVariablesFromExpressionList$recur(mutableList, linkedHashSet, arrayList, set, irContainerExpression);
        List list2 = mutableList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(ExpressionHelpersKt.irGet(irBuilderWithScope, (IrVariable) it.next()));
        }
        return CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.asReversedMutable(arrayList));
    }

    private static final boolean removeExtraSetVariablesFromExpressionList$recur(List<IrVariable> list, final Set<IrVariable> set, List<IrExpression> list2, final Set<? extends IrVariable> set2, IrContainerExpression irContainerExpression) {
        while (!irContainerExpression.getStatements().isEmpty() && !list.isEmpty()) {
            IrStatement irStatement = (IrStatement) CollectionsKt.last((List) irContainerExpression.getStatements());
            if (irStatement instanceof IrContainerExpression) {
                IrContainerExpression irContainerExpression2 = (IrContainerExpression) irStatement;
                if (removeExtraSetVariablesFromExpressionList$recur(list, set, list2, set2, irContainerExpression2)) {
                    if (irContainerExpression2.getStatements().isEmpty()) {
                        CollectionsKt.removeLast(irContainerExpression.getStatements());
                    }
                    return true;
                }
                if (!irContainerExpression2.getStatements().isEmpty() && !list.isEmpty()) {
                    throw new IllegalArgumentException("Not all statements removed".toString());
                }
                if (irContainerExpression2.getStatements().isEmpty()) {
                    CollectionsKt.removeLast(irContainerExpression.getStatements());
                }
            } else {
                if (!(irStatement instanceof IrSetValue)) {
                    return true;
                }
                IrSetValue irSetValue = (IrSetValue) irStatement;
                if (!Intrinsics.areEqual(irSetValue.getSymbol().getOwner(), CollectionsKt.last((List) list)) || CollectionsKt.contains(set, irSetValue.getSymbol().getOwner())) {
                    return true;
                }
                list2.add(irSetValue.getValue());
                CollectionsKt.removeLast(list);
                CollectionsKt.removeLast(irContainerExpression.getStatements());
                IrVisitorsKt.acceptVoid(irSetValue.getValue(), new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$removeExtraSetVariablesFromExpressionList$recur$2
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
                    public void visitCall(IrCall irCall) {
                        IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
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
                    public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression3, Object obj) {
                        visitContainerExpression(irContainerExpression3, (Void) obj);
                        return Unit.INSTANCE;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitContainerExpression(IrContainerExpression irContainerExpression3) {
                        IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression3);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitContainerExpression(IrContainerExpression irContainerExpression3, Void r2) {
                        IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression3, r2);
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
                    public void visitSetField(IrSetField irSetField) {
                        IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitSetField(IrSetField irSetField, Void r2) {
                        IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                    /* JADX INFO: renamed from: visitSetValue */
                    public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue2, Object obj) {
                        visitSetValue(irSetValue2, (Void) obj);
                        return Unit.INSTANCE;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitSetValue(IrSetValue irSetValue2) {
                        IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue2);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitSetValue(IrSetValue irSetValue2, Void r2) {
                        IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue2, r2);
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
                    public void visitVariable(IrVariable irVariable) {
                        IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
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

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    /* JADX INFO: renamed from: visitElement */
                    public void mo6335visitElement(IrElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        IrVisitorsKt.acceptChildrenVoid(element, this);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitValueAccess(IrValueAccessExpression expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrValueDeclaration owner = expression.getSymbol().getOwner();
                        if ((owner instanceof IrVariable) && set2.contains(owner)) {
                            set.add((IrVariable) owner);
                        }
                        IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, expression);
                    }
                });
            }
        }
        return false;
    }

    private final boolean isSpecializedMFVCEqEq(IrCall irCall) {
        if (!Intrinsics.areEqual(irCall.getSymbol(), getContext().getIrBuiltIns().getEqeqSymbol())) {
            return false;
        }
        List<IrExpression> listListOf = CollectionsKt.listOf((Object[]) new IrExpression[]{irCall.getValueArgument(0), irCall.getValueArgument(1)});
        if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
            return false;
        }
        for (IrExpression irExpression : listListOf) {
            Intrinsics.checkNotNull(irExpression);
            if (IrDeclarationsKt.isMultiFieldValueClass(JvmIrTypeUtilsKt.getErasedUpperBound(irExpression.getType()))) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression receiver = expression.getReceiver();
        expression.setReceiver(receiver != null ? receiver.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
        ValueDeclarationRemapper valueDeclarationRemapper = this.valueDeclarationsRemapper;
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), expression.getSymbol(), expression);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        return valueDeclarationRemapper.addReplacement(irBlockBuilder, expression) == null ? expression : IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression receiver = expression.getReceiver();
        expression.setReceiver(receiver != null ? receiver.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
        ValueDeclarationRemapper valueDeclarationRemapper = this.valueDeclarationsRemapper;
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        if (valueDeclarationRemapper.addReplacement(irBlockBuilder, expression, !Intrinsics.areEqual(expression.getOrigin(), UNSAFE_MFVC_SET_ORIGIN.INSTANCE)) == null) {
            expression.setValue(expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null));
            return expression;
        }
        return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression irExpressionMakeReplacement = this.valueDeclarationsRemapper.makeReplacement(JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression), expression);
        return irExpressionMakeReplacement == null ? super.visitGetValue(expression) : irExpressionMakeReplacement;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), expression);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        if (this.valueDeclarationsRemapper.addReplacement(irBlockBuilder, expression, !Intrinsics.areEqual(expression.getOrigin(), UNSAFE_MFVC_SET_ORIGIN.INSTANCE)) == null) {
            return super.visitSetValue(expression);
        }
        return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
    }

    private final boolean isTemporary(IrDeclarationOrigin irDeclarationOrigin) {
        return Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitVariable(IrVariable declaration) {
        JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE multi_field_value_class_representation_variable;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrExpression initializer = declaration.getInitializer();
        if (MfvcNodeKt.needsMfvcFlattening(declaration.getType())) {
            RootMfvcNode rootMfvcNodeInvoke = getReplacements().getGetRootMfvcNode().invoke(JvmIrTypeUtilsKt.getErasedUpperBound(declaration.getType()));
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(getContext(), getCurrentScopeSymbol(), declaration);
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset(), null, null, false, 64, null);
            if (isTemporary(declaration.getOrigin())) {
                multi_field_value_class_representation_variable = JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE;
            } else {
                multi_field_value_class_representation_variable = JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE.INSTANCE;
            }
            IrType type = declaration.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType = MfvcNodeKt.createInstanceFromValueDeclarationsAndBoxType(rootMfvcNodeInvoke, irBlockBuilder, (IrSimpleType) type, declaration.getName(), new JvmMultiFieldValueClassLowering$visitVariable$1$instance$1(this), declaration.getIsVar(), multi_field_value_class_representation_variable);
            this.valueDeclarationsRemapper.registerReplacement(declaration, valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType);
            if (initializer != null) {
                flattenExpressionTo(irBlockBuilder, initializer, valueDeclarationMfvcNodeInstanceCreateInstanceFromValueDeclarationsAndBoxType);
            }
            return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
        }
        return super.visitVariable(declaration);
    }

    private final IrSymbol getCurrentScopeSymbol() {
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        IrElement irElement = currentScope.getIrElement();
        Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
        return ((IrSymbolOwner) irElement).getSymbol();
    }

    public final List<IrExpression> flattenExpression(IrBlockBuilder irBlockBuilder, IrExpression expression) {
        Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!MfvcNodeKt.needsMfvcFlattening(expression.getType())) {
            return CollectionsKt.listOf(expression.transform((IrElementTransformer<? super Object>) this, (Object) null));
        }
        RootMfvcNode rootMfvcNodeInvoke = getReplacements().getGetRootMfvcNode().invoke(JvmIrTypeUtilsKt.getErasedUpperBound(expression.getType()));
        IrType type = expression.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeArgumentsFromType = MfvcNodeKt.makeTypeArgumentsFromType((IrSimpleType) type);
        List<LeafMfvcNode> leaves = rootMfvcNodeInvoke.getLeaves();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(leaves, 10));
        Iterator<T> it = leaves.iterator();
        while (it.hasNext()) {
            arrayList.add(MfvcNodeInstanceKt.savableStandaloneVariable$default(irBlockBuilder, IrTypeUtilsKt.substitute(((LeafMfvcNode) it.next()).getType(), mapMakeTypeArgumentsFromType), null, false, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE, false, new JvmMultiFieldValueClassLowering$flattenExpression$variables$1$1(this), 18, null));
        }
        ArrayList arrayList2 = arrayList;
        ValueDeclarationMfvcNodeInstance valueDeclarationMfvcNodeInstance = new ValueDeclarationMfvcNodeInstance(rootMfvcNodeInvoke, mapMakeTypeArgumentsFromType, arrayList2);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(irBlockBuilder2.getContext(), irBlockBuilder2.getScope(), irBlockBuilder2.getStartOffset(), irBlockBuilder2.getEndOffset(), null, null, false, 64, null);
        flattenExpressionTo(irBlockBuilder3, expression, valueDeclarationMfvcNodeInstance);
        IrContainerExpression irBlockBody = irBlockBuilder3.getIrBlockBody();
        List<IrExpression> listRemoveExtraSetVariablesFromExpressionList = removeExtraSetVariablesFromExpressionList(irBlockBuilder2, irBlockBody, arrayList2);
        if (!irBlockBody.getStatements().isEmpty()) {
            irBlockBuilder.unaryPlus(IrUtilsKt.unwrapBlock(irBlockBody));
        }
        return listRemoveExtraSetVariablesFromExpressionList;
    }

    private final void flattenExpressionTo(final IrBlockBuilder irBlockBuilder, IrExpression irExpression, final MfvcNodeInstance mfvcNodeInstance) {
        boolean z = irExpression instanceof IrConstructorCall;
        RootMfvcNode rootMfvcNodeOrNull = getReplacements().getRootMfvcNodeOrNull(z ? AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) irExpression).getSymbol().getOwner()) : JvmIrTypeUtilsKt.getErasedUpperBound(irExpression.getType()));
        IrSimpleType defaultType = z ? IrUtilsKt.getDefaultType(AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) irExpression).getSymbol().getOwner())) : irExpression.getType();
        if (Intrinsics.areEqual(defaultType, irBlockBuilder.getContext().getIrBuiltIns().getNothingType())) {
            flattenExpressionTo(irBlockBuilder, ExpressionHelpersKt.irImplicitCast(irBlockBuilder, irExpression, mfvcNodeInstance.getType()), mfvcNodeInstance);
            return;
        }
        if (rootMfvcNodeOrNull == null || !MfvcNodeKt.needsMfvcFlattening(defaultType) || MfvcNodeInstanceKt.getSize(mfvcNodeInstance) == 1) {
            if (MfvcNodeInstanceKt.getSize(mfvcNodeInstance) != 1) {
                throw new IllegalArgumentException(("Required 1 variable/field to store regular value but got " + MfvcNodeInstanceKt.getSize(mfvcNodeInstance)).toString());
            }
            MfvcNodeInstanceKt.addSetterStatements(mfvcNodeInstance, irBlockBuilder, CollectionsKt.listOf(irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null)));
            return;
        }
        if (rootMfvcNodeOrNull.getLeavesCount() != MfvcNodeInstanceKt.getSize(mfvcNodeInstance)) {
            throw new IllegalArgumentException(("Required " + rootMfvcNodeOrNull.getLeavesCount() + " variable/field to store regular value but got " + MfvcNodeInstanceKt.getSize(mfvcNodeInstance)).toString());
        }
        if (irExpression instanceof IrWhen) {
            for (IrBranch irBranch : ((IrWhen) irExpression).getBranches()) {
                irBranch.setCondition(irBranch.getCondition().transform((IrElementTransformer<? super Object>) this, (Object) null));
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(irBlockBuilder2.getContext(), irBlockBuilder2.getScope(), irBlockBuilder2.getStartOffset(), irBlockBuilder2.getEndOffset(), null, null, false, 64, null);
                flattenExpressionTo(irBlockBuilder3, irBranch.getResult(), mfvcNodeInstance);
                irBranch.setResult(IrUtilsKt.unwrapBlock(irBlockBuilder3.getIrBlockBody()));
            }
            irBlockBuilder.unaryPlus(irExpression);
            return;
        }
        if (irExpression instanceof IrTry) {
            IrTry irTry = (IrTry) irExpression;
            IrBlockBuilder irBlockBuilder4 = irBlockBuilder;
            IrBlockBuilder irBlockBuilder5 = new IrBlockBuilder(irBlockBuilder4.getContext(), irBlockBuilder4.getScope(), irBlockBuilder4.getStartOffset(), irBlockBuilder4.getEndOffset(), null, null, false, 64, null);
            flattenExpressionTo(irBlockBuilder5, irTry.getTryResult(), mfvcNodeInstance);
            irTry.setTryResult(IrUtilsKt.unwrapBlock(irBlockBuilder5.getIrBlockBody()));
            irTry.getCatches().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return JvmMultiFieldValueClassLowering.flattenExpressionTo$lambda$129(irBlockBuilder, this, mfvcNodeInstance, (IrCatch) obj);
                }
            });
            IrExpression finallyExpression = irTry.getFinallyExpression();
            irTry.setFinallyExpression(finallyExpression != null ? finallyExpression.transform((IrElementTransformer<? super Object>) this, (Object) null) : null);
            irBlockBuilder.unaryPlus(irExpression);
            return;
        }
        if (z) {
            IrConstructorCall irConstructorCall = (IrConstructorCall) irExpression;
            IrConstructor owner = irConstructorCall.getSymbol().getOwner();
            if (owner.getIsPrimary() && IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(owner)) && !Intrinsics.areEqual(owner.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE)) {
                int valueArgumentsCount = irConstructorCall.getValueArgumentsCount();
                ArrayList arrayList = new ArrayList(valueArgumentsCount);
                int i = 0;
                for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
                    IrExpression valueArgument = irConstructorCall.getValueArgument(i2);
                    if (valueArgument != null) {
                        arrayList.add(valueArgument);
                    } else {
                        throw new IllegalStateException(("Default arguments for MFVC primary constructors are not yet supported:\n" + DumpIrTreeKt.dump$default(irExpression, null, 1, null)).toString());
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (rootMfvcNodeOrNull.getSubnodes().size() != arrayList2.size()) {
                    throw new IllegalArgumentException(("Old " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null) + " must have " + rootMfvcNodeOrNull.getSubnodes().size() + " arguments but got " + arrayList2.size()).toString());
                }
                for (Pair pair : CollectionsKt.zip(rootMfvcNodeOrNull.getSubnodes(), arrayList2)) {
                    NameableMfvcNode nameableMfvcNode = (NameableMfvcNode) pair.component1();
                    IrExpression irExpression2 = (IrExpression) pair.component2();
                    MfvcNodeInstance mfvcNodeInstance2 = mfvcNodeInstance.get(MfvcNodeKt.getName(nameableMfvcNode));
                    Intrinsics.checkNotNull(mfvcNodeInstance2);
                    flattenExpressionTo(irBlockBuilder, irExpression2, mfvcNodeInstance2);
                }
                IrSimpleFunction primaryConstructorImpl = rootMfvcNodeOrNull.getPrimaryConstructorImpl();
                MfvcNodeFactoryKt.throwWhenNotExternalIsNull(rootMfvcNodeOrNull, primaryConstructorImpl);
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder, primaryConstructorImpl);
                IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionAccessExpressionIrCall, (IrMemberAccessExpression) irExpression, 0, 2, null);
                Iterator<IrExpression> it = mfvcNodeInstance.makeFlattenedGetterExpressions(irBlockBuilder, getIrCurrentClass(), new JvmMultiFieldValueClassLowering$flattenExpressionTo$8$flattenedGetterExpressions$1(this)).iterator();
                while (it.hasNext()) {
                    irFunctionAccessExpressionIrCall.putValueArgument(i, it.next());
                    i++;
                }
                irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
                return;
            }
        }
        IrBlockBuilder irBlockBuilder6 = irBlockBuilder;
        IrExpression irExpressionCastExpressionToNotNullTypeIfNeeded = castExpressionToNotNullTypeIfNeeded(irBlockBuilder6, irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null), mfvcNodeInstance.getType());
        IrExpression irExpressionHandleFlattenedGetterExpressions = this.valueDeclarationsRemapper.handleFlattenedGetterExpressions(irBlockBuilder6, irExpressionCastExpressionToNotNullTypeIfNeeded, new Function2<IrBlockBuilder, List<? extends IrExpression>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$flattenExpressionTo$addedSettersToFlattened$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrBlockBuilder handleFlattenedGetterExpressions, List<? extends IrExpression> it2) {
                Intrinsics.checkNotNullParameter(handleFlattenedGetterExpressions, "$this$handleFlattenedGetterExpressions");
                Intrinsics.checkNotNullParameter(it2, "it");
                boolean z2 = it2.size() == MfvcNodeInstanceKt.getSize(mfvcNodeInstance);
                MfvcNodeInstance mfvcNodeInstance3 = mfvcNodeInstance;
                if (!z2) {
                    throw new IllegalArgumentException(("Incompatible assignment sizes: " + it2.size() + ", " + MfvcNodeInstanceKt.getSize(mfvcNodeInstance3)).toString());
                }
                return MfvcNodeInstanceKt.makeSetterExpressions(mfvcNodeInstance3, handleFlattenedGetterExpressions, it2);
            }
        });
        if (irExpressionHandleFlattenedGetterExpressions != null) {
            irBlockBuilder.unaryPlus(irExpressionHandleFlattenedGetterExpressions);
            return;
        }
        ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstanceCreateInstanceFromBox = MfvcNodeKt.createInstanceFromBox(rootMfvcNodeOrNull, irBlockBuilder, irExpressionCastExpressionToNotNullTypeIfNeeded, AccessType.ChooseEffective, new JvmMultiFieldValueClassLowering$flattenExpressionTo$expressionInstance$1(this));
        ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstance = receiverBasedMfvcNodeInstanceCreateInstanceFromBox;
        if (MfvcNodeInstanceKt.getSize(receiverBasedMfvcNodeInstance) != MfvcNodeInstanceKt.getSize(mfvcNodeInstance)) {
            throw new IllegalArgumentException(("Incompatible assignment sizes: " + MfvcNodeInstanceKt.getSize(receiverBasedMfvcNodeInstance) + ", " + MfvcNodeInstanceKt.getSize(mfvcNodeInstance)).toString());
        }
        MfvcNodeInstanceKt.addSetterStatements(mfvcNodeInstance, irBlockBuilder, receiverBasedMfvcNodeInstanceCreateInstanceFromBox.makeFlattenedGetterExpressions(irBlockBuilder, getIrCurrentClass(), new AnonymousClass10(this)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrCatch flattenExpressionTo$lambda$129(IrBlockBuilder this_flattenExpressionTo, JvmMultiFieldValueClassLowering this$0, MfvcNodeInstance instance, IrCatch it) {
        Intrinsics.checkNotNullParameter(this_flattenExpressionTo, "$this_flattenExpressionTo");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(instance, "$instance");
        Intrinsics.checkNotNullParameter(it, "it");
        IrBlockBuilder irBlockBuilder = this_flattenExpressionTo;
        IrVariable catchParameter = it.getCatchParameter();
        IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(irBlockBuilder.getContext(), irBlockBuilder.getScope(), irBlockBuilder.getStartOffset(), irBlockBuilder.getEndOffset(), null, null, false, 64, null);
        this$0.flattenExpressionTo(irBlockBuilder2, it.getResult(), instance);
        Unit unit = Unit.INSTANCE;
        return LowerUtilsKt.irCatch(irBlockBuilder, catchParameter, IrUtilsKt.unwrapBlock(irBlockBuilder2.getIrBlockBody()));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$flattenExpressionTo$10, reason: invalid class name */
    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass10 extends FunctionReference implements Function0<Unit> {
        AnonymousClass10(Object obj) {
            super(0, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "registerPossibleExtraBoxUsage()V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "registerPossibleExtraBoxUsage";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JvmMultiFieldValueClassLowering.class);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((JvmMultiFieldValueClassLowering) this.receiver).registerPossibleExtraBoxUsage();
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$removeAllExtraBoxes$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000o\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0082\u0010J\u0018\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J\u0018\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001f2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0018\u0010%\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016¨\u0006'"}, d2 = {"org/jetbrains/kotlin/backend/jvm/lower/JvmMultiFieldValueClassLowering$removeAllExtraBoxes$1", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "", "getFunctionCallOrNull", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "statement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "handleStatementContainer", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "resultIsUsed", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "data", "visitBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "visitCatch", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class C43071 implements IrElementVisitor<Unit, Boolean> {
        public void visitClass(IrClass declaration, boolean data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
        }

        public void visitFunction(IrFunction declaration, boolean data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
        }

        C43071() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Boolean bool) {
            visitAnonymousInitializer(irAnonymousInitializer, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, boolean z) {
            IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, Boolean bool) {
            visitBlock(irBlock, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitBlock(IrBlock irBlock, boolean z) {
            IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, Boolean bool) {
            visitBlockBody(irBlockBody, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, Boolean bool) {
            visitBody(irBody, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitBody(IrBody irBody, boolean z) {
            IrElementVisitor.DefaultImpls.visitBody(this, irBody, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, Boolean bool) {
            visitBranch(irBranch, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, Boolean bool) {
            visitBreak(irBreak, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitBreak(IrBreak irBreak, boolean z) {
            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, Boolean bool) {
            visitBreakContinue(irBreakContinue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitBreakContinue(IrBreakContinue irBreakContinue, boolean z) {
            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, Boolean bool) {
            visitCall(irCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitCall(IrCall irCall, boolean z) {
            IrElementVisitor.DefaultImpls.visitCall(this, irCall, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, Boolean bool) {
            visitCallableReference((IrCallableReference<?>) irCallableReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitCallableReference(IrCallableReference<?> irCallableReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, Boolean bool) {
            visitCatch(irCatch, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, Boolean bool) {
            visitClass(irClass, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, Boolean bool) {
            visitClassReference(irClassReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitClassReference(IrClassReference irClassReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, Boolean bool) {
            visitComposite(irComposite, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitComposite(IrComposite irComposite, boolean z) {
            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, Boolean bool) {
            visitConst((IrConst<?>) irConst, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConst(IrConst<?> irConst, boolean z) {
            IrElementVisitor.DefaultImpls.visitConst(this, irConst, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, Boolean bool) {
            visitConstantArray(irConstantArray, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstantArray(IrConstantArray irConstantArray, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, Boolean bool) {
            visitConstantObject(irConstantObject, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstantObject(IrConstantObject irConstantObject, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Boolean bool) {
            visitConstantPrimitive(irConstantPrimitive, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, Boolean bool) {
            visitConstantValue(irConstantValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstantValue(IrConstantValue irConstantValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, Boolean bool) {
            visitConstructor(irConstructor, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstructor(IrConstructor irConstructor, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, Boolean bool) {
            visitConstructorCall(irConstructorCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitConstructorCall(IrConstructorCall irConstructorCall, boolean z) {
            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, Boolean bool) {
            visitContainerExpression(irContainerExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, Boolean bool) {
            visitContinue(irContinue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitContinue(IrContinue irContinue, boolean z) {
            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, Boolean bool) {
            visitDeclaration(irDeclarationBase, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDeclaration(IrDeclarationBase irDeclarationBase, boolean z) {
            IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Boolean bool) {
            visitDeclarationReference(irDeclarationReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Boolean bool) {
            visitDelegatingConstructorCall(irDelegatingConstructorCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, boolean z) {
            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Boolean bool) {
            visitDoWhileLoop(irDoWhileLoop, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, boolean z) {
            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Boolean bool) {
            visitDynamicExpression(irDynamicExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Boolean bool) {
            visitDynamicMemberExpression(irDynamicMemberExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Boolean bool) {
            visitDynamicOperatorExpression(irDynamicOperatorExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, Boolean bool) {
            visitElement(irElement, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, Boolean bool) {
            visitElseBranch(irElseBranch, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitElseBranch(IrElseBranch irElseBranch, boolean z) {
            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Boolean bool) {
            visitEnumConstructorCall(irEnumConstructorCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, boolean z) {
            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, Boolean bool) {
            visitEnumEntry(irEnumEntry, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitEnumEntry(IrEnumEntry irEnumEntry, boolean z) {
            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Boolean bool) {
            visitErrorCallExpression(irErrorCallExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Boolean bool) {
            visitErrorDeclaration(irErrorDeclaration, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, boolean z) {
            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, Boolean bool) {
            visitErrorExpression(irErrorExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitErrorExpression(IrErrorExpression irErrorExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, Boolean bool) {
            visitExpression(irExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitExpression(IrExpression irExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, Boolean bool) {
            visitExpressionBody(irExpressionBody, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitExpressionBody(IrExpressionBody irExpressionBody, boolean z) {
            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Boolean bool) {
            visitExternalPackageFragment(irExternalPackageFragment, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, boolean z) {
            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, Boolean bool) {
            visitField(irField, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitField(IrField irField, boolean z) {
            IrElementVisitor.DefaultImpls.visitField(this, irField, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Boolean bool) {
            visitFieldAccess(irFieldAccessExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, Boolean bool) {
            visitFile(irFile, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitFile(IrFile irFile, boolean z) {
            IrElementVisitor.DefaultImpls.visitFile(this, irFile, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, Boolean bool) {
            visitFunction(irFunction, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Boolean bool) {
            visitFunctionAccess(irFunctionAccessExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Boolean bool) {
            visitFunctionExpression(irFunctionExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, Boolean bool) {
            visitFunctionReference(irFunctionReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitFunctionReference(IrFunctionReference irFunctionReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, Boolean bool) {
            visitGetClass(irGetClass, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitGetClass(IrGetClass irGetClass, boolean z) {
            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Boolean bool) {
            visitGetEnumValue(irGetEnumValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, Boolean bool) {
            visitGetField(irGetField, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitGetField(IrGetField irGetField, boolean z) {
            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Boolean bool) {
            visitGetObjectValue(irGetObjectValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, Boolean bool) {
            visitGetValue(irGetValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitGetValue(IrGetValue irGetValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Boolean bool) {
            visitInstanceInitializerCall(irInstanceInitializerCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, boolean z) {
            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Boolean bool) {
            visitLocalDelegatedProperty(irLocalDelegatedProperty, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, boolean z) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Boolean bool) {
            visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, Boolean bool) {
            visitLoop(irLoop, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitLoop(IrLoop irLoop, boolean z) {
            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Boolean bool) {
            visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, Boolean bool) {
            visitModuleFragment(irModuleFragment, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitModuleFragment(IrModuleFragment irModuleFragment, boolean z) {
            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, Boolean bool) {
            visitPackageFragment(irPackageFragment, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitPackageFragment(IrPackageFragment irPackageFragment, boolean z) {
            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, Boolean bool) {
            visitProperty(irProperty, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitProperty(IrProperty irProperty, boolean z) {
            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, Boolean bool) {
            visitPropertyReference(irPropertyReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitPropertyReference(IrPropertyReference irPropertyReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Boolean bool) {
            visitRawFunctionReference(irRawFunctionReference, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, boolean z) {
            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, Boolean bool) {
            visitReturn(irReturn, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitReturn(IrReturn irReturn, boolean z) {
            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, Boolean bool) {
            visitScript(irScript, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitScript(IrScript irScript, boolean z) {
            IrElementVisitor.DefaultImpls.visitScript(this, irScript, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, Boolean bool) {
            visitSetField(irSetField, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSetField(IrSetField irSetField, boolean z) {
            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, Boolean bool) {
            visitSetValue(irSetValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSetValue(IrSetValue irSetValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Boolean bool) {
            visitSimpleFunction(irSimpleFunction, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, boolean z) {
            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Boolean bool) {
            visitSingletonReference(irGetSingletonValue, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, boolean z) {
            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, Boolean bool) {
            visitSpreadElement(irSpreadElement, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSpreadElement(IrSpreadElement irSpreadElement, boolean z) {
            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Boolean bool) {
            visitStringConcatenation(irStringConcatenation, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, boolean z) {
            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Boolean bool) {
            visitSuspendableExpression(irSuspendableExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Boolean bool) {
            visitSuspensionPoint(irSuspensionPoint, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, boolean z) {
            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Boolean bool) {
            visitSyntheticBody(irSyntheticBody, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, boolean z) {
            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, Boolean bool) {
            visitThrow(irThrow, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitThrow(IrThrow irThrow, boolean z) {
            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, Boolean bool) {
            visitTry(irTry, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, Boolean bool) {
            visitTypeAlias(irTypeAlias, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitTypeAlias(IrTypeAlias irTypeAlias, boolean z) {
            IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Boolean bool) {
            visitTypeOperator(irTypeOperatorCall, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, Boolean bool) {
            visitTypeParameter(irTypeParameter, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitTypeParameter(IrTypeParameter irTypeParameter, boolean z) {
            IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Boolean bool) {
            visitValueAccess(irValueAccessExpression, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, boolean z) {
            IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, Boolean bool) {
            visitValueParameter(irValueParameter, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitValueParameter(IrValueParameter irValueParameter, boolean z) {
            IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, Boolean bool) {
            visitVararg(irVararg, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitVararg(IrVararg irVararg, boolean z) {
            IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, Boolean bool) {
            visitVariable(irVariable, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitVariable(IrVariable irVariable, boolean z) {
            IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, Boolean.valueOf(z));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, Boolean bool) {
            visitWhen(irWhen, bool.booleanValue());
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, Boolean bool) {
            visitWhileLoop(irWhileLoop, bool.booleanValue());
            return Unit.INSTANCE;
        }

        public void visitWhileLoop(IrWhileLoop irWhileLoop, boolean z) {
            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, Boolean.valueOf(z));
        }

        public void visitElement(IrElement element, boolean data) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this, true);
        }

        public void visitTypeOperator(IrTypeOperatorCall expression, boolean data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.acceptChildren(this, Boolean.valueOf(data));
        }

        private final IrCall getFunctionCallOrNull(IrStatement statement) {
            while (statement instanceof IrTypeOperatorCall) {
                statement = ((IrTypeOperatorCall) statement).getArgument();
            }
            if (statement instanceof IrCall) {
                return (IrCall) statement;
            }
            return null;
        }

        public void visitContainerExpression(IrContainerExpression expression, boolean data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            handleStatementContainer(expression, data);
        }

        public void visitWhen(IrWhen expression, boolean data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.acceptChildren(this, Boolean.valueOf(data));
        }

        public void visitCatch(IrCatch aCatch, boolean data) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            aCatch.acceptChildren(this, Boolean.valueOf(data));
        }

        public void visitTry(IrTry aTry, boolean data) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            C43071 c43071 = this;
            aTry.getTryResult().accept(c43071, Boolean.valueOf(data));
            Iterator<T> it = aTry.getCatches().iterator();
            while (it.hasNext()) {
                ((IrCatch) it.next()).accept(c43071, Boolean.valueOf(data));
            }
            IrExpression finallyExpression = aTry.getFinallyExpression();
            if (finallyExpression != null) {
                finallyExpression.accept(c43071, false);
            }
        }

        public void visitBranch(IrBranch branch, boolean data) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            C43071 c43071 = this;
            branch.getCondition().accept(c43071, true);
            branch.getResult().accept(c43071, Boolean.valueOf(data));
        }

        public void visitBlockBody(IrBlockBody body, boolean data) {
            Intrinsics.checkNotNullParameter(body, "body");
            handleStatementContainer(body, data);
        }

        private final void handleStatementContainer(IrStatementContainer expression, boolean resultIsUsed) {
            RootMfvcNode rootMfvcNodeOrNull;
            Iterator it = JvmMultiFieldValueClassLoweringKt.subListWithoutLast(expression.getStatements(), 1).iterator();
            while (it.hasNext()) {
                ((IrStatement) it.next()).accept(this, false);
            }
            IrStatement irStatement = (IrStatement) CollectionsKt.lastOrNull((List) expression.getStatements());
            if (irStatement != null) {
                irStatement.accept(this, Boolean.valueOf(resultIsUsed));
            }
            final LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (IrStatement irStatement2 : JvmMultiFieldValueClassLoweringKt.subListWithoutLast(expression.getStatements(), resultIsUsed ? 1 : 0)) {
                IrCall functionCallOrNull = getFunctionCallOrNull(irStatement2);
                if (functionCallOrNull != null && (rootMfvcNodeOrNull = JvmMultiFieldValueClassLowering.this.getReplacements().getRootMfvcNodeOrNull(JvmIrTypeUtilsKt.getErasedUpperBound(functionCallOrNull.getType()))) != null && Intrinsics.areEqual(rootMfvcNodeOrNull.getBoxMethod(), functionCallOrNull.getSymbol().getOwner())) {
                    int valueArgumentsCount = functionCallOrNull.getValueArgumentsCount();
                    ArrayList arrayList = new ArrayList(valueArgumentsCount);
                    for (int i = 0; i < valueArgumentsCount; i++) {
                        arrayList.add(functionCallOrNull.getValueArgument(i));
                    }
                    ArrayList arrayList2 = arrayList;
                    if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                        Iterator it2 = arrayList2.iterator();
                        do {
                            if (it2.hasNext()) {
                            }
                        } while (MfvcNodeInstanceKt.isRepeatableGetter((IrExpression) it2.next()));
                    }
                    linkedHashSet.add(irStatement2);
                    break;
                }
            }
            List<IrStatement> statements = expression.getStatements();
            final Function1<IrStatement, Boolean> function1 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$removeAllExtraBoxes$1$handleStatementContainer$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrStatement it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return Boolean.valueOf(linkedHashSet.contains(it3));
                }
            };
            statements.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLowering$removeAllExtraBoxes$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return JvmMultiFieldValueClassLowering.C43071.handleStatementContainer$lambda$3(function1, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean handleStatementContainer$lambda$3(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }
    }

    private final void removeAllExtraBoxes(IrBody irBody) {
        irBody.accept(new C43071(), false);
    }
}
