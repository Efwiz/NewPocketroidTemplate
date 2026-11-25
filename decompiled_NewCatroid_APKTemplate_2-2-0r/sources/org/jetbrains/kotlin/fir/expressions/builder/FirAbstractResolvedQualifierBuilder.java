package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirAbstractResolvedQualifierBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u00108\u001a\u000209H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0006R\u0018\u0010\u0017\u001a\u00020\u0018X¦\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u0018X¦\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001a\"\u0004\b\u001f\u0010\u001cR\u0018\u0010 \u001a\u00020\u000eX¦\u000e¢\u0006\f\u001a\u0004\b!\u0010\u000f\"\u0004\b\"\u0010\u0011R\u001a\u0010#\u001a\u0004\u0018\u00010$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001e\u0010)\u001a\b\u0012\u0002\b\u0003\u0018\u00010*X¦\u000e¢\u0006\f\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0018\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\u0006R\u0018\u00102\u001a\u000203X¦\u000e¢\u0006\f\u001a\u0004\b4\u00105\"\u0004\b6\u00107ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006:À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAbstractResolvedQualifierBuilder;", "", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "setClassId", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "isFullyQualified", "", "()Z", "setFullyQualified", "(Z)V", "isNullableLHSForCallableReference", "setNullableLHSForCallableReference", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getNonFatalDiagnostics", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "relativeClassFqName", "getRelativeClassFqName", "setRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject", "setResolvedToCompanionObject", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirAbstractResolvedQualifierBuilder {
    FirResolvedQualifier build();

    List<FirAnnotation> getAnnotations();

    ClassId getClassId();

    List<ConeDiagnostic> getNonFatalDiagnostics();

    FqName getPackageFqName();

    FqName getRelativeClassFqName();

    boolean getResolvedToCompanionObject();

    KtSourceElement getSource();

    FirClassLikeSymbol<?> getSymbol();

    List<FirTypeProjection> getTypeArguments();

    FirTypeRef getTypeRef();

    /* JADX INFO: renamed from: isFullyQualified */
    boolean getIsFullyQualified();

    /* JADX INFO: renamed from: isNullableLHSForCallableReference */
    boolean getIsNullableLHSForCallableReference();

    void setClassId(ClassId classId);

    void setFullyQualified(boolean z);

    void setNullableLHSForCallableReference(boolean z);

    void setPackageFqName(FqName fqName);

    void setRelativeClassFqName(FqName fqName);

    void setResolvedToCompanionObject(boolean z);

    void setSource(KtSourceElement ktSourceElement);

    void setSymbol(FirClassLikeSymbol<?> firClassLikeSymbol);

    void setTypeRef(FirTypeRef firTypeRef);
}
