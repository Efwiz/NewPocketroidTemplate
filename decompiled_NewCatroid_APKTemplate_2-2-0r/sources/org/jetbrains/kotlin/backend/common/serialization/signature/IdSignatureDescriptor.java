package org.jetbrains.kotlin.backend.common.serialization.signature;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrImplementingDelegateDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptor;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;

/* JADX INFO: compiled from: IdSignatureDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u000fH\u0016J\u0014\u0010\u0010\u001a\u00060\u0011R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0019H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;)V", "getMangler", "()Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "composeAnonInitSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "composeEnumEntrySignature", "composeFieldSignature", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "composeSignature", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createSignatureBuilder", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor$DescriptorBasedSignatureBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "withFileSignature", "", "fileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "body", "Lkotlin/Function0;", "DescriptorBasedSignatureBuilder", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IdSignatureDescriptor implements IdSignatureComposer {
    private final KotlinMangler.DescriptorMangler mangler;

    public IdSignatureDescriptor(KotlinMangler.DescriptorMangler mangler) {
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        this.mangler = mangler;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public KotlinMangler.DescriptorMangler getMangler() {
        return this.mangler;
    }

    protected DescriptorBasedSignatureBuilder createSignatureBuilder(SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new DescriptorBasedSignatureBuilder(this, type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: compiled from: IdSignatureDescriptor.kt */
    @Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0094\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0002H\u0014J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0002H\u0014J\u0010\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0002H\u0002J\u001a\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010%\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020&2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010'\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020(2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010)\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020*2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010+\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020,2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010-\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020.2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010/\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u0002002\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u00101\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u0002022\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u00103\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u0002042\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u0002072\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u00108\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u0002092\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010:\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020;2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010<\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020=2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010>\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020?2\b\u0010!\u001a\u0004\u0018\u00010\u0006H\u0016R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00038TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\u00020\u0012*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0012*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0014¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor$DescriptorBasedSignatureBuilder;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureBuilder;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "(Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)V", "currentFileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "getCurrentFileSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "mangler", "getMangler", "()Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "isPrivate", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;)Z", "isTopLevelPrivate", "accept", "d", "collectParents", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "renderDeclarationForDescription", "", "declaration", "reportUnexpectedDescriptor", "visitClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "data", "visitConstructorDescriptor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "visitFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "visitPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "visitPackageViewDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "visitPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "visitPropertyGetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "visitPropertySetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "visitReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "visitScriptDescriptor", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "visitTypeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "visitTypeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "visitValueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "visitVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public class DescriptorBasedSignatureBuilder extends IdSignatureBuilder<DeclarationDescriptor, KotlinMangler.DescriptorMangler> implements DeclarationDescriptorVisitor {
        final /* synthetic */ IdSignatureDescriptor this$0;
        private final SpecialDeclarationType type;

        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        protected IdSignature.FileSignature getCurrentFileSignature() {
            return null;
        }

        public DescriptorBasedSignatureBuilder(IdSignatureDescriptor idSignatureDescriptor, SpecialDeclarationType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.this$0 = idSignatureDescriptor;
            this.type = type;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitClassDescriptor(ClassDescriptor classDescriptor, Object obj) {
            visitClassDescriptor(classDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, Object obj) {
            visitConstructorDescriptor(constructorDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitFunctionDescriptor(FunctionDescriptor functionDescriptor, Object obj) {
            visitFunctionDescriptor(functionDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitModuleDeclaration(ModuleDescriptor moduleDescriptor, Object obj) {
            visitModuleDeclaration(moduleDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, Object obj) {
            visitPackageFragmentDescriptor(packageFragmentDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, Object obj) {
            visitPackageViewDescriptor(packageViewDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, Object obj) {
            visitPropertyDescriptor(propertyDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, Object obj) {
            visitPropertyGetterDescriptor(propertyGetterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, Object obj) {
            visitPropertySetterDescriptor(propertySetterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, Object obj) {
            visitReceiverParameterDescriptor(receiverParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitScriptDescriptor(ScriptDescriptor scriptDescriptor, Object obj) {
            visitScriptDescriptor(scriptDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, Object obj) {
            visitTypeAliasDescriptor(typeAliasDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, Object obj) {
            visitTypeParameterDescriptor(typeParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, Object obj) {
            visitValueParameterDescriptor(valueParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitVariableDescriptor(VariableDescriptor variableDescriptor, Object obj) {
            visitVariableDescriptor(variableDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        public KotlinMangler.DescriptorMangler getMangler() {
            return this.this$0.getMangler();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        public void accept(DeclarationDescriptor d) {
            Intrinsics.checkNotNullParameter(d, "d");
            d.accept(this, null);
        }

        private final void reportUnexpectedDescriptor(DeclarationDescriptor descriptor) {
            throw new IllegalStateException(("Unexpected descriptor " + descriptor).toString());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureBuilder
        public String renderDeclarationForDescription(DeclarationDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return DescriptorRenderer.SHORT_NAMES_IN_TYPES.render(declaration);
        }

        private final void collectParents(DeclarationDescriptorNonRoot descriptor) {
            descriptor.getPackageFragment().accept(this, null);
            List<String> classFqnSegments = getClassFqnSegments();
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            classFqnSegments.add(strAsString);
        }

        private final boolean isPrivate(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
            return Intrinsics.areEqual(declarationDescriptorWithVisibility.getVisibility(), DescriptorVisibilities.PRIVATE);
        }

        private final boolean isTopLevelPrivate(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
            return isPrivate(declarationDescriptorWithVisibility) && !getMangler().isPlatformSpecificExport(declarationDescriptorWithVisibility) && (declarationDescriptorWithVisibility.getPackageFragment() instanceof PackageFragmentDescriptor);
        }

        public void visitPackageFragmentDescriptor(PackageFragmentDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            setPackageFqn(descriptor.getFqName());
            platformSpecificPackage(descriptor);
        }

        public void visitPackageViewDescriptor(PackageViewDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            setPackageFqn(descriptor.getFqName());
        }

        public void visitVariableDescriptor(VariableDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            reportUnexpectedDescriptor(descriptor);
        }

        public void visitFunctionDescriptor(FunctionDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            collectParents(descriptor);
            setHashIdAndDescriptionFor(descriptor, false);
            setTopLevelPrivate(getIsTopLevelPrivate() | isTopLevelPrivate(descriptor));
            setDescriptionIfLocalDeclaration(descriptor);
            setExpected(descriptor.get$isExpect());
            platformSpecificFunction(descriptor);
        }

        public void visitTypeParameterDescriptor(TypeParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            descriptor.getPackageFragment().accept(this, null);
            createContainer();
            getClassFqnSegments().add(MangleConstant.TYPE_PARAMETER_MARKER_NAME);
            setHashIdAndDescription(descriptor.getIndex(), DescriptorRenderer.SHORT_NAMES_IN_TYPES.render(descriptor), false);
        }

        public void visitClassDescriptor(ClassDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            collectParents(descriptor);
            setTopLevelPrivate(getIsTopLevelPrivate() | isTopLevelPrivate(descriptor));
            if (descriptor.getKind() == ClassKind.ENUM_ENTRY && this.type != SpecialDeclarationType.ENUM_ENTRY) {
                getClassFqnSegments().add(MangleConstant.ENUM_ENTRY_CLASS_NAME);
            }
            setDescriptionIfLocalDeclaration(descriptor);
            setExpected(descriptor.get$isExpect());
            platformSpecificClass(descriptor);
        }

        public void visitTypeAliasDescriptor(TypeAliasDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            collectParents(descriptor);
            setTopLevelPrivate(getIsTopLevelPrivate() | isTopLevelPrivate(descriptor));
            setExpected(descriptor.get$isExpect());
            platformSpecificAlias(descriptor);
        }

        public void visitModuleDeclaration(ModuleDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            platformSpecificModule(descriptor);
        }

        public void visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, Void data) {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            collectParents(constructorDescriptor);
            setHashIdAndDescriptionFor(constructorDescriptor, false);
            platformSpecificConstructor(constructorDescriptor);
        }

        public void visitScriptDescriptor(ScriptDescriptor scriptDescriptor, Void data) {
            Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
            visitClassDescriptor((ClassDescriptor) scriptDescriptor, data);
        }

        public void visitPropertyDescriptor(PropertyDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            PropertyDescriptor correspondingProperty = descriptor instanceof IrPropertyDelegateDescriptor ? ((IrPropertyDelegateDescriptor) descriptor).getCorrespondingProperty() : descriptor;
            collectParents(correspondingProperty);
            setTopLevelPrivate(getIsTopLevelPrivate() | isTopLevelPrivate(correspondingProperty));
            setHashIdAndDescriptionFor(correspondingProperty, false);
            setExpected(correspondingProperty.get$isExpect());
            platformSpecificProperty(correspondingProperty);
            if (this.type != SpecialDeclarationType.BACKING_FIELD || (descriptor instanceof IrImplementingDelegateDescriptor)) {
                return;
            }
            createContainer();
            getClassFqnSegments().add(MangleConstant.BACKING_FIELD_NAME);
        }

        public void visitValueParameterDescriptor(ValueParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            reportUnexpectedDescriptor(descriptor);
        }

        public void visitPropertyGetterDescriptor(PropertyGetterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            descriptor.getCorrespondingProperty().accept(this, null);
            setHashIdAndDescriptionFor(descriptor, true);
            List<String> classFqnSegments = getClassFqnSegments();
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            classFqnSegments.add(strAsString);
            setExpected(descriptor.get$isExpect());
            platformSpecificGetter(descriptor);
        }

        public void visitPropertySetterDescriptor(PropertySetterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            descriptor.getCorrespondingProperty().accept(this, null);
            setHashIdAndDescriptionFor(descriptor, true);
            List<String> classFqnSegments = getClassFqnSegments();
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            classFqnSegments.add(strAsString);
            setExpected(descriptor.get$isExpect());
            platformSpecificSetter(descriptor);
        }

        public void visitReceiverParameterDescriptor(ReceiverParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            reportUnexpectedDescriptor(descriptor);
        }
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeSignature(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (getMangler().isExported(descriptor, false)) {
            return createSignatureBuilder(SpecialDeclarationType.REGULAR).buildSignature(descriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeEnumEntrySignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (getMangler().isExported(descriptor, false)) {
            return createSignatureBuilder(SpecialDeclarationType.ENUM_ENTRY).buildSignature(descriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeFieldSignature(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (getMangler().isExported(descriptor, false)) {
            return createSignatureBuilder(SpecialDeclarationType.BACKING_FIELD).buildSignature(descriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeAnonInitSignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (getMangler().isExported(descriptor, false)) {
            return createSignatureBuilder(SpecialDeclarationType.ANON_INIT).buildSignature(descriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public void withFileSignature(IdSignature.FileSignature fileSignature, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(fileSignature, "fileSignature");
        Intrinsics.checkNotNullParameter(body, "body");
        body.invoke();
    }
}
