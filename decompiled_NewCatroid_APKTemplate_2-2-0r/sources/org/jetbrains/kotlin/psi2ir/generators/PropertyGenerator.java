package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.SymbolTableExtension;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ResolveUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: PropertyGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0002J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0007H\u0002J/\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u00072\u0014\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010!0 H\u0082\bJ\u000e\u0010\"\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\u001a\u0010)\u001a\u0004\u0018\u00010\u00182\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0007H\u0002J\u0018\u0010*\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u0007H\u0002J*\u0010+\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u00072\b\u0010'\u001a\u0004\u0018\u00010(2\b\b\u0002\u0010-\u001a\u00020.J4\u0010/\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010-\u001a\u00020.2\u0014\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010!0 J\u0010\u00100\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0019H\u0002J\u0014\u00101\u001a\u00020.*\u00020\u00072\u0006\u00102\u001a\u000203H\u0002J\u0014\u00104\u001a\u000205*\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0007H\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/PropertyGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "fieldVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFieldVisibility", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "generateDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "ktProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "ktDelegate", "Lorg/jetbrains/kotlin/psi/KtPropertyDelegate;", "propertyDescriptor", "generateDestructuringDeclarationEntryAsPropertyDeclaration", "ktEntry", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "generateFakeOverrideProperty", "ktElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "generateGetterIfRequired", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "property", "generatePropertyBackingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "ktPropertyElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "generateInitializer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "generatePropertyDeclaration", "generatePropertyForPrimaryConstructorParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ktParameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "generateSetterIfRequired", "generateSimpleProperty", "generateSyntheticProperty", "ktDeclarationContainer", "generateSyntheticAccessors", "", "generateSyntheticPropertyWithInitializer", "getPropertyDescriptor", "actuallyHasBackingField", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "generateOverrides", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    public final IrProperty generatePropertyDeclaration(KtProperty ktProperty) {
        Intrinsics.checkNotNullParameter(ktProperty, "ktProperty");
        KtProperty ktProperty2 = ktProperty;
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(ktProperty2);
        KtPropertyDelegate delegate = ktProperty.getDelegate();
        if (delegate != null) {
            return generateDelegatedProperty(ktProperty, delegate, propertyDescriptor);
        }
        return generateSimpleProperty(ktProperty2, propertyDescriptor);
    }

    public final IrProperty generateDestructuringDeclarationEntryAsPropertyDeclaration(KtDestructuringDeclarationEntry ktEntry) {
        Intrinsics.checkNotNullParameter(ktEntry, "ktEntry");
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry = ktEntry;
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(ktDestructuringDeclarationEntry);
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktEntry;
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry2), PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry2), IrDeclarationOrigin.DEFINED.INSTANCE, propertyDescriptor, false);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irPropertyDeclareProperty);
        IrProperty irProperty = irPropertyDeclareProperty;
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry3 = ktEntry;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclarationEntry3);
        int endOffset = PsiUtilsKt.getEndOffset(ktDestructuringDeclarationEntry3);
        IrDeclarationOrigin.PROPERTY_BACKING_FIELD property_backing_field = IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE;
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrField irFieldDeclareField$default = SymbolTableExtension.declareField$default(descriptorExtension, startOffsetSkippingComments, endOffset, property_backing_field, propertyDescriptor, toIrType(type), getFieldVisibility(propertyDescriptor), null, 64, null);
        irFieldDeclareField$default.setInitializer(null);
        irProperty.setBackingField(irFieldDeclareField$default);
        irProperty.setGetter(generateGetterIfRequired(ktDestructuringDeclarationEntry, propertyDescriptor));
        irProperty.setSetter(generateSetterIfRequired(ktDestructuringDeclarationEntry, propertyDescriptor));
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irProperty);
        generateOverrides(irProperty, propertyDescriptor);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irPropertyDeclareProperty);
        return irProperty;
    }

    public final IrDeclaration generatePropertyForPrimaryConstructorParameter(KtParameter ktParameter, IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(ktParameter, "ktParameter");
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        WritableSlice<PsiElement, PropertyDescriptor> PRIMARY_CONSTRUCTOR_PARAMETER = BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER;
        Intrinsics.checkNotNullExpressionValue(PRIMARY_CONSTRUCTOR_PARAMETER, "PRIMARY_CONSTRUCTOR_PARAMETER");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) GeneratorKt.getOrFail(this, PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter);
        Intrinsics.checkNotNull(propertyDescriptor);
        return generateSyntheticProperty$default(this, ktParameter, propertyDescriptor, irValueParameter, false, 8, null);
    }

    public static /* synthetic */ IrProperty generateSyntheticProperty$default(PropertyGenerator propertyGenerator, KtElement ktElement, PropertyDescriptor propertyDescriptor, IrValueParameter irValueParameter, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return propertyGenerator.generateSyntheticProperty(ktElement, propertyDescriptor, irValueParameter, z);
    }

    public final IrProperty generateSyntheticProperty(final KtElement ktDeclarationContainer, final PropertyDescriptor propertyDescriptor, final IrValueParameter irValueParameter, boolean generateSyntheticAccessors) {
        Intrinsics.checkNotNullParameter(ktDeclarationContainer, "ktDeclarationContainer");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        final IrType irType = toIrType(type);
        return generateSyntheticPropertyWithInitializer(ktDeclarationContainer, propertyDescriptor, generateSyntheticAccessors, new Function1<IrField, IrExpressionBody>() { // from class: org.jetbrains.kotlin.psi2ir.generators.PropertyGenerator.generateSyntheticProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpressionBody invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (irValueParameter == null) {
                    return null;
                }
                if (!this.getContext().getConfiguration().getSkipBodies() || DescriptorUtils.isAnnotationClass(propertyDescriptor.getContainingModule())) {
                    return IrFactoryHelpersKt.createExpressionBody(this.getContext().getIrFactory(), new IrGetValueImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktDeclarationContainer), PsiUtilsKt.getEndOffset(ktDeclarationContainer), irType, irValueParameter.getSymbol(), IrStatementOrigin.INITIALIZE_PROPERTY_FROM_PARAMETER.INSTANCE));
                }
                return null;
            }
        });
    }

    public final IrProperty generateSyntheticPropertyWithInitializer(KtElement ktDeclarationContainer, PropertyDescriptor propertyDescriptor, boolean generateSyntheticAccessors, Function1<? super IrField, ? extends IrExpressionBody> generateInitializer) {
        Intrinsics.checkNotNullParameter(ktDeclarationContainer, "ktDeclarationContainer");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(generateInitializer, "generateInitializer");
        KtElement ktElement = ktDeclarationContainer;
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(PsiUtilsKt.getStartOffsetSkippingComments(ktElement), PsiUtilsKt.getEndOffset(ktElement), IrDeclarationOrigin.DEFINED.INSTANCE, propertyDescriptor, false);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktElement);
        int endOffset = PsiUtilsKt.getEndOffset(ktElement);
        IrDeclarationOrigin.PROPERTY_BACKING_FIELD property_backing_field = IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE;
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrField irFieldDeclareField$default = SymbolTableExtension.declareField$default(descriptorExtension, startOffsetSkippingComments, endOffset, property_backing_field, propertyDescriptor, toIrType(type), getFieldVisibility(propertyDescriptor), null, 64, null);
        irFieldDeclareField$default.setInitializer(generateInitializer.invoke(irFieldDeclareField$default));
        irPropertyDeclareProperty.setBackingField(irFieldDeclareField$default);
        PropertyGetterDescriptorImpl getter = propertyDescriptor.getGetter();
        if (getter == null) {
            if (!generateSyntheticAccessors) {
                throw new AssertionError("Property declared in primary constructor has no getter: " + propertyDescriptor);
            }
            PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = new PropertyGetterDescriptorImpl(propertyDescriptor, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.PUBLIC, false, false, false, CallableMemberDescriptor.Kind.SYNTHESIZED, null, propertyDescriptor.getSourceElement());
            propertyGetterDescriptorImpl.initialize(propertyDescriptor.getType());
            getter = propertyGetterDescriptorImpl;
        }
        Intrinsics.checkNotNull(getter);
        irPropertyDeclareProperty.setGetter(new FunctionGenerator(getDeclarationGenerator()).generateDefaultAccessorForPrimaryConstructorParameter(getter, ktDeclarationContainer));
        if (propertyDescriptor.isVar()) {
            PropertySetterDescriptorImpl setter = propertyDescriptor.getSetter();
            if (setter == null) {
                if (!generateSyntheticAccessors) {
                    throw new AssertionError("Property declared in primary constructor has no setter: " + propertyDescriptor);
                }
                PropertySetterDescriptorImpl propertySetterDescriptorImpl = new PropertySetterDescriptorImpl(propertyDescriptor, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.PUBLIC, false, false, false, CallableMemberDescriptor.Kind.SYNTHESIZED, null, propertyDescriptor.getSourceElement());
                Annotations empty = Annotations.INSTANCE.getEMPTY();
                Name name2 = StandardNames.DEFAULT_VALUE_PARAMETER;
                KotlinType type2 = propertyDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                propertySetterDescriptorImpl.initialize(new ValueParameterDescriptorImpl(propertySetterDescriptorImpl, null, 0, empty, name2, type2, false, false, false, null, NO_SOURCE));
                setter = propertySetterDescriptorImpl;
            }
            Intrinsics.checkNotNull(setter);
            irPropertyDeclareProperty.setSetter(new FunctionGenerator(getDeclarationGenerator()).generateDefaultAccessorForPrimaryConstructorParameter(setter, ktDeclarationContainer));
        }
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irPropertyDeclareProperty);
        generateOverrides(irPropertyDeclareProperty, propertyDescriptor);
        return irPropertyDeclareProperty;
    }

    private final IrProperty generateDelegatedProperty(KtProperty ktProperty, KtPropertyDelegate ktDelegate, PropertyDescriptor propertyDescriptor) {
        IrProperty irPropertyGenerateDelegatedProperty = new DelegatedPropertyGenerator(getDeclarationGenerator()).generateDelegatedProperty(ktProperty, ktDelegate, propertyDescriptor);
        generateOverrides(irPropertyGenerateDelegatedProperty, propertyDescriptor);
        return irPropertyGenerateDelegatedProperty;
    }

    private final boolean actuallyHasBackingField(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        return ResolveUtilKt.hasBackingField(propertyDescriptor, bindingContext) || getContext().getExtensions().isPropertyWithPlatformField(propertyDescriptor);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:21:0x0105  */
    private final IrProperty generateSimpleProperty(KtVariableDeclaration ktProperty, PropertyDescriptor propertyDescriptor) {
        KtVariableDeclaration ktVariableDeclaration = ktProperty;
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(PsiUtilsKt.getStartOffsetSkippingComments(ktVariableDeclaration), PsiUtilsKt.getEndOffset(ktVariableDeclaration), IrDeclarationOrigin.DEFINED.INSTANCE, propertyDescriptor, false);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irPropertyDeclareProperty);
        IrProperty irProperty = irPropertyDeclareProperty;
        IrField irField = null;
        irExpressionBodyGenerateInitializerBody = null;
        IrExpressionBody irExpressionBodyGenerateInitializerBody = null;
        if (actuallyHasBackingField(propertyDescriptor, getContext().getBindingContext())) {
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            KtVariableDeclaration ktVariableDeclaration2 = ktProperty;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktVariableDeclaration2);
            int endOffset = PsiUtilsKt.getEndOffset(ktVariableDeclaration2);
            IrDeclarationOrigin.PROPERTY_BACKING_FIELD property_backing_field = IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE;
            KotlinType type = propertyDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            IrField irFieldDeclareField$default = SymbolTableExtension.declareField$default(descriptorExtension, startOffsetSkippingComments, endOffset, property_backing_field, propertyDescriptor, toIrType(type), getFieldVisibility(propertyDescriptor), null, 64, null);
            KtExpression initializer = ktProperty.getInitializer();
            if (initializer != null) {
                ConstantValue<?> compileTimeInitializer = propertyDescriptor.mo6754getCompileTimeInitializer();
                if (compileTimeInitializer != null) {
                    CompileTimeConstant compileTimeConstant = (CompileTimeConstant) getContext().getBindingContext().get(BindingContext.COMPILE_TIME_VALUE, initializer);
                    if (propertyDescriptor.isConst() || (compileTimeConstant != null && !compileTimeConstant.getUsesNonConstValAsConstant() && (!compileTimeConstant.getUsesVariableAsConstant() || getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineConstVals)))) {
                        IrFactory irFactory = getContext().getIrFactory();
                        ConstantValueGenerator constantValueGenerator = getContext().getConstantValueGenerator();
                        Intrinsics.checkNotNull(initializer);
                        KtExpression ktExpression = initializer;
                        irExpressionBodyGenerateInitializerBody = IrFactoryHelpersKt.createExpressionBody(irFactory, constantValueGenerator.generateConstantValueAsExpression(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression), compileTimeInitializer));
                    } else if (!getContext().getConfiguration().getSkipBodies()) {
                        DeclarationGenerator declarationGenerator = getDeclarationGenerator();
                        IrFieldSymbol symbol = irFieldDeclareField$default.getSymbol();
                        Intrinsics.checkNotNull(initializer);
                        irExpressionBodyGenerateInitializerBody = declarationGenerator.generateInitializerBody(symbol, initializer);
                    }
                } else if (!getContext().getConfiguration().getSkipBodies()) {
                    DeclarationGenerator declarationGenerator2 = getDeclarationGenerator();
                    IrFieldSymbol symbol2 = irFieldDeclareField$default.getSymbol();
                    Intrinsics.checkNotNull(initializer);
                    irExpressionBodyGenerateInitializerBody = declarationGenerator2.generateInitializerBody(symbol2, initializer);
                }
            }
            irFieldDeclareField$default.setInitializer(irExpressionBodyGenerateInitializerBody);
            irField = irFieldDeclareField$default;
        }
        irProperty.setBackingField(irField);
        irProperty.setGetter(generateGetterIfRequired(ktProperty, propertyDescriptor));
        irProperty.setSetter(generateSetterIfRequired(ktProperty, propertyDescriptor));
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irProperty);
        generateOverrides(irProperty, propertyDescriptor);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irPropertyDeclareProperty);
        return irProperty;
    }

    public final IrProperty generateFakeOverrideProperty(PropertyDescriptor propertyDescriptor, KtPureElement ktElement) {
        IrSimpleFunction irSimpleFunctionGenerateFakeOverrideFunction;
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        IrSimpleFunction irSimpleFunctionGenerateFakeOverrideFunction2 = null;
        if (Intrinsics.areEqual(propertyDescriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            return null;
        }
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(KotlinUtilsKt.getPureStartOffsetOrUndefined(ktElement), KotlinUtilsKt.getPureEndOffsetOrUndefined(ktElement), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE, propertyDescriptor, propertyDescriptor.getIsDelegated());
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        if (getter != null) {
            FunctionGenerator functionGenerator = new FunctionGenerator(getDeclarationGenerator());
            Intrinsics.checkNotNull(getter);
            irSimpleFunctionGenerateFakeOverrideFunction = functionGenerator.generateFakeOverrideFunction(getter, ktElement);
        } else {
            irSimpleFunctionGenerateFakeOverrideFunction = null;
        }
        irPropertyDeclareProperty.setGetter(irSimpleFunctionGenerateFakeOverrideFunction);
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        if (setter != null) {
            FunctionGenerator functionGenerator2 = new FunctionGenerator(getDeclarationGenerator());
            Intrinsics.checkNotNull(setter);
            irSimpleFunctionGenerateFakeOverrideFunction2 = functionGenerator2.generateFakeOverrideFunction(setter, ktElement);
        }
        irPropertyDeclareProperty.setSetter(irSimpleFunctionGenerateFakeOverrideFunction2);
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irPropertyDeclareProperty);
        generateOverrides(irPropertyDeclareProperty, propertyDescriptor);
        return irPropertyDeclareProperty;
    }

    private final void generateOverrides(IrProperty irProperty, PropertyDescriptor propertyDescriptor) {
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (PropertyDescriptor propertyDescriptor2 : collection) {
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            PropertyDescriptor original = propertyDescriptor2.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            arrayList.add(descriptorExtension.referenceProperty(original));
        }
        irProperty.setOverriddenSymbols(arrayList);
    }

    private final IrSimpleFunction generateGetterIfRequired(KtVariableDeclaration ktProperty, PropertyDescriptor property) {
        PropertyGetterDescriptor getter = property.getGetter();
        if (getter == null) {
            return null;
        }
        FunctionGenerator functionGenerator = new FunctionGenerator(getDeclarationGenerator());
        PropertyGetterDescriptor propertyGetterDescriptor = getter;
        KtProperty ktProperty2 = ktProperty instanceof KtProperty ? (KtProperty) ktProperty : null;
        return functionGenerator.generatePropertyAccessor(propertyGetterDescriptor, ktProperty, ktProperty2 != null ? ktProperty2.getGetter() : null);
    }

    private final IrSimpleFunction generateSetterIfRequired(KtVariableDeclaration ktProperty, PropertyDescriptor property) {
        PropertySetterDescriptor setter;
        if (!property.isVar() || (setter = property.getSetter()) == null) {
            return null;
        }
        FunctionGenerator functionGenerator = new FunctionGenerator(getDeclarationGenerator());
        PropertySetterDescriptor propertySetterDescriptor = setter;
        KtProperty ktProperty2 = ktProperty instanceof KtProperty ? (KtProperty) ktProperty : null;
        return functionGenerator.generatePropertyAccessor(propertySetterDescriptor, ktProperty, ktProperty2 != null ? ktProperty2.getSetter() : null);
    }

    private final PropertyDescriptor getPropertyDescriptor(KtVariableDeclaration ktProperty) {
        WritableSlice<PsiElement, VariableDescriptor> VARIABLE = BindingContext.VARIABLE;
        Intrinsics.checkNotNullExpressionValue(VARIABLE, "VARIABLE");
        VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(this, VARIABLE, ktProperty);
        PropertyDescriptor propertyDescriptor = variableDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) variableDescriptor : null;
        if (propertyDescriptor != null) {
            return propertyDescriptor;
        }
        throw new NotImplementedError("An operation is not implemented: " + ("not a property: " + variableDescriptor));
    }

    private final DescriptorVisibility getFieldVisibility(PropertyDescriptor propertyDescriptor) {
        DescriptorVisibility visibility;
        DescriptorVisibility descriptorVisibilityComputeFieldVisibility = getDeclarationGenerator().getContext().getExtensions().computeFieldVisibility(propertyDescriptor);
        if (descriptorVisibilityComputeFieldVisibility == null) {
            if (propertyDescriptor.isLateInit()) {
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter == null || (descriptorVisibilityComputeFieldVisibility = setter.getVisibility()) == null) {
                    visibility = propertyDescriptor.getVisibility();
                }
                Intrinsics.checkNotNull(descriptorVisibilityComputeFieldVisibility);
            } else {
                visibility = propertyDescriptor.isConst() ? propertyDescriptor.getVisibility() : DescriptorVisibilities.PRIVATE;
            }
            descriptorVisibilityComputeFieldVisibility = visibility;
            Intrinsics.checkNotNull(descriptorVisibilityComputeFieldVisibility);
        }
        return descriptorVisibilityComputeFieldVisibility;
    }
}
