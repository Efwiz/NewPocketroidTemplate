package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: PrivateMembersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0018\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u0015H\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0018\u001a\u00020\bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PrivateMembersLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "correspondingStatic", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getCorrespondingStatic", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setCorrespondingStatic", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "correspondingStatic$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "transformAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "transformMemberToStaticFunction", "function", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrivateMembersLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(PrivateMembersLowering.class), "correspondingStatic", "getCorrespondingStatic(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsIrBackendContext context;

    /* JADX INFO: renamed from: correspondingStatic$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingStatic;

    public PrivateMembersLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.correspondingStatic = context.getMapping().getPrivateMemberToCorrespondingStatic();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    private final void setCorrespondingStatic(IrFunction irFunction, IrSimpleFunction irSimpleFunction) {
        this.correspondingStatic.setValue(irFunction, $$delegatedProperties[0], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunctionTransformMemberToStaticFunction = transformMemberToStaticFunction((IrSimpleFunction) declaration);
            if (irSimpleFunctionTransformMemberToStaticFunction == null) {
                return null;
            }
            setCorrespondingStatic((IrFunction) declaration, irSimpleFunctionTransformMemberToStaticFunction);
            return CollectionsKt.listOf(irSimpleFunctionTransformMemberToStaticFunction);
        }
        if (!(declaration instanceof IrProperty)) {
            return null;
        }
        IrProperty irProperty = (IrProperty) declaration;
        IrSimpleFunction getter = irProperty.getGetter();
        irProperty.setGetter(getter != null ? transformAccessor(getter) : null);
        IrSimpleFunction setter = irProperty.getSetter();
        irProperty.setSetter(setter != null ? transformAccessor(setter) : null);
        return CollectionsKt.listOf(declaration);
    }

    private final IrSimpleFunction transformAccessor(IrSimpleFunction accessor) {
        IrSimpleFunction irSimpleFunctionTransformMemberToStaticFunction = transformMemberToStaticFunction(accessor);
        return irSimpleFunctionTransformMemberToStaticFunction == null ? accessor : irSimpleFunctionTransformMemberToStaticFunction;
    }

    /* JADX WARN: Type inference failed for: r4v18, types: [org.jetbrains.kotlin.ir.backend.js.lower.PrivateMembersLowering$transformMemberToStaticFunction$parameterTransformer$1] */
    private final IrSimpleFunction transformMemberToStaticFunction(IrSimpleFunction function) {
        IrExpressionBody defaultValue;
        if (!Intrinsics.areEqual(function.getVisibility(), DescriptorVisibilities.PRIVATE) || function.getDispatchReceiverParameter() == null) {
            return null;
        }
        DescriptorVisibility visibility = AdditionalIrUtilsKt.isLocal(function) ? DescriptorVisibilities.LOCAL : function.getVisibility();
        Intrinsics.checkNotNull(visibility);
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction = function;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setName(function.getName());
        irFunctionBuilder.setReturnType(function.getReturnType());
        irFunctionBuilder.setVisibility(visibility);
        final IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(function.getParent());
        List<IrTypeParameter> typeParameters = irSimpleFunctionBuildFunction.getTypeParameters();
        List<IrTypeParameter> typeParameters2 = function.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        for (IrTypeParameter irTypeParameter : typeParameters2) {
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
            IrVisitorsKt.acceptVoid(irTypeParameter, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irTypeParameter.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunctionBuildFunction);
            if (irElementPatchDeclarationParents != null) {
                arrayList.add((IrTypeParameter) irElementPatchDeclarationParents);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParameter");
            }
        }
        irSimpleFunctionBuildFunction.setTypeParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) typeParameters, (List) arrayList));
        IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        List<IrValueParameter> valueParameters = irSimpleFunctionBuildFunction.getValueParameters();
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setOrigin(PrivateMembersLoweringKt.STATIC_THIS_PARAMETER);
        Name nameIdentifier = Name.identifier("$this");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setIndex(0);
        IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irValueParameterBuilder.setType(dispatchReceiverParameter.getType());
        Unit unit = Unit.INSTANCE;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        irSimpleFunctionBuildFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(valueParameters, DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2)));
        setCorrespondingStatic(irSimpleFunction, irSimpleFunctionBuildFunction);
        List<IrValueParameter> valueParameters2 = irSimpleFunctionBuildFunction.getValueParameters();
        List<IrValueParameter> valueParameters3 = function.getValueParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters3, 10));
        for (IrValueParameter irValueParameter : valueParameters3) {
            arrayList2.add(IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunctionBuildFunction, null, irValueParameter.getIndex() + 1, 0, 0, null, null, null, null, null, false, false, false, 7674, null));
        }
        irSimpleFunctionBuildFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) valueParameters2, (List) arrayList2));
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{function.getExtensionReceiverParameter(), function.getDispatchReceiverParameter()}), (Iterable) function.getValueParameters());
        List listPlus2 = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(irSimpleFunctionBuildFunction.getExtensionReceiverParameter()), (Iterable) irSimpleFunctionBuildFunction.getValueParameters());
        listPlus.size();
        listPlus2.size();
        final Map map = MapsKt.toMap(CollectionsKt.zip(listPlus, listPlus2));
        final ?? r4 = new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrivateMembersLowering$transformMemberToStaticFunction$parameterTransformer$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrGetValue visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrValueParameter irValueParameter2 = map.get(expression.getSymbol().getOwner());
                return irValueParameter2 != null ? new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irValueParameter2.getSymbol(), expression.getOrigin()) : expression;
            }
        };
        for (IrValueParameter irValueParameter2 : function.getValueParameters()) {
            IrValueParameter irValueParameter3 = (IrValueParameter) map.get(irValueParameter2);
            if (irValueParameter3 != null && (defaultValue = irValueParameter2.getDefaultValue()) != null) {
                IrFactory irFactory2 = this.context.getIrFactory();
                int startOffset = irValueParameter2.getStartOffset();
                int endOffset = irValueParameter2.getEndOffset();
                IrBody irBodyTransformMemberToStaticFunction$copyWithParameters = transformMemberToStaticFunction$copyWithParameters(defaultValue, irSimpleFunctionBuildFunction, r4);
                Intrinsics.checkNotNull(irBodyTransformMemberToStaticFunction$copyWithParameters, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
                irValueParameter3.setDefaultValue(irFactory2.createExpressionBody(startOffset, endOffset, ((IrExpressionBody) irBodyTransformMemberToStaticFunction$copyWithParameters).getExpression()));
            }
        }
        final IrExpressionBody body = function.getBody();
        if (body != null) {
            if (body instanceof IrBlockBody) {
                body = IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), body.getStartOffset(), body.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrivateMembersLowering$transformMemberToStaticFunction$5$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                        invoke2(irBlockBody);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrBlockBody createBlockBody) {
                        Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                        List<IrStatement> statements = createBlockBody.getStatements();
                        IrBody irBodyTransformMemberToStaticFunction$copyWithParameters2 = PrivateMembersLowering.transformMemberToStaticFunction$copyWithParameters(body, irSimpleFunctionBuildFunction, r4);
                        Intrinsics.checkNotNull(irBodyTransformMemberToStaticFunction$copyWithParameters2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
                        CollectionsKt.addAll(statements, ((IrBlockBody) irBodyTransformMemberToStaticFunction$copyWithParameters2).getStatements());
                    }
                });
            } else if (body instanceof IrExpressionBody) {
                IrFactory irFactory3 = this.context.getIrFactory();
                int startOffset2 = body.getStartOffset();
                int endOffset2 = body.getEndOffset();
                IrBody irBodyTransformMemberToStaticFunction$copyWithParameters2 = transformMemberToStaticFunction$copyWithParameters(body, irSimpleFunctionBuildFunction, r4);
                Intrinsics.checkNotNull(irBodyTransformMemberToStaticFunction$copyWithParameters2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
                body = irFactory3.createExpressionBody(startOffset2, endOffset2, ((IrExpressionBody) irBodyTransformMemberToStaticFunction$copyWithParameters2).getExpression());
            } else if (!(body instanceof IrSyntheticBody)) {
                CompilationExceptionKt.compilationException("Unexpected body kind", body);
                throw null;
            }
            irSimpleFunctionBuildFunction.setBody(body);
        }
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrBody transformMemberToStaticFunction$copyWithParameters(IrBody irBody, IrSimpleFunction irSimpleFunction, PrivateMembersLowering$transformMemberToStaticFunction$parameterTransformer$1 privateMembersLowering$transformMemberToStaticFunction$parameterTransformer$1) {
        IrBody irBody2 = irBody;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(irBody2, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunction);
        if (irElementPatchDeclarationParents != null) {
            IrBody irBody3 = (IrBody) irElementPatchDeclarationParents;
            irBody3.transform((IrElementTransformer<? super Object>) privateMembersLowering$transformMemberToStaticFunction$parameterTransformer$1, (Object) null);
            return irBody3;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
    }
}
