package org.jetbrains.kotlin.backend.wasm.lower;

import androidx.core.view.PointerIconCompat;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.JsHelpersKt;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.TypeTransformerKt;
import org.jetbrains.kotlin.backend.wasm.utils.AnnotationsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001:\nTUVWXYZ[\\]B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/2\u000e\u00101\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"022\b\u00103\u001a\u0004\u0018\u00010\"H\u0002J\u0014\u00104\u001a\u00020/2\n\u00105\u001a\u000606R\u00020\u0000H\u0002J\u001c\u00107\u001a\u00020/2\n\u00105\u001a\u000606R\u00020\u00002\u0006\u00108\u001a\u00020/H\u0002J\u0014\u00109\u001a\u00020/2\n\u00105\u001a\u000606R\u00020\u0000H\u0002J\u0014\u0010:\u001a\u00020/2\n\u00105\u001a\u000606R\u00020\u0000H\u0002J$\u0010;\u001a\u0004\u0018\u00010\"2\u0006\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010\"H\u0002J$\u0010@\u001a\u0004\u0018\u00010\"2\u0006\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010\"H\u0002J\u0010\u0010A\u001a\u00020!2\u0006\u0010B\u001a\u00020!H\u0002J\u0014\u0010C\u001a\u00020D2\n\u0010E\u001a\u000606R\u00020\u0000H\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020!H\u0002J\u0010\u0010F\u001a\u00020D2\u0006\u0010E\u001a\u00020!H\u0002J\u0016\u0010G\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u0001022\u0006\u0010.\u001a\u00020/J\u0016\u0010H\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u0001022\u0006\u0010.\u001a\u00020/J\u0018\u0010I\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u0001022\u0006\u0010J\u001a\u00020\fH\u0016J\u001c\u0010K\u001a\u00020L*\u0004\u0018\u00010\"2\u0006\u0010M\u001a\u00020L2\u0006\u0010,\u001a\u00020NJ\u0016\u0010O\u001a\u0004\u0018\u00010\"*\u00020!2\u0006\u0010P\u001a\u00020>H\u0002J\u0016\u0010Q\u001a\u0004\u0018\u00010\"*\u00020!2\u0006\u0010P\u001a\u00020>H\u0002J\u0016\u0010R\u001a\u0004\u0018\u00010\"*\u00020!2\u0006\u0010P\u001a\u00020>H\u0002J\u0016\u0010S\u001a\u0004\u0018\u00010\"*\u00020!2\u0006\u0010P\u001a\u00020>H\u0002R\u0015\u0010\u0005\u001a\u00060\u0006R\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0015\u0010\u001b\u001a\u00060\u001cR\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR'\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"0 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b#\u0010$R\u0011\u0010'\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)¨\u0006^"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "adapters", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsInteropAdapters;", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getAdapters", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsInteropAdapters;", "additionalDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getAdditionalDeclarations", "()Ljava/util/List;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "currentParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getCurrentParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setCurrentParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "jsRelatedSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "getJsRelatedSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "primitivesToExternRefAdapters", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "getPrimitivesToExternRefAdapters", "()Ljava/util/Map;", "primitivesToExternRefAdapters$delegate", "Lkotlin/Lazy;", "symbols", "getSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "createAdapterFunctionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "functionToCall", "valueParametersAdapters", "", "resultAdapter", "createJsClosureCaller", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$FunctionTypeInfo;", "createJsToKotlinClosureConverter", "jsClosureCaller", "createKotlinClosureCaller", "createKotlinToJsClosureConvertor", "createNotNullAdapter", "notNullType", "isPrimitive", "", "valueAdapter", "createNullableAdapter", "doubleIfNumber", "possiblyNumber", "jsInteropNotNullTypeSignature", "", ModuleXmlParser.TYPE, "jsInteropTypeSignature", "transformExportFunction", "transformExternalFunction", "transformFlat", "declaration", "adaptIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "jsToKotlinAdapterIfNeeded", "isReturn", "jsToKotlinAdapterIfNeededNotNullable", "kotlinToJsAdapterIfNeeded", "kotlinToJsAdapterIfNeededNotNullable", "CheckNotNullAndAdapter", "CheckNotNullNoAdapter", "CombineAdapter", "CopyToJsArrayAdapter", "FunctionBasedAdapter", "FunctionTypeInfo", "InteropTypeAdapter", "NullOrAdapter", "ReceivingKotlinObjectFromJsAdapter", "SendKotlinObjectToJsAdapter", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInteropFunctionsLowering implements DeclarationTransformer {
    private final List<IrDeclaration> additionalDeclarations;
    private final IrBuiltIns builtIns;
    private final WasmBackendContext context;
    public IrDeclarationParent currentParent;

    /* JADX INFO: renamed from: primitivesToExternRefAdapters$delegate, reason: from kotlin metadata */
    private final Lazy primitivesToExternRefAdapters;
    private final WasmSymbols symbols;

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface InteropTypeAdapter {
        IrExpression adapt(IrExpression expression, IrBuilderWithScope builder);

        IrType getFromType();

        IrType getToType();
    }

    public JsInteropFunctionsLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.builtIns = context.getIrBuiltIns();
        this.symbols = context.getWasmSymbols();
        this.additionalDeclarations = new ArrayList();
        this.primitivesToExternRefAdapters = LazyKt.lazy(new Function0<Map<IrType, ? extends FunctionBasedAdapter>>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering$primitivesToExternRefAdapters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrType, ? extends JsInteropFunctionsLowering.FunctionBasedAdapter> invoke() {
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to(this.this$0.getBuiltIns().getByteType(), this.this$0.getAdapters().getKotlinByteToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getShortType(), this.this$0.getAdapters().getKotlinShortToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getCharType(), this.this$0.getAdapters().getKotlinCharToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getIntType(), this.this$0.getAdapters().getKotlinIntToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getLongType(), this.this$0.getAdapters().getKotlinLongToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getFloatType(), this.this$0.getAdapters().getKotlinFloatToExternRefAdapter()), TuplesKt.to(this.this$0.getBuiltIns().getDoubleType(), this.this$0.getAdapters().getKotlinDoubleToExternRefAdapter()));
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapMapOf.size()));
                for (Map.Entry entry : mapMapOf.entrySet()) {
                    linkedHashMap.put(entry.getKey(), new JsInteropFunctionsLowering.FunctionBasedAdapter(((IrSimpleFunctionSymbol) entry.getValue()).getOwner()));
                }
                return linkedHashMap;
            }
        });
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final WasmSymbols getSymbols() {
        return this.symbols;
    }

    public final WasmSymbols.JsRelatedSymbols getJsRelatedSymbols() {
        return this.context.getWasmSymbols().getJsRelatedSymbols();
    }

    public final WasmSymbols.JsInteropAdapters getAdapters() {
        return getJsRelatedSymbols().getJsInteropAdapters();
    }

    public final List<IrDeclaration> getAdditionalDeclarations() {
        return this.additionalDeclarations;
    }

    public final IrDeclarationParent getCurrentParent() {
        IrDeclarationParent irDeclarationParent = this.currentParent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        Intrinsics.throwUninitializedPropertyAccessException("currentParent");
        return null;
    }

    public final void setCurrentParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.currentParent = irDeclarationParent;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        List<IrDeclaration> listTransformExportFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI || IrFakeOverrideUtilsKt.isFakeOverride(declaration) || !(declaration instanceof IrSimpleFunction)) {
            return null;
        }
        IrDeclaration irDeclaration = declaration;
        boolean zIsJsExport = AnnotationUtilsKt.isJsExport(irDeclaration);
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
        boolean z = irSimpleFunction.getIsExternal() || AnnotationsKt.getJsFunAnnotation(irDeclaration) != null;
        IrFunction irFunction = (IrFunction) declaration;
        if (IrDeclarationsKt.isPropertyAccessor(irFunction) || !(declaration.getParent() instanceof IrPackageFragment)) {
            return null;
        }
        if ((!zIsJsExport && !z) || AnnotationsKt.getWasmImportDescriptor(irFunction) != null) {
            return null;
        }
        if (zIsJsExport && z) {
            throw new IllegalStateException(("Exported external declarations are not supported: " + IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) declaration)).toString());
        }
        if (declaration.getParent() instanceof IrClass) {
            throw new IllegalStateException(("Interop members are not supported:  " + IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) declaration)).toString());
        }
        if (this.context.getMapping().getWasmNestedExternalToNewTopLevelFunction().getKeys().contains(declaration)) {
            return null;
        }
        this.additionalDeclarations.clear();
        setCurrentParent(declaration.getParent());
        if (z) {
            listTransformExportFunction = transformExternalFunction(irSimpleFunction);
        } else {
            listTransformExportFunction = transformExportFunction(irSimpleFunction);
        }
        if (listTransformExportFunction == null) {
            listTransformExportFunction = CollectionsKt.listOf(declaration);
        }
        return CollectionsKt.plus((Collection) listTransformExportFunction, (Iterable) this.additionalDeclarations);
    }

    private final IrType doubleIfNumber(IrType possiblyNumber) {
        boolean zIsNullable = IrTypeUtilsKt.isNullable(possiblyNumber);
        if (!Intrinsics.areEqual(IrTypesKt.makeNotNull(possiblyNumber), this.builtIns.getNumberType())) {
            return possiblyNumber;
        }
        IrType doubleType = this.builtIns.getDoubleType();
        return zIsNullable ? IrTypesKt.makeNullable(doubleType) : doubleType;
    }

    public final List<IrDeclaration> transformExternalFunction(IrSimpleFunction function) {
        CopyToJsArrayAdapter copyToJsArrayAdapterKotlinToJsAdapterIfNeeded;
        Intrinsics.checkNotNullParameter(function, "function");
        List<IrValueParameter> valueParameters = function.getValueParameters();
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                if (((IrValueParameter) it.next()).getDefaultValue() != null) {
                    return null;
                }
            }
        }
        function.setReturnType(doubleIfNumber(function.getReturnType()));
        List<IrValueParameter> valueParameters2 = function.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
        for (IrValueParameter irValueParameter : valueParameters2) {
            IrType varargElementType = irValueParameter.getVarargElementType();
            if (varargElementType != null) {
                copyToJsArrayAdapterKotlinToJsAdapterIfNeeded = new CopyToJsArrayAdapter(this, irValueParameter.getType(), varargElementType);
            } else {
                copyToJsArrayAdapterKotlinToJsAdapterIfNeeded = kotlinToJsAdapterIfNeeded(irValueParameter.getType(), false);
            }
            arrayList.add(copyToJsArrayAdapterKotlinToJsAdapterIfNeeded);
        }
        ArrayList arrayList2 = arrayList;
        InteropTypeAdapter interopTypeAdapterJsToKotlinAdapterIfNeeded = jsToKotlinAdapterIfNeeded(function.getReturnType(), true);
        if (interopTypeAdapterJsToKotlinAdapterIfNeeded == null) {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    if (((InteropTypeAdapter) it2.next()) == null) {
                    }
                }
            }
            return null;
        }
        IrFactory irFactory = this.context.getIrFactory();
        IrDeclarationParent parent = function.getParent();
        Name nameIdentifier = Name.identifier(function.getName().asStringStripSpecialMarkers() + "__externalAdapter");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrSimpleFunction irSimpleFunctionCreateStaticFunctionWithReceivers$default = IrUtilsKt.createStaticFunctionWithReceivers$default(irFactory, parent, nameIdentifier, function, null, null, null, null, false, false, null, new JsInteropFunctionsLowering$transformExternalFunction$newFun$1(this.context), PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, null);
        int i = 0;
        for (Object obj : function.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter2 = (IrValueParameter) obj;
            InteropTypeAdapter interopTypeAdapter = (InteropTypeAdapter) arrayList2.get(i);
            if (interopTypeAdapter != null) {
                irValueParameter2.setType(interopTypeAdapter.getToType());
            }
            i = i2;
        }
        if (interopTypeAdapterJsToKotlinAdapterIfNeeded != null) {
            function.setReturnType(interopTypeAdapterJsToKotlinAdapterIfNeeded.getFromType());
        }
        irSimpleFunctionCreateStaticFunctionWithReceivers$default.setBody(createAdapterFunctionBody(LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionCreateStaticFunctionWithReceivers$default.getSymbol(), 0, 0, 6, (Object) null), irSimpleFunctionCreateStaticFunctionWithReceivers$default, function, arrayList2, interopTypeAdapterJsToKotlinAdapterIfNeeded));
        irSimpleFunctionCreateStaticFunctionWithReceivers$default.setAnnotations(CollectionsKt.emptyList());
        this.context.getMapping().getWasmJsInteropFunctionToWrapper().set(function, irSimpleFunctionCreateStaticFunctionWithReceivers$default);
        return CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{function, irSimpleFunctionCreateStaticFunctionWithReceivers$default});
    }

    public final List<IrDeclaration> transformExportFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<IrValueParameter> valueParameters = function.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(jsToKotlinAdapterIfNeeded(((IrValueParameter) it.next()).getType(), false));
        }
        ArrayList arrayList2 = arrayList;
        InteropTypeAdapter interopTypeAdapterKotlinToJsAdapterIfNeeded = kotlinToJsAdapterIfNeeded(function.getReturnType(), true);
        if (interopTypeAdapterKotlinToJsAdapterIfNeeded == null) {
            ArrayList arrayList3 = arrayList2;
            if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                return null;
            }
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                if (((InteropTypeAdapter) it2.next()) == null) {
                }
            }
            return null;
        }
        IrFactory irFactory = this.context.getIrFactory();
        IrDeclarationParent parent = function.getParent();
        Name nameIdentifier = Name.identifier(function.getName().asStringStripSpecialMarkers() + "__JsExportAdapter");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrSimpleFunction irSimpleFunctionCreateStaticFunctionWithReceivers$default = IrUtilsKt.createStaticFunctionWithReceivers$default(irFactory, parent, nameIdentifier, function, null, null, null, null, false, false, null, new JsInteropFunctionsLowering$transformExportFunction$newFun$1(this.context), PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, null);
        int i = 0;
        for (Object obj : irSimpleFunctionCreateStaticFunctionWithReceivers$default.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            InteropTypeAdapter interopTypeAdapter = (InteropTypeAdapter) arrayList2.get(i);
            if (interopTypeAdapter != null) {
                irValueParameter.setType(interopTypeAdapter.getFromType());
            }
            i = i2;
        }
        if (interopTypeAdapterKotlinToJsAdapterIfNeeded != null) {
            irSimpleFunctionCreateStaticFunctionWithReceivers$default.setReturnType(interopTypeAdapterKotlinToJsAdapterIfNeeded.getToType());
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionCreateStaticFunctionWithReceivers$default.getSymbol(), 0, 0, 6, (Object) null);
        irSimpleFunctionCreateStaticFunctionWithReceivers$default.setBody(createAdapterFunctionBody(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionCreateStaticFunctionWithReceivers$default, function, arrayList2, interopTypeAdapterKotlinToJsAdapterIfNeeded));
        List<IrConstructorCall> annotations = irSimpleFunctionCreateStaticFunctionWithReceivers$default.getAnnotations();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder, getJsRelatedSymbols().getJsNameConstructor(), CollectionsKt.emptyList());
        String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(function).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, identifier));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionCreateStaticFunctionWithReceivers$default.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, irConstructorCallIrCallConstructor));
        List<IrConstructorCall> annotations2 = function.getAnnotations();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : annotations2) {
            if (!Intrinsics.areEqual(((IrConstructorCall) obj2).getSymbol(), getJsRelatedSymbols().getJsExportConstructor())) {
                arrayList4.add(obj2);
            }
        }
        function.setAnnotations(arrayList4);
        return CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{function, irSimpleFunctionCreateStaticFunctionWithReceivers$default});
    }

    private final IrBlockBody createAdapterFunctionBody(DeclarationIrBuilder builder, IrSimpleFunction function, IrSimpleFunction functionToCall, List<? extends InteropTypeAdapter> valueParametersAdapters, InteropTypeAdapter resultAdapter) {
        DeclarationIrBuilder declarationIrBuilder = builder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, functionToCall);
        Iterator<IrValueParameter> it = function.getValueParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            irFunctionAccessExpressionIrCall.putValueArgument(i, adaptIfNeeded(valueParametersAdapters.get(i), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, it.next()), declarationIrBuilder));
            i++;
        }
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, adaptIfNeeded(resultAdapter, irFunctionAccessExpressionIrCall, declarationIrBuilder)));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    public final Map<IrType, InteropTypeAdapter> getPrimitivesToExternRefAdapters() {
        return (Map) this.primitivesToExternRefAdapters.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final InteropTypeAdapter kotlinToJsAdapterIfNeeded(IrType irType, boolean z) {
        if ((z && Intrinsics.areEqual(irType, this.builtIns.getUnitType())) || Intrinsics.areEqual(irType, this.builtIns.getNothingType())) {
            return null;
        }
        if (!IrTypeUtilsKt.isNullable(irType)) {
            return kotlinToJsAdapterIfNeededNotNullable(irType, z);
        }
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
        if (Intrinsics.areEqual(irTypeMakeNotNull, this.builtIns.getNumberType())) {
            return new NullOrAdapter(this, new CombineAdapter(new FunctionBasedAdapter(getAdapters().getKotlinDoubleToExternRefAdapter().getOwner()), new FunctionBasedAdapter(getAdapters().getNumberToDoubleAdapter().getOwner())));
        }
        InteropTypeAdapter interopTypeAdapterKotlinToJsAdapterIfNeededNotNullable = getPrimitivesToExternRefAdapters().get(irTypeMakeNotNull);
        if (interopTypeAdapterKotlinToJsAdapterIfNeededNotNullable == null && (interopTypeAdapterKotlinToJsAdapterIfNeededNotNullable = kotlinToJsAdapterIfNeededNotNullable(irTypeMakeNotNull, z)) == null) {
            return null;
        }
        return new NullOrAdapter(this, interopTypeAdapterKotlinToJsAdapterIfNeededNotNullable);
    }

    private final InteropTypeAdapter kotlinToJsAdapterIfNeededNotNullable(IrType irType, boolean z) {
        if ((z && Intrinsics.areEqual(irType, this.builtIns.getUnitType())) || Intrinsics.areEqual(irType, this.builtIns.getNothingType())) {
            return null;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getStringType())) {
            return new FunctionBasedAdapter(getAdapters().getKotlinToJsStringAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getBooleanType())) {
            return new FunctionBasedAdapter(getAdapters().getKotlinBooleanToExternRefAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getAnyType())) {
            return new FunctionBasedAdapter(getAdapters().getKotlinToJsAnyAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getNumberType())) {
            return new FunctionBasedAdapter(getAdapters().getNumberToDoubleAdapter().getOwner());
        }
        if ((Intrinsics.areEqual(irType, this.builtIns.getByteType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getShortType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getCharType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getIntType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getLongType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getFloatType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getDoubleType()) ? true : Intrinsics.areEqual(irType, this.context.getWasmSymbols().getVoidType())) || TypeTransformerKt.isExternalType(irType) || TypeTransformerKt.isBuiltInWasmRefType(irType)) {
            return null;
        }
        if ((irType instanceof IrSimpleType) && org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(irType)) {
            FunctionTypeInfo functionTypeInfo = new FunctionTypeInfo(this, (IrSimpleType) irType, true);
            Map<String, IrSimpleFunction> closureCallExports = this.context.getClosureCallExports();
            String signatureString = functionTypeInfo.getSignatureString();
            if (closureCallExports.get(signatureString) == null) {
                closureCallExports.put(signatureString, createKotlinClosureCaller(functionTypeInfo));
            }
            Map<String, IrSimpleFunction> kotlinClosureToJsConverters = this.context.getKotlinClosureToJsConverters();
            String signatureString2 = functionTypeInfo.getSignatureString();
            IrSimpleFunction irSimpleFunctionCreateKotlinToJsClosureConvertor = kotlinClosureToJsConverters.get(signatureString2);
            if (irSimpleFunctionCreateKotlinToJsClosureConvertor == null) {
                irSimpleFunctionCreateKotlinToJsClosureConvertor = createKotlinToJsClosureConvertor(functionTypeInfo);
                kotlinClosureToJsConverters.put(signatureString2, irSimpleFunctionCreateKotlinToJsClosureConvertor);
            }
            return new FunctionBasedAdapter(irSimpleFunctionCreateKotlinToJsClosureConvertor);
        }
        return new SendKotlinObjectToJsAdapter(this, irType);
    }

    private final InteropTypeAdapter createNullableAdapter(IrType notNullType, boolean isPrimitive, InteropTypeAdapter valueAdapter) {
        IrSimpleFunction owner;
        if (isPrimitive) {
            if (Intrinsics.areEqual(notNullType, this.builtIns.getFloatType())) {
                owner = getAdapters().getExternRefToKotlinFloatAdapter().getOwner();
            } else if (Intrinsics.areEqual(notNullType, this.builtIns.getDoubleType())) {
                owner = getAdapters().getExternRefToKotlinDoubleAdapter().getOwner();
            } else if (Intrinsics.areEqual(notNullType, this.builtIns.getLongType())) {
                owner = getAdapters().getExternRefToKotlinLongAdapter().getOwner();
            } else {
                owner = Intrinsics.areEqual(notNullType, this.builtIns.getBooleanType()) ? getAdapters().getExternRefToKotlinBooleanAdapter().getOwner() : getAdapters().getExternRefToKotlinIntAdapter().getOwner();
            }
            FunctionBasedAdapter functionBasedAdapter = new FunctionBasedAdapter(owner);
            return new NullOrAdapter(this, valueAdapter != null ? new CombineAdapter(valueAdapter, functionBasedAdapter) : functionBasedAdapter);
        }
        NullOrAdapter nullOrAdapter = valueAdapter != null ? new NullOrAdapter(this, valueAdapter) : null;
        FunctionBasedAdapter functionBasedAdapter2 = new FunctionBasedAdapter(getAdapters().getJsCheckIsNullOrUndefinedAdapter().getOwner());
        if (nullOrAdapter != null) {
            return new CombineAdapter(nullOrAdapter, functionBasedAdapter2);
        }
        return functionBasedAdapter2;
    }

    private final InteropTypeAdapter createNotNullAdapter(IrType notNullType, boolean isPrimitive, InteropTypeAdapter valueAdapter) {
        CheckNotNullNoAdapter checkNotNullNoAdapter;
        if (isPrimitive) {
            return valueAdapter;
        }
        if (valueAdapter != null) {
            checkNotNullNoAdapter = new CheckNotNullAndAdapter(this, valueAdapter);
        } else {
            checkNotNullNoAdapter = new CheckNotNullNoAdapter(this, notNullType);
        }
        return !TypeTransformerKt.isExternalType(notNullType) ? checkNotNullNoAdapter : new CombineAdapter(checkNotNullNoAdapter, new FunctionBasedAdapter(getAdapters().getJsCheckIsNullOrUndefinedAdapter().getOwner()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final InteropTypeAdapter jsToKotlinAdapterIfNeeded(IrType irType, boolean z) {
        IrType fromType;
        if (z && Intrinsics.areEqual(irType, this.builtIns.getUnitType())) {
            return null;
        }
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
        InteropTypeAdapter interopTypeAdapterJsToKotlinAdapterIfNeededNotNullable = jsToKotlinAdapterIfNeededNotNullable(irTypeMakeNotNull, z);
        boolean zIsPrimitiveType$default = (interopTypeAdapterJsToKotlinAdapterIfNeededNotNullable == null || (fromType = interopTypeAdapterJsToKotlinAdapterIfNeededNotNullable.getFromType()) == null) ? IrTypePredicatesKt.isPrimitiveType$default(irTypeMakeNotNull, false, 1, null) : IrTypePredicatesKt.isPrimitiveType$default(fromType, false, 1, null);
        if (IrTypeUtilsKt.isNullable(irType)) {
            return createNullableAdapter(irTypeMakeNotNull, zIsPrimitiveType$default, interopTypeAdapterJsToKotlinAdapterIfNeededNotNullable);
        }
        return createNotNullAdapter(irTypeMakeNotNull, zIsPrimitiveType$default, interopTypeAdapterJsToKotlinAdapterIfNeededNotNullable);
    }

    private final InteropTypeAdapter jsToKotlinAdapterIfNeededNotNullable(IrType irType, boolean z) {
        if (z && (Intrinsics.areEqual(irType, this.builtIns.getUnitType()) || Intrinsics.areEqual(irType, this.builtIns.getNothingType()))) {
            return null;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getStringType())) {
            return new FunctionBasedAdapter(getAdapters().getJsToKotlinStringAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getAnyType())) {
            return new FunctionBasedAdapter(getAdapters().getJsToKotlinAnyAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getByteType())) {
            return new FunctionBasedAdapter(getAdapters().getJsToKotlinByteAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getShortType())) {
            return new FunctionBasedAdapter(getAdapters().getJsToKotlinShortAdapter().getOwner());
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getCharType())) {
            return new FunctionBasedAdapter(getAdapters().getJsToKotlinCharAdapter().getOwner());
        }
        if ((Intrinsics.areEqual(irType, this.builtIns.getBooleanType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getIntType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getLongType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getFloatType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getDoubleType()) ? true : Intrinsics.areEqual(irType, this.context.getWasmSymbols().getVoidType())) || TypeTransformerKt.isExternalType(irType) || TypeTransformerKt.isBuiltInWasmRefType(irType)) {
            return null;
        }
        if ((irType instanceof IrSimpleType) && org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(irType)) {
            FunctionTypeInfo functionTypeInfo = new FunctionTypeInfo(this, (IrSimpleType) irType, false);
            Map<String, IrSimpleFunction> jsClosureCallers = this.context.getJsClosureCallers();
            String signatureString = functionTypeInfo.getSignatureString();
            IrSimpleFunction irSimpleFunctionCreateJsClosureCaller = jsClosureCallers.get(signatureString);
            if (irSimpleFunctionCreateJsClosureCaller == null) {
                irSimpleFunctionCreateJsClosureCaller = createJsClosureCaller(functionTypeInfo);
                jsClosureCallers.put(signatureString, irSimpleFunctionCreateJsClosureCaller);
            }
            IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateJsClosureCaller;
            Map<String, IrSimpleFunction> jsToKotlinClosures = this.context.getJsToKotlinClosures();
            String signatureString2 = functionTypeInfo.getSignatureString();
            IrSimpleFunction irSimpleFunctionCreateJsToKotlinClosureConverter = jsToKotlinClosures.get(signatureString2);
            if (irSimpleFunctionCreateJsToKotlinClosureConverter == null) {
                irSimpleFunctionCreateJsToKotlinClosureConverter = createJsToKotlinClosureConverter(functionTypeInfo, irSimpleFunction);
                jsToKotlinClosures.put(signatureString2, irSimpleFunctionCreateJsToKotlinClosureConverter);
            }
            return new FunctionBasedAdapter(irSimpleFunctionCreateJsToKotlinClosureConverter);
        }
        return new ReceivingKotlinObjectFromJsAdapter(this, irType);
    }

    private final IrSimpleFunction createKotlinClosureCaller(FunctionTypeInfo info) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("__callFunction_" + info.getSignatureString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(info.getAdaptedResultType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(getCurrentParent());
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("f");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(this.context.getWasmSymbols().getWasmStructRefType());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction2)));
        int i = 0;
        int i2 = 0;
        for (IrType irType : info.getAdaptedParameterTypes()) {
            IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
            int i3 = i2 + 1;
            Name nameIdentifier3 = Name.identifier("p" + i2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            irValueParameterBuilder2.setName(nameIdentifier3);
            irValueParameterBuilder2.setType(irType);
            if (irValueParameterBuilder2.getIndex() == -1) {
                irValueParameterBuilder2.setIndex(irSimpleFunction.getValueParameters().size());
            }
            irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder2, irSimpleFunction2)));
            i2 = i3;
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(info.getFunctionType());
        Intrinsics.checkNotNull(classOrNull);
        IrSimpleFunction irSimpleFunction3 = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction4 : IrUtilsKt.getFunctions(classOrNull.getOwner())) {
            if (Intrinsics.areEqual(irSimpleFunction4.getName(), Name.identifier("invoke"))) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction3 = irSimpleFunction4;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction5 = irSimpleFunction3;
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder2, irSimpleFunction5.getSymbol(), info.getOriginalResultType(), 0, 0, null, 28, null);
            IrValueParameter dispatchReceiverParameter = irSimpleFunction5.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            irCallIrCall$default.setDispatchReceiver(new ReceivingKotlinObjectFromJsAdapter(this, dispatchReceiverParameter.getType()).adapt(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irSimpleFunctionBuildFunction.getValueParameters().get(0)), declarationIrBuilderCreateIrBuilder$default));
            int size = info.getAdaptedParameterTypes().size();
            while (i < size) {
                int i4 = i + 1;
                irCallIrCall$default.putValueArgument(i, adaptIfNeeded(info.getParametersAdapters().get(i), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irSimpleFunctionBuildFunction.getValueParameters().get(i4)), declarationIrBuilderCreateIrBuilder$default));
                i = i4;
            }
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, adaptIfNeeded(info.getResultAdapter(), irCallIrCall$default, declarationIrBuilderCreateIrBuilder$default)));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
            irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) irSimpleFunctionBuildFunction.getAnnotations(), ExpressionHelpersKt.irCallConstructor(declarationIrBuilderCreateIrBuilder$default, getJsRelatedSymbols().getJsExportConstructor(), CollectionsKt.emptyList())));
            this.additionalDeclarations.add(irSimpleFunctionBuildFunction);
            return irSimpleFunctionBuildFunction;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrSimpleFunction createKotlinToJsClosureConvertor(FunctionTypeInfo info) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("__convertKotlinClosureToJsClosure_" + info.getSignatureString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(getJsRelatedSymbols().getJsAnyType());
        irFunctionBuilder.setExternal(true);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(getCurrentParent());
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("f");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(this.context.getWasmSymbols().getWasmStructRefType());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
        }
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        int size = info.getParametersAdapters().size();
        StringBuilder sb = new StringBuilder();
        sb.append("(f) => (");
        JsInteropFunctionsLoweringKt.appendParameterList$default(sb, size, null, false, 6, null);
        sb.append(") => wasmExports[");
        sb.append(JsHelpersKt.toJsStringLiteral("__callFunction_" + info.getSignatureString()));
        sb.append("](f, ");
        JsInteropFunctionsLoweringKt.appendParameterList$default(sb, size, null, false, 6, null);
        sb.append(Constant.AFTER_QUTO);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        List<IrConstructorCall> annotations = irSimpleFunctionBuildFunction.getAnnotations();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder, getJsRelatedSymbols().getJsFunConstructor(), CollectionsKt.emptyList());
        irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, string));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, irConstructorCallIrCallConstructor));
        this.additionalDeclarations.add(irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction createJsToKotlinClosureConverter(FunctionTypeInfo info, IrSimpleFunction jsClosureCaller) {
        IrSimpleType functionType = info.getFunctionType();
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("__convertJsClosureToKotlinClosure_" + info.getSignatureString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        IrSimpleType irSimpleType = functionType;
        irFunctionBuilder.setReturnType(irSimpleType);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(getCurrentParent());
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("f");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(getJsRelatedSymbols().getJsAnyType());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
        }
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
        IrFactory irFactory2 = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameIdentifier3 = Name.identifier("__JsClosureToKotlinClosure_" + info.getSignatureString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        irClassBuilder.setName(nameIdentifier3);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory2, irClassBuilder);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        irClassBuildClass.setSuperTypes(CollectionsKt.listOf(functionType));
        irClassBuildClass.setParent(getCurrentParent());
        IrFactory factory = irClassBuildClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        Name nameIdentifier4 = Name.identifier("jsClosure");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier4);
        irFieldBuilder.setType(getJsRelatedSymbols().getJsAnyType());
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFieldBuilder.setVisibility(PRIVATE);
        irFieldBuilder.setFinal(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        IrClass irClass = irClassBuildClass;
        irFieldBuildField.setParent(irClass);
        irClassBuildClass.getDeclarations().add(irFieldBuildField);
        IrFactory factory2 = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
        irFunctionBuilder2.setPrimary(true);
        irFunctionBuilder2.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder2);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        IrConstructor irConstructor = irConstructorBuildConstructor;
        IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
        irValueParameterBuilder2.setName(irFieldBuildField.getName());
        irValueParameterBuilder2.setType(irFieldBuildField.getType());
        if (irValueParameterBuilder2.getIndex() == -1) {
            irValueParameterBuilder2.setIndex(irConstructor.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irConstructor.getFactory(), irValueParameterBuilder2, irConstructor);
        irConstructor.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irConstructor.getValueParameters(), irValueParameterBuildValueParameter));
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irConstructorBuildConstructor.getStartOffset(), irConstructorBuildConstructor.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyClass().getOwner()))));
        IrValueParameter thisReceiver = irClassBuildClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver), irFieldBuildField, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterBuildValueParameter), null, 8, null));
        irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), irClassBuildClass.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
        irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        IrFactory factory3 = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder3 = new IrFunctionBuilder();
        Name nameIdentifier5 = Name.identifier("invoke");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        irFunctionBuilder3.setName(nameIdentifier5);
        irFunctionBuilder3.setReturnType(info.getOriginalResultType());
        IrSimpleFunction irSimpleFunctionBuildFunction2 = DeclarationBuildersKt.buildFunction(factory3, irFunctionBuilder3);
        IrClass irClass2 = irClassBuildClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction2);
        irSimpleFunctionBuildFunction2.setParent(irClass2);
        IrValueParameterBuilder irValueParameterBuilder3 = new IrValueParameterBuilder();
        irValueParameterBuilder3.setType(IrUtilsKt.getDefaultType(irClassBuildClass));
        irValueParameterBuilder3.setIndex(-1);
        irValueParameterBuilder3.setName(DescriptorUtilsKt.getSynthesizedName("this"));
        irSimpleFunctionBuildFunction2.setDispatchReceiverParameter(DeclarationBuildersKt.buildValueParameter(irSimpleFunctionBuildFunction2.getFactory(), irValueParameterBuilder3, irSimpleFunctionBuildFunction2));
        Iterator it = info.getOriginalParameterTypes().iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction2;
            IrValueParameterBuilder irValueParameterBuilder4 = new IrValueParameterBuilder();
            Iterator it2 = it;
            Name nameIdentifier6 = Name.identifier("p" + i);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
            irValueParameterBuilder4.setName(nameIdentifier6);
            irValueParameterBuilder4.setType((IrType) next);
            if (irValueParameterBuilder4.getIndex() == -1) {
                irValueParameterBuilder4.setIndex(irSimpleFunction2.getValueParameters().size());
            }
            irSimpleFunction2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction2.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder4, irSimpleFunction2)));
            i = i2;
            it = it2;
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction2.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder3 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default2.getContext(), declarationIrBuilderCreateIrBuilder$default2.getScope(), declarationIrBuilderCreateIrBuilder$default2.getStartOffset(), declarationIrBuilderCreateIrBuilder$default2.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder3;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder4, jsClosureCaller);
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction2.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder4, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, dispatchReceiverParameter), irFieldBuildField, null, 4, null));
        Iterator<InteropTypeAdapter> it3 = info.getParametersAdapters().iterator();
        int i3 = 0;
        while (it3.hasNext()) {
            int i4 = i3 + 1;
            irFunctionAccessExpressionIrCall.putValueArgument(i4, adaptIfNeeded(it3.next(), ExpressionHelpersKt.irGet(irBlockBodyBuilder4, irSimpleFunctionBuildFunction2.getValueParameters().get(i3)), declarationIrBuilderCreateIrBuilder$default2));
            i3 = i4;
        }
        irBlockBodyBuilder3.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder4, adaptIfNeeded(info.getResultAdapter(), irFunctionAccessExpressionIrCall, declarationIrBuilderCreateIrBuilder$default2)));
        irSimpleFunctionBuildFunction2.setBody(irBlockBodyBuilder3.getIrBlockBody());
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunctionBuildFunction2.getOverriddenSymbols();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleType);
        Intrinsics.checkNotNull(classOrNull);
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(classOrNull)) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), Name.identifier("invoke"))) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                z = true;
            }
        }
        if (z) {
            irSimpleFunctionBuildFunction2.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) overriddenSymbols, irSimpleFunctionSymbol));
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default3 = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            IrBlockBodyBuilder irBlockBodyBuilder5 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default3.getContext(), declarationIrBuilderCreateIrBuilder$default3.getScope(), declarationIrBuilderCreateIrBuilder$default3.getStartOffset(), declarationIrBuilderCreateIrBuilder$default3.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder6 = irBlockBodyBuilder5;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBodyBuilder6, irConstructor);
            irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder6, irSimpleFunctionBuildFunction.getValueParameters().get(0)));
            Unit unit = Unit.INSTANCE;
            irBlockBodyBuilder5.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder6, irFunctionAccessExpressionIrCall2));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder5.getIrBlockBody());
            this.additionalDeclarations.add(irClassBuildClass);
            this.additionalDeclarations.add(irSimpleFunctionBuildFunction);
            return irSimpleFunctionBuildFunction;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrSimpleFunction createJsClosureCaller(FunctionTypeInfo info) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("__callJsClosure_" + info.getSignatureString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(info.getAdaptedResultType());
        irFunctionBuilder.setExternal(true);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(getCurrentParent());
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("f");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(getJsRelatedSymbols().getJsAnyType());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction2)));
        int size = info.getAdaptedParameterTypes().size();
        for (int i = 0; i < size; i++) {
            IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
            Name nameIdentifier3 = Name.identifier("p" + i);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            irValueParameterBuilder2.setName(nameIdentifier3);
            irValueParameterBuilder2.setType(info.getAdaptedParameterTypes().get(i));
            if (irValueParameterBuilder2.getIndex() == -1) {
                irValueParameterBuilder2.setIndex(irSimpleFunction.getValueParameters().size());
            }
            irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder2, irSimpleFunction2)));
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        StringBuilder sb = new StringBuilder();
        sb.append("(f, ");
        JsInteropFunctionsLoweringKt.appendParameterList$default(sb, size, null, false, 6, null);
        sb.append(") => f(");
        JsInteropFunctionsLoweringKt.appendParameterList$default(sb, size, null, false, 6, null);
        sb.append(Constant.AFTER_QUTO);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        List<IrConstructorCall> annotations = irSimpleFunctionBuildFunction.getAnnotations();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder, getJsRelatedSymbols().getJsFunConstructor(), CollectionsKt.emptyList());
        irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, string));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, irConstructorCallIrCallConstructor));
        this.additionalDeclarations.add(irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000bR\u0011\u0010\u0013\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000bR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$FunctionTypeInfo;", "", "functionType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "toJs", "", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/ir/types/IrSimpleType;Z)V", "adaptedParameterTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getAdaptedParameterTypes", "()Ljava/util/List;", "adaptedResultType", "getAdaptedResultType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getFunctionType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "originalParameterTypes", "getOriginalParameterTypes", "originalResultType", "getOriginalResultType", "parametersAdapters", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "getParametersAdapters", "resultAdapter", "getResultAdapter", "()Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "signatureString", "", "getSignatureString", "()Ljava/lang/String;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FunctionTypeInfo {
        private final List<IrType> adaptedParameterTypes;
        private final IrType adaptedResultType;
        private final IrSimpleType functionType;
        private final List<IrType> originalParameterTypes;
        private final IrType originalResultType;
        private final List<InteropTypeAdapter> parametersAdapters;
        private final InteropTypeAdapter resultAdapter;
        private final String signatureString;
        final /* synthetic */ JsInteropFunctionsLowering this$0;

        /* JADX WARN: Code duplicated, block: B:40:0x0132  */
        public FunctionTypeInfo(JsInteropFunctionsLowering jsInteropFunctionsLowering, IrSimpleType functionType, boolean z) {
            IrType toType;
            Intrinsics.checkNotNullParameter(functionType, "functionType");
            this.this$0 = jsInteropFunctionsLowering;
            this.functionType = functionType;
            List<IrTypeArgument> arguments = functionType.getArguments();
            IrType fromType = null;
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    if (!(((IrTypeArgument) it.next()) instanceof IrTypeProjection)) {
                        throw new IllegalArgumentException(("Star projection is not supported in function type interop " + RenderIrElementKt.render$default(this.functionType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                }
            }
            List<IrTypeArgument> listDropLast = CollectionsKt.dropLast(this.functionType.getArguments(), 1);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
            for (IrTypeArgument irTypeArgument : listDropLast) {
                Intrinsics.checkNotNull(irTypeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
                arrayList.add(((IrTypeProjection) irTypeArgument).getType());
            }
            ArrayList arrayList2 = arrayList;
            this.originalParameterTypes = arrayList2;
            Object objLast = CollectionsKt.last((List<? extends Object>) this.functionType.getArguments());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
            this.originalResultType = ((IrTypeProjection) objLast).getType();
            ArrayList<IrType> arrayList3 = arrayList2;
            JsInteropFunctionsLowering jsInteropFunctionsLowering2 = this.this$0;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            for (IrType irType : arrayList3) {
                arrayList4.add(z ? jsInteropFunctionsLowering2.jsToKotlinAdapterIfNeeded(irType, false) : jsInteropFunctionsLowering2.kotlinToJsAdapterIfNeeded(irType, false));
            }
            ArrayList arrayList5 = arrayList4;
            this.parametersAdapters = arrayList5;
            this.resultAdapter = z ? this.this$0.kotlinToJsAdapterIfNeeded(this.originalResultType, true) : this.this$0.jsToKotlinAdapterIfNeeded(this.originalResultType, true);
            List<Pair> listZip = CollectionsKt.zip(this.originalParameterTypes, arrayList5);
            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                IrType irType2 = (IrType) pair.component1();
                InteropTypeAdapter interopTypeAdapter = (InteropTypeAdapter) pair.component2();
                if (z) {
                    if (interopTypeAdapter != null) {
                        toType = interopTypeAdapter.getFromType();
                    } else {
                        toType = null;
                    }
                } else if (interopTypeAdapter != null) {
                    toType = interopTypeAdapter.getToType();
                } else {
                    toType = null;
                }
                if (toType != null) {
                    irType2 = toType;
                }
                arrayList6.add(irType2);
            }
            this.adaptedParameterTypes = arrayList6;
            InteropTypeAdapter interopTypeAdapter2 = this.resultAdapter;
            if (z) {
                if (interopTypeAdapter2 != null) {
                    fromType = interopTypeAdapter2.getToType();
                }
            } else if (interopTypeAdapter2 != null) {
                fromType = interopTypeAdapter2.getFromType();
            }
            this.adaptedResultType = fromType == null ? this.originalResultType : fromType;
            this.signatureString = this.this$0.jsInteropNotNullTypeSignature(this);
        }

        public final IrSimpleType getFunctionType() {
            return this.functionType;
        }

        public final List<IrType> getOriginalParameterTypes() {
            return this.originalParameterTypes;
        }

        public final IrType getOriginalResultType() {
            return this.originalResultType;
        }

        public final List<InteropTypeAdapter> getParametersAdapters() {
            return this.parametersAdapters;
        }

        public final InteropTypeAdapter getResultAdapter() {
            return this.resultAdapter;
        }

        public final List<IrType> getAdaptedParameterTypes() {
            return this.adaptedParameterTypes;
        }

        public final IrType getAdaptedResultType() {
            return this.adaptedResultType;
        }

        public final String getSignatureString() {
            return this.signatureString;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String jsInteropNotNullTypeSignature(FunctionTypeInfo type) {
        return "((" + CollectionsKt.joinToString$default(type.getOriginalParameterTypes(), ",", null, null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering$jsInteropNotNullTypeSignature$parameterTypes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.jsInteropTypeSignature(it);
            }
        }, 30, null) + ")->" + jsInteropTypeSignature(type.getOriginalResultType()) + ')';
    }

    private final String jsInteropNotNullTypeSignature(IrType type) {
        if (TypeTransformerKt.isExternalType(type)) {
            return "Js";
        }
        if (!(type instanceof IrSimpleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(type)) {
            return jsInteropNotNullTypeSignature(new FunctionTypeInfo(this, (IrSimpleType) type, true));
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        if (classOrNull == null) {
            throw new IllegalStateException(("Unsupported JS interop type: " + RenderIrElementKt.render$default((IrSimpleType) type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (Intrinsics.areEqual(AdditionalIrUtilsKt.getPackageFqName(classOrNull.getOwner()), new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME))) {
            String identifier = classOrNull.getOwner().getName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            return identifier;
        }
        throw new IllegalStateException(("Unsupported JS interop type: " + RenderIrElementKt.render$default((IrSimpleType) type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String jsInteropTypeSignature(IrType type) {
        if (IrTypeUtilsKt.isNullable(type)) {
            return jsInteropNotNullTypeSignature(IrTypesKt.makeNotNull(type)) + MangleConstant.Q_MARK;
        }
        return jsInteropNotNullTypeSignature(type);
    }

    public final IrExpression adaptIfNeeded(InteropTypeAdapter interopTypeAdapter, IrExpression expression, IrBuilderWithScope builder) {
        IrExpression irExpressionAdapt;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return (interopTypeAdapter == null || (irExpressionAdapt = interopTypeAdapter.adapt(expression, builder)) == null) ? expression : irExpressionAdapt;
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$FunctionBasedAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FunctionBasedAdapter implements InteropTypeAdapter {
        private final IrType fromType;
        private final IrSimpleFunction function;
        private final IrType toType;

        public FunctionBasedAdapter(IrSimpleFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            this.function = function;
            this.fromType = function.getValueParameters().get(0).getType();
            this.toType = function.getReturnType();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(builder, this.function);
            irFunctionAccessExpressionIrCall.putValueArgument(0, expression);
            return irFunctionAccessExpressionIrCall;
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$CombineAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "outerAdapter", "innerAdapter", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;)V", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CombineAdapter implements InteropTypeAdapter {
        private final IrType fromType;
        private final InteropTypeAdapter innerAdapter;
        private final InteropTypeAdapter outerAdapter;
        private final IrType toType;

        public CombineAdapter(InteropTypeAdapter outerAdapter, InteropTypeAdapter innerAdapter) {
            Intrinsics.checkNotNullParameter(outerAdapter, "outerAdapter");
            Intrinsics.checkNotNullParameter(innerAdapter, "innerAdapter");
            this.outerAdapter = outerAdapter;
            this.innerAdapter = innerAdapter;
            this.fromType = innerAdapter.getFromType();
            this.toType = outerAdapter.getToType();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            return this.outerAdapter.adapt(this.innerAdapter.adapt(expression, builder), builder);
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$SendKotlinObjectToJsAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class SendKotlinObjectToJsAdapter implements InteropTypeAdapter {
        private final IrType fromType;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public SendKotlinObjectToJsAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, IrType fromType) {
            Intrinsics.checkNotNullParameter(fromType, "fromType");
            this.this$0 = jsInteropFunctionsLowering;
            this.fromType = fromType;
            this.toType = jsInteropFunctionsLowering.getContext().getWasmSymbols().getWasmStructRefType();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            return ExpressionHelpersKt.irReinterpretCast(builder, expression, getToType());
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$ReceivingKotlinObjectFromJsAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "toType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "fromType", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ReceivingKotlinObjectFromJsAdapter implements InteropTypeAdapter {
        private final IrType fromType;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public ReceivingKotlinObjectFromJsAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, IrType toType) {
            Intrinsics.checkNotNullParameter(toType, "toType");
            this.this$0 = jsInteropFunctionsLowering;
            this.toType = toType;
            this.fromType = jsInteropFunctionsLowering.getContext().getWasmSymbols().getWasmStructRefType();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            IrCall irCall = ExpressionHelpersKt.irCall(builder, this.this$0.getContext().getWasmSymbols().getRefCastNull());
            irCall.putValueArgument(0, expression);
            irCall.putTypeArgument(0, getToType());
            return irCall;
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$CheckNotNullNoAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "fromType", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class CheckNotNullNoAdapter implements InteropTypeAdapter {
        private final IrType fromType;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public CheckNotNullNoAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.this$0 = jsInteropFunctionsLowering;
            this.fromType = IrTypesKt.makeNullable(type);
            this.toType = IrTypesKt.makeNotNull(type);
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            JsInteropFunctionsLowering jsInteropFunctionsLowering = this.this$0;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, null, true);
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, expression, null, null, false, null, 30, null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrVariable irVariable = irVariableIrTemporary$default;
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, getToType(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable), ExpressionHelpersKt.irCall(builder, jsInteropFunctionsLowering.getSymbols().getThrowNullPointerException()), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable)));
            return irBlockBuilder.getIrBlockBody();
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$NullOrAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "adapter", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;)V", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class NullOrAdapter implements InteropTypeAdapter {
        private final InteropTypeAdapter adapter;
        private final IrType fromType;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public NullOrAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, InteropTypeAdapter adapter) {
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            this.this$0 = jsInteropFunctionsLowering;
            this.adapter = adapter;
            this.fromType = IrTypesKt.makeNullable(adapter.getFromType());
            this.toType = IrTypesKt.makeNullable(adapter.getToType());
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, null, true);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, expression, null, null, false, null, 30, null);
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, getToType(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default), ExpressionHelpersKt.irNull(irBlockBuilder2, getToType()), ExpressionHelpersKt.irImplicitCast(irBlockBuilder2, this.adapter.adapt(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default), builder), getToType())));
            return irBlockBuilder.getIrBlockBody();
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$CheckNotNullAndAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "adapter", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;)V", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class CheckNotNullAndAdapter implements InteropTypeAdapter {
        private final InteropTypeAdapter adapter;
        private final IrType fromType;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public CheckNotNullAndAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, InteropTypeAdapter adapter) {
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            this.this$0 = jsInteropFunctionsLowering;
            this.adapter = adapter;
            this.fromType = IrTypesKt.makeNullable(adapter.getFromType());
            this.toType = adapter.getToType();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            JsInteropFunctionsLowering jsInteropFunctionsLowering = this.this$0;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, null, true);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, expression, null, null, false, null, 30, null);
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, getToType(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default), ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, jsInteropFunctionsLowering.getContext().getWasmSymbols().getThrowNullPointerException()), this.adapter.adapt(ExpressionHelpersKt.irImplicitCast(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default), IrTypesKt.makeNotNull(this.adapter.getFromType())), builder)));
            return irBlockBuilder.getIrBlockBody();
        }
    }

    /* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$CopyToJsArrayAdapter;", "Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering$InteropTypeAdapter;", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "fromElementType", "(Lorg/jetbrains/kotlin/backend/wasm/lower/JsInteropFunctionsLowering;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "arrayClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "elementAdapter", "getFromType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "sizeMethod", "toType", "getToType", "adapt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class CopyToJsArrayAdapter implements InteropTypeAdapter {
        private final IrClassSymbol arrayClass;
        private final InteropTypeAdapter elementAdapter;
        private final IrType fromElementType;
        private final IrType fromType;
        private final IrSimpleFunction getMethod;
        private final IrSimpleFunction sizeMethod;
        final /* synthetic */ JsInteropFunctionsLowering this$0;
        private final IrType toType;

        public CopyToJsArrayAdapter(JsInteropFunctionsLowering jsInteropFunctionsLowering, IrType fromType, IrType fromElementType) {
            Intrinsics.checkNotNullParameter(fromType, "fromType");
            Intrinsics.checkNotNullParameter(fromElementType, "fromElementType");
            this.this$0 = jsInteropFunctionsLowering;
            this.fromType = fromType;
            this.fromElementType = fromElementType;
            this.toType = jsInteropFunctionsLowering.getJsRelatedSymbols().getJsAnyType();
            InteropTypeAdapter interopTypeAdapter = jsInteropFunctionsLowering.getPrimitivesToExternRefAdapters().get(fromElementType);
            this.elementAdapter = interopTypeAdapter == null ? jsInteropFunctionsLowering.kotlinToJsAdapterIfNeeded(fromElementType, false) : interopTypeAdapter;
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(getFromType());
            Intrinsics.checkNotNull(classOrNull);
            this.arrayClass = classOrNull;
            IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(classOrNull, "get");
            Intrinsics.checkNotNull(simpleFunction);
            this.getMethod = simpleFunction.getOwner();
            IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(classOrNull, "size");
            Intrinsics.checkNotNull(propertyGetter);
            this.sizeMethod = propertyGetter.getOwner();
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getFromType() {
            return this.fromType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrType getToType() {
            return this.toType;
        }

        @Override // org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering.InteropTypeAdapter
        public IrExpression adapt(IrExpression expression, IrBuilderWithScope builder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(builder, "builder");
            JsInteropFunctionsLowering jsInteropFunctionsLowering = this.this$0;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, null, true);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            boolean z = false;
            IrSimpleFunction irSimpleFunction = null;
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, expression, null, null, false, null, 30, null);
            IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
            IrVariable irVariableIrTemporary$default2 = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder3, jsInteropFunctionsLowering.getJsRelatedSymbols().getNewJsArray()), null, null, false, null, 30, null);
            IrVariable irVariableIrTemporary$default3 = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, ExpressionHelpersKt.irInt$default(irBlockBuilder3, 0, null, 2, null), null, null, true, null, 22, null);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder3, this.sizeMethod);
            IrVariable irVariable = irVariableIrTemporary$default;
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable));
            IrVariable irVariableIrTemporary$default4 = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, irFunctionAccessExpressionIrCall, null, null, false, null, 30, null);
            IrWhileLoopImpl irWhileLoopImplIrWhile$default = IrBuilderKt.irWhile$default(irBlockBuilder3, null, 1, null);
            IrVariable irVariable2 = irVariableIrTemporary$default3;
            irWhileLoopImplIrWhile$default.setCondition(ExpressionHelpersKt.irNotEquals(irBlockBuilder3, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable2), ExpressionHelpersKt.irGet(irBlockBuilder3, irVariableIrTemporary$default4)));
            IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(irBlockBuilder3.getContext(), irBlockBuilder3.getScope(), irBlockBuilder3.getStartOffset(), irBlockBuilder3.getEndOffset(), null, null, false, 64, null);
            InteropTypeAdapter interopTypeAdapter = this.elementAdapter;
            IrBlockBuilder irBlockBuilder5 = irBlockBuilder4;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBuilder5, this.getMethod);
            irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable));
            irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable2));
            Unit unit = Unit.INSTANCE;
            IrExpression irExpressionAdaptIfNeeded = jsInteropFunctionsLowering.adaptIfNeeded(interopTypeAdapter, ExpressionHelpersKt.irImplicitCast(irBlockBuilder5, irFunctionAccessExpressionIrCall2, this.fromElementType), irBlockBuilder5);
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder5, jsInteropFunctionsLowering.getJsRelatedSymbols().getJsArrayPush());
            IrVariable irVariable3 = irVariableIrTemporary$default2;
            irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable3));
            irCall.putValueArgument(1, irExpressionAdaptIfNeeded);
            irBlockBuilder4.unaryPlus(irCall);
            IrClass irClass = IrTypesKt.getClass(irVariableIrTemporary$default3.getType());
            Intrinsics.checkNotNull(irClass);
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
                if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.INC)) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction = irSimpleFunction2;
                    z = true;
                }
            }
            if (z) {
                irBlockBuilder4.unaryPlus(ExpressionHelpersKt.irSet(irBlockBuilder5, irVariable2, ExpressionHelpersKt.irCallOp(irBlockBuilder5, irSimpleFunction.getSymbol(), irVariableIrTemporary$default3.getType(), ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable2), (24 & 8) != 0 ? null : null, (24 & 16) != 0 ? null : null), IrStatementOrigin.PREFIX_INCR.INSTANCE));
                irWhileLoopImplIrWhile$default.setBody(irBlockBuilder4.getIrBlockBody());
                irBlockBuilder.unaryPlus(irWhileLoopImplIrWhile$default);
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable3));
                return irBlockBuilder.getIrBlockBody();
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
    }
}
