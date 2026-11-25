package org.jetbrains.kotlin.load.java.sam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConstructorTypeParameters;
import org.jetbrains.kotlin.resolve.sam.SamConstructorUtilsKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: loaded from: classes5.dex */
public class JavaSingleAbstractMethodUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 14 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 14 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
                objArr[0] = "fun";
                break;
            case 3:
            case 6:
            case 9:
            case 15:
                objArr[0] = "original";
                break;
            case 4:
            case 7:
            case 12:
            case 18:
                objArr[0] = "samResolver";
                break;
            case 5:
            case 8:
            case 13:
            case 19:
                objArr[0] = "samConversionOracle";
                break;
            case 10:
                objArr[0] = "adapter";
                break;
            case 11:
                objArr[0] = "initializer";
                break;
            case 14:
                objArr[0] = "org/jetbrains/kotlin/load/java/sam/JavaSingleAbstractMethodUtils";
                break;
            case 16:
                objArr[0] = "samAdapter";
                break;
            case 17:
                objArr[0] = "substitutor";
                break;
            default:
                objArr[0] = "descriptor";
                break;
        }
        if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/load/java/sam/JavaSingleAbstractMethodUtils";
        } else {
            objArr[1] = "initSamAdapter";
        }
        switch (i) {
            case 1:
                objArr[2] = "isSamType";
                break;
            case 2:
                objArr[2] = "isSamAdapterNecessary";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "createSamAdapterFunction";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "createSamAdapterConstructor";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "initSamAdapter";
                break;
            case 14:
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "createValueParametersForSamAdapter";
                break;
            default:
                objArr[2] = "isSamClassDescriptor";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 14) {
            throw new IllegalStateException(str2);
        }
    }

    private JavaSingleAbstractMethodUtils() {
    }

    public static boolean isSamClassDescriptor(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (classDescriptor.isFun()) {
            return true;
        }
        return (classDescriptor instanceof LazyJavaClassDescriptor) && classDescriptor.getDefaultFunctionTypeForSamInterface() != null;
    }

    public static boolean isSamType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(1);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        return ((classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).isFun()) || SamConversionResolverImplKt.getFunctionTypeForSamType(kotlinType, JavaBasedSamConversionResolver.INSTANCE, JavaBasedSamConversionOracle.INSTANCE) != null;
    }

    public static boolean isSamAdapterNecessary(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<ValueParameterDescriptor> it = functionDescriptor.getValueParameters().iterator();
        while (it.hasNext()) {
            if (isSamType(it.next().getType())) {
                return true;
            }
        }
        return false;
    }

    public static SamAdapterDescriptor<JavaMethodDescriptor> createSamAdapterFunction(final JavaMethodDescriptor javaMethodDescriptor, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle, boolean z) {
        if (javaMethodDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (samConversionResolver == null) {
            $$$reportNull$$$0(4);
        }
        if (samConversionOracle == null) {
            $$$reportNull$$$0(5);
        }
        final SamAdapterFunctionDescriptor samAdapterFunctionDescriptor = new SamAdapterFunctionDescriptor(javaMethodDescriptor);
        return initSamAdapter(javaMethodDescriptor, samAdapterFunctionDescriptor, new FunctionInitializer() { // from class: org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "valueParameters";
                } else if (i != 2) {
                    objArr[0] = "typeParameters";
                } else {
                    objArr[0] = "returnType";
                }
                objArr[1] = "org/jetbrains/kotlin/load/java/sam/JavaSingleAbstractMethodUtils$1";
                objArr[2] = "initialize";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils.FunctionInitializer
            public void initialize(List<TypeParameterDescriptor> list, List<ValueParameterDescriptor> list2, KotlinType kotlinType) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (list2 == null) {
                    $$$reportNull$$$0(1);
                }
                if (kotlinType == null) {
                    $$$reportNull$$$0(2);
                }
                samAdapterFunctionDescriptor.initialize((ReceiverParameterDescriptor) null, javaMethodDescriptor.mo6753getDispatchReceiverParameter(), CollectionsKt.emptyList(), (List) list, (List) list2, kotlinType, Modality.FINAL, javaMethodDescriptor.getVisibility());
            }
        }, samConversionResolver, samConversionOracle, z);
    }

    public static SamAdapterDescriptor<JavaClassConstructorDescriptor> createSamAdapterConstructor(final JavaClassConstructorDescriptor javaClassConstructorDescriptor, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle, boolean z) {
        if (javaClassConstructorDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (samConversionResolver == null) {
            $$$reportNull$$$0(7);
        }
        if (samConversionOracle == null) {
            $$$reportNull$$$0(8);
        }
        final SamAdapterClassConstructorDescriptor samAdapterClassConstructorDescriptor = new SamAdapterClassConstructorDescriptor(javaClassConstructorDescriptor);
        return initSamAdapter(javaClassConstructorDescriptor, samAdapterClassConstructorDescriptor, new FunctionInitializer() { // from class: org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "valueParameters";
                } else if (i != 2) {
                    objArr[0] = "typeParameters";
                } else {
                    objArr[0] = "returnType";
                }
                objArr[1] = "org/jetbrains/kotlin/load/java/sam/JavaSingleAbstractMethodUtils$2";
                objArr[2] = "initialize";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils.FunctionInitializer
            public void initialize(List<TypeParameterDescriptor> list, List<ValueParameterDescriptor> list2, KotlinType kotlinType) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (list2 == null) {
                    $$$reportNull$$$0(1);
                }
                if (kotlinType == null) {
                    $$$reportNull$$$0(2);
                }
                samAdapterClassConstructorDescriptor.initialize(list2, javaClassConstructorDescriptor.getVisibility());
                samAdapterClassConstructorDescriptor.setReturnType(kotlinType);
            }
        }, samConversionResolver, samConversionOracle, z);
    }

    private static <F extends FunctionDescriptor> SamAdapterDescriptor<F> initSamAdapter(F f, SamAdapterDescriptor<F> samAdapterDescriptor, FunctionInitializer functionInitializer, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle, boolean z) {
        SamConstructorTypeParameters samConstructorTypeParametersRecreateAndInitializeTypeParameters;
        if (f == null) {
            $$$reportNull$$$0(9);
        }
        if (samAdapterDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (functionInitializer == null) {
            $$$reportNull$$$0(11);
        }
        if (samConversionResolver == null) {
            $$$reportNull$$$0(12);
        }
        if (samConversionOracle == null) {
            $$$reportNull$$$0(13);
        }
        if (samAdapterDescriptor instanceof SamAdapterClassConstructorDescriptor) {
            samConstructorTypeParametersRecreateAndInitializeTypeParameters = new SamConstructorTypeParameters(f.getTypeParameters(), TypeSubstitutor.EMPTY);
        } else {
            samConstructorTypeParametersRecreateAndInitializeTypeParameters = SamConstructorUtilsKt.recreateAndInitializeTypeParameters(f.getTypeParameters(), samAdapterDescriptor);
        }
        KotlinType returnType = f.getReturnType();
        TypeSubstitutor substitutor = samConstructorTypeParametersRecreateAndInitializeTypeParameters.getSubstitutor();
        KotlinType kotlinTypeSubstitute = substitutor.substitute(returnType, Variance.INVARIANT);
        functionInitializer.initialize(samConstructorTypeParametersRecreateAndInitializeTypeParameters.getDescriptors(), createValueParametersForSamAdapter(f, samAdapterDescriptor, substitutor, samConversionResolver, samConversionOracle, z), kotlinTypeSubstitute);
        if (samAdapterDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        return samAdapterDescriptor;
    }

    public static List<ValueParameterDescriptor> createValueParametersForSamAdapter(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, TypeSubstitutor typeSubstitutor, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle, boolean z) {
        TypeSubstitutor typeSubstitutor2 = typeSubstitutor;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(16);
        }
        if (typeSubstitutor2 == null) {
            $$$reportNull$$$0(17);
        }
        if (samConversionResolver == null) {
            $$$reportNull$$$0(18);
        }
        if (samConversionOracle == null) {
            $$$reportNull$$$0(19);
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
            KotlinType type = valueParameterDescriptor.getType();
            KotlinType functionTypeForSamType = SamConversionResolverImplKt.getFunctionTypeForSamType(type, samConversionResolver, samConversionOracle);
            if (functionTypeForSamType != null) {
                type = functionTypeForSamType;
            }
            KotlinType kotlinTypeSubstitute = typeSubstitutor2.substitute(type, Variance.IN_VARIANCE);
            ArrayList arrayList2 = arrayList;
            arrayList2.add(new ValueParameterDescriptorImpl(functionDescriptor2, null, valueParameterDescriptor.getIndex(), valueParameterDescriptor.getAnnotations(), valueParameterDescriptor.getName(), kotlinTypeSubstitute, false, false, false, z ? null : valueParameterDescriptor.getVarargElementType(), SourceElement.NO_SOURCE));
            arrayList = arrayList2;
            typeSubstitutor2 = typeSubstitutor;
        }
        return arrayList;
    }

    private static abstract class FunctionInitializer {
        public abstract void initialize(List<TypeParameterDescriptor> list, List<ValueParameterDescriptor> list2, KotlinType kotlinType);

        private FunctionInitializer() {
        }
    }
}
