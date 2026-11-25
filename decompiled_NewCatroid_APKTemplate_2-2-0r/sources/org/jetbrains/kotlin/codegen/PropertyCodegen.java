package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContextUtil;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassFacadeContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassPartContext;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BindingContext bindingContext;
    private final FieldOwnerContext context;
    private final FunctionCodegen functionCodegen;
    private final OwnerKind kind;
    private final MemberCodegen<?> memberCodegen;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;
    private final ClassBuilder v;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 26 || i == 37 || i == 40) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 26 || i == 37 || i == 40) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 2:
                objArr[0] = "functionCodegen";
                break;
            case 3:
                objArr[0] = "memberCodegen";
                break;
            case 4:
                objArr[0] = "property";
                break;
            case 5:
                objArr[0] = "entry";
                break;
            case 6:
                objArr[0] = "deserializedProperty";
                break;
            case 7:
                objArr[0] = "declaration";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 18:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            case 29:
            case 30:
            case 35:
            case 38:
                objArr[0] = "descriptor";
                break;
            case 15:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 17:
                objArr[0] = "parameter";
                break;
            case 19:
                objArr[0] = "ktParameter";
                break;
            case 23:
            case 24:
            case 34:
                objArr[0] = "propertyDescriptor";
                break;
            case 25:
            case 36:
            case 39:
                objArr[0] = "bindingContext";
                break;
            case 26:
            case 37:
            case 40:
                objArr[0] = "org/jetbrains/kotlin/codegen/PropertyCodegen";
                break;
            case 31:
                objArr[0] = "codegen";
                break;
            case 32:
                objArr[0] = "resolvedCall";
                break;
            case 33:
                objArr[0] = "receiver";
                break;
            case 41:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 42:
                objArr[0] = "delegateTo";
                break;
            case 43:
                objArr[0] = "field";
                break;
            default:
                objArr[0] = "context";
                break;
        }
        if (i == 26) {
            objArr[1] = "getDelegateTypeForProperty";
        } else if (i == 37) {
            objArr[1] = "getOptimizedDelegatedPropertyMetadataValue";
        } else if (i != 40) {
            objArr[1] = "org/jetbrains/kotlin/codegen/PropertyCodegen";
        } else {
            objArr[1] = "getDelegatedPropertyMetadata";
        }
        switch (i) {
            case 4:
            case 7:
            case 8:
                objArr[2] = "gen";
                break;
            case 5:
            case 9:
                objArr[2] = "genDestructuringDeclaration";
                break;
            case 6:
                objArr[2] = "generateInPackageFacade";
                break;
            case 10:
                objArr[2] = "genBackingFieldAndAnnotations";
                break;
            case 11:
            case 13:
                objArr[2] = "isAccessorNeeded";
                break;
            case 12:
                objArr[2] = "isReferenceablePropertyWithGetter";
                break;
            case 14:
            case 15:
                objArr[2] = "areAccessorsNeededForPrimaryConstructorProperty";
                break;
            case 16:
                objArr[2] = "generatePrimaryConstructorProperty";
                break;
            case 17:
            case 18:
                objArr[2] = "generateConstructorPropertyAsMethodForAnnotationClass";
                break;
            case 19:
            case 20:
                objArr[2] = "loadAnnotationArgumentDefaultValue";
                break;
            case 21:
            case 23:
                objArr[2] = "generateBackingField";
                break;
            case 22:
                objArr[2] = "generateSyntheticMethodIfNeeded";
                break;
            case 24:
            case 25:
                objArr[2] = "getDelegateTypeForProperty";
                break;
            case 26:
            case 37:
            case 40:
                break;
            case 27:
                objArr[2] = "shouldWriteFieldInitializer";
                break;
            case 28:
                objArr[2] = "generateGetter";
                break;
            case 29:
                objArr[2] = "generateSetter";
                break;
            case 30:
                objArr[2] = "generateAccessor";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "invokeDelegatedPropertyConventionMethod";
                break;
            case 35:
            case 36:
                objArr[2] = "isDelegatedPropertyWithOptimizedMetadata";
                break;
            case 38:
            case 39:
                objArr[2] = "getDelegatedPropertyMetadata";
                break;
            case 41:
            case 42:
            case 43:
                objArr[2] = "genDelegate";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 26 && i != 37 && i != 40) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PropertyCodegen(FieldOwnerContext fieldOwnerContext, ClassBuilder classBuilder, FunctionCodegen functionCodegen, MemberCodegen<?> memberCodegen) {
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(0);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(1);
        }
        if (functionCodegen == null) {
            $$$reportNull$$$0(2);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(3);
        }
        GenerationState generationState = functionCodegen.state;
        this.state = generationState;
        this.v = classBuilder;
        this.functionCodegen = functionCodegen;
        this.typeMapper = generationState.getTypeMapper();
        this.bindingContext = generationState.getBindingContext();
        this.context = fieldOwnerContext;
        this.memberCodegen = memberCodegen;
        this.kind = fieldOwnerContext.getContextKind();
    }

    public void gen(KtProperty ktProperty) {
        if (ktProperty == null) {
            $$$reportNull$$$0(4);
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktProperty);
        if (!(variableDescriptor instanceof PropertyDescriptor)) {
            throw ExceptionLogger.logDescriptorNotFound("Property " + ktProperty.get$name() + " should have a property descriptor: " + variableDescriptor, ktProperty);
        }
        gen(ktProperty, (PropertyDescriptor) variableDescriptor, ktProperty.getGetter(), ktProperty.getSetter());
    }

    public void genDestructuringDeclaration(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry) {
        if (ktDestructuringDeclarationEntry == null) {
            $$$reportNull$$$0(5);
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktDestructuringDeclarationEntry);
        if (!(variableDescriptor instanceof PropertyDescriptor)) {
            throw ExceptionLogger.logDescriptorNotFound("Destructuring declaration entry" + ktDestructuringDeclarationEntry.get$name() + " should have a property descriptor: " + variableDescriptor, ktDestructuringDeclarationEntry);
        }
        if (UnderscoreUtilKt.isSingleUnderscore(ktDestructuringDeclarationEntry)) {
            return;
        }
        genDestructuringDeclaration((PropertyDescriptor) variableDescriptor);
    }

    public void generateInPackageFacade(DeserializedPropertyDescriptor deserializedPropertyDescriptor) {
        if (deserializedPropertyDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        genBackingFieldAndAnnotations(deserializedPropertyDescriptor);
        if (JvmCodegenUtil.isConstOrHasJvmFieldAnnotation(deserializedPropertyDescriptor)) {
            return;
        }
        generateGetter(deserializedPropertyDescriptor, null);
        generateSetter(deserializedPropertyDescriptor, null);
    }

    private void gen(KtProperty ktProperty, PropertyDescriptor propertyDescriptor, KtPropertyAccessor ktPropertyAccessor, KtPropertyAccessor ktPropertyAccessor2) {
        if (ktProperty == null) {
            $$$reportNull$$$0(7);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        genBackingFieldAndAnnotations(propertyDescriptor);
        boolean z = isDefaultAccessor(ktPropertyAccessor) && isDefaultAccessor(ktPropertyAccessor2);
        if (isAccessorNeeded(propertyDescriptor, ktPropertyAccessor, z)) {
            generateGetter(propertyDescriptor, ktPropertyAccessor);
        }
        if (isAccessorNeeded(propertyDescriptor, ktPropertyAccessor2, z)) {
            generateSetter(propertyDescriptor, ktPropertyAccessor2);
        }
    }

    private static boolean isDefaultAccessor(KtPropertyAccessor ktPropertyAccessor) {
        return ktPropertyAccessor == null || !ktPropertyAccessor.hasBody();
    }

    private void genDestructuringDeclaration(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        genBackingFieldAndAnnotations(propertyDescriptor);
        generateGetter(propertyDescriptor, null);
        generateSetter(propertyDescriptor, null);
    }

    private void genBackingFieldAndAnnotations(PropertyDescriptor propertyDescriptor) {
        boolean zIsImplementationOwner;
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (!propertyDescriptor.isConst() || DescriptorVisibilities.isPrivate(propertyDescriptor.getVisibility())) {
            zIsImplementationOwner = CodegenContextUtil.isImplementationOwner(this.context, propertyDescriptor);
        } else {
            zIsImplementationOwner = !(this.context instanceof MultifileClassPartContext);
        }
        generateBackingField(propertyDescriptor, zIsImplementationOwner);
        generateSyntheticMethodIfNeeded(propertyDescriptor, zIsImplementationOwner);
    }

    private boolean isAccessorNeeded(PropertyDescriptor propertyDescriptor, KtPropertyAccessor ktPropertyAccessor, boolean z) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        return isAccessorNeeded(propertyDescriptor, ktPropertyAccessor, z, this.kind);
    }

    public static boolean isReferenceablePropertyWithGetter(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor);
        KtDeclaration ktDeclaration = psiElementDescriptorToDeclaration instanceof KtDeclaration ? (KtDeclaration) psiElementDescriptorToDeclaration : null;
        if (ktDeclaration instanceof KtProperty) {
            KtProperty ktProperty = (KtProperty) ktDeclaration;
            return isAccessorNeeded(propertyDescriptor, ktProperty.getGetter(), isDefaultAccessor(ktProperty.getGetter()) && isDefaultAccessor(ktProperty.getSetter()), OwnerKind.IMPLEMENTATION);
        }
        if (ktDeclaration instanceof KtParameter) {
            return isAccessorNeeded(propertyDescriptor, null, true, OwnerKind.IMPLEMENTATION);
        }
        return isAccessorNeeded(propertyDescriptor, null, false, OwnerKind.IMPLEMENTATION);
    }

    private static boolean isAccessorNeeded(PropertyDescriptor propertyDescriptor, KtPropertyAccessor ktPropertyAccessor, boolean z, OwnerKind ownerKind) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        if (JvmCodegenUtil.isConstOrHasJvmFieldAnnotation(propertyDescriptor)) {
            return false;
        }
        boolean zIsDefaultAccessor = isDefaultAccessor(ktPropertyAccessor);
        if (ownerKind == OwnerKind.DEFAULT_IMPLS && zIsDefaultAccessor) {
            return false;
        }
        if (!propertyDescriptor.getIsDelegated() && propertyDescriptor.getExtensionReceiverParameter() == null) {
            if (DescriptorUtils.isCompanionObject(propertyDescriptor.getContainingModule())) {
                return (DescriptorVisibilities.isPrivate(propertyDescriptor.getVisibility()) && z) ? false : true;
            }
            if (JvmFileClassUtilsKt.isTopLevelInJvmMultifileClass(propertyDescriptor)) {
                return true;
            }
            if (DescriptorVisibilities.isPrivate(propertyDescriptor.getVisibility())) {
                return !zIsDefaultAccessor;
            }
            if (ktPropertyAccessor != null && ktPropertyAccessor.isSetter() && DescriptorVisibilities.isPrivate(propertyDescriptor.getSetter().getVisibility())) {
                return !zIsDefaultAccessor;
            }
            if (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(propertyDescriptor) && !propertyDescriptor.getVisibility().isPublicAPI()) {
                return false;
            }
        }
        return true;
    }

    private static boolean areAccessorsNeededForPrimaryConstructorProperty(PropertyDescriptor propertyDescriptor, OwnerKind ownerKind) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(15);
        }
        if (JvmAnnotationUtilKt.hasJvmFieldAnnotation(propertyDescriptor) || ownerKind == OwnerKind.ERASED_INLINE_CLASS) {
            return false;
        }
        DescriptorVisibility visibility = propertyDescriptor.getVisibility();
        if (InlineClassesUtilsKt.isInlineClass(propertyDescriptor.getContainingModule())) {
            return visibility.isPublicAPI();
        }
        return !DescriptorVisibilities.isPrivate(visibility);
    }

    public void generatePrimaryConstructorProperty(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        genBackingFieldAndAnnotations(propertyDescriptor);
        if (areAccessorsNeededForPrimaryConstructorProperty(propertyDescriptor, this.context.getContextKind())) {
            generateGetter(propertyDescriptor, null);
            generateSetter(propertyDescriptor, null);
        }
    }

    public void generateConstructorPropertyAsMethodForAnnotationClass(KtParameter ktParameter, PropertyDescriptor propertyDescriptor, FunctionDescriptor functionDescriptor) {
        ConstantValue<?> compileTimeConstant;
        if (ktParameter == null) {
            $$$reportNull$$$0(17);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(18);
        }
        JvmMethodGenericSignature jvmMethodGenericSignatureMapAnnotationParameterSignature = this.typeMapper.mapAnnotationParameterSignature(propertyDescriptor);
        Method asmMethod = jvmMethodGenericSignatureMapAnnotationParameterSignature.getAsmMethod();
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin(ktParameter, propertyDescriptor), 1025, asmMethod.getName(), asmMethod.getDescriptor(), jvmMethodGenericSignatureMapAnnotationParameterSignature.getGenericsSignature(), null);
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        this.v.getSerializationBindings().put(JvmSerializationBindings.METHOD_FOR_FUNCTION, getter, asmMethod);
        AnnotationCodegen.forMethod(methodVisitorNewMethod, this.memberCodegen, this.state).genAnnotations(getter, asmMethod.getReturnType(), null);
        KtExpression ktExpressionLoadAnnotationArgumentDefaultValue = loadAnnotationArgumentDefaultValue(ktParameter, propertyDescriptor, functionDescriptor);
        if (ktExpressionLoadAnnotationArgumentDefaultValue != null && (compileTimeConstant = ExpressionCodegen.getCompileTimeConstant(ktExpressionLoadAnnotationArgumentDefaultValue, this.bindingContext, true, this.state.getConfig().getShouldInlineConstVals())) != null) {
            AnnotationCodegen.forAnnotationDefaultValue(methodVisitorNewMethod, this.memberCodegen, this.state).generateAnnotationDefaultValue(compileTimeConstant, propertyDescriptor.getType());
        }
        methodVisitorNewMethod.visitEnd();
    }

    private KtExpression loadAnnotationArgumentDefaultValue(KtParameter ktParameter, final PropertyDescriptor propertyDescriptor, FunctionDescriptor functionDescriptor) {
        if (ktParameter == null) {
            $$$reportNull$$$0(19);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        KtExpression defaultValue = ktParameter.getDefaultValue();
        if (defaultValue != null) {
            return defaultValue;
        }
        if (functionDescriptor == null) {
            return null;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration((ValueParameterDescriptor) CollectionsKt.single(functionDescriptor.getValueParameters(), new Function1() { // from class: org.jetbrains.kotlin.codegen.PropertyCodegen$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((ValueParameterDescriptor) obj).getShortName().equals(propertyDescriptor.getShortName()));
            }
        }));
        if (!(psiElementDescriptorToDeclaration instanceof KtParameter)) {
            this.state.getDiagnostics().report(Errors.EXPECTED_FUNCTION_SOURCE_WITH_DEFAULT_ARGUMENTS_NOT_FOUND.on(ktParameter));
            return null;
        }
        return ((KtParameter) psiElementDescriptorToDeclaration).getDefaultValue();
    }

    private void generateBackingField(PropertyDescriptor propertyDescriptor, boolean z) {
        ConstantValue<?> compileTimeInitializer;
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        if (JvmCodegenUtil.isJvmInterface(propertyDescriptor.getContainingModule()) || this.kind == OwnerKind.DEFAULT_IMPLS || this.kind == OwnerKind.ERASED_INLINE_CLASS) {
            return;
        }
        Object value = null;
        if (!propertyDescriptor.getIsDelegated()) {
            if (!Boolean.TRUE.equals(this.bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor))) {
                return;
            }
            if (shouldWriteFieldInitializer(propertyDescriptor) && (compileTimeInitializer = propertyDescriptor.mo6754getCompileTimeInitializer()) != null) {
                value = compileTimeInitializer.getValue();
            }
        }
        generateBackingField(propertyDescriptor, propertyDescriptor.getIsDelegated(), value, z);
    }

    /* JADX WARN: Type inference failed for: r7v2, types: [org.jetbrains.kotlin.descriptors.DeclarationDescriptor] */
    private void generateSyntheticMethodIfNeeded(PropertyDescriptor propertyDescriptor, boolean z) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        Annotations annotations = propertyDescriptor.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }
        Method methodMapSyntheticMethodForPropertyAnnotations = this.typeMapper.mapSyntheticMethodForPropertyAnnotations(propertyDescriptor);
        if (this.kind != OwnerKind.DEFAULT_IMPLS && CodegenContextUtil.isImplementationOwner(this.context, propertyDescriptor)) {
            this.v.getSerializationBindings().put(JvmSerializationBindings.SYNTHETIC_METHOD_FOR_PROPERTY, propertyDescriptor, methodMapSyntheticMethodForPropertyAnnotations);
        }
        if (z) {
            if (!DescriptorUtils.isInterface(this.context.getContextDescriptor()) || FunctionCodegen.processInterfaceMethod(propertyDescriptor, this.kind, false, true, this.state.getJvmDefaultMode())) {
                this.memberCodegen.generateSyntheticAnnotationsMethod(propertyDescriptor, methodMapSyntheticMethodForPropertyAnnotations, annotations);
            }
        }
    }

    private void generateBackingField(PropertyDescriptor propertyDescriptor, boolean z, Object obj, boolean z2) {
        ClassBuilder classBuilder;
        List<String> listSingletonList;
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        FieldDescriptor delegateField = z ? propertyDescriptor.getDelegateField() : propertyDescriptor.getBackingField();
        int deprecatedAccessFlag = DescriptorAsmUtil.getDeprecatedAccessFlag(propertyDescriptor);
        Iterator<AnnotationCodegen.JvmFlagAnnotation> it = AnnotationCodegen.FIELD_FLAGS.iterator();
        while (it.hasNext()) {
            deprecatedAccessFlag |= it.next().getJvmFlag(delegateField);
        }
        if (this.kind == OwnerKind.PACKAGE) {
            deprecatedAccessFlag |= 8;
        }
        if (!propertyDescriptor.isLateInit() && (!propertyDescriptor.isVar() || z)) {
            deprecatedAccessFlag |= 16;
        }
        if (JvmAnnotationUtilKt.hasJvmSyntheticAnnotation(propertyDescriptor)) {
            deprecatedAccessFlag |= 4096;
        }
        KotlinType delegateTypeForProperty = z ? getDelegateTypeForProperty(propertyDescriptor, this.bindingContext) : propertyDescriptor.getType();
        Type typeMapType = this.typeMapper.mapType(delegateTypeForProperty);
        ClassBuilder classBuilder2 = this.v;
        FieldOwnerContext<?> fieldOwnerContext = this.context;
        List<String> listEmptyList = Collections.emptyList();
        if (DescriptorAsmUtil.isInstancePropertyWithStaticBackingField(propertyDescriptor)) {
            int i = deprecatedAccessFlag | 8;
            if (DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(propertyDescriptor)) {
                ImplementationBodyCodegen implementationBodyCodegen = (ImplementationBodyCodegen) this.memberCodegen.getParentCodegen();
                classBuilder = implementationBodyCodegen.v;
                FieldOwnerContext<?> fieldOwnerContext2 = implementationBodyCodegen.context;
                if (DescriptorVisibilities.isPrivate(((ClassDescriptor) propertyDescriptor.getContainingModule()).getVisibility())) {
                    deprecatedAccessFlag |= 131080;
                    listSingletonList = Collections.singletonList(CodegenUtilKt.JAVA_LANG_DEPRECATED);
                } else {
                    listSingletonList = listEmptyList;
                    deprecatedAccessFlag = i;
                }
                fieldOwnerContext = fieldOwnerContext2;
            } else {
                listSingletonList = listEmptyList;
                deprecatedAccessFlag = i;
                classBuilder = classBuilder2;
            }
        } else {
            classBuilder = classBuilder2;
            listSingletonList = listEmptyList;
        }
        int visibilityForBackingField = deprecatedAccessFlag | DescriptorAsmUtil.getVisibilityForBackingField(propertyDescriptor, z);
        if (DescriptorAsmUtil.isPropertyWithBackingFieldCopyInOuterClass(propertyDescriptor)) {
            ((ImplementationBodyCodegen) this.memberCodegen.getParentCodegen()).addCompanionObjectPropertyToCopy(propertyDescriptor, obj);
        }
        String fieldName = fieldOwnerContext.getFieldName(propertyDescriptor, z);
        this.v.getSerializationBindings().put(JvmSerializationBindings.FIELD_FOR_PROPERTY, propertyDescriptor, new Pair(typeMapType, fieldName));
        if (z2) {
            String strMapFieldSignature = z ? null : this.typeMapper.mapFieldSignature(delegateTypeForProperty, propertyDescriptor);
            FieldVisitor fieldVisitorNewField = classBuilder.newField(JvmDeclarationOriginKt.OtherOrigin(propertyDescriptor), visibilityForBackingField, fieldName, typeMapType.getDescriptor(), strMapFieldSignature, obj);
            if (delegateField != null) {
                AnnotationCodegen.forField(fieldVisitorNewField, this.memberCodegen, this.state, (visibilityForBackingField & 4096) != 0 || propertyDescriptor.isLateInit()).genAnnotations(delegateField, typeMapType, propertyDescriptor.getType(), null, listSingletonList);
            }
            if ((propertyDescriptor.getContainingModule() instanceof ClassDescriptor) && JvmAnnotationUtilKt.isJvmRecord((ClassDescriptor) propertyDescriptor.getContainingModule())) {
                ClassBuilderRecordKt.addRecordComponent(classBuilder, fieldName, typeMapType.getDescriptor(), strMapFieldSignature);
            }
        }
    }

    public static KotlinType getDelegateTypeForProperty(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(25);
        }
        ResolvedCall resolvedCall = (ResolvedCall) bindingContext.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, propertyDescriptor);
        KtProperty ktProperty = (KtProperty) DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor);
        KotlinType type = null;
        KtExpression delegateExpression = ktProperty != null ? ktProperty.getDelegateExpression() : null;
        if (resolvedCall != null) {
            type = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        } else if (delegateExpression != null) {
            type = bindingContext.getType(delegateExpression);
        }
        if (type == null) {
            type = ErrorUtils.createErrorType(ErrorTypeKind.TYPE_FOR_DELEGATION, delegateExpression.getText());
        }
        if (type == null) {
            $$$reportNull$$$0(26);
        }
        return type;
    }

    private boolean shouldWriteFieldInitializer(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        if ((!propertyDescriptor.isConst() && this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.NoConstantValueAttributeForNonConstVals)) || propertyDescriptor.isVar()) {
            return false;
        }
        Type typeMapType = this.typeMapper.mapType(propertyDescriptor);
        return AsmUtil.isPrimitive(typeMapType) || CommonClassNames.JAVA_LANG_STRING.equals(typeMapType.getClassName());
    }

    private void generateGetter(PropertyDescriptor propertyDescriptor, KtPropertyAccessor ktPropertyAccessor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        generateAccessor(ktPropertyAccessor, propertyDescriptor.getGetter() != null ? propertyDescriptor.getGetter() : DescriptorFactory.createDefaultGetter(propertyDescriptor, Annotations.INSTANCE.getEMPTY()));
    }

    private void generateSetter(PropertyDescriptor propertyDescriptor, KtPropertyAccessor ktPropertyAccessor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        if (propertyDescriptor.isVar()) {
            generateAccessor(ktPropertyAccessor, propertyDescriptor.getSetter() != null ? propertyDescriptor.getSetter() : DescriptorFactory.createDefaultSetter(propertyDescriptor, Annotations.INSTANCE.getEMPTY(), Annotations.INSTANCE.getEMPTY()));
        }
    }

    private void generateAccessor(KtPropertyAccessor ktPropertyAccessor, PropertyAccessorDescriptor propertyAccessorDescriptor) {
        FunctionGenerationStrategy defaultPropertyAccessorStrategy;
        if (propertyAccessorDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        if ((this.context instanceof MultifileClassFacadeContext) && (DescriptorVisibilities.isPrivate(propertyAccessorDescriptor.getVisibility()) || DescriptorAsmUtil.getVisibilityAccessFlag(propertyAccessorDescriptor) == 2)) {
            return;
        }
        if (ktPropertyAccessor == null || !ktPropertyAccessor.hasBody()) {
            if (propertyAccessorDescriptor.getCorrespondingProperty().getIsDelegated()) {
                defaultPropertyAccessorStrategy = new DelegatedPropertyAccessorStrategy(this.state, propertyAccessorDescriptor);
            } else {
                defaultPropertyAccessorStrategy = new DefaultPropertyAccessorStrategy(this.state, propertyAccessorDescriptor);
            }
        } else {
            defaultPropertyAccessorStrategy = new FunctionGenerationStrategy.FunctionDefault(this.state, ktPropertyAccessor);
        }
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin(propertyAccessorDescriptor), propertyAccessorDescriptor, defaultPropertyAccessorStrategy);
    }

    private static class DefaultPropertyAccessorStrategy extends FunctionGenerationStrategy.CodegenBased {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final PropertyAccessorDescriptor propertyAccessorDescriptor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "descriptor";
            } else if (i == 2) {
                objArr[0] = "codegen";
            } else if (i != 3) {
                objArr[0] = "state";
            } else {
                objArr[0] = "signature";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/PropertyCodegen$DefaultPropertyAccessorStrategy";
            if (i == 2 || i == 3) {
                objArr[2] = "doGenerateBody";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DefaultPropertyAccessorStrategy(GenerationState generationState, PropertyAccessorDescriptor propertyAccessorDescriptor) {
            super(generationState);
            if (generationState == null) {
                $$$reportNull$$$0(0);
            }
            if (propertyAccessorDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            this.propertyAccessorDescriptor = propertyAccessorDescriptor;
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
        public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            if (jvmMethodSignature == null) {
                $$$reportNull$$$0(3);
            }
            InstructionAdapter instructionAdapter = expressionCodegen.v;
            PropertyDescriptor correspondingProperty = this.propertyAccessorDescriptor.getCorrespondingProperty();
            StackValue.Property propertyIntermediateValueForProperty = expressionCodegen.intermediateValueForProperty(correspondingProperty, true, null, StackValue.LOCAL_0);
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(correspondingProperty);
            if ((psiElementDescriptorToDeclaration instanceof KtProperty) || (psiElementDescriptorToDeclaration instanceof KtParameter)) {
                expressionCodegen.markLineNumber((KtElement) psiElementDescriptorToDeclaration, false);
            }
            PropertyAccessorDescriptor propertyAccessorDescriptor = this.propertyAccessorDescriptor;
            if (propertyAccessorDescriptor instanceof PropertyGetterDescriptor) {
                Type returnType = jvmMethodSignature.getReturnType();
                propertyIntermediateValueForProperty.put(returnType, instructionAdapter);
                instructionAdapter.areturn(returnType);
            } else {
                if (propertyAccessorDescriptor instanceof PropertySetterDescriptor) {
                    propertyIntermediateValueForProperty.store(StackValue.local(expressionCodegen.lookupLocalIndex(propertyAccessorDescriptor.getValueParameters().get(0)), expressionCodegen.typeMapper.mapType(correspondingProperty)), expressionCodegen.v);
                    instructionAdapter.visitInsn(177);
                    return;
                }
                throw new IllegalStateException("Unknown property accessor: " + this.propertyAccessorDescriptor);
            }
        }
    }

    public static StackValue invokeDelegatedPropertyConventionMethod(ExpressionCodegen expressionCodegen, ResolvedCall<FunctionDescriptor> resolvedCall, StackValue stackValue, PropertyDescriptor propertyDescriptor) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(31);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(32);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(33);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(34);
        }
        expressionCodegen.tempVariables.put(resolvedCall.get$fakeCall().getValueArguments().get(1).getCallElement(), getDelegatedPropertyMetadata(propertyDescriptor, expressionCodegen.getBindingContext()));
        return expressionCodegen.invokeFunction(resolvedCall, stackValue);
    }

    public static boolean isDelegatedPropertyWithOptimizedMetadata(VariableDescriptorWithAccessors variableDescriptorWithAccessors, BindingContext bindingContext) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(35);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(36);
        }
        return Boolean.TRUE == bindingContext.get(CodegenBinding.DELEGATED_PROPERTY_WITH_OPTIMIZED_METADATA, variableDescriptorWithAccessors);
    }

    public static StackValue getOptimizedDelegatedPropertyMetadataValue() {
        StackValue stackValueConstant = StackValue.constant(null, AsmTypes.K_PROPERTY_TYPE);
        if (stackValueConstant == null) {
            $$$reportNull$$$0(37);
        }
        return stackValueConstant;
    }

    public static StackValue getDelegatedPropertyMetadata(VariableDescriptorWithAccessors variableDescriptorWithAccessors, BindingContext bindingContext) {
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(38);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(39);
        }
        if (isDelegatedPropertyWithOptimizedMetadata(variableDescriptorWithAccessors, bindingContext)) {
            return getOptimizedDelegatedPropertyMetadataValue();
        }
        Type type = (Type) bindingContext.get(CodegenBinding.DELEGATED_PROPERTY_METADATA_OWNER, variableDescriptorWithAccessors);
        List list = (List) bindingContext.get(CodegenBinding.DELEGATED_PROPERTIES_WITH_METADATA, type);
        int iIndexOf = list == null ? -1 : list.indexOf(variableDescriptorWithAccessors);
        if (iIndexOf < 0) {
            throw new AssertionError("Delegated property not found in " + type + ": " + variableDescriptorWithAccessors);
        }
        StackValue stackValueArrayElement = StackValue.arrayElement(AsmTypes.K_PROPERTY_TYPE, null, StackValue.field(Type.getType("[" + AsmTypes.K_PROPERTY_TYPE), type, JvmAbi.DELEGATED_PROPERTIES_ARRAY_NAME, true, StackValue.none()), StackValue.constant(iIndexOf));
        if (stackValueArrayElement == null) {
            $$$reportNull$$$0(40);
        }
        return stackValueArrayElement;
    }

    private static class DelegatedPropertyAccessorStrategy extends FunctionGenerationStrategy.CodegenBased {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final PropertyAccessorDescriptor propertyAccessorDescriptor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "descriptor";
            } else if (i == 2) {
                objArr[0] = "codegen";
            } else if (i != 3) {
                objArr[0] = "state";
            } else {
                objArr[0] = "signature";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/PropertyCodegen$DelegatedPropertyAccessorStrategy";
            if (i == 2 || i == 3) {
                objArr[2] = "doGenerateBody";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DelegatedPropertyAccessorStrategy(GenerationState generationState, PropertyAccessorDescriptor propertyAccessorDescriptor) {
            super(generationState);
            if (generationState == null) {
                $$$reportNull$$$0(0);
            }
            if (propertyAccessorDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            this.propertyAccessorDescriptor = propertyAccessorDescriptor;
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
        public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            if (jvmMethodSignature == null) {
                $$$reportNull$$$0(3);
            }
            InstructionAdapter instructionAdapter = expressionCodegen.v;
            ResolvedCall resolvedCall = (ResolvedCall) this.state.getBindingContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, this.propertyAccessorDescriptor);
            PropertyDescriptor correspondingProperty = this.propertyAccessorDescriptor.getCorrespondingProperty();
            StackValue stackValueInvokeDelegatedPropertyConventionMethod = PropertyCodegen.invokeDelegatedPropertyConventionMethod(expressionCodegen, resolvedCall, expressionCodegen.intermediateValueForProperty(correspondingProperty, true, null, StackValue.LOCAL_0).getDelegateOrNull(), correspondingProperty);
            Type returnType = jvmMethodSignature.getReturnType();
            stackValueInvokeDelegatedPropertyConventionMethod.put(returnType, this.propertyAccessorDescriptor.getOriginal().getReturnType(), instructionAdapter);
            instructionAdapter.areturn(returnType);
        }
    }

    public void genDelegate(PropertyDescriptor propertyDescriptor, PropertyDescriptor propertyDescriptor2, StackValue stackValue) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(41);
        }
        if (propertyDescriptor2 == null) {
            $$$reportNull$$$0(42);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(43);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) propertyDescriptor2.getContainingModule();
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        if (getter != null) {
            this.functionCodegen.genDelegate(getter, propertyDescriptor2.getGetter().getOriginal(), classDescriptor, stackValue);
        }
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        if (setter != null) {
            this.functionCodegen.genDelegate(setter, propertyDescriptor2.getSetter().getOriginal(), classDescriptor, stackValue);
        }
    }
}
