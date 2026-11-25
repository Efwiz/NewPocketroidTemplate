package org.jetbrains.kotlin.backend.common.lower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.SamWrapperCodegen;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: AnnotationImplementationTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0010\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J8\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H\u0016J&\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020$J\u0010\u0010+\u001a\u00020,2\u0006\u0010'\u001a\u00020(H&J \u0010-\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u0015H&J\u0016\u0010/\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fJ\u0018\u00100\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0018\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u0018H\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u000fH\u0016J\u0010\u00107\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\f\u00108\u001a\u00020\u001c*\u00020\u000fH\u0004J\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020;0:*\u00020\u000fJ\f\u0010<\u001a\u00020\u001c*\u00020\u000fH\u0016J\u001c\u0010=\u001a\u00020$*\u00020$2\u0006\u0010'\u001a\u00020(2\u0006\u0010%\u001a\u00020&H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "forbidDirectFieldAccessInMethods", "", "getForbidDirectFieldAccessInMethods", "()Z", "implementations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getImplementations$ir_backend_common", "()Ljava/util/Map;", "getIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "chooseConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "implClass", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "createAnnotationImplementation", "annotationClass", "generateFunctionBodies", "", "eqFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "hcFun", "toStringFun", "generator", "Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationMemberGenerator;", "generatedEquals", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "arg1", "arg2", "getArrayContentEqualsSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "implementAnnotationPropertiesAndConstructor", "generatedConstructor", "implementGeneratedFunctions", "implementPlatformSpecificParts", "moveValueArgumentsUsingNames", FirebaseAnalytics.Param.SOURCE, FirebaseAnalytics.Param.DESTINATION, "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "visitConstructorCall", "addConstructorBodyForCompatibility", "getAnnotationProperties", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "platformSetup", "transformArrayEqualsArgument", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AnnotationImplementationTransformer extends IrElementTransformerVoidWithContext {
    private final BackendContext context;
    private final boolean forbidDirectFieldAccessInMethods;
    private final Map<IrClass, IrClass> implementations;
    private final IrFile irFile;

    public abstract IrConstructor chooseConstructor(IrClass implClass, IrConstructorCall expression);

    public abstract IrFunctionSymbol getArrayContentEqualsSymbol(IrType type);

    public abstract void implementAnnotationPropertiesAndConstructor(IrClass implClass, IrClass annotationClass, IrConstructor generatedConstructor);

    public void implementPlatformSpecificParts(IrClass annotationClass, IrClass implClass) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(implClass, "implClass");
    }

    public void platformSetup(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
    }

    public IrExpression transformArrayEqualsArgument(IrExpression irExpression, IrType type, IrBlockBodyBuilder irBuilder) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(irBuilder, "irBuilder");
        return irExpression;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    public final IrFile getIrFile() {
        return this.irFile;
    }

    public AnnotationImplementationTransformer(BackendContext context, IrFile irFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.irFile = irFile;
        this.implementations = new LinkedHashMap();
    }

    public final Map<IrClass, IrClass> getImplementations$ir_backend_common() {
        return this.implementations;
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        addConstructorBodyForCompatibility(declaration);
        return super.visitClassNew(declaration);
    }

    protected final void addConstructorBodyForCompatibility(IrClass irClass) {
        IrConstructor irConstructor;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        if (IrUtilsKt.isAnnotationClass(irClass) && (irConstructor = (IrConstructor) SequencesKt.singleOrNull(IrUtilsKt.getConstructors(irClass))) != null && irConstructor.getBody() == null) {
            irClass.setModality(Modality.OPEN);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irClass.getSymbol(), 0, 0, 6, (Object) null);
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), -2, -2);
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyClass().getOwner()))));
            irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), irClass.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
            irConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getType());
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            return super.visitConstructorCall(expression);
        }
        if (!IrUtilsKt.isAnnotationClass(owner)) {
            return super.visitConstructorCall(expression);
        }
        if (!expression.getSymbol().getOwner().getIsPrimary()) {
            throw new IllegalArgumentException("Non-primary constructors of annotations are not supported".toString());
        }
        Map<IrClass, IrClass> map = this.implementations;
        IrClass irClassCreateAnnotationImplementation = map.get(owner);
        if (irClassCreateAnnotationImplementation == null) {
            irClassCreateAnnotationImplementation = createAnnotationImplementation(owner);
            map.put(owner, irClassCreateAnnotationImplementation);
        }
        IrClass irClass = irClassCreateAnnotationImplementation;
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, expression.getStartOffset(), expression.getEndOffset(), IrUtilsKt.getDefaultType(irClass), chooseConstructor(irClass, expression).getSymbol(), null, 16, null);
        moveValueArgumentsUsingNames(expression, irConstructorCallImplFromSymbolOwner$default);
        transformChildrenVoid(irConstructorCallImplFromSymbolOwner$default);
        return irConstructorCallImplFromSymbolOwner$default;
    }

    private final void moveValueArgumentsUsingNames(IrConstructorCall source, IrConstructorCall destination) {
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default;
        IrClass owner;
        List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(source);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(argumentsWithIr, 10)), 16));
        Iterator<T> it = argumentsWithIr.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            linkedHashMap.put(((IrValueParameter) pair.component1()).getName(), (IrExpression) pair.component2());
        }
        int i = 0;
        for (Object obj : destination.getSymbol().getOwner().getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            IrExpression irExpression = (IrExpression) linkedHashMap.get(irValueParameter.getName());
            if (irValueParameter.getDefaultValue() == null && irExpression == null) {
                Object name2 = null;
                if (!IrTypeUtilsKt.isBoxedArray(irValueParameter.getType()) && !org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isPrimitiveArray(irValueParameter.getType()) && !org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isUnsignedArray(irValueParameter.getType())) {
                    StringBuilder sb = new StringBuilder("Usage of default value argument for this annotation is not yet possible.\nPlease specify value for '");
                    IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(source.getType());
                    if (classOrNull != null && (owner = classOrNull.getOwner()) != null) {
                        name2 = owner.getName();
                    }
                    throw new IllegalStateException(sb.append(name2).append('.').append(irValueParameter.getName()).append("' explicitly").toString().toString());
                }
                IrType type = irValueParameter.getType();
                if (IrTypeUtilsKt.isBoxedArray(type)) {
                    IrSimpleFunctionSymbol arrayOfNulls = this.context.getIr().getSymbols().getArrayOfNulls();
                    irConstructorCallImplFromSymbolOwner$default = IrCallImpl.INSTANCE.fromSymbolOwner(source.getStartOffset(), source.getEndOffset(), type, arrayOfNulls, (240 & 16) != 0 ? arrayOfNulls.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? arrayOfNulls.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
                } else {
                    IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(type);
                    Intrinsics.checkNotNull(classOrNull2);
                    boolean z = false;
                    for (IrConstructorSymbol irConstructorSymbol : IrUtilsKt.getConstructors(classOrNull2)) {
                        IrConstructorSymbol irConstructorSymbol2 = irConstructorSymbol;
                        if (irConstructorSymbol2.getOwner().getValueParameters().size() == 1 && Intrinsics.areEqual(((IrValueParameter) CollectionsKt.single((List) irConstructorSymbol2.getOwner().getValueParameters())).getType(), this.context.getIrBuiltIns().getIntType())) {
                            if (z) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            name2 = irConstructorSymbol;
                            z = true;
                        }
                    }
                    if (!z) {
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                    irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, source.getStartOffset(), source.getEndOffset(), type, (IrConstructorSymbol) name2, null, 16, null);
                }
                irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, IrConstImpl.INSTANCE.m7211int(source.getStartOffset(), source.getEndOffset(), this.context.getIrBuiltIns().getIntType(), 0));
                destination.putValueArgument(i, irConstructorCallImplFromSymbolOwner$default);
                return;
            }
            destination.putValueArgument(i, irExpression);
            i = i2;
        }
    }

    private final IrClass createAnnotationImplementation(IrClass annotationClass) {
        Scope scope;
        ScopeWithIr currentClass = getCurrentClass();
        IrDeclarationParent localDeclarationParent = (currentClass == null || (scope = currentClass.getScope()) == null) ? null : scope.getLocalDeclarationParent();
        IrDeclarationParent irDeclarationParent = localDeclarationParent instanceof IrClass ? (IrClass) localDeclarationParent : null;
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(annotationClass);
        Intrinsics.checkNotNull(fqNameWhenAvailable);
        String strAsString = fqNameWhenAvailable.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Name nameIdentifier = Name.identifier("annotationImpl$" + StringsKt.replace$default(strAsString, '.', '_', false, 4, (Object) null) + SamWrapperCodegen.SAM_WRAPPER_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        irClassBuilder.setStartOffset(-2);
        irClassBuilder.setEndOffset(-2);
        irClassBuilder.setName(nameIdentifier);
        irClassBuilder.setOrigin(AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION());
        DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
        Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
        irClassBuilder.setVisibility(INTERNAL);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        if (irDeclarationParent == null) {
            irDeclarationParent = this.irFile;
        }
        if (irDeclarationParent == null) {
            throw new IllegalStateException("irFile in transformer should be specified when creating synthetic implementation".toString());
        }
        irClassBuildClass.setParent(irDeclarationParent);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        irClassBuildClass.setSuperTypes(CollectionsKt.listOf(IrUtilsKt.getDefaultType(annotationClass)));
        platformSetup(irClassBuildClass);
        IrFactory factory = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(-2);
        irFunctionBuilder.setEndOffset(-2);
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClassBuildClass);
        implementAnnotationPropertiesAndConstructor(irClassBuildClass, annotationClass, irConstructorBuildConstructor);
        implementGeneratedFunctions(annotationClass, irClassBuildClass);
        implementPlatformSpecificParts(annotationClass, irClassBuildClass);
        return irClassBuildClass;
    }

    public final List<IrProperty> getAnnotationProperties(IrClass irClass) {
        SmartList smartListEmptyList;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrProperty) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            return arrayList2;
        }
        List<IrDeclaration> declarations2 = irClass.getDeclarations();
        if (declarations2.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj2 : declarations2) {
                if ((obj2 instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) obj2).getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE)) {
                    smartList.add(obj2);
                }
            }
            smartListEmptyList = smartList;
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = smartListEmptyList.iterator();
        while (it.hasNext()) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) it.next()).getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null) {
                arrayList3.add(owner);
            }
        }
        return arrayList3;
    }

    public final IrExpression generatedEquals(IrBlockBodyBuilder irBuilder, IrType type, IrExpression arg1, IrExpression arg2) {
        Intrinsics.checkNotNullParameter(irBuilder, "irBuilder");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        if (IrTypePredicatesKt.isArray(type) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isPrimitiveArray(type) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isUnsignedArray(type)) {
            IrFunctionSymbol arrayContentEqualsSymbol = getArrayContentEqualsSymbol(type);
            IrExpression irExpressionTransformArrayEqualsArgument = transformArrayEqualsArgument(arg1, type, irBuilder);
            IrExpression irExpressionTransformArrayEqualsArgument2 = transformArrayEqualsArgument(arg2, type, irBuilder);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBuilder, arrayContentEqualsSymbol);
            if (arrayContentEqualsSymbol.getOwner().getExtensionReceiverParameter() != null) {
                irFunctionAccessExpressionIrCall.setExtensionReceiver(irExpressionTransformArrayEqualsArgument);
                irFunctionAccessExpressionIrCall.putValueArgument(0, irExpressionTransformArrayEqualsArgument2);
            } else {
                irFunctionAccessExpressionIrCall.putValueArgument(0, irExpressionTransformArrayEqualsArgument);
                irFunctionAccessExpressionIrCall.putValueArgument(1, irExpressionTransformArrayEqualsArgument2);
            }
            return irFunctionAccessExpressionIrCall;
        }
        return ExpressionHelpersKt.irEquals$default(irBuilder, arg1, arg2, null, 4, null);
    }

    public boolean getForbidDirectFieldAccessInMethods() {
        return this.forbidDirectFieldAccessInMethods;
    }

    public void generateFunctionBodies(IrClass annotationClass, IrClass implClass, IrSimpleFunction eqFun, IrSimpleFunction hcFun, IrSimpleFunction toStringFun, AnnotationImplementationMemberGenerator generator) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(eqFun, "eqFun");
        Intrinsics.checkNotNullParameter(hcFun, "hcFun");
        Intrinsics.checkNotNullParameter(toStringFun, "toStringFun");
        Intrinsics.checkNotNullParameter(generator, "generator");
        List<IrProperty> annotationProperties = getAnnotationProperties(annotationClass);
        generator.generateEqualsUsingGetters(eqFun, IrUtilsKt.getDefaultType(annotationClass), annotationProperties);
        generator.generateHashCodeMethod(hcFun, annotationProperties);
        generator.generateToStringMethod(toStringFun, annotationProperties);
    }

    public final void implementGeneratedFunctions(IrClass annotationClass, IrClass implClass) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        MethodsFromAnyGeneratorForLowerings methodsFromAnyGeneratorForLowerings = new MethodsFromAnyGeneratorForLowerings(this.context, implClass, AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION());
        IrSimpleFunction irSimpleFunctionCreateEqualsMethodDeclaration = methodsFromAnyGeneratorForLowerings.createEqualsMethodDeclaration();
        IrSimpleFunction irSimpleFunctionCreateHashCodeMethodDeclaration = methodsFromAnyGeneratorForLowerings.createHashCodeMethodDeclaration();
        IrSimpleFunction irSimpleFunctionCreateToStringMethodDeclaration = methodsFromAnyGeneratorForLowerings.createToStringMethodDeclaration();
        if (!Intrinsics.areEqual(annotationClass, implClass)) {
            IrUtilsKt.addFakeOverrides$default(implClass, this.context.getTypeSystem(), null, null, 6, null);
        }
        BackendContext backendContext = this.context;
        StringBuilder sb = new StringBuilder(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(annotationClass);
        Intrinsics.checkNotNull(fqNameWhenAvailable);
        generateFunctionBodies(annotationClass, implClass, irSimpleFunctionCreateEqualsMethodDeclaration, irSimpleFunctionCreateHashCodeMethodDeclaration, irSimpleFunctionCreateToStringMethodDeclaration, new AnnotationImplementationMemberGenerator(backendContext, implClass, sb.append(fqNameWhenAvailable.asString()).toString(), getForbidDirectFieldAccessInMethods(), new Function4<IrBlockBodyBuilder, IrType, IrExpression, IrExpression, IrExpression>() { // from class: org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer$implementGeneratedFunctions$generator$1
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final IrExpression invoke(IrBlockBodyBuilder $receiver, IrType type, IrExpression a, IrExpression b) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                return this.this$0.generatedEquals($receiver, type, a, b);
            }
        }));
    }
}
