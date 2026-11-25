package org.jetbrains.kotlin.backend.jvm.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.lower.BOUND_RECEIVER_PARAMETER;
import org.jetbrains.kotlin.backend.common.lower.LoweredStatementOrigins;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadeFileEntry;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadesKt;
import org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod;
import org.jetbrains.kotlin.backend.jvm.intrinsics.JavaClassProperty;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrCallableMethod;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegen;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.StringConcatGenerator;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.SuspensionPointKind;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.NameGenerator;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.inline.TypeParameterMappings;
import org.jetbrains.kotlin.codegen.intrinsics.TypeIntrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.diagnostics.BackendErrors;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
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
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
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
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.ExpandedTypeUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008c\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u008a\u00022\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004:\u0002\u008a\u0002B=\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\u0010\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020fH\u0016J \u0010g\u001a\u00020d2\u0006\u0010h\u001a\u00020\u00032\u0006\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020lH\u0002J\u0010\u0010m\u001a\u00020\"2\u0006\u0010n\u001a\u00020oH\u0002J&\u0010p\u001a\u00020d2\u0006\u0010q\u001a\u00020V2\u0006\u0010r\u001a\u00020R2\u0006\u0010s\u001a\u00020X2\u0006\u0010t\u001a\u00020\u0003J<\u0010u\u001a\u00020d2\u0006\u0010v\u001a\u00020w2\b\u0010x\u001a\u0004\u0018\u00010l2\b\u0010y\u001a\u0004\u0018\u00010l2\u0006\u0010t\u001a\u00020\u00032\u000e\b\u0002\u0010z\u001a\b\u0012\u0004\u0012\u00020|0{H\u0002J.\u0010}\u001a\u00020~2\u0006\u0010q\u001a\u00020V2\u0006\u0010r\u001a\u00020R2\u0006\u0010\u007f\u001a\u00020X2\u0006\u0010t\u001a\u00020\u0003H\u0000¢\u0006\u0003\b\u0080\u0001J'\u0010\u0081\u0001\u001a\u00020~2\u0006\u0010q\u001a\u00020V2\u0006\u0010r\u001a\u00020R2\u0006\u0010s\u001a\u00020X2\u0006\u0010t\u001a\u00020\u0003JL\u0010\u0082\u0001\u001a\u00020d2\u0007\u0010\u0083\u0001\u001a\u00020l2\u0007\u0010\u0084\u0001\u001a\u00020l2\u0007\u0010\u0085\u0001\u001a\u00020l2\u001b\u0010\u0086\u0001\u001a\u0016\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020l\u0012\u0004\u0012\u00020l0\u0088\u00010\u0087\u00012\t\u0010r\u001a\u0005\u0018\u00010\u0089\u0001H\u0002J\u0007\u0010\u008a\u0001\u001a\u00020dJ*\u0010\u008b\u0001\u001a\u00020]2\u0007\u0010\u008c\u0001\u001a\u00020V2\u0007\u0010\u008d\u0001\u001a\u00020/2\u0007\u0010\u008e\u0001\u001a\u00020/2\u0006\u0010t\u001a\u00020\u0003J\"\u0010\u008f\u0001\u001a\u00020d2\u0007\u0010q\u001a\u00030\u0090\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010t\u001a\u00020\u0003H\u0002J\t\u0010\u0091\u0001\u001a\u00020dH\u0002J,\u0010\u0092\u0001\u001a\u00020d2\u0007\u0010\u0093\u0001\u001a\u00020R2\u0007\u0010\u0094\u0001\u001a\u00020l2\u0006\u0010t\u001a\u00020\u00032\t\u0010\u0095\u0001\u001a\u0004\u0018\u00010lJ\u001b\u0010\u0096\u0001\u001a\u00020d2\u0006\u0010q\u001a\u00020V2\b\u0010\u0097\u0001\u001a\u00030\u0089\u0001H\u0002J\u0013\u0010\u0098\u0001\u001a\u00020d2\b\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0002J\t\u0010\u009b\u0001\u001a\u00020dH\u0002J\u0013\u0010\u009c\u0001\u001a\u00020d2\b\u0010\u009d\u0001\u001a\u00030\u0089\u0001H\u0002J$\u0010\u009e\u0001\u001a\u00030\u009f\u00012\b\u0010 \u0001\u001a\u00030\u0090\u00012\u0006\u0010t\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001d\u0010¡\u0001\u001a\u00020/2\u0006\u0010r\u001a\u00020R2\n\u0010\u009d\u0001\u001a\u0005\u0018\u00010¢\u0001H\u0002J\u0012\u0010£\u0001\u001a\u00020d2\u0007\u0010¤\u0001\u001a\u00020/H\u0016J\t\u0010¥\u0001\u001a\u00020lH\u0002J\t\u0010¦\u0001\u001a\u00020lH\u0002J\u0017\u0010§\u0001\u001a\u00020d2\u000e\u0010¨\u0001\u001a\t\u0012\u0004\u0012\u00020d0©\u0001J\u0011\u0010ª\u0001\u001a\u00020d2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010«\u0001\u001a\u00020d2\u0007\u0010¬\u0001\u001a\u00020\u0018H\u0002J\u0011\u0010\u00ad\u0001\u001a\u00020d2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0011\u0010®\u0001\u001a\u00020/2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J,\u0010¯\u0001\u001a\u00020d2\u0007\u0010°\u0001\u001a\u00020\u00032\u0006\u0010v\u001a\u00020w2\u0007\u0010±\u0001\u001a\u00020l2\u0007\u0010²\u0001\u001a\u00020lH\u0002J\n\u0010³\u0001\u001a\u00030\u0089\u0001H\u0016JD\u0010´\u0001\u001a\u0005\u0018\u00010µ\u00012\u0007\u0010¶\u0001\u001a\u00020l2\u0006\u0010t\u001a\u00020\u00032\u000e\b\u0002\u0010z\u001a\b\u0012\u0004\u0012\u00020|0{2\u0015\u0010·\u0001\u001a\u0010\u0012\u0005\u0012\u00030µ\u0001\u0012\u0004\u0012\u00020/0¸\u0001H\u0002J\u001a\u0010¹\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030º\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010»\u0001\u001a\u00020\u00022\b\u0010¼\u0001\u001a\u00030½\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010¾\u0001\u001a\u00020\u00022\b\u0010¿\u0001\u001a\u00030À\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Á\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Â\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ã\u0001\u001a\u00020\u00022\u0007\u0010¬\u0001\u001a\u00020\u00182\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ä\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Å\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001e\u0010Æ\u0001\u001a\u00020\u00022\u000b\u0010q\u001a\u0007\u0012\u0002\b\u00030Ç\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010È\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030É\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ê\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Ë\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ì\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Í\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u0019\u0010Î\u0001\u001a\u00020\u00022\u0006\u0010i\u001a\u00020j2\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001c\u0010Ï\u0001\u001a\u00030Ð\u00012\b\u0010 \u0001\u001a\u00030Ñ\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010Ò\u0001\u001a\u00020\u00022\b\u0010¼\u0001\u001a\u00030Ó\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ô\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Õ\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ö\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030×\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010Ø\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Ù\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010Ú\u0001\u001a\u00020\u00022\b\u0010Û\u0001\u001a\u00030Ü\u00012\u0006\u0010t\u001a\u00020\u0003H\u0002J\u001a\u0010Ý\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030Þ\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010ß\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030à\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010á\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030â\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010ã\u0001\u001a\u00020\u00022\b\u0010ä\u0001\u001a\u00030å\u00012\u0006\u0010t\u001a\u00020\u0003H\u0002J\u001a\u0010æ\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030ç\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010è\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030é\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010ê\u0001\u001a\u00020\u00022\b\u0010ë\u0001\u001a\u00030ì\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J$\u0010í\u0001\u001a\u00020\u00022\b\u0010ë\u0001\u001a\u00030ì\u00012\u0006\u0010t\u001a\u00020\u00032\u0007\u0010î\u0001\u001a\u00020|H\u0002J\u001a\u0010ï\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030ð\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010ñ\u0001\u001a\u00020\u00022\b\u0010¬\u0001\u001a\u00030ò\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010ó\u0001\u001a\u00020\u00022\u0007\u0010q\u001a\u00030ô\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001a\u0010õ\u0001\u001a\u00020\u00022\u0007\u0010i\u001a\u00030ö\u00012\u0006\u0010t\u001a\u00020\u0003H\u0016J\u001b\u0010÷\u0001\u001a\u00020d2\u0007\u0010°\u0001\u001a\u00020\u00032\u0007\u0010¶\u0001\u001a\u00020lH\u0002J\u001b\u0010ø\u0001\u001a\u00020d2\u0007\u0010ù\u0001\u001a\u00020l2\u0007\u0010¶\u0001\u001a\u00020lH\u0002J.\u0010ú\u0001\u001a\u00020d2\b\u0010\u0099\u0001\u001a\u00030\u009a\u00012\u0007\u0010ù\u0001\u001a\u00020l2\u0007\u0010¶\u0001\u001a\u00020l2\u0007\u0010û\u0001\u001a\u00020/H\u0002J\u0017\u0010ü\u0001\u001a\u00030ý\u0001*\u00030Ü\u00012\u0006\u0010t\u001a\u00020\u0003H\u0002J\u000e\u0010þ\u0001\u001a\u00030Ñ\u0001*\u00020VH\u0002J\u0010\u0010ÿ\u0001\u001a\u0004\u0018\u00010\u0018*\u00030\u0080\u0002H\u0002J\u000f\u0010\u0081\u0002\u001a\u00030\u0082\u0002*\u00030\u0090\u0001H\u0002J\u0015\u0010\u0083\u0002\u001a\u00020d*\u00030Ñ\u00012\u0007\u0010\u0084\u0002\u001a\u00020/J!\u0010\u0085\u0002\u001a\u0004\u0018\u00010/*\u00030\u0086\u00022\t\u0010\u0087\u0002\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0003\u0010\u0088\u0002J\u001a\u0010\u0089\u0002\u001a\u000f\u0012\u0004\u0012\u00020R\u0012\u0004\u0012\u00020X0\u0088\u0001*\u00020\u0006H\u0002R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R*\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00120\u0017j\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0012`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010.\u001a\u00020/8F¢\u0006\u0006\u001a\u0004\b.\u00100R\u001e\u00102\u001a\u00020/2\u0006\u00101\u001a\u00020/@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b2\u00100R\u0014\u00103\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010$R\u000e\u00105\u001a\u000206X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u00107\u001a\u000208¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010C\u001a\u00020D¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0011\u0010G\u001a\u00020H¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020L8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0014\u0010O\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010<R\u0015\u0010Q\u001a\u00020R*\u00020S8F¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0015\u0010Q\u001a\u00020R*\u00020V8F¢\u0006\u0006\u001a\u0004\bT\u0010WR\u0018\u0010Q\u001a\u00020R*\u00020X8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bT\u0010YR\u0018\u0010Z\u001a\u00020/*\u00020S8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0015\u0010\\\u001a\u00020]*\u00020V8F¢\u0006\u0006\u001a\u0004\b^\u0010_R\u0015\u0010`\u001a\u00020X*\u00020S8F¢\u0006\u0006\u001a\u0004\ba\u0010b¨\u0006\u008b\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "frameMap", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "smap", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;)V", "getClassCodegen", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "closureReifiedMarkers", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lkotlin/collections/HashMap;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "enclosingFunctionForLocalObjects", "getEnclosingFunctionForLocalObjects", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "finallyDepth", "", "getFinallyDepth", "()I", "setFinallyDepth", "(I)V", "getFrameMap", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;", "inlineNameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getInlineNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getIrFunction", "isFinallyMarkerRequired", "", "()Z", "<set-?>", "isInsideCondition", "lastLineNumber", "getLastLineNumber", "lineNumberMapper", "Lorg/jetbrains/kotlin/backend/jvm/codegen/LineNumberMapper;", "methodSignatureMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "getMethodSignatureMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "getMv", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getReifiedTypeParametersUsages", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getSignature", "()Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "getSmap", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "getTypeMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "visitor", "getVisitor", "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getAsmType", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/org/objectweb/asm/Type;", "isVisibleInLVT", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Z", "onStack", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "getOnStack", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "realType", "getRealType", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Lorg/jetbrains/kotlin/ir/types/IrType;", "consumeReifiedOperationMarker", "", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "endUnreferencedDoWhileLocals", "blockInfo", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "continueLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "findLocalIndex", "irSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "gen", "expression", ModuleXmlParser.TYPE, "irType", "data", "genFinallyBlock", "tryWithFinallyInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "tryCatchBlockEnd", "afterJumpLabel", "nestedTryWithoutFinally", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryInfo;", "genOrGetLocal", "Lorg/jetbrains/kotlin/codegen/StackValue;", "parameterType", "genOrGetLocal$backend_jvm_codegen", "genToStackValue", "genTryCatchCover", "catchStart", "tryStart", "tryEnd", "tryGaps", "", "Lkotlin/Pair;", "", CompilerOptions.GENERATE, "generateClassLiteralReference", "classReference", "wrapIntoKClass", "wrapPrimitives", "generateConstructorArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "generateFakeContinuationConstructorIfNeeded", "generateFinallyBlocksIfNeeded", "returnType", "afterReturnLabel", "jumpLabel", "generateGlobalReturnFlagIfPossible", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "generateNonNullAssertion", "param", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "generateNonNullAssertions", "generateStringConstant", "value", "getOrCreateCallGenerator", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrCallGenerator;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "isDefaultValueForType", "", "markLineNumberAfterInlineIfNeeded", "registerLineNumberAfterwards", "markNewLabel", "markNewLinkedLabel", "noLineNumberScope", "block", "Lkotlin/Function0;", "propagateChildReifiedTypeParametersUsages", "putNeedClassReificationMarker", "declaration", "setExtraLineNumberForVoidReturningFunction", "shouldGenerateNonNullAssertionsForPrivateFun", "splitLocalVariableRangesByFinallyBlocks", CompilerOptions.INFO, "gapStart", "restartLabel", "toString", "unwindBlockStack", "Lorg/jetbrains/kotlin/backend/jvm/codegen/LoopInfo;", "endLabel", "stop", "Lkotlin/Function1;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBreakContinue", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClass", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "visitElement", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitFieldAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInlinedFunctionBlock", "inlinedBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStatementContainer", "container", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTryWithInfo", "tryInfo", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "writeLocalVariablesInTable", "writeParameterInLocalVariableTable", "startLabel", "writeValueParameterInLocalVariableTable", "isReceiver", "buildOrGetClassSMAP", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "firstChild", "getClassWithDeclaredFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getSuspensionPointKind", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPointKind;", "markLineNumber", "startOffset", "resultIsActuallyAny", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "index", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Ljava/lang/Integer;)Ljava/lang/Boolean;", "returnAsmAndIrTypes", "Companion", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionCodegen implements BaseExpressionCodegen, IrElementVisitor<PromisedValue, BlockInfo> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ClassCodegen classCodegen;
    private final HashMap<IrClass, ReifiedTypeParametersUsages> closureReifiedMarkers;
    private final JvmBackendContext context;
    private int finallyDepth;
    private final IrFrameMap frameMap;
    private final NameGenerator inlineNameGenerator;
    private final IrFunction irFunction;
    private boolean isInsideCondition;
    private final LineNumberMapper lineNumberMapper;
    private final MethodSignatureMapper methodSignatureMapper;
    private final InstructionAdapter mv;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages;
    private final JvmMethodSignature signature;
    private final SourceMapper smap;
    private final GenerationState state;
    private final IrTypeMapper typeMapper;

    /* JADX INFO: compiled from: ExpressionCodegen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IrTypeOperator.INSTANCEOF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ExpressionCodegen(IrFunction irFunction, JvmMethodSignature signature, IrFrameMap frameMap, InstructionAdapter mv, ClassCodegen classCodegen, SourceMapper smap, ReifiedTypeParametersUsages reifiedTypeParametersUsages) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        Intrinsics.checkNotNullParameter(mv, "mv");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        Intrinsics.checkNotNullParameter(smap, "smap");
        Intrinsics.checkNotNullParameter(reifiedTypeParametersUsages, "reifiedTypeParametersUsages");
        this.irFunction = irFunction;
        this.signature = signature;
        this.frameMap = frameMap;
        this.mv = mv;
        this.classCodegen = classCodegen;
        this.smap = smap;
        this.reifiedTypeParametersUsages = reifiedTypeParametersUsages;
        JvmBackendContext context = classCodegen.getContext();
        this.context = context;
        this.typeMapper = classCodegen.getTypeMapper();
        this.methodSignatureMapper = classCodegen.getMethodSignatureMapper();
        this.state = context.getState();
        this.inlineNameGenerator = classCodegen.getRegeneratedObjectNameGenerator(irFunction);
        this.lineNumberMapper = new LineNumberMapper(this);
        this.closureReifiedMarkers = new HashMap<>();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public PromisedValue visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public PromisedValue visitBody2(IrBody irBody, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitBody(this, irBody, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public PromisedValue visitBranch2(IrBranch irBranch, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public PromisedValue visitBreak2(IrBreak irBreak, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ PromisedValue visitCallableReference(IrCallableReference irCallableReference, BlockInfo blockInfo) {
        return visitCallableReference2((IrCallableReference<?>) irCallableReference, blockInfo);
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitCallableReference2(IrCallableReference<?> irCallableReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public PromisedValue visitCatch2(IrCatch irCatch, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public PromisedValue visitComposite2(IrComposite irComposite, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ PromisedValue visitConst(IrConst irConst, BlockInfo blockInfo) {
        return visitConst2((IrConst<?>) irConst, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstantArray2(IrConstantArray irConstantArray, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstantObject2(IrConstantObject irConstantObject, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstantValue2(IrConstantValue irConstantValue, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstructor2(IrConstructor irConstructor, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public PromisedValue visitContinue2(IrContinue irContinue, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public PromisedValue visitDeclaration2(IrDeclarationBase irDeclarationBase, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitDeclarationReference2(IrDeclarationReference irDeclarationReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitDynamicExpression2(IrDynamicExpression irDynamicExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public PromisedValue visitElseBranch2(IrElseBranch irElseBranch, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public PromisedValue visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public PromisedValue visitEnumEntry2(IrEnumEntry irEnumEntry, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public PromisedValue visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitErrorExpression2(IrErrorExpression irErrorExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitExpression2(IrExpression irExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public PromisedValue visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public PromisedValue visitField2(IrField irField, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitField(this, irField, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public PromisedValue visitFile2(IrFile irFile, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitFile(this, irFile, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public PromisedValue visitFunction2(IrFunction irFunction, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public PromisedValue visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitFunctionExpression2(IrFunctionExpression irFunctionExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitFunctionReference2(IrFunctionReference irFunctionReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public PromisedValue visitGetEnumValue2(IrGetEnumValue irGetEnumValue, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public PromisedValue visitGetField2(IrGetField irGetField, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public PromisedValue visitGetObjectValue2(IrGetObjectValue irGetObjectValue, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public PromisedValue visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public PromisedValue visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public PromisedValue visitLoop2(IrLoop irLoop, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ PromisedValue visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, BlockInfo blockInfo) {
        return visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, blockInfo);
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public PromisedValue visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public PromisedValue visitModuleFragment2(IrModuleFragment irModuleFragment, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public PromisedValue visitPackageFragment2(IrPackageFragment irPackageFragment, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public PromisedValue visitProperty2(IrProperty irProperty, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitPropertyReference2(IrPropertyReference irPropertyReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public PromisedValue visitScript2(IrScript irScript, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitScript(this, irScript, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public PromisedValue visitSimpleFunction2(IrSimpleFunction irSimpleFunction, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public PromisedValue visitSpreadElement2(IrSpreadElement irSpreadElement, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public PromisedValue visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public PromisedValue visitSyntheticBody2(IrSyntheticBody irSyntheticBody, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public PromisedValue visitTypeAlias2(IrTypeAlias irTypeAlias, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public PromisedValue visitTypeParameter2(IrTypeParameter irTypeParameter, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public PromisedValue visitValueAccess2(IrValueAccessExpression irValueAccessExpression, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public PromisedValue visitValueParameter2(IrValueParameter irValueParameter, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, blockInfo);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public PromisedValue visitVararg2(IrVararg irVararg, BlockInfo blockInfo) {
        return (PromisedValue) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, blockInfo);
    }

    public final IrFunction getIrFunction() {
        return this.irFunction;
    }

    public final JvmMethodSignature getSignature() {
        return this.signature;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public IrFrameMap getFrameMap() {
        return this.frameMap;
    }

    public final InstructionAdapter getMv() {
        return this.mv;
    }

    public final ClassCodegen getClassCodegen() {
        return this.classCodegen;
    }

    public final SourceMapper getSmap() {
        return this.smap;
    }

    public final ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        return this.reifiedTypeParametersUsages;
    }

    public String toString() {
        String string = this.signature.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final int getFinallyDepth() {
        return this.finallyDepth;
    }

    public final void setFinallyDepth(int i) {
        this.finallyDepth = i;
    }

    public final IrFunction getEnclosingFunctionForLocalObjects() {
        return (IrFunction) SequencesKt.last(SequencesKt.generateSequence(this.irFunction, new Function1<IrFunction, IrFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$enclosingFunctionForLocalObjects$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrFunction invoke(IrFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getContext().getEnclosingMethodOverride().get(it);
            }
        }));
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public final IrTypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    public final MethodSignatureMapper getMethodSignatureMapper() {
        return this.methodSignatureMapper;
    }

    public final GenerationState getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public InstructionAdapter getVisitor() {
        return this.mv;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public NameGenerator getInlineNameGenerator() {
        return this.inlineNameGenerator;
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public TypeSystemCommonBackendContext getTypeSystem() {
        return this.typeMapper.getTypeSystem();
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public int getLastLineNumber() {
        return this.lineNumberMapper.getLastLineNumber();
    }

    /* JADX INFO: renamed from: isInsideCondition, reason: from getter */
    public final boolean getIsInsideCondition() {
        return this.isInsideCondition;
    }

    private final Type getAsmType(IrType irType) {
        return IrTypeMapper.mapType$default(this.typeMapper, irType, null, null, false, 14, null);
    }

    public final Type getAsmType(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return getAsmType(irExpression.getType());
    }

    public final Type getAsmType(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        return getAsmType(irValueDeclaration.getType());
    }

    public final MaterialValue getOnStack(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return new MaterialValue(this, getAsmType(irExpression), irExpression.getType());
    }

    private final Label markNewLabel() {
        Label label = new Label();
        this.mv.visitLabel(label);
        return label;
    }

    private final Label markNewLinkedLabel() {
        Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
        this.mv.visitLabel(labelLinkedLabel);
        return labelLinkedLabel;
    }

    public final void markLineNumber(IrElement irElement, boolean z) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        this.lineNumberMapper.markLineNumber(irElement, z);
    }

    public final void noLineNumberScope(Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.lineNumberMapper.noLineNumberScope(block);
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void markLineNumberAfterInlineIfNeeded(boolean registerLineNumberAfterwards) {
        this.lineNumberMapper.markLineNumberAfterInlineIfNeeded(registerLineNumberAfterwards);
    }

    public final void gen(IrExpression expression, Type type, IrType irType, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(data, "data");
        PromisedValueKt.materializeAt((PromisedValue) expression.accept(this, data), type, irType);
    }

    public final StackValue genToStackValue(IrExpression expression, Type type, IrType irType, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(data, "data");
        gen(expression, type, irType, data);
        StackValue stackValueOnStack = StackValue.onStack(type, IrBasedDescriptorsKt.toIrBasedKotlinType$default(irType, null, 1, null));
        Intrinsics.checkNotNullExpressionValue(stackValueOnStack, "onStack(...)");
        return stackValueOnStack;
    }

    public final void generate() {
        this.mv.visitCode();
        Label labelMarkNewLabel = markNewLabel();
        BlockInfo blockInfo = new BlockInfo(null, 1, null);
        if (this.context.getState().getClassBuilderMode().generateBodies) {
            if (MultifileFacadesKt.isMultifileBridge(this.irFunction)) {
                this.mv.visitLineNumber(1, labelMarkNewLabel);
            }
            IrElement body = this.irFunction.getBody();
            if (body == null) {
                throw new IllegalStateException(("Function has no body: " + RenderIrElementKt.render$default(this.irFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            generateNonNullAssertions();
            generateFakeContinuationConstructorIfNeeded();
            PromisedValue promisedValue = (PromisedValue) body.accept(this, blockInfo);
            setExtraLineNumberForVoidReturningFunction(this.irFunction);
            if (!(body instanceof IrStatementContainer) || !(CollectionsKt.lastOrNull((List) ((IrStatementContainer) body).getStatements()) instanceof IrReturn)) {
                Pair<Type, IrType> pairReturnAsmAndIrTypes = returnAsmAndIrTypes(this.irFunction);
                Type typeComponent1 = pairReturnAsmAndIrTypes.component1();
                PromisedValueKt.materializeAt(promisedValue, typeComponent1, pairReturnAsmAndIrTypes.component2());
                this.mv.areturn(typeComponent1);
            }
        } else {
            this.mv.aconst(null);
            this.mv.athrow();
        }
        Label labelMarkNewLabel2 = markNewLabel();
        writeLocalVariablesInTable(blockInfo, labelMarkNewLabel2);
        writeParameterInLocalVariableTable(labelMarkNewLabel, labelMarkNewLabel2);
    }

    private final void setExtraLineNumberForVoidReturningFunction(IrFunction irFunction) {
        IrElement body = irFunction.getBody();
        if (body == null) {
            return;
        }
        if (((body instanceof IrStatementContainer) && (CollectionsKt.lastOrNull((List) ((IrStatementContainer) body).getStatements()) instanceof IrReturn)) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE)) {
            return;
        }
        markLineNumber(irFunction, (irFunction instanceof IrConstructor) && ((IrConstructor) irFunction).getIsPrimary());
        this.mv.nop();
    }

    private final void generateFakeContinuationConstructorIfNeeded() {
        IrClass irClassContinuationClass;
        if (CoroutineCodegenKt.isSuspendCapturingCrossinline(this.irFunction) && (irClassContinuationClass = CoroutineCodegenKt.continuationClass(this.irFunction)) != null) {
            Type typeMapClass = IrTypeMappingKt.mapClass(this.typeMapper, irClassContinuationClass);
            IrFrameMap frameMap = getFrameMap();
            IrValueParameter irValueParameterContinuationParameter = JvmIrCoroutineUtilsKt.continuationParameter(this.irFunction);
            Intrinsics.checkNotNull(irValueParameterContinuationParameter);
            int index = frameMap.getIndex(irValueParameterContinuationParameter.getSymbol());
            InstructionAdapter instructionAdapter = this.mv;
            InlineCodegenUtilsKt.addFakeContinuationConstructorCallMarker(instructionAdapter, true);
            instructionAdapter.anew(typeMapClass);
            instructionAdapter.dup();
            if (this.irFunction.getDispatchReceiverParameter() != null) {
                instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
                instructionAdapter.load(index, Type.getObjectType("kotlin/coroutines/Continuation"));
                instructionAdapter.invokespecial(typeMapClass.getInternalName(), "<init>", "(" + this.classCodegen.getType() + "Lkotlin/coroutines/Continuation;)V", false);
            } else {
                instructionAdapter.load(index, Type.getObjectType("kotlin/coroutines/Continuation"));
                instructionAdapter.invokespecial(typeMapClass.getInternalName(), "<init>", "(Lkotlin/coroutines/Continuation;)V", false);
            }
            InlineCodegenUtilsKt.addFakeContinuationConstructorCallMarker(instructionAdapter, false);
            instructionAdapter.pop();
        }
    }

    private final void generateNonNullAssertions() {
        if (this.state.getConfig().getIsParamAssertionsDisabled()) {
            return;
        }
        if ((DescriptorVisibilities.isPrivate(this.irFunction.getVisibility()) && !shouldGenerateNonNullAssertionsForPrivateFun(this.irFunction)) || this.irFunction.getOrigin().getIsSynthetic() || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE)) {
            return;
        }
        IrFunction irFunction = this.irFunction;
        if (((irFunction instanceof IrConstructor) && IrUtilsKt.isAnonymousObject(IrUtilsKt.getParentAsClass(irFunction))) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.JVM_STATIC_WRAPPER.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE) || Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE) || Intrinsics.areEqual(IrUtilsKt.getParentAsClass(this.irFunction).getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE) || Intrinsics.areEqual(IrUtilsKt.getParentAsClass(this.irFunction).getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_LAMBDA.INSTANCE) || MultifileFacadesKt.isMultifileBridge(this.irFunction) || AdditionalIrUtilsKt.isSuspend(this.irFunction)) {
            return;
        }
        IrValueParameter extensionReceiverParameter = this.irFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            generateNonNullAssertion(extensionReceiverParameter);
        }
        if (DescriptorVisibilities.isPrivate(this.irFunction.getVisibility())) {
            IrFunction irFunction2 = this.irFunction;
            if ((irFunction2 instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction2).getIsOperator()) {
                return;
            }
        }
        Iterator<T> it = this.irFunction.getValueParameters().iterator();
        while (it.hasNext()) {
            generateNonNullAssertion((IrValueParameter) it.next());
        }
    }

    private final boolean shouldGenerateNonNullAssertionsForPrivateFun(IrFunction irFunction) {
        return ((irFunction instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction).getIsOperator()) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE) || this.context.getHiddenConstructorsWithMangledParams().containsKey(irFunction);
    }

    private final void generateNonNullAssertion(IrValueParameter param) {
        IrType type;
        if (Intrinsics.areEqual(param.getOrigin(), JvmLoweredDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE) || Intrinsics.areEqual(param.getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE)) {
            return;
        }
        Type asmType = getAsmType(param.getType());
        if (JvmIrTypeUtilsKt.isInlineClassType(param.getType())) {
            KotlinTypeMarker kotlinTypeMarkerComputeExpandedTypeForInlineClass = ExpandedTypeUtilsKt.computeExpandedTypeForInlineClass(this.context.getTypeSystem(), param.getType());
            type = kotlinTypeMarkerComputeExpandedTypeForInlineClass instanceof IrType ? (IrType) kotlinTypeMarkerComputeExpandedTypeForInlineClass : null;
            if (type == null) {
                type = param.getType();
            }
        } else {
            type = param.getType();
        }
        if (IrTypeUtilsKt.isNullable(type) || AsmUtil.isPrimitive(asmType)) {
            return;
        }
        this.mv.load(findLocalIndex(param.getSymbol()), asmType);
        this.mv.aconst(param.getName().asString());
        this.mv.invokestatic("kotlin/jvm/internal/Intrinsics", this.state.getConfig().getUnifiedNullChecks() ? "checkNotNullParameter" : "checkParameterIsNotNull", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
    }

    private final void writeParameterInLocalVariableTable(Label startLabel, Label endLabel) {
        if (this.irFunction.getIsInline() || !Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            if (!IrCodegenUtilsKt.isStatic(this.irFunction)) {
                this.mv.visitLocalVariable("this", this.classCodegen.getType().getDescriptor(), null, startLabel, endLabel, 0);
            }
            IrValueParameter extensionReceiverParameter = this.irFunction.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                writeValueParameterInLocalVariableTable(extensionReceiverParameter, startLabel, endLabel, true);
            }
            for (IrValueParameter irValueParameter : this.irFunction.getValueParameters()) {
                if (!Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MASK_FOR_DEFAULT_FUNCTION.INSTANCE) && !Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.METHOD_HANDLER_IN_DEFAULT_FUNCTION.INSTANCE)) {
                    writeValueParameterInLocalVariableTable(irValueParameter, startLabel, endLabel, false);
                }
            }
        }
    }

    private final void writeValueParameterInLocalVariableTable(IrValueParameter param, Label startLabel, Label endLabel, boolean isReceiver) {
        String nameForReceiverParameter;
        if (isVisibleInLVT(param)) {
            if (Intrinsics.areEqual(param.getOrigin(), BOUND_RECEIVER_PARAMETER.INSTANCE) || isReceiver) {
                nameForReceiverParameter = DescriptorAsmUtil.getNameForReceiverParameter(IrBasedDescriptorsKt.toIrBasedDescriptor(this.irFunction), this.state.getBindingContext(), CommonConfigurationKeysKt.getLanguageVersionSettings(this.context.getConfiguration()));
            } else {
                nameForReceiverParameter = param.getName().asString();
            }
            String str = nameForReceiverParameter;
            Intrinsics.checkNotNull(str);
            this.mv.visitLocalVariable(str, IrTypeMappingKt.mapType(this.typeMapper, param).getDescriptor(), null, startLabel, endLabel, findLocalIndex(param.getSymbol()));
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public PromisedValue visitBlock2(IrBlock expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = expression instanceof IrReturnableBlock;
        boolean zAreEqual = Intrinsics.areEqual(expression.getOrigin(), LoweredStatementOrigins.SYNTHESIZED_INIT_BLOCK.INSTANCE);
        if (zAreEqual) {
            markLineNumber(expression, true);
            this.mv.nop();
        }
        if (IrExpressionsKt.isTransparentScope(expression)) {
            return (PromisedValue) IrElementVisitor.DefaultImpls.visitBlock(this, expression, data);
        }
        BlockInfo blockInfo = new BlockInfo(data);
        MaterialValue materialValueMaterialized = PromisedValueKt.materialized((PromisedValue) IrElementVisitor.DefaultImpls.visitBlock(this, expression, blockInfo));
        if (!blockInfo.getVariables().isEmpty()) {
            writeLocalVariablesInTable(blockInfo, markNewLabel());
        }
        if (expression instanceof IrInlinedFunctionBlock) {
            this.lineNumberMapper.afterIrInline((IrInlinedFunctionBlock) expression);
        }
        if (zAreEqual) {
            markLineNumber(expression, false);
            this.mv.nop();
        }
        return materialValueMaterialized;
    }

    private final boolean isVisibleInLVT(IrValueDeclaration irValueDeclaration) {
        return (Intrinsics.areEqual(irValueDeclaration.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || Intrinsics.areEqual(irValueDeclaration.getOrigin(), IrDeclarationOrigin.FOR_LOOP_ITERATOR.INSTANCE) || Intrinsics.areEqual(irValueDeclaration.getOrigin(), IrDeclarationOrigin.UNDERSCORE_PARAMETER.INSTANCE) || Intrinsics.areEqual(irValueDeclaration.getOrigin(), IrDeclarationOrigin.DESTRUCTURED_OBJECT_PARAMETER.INSTANCE) || Intrinsics.areEqual(irValueDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE) || Intrinsics.areEqual(irValueDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER.INSTANCE)) ? false : true;
    }

    private final void writeLocalVariablesInTable(BlockInfo info, Label endLabel) {
        for (VariableInfo variableInfo : info.getVariables()) {
            if (isVisibleInLVT(variableInfo.getDeclaration())) {
                Label startLabel = variableInfo.getStartLabel();
                Label end = startLabel;
                for (Gap gap : variableInfo.getGaps()) {
                    this.mv.visitLocalVariable(variableInfo.getDeclaration().getName().asString(), variableInfo.getType().getDescriptor(), null, end, gap.getStart(), variableInfo.getIndex());
                    end = gap.getEnd();
                }
                Label explicitEndLabel = variableInfo.getExplicitEndLabel();
                this.mv.visitLocalVariable(variableInfo.getDeclaration().getName().asString(), variableInfo.getType().getDescriptor(), null, end, explicitEndLabel == null ? endLabel : explicitEndLabel, variableInfo.getIndex());
            }
        }
        Iterator it = CollectionsKt.reversed(info.getVariables()).iterator();
        while (it.hasNext()) {
            getFrameMap().leave((IrSymbol) ((VariableInfo) it.next()).getDeclaration().getSymbol());
        }
    }

    private final PromisedValue visitInlinedFunctionBlock(IrInlinedFunctionBlock inlinedBlock, BlockInfo data) {
        IrAttributeContainer attributeOwnerId;
        IrBody body;
        List<IrStatement> statements;
        IrFunctionAccessExpression inlineCall = inlinedBlock.getInlineCall();
        IrDeclaration inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(inlinedBlock);
        IrFunction irFunction = inlineDeclaration instanceof IrFunction ? (IrFunction) inlineDeclaration : null;
        Iterator<T> it = org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.getNonDefaultAdditionalStatementsFromInlinedBlock(inlinedBlock).iterator();
        while (it.hasNext()) {
            ((PromisedValue) ((IrStatement) it.next()).accept(this, data)).discard();
        }
        this.lineNumberMapper.beforeIrInline(inlinedBlock);
        LineNumberMapper lineNumberMapper = this.lineNumberMapper;
        boolean zIsInlineOnly = JvmIrInlineUtilsKt.isInlineOnly(IrInlineUtilsKt.getInlineDeclaration(inlinedBlock));
        boolean unused = lineNumberMapper.noLineNumberScope;
        lineNumberMapper.noLineNumberScope = lineNumberMapper.noLineNumberScope || zIsInlineOnly;
        markLineNumber(inlineCall, true);
        this.mv.nop();
        this.lineNumberMapper.buildSmapFor(inlinedBlock, buildOrGetClassSMAP(inlinedBlock, data), data);
        if (IrUtilsKt.usesDefaultArguments(inlineCall)) {
            if (irFunction != null) {
                markLineNumber(irFunction, true);
            }
            this.mv.nop();
        }
        Iterator<T> it2 = org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.getDefaultAdditionalStatementsFromInlinedBlock(inlinedBlock).iterator();
        while (it2.hasNext()) {
            ((PromisedValue) ((IrStatement) it2.next()).accept(this, data)).discard();
        }
        if (IrUtilsKt.usesDefaultArguments(inlineCall)) {
            this.lineNumberMapper.resetLineNumber();
        }
        List<IrStatement> originalStatementsFromInlinedBlock = org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.getOriginalStatementsFromInlinedBlock(inlinedBlock);
        PromisedValue unitValue = PromisedValueKt.getUnitValue(this);
        for (IrStatement irStatement : originalStatementsFromInlinedBlock) {
            unitValue.discard();
            unitValue = (PromisedValue) irStatement.accept(this, data);
        }
        if (irFunction != null && (!(inlinedBlock.getInlinedElement() instanceof IrCallableReference) || irFunction.getIsInline())) {
            setExtraLineNumberForVoidReturningFunction(irFunction);
        }
        IrStatement irStatement2 = (irFunction == null || (body = irFunction.getBody()) == null || (statements = IrUtilsKt.getStatements(body)) == null) ? null : (IrStatement) CollectionsKt.lastOrNull((List) statements);
        if (irStatement2 instanceof IrReturn) {
            IrReturn irReturn = (IrReturn) irStatement2;
            IrReturnTarget owner = irReturn.getReturnTargetSymbol().getOwner();
            IrAttributeContainer irAttributeContainer = owner instanceof IrAttributeContainer ? (IrAttributeContainer) owner : null;
            if (Intrinsics.areEqual((irAttributeContainer == null || (attributeOwnerId = irAttributeContainer.getAttributeOwnerId()) == null) ? owner : attributeOwnerId, IrInlineUtilsKt.getInlineDeclaration(inlinedBlock))) {
                markLineNumber((IrElement) CollectionsKt.last((List) inlinedBlock.getStatements()), irReturn.getStartOffset() != irReturn.getEndOffset());
                this.mv.nop();
            }
        }
        this.lineNumberMapper.dropCurrentSmap();
        return unitValue;
    }

    private final IrClass getClassWithDeclaredFunction(IrDeclaration irDeclaration) {
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration);
        Object obj = null;
        if (parentClassOrNull == null) {
            return null;
        }
        if (!IrUtilsKt.isInterface(parentClassOrNull) || ((irDeclaration instanceof IrFunction) && JvmIrUtilsKt.hasJvmDefault((IrFunction) irDeclaration))) {
            return parentClassOrNull;
        }
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : parentClassOrNull.getDeclarations()) {
            if (Intrinsics.areEqual(((IrDeclaration) obj3).getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
                if (z) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    return (IrClass) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return (IrClass) obj;
    }

    private final SMAP buildOrGetClassSMAP(final IrInlinedFunctionBlock irInlinedFunctionBlock, BlockInfo blockInfo) {
        if (IrInlineUtilsKt.isLambdaInlining(irInlinedFunctionBlock)) {
            Map<Type, SMAP> typeToCachedSMAP = this.context.getTypeToCachedSMAP();
            JvmBackendContext jvmBackendContext = this.context;
            IrElement inlinedElement = irInlinedFunctionBlock.getInlinedElement();
            Intrinsics.checkNotNull(inlinedElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrAttributeContainer");
            SMAP smap = typeToCachedSMAP.get(jvmBackendContext.getLocalClassType((IrAttributeContainer) inlinedElement));
            Intrinsics.checkNotNull(smap);
            return smap;
        }
        final IrDeclaration inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock);
        IrClass classWithDeclaredFunction = getClassWithDeclaredFunction(inlineDeclaration);
        Intrinsics.checkNotNull(classWithDeclaredFunction);
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(classWithDeclaredFunction.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$buildOrGetClassSMAP$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSimpleFunction);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) SequencesKt.single(SequencesKt.filter(SequencesKt.filter(SequencesKt.filter(sequenceFilter, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$buildOrGetClassSMAP$calleeFromActualClass$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getAttributeOwnerId(), inlineDeclaration));
            }
        }), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$buildOrGetClassSMAP$calleeFromActualClass$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                boolean zAreEqual;
                Intrinsics.checkNotNullParameter(it, "it");
                if (IrUtilsKt.usesDefaultArguments(irInlinedFunctionBlock.getInlineCall())) {
                    zAreEqual = Intrinsics.areEqual(it.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE);
                } else {
                    zAreEqual = !Intrinsics.areEqual(it.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE);
                }
                return Boolean.valueOf(zAreEqual);
            }
        }), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$buildOrGetClassSMAP$calleeFromActualClass$3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE));
            }
        }));
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int startOffset = irInlinedFunctionBlock.getInlineCall().getStartOffset();
        int endOffset = irInlinedFunctionBlock.getInlineCall().getEndOffset();
        IrType type = irInlinedFunctionBlock.getInlineCall().getType();
        IrSimpleFunctionSymbol symbol = irSimpleFunction.getSymbol();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, type, symbol, (240 & 16) != 0 ? symbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? symbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        IrCallGenerator orCreateCallGenerator = getOrCreateCallGenerator(irCallImplFromSymbolOwner, blockInfo, this.methodSignatureMapper.mapToCallableMethod(irCallImplFromSymbolOwner, null).getSignature());
        Intrinsics.checkNotNull(orCreateCallGenerator, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.codegen.IrInlineCodegen");
        return ((IrInlineCodegen) orCreateCallGenerator).compileInline().getClassSMAP();
    }

    private final PromisedValue visitStatementContainer(IrStatementContainer container, BlockInfo data) {
        if (container instanceof IrInlinedFunctionBlock) {
            return visitInlinedFunctionBlock((IrInlinedFunctionBlock) container, data);
        }
        List<IrStatement> statements = container.getStatements();
        PromisedValue unitValue = PromisedValueKt.getUnitValue(this);
        for (IrStatement irStatement : statements) {
            unitValue.discard();
            unitValue = (PromisedValue) irStatement.accept(this, data);
        }
        return unitValue;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public PromisedValue visitBlockBody2(IrBlockBody body, BlockInfo data) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(data, "data");
        visitStatementContainer(body, data).discard();
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public PromisedValue visitContainerExpression2(IrContainerExpression expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (Intrinsics.areEqual(expression.getOrigin(), JvmLoweredStatementOrigin.FAKE_CONTINUATION.INSTANCE)) {
            InlineCodegenUtilsKt.addFakeContinuationMarker(this.mv);
            return getOnStack(expression);
        }
        return visitStatementContainer(expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public PromisedValue visitCall2(IrCall expression, BlockInfo data) {
        boolean z;
        boolean z2;
        Type asmType;
        PromisedValue promisedValueInvoke;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IntrinsicMethod intrinsicMethod = (IntrinsicMethod) this.classCodegen.getContext().getGetIntrinsic().invoke(expression.getSymbol());
        if (intrinsicMethod != null && (promisedValueInvoke = intrinsicMethod.invoke(expression, this, data)) != null) {
            return promisedValueInvoke;
        }
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        if (!(owner.getParent() instanceof IrClass)) {
            throw new IllegalArgumentException(("Unhandled intrinsic in ExpressionCodegen: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrCallableMethod irCallableMethodMapToCallableMethod = this.methodSignatureMapper.mapToCallableMethod(expression, this.irFunction);
        IrCall irCall = expression;
        IrCallGenerator orCreateCallGenerator = getOrCreateCallGenerator(irCall, data, irCallableMethodMapToCallableMethod.getSignature());
        SuspensionPointKind suspensionPointKind = getSuspensionPointKind(irCall);
        if (suspensionPointKind != SuspensionPointKind.NEVER) {
            InlineCodegenUtilsKt.addInlineMarker(this.mv, true);
        }
        orCreateCallGenerator.beforeCallStart();
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver != null) {
            Type asmType2 = expression.getSuperQualifierSymbol() != null ? getAsmType(dispatchReceiver) : irCallableMethodMapToCallableMethod.getOwner();
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            orCreateCallGenerator.genValueAndPut(dispatchReceiverParameter, dispatchReceiver, asmType2, this, data);
        }
        boolean z3 = false;
        List<IrValueParameter> listSubList = owner.getValueParameters().subList(0, owner.getContextReceiverParametersCount());
        int i = 0;
        for (Object obj : listSubList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            visitCall$handleValueParameter(expression, irCallableMethodMapToCallableMethod, orCreateCallGenerator, this, data, i, (IrValueParameter) obj);
            suspensionPointKind = suspensionPointKind;
            i = i2;
            z3 = false;
        }
        SuspensionPointKind suspensionPointKind2 = suspensionPointKind;
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        if (extensionReceiver != null) {
            List<JvmMethodParameterSignature> valueParameters = irCallableMethodMapToCallableMethod.getSignature().getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            Iterator<T> it = valueParameters.iterator();
            Object obj2 = null;
            boolean z4 = false;
            while (true) {
                if (!it.hasNext()) {
                    if (!z4) {
                        break;
                    }
                    break;
                }
                Object next = it.next();
                if (((JvmMethodParameterSignature) next).getKind() == JvmMethodParameterKind.RECEIVER) {
                    if (!z4) {
                        obj2 = next;
                        z4 = true;
                    }
                }
                obj2 = null;
                break;
            }
            JvmMethodParameterSignature jvmMethodParameterSignature = (JvmMethodParameterSignature) obj2;
            if (jvmMethodParameterSignature == null || (asmType = jvmMethodParameterSignature.getAsmType()) == null) {
                throw new IllegalStateException(("No single extension receiver parameter: " + irCallableMethodMapToCallableMethod.getSignature().getValueParameters()).toString());
            }
            Intrinsics.checkNotNull(asmType);
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            orCreateCallGenerator.genValueAndPut(extensionReceiverParameter, extensionReceiver, asmType, this, data);
        }
        int i3 = 0;
        for (Object obj3 : owner.getValueParameters().subList(owner.getContextReceiverParametersCount(), owner.getValueParameters().size())) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            visitCall$handleValueParameter(expression, irCallableMethodMapToCallableMethod, orCreateCallGenerator, this, data, i3 + listSubList.size(), (IrValueParameter) obj3);
            i3 = i4;
        }
        markLineNumber(expression, true);
        if (suspensionPointKind2 != SuspensionPointKind.NEVER) {
            InlineCodegenUtilsKt.addSuspendMarker(this.mv, true, suspensionPointKind2 == SuspensionPointKind.NOT_INLINE);
        }
        orCreateCallGenerator.genCall(irCallableMethodMapToCallableMethod, this, irCall, this.isInsideCondition);
        IrType irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(owner);
        if (suspensionPointKind2 != SuspensionPointKind.NEVER) {
            InstructionAdapter instructionAdapter = this.mv;
            if (suspensionPointKind2 == SuspensionPointKind.NOT_INLINE) {
                z2 = true;
                z = false;
            } else {
                z = false;
                z2 = false;
            }
            InlineCodegenUtilsKt.addSuspendMarker(instructionAdapter, z, z2);
            if (irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null) {
                InlineCodegenUtilsKt.generateResumePathUnboxing(this.mv, irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, this.typeMapper);
            }
            InlineCodegenUtilsKt.addInlineMarker(this.mv, z);
        }
        orCreateCallGenerator.afterCallEnd();
        if ((IrTypePredicatesKt.isNothing(expression.getType()) || IrTypePredicatesKt.isUnit(expression.getType())) && JvmIrCoroutineUtilsKt.shouldContainSuspendMarkers(this.irFunction)) {
            if (!Intrinsics.areEqual(irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType(), Type.VOID_TYPE)) {
                Type returnType = irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType();
                Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
                new MaterialValue(this, returnType, irCallableMethodMapToCallableMethod.getReturnType()).discard();
            }
            return PromisedValueKt.getUnitValue(this);
        }
        IrSimpleFunction irSimpleFunction = owner;
        if (IrUtilsKt.isAnnotationClass(IrUtilsKt.getParentAsClass(irSimpleFunction)) && Intrinsics.areEqual(irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType(), AsmTypes.JAVA_CLASS_TYPE)) {
            AsmUtil.wrapJavaClassIntoKClass(this.mv);
            Type K_CLASS_TYPE = AsmTypes.K_CLASS_TYPE;
            Intrinsics.checkNotNullExpressionValue(K_CLASS_TYPE, "K_CLASS_TYPE");
            return new MaterialValue(this, K_CLASS_TYPE, expression.getType());
        }
        if (IrUtilsKt.isAnnotationClass(IrUtilsKt.getParentAsClass(irSimpleFunction)) && Intrinsics.areEqual(irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType(), AsmTypes.JAVA_CLASS_ARRAY_TYPE)) {
            AsmUtil.wrapJavaClassesIntoKClasses(this.mv);
            Type K_CLASS_ARRAY_TYPE = AsmTypes.K_CLASS_ARRAY_TYPE;
            Intrinsics.checkNotNullExpressionValue(K_CLASS_ARRAY_TYPE, "K_CLASS_ARRAY_TYPE");
            return new MaterialValue(this, K_CLASS_ARRAY_TYPE, expression.getType());
        }
        if (irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null && !JvmIrCoroutineUtilsKt.isNonBoxingSuspendDelegation(this.irFunction)) {
            MaterialValue materialValue = new MaterialValue(this, getAsmType(irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass), irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass);
            if (!JvmIrCoroutineUtilsKt.shouldContainSuspendMarkers(this.irFunction)) {
                CoroutineCodegenUtilKt.generateCoroutineSuspendedCheck(materialValue.getMv());
            }
            materialValue.getMv().checkcast(materialValue.getType());
            return materialValue;
        }
        if (Intrinsics.areEqual((Object) resultIsActuallyAny(owner, null), (Object) true)) {
            Type returnType2 = irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType2, "getReturnType(...)");
            return new MaterialValue(this, returnType2, this.context.getIrBuiltIns().getAnyNType());
        }
        Type returnType3 = irCallableMethodMapToCallableMethod.getAsmMethod().getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType3, "getReturnType(...)");
        return new MaterialValue(this, returnType3, irCallableMethodMapToCallableMethod.getReturnType());
    }

    private static final void visitCall$handleValueParameter(IrCall irCall, IrCallableMethod irCallableMethod, IrCallGenerator irCallGenerator, ExpressionCodegen expressionCodegen, BlockInfo blockInfo, int i, IrValueParameter irValueParameter) {
        IrExpression valueArgument = irCall.getValueArgument(i);
        Type type = irCallableMethod.getValueParameterTypes().get(i);
        if (valueArgument == null) {
            throw new IllegalArgumentException(("No argument for parameter " + RenderIrElementKt.render$default(irValueParameter, (DumpIrTreeOptions) null, 1, (Object) null) + ":\n" + DumpIrTreeKt.dump$default(irCall, null, 1, null)).toString());
        }
        irCallGenerator.genValueAndPut(irValueParameter, valueArgument, type, expressionCodegen, blockInfo);
    }

    private final SuspensionPointKind getSuspensionPointKind(IrFunctionAccessExpression irFunctionAccessExpression) {
        if (!AdditionalIrUtilsKt.isSuspend(irFunctionAccessExpression.getSymbol().getOwner()) || !JvmIrCoroutineUtilsKt.shouldContainSuspendMarkers(this.irFunction)) {
            return SuspensionPointKind.NEVER;
        }
        if (irFunctionAccessExpression.getSymbol().getOwner().getIsInline()) {
            if (Intrinsics.areEqual(irFunctionAccessExpression.getSymbol().getOwner().getName().asString(), "suspendCoroutineUninterceptedOrReturn") && Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) irFunctionAccessExpression.getSymbol().getOwner()).getPackageFqName(), new FqName("kotlin.coroutines.intrinsics"))) {
                return SuspensionPointKind.ALWAYS;
            }
            return SuspensionPointKind.NEVER;
        }
        if (CoroutineCodegenKt.isReadOfInlineLambda(irFunctionAccessExpression.getDispatchReceiver())) {
            return SuspensionPointKind.NOT_INLINE;
        }
        return SuspensionPointKind.ALWAYS;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public PromisedValue visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrConstructor owner = expression.getSymbol().getOwner();
        Type typeMapClass = IrTypeMappingKt.mapClass(this.typeMapper, AdditionalIrUtilsKt.getConstructedClass(owner));
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = this.methodSignatureMapper.mapSignatureSkipGeneric(owner);
        IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
        markLineNumber(irDelegatingConstructorCall, true);
        this.mv.load(0, AsmTypes.OBJECT_TYPE);
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            IrType typeArgument = expression.getTypeArgument(i);
            IrClassifierSymbol classifierOrNull = typeArgument != null ? IrTypesKt.getClassifierOrNull(typeArgument) : null;
            if ((classifierOrNull instanceof IrTypeParameterSymbol) && ((IrTypeParameterSymbol) classifierOrNull).getOwner().getIsReified()) {
                consumeReifiedOperationMarker((TypeParameterMarker) classifierOrNull);
            }
        }
        generateConstructorArguments(expression, jvmMethodSignatureMapSignatureSkipGeneric, data);
        markLineNumber(irDelegatingConstructorCall, true);
        this.mv.visitMethodInsn(183, typeMapClass.getInternalName(), jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getName(), jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getDescriptor(), false);
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public PromisedValue visitConstructorCall2(IrConstructorCall expression, BlockInfo data) {
        PromisedValue promisedValueInvoke;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IntrinsicMethod intrinsicMethod = (IntrinsicMethod) this.classCodegen.getContext().getGetIntrinsic().invoke(expression.getSymbol());
        if (intrinsicMethod != null && (promisedValueInvoke = intrinsicMethod.invoke(expression, this, data)) != null) {
            return promisedValueInvoke;
        }
        IrConstructor owner = expression.getSymbol().getOwner();
        Type typeMapClass = IrTypeMappingKt.mapClass(this.typeMapper, AdditionalIrUtilsKt.getConstructedClass(owner));
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = this.methodSignatureMapper.mapSignatureSkipGeneric(owner);
        IrConstructorCall irConstructorCall = expression;
        markLineNumber(irConstructorCall, true);
        putNeedClassReificationMarker(AdditionalIrUtilsKt.getConstructedClass(owner));
        this.mv.anew(typeMapClass);
        this.mv.dup();
        generateConstructorArguments(expression, jvmMethodSignatureMapSignatureSkipGeneric, data);
        markLineNumber(irConstructorCall, true);
        this.mv.visitMethodInsn(183, typeMapClass.getInternalName(), jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getName(), jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getDescriptor(), false);
        return new MaterialValue(this, typeMapClass, expression.getType());
    }

    private final void generateConstructorArguments(IrFunctionAccessExpression expression, JvmMethodSignature signature, BlockInfo data) {
        int i = 0;
        for (Object obj : expression.getSymbol().getOwner().getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument == null) {
                throw new IllegalStateException(("Null argument in ExpressionCodegen for parameter " + RenderIrElementKt.render$default(irValueParameter, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            Type asmType = signature.getValueParameters().get(i).getAsmType();
            Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
            gen(valueArgument, asmType, irValueParameter.getType(), data);
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public PromisedValue visitVariable2(IrVariable declaration, BlockInfo data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        Type typeMapType = IrTypeMappingKt.mapType(this.typeMapper, declaration);
        int iEnter2 = getFrameMap().enter((IrSymbol) declaration.getSymbol(), typeMapType);
        IrExpression initializer = declaration.getInitializer();
        if (initializer != null) {
            PromisedValue promisedValue = (PromisedValue) initializer.accept(this, data);
            markLineNumber(initializer, true);
            PromisedValueKt.materializeAt(promisedValue, typeMapType, declaration.getType());
            markLineNumber(declaration, true);
            this.mv.store(iEnter2, typeMapType);
        } else if (isVisibleInLVT(declaration)) {
            markLineNumber(declaration, true);
            AsmUtil.pushDefaultValueOnStack(typeMapType, this.mv);
            this.mv.store(iEnter2, typeMapType);
        }
        data.getVariables().add(new VariableInfo(declaration, iEnter2, typeMapType, markNewLabel()));
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public PromisedValue visitGetValue2(IrGetValue expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(expression, true);
        Type typeTypeOf = getFrameMap().typeOf(expression.getSymbol());
        this.mv.load(findLocalIndex(expression.getSymbol()), typeTypeOf);
        return new MaterialValue(this, typeTypeOf, getRealType(expression.getSymbol().getOwner()));
    }

    public final StackValue genOrGetLocal$backend_jvm_codegen(IrExpression expression, Type type, IrType parameterType, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(data, "data");
        if (expression instanceof IrGetValue) {
            IrGetValue irGetValue = (IrGetValue) expression;
            StackValue.Local local = StackValue.local(findLocalIndex(irGetValue.getSymbol()), getFrameMap().typeOf(irGetValue.getSymbol()), IrBasedDescriptorsKt.toIrBasedKotlinType$default(getRealType(irGetValue.getSymbol().getOwner()), null, 1, null));
            Intrinsics.checkNotNullExpressionValue(local, "local(...)");
            return local;
        }
        return genToStackValue(expression, type, parameterType, data);
    }

    public final IrType getRealType(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        IrDeclarationParent parent = irValueDeclaration.getParent();
        if ((irValueDeclaration instanceof IrValueParameter) && (parent instanceof IrSimpleFunction)) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) parent;
            if (!Intrinsics.areEqual(irSimpleFunction.getDispatchReceiverParameter(), irValueDeclaration)) {
                IrDeclarationParent parent2 = irSimpleFunction.getParent();
                IrClass irClass = parent2 instanceof IrClass ? (IrClass) parent2 : null;
                if (!Intrinsics.areEqual(irClass != null ? IrUtilsKt.getFqNameWhenAvailable(irClass) : null, StandardNames.RESULT_FQ_NAME) && Intrinsics.areEqual((Object) resultIsActuallyAny(irSimpleFunction, Integer.valueOf(((IrValueParameter) irValueDeclaration).getIndex())), (Object) true)) {
                    return this.context.getIrBuiltIns().getAnyNType();
                }
            }
        }
        return irValueDeclaration.getType();
    }

    private final Boolean resultIsActuallyAny(IrSimpleFunction irSimpleFunction, Integer num) {
        IrType type;
        if (num == null) {
            type = irSimpleFunction.getReturnType();
        } else if (num.intValue() < 0) {
            IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            type = extensionReceiverParameter.getType();
        } else {
            type = irSimpleFunction.getValueParameters().get(num.intValue()).getType();
        }
        if (!IrTypePredicatesKt.isKotlinResult(JvmIrTypeUtilsKt.eraseTypeParameters(type))) {
            return null;
        }
        Method methodMapAsmMethod = this.methodSignatureMapper.mapAsmMethod(irSimpleFunction);
        boolean z = false;
        if (irSimpleFunction.getParent() instanceof IrClass) {
            List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
            if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : overriddenSymbols) {
                    if (Intrinsics.areEqual(this.methodSignatureMapper.mapAsmMethod(irSimpleFunctionSymbol.getOwner()), methodMapAsmMethod) && !Intrinsics.areEqual((Object) resultIsActuallyAny(irSimpleFunctionSymbol.getOwner(), num), (Object) false)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        return Boolean.valueOf(z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public PromisedValue visitFieldAccess2(IrFieldAccessExpression expression, BlockInfo data) {
        Type typeMapClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrField owner = expression.getSymbol().getOwner();
        if (this.context.getConfig().getShouldInlineConstVals()) {
            JvmIrUtilsKt.constantValue(owner);
        }
        boolean z = expression.getReceiver() == null;
        IrFieldAccessExpression irFieldAccessExpression = expression;
        markLineNumber(irFieldAccessExpression, true);
        IrExpression receiver = expression.getReceiver();
        Type type = receiver != null ? PromisedValueKt.materializedAt$default((PromisedValue) receiver.accept(this, data), IrTypeMappingKt.mapTypeAsDeclaration(this.typeMapper, receiver.getType()), receiver.getType(), false, 4, null).getType() : null;
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol != null && (typeMapClass = IrTypeMappingKt.mapClass(this.typeMapper, superQualifierSymbol.getOwner())) != null) {
            type = typeMapClass;
        } else if (type == null) {
            type = IrTypeMappingKt.mapClass(this.typeMapper, IrUtilsKt.getParentAsClass(owner));
        }
        String internalName = type.getInternalName();
        String strAsString = owner.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Type asmType = getAsmType((IrUtilsKt.isFromJava(owner) && JvmIrTypeUtilsKt.isInlineClassType(owner.getType())) ? IrTypesKt.makeNullable(owner.getType()) : owner.getType());
        if (expression instanceof IrSetField) {
            PromisedValue promisedValue = (PromisedValue) ((IrSetField) expression).getValue().accept(this, data);
            if (Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE)) {
                PromisedValueKt.materialize(promisedValue);
            } else {
                PromisedValueKt.materializeAt(promisedValue, asmType, owner.getType());
            }
            markLineNumber(irFieldAccessExpression, true);
            this.mv.visitFieldInsn(z ? 179 : 181, internalName, strAsString, asmType.getDescriptor());
            IrTypePredicatesKt.isUnit(expression.getType());
            return PromisedValueKt.getUnitValue(this);
        }
        if (Intrinsics.areEqual(expression.getSymbol().getOwner().getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE)) {
            putNeedClassReificationMarker(IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner()));
        }
        this.mv.visitFieldInsn(z ? 178 : 180, internalName, strAsString, asmType.getDescriptor());
        return new MaterialValue(this, asmType, owner.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public PromisedValue visitSetField2(IrSetField expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression value = expression.getValue();
        return (((this.irFunction instanceof IrConstructor) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE)) && Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.INITIALIZE_FIELD.INSTANCE) && (value instanceof IrConst) && isDefaultValueForType(getAsmType(expression.getSymbol().getOwner().getType()), ((IrConst) value).getValue())) ? PromisedValueKt.getUnitValue(this) : (PromisedValue) IrElementVisitor.DefaultImpls.visitSetField(this, expression, data);
    }

    private final boolean isDefaultValueForType(Type type, Object value) {
        if (Intrinsics.areEqual(type, Type.BOOLEAN_TYPE)) {
            if (!(value instanceof Boolean) || ((Boolean) value).booleanValue()) {
                return false;
            }
        } else if (!Intrinsics.areEqual(type, Type.CHAR_TYPE)) {
            if (Intrinsics.areEqual(type, Type.BYTE_TYPE) ? true : Intrinsics.areEqual(type, Type.SHORT_TYPE) ? true : Intrinsics.areEqual(type, Type.INT_TYPE) ? true : Intrinsics.areEqual(type, Type.LONG_TYPE)) {
                if (!(value instanceof Number) || ((Number) value).longValue() != 0) {
                    return false;
                }
            } else if (Intrinsics.areEqual(type, Type.FLOAT_TYPE)) {
                if (!(value instanceof Number) || !Float.valueOf(((Number) value).floatValue()).equals(Float.valueOf(0.0f))) {
                    return false;
                }
            } else if (Intrinsics.areEqual(type, Type.DOUBLE_TYPE)) {
                if (!(value instanceof Number) || !Double.valueOf(((Number) value).doubleValue()).equals(Double.valueOf(0.0d))) {
                    return false;
                }
            } else if (AsmUtil.isPrimitive(type) || value != null) {
                return false;
            }
        } else if (!(value instanceof Character) || ((Character) value).charValue() != 0) {
            return false;
        }
        return true;
    }

    private final int findLocalIndex(IrSymbol irSymbol) {
        int index = getFrameMap().getIndex(irSymbol);
        if (index >= 0) {
            return index;
        }
        throw new AssertionError("Non-mapped local declaration: " + DumpIrTreeKt.dump$default(irSymbol.getOwner(), null, 1, null) + "\n in " + DumpIrTreeKt.dump$default(this.irFunction, null, 1, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public PromisedValue visitSetValue2(IrSetValue expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(expression.getValue(), true);
        PromisedValueKt.materializeAt((PromisedValue) expression.getValue().accept(this, data), expression.getSymbol().getOwner().getType());
        if (!(expression.getSymbol() instanceof IrValueParameterSymbol)) {
            markLineNumber(expression, true);
        }
        this.mv.store(findLocalIndex(expression.getSymbol()), getAsmType(expression.getSymbol().getOwner()));
        return PromisedValueKt.getUnitValue(this);
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public PromisedValue visitConst2(IrConst<?> expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(expression, true);
        Object value = expression.getValue();
        if (value instanceof Boolean) {
            this.mv.nop();
            return new BooleanConstant(this, ((Boolean) value).booleanValue());
        }
        if (value instanceof Character) {
            this.mv.iconst(((Character) value).charValue());
        } else if (value instanceof Long) {
            this.mv.lconst(((Number) value).longValue());
        } else if (value instanceof Float) {
            this.mv.fconst(((Number) value).floatValue());
        } else if (value instanceof Double) {
            this.mv.dconst(((Number) value).doubleValue());
        } else if (value instanceof Number) {
            this.mv.iconst(((Number) value).intValue());
        } else if (value instanceof String) {
            generateStringConstant((String) value);
        } else {
            if (Intrinsics.areEqual(expression.getKind(), IrConstKind.Null.INSTANCE)) {
                return PromisedValueKt.getNullConstant(this);
            }
            this.mv.aconst(value);
        }
        return getOnStack(expression);
    }

    private final void generateStringConstant(String value) {
        int length = value.length();
        List<String> listSplitStringConstant = CodegenUtilKt.splitStringConstant(value);
        if (listSplitStringConstant.size() == 1) {
            this.mv.aconst(CollectionsKt.first((List) listSplitStringConstant));
            return;
        }
        this.mv.anew(Type.getObjectType("java/lang/StringBuilder"));
        this.mv.dup();
        this.mv.iconst(length);
        this.mv.invokespecial("java/lang/StringBuilder", "<init>", "(I)V", false);
        Iterator<String> it = listSplitStringConstant.iterator();
        while (it.hasNext()) {
            this.mv.aconst(it.next());
            this.mv.invokevirtual("java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        }
        this.mv.invokevirtual("java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public PromisedValue visitExpressionBody2(IrExpressionBody body, BlockInfo data) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(data, "data");
        return (PromisedValue) body.getExpression().accept(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public Void visitElement2(IrElement element, BlockInfo data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new AssertionError("Unexpected IR element found during code generation. Either code generation for it is not implemented, or it should have been lowered:\n" + RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public PromisedValue visitClass2(IrClass declaration, BlockInfo data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!Intrinsics.areEqual(declaration.getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE)) {
            ClassCodegen orCreate = ClassCodegen.INSTANCE.getOrCreate(declaration, this.context, getEnclosingFunctionForLocalObjects());
            orCreate.generate();
            this.closureReifiedMarkers.put(declaration, orCreate.getReifiedTypeParametersUsages());
            if (Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) || Intrinsics.areEqual(declaration.getOrigin(), JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE)) {
                this.context.getTypeToCachedSMAP().put(orCreate.getType(), new SMAP(orCreate.getSmap().getResultMappings()));
            }
        }
        return PromisedValueKt.getUnitValue(this);
    }

    private final void putNeedClassReificationMarker(IrClass declaration) {
        HashMap<IrClass, ReifiedTypeParametersUsages> map = this.closureReifiedMarkers;
        ReifiedTypeParametersUsages reifiedTypeParameters = map.get(declaration);
        if (reifiedTypeParameters == null) {
            reifiedTypeParameters = IrCodegenUtilsKt.getReifiedTypeParameters(declaration);
            map.put(declaration, reifiedTypeParameters);
        }
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = reifiedTypeParameters;
        if (reifiedTypeParametersUsages.wereUsedReifiedParameters()) {
            ReifiedTypeInliner.INSTANCE.putNeedClassReificationMarker(this.mv);
            propagateChildReifiedTypeParametersUsages(reifiedTypeParametersUsages);
        }
    }

    private final void generateGlobalReturnFlagIfPossible(IrExpression expression, String label) {
        if (this.state.getConfig().getIsInlineDisabled()) {
            this.context.getKtDiagnosticReporter().at(expression, this.irFunction).report(BackendErrors.INSTANCE.getNON_LOCAL_RETURN_IN_DISABLED_INLINE());
            AsmUtil.genThrow(this.mv, "java/lang/UnsupportedOperationException", "Non-local returns are not allowed with inlining disabled");
        } else {
            InlineCodegenUtilsKt.generateGlobalReturnFlag(this.mv, label);
        }
    }

    private final Pair<Type, IrType> returnAsmAndIrTypes(IrFunction irFunction) {
        IrType anyNType;
        IrType irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = CoroutineCodegenKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(irFunction);
        if (irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null && !JvmIrCoroutineUtilsKt.isNonBoxingSuspendDelegation(irFunction)) {
            return TuplesKt.to(getAsmType(irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass), irTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass);
        }
        Type returnType = Intrinsics.areEqual(irFunction, this.irFunction) ? this.signature.getReturnType() : MethodSignatureMapper.mapReturnType$default(this.methodSignatureMapper, irFunction, null, false, 6, null);
        Intrinsics.checkNotNull(returnType);
        if (irFunction instanceof IrConstructor) {
            anyNType = this.context.getIrBuiltIns().getUnitType();
        } else {
            anyNType = ((irFunction instanceof IrSimpleFunction) && Intrinsics.areEqual((Object) resultIsActuallyAny((IrSimpleFunction) irFunction, null), (Object) true)) ? this.context.getIrBuiltIns().getAnyNType() : irFunction.getReturnType();
        }
        return TuplesKt.to(returnType, anyNType);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public PromisedValue visitReturn2(IrReturn expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrReturnTarget owner = expression.getReturnTargetSymbol().getOwner();
        IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
        if (irFunction == null) {
            throw new IllegalStateException(("Unsupported IrReturnTarget: " + owner).toString());
        }
        boolean zAreEqual = Intrinsics.areEqual(MethodSignatureMapper.mapFunctionName$default(this.methodSignatureMapper, irFunction, false, 2, null), MethodSignatureMapper.mapFunctionName$default(this.methodSignatureMapper, this.irFunction, false, 2, null));
        Pair<Type, IrType> pairReturnAsmAndIrTypes = returnAsmAndIrTypes(irFunction);
        Type typeComponent1 = pairReturnAsmAndIrTypes.component1();
        IrType irTypeComponent2 = pairReturnAsmAndIrTypes.component2();
        Label label = new Label();
        PromisedValueKt.materializeAt((PromisedValue) expression.getValue().accept(this, data), typeComponent1, irTypeComponent2);
        generateFinallyBlocksIfNeeded(typeComponent1, label, data, null);
        markLineNumber(expression, true);
        if (!zAreEqual) {
            String strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            generateGlobalReturnFlagIfPossible(expression, strAsString);
        }
        this.mv.areturn(typeComponent1);
        this.mv.mark(label);
        this.mv.nop();
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public PromisedValue visitWhen2(IrWhen expression, BlockInfo data) {
        boolean z;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(expression, true);
        PromisedValue promisedValueGenerate = new SwitchGenerator(expression, data, this).generate();
        if (promisedValueGenerate != null) {
            return promisedValueGenerate;
        }
        if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.WHEN.INSTANCE)) {
            this.mv.nop();
        }
        Label label = new Label();
        List<IrBranch> branches = expression.getBranches();
        if (!(branches instanceof Collection) || !branches.isEmpty()) {
            Iterator<T> it = branches.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (IrUtilsKt.isTrueConst(((IrBranch) it.next()).getCondition())) {
                        if (!IrTypePredicatesKt.isUnit(expression.getType())) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                break;
            }
        }
        z = false;
        break;
        if (!z) {
            IrTypePredicatesKt.isUnit(expression.getType());
        }
        IrBranch irBranch = (IrBranch) CollectionsKt.lastOrNull((List) expression.getBranches());
        for (IrBranch irBranch2 : expression.getBranches()) {
            Label label2 = new Label();
            if (IrUtilsKt.isFalseConst(irBranch2.getCondition()) || IrUtilsKt.isTrueConst(irBranch2.getCondition())) {
                if (!(irBranch2 instanceof IrElseBranch)) {
                    markLineNumber(irBranch2.getCondition(), true);
                    this.mv.nop();
                }
                if (IrUtilsKt.isFalseConst(irBranch2.getCondition())) {
                    continue;
                }
            } else {
                boolean z2 = this.isInsideCondition;
                this.isInsideCondition = true;
                PromisedValueKt.coerceToBoolean((PromisedValue) irBranch2.getCondition().accept(this, data)).jumpIfFalse(label2);
                this.isInsideCondition = z2;
            }
            PromisedValue promisedValue = (PromisedValue) irBranch2.getResult().accept(this, data);
            if (!z) {
                promisedValue.discard();
            } else {
                MaterialValue materialValueMaterializedAt = PromisedValueKt.materializedAt(promisedValue, IrTypeMapper.mapType$default(this.typeMapper, expression.getType(), null, null, false, 14, null), expression.getType(), true);
                if (IrUtilsKt.isTrueConst(irBranch2.getCondition())) {
                    this.mv.mark(label);
                    return materialValueMaterializedAt;
                }
            }
            if (!Intrinsics.areEqual(irBranch2, irBranch)) {
                this.mv.goTo(label);
            }
            this.mv.mark(label2);
        }
        this.mv.mark(label);
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public PromisedValue visitTypeOperator2(IrTypeOperatorCall expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrType typeOperand = expression.getTypeOperand();
        KotlinType irBasedKotlinType$default = IrBasedDescriptorsKt.toIrBasedKotlinType$default(typeOperand, null, 1, null);
        int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()];
        if (i == 1) {
            return (PromisedValue) expression.getArgument().accept(this, data);
        }
        if (i == 2 || i == 3) {
            PromisedValue promisedValue = (PromisedValue) expression.getArgument().accept(this, data);
            PromisedValueKt.materializeAt(promisedValue, this.typeMapper.boxType(promisedValue.getIrType()), expression.getArgument().getType());
            Type typeBoxType = this.typeMapper.boxType(typeOperand);
            if (JvmIrTypeUtilsKt.isReifiedTypeParameter(typeOperand)) {
                BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, typeOperand, expression.getOperator() == IrTypeOperator.CAST ? ReifiedTypeInliner.OperationKind.AS : ReifiedTypeInliner.OperationKind.SAFE_AS);
                this.mv.checkcast(typeBoxType);
            } else {
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.CAST;
                TypeIntrinsics.checkcast(this.mv, irBasedKotlinType$default, typeBoxType, false);
            }
            return new MaterialValue(this, typeBoxType, expression.getType());
        }
        if (i == 4) {
            Type typeMapType$default = IrTypeMapper.mapType$default(this.typeMapper, typeOperand, null, null, false, 14, null);
            PromisedValueKt.materialize((PromisedValue) expression.getArgument().accept(this, data));
            return new MaterialValue(this, typeMapType$default, typeOperand);
        }
        if (i == 5) {
            PromisedValueKt.materializeAt((PromisedValue) expression.getArgument().accept(this, data), this.context.getIrBuiltIns().getAnyNType());
            Type typeBoxType2 = this.typeMapper.boxType(typeOperand);
            if (JvmIrTypeUtilsKt.isReifiedTypeParameter(typeOperand)) {
                BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, typeOperand, ReifiedTypeInliner.OperationKind.IS);
                this.mv.instanceOf(typeBoxType2);
            } else {
                TypeIntrinsics.instanceOf(this.mv, irBasedKotlinType$default, typeBoxType2);
            }
            return getOnStack(expression);
        }
        throw new AssertionError("type operator " + expression.getOperator() + " should have been lowered");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public PromisedValue visitWhileLoop2(IrWhileLoop loop, BlockInfo data) {
        PromisedValue promisedValue;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        InlineCodegenUtilsKt.addInlineMarker(this.mv, true);
        Label labelMarkNewLinkedLabel = markNewLinkedLabel();
        Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.mv, labelLinkedLabel);
        markLineNumber(loop.getCondition(), true);
        PromisedValueKt.coerceToBoolean((PromisedValue) loop.getCondition().accept(this, data)).jumpIfFalse(labelLinkedLabel);
        LoopInfo loopInfo = new LoopInfo(loop, labelMarkNewLinkedLabel, labelLinkedLabel);
        loopInfo.setBlockInfo(data);
        data.getInfos().add(loopInfo);
        try {
            IrExpression body = loop.getBody();
            if (body != null && (promisedValue = (PromisedValue) body.accept(this, data)) != null) {
                promisedValue.discard();
                Unit unit = Unit.INSTANCE;
            }
            data.getInfos().pop().setBlockInfo(null);
            this.mv.goTo(labelMarkNewLinkedLabel);
            this.mv.mark(labelLinkedLabel);
            InlineCodegenUtilsKt.addInlineMarker(this.mv, false);
            return PromisedValueKt.getUnitValue(this);
        } catch (Throwable th) {
            data.getInfos().pop().setBlockInfo(null);
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public PromisedValue visitDoWhileLoop2(IrDoWhileLoop loop, BlockInfo data) {
        PromisedValue promisedValue;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(data, "data");
        InlineCodegenUtilsKt.addInlineMarker(this.mv, true);
        Label labelMarkNewLabel = markNewLabel();
        Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
        Label labelLinkedLabel2 = CodegenUtilKt.linkedLabel();
        LoopInfo loopInfo = new LoopInfo(loop, labelLinkedLabel2, labelLinkedLabel);
        if (Intrinsics.areEqual(loop.getOrigin(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE)) {
            IrExpression body = loop.getBody();
            if (body instanceof IrComposite) {
                IrComposite irComposite = (IrComposite) body;
                if (Intrinsics.areEqual(irComposite.getOrigin(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE) && !irComposite.getStatements().isEmpty()) {
                    IrStatement irStatement = irComposite.getStatements().get(0);
                    if ((irStatement instanceof IrComposite) && Intrinsics.areEqual(((IrComposite) irStatement).getOrigin(), IrStatementOrigin.FOR_LOOP_NEXT.INSTANCE)) {
                        IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irComposite.getStartOffset(), irComposite.getEndOffset(), irComposite.getType(), irComposite.getOrigin(), irComposite.getStatements().subList(1, irComposite.getStatements().size()));
                        LoopInfo loopInfo2 = loopInfo;
                        loopInfo2.setBlockInfo(data);
                        data.getInfos().add(loopInfo2);
                        try {
                            ((PromisedValue) irStatement.accept(this, data)).discard();
                            PseudoInsnsKt.fakeAlwaysFalseIfeq(this.mv, labelLinkedLabel2);
                            PseudoInsnsKt.fakeAlwaysFalseIfeq(this.mv, labelLinkedLabel);
                            ((PromisedValue) irCompositeImpl.accept(this, data)).discard();
                            this.mv.visitLabel(labelLinkedLabel2);
                            markLineNumber(loop.getCondition(), true);
                            PromisedValueKt.coerceToBoolean((PromisedValue) loop.getCondition().accept(this, data)).jumpIfTrue(labelMarkNewLabel);
                            Unit unit = Unit.INSTANCE;
                            data.getInfos().pop().setBlockInfo(null);
                            this.mv.mark(labelLinkedLabel);
                            InlineCodegenUtilsKt.addInlineMarker(this.mv, false);
                            return PromisedValueKt.getUnitValue(this);
                        } catch (Throwable th) {
                            data.getInfos().pop().setBlockInfo(null);
                            throw th;
                        }
                    }
                }
            }
        }
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.mv, labelLinkedLabel2);
        PseudoInsnsKt.fakeAlwaysFalseIfeq(this.mv, labelLinkedLabel);
        LoopInfo loopInfo3 = loopInfo;
        loopInfo3.setBlockInfo(data);
        data.getInfos().add(loopInfo3);
        try {
            IrExpression body2 = loop.getBody();
            if (body2 != null && (promisedValue = (PromisedValue) body2.accept(this, data)) != null) {
                promisedValue.discard();
            }
            this.mv.visitLabel(labelLinkedLabel2);
            markLineNumber(loop.getCondition(), true);
            PromisedValueKt.coerceToBoolean((PromisedValue) loop.getCondition().accept(this, data)).jumpIfTrue(labelMarkNewLabel);
            endUnreferencedDoWhileLocals(data, loop, labelLinkedLabel2);
            Unit unit2 = Unit.INSTANCE;
            data.getInfos().pop().setBlockInfo(null);
            this.mv.mark(labelLinkedLabel);
            InlineCodegenUtilsKt.addInlineMarker(this.mv, false);
            return PromisedValueKt.getUnitValue(this);
        } catch (Throwable th2) {
            data.getInfos().pop().setBlockInfo(null);
            throw th2;
        }
    }

    private final void endUnreferencedDoWhileLocals(BlockInfo blockInfo, IrDoWhileLoop loop, Label continueLabel) {
        final HashSet hashSet = new HashSet();
        IrVisitorsKt.acceptVoid(loop.getCondition(), new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.endUnreferencedDoWhileLocals.1
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
            public void visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                hashSet.add(expression.getSymbol());
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, expression);
            }
        });
        for (VariableInfo variableInfo : blockInfo.getVariables()) {
            if (!hashSet.contains(variableInfo.getDeclaration().getSymbol())) {
                variableInfo.setExplicitEndLabel(continueLabel);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ LoopInfo unwindBlockStack$default(ExpressionCodegen expressionCodegen, Label label, BlockInfo blockInfo, List list, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            list = new ArrayList();
        }
        return expressionCodegen.unwindBlockStack(label, blockInfo, list, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public PromisedValue visitBreakContinue2(final IrBreakContinue jump, BlockInfo data) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(data, "data");
        IrBreakContinue irBreakContinue = jump;
        markLineNumber(irBreakContinue, true);
        this.mv.nop();
        Label label = new Label();
        LoopInfo loopInfoUnwindBlockStack$default = unwindBlockStack$default(this, label, data, null, new Function1<LoopInfo, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$visitBreakContinue$stackElement$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LoopInfo it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getLoop(), jump.getLoop()));
            }
        }, 4, null);
        IrExpression body = jump.getLoop().getBody();
        IrStatement irStatement = null;
        IrBlock irBlock = body instanceof IrBlock ? (IrBlock) body : null;
        if (irBlock != null && (statements = irBlock.getStatements()) != null) {
            irStatement = (IrStatement) CollectionsKt.singleOrNull((List) statements);
        }
        if (irStatement instanceof IrInlinedFunctionBlock) {
            markLineNumber(irBreakContinue, true);
        }
        if (loopInfoUnwindBlockStack$default == null) {
            generateGlobalReturnFlagIfPossible(jump, IrSourceCompilerForInlineKt.nonLocalReturnLabel(jump.getLoop(), jump instanceof IrBreak));
            this.mv.areturn(Type.VOID_TYPE);
        } else {
            PseudoInsnsKt.fixStackAndJump(this.mv, jump instanceof IrBreak ? loopInfoUnwindBlockStack$default.getBreakLabel() : loopInfoUnwindBlockStack$default.getContinueLabel());
            this.mv.mark(label);
        }
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public PromisedValue visitTry2(IrTry aTry, BlockInfo data) {
        TryWithFinallyInfo tryInfo;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(aTry, true);
        if (aTry.getFinallyExpression() != null) {
            IrExpression finallyExpression = aTry.getFinallyExpression();
            Intrinsics.checkNotNull(finallyExpression);
            tryInfo = new TryWithFinallyInfo(finallyExpression);
        } else {
            tryInfo = new TryInfo();
        }
        TryInfo tryInfo2 = tryInfo;
        tryInfo2.setBlockInfo(data);
        data.getInfos().add(tryInfo2);
        try {
            return visitTryWithInfo(aTry, data, tryInfo2);
        } finally {
            data.getInfos().pop().setBlockInfo(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v52, types: [T, java.lang.Integer] */
    private final PromisedValue visitTryWithInfo(IrTry aTry, BlockInfo data, TryInfo tryInfo) throws Throwable {
        Label label;
        ExpressionInfo expressionInfo;
        Type type;
        ExpressionInfo expressionInfo2;
        BlockInfo blockInfo = data;
        Label labelMarkNewLabel = markNewLabel();
        this.mv.nop();
        Type asmType = getAsmType(aTry);
        ExpressionCodegen expressionCodegen = this;
        PromisedValue promisedValue = (PromisedValue) aTry.getTryResult().accept(expressionCodegen, blockInfo);
        boolean zIsUnit = IrTypePredicatesKt.isUnit(aTry.getType());
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (!zIsUnit) {
            promisedValue.materializeAt(asmType, aTry.getType(), true);
            objectRef.element = Integer.valueOf(getFrameMap().enterTemp(asmType));
            this.mv.store(((Number) objectRef.element).intValue(), asmType);
        } else {
            promisedValue.discard();
        }
        Label labelMarkNewLabel2 = markNewLabel();
        List<? extends Pair<? extends Label, ? extends Label>> list = CollectionsKt.toList(tryInfo.getGaps());
        Label label2 = new Label();
        boolean z = tryInfo instanceof TryWithFinallyInfo;
        if (!z) {
            label = label2;
            this.mv.goTo(label);
        } else if (data.getInfos().isEmpty()) {
            label = label2;
        } else {
            ExpressionInfo expressionInfoPop = data.getInfos().pop();
            try {
                Intrinsics.checkNotNull(expressionInfoPop);
                expressionInfo2 = expressionInfoPop;
                label = label2;
                try {
                    genFinallyBlock$default(this, (TryWithFinallyInfo) tryInfo, label2, null, data, null, 16, null);
                    Unit unit = Unit.INSTANCE;
                    data.getInfos().add(expressionInfo2);
                } catch (Throwable th) {
                    th = th;
                    data.getInfos().add(expressionInfo2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                expressionInfo2 = expressionInfoPop;
            }
        }
        List<IrCatch> catches = aTry.getCatches();
        for (IrCatch irCatch : catches) {
            Label labelMarkNewLabel3 = markNewLabel();
            IrVariable catchParameter = irCatch.getCatchParameter();
            Type asmType2 = getAsmType(catchParameter);
            int iEnter = IrCodegenUtilsKt.enter(getFrameMap(), catchParameter, asmType2);
            markLineNumber(irCatch, true);
            this.mv.store(iEnter, asmType2);
            Label labelMarkNewLabel4 = markNewLabel();
            IrExpression result = irCatch.getResult();
            BlockInfo blockInfo2 = new BlockInfo(blockInfo);
            blockInfo2.getVariables().add(new VariableInfo(catchParameter, iEnter, asmType2, labelMarkNewLabel4));
            PromisedValue promisedValue2 = (PromisedValue) result.accept(expressionCodegen, blockInfo2);
            if (objectRef.element != 0) {
                promisedValue2.materializeAt(asmType, aTry.getType(), true);
                this.mv.store(((Number) objectRef.element).intValue(), asmType);
            } else {
                promisedValue2.discard();
            }
            writeLocalVariablesInTable(blockInfo2, markNewLabel());
            if (!z) {
                type = asmType2;
                if (!Intrinsics.areEqual(irCatch, CollectionsKt.last((List) catches))) {
                    this.mv.goTo(label);
                }
            } else if (data.getInfos().isEmpty()) {
                type = asmType2;
            } else {
                ExpressionInfo expressionInfoPop2 = data.getInfos().pop();
                try {
                    Intrinsics.checkNotNull(expressionInfoPop2);
                    type = asmType2;
                    genFinallyBlock$default(this, (TryWithFinallyInfo) tryInfo, label, null, data, null, 16, null);
                    Unit unit2 = Unit.INSTANCE;
                    data.getInfos().add(expressionInfoPop2);
                } catch (Throwable th3) {
                    data.getInfos().add(expressionInfoPop2);
                    throw th3;
                }
            }
            genTryCatchCover(labelMarkNewLabel3, labelMarkNewLabel, labelMarkNewLabel2, list, type.getInternalName());
            blockInfo = data;
        }
        if (z != 0) {
            Label labelMarkNewLabel5 = markNewLabel();
            markLineNumber(firstChild(((TryWithFinallyInfo) tryInfo).getOnExit()), true);
            IrFrameMap frameMap = getFrameMap();
            Type JAVA_THROWABLE_TYPE = AsmTypes.JAVA_THROWABLE_TYPE;
            Intrinsics.checkNotNullExpressionValue(JAVA_THROWABLE_TYPE, "JAVA_THROWABLE_TYPE");
            int iEnterTemp = frameMap.enterTemp(JAVA_THROWABLE_TYPE);
            this.mv.store(iEnterTemp, AsmTypes.JAVA_THROWABLE_TYPE);
            Label labelMarkNewLabel6 = markNewLabel();
            List<? extends Pair<? extends Label, ? extends Label>> list2 = CollectionsKt.toList(tryInfo.getGaps());
            if (!data.getInfos().isEmpty()) {
                ExpressionInfo expressionInfoPop3 = data.getInfos().pop();
                try {
                    Intrinsics.checkNotNull(expressionInfoPop3);
                    expressionInfo = expressionInfoPop3;
                    try {
                        genFinallyBlock$default(this, (TryWithFinallyInfo) tryInfo, null, null, data, null, 16, null);
                        Unit unit3 = Unit.INSTANCE;
                        data.getInfos().add(expressionInfo);
                    } catch (Throwable th4) {
                        th = th4;
                        data.getInfos().add(expressionInfo);
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    expressionInfo = expressionInfoPop3;
                }
            }
            this.mv.load(iEnterTemp, AsmTypes.JAVA_THROWABLE_TYPE);
            IrFrameMap frameMap2 = getFrameMap();
            Type JAVA_THROWABLE_TYPE2 = AsmTypes.JAVA_THROWABLE_TYPE;
            Intrinsics.checkNotNullExpressionValue(JAVA_THROWABLE_TYPE2, "JAVA_THROWABLE_TYPE");
            frameMap2.leaveTemp(JAVA_THROWABLE_TYPE2);
            this.mv.athrow();
            genTryCatchCover(labelMarkNewLabel5, labelMarkNewLabel, labelMarkNewLabel6, list2, null);
        } else {
            asmType = asmType;
        }
        this.mv.mark(label);
        return new PromisedValue(asmType, objectRef, aTry.getType()) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitTryWithInfo.4
            final /* synthetic */ Ref.ObjectRef<Integer> $savedValue;
            final /* synthetic */ Type $tryAsmType;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(ExpressionCodegen.this, asmType, irType);
                this.$tryAsmType = asmType;
                this.$savedValue = objectRef;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(irTarget, "irTarget");
                if (this.$savedValue.element != null) {
                    getMv().load(this.$savedValue.element.intValue(), this.$tryAsmType);
                    ExpressionCodegen.this.getFrameMap().leaveTemp(this.$tryAsmType);
                    super.materializeAt(target, irTarget, castForReified);
                    return;
                }
                PromisedValueKt.materializeAt(PromisedValueKt.getUnitValue(ExpressionCodegen.this), target, irTarget);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void discard() {
                if (this.$savedValue.element != null) {
                    ExpressionCodegen.this.getFrameMap().leaveTemp(this.$tryAsmType);
                }
            }
        };
    }

    private final IrElement firstChild(IrExpression irExpression) {
        if (!(irExpression instanceof IrContainerExpression)) {
            return irExpression;
        }
        IrExpression irExpression2 = (IrStatement) CollectionsKt.firstOrNull((List) ((IrContainerExpression) irExpression).getStatements());
        if (irExpression2 == null) {
            irExpression2 = irExpression;
        }
        return irExpression2;
    }

    private final void genTryCatchCover(Label catchStart, Label tryStart, Label tryEnd, List<? extends Pair<? extends Label, ? extends Label>> tryGaps, String type) {
        Iterator<T> it = tryGaps.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            Label label = (Label) pair.component1();
            Label label2 = (Label) pair.component2();
            this.mv.visitTryCatchBlock(tryStart, label, catchStart, type);
            tryStart = label2;
        }
        this.mv.visitTryCatchBlock(tryStart, tryEnd, catchStart, type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void genFinallyBlock$default(ExpressionCodegen expressionCodegen, TryWithFinallyInfo tryWithFinallyInfo, Label label, Label label2, BlockInfo blockInfo, List list, int i, Object obj) {
        if ((i & 16) != 0) {
            list = new ArrayList();
        }
        expressionCodegen.genFinallyBlock(tryWithFinallyInfo, label, label2, blockInfo, list);
    }

    private final void genFinallyBlock(final TryWithFinallyInfo tryWithFinallyInfo, Label tryCatchBlockEnd, Label afterJumpLabel, final BlockInfo data, List<TryInfo> nestedTryWithoutFinally) {
        Label labelMarkNewLinkedLabel = markNewLinkedLabel();
        for (BlockInfo parent = data; parent != null && !Intrinsics.areEqual(parent, tryWithFinallyInfo.getBlockInfo()); parent = parent.getParent()) {
            parent.setActiveLocalGaps(parent.getActiveLocalGaps() + 1);
            parent.getActiveLocalGaps();
        }
        try {
            this.lineNumberMapper.stashSmapForGivenTry(tryWithFinallyInfo, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen$genFinallyBlock$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ExpressionCodegen expressionCodegen = this.this$0;
                    expressionCodegen.setFinallyDepth(expressionCodegen.getFinallyDepth() + 1);
                    if (this.this$0.isFinallyMarkerRequired()) {
                        InlineCodegenUtilsKt.generateFinallyMarker(this.this$0.getMv(), this.this$0.getFinallyDepth(), true);
                    }
                    ((PromisedValue) tryWithFinallyInfo.getOnExit().accept(this.this$0, data)).discard();
                    if (this.this$0.isFinallyMarkerRequired()) {
                        InlineCodegenUtilsKt.generateFinallyMarker(this.this$0.getMv(), this.this$0.getFinallyDepth(), false);
                    }
                    ExpressionCodegen expressionCodegen2 = this.this$0;
                    expressionCodegen2.setFinallyDepth(expressionCodegen2.getFinallyDepth() - 1);
                }
            });
            if (tryCatchBlockEnd != null) {
                markLineNumber(tryWithFinallyInfo.getOnExit(), false);
                this.mv.goTo(tryCatchBlockEnd);
            }
            for (BlockInfo parent2 = data; parent2 != null && !Intrinsics.areEqual(parent2, tryWithFinallyInfo.getBlockInfo()); parent2 = parent2.getParent()) {
                parent2.setActiveLocalGaps(parent2.getActiveLocalGaps() - 1);
                parent2.getActiveLocalGaps();
            }
            Label labelMarkNewLinkedLabel2 = markNewLinkedLabel();
            splitLocalVariableRangesByFinallyBlocks(data, tryWithFinallyInfo, labelMarkNewLinkedLabel, labelMarkNewLinkedLabel2);
            if (afterJumpLabel == null) {
                afterJumpLabel = labelMarkNewLinkedLabel2;
            }
            tryWithFinallyInfo.getGaps().add(TuplesKt.to(labelMarkNewLinkedLabel, afterJumpLabel));
            if (this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperFinally)) {
                Iterator<TryInfo> it = nestedTryWithoutFinally.iterator();
                while (it.hasNext()) {
                    it.next().getGaps().add(TuplesKt.to(labelMarkNewLinkedLabel, afterJumpLabel));
                }
            }
        } catch (Throwable th) {
            while (data != null && !Intrinsics.areEqual(data, tryWithFinallyInfo.getBlockInfo())) {
                data.setActiveLocalGaps(data.getActiveLocalGaps() - 1);
                data.getActiveLocalGaps();
                data = data.getParent();
            }
            throw th;
        }
    }

    public final void generateFinallyBlocksIfNeeded(Type returnType, Label afterReturnLabel, BlockInfo data, final Label jumpLabel) {
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(afterReturnLabel, "afterReturnLabel");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.hasFinallyBlocks()) {
            if (!Intrinsics.areEqual(Type.VOID_TYPE, returnType)) {
                int iEnterTemp = getFrameMap().enterTemp(returnType);
                this.mv.store(iEnterTemp, returnType);
                unwindBlockStack$default(this, afterReturnLabel, data, null, new Function1<LoopInfo, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.generateFinallyBlocksIfNeeded.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(LoopInfo it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it.getBreakLabel(), jumpLabel) || Intrinsics.areEqual(it.getContinueLabel(), jumpLabel));
                    }
                }, 4, null);
                this.mv.load(iEnterTemp, returnType);
                getFrameMap().leaveTemp(returnType);
                return;
            }
            unwindBlockStack$default(this, afterReturnLabel, data, null, new Function1<LoopInfo, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.generateFinallyBlocksIfNeeded.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(LoopInfo it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(it.getBreakLabel(), jumpLabel) || Intrinsics.areEqual(it.getContinueLabel(), jumpLabel));
                }
            }, 4, null);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public PromisedValue visitThrow2(IrThrow expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        markLineNumber(expression, true);
        PromisedValue promisedValue = (PromisedValue) expression.getValue().accept(this, data);
        if (IrTypeUtilsKt.isSubtypeOfClass(promisedValue.getIrType(), this.context.getIrBuiltIns().getThrowableClass())) {
            PromisedValueKt.materialize(promisedValue);
        } else {
            PromisedValueKt.materializeAt(promisedValue, this.context.getIrBuiltIns().getThrowableType());
        }
        this.mv.athrow();
        return PromisedValueKt.getUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public PromisedValue visitStringConcatenation2(IrStringConcatenation expression, BlockInfo data) {
        Type type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        this.context.getConfig().getRuntimeStringConcat().isDynamic();
        StringConcatGenerator stringConcatGenerator = new StringConcatGenerator(this.context.getConfig().getRuntimeStringConcat(), this.mv);
        for (IrExpression irExpression : expression.getArguments()) {
            if (irExpression instanceof IrConst) {
                IrConst irConst = (IrConst) irExpression;
                IrConstKind kind = irConst.getKind();
                if (Intrinsics.areEqual(kind, IrConstKind.Boolean.INSTANCE)) {
                    type = Type.BOOLEAN_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
                    type = Type.CHAR_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
                    type = Type.INT_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
                    type = Type.LONG_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
                    type = Type.FLOAT_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
                    type = Type.DOUBLE_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
                    type = Type.BYTE_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
                    type = Type.SHORT_TYPE;
                } else if (Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
                    type = AsmTypes.JAVA_STRING_TYPE;
                } else {
                    if (!Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    type = AsmTypes.OBJECT_TYPE;
                }
                StackValue stackValueConstant = StackValue.constant(irConst.getValue(), type, null);
                Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
                StringConcatGenerator.putValueOrProcessConstant$default(stringConcatGenerator, stackValueConstant, null, null, 6, null);
            } else {
                PromisedValue promisedValue = (PromisedValue) irExpression.accept(this, data);
                Type type2 = Intrinsics.areEqual(promisedValue.getType(), Type.VOID_TYPE) ? AsmTypes.UNIT_TYPE : promisedValue.getType();
                Intrinsics.checkNotNull(type2);
                PromisedValueKt.materializeAt(promisedValue, type2, promisedValue.getIrType());
                stringConcatGenerator.invokeAppend(type2);
            }
        }
        stringConcatGenerator.genToString();
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        return new MaterialValue(this, JAVA_STRING_TYPE, this.context.getIrBuiltIns().getStringType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public PromisedValue visitGetClass2(IrGetClass expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return generateClassLiteralReference(expression, true, false, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public PromisedValue visitClassReference2(IrClassReference expression, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return generateClassLiteralReference(expression, true, false, data);
    }

    public final MaterialValue generateClassLiteralReference(IrExpression classReference, boolean wrapIntoKClass, boolean wrapPrimitives, BlockInfo data) {
        Intrinsics.checkNotNullParameter(classReference, "classReference");
        Intrinsics.checkNotNullParameter(data, "data");
        if (classReference instanceof IrGetClass) {
            JavaClassProperty.INSTANCE.invokeWith((PromisedValue) ((IrGetClass) classReference).getArgument().accept(this, data), wrapPrimitives);
        } else if (classReference instanceof IrClassReference) {
            IrType classType = ((IrClassReference) classReference).getClassType();
            if (IrTypesKt.getClassifierOrNull(classType) instanceof IrTypeParameterSymbol) {
                BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(this, classType, ReifiedTypeInliner.OperationKind.JAVA_CLASS);
            }
            INSTANCE.generateClassInstance$backend_jvm_codegen(this.mv, classType, this.typeMapper, wrapPrimitives);
        } else {
            throw new AssertionError("not an IrGetClass or IrClassReference: " + DumpIrTreeKt.dump$default(classReference, null, 1, null));
        }
        if (wrapIntoKClass) {
            AsmUtil.wrapJavaClassIntoKClass(this.mv);
        }
        return getOnStack(classReference);
    }

    private final IrCallGenerator getOrCreateCallGenerator(IrFunctionAccessExpression element, BlockInfo data, JvmMethodSignature signature) {
        Map mapEmptyMap;
        if (!JvmIrInlineUtilsKt.isInlineFunctionCall(element.getSymbol().getOwner(), this.context) || (JvmIrUtilsKt.getFileParent(this.classCodegen.getIrClass()).getFileEntry() instanceof MultifileFacadeFileEntry) || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.JVM_STATIC_WRAPPER.INSTANCE) || JvmIrCoroutineUtilsKt.isInvokeSuspendOfContinuation(this.irFunction)) {
            return IrCallGenerator.DefaultCallGenerator.INSTANCE;
        }
        if (Intrinsics.areEqual(element.getOrigin(), JvmLoweredStatementOrigin.DEFAULT_STUB_CALL_TO_IMPLEMENTATION.INSTANCE)) {
            return IrInlineDefaultCodegen.INSTANCE;
        }
        IrFunction owner = element.getSymbol().getOwner();
        IrFunction parentAsClass = owner instanceof IrConstructor ? IrUtilsKt.getParentAsClass(owner) : owner;
        if (element.getTypeArgumentsCount() == 0) {
            mapEmptyMap = MapsKt.emptyMap();
        } else {
            List<IrTypeParameter> typeParameters = ((IrTypeParametersContainer) parentAsClass).getTypeParameters();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
            for (IrTypeParameter irTypeParameter : typeParameters) {
                IrTypeParameterSymbol symbol = irTypeParameter.getSymbol();
                IrSimpleType typeArgument = element.getTypeArgument(irTypeParameter.getIndex());
                if (typeArgument == null) {
                    typeArgument = IrTypesKt.getDefaultType(irTypeParameter);
                }
                Pair pair = TuplesKt.to(symbol, typeArgument);
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            mapEmptyMap = linkedHashMap;
        }
        TypeParameterMappings typeParameterMappings = new TypeParameterMappings(this.typeMapper.getTypeSystem(), mapEmptyMap, false, new ExpressionCodegen$getOrCreateCallGenerator$mappings$1(this.typeMapper));
        return new IrInlineCodegen(this, this.state, owner, signature, typeParameterMappings, new IrSourceCompilerForInline(this.state, element, owner, this, data), new ReifiedTypeInliner(typeParameterMappings, new IrInlineIntrinsicsSupport(this.classCodegen, element, JvmIrUtilsKt.getFileParent(this.irFunction)), this.context.getTypeSystem(), this.state.getLanguageVersionSettings(), this.state.getConfig().getUnifiedNullChecks()));
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void consumeReifiedOperationMarker(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        if (!(typeParameter instanceof IrTypeParameterSymbol)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) typeParameter;
        if (Intrinsics.areEqual(this.irFunction, irTypeParameterSymbol.getOwner().getParent())) {
            return;
        }
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = this.reifiedTypeParametersUsages;
        String strAsString = irTypeParameterSymbol.getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        reifiedTypeParametersUsages.addUsedReifiedParameter(strAsString);
    }

    @Override // org.jetbrains.kotlin.codegen.BaseExpressionCodegen
    public void propagateChildReifiedTypeParametersUsages(ReifiedTypeParametersUsages reifiedTypeParametersUsages) {
        Intrinsics.checkNotNullParameter(reifiedTypeParametersUsages, "reifiedTypeParametersUsages");
        this.reifiedTypeParametersUsages.propagateChildUsagesWithinContext(reifiedTypeParametersUsages, new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.propagateChildReifiedTypeParametersUsages.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                List<IrTypeParameter> typeParameters = ExpressionCodegen.this.getIrFunction().getTypeParameters();
                ArrayList arrayList = new ArrayList();
                for (Object obj : typeParameters) {
                    if (((IrTypeParameter) obj).getIsReified()) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((IrTypeParameter) it.next()).getName().asString());
                }
                return CollectionsKt.toSet(arrayList3);
            }
        });
    }

    public final boolean isFinallyMarkerRequired() {
        return this.irFunction.getIsInline() || Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE);
    }

    /* JADX INFO: compiled from: ExpressionCodegen.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen$Companion;", "", "()V", "generateClassInstance", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "classType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "wrapPrimitives", "", "generateClassInstance$backend_jvm_codegen", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void generateClassInstance$backend_jvm_codegen(InstructionAdapter v, IrType classType, IrTypeMapper typeMapper, boolean wrapPrimitives) {
            IrClass irClass;
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(classType, "classType");
            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
            Type typeMapType$default = IrTypeMapper.mapType$default(typeMapper, classType, null, null, false, 14, null);
            if (wrapPrimitives || (((irClass = IrTypesKt.getClass(classType)) != null && IrDeclarationsKt.isSingleFieldValueClass(irClass)) || !AsmUtil.isPrimitive(typeMapType$default))) {
                v.aconst(typeMapper.boxType(classType));
            } else {
                v.getstatic(AsmUtil.boxType(typeMapType$default).getInternalName(), "TYPE", "Ljava/lang/Class;");
            }
        }
    }

    private final void splitLocalVariableRangesByFinallyBlocks(BlockInfo info, TryWithFinallyInfo tryWithFinallyInfo, Label gapStart, Label restartLabel) {
        while (info != null && !Intrinsics.areEqual(info, tryWithFinallyInfo.getBlockInfo())) {
            if (info.getActiveLocalGaps() == 0) {
                for (VariableInfo variableInfo : info.getVariables()) {
                    if (isVisibleInLVT(variableInfo.getDeclaration())) {
                        variableInfo.getGaps().add(new Gap(gapStart, restartLabel));
                    }
                }
            }
            info = info.getParent();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final LoopInfo unwindBlockStack(Label endLabel, BlockInfo data, List<TryInfo> nestedTryWithoutFinally, Function1<? super LoopInfo, Boolean> stop) {
        LoopInfo loopInfoUnwindBlockStack;
        if (data.getInfos().isEmpty()) {
            return null;
        }
        ExpressionInfo expressionInfoPop = data.getInfos().pop();
        try {
            Intrinsics.checkNotNull(expressionInfoPop);
            if (expressionInfoPop instanceof TryWithFinallyInfo) {
                genFinallyBlock((TryWithFinallyInfo) expressionInfoPop, null, endLabel, data, nestedTryWithoutFinally);
                nestedTryWithoutFinally.clear();
            } else {
                if (!(expressionInfoPop instanceof TryInfo)) {
                    if ((expressionInfoPop instanceof LoopInfo) && stop.invoke(expressionInfoPop).booleanValue()) {
                        loopInfoUnwindBlockStack = (LoopInfo) expressionInfoPop;
                    }
                    return loopInfoUnwindBlockStack;
                }
                nestedTryWithoutFinally.add(expressionInfoPop);
            }
            loopInfoUnwindBlockStack = unwindBlockStack(endLabel, data, nestedTryWithoutFinally, stop);
            return loopInfoUnwindBlockStack;
        } finally {
            data.getInfos().add(expressionInfoPop);
        }
    }
}
