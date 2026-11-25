package org.jetbrains.kotlin.fir.types.jvm;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaType;

/* JADX INFO: compiled from: FirJavaTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRefBuilder;", "", "()V", "annotationBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotationBuilder", "()Lkotlin/jvm/functions/Function0;", "setAnnotationBuilder", "(Lkotlin/jvm/functions/Function0;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "setType", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRef;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaTypeRefBuilder {
    public Function0<? extends List<? extends FirAnnotation>> annotationBuilder;
    public JavaType type;

    public final Function0<List<FirAnnotation>> getAnnotationBuilder() {
        Function0 function0 = this.annotationBuilder;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationBuilder");
        return null;
    }

    public final void setAnnotationBuilder(Function0<? extends List<? extends FirAnnotation>> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.annotationBuilder = function0;
    }

    public final JavaType getType() {
        JavaType javaType = this.type;
        if (javaType != null) {
            return javaType;
        }
        Intrinsics.throwUninitializedPropertyAccessException(ModuleXmlParser.TYPE);
        return null;
    }

    public final void setType(JavaType javaType) {
        Intrinsics.checkNotNullParameter(javaType, "<set-?>");
        this.type = javaType;
    }

    public final FirJavaTypeRef build() {
        return new FirJavaTypeRef(getType(), getAnnotationBuilder());
    }
}
