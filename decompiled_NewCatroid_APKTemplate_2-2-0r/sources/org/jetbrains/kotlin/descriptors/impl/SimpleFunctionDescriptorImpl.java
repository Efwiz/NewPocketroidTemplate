package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleFunctionDescriptorImpl extends FunctionDescriptorImpl implements SimpleFunctionDescriptor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 18 || i == 23 || i == 24 || i == 29 || i == 30) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 18 || i == 23 || i == 24 || i == 29 || i == 30) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 27:
                objArr[0] = "annotations";
                break;
            case 2:
            case 7:
                objArr[0] = "name";
                break;
            case 3:
            case 8:
            case 26:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 4:
            case 9:
            case 28:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 5:
            default:
                objArr[0] = "containingDeclaration";
                break;
            case 10:
            case 15:
            case 20:
                objArr[0] = "typeParameters";
                break;
            case 11:
            case 16:
            case 21:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 12:
            case 17:
            case 22:
                objArr[0] = "visibility";
                break;
            case 13:
            case 18:
            case 23:
            case 24:
            case 29:
            case 30:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl";
                break;
            case 14:
            case 19:
                objArr[0] = "contextReceiverParameters";
                break;
            case 25:
                objArr[0] = "newOwner";
                break;
        }
        if (i == 13 || i == 18 || i == 23) {
            objArr[1] = "initialize";
        } else if (i == 24) {
            objArr[1] = "getOriginal";
        } else if (i == 29) {
            objArr[1] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
        } else if (i != 30) {
            objArr[1] = "org/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl";
        } else {
            objArr[1] = "newCopyBuilder";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[2] = "initialize";
                break;
            case 13:
            case 18:
            case 23:
            case 24:
            case 29:
            case 30:
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "createSubstitutedCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 18 && i != 23 && i != 24 && i != 29 && i != 30) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    public /* bridge */ /* synthetic */ FunctionDescriptorImpl initialize(ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List list, List list2, List list3, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility) {
        return initialize(receiverParameterDescriptor, receiverParameterDescriptor2, (List<ReceiverParameterDescriptor>) list, (List<? extends TypeParameterDescriptor>) list2, (List<ValueParameterDescriptor>) list3, kotlinType, modality, descriptorVisibility);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected SimpleFunctionDescriptorImpl(DeclarationDescriptor declarationDescriptor, SimpleFunctionDescriptor simpleFunctionDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement sourceElement) {
        super(declarationDescriptor, simpleFunctionDescriptor, annotations, name2, kind, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        if (kind == null) {
            $$$reportNull$$$0(3);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(4);
        }
    }

    public static SimpleFunctionDescriptorImpl create(DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (annotations == null) {
            $$$reportNull$$$0(6);
        }
        if (name2 == null) {
            $$$reportNull$$$0(7);
        }
        if (kind == null) {
            $$$reportNull$$$0(8);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(9);
        }
        return new SimpleFunctionDescriptorImpl(declarationDescriptor, null, annotations, name2, kind, sourceElement);
    }

    @Deprecated(message = "This method is left for binary compatibility with android.nav.safearg plugin. Used in SafeArgSyntheticDescriptorGenerator.kt")
    public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<? extends TypeParameterDescriptor> list, List<ValueParameterDescriptor> list2, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility) {
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        if (list2 == null) {
            $$$reportNull$$$0(11);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(12);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplInitialize = initialize(receiverParameterDescriptor, receiverParameterDescriptor2, Collections.emptyList(), list, list2, kotlinType, modality, descriptorVisibility, null);
        if (simpleFunctionDescriptorImplInitialize == null) {
            $$$reportNull$$$0(13);
        }
        return simpleFunctionDescriptorImplInitialize;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<ReceiverParameterDescriptor> list, List<? extends TypeParameterDescriptor> list2, List<ValueParameterDescriptor> list3, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility) {
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        if (list2 == null) {
            $$$reportNull$$$0(15);
        }
        if (list3 == null) {
            $$$reportNull$$$0(16);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(17);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplInitialize = initialize(receiverParameterDescriptor, receiverParameterDescriptor2, list, list2, list3, kotlinType, modality, descriptorVisibility, null);
        if (simpleFunctionDescriptorImplInitialize == null) {
            $$$reportNull$$$0(18);
        }
        return simpleFunctionDescriptorImplInitialize;
    }

    public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<ReceiverParameterDescriptor> list, List<? extends TypeParameterDescriptor> list2, List<ValueParameterDescriptor> list3, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> map) {
        if (list == null) {
            $$$reportNull$$$0(19);
        }
        if (list2 == null) {
            $$$reportNull$$$0(20);
        }
        if (list3 == null) {
            $$$reportNull$$$0(21);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(22);
        }
        super.initialize(receiverParameterDescriptor, receiverParameterDescriptor2, list, list2, list3, kotlinType, modality, descriptorVisibility);
        if (map != null && !map.isEmpty()) {
            this.userDataMap = new LinkedHashMap(map);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public SimpleFunctionDescriptor getOriginal() {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) super.getOriginal();
        if (simpleFunctionDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        return simpleFunctionDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name2, Annotations annotations, SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (kind == null) {
            $$$reportNull$$$0(26);
        }
        if (annotations == null) {
            $$$reportNull$$$0(27);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(28);
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptor;
        if (name2 == null) {
            name2 = getName();
        }
        return new SimpleFunctionDescriptorImpl(declarationDescriptor, simpleFunctionDescriptor, annotations, name2, kind, sourceElement);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public SimpleFunctionDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) super.copy(declarationDescriptor, modality, descriptorVisibility, kind, z);
        if (simpleFunctionDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        return simpleFunctionDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder() {
        FunctionDescriptor.CopyBuilder copyBuilderNewCopyBuilder = super.newCopyBuilder();
        if (copyBuilderNewCopyBuilder == null) {
            $$$reportNull$$$0(30);
        }
        return copyBuilderNewCopyBuilder;
    }
}
