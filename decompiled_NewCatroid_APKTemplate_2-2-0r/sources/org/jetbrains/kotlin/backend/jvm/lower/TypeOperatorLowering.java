package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.lower.IrBuildingTransformer;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: TypeOperatorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0003stuB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0011\u001a\u00020\nH\u0002J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\b\u0010\u0019\u001a\u00020\nH\u0002J,\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0017H\u0002J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0002J\b\u0010%\u001a\u00020\u0013H\u0002J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020(H\u0016J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020#H\u0002J\u0018\u0010-\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020#H\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0018H\u0002J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000202H\u0002J\u0012\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u000207H\u0002J\u0010\u00108\u001a\u00020*2\u0006\u00109\u001a\u000202H\u0016J\u0010\u0010:\u001a\u00020;2\u0006\u00106\u001a\u00020\u0015H\u0016J\u0010\u0010<\u001a\u00020*2\u0006\u00109\u001a\u00020=H\u0016J \u0010>\u001a\u0002022\u0006\u0010?\u001a\u00020@2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010A\u001a\u00020BH\u0002J\u0016\u0010C\u001a\u0004\u0018\u00010D*\u00020E2\u0006\u0010F\u001a\u00020=H\u0002JB\u0010G\u001a\u000202*\u00020H2\u0006\u0010I\u001a\u00020\u00072\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020K2\u0006\u0010M\u001a\u00020!2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010O\u001a\u000202H\u0002J\u0018\u0010P\u001a\u000e\u0012\u0004\u0012\u00020R\u0012\u0004\u0012\u00020R0Q*\u00020SH\u0002J\u001c\u0010T\u001a\u00020**\u00020H2\u0006\u0010U\u001a\u00020V2\u0006\u00100\u001a\u00020\u0018H\u0002J\u001c\u0010W\u001a\u00020**\u00020H2\u0006\u0010U\u001a\u00020V2\u0006\u0010X\u001a\u00020/H\u0002J\f\u0010Y\u001a\u00020#*\u00020#H\u0002J%\u0010Z\u001a\u00020**\u00020H2\u0012\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u00020*0\\\"\u00020*H\u0002¢\u0006\u0002\u0010]J\u001c\u0010^\u001a\u000202*\u00020H2\u0006\u0010_\u001a\u00020*2\u0006\u0010`\u001a\u00020*H\u0002J\f\u0010a\u001a\u00020!*\u00020*H\u0002J\f\u0010b\u001a\u00020!*\u00020cH\u0002J\f\u0010d\u001a\u00020!*\u00020eH\u0002J*\u0010f\u001a\u000202*\u00020H2\u0006\u0010O\u001a\u0002022\u0006\u0010g\u001a\u00020\r2\f\u0010h\u001a\b\u0012\u0004\u0012\u00020*0\u0017H\u0002J\u0014\u0010i\u001a\u000202*\u00020H2\u0006\u0010j\u001a\u00020\rH\u0002J\u0014\u0010k\u001a\u000202*\u00020H2\u0006\u0010l\u001a\u00020KH\u0002J\f\u0010m\u001a\u00020**\u00020*H\u0002J\u0011\u0010n\u001a\u00070D¢\u0006\u0002\bo*\u00020DH\u0002J\u001c\u0010p\u001a\u00020!*\u0002052\u0006\u0010q\u001a\u00020R2\u0006\u0010r\u001a\u00020RH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006v"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/lower/IrBuildingTransformer;", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "checkExpressionValueIsNotNull", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "classContextStack", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$ClassContext;", "equalsAny", "jdkAltMetafactoryHandle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "jdkMetafactoryHandle", "jvmIndyLambdaMetafactoryIntrinsic", "throwTypeCastException", "enterClass", "generateDeserializeLambdaMethod", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "serializableMethodRefInfos", "", "Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$SerializableMethodRefInfo;", "getClassContext", "getOverriddenMethodsRequiringBridges", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "instanceMethod", "samMethod", "extraOverriddenMethods", "isCompatibleArrayType", "", "actualType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "expectedType", "leaveClass", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lowerCast", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", ModuleXmlParser.TYPE, "lowerInstanceOf", "mapDeserializedLambda", "Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$DeserializedLambdaInfo;", CompilerOptions.INFO, "rewriteIndyLambdaMetafactoryCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "call", "sourceViewFor", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitCall", "expression", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "wrapClosureInDynamicCall", "erasedSamType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "targetRef", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "computeNotNullAssertionText", "", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "typeOperatorCall", "createLambdaMetafactoryCall", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "samMethodSymbol", "implFunSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "instanceMethodSymbol", "shouldBeSerializable", "requiredBridges", "dynamicCall", "extents", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "generateCreateDeserializedMethodRef", "lambdaParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "generateSerializedLambdaEquals", "deserializedLambdaInfo", "getArrayElementLowerType", "irAndAnd", "args", "", "(Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irObjectEquals", "receiver", ParameterDescription.NAME_PREFIX, "isDefinitelyNotNull", "isDefinitelyNotNullVal", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "isDelegated", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "jvmInvokeDynamic", "bootstrapMethodHandle", "bootstrapMethodArguments", "jvmMethodHandle", "handle", "jvmOriginalMethodType", "methodSymbol", "transformVoid", "trimForRuntimeAssertion", "Lorg/jetbrains/annotations/NotNull;", "validSourcePosition", "startOffset", "endOffset", "ClassContext", "DeserializedLambdaInfo", "SerializableMethodRefInfo", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeOperatorLowering extends IrBuildingTransformer implements FileLoweringPass {
    private final JvmBackendContext backendContext;
    private final IrSimpleFunctionSymbol checkExpressionValueIsNotNull;
    private final ArrayDeque<ClassContext> classContextStack;
    private final IrSimpleFunctionSymbol equalsAny;
    private final Handle jdkAltMetafactoryHandle;
    private final Handle jdkMetafactoryHandle;
    private final IrSimpleFunctionSymbol jvmIndyLambdaMetafactoryIntrinsic;
    private final IrSimpleFunctionSymbol throwTypeCastException;

    /* JADX INFO: compiled from: TypeOperatorLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrTypeOperator.CAST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IrTypeOperator.INSTANCEOF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeOperatorLowering(JvmBackendContext backendContext) {
        IrSimpleFunctionSymbol checkExpressionValueIsNotNull;
        super(backendContext);
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
        this.jvmIndyLambdaMetafactoryIntrinsic = backendContext.getIr().getSymbols().getIndyLambdaMetafactoryIntrinsic();
        this.jdkMetafactoryHandle = new Handle(6, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false);
        this.jdkAltMetafactoryHandle = new Handle(6, "java/lang/invoke/LambdaMetafactory", "altMetafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false);
        this.classContextStack = new ArrayDeque<>();
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(backendContext.getIrBuiltIns().getAnyClass(), Namer.EQUALS_METHOD_NAME);
        Intrinsics.checkNotNull(simpleFunction);
        this.equalsAny = simpleFunction;
        this.throwTypeCastException = backendContext.getIr().getSymbols().getThrowTypeCastException();
        if (backendContext.getConfig().getUnifiedNullChecks()) {
            checkExpressionValueIsNotNull = backendContext.getIr().getSymbols().getCheckNotNullExpressionValue();
        } else {
            checkExpressionValueIsNotNull = backendContext.getIr().getSymbols().getCheckExpressionValueIsNotNull();
        }
        this.checkExpressionValueIsNotNull = checkExpressionValueIsNotNull;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    private final IrExpression transformVoid(IrExpression irExpression) {
        return irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004f  */
    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression lowerInstanceOf(IrExpression argument, IrType type) {
        IrVariable irVariable;
        IrValueSymbol symbol;
        IrBuilderWithScope builder = getBuilder();
        if (JvmIrTypeUtilsKt.isReifiedTypeParameter(type)) {
            return ExpressionHelpersKt.irIs(builder, argument, type);
        }
        if (IrTypeUtilsKt.isNullable(argument.getType()) && IrTypeUtilsKt.isNullable(type)) {
            IrType anyNType = builder.getContext().getIrBuiltIns().getAnyNType();
            if (argument instanceof IrGetValue) {
                IrGetValue irGetValue = (IrGetValue) argument;
                if (!IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                    IrVariable irVariableCreateTemporaryVariable = builder.getScope().createTemporaryVariable(argument, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? argument.getStartOffset() : 0, (108 & 64) != 0 ? argument.getEndOffset() : 0);
                    irVariable = irVariableCreateTemporaryVariable;
                    symbol = irVariableCreateTemporaryVariable.getSymbol();
                } else {
                    symbol = irGetValue.getSymbol();
                    irVariable = null;
                }
            } else {
                IrVariable irVariableCreateTemporaryVariable2 = builder.getScope().createTemporaryVariable(argument, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? argument.getStartOffset() : 0, (108 & 64) != 0 ? argument.getEndOffset() : 0);
                irVariable = irVariableCreateTemporaryVariable2;
                symbol = irVariableCreateTemporaryVariable2.getSymbol();
            }
            IrWhen irWhenOror$default = PrimitivesKt.oror$default(builder.getContext(), ExpressionHelpersKt.irEqualsNull(builder, ExpressionHelpersKt.irGet(builder, symbol.getOwner())), ExpressionHelpersKt.irIs(builder, ExpressionHelpersKt.irGet(builder, symbol.getOwner()), IrTypesKt.makeNotNull(type)), null, 4, null);
            if (irVariable == null) {
                return irWhenOror$default;
            }
            IrBlockImpl irBlockImpl = new IrBlockImpl(builder.getStartOffset(), builder.getEndOffset(), irWhenOror$default.getType(), null);
            irBlockImpl.getStatements().add(irVariable);
            if (irWhenOror$default instanceof IrStatementContainer) {
                irBlockImpl.getStatements().addAll(((IrStatementContainer) irWhenOror$default).getStatements());
            } else {
                irBlockImpl.getStatements().add(irWhenOror$default);
            }
            return irBlockImpl;
        }
        if (IrTypeUtilsKt.isNullable(argument.getType()) && !IrTypeUtilsKt.isNullable(type) && Intrinsics.areEqual(JvmIrTypeUtilsKt.getErasedUpperBound(argument.getType()), JvmIrTypeUtilsKt.getErasedUpperBound(type))) {
            return ExpressionHelpersKt.irNotEquals(builder, argument, ExpressionHelpersKt.irNull(builder));
        }
        return ExpressionHelpersKt.irIs(builder, argument, IrTypesKt.makeNotNull(type));
    }

    /* JADX WARN: Code duplicated, block: B:23:0x007d  */
    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression lowerCast(IrExpression argument, IrType type) {
        IrVariable irVariable;
        IrValueSymbol symbol;
        IrIfThenElseImpl irIfThenElseImplIrIfNull;
        IrExpression irExpression;
        IrContainerExpression irBlockBody;
        IrBlockImpl irBlockImpl = argument;
        if (JvmIrTypeUtilsKt.isReifiedTypeParameter(type)) {
            return ExpressionHelpersKt.irAs(getBuilder(), irBlockImpl, type);
        }
        if ((!JvmIrTypeUtilsKt.isInlineClassType(argument.getType()) || !IrTypeUtilsKt.isSubtypeOfClass(argument.getType(), JvmIrTypeUtilsKt.getErasedUpperBound(type).getSymbol())) && !isCompatibleArrayType(argument.getType(), type)) {
            if (IrTypeUtilsKt.isNullable(type) || isDefinitelyNotNull(argument)) {
                return ExpressionHelpersKt.irAs(getBuilder(), irBlockImpl, type);
            }
            IrBuilderWithScope builder = getBuilder();
            IrType anyNType = builder.getContext().getIrBuiltIns().getAnyNType();
            if (irBlockImpl instanceof IrGetValue) {
                IrGetValue irGetValue = (IrGetValue) irBlockImpl;
                if (!IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                    IrVariable irVariableCreateTemporaryVariable = builder.getScope().createTemporaryVariable(argument, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? argument.getStartOffset() : 0, (108 & 64) != 0 ? argument.getEndOffset() : 0);
                    irVariable = irVariableCreateTemporaryVariable;
                    symbol = irVariableCreateTemporaryVariable.getSymbol();
                } else {
                    symbol = irGetValue.getSymbol();
                    irVariable = null;
                }
            } else {
                IrVariable irVariableCreateTemporaryVariable2 = builder.getScope().createTemporaryVariable(argument, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? argument.getStartOffset() : 0, (108 & 64) != 0 ? argument.getEndOffset() : 0);
                irVariable = irVariableCreateTemporaryVariable2;
                symbol = irVariableCreateTemporaryVariable2.getSymbol();
            }
            IrConstImpl<String> irConstImplIrString = ExpressionHelpersKt.irString(builder, "null cannot be cast to non-null type " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null));
            if (!this.backendContext.getConfig().getUnifiedNullChecks()) {
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(builder, symbol.getOwner());
                IrCall irCall = ExpressionHelpersKt.irCall(builder, this.throwTypeCastException);
                irCall.putValueArgument(0, irConstImplIrString);
                Unit unit = Unit.INSTANCE;
                irIfThenElseImplIrIfNull = ExpressionHelpersKt.irIfNull(builder, type, irGetValueImplIrGet, irCall, ExpressionHelpersKt.irAs(builder, ExpressionHelpersKt.irGet(builder, symbol.getOwner()), IrTypesKt.makeNullable(type)));
            } else {
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, type, false, 64, null);
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.backendContext.getIr().getSymbols().getCheckNotNullWithMessage());
                irCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, symbol.getOwner()));
                irCall2.putValueArgument(1, irConstImplIrString);
                irBlockBuilder.unaryPlus(irCall2);
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irAs(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, symbol.getOwner()), IrTypesKt.makeNullable(type)));
                irBlockBody = irBlockBuilder.getIrBlockBody();
            }
            if (irVariable == 0) {
                irExpression = irIfThenElseImplIrIfNull;
                irExpression = irBlockBody;
                return irExpression;
            }
            irExpression = irIfThenElseImplIrIfNull;
            IrBlockImpl irBlockImpl2 = new IrBlockImpl(builder.getStartOffset(), builder.getEndOffset(), irExpression.getType(), null);
            irBlockImpl2.getStatements().add(irVariable);
            if (irExpression instanceof IrStatementContainer) {
                irExpression = irBlockBody;
                irBlockImpl2.getStatements().addAll(((IrStatementContainer) irExpression).getStatements());
            } else {
                irExpression = irBlockBody;
                irBlockImpl2.getStatements().add(irExpression);
            }
            irBlockImpl = irBlockImpl2;
        }
        return irBlockImpl;
    }

    private final boolean isCompatibleArrayType(IrType actualType, IrType expectedType) {
        IrType arrayElementLowerType = actualType;
        IrType arrayElementLowerType2 = expectedType;
        while (true) {
            if ((!IrTypePredicatesKt.isArray(arrayElementLowerType) && !IrTypePredicatesKt.isNullableArray(arrayElementLowerType)) || (!IrTypePredicatesKt.isArray(arrayElementLowerType2) && !IrTypePredicatesKt.isNullableArray(arrayElementLowerType2))) {
                break;
            }
            arrayElementLowerType = getArrayElementLowerType(arrayElementLowerType);
            arrayElementLowerType2 = getArrayElementLowerType(arrayElementLowerType2);
        }
        if (Intrinsics.areEqual(arrayElementLowerType, actualType) || Intrinsics.areEqual(arrayElementLowerType2, expectedType)) {
            return false;
        }
        return IrTypeUtilsKt.isSubtypeOfClass(arrayElementLowerType, JvmIrTypeUtilsKt.getErasedUpperBound(arrayElementLowerType2).getSymbol());
    }

    private final IrType getArrayElementLowerType(IrType irType) {
        if (IrTypeUtilsKt.isBoxedArray(irType) && (irType instanceof IrSimpleType)) {
            Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ((IrSimpleType) irType).getArguments());
            IrTypeProjection irTypeProjection = objSingleOrNull instanceof IrTypeProjection ? (IrTypeProjection) objSingleOrNull : null;
            if ((irTypeProjection != null ? irTypeProjection.getVariance() : null) == Variance.IN_VARIANCE) {
                return this.backendContext.getIrBuiltIns().getAnyNType();
            }
        }
        return IrTypeUtilsKt.getArrayElementType(irType, this.backendContext.getIrBuiltIns());
    }

    private final boolean isDefinitelyNotNull(IrExpression irExpression) {
        if (irExpression instanceof IrGetValue) {
            return isDefinitelyNotNullVal(((IrGetValue) irExpression).getSymbol().getOwner());
        }
        if (irExpression instanceof IrGetClass ? true : irExpression instanceof IrConstructorCall) {
            return true;
        }
        if (irExpression instanceof IrCall) {
            return Intrinsics.areEqual(((IrCall) irExpression).getSymbol(), this.backendContext.getIrBuiltIns().getCheckNotNullSymbol());
        }
        return false;
    }

    private final boolean isDefinitelyNotNullVal(IrValueDeclaration irValueDeclaration) {
        IrExpression initializer;
        IrVariable irVariable = irValueDeclaration instanceof IrVariable ? (IrVariable) irValueDeclaration : null;
        return (irVariable == null || irVariable.getIsVar() || (initializer = irVariable.getInitializer()) == null || !isDefinitelyNotNull(initializer)) ? false : true;
    }

    private final IrCall jvmMethodHandle(JvmIrBuilder jvmIrBuilder, Handle handle) {
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder2, jvmIrBuilder.getBackendContext().getIr().getSymbols().getJvmMethodHandle());
        irCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(jvmIrBuilder2, handle.getTag(), null, 2, null));
        String owner = handle.getOwner();
        Intrinsics.checkNotNullExpressionValue(owner, "getOwner(...)");
        irCall.putValueArgument(1, ExpressionHelpersKt.irString(jvmIrBuilder2, owner));
        String name2 = handle.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        irCall.putValueArgument(2, ExpressionHelpersKt.irString(jvmIrBuilder2, name2));
        String desc = handle.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc, "getDesc(...)");
        irCall.putValueArgument(3, ExpressionHelpersKt.irString(jvmIrBuilder2, desc));
        irCall.putValueArgument(4, ExpressionHelpersKt.irBoolean(jvmIrBuilder2, handle.isInterface()));
        return irCall;
    }

    private final IrCall jvmInvokeDynamic(JvmIrBuilder jvmIrBuilder, IrCall irCall, Handle handle, List<? extends IrExpression> list) {
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(jvmIrBuilder2, jvmIrBuilder.getBackendContext().getIr().getSymbols().getJvmIndyIntrinsic(), irCall.getType(), 0, 0, null, 28, null);
        irCallIrCall$default.putTypeArgument(0, irCall.getType());
        irCallIrCall$default.putValueArgument(0, irCall);
        irCallIrCall$default.putValueArgument(1, jvmMethodHandle(jvmIrBuilder, handle));
        irCallIrCall$default.putValueArgument(2, ExpressionHelpersKt.irVararg(jvmIrBuilder2, jvmIrBuilder.getContext().getIrBuiltIns().getAnyType(), list));
        return irCallIrCall$default;
    }

    private final IrCall jvmOriginalMethodType(JvmIrBuilder jvmIrBuilder, IrFunctionSymbol irFunctionSymbol) {
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(jvmIrBuilder2, jvmIrBuilder.getBackendContext().getIr().getSymbols().getJvmOriginalMethodTypeIntrinsic(), jvmIrBuilder.getContext().getIrBuiltIns().getAnyType(), 0, 0, null, 28, null);
        irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irRawFunctionReference(jvmIrBuilder2, jvmIrBuilder.getContext().getIrBuiltIns().getAnyType(), irFunctionSymbol));
        return irCallIrCall$default;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (Intrinsics.areEqual(expression.getSymbol(), this.jvmIndyLambdaMetafactoryIntrinsic)) {
            transformChildrenVoid(expression);
            return rewriteIndyLambdaMetafactoryCall(expression);
        }
        return super.visitCall(expression);
    }

    /* JADX INFO: compiled from: TypeOperatorLowering.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\r\b\u0002\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\f\u001a\u00020\u0005¢\u0006\u0002\u0010\rR\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$SerializableMethodRefInfo;", "", "samType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "samMethodSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "implFunSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "instanceFunSymbol", "requiredBridges", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "dynamicCallSymbol", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Ljava/util/Collection;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getDynamicCallSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getImplFunSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getInstanceFunSymbol", "getRequiredBridges", "()Ljava/util/Collection;", "getSamMethodSymbol", "getSamType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SerializableMethodRefInfo {
        private final IrSimpleFunctionSymbol dynamicCallSymbol;
        private final IrFunctionSymbol implFunSymbol;
        private final IrFunctionSymbol instanceFunSymbol;
        private final Collection<IrSimpleFunction> requiredBridges;
        private final IrSimpleFunctionSymbol samMethodSymbol;
        private final IrType samType;

        /* JADX WARN: Multi-variable type inference failed */
        public SerializableMethodRefInfo(IrType samType, IrSimpleFunctionSymbol samMethodSymbol, IrFunctionSymbol implFunSymbol, IrFunctionSymbol instanceFunSymbol, Collection<? extends IrSimpleFunction> requiredBridges, IrSimpleFunctionSymbol dynamicCallSymbol) {
            Intrinsics.checkNotNullParameter(samType, "samType");
            Intrinsics.checkNotNullParameter(samMethodSymbol, "samMethodSymbol");
            Intrinsics.checkNotNullParameter(implFunSymbol, "implFunSymbol");
            Intrinsics.checkNotNullParameter(instanceFunSymbol, "instanceFunSymbol");
            Intrinsics.checkNotNullParameter(requiredBridges, "requiredBridges");
            Intrinsics.checkNotNullParameter(dynamicCallSymbol, "dynamicCallSymbol");
            this.samType = samType;
            this.samMethodSymbol = samMethodSymbol;
            this.implFunSymbol = implFunSymbol;
            this.instanceFunSymbol = instanceFunSymbol;
            this.requiredBridges = requiredBridges;
            this.dynamicCallSymbol = dynamicCallSymbol;
        }

        public final IrType getSamType() {
            return this.samType;
        }

        public final IrSimpleFunctionSymbol getSamMethodSymbol() {
            return this.samMethodSymbol;
        }

        public final IrFunctionSymbol getImplFunSymbol() {
            return this.implFunSymbol;
        }

        public final IrFunctionSymbol getInstanceFunSymbol() {
            return this.instanceFunSymbol;
        }

        public final Collection<IrSimpleFunction> getRequiredBridges() {
            return this.requiredBridges;
        }

        public final IrSimpleFunctionSymbol getDynamicCallSymbol() {
            return this.dynamicCallSymbol;
        }
    }

    /* JADX INFO: compiled from: TypeOperatorLowering.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$ClassContext;", "", "()V", "serializableMethodRefInfos", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$SerializableMethodRefInfo;", "Lkotlin/collections/ArrayList;", "getSerializableMethodRefInfos", "()Ljava/util/ArrayList;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ClassContext {
        private final ArrayList<SerializableMethodRefInfo> serializableMethodRefInfos = new ArrayList<>();

        public final ArrayList<SerializableMethodRefInfo> getSerializableMethodRefInfos() {
            return this.serializableMethodRefInfos;
        }
    }

    private final ClassContext enterClass() {
        ClassContext classContext = new ClassContext();
        UtilsKt.push(this.classContextStack, classContext);
        return classContext;
    }

    private final void leaveClass() {
        UtilsKt.pop(this.classContextStack);
    }

    private final ClassContext getClassContext() {
        if (this.classContextStack.isEmpty()) {
            throw new AssertionError("No class context");
        }
        return this.classContextStack.last();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ClassContext classContextEnterClass = enterClass();
        IrStatement irStatementVisitClass = super.visitClass(declaration);
        if (!classContextEnterClass.getSerializableMethodRefInfos().isEmpty()) {
            generateDeserializeLambdaMethod(declaration, classContextEnterClass.getSerializableMethodRefInfos());
        }
        leaveClass();
        return irStatementVisitClass;
    }

    /* JADX INFO: compiled from: TypeOperatorLowering.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TypeOperatorLowering$DeserializedLambdaInfo;", "", "functionalInterfaceClass", "", "implMethodHandle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "functionalInterfaceMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/Handle;Lorg/jetbrains/org/objectweb/asm/commons/Method;)V", "getFunctionalInterfaceClass", "()Ljava/lang/String;", "getFunctionalInterfaceMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getImplMethodHandle", "()Lorg/jetbrains/org/objectweb/asm/Handle;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DeserializedLambdaInfo {
        private final String functionalInterfaceClass;
        private final Method functionalInterfaceMethod;
        private final Handle implMethodHandle;

        public static /* synthetic */ DeserializedLambdaInfo copy$default(DeserializedLambdaInfo deserializedLambdaInfo, String str, Handle handle, Method method, int i, Object obj) {
            if ((i & 1) != 0) {
                str = deserializedLambdaInfo.functionalInterfaceClass;
            }
            if ((i & 2) != 0) {
                handle = deserializedLambdaInfo.implMethodHandle;
            }
            if ((i & 4) != 0) {
                method = deserializedLambdaInfo.functionalInterfaceMethod;
            }
            return deserializedLambdaInfo.copy(str, handle, method);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFunctionalInterfaceClass() {
            return this.functionalInterfaceClass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Handle getImplMethodHandle() {
            return this.implMethodHandle;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Method getFunctionalInterfaceMethod() {
            return this.functionalInterfaceMethod;
        }

        public final DeserializedLambdaInfo copy(String functionalInterfaceClass, Handle implMethodHandle, Method functionalInterfaceMethod) {
            Intrinsics.checkNotNullParameter(functionalInterfaceClass, "functionalInterfaceClass");
            Intrinsics.checkNotNullParameter(implMethodHandle, "implMethodHandle");
            Intrinsics.checkNotNullParameter(functionalInterfaceMethod, "functionalInterfaceMethod");
            return new DeserializedLambdaInfo(functionalInterfaceClass, implMethodHandle, functionalInterfaceMethod);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeserializedLambdaInfo)) {
                return false;
            }
            DeserializedLambdaInfo deserializedLambdaInfo = (DeserializedLambdaInfo) other;
            return Intrinsics.areEqual(this.functionalInterfaceClass, deserializedLambdaInfo.functionalInterfaceClass) && Intrinsics.areEqual(this.implMethodHandle, deserializedLambdaInfo.implMethodHandle) && Intrinsics.areEqual(this.functionalInterfaceMethod, deserializedLambdaInfo.functionalInterfaceMethod);
        }

        public int hashCode() {
            return (((this.functionalInterfaceClass.hashCode() * 31) + this.implMethodHandle.hashCode()) * 31) + this.functionalInterfaceMethod.hashCode();
        }

        public String toString() {
            return "DeserializedLambdaInfo(functionalInterfaceClass=" + this.functionalInterfaceClass + ", implMethodHandle=" + this.implMethodHandle + ", functionalInterfaceMethod=" + this.functionalInterfaceMethod + ')';
        }

        public DeserializedLambdaInfo(String functionalInterfaceClass, Handle implMethodHandle, Method functionalInterfaceMethod) {
            Intrinsics.checkNotNullParameter(functionalInterfaceClass, "functionalInterfaceClass");
            Intrinsics.checkNotNullParameter(implMethodHandle, "implMethodHandle");
            Intrinsics.checkNotNullParameter(functionalInterfaceMethod, "functionalInterfaceMethod");
            this.functionalInterfaceClass = functionalInterfaceClass;
            this.implMethodHandle = implMethodHandle;
            this.functionalInterfaceMethod = functionalInterfaceMethod;
        }

        public final String getFunctionalInterfaceClass() {
            return this.functionalInterfaceClass;
        }

        public final Handle getImplMethodHandle() {
            return this.implMethodHandle;
        }

        public final Method getFunctionalInterfaceMethod() {
            return this.functionalInterfaceMethod;
        }
    }

    private final void generateDeserializeLambdaMethod(IrClass irClass, List<SerializableMethodRefInfo> serializableMethodRefInfos) {
        HashMap map = new HashMap();
        for (SerializableMethodRefInfo serializableMethodRefInfo : serializableMethodRefInfos) {
            DeserializedLambdaInfo deserializedLambdaInfoMapDeserializedLambda = mapDeserializedLambda(serializableMethodRefInfo);
            String name2 = deserializedLambdaInfoMapDeserializedLambda.getImplMethodHandle().getName();
            HashMap map2 = map;
            Intrinsics.checkNotNull(name2);
            Object map3 = map2.get(name2);
            if (map3 == null) {
                map3 = new HashMap();
                map2.put(name2, map3);
            }
            ((HashMap) map3).put(deserializedLambdaInfoMapDeserializedLambda, serializableMethodRefInfo);
        }
        IrFactory irFactory = this.backendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("$deserializeLambda$");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.DESERIALIZE_LAMBDA_FUN.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrValueParameter irValueParameterAddValueParameter$default = DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "lambda", this.backendContext.getIr().getSymbols().getSerializedLambda().getIrType(), (IrDeclarationOrigin) null, 4, (Object) null);
        irSimpleFunctionBuildFunction.setReturnType(this.backendContext.getIrBuiltIns().getAnyType());
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.backendContext, irSimpleFunctionBuildFunction.getSymbol(), -1, -1);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, jvmIrBuilderCreateJvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetImplMethodName());
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterAddValueParameter$default));
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder, irFunctionAccessExpressionIrCall, null, null, false, null, 30, null);
        IrType unitType = jvmIrBuilderCreateJvmIrBuilder.getBackendContext().getIrBuiltIns().getUnitType();
        Set setEntrySet = map.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Set set = setEntrySet;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Intrinsics.checkNotNull(entry);
            String str = (String) entry.getKey();
            HashMap map4 = (HashMap) entry.getValue();
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariableIrTemporary$default);
            Intrinsics.checkNotNull(str);
            IrExpression irExpressionIrEquals$default = ExpressionHelpersKt.irEquals$default(irBlockBodyBuilder2, irGetValueImplIrGet, ExpressionHelpersKt.irString(irBlockBodyBuilder2, str), null, 4, null);
            IrType unitType2 = jvmIrBuilderCreateJvmIrBuilder.getBackendContext().getIrBuiltIns().getUnitType();
            Set setEntrySet2 = map4.entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet2, "<get-entries>(...)");
            Set<Map.Entry> set2 = setEntrySet2;
            Iterator it2 = it;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
            for (Map.Entry entry2 : set2) {
                Intrinsics.checkNotNull(entry2);
                DeserializedLambdaInfo deserializedLambdaInfo = (DeserializedLambdaInfo) entry2.getKey();
                SerializableMethodRefInfo serializableMethodRefInfo2 = (SerializableMethodRefInfo) entry2.getValue();
                Intrinsics.checkNotNull(deserializedLambdaInfo);
                IrExpression irExpressionGenerateSerializedLambdaEquals = generateSerializedLambdaEquals(jvmIrBuilderCreateJvmIrBuilder, irValueParameterAddValueParameter$default, deserializedLambdaInfo);
                Intrinsics.checkNotNull(serializableMethodRefInfo2);
                arrayList2.add(ExpressionHelpersKt.irBranch(irBlockBodyBuilder2, irExpressionGenerateSerializedLambdaEquals, ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, generateCreateDeserializedMethodRef(jvmIrBuilderCreateJvmIrBuilder, irValueParameterAddValueParameter$default, serializableMethodRefInfo2))));
            }
            arrayList.add(ExpressionHelpersKt.irBranch(irBlockBodyBuilder2, irExpressionIrEquals$default, ExpressionHelpersKt.irWhen(irBlockBodyBuilder2, unitType2, arrayList2)));
            it = it2;
        }
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irWhen(irBlockBodyBuilder2, unitType, arrayList));
        IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, jvmIrBuilderCreateJvmIrBuilder.getBackendContext().getIr().getSymbols().getIllegalArgumentExceptionCtorString());
        irConstructorCallIrCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBodyBuilder2, "Invalid lambda deserialization"));
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irThrow(irBlockBodyBuilder2, irConstructorCallIrCall));
        irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
        irClass.getDeclarations().add(irSimpleFunctionBuildFunction);
    }

    private final DeserializedLambdaInfo mapDeserializedLambda(SerializableMethodRefInfo info) {
        String internalName = IrTypeMapper.mapType$default(this.backendContext.getDefaultTypeMapper(), info.getSamType(), null, null, false, 14, null).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return new DeserializedLambdaInfo(internalName, this.backendContext.getDefaultMethodSignatureMapper().mapToMethodHandle(info.getImplFunSymbol().getOwner()), this.backendContext.getDefaultMethodSignatureMapper().mapAsmMethod(info.getSamMethodSymbol().getOwner()));
    }

    private final IrExpression generateSerializedLambdaEquals(JvmIrBuilder jvmIrBuilder, IrValueParameter irValueParameter, DeserializedLambdaInfo deserializedLambdaInfo) {
        String functionalInterfaceClass = deserializedLambdaInfo.getFunctionalInterfaceClass();
        Handle implMethodHandle = deserializedLambdaInfo.getImplMethodHandle();
        Method functionalInterfaceMethod = deserializedLambdaInfo.getFunctionalInterfaceMethod();
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty = generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetFunctionalInterfaceMethodName());
        String name2 = functionalInterfaceMethod.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        IrFunctionAccessExpression irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty2 = generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetFunctionalInterfaceMethodSignature());
        String descriptor = functionalInterfaceMethod.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        IrFunctionAccessExpression irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty3 = generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetImplClass());
        String owner = implMethodHandle.getOwner();
        Intrinsics.checkNotNullExpressionValue(owner, "getOwner(...)");
        IrFunctionAccessExpression irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty4 = generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetImplMethodSignature());
        String desc = implMethodHandle.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc, "getDesc(...)");
        return irAndAnd(jvmIrBuilder, ExpressionHelpersKt.irEquals$default(jvmIrBuilder2, generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetImplMethodKind()), ExpressionHelpersKt.irInt$default(jvmIrBuilder2, implMethodHandle.getTag(), null, 2, null), null, 4, null), irObjectEquals(jvmIrBuilder, generateSerializedLambdaEquals$irGetLambdaProperty(jvmIrBuilder, irValueParameter, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetFunctionalInterfaceClass()), ExpressionHelpersKt.irString(jvmIrBuilder2, functionalInterfaceClass)), irObjectEquals(jvmIrBuilder, irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty, ExpressionHelpersKt.irString(jvmIrBuilder2, name2)), irObjectEquals(jvmIrBuilder, irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty2, ExpressionHelpersKt.irString(jvmIrBuilder2, descriptor)), irObjectEquals(jvmIrBuilder, irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty3, ExpressionHelpersKt.irString(jvmIrBuilder2, owner)), irObjectEquals(jvmIrBuilder, irFunctionAccessExpressionGenerateSerializedLambdaEquals$irGetLambdaProperty4, ExpressionHelpersKt.irString(jvmIrBuilder2, desc)));
    }

    private static final IrFunctionAccessExpression generateSerializedLambdaEquals$irGetLambdaProperty(JvmIrBuilder jvmIrBuilder, IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction) {
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder2, irSimpleFunction);
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(jvmIrBuilder2, irValueParameter));
        return irFunctionAccessExpressionIrCall;
    }

    private final IrCall irObjectEquals(JvmIrBuilder jvmIrBuilder, IrExpression irExpression, IrExpression irExpression2) {
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, this.equalsAny);
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, irExpression2);
        return irCall;
    }

    private final IrExpression irAndAnd(JvmIrBuilder jvmIrBuilder, IrExpression... irExpressionArr) {
        IrExpression irExpression;
        if (irExpressionArr.length == 0) {
            throw new AssertionError("At least one argument expected");
        }
        IrExpression irExpression2 = irExpressionArr[0];
        int length = irExpressionArr.length;
        int i = 1;
        while (i < length) {
            irExpression = irExpression2;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, jvmIrBuilder.getBackendContext().getIrBuiltIns().getAndandSymbol());
            irCall.putValueArgument(0, irExpression);
            irCall.putValueArgument(1, irExpressionArr[i]);
            i++;
            irExpression = irCall;
        }
        irExpression = irExpression2;
        return irExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v9, types: [org.jetbrains.kotlin.ir.expressions.IrCall] */
    private final IrExpression generateCreateDeserializedMethodRef(JvmIrBuilder jvmIrBuilder, IrValueParameter irValueParameter, SerializableMethodRefInfo serializableMethodRefInfo) {
        ?? IrAs;
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder2, serializableMethodRefInfo.getDynamicCallSymbol());
        int i = 0;
        for (IrValueParameter irValueParameter2 : serializableMethodRefInfo.getDynamicCallSymbol().getOwner().getValueParameters()) {
            int i2 = i + 1;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder2, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSerializedLambda().getGetCapturedArg());
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(jvmIrBuilder2, irValueParameter));
            irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(jvmIrBuilder2, i, null, 2, null));
            IrType type = irValueParameter2.getType();
            if (JvmIrTypeUtilsKt.isInlineClassType(type)) {
                IrType irTypeUnboxInlineClass = InlineClassAbiKt.unboxInlineClass(type);
                IrAs = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder2, jvmIrBuilder.getBackendContext().getIr().getSymbols().getUnsafeCoerceIntrinsic());
                IrAs.putTypeArgument(0, irTypeUnboxInlineClass);
                IrAs.putTypeArgument(1, type);
                IrAs.putValueArgument(0, irFunctionAccessExpressionIrCall);
            } else {
                IrAs = ExpressionHelpersKt.irAs(jvmIrBuilder2, irFunctionAccessExpressionIrCall, type);
            }
            irCall.putValueArgument(i, (IrExpression) IrAs);
            i = i2;
        }
        return createLambdaMetafactoryCall(jvmIrBuilder, serializableMethodRefInfo.getSamMethodSymbol(), serializableMethodRefInfo.getImplFunSymbol(), serializableMethodRefInfo.getInstanceFunSymbol(), true, serializableMethodRefInfo.getRequiredBridges(), irCall);
    }

    private static final Void rewriteIndyLambdaMetafactoryCall$fail(IrCall irCall, String str) {
        throw new AssertionError(str + ", call:\n" + DumpIrTreeKt.dump$default(irCall, null, 1, null));
    }

    private final IrCall rewriteIndyLambdaMetafactoryCall(IrCall call) {
        int startOffset = call.getStartOffset();
        int endOffset = call.getEndOffset();
        IrType typeArgument = call.getTypeArgument(0);
        IrSimpleType irSimpleType = typeArgument instanceof IrSimpleType ? (IrSimpleType) typeArgument : null;
        if (irSimpleType == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "'samType' is expected to be a simple type");
            throw null;
        }
        IrExpression valueArgument = call.getValueArgument(0);
        IrRawFunctionReference irRawFunctionReference = valueArgument instanceof IrRawFunctionReference ? (IrRawFunctionReference) valueArgument : null;
        if (irRawFunctionReference == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "'samMethodType' should be 'IrRawFunctionReference'");
            throw null;
        }
        IrExpression valueArgument2 = call.getValueArgument(1);
        IrFunctionReference irFunctionReference = valueArgument2 instanceof IrFunctionReference ? (IrFunctionReference) valueArgument2 : null;
        if (irFunctionReference == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "'implMethodReference' is expected to be 'IrFunctionReference'");
            throw null;
        }
        IrFunctionSymbol symbol = irFunctionReference.getSymbol();
        IrExpression valueArgument3 = call.getValueArgument(2);
        IrRawFunctionReference irRawFunctionReference2 = valueArgument3 instanceof IrRawFunctionReference ? (IrRawFunctionReference) valueArgument3 : null;
        if (irRawFunctionReference2 == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "'instantiatedMethodType' is expected to be 'IrRawFunctionReference'");
            throw null;
        }
        IrExpression valueArgument4 = call.getValueArgument(3);
        IrVararg irVararg = valueArgument4 instanceof IrVararg ? (IrVararg) valueArgument4 : null;
        if (irVararg == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "'extraOverriddenMethodTypes' is expected to be 'IrVararg'");
            throw null;
        }
        List<IrVarargElement> elements = irVararg.getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        for (IrVarargElement irVarargElement : elements) {
            IrRawFunctionReference irRawFunctionReference3 = irVarargElement instanceof IrRawFunctionReference ? (IrRawFunctionReference) irVarargElement : null;
            if (irRawFunctionReference3 == null) {
                rewriteIndyLambdaMetafactoryCall$fail(call, "'extraOverriddenMethodTypes' elements are expected to be 'IrRawFunctionReference'");
                throw null;
            }
            IrFunction owner = irRawFunctionReference3.getSymbol().getOwner();
            IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
            if (irSimpleFunction == null) {
                rewriteIndyLambdaMetafactoryCall$fail(call, "Extra overridden method is expected to be 'IrSimpleFunction': " + RenderIrElementKt.render$default(irRawFunctionReference3.getSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
                throw null;
            }
            arrayList.add(irSimpleFunction);
        }
        ArrayList arrayList2 = arrayList;
        boolean booleanConstArgument = JvmIrUtilsKt.getBooleanConstArgument(call, 4);
        IrFunction owner2 = irRawFunctionReference.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction2 = owner2 instanceof IrSimpleFunction ? (IrSimpleFunction) owner2 : null;
        if (irSimpleFunction2 == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "SAM method is expected to be 'IrSimpleFunction': " + RenderIrElementKt.render$default(irRawFunctionReference.getSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
            throw null;
        }
        IrFunction owner3 = irRawFunctionReference2.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction3 = owner3 instanceof IrSimpleFunction ? (IrSimpleFunction) owner3 : null;
        if (irSimpleFunction3 == null) {
            rewriteIndyLambdaMetafactoryCall$fail(call, "Instance method is expected to be 'IrSimpleFunction': " + RenderIrElementKt.render$default(irRawFunctionReference2.getSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
            throw null;
        }
        IrCall irCallWrapClosureInDynamicCall = wrapClosureInDynamicCall(irSimpleType, irSimpleFunction2, irFunctionReference);
        Collection<IrSimpleFunction> overriddenMethodsRequiringBridges = getOverriddenMethodsRequiringBridges(irSimpleFunction3, irSimpleFunction2, arrayList2);
        if (booleanConstArgument) {
            getClassContext().getSerializableMethodRefInfos().add(new SerializableMethodRefInfo(irSimpleType, irSimpleFunction2.getSymbol(), symbol, irRawFunctionReference2.getSymbol(), overriddenMethodsRequiringBridges, irCallWrapClosureInDynamicCall.getSymbol()));
        }
        return createLambdaMetafactoryCall(JvmIrBuilderKt.createJvmIrBuilder(this.backendContext, symbol, startOffset, endOffset), irSimpleFunction2.getSymbol(), symbol, irRawFunctionReference2.getSymbol(), booleanConstArgument, overriddenMethodsRequiringBridges, irCallWrapClosureInDynamicCall);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v1, types: [int] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    private final IrCall createLambdaMetafactoryCall(JvmIrBuilder jvmIrBuilder, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrFunctionSymbol irFunctionSymbol, IrFunctionSymbol irFunctionSymbol2, boolean z, Collection<? extends IrSimpleFunction> collection, IrCall irCall) {
        IrCall irCallJvmOriginalMethodType = jvmOriginalMethodType(jvmIrBuilder, irSimpleFunctionSymbol);
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrRawFunctionReferenceImpl irRawFunctionReferenceImplIrRawFunctionReference = ExpressionHelpersKt.irRawFunctionReference(jvmIrBuilder2, jvmIrBuilder.getContext().getIrBuiltIns().getAnyType(), irFunctionSymbol);
        IrCall irCallJvmOriginalMethodType2 = jvmOriginalMethodType(jvmIrBuilder, irFunctionSymbol2);
        Handle handle = this.jdkMetafactoryHandle;
        ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(irCallJvmOriginalMethodType, irRawFunctionReferenceImplIrRawFunctionReference, irCallJvmOriginalMethodType2);
        ArrayList arrayListEmptyList = CollectionsKt.emptyList();
        ?? r9 = z;
        if (!collection.isEmpty()) {
            int i = z + 4;
            Collection<? extends IrSimpleFunction> collection2 = collection;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
            Iterator it = collection2.iterator();
            while (it.hasNext()) {
                arrayList.add(jvmOriginalMethodType(jvmIrBuilder, ((IrSimpleFunction) it.next()).getSymbol()));
            }
            arrayListEmptyList = arrayList;
            r9 = i;
        }
        if (r9 != 0) {
            handle = this.jdkAltMetafactoryHandle;
            arrayListArrayListOf.add(ExpressionHelpersKt.irInt$default(jvmIrBuilder2, r9, null, 2, null));
            List list = arrayListEmptyList;
            if (!list.isEmpty()) {
                arrayListArrayListOf.add(ExpressionHelpersKt.irInt$default(jvmIrBuilder2, arrayListEmptyList.size(), null, 2, null));
                arrayListArrayListOf.addAll(list);
            }
        }
        return jvmInvokeDynamic(jvmIrBuilder, irCall, handle, arrayListArrayListOf);
    }

    private final Collection<IrSimpleFunction> getOverriddenMethodsRequiringBridges(IrSimpleFunction instanceMethod, IrSimpleFunction samMethod, List<? extends IrSimpleFunction> extraOverriddenMethods) {
        Method methodMapAsmMethod = this.backendContext.getDefaultMethodSignatureMapper().mapAsmMethod(instanceMethod);
        Method methodMapAsmMethod2 = this.backendContext.getDefaultMethodSignatureMapper().mapAsmMethod(samMethod);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IrSimpleFunction irSimpleFunction : extraOverriddenMethods) {
            Method methodMapAsmMethod3 = this.backendContext.getDefaultMethodSignatureMapper().mapAsmMethod(irSimpleFunction);
            if (!Intrinsics.areEqual(methodMapAsmMethod3, methodMapAsmMethod) && !Intrinsics.areEqual(methodMapAsmMethod3, methodMapAsmMethod2)) {
                linkedHashMap.put(methodMapAsmMethod3, irSimpleFunction);
            }
        }
        Collection<IrSimpleFunction> collectionValues = linkedHashMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return collectionValues;
    }

    private static final Void wrapClosureInDynamicCall$fail$27(IrFunctionReference irFunctionReference, String str) {
        throw new AssertionError(str + ", targetRef:\n" + DumpIrTreeKt.dump$default(irFunctionReference, null, 1, null));
    }

    private final IrCall wrapClosureInDynamicCall(IrSimpleType erasedSamType, IrSimpleFunction samMethod, IrFunctionReference targetRef) {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        IrFactory irFactory = this.backendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.INVOKEDYNAMIC_CALL_TARGET.INSTANCE);
        irFunctionBuilder.setName(samMethod.getName());
        irFunctionBuilder.setReturnType(erasedSamType);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(this.backendContext.getIr().getSymbols().getKotlinJvmInternalInvokeDynamicPackage());
        IrFunction owner = targetRef.getSymbol().getOwner();
        IrExpression dispatchReceiver = targetRef.getDispatchReceiver();
        IrExpression extensionReceiver = targetRef.getExtensionReceiver();
        if (owner instanceof IrSimpleFunction) {
            if (dispatchReceiver != null) {
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p0", IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(owner)), (IrDeclarationOrigin) null, 4, (Object) null);
                arrayList.add(dispatchReceiver);
                i = 1;
            } else {
                i = 0;
            }
            if (extensionReceiver != null) {
                int i3 = i + 1;
                String str = "p" + i;
                IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, str, extensionReceiverParameter.getType(), (IrDeclarationOrigin) null, 4, (Object) null);
                arrayList.add(extensionReceiver);
                i2 = 0;
                i = i3;
            } else {
                i2 = 0;
            }
        } else {
            if (!(owner instanceof IrConstructor)) {
                throw new AssertionError("Unexpected function: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            if (dispatchReceiver != null) {
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p0", owner.getValueParameters().get(0).getType(), (IrDeclarationOrigin) null, 4, (Object) null);
                arrayList.add(dispatchReceiver);
                i = 1;
            } else {
                i = 0;
            }
            i2 = i;
        }
        int size = owner.getValueParameters().size() - (samMethod.getValueParameters().size() + ((samMethod.getExtensionReceiverParameter() == null || extensionReceiver != null) ? 0 : 1));
        while (i2 < size) {
            IrValueParameter irValueParameter = owner.getValueParameters().get(i2);
            int i4 = i + 1;
            DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p" + i, irValueParameter.getType(), (IrDeclarationOrigin) null, 4, (Object) null);
            IrExpression valueArgument = targetRef.getValueArgument(i2);
            if (valueArgument == null) {
                wrapClosureInDynamicCall$fail$27(targetRef, "Captured value argument #" + i2 + " (" + RenderIrElementKt.render$default(irValueParameter, (DumpIrTreeOptions) null, 1, (Object) null) + ") not provided");
                throw null;
            }
            arrayList.add(valueArgument);
            i2++;
            i = i4;
        }
        if (arrayList.size() != irSimpleFunctionBuildFunction.getValueParameters().size()) {
            throw new AssertionError("Dynamic call target value parameters (" + irSimpleFunctionBuildFunction.getValueParameters().size() + ") don't match dynamic call arguments (" + arrayList.size() + "):\nirDynamicCallTarget:\n" + DumpIrTreeKt.dump$default(irSimpleFunctionBuildFunction, null, 1, null) + "dynamicCallArguments:\n" + CollectionsKt.joinToString$default(CollectionsKt.withIndex(arrayList), "\n ", "[\n ", "\n]", 0, null, new Function1<IndexedValue<? extends IrExpression>, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.TypeOperatorLowering.wrapClosureInDynamicCall.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IndexedValue<? extends IrExpression> indexedValue) {
                    Intrinsics.checkNotNullParameter(indexedValue, "<name for destructuring parameter 0>");
                    return "#" + indexedValue.getIndex() + ": " + DumpIrTreeKt.dump$default(indexedValue.component2(), null, 1, null);
                }
            }, 24, null));
        }
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) JvmIrBuilderKt.createJvmIrBuilder$default(this.backendContext, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null), irSimpleFunctionBuildFunction.getSymbol());
        int size2 = arrayList.size();
        for (int i5 = 0; i5 < size2; i5++) {
            irCall.putValueArgument(i5, (IrExpression) arrayList.get(i5));
        }
        return irCall;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0060  */
    /* JADX WARN: Code duplicated, block: B:33:0x01a9  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        IrVariableSymbol symbol;
        IrGetValueImpl irGetValueImplIrGet;
        IrVariable irVariable;
        IrValueSymbol symbol2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrBuilderWithScope builder = getBuilder();
        IrTypeOperatorCall irTypeOperatorCall = expression;
        LowerUtilsKt.at(builder, irTypeOperatorCall);
        IrVariable irVariableCreateTemporaryVariable = null;
        switch (WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()]) {
            case 1:
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, expression.getType(), true);
                irBlockBuilder.unaryPlus(transformVoid(expression.getArgument()));
                if (!IrTypeUtilsKt.isSubtypeOf(expression.getArgument().getType(), IrTypesKt.makeNullable(expression.getType()), this.backendContext.getTypeSystem())) {
                    irBlockBuilder.unaryPlus(new IrCompositeImpl(-1, -1, expression.getType(), null, 8, null));
                }
                return irBlockBuilder.getIrBlockBody();
            case 2:
                return ExpressionHelpersKt.irImplicitCast(builder, transformVoid(expression.getArgument()), expression.getTypeOperand());
            case 3:
                return lowerCast(transformVoid(expression.getArgument()), expression.getTypeOperand());
            case 4:
                if (JvmIrTypeUtilsKt.isReifiedTypeParameter(expression.getTypeOperand())) {
                    transformChildrenVoid(irTypeOperatorCall);
                    return expression;
                }
                IrExpression irExpressionTransformVoid = transformVoid(expression.getArgument());
                IrStatementOrigin.SAFE_CALL safe_call = IrStatementOrigin.SAFE_CALL.INSTANCE;
                IrType anyNType = builder.getContext().getIrBuiltIns().getAnyNType();
                if (irExpressionTransformVoid instanceof IrGetValue) {
                    IrGetValue irGetValue = (IrGetValue) irExpressionTransformVoid;
                    if (!IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                        irVariableCreateTemporaryVariable = builder.getScope().createTemporaryVariable(irExpressionTransformVoid, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? irExpressionTransformVoid.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionTransformVoid.getEndOffset() : 0);
                        symbol = irVariableCreateTemporaryVariable.getSymbol();
                    } else {
                        symbol = irGetValue.getSymbol();
                    }
                } else {
                    irVariableCreateTemporaryVariable = builder.getScope().createTemporaryVariable(irExpressionTransformVoid, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType : null, (108 & 32) != 0 ? irExpressionTransformVoid.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionTransformVoid.getEndOffset() : 0);
                    symbol = irVariableCreateTemporaryVariable.getSymbol();
                }
                IrVariable irVariable2 = irVariableCreateTemporaryVariable;
                if (JvmIrTypeUtilsKt.isInlineClassType(symbol.getOwner().getType())) {
                    irGetValueImplIrGet = lowerCast(ExpressionHelpersKt.irGet(builder, symbol.getOwner()), expression.getTypeOperand());
                } else {
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(builder, symbol.getOwner());
                }
                IrIfThenElseImpl irIfThenElseImplIrIfThenElse$default = ExpressionHelpersKt.irIfThenElse$default(builder, expression.getType(), lowerInstanceOf(ExpressionHelpersKt.irGet(builder, symbol.getOwner()), IrTypesKt.makeNotNull(expression.getTypeOperand())), irGetValueImplIrGet, ExpressionHelpersKt.irNull(builder, expression.getType()), null, 16, null);
                if (irVariable2 == null) {
                    return irIfThenElseImplIrIfThenElse$default;
                }
                IrBlockImpl irBlockImpl = new IrBlockImpl(builder.getStartOffset(), builder.getEndOffset(), irIfThenElseImplIrIfThenElse$default.getType(), safe_call);
                irBlockImpl.getStatements().add(irVariable2);
                if (irIfThenElseImplIrIfThenElse$default instanceof IrStatementContainer) {
                    irBlockImpl.getStatements().addAll(((IrStatementContainer) irIfThenElseImplIrIfThenElse$default).getStatements());
                } else {
                    irBlockImpl.getStatements().add(irIfThenElseImplIrIfThenElse$default);
                }
                return irBlockImpl;
            case 5:
                return lowerInstanceOf(transformVoid(expression.getArgument()), expression.getTypeOperand());
            case 6:
                return LowerUtilsKt.irNot(builder, lowerInstanceOf(transformVoid(expression.getArgument()), expression.getTypeOperand()));
            case 7:
                String strComputeNotNullAssertionText = computeNotNullAssertionText(builder, expression);
                IrExpression irExpressionTransformVoid2 = transformVoid(expression.getArgument());
                IrType anyNType2 = builder.getContext().getIrBuiltIns().getAnyNType();
                if (irExpressionTransformVoid2 instanceof IrGetValue) {
                    IrGetValue irGetValue2 = (IrGetValue) irExpressionTransformVoid2;
                    if (!IrUtilsKt.isImmutable(irGetValue2.getSymbol().getOwner())) {
                        IrVariable irVariableCreateTemporaryVariable2 = builder.getScope().createTemporaryVariable(irExpressionTransformVoid2, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType2 : null, (108 & 32) != 0 ? irExpressionTransformVoid2.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionTransformVoid2.getEndOffset() : 0);
                        irVariable = irVariableCreateTemporaryVariable2;
                        symbol2 = irVariableCreateTemporaryVariable2.getSymbol();
                    } else {
                        symbol2 = irGetValue2.getSymbol();
                        irVariable = null;
                    }
                } else {
                    IrVariable irVariableCreateTemporaryVariable3 = builder.getScope().createTemporaryVariable(irExpressionTransformVoid2, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? anyNType2 : null, (108 & 32) != 0 ? irExpressionTransformVoid2.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionTransformVoid2.getEndOffset() : 0);
                    irVariable = irVariableCreateTemporaryVariable3;
                    symbol2 = irVariableCreateTemporaryVariable3.getSymbol();
                }
                IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, expression.getType(), true);
                if (strComputeNotNullAssertionText != null) {
                    IrBlockBuilder irBlockBuilder3 = irBlockBuilder2;
                    IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder3, this.checkExpressionValueIsNotNull);
                    irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder3, symbol2.getOwner()));
                    irCall.putValueArgument(1, ExpressionHelpersKt.irString(irBlockBuilder3, trimForRuntimeAssertion(strComputeNotNullAssertionText)));
                    irBlockBuilder2.unaryPlus(irCall);
                } else {
                    IrBlockBuilder irBlockBuilder4 = irBlockBuilder2;
                    IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder4, this.backendContext.getIr().getSymbols().getCheckNotNull());
                    irCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder4, symbol2.getOwner()));
                    irBlockBuilder2.unaryPlus(irCall2);
                }
                irBlockBuilder2.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder2, symbol2.getOwner()));
                IrContainerExpression irBlockBody = irBlockBuilder2.getIrBlockBody();
                if (irVariable == null) {
                    return irBlockBody;
                }
                IrBlockImpl irBlockImpl2 = new IrBlockImpl(builder.getStartOffset(), builder.getEndOffset(), irBlockBody.getType(), null);
                irBlockImpl2.getStatements().add(irVariable);
                if (irBlockBody instanceof IrStatementContainer) {
                    irBlockImpl2.getStatements().addAll(irBlockBody.getStatements());
                } else {
                    irBlockImpl2.getStatements().add(irBlockBody);
                }
                return irBlockImpl2;
            default:
                transformChildrenVoid(irTypeOperatorCall);
                return expression;
        }
    }

    private final String computeNotNullAssertionText(IrBuilderWithScope irBuilderWithScope, IrTypeOperatorCall irTypeOperatorCall) {
        Name name2;
        String strAsString;
        if (this.backendContext.getConfig().getNoSourceCodeInNotNullAssertionExceptions()) {
            IrExpression argument = irTypeOperatorCall.getArgument();
            if (argument instanceof IrCall) {
                return ((IrCall) argument).getSymbol().getOwner().getName().asString() + "(...)";
            }
            if (argument instanceof IrGetField) {
                return ((IrGetField) argument).getSymbol().getOwner().getName().asString();
            }
            return null;
        }
        IrSymbolOwner owner = irBuilderWithScope.getScope().getScopeOwnerSymbol().getOwner();
        if (owner instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) owner;
            if (isDelegated(irFunction)) {
                return irFunction.getName().asString() + "(...)";
            }
        }
        IrDeclarationParent parent = ExpressionHelpersKt.getParent(irBuilderWithScope);
        IrDeclaration irDeclaration = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
        CharSequence charSequenceSourceViewFor = irDeclaration != null ? sourceViewFor(irDeclaration) : null;
        Pair<Integer, Integer> pairExtents = extents(irTypeOperatorCall);
        int iIntValue = pairExtents.component1().intValue();
        int iIntValue2 = pairExtents.component2().intValue();
        if (charSequenceSourceViewFor != null && validSourcePosition(charSequenceSourceViewFor, iIntValue, iIntValue2)) {
            return charSequenceSourceViewFor.subSequence(iIntValue, iIntValue2).toString();
        }
        IrDeclarationWithName irDeclarationWithName = irDeclaration instanceof IrDeclarationWithName ? (IrDeclarationWithName) irDeclaration : null;
        return (irDeclarationWithName == null || (name2 = irDeclarationWithName.getName()) == null || (strAsString = name2.asString()) == null) ? "Unknown Declaration" : strAsString;
    }

    private final String trimForRuntimeAssertion(String str) {
        String strTrimMiddle = StringUtil.trimMiddle(str, 50);
        Intrinsics.checkNotNullExpressionValue(strTrimMiddle, "trimMiddle(...)");
        return strTrimMiddle;
    }

    private final boolean isDelegated(IrFunction irFunction) {
        return Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE);
    }

    private final boolean validSourcePosition(CharSequence charSequence, int i, int i2) {
        return i >= 0 && i < i2 && i2 < charSequence.length();
    }

    private final Pair<Integer, Integer> extents(IrElement irElement) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = Integer.MAX_VALUE;
        final Ref.IntRef intRef2 = new Ref.IntRef();
        IrVisitorsKt.acceptVoid(irElement, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.TypeOperatorLowering.extents.1
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
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement2, Object obj) {
                visitElement(irElement2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement2, r2);
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
                int startOffset = element.getStartOffset();
                if (startOffset >= 0 && startOffset < intRef.element) {
                    intRef.element = element.getStartOffset();
                }
                if (intRef2.element < element.getEndOffset()) {
                    intRef2.element = element.getEndOffset();
                }
            }
        });
        return TuplesKt.to(Integer.valueOf(intRef.element), Integer.valueOf(intRef2.element));
    }

    private final CharSequence sourceViewFor(IrDeclaration declaration) {
        FileViewProvider viewProvider;
        KtFile ktFile = JvmIrUtilsKt.getKtFile(JvmIrUtilsKt.getFileParent(declaration));
        if (ktFile == null || (viewProvider = ktFile.getViewProvider()) == null) {
            return null;
        }
        return viewProvider.getContents();
    }
}
