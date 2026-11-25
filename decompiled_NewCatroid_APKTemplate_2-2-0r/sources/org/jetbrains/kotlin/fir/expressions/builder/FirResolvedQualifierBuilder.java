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
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedQualifierImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirResolvedQualifierBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010>\u001a\u00020?H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR.\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\r\u0010\u0004\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\u00020\u001dX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!R*\u0010%\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u00138V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b&\u0010\u0004\u001a\u0004\b'\u0010\u0014\"\u0004\b(\u0010\u0016R\u001c\u0010)\u001a\u0004\u0018\u00010*X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R \u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u000100X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\b\u0012\u0004\u0012\u0002060\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\tR\u001a\u00108\u001a\u000209X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirResolvedQualifierBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirAbstractResolvedQualifierBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/name/ClassId;", "classId", "getClassId$annotations", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "setClassId", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "isFullyQualified", "", "()Z", "setFullyQualified", "(Z)V", "isNullableLHSForCallableReference", "setNullableLHSForCallableReference", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getNonFatalDiagnostics", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "relativeClassFqName", "getRelativeClassFqName", "setRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject$annotations", "getResolvedToCompanionObject", "setResolvedToCompanionObject", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedQualifierBuilder implements FirAnnotationContainerBuilder, FirAbstractResolvedQualifierBuilder, FirExpressionBuilder {
    private boolean isFullyQualified;
    private boolean isNullableLHSForCallableReference;
    public FqName packageFqName;
    private FqName relativeClassFqName;
    private KtSourceElement source;
    private FirClassLikeSymbol<?> symbol;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<ConeDiagnostic> nonFatalDiagnostics = new ArrayList();
    private final List<FirTypeProjection> typeArguments = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'classId' has no impact for FirResolvedQualifierBuilder")
    public static /* synthetic */ void getClassId$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'resolvedToCompanionObject' has no impact for FirResolvedQualifierBuilder")
    public static /* synthetic */ void getResolvedToCompanionObject$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public FqName getPackageFqName() {
        FqName fqName = this.packageFqName;
        if (fqName != null) {
            return fqName;
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageFqName");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public void setPackageFqName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqName = fqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public FqName getRelativeClassFqName() {
        return this.relativeClassFqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public void setRelativeClassFqName(FqName fqName) {
        this.relativeClassFqName = fqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public void setSymbol(FirClassLikeSymbol<?> firClassLikeSymbol) {
        this.symbol = firClassLikeSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    /* JADX INFO: renamed from: isNullableLHSForCallableReference, reason: from getter */
    public boolean getIsNullableLHSForCallableReference() {
        return this.isNullableLHSForCallableReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public void setNullableLHSForCallableReference(boolean z) {
        this.isNullableLHSForCallableReference = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    /* JADX INFO: renamed from: isFullyQualified, reason: from getter */
    public boolean getIsFullyQualified() {
        return this.isFullyQualified;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public void setFullyQualified(boolean z) {
        this.isFullyQualified = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public List<ConeDiagnostic> getNonFatalDiagnostics() {
        return this.nonFatalDiagnostics;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirResolvedQualifier build() {
        return new FirResolvedQualifierImpl(getSource(), getTypeRef(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getPackageFqName(), getRelativeClassFqName(), getSymbol(), getIsNullableLHSForCallableReference(), getIsFullyQualified(), FirBuilderDslKt.toMutableOrEmpty(getNonFatalDiagnostics()), FirBuilderDslKt.toMutableOrEmpty(getTypeArguments()), null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public /* synthetic */ ClassId getClassId() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public /* synthetic */ void setClassId(ClassId classId) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public /* synthetic */ boolean getResolvedToCompanionObject() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractResolvedQualifierBuilder
    public /* synthetic */ void setResolvedToCompanionObject(boolean z) {
        throw new IllegalStateException();
    }
}
