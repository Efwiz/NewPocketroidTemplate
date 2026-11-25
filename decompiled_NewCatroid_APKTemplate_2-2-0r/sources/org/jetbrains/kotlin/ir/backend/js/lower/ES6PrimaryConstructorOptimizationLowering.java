package org.jetbrains.kotlin.ir.backend.js.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.ir.ValueRemapper;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsAnnotations;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: ES6PrimaryConstructorOptimizationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0014\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\f\u0010\u000e\u001a\u00020\u000f*\u00020\rH\u0002J3\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0006\"\u0004\b\u0000\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\u00150\u0014H\u0082\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6PrimaryConstructorOptimizationLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "convertToRegularConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "removeConstructorForExport", "", "withoutFirst", ExifInterface.GPS_DIRECTION_TRUE, "", "predicate", "Lkotlin/Function1;", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6PrimaryConstructorOptimizationLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public ES6PrimaryConstructorOptimizationLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.context.getEs6mode() || !(declaration instanceof IrFunction)) {
            return null;
        }
        IrFunction irFunction = (IrFunction) declaration;
        if (!ES6PrimaryConstructorOptimizationLoweringKt.shouldBeConvertedToPlainConstructor(irFunction, this.context)) {
            return null;
        }
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(declaration);
        IrFunction irFunctionFindDefaultConstructorFor = IrJsUtilsKt.findDefaultConstructorFor(this.context, parentAsClass);
        if (ExportModelGeneratorKt.isExported(parentAsClass, this.context)) {
            removeConstructorForExport(parentAsClass);
        }
        IrConstructor irConstructorConvertToRegularConstructor = convertToRegularConstructor(irFunction, parentAsClass);
        if (Intrinsics.areEqual(declaration, irFunctionFindDefaultConstructorFor)) {
            this.context.getMapping().getClassToItsDefaultConstructor().put(parentAsClass, irConstructorConvertToRegularConstructor);
        }
        return CollectionsKt.listOf(irConstructorConvertToRegularConstructor);
    }

    private final IrConstructor convertToRegularConstructor(final IrFunction irFunction, final IrClass irClass) {
        final IrClass superClass = IrUtilsKt.getSuperClass(irClass);
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        final IrValueParameterSymbol symbol = thisReceiver.getSymbol();
        IrFactory factory = irFunction.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom(irFunction);
        irFunctionBuilder.setPrimary(true);
        irFunctionBuilder.setReturnType(irFunction.getReturnType());
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.DEFINED.INSTANCE);
        final IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        IrUtilsKt.copyAnnotationsFrom(irConstructorBuildConstructor, irFunction);
        IrConstructor irConstructor = irConstructorBuildConstructor;
        IrUtilsKt.copyParameterDeclarationsFrom(irConstructor, irFunction);
        irConstructorBuildConstructor.setParent(irClass);
        if (ExportModelGeneratorKt.isExported(irClass, this.context)) {
            List<IrConstructorCall> annotations = irFunction.getAnnotations();
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            boolean z = true;
            for (Object obj : annotations) {
                if (z && IrUtilsKt.isAnnotation((IrConstructorCall) obj, JsAnnotations.INSTANCE.getJsExportIgnoreFqn())) {
                    z = false;
                } else {
                    listCreateListBuilder.add(obj);
                }
            }
            irConstructorBuildConstructor.setAnnotations(CollectionsKt.build(listCreateListBuilder));
        }
        final IrValueParameter boxParameter = ES6AddBoxParameterLoweringKt.getBoxParameter(irConstructor);
        IrBody body = irFunction.getBody();
        IrBody irBody = null;
        if (body != null) {
            IrBody irBody2 = body;
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
            IrVisitorsKt.acceptVoid(irBody2, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irConstructorBuildConstructor);
            if (irElementPatchDeclarationParents != null) {
                irBody = (IrBody) irElementPatchDeclarationParents;
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
            }
        }
        Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) irBody;
        irConstructorBuildConstructor.setBody(irBlockBody);
        final Map mapEmptyMap = MapsKt.emptyMap();
        IrElementTransformerVoidKt.transformChildrenVoid(irBlockBody, new ValueRemapper(irConstructorBuildConstructor, this, symbol, boxParameter, superClass, irClass, mapEmptyMap) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6PrimaryConstructorOptimizationLowering$convertToRegularConstructor$2$2
            final /* synthetic */ IrValueParameter $boxParameter;
            final /* synthetic */ IrValueParameterSymbol $classThisSymbol;
            final /* synthetic */ IrClass $irClass;
            final /* synthetic */ IrClass $superClass;
            private final HashMap<IrValueSymbol, IrValueSymbol> map;
            final /* synthetic */ ES6PrimaryConstructorOptimizationLowering this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(mapEmptyMap);
                this.this$0 = this;
                this.$classThisSymbol = symbol;
                this.$boxParameter = boxParameter;
                this.$superClass = superClass;
                this.$irClass = irClass;
                List<Pair> listZip = CollectionsKt.zip(this.$original.getValueParameters(), irConstructorBuildConstructor.getValueParameters());
                HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(this.$original.getValueParameters().size());
                for (Pair pair : listZip) {
                    Pair pair2 = TuplesKt.to(((IrValueParameter) pair.getFirst()).getSymbol(), ((IrValueParameter) pair.getSecond()).getSymbol());
                    mapNewHashMapWithExpectedSize.put(pair2.getFirst(), pair2.getSecond());
                }
                this.map = mapNewHashMapWithExpectedSize;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.backend.common.ir.ValueRemapper
            public HashMap<IrValueSymbol, IrValueSymbol> getMap() {
                return this.map;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getReturnTargetSymbol(), this.$original.getSymbol())) {
                    return IrJsUtilsKt.irEmpty(this.this$0.context);
                }
                return super.visitReturn(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getSymbol(), this.this$0.context.getIntrinsics().getJsBoxApplySymbol())) {
                    return IrJsUtilsKt.irEmpty(this.this$0.context);
                }
                return super.visitCall(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitVariable(IrVariable declaration) {
                IrConstructor primaryConstructor;
                IrConstructorSymbol symbol2;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrExpression initializer = declaration.getInitializer();
                if (initializer instanceof IrCall) {
                    if (ES6ConstructorLoweringKt.isSyntheticDelegatingReplacement((IrFunctionAccessExpression) initializer)) {
                        getMap().put(declaration.getSymbol(), this.$classThisSymbol);
                        return super.visitCall((IrCall) initializer);
                    }
                    IrCall irCall = (IrCall) initializer;
                    int i = 0;
                    if (!Intrinsics.areEqual(irCall.getSymbol(), this.this$0.context.getIntrinsics().getJsCreateThisSymbol())) {
                        if (Intrinsics.areEqual(irCall.getSymbol(), this.this$0.context.getIntrinsics().getJsCreateExternalThisSymbol())) {
                            getMap().put(declaration.getSymbol(), this.$classThisSymbol);
                            IrClass irClass2 = this.$superClass;
                            if (irClass2 == null || (primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass2)) == null || (symbol2 = primaryConstructor.getSymbol()) == null) {
                                throw new IllegalStateException("Expect to have external constructor here".toString());
                            }
                            IrExpression valueArgument = irCall.getValueArgument(2);
                            IrVararg irVararg = valueArgument instanceof IrVararg ? (IrVararg) valueArgument : null;
                            if (irVararg == null) {
                                throw new IllegalStateException("Wrong type of argument was provided".toString());
                            }
                            IrDelegatingConstructorCall irDelegatingConstructorCallBuildDelegatingConstructorCall$default = JsIrBuilder.buildDelegatingConstructorCall$default(JsIrBuilder.INSTANCE, symbol2, null, 2, null);
                            for (Object obj2 : irVararg.getElements()) {
                                int i2 = i + 1;
                                if (i < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                IrVarargElement irVarargElement = (IrVarargElement) obj2;
                                Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                                irDelegatingConstructorCallBuildDelegatingConstructorCall$default.putValueArgument(i, (IrExpression) irVarargElement);
                                i = i2;
                            }
                            return irDelegatingConstructorCallBuildDelegatingConstructorCall$default;
                        }
                    } else {
                        getMap().put(declaration.getSymbol(), this.$classThisSymbol);
                        if (this.$boxParameter == null || this.$superClass != null) {
                            return IrJsUtilsKt.irEmpty(this.this$0.context);
                        }
                        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.this$0.context.getIntrinsics().getJsBoxApplySymbol(), null, null, null, null, 30, null);
                        IrClass irClass3 = this.$irClass;
                        IrValueParameter irValueParameter = this.$boxParameter;
                        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                        IrValueParameter thisReceiver2 = irClass3.getThisReceiver();
                        Intrinsics.checkNotNull(thisReceiver2);
                        irCallBuildCall$default.putValueArgument(0, JsIrBuilder.buildGetValue$default(jsIrBuilder, thisReceiver2.getSymbol(), null, 2, null));
                        irCallBuildCall$default.putValueArgument(1, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueParameter.getSymbol(), null, 2, null));
                        return super.visitCall(irCallBuildCall$default);
                    }
                }
                return super.visitVariable(declaration);
            }
        });
        return irConstructorBuildConstructor;
    }

    private final void removeConstructorForExport(IrClass irClass) {
        List<IrDeclaration> declarations = irClass.getDeclarations();
        final AnonymousClass1 anonymousClass1 = new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6PrimaryConstructorOptimizationLowering.removeConstructorForExport.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof IrConstructor);
            }
        };
        declarations.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6PrimaryConstructorOptimizationLowering$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ES6PrimaryConstructorOptimizationLowering.removeConstructorForExport$lambda$4(anonymousClass1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeConstructorForExport$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }
}
