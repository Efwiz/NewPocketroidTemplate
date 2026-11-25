package org.jetbrains.kotlin.load.kotlin;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0003BG\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00010\u0005\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\nR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR&\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AnnotationsContainerWithConstants;", "A", "C", "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$AnnotationsContainer;", "memberAnnotations", "", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "", "propertyConstants", "annotationParametersDefaultValues", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getAnnotationParametersDefaultValues", "()Ljava/util/Map;", "getMemberAnnotations", "getPropertyConstants", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsContainerWithConstants<A, C> extends AbstractBinaryClassAnnotationLoader.AnnotationsContainer<A> {
    private final Map<MemberSignature, C> annotationParametersDefaultValues;
    private final Map<MemberSignature, List<A>> memberAnnotations;
    private final Map<MemberSignature, C> propertyConstants;

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader.AnnotationsContainer
    public Map<MemberSignature, List<A>> getMemberAnnotations() {
        return this.memberAnnotations;
    }

    public final Map<MemberSignature, C> getPropertyConstants() {
        return this.propertyConstants;
    }

    public final Map<MemberSignature, C> getAnnotationParametersDefaultValues() {
        return this.annotationParametersDefaultValues;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationsContainerWithConstants(Map<MemberSignature, ? extends List<? extends A>> memberAnnotations, Map<MemberSignature, ? extends C> propertyConstants, Map<MemberSignature, ? extends C> annotationParametersDefaultValues) {
        Intrinsics.checkNotNullParameter(memberAnnotations, "memberAnnotations");
        Intrinsics.checkNotNullParameter(propertyConstants, "propertyConstants");
        Intrinsics.checkNotNullParameter(annotationParametersDefaultValues, "annotationParametersDefaultValues");
        this.memberAnnotations = memberAnnotations;
        this.propertyConstants = propertyConstants;
        this.annotationParametersDefaultValues = annotationParametersDefaultValues;
    }
}
