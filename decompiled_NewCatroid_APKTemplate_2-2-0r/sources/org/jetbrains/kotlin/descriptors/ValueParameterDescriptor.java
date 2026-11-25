package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: ValueParameterDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J \u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0005H&J\b\u0010\u0016\u001a\u00020\tH&J\b\u0010\u0017\u001a\u00020\u0012H&J\b\u0010\u0018\u001a\u00020\u0000H&J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00000\u001aH&J\b\u0010\u001b\u001a\u00020\tH\u0016J\u0010\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001eH&R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0012\u0010\u000b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "index", "", "getIndex", "()I", "isCrossinline", "", "()Z", "isNoinline", "varargElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getVarargElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "newName", "Lorg/jetbrains/kotlin/name/Name;", "newIndex", "declaresDefaultValue", "getContainingDeclaration", "getOriginal", "getOverriddenDescriptors", "", "isLateInit", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ValueParameterDescriptor extends ParameterDescriptor, VariableDescriptor, ValueParameterSymbolMarker {

    /* JADX INFO: compiled from: ValueParameterDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isLateInit(ValueParameterDescriptor valueParameterDescriptor) {
            return false;
        }
    }

    /* JADX INFO: renamed from: copy */
    ValueParameterDescriptor mo7204copy(CallableDescriptor newOwner, Name newName, int newIndex);

    boolean declaresDefaultValue();

    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    CallableDescriptor getContainingDeclaration();

    int getIndex();

    @Override // org.jetbrains.kotlin.descriptors.ParameterDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ValueParameterDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<ValueParameterDescriptor> getOverriddenDescriptors();

    KotlinType getVarargElementType();

    boolean isCrossinline();

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    boolean isLateInit();

    boolean isNoinline();

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    ValueParameterDescriptor substitute(TypeSubstitutor substitutor);
}
