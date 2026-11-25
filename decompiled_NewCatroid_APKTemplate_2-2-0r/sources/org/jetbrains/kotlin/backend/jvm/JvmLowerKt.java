package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DefaultParameterCleaner;
import org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover;
import org.jetbrains.kotlin.backend.common.lower.FlattenStringConcatenationLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.InitializersCleanupLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassConstructorCallsLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesMemberBodyLowering;
import org.jetbrains.kotlin.backend.common.lower.KotlinNothingValueExceptionLowering;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.lower.PropertiesLowering;
import org.jetbrains.kotlin.backend.common.lower.ProvisionalFunctionExpressionLowering;
import org.jetbrains.kotlin.backend.common.lower.RangeContainsLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.SharedVariablesLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining;
import org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver;
import org.jetbrains.kotlin.backend.common.lower.loops.ForLoopsLoweringKt;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PerformByIrFileKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.AddSuperQualifierToJavaFieldAccessLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.AdditionalClassAnnotationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.AnnotationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.AnonymousObjectSuperConstructorLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ApiVersionIsAtLeastEvaluationLowering;
import org.jetbrains.kotlin.backend.jvm.lower.AssertionLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.BridgeLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.CreateSeparateCallForInlinedLambdasLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.DirectInvokeLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.EnumClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.EnumExternalEntriesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ExternalPackageParentPatcherLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FakeLocalVariablesForBytecodeInlinerLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FakeLocalVariablesForIrInlinerLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FileClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FragmentLocalFunctionPatchLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FragmentSharedVariablesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FunctionNVarargBridgeLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt;
import org.jetbrains.kotlin.backend.jvm.lower.InheritedDefaultMethodsOnClassesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.InlineCallableReferenceToLambdaKt;
import org.jetbrains.kotlin.backend.jvm.lower.InlinedClassReferencesBoxingLowering;
import org.jetbrains.kotlin.backend.jvm.lower.JvmAnnotationImplementationTransformerKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmArgumentNullabilityAssertionsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmBuiltInsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmDefaultConstructorLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmInlineClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmInventNamesForLocalClassesKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmLateinitLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmMultiFieldValueClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmOverloadsAnnotationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmSafeCallChainFoldingLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmSingleAbstractMethodLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmStaticAnnotationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.JvmStringConcatenationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.MainMethodGenerationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.MakePropertyDelegateMethodsStaticLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.MappedEnumWhenLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.MarkNecessaryInlinedClassesAsRegeneratedLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.MoveCompanionObjectFieldsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ObjectClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.PolymorphicSignatureLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ProcessOptionalAnnotationsKt;
import org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceDelegationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.RecordEnclosingMethodsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ReflectiveAccessKt;
import org.jetbrains.kotlin.backend.jvm.lower.RemoveDuplicatedInlinedLocalClassesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.RenameFieldsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.RepeatedAnnotationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ReplaceKFunctionInvokeWithFunctionInvokeKt;
import org.jetbrains.kotlin.backend.jvm.lower.ReplaceNumberToCharCallSitesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ResolveInlineCallsKt;
import org.jetbrains.kotlin.backend.jvm.lower.ScriptLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.SerializeIrPhaseKt;
import org.jetbrains.kotlin.backend.jvm.lower.SingletonOrConstantDelegationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.SingletonReferencesLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.StaticCallableReferenceLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.StaticDefaultFunctionLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.SuspendLambdaLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.TailCallOptimizationLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.ToArrayLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.TypeAliasAnnotationMethodsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.TypeOperatorLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.UniqueLoopLabelsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
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
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.NameUtils;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010 \n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001ah\u00103\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u00042\u0006\u00104\u001a\u0002052:\u00106\u001a6\u00122\u00120\u0012\u0004\u0012\u000205\u0012&\u0012$\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u00040\u001c070\u001cH\u0002\u001aV\u00108\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003092:\u0010:\u001a6\u00122\u00120\u0012\u0004\u0012\u000205\u0012&\u0012$\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u00040\u001c070\u001cH\u0002\u001a$\u0010;\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\u0004H\u0002\u001a$\u0010<\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\u0004H\u0002\u001a\f\u0010=\u001a\u000200*\u00020\u0002H\u0000\"*\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000\"*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"-\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\"*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"0\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r\"0\u0010\u0012\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r\"*\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0017\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0019\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u001a\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"0\u0010\u001b\u001a$\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u00040\u001cX\u0082\u0004¢\u0006\u0002\n\u0000\"-\u0010\u001d\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\r\"*\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"-\u0010 \u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\r\"*\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"0\u0010#\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\r\"\u000e\u0010%\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000\"0\u0010&\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\r\"*\u0010(\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010)\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010*\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010+\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010,\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010-\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010.\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010/\u001a\u000200*\u0002018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b/\u00102¨\u0006>"}, d2 = {"apiVersionIsAtLeastEvaluationPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "arrayConstructorPhase", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "checkParentPhases", "", "constEvaluationPhase", "defaultArgumentCleanerPhase", "getDefaultArgumentCleanerPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "defaultArgumentInjectorPhase", "defaultArgumentStubPhase", "expectDeclarationsRemovingPhase", "getExpectDeclarationsRemovingPhase", "functionInliningPhase", "getFunctionInliningPhase", "initializersCleanupPhase", "initializersPhase", "inlinedClassReferencesBoxingPhase", "innerClassConstructorCallsPhase", "innerClassesMemberBodyPhase", "innerClassesPhase", "interfacePhase", "jvmFilePhases", "", "jvmFragmentLoweringPhases", "getJvmFragmentLoweringPhases", "jvmLocalClassExtractionPhase", "jvmLoweringPhases", "getJvmLoweringPhases", "kotlinNothingValueExceptionPhase", "localDeclarationsPhase", "getLocalDeclarationsPhase", "patchParentPhases", "propertiesPhase", "getPropertiesPhase", "provisionalFunctionExpressionPhase", "returnableBlocksPhase", "staticInitializersPhase", "syntheticAccessorPhase", "tailrecPhase", "validateIrAfterLowering", "validateIrBeforeLowering", "isGeneratedLambdaClass", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "buildJvmLoweringPhases", "name", "", "phases", "Lkotlin/Pair;", "buildLoweringsPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "perModuleLowerings", "makeCheckParentsPhase", "makePatchParentsPhase", "irInlinerIsEnabled", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmLowerKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> apiVersionIsAtLeastEvaluationPhase;
    private static final NamedCompilerPhase<CommonBackendContext, IrFile> arrayConstructorPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> constEvaluationPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> defaultArgumentCleanerPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> defaultArgumentInjectorPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> defaultArgumentStubPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> expectDeclarationsRemovingPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> functionInliningPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> initializersCleanupPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> initializersPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> inlinedClassReferencesBoxingPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> innerClassConstructorCallsPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> innerClassesMemberBodyPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> innerClassesPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> interfacePhase;
    private static final List<NamedCompilerPhase<JvmBackendContext, IrFile>> jvmFilePhases;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> jvmFragmentLoweringPhases;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> jvmLocalClassExtractionPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> jvmLoweringPhases;
    private static final NamedCompilerPhase<CommonBackendContext, IrFile> kotlinNothingValueExceptionPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> localDeclarationsPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> propertiesPhase;
    private static final NamedCompilerPhase<CommonBackendContext, IrFile> provisionalFunctionExpressionPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> returnableBlocksPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> staticInitializersPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> syntheticAccessorPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> tailrecPhase;
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> validateIrBeforeLowering = PhaseBuildersKt.makeCustomPhase(JvmLowerKt$validateIrBeforeLowering$1.INSTANCE, "ValidateIrBeforeLowering", "Validate IR before lowering", (504 & 8) != 0 ? SetsKt.emptySet() : null, (504 & 16) != 0 ? SetsKt.emptySet() : null, (504 & 32) != 0 ? SetsKt.emptySet() : null, (504 & 64) != 0 ? SetsKt.emptySet() : null, (504 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null, (504 & 256) != 0 ? 1 : 0);
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> validateIrAfterLowering = PhaseBuildersKt.makeCustomPhase(JvmLowerKt$validateIrAfterLowering$1.INSTANCE, "ValidateIrAfterLowering", "Validate IR after lowering", (504 & 8) != 0 ? SetsKt.emptySet() : null, (504 & 16) != 0 ? SetsKt.emptySet() : null, (504 & 32) != 0 ? SetsKt.emptySet() : null, (504 & 64) != 0 ? SetsKt.emptySet() : null, (504 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null, (504 & 256) != 0 ? 1 : 0);

    public static final boolean irInlinerIsEnabled(JvmBackendContext jvmBackendContext) {
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        return jvmBackendContext.getConfiguration().getBoolean(JVMConfigurationKeys.ENABLE_IR_INLINER);
    }

    static {
        NamedCompilerPhase<CommonBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase = PhaseBuildersKt.makeIrFilePhase(new Function1<CommonBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$provisionalFunctionExpressionPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(CommonBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ProvisionalFunctionExpressionLowering();
            }
        }, "FunctionExpression", "Transform IrFunctionExpression to a local function reference", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        provisionalFunctionExpressionPhase = namedCompilerPhaseMakeIrFilePhase;
        NamedCompilerPhase<CommonBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase2 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$arrayConstructorPhase$1.INSTANCE, "ArrayConstructor", "Transform `Array(size) { index -> value }` into a loop", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        arrayConstructorPhase = namedCompilerPhaseMakeIrFilePhase2;
        NamedCompilerPhase<JvmBackendContext, IrModuleFragment> namedCompilerPhaseMakeIrModulePhase = PhaseBuildersKt.makeIrModulePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$expectDeclarationsRemovingPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                if (context.getState().getConfiguration().getBoolean(CommonConfigurationKeys.USE_FIR)) {
                    return FileLoweringPass.Empty.INSTANCE;
                }
                return new ExpectDeclarationRemover(context);
            }
        }, "ExpectDeclarationsRemoving", "Remove expect declaration from module fragment", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        expectDeclarationsRemovingPhase = namedCompilerPhaseMakeIrModulePhase;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase3 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$propertiesPhase$1.INSTANCE, "Properties", "Move fields and accessors for properties to their classes, replace calls to default property accessors with field accesses, remove unused accessors and create synthetic methods for property annotations", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : SetsKt.setOf(new JvmLowerKt$propertiesPhase$2(PropertiesLowering.INSTANCE)), (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        propertiesPhase = namedCompilerPhaseMakeIrFilePhase3;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase4 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$localDeclarationsPhase$1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$localDeclarationsPhase$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: JvmLower.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<String, String> {
                AnonymousClass1(Object obj) {
                    super(1, obj);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "sanitizeAsJavaIdentifier(Ljava/lang/String;)Ljava/lang/String;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "sanitizeAsJavaIdentifier";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(NameUtils.class);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return NameUtils.sanitizeAsJavaIdentifier(p0);
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                JvmBackendContext jvmBackendContext = context;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(NameUtils.INSTANCE);
                JvmVisibilityPolicy jvmVisibilityPolicy = new JvmVisibilityPolicy();
                final Map<IrFunction, JvmBackendContext.LocalFunctionData> localDeclarationsLoweringData = context.getLocalDeclarationsLoweringData();
                return new LocalDeclarationsLowering(jvmBackendContext, anonymousClass1, jvmVisibilityPolicy, false, true, true, localDeclarationsLoweringData != null ? new Function1<LocalDeclarationsLowering.IntermediateDatastructures, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$localDeclarationsPhase$1$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(LocalDeclarationsLowering.IntermediateDatastructures intermediateDatastructures) {
                        invoke2(intermediateDatastructures);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(LocalDeclarationsLowering.IntermediateDatastructures data) {
                        Intrinsics.checkNotNullParameter(data, "data");
                        Map<IrFunction, LocalDeclarationsLowering.LocalFunctionContext> localFunctions = data.getLocalFunctions();
                        Map<IrFunction, JvmBackendContext.LocalFunctionData> map = localDeclarationsLoweringData;
                        for (Map.Entry<IrFunction, LocalDeclarationsLowering.LocalFunctionContext> entry : localFunctions.entrySet()) {
                            map.put(entry.getKey(), new JvmBackendContext.LocalFunctionData(entry.getValue(), data.getNewParameterToOld(), data.getNewParameterToCaptured()));
                        }
                    }
                } : null, 8, null);
            }
        }, "JvmLocalDeclarations", "Move local declarations to classes", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{FunctionReferenceLoweringKt.getFunctionReferencePhase(), SharedVariablesLoweringKt.getSharedVariablesPhase()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        localDeclarationsPhase = namedCompilerPhaseMakeIrFilePhase4;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase5 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$jvmLocalClassExtractionPhase$1.INSTANCE, "JvmLocalClassExtraction", "Move local classes from field initializers and anonymous init blocks into the containing class", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        jvmLocalClassExtractionPhase = namedCompilerPhaseMakeIrFilePhase5;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase6 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$defaultArgumentStubPhase$1.INSTANCE, "DefaultArgumentsStubGenerator", "Generate synthetic stubs for functions with default parameter values", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase4), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        defaultArgumentStubPhase = namedCompilerPhaseMakeIrFilePhase6;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase7 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$defaultArgumentCleanerPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new DefaultParameterCleaner(context, true);
            }
        }, "DefaultParameterCleaner", "Replace default values arguments with stubs", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase6), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        defaultArgumentCleanerPhase = namedCompilerPhaseMakeIrFilePhase7;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase8 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$defaultArgumentInjectorPhase$1.INSTANCE, "DefaultParameterInjector", "Transform calls with default arguments into calls to stubs", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{FunctionReferenceLoweringKt.getFunctionReferencePhase(), InlineCallableReferenceToLambdaKt.getInlineCallableReferenceToLambdaPhase()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        defaultArgumentInjectorPhase = namedCompilerPhaseMakeIrFilePhase8;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase9 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$interfacePhase$1.INSTANCE, "Interface", "Move default implementations of interface members to DefaultImpls class", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase8), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        interfacePhase = namedCompilerPhaseMakeIrFilePhase9;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase10 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$innerClassesPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClasses", "Add 'outer this' fields to inner classes", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase4), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        innerClassesPhase = namedCompilerPhaseMakeIrFilePhase10;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase11 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$innerClassesMemberBodyPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesMemberBodyLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassesMemberBody", "Replace `this` with 'outer this' field references", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase10), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        innerClassesMemberBodyPhase = namedCompilerPhaseMakeIrFilePhase11;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase12 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$innerClassConstructorCallsPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassConstructorCallsLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassConstructorCalls", "Handle constructor calls for inner classes", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        innerClassConstructorCallsPhase = namedCompilerPhaseMakeIrFilePhase12;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase13 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$staticInitializersPhase$1.INSTANCE, "StaticInitializers", "Move code from object init blocks and static field initializers to a new <clinit> function", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        staticInitializersPhase = namedCompilerPhaseMakeIrFilePhase13;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase14 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$initializersPhase$1.INSTANCE, "Initializers", "Merge init blocks and field initializers into constructors", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase5), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        initializersPhase = namedCompilerPhaseMakeIrFilePhase14;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase15 = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$initializersCleanupPhase$2
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InitializersCleanupLowering(context, new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$initializersCleanupPhase$2.1
                    /* JADX WARN: Code duplicated, block: B:12:0x0026  */
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(IrField it) {
                        boolean z;
                        IrPropertySymbol correspondingPropertySymbol;
                        IrProperty owner;
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (JvmIrUtilsKt.constantValue(it) == null) {
                            z = true;
                            if (it.getIsStatic() && (correspondingPropertySymbol = it.getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) {
                                z = false;
                            }
                        } else {
                            z = false;
                        }
                        return Boolean.valueOf(z);
                    }
                });
            }
        }, "InitializersCleanup", "Remove non-static anonymous initializers and non-constant non-static field init expressions", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrFilePhase14), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : SetsKt.setOf(new Function1<IrFile, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$initializersCleanupPhase$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFile irFile) {
                invoke2(irFile);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFile irFile) {
                Intrinsics.checkNotNullParameter(irFile, "irFile");
                IrVisitorsKt.acceptVoid(irFile, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$initializersCleanupPhase$1.1
                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                    /* JADX INFO: renamed from: visitAnonymousInitializer */
                    public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                        visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                        return Unit.INSTANCE;
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
                    public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile2, Object obj) {
                        visitFile(irFile2, (Void) obj);
                        return Unit.INSTANCE;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitFile(IrFile irFile2) {
                        IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile2);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                    public void visitFile(IrFile irFile2, Void r2) {
                        IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile2, r2);
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
                    public void visitAnonymousInitializer(IrAnonymousInitializer declaration) {
                        Intrinsics.checkNotNullParameter(declaration, "declaration");
                        throw new IllegalStateException("No anonymous initializers should remain at this stage".toString());
                    }
                });
            }
        }), (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        initializersCleanupPhase = namedCompilerPhaseMakeIrFilePhase15;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase16 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$returnableBlocksPhase$1.INSTANCE, "ReturnableBlock", "Replace returnable blocks with do-while(false) loops", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeIrFilePhase2, AssertionLoweringKt.getAssertionPhase(), DirectInvokeLoweringKt.getDirectInvokeLowering()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        returnableBlocksPhase = namedCompilerPhaseMakeIrFilePhase16;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase17 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$syntheticAccessorPhase$1.INSTANCE, "SyntheticAccessor", "Introduce synthetic accessors", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{ObjectClassLoweringKt.getObjectClassPhase(), StaticDefaultFunctionLoweringKt.getStaticDefaultFunctionPhase(), namedCompilerPhaseMakeIrFilePhase9}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        syntheticAccessorPhase = namedCompilerPhaseMakeIrFilePhase17;
        NamedCompilerPhase<JvmBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase18 = PhaseBuildersKt.makeIrFilePhase(JvmLowerKt$tailrecPhase$1.INSTANCE, "Tailrec", "Handle tailrec calls", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        tailrecPhase = namedCompilerPhaseMakeIrFilePhase18;
        NamedCompilerPhase<CommonBackendContext, IrFile> namedCompilerPhaseMakeIrFilePhase19 = PhaseBuildersKt.makeIrFilePhase(new Function1<CommonBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$kotlinNothingValueExceptionPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(CommonBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new KotlinNothingValueExceptionLowering(context, new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$kotlinNothingValueExceptionPhase$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(IrDeclaration it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf((it instanceof IrFunction) && !JvmIrCoroutineUtilsKt.shouldContainSuspendMarkers((IrFunction) it));
                    }
                });
            }
        }, "KotlinNothingValueException", "Throw proper exception for calls returning value of type 'kotlin.Nothing'", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        kotlinNothingValueExceptionPhase = namedCompilerPhaseMakeIrFilePhase19;
        NamedCompilerPhase<JvmBackendContext, IrModuleFragment> namedCompilerPhaseMakeIrModulePhase2 = PhaseBuildersKt.makeIrModulePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$functionInliningPhase$1

            /* JADX INFO: compiled from: JvmLower.kt */
            @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0016¨\u0006\t"}, d2 = {"org/jetbrains/kotlin/backend/jvm/JvmLowerKt$functionInliningPhase$1$JvmInlineFunctionResolver", "Lorg/jetbrains/kotlin/backend/common/lower/inline/InlineFunctionResolver;", "()V", "getFunctionDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getFunctionSymbol", "irFunction", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class JvmInlineFunctionResolver implements InlineFunctionResolver {
                @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
                public IrFunction getFunctionDeclaration(IrFunctionSymbol symbol) {
                    IrSimpleFunction irSimpleFunction;
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    IrFunction owner = symbol.getOwner();
                    IrSimpleFunction irSimpleFunction2 = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
                    return (irSimpleFunction2 == null || (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction2, false, null, 3, null)) == null) ? symbol.getOwner() : irSimpleFunction;
                }

                @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
                public IrFunctionSymbol getFunctionSymbol(IrFunction irFunction) {
                    Intrinsics.checkNotNullParameter(irFunction, "irFunction");
                    return irFunction.getSymbol();
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                if (!JvmLowerKt.irInlinerIsEnabled(context)) {
                    return FileLoweringPass.Empty.INSTANCE;
                }
                return new FunctionInlining(context, new JvmInlineFunctionResolver(), context.getInnerClassesSupport(), false, true, true, true, false, false, 392, null);
            }
        }, "FunctionInliningPhase", "Perform function inlining", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrModulePhase), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        functionInliningPhase = namedCompilerPhaseMakeIrModulePhase2;
        apiVersionIsAtLeastEvaluationPhase = PhaseBuildersKt.makeIrModulePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$apiVersionIsAtLeastEvaluationPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return !JvmLowerKt.irInlinerIsEnabled(context) ? FileLoweringPass.Empty.INSTANCE : new ApiVersionIsAtLeastEvaluationLowering(context);
            }
        }, "ApiVersionIsAtLeastEvaluationLowering", "Evaluate inlined invocations of `apiVersionIsAtLeast`", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(namedCompilerPhaseMakeIrModulePhase2), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        inlinedClassReferencesBoxingPhase = PhaseBuildersKt.makeIrModulePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmLowerKt$inlinedClassReferencesBoxingPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JvmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return !JvmLowerKt.irInlinerIsEnabled(context) ? FileLoweringPass.Empty.INSTANCE : new InlinedClassReferencesBoxingLowering(context);
            }
        }, "InlinedClassReferencesBoxingLowering", "Replace inlined primitive types in class references with boxed versions", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeIrModulePhase2, MarkNecessaryInlinedClassesAsRegeneratedLoweringKt.getMarkNecessaryInlinedClassesAsRegenerated()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        constEvaluationPhase = PhaseBuildersKt.makeIrModulePhase(JvmLowerKt$constEvaluationPhase$1.INSTANCE, "ConstEvaluationLowering", "Evaluate functions that are marked as `IntrinsicConstEvaluation`", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
        List<NamedCompilerPhase<JvmBackendContext, IrFile>> listListOf = CollectionsKt.listOf((Object[]) new NamedCompilerPhase[]{TypeAliasAnnotationMethodsLoweringKt.getTypeAliasAnnotationMethodsPhase(), namedCompilerPhaseMakeIrFilePhase, JvmOverloadsAnnotationLoweringKt.getJvmOverloadsAnnotationPhase(), MainMethodGenerationLoweringKt.getMainMethodGenerationPhase(), AnnotationLoweringKt.getAnnotationPhase(), JvmAnnotationImplementationTransformerKt.getAnnotationImplementationPhase(), PolymorphicSignatureLoweringKt.getPolymorphicSignaturePhase(), VarargLoweringKt.getVarargPhase(), JvmLateinitLoweringKt.getJvmLateinitLowering(), JvmInventNamesForLocalClassesKt.getInventNamesForLocalClassesPhase(), InlineCallableReferenceToLambdaKt.getInlineCallableReferenceToLambdaPhase(), DirectInvokeLoweringKt.getDirectInvokeLowering(), FunctionReferenceLoweringKt.getFunctionReferencePhase(), SuspendLambdaLoweringKt.getSuspendLambdaPhase(), PropertyReferenceDelegationLoweringKt.getPropertyReferenceDelegationPhase(), SingletonOrConstantDelegationLoweringKt.getSingletonOrConstantDelegationPhase(), PropertyReferenceLoweringKt.getPropertyReferencePhase(), namedCompilerPhaseMakeIrFilePhase2, MoveCompanionObjectFieldsLoweringKt.getMoveOrCopyCompanionObjectFieldsPhase(), namedCompilerPhaseMakeIrFilePhase3, MoveCompanionObjectFieldsLoweringKt.getRemapObjectFieldAccesses(), AnonymousObjectSuperConstructorLoweringKt.getAnonymousObjectSuperConstructorPhase(), JvmBuiltInsLoweringKt.getJvmBuiltInsPhase(), RangeContainsLoweringKt.getRangeContainsLoweringPhase(), ForLoopsLoweringKt.getForLoopsPhase(), CollectionStubMethodLoweringKt.getCollectionStubMethodLowering(), JvmSingleAbstractMethodLoweringKt.getSingleAbstractMethodPhase(), JvmMultiFieldValueClassLoweringKt.getJvmMultiFieldValueClassPhase(), JvmInlineClassLoweringKt.getJvmInlineClassPhase(), namedCompilerPhaseMakeIrFilePhase18, MappedEnumWhenLoweringKt.getEnumWhenPhase(), SingletonReferencesLoweringKt.getSingletonReferencesPhase(), AssertionLoweringKt.getAssertionPhase(), namedCompilerPhaseMakeIrFilePhase16, SharedVariablesLoweringKt.getSharedVariablesPhase(), namedCompilerPhaseMakeIrFilePhase4, RemoveDuplicatedInlinedLocalClassesLoweringKt.getRemoveDuplicatedInlinedLocalClasses(), JvmInventNamesForLocalClassesKt.getInventNamesForInlinedLocalClassesPhase(), namedCompilerPhaseMakeIrFilePhase5, StaticCallableReferenceLoweringKt.getStaticCallableReferencePhase(), JvmDefaultConstructorLoweringKt.getJvmDefaultConstructorPhase(), FlattenStringConcatenationLoweringKt.getFlattenStringConcatenationPhase(), JvmStringConcatenationLoweringKt.getJvmStringConcatenationLowering(), namedCompilerPhaseMakeIrFilePhase6, namedCompilerPhaseMakeIrFilePhase8, namedCompilerPhaseMakeIrFilePhase7, namedCompilerPhaseMakeIrFilePhase9, InheritedDefaultMethodsOnClassesLoweringKt.getInheritedDefaultMethodsOnClassesPhase(), InheritedDefaultMethodsOnClassesLoweringKt.getReplaceDefaultImplsOverriddenSymbolsPhase(), InheritedDefaultMethodsOnClassesLoweringKt.getInterfaceSuperCallsPhase(), InheritedDefaultMethodsOnClassesLoweringKt.getInterfaceDefaultCallsPhase(), InheritedDefaultMethodsOnClassesLoweringKt.getInterfaceObjectCallsPhase(), TailCallOptimizationLoweringKt.getTailCallOptimizationPhase(), AddContinuationLoweringKt.getAddContinuationPhase(), namedCompilerPhaseMakeIrFilePhase10, namedCompilerPhaseMakeIrFilePhase11, namedCompilerPhaseMakeIrFilePhase12, EnumClassLoweringKt.getEnumClassPhase(), EnumExternalEntriesLoweringKt.getEnumExternalEntriesPhase(), ObjectClassLoweringKt.getObjectClassPhase(), namedCompilerPhaseMakeIrFilePhase13, UniqueLoopLabelsLoweringKt.getUniqueLoopLabelsPhase(), namedCompilerPhaseMakeIrFilePhase14, namedCompilerPhaseMakeIrFilePhase15, FunctionNVarargBridgeLoweringKt.getFunctionNVarargBridgePhase(), JvmStaticAnnotationLoweringKt.getJvmStaticInCompanionPhase(), StaticDefaultFunctionLoweringKt.getStaticDefaultFunctionPhase(), BridgeLoweringKt.getBridgePhase(), namedCompilerPhaseMakeIrFilePhase17, JvmArgumentNullabilityAssertionsLoweringKt.getJvmArgumentNullabilityAssertions(), ToArrayLoweringKt.getToArrayPhase(), JvmSafeCallChainFoldingLoweringKt.getJvmSafeCallFoldingPhase(), JvmOptimizationLoweringKt.getJvmOptimizationLoweringPhase(), AdditionalClassAnnotationLoweringKt.getAdditionalClassAnnotationPhase(), RecordEnclosingMethodsLoweringKt.getRecordEnclosingMethodsPhase(), TypeOperatorLoweringKt.getTypeOperatorLowering(), ReplaceKFunctionInvokeWithFunctionInvokeKt.getReplaceKFunctionInvokeWithFunctionInvokePhase(), namedCompilerPhaseMakeIrFilePhase19, MakePropertyDelegateMethodsStaticLoweringKt.getMakePropertyDelegateMethodsStaticPhase(), AddSuperQualifierToJavaFieldAccessLoweringKt.getAddSuperQualifierToJavaFieldAccessPhase(), ReplaceNumberToCharCallSitesLoweringKt.getReplaceNumberToCharCallSitesPhase(), RenameFieldsLoweringKt.getRenameFieldsPhase(), FakeLocalVariablesForBytecodeInlinerLoweringKt.getFakeLocalVariablesForBytecodeInlinerLowering(), FakeLocalVariablesForIrInlinerLoweringKt.getFakeLocalVariablesForIrInlinerLowering()});
        jvmFilePhases = listListOf;
        jvmLoweringPhases = buildJvmLoweringPhases("IrLowering", CollectionsKt.listOf(TuplesKt.to("PerformByIrFile", listListOf)));
        int iIndexOf = listListOf.indexOf(namedCompilerPhaseMakeIrFilePhase7) + 1;
        jvmFragmentLoweringPhases = buildJvmLoweringPhases("IrFragmentLowering", CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("PrefixOfIRPhases", listListOf.subList(0, iIndexOf)), TuplesKt.to("FragmentLowerings", CollectionsKt.listOf((Object[]) new NamedCompilerPhase[]{FragmentLocalFunctionPatchLoweringKt.getFragmentLocalFunctionPatchLowering(), ReflectiveAccessKt.getReflectiveAccessLowering()})), TuplesKt.to("SuffixOfIRPhases", listListOf.subList(iIndexOf, listListOf.size()))}));
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getExpectDeclarationsRemovingPhase() {
        return expectDeclarationsRemovingPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getPropertiesPhase() {
        return propertiesPhase;
    }

    public static final boolean isGeneratedLambdaClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_LAMBDA.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE);
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getLocalDeclarationsPhase() {
        return localDeclarationsPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getDefaultArgumentCleanerPhase() {
        return defaultArgumentCleanerPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getFunctionInliningPhase() {
        return functionInliningPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getJvmLoweringPhases() {
        return jvmLoweringPhases;
    }

    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> buildJvmLoweringPhases(String str, List<? extends Pair<String, ? extends List<? extends NamedCompilerPhase<? super JvmBackendContext, IrFile>>>> list) {
        return new NamedCompilerPhase<>(str, "IR lowering", null, PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(ExternalPackageParentPatcherLoweringKt.getExternalPackageParentPatcherPhase(), FragmentSharedVariablesLoweringKt.getFragmentSharedVariablesLowering()), validateIrBeforeLowering), ProcessOptionalAnnotationsKt.getProcessOptionalAnnotationsPhase()), expectDeclarationsRemovingPhase), constEvaluationPhase), SerializeIrPhaseKt.getSerializeIrPhase()), ScriptLoweringKt.getScriptsToClassesPhase()), FileClassLoweringKt.getFileClassPhase()), JvmStaticAnnotationLoweringKt.getJvmStaticInObjectPhase()), RepeatedAnnotationLoweringKt.getRepeatedAnnotationPhase()), functionInliningPhase), apiVersionIsAtLeastEvaluationPhase), CreateSeparateCallForInlinedLambdasLoweringKt.getCreateSeparateCallForInlinedLambdas()), MarkNecessaryInlinedClassesAsRegeneratedLoweringKt.getMarkNecessaryInlinedClassesAsRegenerated()), inlinedClassReferencesBoxingPhase), buildLoweringsPhase(list)), GenerateMultifileFacadesKt.getGenerateMultifileFacadesPhase()), ResolveInlineCallsKt.getResolveInlineCallsPhase()), validateIrAfterLowering), null, null, null, SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}), 1, 116, null);
    }

    private static final CompilerPhase<JvmBackendContext, IrModuleFragment, IrModuleFragment> buildLoweringsPhase(List<? extends Pair<String, ? extends List<? extends NamedCompilerPhase<? super JvmBackendContext, IrFile>>>> list) {
        List<? extends Pair<String, ? extends List<? extends NamedCompilerPhase<? super JvmBackendContext, IrFile>>>> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(PerformByIrFileKt.performByIrFile$default((String) pair.component1(), null, false, (List) pair.component2(), 6, null));
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = PhaseBuildersKt.then((CompilerPhase) next, (CompilerPhase) it2.next());
        }
        return (CompilerPhase) next;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getJvmFragmentLoweringPhases() {
        return jvmFragmentLoweringPhases;
    }
}
