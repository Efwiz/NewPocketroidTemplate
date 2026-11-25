package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryPackageSourceElement;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.load.kotlin.VirtualFileKotlinClass;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: JvmModuleAccessibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J.\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmModuleAccessibilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "moduleResolver", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "diagnosticFor", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "targetClassOrPackage", "Lorg/jetbrains/kotlin/descriptors/ClassOrPackageFragmentDescriptor;", "originalDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "fileFromOurModule", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "findVirtualFile", "descriptor", "ClassifierUsage", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmModuleAccessibilityChecker implements CallChecker {
    private final JavaModuleResolver moduleResolver;

    public JvmModuleAccessibilityChecker(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.moduleResolver = JavaModuleResolver.INSTANCE.getInstance(project);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if ((resultingDescriptor instanceof ClassConstructorDescriptor) || (classOrPackageFragmentDescriptor = (ClassOrPackageFragmentDescriptor) DescriptorUtils.getParentOfType(resultingDescriptor, ClassOrPackageFragmentDescriptor.class)) == null) {
            return;
        }
        KtFile containingFile = DescriptorToSourceUtils.getContainingFile(context.getScope().getOwnerDescriptor());
        Diagnostic diagnosticDiagnosticFor = diagnosticFor(classOrPackageFragmentDescriptor, resultingDescriptor, containingFile != null ? containingFile.getVirtualFile() : null, reportOn);
        if (diagnosticDiagnosticFor != null) {
            context.getTrace().report(diagnosticDiagnosticFor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Diagnostic diagnosticFor(ClassOrPackageFragmentDescriptor targetClassOrPackage, DeclarationDescriptorWithSource originalDescriptor, VirtualFile fileFromOurModule, PsiElement reportOn) {
        FqName fqName;
        JvmClassName implClassNameForDeserialized;
        VirtualFile virtualFileFindVirtualFile = findVirtualFile(targetClassOrPackage, originalDescriptor);
        if (virtualFileFindVirtualFile == null) {
            return null;
        }
        DeserializedMemberDescriptor deserializedMemberDescriptor = originalDescriptor instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) originalDescriptor : null;
        if (deserializedMemberDescriptor == null || (implClassNameForDeserialized = UtilKt.getImplClassNameForDeserialized(deserializedMemberDescriptor)) == null || (fqName = implClassNameForDeserialized.getPackageFqName()) == null) {
            PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(targetClassOrPackage, PackageFragmentDescriptor.class, false);
            fqName = packageFragmentDescriptor != null ? packageFragmentDescriptor.getFqName() : null;
        }
        JavaModuleResolver.AccessError accessErrorCheckAccessibility = this.moduleResolver.checkAccessibility(fileFromOurModule, virtualFileFindVirtualFile, fqName);
        if (accessErrorCheckAccessibility instanceof JavaModuleResolver.AccessError.ModuleDoesNotReadUnnamedModule) {
            return ErrorsJvm.JAVA_MODULE_DOES_NOT_READ_UNNAMED_MODULE.on(reportOn);
        }
        if (accessErrorCheckAccessibility instanceof JavaModuleResolver.AccessError.ModuleDoesNotReadModule) {
            return ErrorsJvm.JAVA_MODULE_DOES_NOT_DEPEND_ON_MODULE.on(reportOn, ((JavaModuleResolver.AccessError.ModuleDoesNotReadModule) accessErrorCheckAccessibility).getDependencyModuleName());
        }
        if (!(accessErrorCheckAccessibility instanceof JavaModuleResolver.AccessError.ModuleDoesNotExportPackage)) {
            return null;
        }
        DiagnosticFactory2<PsiElement, String, String> diagnosticFactory2 = ErrorsJvm.JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE;
        String dependencyModuleName = ((JavaModuleResolver.AccessError.ModuleDoesNotExportPackage) accessErrorCheckAccessibility).getDependencyModuleName();
        Intrinsics.checkNotNull(fqName);
        return diagnosticFactory2.on(reportOn, dependencyModuleName, fqName.asString());
    }

    private final VirtualFile findVirtualFile(ClassOrPackageFragmentDescriptor descriptor, DeclarationDescriptorWithSource originalDescriptor) {
        VirtualFile file;
        VirtualFile virtualFile;
        SourceElement sourceElement;
        VirtualFile file2;
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        if (source instanceof KotlinJvmBinarySourceElement) {
            KotlinJvmBinaryClass binaryClass = ((KotlinJvmBinarySourceElement) source).getBinaryClass();
            VirtualFileKotlinClass virtualFileKotlinClass = binaryClass instanceof VirtualFileKotlinClass ? (VirtualFileKotlinClass) binaryClass : null;
            if (virtualFileKotlinClass != null && (file2 = virtualFileKotlinClass.getFile()) != null) {
                return file2;
            }
        } else if (source instanceof JavaSourceElement) {
            JavaElement javaElement = ((JavaSourceElement) source).getJavaElement();
            VirtualFileBoundJavaClass virtualFileBoundJavaClass = javaElement instanceof VirtualFileBoundJavaClass ? (VirtualFileBoundJavaClass) javaElement : null;
            if (virtualFileBoundJavaClass != null && (virtualFile = virtualFileBoundJavaClass.getVirtualFile()) != null) {
                return virtualFile;
            }
        } else if ((source instanceof KotlinJvmBinaryPackageSourceElement) && (originalDescriptor instanceof DeserializedMemberDescriptor)) {
            KotlinJvmBinaryClass containingBinaryClass = ((KotlinJvmBinaryPackageSourceElement) source).getContainingBinaryClass((DeserializedMemberDescriptor) originalDescriptor);
            VirtualFileKotlinClass virtualFileKotlinClass2 = containingBinaryClass instanceof VirtualFileKotlinClass ? (VirtualFileKotlinClass) containingBinaryClass : null;
            if (virtualFileKotlinClass2 != null && (file = virtualFileKotlinClass2.getFile()) != null) {
                return file;
            }
        }
        VirtualFile virtualFileFindVirtualFile$getContainingVirtualFile = findVirtualFile$getContainingVirtualFile(source);
        if (virtualFileFindVirtualFile$getContainingVirtualFile != null) {
            return virtualFileFindVirtualFile$getContainingVirtualFile;
        }
        if (originalDescriptor == null || (sourceElement = originalDescriptor.getSourceElement()) == null) {
            return null;
        }
        return findVirtualFile$getContainingVirtualFile(sourceElement);
    }

    private static final VirtualFile findVirtualFile$getContainingVirtualFile(SourceElement sourceElement) {
        PsiFile containingFile;
        VirtualFile virtualFile;
        PsiElement psi = PsiSourceElementKt.getPsi(sourceElement);
        if (psi == null) {
            return null;
        }
        if (!psi.isValid()) {
            psi = null;
        }
        if (psi == null || (containingFile = psi.getContainingFile()) == null || (virtualFile = containingFile.getVirtualFile()) == null || !virtualFile.isValid()) {
            return null;
        }
        return virtualFile;
    }

    /* JADX INFO: compiled from: JvmModuleAccessibilityChecker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmModuleAccessibilityChecker$ClassifierUsage;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "(Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmModuleAccessibilityChecker;)V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ClassifierUsage implements ClassifierUsageChecker {
        public ClassifierUsage() {
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
        public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
            Diagnostic diagnosticDiagnosticFor;
            Diagnostic diagnosticDiagnosticFor2;
            Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(context, "context");
            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (targetDescriptor instanceof ClassDescriptor) {
                Diagnostic diagnosticDiagnosticFor3 = JvmModuleAccessibilityChecker.this.diagnosticFor((ClassOrPackageFragmentDescriptor) targetDescriptor, targetDescriptor, virtualFile, element);
                if (diagnosticDiagnosticFor3 != null) {
                    context.getTrace().report(diagnosticDiagnosticFor3);
                    return;
                }
                return;
            }
            if (targetDescriptor instanceof TypeAliasDescriptor) {
                ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor = (ClassOrPackageFragmentDescriptor) DescriptorUtils.getParentOfType(targetDescriptor, ClassOrPackageFragmentDescriptor.class);
                if (classOrPackageFragmentDescriptor != null && (diagnosticDiagnosticFor2 = JvmModuleAccessibilityChecker.this.diagnosticFor(classOrPackageFragmentDescriptor, targetDescriptor, virtualFile, element)) != null) {
                    context.getTrace().report(diagnosticDiagnosticFor2);
                }
                ClassifierDescriptor this$0 = ((TypeAliasDescriptor) targetDescriptor).getExpandedType().getConstructor().getThis$0();
                ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                if (classDescriptor == null || (diagnosticDiagnosticFor = JvmModuleAccessibilityChecker.this.diagnosticFor(classDescriptor, classDescriptor, virtualFile, element)) == null) {
                    return;
                }
                context.getTrace().report(diagnosticDiagnosticFor);
            }
        }
    }
}
