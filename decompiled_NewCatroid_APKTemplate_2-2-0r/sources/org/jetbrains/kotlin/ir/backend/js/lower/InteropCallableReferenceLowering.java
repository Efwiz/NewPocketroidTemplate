package org.jetbrains.kotlin.ir.backend.js.lower;

import androidx.exifinterface.media.ExifInterface;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.lower.LoweredStatementOrigins;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
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
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: InteropCallableReferenceLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0004QRSTB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J.\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u00152\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u0018\u0012\b\u0012\u00060\u001cR\u00020\u00000\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000eH\u0002J(\u0010\"\u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u000eH\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eH\u0002JV\u0010+\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000e2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.0-2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010-2\u0014\b\u0002\u00102\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002030-H\u0002J*\u00104\u001a\b\u0012\u0004\u0012\u00020\u00110\u00152\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0016J\u0018\u00106\u001a\u0002072\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\u0011H\u0016J8\u0010=\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002H>0-\"\u0004\b\u0000\u0010>2\u0006\u0010?\u001a\u00020@2\u0014\u0010A\u001a\u0010\u0012\u0004\u0012\u00020.\u0012\u0006\u0012\u0004\u0018\u0001H>0BH\u0002J\u0018\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u0019H\u0002J\u0018\u0010H\u001a\u00020I2\u0006\u0010E\u001a\u00020F2\u0006\u0010J\u001a\u00020\u0019H\u0002J \u0010K\u001a\u00020L2\u0006\u0010M\u001a\u0002012\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020)H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "generateInlineAnonymousFunctions", "", "getGenerateInlineAnonymousFunctions", "()Z", "buildFactoryBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "factoryFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "newDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lambdaInfo", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$LambdaInfo;", "buildFactoryFunction", "", "ctorToFactoryMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "buildFunctionExpression", "ctorToFunctionExpressionMap", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$FunctionExpressionFactory;", "buildLambdaBody", "instance", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "lambdaDeclaration", "invokeFun", "createLambdaDeclaration", "lambdaName", "Lorg/jetbrains/kotlin/name/Name;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "superInvokeFun", "extractReferenceReflectionName", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getter", "inlineLambdaBody", "invokeMapping", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "lambdaContextMapping", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "outerReceiverMapping", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "liftLambda", "ctorToFreeFunctionMap", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "remapCapturedFields", ExifInterface.GPS_DIRECTION_TRUE, "lambdaConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "remapVP", "Lkotlin/Function1;", "replaceLambdaConstructorCallWithFactoryCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "factory", "replaceLambdaConstructorCallWithReferenceToLiftedLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "liftedLambda", "setDynamicProperty", "Lorg/jetbrains/kotlin/ir/IrStatement;", "r", "property", "", "value", "CallableReferenceClassTransformer", "ClosureUsageAnalyser", "FunctionExpressionFactory", "LambdaInfo", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InteropCallableReferenceLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public InteropCallableReferenceLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final boolean getGenerateInlineAnonymousFunctions() {
        return this.context.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_INLINE_ANONYMOUS_FUNCTIONS);
    }

    /* JADX INFO: compiled from: InteropCallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$FunctionExpressionFactory;", "", "lambdaDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "lambdaInfo", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$LambdaInfo;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$LambdaInfo;)V", "createFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ctorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class FunctionExpressionFactory {
        private final IrConstructor constructor;
        private final IrSimpleFunction lambdaDeclaration;
        private final LambdaInfo lambdaInfo;
        final /* synthetic */ InteropCallableReferenceLowering this$0;

        public FunctionExpressionFactory(InteropCallableReferenceLowering interopCallableReferenceLowering, IrSimpleFunction lambdaDeclaration, IrConstructor constructor, LambdaInfo lambdaInfo) {
            Intrinsics.checkNotNullParameter(lambdaDeclaration, "lambdaDeclaration");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(lambdaInfo, "lambdaInfo");
            this.this$0 = interopCallableReferenceLowering;
            this.lambdaDeclaration = lambdaDeclaration;
            this.constructor = constructor;
            this.lambdaInfo = lambdaInfo;
        }

        /* JADX WARN: Code duplicated, block: B:25:0x00b7  */
        public final IrExpression createFunctionExpression(final IrConstructorCall ctorCall) {
            IrValueSymbol symbol;
            Intrinsics.checkNotNullParameter(ctorCall, "ctorCall");
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(this.lambdaInfo.getSuperInvokeFun());
            Object obj = null;
            boolean z = false;
            for (Object obj2 : this.lambdaInfo.getLambdaClass().getSuperTypes()) {
                if (IrTypesKt.getClassifierOrNull((IrType) obj2) == parentAsClass.getSymbol()) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    obj = obj2;
                    z = true;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            IrType irType = (IrType) obj;
            Map mapRemapCapturedFields = this.this$0.remapCapturedFields(this.constructor, new Function1<IrValueParameterSymbol, IrValueSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$FunctionExpressionFactory$createFunctionExpression$lambdaContextMapping$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrValueSymbol invoke(IrValueParameterSymbol ctorParameter) {
                    Intrinsics.checkNotNullParameter(ctorParameter, "ctorParameter");
                    IrExpression valueArgument = ctorCall.getValueArgument(ctorParameter.getOwner().getIndex());
                    Intrinsics.checkNotNull(valueArgument);
                    if (valueArgument instanceof IrGetValue) {
                        return ((IrGetValue) valueArgument).getSymbol();
                    }
                    if (valueArgument instanceof IrTypeOperatorCall) {
                        IrExpression argument = ((IrTypeOperatorCall) valueArgument).getArgument();
                        if (!(argument instanceof IrGetValue)) {
                            argument = null;
                        }
                        IrGetValue irGetValue = (IrGetValue) argument;
                        if (irGetValue != null) {
                            return irGetValue.getSymbol();
                        }
                    }
                    return null;
                }
            });
            Map mapRemapCapturedFields2 = this.this$0.remapCapturedFields(this.constructor, new Function1<IrValueParameterSymbol, IrGetField>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$FunctionExpressionFactory$createFunctionExpression$outerReceiverMapping$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrGetField invoke(IrValueParameterSymbol ctorParameter) {
                    Intrinsics.checkNotNullParameter(ctorParameter, "ctorParameter");
                    IrExpression valueArgument = ctorCall.getValueArgument(ctorParameter.getOwner().getIndex());
                    Intrinsics.checkNotNull(valueArgument);
                    if (!(valueArgument instanceof IrGetField)) {
                        valueArgument = null;
                    }
                    return (IrGetField) valueArgument;
                }
            });
            mapRemapCapturedFields.size();
            mapRemapCapturedFields2.size();
            ctorCall.getValueArgumentsCount();
            IrValueSymbol irValueSymbolCreateFunctionExpression$findDispatchReceiver = createFunctionExpression$findDispatchReceiver(mapRemapCapturedFields.values());
            if (irValueSymbolCreateFunctionExpression$findDispatchReceiver == null) {
                Collection collectionValues = mapRemapCapturedFields2.values();
                ArrayList arrayList = new ArrayList();
                Iterator it = collectionValues.iterator();
                while (it.hasNext()) {
                    IrExpression receiver = ((IrGetField) it.next()).getReceiver();
                    if (receiver == null) {
                        symbol = null;
                    } else {
                        if (!(receiver instanceof IrGetValue)) {
                            receiver = null;
                        }
                        IrGetValue irGetValue = (IrGetValue) receiver;
                        if (irGetValue != null) {
                            symbol = irGetValue.getSymbol();
                        } else {
                            symbol = null;
                        }
                    }
                    if (symbol != null) {
                        arrayList.add(symbol);
                    }
                }
                irValueSymbolCreateFunctionExpression$findDispatchReceiver = createFunctionExpression$findDispatchReceiver(arrayList);
            }
            IrValueSymbol irValueSymbol = irValueSymbolCreateFunctionExpression$findDispatchReceiver;
            IrSimpleFunction irSimpleFunction = this.lambdaDeclaration;
            irSimpleFunction.setBody(this.this$0.inlineLambdaBody(irSimpleFunction, this.lambdaInfo.getInvokeFun(), this.lambdaInfo.createOldToNewInvokeParametersMapping(this.lambdaDeclaration), mapRemapCapturedFields, mapRemapCapturedFields2));
            IrFunctionExpressionImpl irFunctionExpressionImpl = new IrFunctionExpressionImpl(ctorCall.getStartOffset(), ctorCall.getEndOffset(), irType, this.lambdaDeclaration, JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE);
            if (irValueSymbol == null) {
                return irFunctionExpressionImpl;
            }
            IrCallImpl irCallImpl = new IrCallImpl(-1, -1, this.this$0.getContext().getIrBuiltIns().getAnyType(), this.this$0.getContext().getIntrinsics().getJsBind(), 0, 2, JsStatementOrigins.BIND_CALL.INSTANCE, null, 128, null);
            irCallImpl.putValueArgument(0, new IrGetValueImpl(-1, -1, irValueSymbol, null, 8, null));
            irCallImpl.putValueArgument(1, irFunctionExpressionImpl);
            return irCallImpl;
        }

        private static final IrValueSymbol createFunctionExpression$findDispatchReceiver(Iterable<? extends IrValueSymbol> iterable) {
            IrValueSymbol next;
            Iterator<? extends IrValueSymbol> it = iterable.iterator();
            while (it.hasNext()) {
                next = it.next();
                if (MiscKt.isDispatchReceiver(next.getOwner())) {
                    return next;
                }
            }
            next = null;
            return next;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final HashMap map = new HashMap();
        final HashMap map2 = new HashMap();
        final HashMap map3 = new HashMap();
        ClosureUsageAnalyser closureUsageAnalyser = new ClosureUsageAnalyser();
        if (getGenerateInlineAnonymousFunctions()) {
            IrVisitorsKt.acceptChildrenVoid(irFile, closureUsageAnalyser);
        }
        irFile.transform((IrElementTransformer<? super Object>) new CallableReferenceClassTransformer(this, map3, map, map2, closureUsageAnalyser), (Object) null);
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitConstructorCall(IrConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                if (!Intrinsics.areEqual(expression.getOrigin(), JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE)) {
                    return expression;
                }
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = map.get(expression.getSymbol());
                if (irSimpleFunctionSymbol != null) {
                    return this.replaceLambdaConstructorCallWithReferenceToLiftedLambda(expression, irSimpleFunctionSymbol);
                }
                FunctionExpressionFactory functionExpressionFactory = map2.get(expression.getSymbol());
                if (functionExpressionFactory != null) {
                    IrExpression irExpressionCreateFunctionExpression = functionExpressionFactory.createFunctionExpression(expression);
                    transformChildrenVoid(irExpressionCreateFunctionExpression);
                    return irExpressionCreateFunctionExpression;
                }
                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = map3.get(expression.getSymbol());
                if (irSimpleFunctionSymbol2 != null) {
                    return this.replaceLambdaConstructorCallWithFactoryCall(expression, irSimpleFunctionSymbol2);
                }
                return expression;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall replaceLambdaConstructorCallWithFactoryCall(IrConstructorCall expression, IrSimpleFunctionSymbol factory) {
        IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), factory, expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), expression.getOrigin(), null, 128, null);
        irCallImpl.setDispatchReceiver(expression.getDispatchReceiver());
        irCallImpl.setExtensionReceiver(expression.getExtensionReceiver());
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            irCallImpl.putTypeArgument(i, expression.getTypeArgument(i));
        }
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
            irCallImpl.putValueArgument(i2, expression.getValueArgument(i2));
        }
        return irCallImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrRawFunctionReference replaceLambdaConstructorCallWithReferenceToLiftedLambda(IrConstructorCall expression, IrSimpleFunctionSymbol liftedLambda) {
        return new IrRawFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), liftedLambda);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        CompilationExceptionKt.compilationException("Unreachable", irBody);
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: InteropCallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\f\u0010 \u001a\u00020\u0011*\u00020\u0019H\u0002J\f\u0010 \u001a\u00020\u0011*\u00020\u0007H\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$ClosureUsageAnalyser;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "()V", "lambdaConstructorCalls", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "loopNestedness", "", "variablesDeclaredInLoops", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getLambdaConstructorCalls", "", "constructorSymbol", "lambdaCapturesVariablesDeclaredInLoops", "", "lambdaClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructorCall", "", "expression", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitVariable", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "referencesVariablesDeclaredInLoops", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ClosureUsageAnalyser implements IrElementVisitorVoid {
        private int loopNestedness;
        private final Map<IrConstructorSymbol, List<IrConstructorCall>> lambdaConstructorCalls = new HashMap();
        private final Set<IrValueDeclaration> variablesDeclaredInLoops = new HashSet();

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
        public void visitLoop(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            this.loopNestedness++;
            IrVisitorsKt.acceptChildrenVoid(loop, this);
            this.loopNestedness--;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (this.loopNestedness > 0) {
                this.variablesDeclaredInLoops.add(declaration);
            }
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (Intrinsics.areEqual(expression.getOrigin(), JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE)) {
                Map<IrConstructorSymbol, List<IrConstructorCall>> map = this.lambdaConstructorCalls;
                IrConstructorSymbol symbol = expression.getSymbol();
                ArrayList arrayList = map.get(symbol);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    map.put(symbol, arrayList);
                }
                arrayList.add(expression);
            }
            IrVisitorsKt.acceptChildrenVoid(expression, this);
        }

        private final boolean referencesVariablesDeclaredInLoops(IrElement irElement) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            IrVisitorsKt.acceptVoid(irElement, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$ClosureUsageAnalyser$referencesVariablesDeclaredInLoops$1
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
                    if (booleanRef.element) {
                        return;
                    }
                    IrVisitorsKt.acceptChildrenVoid(element, this);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetValue(IrGetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    if (this.variablesDeclaredInLoops.contains(expression.getSymbol().getOwner())) {
                        booleanRef.element = true;
                    } else {
                        IrVisitorsKt.acceptChildrenVoid(expression, this);
                    }
                }
            });
            return booleanRef.element;
        }

        private final boolean referencesVariablesDeclaredInLoops(IrConstructorCall irConstructorCall) {
            Iterable iterableUntil = RangesKt.until(0, irConstructorCall.getValueArgumentsCount());
            if ((iterableUntil instanceof Collection) && ((Collection) iterableUntil).isEmpty()) {
                return false;
            }
            Iterator it = iterableUntil.iterator();
            while (it.hasNext()) {
                IrExpression valueArgument = irConstructorCall.getValueArgument(((IntIterator) it).nextInt());
                Intrinsics.checkNotNull(valueArgument);
                if (referencesVariablesDeclaredInLoops(valueArgument)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean lambdaCapturesVariablesDeclaredInLoops(IrClass lambdaClass) {
            List<IrConstructorCall> list;
            Intrinsics.checkNotNullParameter(lambdaClass, "lambdaClass");
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(lambdaClass);
            if (primaryConstructor == null || (list = this.lambdaConstructorCalls.get(primaryConstructor.getSymbol())) == null) {
                return false;
            }
            List<IrConstructorCall> list2 = list;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                return false;
            }
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (referencesVariablesDeclaredInLoops((IrConstructorCall) it.next())) {
                    return true;
                }
            }
            return false;
        }

        public final List<IrConstructorCall> getLambdaConstructorCalls(IrConstructorSymbol constructorSymbol) {
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            List<IrConstructorCall> list = this.lambdaConstructorCalls.get(constructorSymbol);
            return list == null ? CollectionsKt.emptyList() : list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: InteropCallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001BM\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\bR\u00020\t0\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0018H\u0016J\u000e\u0010\u0019\u001a\u0004\u0018\u00010\u0011*\u00020\u000fH\u0002J\u0014\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e*\u00020\u000fH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\bR\u00020\t0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$CallableReferenceClassTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "ctorToFactoryMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "ctorToFreeFunctionMap", "ctorToFunctionExpressionMap", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$FunctionExpressionFactory;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering;", "closureUsageAnalyser", "Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$ClosureUsageAnalyser;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$ClosureUsageAnalyser;)V", "replaceWithFactory", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lambdaClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "asCallableReference", "transformCallableReference", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class CallableReferenceClassTransformer extends IrElementTransformerVoid {
        private final ClosureUsageAnalyser closureUsageAnalyser;
        private final Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFactoryMap;
        private final Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFreeFunctionMap;
        private final Map<IrConstructorSymbol, FunctionExpressionFactory> ctorToFunctionExpressionMap;
        final /* synthetic */ InteropCallableReferenceLowering this$0;

        public CallableReferenceClassTransformer(InteropCallableReferenceLowering interopCallableReferenceLowering, Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFactoryMap, Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFreeFunctionMap, Map<IrConstructorSymbol, FunctionExpressionFactory> ctorToFunctionExpressionMap, ClosureUsageAnalyser closureUsageAnalyser) {
            Intrinsics.checkNotNullParameter(ctorToFactoryMap, "ctorToFactoryMap");
            Intrinsics.checkNotNullParameter(ctorToFreeFunctionMap, "ctorToFreeFunctionMap");
            Intrinsics.checkNotNullParameter(ctorToFunctionExpressionMap, "ctorToFunctionExpressionMap");
            Intrinsics.checkNotNullParameter(closureUsageAnalyser, "closureUsageAnalyser");
            this.this$0 = interopCallableReferenceLowering;
            this.ctorToFactoryMap = ctorToFactoryMap;
            this.ctorToFreeFunctionMap = ctorToFreeFunctionMap;
            this.ctorToFunctionExpressionMap = ctorToFunctionExpressionMap;
            this.closureUsageAnalyser = closureUsageAnalyser;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrFile visitFile(IrFile declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            transformChildrenVoid(declaration);
            TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$CallableReferenceClassTransformer$visitFile$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<IrDeclaration> invoke(IrDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.transformCallableReference(it);
                }
            });
            return declaration;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitClass(IrClass declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            transformChildrenVoid(declaration);
            TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$CallableReferenceClassTransformer$visitClass$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<IrDeclaration> invoke(IrDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.transformCallableReference(it);
                }
            });
            return declaration;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitScript(IrScript declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            transformChildrenVoid(declaration);
            List<IrStatement> statements = declaration.getStatements();
            int iReplaceInPlace = 0;
            while (iReplaceInPlace < statements.size()) {
                IrStatement irStatement = statements.get(iReplaceInPlace);
                iReplaceInPlace = TransformKt.replaceInPlace(statements, irStatement instanceof IrDeclaration ? transformCallableReference((IrDeclaration) irStatement) : null, iReplaceInPlace);
            }
            return declaration;
        }

        private final IrClass asCallableReference(IrDeclaration irDeclaration) {
            if ((Intrinsics.areEqual(irDeclaration.getOrigin(), CallableReferenceLowering.Companion.FUNCTION_REFERENCE_IMPL.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), CallableReferenceLowering.Companion.LAMBDA_IMPL.INSTANCE)) && (irDeclaration instanceof IrClass)) {
                return (IrClass) irDeclaration;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<IrDeclaration> transformCallableReference(IrDeclaration irDeclaration) {
            IrClass irClassAsCallableReference = asCallableReference(irDeclaration);
            if (irClassAsCallableReference != null) {
                return replaceWithFactory(irClassAsCallableReference);
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x005b  */
        private final List<IrDeclaration> replaceWithFactory(IrClass lambdaClass) {
            List<IrDeclaration> listBuildFactoryFunction;
            LambdaInfo lambdaInfo = new LambdaInfo(lambdaClass);
            if (!Intrinsics.areEqual(lambdaClass.getOrigin(), CallableReferenceLowering.Companion.LAMBDA_IMPL.INSTANCE) || lambdaInfo.getIsSuspendLambda()) {
                listBuildFactoryFunction = this.this$0.buildFactoryFunction(this.ctorToFactoryMap, lambdaInfo);
            } else if (SequencesKt.none(IrUtilsKt.getFields(lambdaClass))) {
                listBuildFactoryFunction = this.this$0.liftLambda(this.ctorToFreeFunctionMap, lambdaInfo);
            } else if (!this.this$0.getGenerateInlineAnonymousFunctions()) {
                listBuildFactoryFunction = this.this$0.buildFactoryFunction(this.ctorToFactoryMap, lambdaInfo);
            } else {
                ClosureUsageAnalyser closureUsageAnalyser = this.closureUsageAnalyser;
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(lambdaClass);
                Intrinsics.checkNotNull(primaryConstructor);
                if (closureUsageAnalyser.getLambdaConstructorCalls(primaryConstructor.getSymbol()).size() != 1 || this.closureUsageAnalyser.lambdaCapturesVariablesDeclaredInLoops(lambdaClass)) {
                    listBuildFactoryFunction = this.this$0.buildFactoryFunction(this.ctorToFactoryMap, lambdaInfo);
                } else {
                    listBuildFactoryFunction = this.this$0.buildFunctionExpression(this.ctorToFunctionExpressionMap, lambdaInfo);
                }
            }
            List<IrDeclaration> list = listBuildFactoryFunction;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((IrDeclaration) it.next()).setParent(lambdaClass.getParent());
            }
            return list;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ IrBlockBody inlineLambdaBody$default(InteropCallableReferenceLowering interopCallableReferenceLowering, IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2, Map map, Map map2, Map map3, int i, Object obj) {
        if ((i & 16) != 0) {
            map3 = MapsKt.emptyMap();
        }
        return interopCallableReferenceLowering.inlineLambdaBody(irSimpleFunction, irSimpleFunction2, map, map2, map3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrBlockBody inlineLambdaBody(final IrSimpleFunction lambdaDeclaration, final IrSimpleFunction invokeFun, final Map<IrValueParameterSymbol, ? extends IrValueParameterSymbol> invokeMapping, final Map<IrFieldSymbol, ? extends IrValueSymbol> lambdaContextMapping, final Map<IrFieldSymbol, ? extends IrGetField> outerReceiverMapping) {
        IrBody body = invokeFun.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null) {
            CompilationExceptionKt.compilationException("invoke() method has to have a body", (IrDeclaration) invokeFun);
            throw null;
        }
        IrBlockBody irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), invokeFun.getStartOffset(), invokeFun.getEndOffset(), irBlockBody.getStatements());
        IrBlockBody irBlockBody2 = irBlockBodyCreateBlockBody;
        IrElementTransformerVoidKt.transformChildrenVoid(irBlockBody2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering.inlineLambdaBody.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetField(IrGetField expression) {
                IrExpression irExpression;
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                IrValueSymbol irValueSymbol = lambdaContextMapping.get(expression.getSymbol());
                if (irValueSymbol != null) {
                    return InteropCallableReferenceLowering.inlineLambdaBody$getValue(expression, irValueSymbol);
                }
                IrGetField irGetField = outerReceiverMapping.get(expression.getSymbol());
                if (irGetField != null) {
                    int startOffset = expression.getStartOffset();
                    int endOffset = expression.getEndOffset();
                    IrFieldSymbol symbol = irGetField.getSymbol();
                    IrType type = irGetField.getType();
                    IrExpression receiver = irGetField.getReceiver();
                    if (receiver != null) {
                        IrExpression irExpression2 = receiver;
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                        IrVisitorsKt.acceptVoid(irExpression2, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                        if (irElementPatchDeclarationParents != null) {
                            irExpression = (IrExpression) irElementPatchDeclarationParents;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    } else {
                        irExpression = null;
                    }
                    return new IrGetFieldImpl(startOffset, endOffset, symbol, type, irExpression, null, null, 96, null);
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                IrValueParameterSymbol irValueParameterSymbol = invokeMapping.get(expression.getSymbol());
                if (irValueParameterSymbol == null) {
                    return expression;
                }
                return InteropCallableReferenceLowering.inlineLambdaBody$getCastedValue(expression, irValueParameterSymbol, invokeFun.getValueParameters().get(irValueParameterSymbol.getOwner().getIndex()).getType());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                if (!Intrinsics.areEqual(expression.getReturnTargetSymbol(), invokeFun.getSymbol())) {
                    return expression;
                }
                return new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), lambdaDeclaration.getSymbol(), expression.getValue());
            }
        });
        PatchDeclarationParentsKt.patchDeclarationParents(irBlockBody2, lambdaDeclaration);
        if (IrTypePredicatesKt.isUnit(invokeFun.getReturnType())) {
            irBlockBodyCreateBlockBody.getStatements().add(new IrReturnImpl(IrUtilsKt.getPreviousOffset(irBlockBodyCreateBlockBody.getEndOffset()), IrUtilsKt.getPreviousOffset(irBlockBodyCreateBlockBody.getEndOffset()), this.context.getIrBuiltIns().getNothingType(), lambdaDeclaration.getSymbol(), JsIrBuilder.INSTANCE.buildGetObjectValue(this.context.getIrBuiltIns().getUnitType(), this.context.getIrBuiltIns().getUnitClass())));
        }
        return irBlockBodyCreateBlockBody;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression inlineLambdaBody$getValue(IrExpression irExpression, IrValueSymbol irValueSymbol) {
        return new IrGetValueImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irValueSymbol, null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression inlineLambdaBody$getCastedValue(IrExpression irExpression, IrValueSymbol irValueSymbol, IrType irType) {
        return new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrTypeOperator.IMPLICIT_CAST, irType, inlineLambdaBody$getValue(irExpression, irValueSymbol));
    }

    private final IrBlockBody buildLambdaBody(IrVariable instance, IrSimpleFunction lambdaDeclaration, IrSimpleFunction invokeFun) {
        IrCallImpl irCallImpl = new IrCallImpl(-1, -1, invokeFun.getReturnType(), invokeFun.getSymbol(), 0, invokeFun.getValueParameters().size(), JsStatementOrigins.EXPLICIT_INVOKE.INSTANCE, null);
        irCallImpl.setDispatchReceiver(buildLambdaBody$getValue$1(instance));
        Iterator<IrValueParameter> it = lambdaDeclaration.getValueParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            irCallImpl.putValueArgument(i, buildLambdaBody$getValue$1(it.next()));
            i++;
        }
        return IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(new IrReturnImpl(-1, -1, this.context.getIrBuiltIns().getNothingType(), lambdaDeclaration.getSymbol(), irCallImpl)));
    }

    private static final IrExpression buildLambdaBody$getValue$1(IrValueDeclaration irValueDeclaration) {
        return new IrGetValueImpl(-1, -1, irValueDeclaration.getSymbol(), null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> Map<IrFieldSymbol, T> remapCapturedFields(IrConstructor lambdaConstructor, final Function1<? super IrValueParameterSymbol, ? extends T> remapVP) {
        List<IrStatement> statements;
        IrBody body = lambdaConstructor.getBody();
        if (body == null || (statements = ((IrBlockBody) body).getStatements()) == null) {
            CompilationExceptionKt.compilationException("Expecting Body for function ref constructor", (IrDeclaration) lambdaConstructor);
            throw null;
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(statements), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$remapCapturedFields$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSetField);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return MapsKt.toMap(SequencesKt.mapNotNull(SequencesKt.filter(sequenceFilter, new Function1<IrSetField, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering.remapCapturedFields.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSetField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getOrigin(), LoweredStatementOrigins.STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE.INSTANCE));
            }
        }), new Function1<IrSetField, Pair<? extends IrFieldSymbol, ? extends T>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering.remapCapturedFields.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Pair<IrFieldSymbol, T> invoke(IrSetField irSetField) {
                Intrinsics.checkNotNullParameter(irSetField, "irSetField");
                Function1<IrValueParameterSymbol, T> function1 = remapVP;
                IrExpression value = irSetField.getValue();
                if (value == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrGetValue");
                }
                IrValueSymbol symbol = ((IrGetValue) value).getSymbol();
                if (symbol != null) {
                    T tInvoke = function1.invoke((IrValueParameterSymbol) symbol);
                    if (tInvoke != null) {
                        return TuplesKt.to(irSetField.getSymbol(), tInvoke);
                    }
                    return null;
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol");
            }
        }));
    }

    private final IrExpression extractReferenceReflectionName(IrSimpleFunction getter) {
        IrBody body = getter.getBody();
        if (body == null) {
            CompilationExceptionKt.compilationException("Expected body", (IrDeclaration) getter);
            throw null;
        }
        IrStatement irStatement = ((IrBlockBody) body).getStatements().get(0);
        Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrReturn");
        return ((IrReturn) irStatement).getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: InteropCallableReferenceLowering.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0006J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InteropCallableReferenceLowering$LambdaInfo;", "", "lambdaClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "invokeFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getInvokeFun", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isSuspendLambda", "", "()Z", "getLambdaClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "superInvokeFun", "getSuperInvokeFun", "createOldToNewInvokeParametersMapping", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "lambdaDeclaration", "lambdaInnerClasses", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class LambdaInfo {
        private final IrSimpleFunction invokeFun;
        private final boolean isSuspendLambda;
        private final IrClass lambdaClass;
        private final IrSimpleFunction superInvokeFun;

        public LambdaInfo(IrClass lambdaClass) {
            Intrinsics.checkNotNullParameter(lambdaClass, "lambdaClass");
            this.lambdaClass = lambdaClass;
            IrSimpleFunction invokeFun = IrUtilsKt.getInvokeFun(lambdaClass);
            Intrinsics.checkNotNull(invokeFun);
            this.invokeFun = invokeFun;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : invokeFun.getOverriddenSymbols()) {
                if (irSimpleFunctionSymbol.getOwner().getIsSuspend() == this.invokeFun.getIsSuspend()) {
                    this.superInvokeFun = irSimpleFunctionSymbol.getOwner();
                    List<IrSimpleFunctionSymbol> overriddenSymbols = this.invokeFun.getOverriddenSymbols();
                    boolean z = false;
                    if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                        Iterator<T> it = overriddenSymbols.iterator();
                        while (it.hasNext()) {
                            if (((IrSimpleFunctionSymbol) it.next()).getOwner().getIsSuspend()) {
                                z = true;
                                break;
                            }
                        }
                    }
                    this.isSuspendLambda = z;
                    return;
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }

        public final IrClass getLambdaClass() {
            return this.lambdaClass;
        }

        public final IrSimpleFunction getInvokeFun() {
            return this.invokeFun;
        }

        public final IrSimpleFunction getSuperInvokeFun() {
            return this.superInvokeFun;
        }

        /* JADX INFO: renamed from: isSuspendLambda, reason: from getter */
        public final boolean getIsSuspendLambda() {
            return this.isSuspendLambda;
        }

        public final Map<IrValueParameterSymbol, IrValueParameterSymbol> createOldToNewInvokeParametersMapping(IrSimpleFunction lambdaDeclaration) {
            Intrinsics.checkNotNullParameter(lambdaDeclaration, "lambdaDeclaration");
            List<IrValueParameter> valueParameters = this.invokeFun.getValueParameters();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(valueParameters, 10)), 16));
            for (IrValueParameter irValueParameter : valueParameters) {
                linkedHashMap.put(irValueParameter.getSymbol(), lambdaDeclaration.getValueParameters().get(irValueParameter.getIndex()).getSymbol());
            }
            return linkedHashMap;
        }

        public final List<IrDeclaration> lambdaInnerClasses() {
            List<IrDeclaration> declarations = this.lambdaClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                IrDeclaration irDeclaration = (IrDeclaration) obj;
                if ((irDeclaration instanceof IrClass) || ((irDeclaration instanceof IrSimpleFunction) && ((IrSimpleFunction) irDeclaration).getDispatchReceiverParameter() == null)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
    }

    private final IrBlockBody buildFactoryBody(final IrSimpleFunction factoryFunction, List<IrDeclaration> newDeclarations, LambdaInfo lambdaInfo) {
        IrConstructor irConstructor;
        IrSimpleFunction irSimpleFunction;
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(lambdaInfo.getSuperInvokeFun());
        Name nameIdentifier = Name.identifier(lambdaInfo.getLambdaClass().getName().asString() + "$lambda");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrSimpleFunction irSimpleFunction2 = factoryFunction;
        IrSimpleFunction irSimpleFunctionCreateLambdaDeclaration = createLambdaDeclaration(lambdaInfo.getInvokeFun(), nameIdentifier, irSimpleFunction2, lambdaInfo.getSuperInvokeFun());
        ArrayList arrayList = new ArrayList(4);
        Iterator<T> it = lambdaInfo.getLambdaClass().getDeclarations().iterator();
        do {
            if (!it.hasNext()) {
                irConstructor = null;
                break;
            }
            IrDeclaration irDeclaration = (IrDeclaration) it.next();
            irConstructor = irDeclaration instanceof IrConstructor ? (IrConstructor) irDeclaration : null;
        } while (irConstructor == null);
        if (irConstructor == null) {
            throw new NoSuchElementException("No element of the collection was transformed to a non-null value.");
        }
        boolean z = false;
        if (lambdaInfo.getIsSuspendLambda()) {
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(lambdaInfo.getLambdaClass());
            IrVariable irVariableBuildVar = JsIrBuilder.INSTANCE.buildVar(defaultType, irSimpleFunction2, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : null, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
            IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(-1, -1, defaultType, irConstructor.getSymbol(), lambdaInfo.getLambdaClass().getTypeParameters().size(), irConstructor.getTypeParameters().size(), irConstructor.getValueParameters().size(), null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
            int i = 0;
            for (IrValueParameter irValueParameter : factoryFunction.getValueParameters()) {
                irConstructorCallImpl.putValueArgument(i, new IrGetValueImpl(irVariableBuildVar.getStartOffset(), irVariableBuildVar.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
                i++;
            }
            irVariableBuildVar.setInitializer(irConstructorCallImpl);
            arrayList.add(irVariableBuildVar);
            irSimpleFunctionCreateLambdaDeclaration.setBody(buildLambdaBody(irVariableBuildVar, irSimpleFunctionCreateLambdaDeclaration, lambdaInfo.getInvokeFun()));
            newDeclarations.add(lambdaInfo.getLambdaClass());
            irSimpleFunction = irSimpleFunctionCreateLambdaDeclaration;
        } else {
            Map mapRemapCapturedFields = remapCapturedFields(irConstructor, new Function1<IrValueParameterSymbol, IrValueParameterSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$buildFactoryBody$fieldToParameterMapping$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrValueParameterSymbol invoke(IrValueParameterSymbol it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return factoryFunction.getValueParameters().get(it2.getOwner().getIndex()).getSymbol();
                }
            });
            irSimpleFunction = irSimpleFunctionCreateLambdaDeclaration;
            irSimpleFunction.setBody(inlineLambdaBody$default(this, irSimpleFunctionCreateLambdaDeclaration, lambdaInfo.getInvokeFun(), lambdaInfo.createOldToNewInvokeParametersMapping(irSimpleFunctionCreateLambdaDeclaration), mapRemapCapturedFields, null, 16, null));
            newDeclarations.addAll(lambdaInfo.lambdaInnerClasses());
        }
        Object obj = null;
        for (Object obj2 : lambdaInfo.getLambdaClass().getSuperTypes()) {
            if (IrTypesKt.getClassifierOrNull((IrType) obj2) == parentAsClass.getSymbol()) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj = obj2;
            }
        }
        if (z) {
            IrClass lambdaClass = lambdaInfo.getLambdaClass();
            IrFunctionExpressionImpl irFunctionExpressionImpl = new IrFunctionExpressionImpl(lambdaClass.getStartOffset(), lambdaClass.getEndOffset(), (IrType) obj, irSimpleFunction, JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE);
            IrSimpleFunction irSimpleFunction3 = this.context.getMapping().getReflectedNameAccessor().get(lambdaInfo.getLambdaClass());
            if (irSimpleFunction3 != null || irSimpleFunction.getIsSuspend()) {
                IrVariable irVariableBuildVar2 = JsIrBuilder.INSTANCE.buildVar(irFunctionExpressionImpl.getType(), irSimpleFunction2, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : "l", (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : irFunctionExpressionImpl, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
                arrayList.add(irVariableBuildVar2);
                if (irSimpleFunction3 != null) {
                    arrayList.add(setDynamicProperty(irVariableBuildVar2.getSymbol(), Namer.INSTANCE.getKCALLABLE_NAME(), extractReferenceReflectionName(irSimpleFunction3)));
                }
                if (irSimpleFunction.getIsSuspend()) {
                    arrayList.add(setDynamicProperty(irVariableBuildVar2.getSymbol(), Namer.KCALLABLE_ARITY, IrConstImpl.INSTANCE.m7211int(-1, -1, this.context.getIrBuiltIns().getIntType(), irSimpleFunction.getValueParameters().size())));
                }
                arrayList.add(JsIrBuilder.INSTANCE.buildReturn(factoryFunction.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar2.getSymbol(), null, 2, null), this.context.getIrBuiltIns().getNothingType()));
            } else {
                arrayList.add(JsIrBuilder.INSTANCE.buildReturn(factoryFunction.getSymbol(), irFunctionExpressionImpl, this.context.getIrBuiltIns().getNothingType()));
            }
            return IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, arrayList);
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final IrSimpleFunction createLambdaDeclaration(IrSimpleFunction invokeFun, Name lambdaName, IrDeclarationParent parent, IrSimpleFunction superInvokeFun) {
        IrType anyNType = this.context.getIrBuiltIns().getAnyNType();
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(invokeFun.getStartOffset());
        irFunctionBuilder.setEndOffset(invokeFun.getEndOffset());
        irFunctionBuilder.setReturnType(invokeFun.getIsSuspend() ? invokeFun.getReturnType() : anyNType);
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        irFunctionBuilder.setName(lambdaName);
        irFunctionBuilder.setSuspend(invokeFun.getIsSuspend());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parent);
        List<IrValueParameter> valueParameters = superInvokeFun.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = invokeFun.getValueParameters().get(i);
            ArrayList arrayList2 = arrayList;
            arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) obj, irSimpleFunctionBuildFunction, irValueParameter.getOrigin(), 0, irValueParameter.getStartOffset(), irValueParameter.getEndOffset(), irValueParameter.getName(), null, anyNType, null, null, false, false, false, 8004, null));
            arrayList = arrayList2;
            i = i2;
            irSimpleFunctionBuildFunction = irSimpleFunctionBuildFunction;
        }
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        irSimpleFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        return irSimpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> buildFactoryFunction(Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFactoryMap, final LambdaInfo lambdaInfo) {
        ArrayList arrayList = new ArrayList();
        IrConstructor irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(lambdaInfo.getLambdaClass()));
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) this.context.getIrFactory().getStageController().restrictTo(lambdaInfo.getLambdaClass(), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InteropCallableReferenceLowering$buildFactoryFunction$factoryDeclaration$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrFactory irFactory = this.this$0.getContext().getIrFactory();
                InteropCallableReferenceLowering.LambdaInfo lambdaInfo2 = lambdaInfo;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.setStartOffset(lambdaInfo2.getLambdaClass().getStartOffset());
                irFunctionBuilder.setEndOffset(lambdaInfo2.getLambdaClass().getEndOffset());
                irFunctionBuilder.setVisibility(lambdaInfo2.getLambdaClass().getVisibility());
                irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(lambdaInfo2.getLambdaClass()));
                irFunctionBuilder.setName(lambdaInfo2.getLambdaClass().getName());
                irFunctionBuilder.setOrigin(JsStatementOrigins.FACTORY_ORIGIN.INSTANCE);
                return DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            }
        });
        List<IrValueParameter> valueParameters = irConstructor.getValueParameters();
        ArrayList arrayList2 = new ArrayList(valueParameters.size());
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        }
        irSimpleFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
        List<IrTypeParameter> typeParameters = irConstructor.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(typeParameters.size());
        for (IrTypeParameter irTypeParameter : typeParameters) {
            IrTypeParameter irTypeParameterCopyToWithoutSuperTypes$default = IrUtilsKt.copyToWithoutSuperTypes$default(irTypeParameter, irSimpleFunction, 0, null, 6, null);
            irTypeParameterCopyToWithoutSuperTypes$default.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irTypeParameterCopyToWithoutSuperTypes$default.getSuperTypes(), (List) irTypeParameter.getSuperTypes()));
            arrayList3.add(irTypeParameterCopyToWithoutSuperTypes$default);
        }
        irSimpleFunction.setTypeParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3));
        irSimpleFunction.setBody(buildFactoryBody(irSimpleFunction, arrayList, lambdaInfo));
        arrayList.add(irSimpleFunction);
        ctorToFactoryMap.put(irConstructor.getSymbol(), irSimpleFunction.getSymbol());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> buildFunctionExpression(Map<IrConstructorSymbol, FunctionExpressionFactory> ctorToFunctionExpressionMap, LambdaInfo lambdaInfo) {
        IrSimpleFunction irSimpleFunctionCreateLambdaDeclaration = createLambdaDeclaration(lambdaInfo.getInvokeFun(), lambdaInfo.getLambdaClass().getName(), lambdaInfo.getLambdaClass().getParent(), lambdaInfo.getSuperInvokeFun());
        IrConstructor irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(lambdaInfo.getLambdaClass()));
        ctorToFunctionExpressionMap.put(irConstructor.getSymbol(), new FunctionExpressionFactory(this, irSimpleFunctionCreateLambdaDeclaration, irConstructor, lambdaInfo));
        return lambdaInfo.lambdaInnerClasses();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> liftLambda(Map<IrConstructorSymbol, IrSimpleFunctionSymbol> ctorToFreeFunctionMap, LambdaInfo lambdaInfo) {
        IrConstructor irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(lambdaInfo.getLambdaClass()));
        ArrayList arrayList = new ArrayList();
        IrSimpleFunction irSimpleFunctionCreateLambdaDeclaration = createLambdaDeclaration(lambdaInfo.getInvokeFun(), lambdaInfo.getLambdaClass().getName(), lambdaInfo.getLambdaClass().getParent(), lambdaInfo.getSuperInvokeFun());
        irSimpleFunctionCreateLambdaDeclaration.setBody(inlineLambdaBody$default(this, irSimpleFunctionCreateLambdaDeclaration, lambdaInfo.getInvokeFun(), lambdaInfo.createOldToNewInvokeParametersMapping(irSimpleFunctionCreateLambdaDeclaration), MapsKt.emptyMap(), null, 16, null));
        arrayList.add(irSimpleFunctionCreateLambdaDeclaration);
        arrayList.addAll(lambdaInfo.lambdaInnerClasses());
        ctorToFreeFunctionMap.put(irConstructor.getSymbol(), irSimpleFunctionCreateLambdaDeclaration.getSymbol());
        return arrayList;
    }

    private final IrStatement setDynamicProperty(IrValueSymbol r, String property, IrExpression value) {
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(-1, -1, this.context.getIrBuiltIns().getUnitType(), IrDynamicOperator.EQ);
        irDynamicOperatorExpressionImpl.setReceiver(new IrDynamicMemberExpressionImpl(irDynamicOperatorExpressionImpl.getStartOffset(), irDynamicOperatorExpressionImpl.getEndOffset(), this.context.getDynamicType(), property, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, r, null, 2, null)));
        irDynamicOperatorExpressionImpl.getArguments().add(value);
        return irDynamicOperatorExpressionImpl;
    }
}
