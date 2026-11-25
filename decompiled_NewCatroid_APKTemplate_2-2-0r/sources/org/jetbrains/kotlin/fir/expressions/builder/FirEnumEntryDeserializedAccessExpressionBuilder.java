package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirEnumEntryDeserializedAccessExpressionImpl;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirEnumEntryDeserializedAccessExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010$\u001a\u00020%H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R.\u0010\u0017\u001a\u0004\u0018\u00010\u00162\b\u0010\u0015\u001a\u0004\u0018\u00010\u00168V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u0018\u0010\u0003\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR*\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0015\u001a\u00020\u001d8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u001f\u0010\u0003\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirEnumEntryDeserializedAccessExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "setEnumClassId", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "enumEntryName", "Lorg/jetbrains/kotlin/name/Name;", "getEnumEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "setEnumEntryName", "(Lorg/jetbrains/kotlin/name/Name;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/KtSourceElement;", FirebaseAnalytics.Param.SOURCE, "getSource$annotations", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeRef", "getTypeRef$annotations", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEnumEntryDeserializedAccessExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    public ClassId enumClassId;
    public Name enumEntryName;

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'source' has no impact for FirEnumEntryDeserializedAccessExpressionBuilder")
    public static /* synthetic */ void getSource$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirEnumEntryDeserializedAccessExpressionBuilder")
    public static /* synthetic */ void getTypeRef$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final ClassId getEnumClassId() {
        ClassId classId = this.enumClassId;
        if (classId != null) {
            return classId;
        }
        Intrinsics.throwUninitializedPropertyAccessException("enumClassId");
        return null;
    }

    public final void setEnumClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "<set-?>");
        this.enumClassId = classId;
    }

    public final Name getEnumEntryName() {
        Name name2 = this.enumEntryName;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("enumEntryName");
        return null;
    }

    public final void setEnumEntryName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.enumEntryName = name2;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirEnumEntryDeserializedAccessExpression build() {
        return new FirEnumEntryDeserializedAccessExpressionImpl(FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getEnumClassId(), getEnumEntryName(), null);
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ KtSourceElement getSource() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ void setSource(KtSourceElement ktSourceElement) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ FirTypeRef getTypeRef() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }
}
