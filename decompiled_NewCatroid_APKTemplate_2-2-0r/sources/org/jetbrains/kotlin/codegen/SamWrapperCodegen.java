package org.jetbrains.kotlin.codegen;

import java.util.Collections;
import java.util.Map;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class SamWrapperCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Method GET_FUNCTION_DELEGATE = new Method("getFunctionDelegate", AsmTypes.FUNCTION, new Type[0]);
    public static final String SAM_WRAPPER_SUFFIX = "$0";
    private final int classFlags;
    private final boolean isInsideInline;
    private final MemberCodegen<?> parentCodegen;
    private final CodegenContext<?> parentContext;
    private final SamType samType;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;
    private final int visibility;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 29) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 29) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "samType";
                break;
            case 2:
                objArr[0] = "parentCodegen";
                break;
            case 3:
                objArr[0] = "parentContext";
                break;
            case 4:
            case 7:
                objArr[0] = "file";
                break;
            case 5:
                objArr[0] = "contextDescriptor";
                break;
            case 6:
            case 29:
                objArr[0] = "org/jetbrains/kotlin/codegen/SamWrapperCodegen";
                break;
            case 8:
                objArr[0] = "ownerType";
                break;
            case 9:
                objArr[0] = "functionType";
                break;
            case 10:
                objArr[0] = "erasedInterfaceFunction";
                break;
            case 11:
                objArr[0] = "functionKotlinType";
                break;
            case 12:
            case 26:
                objArr[0] = "functionCodegen";
                break;
            case 13:
            case 17:
            case 20:
                objArr[0] = "cv";
                break;
            case 14:
            case 18:
            case 21:
            case 23:
                objArr[0] = "asmType";
                break;
            case 15:
            case 19:
            case 22:
                objArr[0] = "functionAsmType";
                break;
            case 16:
                objArr[0] = "samAsmType";
                break;
            case 24:
                objArr[0] = "classDescriptor";
                break;
            case 25:
                objArr[0] = "funInterface";
                break;
            case 27:
            default:
                objArr[0] = "state";
                break;
            case 28:
                objArr[0] = "containingFile";
                break;
        }
        if (i == 6) {
            objArr[1] = "genWrapper";
        } else if (i != 29) {
            objArr[1] = "org/jetbrains/kotlin/codegen/SamWrapperCodegen";
        } else {
            objArr[1] = "getWrapperName";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "genWrapper";
                break;
            case 6:
            case 29:
                break;
            case 7:
                objArr[2] = "generateInnerClassInformation";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "generateMethod";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "generateEquals";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "generateHashCode";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "generateGetFunctionDelegate";
                break;
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[2] = "generateDelegatesToDefaultImpl";
                break;
            case 28:
                objArr[2] = "getWrapperName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 29) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public SamWrapperCodegen(GenerationState generationState, SamType samType, MemberCodegen<?> memberCodegen, CodegenContext<?> codegenContext, boolean z) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (samType == null) {
            $$$reportNull$$$0(1);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(2);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(3);
        }
        this.state = generationState;
        this.isInsideInline = z;
        this.typeMapper = generationState.getTypeMapper();
        this.samType = samType;
        this.parentCodegen = memberCodegen;
        this.parentContext = codegenContext;
        this.visibility = z ? 1 : 0;
        this.classFlags = (z ? 1 : 0) | 48 | (generationState.getLanguageVersionSettings().supportsFeature(LanguageFeature.SamWrapperClassesAreSynthetic) ? 4096 : 0);
    }

    public Type genWrapper(KtFile ktFile, CallableMemberDescriptor callableMemberDescriptor) {
        if (ktFile == null) {
            $$$reportNull$$$0(4);
        }
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        FqName wrapperName = getWrapperName(ktFile, callableMemberDescriptor);
        Type typeAsmTypeByFqNameWithoutInnerClasses = AsmUtil.asmTypeByFqNameWithoutInnerClasses(wrapperName);
        KotlinType kotlinFunctionType = this.samType.getKotlinFunctionType();
        boolean z = this.samType.getClassDescriptor() instanceof JavaClassDescriptor;
        ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl(this.samType.getClassDescriptor().getContainingModule(), wrapperName.shortName(), Modality.FINAL, ClassKind.CLASS, Collections.singleton(this.samType.getType()), SourceElement.NO_SOURCE, false, LockBasedStorageManager.NO_LOCKS);
        classDescriptorImpl.initialize(MemberScope.Empty.INSTANCE, Collections.emptySet(), null);
        SimpleFunctionDescriptor simpleFunctionDescriptorCopy = this.samType.getOriginalAbstractMethod().copy((DeclarationDescriptor) classDescriptorImpl, Modality.FINAL, DescriptorVisibilities.PUBLIC, CallableMemberDescriptor.Kind.SYNTHESIZED, false);
        ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(simpleFunctionDescriptorCopy), typeAsmTypeByFqNameWithoutInnerClasses, ktFile);
        Type typeMapType = this.typeMapper.mapType(this.samType.getType());
        classBuilderNewVisitor.defineClass(ktFile, this.state.getConfig().getClassFileVersion(), this.classFlags, typeAsmTypeByFqNameWithoutInnerClasses.getInternalName(), null, AsmTypes.OBJECT_TYPE.getInternalName(), !z ? new String[]{typeMapType.getInternalName(), AsmTypes.FUNCTION_ADAPTER.getInternalName()} : new String[]{typeMapType.getInternalName()});
        classBuilderNewVisitor.visitSource(ktFile.get$name(), null);
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(classBuilderNewVisitor, this.state, this.isInsideInline);
        generateInnerClassInformation(ktFile, typeAsmTypeByFqNameWithoutInnerClasses, classBuilderNewVisitor);
        Type typeMapType2 = this.typeMapper.mapType(kotlinFunctionType);
        classBuilderNewVisitor.newField(JvmDeclarationOriginKt.OtherOrigin(simpleFunctionDescriptorCopy), 4114, "function", typeMapType2.getDescriptor(), null, null);
        generateConstructor(typeAsmTypeByFqNameWithoutInnerClasses, typeMapType2, classBuilderNewVisitor);
        FunctionCodegen functionCodegen = new FunctionCodegen(this.state.getRootContext().intoClass(classDescriptorImpl, OwnerKind.IMPLEMENTATION, this.state), classBuilderNewVisitor, this.state, this.parentCodegen);
        generateMethod(typeAsmTypeByFqNameWithoutInnerClasses, typeMapType2, simpleFunctionDescriptorCopy, kotlinFunctionType, functionCodegen);
        if (!z) {
            generateGetFunctionDelegate(classBuilderNewVisitor, typeAsmTypeByFqNameWithoutInnerClasses, typeMapType2);
            generateEquals(classBuilderNewVisitor, typeAsmTypeByFqNameWithoutInnerClasses, typeMapType2, typeMapType);
            generateHashCode(classBuilderNewVisitor, typeAsmTypeByFqNameWithoutInnerClasses, typeMapType2);
            generateDelegatesToDefaultImpl(typeAsmTypeByFqNameWithoutInnerClasses, classDescriptorImpl, this.samType.getClassDescriptor(), functionCodegen, this.state);
        }
        classBuilderNewVisitor.done(this.state.getConfig().getGenerateSmapCopyToAnnotation());
        if (typeAsmTypeByFqNameWithoutInnerClasses == null) {
            $$$reportNull$$$0(6);
        }
        return typeAsmTypeByFqNameWithoutInnerClasses;
    }

    private void generateInnerClassInformation(KtFile ktFile, Type type, ClassBuilder classBuilder) {
        if (ktFile == null) {
            $$$reportNull$$$0(7);
        }
        this.parentCodegen.addSyntheticAnonymousInnerClass(new SyntheticInnerClassInfo(type.getInternalName(), this.classFlags));
        CodegenContext<?> nonInlineOuterContext = MemberCodegen.getNonInlineOuterContext(this.parentContext);
        Type typeComputeOuterClass = MemberCodegen.computeOuterClass(this.state.getTypeMapper(), this.state.getJvmDefaultMode(), ktFile, nonInlineOuterContext);
        Method methodComputeEnclosingMethod = MemberCodegen.computeEnclosingMethod(this.state.getTypeMapper(), nonInlineOuterContext);
        classBuilder.visitOuterClass(typeComputeOuterClass.getInternalName(), methodComputeEnclosingMethod == null ? null : methodComputeEnclosingMethod.getName(), methodComputeEnclosingMethod == null ? null : methodComputeEnclosingMethod.getDescriptor());
        classBuilder.visitInnerClass(type.getInternalName(), null, null, this.classFlags);
    }

    private void generateConstructor(Type type, Type type2, ClassBuilder classBuilder) {
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(JvmDeclarationOriginKt.OtherOrigin(this.samType.getClassDescriptor()), this.visibility, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, type2), null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
            instructionAdapter.invokespecial(AsmTypes.OBJECT_TYPE.getInternalName(), "<init>", "()V", false);
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
            instructionAdapter.load(1, type2);
            instructionAdapter.putfield(type.getInternalName(), "function", type2.getDescriptor());
            instructionAdapter.visitInsn(177);
            FunctionCodegen.endVisit(instructionAdapter, "constructor of SAM wrapper");
        }
    }

    private void generateMethod(Type type, Type type2, SimpleFunctionDescriptor simpleFunctionDescriptor, KotlinType kotlinType, FunctionCodegen functionCodegen) {
        if (type == null) {
            $$$reportNull$$$0(8);
        }
        if (type2 == null) {
            $$$reportNull$$$0(9);
        }
        if (simpleFunctionDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(11);
        }
        if (functionCodegen == null) {
            $$$reportNull$$$0(12);
        }
        functionCodegen.genSamDelegate(simpleFunctionDescriptor, kotlinType.getMemberScope().getContributedFunctions(OperatorNameConventions.INVOKE, NoLookupLocation.FROM_BACKEND).iterator().next().getOriginal(), StackValue.field(type2, type, "function", false, StackValue.none()));
        ClosureCodegen.generateBridgesForSAM(this.samType.getOriginalAbstractMethod(), simpleFunctionDescriptor, functionCodegen);
    }

    private static void generateEquals(ClassBuilder classBuilder, Type type, Type type2, Type type3) {
        if (classBuilder == null) {
            $$$reportNull$$$0(13);
        }
        if (type == null) {
            $$$reportNull$$$0(14);
        }
        if (type2 == null) {
            $$$reportNull$$$0(15);
        }
        if (type3 == null) {
            $$$reportNull$$$0(16);
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(classBuilder.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 1, Namer.EQUALS_METHOD_NAME, "(Ljava/lang/Object;)Z", null, null));
        Label label = new Label();
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.instanceOf(type3);
        instructionAdapter.ifeq(label);
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.instanceOf(AsmTypes.FUNCTION_ADAPTER);
        instructionAdapter.ifeq(label);
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.getfield(type.getInternalName(), "function", type2.getDescriptor());
        instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
        instructionAdapter.checkcast(AsmTypes.FUNCTION_ADAPTER);
        String internalName = AsmTypes.FUNCTION_ADAPTER.getInternalName();
        Method method = GET_FUNCTION_DELEGATE;
        instructionAdapter.invokeinterface(internalName, method.getName(), method.getDescriptor());
        DescriptorAsmUtil.genAreEqualCall(instructionAdapter);
        instructionAdapter.ifeq(label);
        instructionAdapter.iconst(1);
        Label label2 = new Label();
        instructionAdapter.goTo(label2);
        instructionAdapter.visitLabel(label);
        instructionAdapter.iconst(0);
        instructionAdapter.visitLabel(label2);
        instructionAdapter.areturn(Type.BOOLEAN_TYPE);
        FunctionCodegen.endVisit(instructionAdapter, "equals of SAM wrapper");
    }

    private static void generateHashCode(ClassBuilder classBuilder, Type type, Type type2) {
        if (classBuilder == null) {
            $$$reportNull$$$0(17);
        }
        if (type == null) {
            $$$reportNull$$$0(18);
        }
        if (type2 == null) {
            $$$reportNull$$$0(19);
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(classBuilder.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 1, "hashCode", "()I", null, null));
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.getfield(type.getInternalName(), "function", type2.getDescriptor());
        instructionAdapter.invokevirtual(AsmTypes.OBJECT_TYPE.getInternalName(), "hashCode", "()I", false);
        instructionAdapter.areturn(Type.INT_TYPE);
        FunctionCodegen.endVisit(instructionAdapter, "hashCode of SAM wrapper");
    }

    private static void generateGetFunctionDelegate(ClassBuilder classBuilder, Type type, Type type2) {
        if (classBuilder == null) {
            $$$reportNull$$$0(20);
        }
        if (type == null) {
            $$$reportNull$$$0(21);
        }
        if (type2 == null) {
            $$$reportNull$$$0(22);
        }
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        Method method = GET_FUNCTION_DELEGATE;
        InstructionAdapter instructionAdapter = new InstructionAdapter(classBuilder.newMethod(jvmDeclarationOrigin, 1, method.getName(), method.getDescriptor(), null, null));
        instructionAdapter.load(0, type);
        instructionAdapter.getfield(type.getInternalName(), "function", type2.getDescriptor());
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
        FunctionCodegen.endVisit(instructionAdapter, "getFunctionDelegate of SAM wrapper");
    }

    public static void generateDelegatesToDefaultImpl(Type type, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2, FunctionCodegen functionCodegen, GenerationState generationState) {
        if (type == null) {
            $$$reportNull$$$0(23);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (classDescriptor2 == null) {
            $$$reportNull$$$0(25);
        }
        if (functionCodegen == null) {
            $$$reportNull$$$0(26);
        }
        if (generationState == null) {
            $$$reportNull$$$0(27);
        }
        JvmKotlinType jvmKotlinType = new JvmKotlinType(type, classDescriptor.getDefaultType());
        for (DeclarationDescriptor declarationDescriptor : DescriptorUtils.getAllDescriptors(classDescriptor2.getDefaultType().getMemberScope())) {
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                if (callableMemberDescriptor.getModality() != Modality.ABSTRACT && !DescriptorVisibilities.isPrivate(callableMemberDescriptor.getVisibility()) && callableMemberDescriptor.getVisibility() != DescriptorVisibilities.INVISIBLE_FAKE && !DescriptorUtils.isMethodOfAny(callableMemberDescriptor)) {
                    for (Map.Entry<FunctionDescriptor, FunctionDescriptor> entry : CodegenUtil.INSTANCE.copyFunctions(callableMemberDescriptor, callableMemberDescriptor, classDescriptor, Modality.OPEN, DescriptorVisibilities.PUBLIC, CallableMemberDescriptor.Kind.DECLARATION, false).entrySet()) {
                        ClassBodyCodegen.generateDelegationToDefaultImpl(entry.getKey(), entry.getValue(), jvmKotlinType, functionCodegen, generationState, false);
                    }
                }
            }
        }
    }

    private FqName getWrapperName(KtFile ktFile, CallableMemberDescriptor callableMemberDescriptor) {
        if (ktFile == null) {
            $$$reportNull$$$0(28);
        }
        boolean zIsEmpty = CodegenUtil.getMemberDeclarationsToGenerate(ktFile).isEmpty();
        FqName fileClassFqName = JvmFileClassUtil.getFileClassInfoNoResolve(ktFile).getFileClassFqName();
        if (zIsEmpty) {
            ClassDescriptor outermostParentClass = getOutermostParentClass(callableMemberDescriptor);
            if (outermostParentClass == null) {
                throw new IllegalStateException("Can't find outermost parent class for " + callableMemberDescriptor);
            }
            String internalName = this.typeMapper.mapType(outermostParentClass).getInternalName();
            fileClassFqName = fileClassFqName.parent().child(Name.identifier(StringsKt.substringAfterLast(internalName, '/', internalName)));
        }
        FqName fqNameChild = fileClassFqName.parent().child(Name.identifier(String.format("%s$sam%s$%s$0", fileClassFqName.shortName().asString(), this.isInsideInline ? "$i" : "", DescriptorUtils.getFqNameSafe(this.samType.getClassDescriptor()).asString().replace('.', '_'))));
        if (fqNameChild == null) {
            $$$reportNull$$$0(29);
        }
        return fqNameChild;
    }

    private static ClassDescriptor getOutermostParentClass(CallableMemberDescriptor callableMemberDescriptor) {
        ClassDescriptor classDescriptor;
        ClassDescriptor classDescriptor2 = (ClassDescriptor) DescriptorUtils.getParentOfType(callableMemberDescriptor, ClassDescriptor.class, true);
        do {
            classDescriptor = (ClassDescriptor) DescriptorUtils.getParentOfType(classDescriptor2, ClassDescriptor.class, true);
            if (classDescriptor != null) {
                classDescriptor2 = classDescriptor;
            }
        } while (classDescriptor != null);
        return classDescriptor2;
    }
}
