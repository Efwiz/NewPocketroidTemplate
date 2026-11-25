package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import com.android.dx.cf.attrib.AttSynthetic;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: compiled from: JvmDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001a\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0018\u0010\u000b\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\f\u001a\u0018\u0010\r\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u000e\u001a\u0018\u0010\u000f\u001a\u00020\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0005\u001a\u00020\u0012\u001a\u0016\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0012\u001a\u001e\u0010\u0015\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\bH\u0007\u001a\u000e\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\b\u001a\u001e\u0010\u0016\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\bH\u0007\u001a\u0016\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0012\u001a\u000e\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u000e\u001a\u0018\u0010\u001a\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\b\u001a\u000e\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u000e\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u001c"}, d2 = {"CollectionStub", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "getCollectionStub", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "AugmentedBuiltInApi", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Bridge", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", JvmAbi.DEFAULT_IMPLS_CLASS_NAME, "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Delegation", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "MultifileClass", "representativeFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "MultifileClassPart", "file", "OtherOrigin", "OtherOriginFromPure", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "PackagePart", "SamDelegation", AttSynthetic.ATTRIBUTE_NAME, "UnboxMethodOfInlineClass", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmDeclarationOriginKt {
    private static final JvmDeclarationOrigin CollectionStub = new JvmDeclarationOrigin(JvmDeclarationOriginKind.COLLECTION_STUB, null, null, null, 8, null);

    public static final JvmDeclarationOrigin OtherOrigin(PsiElement psiElement) {
        return OtherOrigin$default(psiElement, null, 2, null);
    }

    public static final JvmDeclarationOrigin OtherOriginFromPure(KtPureElement ktPureElement) {
        return OtherOriginFromPure$default(ktPureElement, null, 2, null);
    }

    public static /* synthetic */ JvmDeclarationOrigin OtherOrigin$default(PsiElement psiElement, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
        if ((i & 2) != 0) {
            declarationDescriptor = null;
        }
        return OtherOrigin(psiElement, declarationDescriptor);
    }

    public static final JvmDeclarationOrigin OtherOrigin(PsiElement psiElement, DeclarationDescriptor declarationDescriptor) {
        if (psiElement == null && declarationDescriptor == null) {
            return JvmDeclarationOrigin.NO_ORIGIN;
        }
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.OTHER, psiElement, declarationDescriptor, null, 8, null);
    }

    public static /* synthetic */ JvmDeclarationOrigin OtherOriginFromPure$default(KtPureElement ktPureElement, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
        if ((i & 2) != 0) {
            declarationDescriptor = null;
        }
        return OtherOriginFromPure(ktPureElement, declarationDescriptor);
    }

    public static final JvmDeclarationOrigin OtherOriginFromPure(KtPureElement ktPureElement, DeclarationDescriptor declarationDescriptor) {
        return OtherOrigin(ktPureElement != null ? ktPureElement.getPsiOrParent() : null, declarationDescriptor);
    }

    public static final JvmDeclarationOrigin OtherOrigin(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.OTHER, DescriptorToSourceUtils.descriptorToDeclaration(descriptor), descriptor, null, 8, null);
    }

    public static /* synthetic */ JvmDeclarationOrigin Bridge$default(DeclarationDescriptor declarationDescriptor, PsiElement psiElement, int i, Object obj) {
        if ((i & 2) != 0) {
            psiElement = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
        }
        return Bridge(declarationDescriptor, psiElement);
    }

    public static final JvmDeclarationOrigin Bridge(DeclarationDescriptor descriptor, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.BRIDGE, psiElement, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin PackagePart(KtFile file, PackageFragmentDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.PACKAGE_PART, file, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin MultifileClass(KtFile ktFile, PackageFragmentDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.MULTIFILE_CLASS, ktFile, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin MultifileClassPart(KtFile file, PackageFragmentDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.MULTIFILE_CLASS_PART, file, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin DefaultImpls(PsiElement psiElement, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.INTERFACE_DEFAULT_IMPL, psiElement, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin Delegation(PsiElement psiElement, FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.DELEGATION, psiElement, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin SamDelegation(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.SAM_DELEGATION, null, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin Synthetic(PsiElement psiElement, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.SYNTHETIC, psiElement, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin getCollectionStub() {
        return CollectionStub;
    }

    public static final JvmDeclarationOrigin AugmentedBuiltInApi(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.AUGMENTED_BUILTIN_API, null, descriptor, null, 8, null);
    }

    public static final JvmDeclarationOrigin UnboxMethodOfInlineClass(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JvmDeclarationOrigin(JvmDeclarationOriginKind.UNBOX_METHOD_OF_INLINE_CLASS, null, descriptor, null, 8, null);
    }
}
