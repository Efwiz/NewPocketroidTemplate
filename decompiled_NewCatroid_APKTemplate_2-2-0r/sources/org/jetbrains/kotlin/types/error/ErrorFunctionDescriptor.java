package org.jetbrains.kotlin.types.error;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitution;

/* JADX INFO: compiled from: ErrorFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J0\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J<\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J#\u0010\u001b\u001a\u0004\u0018\u0001H\u001c\"\u0004\b\u0000\u0010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u0010H\u0016J\u0010\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\"H\u0016J\u0018\u0010#\u001a\u00020$2\u000e\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010'0&H\u0016¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "createSubstitutedCopy", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl;", "original", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "newName", "Lorg/jetbrains/kotlin/name/Name;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "isSuspend", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor$CopyBuilder;", "setOverriddenDescriptors", "", "overriddenDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorFunctionDescriptor extends SimpleFunctionDescriptorImpl {
    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isSuspend() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        Intrinsics.checkNotNullParameter(overriddenDescriptors, "overriddenDescriptors");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ErrorFunctionDescriptor(ClassDescriptor containingDeclaration) {
        super(containingDeclaration, null, Annotations.INSTANCE.getEMPTY(), Name.special(ErrorEntity.ERROR_FUNCTION.getDebugText()), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), (KotlinType) ErrorUtils.createErrorType(ErrorTypeKind.RETURN_TYPE_FOR_FUNCTION, new String[0]), Modality.OPEN, DescriptorVisibilities.PUBLIC);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, Annotations annotations, SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public SimpleFunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> newCopyBuilder() {
        return new FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor>() { // from class: org.jetbrains.kotlin.types.error.ErrorFunctionDescriptor.newCopyBuilder.1
            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public /* bridge */ /* synthetic */ CallableMemberDescriptor.CopyBuilder setTypeParameters(List list) {
                return setTypeParameters((List<? extends TypeParameterDescriptor>) list);
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOwner(DeclarationDescriptor owner) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setModality(Modality modality) {
                Intrinsics.checkNotNullParameter(modality, "modality");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setVisibility(DescriptorVisibility visibility) {
                Intrinsics.checkNotNullParameter(visibility, "visibility");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setKind(CallableMemberDescriptor.Kind kind) {
                Intrinsics.checkNotNullParameter(kind, "kind");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setCopyOverrides(boolean copyOverrides) {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setName(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSubstitution(TypeSubstitution substitution) {
                Intrinsics.checkNotNullParameter(substitution, "substitution");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setValueParameters(List<? extends ValueParameterDescriptor> parameters) {
                Intrinsics.checkNotNullParameter(parameters, "parameters");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public <V> FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> putUserData(CallableDescriptor.UserDataKey<V> userDataKey, V value) {
                Intrinsics.checkNotNullParameter(userDataKey, "userDataKey");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setTypeParameters(List<? extends TypeParameterDescriptor> parameters) {
                Intrinsics.checkNotNullParameter(parameters, "parameters");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setReturnType(KotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setContextReceiverParameters(List<? extends ReceiverParameterDescriptor> contextReceiverParameters) {
                Intrinsics.checkNotNullParameter(contextReceiverParameters, "contextReceiverParameters");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setExtensionReceiverParameter(ReceiverParameterDescriptor extensionReceiverParameter) {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDispatchReceiverParameter(ReceiverParameterDescriptor dispatchReceiverParameter) {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOriginal(CallableMemberDescriptor original) {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSignatureChange() {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setPreserveSourceElement() {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDropOriginalInContainingParts() {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenToOvercomeSignatureClash() {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenForResolutionEverywhereBesideSupercalls() {
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setAdditionalAnnotations(Annotations additionalAnnotations) {
                Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
                return this;
            }

            @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor.CopyBuilder, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
            public SimpleFunctionDescriptor build() {
                return ErrorFunctionDescriptor.this;
            }
        };
    }
}
