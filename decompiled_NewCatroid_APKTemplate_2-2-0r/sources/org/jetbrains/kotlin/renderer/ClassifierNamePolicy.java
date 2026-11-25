package org.jetbrains.kotlin.renderer;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: ClassifierNamePolicy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\b\t\nJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "", "renderClassifier", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "FULLY_QUALIFIED", "SHORT", "SOURCE_CODE_QUALIFIED", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassifierNamePolicy {
    String renderClassifier(ClassifierDescriptor classifier, DescriptorRenderer renderer);

    /* JADX INFO: compiled from: ClassifierNamePolicy.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy$SHORT;", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "()V", "renderClassifier", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SHORT implements ClassifierNamePolicy {
        public static final SHORT INSTANCE = new SHORT();

        private SHORT() {
        }

        @Override // org.jetbrains.kotlin.renderer.ClassifierNamePolicy
        public String renderClassifier(ClassifierDescriptor classifier, DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            if (classifier instanceof TypeParameterDescriptor) {
                Name name2 = ((TypeParameterDescriptor) classifier).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return renderer.renderName(name2, false);
            }
            ArrayList arrayList = new ArrayList();
            ClassifierDescriptor moduleDescriptor = classifier;
            do {
                arrayList.add(moduleDescriptor.getName());
                moduleDescriptor = moduleDescriptor.getModuleDescriptor();
            } while (moduleDescriptor instanceof ClassDescriptor);
            return RenderingUtilsKt.renderFqName(CollectionsKt.asReversedMutable(arrayList));
        }
    }

    /* JADX INFO: compiled from: ClassifierNamePolicy.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy$FULLY_QUALIFIED;", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "()V", "renderClassifier", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FULLY_QUALIFIED implements ClassifierNamePolicy {
        public static final FULLY_QUALIFIED INSTANCE = new FULLY_QUALIFIED();

        private FULLY_QUALIFIED() {
        }

        @Override // org.jetbrains.kotlin.renderer.ClassifierNamePolicy
        public String renderClassifier(ClassifierDescriptor classifier, DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            if (classifier instanceof TypeParameterDescriptor) {
                Name name2 = ((TypeParameterDescriptor) classifier).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return renderer.renderName(name2, false);
            }
            FqNameUnsafe fqName = DescriptorUtils.getFqName(classifier);
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            return renderer.renderFqName(fqName);
        }
    }

    /* JADX INFO: compiled from: ClassifierNamePolicy.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy$SOURCE_CODE_QUALIFIED;", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "()V", "qualifiedNameForSourceCode", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "qualifierName", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "renderClassifier", "classifier", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SOURCE_CODE_QUALIFIED implements ClassifierNamePolicy {
        public static final SOURCE_CODE_QUALIFIED INSTANCE = new SOURCE_CODE_QUALIFIED();

        private SOURCE_CODE_QUALIFIED() {
        }

        @Override // org.jetbrains.kotlin.renderer.ClassifierNamePolicy
        public String renderClassifier(ClassifierDescriptor classifier, DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            return qualifiedNameForSourceCode(classifier);
        }

        private final String qualifiedNameForSourceCode(ClassifierDescriptor descriptor) {
            Name name2 = descriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            String strRender = RenderingUtilsKt.render(name2);
            if (descriptor instanceof TypeParameterDescriptor) {
                return strRender;
            }
            DeclarationDescriptor containingDeclaration = descriptor.getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            String strQualifierName = qualifierName(containingDeclaration);
            return (strQualifierName == null || Intrinsics.areEqual(strQualifierName, "")) ? strRender : strQualifierName + '.' + strRender;
        }

        private final String qualifierName(DeclarationDescriptor descriptor) {
            if (descriptor instanceof ClassDescriptor) {
                return qualifiedNameForSourceCode((ClassifierDescriptor) descriptor);
            }
            if (!(descriptor instanceof PackageFragmentDescriptor)) {
                return null;
            }
            FqNameUnsafe unsafe = ((PackageFragmentDescriptor) descriptor).getFqName().toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
            return RenderingUtilsKt.render(unsafe);
        }
    }
}
