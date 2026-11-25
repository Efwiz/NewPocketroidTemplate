package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsAnnotations;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: JsDefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0016J \u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000e2\b\u0010\f\u001a\u0004\u0018\u00010\nH\u0002J\u0014\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0014J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0014H\u0002J\f\u0010\u0018\u001a\u00020\u0019*\u00020\u0014H\u0002J\f\u0010\u001a\u001a\u00020\u0014*\u00020\u0014H\u0002J\u0014\u0010\u001b\u001a\u00020\u0019*\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0002¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsDefaultArgumentStubGenerator;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentStubGenerator;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "context", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "createDefaultResolutionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "defaultExpression", "toParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "createResolutionStatement", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "parameter", "generateDefaultStubBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "originalDeclaration", "generateJsNameAnnotationCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "hasDefaultArgs", "", "introduceDefaultResolution", "isAnnotation", "name", "Lorg/jetbrains/kotlin/name/FqName;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDefaultArgumentStubGenerator extends DefaultArgumentStubGenerator<JsIrBackendContext> {
    /* JADX WARN: Illegal instructions before constructor call */
    public JsDefaultArgumentStubGenerator(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JsIrBackendContext jsIrBackendContext = context;
        super(jsIrBackendContext, new JsDefaultArgumentFunctionFactory(jsIrBackendContext), false, true, false, 4, null);
    }

    private final IrExpression createDefaultResolutionExpression(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrValueParameter irValueParameter) {
        IrIfThenElseImpl irIfThenElseImplIrIfThenElse$default;
        if (irExpression != null) {
            IrValueParameter irValueParameter2 = irValueParameter;
            irIfThenElseImplIrIfThenElse$default = ExpressionHelpersKt.irIfThenElse$default(irBuilderWithScope, irValueParameter.getType(), ExpressionHelpersKt.irEqeqeqWithoutBox(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, irValueParameter2, irValueParameter.getType()), IrJsUtilsKt.getVoid(getContext())), irExpression, ExpressionHelpersKt.irGet(irBuilderWithScope, irValueParameter2), null, 16, null);
        } else {
            irIfThenElseImplIrIfThenElse$default = null;
        }
        return irIfThenElseImplIrIfThenElse$default;
    }

    private final IrSetValue createResolutionStatement(IrBuilderWithScope irBuilderWithScope, IrValueParameter irValueParameter, IrExpression irExpression) {
        IrExpression irExpressionCreateDefaultResolutionExpression = createDefaultResolutionExpression(irBuilderWithScope, irExpression, irValueParameter);
        return irExpressionCreateDefaultResolutionExpression != null ? JsIrBuilder.INSTANCE.buildSetValue(irValueParameter.getSymbol(), irExpressionCreateDefaultResolutionExpression) : null;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0104  */
    private final IrFunction introduceDefaultResolution(IrFunction irFunction) {
        IrValueParameter irValueParameter;
        ArrayList arrayList;
        IrValueParameter irValueParameterCopyTo$default;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getContext(), irFunction.getSymbol(), -1, -1);
        HashMap map = new HashMap();
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        ArrayList arrayList2 = new ArrayList(valueParameters.size());
        for (IrValueParameter irValueParameter2 : valueParameters) {
            IrValueParameter irValueParameter3 = irValueParameter2.getDefaultValue() != null ? irValueParameter2 : null;
            if (irValueParameter3 != null) {
                arrayList = arrayList2;
                irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter3, irFunction, JsLoweredDeclarationOrigin.JS_SHADOWED_DEFAULT_PARAMETER.INSTANCE, 0, 0, 0, null, null, null, null, null, false, false, true, 4092, null);
                if (irValueParameterCopyTo$default != null) {
                    map.put(irValueParameter2, irValueParameterCopyTo$default);
                } else {
                    irValueParameter = irValueParameter2;
                }
                ArrayList arrayList3 = arrayList;
                arrayList3.add(irValueParameterCopyTo$default);
                arrayList2 = arrayList3;
            } else {
                irValueParameter = irValueParameter2;
                arrayList = arrayList2;
            }
            irValueParameterCopyTo$default = irValueParameter;
            ArrayList arrayList4 = arrayList;
            arrayList4.add(irValueParameterCopyTo$default);
            arrayList2 = arrayList4;
        }
        irFunction.setValueParameters(CollectionsKt.compactIfPossible(arrayList2));
        IrBody body = irFunction.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody != null) {
            HashMap map2 = map;
            if (!map2.isEmpty()) {
                irBlockBody.transformChildren(new VariableRemapper(map2), null);
                List<IrStatement> statements = irBlockBody.getStatements();
                List<IrValueParameter> valueParameters2 = irFunction.getValueParameters();
                ArrayList arrayList5 = new ArrayList();
                for (IrValueParameter irValueParameter4 : valueParameters2) {
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                    IrExpressionBody defaultValue = irValueParameter4.getDefaultValue();
                    IrSetValue irSetValueCreateResolutionStatement = createResolutionStatement(declarationIrBuilder, irValueParameter4, defaultValue != null ? defaultValue.getExpression() : null);
                    if (irSetValueCreateResolutionStatement != null) {
                        arrayList5.add(irSetValueCreateResolutionStatement);
                    }
                }
                statements.addAll(0, arrayList5);
            }
        }
        getContext().getMapping().getDefaultArgumentsDispatchFunction().set(irFunction, irFunction);
        return irFunction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator, org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) declaration;
            if (!IrUtilsKt.isExternalOrInheritedFromExternal(irFunction)) {
                if (hasDefaultArgs(irFunction) && ((declaration instanceof IrConstructor) || IrUtilsKt.isTopLevel(declaration))) {
                    return kotlin.collections.CollectionsKt.listOf(introduceDefaultResolution(irFunction));
                }
                List<IrDeclaration> listTransformFlat = super.transformFlat(declaration);
                if (listTransformFlat == null) {
                    return null;
                }
                IrDeclaration irDeclaration = listTransformFlat.get(0);
                IrDeclaration irDeclaration2 = listTransformFlat.get(1);
                if (!(irDeclaration instanceof IrFunction) || !(irDeclaration2 instanceof IrFunction)) {
                    return kotlin.collections.CollectionsKt.listOf((Object[]) new IrDeclaration[]{irDeclaration, irDeclaration2});
                }
                if (!IrFakeOverrideUtilsKt.isFakeOverride(irDeclaration2)) {
                    IrFunction irFunction2 = (IrFunction) irDeclaration2;
                    for (IrValueParameter irValueParameter : irFunction2.getValueParameters()) {
                        if (irValueParameter.getDefaultValue() != null) {
                            irValueParameter.setOrigin(JsLoweredDeclarationOrigin.JS_SHADOWED_DEFAULT_PARAMETER.INSTANCE);
                        }
                        irValueParameter.setDefaultValue(null);
                    }
                    if (ExportModelGeneratorKt.isExported(irDeclaration, getContext())) {
                        getContext().getAdditionalExportedDeclarations().add(irDeclaration2);
                        if (!IrUtilsKt.hasAnnotation(irDeclaration, JsAnnotations.INSTANCE.getJsNameFqn())) {
                            irFunction2.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irFunction2.getAnnotations(), generateJsNameAnnotationCall((IrFunction) irDeclaration)));
                        }
                    }
                }
                List<IrConstructorCall> annotations = irDeclaration.getAnnotations();
                ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(annotations, 10));
                for (IrConstructorCall irConstructorCall : annotations) {
                    IrDeclarationParent irDeclarationParent = irDeclaration instanceof IrDeclarationParent ? (IrDeclarationParent) irDeclaration : null;
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irConstructorCall, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irDeclarationParent);
                    if (irElementPatchDeclarationParents != null) {
                        arrayList.add((IrConstructorCall) irElementPatchDeclarationParents);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : arrayList) {
                    IrConstructorCall irConstructorCall2 = (IrConstructorCall) obj;
                    if (isAnnotation(irConstructorCall2, JsAnnotations.INSTANCE.getJsExportFqn()) || isAnnotation(irConstructorCall2, JsAnnotations.INSTANCE.getJsNameFqn())) {
                        arrayList2.add(obj);
                    } else {
                        arrayList3.add(obj);
                    }
                }
                Pair pair = new Pair(arrayList2, arrayList3);
                List list = (List) pair.component1();
                irDeclaration.setAnnotations((List) pair.component2());
                irDeclaration2.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irDeclaration2.getAnnotations(), list));
                irDeclaration.setOrigin(JsLoweredDeclarationOrigin.JS_SHADOWED_EXPORT.INSTANCE);
                return kotlin.collections.CollectionsKt.listOf((Object[]) new IrFunction[]{irDeclaration, irDeclaration2});
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r22v0, types: [org.jetbrains.kotlin.ir.backend.js.lower.JsDefaultArgumentStubGenerator] */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v4, types: [org.jetbrains.kotlin.ir.declarations.IrFunction] */
    /* JADX WARN: Type inference failed for: r4v48 */
    /* JADX WARN: Type inference failed for: r4v49 */
    /* JADX WARN: Type inference failed for: r7v13 */
    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator
    protected IrBody generateDefaultStubBody(IrFunction irFunction, IrFunction originalDeclaration) {
        IrExpression irExpressionTransform;
        IrValueParameter irValueParameter;
        IrIfThenElseImpl irIfThenElseImplIrIfThenElse$default;
        ?? r4;
        IrExpression irExpression;
        IrValueParameter irValueParameter2;
        IrExpression irExpression2;
        IrClass parentClassOrNull;
        IrExpression irExpression3;
        IrExpression irExpression4;
        IrExpression expression;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        JsIrBackendContext jsIrBackendContext = (JsIrBackendContext) getContext();
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getContext(), irFunction.getSymbol(), -1, -1);
        HashMap map = new HashMap();
        IrValueParameter dispatchReceiverParameter = originalDeclaration.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            IrValueParameter dispatchReceiverParameter2 = irFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter2);
            map.put(dispatchReceiverParameter, dispatchReceiverParameter2);
            Unit unit = Unit.INSTANCE;
            Unit unit2 = Unit.INSTANCE;
        }
        IrValueParameter extensionReceiverParameter = originalDeclaration.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            IrValueParameter extensionReceiverParameter2 = irFunction.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter2);
            map.put(extensionReceiverParameter, extensionReceiverParameter2);
            Unit unit3 = Unit.INSTANCE;
            Unit unit4 = Unit.INSTANCE;
        }
        int i = 0;
        for (Object obj : originalDeclaration.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            map.put((IrValueParameter) obj, irFunction.getValueParameters().get(i));
            i = i2;
        }
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), -1, -1);
        List listZip = kotlin.collections.CollectionsKt.zip(irFunction.getValueParameters(), originalDeclaration.getValueParameters());
        ArrayList arrayList = new ArrayList();
        Iterator it = listZip.iterator();
        while (true) {
            irExpressionTransform = null;
            if (!it.hasNext()) {
                break;
            }
            Pair pair = (Pair) it.next();
            IrValueParameter irValueParameter3 = (IrValueParameter) pair.component1();
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrExpressionBody defaultValue = ((IrValueParameter) pair.component2()).getDefaultValue();
            if (defaultValue != null && (expression = defaultValue.getExpression()) != null) {
                irExpressionTransform = expression.transform((IrElementTransformer<? super Object>) new VariableRemapper(map), (Object) null);
            }
            IrSetValue irSetValueCreateResolutionStatement = createResolutionStatement(irBlockBodyBuilder2, irValueParameter3, irExpressionTransform);
            if (irSetValueCreateResolutionStatement != null) {
                arrayList.add(irSetValueCreateResolutionStatement);
            }
        }
        irBlockBodyBuilder.unaryPlus(arrayList);
        IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder3, originalDeclaration, JsStatementOrigins.IMPLEMENTATION_DELEGATION_CALL.INSTANCE, (IrClassSymbol) null, 4, (Object) null);
        IrUtilsKt.passTypeArgumentsFrom$default(irCallIrCall$default, originalDeclaration, 0, 2, null);
        IrValueParameter dispatchReceiverParameter3 = irFunction.getDispatchReceiverParameter();
        irCallIrCall$default.setDispatchReceiver(dispatchReceiverParameter3 != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder3, dispatchReceiverParameter3) : null);
        IrValueParameter extensionReceiverParameter3 = irFunction.getExtensionReceiverParameter();
        irCallIrCall$default.setExtensionReceiver(extensionReceiverParameter3 != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder3, extensionReceiverParameter3) : null);
        int i3 = 0;
        for (Object obj2 : originalDeclaration.getValueParameters()) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter4 = (IrValueDeclaration) map.get((IrValueParameter) obj2);
            if (irValueParameter4 == null) {
                irValueParameter4 = irFunction.getValueParameters().get(i3);
            }
            Intrinsics.checkNotNull(irValueParameter4);
            irCallIrCall$default.putValueArgument(i3, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameter4));
            i3 = i4;
        }
        int i5 = 1;
        if ((irCallIrCall$default.getDispatchReceiver() == null || ExportModelGeneratorKt.isExported(originalDeclaration, jsIrBackendContext)) ? false : true) {
            IrValueParameter irValueParameter5 = (IrValueParameter) kotlin.collections.CollectionsKt.last((List) irFunction.getValueParameters());
            IrMemberWithContainerSource realOverrideTarget = IrResolveUtilsKt.getRealOverrideTarget(originalDeclaration);
            if ((realOverrideTarget instanceof IrOverridableMember) && ((IrOverridableMember) realOverrideTarget).getModality() == Modality.ABSTRACT) {
                r4 = realOverrideTarget;
                r4 = realOverrideTarget;
                r4 = 0;
            }
            r4 = realOverrideTarget;
            r4 = realOverrideTarget;
            r4 = realOverrideTarget;
            if (r4 != 0 && (parentClassOrNull = IrUtilsKt.getParentClassOrNull((IrDeclaration) r4)) != null && IrUtilsKt.isInterface(parentClassOrNull)) {
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder3, (IrFunction) r4);
                IrExpression extensionReceiver = irCallIrCall$default.getExtensionReceiver();
                if (extensionReceiver != null) {
                    IrExpression irExpression5 = extensionReceiver;
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irExpression5, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression5.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                    if (irElementPatchDeclarationParents != null) {
                        irExpression3 = (IrExpression) irElementPatchDeclarationParents;
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    }
                } else {
                    irExpression3 = null;
                }
                irFunctionAccessExpressionIrCall.setExtensionReceiver(irExpression3);
                Iterator<Integer> it2 = RangesKt.until(0, irCallIrCall$default.getValueArgumentsCount()).iterator();
                while (it2.hasNext()) {
                    int iNextInt = ((IntIterator) it2).nextInt();
                    IrExpression valueArgument = irCallIrCall$default.getValueArgument(iNextInt);
                    if (valueArgument != null) {
                        IrExpression irExpression6 = valueArgument;
                        DeepCopySymbolRemapper deepCopySymbolRemapper3 = new DeepCopySymbolRemapper(null, i5, null);
                        IrVisitorsKt.acceptVoid(irExpression6, deepCopySymbolRemapper3);
                        DeepCopySymbolRemapper deepCopySymbolRemapper4 = deepCopySymbolRemapper3;
                        IrElement irElementPatchDeclarationParents2 = PatchDeclarationParentsKt.patchDeclarationParents(irExpression6.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper4, new DeepCopyTypeRemapper(deepCopySymbolRemapper4)), (Object) null), null);
                        if (irElementPatchDeclarationParents2 != null) {
                            irExpression4 = (IrExpression) irElementPatchDeclarationParents2;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    } else {
                        irExpression4 = null;
                    }
                    irFunctionAccessExpressionIrCall.putValueArgument(iNextInt, irExpression4);
                    i5 = 1;
                }
                irValueParameter2 = irValueParameter5;
                irExpressionTransform = irFunctionAccessExpressionIrCall;
            } else {
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder3, jsIrBackendContext.getIntrinsics().getJsCall());
                IrExpression dispatchReceiver = irCallIrCall$default.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                IrExpression irExpression7 = dispatchReceiver;
                DeepCopySymbolRemapper deepCopySymbolRemapper5 = new DeepCopySymbolRemapper(null, 1, null);
                IrVisitorsKt.acceptVoid(irExpression7, deepCopySymbolRemapper5);
                DeepCopySymbolRemapper deepCopySymbolRemapper6 = deepCopySymbolRemapper5;
                IrElement irElementPatchDeclarationParents3 = PatchDeclarationParentsKt.patchDeclarationParents(irExpression7.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper6, new DeepCopyTypeRemapper(deepCopySymbolRemapper6)), (Object) null), null);
                if (irElementPatchDeclarationParents3 != null) {
                    irCall.putValueArgument(0, (IrExpression) irElementPatchDeclarationParents3);
                    IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder3, jsIrBackendContext.getIntrinsics().getJsContexfulRef());
                    irCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameter5));
                    irCall2.putValueArgument(1, ExpressionHelpersKt.irRawFunctionReference(irBlockBodyBuilder3, jsIrBackendContext.getDynamicType(), originalDeclaration.getSymbol()));
                    Unit unit5 = Unit.INSTANCE;
                    irCall.putValueArgument(1, irCall2);
                    IrDynamicType dynamicType = jsIrBackendContext.getDynamicType();
                    List listCreateListBuilder = kotlin.collections.CollectionsKt.createListBuilder();
                    List list = listCreateListBuilder;
                    IrExpression extensionReceiver2 = irCallIrCall$default.getExtensionReceiver();
                    if (extensionReceiver2 != null) {
                        IrExpression irExpression8 = extensionReceiver2;
                        DeepCopySymbolRemapper deepCopySymbolRemapper7 = new DeepCopySymbolRemapper(null, 1, null);
                        IrVisitorsKt.acceptVoid(irExpression8, deepCopySymbolRemapper7);
                        DeepCopySymbolRemapper deepCopySymbolRemapper8 = deepCopySymbolRemapper7;
                        IrElement irElementPatchDeclarationParents4 = PatchDeclarationParentsKt.patchDeclarationParents(irExpression8.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper8, new DeepCopyTypeRemapper(deepCopySymbolRemapper8)), (Object) null), null);
                        if (irElementPatchDeclarationParents4 != null) {
                            irExpression = (IrExpression) irElementPatchDeclarationParents4;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    } else {
                        irExpression = null;
                    }
                    CollectionsKt.addIfNotNull(list, irExpression);
                    Iterator<Integer> it3 = RangesKt.until(0, irCallIrCall$default.getValueArgumentsCount()).iterator();
                    while (it3.hasNext()) {
                        IrExpression valueArgument2 = irCallIrCall$default.getValueArgument(((IntIterator) it3).nextInt());
                        if (valueArgument2 != null) {
                            IrExpression irExpression9 = valueArgument2;
                            DeepCopySymbolRemapper deepCopySymbolRemapper9 = new DeepCopySymbolRemapper(null, 1, null);
                            IrVisitorsKt.acceptVoid(irExpression9, deepCopySymbolRemapper9);
                            DeepCopySymbolRemapper deepCopySymbolRemapper10 = deepCopySymbolRemapper9;
                            IrElement irElementPatchDeclarationParents5 = PatchDeclarationParentsKt.patchDeclarationParents(irExpression9.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper10, new DeepCopyTypeRemapper(deepCopySymbolRemapper10)), (Object) null), null);
                            if (irElementPatchDeclarationParents5 != null) {
                                irExpression2 = (IrExpression) irElementPatchDeclarationParents5;
                            } else {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                            }
                        } else {
                            irExpression2 = null;
                        }
                        CollectionsKt.addIfNotNull(list, irExpression2);
                        irValueParameter5 = irValueParameter5;
                    }
                    irValueParameter2 = irValueParameter5;
                    Unit unit6 = Unit.INSTANCE;
                    irCall.putValueArgument(2, ExpressionHelpersKt.irVararg(irBlockBodyBuilder3, dynamicType, kotlin.collections.CollectionsKt.build(listCreateListBuilder)));
                    irExpressionTransform = irCall;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
            }
            irValueParameter = irValueParameter2;
        } else {
            irValueParameter = null;
        }
        if (irExpressionTransform == null) {
            irIfThenElseImplIrIfThenElse$default = irCallIrCall$default;
        } else {
            IrType returnType = originalDeclaration.getReturnType();
            Intrinsics.checkNotNull(irValueParameter);
            irIfThenElseImplIrIfThenElse$default = ExpressionHelpersKt.irIfThenElse$default(irBlockBodyBuilder3, returnType, ExpressionHelpersKt.irEqeqeqWithoutBox(irBlockBodyBuilder3, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameter), IrJsUtilsKt.getVoid(jsIrBackendContext)), irCallIrCall$default, irExpressionTransform, null, 16, null);
        }
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irIfThenElseImplIrIfThenElse$default));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    private final IrConstructorCall generateJsNameAnnotationCall(IrFunction irFunction) {
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getContext(), irFunction.getSymbol(), irFunction.getStartOffset(), irFunction.getEndOffset());
        JsIrBackendContext context = getContext();
        IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder, (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(context.getIntrinsics().getJsNameAnnotationSymbol())));
        IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
        IrType stringType = context.getIrBuiltIns().getStringType();
        String identifier = irFunction.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        irConstructorCallIrCall.putValueArgument(0, companion.string(-1, -1, stringType, identifier));
        return irConstructorCallIrCall;
    }

    private final boolean isAnnotation(IrConstructorCall irConstructorCall, FqName fqName) {
        return Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner())), fqName);
    }

    private final boolean hasDefaultArgs(IrFunction irFunction) {
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            return false;
        }
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            if (((IrValueParameter) it.next()).getDefaultValue() != null) {
                return true;
            }
        }
        return false;
    }
}
