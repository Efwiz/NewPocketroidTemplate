package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitContextReceiver;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FunctionDescriptorImpl extends DeclarationDescriptorNonRootImpl implements FunctionDescriptor {
    private List<ReceiverParameterDescriptor> contextReceiverParameters;
    private ReceiverParameterDescriptor dispatchReceiverParameter;
    private ReceiverParameterDescriptor extensionReceiverParameter;
    private boolean hasStableParameterNames;
    private boolean hasSynthesizedParameterNames;
    private FunctionDescriptor initialSignatureDescriptor;
    private boolean isActual;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isHiddenForResolutionEverywhereBesideSupercalls;
    private boolean isHiddenToOvercomeSignatureClash;
    private boolean isInfix;
    private boolean isInline;
    private boolean isOperator;
    private boolean isSuspend;
    private boolean isTailrec;
    private final CallableMemberDescriptor.Kind kind;
    private volatile Function0<Collection<FunctionDescriptor>> lazyOverriddenFunctionsTask;
    private Modality modality;
    private final FunctionDescriptor original;
    private Collection<? extends FunctionDescriptor> overriddenFunctions;
    private List<TypeParameterDescriptor> typeParameters;
    private KotlinType unsubstitutedReturnType;
    private List<ValueParameterDescriptor> unsubstitutedValueParameters;
    protected Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap;
    private DescriptorVisibility visibility;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 26:
            case 27:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 10:
            case 11:
            case 12:
            case 17:
            case 22:
            case 24:
            case 25:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 26:
            case 27:
                i2 = 2;
                break;
            case 10:
            case 11:
            case 12:
            case 17:
            case 22:
            case 24:
            case 25:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 4:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 5:
                objArr[0] = "contextReceiverParameters";
                break;
            case 6:
                objArr[0] = "typeParameters";
                break;
            case 7:
            case 28:
            case 30:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 8:
            case 10:
                objArr[0] = "visibility";
                break;
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 26:
            case 27:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl";
                break;
            case 11:
                objArr[0] = "unsubstitutedReturnType";
                break;
            case 12:
                objArr[0] = "extensionReceiverParameter";
                break;
            case 17:
                objArr[0] = "overriddenDescriptors";
                break;
            case 22:
                objArr[0] = "originalSubstitutor";
                break;
            case 24:
            case 29:
            case 31:
                objArr[0] = "substitutor";
                break;
            case 25:
                objArr[0] = "configuration";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 9:
                objArr[1] = "initialize";
                break;
            case 10:
            case 11:
            case 12:
            case 17:
            case 22:
            case 24:
            case 25:
            default:
                objArr[1] = "org/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl";
                break;
            case 13:
                objArr[1] = "getContextReceiverParameters";
                break;
            case 14:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 15:
                objArr[1] = "getModality";
                break;
            case 16:
                objArr[1] = "getVisibility";
                break;
            case 18:
                objArr[1] = "getTypeParameters";
                break;
            case 19:
                objArr[1] = "getValueParameters";
                break;
            case 20:
                objArr[1] = "getOriginal";
                break;
            case 21:
                objArr[1] = "getKind";
                break;
            case 23:
                objArr[1] = "newCopyBuilder";
                break;
            case 26:
                objArr[1] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 27:
                objArr[1] = "getSourceToUseForCopy";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "initialize";
                break;
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 26:
            case 27:
                break;
            case 10:
                objArr[2] = "setVisibility";
                break;
            case 11:
                objArr[2] = "setReturnType";
                break;
            case 12:
                objArr[2] = "setExtensionReceiverParameter";
                break;
            case 17:
                objArr[2] = "setOverriddenDescriptors";
                break;
            case 22:
                objArr[2] = "substitute";
                break;
            case 24:
                objArr[2] = "newCopyBuilder";
                break;
            case 25:
                objArr[2] = "doSubstitute";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "getSubstitutedValueParameters";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 9:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 26:
            case 27:
                throw new IllegalStateException(str2);
            case 10:
            case 11:
            case 12:
            case 17:
            case 22:
            case 24:
            case 25:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected abstract FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name2, Annotations annotations, SourceElement sourceElement);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected FunctionDescriptorImpl(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement sourceElement) {
        super(declarationDescriptor, annotations, name2, sourceElement);
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
        this.visibility = DescriptorVisibilities.UNKNOWN;
        this.isOperator = false;
        this.isInfix = false;
        this.isExternal = false;
        this.isInline = false;
        this.isTailrec = false;
        this.isExpect = false;
        this.isActual = false;
        this.isHiddenToOvercomeSignatureClash = false;
        this.isHiddenForResolutionEverywhereBesideSupercalls = false;
        this.isSuspend = false;
        this.hasStableParameterNames = true;
        this.hasSynthesizedParameterNames = false;
        this.overriddenFunctions = null;
        this.lazyOverriddenFunctionsTask = null;
        this.initialSignatureDescriptor = null;
        this.userDataMap = null;
        this.original = functionDescriptor == null ? this : functionDescriptor;
        this.kind = kind;
    }

    public FunctionDescriptorImpl initialize(ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, List<ReceiverParameterDescriptor> list, List<? extends TypeParameterDescriptor> list2, List<ValueParameterDescriptor> list3, KotlinType kotlinType, Modality modality, DescriptorVisibility descriptorVisibility) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (list2 == null) {
            $$$reportNull$$$0(6);
        }
        if (list3 == null) {
            $$$reportNull$$$0(7);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(8);
        }
        this.typeParameters = CollectionsKt.toList(list2);
        this.unsubstitutedValueParameters = CollectionsKt.toList(list3);
        this.unsubstitutedReturnType = kotlinType;
        this.modality = modality;
        this.visibility = descriptorVisibility;
        this.extensionReceiverParameter = receiverParameterDescriptor;
        this.dispatchReceiverParameter = receiverParameterDescriptor2;
        this.contextReceiverParameters = list;
        for (int i = 0; i < list2.size(); i++) {
            TypeParameterDescriptor typeParameterDescriptor = list2.get(i);
            if (typeParameterDescriptor.getIndex() != i) {
                throw new IllegalStateException(typeParameterDescriptor + " index is " + typeParameterDescriptor.getIndex() + " but position is " + i);
            }
        }
        for (int i2 = 0; i2 < list3.size(); i2++) {
            ValueParameterDescriptor valueParameterDescriptor = list3.get(i2);
            if (valueParameterDescriptor.getIndex() != i2) {
                throw new IllegalStateException(valueParameterDescriptor + "index is " + valueParameterDescriptor.getIndex() + " but position is " + i2);
            }
        }
        return this;
    }

    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(10);
        }
        this.visibility = descriptorVisibility;
    }

    public void setOperator(boolean z) {
        this.isOperator = z;
    }

    public void setInfix(boolean z) {
        this.isInfix = z;
    }

    public void setExternal(boolean z) {
        this.isExternal = z;
    }

    public void setInline(boolean z) {
        this.isInline = z;
    }

    public void setTailrec(boolean z) {
        this.isTailrec = z;
    }

    public void setExpect(boolean z) {
        this.isExpect = z;
    }

    public void setActual(boolean z) {
        this.isActual = z;
    }

    private void setHiddenToOvercomeSignatureClash(boolean z) {
        this.isHiddenToOvercomeSignatureClash = z;
    }

    private void setHiddenForResolutionEverywhereBesideSupercalls(boolean z) {
        this.isHiddenForResolutionEverywhereBesideSupercalls = z;
    }

    public void setSuspend(boolean z) {
        this.isSuspend = z;
    }

    public void setReturnType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(11);
        }
        this.unsubstitutedReturnType = kotlinType;
    }

    public void setHasStableParameterNames(boolean z) {
        this.hasStableParameterNames = z;
    }

    public void setHasSynthesizedParameterNames(boolean z) {
        this.hasSynthesizedParameterNames = z;
    }

    public void setExtensionReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor) {
        if (receiverParameterDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        this.extensionReceiverParameter = receiverParameterDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        List<ReceiverParameterDescriptor> list = this.contextReceiverParameters;
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return this.dispatchReceiverParameter;
    }

    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        performOverriddenLazyCalculationIfNeeded();
        Collection<? extends FunctionDescriptor> collectionEmptyList = this.overriddenFunctions;
        if (collectionEmptyList == null) {
            collectionEmptyList = Collections.emptyList();
        }
        if (collectionEmptyList == null) {
            $$$reportNull$$$0(14);
        }
        return collectionEmptyList;
    }

    private void performOverriddenLazyCalculationIfNeeded() {
        Function0<Collection<FunctionDescriptor>> function0 = this.lazyOverriddenFunctionsTask;
        if (function0 != null) {
            this.overriddenFunctions = function0.invoke();
            this.lazyOverriddenFunctionsTask = null;
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        Modality modality = this.modality;
        if (modality == null) {
            $$$reportNull$$$0(15);
        }
        return modality;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility descriptorVisibility = this.visibility;
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(16);
        }
        return descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isOperator() {
        if (this.isOperator) {
            return true;
        }
        Iterator<? extends FunctionDescriptor> it = getOriginal().getOverriddenDescriptors().iterator();
        while (it.hasNext()) {
            if (it.next().isOperator()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInfix() {
        if (this.isInfix) {
            return true;
        }
        Iterator<? extends FunctionDescriptor> it = getOriginal().getOverriddenDescriptors().iterator();
        while (it.hasNext()) {
            if (it.next().isInfix()) {
                return true;
            }
        }
        return false;
    }

    public boolean isExternal() {
        return this.isExternal;
    }

    public boolean isInline() {
        return this.isInline;
    }

    public boolean isTailrec() {
        return this.isTailrec;
    }

    public boolean isSuspend() {
        return this.isSuspend;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return this.isActual;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> userDataKey) {
        Map<CallableDescriptor.UserDataKey<?>, Object> map = this.userDataMap;
        if (map == null) {
            return null;
        }
        return (V) map.get(userDataKey);
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenToOvercomeSignatureClash() {
        return this.isHiddenToOvercomeSignatureClash;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> collection) {
        if (collection == 0) {
            $$$reportNull$$$0(17);
        }
        this.overriddenFunctions = collection;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (((FunctionDescriptor) it.next()).isHiddenForResolutionEverywhereBesideSupercalls()) {
                this.isHiddenForResolutionEverywhereBesideSupercalls = true;
                return;
            }
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> list = this.typeParameters;
        if (list == null) {
            throw new IllegalStateException("typeParameters == null for " + this);
        }
        if (list == null) {
            $$$reportNull$$$0(18);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.ValidateableDescriptor
    public void validate() {
        getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> list = this.unsubstitutedValueParameters;
        if (list == null) {
            $$$reportNull$$$0(19);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        return this.hasStableParameterNames;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        return this.hasSynthesizedParameterNames;
    }

    public KotlinType getReturnType() {
        return this.unsubstitutedReturnType;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public FunctionDescriptor getOriginal() {
        FunctionDescriptor functionDescriptor = this.original;
        FunctionDescriptor original = functionDescriptor == this ? this : functionDescriptor.getOriginal();
        if (original == null) {
            $$$reportNull$$$0(20);
        }
        return original;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.Kind getKind() {
        CallableMemberDescriptor.Kind kind = this.kind;
        if (kind == null) {
            $$$reportNull$$$0(21);
        }
        return kind;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public FunctionDescriptor substitute(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(22);
        }
        return typeSubstitutor.isEmpty() ? this : newCopyBuilder(typeSubstitutor).setOriginal((CallableMemberDescriptor) getOriginal()).setPreserveSourceElement().setJustForTypeSubstitution(true).build();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenForResolutionEverywhereBesideSupercalls() {
        return this.isHiddenForResolutionEverywhereBesideSupercalls;
    }

    public class CopyConfiguration implements FunctionDescriptor.CopyBuilder<FunctionDescriptor> {
        private Annotations additionalAnnotations;
        protected boolean copyOverrides;
        protected ReceiverParameterDescriptor dispatchReceiverParameter;
        protected boolean dropOriginalInContainingParts;
        private boolean isHiddenForResolutionEverywhereBesideSupercalls;
        private boolean isHiddenToOvercomeSignatureClash;
        protected boolean justForTypeSubstitution;
        protected CallableMemberDescriptor.Kind kind;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        protected Name f740name;
        protected List<ReceiverParameterDescriptor> newContextReceiverParameters;
        protected ReceiverParameterDescriptor newExtensionReceiverParameter;
        private Boolean newHasSynthesizedParameterNames;
        protected Modality newModality;
        protected DeclarationDescriptor newOwner;
        protected KotlinType newReturnType;
        private List<TypeParameterDescriptor> newTypeParameters;
        protected List<ValueParameterDescriptor> newValueParameterDescriptors;
        protected DescriptorVisibility newVisibility;
        protected FunctionDescriptor original;
        protected boolean preserveSourceElement;
        protected boolean signatureChange;
        protected TypeSubstitution substitution;
        final /* synthetic */ FunctionDescriptorImpl this$0;
        private Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 9:
                case 11:
                case 13:
                case 15:
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 38:
                case 40:
                case 41:
                case 42:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 10:
                case 12:
                case 14:
                case 17:
                case 19:
                case 21:
                case 23:
                case 25:
                case 35:
                case 37:
                case 39:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 9:
                case 11:
                case 13:
                case 15:
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 38:
                case 40:
                case 41:
                case 42:
                    i2 = 2;
                    break;
                case 10:
                case 12:
                case 14:
                case 17:
                case 19:
                case 21:
                case 23:
                case 25:
                case 35:
                case 37:
                case 39:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "newOwner";
                    break;
                case 2:
                    objArr[0] = "newModality";
                    break;
                case 3:
                    objArr[0] = "newVisibility";
                    break;
                case 4:
                case 14:
                    objArr[0] = Namer.METADATA_CLASS_KIND;
                    break;
                case 5:
                    objArr[0] = "newValueParameterDescriptors";
                    break;
                case 6:
                    objArr[0] = "newContextReceiverParameters";
                    break;
                case 7:
                    objArr[0] = "newReturnType";
                    break;
                case 8:
                    objArr[0] = "owner";
                    break;
                case 9:
                case 11:
                case 13:
                case 15:
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 38:
                case 40:
                case 41:
                case 42:
                    objArr[0] = "org/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
                case 10:
                    objArr[0] = "modality";
                    break;
                case 12:
                    objArr[0] = "visibility";
                    break;
                case 17:
                    objArr[0] = "name";
                    break;
                case 19:
                case 21:
                    objArr[0] = "parameters";
                    break;
                case 23:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 25:
                    objArr[0] = "contextReceiverParameters";
                    break;
                case 35:
                    objArr[0] = "additionalAnnotations";
                    break;
                case 37:
                default:
                    objArr[0] = SchemaSymbols.ATTVAL_SUBSTITUTION;
                    break;
                case 39:
                    objArr[0] = "userDataKey";
                    break;
            }
            switch (i) {
                case 9:
                    objArr[1] = "setOwner";
                    break;
                case 10:
                case 12:
                case 14:
                case 17:
                case 19:
                case 21:
                case 23:
                case 25:
                case 35:
                case 37:
                case 39:
                default:
                    objArr[1] = "org/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
                case 11:
                    objArr[1] = "setModality";
                    break;
                case 13:
                    objArr[1] = "setVisibility";
                    break;
                case 15:
                    objArr[1] = "setKind";
                    break;
                case 16:
                    objArr[1] = "setCopyOverrides";
                    break;
                case 18:
                    objArr[1] = "setName";
                    break;
                case 20:
                    objArr[1] = "setValueParameters";
                    break;
                case 22:
                    objArr[1] = "setTypeParameters";
                    break;
                case 24:
                    objArr[1] = "setReturnType";
                    break;
                case 26:
                    objArr[1] = "setContextReceiverParameters";
                    break;
                case 27:
                    objArr[1] = "setExtensionReceiverParameter";
                    break;
                case 28:
                    objArr[1] = "setDispatchReceiverParameter";
                    break;
                case 29:
                    objArr[1] = "setOriginal";
                    break;
                case 30:
                    objArr[1] = "setSignatureChange";
                    break;
                case 31:
                    objArr[1] = "setPreserveSourceElement";
                    break;
                case 32:
                    objArr[1] = "setDropOriginalInContainingParts";
                    break;
                case 33:
                    objArr[1] = "setHiddenToOvercomeSignatureClash";
                    break;
                case 34:
                    objArr[1] = "setHiddenForResolutionEverywhereBesideSupercalls";
                    break;
                case 36:
                    objArr[1] = "setAdditionalAnnotations";
                    break;
                case 38:
                    objArr[1] = "setSubstitution";
                    break;
                case 40:
                    objArr[1] = "putUserData";
                    break;
                case 41:
                    objArr[1] = "getSubstitution";
                    break;
                case 42:
                    objArr[1] = "setJustForTypeSubstitution";
                    break;
            }
            switch (i) {
                case 8:
                    objArr[2] = "setOwner";
                    break;
                case 9:
                case 11:
                case 13:
                case 15:
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 38:
                case 40:
                case 41:
                case 42:
                    break;
                case 10:
                    objArr[2] = "setModality";
                    break;
                case 12:
                    objArr[2] = "setVisibility";
                    break;
                case 14:
                    objArr[2] = "setKind";
                    break;
                case 17:
                    objArr[2] = "setName";
                    break;
                case 19:
                    objArr[2] = "setValueParameters";
                    break;
                case 21:
                    objArr[2] = "setTypeParameters";
                    break;
                case 23:
                    objArr[2] = "setReturnType";
                    break;
                case 25:
                    objArr[2] = "setContextReceiverParameters";
                    break;
                case 35:
                    objArr[2] = "setAdditionalAnnotations";
                    break;
                case 37:
                    objArr[2] = "setSubstitution";
                    break;
                case 39:
                    objArr[2] = "putUserData";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 9:
                case 11:
                case 13:
                case 15:
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 38:
                case 40:
                case 41:
                case 42:
                    throw new IllegalStateException(str2);
                case 10:
                case 12:
                case 14:
                case 17:
                case 19:
                case 21:
                case 23:
                case 25:
                case 35:
                case 37:
                case 39:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public /* bridge */ /* synthetic */ CallableMemberDescriptor.CopyBuilder setTypeParameters(List list) {
            return setTypeParameters((List<TypeParameterDescriptor>) list);
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public /* bridge */ /* synthetic */ FunctionDescriptor.CopyBuilder setTypeParameters(List list) {
            return setTypeParameters((List<TypeParameterDescriptor>) list);
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public /* bridge */ /* synthetic */ FunctionDescriptor.CopyBuilder setValueParameters(List list) {
            return setValueParameters((List<ValueParameterDescriptor>) list);
        }

        public CopyConfiguration(FunctionDescriptorImpl functionDescriptorImpl, TypeSubstitution typeSubstitution, DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, List<ValueParameterDescriptor> list, List<ReceiverParameterDescriptor> list2, ReceiverParameterDescriptor receiverParameterDescriptor, KotlinType kotlinType, Name name2) {
            if (typeSubstitution == null) {
                $$$reportNull$$$0(0);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            if (modality == null) {
                $$$reportNull$$$0(2);
            }
            if (descriptorVisibility == null) {
                $$$reportNull$$$0(3);
            }
            if (kind == null) {
                $$$reportNull$$$0(4);
            }
            if (list == null) {
                $$$reportNull$$$0(5);
            }
            if (list2 == null) {
                $$$reportNull$$$0(6);
            }
            if (kotlinType == null) {
                $$$reportNull$$$0(7);
            }
            this.this$0 = functionDescriptorImpl;
            this.original = null;
            this.dispatchReceiverParameter = functionDescriptorImpl.dispatchReceiverParameter;
            this.copyOverrides = true;
            this.signatureChange = false;
            this.preserveSourceElement = false;
            this.dropOriginalInContainingParts = false;
            this.isHiddenToOvercomeSignatureClash = functionDescriptorImpl.isHiddenToOvercomeSignatureClash();
            this.newTypeParameters = null;
            this.additionalAnnotations = null;
            this.isHiddenForResolutionEverywhereBesideSupercalls = functionDescriptorImpl.isHiddenForResolutionEverywhereBesideSupercalls();
            this.userDataMap = new LinkedHashMap();
            this.newHasSynthesizedParameterNames = null;
            this.justForTypeSubstitution = false;
            this.substitution = typeSubstitution;
            this.newOwner = declarationDescriptor;
            this.newModality = modality;
            this.newVisibility = descriptorVisibility;
            this.kind = kind;
            this.newValueParameterDescriptors = list;
            this.newContextReceiverParameters = list2;
            this.newExtensionReceiverParameter = receiverParameterDescriptor;
            this.newReturnType = kotlinType;
            this.f740name = name2;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setOwner(DeclarationDescriptor declarationDescriptor) {
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(8);
            }
            this.newOwner = declarationDescriptor;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setModality(Modality modality) {
            if (modality == null) {
                $$$reportNull$$$0(10);
            }
            this.newModality = modality;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setVisibility(DescriptorVisibility descriptorVisibility) {
            if (descriptorVisibility == null) {
                $$$reportNull$$$0(12);
            }
            this.newVisibility = descriptorVisibility;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setKind(CallableMemberDescriptor.Kind kind) {
            if (kind == null) {
                $$$reportNull$$$0(14);
            }
            this.kind = kind;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setCopyOverrides(boolean z) {
            this.copyOverrides = z;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setName(Name name2) {
            if (name2 == null) {
                $$$reportNull$$$0(17);
            }
            this.f740name = name2;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setValueParameters(List<ValueParameterDescriptor> list) {
            if (list == null) {
                $$$reportNull$$$0(19);
            }
            this.newValueParameterDescriptors = list;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setTypeParameters(List<TypeParameterDescriptor> list) {
            if (list == null) {
                $$$reportNull$$$0(21);
            }
            this.newTypeParameters = list;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setReturnType(KotlinType kotlinType) {
            if (kotlinType == null) {
                $$$reportNull$$$0(23);
            }
            this.newReturnType = kotlinType;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public FunctionDescriptor.CopyBuilder<FunctionDescriptor> setContextReceiverParameters(List<ReceiverParameterDescriptor> list) {
            if (list == null) {
                $$$reportNull$$$0(25);
            }
            this.newContextReceiverParameters = list;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setExtensionReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor) {
            this.newExtensionReceiverParameter = receiverParameterDescriptor;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setDispatchReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor) {
            this.dispatchReceiverParameter = receiverParameterDescriptor;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setOriginal(CallableMemberDescriptor callableMemberDescriptor) {
            this.original = (FunctionDescriptor) callableMemberDescriptor;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setSignatureChange() {
            this.signatureChange = true;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setPreserveSourceElement() {
            this.preserveSourceElement = true;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setDropOriginalInContainingParts() {
            this.dropOriginalInContainingParts = true;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setHiddenToOvercomeSignatureClash() {
            this.isHiddenToOvercomeSignatureClash = true;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setHiddenForResolutionEverywhereBesideSupercalls() {
            this.isHiddenForResolutionEverywhereBesideSupercalls = true;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public CopyConfiguration setAdditionalAnnotations(Annotations annotations) {
            if (annotations == null) {
                $$$reportNull$$$0(35);
            }
            this.additionalAnnotations = annotations;
            return this;
        }

        public CopyConfiguration setHasSynthesizedParameterNames(boolean z) {
            this.newHasSynthesizedParameterNames = Boolean.valueOf(z);
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public CopyConfiguration setSubstitution(TypeSubstitution typeSubstitution) {
            if (typeSubstitution == null) {
                $$$reportNull$$$0(37);
            }
            this.substitution = typeSubstitution;
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
        public <V> FunctionDescriptor.CopyBuilder<FunctionDescriptor> putUserData(CallableDescriptor.UserDataKey<V> userDataKey, V v) {
            if (userDataKey == null) {
                $$$reportNull$$$0(39);
            }
            this.userDataMap.put(userDataKey, v);
            return this;
        }

        @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        public FunctionDescriptor build() {
            return this.this$0.doSubstitute(this);
        }

        public FunctionDescriptor getOriginal() {
            return this.original;
        }

        public TypeSubstitution getSubstitution() {
            TypeSubstitution typeSubstitution = this.substitution;
            if (typeSubstitution == null) {
                $$$reportNull$$$0(41);
            }
            return typeSubstitution;
        }

        public CopyConfiguration setJustForTypeSubstitution(boolean z) {
            this.justForTypeSubstitution = z;
            return this;
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> newCopyBuilder() {
        CopyConfiguration copyConfigurationNewCopyBuilder = newCopyBuilder(TypeSubstitutor.EMPTY);
        if (copyConfigurationNewCopyBuilder == null) {
            $$$reportNull$$$0(23);
        }
        return copyConfigurationNewCopyBuilder;
    }

    protected CopyConfiguration newCopyBuilder(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(24);
        }
        return new CopyConfiguration(this, typeSubstitutor.getSubstitution(), getPackageFragment(), getModality(), getVisibility(), getKind(), getValueParameters(), getContextReceiverParameters(), getExtensionReceiverParameter(), getReturnType(), null);
    }

    protected FunctionDescriptor doSubstitute(CopyConfiguration copyConfiguration) {
        Annotations annotations;
        ReceiverParameterDescriptorImpl receiverParameterDescriptorImpl;
        ReceiverParameterDescriptor receiverParameterDescriptor;
        KotlinType kotlinTypeSubstitute;
        if (copyConfiguration == null) {
            $$$reportNull$$$0(25);
        }
        boolean[] zArr = new boolean[1];
        if (copyConfiguration.additionalAnnotations != null) {
            annotations = AnnotationsKt.composeAnnotations(getAnnotations(), copyConfiguration.additionalAnnotations);
        } else {
            annotations = getAnnotations();
        }
        FunctionDescriptorImpl functionDescriptorImplCreateSubstitutedCopy = createSubstitutedCopy(copyConfiguration.newOwner, copyConfiguration.original, copyConfiguration.kind, copyConfiguration.f740name, annotations, getSourceToUseForCopy(copyConfiguration.preserveSourceElement, copyConfiguration.original));
        List<TypeParameterDescriptor> typeParameters = copyConfiguration.newTypeParameters == null ? getTypeParameters() : copyConfiguration.newTypeParameters;
        zArr[0] = zArr[0] | (!typeParameters.isEmpty());
        ArrayList arrayList = new ArrayList(typeParameters.size());
        final TypeSubstitutor typeSubstitutorSubstituteTypeParameters = DescriptorSubstitutor.substituteTypeParameters(typeParameters, copyConfiguration.substitution, functionDescriptorImplCreateSubstitutedCopy, arrayList, zArr);
        if (typeSubstitutorSubstituteTypeParameters == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        if (!copyConfiguration.newContextReceiverParameters.isEmpty()) {
            int i = 0;
            for (ReceiverParameterDescriptor receiverParameterDescriptor2 : copyConfiguration.newContextReceiverParameters) {
                KotlinType kotlinTypeSubstitute2 = typeSubstitutorSubstituteTypeParameters.substitute(receiverParameterDescriptor2.getType(), Variance.IN_VARIANCE);
                if (kotlinTypeSubstitute2 == null) {
                    return null;
                }
                int i2 = i + 1;
                arrayList2.add(DescriptorFactory.createContextReceiverParameterForCallable(functionDescriptorImplCreateSubstitutedCopy, kotlinTypeSubstitute2, ((ImplicitContextReceiver) receiverParameterDescriptor2.getValue()).getCustomLabelName(), receiverParameterDescriptor2.getAnnotations(), i));
                zArr[0] = zArr[0] | (kotlinTypeSubstitute2 != receiverParameterDescriptor2.getType());
                i = i2;
            }
        }
        if (copyConfiguration.newExtensionReceiverParameter != null) {
            KotlinType kotlinTypeSubstitute3 = typeSubstitutorSubstituteTypeParameters.substitute(copyConfiguration.newExtensionReceiverParameter.getType(), Variance.IN_VARIANCE);
            if (kotlinTypeSubstitute3 == null) {
                return null;
            }
            ReceiverParameterDescriptorImpl receiverParameterDescriptorImpl2 = new ReceiverParameterDescriptorImpl(functionDescriptorImplCreateSubstitutedCopy, new ExtensionReceiver(functionDescriptorImplCreateSubstitutedCopy, kotlinTypeSubstitute3, copyConfiguration.newExtensionReceiverParameter.getValue()), copyConfiguration.newExtensionReceiverParameter.getAnnotations());
            zArr[0] = (kotlinTypeSubstitute3 != copyConfiguration.newExtensionReceiverParameter.getType()) | zArr[0];
            receiverParameterDescriptorImpl = receiverParameterDescriptorImpl2;
        } else {
            receiverParameterDescriptorImpl = null;
        }
        if (copyConfiguration.dispatchReceiverParameter != null) {
            ReceiverParameterDescriptor receiverParameterDescriptorSubstitute = copyConfiguration.dispatchReceiverParameter.substitute(typeSubstitutorSubstituteTypeParameters);
            if (receiverParameterDescriptorSubstitute == null) {
                return null;
            }
            zArr[0] = zArr[0] | (receiverParameterDescriptorSubstitute != copyConfiguration.dispatchReceiverParameter);
            receiverParameterDescriptor = receiverParameterDescriptorSubstitute;
        } else {
            receiverParameterDescriptor = null;
        }
        List<ValueParameterDescriptor> substitutedValueParameters = getSubstitutedValueParameters(functionDescriptorImplCreateSubstitutedCopy, copyConfiguration.newValueParameterDescriptors, typeSubstitutorSubstituteTypeParameters, copyConfiguration.dropOriginalInContainingParts, copyConfiguration.preserveSourceElement, zArr);
        if (substitutedValueParameters == null || (kotlinTypeSubstitute = typeSubstitutorSubstituteTypeParameters.substitute(copyConfiguration.newReturnType, Variance.OUT_VARIANCE)) == null) {
            return null;
        }
        boolean z = zArr[0] | (kotlinTypeSubstitute != copyConfiguration.newReturnType);
        zArr[0] = z;
        if (!z && copyConfiguration.justForTypeSubstitution) {
            return this;
        }
        functionDescriptorImplCreateSubstitutedCopy.initialize(receiverParameterDescriptorImpl, receiverParameterDescriptor, arrayList2, arrayList, substitutedValueParameters, kotlinTypeSubstitute, copyConfiguration.newModality, copyConfiguration.newVisibility);
        functionDescriptorImplCreateSubstitutedCopy.setOperator(this.isOperator);
        functionDescriptorImplCreateSubstitutedCopy.setInfix(this.isInfix);
        functionDescriptorImplCreateSubstitutedCopy.setExternal(this.isExternal);
        functionDescriptorImplCreateSubstitutedCopy.setInline(this.isInline);
        functionDescriptorImplCreateSubstitutedCopy.setTailrec(this.isTailrec);
        functionDescriptorImplCreateSubstitutedCopy.setSuspend(this.isSuspend);
        functionDescriptorImplCreateSubstitutedCopy.setExpect(this.isExpect);
        functionDescriptorImplCreateSubstitutedCopy.setActual(this.isActual);
        functionDescriptorImplCreateSubstitutedCopy.setHasStableParameterNames(this.hasStableParameterNames);
        functionDescriptorImplCreateSubstitutedCopy.setHiddenToOvercomeSignatureClash(copyConfiguration.isHiddenToOvercomeSignatureClash);
        functionDescriptorImplCreateSubstitutedCopy.setHiddenForResolutionEverywhereBesideSupercalls(copyConfiguration.isHiddenForResolutionEverywhereBesideSupercalls);
        functionDescriptorImplCreateSubstitutedCopy.setHasSynthesizedParameterNames(copyConfiguration.newHasSynthesizedParameterNames != null ? copyConfiguration.newHasSynthesizedParameterNames.booleanValue() : this.hasSynthesizedParameterNames);
        if (!copyConfiguration.userDataMap.isEmpty() || this.userDataMap != null) {
            Map<CallableDescriptor.UserDataKey<?>, Object> map = copyConfiguration.userDataMap;
            Map<CallableDescriptor.UserDataKey<?>, Object> map2 = this.userDataMap;
            if (map2 != null) {
                for (Map.Entry<CallableDescriptor.UserDataKey<?>, Object> entry : map2.entrySet()) {
                    if (!map.containsKey(entry.getKey())) {
                        map.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (map.size() == 1) {
                functionDescriptorImplCreateSubstitutedCopy.userDataMap = Collections.singletonMap(map.keySet().iterator().next(), map.values().iterator().next());
            } else {
                functionDescriptorImplCreateSubstitutedCopy.userDataMap = map;
            }
        }
        if (copyConfiguration.signatureChange || getInitialSignatureDescriptor() != null) {
            functionDescriptorImplCreateSubstitutedCopy.setInitialSignatureDescriptor((getInitialSignatureDescriptor() != null ? getInitialSignatureDescriptor() : this).substitute(typeSubstitutorSubstituteTypeParameters));
        }
        if (copyConfiguration.copyOverrides && !getOriginal().getOverriddenDescriptors().isEmpty()) {
            if (copyConfiguration.substitution.isEmpty()) {
                Function0<Collection<FunctionDescriptor>> function0 = this.lazyOverriddenFunctionsTask;
                if (function0 != null) {
                    functionDescriptorImplCreateSubstitutedCopy.lazyOverriddenFunctionsTask = function0;
                } else {
                    functionDescriptorImplCreateSubstitutedCopy.mo7205setOverriddenDescriptors(getOverriddenDescriptors());
                }
            } else {
                functionDescriptorImplCreateSubstitutedCopy.lazyOverriddenFunctionsTask = new Function0<Collection<FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl.1
                    @Override // kotlin.jvm.functions.Function0
                    public Collection<FunctionDescriptor> invoke() {
                        SmartList smartList = new SmartList();
                        Iterator<? extends FunctionDescriptor> it = FunctionDescriptorImpl.this.getOverriddenDescriptors().iterator();
                        while (it.hasNext()) {
                            smartList.add(it.next().substitute(typeSubstitutorSubstituteTypeParameters));
                        }
                        return smartList;
                    }
                };
            }
        }
        return functionDescriptorImplCreateSubstitutedCopy;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        FunctionDescriptor functionDescriptorBuild = newCopyBuilder().setOwner(declarationDescriptor).setModality(modality).setVisibility(descriptorVisibility).setKind(kind).setCopyOverrides(z).build();
        if (functionDescriptorBuild == null) {
            $$$reportNull$$$0(26);
        }
        return functionDescriptorBuild;
    }

    private SourceElement getSourceToUseForCopy(boolean z, FunctionDescriptor functionDescriptor) {
        SourceElement source;
        if (z) {
            if (functionDescriptor == null) {
                functionDescriptor = getOriginal();
            }
            source = functionDescriptor.getSourceElement();
        } else {
            source = SourceElement.NO_SOURCE;
        }
        if (source == null) {
            $$$reportNull$$$0(27);
        }
        return source;
    }

    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitFunctionDescriptor(this, d);
    }

    public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor functionDescriptor, List<ValueParameterDescriptor> list, TypeSubstitutor typeSubstitutor) {
        if (list == null) {
            $$$reportNull$$$0(28);
        }
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(29);
        }
        return getSubstitutedValueParameters(functionDescriptor, list, typeSubstitutor, false, false, null);
    }

    public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor functionDescriptor, List<ValueParameterDescriptor> list, TypeSubstitutor typeSubstitutor, boolean z, boolean z2, boolean[] zArr) {
        Function0<List<VariableDescriptor>> function0;
        if (list == null) {
            $$$reportNull$$$0(30);
        }
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(31);
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            KotlinType kotlinTypeSubstitute = typeSubstitutor.substitute(valueParameterDescriptor.getType(), Variance.IN_VARIANCE);
            KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
            KotlinType kotlinTypeSubstitute2 = varargElementType == null ? null : typeSubstitutor.substitute(varargElementType, Variance.IN_VARIANCE);
            if (kotlinTypeSubstitute == null) {
                return null;
            }
            if ((kotlinTypeSubstitute != valueParameterDescriptor.getType() || varargElementType != kotlinTypeSubstitute2) && zArr != null) {
                zArr[0] = true;
            }
            if (valueParameterDescriptor instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
                final List<VariableDescriptor> destructuringVariables = ((ValueParameterDescriptorImpl.WithDestructuringDeclaration) valueParameterDescriptor).getDestructuringVariables();
                function0 = new Function0<List<VariableDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl.2
                    @Override // kotlin.jvm.functions.Function0
                    public List<VariableDescriptor> invoke() {
                        return destructuringVariables;
                    }
                };
            } else {
                function0 = null;
            }
            arrayList.add(ValueParameterDescriptorImpl.createWithDestructuringDeclarations(functionDescriptor, z ? null : valueParameterDescriptor, valueParameterDescriptor.getIndex(), valueParameterDescriptor.getAnnotations(), valueParameterDescriptor.getShortName(), kotlinTypeSubstitute, valueParameterDescriptor.declaresDefaultValue(), valueParameterDescriptor.getIsCrossinline(), valueParameterDescriptor.getIsNoinline(), kotlinTypeSubstitute2, z2 ? valueParameterDescriptor.getSourceElement() : SourceElement.NO_SOURCE, function0));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return this.initialSignatureDescriptor;
    }

    private void setInitialSignatureDescriptor(FunctionDescriptor functionDescriptor) {
        this.initialSignatureDescriptor = functionDescriptor;
    }

    public <V> void putInUserDataMap(CallableDescriptor.UserDataKey<V> userDataKey, Object obj) {
        if (this.userDataMap == null) {
            this.userDataMap = new LinkedHashMap();
        }
        this.userDataMap.put(userDataKey, obj);
    }
}
