package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB-\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0018¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "desc", "", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "arguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "isFreshlySupportedTypeUseAnnotation", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Ljava/util/Collection;Z)V", "getArguments", "()Ljava/util/Collection;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "classifierResolutionResult", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "getClassifierResolutionResult", "()Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "classifierResolutionResult$delegate", "Lkotlin/Lazy;", "()Z", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Companion", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaAnnotation implements JavaAnnotation {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Collection<JavaAnnotationArgument> arguments;

    /* JADX INFO: renamed from: classifierResolutionResult$delegate, reason: from kotlin metadata */
    private final Lazy classifierResolutionResult;
    private final ClassifierResolutionContext context;
    private final boolean isFreshlySupportedTypeUseAnnotation;

    public /* synthetic */ BinaryJavaAnnotation(String str, ClassifierResolutionContext classifierResolutionContext, Collection collection, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, classifierResolutionContext, collection, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private BinaryJavaAnnotation(final String str, ClassifierResolutionContext classifierResolutionContext, Collection<? extends JavaAnnotationArgument> collection, boolean z) {
        this.context = classifierResolutionContext;
        this.arguments = collection;
        this.isFreshlySupportedTypeUseAnnotation = z;
        this.classifierResolutionResult = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ClassifierResolutionContext.Result>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaAnnotation$classifierResolutionResult$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierResolutionContext.Result invoke() {
                ClassifierResolutionContext classifierResolutionContext2 = this.this$0.context;
                String internalName = Type.getType(str).getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                return classifierResolutionContext2.resolveByInternalName$resolution_common_jvm(internalName);
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public Collection<JavaAnnotationArgument> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    /* JADX INFO: renamed from: isFreshlySupportedTypeUseAnnotation, reason: from getter */
    public boolean getIsFreshlySupportedTypeUseAnnotation() {
        return this.isFreshlySupportedTypeUseAnnotation;
    }

    /* JADX INFO: compiled from: Annotations.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eJ\u001f\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u0014J#\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0000¢\u0006\u0002\b\u001cJ4\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00040\u001e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010 \u001a\u00020\u000eJ\"\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0\u001e0\u00182\u0006\u0010#\u001a\u00020\u0013H\u0002¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaAnnotation$Companion;", "", "()V", "addAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "annotationOwner", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "desc", "", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "isFreshlySupportedAnnotation", "", "computeTargetType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "baseType", "typePath", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "computeTargetType$resolution_common_jvm", "computeTypeParameterBound", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "typeReference", "Lorg/jetbrains/org/objectweb/asm/TypeReference;", "computeTypeParameterBound$resolution_common_jvm", "createAnnotationAndVisitor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "isFreshlySupportedTypeUseAnnotation", "translatePath", "", "path", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ Pair createAnnotationAndVisitor$default(Companion companion, String str, ClassifierResolutionContext classifierResolutionContext, BinaryClassSignatureParser binaryClassSignatureParser, boolean z, int i, Object obj) {
            if ((i & 8) != 0) {
                z = false;
            }
            return companion.createAnnotationAndVisitor(str, classifierResolutionContext, binaryClassSignatureParser, z);
        }

        public final Pair<JavaAnnotation, AnnotationVisitor> createAnnotationAndVisitor(String desc, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, boolean isFreshlySupportedTypeUseAnnotation) {
            Intrinsics.checkNotNullParameter(desc, "desc");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
            ArrayList arrayList = new ArrayList();
            return TuplesKt.to(new BinaryJavaAnnotation(desc, context, arrayList, isFreshlySupportedTypeUseAnnotation, null), new BinaryJavaAnnotationVisitor(context, signatureParser, arrayList));
        }

        public static /* synthetic */ AnnotationVisitor addAnnotation$default(Companion companion, MutableJavaAnnotationOwner mutableJavaAnnotationOwner, String str, ClassifierResolutionContext classifierResolutionContext, BinaryClassSignatureParser binaryClassSignatureParser, boolean z, int i, Object obj) {
            if ((i & 16) != 0) {
                z = false;
            }
            return companion.addAnnotation(mutableJavaAnnotationOwner, str, classifierResolutionContext, binaryClassSignatureParser, z);
        }

        public final AnnotationVisitor addAnnotation(MutableJavaAnnotationOwner annotationOwner, String desc, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, boolean isFreshlySupportedAnnotation) {
            Intrinsics.checkNotNullParameter(annotationOwner, "annotationOwner");
            Intrinsics.checkNotNullParameter(desc, "desc");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
            Pair<JavaAnnotation, AnnotationVisitor> pairCreateAnnotationAndVisitor = createAnnotationAndVisitor(desc, context, signatureParser, isFreshlySupportedAnnotation);
            JavaAnnotation javaAnnotationComponent1 = pairCreateAnnotationAndVisitor.component1();
            AnnotationVisitor annotationVisitorComponent2 = pairCreateAnnotationAndVisitor.component2();
            annotationOwner.mo7252getAnnotations().add(javaAnnotationComponent1);
            return annotationVisitorComponent2;
        }

        private final List<Pair<Integer, Integer>> translatePath(TypePath path) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            int length = path.getLength();
            for (int i = 0; i < length; i++) {
                int step = path.getStep(i);
                if (step == 0 || step == 2) {
                    listCreateListBuilder.add(TuplesKt.to(Integer.valueOf(step), 0));
                } else if (step == 3) {
                    listCreateListBuilder.add(TuplesKt.to(Integer.valueOf(step), Integer.valueOf(path.getStepArgument(i))));
                }
            }
            return CollectionsKt.build(listCreateListBuilder);
        }

        public final JavaType computeTargetType$resolution_common_jvm(JavaType baseType, TypePath typePath) {
            Intrinsics.checkNotNullParameter(baseType, "baseType");
            Intrinsics.checkNotNullParameter(typePath, "typePath");
            Iterator<T> it = translatePath(typePath).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                int iIntValue = ((Number) pair.component1()).intValue();
                int iIntValue2 = ((Number) pair.component2()).intValue();
                if (iIntValue != 0) {
                    if (iIntValue != 2) {
                        if (iIntValue != 3) {
                            continue;
                        } else {
                            if (!(baseType instanceof JavaClassifierType)) {
                                throw new IllegalArgumentException("Failed requirement.".toString());
                            }
                            baseType = (JavaType) CollectionsKt.getOrNull(((JavaClassifierType) baseType).mo7270getTypeArguments(), iIntValue2);
                        }
                    } else {
                        if (!(baseType instanceof JavaWildcardType)) {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                        baseType = ((JavaWildcardType) baseType).getBound();
                    }
                } else {
                    if (!(baseType instanceof JavaArrayType)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    baseType = ((JavaArrayType) baseType).getComponentType();
                }
            }
            return baseType;
        }

        public final JavaClassifierType computeTypeParameterBound$resolution_common_jvm(List<? extends JavaTypeParameter> typeParameters, TypeReference typeReference) {
            int typeParameterBoundIndex;
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(typeReference, "typeReference");
            JavaTypeParameter javaTypeParameter = typeParameters.get(typeReference.getTypeParameterIndex());
            if (!(javaTypeParameter instanceof BinaryJavaTypeParameter)) {
                throw new IllegalArgumentException("Type parameter must be a binary type parameter".toString());
            }
            if (((BinaryJavaTypeParameter) javaTypeParameter).getHasImplicitObjectClassBound()) {
                typeParameterBoundIndex = typeReference.getTypeParameterBoundIndex() - 1;
            } else {
                typeParameterBoundIndex = typeReference.getTypeParameterBoundIndex();
            }
            return (JavaClassifierType) CollectionsKt.elementAt(javaTypeParameter.getUpperBounds(), typeParameterBoundIndex);
        }
    }

    private final ClassifierResolutionContext.Result getClassifierResolutionResult() {
        return (ClassifierResolutionContext.Result) this.classifierResolutionResult.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public ClassId getClassId() {
        ClassId classId;
        JavaClassifier classifier = getClassifierResolutionResult().getClassifier();
        JavaClass javaClass = classifier instanceof JavaClass ? (JavaClass) classifier : null;
        if (javaClass != null && (classId = JavaElementsKt.getClassId(javaClass)) != null) {
            return classId;
        }
        ClassId classId2 = ClassId.topLevel(new FqName(getClassifierResolutionResult().getQualifiedName()));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        return classId2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public JavaClass resolve() {
        JavaClassifier classifier = getClassifierResolutionResult().getClassifier();
        if (classifier instanceof JavaClass) {
            return (JavaClass) classifier;
        }
        return null;
    }
}
