package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;

/* JADX INFO: compiled from: CyclicAnnotationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/CyclicAnnotationsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Checker", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CyclicAnnotationsChecker implements DeclarationChecker {
    public static final CyclicAnnotationsChecker INSTANCE = new CyclicAnnotationsChecker();

    private CyclicAnnotationsChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtPrimaryConstructor primaryConstructor;
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (declaration instanceof KtClass) {
            KtClass ktClass = (KtClass) declaration;
            if (ktClass.isAnnotation() && (descriptor instanceof ClassDescriptor)) {
                ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                if (classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS || (primaryConstructor = ktClass.getPrimaryConstructor()) == null || (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor()) == null) {
                    return;
                }
                Checker checker = new Checker(classDescriptor);
                List<KtParameter> valueParameters = primaryConstructor.getValueParameters();
                List<ValueParameterDescriptor> valueParameters2 = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                for (Pair pair : CollectionsKt.zip(valueParameters, valueParameters2)) {
                    KtParameter ktParameter = (KtParameter) pair.component1();
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    if (checker.parameterHasCycle(classDescriptor, valueParameterDescriptor)) {
                        context.getTrace().report(Errors.CYCLE_IN_ANNOTATION_PARAMETER.on(context.getLanguageVersionSettings(), ktParameter));
                    }
                }
            }
        }
    }

    /* JADX INFO: compiled from: CyclicAnnotationsChecker.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003J\u0016\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/CyclicAnnotationsChecker$Checker;", "", "targetAnnotation", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "annotationDescriptorsWithCycle", "", "getTargetAnnotation", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "visitedAnnotationDescriptors", "annotationHasCycle", "", "annotationDescriptor", "parameterHasCycle", "ownedAnnotation", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "typeHasCycle", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/UnwrappedType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Checker {
        private final Set<ClassDescriptor> annotationDescriptorsWithCycle;
        private final ClassDescriptor targetAnnotation;
        private final Set<ClassDescriptor> visitedAnnotationDescriptors;

        public Checker(ClassDescriptor targetAnnotation) {
            Intrinsics.checkNotNullParameter(targetAnnotation, "targetAnnotation");
            this.targetAnnotation = targetAnnotation;
            this.visitedAnnotationDescriptors = SetsKt.mutableSetOf(targetAnnotation);
            this.annotationDescriptorsWithCycle = SetsKt.mutableSetOf(targetAnnotation);
        }

        public final ClassDescriptor getTargetAnnotation() {
            return this.targetAnnotation;
        }

        public final boolean annotationHasCycle(ClassDescriptor annotationDescriptor) {
            Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
            ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = annotationDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
            if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
                return false;
            }
            for (ValueParameterDescriptor valueParameterDescriptor : classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                if (parameterHasCycle(annotationDescriptor, valueParameterDescriptor)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean parameterHasCycle(ClassDescriptor ownedAnnotation, ValueParameterDescriptor parameterDescriptor) {
            UnwrappedType unwrappedTypeUnwrap;
            Intrinsics.checkNotNullParameter(ownedAnnotation, "ownedAnnotation");
            Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
            KotlinType returnType = parameterDescriptor.getReturnType();
            if (returnType == null || (unwrappedTypeUnwrap = returnType.unwrap()) == null || ArgumentsUtilsKt.isVararg(parameterDescriptor) || SimpleClassicTypeSystemContext.INSTANCE.isArrayOrNullableArray(unwrappedTypeUnwrap)) {
                return false;
            }
            if (!unwrappedTypeUnwrap.getArguments().isEmpty() && !ReflectionTypes.INSTANCE.isKClassType(unwrappedTypeUnwrap)) {
                for (TypeProjection typeProjection : unwrappedTypeUnwrap.getArguments()) {
                    if (!typeProjection.isStarProjection() && typeHasCycle(ownedAnnotation, typeProjection.getNullableAnyType().unwrap())) {
                        return true;
                    }
                }
                return false;
            }
            return typeHasCycle(ownedAnnotation, unwrappedTypeUnwrap);
        }

        public final boolean typeHasCycle(ClassDescriptor ownedAnnotation, UnwrappedType type) {
            Intrinsics.checkNotNullParameter(ownedAnnotation, "ownedAnnotation");
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor == null) {
                return false;
            }
            ClassDescriptor classDescriptor2 = classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS ? classDescriptor : null;
            if (classDescriptor2 == null) {
                return false;
            }
            if (!this.visitedAnnotationDescriptors.add(classDescriptor2)) {
                boolean zContains = this.annotationDescriptorsWithCycle.contains(classDescriptor2);
                if (zContains) {
                    this.annotationDescriptorsWithCycle.add(ownedAnnotation);
                }
                return zContains;
            }
            if (Intrinsics.areEqual(classDescriptor2, this.targetAnnotation)) {
                this.annotationDescriptorsWithCycle.add(ownedAnnotation);
                return true;
            }
            return annotationHasCycle(classDescriptor2);
        }
    }
}
