package org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrExportCheckerVisitorKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
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
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DescriptorExportCheckerVisitor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u001d\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020&2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010'J\u001d\u0010(\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020)2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00020\u00022\u0006\u0010,\u001a\u00020-2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010.J\u001d\u0010/\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u0002002\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00101J\u001d\u00102\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u0002032\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00104J\u001d\u00105\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u0002062\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u00107J\u001d\u00108\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u0002092\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010:J\u001f\u0010;\u001a\u00020\u0002\"\f\b\u0000\u0010<*\u00020=*\u00020>*\u0002H<H\u0002¢\u0006\u0002\u0010?¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorExportCheckerVisitor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "()V", "check", "declaration", ModuleXmlParser.TYPE, "visitClassDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "data", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitConstructorDescriptor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitPackageViewDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitPropertyGetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitPropertySetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitScriptDescriptor", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitTypeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitTypeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitValueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "isExported", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;)Z", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorExportCheckerVisitor implements KotlinExportChecker<DeclarationDescriptor>, DeclarationDescriptorVisitor<Boolean, SpecialDeclarationType> {
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
    public boolean check(DeclarationDescriptor declaration, SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(type, "type");
        Object objAccept = declaration.accept(this, type);
        Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
        return ((Boolean) objAccept).booleanValue();
    }

    private final <D extends DeclarationDescriptorNonRoot & DeclarationDescriptorWithVisibility> boolean isExported(D d) {
        if (d.getContainingDeclaration() instanceof PackageFragmentDescriptor) {
            DescriptorVisibility visibility = d.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            if (visibility.isPublicAPI() || visibility == DescriptorVisibilities.INTERNAL) {
                return true;
            }
            if (visibility == DescriptorVisibilities.LOCAL) {
                return false;
            }
            return d.getAnnotations().hasAnnotation(MangleUtilsKt.getPublishedApiAnnotation()) || isPlatformSpecificExported(d);
        }
        if (d.getVisibility() != DescriptorVisibilities.LOCAL) {
            Object objAccept = d.getContainingDeclaration().accept(this, SpecialDeclarationType.REGULAR);
            Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
            if (((Boolean) objAccept).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitPackageFragmentDescriptor(PackageFragmentDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitPackageViewDescriptor(PackageViewDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitVariableDescriptor(VariableDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitFunctionDescriptor(FunctionDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return Boolean.valueOf(!IrExportCheckerVisitorKt.isAnonymous(name2) && isExported(descriptor));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitTypeParameterDescriptor(TypeParameterDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        Object objAccept = descriptor.getContainingDeclaration().accept(this, data);
        Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
        return (Boolean) objAccept;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitClassDescriptor(ClassDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data == SpecialDeclarationType.ANON_INIT) {
            return false;
        }
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (IrExportCheckerVisitorKt.isAnonymous(name2)) {
            return false;
        }
        return Boolean.valueOf(isExported(descriptor));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitTypeAliasDescriptor(TypeAliasDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(descriptor));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitModuleDeclaration(ModuleDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(constructorDescriptor.getConstructedClass()));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitScriptDescriptor(ScriptDescriptor scriptDescriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitPropertyDescriptor(PropertyDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(descriptor));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitValueParameterDescriptor(ValueParameterDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitPropertyGetterDescriptor(PropertyGetterDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(descriptor.getCorrespondingProperty()));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitPropertySetterDescriptor(PropertySetterDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(descriptor.getCorrespondingProperty()));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public Boolean visitReceiverParameterDescriptor(ReceiverParameterDescriptor descriptor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }
}
