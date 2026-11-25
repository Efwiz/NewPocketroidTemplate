package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.descriptors.IrLocalDelegatedPropertyDelegateDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrLocalDelegatedPropertyDelegateDescriptorImpl;
import org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptorImpl;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.SymbolTableExtension;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.BackingFieldLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.ExpressionValue;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.OnceExpressionValue;
import org.jetbrains.kotlin.psi2ir.intermediate.VariableLValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DelegatedPropertyGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J*\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J0\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J-\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#H\u0082\bJ \u0010&\u001a\u00020'2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u0017H\u0002J \u0010*\u001a\u00020+2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u0017H\u0002JB\u0010,\u001a\u0006\u0012\u0002\b\u00030-2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0014\u0010/\u001a\u0010\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0017\u0018\u000100H\u0002J\u001c\u00102\u001a\u0004\u0018\u00010\t2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u00106\u001a\u0002072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u00108\u001a\u0002092\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001e\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J5\u0010>\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010?\u001a\u00020@2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#H\u0082\bJ4\u0010A\u001a\u00020%2\u0006\u0010B\u001a\u00020$2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\u0006\u0010C\u001a\u00020\t2\n\u0010D\u001a\u0006\u0012\u0002\b\u00030-H\u0002J4\u0010E\u001a\u00020%2\u0006\u0010F\u001a\u00020$2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010G\u001a\u00020!2\u0006\u0010C\u001a\u00020\t2\n\u0010D\u001a\u0006\u0012\u0002\b\u00030-H\u0002J(\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J0\u0010K\u001a\u00020L2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J&\u0010N\u001a\u00020O2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010P\u001a\u00020\u001dJ\u0018\u0010Q\u001a\u00020\u00172\u0006\u0010R\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010S\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010T\u001a\u00020U2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020YH\u0002¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DelegatedPropertyGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "createBackingFieldValueForDelegate", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "irDelegateField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "thisClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "ktDelegate", "Lorg/jetbrains/kotlin/psi/KtPropertyDelegate;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "createLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "irDelegateSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "createLocalPropertyAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getterDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "generateBody", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "createLocalPropertyDelegatedDescriptor", "Lorg/jetbrains/kotlin/ir/descriptors/IrLocalDelegatedPropertyDelegateDescriptor;", "delegateType", "kPropertyType", "createPropertyDelegateDescriptor", "Lorg/jetbrains/kotlin/ir/descriptors/IrPropertyDelegateDescriptor;", "createPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "referencedDescriptor", "typeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "createThisValueForDelegate", "createVariableValueForDelegate", "Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "irDelegate", "generateDelegateFieldForProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "generateDelegateVariableForLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "generateDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "ktProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "generateDelegatedPropertyAccessor", "accessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "generateDelegatedPropertyGetterBody", "irGetter", "delegateReceiverValue", "irPropertyReference", "generateDelegatedPropertySetterBody", "irSetter", "setterDescriptor", "generateInitializerBodyForPropertyDelegate", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "property", "generateInitializerForLocalDelegatedPropertyDelegate", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "delegateSymbol", "generateLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "scopeOwnerSymbol", "getDelegatedPropertyDelegateType", "delegatedPropertyDescriptor", "getKPropertyTypeForDelegatedProperty", "getKPropertyTypeForLocalDelegatedProperty", "Lorg/jetbrains/kotlin/types/SimpleType;", "updateNullThisRefValue", "", "conventionMethodCall", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedPropertyGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegatedPropertyGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelegatedPropertyGenerator(GeneratorContext context) {
        this(new DeclarationGenerator(context));
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final IrProperty generateDelegatedProperty(KtProperty ktProperty, KtPropertyDelegate ktDelegate, PropertyDescriptor propertyDescriptor) {
        KtProperty ktProperty2;
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(ktProperty, "ktProperty");
        Intrinsics.checkNotNullParameter(ktDelegate, "ktDelegate");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        KotlinType kPropertyTypeForDelegatedProperty = getKPropertyTypeForDelegatedProperty(propertyDescriptor);
        KtProperty ktProperty3 = ktProperty;
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(PsiUtilsKt.getStartOffsetSkippingComments(ktProperty3), PsiUtilsKt.getEndOffset(ktProperty3), IrDeclarationOrigin.DEFINED.INSTANCE, propertyDescriptor, true);
        irPropertyDeclareProperty.setBackingField(generateDelegateFieldForProperty(propertyDescriptor, kPropertyTypeForDelegatedProperty, ktDelegate));
        IrField backingField = irPropertyDeclareProperty.getBackingField();
        Intrinsics.checkNotNull(backingField);
        List<TypeParameterDescriptor> typeParameters = propertyDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(obj, ((TypeParameterDescriptor) obj).getDefaultType());
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
        IntermediateValue intermediateValueCreateBackingFieldValueForDelegate = createBackingFieldValueForDelegate(backingField.getSymbol(), containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null, ktDelegate, propertyDescriptor);
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        DelegatedPropertyGenerator delegatedPropertyGenerator = this;
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, getter);
        SymbolTable symbolTable = delegatedPropertyGenerator.getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionDeclareSimpleFunctionWithOverrides;
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
        KtProperty ktProperty4 = ktProperty;
        new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction3, ktProperty4, null, CollectionsKt.emptyList());
        if (getContext().getConfiguration().getGenerateBodies()) {
            ktProperty2 = ktProperty4;
            irSimpleFunction = irSimpleFunction2;
            irSimpleFunction.setBody(generateDelegatedPropertyGetterBody(irSimpleFunction3, ktDelegate, getter, intermediateValueCreateBackingFieldValueForDelegate, createPropertyReference(ktDelegate, kPropertyTypeForDelegatedProperty, propertyDescriptor, irSimpleFunction3.getSymbol(), linkedHashMap2)));
        } else {
            ktProperty2 = ktProperty4;
            irSimpleFunction = irSimpleFunction2;
        }
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        irPropertyDeclareProperty.setGetter(irSimpleFunction);
        if (propertyDescriptor.isVar()) {
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            Intrinsics.checkNotNull(setter);
            IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides2 = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, setter);
            SymbolTable symbolTable2 = delegatedPropertyGenerator.getContext().getSymbolTable();
            symbolTable2.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides2);
            IrSimpleFunction irSimpleFunction4 = irSimpleFunctionDeclareSimpleFunctionWithOverrides2;
            IrSimpleFunction irSimpleFunction5 = irSimpleFunction4;
            new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction5, ktProperty2, null, CollectionsKt.emptyList());
            if (getContext().getConfiguration().getGenerateBodies()) {
                irSimpleFunction4.setBody(generateDelegatedPropertySetterBody(irSimpleFunction5, ktDelegate, setter, intermediateValueCreateBackingFieldValueForDelegate, createPropertyReference(ktDelegate, kPropertyTypeForDelegatedProperty, propertyDescriptor, irSimpleFunction5.getSymbol(), linkedHashMap2)));
            }
            Unit unit2 = Unit.INSTANCE;
            symbolTable2.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides2);
            irPropertyDeclareProperty.setSetter(irSimpleFunction4);
        }
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irPropertyDeclareProperty);
        return irPropertyDeclareProperty;
    }

    private final KotlinType getKPropertyTypeForDelegatedProperty(PropertyDescriptor propertyDescriptor) {
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new ReceiverParameterDescriptor[]{propertyDescriptor.getExtensionReceiverParameter(), propertyDescriptor.mo6753getDispatchReceiverParameter()});
        ReflectionTypes reflectionTypes = getContext().getReflectionTypes();
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        List list = listListOfNotNull;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            KotlinType type = ((ReceiverParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            arrayList.add(type);
        }
        KotlinType type2 = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return reflectionTypes.getKPropertyType(empty, arrayList, type2, propertyDescriptor.isVar());
    }

    private final IrField generateDelegateFieldForProperty(final PropertyDescriptor propertyDescriptor, final KotlinType kPropertyType, final KtPropertyDelegate ktDelegate) {
        return (IrField) getContext().getTypeTranslator().withTypeErasure(propertyDescriptor, new Function0<IrField>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DelegatedPropertyGenerator.generateDelegateFieldForProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                final IrPropertyDelegateDescriptor irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor = DelegatedPropertyGenerator.this.createPropertyDelegateDescriptor(propertyDescriptor, DelegatedPropertyGenerator.this.getDelegatedPropertyDelegateType(propertyDescriptor, ktDelegate), kPropertyType);
                final int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDelegate);
                final int endOffset = PsiUtilsKt.getEndOffset(ktDelegate);
                final IrDeclarationOrigin.PROPERTY_DELEGATE property_delegate = IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE;
                DelegatedPropertyGenerator delegatedPropertyGenerator = DelegatedPropertyGenerator.this;
                KotlinType type = irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                final IrType irType = delegatedPropertyGenerator.toIrType(type);
                final DelegatedPropertyGenerator delegatedPropertyGenerator2 = DelegatedPropertyGenerator.this;
                final PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
                IrField irFieldDeclareField$default = SymbolTableExtension.declareField$default(DelegatedPropertyGenerator.this.getContext().getSymbolTable().getDescriptorExtension(), startOffsetSkippingComments, endOffset, property_delegate, irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor, irType, null, new Function1<IrFieldSymbol, IrField>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DelegatedPropertyGenerator.generateDelegateFieldForProperty.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrField invoke(IrFieldSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        IrFactory irFactory = delegatedPropertyGenerator2.getContext().getIrFactory();
                        int i = startOffsetSkippingComments;
                        int i2 = endOffset;
                        IrDeclarationOrigin.PROPERTY_DELEGATE property_delegate2 = property_delegate;
                        Name name2 = irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        DescriptorVisibility visibility = irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor.getVisibility();
                        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                        IrField irFieldCreateField = irFactory.createField(i, i2, (IrDeclarationOrigin) property_delegate2, name2, visibility, it, irType, !irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor.isVar(), irPropertyDelegateDescriptorCreatePropertyDelegateDescriptor.mo6753getDispatchReceiverParameter() == null, false);
                        irFieldCreateField.setMetadata(new DescriptorMetadataSource.Property(propertyDescriptor2));
                        return irFieldCreateField;
                    }
                }, 32, null);
                DelegatedPropertyGenerator delegatedPropertyGenerator3 = DelegatedPropertyGenerator.this;
                PropertyDescriptor propertyDescriptor3 = propertyDescriptor;
                KotlinType kotlinType = kPropertyType;
                KtPropertyDelegate ktPropertyDelegate = ktDelegate;
                if (delegatedPropertyGenerator3.getContext().getConfiguration().getGenerateBodies()) {
                    irFieldDeclareField$default.setInitializer(delegatedPropertyGenerator3.generateInitializerBodyForPropertyDelegate(propertyDescriptor3, kotlinType, ktPropertyDelegate, irFieldDeclareField$default.getSymbol()));
                }
                return irFieldDeclareField$default;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpressionBody generateInitializerBodyForPropertyDelegate(VariableDescriptorWithAccessors property, KotlinType kPropertyType, KtPropertyDelegate ktDelegate, IrSymbol scopeOwner) {
        KtExpression expression = ktDelegate.getExpression();
        Intrinsics.checkNotNull(expression);
        IrExpressionBody irExpressionBodyGenerateInitializerBody = getDeclarationGenerator().generateInitializerBody(scopeOwner, expression);
        DelegatedPropertyGenerator delegatedPropertyGenerator = this;
        WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(PROVIDE_DELEGATE_RESOLVED_CALL, "PROVIDE_DELEGATE_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.get(delegatedPropertyGenerator, PROVIDE_DELEGATE_RESOLVED_CALL, property);
        if (resolvedCall == null) {
            return irExpressionBodyGenerateInitializerBody;
        }
        StatementGenerator statementGeneratorCreateStatementGenerator = DeclarationGeneratorKt.createBodyGenerator$default(delegatedPropertyGenerator, scopeOwner, null, 2, null).createStatementGenerator();
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall);
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, new OnceExpressionValue(irExpressionBodyGenerateInitializerBody.getExpression()));
        callBuilderPregenerateCall.getIrValueArgumentsByIndex()[1] = createPropertyReference(ktDelegate, kPropertyType, property, scopeOwner, null);
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        return IrFactoryHelpersKt.createExpressionBody(getContext().getIrFactory(), CallGenerator.generateCall$default(new CallGenerator(statementGeneratorCreateStatementGenerator), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), callBuilderPregenerateCall, null, 8, null));
    }

    private final IntermediateValue createBackingFieldValueForDelegate(final IrFieldSymbol irDelegateField, final ClassDescriptor thisClass, final KtPropertyDelegate ktDelegate, PropertyDescriptor propertyDescriptor) {
        return (IntermediateValue) getContext().getTypeTranslator().withTypeErasure(propertyDescriptor, new Function0<BackingFieldLValue>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DelegatedPropertyGenerator.createBackingFieldValueForDelegate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final BackingFieldLValue invoke() {
                return new BackingFieldLValue(DelegatedPropertyGenerator.this.getContext(), PsiUtilsKt.getStartOffsetSkippingComments(ktDelegate), PsiUtilsKt.getEndOffset(ktDelegate), irDelegateField.getOwner().getType(), irDelegateField, DelegatedPropertyGenerator.this.createThisValueForDelegate(thisClass, ktDelegate), null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IntermediateValue createThisValueForDelegate(final ClassDescriptor thisClass, final KtPropertyDelegate ktDelegate) {
        ExpressionValue expressionValue;
        if (thisClass != null) {
            KotlinType type = thisClass.getThisAsReceiverParameter().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            final IrType irType = toIrType(type);
            expressionValue = new ExpressionValue(irType) { // from class: org.jetbrains.kotlin.psi2ir.generators.DelegatedPropertyGenerator$createThisValueForDelegate$lambda$7$$inlined$generateExpressionValue$1
                @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
                public IrExpression load() {
                    ReceiverParameterDescriptor thisAsReceiverParameter = thisClass.getThisAsReceiverParameter();
                    Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
                    int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDelegate);
                    int endOffset = PsiUtilsKt.getEndOffset(ktDelegate);
                    DelegatedPropertyGenerator delegatedPropertyGenerator = this;
                    KotlinType type2 = thisAsReceiverParameter.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    return new IrGetValueImpl(startOffsetSkippingComments, endOffset, delegatedPropertyGenerator.toIrType(type2), this.getContext().getSymbolTable().getDescriptorExtension().referenceValueParameter((ParameterDescriptor) thisAsReceiverParameter), null, 16, null);
                }
            };
        } else {
            expressionValue = null;
        }
        return expressionValue;
    }

    private final IrCallableReference<?> createPropertyReference(KtElement ktElement, KotlinType type, VariableDescriptorWithAccessors referencedDescriptor, IrSymbol scopeOwner, Map<TypeParameterDescriptor, ? extends KotlinType> typeArguments) {
        return new ReflectionReferencesGenerator(DeclarationGeneratorKt.createBodyGenerator$default(this, scopeOwner, null, 2, null).createStatementGenerator()).generateCallableReference(ktElement, type, referencedDescriptor, typeArguments, IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE.INSTANCE);
    }

    private final IrLocalDelegatedPropertyReference createLocalDelegatedPropertyReference(KtElement ktElement, KotlinType type, VariableDescriptorWithAccessors variableDescriptor, IrVariableSymbol irDelegateSymbol, IrSymbol scopeOwner) {
        KtElement ktElement2 = ktElement;
        return new ReflectionReferencesGenerator(DeclarationGeneratorKt.createBodyGenerator$default(this, scopeOwner, null, 2, null).createStatementGenerator()).generateLocalDelegatedPropertyReference(PsiUtilsKt.getStartOffsetSkippingComments(ktElement2), PsiUtilsKt.getEndOffset(ktElement2), type, variableDescriptor, irDelegateSymbol, IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE.INSTANCE);
    }

    public final IrLocalDelegatedProperty generateLocalDelegatedProperty(KtProperty ktProperty, KtPropertyDelegate ktDelegate, VariableDescriptorWithAccessors variableDescriptor, IrSymbol scopeOwnerSymbol) {
        Intrinsics.checkNotNullParameter(ktProperty, "ktProperty");
        Intrinsics.checkNotNullParameter(ktDelegate, "ktDelegate");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        SimpleType kPropertyTypeForLocalDelegatedProperty = getKPropertyTypeForLocalDelegatedProperty(variableDescriptor);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        KtProperty ktProperty2 = ktProperty;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktProperty2);
        int endOffset = PsiUtilsKt.getEndOffset(ktProperty2);
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrLocalDelegatedProperty irLocalDelegatedPropertyDeclareLocalDelegatedProperty = descriptorExtension.declareLocalDelegatedProperty(startOffsetSkippingComments, endOffset, defined, variableDescriptor, toIrType(type));
        SimpleType simpleType = kPropertyTypeForLocalDelegatedProperty;
        irLocalDelegatedPropertyDeclareLocalDelegatedProperty.setDelegate(generateDelegateVariableForLocalDelegatedProperty(ktDelegate, variableDescriptor, simpleType, scopeOwnerSymbol));
        IrVariable delegate = irLocalDelegatedPropertyDeclareLocalDelegatedProperty.getDelegate();
        VariableAccessorDescriptor getter = variableDescriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        VariableLValue variableLValueCreateVariableValueForDelegate = createVariableValueForDelegate(delegate.getSymbol(), ktDelegate);
        DelegatedPropertyGenerator delegatedPropertyGenerator = this;
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, getter);
        SymbolTable symbolTable = delegatedPropertyGenerator.getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionWithOverrides;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        KtPropertyDelegate ktPropertyDelegate2 = ktDelegate;
        new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction2, ktPropertyDelegate2, null, CollectionsKt.emptyList());
        VariableLValue variableLValue = variableLValueCreateVariableValueForDelegate;
        KtPropertyDelegate ktPropertyDelegate3 = ktDelegate;
        irSimpleFunction.setBody(generateDelegatedPropertyGetterBody(irSimpleFunction2, ktDelegate, getter, variableLValue, createLocalDelegatedPropertyReference(ktPropertyDelegate3, simpleType, variableDescriptor, delegate.getSymbol(), irSimpleFunction2.getSymbol())));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        irLocalDelegatedPropertyDeclareLocalDelegatedProperty.setGetter(irSimpleFunction);
        if (variableDescriptor.isVar()) {
            VariableAccessorDescriptor setter = variableDescriptor.getSetter();
            Intrinsics.checkNotNull(setter);
            IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides2 = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, setter);
            SymbolTable symbolTable2 = delegatedPropertyGenerator.getContext().getSymbolTable();
            symbolTable2.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides2);
            IrSimpleFunction irSimpleFunction3 = irSimpleFunctionDeclareSimpleFunctionWithOverrides2;
            IrSimpleFunction irSimpleFunction4 = irSimpleFunction3;
            new FunctionGenerator(getDeclarationGenerator()).generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction4, ktPropertyDelegate2, null, CollectionsKt.emptyList());
            irSimpleFunction3.setBody(generateDelegatedPropertySetterBody(irSimpleFunction4, ktDelegate, setter, variableLValue, createLocalDelegatedPropertyReference(ktPropertyDelegate3, simpleType, variableDescriptor, delegate.getSymbol(), irSimpleFunction4.getSymbol())));
            Unit unit2 = Unit.INSTANCE;
            symbolTable2.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides2);
            irLocalDelegatedPropertyDeclareLocalDelegatedProperty.setSetter(irSimpleFunction3);
        }
        return irLocalDelegatedPropertyDeclareLocalDelegatedProperty;
    }

    private final IrVariable generateDelegateVariableForLocalDelegatedProperty(KtPropertyDelegate ktDelegate, VariableDescriptorWithAccessors variableDescriptor, KotlinType kPropertyType, IrSymbol scopeOwner) {
        IrLocalDelegatedPropertyDelegateDescriptor irLocalDelegatedPropertyDelegateDescriptorCreateLocalPropertyDelegatedDescriptor = createLocalPropertyDelegatedDescriptor(variableDescriptor, getDelegatedPropertyDelegateType(variableDescriptor, ktDelegate), kPropertyType);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate);
        int endOffset = PsiUtilsKt.getEndOffset(ktPropertyDelegate);
        IrDeclarationOrigin.PROPERTY_DELEGATE property_delegate = IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE;
        IrLocalDelegatedPropertyDelegateDescriptor irLocalDelegatedPropertyDelegateDescriptor = irLocalDelegatedPropertyDelegateDescriptorCreateLocalPropertyDelegatedDescriptor;
        KotlinType type = irLocalDelegatedPropertyDelegateDescriptorCreateLocalPropertyDelegatedDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrVariable irVariableDeclareVariable = descriptorExtension.declareVariable(startOffsetSkippingComments, endOffset, property_delegate, irLocalDelegatedPropertyDelegateDescriptor, toIrType(type));
        irVariableDeclareVariable.setInitializer(generateInitializerForLocalDelegatedPropertyDelegate(variableDescriptor, kPropertyType, ktDelegate, irVariableDeclareVariable.getSymbol(), scopeOwner));
        return irVariableDeclareVariable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType getDelegatedPropertyDelegateType(VariableDescriptorWithAccessors delegatedPropertyDescriptor, KtPropertyDelegate ktDelegate) {
        ErrorType typeInferredByFrontend;
        DelegatedPropertyGenerator delegatedPropertyGenerator = this;
        WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(PROVIDE_DELEGATE_RESOLVED_CALL, "PROVIDE_DELEGATE_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.get(delegatedPropertyGenerator, PROVIDE_DELEGATE_RESOLVED_CALL, delegatedPropertyDescriptor);
        if (resolvedCall != null) {
            typeInferredByFrontend = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
            Intrinsics.checkNotNull(typeInferredByFrontend);
        } else if (getContext().getConfiguration().getGenerateBodies()) {
            KtExpression expression = ktDelegate.getExpression();
            Intrinsics.checkNotNull(expression);
            typeInferredByFrontend = GeneratorKt.getTypeInferredByFrontendOrFail(delegatedPropertyGenerator, expression);
        } else {
            KtExpression expression2 = ktDelegate.getExpression();
            Intrinsics.checkNotNull(expression2);
            typeInferredByFrontend = GeneratorKt.getTypeInferredByFrontend(delegatedPropertyGenerator, expression2);
            if (typeInferredByFrontend == null) {
                ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_TYPE;
                KtExpression expression3 = ktDelegate.getExpression();
                Intrinsics.checkNotNull(expression3);
                String text = expression3.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                typeInferredByFrontend = ErrorUtils.createErrorType(errorTypeKind, text);
            }
        }
        Intrinsics.checkNotNull(typeInferredByFrontend);
        if (getContext().getConfiguration().getGenerateBodies()) {
            return typeInferredByFrontend;
        }
        if (ktDelegate.getExpression() instanceof KtObjectLiteralExpression) {
            Collection<KotlinType> collectionMo7438getSupertypes = typeInferredByFrontend.getConstructor().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            Object objFirst = CollectionsKt.first(collectionMo7438getSupertypes);
            Intrinsics.checkNotNull(objFirst);
            return (KotlinType) objFirst;
        }
        return TypeUtilsKt.replaceArgumentsWithStarProjections(typeInferredByFrontend);
    }

    private final IrExpression generateInitializerForLocalDelegatedPropertyDelegate(VariableDescriptorWithAccessors variableDescriptor, KotlinType kPropertyType, KtPropertyDelegate ktDelegate, IrVariableSymbol delegateSymbol, IrSymbol scopeOwner) {
        KtExpression expression = ktDelegate.getExpression();
        Intrinsics.checkNotNull(expression);
        DelegatedPropertyGenerator delegatedPropertyGenerator = this;
        IrExpression irExpressionGenerateExpression = DeclarationGeneratorKt.createBodyGenerator$default(delegatedPropertyGenerator, scopeOwner, null, 2, null).createStatementGenerator().generateExpression(expression);
        WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(PROVIDE_DELEGATE_RESOLVED_CALL, "PROVIDE_DELEGATE_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.get(delegatedPropertyGenerator, PROVIDE_DELEGATE_RESOLVED_CALL, variableDescriptor);
        if (resolvedCall == null) {
            return irExpressionGenerateExpression;
        }
        StatementGenerator statementGeneratorCreateStatementGenerator = DeclarationGeneratorKt.createBodyGenerator$default(delegatedPropertyGenerator, scopeOwner, null, 2, null).createStatementGenerator();
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall);
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, new OnceExpressionValue(irExpressionGenerateExpression));
        callBuilderPregenerateCall.getIrValueArgumentsByIndex()[1] = createLocalDelegatedPropertyReference(ktDelegate, kPropertyType, variableDescriptor, delegateSymbol, scopeOwner);
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        return CallGenerator.generateCall$default(new CallGenerator(statementGeneratorCreateStatementGenerator), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), callBuilderPregenerateCall, null, 8, null);
    }

    private final VariableLValue createVariableValueForDelegate(IrVariableSymbol irDelegate, KtPropertyDelegate ktDelegate) {
        KtPropertyDelegate ktPropertyDelegate = ktDelegate;
        return new VariableLValue(getContext(), PsiUtilsKt.getStartOffsetSkippingComments(ktPropertyDelegate), PsiUtilsKt.getEndOffset(ktPropertyDelegate), irDelegate, irDelegate.getOwner().getType(), null, 32, null);
    }

    private final IrLocalDelegatedPropertyDelegateDescriptor createLocalPropertyDelegatedDescriptor(VariableDescriptorWithAccessors variableDescriptor, KotlinType delegateType, KotlinType kPropertyType) {
        return new IrLocalDelegatedPropertyDelegateDescriptorImpl(variableDescriptor, delegateType, kPropertyType);
    }

    private final SimpleType getKPropertyTypeForLocalDelegatedProperty(VariableDescriptorWithAccessors variableDescriptor) {
        ReflectionTypes reflectionTypes = getContext().getReflectionTypes();
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        List<? extends KotlinType> listEmptyList = CollectionsKt.emptyList();
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return reflectionTypes.getKPropertyType(empty, listEmptyList, type, variableDescriptor.isVar());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrPropertyDelegateDescriptor createPropertyDelegateDescriptor(PropertyDescriptor propertyDescriptor, KotlinType delegateType, KotlinType kPropertyType) {
        return new IrPropertyDelegateDescriptorImpl(propertyDescriptor, delegateType, kPropertyType);
    }

    private final IrBody generateDelegatedPropertyGetterBody(IrFunction irGetter, KtPropertyDelegate ktDelegate, VariableAccessorDescriptor getterDescriptor, IntermediateValue delegateReceiverValue, IrCallableReference<?> irPropertyReference) {
        BodyGenerator bodyGeneratorCreateBodyGenerator$default = DeclarationGeneratorKt.createBodyGenerator$default(this, irGetter.getSymbol(), null, 2, null);
        KtExpression expression = ktDelegate.getExpression();
        Intrinsics.checkNotNull(expression);
        KtExpression ktExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
        BodyGenerator bodyGenerator = bodyGeneratorCreateBodyGenerator$default;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(bodyGenerator.getContext(), bodyGenerator.getScope(), startOffsetSkippingComments, endOffset);
        StatementGenerator statementGeneratorCreateStatementGenerator = bodyGeneratorCreateBodyGenerator$default.createStatementGenerator();
        WritableSlice<VariableAccessorDescriptor, ResolvedCall<FunctionDescriptor>> DELEGATED_PROPERTY_RESOLVED_CALL = BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(DELEGATED_PROPERTY_RESOLVED_CALL, "DELEGATED_PROPERTY_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(bodyGeneratorCreateBodyGenerator$default, DELEGATED_PROPERTY_RESOLVED_CALL, getterDescriptor);
        Intrinsics.checkNotNull(resolvedCall);
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall);
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, delegateReceiverValue);
        updateNullThisRefValue(callBuilderPregenerateCall);
        callBuilderPregenerateCall.getIrValueArgumentsByIndex()[1] = irPropertyReference;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, CallGenerator.generateCall$default(new CallGenerator(statementGeneratorCreateStatementGenerator), startOffsetSkippingComments, endOffset, callBuilderPregenerateCall, null, 8, null)));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    private final IrBody generateDelegatedPropertySetterBody(IrFunction irSetter, KtPropertyDelegate ktDelegate, VariableAccessorDescriptor setterDescriptor, IntermediateValue delegateReceiverValue, IrCallableReference<?> irPropertyReference) {
        BodyGenerator bodyGeneratorCreateBodyGenerator$default = DeclarationGeneratorKt.createBodyGenerator$default(this, irSetter.getSymbol(), null, 2, null);
        KtExpression expression = ktDelegate.getExpression();
        Intrinsics.checkNotNull(expression);
        KtExpression ktExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
        BodyGenerator bodyGenerator = bodyGeneratorCreateBodyGenerator$default;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(bodyGenerator.getContext(), bodyGenerator.getScope(), startOffsetSkippingComments, endOffset);
        StatementGenerator statementGeneratorCreateStatementGenerator = bodyGeneratorCreateBodyGenerator$default.createStatementGenerator();
        WritableSlice<VariableAccessorDescriptor, ResolvedCall<FunctionDescriptor>> DELEGATED_PROPERTY_RESOLVED_CALL = BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(DELEGATED_PROPERTY_RESOLVED_CALL, "DELEGATED_PROPERTY_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(bodyGeneratorCreateBodyGenerator$default, DELEGATED_PROPERTY_RESOLVED_CALL, setterDescriptor);
        Intrinsics.checkNotNull(resolvedCall);
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall);
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, delegateReceiverValue);
        updateNullThisRefValue(callBuilderPregenerateCall);
        callBuilderPregenerateCall.getIrValueArgumentsByIndex()[1] = irPropertyReference;
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        callBuilderPregenerateCall.getIrValueArgumentsByIndex()[2] = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irSetter.getValueParameters().get(0));
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, CallGenerator.generateCall$default(new CallGenerator(statementGeneratorCreateStatementGenerator), startOffsetSkippingComments, endOffset, callBuilderPregenerateCall, null, 8, null)));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    private final void updateNullThisRefValue(CallBuilder conventionMethodCall) {
        IrExpression irExpression = conventionMethodCall.getIrValueArgumentsByIndex()[0];
        if ((irExpression instanceof IrConstImpl) && Intrinsics.areEqual(((IrConstImpl) irExpression).getKind(), IrConstKind.Null.INSTANCE)) {
            conventionMethodCall.getIrValueArgumentsByIndex()[0] = IrConstImpl.INSTANCE.constNull(-1, -1, getContext().getIrBuiltIns().getNothingNType());
        }
    }
}
