package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;

/* JADX INFO: compiled from: FrameMapWithExpectActualSupport.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/FrameMapWithExpectActualSupport;", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "findActualParameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "parameter", "getIndex", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FrameMapWithExpectActualSupport extends FrameMap {
    private final ModuleDescriptor module;

    public FrameMapWithExpectActualSupport(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.module = module;
    }

    @Override // org.jetbrains.kotlin.codegen.FrameMapBase
    public int getIndex(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ParameterDescriptor) {
            ParameterDescriptor parameterDescriptor = (ParameterDescriptor) descriptor;
            ParameterDescriptor parameterDescriptorFindActualParameter = findActualParameter(parameterDescriptor);
            if (parameterDescriptorFindActualParameter != null) {
                parameterDescriptor = parameterDescriptorFindActualParameter;
            }
            descriptor = parameterDescriptor;
        }
        return super.getIndex(descriptor);
    }

    private final ParameterDescriptor findActualParameter(ParameterDescriptor parameter) {
        DeclarationDescriptor containingDeclaration = parameter.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!(containingDeclaration instanceof CallableMemberDescriptor) || !((CallableMemberDescriptor) containingDeclaration).get$isExpect() || !(parameter instanceof ReceiverParameterDescriptor) || !(((ReceiverParameterDescriptor) parameter).getValue() instanceof ExtensionReceiver)) {
            return null;
        }
        MemberDescriptor memberDescriptor = (MemberDescriptor) CollectionsKt.firstOrNull(ExpectedActualResolverKt.findCompatibleActualsForExpected$default((MemberDescriptor) containingDeclaration, this.module, null, 2, null));
        CallableDescriptor callableDescriptor = memberDescriptor instanceof CallableDescriptor ? (CallableDescriptor) memberDescriptor : null;
        return callableDescriptor != null ? callableDescriptor.getExtensionReceiverParameter() : null;
    }
}
