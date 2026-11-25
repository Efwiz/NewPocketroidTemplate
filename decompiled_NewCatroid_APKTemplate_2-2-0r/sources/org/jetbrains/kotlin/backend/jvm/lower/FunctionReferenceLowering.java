package org.jetbrains.kotlin.backend.jvm.lower;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.SamEqualsHashCodeMethodsGenerator;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.lower.indy.LambdaMetafactoryArguments;
import org.jetbrains.kotlin.backend.jvm.lower.indy.LambdaMetafactoryArgumentsBuilder;
import org.jetbrains.kotlin.backend.jvm.lower.indy.MetafactoryArgumentsResult;
import org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBlock;
import org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBuilder;
import org.jetbrains.kotlin.config.JvmClosureGenerationScheme;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrTypeParameterBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeParameterRemapper;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;

/* JADX INFO: compiled from: FunctionReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u0000\u0018\u0000 B2\u00020\u00012\u00020\u0002:\u0002BCB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0016H\u0002J\b\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u001eH\u0016J\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u0016H\u0016J\u0010\u0010(\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020)H\u0016J,\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u001e2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001c0.H\u0002J \u0010/\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u00100\u001a\u000201H\u0002J$\u00102\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020)2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001c0.H\u0002J \u00103\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u00104\u001a\u0002052\u0006\u00100\u001a\u000201H\u0002J\u0018\u00106\u001a\u0002072\u0006\u0010+\u001a\u00020\u001a2\u0006\u00100\u001a\u000201H\u0002J\u0014\u00108\u001a\u000209*\u00020:2\u0006\u0010;\u001a\u00020<H\u0002J\u001a\u0010=\u001a\u00020>*\u00020:2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020<0@H\u0002J\f\u0010A\u001a\u00020\u000b*\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00130\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0015\u001a\u00020\u000b*\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FunctionReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "crossinlineLambdas", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lkotlin/collections/HashSet;", "isJavaSamConversionWithEqualsHashCode", "", "jvmIndyLambdaMetafactoryIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "shouldGenerateIndyLambdas", "shouldGenerateIndySamConversions", "shouldGenerateLightweightLambdas", "specialNullabilityAnnotationsFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isIgnored", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)Z", "canGenerateIndySamConversionOnFunctionalExpression", "samSuperType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "createProxyLocalFunctionForIndySamConversion", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "reference", "getDeclarationParentForDelegatingLambda", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitBlock", "visitFunctionReference", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "wrapFunctionReferenceInsideBlockWithIndySamConversion", "samType", "block", "produceSamConversion", "Lkotlin/Function1;", "wrapLambdaReferenceWithIndySamConversion", "lambdaMetafactoryArguments", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArguments;", "wrapSamConversionArgumentWithIndySamConversion", "wrapSamDelegatingLambdaWithIndySamConversion", "lambdaBlock", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBlock;", "wrapWithIndySamConversion", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "irRawFunctionRef", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrRawFunctionReferenceImpl;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "irFun", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irVarargOfRawFunctionRefs", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrVarargImpl;", "irFuns", "", "isSuspendFunctionReference", "Companion", "FunctionReferenceBuilder", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionReferenceLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JvmBackendContext context;
    private final HashSet<IrSimpleFunction> crossinlineLambdas;
    private final boolean isJavaSamConversionWithEqualsHashCode;
    private final IrSimpleFunctionSymbol jvmIndyLambdaMetafactoryIntrinsic;
    private final boolean shouldGenerateIndyLambdas;
    private final boolean shouldGenerateIndySamConversions;
    private final boolean shouldGenerateLightweightLambdas;
    private final Set<FqName> specialNullabilityAnnotationsFqNames;

    public FunctionReferenceLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.crossinlineLambdas = new HashSet<>();
        this.shouldGenerateIndySamConversions = context.getConfig().getSamConversionsScheme() == JvmClosureGenerationScheme.INDY;
        boolean z = context.getConfig().getLambdasScheme() == JvmClosureGenerationScheme.INDY;
        this.shouldGenerateIndyLambdas = z;
        this.shouldGenerateLightweightLambdas = z && context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.LightweightLambdas);
        this.isJavaSamConversionWithEqualsHashCode = context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.JavaSamConversionEqualsHashCode);
        this.jvmIndyLambdaMetafactoryIntrinsic = context.getIr().getSymbols().getIndyLambdaMetafactoryIntrinsic();
        this.specialNullabilityAnnotationsFqNames = SetsKt.setOf((Object[]) new FqName[]{JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME(), JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION});
    }

    private final boolean isIgnored(IrFunctionReference irFunctionReference) {
        return !(IrTypeUtilsKt.isFunctionOrKFunction(irFunctionReference.getType()) || isSuspendFunctionReference(irFunctionReference)) || Intrinsics.areEqual(irFunctionReference.getOrigin(), JvmLoweredStatementOrigin.INLINE_LAMBDA.INSTANCE);
    }

    private final boolean isSuspendFunctionReference(IrFunctionReference irFunctionReference) {
        return IrUtilsKt.isSuspend(irFunctionReference) && (irFunctionReference.getOrigin() == null || Intrinsics.areEqual(irFunctionReference.getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE) || Intrinsics.areEqual(irFunctionReference.getOrigin(), IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final JvmBackendContext jvmBackendContext = this.context;
        irFile.accept(new IrInlineReferenceLocator(jvmBackendContext) { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$lower$$inlined$findInlineLambdas$1
            @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
            public void visitInlineLambda(IrFunctionReference argument, IrFunction callee, IrValueParameter parameter, IrDeclaration scope) {
                Intrinsics.checkNotNullParameter(argument, "argument");
                Intrinsics.checkNotNullParameter(callee, "callee");
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(scope, "scope");
                if (parameter.getIsCrossinline()) {
                    HashSet hashSet = this.crossinlineLambdas;
                    IrFunction owner = argument.getSymbol().getOwner();
                    Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                    hashSet.add((IrSimpleFunction) owner);
                }
            }
        }, null);
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
        this.crossinlineLambdas.clear();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!IrUtilsKt.isLambda(expression.getOrigin())) {
            return super.visitBlock(expression);
        }
        Object objLast = CollectionsKt.last((List<? extends Object>) expression.getStatements());
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
        IrFunctionReference irFunctionReference = (IrFunctionReference) objLast;
        if (isIgnored(irFunctionReference)) {
            return super.visitBlock(expression);
        }
        Iterator it = CollectionsKt.dropLast(expression.getStatements(), 1).iterator();
        while (it.hasNext()) {
            ((IrStatement) it.next()).transform(this, null);
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irFunctionReference, this);
        if (this.shouldGenerateIndyLambdas) {
            MetafactoryArgumentsResult lambdaMetafactoryArguments = new LambdaMetafactoryArgumentsBuilder(this.context, this.crossinlineLambdas).getLambdaMetafactoryArguments(irFunctionReference, irFunctionReference.getType(), true);
            if (lambdaMetafactoryArguments instanceof LambdaMetafactoryArguments) {
                return wrapLambdaReferenceWithIndySamConversion(expression, irFunctionReference, (LambdaMetafactoryArguments) lambdaMetafactoryArguments);
            }
        }
        return new FunctionReferenceBuilder(this, irFunctionReference, null, 2, null).build();
    }

    private final IrBlock wrapLambdaReferenceWithIndySamConversion(IrBlock expression, IrFunctionReference reference, LambdaMetafactoryArguments lambdaMetafactoryArguments) {
        IrCall irCallWrapWithIndySamConversion = wrapWithIndySamConversion(reference.getType(), lambdaMetafactoryArguments);
        expression.getStatements().set(expression.getStatements().size() - 1, irCallWrapWithIndySamConversion);
        expression.setType(irCallWrapWithIndySamConversion.getType());
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (isIgnored(expression)) {
            return expression;
        }
        return new FunctionReferenceBuilder(this, expression, null, 2, null).build();
    }

    private final IrDeclarationParent getDeclarationParentForDelegatingLambda() {
        Iterator it = CollectionsKt.asReversedMutable(getAllScopes()).iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((ScopeWithIr) it.next()).getScope().getScopeOwnerSymbol().getOwner();
            if (owner instanceof IrDeclarationParent) {
                return (IrDeclarationParent) owner;
            }
        }
        throw new AssertionError("No IrDeclarationParent found in scopes:\n" + CollectionsKt.joinToString$default(getAllScopes(), "\n", null, null, 0, null, new Function1<ScopeWithIr, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering.getDeclarationParentForDelegatingLambda.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ScopeWithIr it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return "  " + RenderIrElementKt.render$default(it2.getScope().getScopeOwnerSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null);
            }
        }, 30, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        IrFunctionReference irFunctionReference;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression.getOperator() != IrTypeOperator.SAM_CONVERSION) {
            return super.visitTypeOperator(expression);
        }
        IrType typeOperand = expression.getTypeOperand();
        IrExpression argument = expression.getArgument();
        if (argument instanceof IrFunctionReference) {
            irFunctionReference = (IrFunctionReference) argument;
        } else {
            if (argument instanceof IrBlock) {
                IrBlock irBlock = (IrBlock) argument;
                if (IrUtilsKt.isLambda(irBlock.getOrigin()) && (CollectionsKt.last((List) irBlock.getStatements()) instanceof IrFunctionReference)) {
                    Iterator it = CollectionsKt.dropLast(irBlock.getStatements(), 1).iterator();
                    while (it.hasNext()) {
                        ((IrStatement) it.next()).transform(this, null);
                    }
                    Object objLast = CollectionsKt.last((List<? extends Object>) irBlock.getStatements());
                    Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                    irFunctionReference = (IrFunctionReference) objLast;
                }
            }
            if (this.shouldGenerateIndySamConversions && canGenerateIndySamConversionOnFunctionalExpression(typeOperand, argument)) {
                SamDelegatingLambdaBuilder samDelegatingLambdaBuilder = new SamDelegatingLambdaBuilder(this.context);
                ScopeWithIr currentScope = getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                SamDelegatingLambdaBlock samDelegatingLambdaBlockBuild = samDelegatingLambdaBuilder.build(argument, typeOperand, currentScope.getScope().getScopeOwnerSymbol(), getDeclarationParentForDelegatingLambda());
                MetafactoryArgumentsResult lambdaMetafactoryArguments = new LambdaMetafactoryArgumentsBuilder(this.context, this.crossinlineLambdas).getLambdaMetafactoryArguments(samDelegatingLambdaBlockBuild.getRef(), typeOperand, false);
                if (!(lambdaMetafactoryArguments instanceof LambdaMetafactoryArguments)) {
                    return super.visitTypeOperator(expression);
                }
                transformChildrenVoid(argument);
                return wrapSamDelegatingLambdaWithIndySamConversion(typeOperand, samDelegatingLambdaBlockBuild, (LambdaMetafactoryArguments) lambdaMetafactoryArguments);
            }
            return super.visitTypeOperator(expression);
        }
        transformChildrenVoid(irFunctionReference);
        if (this.shouldGenerateIndySamConversions) {
            final MetafactoryArgumentsResult lambdaMetafactoryArguments2 = new LambdaMetafactoryArgumentsBuilder(this.context, this.crossinlineLambdas).getLambdaMetafactoryArguments(irFunctionReference, typeOperand, false);
            if (lambdaMetafactoryArguments2 instanceof LambdaMetafactoryArguments) {
                return wrapSamConversionArgumentWithIndySamConversion(expression, new Function1<IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering.visitTypeOperator.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrExpression invoke(IrType samType) {
                        Intrinsics.checkNotNullParameter(samType, "samType");
                        return FunctionReferenceLowering.this.wrapWithIndySamConversion(samType, (LambdaMetafactoryArguments) lambdaMetafactoryArguments2);
                    }
                });
            }
            if (lambdaMetafactoryArguments2 instanceof MetafactoryArgumentsResult.Failure.FunctionHazard) {
                final IrBlock irBlockCreateProxyLocalFunctionForIndySamConversion = createProxyLocalFunctionForIndySamConversion(irFunctionReference);
                IrStatement irStatement = irBlockCreateProxyLocalFunctionForIndySamConversion.getStatements().get(irBlockCreateProxyLocalFunctionForIndySamConversion.getStatements().size() - 1);
                Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                final MetafactoryArgumentsResult lambdaMetafactoryArguments3 = new LambdaMetafactoryArgumentsBuilder(this.context, this.crossinlineLambdas).getLambdaMetafactoryArguments((IrFunctionReference) irStatement, typeOperand, false);
                if (lambdaMetafactoryArguments3 instanceof LambdaMetafactoryArguments) {
                    return wrapSamConversionArgumentWithIndySamConversion(expression, new Function1<IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering.visitTypeOperator.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrExpression invoke(IrType samType) {
                            Intrinsics.checkNotNullParameter(samType, "samType");
                            irBlockCreateProxyLocalFunctionForIndySamConversion.getStatements().set(irBlockCreateProxyLocalFunctionForIndySamConversion.getStatements().size() - 1, this.wrapWithIndySamConversion(samType, (LambdaMetafactoryArguments) lambdaMetafactoryArguments3));
                            irBlockCreateProxyLocalFunctionForIndySamConversion.setType(samType);
                            return irBlockCreateProxyLocalFunctionForIndySamConversion;
                        }
                    });
                }
            }
        }
        return new FunctionReferenceBuilder(this, irFunctionReference, JvmIrUtilsKt.rawType(JvmIrTypeUtilsKt.getErasedUpperBound(typeOperand), this.context)).build();
    }

    private final IrBlock createProxyLocalFunctionForIndySamConversion(IrFunctionReference reference) {
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default;
        JvmLoweredDeclarationOrigin.PROXY_FUN_FOR_METAFACTORY proxy_fun_for_metafactory;
        int startOffset = reference.getStartOffset();
        int endOffset = reference.getEndOffset();
        IrFunction owner = reference.getSymbol().getOwner();
        ArrayList arrayList = new ArrayList();
        if (owner instanceof IrSimpleFunction) {
            irConstructorCallImplFromSymbolOwner$default = IrCallImpl.INSTANCE.fromSymbolOwner(startOffset, endOffset, ((IrSimpleFunction) owner).getSymbol());
        } else if (owner instanceof IrConstructor) {
            irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, startOffset, endOffset, owner.getReturnType(), ((IrConstructor) owner).getSymbol(), null, 16, null);
        } else {
            throw new AssertionError("Unexpected callable reference target: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        IrMemberAccessExpression irMemberAccessExpression = irConstructorCallImplFromSymbolOwner$default;
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(owner.getName().asString() + "__proxy");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(JvmIrTypeUtilsKt.eraseTypeParameters(owner.getReturnType()));
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setSuspend(false);
        irFunctionBuilder.setInline(false);
        if (owner.getIsInline() || VarargLoweringKt.isArrayOf(owner)) {
            proxy_fun_for_metafactory = JvmLoweredDeclarationOrigin.PROXY_FUN_FOR_METAFACTORY.INSTANCE;
        } else {
            proxy_fun_for_metafactory = JvmLoweredDeclarationOrigin.SYNTHETIC_PROXY_FUN_FOR_METAFACTORY.INSTANCE;
        }
        irFunctionBuilder.setOrigin(proxy_fun_for_metafactory);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        IrDeclarationParent currentDeclarationParent = getCurrentDeclarationParent();
        if (currentDeclarationParent == null) {
            throw new AssertionError("No declaration parent when processing " + reference);
        }
        irSimpleFunctionBuildFunction.setParent(currentDeclarationParent);
        Ref.IntRef intRef = new Ref.IntRef();
        Ref.IntRef intRef2 = new Ref.IntRef();
        irMemberAccessExpression.setDispatchReceiver(createProxyLocalFunctionForIndySamConversion$lambda$7$getTargetCallArgument(startOffset, endOffset, intRef, irSimpleFunctionBuildFunction, arrayList, intRef2, reference.getDispatchReceiver(), owner.getDispatchReceiverParameter()));
        irMemberAccessExpression.setExtensionReceiver(createProxyLocalFunctionForIndySamConversion$lambda$7$getTargetCallArgument(startOffset, endOffset, intRef, irSimpleFunctionBuildFunction, arrayList, intRef2, reference.getExtensionReceiver(), owner.getExtensionReceiverParameter()));
        Iterator<IrValueParameter> it = owner.getValueParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            irMemberAccessExpression.putValueArgument(i, createProxyLocalFunctionForIndySamConversion$lambda$7$getTargetCallArgument(startOffset, endOffset, intRef, irSimpleFunctionBuildFunction, arrayList, intRef2, reference.getValueArgument(i), it.next()));
            i++;
        }
        int size = owner.getTypeParameters().size();
        for (int i2 = 0; i2 < size; i2++) {
            irMemberAccessExpression.putTypeArgument(i2, reference.getTypeArgument(i2));
        }
        IrBlockBodyImpl irBlockBodyImpl = new IrBlockBodyImpl(startOffset, endOffset);
        irSimpleFunctionBuildFunction.setBody(irBlockBodyImpl);
        if (IrTypePredicatesKt.isUnit(owner.getReturnType())) {
            irBlockBodyImpl.getStatements().add(irMemberAccessExpression);
        } else {
            irBlockBodyImpl.getStatements().add(new IrReturnImpl(startOffset, endOffset, this.context.getIrBuiltIns().getNothingType(), irSimpleFunctionBuildFunction.getSymbol(), irMemberAccessExpression));
        }
        return new IrBlockImpl(startOffset, endOffset, reference.getType(), null, CollectionsKt.plus((Collection<? extends IrFunctionReferenceImpl>) CollectionsKt.plus((Collection<? extends IrSimpleFunction>) arrayList, irSimpleFunctionBuildFunction), new IrFunctionReferenceImpl(startOffset, endOffset, reference.getType(), irSimpleFunctionBuildFunction.getSymbol(), 0, irSimpleFunctionBuildFunction.getValueParameters().size(), null, null, 192, null)));
    }

    private static final IrGetValue createProxyLocalFunctionForIndySamConversion$lambda$7$addAndGetTemporaryVal(int i, int i2, Ref.IntRef intRef, IrSimpleFunction irSimpleFunction, ArrayList<IrVariable> arrayList, IrExpression irExpression) {
        IrDeclarationOrigin.IR_TEMPORARY_VARIABLE ir_temporary_variable = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        IrVariableSymbolImpl irVariableSymbolImpl = new IrVariableSymbolImpl(null, 1, null);
        StringBuilder sb = new StringBuilder("tmp_proxy_");
        int i3 = intRef.element;
        intRef.element = i3 + 1;
        Name nameIdentifier = Name.identifier(sb.append(i3).toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariableImpl irVariableImpl = new IrVariableImpl(i, i2, ir_temporary_variable, irVariableSymbolImpl, nameIdentifier, irExpression.getType(), false, false, false);
        irVariableImpl.setInitializer(irExpression);
        irVariableImpl.setParent(irSimpleFunction.getParent());
        arrayList.add(irVariableImpl);
        return new IrGetValueImpl(i, i2, irVariableImpl.getSymbol(), null, 8, null);
    }

    private static final IrExpression createProxyLocalFunctionForIndySamConversion$lambda$7$getTargetCallArgument(int i, int i2, Ref.IntRef intRef, IrSimpleFunction irSimpleFunction, ArrayList<IrVariable> arrayList, Ref.IntRef intRef2, IrExpression irExpression, IrValueParameter irValueParameter) {
        if (irExpression != null) {
            return createProxyLocalFunctionForIndySamConversion$lambda$7$addAndGetTemporaryVal(i, i2, intRef, irSimpleFunction, arrayList, irExpression);
        }
        if (irValueParameter != null) {
            return createProxyLocalFunctionForIndySamConversion$lambda$7$addAndGetProxyValueParameter(irSimpleFunction, i, i2, intRef2, irValueParameter);
        }
        return null;
    }

    private final boolean canGenerateIndySamConversionOnFunctionalExpression(IrType samSuperType, IrExpression expression) {
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(samSuperType);
        if (classOrNull == null) {
            throw new AssertionError("Class type expected: " + RenderIrElementKt.render$default(samSuperType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (!IrUtilsKt.isFromJava(classOrNull.getOwner()) || this.isJavaSamConversionWithEqualsHashCode) {
            return false;
        }
        return ((expression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) expression).getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE)) ? false : true;
    }

    private final IrExpression wrapSamDelegatingLambdaWithIndySamConversion(IrType samSuperType, SamDelegatingLambdaBlock lambdaBlock, LambdaMetafactoryArguments lambdaMetafactoryArguments) {
        lambdaBlock.replaceRefWith(wrapWithIndySamConversion(samSuperType, lambdaMetafactoryArguments));
        return lambdaBlock.getBlock();
    }

    private final IrExpression wrapSamConversionArgumentWithIndySamConversion(IrTypeOperatorCall expression, Function1<? super IrType, ? extends IrExpression> produceSamConversion) {
        IrType typeOperand = expression.getTypeOperand();
        IrExpression argument = expression.getArgument();
        if (argument instanceof IrFunctionReference) {
            return produceSamConversion.invoke(typeOperand);
        }
        if (argument instanceof IrBlock) {
            return wrapFunctionReferenceInsideBlockWithIndySamConversion(typeOperand, (IrBlock) argument, produceSamConversion);
        }
        throw new AssertionError("Block or function reference expected: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    private final IrExpression wrapFunctionReferenceInsideBlockWithIndySamConversion(IrType samType, IrBlock block, Function1<? super IrType, ? extends IrExpression> produceSamConversion) {
        IrExpression irExpressionInvoke = produceSamConversion.invoke(samType);
        block.getStatements().set(block.getStatements().size() - 1, irExpressionInvoke);
        block.setType(irExpressionInvoke.getType());
        return block;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall wrapWithIndySamConversion(IrType samType, LambdaMetafactoryArguments lambdaMetafactoryArguments) {
        IrType irTypeRemoveAnnotations = IrTypesKt.removeAnnotations(IrTypesKt.makeNotNull(samType), new Function1<IrConstructorCall, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$wrapWithIndySamConversion$notNullSamType$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrConstructorCall it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(this.this$0.specialNullabilityAnnotationsFqNames.contains(IrTypesKt.getClassFqName(it.getType())));
            }
        });
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope);
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(jvmIrBuilderCreateJvmIrBuilder, this.jvmIndyLambdaMetafactoryIntrinsic, irTypeRemoveAnnotations, 0, 0, null, 28, null);
        irCallIrCall$default.putTypeArgument(0, irTypeRemoveAnnotations);
        irCallIrCall$default.putValueArgument(0, irRawFunctionRef(jvmIrBuilderCreateJvmIrBuilder, lambdaMetafactoryArguments.getSamMethod()));
        irCallIrCall$default.putValueArgument(1, lambdaMetafactoryArguments.getImplMethodReference());
        irCallIrCall$default.putValueArgument(2, irRawFunctionRef(jvmIrBuilderCreateJvmIrBuilder, lambdaMetafactoryArguments.getFakeInstanceMethod()));
        irCallIrCall$default.putValueArgument(3, irVarargOfRawFunctionRefs(jvmIrBuilderCreateJvmIrBuilder, lambdaMetafactoryArguments.getExtraOverriddenMethods()));
        irCallIrCall$default.putValueArgument(4, ExpressionHelpersKt.irBoolean(jvmIrBuilderCreateJvmIrBuilder, lambdaMetafactoryArguments.getShouldBeSerializable()));
        return irCallIrCall$default;
    }

    private final IrRawFunctionReferenceImpl irRawFunctionRef(IrBuilderWithScope irBuilderWithScope, IrFunction irFunction) {
        return ExpressionHelpersKt.irRawFunctionReference(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getAnyType(), irFunction.getSymbol());
    }

    private final IrVarargImpl irVarargOfRawFunctionRefs(IrBuilderWithScope irBuilderWithScope, List<? extends IrFunction> list) {
        IrType anyType = irBuilderWithScope.getContext().getIrBuiltIns().getAnyType();
        List<? extends IrFunction> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(irRawFunctionRef(irBuilderWithScope, (IrFunction) it.next()));
        }
        return ExpressionHelpersKt.irVararg(irBuilderWithScope, anyType, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FunctionReferenceLowering.kt */
    @Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0006\u00101\u001a\u00020\u0010J\u001a\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020'2\b\b\u0002\u00104\u001a\u00020\u0005H\u0002J\b\u00105\u001a\u000206H\u0002J\u0014\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002080\u000eH\u0002J$\u00109\u001a\b\u0012\u0004\u0012\u00020:0\f2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020:0\f2\u0006\u0010<\u001a\u00020\u0015H\u0002J\u0012\u0010=\u001a\u00020'2\b\u0010>\u001a\u0004\u0018\u00010?H\u0002J)\u0010@\u001a\u00020'2\u0006\u00103\u001a\u00020'2\u0017\u0010A\u001a\u0013\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020\u00100B¢\u0006\u0002\bDH\u0002J\b\u0010E\u001a\u00020FH\u0002J\u0010\u0010G\u001a\u00020F2\u0006\u0010H\u001a\u00020\bH\u0002J\b\u0010I\u001a\u00020\u001aH\u0002J\u0018\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\n2\u0006\u0010L\u001a\u00020'H\u0002J\u0010\u0010M\u001a\u00020\u001a2\u0006\u0010N\u001a\u00020'H\u0002J\u0010\u0010O\u001a\u00020\u001a2\u0006\u0010N\u001a\u00020'H\u0002J\f\u0010P\u001a\u00020Q*\u00020'H\u0002J\u0016\u0010R\u001a\u00020Q*\u00020'2\b\u0010S\u001a\u0004\u0018\u00010?H\u0002J\f\u0010T\u001a\u00020Q*\u00020'H\u0002J-\u0010U\u001a\u00020Q*\u00020C2\u0006\u0010V\u001a\u00020\n2\u0017\u0010W\u001a\u0013\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020\u00100B¢\u0006\u0002\bDH\u0002J\u0016\u0010Y\u001a\u00020Q*\u00020C2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0002J\u0014\u0010\\\u001a\u00020\u0010*\u00020C2\u0006\u0010]\u001a\u00020^H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u00050*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010-\u001a\u00020.*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b/\u00100¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FunctionReferenceLowering$FunctionReferenceBuilder;", "", "irFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "samSuperType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/jvm/lower/FunctionReferenceLowering;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "adaptedReferenceOriginalTarget", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "adapteeCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "argumentTypes", "", "boundReceiver", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "callee", "constructedFunInterfaceSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "functionReferenceClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "functionSuperClass", "getIrFunctionReference", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "isAdaptedReference", "", "isFunInterfaceConstructorReference", "isHeavyweightLambda", "isKotlinFunInterface", "isLambda", "isLightweightLambda", "needToGenerateSamEqualsHashCodeMethods", "parameterTypes", "referenceReturnType", "samInterface", "getSamSuperType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "superMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "superType", "typeArgumentsMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "useOptimizedSuperClass", "originalName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginalName", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/name/Name;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "buildOverride", "superFunction", "newReturnType", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "createFakeBoundReceiverForJvmStaticInObject", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "createFakeFormalTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "sourceTypeParameters", "irClass", "createInvokeMethod", "receiverVar", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "createLegacyMethodOverride", "generator", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "Lkotlin/ExtensionFunctionType;", "getAdaptedCallableReferenceFlags", "", "getFunctionReferenceFlags", "callableReferenceTarget", "hasVarargMappedToElement", "inlineAdapterCallIfPossible", "expression", "invokeMethod", "isEqualsFromAny", "f", "isHashCodeFromAny", "createFunInterfaceConstructorInvokeMethod", "", "createFunctionReferenceInvokeMethod", "receiver", "createLambdaInvokeMethod", "generateConstructorCallArguments", "call", "generateBoundReceiver", "Lorg/jetbrains/kotlin/ir/builders/IrBuilder;", "generateSamEqualsHashCodeMethods", "boundReceiverVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "generateSignature", "target", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class FunctionReferenceBuilder {
        private final IrFunction adaptedReferenceOriginalTarget;
        private final IrFunctionAccessExpression adapteeCall;
        private final List<IrType> argumentTypes;
        private final Pair<IrValueParameter, IrExpression> boundReceiver;
        private final IrFunction callee;
        private final IrClassSymbol constructedFunInterfaceSymbol;
        private final IrClass functionReferenceClass;
        private final IrClassSymbol functionSuperClass;
        private final IrFunctionReference irFunctionReference;
        private final boolean isAdaptedReference;
        private final boolean isFunInterfaceConstructorReference;
        private final boolean isHeavyweightLambda;
        private final boolean isKotlinFunInterface;
        private final boolean isLambda;
        private final boolean isLightweightLambda;
        private final boolean needToGenerateSamEqualsHashCodeMethods;
        private final List<IrType> parameterTypes;
        private final IrType referenceReturnType;
        private final IrClass samInterface;
        private final IrType samSuperType;
        private final IrSimpleFunction superMethod;
        private final IrType superType;
        final /* synthetic */ FunctionReferenceLowering this$0;
        private final Map<IrTypeParameterSymbol, IrType> typeArgumentsMap;
        private final boolean useOptimizedSuperClass;

        public FunctionReferenceBuilder(FunctionReferenceLowering functionReferenceLowering, IrFunctionReference irFunctionReference, IrType irType) {
            IrFunctionAccessExpression irFunctionAccessExpression;
            IrClassSymbol classOrNull;
            IrClassSymbol functionReference;
            IrFunctionSymbol symbol;
            IrType anyNType;
            Intrinsics.checkNotNullParameter(irFunctionReference, "irFunctionReference");
            this.this$0 = functionReferenceLowering;
            this.irFunctionReference = irFunctionReference;
            this.samSuperType = irType;
            boolean zIsLambda = IrUtilsKt.isLambda(irFunctionReference.getOrigin());
            this.isLambda = zIsLambda;
            boolean z = zIsLambda && functionReferenceLowering.shouldGenerateLightweightLambdas && !IrUtilsKt.hasAnnotation(irFunctionReference.getSymbol().getOwner(), JvmAnnotationUtilKt.JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME);
            this.isLightweightLambda = z;
            this.isHeavyweightLambda = zIsLambda && !z;
            IrFunction owner = irFunctionReference.getSymbol().getOwner();
            this.callee = owner;
            this.boundReceiver = JvmStaticAnnotationLoweringKt.isJvmStaticInObject(owner) ? createFakeBoundReceiverForJvmStaticInObject() : (Pair) CollectionsKt.singleOrNull((List) IrUtilsKt.getArgumentsWithIr(irFunctionReference));
            IrType type = irFunctionReference.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
            for (IrTypeArgument irTypeArgument : arguments) {
                if (irTypeArgument instanceof IrTypeProjection) {
                    anyNType = ((IrTypeProjection) irTypeArgument).getType();
                } else if (irTypeArgument instanceof IrStarProjection) {
                    anyNType = functionReferenceLowering.context.getIrBuiltIns().getAnyNType();
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                arrayList.add(anyNType);
            }
            ArrayList arrayList2 = arrayList;
            this.parameterTypes = arrayList2;
            List<IrType> listDropLast = CollectionsKt.dropLast(arrayList2, 1);
            this.argumentTypes = listDropLast;
            this.referenceReturnType = (IrType) CollectionsKt.last((List) arrayList2);
            this.typeArgumentsMap = IrUtilsKt.getTypeSubstitutionMap(this.irFunctionReference);
            IrType irType2 = this.samSuperType;
            IrClassSymbol symbol2 = (irType2 == null || (symbol2 = IrTypesKt.getClassOrNull(irType2)) == null) ? IrUtilsKt.isSuspend(this.irFunctionReference) ? this.this$0.context.getIrBuiltIns().suspendFunctionN(listDropLast.size()).getSymbol() : this.this$0.context.getIrBuiltIns().functionN(listDropLast.size()).getSymbol() : symbol2;
            this.functionSuperClass = symbol2;
            IrSimpleFunction singleAbstractMethod = JvmIrUtilsKt.getSingleAbstractMethod(symbol2.getOwner());
            if (singleAbstractMethod != null) {
                this.superMethod = singleAbstractMethod;
                boolean generateOptimizedCallableReferenceSuperClasses = this.this$0.context.getConfig().getGenerateOptimizedCallableReferenceSuperClasses();
                this.useOptimizedSuperClass = generateOptimizedCallableReferenceSuperClasses;
                if (Intrinsics.areEqual(this.callee.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE)) {
                    IrBody body = this.callee.getBody();
                    Intrinsics.checkNotNull(body);
                    IrExpression value = (IrStatement) CollectionsKt.single((List) IrUtilsKt.getStatements(body));
                    if (value instanceof IrTypeOperatorCall) {
                        IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) value;
                        irTypeOperatorCall.getOperator();
                        IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_COERCION_TO_UNIT;
                        value = irTypeOperatorCall.getArgument();
                    } else if (value instanceof IrReturn) {
                        value = ((IrReturn) value).getValue();
                    }
                    if (!(value instanceof IrFunctionAccessExpression)) {
                        throw new UnsupportedOperationException("Unknown structure of ADAPTER_FOR_CALLABLE_REFERENCE: " + RenderIrElementKt.render$default(this.callee, (DumpIrTreeOptions) null, 1, (Object) null));
                    }
                    irFunctionAccessExpression = (IrFunctionAccessExpression) value;
                } else {
                    irFunctionAccessExpression = null;
                }
                this.adapteeCall = irFunctionAccessExpression;
                IrFunction owner2 = (irFunctionAccessExpression == null || (symbol = irFunctionAccessExpression.getSymbol()) == null) ? null : symbol.getOwner();
                this.adaptedReferenceOriginalTarget = owner2;
                boolean zAreEqual = Intrinsics.areEqual(this.callee.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR.INSTANCE);
                this.isFunInterfaceConstructorReference = zAreEqual;
                if (zAreEqual) {
                    classOrNull = IrTypesKt.getClassOrNull(this.callee.getReturnType());
                    if (classOrNull == null) {
                        throw new AssertionError("Fun interface type expected: " + RenderIrElementKt.render$default(this.callee.getReturnType(), (DumpIrTreeOptions) null, 1, (Object) null));
                    }
                } else {
                    classOrNull = null;
                }
                this.constructedFunInterfaceSymbol = classOrNull;
                boolean z2 = zAreEqual || owner2 != null;
                this.isAdaptedReference = z2;
                IrType irType3 = this.samSuperType;
                IrClass irClass = irType3 != null ? IrTypesKt.getClass(irType3) : null;
                this.samInterface = irClass;
                boolean z3 = (irClass == null || IrUtilsKt.isFromJava(irClass)) ? false : true;
                this.isKotlinFunInterface = z3;
                boolean z4 = (z3 || this.this$0.isJavaSamConversionWithEqualsHashCode) && (z2 || !this.isLambda);
                this.needToGenerateSamEqualsHashCodeMethods = z4;
                IrType defaultType = this.samSuperType;
                if (defaultType == null) {
                    if (this.isLightweightLambda) {
                        functionReference = this.this$0.context.getIr().getSymbols().getAny();
                    } else if (this.isHeavyweightLambda) {
                        functionReference = this.this$0.context.getIr().getSymbols().getLambdaClass();
                    } else if (zAreEqual) {
                        functionReference = this.this$0.context.getIr().getSymbols().getFunInterfaceConstructorReferenceClass();
                    } else if (generateOptimizedCallableReferenceSuperClasses) {
                        functionReference = z2 ? this.this$0.context.getIr().getSymbols().getAdaptedFunctionReference() : this.this$0.context.getIr().getSymbols().getFunctionReferenceImpl();
                    } else {
                        functionReference = this.this$0.context.getIr().getSymbols().getFunctionReference();
                    }
                    defaultType = IrTypesKt.getDefaultType(functionReference);
                }
                this.superType = defaultType;
                IrFactory irFactory = this.this$0.context.getIrFactory();
                IrClassBuilder irClassBuilder = new IrClassBuilder();
                IrElementBuilderKt.setSourceRange(irClassBuilder, this.irFunctionReference);
                DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                irClassBuilder.setVisibility(LOCAL);
                irClassBuilder.setOrigin(this.isLambda ? JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE : JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE);
                irClassBuilder.setName(SpecialNames.NO_NAME_PROVIDED);
                IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
                FunctionReferenceLowering functionReferenceLowering2 = this.this$0;
                IrDeclarationParent currentDeclarationParent = functionReferenceLowering2.getCurrentDeclarationParent();
                if (currentDeclarationParent == null) {
                    throw new IllegalStateException(("No current declaration parent at " + DumpIrTreeKt.dump$default(this.irFunctionReference, null, 1, null)).toString());
                }
                irClassBuildClass.setParent(currentDeclarationParent);
                IrType[] irTypeArr = new IrType[3];
                irTypeArr[0] = defaultType;
                irTypeArr[1] = this.samSuperType == null ? IrTypesKt.typeWith(symbol2, arrayList2) : null;
                irTypeArr[2] = z4 ? IrTypesKt.getDefaultType(functionReferenceLowering2.context.getIr().getSymbols().getFunctionAdapter()) : null;
                irClassBuildClass.setSuperTypes(CollectionsKt.listOfNotNull((Object[]) irTypeArr));
                if (irClass != null && Intrinsics.areEqual(irClassBuildClass.getOrigin(), JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE)) {
                    irClassBuildClass.setTypeParameters(createFakeFormalTypeParameters(irClass.getTypeParameters(), irClassBuildClass));
                }
                IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                IrDeclarationsKt.copyAttributes(irClassBuildClass, this.irFunctionReference);
                if (this.isHeavyweightLambda) {
                    irClassBuildClass.setMetadata(this.irFunctionReference.getSymbol().getOwner().getMetadata());
                }
                this.functionReferenceClass = irClassBuildClass;
                return;
            }
            throw new AssertionError("Not a SAM class: " + RenderIrElementKt.render$default(symbol2.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
        }

        public /* synthetic */ FunctionReferenceBuilder(FunctionReferenceLowering functionReferenceLowering, IrFunctionReference irFunctionReference, IrType irType, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(functionReferenceLowering, irFunctionReference, (i & 2) != 0 ? null : irType);
        }

        public final IrFunctionReference getIrFunctionReference() {
            return this.irFunctionReference;
        }

        public final IrType getSamSuperType() {
            return this.samSuperType;
        }

        private final List<IrTypeParameter> createFakeFormalTypeParameters(List<? extends IrTypeParameter> sourceTypeParameters, IrClass irClass) {
            if (sourceTypeParameters.isEmpty()) {
                return CollectionsKt.emptyList();
            }
            List<? extends IrTypeParameter> list = sourceTypeParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (IrTypeParameter irTypeParameter : list) {
                IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
                irTypeParameterBuilder.updateFrom(irTypeParameter);
                irTypeParameterBuilder.setName(irTypeParameter.getName());
                IrClass irClass2 = irClass;
                arrayList.add(DeclarationBuildersKt.buildTypeParameter(irClass2.getFactory(), irTypeParameterBuilder, irClass2));
            }
            ArrayList<IrTypeParameter> arrayList2 = arrayList;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj : list) {
                linkedHashMap.put(obj, (IrTypeParameter) arrayList2.get(((IrTypeParameter) obj).getIndex()));
            }
            IrTypeParameterRemapper irTypeParameterRemapper = new IrTypeParameterRemapper(linkedHashMap);
            for (IrTypeParameter irTypeParameter2 : arrayList2) {
                List<IrType> superTypes = sourceTypeParameters.get(irTypeParameter2.getIndex()).getSuperTypes();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
                Iterator<T> it = superTypes.iterator();
                while (it.hasNext()) {
                    arrayList3.add(irTypeParameterRemapper.remapType((IrType) it.next()));
                }
                irTypeParameter2.setSuperTypes(arrayList3);
            }
            return arrayList2;
        }

        public final IrExpression build() {
            Pair<IrValueParameter, IrExpression> pair;
            JvmBackendContext jvmBackendContext = this.this$0.context;
            ScopeWithIr currentScope = this.this$0.getCurrentScope();
            Intrinsics.checkNotNull(currentScope);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope);
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), this.irFunctionReference.getStartOffset(), this.irFunctionReference.getEndOffset(), null, null, false, 64, null);
            IrConstructor irConstructorCreateConstructor = createConstructor();
            IrVariable irVariableIrTemporary$default = (this.samSuperType == null || (pair = this.boundReceiver) == null) ? null : ExpressionHelpersKt.irTemporary$default(irBlockBuilder, pair.getSecond(), null, null, false, null, 30, null);
            createInvokeMethod(irVariableIrTemporary$default);
            if (!this.isLambda && this.samSuperType == null && !this.useOptimizedSuperClass) {
                createLegacyMethodOverride(jvmIrBuilderCreateJvmIrBuilder.getIrSymbols().getFunctionReferenceGetSignature().getOwner(), new Function1<JvmIrBuilder, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$build$1$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrExpression invoke(JvmIrBuilder createLegacyMethodOverride) {
                        Intrinsics.checkNotNullParameter(createLegacyMethodOverride, "$this$createLegacyMethodOverride");
                        FunctionReferenceLowering.FunctionReferenceBuilder functionReferenceBuilder = this.this$0;
                        return functionReferenceBuilder.generateSignature(createLegacyMethodOverride, functionReferenceBuilder.callee.getSymbol());
                    }
                });
                createLegacyMethodOverride(jvmIrBuilderCreateJvmIrBuilder.getIrSymbols().getFunctionReferenceGetName().getOwner(), new Function1<JvmIrBuilder, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$build$1$1$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrExpression invoke(JvmIrBuilder createLegacyMethodOverride) {
                        Intrinsics.checkNotNullParameter(createLegacyMethodOverride, "$this$createLegacyMethodOverride");
                        FunctionReferenceLowering.FunctionReferenceBuilder functionReferenceBuilder = this.this$0;
                        String strAsString = functionReferenceBuilder.getOriginalName(functionReferenceBuilder.callee).asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        return ExpressionHelpersKt.irString(createLegacyMethodOverride, strAsString);
                    }
                });
                createLegacyMethodOverride(jvmIrBuilderCreateJvmIrBuilder.getIrSymbols().getFunctionReferenceGetOwner().getOwner(), new Function1<JvmIrBuilder, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$build$1$1$3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrExpression invoke(JvmIrBuilder createLegacyMethodOverride) {
                        Intrinsics.checkNotNullParameter(createLegacyMethodOverride, "$this$createLegacyMethodOverride");
                        return FunctionReferenceLowering.INSTANCE.calculateOwner$backend_jvm_lower(createLegacyMethodOverride, this.this$0.callee.getParent());
                    }
                });
            }
            if (this.needToGenerateSamEqualsHashCodeMethods) {
                generateSamEqualsHashCodeMethods(jvmIrBuilderCreateJvmIrBuilder, irVariableIrTemporary$default);
            }
            if (this.isKotlinFunInterface) {
                IrUtilsKt.addFakeOverrides$default(this.functionReferenceClass, jvmIrBuilderCreateJvmIrBuilder.getBackendContext().getTypeSystem(), null, null, 6, null);
            }
            irBlockBuilder.unaryPlus(this.functionReferenceClass);
            IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder, irConstructorCreateConstructor.getSymbol());
            if (irConstructorCallIrCall.getValueArgumentsCount() > 0) {
                Pair<IrValueParameter, IrExpression> pair2 = this.boundReceiver;
                Intrinsics.checkNotNull(pair2);
                irConstructorCallIrCall.putValueArgument(0, pair2.getSecond());
            }
            irBlockBuilder.unaryPlus(irConstructorCallIrCall);
            return irBlockBuilder.getIrBlockBody();
        }

        private final void generateSamEqualsHashCodeMethods(final JvmIrBuilder jvmIrBuilder, final IrVariable irVariable) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
            if (this.samSuperType == null) {
                throw new IllegalStateException(("equals/hashCode can only be generated for fun interface wrappers: " + RenderIrElementKt.render$default(this.callee, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            if (!this.useOptimizedSuperClass) {
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(this.functionReferenceClass, Namer.EQUALS_METHOD_NAME, jvmIrBuilder.getBackendContext().getIrBuiltIns().getBooleanType(), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "other", jvmIrBuilder.getBackendContext().getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                boolean z = false;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = null;
                boolean z2 = false;
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol3 : IrUtilsKt.getFunctions(this.functionSuperClass)) {
                    if (isEqualsFromAny(irSimpleFunctionSymbol3.getOwner())) {
                        if (z2) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z2 = true;
                        irSimpleFunctionSymbol2 = irSimpleFunctionSymbol3;
                    }
                }
                if (z2) {
                    irSimpleFunctionAddFunction$default.setOverriddenSymbols(CollectionsKt.listOf(irSimpleFunctionSymbol2));
                    IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(this.functionReferenceClass, "hashCode", jvmIrBuilder.getBackendContext().getIrBuiltIns().getIntType(), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol4 : IrUtilsKt.getFunctions(this.functionSuperClass)) {
                        if (isHashCodeFromAny(irSimpleFunctionSymbol4.getOwner())) {
                            if (z) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            z = true;
                            irSimpleFunctionSymbol = irSimpleFunctionSymbol4;
                        }
                    }
                    if (z) {
                        irSimpleFunctionAddFunction$default2.setOverriddenSymbols(CollectionsKt.listOf(irSimpleFunctionSymbol));
                        return;
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            new SamEqualsHashCodeMethodsGenerator(jvmIrBuilder.getBackendContext(), this.functionReferenceClass, this.samSuperType, new Function2<IrBuilderWithScope, IrExpression, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$generateSamEqualsHashCodeMethods$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(final IrBuilderWithScope $receiver, IrExpression it) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    Intrinsics.checkNotNullParameter(it, "it");
                    Sequence<IrConstructor> constructors = IrUtilsKt.getConstructors((this.this$0.isAdaptedReference ? jvmIrBuilder.getBackendContext().getIr().getSymbols().getAdaptedFunctionReference() : jvmIrBuilder.getBackendContext().getIr().getSymbols().getFunctionReferenceImpl()).getOwner());
                    FunctionReferenceLowering.FunctionReferenceBuilder functionReferenceBuilder = this.this$0;
                    IrConstructor irConstructor = null;
                    boolean z3 = false;
                    for (IrConstructor irConstructor2 : constructors) {
                        if (irConstructor2.getValueParameters().size() == (functionReferenceBuilder.boundReceiver != null ? 1 : 0) + 5) {
                            if (z3) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            irConstructor = irConstructor2;
                            z3 = true;
                        }
                    }
                    if (z3) {
                        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor($receiver, irConstructor.getSymbol(), CollectionsKt.emptyList());
                        FunctionReferenceLowering.FunctionReferenceBuilder functionReferenceBuilder2 = this.this$0;
                        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
                        final IrVariable irVariable2 = irVariable;
                        functionReferenceBuilder2.generateConstructorCallArguments(jvmIrBuilder2, irConstructorCallIrCallConstructor, new Function1<IrBuilder, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$generateSamEqualsHashCodeMethods$4$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final IrExpression invoke(IrBuilder generateConstructorCallArguments) {
                                Intrinsics.checkNotNullParameter(generateConstructorCallArguments, "$this$generateConstructorCallArguments");
                                IrBuilderWithScope irBuilderWithScope = $receiver;
                                IrVariable irVariable3 = irVariable2;
                                Intrinsics.checkNotNull(irVariable3);
                                return ExpressionHelpersKt.irGet(irBuilderWithScope, irVariable3);
                            }
                        });
                        return irConstructorCallIrCallConstructor;
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            }).generate();
        }

        private final boolean isEqualsFromAny(IrSimpleFunction f) {
            IrValueParameter irValueParameter;
            IrType type;
            return Intrinsics.areEqual(f.getName().asString(), Namer.EQUALS_METHOD_NAME) && f.getExtensionReceiverParameter() == null && (irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) f.getValueParameters())) != null && (type = irValueParameter.getType()) != null && IrTypePredicatesKt.isNullableAny(type);
        }

        private final boolean isHashCodeFromAny(IrSimpleFunction f) {
            return Intrinsics.areEqual(f.getName().asString(), "hashCode") && f.getExtensionReceiverParameter() == null && f.getValueParameters().isEmpty();
        }

        private final IrConstructor createConstructor() {
            int i;
            IrConstructor irConstructor;
            IrClass irClass = this.functionReferenceClass;
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(this.functionReferenceClass));
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
            final IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            irClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(irClass);
            FunctionReferenceLowering functionReferenceLowering = this.this$0;
            if (this.samSuperType == null && this.boundReceiver != null) {
                DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "receiver", functionReferenceLowering.context.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
            }
            if (this.isFunInterfaceConstructorReference) {
                irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(functionReferenceLowering.context.getIr().getSymbols().getFunInterfaceConstructorReferenceClass().getOwner()));
            } else if (this.samSuperType != null) {
                irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(functionReferenceLowering.context.getIrBuiltIns().getAnyClass().getOwner()));
            } else {
                boolean z = false;
                if (this.isLightweightLambda && !this.isAdaptedReference) {
                    i = 0;
                } else if (!this.isHeavyweightLambda || this.isAdaptedReference) {
                    i = (this.boundReceiver != null ? 1 : 0) + 1 + (this.useOptimizedSuperClass ? 4 : 0);
                } else {
                    i = 1;
                }
                IrClass irClass2 = IrTypesKt.getClass(this.superType);
                Intrinsics.checkNotNull(irClass2);
                IrConstructor irConstructor2 = null;
                for (IrConstructor irConstructor3 : IrUtilsKt.getConstructors(irClass2)) {
                    if (irConstructor3.getValueParameters().size() == i) {
                        if (z) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z = true;
                        irConstructor2 = irConstructor3;
                    }
                }
                if (!z) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                irConstructor = irConstructor2;
            }
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(functionReferenceLowering.context, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, null);
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            final IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset());
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, irConstructor);
            if (this.samSuperType == null) {
                generateConstructorCallArguments(jvmIrBuilderCreateJvmIrBuilder$default, irDelegatingConstructorCall, new Function1<IrBuilder, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionReferenceLowering$FunctionReferenceBuilder$createConstructor$2$1$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrExpression invoke(IrBuilder generateConstructorCallArguments) {
                        Intrinsics.checkNotNullParameter(generateConstructorCallArguments, "$this$generateConstructorCallArguments");
                        return ExpressionHelpersKt.irGet(irBlockBodyBuilder, (IrValueDeclaration) CollectionsKt.first((List) irConstructorBuildConstructor.getValueParameters()));
                    }
                });
            }
            irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
            irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), this.functionReferenceClass.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
            return irConstructorBuildConstructor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void generateConstructorCallArguments(JvmIrBuilder jvmIrBuilder, IrFunctionAccessExpression irFunctionAccessExpression, Function1<? super IrBuilder, ? extends IrExpression> function1) {
            int i = 0;
            if (this.isFunInterfaceConstructorReference) {
                IrClassSymbol irClassSymbol = this.constructedFunInterfaceSymbol;
                Intrinsics.checkNotNull(irClassSymbol);
                irFunctionAccessExpression.putValueArgument(0, JvmIrUtilsKt.kClassToJavaClass(jvmIrBuilder, JvmIrUtilsKt.kClassReference(jvmIrBuilder, IrUtilsKt.getDefaultType(irClassSymbol.getOwner()))));
                return;
            }
            if (!this.isLightweightLambda) {
                irFunctionAccessExpression.putValueArgument(0, ExpressionHelpersKt.irInt$default(jvmIrBuilder, this.argumentTypes.size() + (IrUtilsKt.isSuspend(this.irFunctionReference) ? 1 : 0), null, 2, null));
                i = 1;
            }
            if (this.boundReceiver != null) {
                irFunctionAccessExpression.putValueArgument(i, function1.invoke(jvmIrBuilder));
                i++;
            }
            if (this.isLambda || !this.useOptimizedSuperClass) {
                return;
            }
            IrFunction irFunction = this.adaptedReferenceOriginalTarget;
            if (irFunction == null) {
                irFunction = this.callee;
            }
            irFunctionAccessExpression.putValueArgument(i, JvmIrUtilsKt.kClassToJavaClass(jvmIrBuilder, FunctionReferenceLowering.INSTANCE.calculateOwnerKClass$backend_jvm_lower(jvmIrBuilder, irFunction.getParent())));
            JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
            String strAsString = getOriginalName(irFunction).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irFunctionAccessExpression.putValueArgument(i + 1, ExpressionHelpersKt.irString(jvmIrBuilder2, strAsString));
            irFunctionAccessExpression.putValueArgument(i + 2, generateSignature(jvmIrBuilder, irFunction.getSymbol()));
            irFunctionAccessExpression.putValueArgument(i + 3, ExpressionHelpersKt.irInt$default(jvmIrBuilder2, getFunctionReferenceFlags(irFunction), null, 2, null));
        }

        private final int getFunctionReferenceFlags(IrFunction callableReferenceTarget) {
            return JvmIrUtilsKt.getCallableReferenceTopLevelFlag(callableReferenceTarget) + (getAdaptedCallableReferenceFlags() << 1);
        }

        private final int getAdaptedCallableReferenceFlags() {
            int i = 0;
            if (this.adaptedReferenceOriginalTarget == null) {
                return 0;
            }
            boolean zHasVarargMappedToElement = hasVarargMappedToElement();
            int i2 = (AdditionalIrUtilsKt.isSuspend(this.adaptedReferenceOriginalTarget) || !AdditionalIrUtilsKt.isSuspend(this.callee)) ? 0 : 1;
            if (!IrTypePredicatesKt.isUnit(this.adaptedReferenceOriginalTarget.getReturnType()) && IrTypePredicatesKt.isUnit(this.callee.getReturnType())) {
                i = 1;
            }
            return (zHasVarargMappedToElement ? 1 : 0) + (i2 << 1) + (i << 2);
        }

        private final boolean hasVarargMappedToElement() {
            IrFunctionAccessExpression irFunctionAccessExpression = this.adapteeCall;
            if (irFunctionAccessExpression == null) {
                return false;
            }
            int valueArgumentsCount = irFunctionAccessExpression.getValueArgumentsCount();
            for (int i = 0; i < valueArgumentsCount; i++) {
                IrExpression valueArgument = this.adapteeCall.getValueArgument(i);
                if (valueArgument != null && (valueArgument instanceof IrVararg)) {
                    Iterator<IrVarargElement> it = ((IrVararg) valueArgument).getElements().iterator();
                    while (it.hasNext()) {
                        if (it.next() instanceof IrGetValue) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private final IrSimpleFunction createInvokeMethod(IrValueDeclaration receiverVar) {
            IrClass irClass = this.functionReferenceClass;
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            IrElementBuilderKt.setSourceRange(irFunctionBuilder, this.isLambda ? this.callee : this.irFunctionReference);
            irFunctionBuilder.setName(this.superMethod.getName());
            irFunctionBuilder.setReturnType(this.referenceReturnType);
            irFunctionBuilder.setSuspend(AdditionalIrUtilsKt.isSuspend(this.callee));
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            irSimpleFunctionBuildFunction.setMetadata(this.functionReferenceClass.getMetadata());
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionBuildFunction.getOverriddenSymbols(), this.superMethod.getSymbol()));
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(DeclarationBuildersKt.buildReceiverParameter$default(irSimpleFunctionBuildFunction, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, IrTypesKt.getDefaultType(this.functionReferenceClass.getSymbol()), 0, 0, 24, null));
            if (this.isLambda) {
                createLambdaInvokeMethod(irSimpleFunctionBuildFunction);
            } else if (this.isFunInterfaceConstructorReference) {
                createFunInterfaceConstructorInvokeMethod(irSimpleFunctionBuildFunction);
            } else {
                createFunctionReferenceInvokeMethod(irSimpleFunctionBuildFunction, receiverVar);
            }
            return irSimpleFunctionBuildFunction;
        }

        private final void createLambdaInvokeMethod(IrSimpleFunction irSimpleFunction) {
            irSimpleFunction.setAnnotations(CollectionsKt.plus((Collection) irSimpleFunction.getAnnotations(), (Iterable) this.callee.getAnnotations()));
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(IrUtilsKt.getExplicitParameters(this.callee));
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                IrValueParameter irValueParameter = (IrValueParameter) indexedValue.component2();
                Pair pair = TuplesKt.to(irValueParameter, IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction, null, index, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection) irSimpleFunction.getValueParameters(), (Iterable) linkedHashMap.values()));
            irSimpleFunction.setBody(IrInlineUtilsKt.moveBodyTo(this.callee, irSimpleFunction, linkedHashMap));
        }

        private final void createFunInterfaceConstructorInvokeMethod(IrSimpleFunction irSimpleFunction) {
            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) this.callee.getValueParameters());
            if (irValueParameter == null) {
                throw new AssertionError("Single value parameter expected: " + RenderIrElementKt.render$default(this.callee, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8186, null);
            Map mapMapOf = MapsKt.mapOf(TuplesKt.to(irValueParameter, irValueParameterCopyTo$default));
            irSimpleFunction.setValueParameters(CollectionsKt.listOf(irValueParameterCopyTo$default));
            irSimpleFunction.setBody(IrInlineUtilsKt.moveBodyTo(this.callee, irSimpleFunction2, mapMapOf));
            this.callee.setBody(null);
        }

        private final void createFunctionReferenceInvokeMethod(IrSimpleFunction irSimpleFunction, IrValueDeclaration irValueDeclaration) {
            int i = 0;
            int i2 = 0;
            for (IrType irType : this.argumentTypes) {
                int i3 = i2 + 1;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                Name nameIdentifier = Name.identifier("p" + i2);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irValueParameterBuilder.setName(nameIdentifier);
                irValueParameterBuilder.setType(irType);
                if (irValueParameterBuilder.getIndex() == -1) {
                    irValueParameterBuilder.setIndex(irSimpleFunction2.getValueParameters().size());
                }
                irSimpleFunction2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction2.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2)));
                i2 = i3;
            }
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.this$0.context, irSimpleFunction.getSymbol(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset());
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, this.callee.getSymbol(), this.referenceReturnType);
            for (IrTypeParameter irTypeParameter : IrUtilsKt.getAllTypeParameters(this.irFunctionReference.getSymbol().getOwner())) {
                irFunctionAccessExpressionIrCall.putTypeArgument(irTypeParameter.getIndex(), this.typeArgumentsMap.get(irTypeParameter.getSymbol()));
            }
            for (IrValueParameter irValueParameter : IrUtilsKt.getExplicitParameters(this.callee)) {
                Pair<IrValueParameter, IrExpression> pair = this.boundReceiver;
                IrGetValueImpl irGetValueImplIrGet = null;
                if (Intrinsics.areEqual(pair != null ? pair.getFirst() : null, irValueParameter)) {
                    if (this.samSuperType == null) {
                        IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter);
                        irGetValueImplIrGet = ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, ExpressionHelpersKt.irGetField$default(jvmIrBuilder, ExpressionHelpersKt.irGet(jvmIrBuilder, dispatchReceiverParameter), FunctionReferenceLowering.INSTANCE.getReceiverField$backend_jvm_lower(this.functionReferenceClass, jvmIrBuilderCreateJvmIrBuilder.getBackendContext()), null, 4, null), this.boundReceiver.getSecond().getType());
                    } else {
                        Intrinsics.checkNotNull(irValueDeclaration);
                        irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, irValueDeclaration);
                    }
                } else if (i < this.argumentTypes.size()) {
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, irSimpleFunction.getValueParameters().get(i));
                    i++;
                }
                if (irGetValueImplIrGet != null) {
                    IrExpressionsKt.putArgument(irFunctionAccessExpressionIrCall, this.callee, irValueParameter, irGetValueImplIrGet);
                }
            }
            irSimpleFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, inlineAdapterCallIfPossible(irFunctionAccessExpressionIrCall, irSimpleFunction)));
        }

        private final IrExpression inlineAdapterCallIfPossible(IrFunctionAccessExpression expression, IrSimpleFunction invokeMethod) {
            IrExpression value;
            IrCall irCall = expression instanceof IrCall ? (IrCall) expression : null;
            if (irCall == null) {
                return expression;
            }
            IrSimpleFunction owner = irCall.getSymbol().getOwner();
            if (!Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE)) {
                return expression;
            }
            if (owner.getIsSuspend()) {
                return expression;
            }
            IrBody body = owner.getBody();
            IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
            if (irBlockBody == null || irBlockBody.getStatements().size() != 1) {
                throw new AssertionError("Unexpected adapter body: " + DumpIrTreeKt.dump$default(owner, null, 1, null));
            }
            IrStatement irStatement = irBlockBody.getStatements().get(0);
            if (irStatement instanceof IrReturn) {
                value = ((IrReturn) irStatement).getValue();
            } else if (((irStatement instanceof IrTypeOperatorCall) && ((IrTypeOperatorCall) irStatement).getOperator() == IrTypeOperator.IMPLICIT_COERCION_TO_UNIT) || (irStatement instanceof IrCall) || (irStatement instanceof IrConstructorCall)) {
                value = (IrExpression) irStatement;
            } else {
                throw new AssertionError("Unexpected adapter body: " + DumpIrTreeKt.dump$default(owner, null, 1, null));
            }
            int startOffset = irCall.getStartOffset();
            int endOffset = irCall.getEndOffset();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            IrBlockImpl irBlockImpl = new IrBlockImpl(startOffset, endOffset, irCall.getType(), null);
            Ref.IntRef intRef = new Ref.IntRef();
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                IrExpression dispatchReceiver = irCall.getDispatchReceiver();
                if (dispatchReceiver != null) {
                    linkedHashMap2.put(dispatchReceiverParameter, inlineAdapterCallIfPossible$wrapIntoTemporaryVariableIfNecessary(startOffset, endOffset, intRef, invokeMethod, irBlockImpl, dispatchReceiver));
                } else {
                    throw new AssertionError("No dispatch receiver in adapter call: " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
                }
            }
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                LinkedHashMap linkedHashMap3 = linkedHashMap;
                IrExpression extensionReceiver = irCall.getExtensionReceiver();
                if (extensionReceiver != null) {
                    linkedHashMap3.put(extensionReceiverParameter, inlineAdapterCallIfPossible$wrapIntoTemporaryVariableIfNecessary(startOffset, endOffset, intRef, invokeMethod, irBlockImpl, extensionReceiver));
                } else {
                    throw new AssertionError("No extension receiver in adapter call: " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
                }
            }
            for (Iterator<IrValueParameter> it = owner.getValueParameters().iterator(); it.hasNext(); it = it) {
                IrValueParameter next = it.next();
                LinkedHashMap linkedHashMap4 = linkedHashMap;
                IrExpression valueArgument = irCall.getValueArgument(next.getIndex());
                if (valueArgument != null) {
                    linkedHashMap4.put(next, inlineAdapterCallIfPossible$wrapIntoTemporaryVariableIfNecessary(startOffset, endOffset, intRef, invokeMethod, irBlockImpl, valueArgument));
                } else {
                    throw new AssertionError("No value argument #" + next.getIndex() + " in adapter call: " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
                }
            }
            irBlockImpl.getStatements().add(value.transform((IrElementTransformer<? super Object>) new VariableRemapper(linkedHashMap), (Object) null));
            owner.setBody(null);
            return (IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(irBlockImpl, invokeMethod);
        }

        private static final IrValueDeclaration inlineAdapterCallIfPossible$wrapIntoTemporaryVariableIfNecessary(int i, int i2, Ref.IntRef intRef, IrSimpleFunction irSimpleFunction, IrBlockImpl irBlockImpl, IrExpression irExpression) {
            if (irExpression instanceof IrGetValue) {
                return ((IrGetValue) irExpression).getSymbol().getOwner();
            }
            if (!(irExpression instanceof IrTypeOperatorCall) || !(((IrTypeOperatorCall) irExpression).getArgument() instanceof IrGetField)) {
                throw new AssertionError("Unexpected adapter argument:\n" + DumpIrTreeKt.dump$default(irExpression, null, 1, null));
            }
            IrDeclarationOrigin.IR_TEMPORARY_VARIABLE ir_temporary_variable = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
            IrVariableSymbolImpl irVariableSymbolImpl = new IrVariableSymbolImpl(null, 1, null);
            StringBuilder sb = new StringBuilder("tmp_");
            int i3 = intRef.element;
            intRef.element = i3 + 1;
            Name nameIdentifier = Name.identifier(sb.append(i3).toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrVariableImpl irVariableImpl = new IrVariableImpl(i, i2, ir_temporary_variable, irVariableSymbolImpl, nameIdentifier, irExpression.getType(), false, false, false);
            irVariableImpl.setParent(irSimpleFunction);
            irVariableImpl.setInitializer(irExpression);
            irBlockImpl.getStatements().add(irVariableImpl);
            return irVariableImpl;
        }

        static /* synthetic */ IrSimpleFunction buildOverride$default(FunctionReferenceBuilder functionReferenceBuilder, IrSimpleFunction irSimpleFunction, IrType irType, int i, Object obj) {
            if ((i & 2) != 0) {
                irType = irSimpleFunction.getReturnType();
            }
            return functionReferenceBuilder.buildOverride(irSimpleFunction, irType);
        }

        private final IrSimpleFunction buildOverride(IrSimpleFunction superFunction, IrType newReturnType) {
            IrClass irClass = this.functionReferenceClass;
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            IrElementBuilderKt.setSourceRange(irFunctionBuilder, this.irFunctionReference);
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
            irFunctionBuilder.setName(superFunction.getName());
            irFunctionBuilder.setReturnType(newReturnType);
            irFunctionBuilder.setVisibility(superFunction.getVisibility());
            irFunctionBuilder.setSuspend(superFunction.getIsSuspend());
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionBuildFunction.getOverriddenSymbols(), superFunction.getSymbol()));
            IrValueParameter thisReceiver = this.functionReferenceClass.getThisReceiver();
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
            return irSimpleFunctionBuildFunction;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Name getOriginalName(IrFunction irFunction) {
            Name name2;
            MetadataSource metadata = irFunction.getMetadata();
            return (metadata == null || (name2 = metadata.getName()) == null) ? irFunction.getName() : name2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression generateSignature(JvmIrBuilder jvmIrBuilder, IrFunctionSymbol irFunctionSymbol) {
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, jvmIrBuilder.getBackendContext().getIr().getSymbols().getSignatureStringIntrinsic());
            IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(-1, -1, this.irFunctionReference.getType(), irFunctionSymbol, this.irFunctionReference.getTypeArgumentsCount(), irFunctionSymbol.getOwner().getValueParameters().size(), this.irFunctionReference.getReflectionTarget(), null);
            IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionReferenceImpl, this.irFunctionReference, 0, 2, null);
            Unit unit = Unit.INSTANCE;
            irCall.putValueArgument(0, irFunctionReferenceImpl);
            return irCall;
        }

        private final Pair<IrValueParameter, IrGetObjectValueImpl> createFakeBoundReceiverForJvmStaticInObject() {
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(this.callee);
            IrFunction irFunction = this.callee;
            IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
            Name nameIdentifier = Name.identifier("$this");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irValueParameterBuilder.setName(nameIdentifier);
            irValueParameterBuilder.setType(IrTypesKt.typeWith(parentAsClass, new IrType[0]));
            return TuplesKt.to(DeclarationBuildersKt.buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction), new IrGetObjectValueImpl(-1, -1, IrTypesKt.typeWith(parentAsClass, new IrType[0]), parentAsClass.getSymbol()));
        }

        private final IrSimpleFunction createLegacyMethodOverride(IrSimpleFunction superFunction, Function1<? super JvmIrBuilder, ? extends IrExpression> generator) {
            IrSimpleFunction irSimpleFunctionBuildOverride$default = buildOverride$default(this, superFunction, null, 2, null);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.this$0.context, irSimpleFunctionBuildOverride$default.getSymbol(), irSimpleFunctionBuildOverride$default.getStartOffset(), irSimpleFunctionBuildOverride$default.getEndOffset());
            irSimpleFunctionBuildOverride$default.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder, generator.invoke(jvmIrBuilderCreateJvmIrBuilder)));
            return irSimpleFunctionBuildOverride$default;
        }
    }

    /* JADX INFO: compiled from: FunctionReferenceLowering.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¢\u0006\u0002\b\bJ\u0019\u0010\t\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¢\u0006\u0002\b\nJ\u0019\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FunctionReferenceLowering$Companion;", "", "()V", "calculateOwner", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "irContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "calculateOwner$backend_jvm_lower", "calculateOwnerKClass", "calculateOwnerKClass$backend_jvm_lower", "getReceiverField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getReceiverField$backend_jvm_lower", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrExpression calculateOwner$backend_jvm_lower(JvmIrBuilder jvmIrBuilder, IrDeclarationParent irContainer) {
            Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
            Intrinsics.checkNotNullParameter(irContainer, "irContainer");
            IrExpression irExpressionCalculateOwnerKClass$backend_jvm_lower = calculateOwnerKClass$backend_jvm_lower(jvmIrBuilder, irContainer);
            IrClass irClass = irContainer instanceof IrClass ? (IrClass) irContainer : null;
            if ((irClass == null || !IrUtilsKt.isFileClass(irClass)) && !(irContainer instanceof IrPackageFragment)) {
                return irExpressionCalculateOwnerKClass$backend_jvm_lower;
            }
            JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder2, jvmIrBuilder.getIrSymbols().getGetOrCreateKotlinPackage());
            irCall.putValueArgument(0, JvmIrUtilsKt.kClassToJavaClass(jvmIrBuilder, irExpressionCalculateOwnerKClass$backend_jvm_lower));
            irCall.putValueArgument(1, ExpressionHelpersKt.irString(jvmIrBuilder2, jvmIrBuilder.getBackendContext().getState().getModuleName()));
            return irCall;
        }

        public final IrExpression calculateOwnerKClass$backend_jvm_lower(JvmIrBuilder jvmIrBuilder, IrDeclarationParent irContainer) {
            Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
            Intrinsics.checkNotNullParameter(irContainer, "irContainer");
            return JvmIrUtilsKt.kClassReference(jvmIrBuilder, JvmIrUtilsKt.getCallableReferenceOwnerKClassType(irContainer, jvmIrBuilder.getBackendContext()));
        }

        public final IrField getReceiverField$backend_jvm_lower(IrClass irClass, JvmBackendContext context) {
            Intrinsics.checkNotNullParameter(irClass, "<this>");
            Intrinsics.checkNotNullParameter(context, "context");
            IrFactory irFactory = context.getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier("receiver");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(context.getIrBuiltIns().getAnyNType());
            DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
            Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
            irFieldBuilder.setVisibility(PROTECTED);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            irFieldBuildField.setParent(irClass);
            return irFieldBuildField;
        }
    }

    private static final IrGetValue createProxyLocalFunctionForIndySamConversion$lambda$7$addAndGetProxyValueParameter(IrSimpleFunction irSimpleFunction, int i, int i2, Ref.IntRef intRef, IrValueParameter irValueParameter) {
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.updateFrom(irValueParameter);
        Name nameIdentifier = Name.identifier("p" + intRef.element + '$' + irValueParameter.getName().asString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setIndex(intRef.element);
        irValueParameterBuilder.setType(JvmIrTypeUtilsKt.eraseTypeParameters(irValueParameter.getType()));
        intRef.element++;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2);
        irValueParameterBuildValueParameter.setParent(irSimpleFunction);
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), irValueParameterBuildValueParameter));
        return new IrGetValueImpl(i, i2, irValueParameterBuildValueParameter.getSymbol(), null, 8, null);
    }
}
