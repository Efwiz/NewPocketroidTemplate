package org.jetbrains.kotlin;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: SpecialJvmAnnotations.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/SpecialJvmAnnotations;", "", "()V", "JAVA_LANG_ANNOTATION_REPEATABLE", "Lorg/jetbrains/kotlin/name/ClassId;", "getJAVA_LANG_ANNOTATION_REPEATABLE", "()Lorg/jetbrains/kotlin/name/ClassId;", "SPECIAL_ANNOTATIONS", "", "getSPECIAL_ANNOTATIONS", "()Ljava/util/Set;", "isAnnotatedWithContainerMetaAnnotation", "", "klass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpecialJvmAnnotations {
    public static final SpecialJvmAnnotations INSTANCE = new SpecialJvmAnnotations();
    private static final ClassId JAVA_LANG_ANNOTATION_REPEATABLE;
    private static final Set<ClassId> SPECIAL_ANNOTATIONS;

    private SpecialJvmAnnotations() {
    }

    public final Set<ClassId> getSPECIAL_ANNOTATIONS() {
        return SPECIAL_ANNOTATIONS;
    }

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new FqName[]{JvmAnnotationNames.METADATA_FQ_NAME, JvmAnnotationNames.JETBRAINS_NOT_NULL_ANNOTATION, JvmAnnotationNames.JETBRAINS_NULLABLE_ANNOTATION, JvmAnnotationNames.TARGET_ANNOTATION, JvmAnnotationNames.RETENTION_ANNOTATION, JvmAnnotationNames.DOCUMENTED_ANNOTATION});
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(ClassId.topLevel((FqName) it.next()));
        }
        SPECIAL_ANNOTATIONS = linkedHashSet;
        ClassId classId = ClassId.topLevel(JvmAnnotationNames.REPEATABLE_ANNOTATION);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JAVA_LANG_ANNOTATION_REPEATABLE = classId;
    }

    public final ClassId getJAVA_LANG_ANNOTATION_REPEATABLE() {
        return JAVA_LANG_ANNOTATION_REPEATABLE;
    }

    public final boolean isAnnotatedWithContainerMetaAnnotation(KotlinJvmBinaryClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        klass.loadClassAnnotations(new KotlinJvmBinaryClass.AnnotationVisitor() { // from class: org.jetbrains.kotlin.SpecialJvmAnnotations.isAnnotatedWithContainerMetaAnnotation.1
            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public void visitEnd() {
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Intrinsics.checkNotNullParameter(source, "source");
                if (!Intrinsics.areEqual(classId, JvmAbi.INSTANCE.getREPEATABLE_ANNOTATION_CONTAINER_META_ANNOTATION())) {
                    return null;
                }
                booleanRef.element = true;
                return null;
            }
        }, null);
        return booleanRef.element;
    }
}
