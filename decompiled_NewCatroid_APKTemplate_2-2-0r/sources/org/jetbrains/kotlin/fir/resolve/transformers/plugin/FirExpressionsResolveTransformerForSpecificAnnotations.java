package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguouslyResolvedAnnotationArgument;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAnnotationArgumentsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J(\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirExpressionsResolveTransformerForSpecificAnnotations;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirExpressionsResolveTransformerForAnnotations;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "generatePropertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "accessSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "resolveQualifiedAccessAndSelectCandidate", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "isUsedAsReceiver", "", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "resolveSpecialPropertyAccess", "originalAccess", "originalCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "originalResolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformQualifiedAccessExpression", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirExpressionsResolveTransformerForSpecificAnnotations extends AbstractFirExpressionsResolveTransformerForAnnotations {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExpressionsResolveTransformerForSpecificAnnotations(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (qualifiedAccessExpression instanceof FirPropertyAccessExpression) {
            FirReference calleeReference = qualifiedAccessExpression.getCalleeReference();
            if (calleeReference instanceof FirResolvedNamedReference) {
                FirResolvedNamedReference firResolvedNamedReference = (FirResolvedNamedReference) calleeReference;
                FirBasedSymbol<?> resolvedSymbol = firResolvedNamedReference.getResolvedSymbol();
                if (resolvedSymbol instanceof FirEnumEntrySymbol) {
                    Set set = FirAnnotationArgumentsResolveTransformerKt.classIdsToCheck;
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) resolvedSymbol);
                    if (CollectionsKt.contains(set, coneClassLikeLookupTagContainingClassLookupTag != null ? coneClassLikeLookupTagContainingClassLookupTag.getClassId() : null)) {
                        return resolveSpecialPropertyAccess((FirPropertyAccessExpression) qualifiedAccessExpression, firResolvedNamedReference, (FirEnumEntrySymbol) resolvedSymbol, data);
                    }
                }
            }
        }
        return super.transformQualifiedAccessExpression(qualifiedAccessExpression, data);
    }

    private final FirPropertyAccessExpression generatePropertyAccessExpression(FqName fqName, KtSourceElement accessSource) {
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        FirPropertyAccessExpression firPropertyAccessExpressionMo6809build = null;
        int i = 0;
        for (Name name2 : listPathSegments) {
            int i2 = i + 1;
            FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            Intrinsics.checkNotNull(name2);
            firSimpleNamedReferenceBuilder.setName(name2);
            firPropertyAccessExpressionBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            firPropertyAccessExpressionBuilder.setExplicitReceiver(firPropertyAccessExpressionMo6809build);
            if (i == CollectionsKt.getLastIndex(listPathSegments)) {
                firPropertyAccessExpressionBuilder.setSource(accessSource);
            }
            firPropertyAccessExpressionMo6809build = firPropertyAccessExpressionBuilder.mo6809build();
            i = i2;
        }
        if (firPropertyAccessExpressionMo6809build != null) {
            return firPropertyAccessExpressionMo6809build;
        }
        throw new IllegalStateException("Got an empty ClassId".toString());
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirExpressionsResolveTransformerForAnnotations, org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer
    protected FirStatement resolveQualifiedAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccessExpression, boolean isUsedAsReceiver, FirElement callSite) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(callSite, "callSite");
        return getComponents().getCallResolver().resolveOnlyEnumOrQualifierAccessAndSelectCandidate(qualifiedAccessExpression, isUsedAsReceiver);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003b  */
    private final FirStatement resolveSpecialPropertyAccess(FirPropertyAccessExpression originalAccess, FirResolvedNamedReference originalCalleeReference, FirEnumEntrySymbol originalResolvedSymbol, ResolutionMode data) {
        FqName packageFqName;
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(originalAccess.getSource());
        firPropertyAccessExpressionBuilder.getTypeArguments().addAll(originalAccess.getTypeArguments());
        FirExpression explicitReceiver = originalAccess.getExplicitReceiver();
        if (explicitReceiver instanceof FirResolvedQualifier) {
            FirResolvedQualifier firResolvedQualifier = (FirResolvedQualifier) explicitReceiver;
            ClassId classId = firResolvedQualifier.getClassId();
            if (classId != null) {
                packageFqName = firResolvedQualifier.getIsFullyQualified() ? classId.asSingleFqName() : classId.getRelativeClassName();
                if (packageFqName == null) {
                    packageFqName = firResolvedQualifier.getPackageFqName();
                }
            } else {
                packageFqName = firResolvedQualifier.getPackageFqName();
            }
            Intrinsics.checkNotNull(packageFqName);
            firPropertyAccessExpressionBuilder.setExplicitReceiver(generatePropertyAccessExpression(packageFqName, firResolvedQualifier.getSource()));
        }
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(originalCalleeReference.getSource());
        firSimpleNamedReferenceBuilder.setName(originalCalleeReference.getName());
        firPropertyAccessExpressionBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        FirStatement firStatementTransformQualifiedAccessExpression = super.transformQualifiedAccessExpression((FirQualifiedAccessExpression) firPropertyAccessExpressionBuilder.mo6809build(), data);
        if (firStatementTransformQualifiedAccessExpression instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) firStatementTransformQualifiedAccessExpression;
            FirBasedSymbol resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
            if (!Intrinsics.areEqual(originalResolvedSymbol, resolvedBaseSymbol$default)) {
                FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
                firErrorNamedReferenceBuilder.setSource(firQualifiedAccessExpression.getCalleeReference().getSource());
                firErrorNamedReferenceBuilder.setDiagnostic(new ConeAmbiguouslyResolvedAnnotationArgument(originalResolvedSymbol, resolvedBaseSymbol$default));
                firQualifiedAccessExpression.replaceCalleeReference(firErrorNamedReferenceBuilder.build());
            }
        }
        return firStatementTransformQualifiedAccessExpression;
    }
}
