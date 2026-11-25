package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class FunctionsFromAnyGeneratorImpl extends FunctionsFromAnyGenerator {
    private final Type classAsmType;
    private final ClassDescriptor classDescriptor;
    private final FieldOwnerContext<?> fieldOwnerContext;
    private final GenerationState generationState;
    private final boolean isInErasedInlineClass;
    private final KotlinTypeMapper typeMapper;
    private final JvmKotlinType underlyingType;
    private final ClassBuilder v;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "bindingContext";
                break;
            case 2:
                objArr[0] = "descriptor";
                break;
            case 3:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 4:
                objArr[0] = "fieldOwnerContext";
                break;
            case 5:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 6:
                objArr[0] = "state";
                break;
            case 7:
            case 9:
            case 12:
            case 15:
                objArr[0] = "function";
                break;
            case 8:
            case 13:
            case 16:
                objArr[0] = "properties";
                break;
            case 10:
                objArr[0] = "name";
                break;
            case 11:
                objArr[0] = "desc";
                break;
            case 14:
                objArr[0] = "functionDescriptor";
                break;
            default:
                objArr[0] = "declaration";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/FunctionsFromAnyGeneratorImpl";
        switch (i) {
            case 7:
            case 8:
                objArr[2] = "generateToStringMethod";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "recordMethodForFunctionIfRequired";
                break;
            case 12:
            case 13:
                objArr[2] = "generateHashCodeMethod";
                break;
            case 14:
                objArr[2] = "mapFunctionName";
                break;
            case 15:
            case 16:
                objArr[2] = "generateEqualsMethod";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionsFromAnyGeneratorImpl(KtClassOrObject ktClassOrObject, BindingContext bindingContext, ClassDescriptor classDescriptor, Type type, FieldOwnerContext<?> fieldOwnerContext, ClassBuilder classBuilder, GenerationState generationState) {
        super(ktClassOrObject, bindingContext);
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(1);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (type == null) {
            $$$reportNull$$$0(3);
        }
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(4);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(5);
        }
        if (generationState == null) {
            $$$reportNull$$$0(6);
        }
        this.classDescriptor = classDescriptor;
        this.classAsmType = type;
        this.fieldOwnerContext = fieldOwnerContext;
        this.v = classBuilder;
        this.generationState = generationState;
        KotlinTypeMapper typeMapper = generationState.getTypeMapper();
        this.typeMapper = typeMapper;
        this.underlyingType = new JvmKotlinType(typeMapper.mapType(classDescriptor), InlineClassesUtilsKt.substitutedUnderlyingType(classDescriptor.getDefaultType()));
        this.isInErasedInlineClass = fieldOwnerContext.getContextKind() == OwnerKind.ERASED_INLINE_CLASS;
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    protected void generateToStringMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        MethodContext methodContext;
        int i;
        Iterator<? extends PropertyDescriptor> it;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        MethodContext methodContextIntoFunction = this.fieldOwnerContext.intoFunction(functionDescriptor);
        JvmDeclarationOrigin jvmDeclarationOriginOtherOrigin = JvmDeclarationOriginKt.OtherOrigin(functionDescriptor);
        String strMapFunctionName = mapFunctionName(functionDescriptor);
        String toStringDesc = getToStringDesc();
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(jvmDeclarationOriginOtherOrigin, getAccess(), strMapFunctionName, toStringDesc, null, null);
        if (!this.isInErasedInlineClass && InlineClassesUtilsKt.isInlineClass(this.classDescriptor)) {
            FunctionCodegen.generateMethodInsideInlineClassWrapper(jvmDeclarationOriginOtherOrigin, functionDescriptor, this.classDescriptor, methodVisitorNewMethod, this.typeMapper);
            return;
        }
        int i2 = 0;
        if (!this.isInErasedInlineClass) {
            visitEndForAnnotationVisitor(methodVisitorNewMethod.visitAnnotation(Type.getDescriptor(NotNull.class), false));
        }
        if (!this.generationState.getClassBuilderMode().generateBodies) {
            FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
            return;
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
        methodVisitorNewMethod.visitCode();
        if (list.isEmpty()) {
            instructionAdapter.aconst(this.classDescriptor.getShortName().asString());
        } else {
            StringConcatGenerator stringConcatGeneratorCreate = StringConcatGenerator.INSTANCE.create(this.generationState, instructionAdapter);
            stringConcatGeneratorCreate.genStringBuilderConstructorIfNeded();
            Iterator<? extends PropertyDescriptor> it2 = list.iterator();
            int i3 = 1;
            while (it2.hasNext()) {
                PropertyDescriptor next = it2.next();
                if (i3 != 0) {
                    stringConcatGeneratorCreate.addStringConstant(this.classDescriptor.getShortName() + "(" + next.getShortName().asString() + ContainerUtils.KEY_VALUE_DELIMITER);
                    i3 = i2;
                } else {
                    stringConcatGeneratorCreate.addStringConstant(", " + next.getShortName().asString() + ContainerUtils.KEY_VALUE_DELIMITER);
                }
                JvmKotlinType jvmKotlinTypeGenOrLoadOnStack = genOrLoadOnStack(instructionAdapter, methodContextIntoFunction, next, i2);
                Type type = jvmKotlinTypeGenOrLoadOnStack.getType();
                KotlinType kotlinType = jvmKotlinTypeGenOrLoadOnStack.getKotlinType();
                if (type.getSort() == 9) {
                    Type typeCorrectElementType = AsmUtil.correctElementType(type);
                    methodContext = methodContextIntoFunction;
                    it = it2;
                    if (typeCorrectElementType.getSort() == 10 || typeCorrectElementType.getSort() == 9) {
                        i = 0;
                        instructionAdapter.invokestatic("java/util/Arrays", "toString", "([Ljava/lang/Object;)Ljava/lang/String;", false);
                        type = AsmTypes.JAVA_STRING_TYPE;
                        kotlinType = DescriptorUtilsKt.getBuiltIns(functionDescriptor).getStringType();
                    } else if (typeCorrectElementType.getSort() != 2) {
                        i = 0;
                        instructionAdapter.invokestatic("java/util/Arrays", "toString", "(" + type.getDescriptor() + ")Ljava/lang/String;", false);
                        type = AsmTypes.JAVA_STRING_TYPE;
                        kotlinType = DescriptorUtilsKt.getBuiltIns(functionDescriptor).getStringType();
                    } else {
                        i = 0;
                    }
                } else {
                    methodContext = methodContextIntoFunction;
                    i = i2;
                    it = it2;
                }
                DescriptorAsmUtil.genInvokeAppendMethod(stringConcatGeneratorCreate, type, kotlinType, this.typeMapper, StackValue.onStack(type));
                i2 = i;
                methodContextIntoFunction = methodContext;
                it2 = it;
            }
            stringConcatGeneratorCreate.addStringConstant(Constant.AFTER_QUTO);
            stringConcatGeneratorCreate.genToString();
        }
        instructionAdapter.areturn(AsmTypes.JAVA_STRING_TYPE);
        FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
        recordMethodForFunctionIfRequired(functionDescriptor, strMapFunctionName, toStringDesc);
    }

    private void recordMethodForFunctionIfRequired(FunctionDescriptor functionDescriptor, String str, String str2) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (str2 == null) {
            $$$reportNull$$$0(11);
        }
        if (this.isInErasedInlineClass) {
            this.v.getSerializationBindings().put(JvmSerializationBindings.METHOD_FOR_FUNCTION, functionDescriptor, new Method(str, str2));
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    protected void generateHashCodeMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        Label label;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        MethodContext methodContextIntoFunction = this.fieldOwnerContext.intoFunction(functionDescriptor);
        JvmDeclarationOrigin jvmDeclarationOriginOtherOrigin = JvmDeclarationOriginKt.OtherOrigin(functionDescriptor);
        String strMapFunctionName = mapFunctionName(functionDescriptor);
        String hashCodeDesc = getHashCodeDesc();
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(jvmDeclarationOriginOtherOrigin, getAccess(), strMapFunctionName, hashCodeDesc, null, null);
        if (!this.isInErasedInlineClass && InlineClassesUtilsKt.isInlineClass(this.classDescriptor)) {
            FunctionCodegen.generateMethodInsideInlineClassWrapper(jvmDeclarationOriginOtherOrigin, functionDescriptor, this.classDescriptor, methodVisitorNewMethod, this.typeMapper);
            return;
        }
        if (!this.generationState.getClassBuilderMode().generateBodies) {
            FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
            return;
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
        methodVisitorNewMethod.visitCode();
        if (list.isEmpty()) {
            instructionAdapter.iconst(DescriptorUtils.getFqNameSafe(this.classDescriptor).asString().hashCode());
        } else {
            boolean z = true;
            for (PropertyDescriptor propertyDescriptor : list) {
                if (!z) {
                    instructionAdapter.iconst(31);
                    instructionAdapter.mul(Type.INT_TYPE);
                }
                JvmKotlinType jvmKotlinTypeGenOrLoadOnStack = genOrLoadOnStack(instructionAdapter, methodContextIntoFunction, propertyDescriptor, 0);
                KotlinType returnType = propertyDescriptor.getReturnType();
                Type typeMapType = this.typeMapper.mapType(returnType);
                StackValue.coerce(jvmKotlinTypeGenOrLoadOnStack.getType(), jvmKotlinTypeGenOrLoadOnStack.getKotlinType(), typeMapType, returnType, instructionAdapter);
                if (AsmUtil.isPrimitive(typeMapType)) {
                    label = null;
                } else {
                    label = new Label();
                    instructionAdapter.dup();
                    instructionAdapter.ifnull(label);
                }
                DescriptorAsmUtil.genHashCode(methodVisitorNewMethod, instructionAdapter, typeMapType, this.generationState.getConfig().getTarget());
                if (label != null) {
                    Label label2 = new Label();
                    instructionAdapter.goTo(label2);
                    instructionAdapter.mark(label);
                    instructionAdapter.pop();
                    instructionAdapter.iconst(0);
                    instructionAdapter.mark(label2);
                }
                if (z) {
                    z = false;
                } else {
                    instructionAdapter.add(Type.INT_TYPE);
                }
            }
        }
        methodVisitorNewMethod.visitInsn(172);
        FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
        recordMethodForFunctionIfRequired(functionDescriptor, strMapFunctionName, hashCodeDesc);
    }

    private String mapFunctionName(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        return this.typeMapper.mapFunctionName(functionDescriptor, this.fieldOwnerContext.getContextKind());
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    protected void generateEqualsMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        FunctionsFromAnyGeneratorImpl functionsFromAnyGeneratorImpl = this;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (list == null) {
            $$$reportNull$$$0(16);
        }
        MethodContext methodContextIntoFunction = functionsFromAnyGeneratorImpl.fieldOwnerContext.intoFunction(functionDescriptor);
        JvmDeclarationOrigin jvmDeclarationOriginOtherOrigin = JvmDeclarationOriginKt.OtherOrigin(functionDescriptor);
        String strMapFunctionName = mapFunctionName(functionDescriptor);
        String equalsDesc = getEqualsDesc();
        MethodVisitor methodVisitorNewMethod = functionsFromAnyGeneratorImpl.v.newMethod(jvmDeclarationOriginOtherOrigin, getAccess(), strMapFunctionName, equalsDesc, null, null);
        if (!functionsFromAnyGeneratorImpl.isInErasedInlineClass && InlineClassesUtilsKt.isInlineClass(functionsFromAnyGeneratorImpl.classDescriptor)) {
            FunctionCodegen.generateMethodInsideInlineClassWrapper(jvmDeclarationOriginOtherOrigin, functionDescriptor, functionsFromAnyGeneratorImpl.classDescriptor, methodVisitorNewMethod, functionsFromAnyGeneratorImpl.typeMapper);
            return;
        }
        if (!functionsFromAnyGeneratorImpl.isInErasedInlineClass) {
            visitEndForAnnotationVisitor(methodVisitorNewMethod.visitParameterAnnotation(0, Type.getDescriptor(Nullable.class), false));
        }
        if (!functionsFromAnyGeneratorImpl.generationState.getClassBuilderMode().generateBodies) {
            FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
            return;
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
        methodVisitorNewMethod.visitCode();
        Label label = new Label();
        Label label2 = new Label();
        final int iGenerateBasicChecksAndStoreTarget = functionsFromAnyGeneratorImpl.generateBasicChecksAndStoreTarget(instructionAdapter, label, label2);
        for (final PropertyDescriptor propertyDescriptor : list) {
            final KotlinType returnType = propertyDescriptor.getReturnType();
            final Type typeMapType = functionsFromAnyGeneratorImpl.typeMapper.mapType(returnType);
            final MethodContext methodContext = methodContextIntoFunction;
            MethodContext methodContext2 = methodContextIntoFunction;
            Label label3 = label2;
            DescriptorAsmUtil.genTotalOrderEqualsForExpressionOnStack(StackValue.operation(typeMapType, returnType, new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionsFromAnyGeneratorImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.m6415xd430307c(methodContext, propertyDescriptor, typeMapType, returnType, (InstructionAdapter) obj);
                }
            }), StackValue.operation(typeMapType, returnType, new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionsFromAnyGeneratorImpl$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.m6416xda33fbdb(methodContext, propertyDescriptor, iGenerateBasicChecksAndStoreTarget, typeMapType, returnType, (InstructionAdapter) obj);
                }
            }), typeMapType).condJump(label3, instructionAdapter, true);
            functionsFromAnyGeneratorImpl = this;
            label2 = label3;
            methodContextIntoFunction = methodContext2;
        }
        instructionAdapter.mark(label);
        instructionAdapter.iconst(1);
        instructionAdapter.areturn(Type.INT_TYPE);
        instructionAdapter.mark(label2);
        instructionAdapter.iconst(0);
        instructionAdapter.areturn(Type.INT_TYPE);
        FunctionCodegen.endVisit(methodVisitorNewMethod, strMapFunctionName, (KtElement) getDeclaration());
        recordMethodForFunctionIfRequired(functionDescriptor, strMapFunctionName, equalsDesc);
    }

    /* JADX INFO: renamed from: lambda$generateEqualsMethod$0$org-jetbrains-kotlin-codegen-FunctionsFromAnyGeneratorImpl, reason: not valid java name */
    /* synthetic */ Unit m6415xd430307c(MethodContext methodContext, PropertyDescriptor propertyDescriptor, Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        JvmKotlinType jvmKotlinTypeGenOrLoadOnStack = genOrLoadOnStack(instructionAdapter, methodContext, propertyDescriptor, 0);
        StackValue.coerce(jvmKotlinTypeGenOrLoadOnStack.getType(), jvmKotlinTypeGenOrLoadOnStack.getKotlinType(), type, kotlinType, instructionAdapter);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: lambda$generateEqualsMethod$1$org-jetbrains-kotlin-codegen-FunctionsFromAnyGeneratorImpl, reason: not valid java name */
    /* synthetic */ Unit m6416xda33fbdb(MethodContext methodContext, PropertyDescriptor propertyDescriptor, int i, Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        JvmKotlinType jvmKotlinTypeGenOrLoadOnStack = genOrLoadOnStack(instructionAdapter, methodContext, propertyDescriptor, i);
        StackValue.coerce(jvmKotlinTypeGenOrLoadOnStack.getType(), jvmKotlinTypeGenOrLoadOnStack.getKotlinType(), type, kotlinType, instructionAdapter);
        return Unit.INSTANCE;
    }

    private static void visitEndForAnnotationVisitor(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
    }

    private int generateBasicChecksAndStoreTarget(InstructionAdapter instructionAdapter, Label label, Label label2) {
        if (this.fieldOwnerContext.getContextKind() == OwnerKind.ERASED_INLINE_CLASS) {
            SimpleType defaultType = this.classDescriptor.getDefaultType();
            Type typeMapTypeAsDeclaration = this.typeMapper.mapTypeAsDeclaration(defaultType);
            int size = this.underlyingType.getType().getSize();
            instructionAdapter.load(size, AsmTypes.OBJECT_TYPE);
            instructionAdapter.instanceOf(typeMapTypeAsDeclaration);
            instructionAdapter.ifeq(label2);
            int i = size + 1;
            instructionAdapter.load(size, AsmTypes.OBJECT_TYPE);
            instructionAdapter.checkcast(typeMapTypeAsDeclaration);
            StackValue.unboxInlineClass(typeMapTypeAsDeclaration, defaultType, instructionAdapter, this.typeMapper);
            instructionAdapter.store(i, this.underlyingType.getType());
            return i;
        }
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.ifacmpeq(label);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.instanceOf(this.classAsmType);
        instructionAdapter.ifeq(label2);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.checkcast(this.classAsmType);
        instructionAdapter.store(2, AsmTypes.OBJECT_TYPE);
        return 2;
    }

    private JvmKotlinType genOrLoadOnStack(InstructionAdapter instructionAdapter, MethodContext methodContext, PropertyDescriptor propertyDescriptor, int i) {
        if (this.fieldOwnerContext.getContextKind() == OwnerKind.ERASED_INLINE_CLASS) {
            instructionAdapter.load(i, this.underlyingType.getType());
            return this.underlyingType;
        }
        return ImplementationBodyCodegen.genPropertyOnStack(instructionAdapter, methodContext, propertyDescriptor, this.classAsmType, i, this.generationState);
    }

    private String getToStringDesc() {
        return "(" + getFirstParameterDesc() + ")Ljava/lang/String;";
    }

    private String getHashCodeDesc() {
        return "(" + getFirstParameterDesc() + ")I";
    }

    private String getEqualsDesc() {
        return "(" + getFirstParameterDesc() + "Ljava/lang/Object;)Z";
    }

    private String getFirstParameterDesc() {
        return this.fieldOwnerContext.getContextKind() == OwnerKind.ERASED_INLINE_CLASS ? this.underlyingType.getType().getDescriptor() : "";
    }

    private int getAccess() {
        return this.fieldOwnerContext.getContextKind() == OwnerKind.ERASED_INLINE_CLASS ? 9 : 1;
    }
}
