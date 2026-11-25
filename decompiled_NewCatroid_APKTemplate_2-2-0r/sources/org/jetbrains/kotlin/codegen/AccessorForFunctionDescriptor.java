package org.jetbrains.kotlin.codegen;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AccessorForFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B/\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ<\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForFunctionDescriptor;", "Lorg/jetbrains/kotlin/codegen/AbstractAccessorForFunctionDescriptor;", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "calleeDescriptor", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "nameSuffix", "", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "createSubstitutedCopy", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl;", "newOwner", "original", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "newName", "Lorg/jetbrains/kotlin/name/Name;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessorForFunctionDescriptor extends AbstractAccessorForFunctionDescriptor implements AccessorForCallableDescriptor<FunctionDescriptor> {
    private final AccessorKind accessorKind;
    private final FunctionDescriptor calleeDescriptor;
    private final String nameSuffix;
    private final ClassDescriptor superCallTarget;

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public FunctionDescriptor getCalleeDescriptor() {
        return this.calleeDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public ClassDescriptor getSuperCallTarget() {
        return this.superCallTarget;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public AccessorKind getAccessorKind() {
        return this.accessorKind;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AccessorForFunctionDescriptor(FunctionDescriptor calleeDescriptor, DeclarationDescriptor containingDeclaration, ClassDescriptor classDescriptor, String nameSuffix, AccessorKind accessorKind) {
        Intrinsics.checkNotNullParameter(calleeDescriptor, "calleeDescriptor");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(nameSuffix, "nameSuffix");
        Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
        Name nameIdentifier = Name.identifier("access$" + nameSuffix);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        super(containingDeclaration, nameIdentifier);
        this.calleeDescriptor = calleeDescriptor;
        this.superCallTarget = classDescriptor;
        this.nameSuffix = nameSuffix;
        this.accessorKind = accessorKind;
        ReceiverParameterDescriptor extensionReceiverParameter = getCalleeDescriptor().getExtensionReceiverParameter();
        ReceiverParameterDescriptor dispatchReceiverParameter = null;
        ReceiverParameterDescriptor receiverParameterDescriptorMo7203copy = extensionReceiverParameter != null ? extensionReceiverParameter.mo7203copy(this) : null;
        if (!(getCalleeDescriptor() instanceof ConstructorDescriptor) && !CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(getCalleeDescriptor())) {
            dispatchReceiverParameter = getCalleeDescriptor().mo6753getDispatchReceiverParameter();
        }
        ReceiverParameterDescriptor receiverParameterDescriptor = dispatchReceiverParameter;
        List<ReceiverParameterDescriptor> contextReceiverParameters = getCalleeDescriptor().getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverParameterDescriptor) it.next()).mo7203copy(this));
        }
        initialize(receiverParameterDescriptorMo7203copy, receiverParameterDescriptor, (List) arrayList, (List) copyTypeParameters(getCalleeDescriptor()), (List) copyValueParameters(getCalleeDescriptor()), getCalleeDescriptor().getReturnType(), Modality.FINAL, DescriptorVisibilities.LOCAL);
        setSuspend(getCalleeDescriptor().isSuspend());
        if (getCalleeDescriptor().getUserData(CoroutineCodegenUtilKt.INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION) != null) {
            this.userDataMap = new LinkedHashMap();
            Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap = this.userDataMap;
            Intrinsics.checkNotNullExpressionValue(userDataMap, "userDataMap");
            userDataMap.put(CoroutineCodegenUtilKt.INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION, getCalleeDescriptor().getUserData(CoroutineCodegenUtilKt.INITIAL_DESCRIPTOR_FOR_SUSPEND_FUNCTION));
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, Annotations annotations, SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        return new AccessorForFunctionDescriptor(getCalleeDescriptor(), newOwner, getSuperCallTarget(), this.nameSuffix, getAccessorKind());
    }
}
