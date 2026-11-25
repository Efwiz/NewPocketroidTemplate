package org.jetbrains.kotlin.resolve.jvm.kotlinSignature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.KotlinToJvmSignatureMapper;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.KotlinToJvmSignatureMapperKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes6.dex */
public class SignaturesPropagationData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final KotlinToJvmSignatureMapper SIGNATURE_MAPPER = (KotlinToJvmSignatureMapper) ServiceLoader.load(KotlinToJvmSignatureMapper.class, KotlinToJvmSignatureMapper.class.getClassLoader()).iterator().next();
    private final ValueParameters modifiedValueParameters;
    private final List<String> signatureErrors;
    private final List<FunctionDescriptor> superFunctions;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 9 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 9 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "autoReturnType";
                break;
            case 2:
            case 7:
                objArr[0] = "autoValueParameters";
                break;
            case 3:
            case 8:
                objArr[0] = "autoTypeParameters";
                break;
            case 4:
            case 6:
            case 11:
                objArr[0] = "method";
                break;
            case 5:
            case 13:
            default:
                objArr[0] = "containingClass";
                break;
            case 9:
                objArr[0] = "org/jetbrains/kotlin/resolve/jvm/kotlinSignature/SignaturesPropagationData";
                break;
            case 10:
                objArr[0] = "parameters";
                break;
            case 12:
                objArr[0] = "autoMethodDescriptor";
                break;
            case 14:
                objArr[0] = "originalParam";
                break;
            case 15:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/kotlinSignature/SignaturesPropagationData";
        } else {
            objArr[1] = "createAutoMethodDescriptor";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "createAutoMethodDescriptor";
                break;
            case 9:
                break;
            case 10:
                objArr[2] = "modifyValueParametersAccordingToSuperMethods";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "getSuperFunctionsForMethod";
                break;
            case 14:
                objArr[2] = "checkVarargInSuperFunctions";
                break;
            case 15:
                objArr[2] = "isArrayType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 9) {
            throw new IllegalStateException(str2);
        }
    }

    public SignaturesPropagationData(ClassDescriptor classDescriptor, KotlinType kotlinType, KotlinType kotlinType2, List<ValueParameterDescriptor> list, List<TypeParameterDescriptor> list2, JavaMethod javaMethod) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (list2 == null) {
            $$$reportNull$$$0(3);
        }
        if (javaMethod == null) {
            $$$reportNull$$$0(4);
        }
        this.signatureErrors = new ArrayList(0);
        List<FunctionDescriptor> superFunctionsForMethod = getSuperFunctionsForMethod(javaMethod, createAutoMethodDescriptor(classDescriptor, javaMethod, kotlinType, list, list2), classDescriptor);
        this.superFunctions = superFunctionsForMethod;
        this.modifiedValueParameters = superFunctionsForMethod.isEmpty() ? new ValueParameters(null, list, false) : modifyValueParametersAccordingToSuperMethods(list);
    }

    private static JavaMethodDescriptor createAutoMethodDescriptor(ClassDescriptor classDescriptor, JavaMethod javaMethod, KotlinType kotlinType, List<ValueParameterDescriptor> list, List<TypeParameterDescriptor> list2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (javaMethod == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        if (list2 == null) {
            $$$reportNull$$$0(8);
        }
        JavaMethodDescriptor javaMethodDescriptorCreateJavaMethod = JavaMethodDescriptor.createJavaMethod(classDescriptor, Annotations.INSTANCE.getEMPTY(), javaMethod.getName(), SourceElement.NO_SOURCE, false);
        javaMethodDescriptorCreateJavaMethod.initialize((ReceiverParameterDescriptor) null, classDescriptor.getThisAsReceiverParameter(), CollectionsKt.emptyList(), (List) list2, (List) list, kotlinType, Modality.OPEN, DescriptorVisibilities.PUBLIC);
        if (javaMethodDescriptorCreateJavaMethod == null) {
            $$$reportNull$$$0(9);
        }
        return javaMethodDescriptorCreateJavaMethod;
    }

    public KotlinType getModifiedReceiverType() {
        return this.modifiedValueParameters.receiverType;
    }

    public List<ValueParameterDescriptor> getModifiedValueParameters() {
        return this.modifiedValueParameters.descriptors;
    }

    public boolean getModifiedHasStableParameterNames() {
        return this.modifiedValueParameters.hasStableParameterNames;
    }

    public List<String> getSignatureErrors() {
        return this.signatureErrors;
    }

    void reportError(String str) {
        this.signatureErrors.add(str);
    }

    private ValueParameters modifyValueParametersAccordingToSuperMethods(List<ValueParameterDescriptor> list) {
        Name name2;
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        ArrayList arrayList = new ArrayList(list.size());
        boolean zCheckIfShouldBeExtension = checkIfShouldBeExtension();
        KotlinType kotlinType = null;
        for (final ValueParameterDescriptor valueParameterDescriptor : list) {
            final int index = valueParameterDescriptor.getIndex();
            List map = CollectionsKt.map(this.superFunctions, new Function1() { // from class: org.jetbrains.kotlin.resolve.jvm.kotlinSignature.SignaturesPropagationData$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SignaturesPropagationData.lambda$modifyValueParametersAccordingToSuperMethods$0(index, valueParameterDescriptor, (FunctionDescriptor) obj);
                }
            });
            VarargCheckResult varargCheckResultCheckVarargInSuperFunctions = checkVarargInSuperFunctions(valueParameterDescriptor);
            KotlinType kotlinType2 = varargCheckResultCheckVarargInSuperFunctions.parameterType;
            if (zCheckIfShouldBeExtension && index == 0) {
                kotlinType = kotlinType2;
            } else {
                int i = 0;
                while (true) {
                    if (i >= this.superFunctions.size()) {
                        name2 = null;
                        break;
                    }
                    if (this.superFunctions.get(i).hasStableParameterNames()) {
                        name2 = ((TypeAndName) map.get(i)).f968name;
                        break;
                    }
                    i++;
                }
                CallableDescriptor containingDeclaration = valueParameterDescriptor.getContainingDeclaration();
                if (zCheckIfShouldBeExtension) {
                    index--;
                }
                int i2 = index;
                Annotations annotations = valueParameterDescriptor.getAnnotations();
                if (name2 == null) {
                    name2 = valueParameterDescriptor.getShortName();
                }
                arrayList.add(new ValueParameterDescriptorImpl(containingDeclaration, null, i2, annotations, name2, kotlinType2, valueParameterDescriptor.declaresDefaultValue(), valueParameterDescriptor.getIsCrossinline(), valueParameterDescriptor.getIsNoinline(), varargCheckResultCheckVarargInSuperFunctions.isVararg ? DescriptorUtilsKt.getBuiltIns(valueParameterDescriptor).getArrayElementType(kotlinType2) : null, SourceElement.NO_SOURCE));
            }
        }
        return new ValueParameters(kotlinType, arrayList, CollectionsKt.any(this.superFunctions, new Function1() { // from class: org.jetbrains.kotlin.resolve.jvm.kotlinSignature.SignaturesPropagationData$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((FunctionDescriptor) obj).hasStableParameterNames());
            }
        }));
    }

    static /* synthetic */ TypeAndName lambda$modifyValueParametersAccordingToSuperMethods$0(int i, ValueParameterDescriptor valueParameterDescriptor, FunctionDescriptor functionDescriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            i--;
        }
        if (i == -1) {
            return new TypeAndName(extensionReceiverParameter.getType(), valueParameterDescriptor.getShortName());
        }
        ValueParameterDescriptor valueParameterDescriptor2 = functionDescriptor.getValueParameters().get(i);
        return new TypeAndName(valueParameterDescriptor2.getType(), valueParameterDescriptor2.getShortName());
    }

    private static List<FunctionDescriptor> getSuperFunctionsForMethod(JavaMethod javaMethod, JavaMethodDescriptor javaMethodDescriptor, ClassDescriptor classDescriptor) {
        if (javaMethod == null) {
            $$$reportNull$$$0(11);
        }
        if (javaMethodDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Name name2 = javaMethod.getName();
        boolean zContainsVarargs = SignaturePropagationUtilKt.containsVarargs(javaMethodDescriptor);
        Iterator<KotlinType> it = classDescriptor.getTypeConstructor().mo7438getSupertypes().iterator();
        Method methodMapToJvmMethodSignature = null;
        while (it.hasNext()) {
            Collection<? extends SimpleFunctionDescriptor> contributedFunctions = it.next().getMemberScope().getContributedFunctions(name2, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            if (zContainsVarargs || SignaturePropagationUtilKt.containsAnyNotTrivialSignature(contributedFunctions)) {
                if (methodMapToJvmMethodSignature == null) {
                    methodMapToJvmMethodSignature = SIGNATURE_MAPPER.mapToJvmMethodSignature(javaMethodDescriptor);
                }
                for (SimpleFunctionDescriptor simpleFunctionDescriptor : contributedFunctions) {
                    if (!simpleFunctionDescriptor.isSuspend() && KotlinToJvmSignatureMapperKt.erasedSignaturesEqualIgnoringReturnTypes(methodMapToJvmMethodSignature, SIGNATURE_MAPPER.mapToJvmMethodSignature(simpleFunctionDescriptor))) {
                        arrayListNewArrayList.add(simpleFunctionDescriptor);
                    }
                }
            }
        }
        arrayListNewArrayList.sort(Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.resolve.jvm.kotlinSignature.SignaturesPropagationData$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DescriptorUtils.getFqName(((FunctionDescriptor) obj).getContainingDeclaration()).asString();
            }
        }));
        return arrayListNewArrayList;
    }

    private boolean checkIfShouldBeExtension() {
        Iterator<FunctionDescriptor> it = this.superFunctions.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            if (it.next().getExtensionReceiverParameter() != null) {
                z = true;
            } else {
                z2 = true;
            }
        }
        if (z) {
            if (!z2) {
                return true;
            }
            reportError("Incompatible super methods: some are extension functions, some are not");
        }
        return false;
    }

    private VarargCheckResult checkVarargInSuperFunctions(ValueParameterDescriptor valueParameterDescriptor) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        boolean z = false;
        boolean z2 = false;
        for (FunctionDescriptor functionDescriptor : this.superFunctions) {
            int index = valueParameterDescriptor.getIndex();
            if (functionDescriptor.getExtensionReceiverParameter() != null) {
                index--;
            }
            if (index == -1 || functionDescriptor.getValueParameters().get(index).getVarargElementType() == null) {
                z2 = true;
            } else {
                z = true;
            }
        }
        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
        KotlinType type = valueParameterDescriptor.getType();
        if (z && z2) {
            reportError("Incompatible super methods: some have vararg parameter, some have not");
            return new VarargCheckResult(type, varargElementType != null);
        }
        if (z && varargElementType == null) {
            return new VarargCheckResult(TypeUtils.makeNotNullable(type), true);
        }
        if (z2 && varargElementType != null) {
            return new VarargCheckResult(TypeUtils.makeNullable(type), false);
        }
        return new VarargCheckResult(type, varargElementType != null);
    }

    private static boolean isArrayType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(15);
        }
        return KotlinBuiltIns.isArray(kotlinType) || KotlinBuiltIns.isPrimitiveArray(kotlinType);
    }

    private static class VarargCheckResult {
        public final boolean isVararg;
        public final KotlinType parameterType;

        public VarargCheckResult(KotlinType kotlinType, boolean z) {
            this.parameterType = kotlinType;
            this.isVararg = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TypeAndName {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final Name f968name;
        public final KotlinType type;

        public TypeAndName(KotlinType kotlinType, Name name2) {
            this.type = kotlinType;
            this.f968name = name2;
        }
    }

    private static class ValueParameters {
        private final List<ValueParameterDescriptor> descriptors;
        private final boolean hasStableParameterNames;
        private final KotlinType receiverType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "descriptors", "org/jetbrains/kotlin/resolve/jvm/kotlinSignature/SignaturesPropagationData$ValueParameters", "<init>"));
        }

        public ValueParameters(KotlinType kotlinType, List<ValueParameterDescriptor> list, boolean z) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            this.receiverType = kotlinType;
            this.descriptors = list;
            this.hasStableParameterNames = z;
        }
    }
}
