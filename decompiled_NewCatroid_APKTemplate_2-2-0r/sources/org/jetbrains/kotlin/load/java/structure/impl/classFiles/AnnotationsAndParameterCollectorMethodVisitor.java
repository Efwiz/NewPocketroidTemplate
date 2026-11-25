package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\u001a\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001f\u001a\u00020\tH\u0016J\"\u0010 \u001a\u0004\u0018\u00010\u00192\u0006\u0010!\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J,\u0010\"\u001a\u0004\u0018\u00010\u00192\u0006\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/AnnotationsAndParameterCollectorMethodVisitor;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "member", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "parametersToSkipNumber", "", "parametersCountInMethodDesc", "(Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;II)V", "freshlySupportedPositions", "", "getFreshlySupportedPositions", "()Ljava/util/Set;", "invisibleAnnotableParameterCount", "parameterIndex", "visibleAnnotableParameterCount", "visitAnnotableParameterCount", "", "parameterCount", "visible", "", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "", "visitAnnotationDefault", "visitParameter", "name", "access", "visitParameterAnnotation", "parameter", "visitTypeAnnotation", "typeRef", "typePath", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "descriptor", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsAndParameterCollectorMethodVisitor extends MethodVisitor {
    private final ClassifierResolutionContext context;
    private final Set<Integer> freshlySupportedPositions;
    private int invisibleAnnotableParameterCount;
    private final BinaryJavaMethodBase member;
    private int parameterIndex;
    private final int parametersCountInMethodDesc;
    private final int parametersToSkipNumber;
    private final BinaryClassSignatureParser signatureParser;
    private int visibleAnnotableParameterCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotationsAndParameterCollectorMethodVisitor(BinaryJavaMethodBase member, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, int i, int i2) {
        super(589824);
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        this.member = member;
        this.context = context;
        this.signatureParser = signatureParser;
        this.parametersToSkipNumber = i;
        this.parametersCountInMethodDesc = i2;
        this.visibleAnnotableParameterCount = i2;
        this.invisibleAnnotableParameterCount = i2;
        this.freshlySupportedPositions = SetsKt.setOf((Object[]) new Integer[]{1, 18});
    }

    public final Set<Integer> getFreshlySupportedPositions() {
        return this.freshlySupportedPositions;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        return new BinaryJavaAnnotationVisitor(this.context, this.signatureParser, new Function1<JavaAnnotationArgument, Unit>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.AnnotationsAndParameterCollectorMethodVisitor.visitAnnotationDefault.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JavaAnnotationArgument javaAnnotationArgument) {
                invoke2(javaAnnotationArgument);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JavaAnnotationArgument it) {
                Intrinsics.checkNotNullParameter(it, "it");
                BinaryJavaMethodBase binaryJavaMethodBase = AnnotationsAndParameterCollectorMethodVisitor.this.member;
                BinaryJavaMethod binaryJavaMethod = binaryJavaMethodBase instanceof BinaryJavaMethod ? (BinaryJavaMethod) binaryJavaMethodBase : null;
                if (binaryJavaMethod == null) {
                    return;
                }
                binaryJavaMethod.setAnnotationParameterDefaultValue$resolution_common_jvm(it);
            }
        });
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitParameter(String name2, int access) {
        int i;
        if (name2 != null && (i = this.parameterIndex - this.parametersToSkipNumber) >= 0) {
            BinaryJavaValueParameter binaryJavaValueParameter = (BinaryJavaValueParameter) CollectionsKt.getOrNull(this.member.getValueParameters(), i);
            if (binaryJavaValueParameter == null) {
                throw new IllegalStateException(("No parameter with index " + this.parameterIndex + '-' + this.parametersToSkipNumber + " (name=" + name2 + " access=" + access + ") in method " + this.member.getContainingClass().getFqName() + '.' + this.member.getName()).toString());
            }
            Name nameIdentifier = Name.identifier(name2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            binaryJavaValueParameter.updateName$resolution_common_jvm(nameIdentifier);
        }
        this.parameterIndex++;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return BinaryJavaAnnotation.Companion.addAnnotation$default(BinaryJavaAnnotation.INSTANCE, this.member, desc, this.context, this.signatureParser, false, 16, null);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
        if (visible) {
            this.visibleAnnotableParameterCount = parameterCount;
        } else {
            this.invisibleAnnotableParameterCount = parameterCount;
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        int i = ((parameter + this.parametersCountInMethodDesc) - (visible ? this.visibleAnnotableParameterCount : this.invisibleAnnotableParameterCount)) - this.parametersToSkipNumber;
        if (i < 0) {
            return null;
        }
        return BinaryJavaAnnotation.Companion.addAnnotation$default(BinaryJavaAnnotation.INSTANCE, this.member.getValueParameters().get(i), desc, this.context, this.signatureParser, false, 16, null);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        Pair<JavaType, Boolean> pairVisitTypeAnnotation$getTargetType;
        JavaType returnType;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        TypeReference typeReference = new TypeReference(typeRef);
        int sort = typeReference.getSort();
        if (sort == 1) {
            pairVisitTypeAnnotation$getTargetType = TuplesKt.to(this.member.getTypeParameters().get(typeReference.getTypeParameterIndex()), true);
        } else if (sort == 18) {
            pairVisitTypeAnnotation$getTargetType = visitTypeAnnotation$getTargetType(typePath, this, typeReference, BinaryJavaAnnotation.INSTANCE.computeTypeParameterBound$resolution_common_jvm(this.member.getTypeParameters(), typeReference));
        } else if (sort == 20) {
            BinaryJavaMethodBase binaryJavaMethodBase = this.member;
            BinaryJavaMethod binaryJavaMethod = binaryJavaMethodBase instanceof BinaryJavaMethod ? (BinaryJavaMethod) binaryJavaMethodBase : null;
            if (binaryJavaMethod == null || (returnType = binaryJavaMethod.getReturnType()) == null) {
                return null;
            }
            pairVisitTypeAnnotation$getTargetType = visitTypeAnnotation$getTargetType(typePath, this, typeReference, returnType);
        } else {
            if (sort != 22) {
                return null;
            }
            pairVisitTypeAnnotation$getTargetType = visitTypeAnnotation$getTargetType(typePath, this, typeReference, this.member.getValueParameters().get(typeReference.getFormalParameterIndex()).getType());
        }
        JavaType javaTypeComponent1 = pairVisitTypeAnnotation$getTargetType.component1();
        boolean zBooleanValue = pairVisitTypeAnnotation$getTargetType.component2().booleanValue();
        if (javaTypeComponent1 instanceof MutableJavaAnnotationOwner) {
            return BinaryJavaAnnotation.INSTANCE.addAnnotation((MutableJavaAnnotationOwner) javaTypeComponent1, descriptor, this.context, this.signatureParser, zBooleanValue);
        }
        return null;
    }

    private static final Pair<JavaType, Boolean> visitTypeAnnotation$getTargetType(TypePath typePath, AnnotationsAndParameterCollectorMethodVisitor annotationsAndParameterCollectorMethodVisitor, TypeReference typeReference, JavaType javaType) {
        if (typePath != null) {
            return TuplesKt.to(BinaryJavaAnnotation.INSTANCE.computeTargetType$resolution_common_jvm(javaType, typePath), true);
        }
        return TuplesKt.to(javaType, Boolean.valueOf(annotationsAndParameterCollectorMethodVisitor.freshlySupportedPositions.contains(Integer.valueOf(typeReference.getSort()))));
    }
}
