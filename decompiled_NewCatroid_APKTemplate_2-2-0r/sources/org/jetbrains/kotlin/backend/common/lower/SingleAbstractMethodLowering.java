package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.codegen.SamWrapperCodegen;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: SingleAbstractMethodLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\n\u0010&\u001a\u0004\u0018\u00010'H\u0014J \u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\f2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020,H\u0002J\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.2\u0006\u00100\u001a\u00020\u000bH\u0002J\u0010\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u000bH&J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0014J\u0010\u00106\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\fH\u0014J\u001e\u00107\u001a\u00020#2\u0006\u00108\u001a\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0.H&J\u0010\u0010<\u001a\u00020)2\u0006\u0010=\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\fH\u0016J\u0010\u0010B\u001a\u00020C2\u0006\u00108\u001a\u000209H\u0016J\u0014\u0010D\u001a\u00020)*\u00020E2\u0006\u0010$\u001a\u00020%H\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR \u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000eR\u0016\u0010\u001d\u001a\u00020\u0018*\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SingleAbstractMethodLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "FUNCTION_FIELD_NAME", "", "SAM_WRAPPER_SUFFIX", "cachedImplementations", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCachedImplementations", "()Ljava/util/Map;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "enclosingContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "getEnclosingContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "setEnclosingContainer", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)V", "inInlineFunctionScope", "", "getInInlineFunctionScope", "()Z", "inlineCachedImplementations", "getInlineCachedImplementations", "needEqualsHashCodeMethods", "getNeedEqualsHashCodeMethods", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "createObjectProxy", "superType", "wrapperVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "createFor", "Lorg/jetbrains/kotlin/ir/IrElement;", "currentScopeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "generateEqualsHashCode", "", "klass", "functionDelegateField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getAdditionalSupertypes", "", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "supertype", "getSuperTypeForWrapper", "typeOperand", "getSuspendFunctionWithoutContinuation", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "getWrappedFunctionType", "getWrapperVisibility", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "scopes", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setConstructorSourceRange", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SingleAbstractMethodLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final String FUNCTION_FIELD_NAME;
    private final String SAM_WRAPPER_SUFFIX;
    private final Map<IrType, IrClass> cachedImplementations;
    private final CommonBackendContext context;
    private IrDeclarationContainer enclosingContainer;
    private final Map<IrType, IrClass> inlineCachedImplementations;

    public abstract boolean getNeedEqualsHashCodeMethods(IrType irType);

    public abstract IrType getSuperTypeForWrapper(IrType typeOperand);

    protected IrSimpleFunction getSuspendFunctionWithoutContinuation(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function;
    }

    public abstract DescriptorVisibility getWrapperVisibility(IrTypeOperatorCall expression, List<? extends ScopeWithIr> scopes);

    public SingleAbstractMethodLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.cachedImplementations = new LinkedHashMap();
        this.inlineCachedImplementations = new LinkedHashMap();
        this.SAM_WRAPPER_SUFFIX = SamWrapperCodegen.SAM_WRAPPER_SUFFIX;
        this.FUNCTION_FIELD_NAME = "function";
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    protected final Map<IrType, IrClass> getCachedImplementations() {
        return this.cachedImplementations;
    }

    protected final Map<IrType, IrClass> getInlineCachedImplementations() {
        return this.inlineCachedImplementations;
    }

    protected final IrDeclarationContainer getEnclosingContainer() {
        return this.enclosingContainer;
    }

    protected final void setEnclosingContainer(IrDeclarationContainer irDeclarationContainer) {
        this.enclosingContainer = irDeclarationContainer;
    }

    protected IrType getWrappedFunctionType(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return IrUtilsKt.getDefaultType(klass);
    }

    protected void setConstructorSourceRange(IrFunctionBuilder irFunctionBuilder, IrElement createFor) {
        Intrinsics.checkNotNullParameter(irFunctionBuilder, "<this>");
        Intrinsics.checkNotNullParameter(createFor, "createFor");
        IrElementBuilderKt.setSourceRange(irFunctionBuilder, createFor);
    }

    public boolean getInInlineFunctionScope() {
        List<ScopeWithIr> allScopes = getAllScopes();
        if ((allScopes instanceof Collection) && allScopes.isEmpty()) {
            return false;
        }
        Iterator<T> it = allScopes.iterator();
        while (it.hasNext()) {
            IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
            IrFunction irFunction = irElement instanceof IrFunction ? (IrFunction) irElement : null;
            if (irFunction != null ? irFunction.getIsInline() : false) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Object next;
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        this.cachedImplementations.clear();
        this.inlineCachedImplementations.clear();
        Iterator<T> it = irFile.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if ((next instanceof IrClass) && IrUtilsKt.isFileClass((IrClass) next)) {
                break;
            }
        }
        IrClass irClass = (IrClass) next;
        this.enclosingContainer = irClass != null ? irClass : irFile;
        transformChildrenVoid(irFile);
        for (IrClass irClass2 : CollectionsKt.plus((Collection) this.cachedImplementations.values(), (Iterable) this.inlineCachedImplementations.values())) {
            IrDeclarationParent parent = irClass2.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            ((IrDeclarationContainer) parent).getDeclarations().add(irClass2);
        }
    }

    protected IrSymbol currentScopeSymbol() {
        Scope scope;
        ScopeWithIr currentScope = getCurrentScope();
        if (currentScope == null || (scope = currentScope.getScope()) == null) {
            return null;
        }
        return scope.getScopeOwnerSymbol();
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclarationContainer irDeclarationContainer = this.enclosingContainer;
        if (irDeclarationContainer == null || (irDeclarationContainer instanceof IrFile)) {
            this.enclosingContainer = declaration;
        }
        super.visitClassNew(declaration);
        this.enclosingContainer = irDeclarationContainer;
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression.getOperator() != IrTypeOperator.SAM_CONVERSION) {
            return super.visitTypeOperator(expression);
        }
        IrType superTypeForWrapper = getSuperTypeForWrapper(expression.getTypeOperand());
        IrType irTypeMakeNullable = IrTypeUtilsKt.isNullable(expression.getTypeOperand()) ? IrTypesKt.makeNullable(superTypeForWrapper) : superTypeForWrapper;
        IrExpression irExpressionTransform = expression.getArgument().transform((IrElementTransformer<? super Object>) this, (Object) null);
        CommonBackendContext commonBackendContext = this.context;
        IrSymbol irSymbolCurrentScopeSymbol = currentScopeSymbol();
        Intrinsics.checkNotNull(irSymbolCurrentScopeSymbol);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(commonBackendContext, irSymbolCurrentScopeSymbol, 0, 0, 6, (Object) null);
        if (IrUtilsKt.isNullConst(irExpressionTransform)) {
            return irExpressionTransform;
        }
        Map<IrType, IrClass> map = getInInlineFunctionScope() ? this.inlineCachedImplementations : this.cachedImplementations;
        IrClass irClassCreateObjectProxy = map.get(superTypeForWrapper);
        if (irClassCreateObjectProxy == null) {
            irClassCreateObjectProxy = createObjectProxy(superTypeForWrapper, getWrapperVisibility(expression, getAllScopes()), expression);
            map.put(superTypeForWrapper, irClassCreateObjectProxy);
        }
        IrClass irClass = irClassCreateObjectProxy;
        if (IrTypeUtilsKt.isNullable(irTypeMakeNullable) && IrTypeUtilsKt.isNullable(irExpressionTransform.getType())) {
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irExpressionTransform.getStartOffset(), irExpressionTransform.getEndOffset(), null, irTypeMakeNullable, false, 64, null);
            IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, irExpressionTransform, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, (IrFunction) SequencesKt.single(IrUtilsKt.getConstructors(irClass)));
            IrVariable irVariable = irVariableCreateTmpVariable$default;
            irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, irTypeMakeNullable, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable), ExpressionHelpersKt.irNull(irBlockBuilder2), irFunctionAccessExpressionIrCall));
            return irBlockBuilder.getIrBlockBody();
        }
        if (!(irExpressionTransform instanceof IrGetValue)) {
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder$default;
            IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(declarationIrBuilder2.getContext(), declarationIrBuilder2.getScope(), irExpressionTransform.getStartOffset(), irExpressionTransform.getEndOffset(), null, irTypeMakeNullable, false, 64, null);
            IrVariable irVariableCreateTmpVariable$default2 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder3, irExpressionTransform, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
            IrBlockBuilder irBlockBuilder4 = irBlockBuilder3;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBuilder4, (IrFunction) SequencesKt.single(IrUtilsKt.getConstructors(irClass)));
            irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder4, irVariableCreateTmpVariable$default2));
            irBlockBuilder3.unaryPlus(irFunctionAccessExpressionIrCall2);
            return irBlockBuilder3.getIrBlockBody();
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall3 = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, (IrFunction) SequencesKt.single(IrUtilsKt.getConstructors(irClass)));
        irFunctionAccessExpressionIrCall3.putValueArgument(0, irExpressionTransform);
        return irFunctionAccessExpressionIrCall3;
    }

    private final IrClass createObjectProxy(IrType superType, DescriptorVisibility wrapperVisibility, IrElement createFor) {
        IrClass owner;
        int i;
        IrSymbolOwner owner2 = IrTypesKt.getClassifierOrFail(superType).getOwner();
        Intrinsics.checkNotNull(owner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) owner2;
        irClass.getKind();
        ClassKind classKind = ClassKind.INTERFACE;
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
        Intrinsics.checkNotNull(fqNameWhenAvailable);
        String strAsString = fqNameWhenAvailable.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Name nameIdentifier = Name.identifier("sam" + (Intrinsics.areEqual(wrapperVisibility, DescriptorVisibilities.PUBLIC) ? "$i" : "") + '$' + StringsKt.replace$default(strAsString, '.', '_', false, 4, (Object) null) + this.SAM_WRAPPER_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        boolean z = false;
        IrSimpleFunction irSimpleFunction = null;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            if (irSimpleFunction2.getModality() == Modality.ABSTRACT) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunction = irSimpleFunction2;
                z = true;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
            IrSimpleFunction suspendFunctionWithoutContinuation = getSuspendFunctionWithoutContinuation(irSimpleFunction3);
            int i2 = suspendFunctionWithoutContinuation.getExtensionReceiverParameter() == null ? 0 : 1;
            if (suspendFunctionWithoutContinuation.getIsSuspend()) {
                owner = this.context.getIr().getSymbols().suspendFunctionN(suspendFunctionWithoutContinuation.getValueParameters().size() + i2).getOwner();
            } else {
                owner = this.context.getIr().getSymbols().functionN(suspendFunctionWithoutContinuation.getValueParameters().size() + i2).getOwner();
            }
            IrType wrappedFunctionType = getWrappedFunctionType(owner);
            IrFactory irFactory = this.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            irClassBuilder.setName(nameIdentifier);
            irClassBuilder.setOrigin(IrDeclarationOrigin.GENERATED_SAM_IMPLEMENTATION.INSTANCE);
            irClassBuilder.setVisibility(wrapperVisibility);
            IrElementBuilderKt.setSourceRange(irClassBuilder, createFor);
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            irClassBuildClass.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(CollectionsKt.listOf(superType), (List) getAdditionalSupertypes(superType)));
            IrDeclarationContainer irDeclarationContainer = this.enclosingContainer;
            Intrinsics.checkNotNull(irDeclarationContainer);
            irClassBuildClass.setParent(irDeclarationContainer);
            IrFactory factory = irClassBuildClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier2 = Name.identifier(this.FUNCTION_FIELD_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier2);
            irFieldBuilder.setType(wrappedFunctionType);
            irFieldBuilder.setOrigin(IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE);
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFieldBuilder.setVisibility(PRIVATE);
            irFieldBuilder.setFinal(true);
            IrElementBuilderKt.setSourceRange(irFieldBuilder, createFor);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
            IrClass irClass2 = irClassBuildClass;
            irFieldBuildField.setParent(irClass2);
            irClassBuildClass.getDeclarations().add(irFieldBuildField);
            IrFactory factory2 = irClassBuildClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(IrDeclarationOrigin.GENERATED_SAM_IMPLEMENTATION.INSTANCE);
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setVisibility(wrapperVisibility);
            setConstructorSourceRange(irFunctionBuilder, createFor);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder);
            irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(irClass2);
            IrConstructor irConstructor = irConstructorBuildConstructor;
            IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
            irValueParameterBuilder.setName(irFieldBuildField.getName());
            irValueParameterBuilder.setType(irFieldBuildField.getType());
            irValueParameterBuilder.setOrigin(irClassBuildClass.getOrigin());
            if (irValueParameterBuilder.getIndex() == -1) {
                irValueParameterBuilder.setIndex(irConstructor.getValueParameters().size());
            }
            IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irConstructor.getFactory(), irValueParameterBuilder, irConstructor);
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
            IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
            irFunctionBuilder2.setName(suspendFunctionWithoutContinuation.getName());
            irFunctionBuilder2.setReturnType(suspendFunctionWithoutContinuation.getReturnType());
            irFunctionBuilder2.setVisibility(suspendFunctionWithoutContinuation.getVisibility());
            irFunctionBuilder2.setModality(Modality.FINAL);
            irFunctionBuilder2.setOrigin(IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE);
            irFunctionBuilder2.setSuspend(suspendFunctionWithoutContinuation.getIsSuspend());
            IrElementBuilderKt.setSourceRange(irFunctionBuilder2, createFor);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory3, irFunctionBuilder2);
            IrClass irClass3 = irClassBuildClass;
            irClass3.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass3);
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(suspendFunctionWithoutContinuation.getSymbol()));
            IrValueParameter thisReceiver2 = irClassBuildClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver2);
            IrSimpleFunction irSimpleFunction4 = irSimpleFunctionBuildFunction;
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver2, irSimpleFunction4, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
            IrValueParameter extensionReceiverParameter = suspendFunctionWithoutContinuation.getExtensionReceiverParameter();
            irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunction4, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
            List<IrValueParameter> valueParameters = suspendFunctionWithoutContinuation.getValueParameters();
            ArrayList arrayList = new ArrayList(valueParameters.size());
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunction4, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
            }
            irSimpleFunctionBuildFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            IrBlockBodyBuilder irBlockBodyBuilder3 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default2.getContext(), declarationIrBuilderCreateIrBuilder$default2.getScope(), declarationIrBuilderCreateIrBuilder$default2.getStartOffset(), declarationIrBuilderCreateIrBuilder$default2.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder3;
            IrSimpleFunction irSimpleFunction5 = null;
            boolean z2 = false;
            for (IrSimpleFunction irSimpleFunction6 : IrUtilsKt.getFunctions(owner)) {
                if (Intrinsics.areEqual(irSimpleFunction6.getName(), OperatorNameConventions.INVOKE)) {
                    if (z2) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction5 = irSimpleFunction6;
                    z2 = true;
                }
            }
            if (z2) {
                IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder4, getSuspendFunctionWithoutContinuation(irSimpleFunction5).getSymbol(), suspendFunctionWithoutContinuation.getReturnType(), 0, 0, null, 28, null);
                IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                irCallIrCall$default.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder4, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, dispatchReceiverParameter), irFieldBuildField, null, 4, null));
                IrValueParameter extensionReceiverParameter2 = irSimpleFunctionBuildFunction.getExtensionReceiverParameter();
                if (extensionReceiverParameter2 != null) {
                    i = 0;
                    irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, extensionReceiverParameter2));
                    Unit unit = Unit.INSTANCE;
                    Unit unit2 = Unit.INSTANCE;
                } else {
                    i = 0;
                }
                for (Object obj : irSimpleFunctionBuildFunction.getValueParameters()) {
                    int i3 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    irCallIrCall$default.putValueArgument(i + i2, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, (IrValueParameter) obj));
                    i = i3;
                }
                Unit unit3 = Unit.INSTANCE;
                irBlockBodyBuilder3.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder4, irCallIrCall$default));
                irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder3.getIrBlockBody());
                if (getNeedEqualsHashCodeMethods(superType)) {
                    generateEqualsHashCode(irClassBuildClass, superType, irFieldBuildField);
                }
                IrUtilsKt.addFakeOverrides$default(irClassBuildClass, this.context.getTypeSystem(), null, CollectionsKt.listOf(irSimpleFunction3.getSymbol()), 2, null);
                return irClassBuildClass;
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final void generateEqualsHashCode(IrClass klass, IrType superType, final IrField functionDelegateField) {
        new SamEqualsHashCodeMethodsGenerator(this.context, klass, superType, new Function2<IrBuilderWithScope, IrExpression, IrExpression>() { // from class: org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering.generateEqualsHashCode.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrBuilderWithScope $receiver, IrExpression receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(receiver, "receiver");
                return ExpressionHelpersKt.irGetField$default($receiver, receiver, functionDelegateField, null, 4, null);
            }
        }).generate();
    }

    private final List<IrSimpleType> getAdditionalSupertypes(IrType supertype) {
        if (getNeedEqualsHashCodeMethods(supertype)) {
            return CollectionsKt.listOf(IrTypesKt.typeWith(this.context.getIr().getSymbols().getFunctionAdapter(), new IrType[0]));
        }
        return CollectionsKt.emptyList();
    }
}
