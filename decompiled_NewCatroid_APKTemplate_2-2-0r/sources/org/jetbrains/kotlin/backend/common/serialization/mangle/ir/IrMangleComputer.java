package org.jetbrains.kotlin.backend.common.serialization.mangle.ir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
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
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
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
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: IrMangleComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\n\b\u0016\u0018\u00002.\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0001:\u00016B+\u0012\n\u0010\b\u001a\u00060\tj\u0002`\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\fH\u0016J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0014J\u0010\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0004H\u0004J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0017\u001a\u00020\u0006H\u0014J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0002H\u0004J\u0010\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0006H\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0019\u001a\u00020\u0004H\u0014J$\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\"0\u00162\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u0002H\u0014J\u0012\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0007H\u0004J\u0010\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u0005H\u0004J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0014J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020\u0003H\u0014J\u0010\u0010+\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u0005H\u0004J&\u0010,\u001a\u00020-2\n\u0010.\u001a\u00060\tj\u0002`\n2\u0006\u0010*\u001a\u00020\u00032\b\u0010/\u001a\u0004\u0018\u00010\u0007H\u0004J\u0010\u00100\u001a\u00020 2\u0006\u00101\u001a\u00020\u0002H\u0014J\f\u00102\u001a\u00020\u0002*\u00020\u0002H\u0014J\f\u00103\u001a\u00020-*\u00020\u0002H\u0004J\f\u00104\u001a\u00020-*\u00020\u0002H\u0004J\f\u00105\u001a\u00020-*\u00020\u0002H\u0014R\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrMangleComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/BaseKotlinMangleComputer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "compatibleMode", "", "allowOutOfScopeTypeParameters", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;ZZ)V", "getCompatibleMode", "()Z", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "getContextReceiverTypes", "", "function", "getEffectiveParent", "typeParameter", "getExtensionReceiverParameterType", "getIndexOfTypeParameter", "", "container", "getReturnType", "getTypeParameterName", "", "getTypeParametersWithIndices", "Lkotlin/collections/IndexedValue;", "getTypeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "session", "getValueParameterType", "valueParameter", "getValueParameters", "isUnit", ModuleXmlParser.TYPE, "isVararg", "mangleType", "", "tBuilder", "declarationSiteSession", "renderDeclaration", "declaration", "asTypeParameterContainer", "visit", "visitParent", "visitParentForFunctionMangling", "Visitor", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrMangleComputer extends BaseKotlinMangleComputer {
    private final boolean compatibleMode;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public IrDeclaration asTypeParameterContainer(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return irDeclaration;
    }

    public /* synthetic */ IrMangleComputer(StringBuilder sb, MangleMode mangleMode, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sb, mangleMode, z, (i & 8) != 0 ? false : z2);
    }

    protected final boolean getCompatibleMode() {
        return this.compatibleMode;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrMangleComputer(StringBuilder builder, MangleMode mode, boolean z, boolean z2) {
        super(builder, mode, z2);
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.compatibleMode = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final IrTypeSystemContext getTypeSystemContext(Void session) {
        return new IrTypeSystemContext() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer.getTypeSystemContext.1
            @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public IrSimpleType anyType() {
                return IrTypeSystemContext.DefaultImpls.anyType(this);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean areEqualTypeConstructors(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
                return IrTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, typeConstructorMarker, typeConstructorMarker2);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.argumentsCount(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public SimpleTypeMarker arrayType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.arrayType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrSimpleType asArgumentList(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asArgumentList(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asCapturedType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asDynamicType(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asFlexibleType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asSimpleType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrTypeArgument asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.asTypeArgument(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.canHaveUndefinedNullability(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus) {
                return IrTypeSystemContext.DefaultImpls.captureFromArguments(this, simpleTypeMarker, captureStatus);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker captureFromExpression(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.captureFromExpression(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.captureStatus(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeSubstitutorMarker createEmptySubstitutor() {
                return IrTypeSystemContext.DefaultImpls.createEmptySubstitutor(this);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public SimpleTypeMarker createErrorType(String str) {
                return IrTypeSystemContext.DefaultImpls.createErrorType(this, str);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
                return IrTypeSystemContext.DefaultImpls.createFlexibleType(this, simpleTypeMarker, simpleTypeMarker2);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public SimpleTypeMarker createSimpleType(TypeConstructorMarker typeConstructorMarker, List<? extends TypeArgumentMarker> list, boolean z, boolean z2, List<? extends AnnotationMarker> list2) {
                return IrTypeSystemContext.DefaultImpls.createSimpleType(this, typeConstructorMarker, list, z, z2, list2);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public IrStarProjectionImpl createStarProjection(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.createStarProjection(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public TypeArgumentMarker createTypeArgument(KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance) {
                return IrTypeSystemContext.DefaultImpls.createTypeArgument(this, kotlinTypeMarker, typeVariance);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
            public KotlinTypeMarker createUninferredType(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.createUninferredType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> list) {
                return IrTypeSystemContext.DefaultImpls.findCommonIntegerLiteralTypesSuperType(this, list);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
                return IrTypeSystemContext.DefaultImpls.getAnnotationFirstArgumentValue(this, kotlinTypeMarker, fqName);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
                return IrTypeSystemContext.DefaultImpls.getArgument(this, kotlinTypeMarker, i);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.getArguments(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.getAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.getCustomAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public Name getName(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.getName(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrTypeParameterSymbol getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
                return IrTypeSystemContext.DefaultImpls.getParameter(this, typeConstructorMarker, i);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<IrTypeParameterSymbol> getParameters(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getParameters(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getPrimitiveType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrType getType(TypeArgumentMarker typeArgumentMarker) {
                return IrTypeSystemContext.DefaultImpls.getType(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public IrTypeParameterSymbol getTypeConstructor(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.getTypeConstructor(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getTypeParameter(this, typeVariableTypeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.getUnsubstitutedUnderlyingType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
                return IrTypeSystemContext.DefaultImpls.getUpperBound(this, typeParameterMarker, i);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.getUpperBounds(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.getValueClassProperties(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
                return IrTypeSystemContext.DefaultImpls.getVariance(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.getVariance(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
                return IrTypeSystemContext.DefaultImpls.hasAnnotation(this, kotlinTypeMarker, fqName);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.hasCustomAttributes(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.hasRecursiveBounds(this, typeParameterMarker, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list) {
                return IrTypeSystemContext.DefaultImpls.intersectTypes(this, list);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            /* JADX INFO: renamed from: intersectTypes */
            public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> list) {
                return IrTypeSystemContext.DefaultImpls.m7220intersectTypes((IrTypeSystemContext) this, list);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isAnonymous(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isAnyConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isArrayOrNullableArray(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isDenotable(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isError(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isError(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isError(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isExtensionFunction(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isFinalClassOrEnumEntryOrAnnotationClassConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isInlineClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isInnerClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isIntegerConstantOperatorTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isIntegerLiteralConstantTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isInterface(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isInterfaceOrAnnotationClass(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isIntersection(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isLocalType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isMarkedNullable(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isMarkedNullable((IrTypeSystemContext) this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isMultiFieldValueClass(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isNothingConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isNullableType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isOldCapturedType(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isPrimitiveType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isProjectionNotNull(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isRawType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isReified(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.isReified(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isSingleClassifierType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
                return IrTypeSystemContext.DefaultImpls.isStarProjection(this, typeArgumentMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isStubType(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isStubTypeForBuilderInference(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isStubTypeForVariableInSubtyping(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isTypeParameterTypeConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isTypeVariableType(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
            public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.isUninferredParameter(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.lowerBound(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.lowerType(this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.makeDefinitelyNotNullOrNotNull(this, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.makeSimpleTypeDefinitelyNotNullOrNotNull(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
            public TypeCheckerState newTypeCheckerState(boolean z, boolean z2) {
                return IrTypeSystemContext.DefaultImpls.newTypeCheckerState(this, z, z2);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public IrSimpleType nothingType() {
                return IrTypeSystemContext.DefaultImpls.nothingType(this);
            }

            @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public IrSimpleType nullableAnyType() {
                return IrTypeSystemContext.DefaultImpls.nullableAnyType(this);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
            public IrSimpleType nullableNothingType() {
                return IrTypeSystemContext.DefaultImpls.nullableNothingType(this);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.original(this, definitelyNotNullTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.parametersCount(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
            public Set<IrType> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.projection(this, capturedTypeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list) {
                return IrTypeSystemContext.DefaultImpls.replaceCustomAttributes(this, kotlinTypeMarker, list);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.replaceType(this, typeArgumentMarker, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.safeSubstitute(this, typeSubstitutorMarker, kotlinTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.substitutionSupertypePolicy(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.supertypes(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.toErrorType(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.typeConstructor((IrTypeSystemContext) this, capturedTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.typeConstructor(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.typeDepth(this, simpleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
                return IrTypeSystemContext.DefaultImpls.typeSubstitutorByTypeConstructor(this, map);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
            public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> list) {
                return IrTypeSystemContext.DefaultImpls.unionTypeAttributes(this, list);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
                return IrTypeSystemContext.DefaultImpls.unwrapStubTypeVariableConstructor(this, typeConstructorMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
                return IrTypeSystemContext.DefaultImpls.upperBound(this, flexibleTypeMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
                return IrTypeSystemContext.DefaultImpls.upperBoundCount(this, typeParameterMarker);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
                return IrTypeSystemContext.DefaultImpls.withNullability(this, kotlinTypeMarker, z);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
            public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
                return IrTypeSystemContext.DefaultImpls.withNullability((IrTypeSystemContext) this, simpleTypeMarker, z);
            }

            @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext
            public IrBuiltIns getIrBuiltIns() {
                throw new UnsupportedOperationException("Builtins are unavailable");
            }
        };
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
    public IrMangleComputer copy(MangleMode newMode) {
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        return new IrMangleComputer(getBuilder(), newMode, this.compatibleMode, false, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void visitParent(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrVisitorsKt.acceptVoid(irDeclaration.getParent(), new Visitor());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void visit(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrVisitorsKt.acceptVoid(irDeclaration, new Visitor());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public void visitParentForFunctionMangling(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        if (parent instanceof IrField) {
            IrField irField = (IrField) parent;
            if (Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.DELEGATE.INSTANCE)) {
                parent = irField.getParent();
            }
        }
        IrVisitorsKt.acceptVoid(parent, new Visitor());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<IrType> getContextReceiverTypes(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(SequencesKt.take(CollectionsKt.asSequence(function.getValueParameters()), function.getContextReceiverParametersCount()), new Function1<IrValueParameter, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer.getContextReceiverTypes.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getIsHidden());
            }
        }), new Function1<IrValueParameter, IrType>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer.getContextReceiverTypes.2
            @Override // kotlin.jvm.functions.Function1
            public final IrType invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getType();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public IrType getExtensionReceiverParameterType(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null) {
            return null;
        }
        if (extensionReceiverParameter.getIsHidden()) {
            extensionReceiverParameter = null;
        }
        if (extensionReceiverParameter != null) {
            return extensionReceiverParameter.getType();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<IrValueParameter> getValueParameters(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return SequencesKt.toList(SequencesKt.filterNot(SequencesKt.drop(CollectionsKt.asSequence(function.getValueParameters()), function.getContextReceiverParametersCount()), new Function1<IrValueParameter, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer.getValueParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getIsHidden());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public IrType getReturnType(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function.getReturnType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<IndexedValue<IrTypeParameterSymbol>> getTypeParametersWithIndices(IrFunction function, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(container, "container");
        List<IrTypeParameter> typeParameters = function.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        for (IrTypeParameter irTypeParameter : typeParameters) {
            arrayList.add(new IndexedValue(irTypeParameter.getIndex(), irTypeParameter.getSymbol()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public boolean isUnit(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrTypePredicatesKt.isUnit(type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final IrDeclaration getEffectiveParent(IrTypeParameterSymbol typeParameter) {
        IrElement irElement;
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        IrTypeParameter owner = typeParameter.getOwner();
        IrElement parent = owner.getParent();
        if (parent instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) parent).getCorrespondingPropertySymbol();
            return (IrDeclaration) ((correspondingPropertySymbol == null || (irElement = (IrProperty) correspondingPropertySymbol.getOwner()) == null) ? parent : irElement);
        }
        if (parent instanceof IrTypeParametersContainer) {
            return (IrDeclaration) parent;
        }
        throw new IllegalStateException(("Unexpected type parameter container " + RenderIrElementKt.render$default(parent, (DumpIrTreeOptions) null, 1, (Object) null) + " for TP " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String renderDeclaration(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String getTypeParameterName(IrTypeParameterSymbol typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        String strAsString = typeParameter.getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final boolean isVararg(IrValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return valueParameter.getVarargElementType() != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final IrType getValueParameterType(IrValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return valueParameter.getType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final int getIndexOfTypeParameter(IrTypeParameterSymbol typeParameter, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(container, "container");
        return typeParameter.getOwner().getIndex();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void mangleType(StringBuilder tBuilder, IrType type, Void declarationSiteSession) {
        Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) type;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            if (classifier instanceof IrClassSymbol) {
                copy(MangleMode.FQNAME).visit((IrDeclaration) ((IrClassSymbol) classifier).getOwner());
            } else if (classifier instanceof IrTypeParameterSymbol) {
                mangleTypeParameterReference(tBuilder, (TypeParameterMarker) classifier);
            } else {
                boolean z = classifier instanceof IrScriptSymbol;
            }
            IrType irType = type;
            mangleTypeArguments(tBuilder, irType, null);
            if (IrTypePredicatesKt.isMarkedNullable(irSimpleType)) {
                appendSignature(tBuilder, MangleConstant.Q_MARK);
            }
            mangleTypePlatformSpecific(irType, tBuilder);
            return;
        }
        if (type instanceof IrDynamicType) {
            appendSignature(tBuilder, MangleConstant.DYNAMIC_MARK);
        } else {
            if (!(type instanceof IrErrorType)) {
                throw new IllegalStateException(("Unexpected type " + type).toString());
            }
            appendSignature(tBuilder, MangleConstant.ERROR_MARK);
        }
    }

    /* JADX INFO: compiled from: IrMangleComputer.kt */
    @Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\u001c\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\b2\u0006\u0010\t\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\b2\u0006\u0010\t\u001a\u00020#H\u0016R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrMangleComputer$Visitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "(Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrMangleComputer;)V", "isSyntheticForJavaField", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Z", "visitAnonymousInitializer", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "visitProperty", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Visitor implements IrElementVisitorVoid {
        public Visitor() {
        }

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
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public Void mo6335visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            throw new IllegalStateException(("unexpected element " + RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer.this.visitParent((IrDeclaration) declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer.this.mangleSimpleDeclaration(declaration, MangleConstant.ERROR_DECLARATION);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitClass */
        public void mo6112visitClass(IrClass declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer.this.getTypeParameterContainers().add(declaration);
            String strAsString = declaration.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            IrMangleComputer.this.mangleSimpleDeclaration(declaration, strAsString);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            FqName packageFqName = declaration.getPackageFqName();
            IrMangleComputer irMangleComputer = IrMangleComputer.this;
            if (packageFqName.isRoot()) {
                return;
            }
            StringBuilder builder = irMangleComputer.getBuilder();
            String strAsString = packageFqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irMangleComputer.appendName(builder, strAsString);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty declaration) {
            IrField backingField;
            List<IrTypeParameter> listEmptyList;
            IrValueParameter extensionReceiverParameter;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrSimpleFunction getter = declaration.getGetter();
            if (getter == null) {
                getter = declaration.getSetter();
            }
            boolean zIsStatic = true;
            if (getter != null || declaration.getBackingField() != null) {
                IrMangleComputer.this.getTypeParameterContainers().add(declaration);
                IrMangleComputer.this.visitParent((IrDeclaration) declaration);
                if (getter != null) {
                    if (getter.getDispatchReceiverParameter() != null || (declaration.getParent() instanceof IrPackageFragment) || IrUtilsKt.isFacadeClass(declaration.getParent())) {
                        zIsStatic = false;
                    }
                } else {
                    IrProperty irProperty = (IrProperty) IrFakeOverrideUtilsKt.resolveFakeOverride$default(declaration, false, null, 3, null);
                    if (irProperty == null || (backingField = irProperty.getBackingField()) == null) {
                        throw new IllegalStateException(("Expected at least one accessor or a backing field for property " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    zIsStatic = backingField.getIsStatic();
                }
                if (zIsStatic) {
                    IrMangleComputer irMangleComputer = IrMangleComputer.this;
                    irMangleComputer.appendSignature(irMangleComputer.getBuilder(), MangleConstant.STATIC_MEMBER_MARK);
                }
                if (getter != null && (extensionReceiverParameter = getter.getExtensionReceiverParameter()) != null) {
                    IrMangleComputer irMangleComputer2 = IrMangleComputer.this;
                    irMangleComputer2.appendSignature(irMangleComputer2.getBuilder(), '@');
                    irMangleComputer2.mangleValueParameter(irMangleComputer2.getBuilder(), extensionReceiverParameter, null);
                }
                if (getter == null || (listEmptyList = getter.getTypeParameters()) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                StringBuilder builder = IrMangleComputer.this.getBuilder();
                MangleConstant mangleConstant = MangleConstant.TYPE_PARAMETERS;
                final IrMangleComputer irMangleComputer3 = IrMangleComputer.this;
                MangleUtilsKt.collectForMangler(listEmptyList, builder, mangleConstant, new Function2<StringBuilder, IrTypeParameter, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer$Visitor$visitProperty$3
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, IrTypeParameter irTypeParameter) {
                        invoke2(sb, irTypeParameter);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(StringBuilder collectForMangler, IrTypeParameter it) {
                        Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                        Intrinsics.checkNotNullParameter(it, "it");
                        irMangleComputer3.mangleTypeParameter(collectForMangler, it.getSymbol(), it.getIndex(), null);
                    }
                });
                IrMangleComputer.this.getBuilder().append(declaration.getName().asString());
                if (isSyntheticForJavaField(declaration)) {
                    IrMangleComputer.this.getBuilder().append(MangleConstant.JAVA_FIELD_SUFFIX);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException(("Expected at least one accessor or backing field for property " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        private final boolean isSyntheticForJavaField(IrProperty irProperty) {
            return Intrinsics.areEqual(irProperty.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && irProperty.getGetter() == null && irProperty.getSetter() == null;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
            if (IrMangleComputer.this.getCompatibleMode() || correspondingPropertySymbol == null) {
                IrMangleComputer irMangleComputer = IrMangleComputer.this;
                IrField irField = declaration;
                String strAsString = declaration.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                irMangleComputer.mangleSimpleDeclaration(irField, strAsString);
                return;
            }
            visitProperty(correspondingPropertySymbol.getOwner());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer irMangleComputer = IrMangleComputer.this;
            IrEnumEntry irEnumEntry = declaration;
            String strAsString = declaration.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irMangleComputer.mangleSimpleDeclaration(irEnumEntry, strAsString);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrAnonymousInitializer irAnonymousInitializer = declaration;
            List<IrDeclaration> declarations = IrUtilsKt.getParentAsClass(irAnonymousInitializer).getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrAnonymousInitializer) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            StringBuilder sb = new StringBuilder(MangleConstant.ANON_INIT_NAME_PREFIX);
            if (arrayList2.size() > 1) {
                sb.append('$');
                sb.append(arrayList2.indexOf(declaration));
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            IrMangleComputer.this.mangleSimpleDeclaration(irAnonymousInitializer, string);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer irMangleComputer = IrMangleComputer.this;
            IrTypeAlias irTypeAlias = declaration;
            String strAsString = declaration.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irMangleComputer.mangleSimpleDeclaration(irTypeAlias, strAsString);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer irMangleComputer = IrMangleComputer.this;
            irMangleComputer.visit(irMangleComputer.getEffectiveParent(declaration.getSymbol()));
            IrMangleComputer irMangleComputer2 = IrMangleComputer.this;
            irMangleComputer2.appendSignature(irMangleComputer2.getBuilder(), '@');
            IrMangleComputer irMangleComputer3 = IrMangleComputer.this;
            irMangleComputer3.appendSignature(irMangleComputer3.getBuilder(), declaration.getIndex());
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0036  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction declaration) {
            boolean z;
            IrMemberWithContainerSource irMemberWithContainerSource;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
            IrSimpleFunction irSimpleFunction = (correspondingPropertySymbol == null || (irMemberWithContainerSource = (IrProperty) correspondingPropertySymbol.getOwner()) == null) ? declaration : irMemberWithContainerSource;
            if (declaration.getDispatchReceiverParameter() == null) {
                IrDeclarationBase irDeclarationBase = (IrDeclarationBase) irSimpleFunction;
                if ((irDeclarationBase.getParent() instanceof IrPackageFragment) || IrUtilsKt.isFacadeClass(irDeclarationBase.getParent())) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            IrMangleComputer.this.mangleFunction(declaration, declaration.getName(), false, z, irSimpleFunction, null);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrMangleComputer.this.mangleFunction(declaration, declaration.getName(), true, false, declaration, null);
        }
    }
}
