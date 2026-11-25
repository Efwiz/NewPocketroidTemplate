package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.ClassifierNamePolicy;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: adaptiveClassifierNamePolicy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J$\u0010\u0012\u001a\u00020\u000f*\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000bH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/AdaptiveClassifierNamePolicy;", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "ambiguousNames", "", "Lorg/jetbrains/kotlin/name/Name;", "(Ljava/util/List;)V", "renderedParameters", "", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "hasUniqueName", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderClassifier", "", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "renderAmbiguousTypeParameter", "typeParameter", "index", "", "firstOccurence", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AdaptiveClassifierNamePolicy implements ClassifierNamePolicy {
    private final List<Name> ambiguousNames;
    private final Map<Name, LinkedHashSet<TypeParameterDescriptor>> renderedParameters;

    public AdaptiveClassifierNamePolicy(List<Name> ambiguousNames) {
        Intrinsics.checkNotNullParameter(ambiguousNames, "ambiguousNames");
        this.ambiguousNames = ambiguousNames;
        this.renderedParameters = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.renderer.ClassifierNamePolicy
    public String renderClassifier(ClassifierDescriptor classifier, DescriptorRenderer renderer) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        if (hasUniqueName(classifier)) {
            return ClassifierNamePolicy.SHORT.INSTANCE.renderClassifier(classifier, renderer);
        }
        if ((classifier instanceof ClassDescriptor) || (classifier instanceof TypeAliasDescriptor)) {
            return ClassifierNamePolicy.FULLY_QUALIFIED.INSTANCE.renderClassifier(classifier, renderer);
        }
        if (classifier instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) classifier;
            Name name2 = typeParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Map<Name, LinkedHashSet<TypeParameterDescriptor>> map = this.renderedParameters;
            LinkedHashSet<TypeParameterDescriptor> linkedHashSet = map.get(name2);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet<>();
                map.put(name2, linkedHashSet);
            }
            LinkedHashSet<TypeParameterDescriptor> linkedHashSet2 = linkedHashSet;
            return renderAmbiguousTypeParameter(renderer, typeParameterDescriptor, CollectionsKt.indexOf(linkedHashSet2, classifier) + 1, linkedHashSet2.add(classifier));
        }
        throw new IllegalStateException(("Unexpected classifier: " + classifier.getClass()).toString());
    }

    private final boolean hasUniqueName(ClassifierDescriptor classifier) {
        return !this.ambiguousNames.contains(classifier.getShortName());
    }

    private final String renderAmbiguousTypeParameter(DescriptorRenderer descriptorRenderer, TypeParameterDescriptor typeParameterDescriptor, int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(typeParameterDescriptor.getShortName());
        sb.append("#" + i);
        if (z) {
            StringBuilder sb2 = new StringBuilder(" (type parameter of ");
            DeclarationDescriptor containingDeclaration = typeParameterDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            sb.append(descriptorRenderer.renderMessage(sb2.append(descriptorRenderer.renderFqName(DescriptorUtilsKt.getFqNameUnsafe(containingDeclaration))).append(')').toString()));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
