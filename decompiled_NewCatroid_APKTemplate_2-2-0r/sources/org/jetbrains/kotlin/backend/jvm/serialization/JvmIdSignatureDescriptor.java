package org.jetbrains.kotlin.backend.jvm.serialization;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.load.java.descriptors.JavaForKotlinOverridePropertyDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JvmIdSignatureDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u00060\bR\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0014J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000fH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmIdSignatureDescriptor;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;)V", "externallyGivenFileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "createSignatureBuilder", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor$DescriptorBasedSignatureBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "withFileSignature", "", "fileSignature", "body", "Lkotlin/Function0;", "JvmDescriptorBasedSignatureBuilder", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIdSignatureDescriptor extends IdSignatureDescriptor {
    private IdSignature.FileSignature externallyGivenFileSignature;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmIdSignatureDescriptor(KotlinMangler.DescriptorMangler mangler) {
        super(mangler);
        Intrinsics.checkNotNullParameter(mangler, "mangler");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmIdSignatureDescriptor.kt */
    @Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0002J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u001eH\u0014J\u0010\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020 H\u0014J\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\"H\u0014J\u0010\u0010#\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020$H\u0014J\f\u0010\u0012\u001a\u00020\u0013*\u00020%H\u0002R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\r¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmIdSignatureDescriptor$JvmDescriptorBasedSignatureBuilder;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor$DescriptorBasedSignatureBuilder;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "(Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmIdSignatureDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)V", "currentFileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "getCurrentFileSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "storedFileSignature", "getStoredFileSignature", "setStoredFileSignature", "(Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;)V", "computeStoredFileSignature", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "isCapturingTypeParameter", "", "member", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "keepTrackOfOverridesForPossiblyClashingFakeOverride", "platformSpecificAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "platformSpecificClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "platformSpecificFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "platformSpecificGetter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "platformSpecificModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "platformSpecificProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "platformSpecificSetter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class JvmDescriptorBasedSignatureBuilder extends IdSignatureDescriptor.DescriptorBasedSignatureBuilder {
        private IdSignature.FileSignature storedFileSignature;
        final /* synthetic */ JvmIdSignatureDescriptor this$0;

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificModule(ModuleDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmDescriptorBasedSignatureBuilder(JvmIdSignatureDescriptor jvmIdSignatureDescriptor, SpecialDeclarationType type) {
            super(jvmIdSignatureDescriptor, type);
            Intrinsics.checkNotNullParameter(type, "type");
            this.this$0 = jvmIdSignatureDescriptor;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor.DescriptorBasedSignatureBuilder, org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected IdSignature.FileSignature getCurrentFileSignature() {
            IdSignature.FileSignature fileSignature = this.this$0.externallyGivenFileSignature;
            return fileSignature == null ? this.storedFileSignature : fileSignature;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificFunction(FunctionDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            keepTrackOfOverridesForPossiblyClashingFakeOverride(descriptor);
            computeStoredFileSignature(descriptor);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificClass(ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            computeStoredFileSignature(descriptor);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificProperty(PropertyDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            setSpecialJavaProperty(descriptor instanceof JavaForKotlinOverridePropertyDescriptor);
            setSyntheticJavaProperty(descriptor instanceof SyntheticJavaPropertyDescriptor);
            keepTrackOfOverridesForPossiblyClashingFakeOverride(descriptor);
            computeStoredFileSignature(descriptor);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificGetter(PropertyGetterDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            keepTrackOfOverridesForPossiblyClashingFakeOverride(descriptor);
            computeStoredFileSignature(descriptor);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificSetter(PropertySetterDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            keepTrackOfOverridesForPossiblyClashingFakeOverride(descriptor);
            computeStoredFileSignature(descriptor);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected void platformSpecificAlias(TypeAliasDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            computeStoredFileSignature(descriptor);
        }

        private final void keepTrackOfOverridesForPossiblyClashingFakeOverride(CallableMemberDescriptor descriptor) {
            Collection contributedFunctions;
            if (descriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                return;
            }
            DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor == null) {
                return;
            }
            if (descriptor instanceof PropertyAccessorDescriptor) {
                MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
                Name name2 = ((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty().getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                contributedFunctions = unsubstitutedMemberScope.getContributedVariables(name2, NoLookupLocation.FROM_BACKEND);
            } else if (descriptor instanceof PropertyDescriptor) {
                MemberScope unsubstitutedMemberScope2 = classDescriptor.getUnsubstitutedMemberScope();
                Name name3 = ((PropertyDescriptor) descriptor).getShortName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                contributedFunctions = unsubstitutedMemberScope2.getContributedVariables(name3, NoLookupLocation.FROM_BACKEND);
            } else if (descriptor instanceof FunctionDescriptor) {
                MemberScope unsubstitutedMemberScope3 = classDescriptor.getUnsubstitutedMemberScope();
                Name name4 = ((FunctionDescriptor) descriptor).getShortName();
                Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                contributedFunctions = unsubstitutedMemberScope3.getContributedFunctions(name4, NoLookupLocation.FROM_BACKEND);
            } else {
                throw new AssertionError("Unexpected CallableMemberDescriptor: " + descriptor);
            }
            if (contributedFunctions.size() <= 1) {
                return;
            }
            List list = SequencesKt.toList(SequencesKt.filter(DescriptorUtilsKt.overriddenTreeAsSequence(descriptor, true), new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor$JvmDescriptorBasedSignatureBuilder$keepTrackOfOverridesForPossiblyClashingFakeOverride$capturingOverrides$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE && this.this$0.isCapturingTypeParameter(it));
                }
            }));
            if (list.isEmpty()) {
                return;
            }
            setOverridden(CollectionsKt.sortedWith(list, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor$JvmDescriptorBasedSignatureBuilder$keepTrackOfOverridesForPossiblyClashingFakeOverride$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    DeclarationDescriptor containingDeclaration2 = ((CallableMemberDescriptor) t).getContainingModule();
                    Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    String strAsString = DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor) containingDeclaration2).asString();
                    DeclarationDescriptor containingDeclaration3 = ((CallableMemberDescriptor) t2).getContainingModule();
                    Intrinsics.checkNotNull(containingDeclaration3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    return ComparisonsKt.compareValues(strAsString, DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor) containingDeclaration3).asString());
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isCapturingTypeParameter(CallableMemberDescriptor member) {
            ReceiverParameterDescriptor extensionReceiverParameter = member.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null && isCapturingTypeParameter(extensionReceiverParameter)) {
                return true;
            }
            List<ValueParameterDescriptor> valueParameters = member.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (ValueParameterDescriptor valueParameterDescriptor : list) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    if (isCapturingTypeParameter(valueParameterDescriptor)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private final boolean isCapturingTypeParameter(ParameterDescriptor parameterDescriptor) {
            KotlinType type = parameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor$JvmDescriptorBasedSignatureBuilder$isCapturingTypeParameter$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    ClassifierDescriptor this$0 = it.getConstructor().getThis$0();
                    return Boolean.valueOf((this$0 instanceof TypeParameterDescriptor) && (((TypeParameterDescriptor) this$0).getContainingModule() instanceof ClassDescriptor));
                }
            });
        }

        private final void computeStoredFileSignature(DeclarationDescriptorWithSource descriptor) {
            if (getIsTopLevelPrivate() && this.this$0.externallyGivenFileSignature == null) {
                SourceFile containingFile = descriptor.getSourceElement().getContainingFile();
                Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
                FqName fqNameContainingPackage = DescriptorUtilKt.containingPackage(descriptor);
                if (fqNameContainingPackage == null) {
                    fqNameContainingPackage = FqName.ROOT;
                }
                Intrinsics.checkNotNull(fqNameContainingPackage);
                String name_ = descriptor.getSourceElement().getContainingFile().getName_();
                if (name_ == null) {
                    name_ = "unknown";
                }
                this.storedFileSignature = new IdSignature.FileSignature(containingFile, fqNameContainingPackage, name_);
            }
        }

        public final IdSignature.FileSignature getStoredFileSignature() {
            return this.storedFileSignature;
        }

        public final void setStoredFileSignature(IdSignature.FileSignature fileSignature) {
            this.storedFileSignature = fileSignature;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor
    protected IdSignatureDescriptor.DescriptorBasedSignatureBuilder createSignatureBuilder(SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new JvmDescriptorBasedSignatureBuilder(this, type);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor, org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public void withFileSignature(IdSignature.FileSignature fileSignature, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(fileSignature, "fileSignature");
        Intrinsics.checkNotNullParameter(body, "body");
        this.externallyGivenFileSignature = fileSignature;
        body.invoke();
        this.externallyGivenFileSignature = null;
    }
}
