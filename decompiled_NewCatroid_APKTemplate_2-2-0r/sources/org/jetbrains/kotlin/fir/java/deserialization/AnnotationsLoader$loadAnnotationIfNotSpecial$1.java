package org.jetbrains.kotlin.fir.java.deserialization;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AnnotationsLoader.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class AnnotationsLoader$loadAnnotationIfNotSpecial$1 extends FunctionReference implements Function2<ClassId, Name, FirPropertyAccessExpression> {
    AnnotationsLoader$loadAnnotationIfNotSpecial$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "toEnumEntryReferenceExpressionWithResolve(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "toEnumEntryReferenceExpressionWithResolve";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AnnotationsLoader.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final FirPropertyAccessExpression invoke(ClassId p0, Name p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((AnnotationsLoader) this.receiver).toEnumEntryReferenceExpressionWithResolve(p0, p1);
    }
}
