package org.jetbrains.kotlin.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirGeneration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u001aF\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011\u001a \u0010\u0013\u001a\u00020\u0014*\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u000f¨\u0006\u0017"}, d2 = {"generateExplicitReceiverTemporaryVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "generateTemporaryVariable", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "initializer", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "extractedAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "toQualifiedAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "fakeSource", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirGenerationKt {
    public static /* synthetic */ FirQualifiedAccessExpression toQualifiedAccess$default(FirVariable firVariable, KtSourceElement ktSourceElement, FirTypeRef firTypeRef, int i, Object obj) {
        if ((i & 1) != 0) {
            KtSourceElement source = firVariable.getSource();
            ktSourceElement = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null) : null;
        }
        if ((i & 2) != 0) {
            firTypeRef = firVariable.getReturnTypeRef();
        }
        return toQualifiedAccess(firVariable, ktSourceElement, firTypeRef);
    }

    public static /* synthetic */ FirProperty generateTemporaryVariable$default(FirModuleData firModuleData, KtSourceElement ktSourceElement, Name name2, FirExpression firExpression, FirTypeRef firTypeRef, Collection collection, int i, Object obj) {
        return generateTemporaryVariable(firModuleData, ktSourceElement, name2, firExpression, (i & 16) != 0 ? null : firTypeRef, (i & 32) != 0 ? null : collection);
    }

    public static final FirProperty generateExplicitReceiverTemporaryVariable(FirSession session, FirExpression expression, KtSourceElement ktSourceElement) {
        FirExpression explicitReceiver;
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(expression, "expression");
        FirQualifiedAccessExpression firQualifiedAccessExpression = expression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) expression : null;
        if (firQualifiedAccessExpression == null || (explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver()) == null) {
            return null;
        }
        if ((explicitReceiver instanceof FirResolvedQualifier) || (explicitReceiver instanceof FirThisReceiverExpression) || ((explicitReceiver instanceof FirQualifiedAccessExpression) && (((FirQualifiedAccessExpression) explicitReceiver).getCalleeReference() instanceof FirSuperReference))) {
            explicitReceiver = null;
        }
        if (explicitReceiver == null) {
            return null;
        }
        FirProperty firPropertyGenerateTemporaryVariable$default = generateTemporaryVariable$default(FirModuleDataKt.getModuleData(session), ktSourceElement, SpecialNames.RECEIVER, explicitReceiver, UtilsKt.copyWithNewSource(explicitReceiver.getTypeRef(), ktSourceElement), null, 32, null);
        FirProperty firProperty = firPropertyGenerateTemporaryVariable$default;
        KtSourceElement source = explicitReceiver.getSource();
        FirQualifiedAccessExpression qualifiedAccess$default = toQualifiedAccess$default(firProperty, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null, null, 2, null);
        FirQualifiedAccessExpression firQualifiedAccessExpression2 = (FirQualifiedAccessExpression) expression;
        if (Intrinsics.areEqual(firQualifiedAccessExpression2.getExplicitReceiver(), firQualifiedAccessExpression2.getDispatchReceiver())) {
            firQualifiedAccessExpression2.replaceDispatchReceiver(qualifiedAccess$default);
        } else {
            firQualifiedAccessExpression2.replaceExtensionReceiver(qualifiedAccess$default);
        }
        firQualifiedAccessExpression2.replaceExplicitReceiver(qualifiedAccess$default);
        return firPropertyGenerateTemporaryVariable$default;
    }

    public static final FirQualifiedAccessExpression toQualifiedAccess(FirVariable firVariable, KtSourceElement ktSourceElement, FirTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(firVariable, "<this>");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElement);
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setSource(ktSourceElement);
        firResolvedNamedReferenceBuilder.setName(firVariable.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firVariable.getSymbol());
        firPropertyAccessExpressionBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        firPropertyAccessExpressionBuilder.setTypeRef(typeRef);
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    public static final FirProperty generateTemporaryVariable(FirModuleData moduleData, KtSourceElement ktSourceElement, Name name2, FirExpression initializer, FirTypeRef firTypeRef, Collection<? extends FirAnnotation> collection) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(ktSourceElement);
        firPropertyBuilder.setModuleData(moduleData);
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        if (firTypeRef == null) {
            firTypeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
        }
        firPropertyBuilder.setReturnTypeRef(firTypeRef);
        firPropertyBuilder.setName(name2);
        firPropertyBuilder.setInitializer(initializer);
        firPropertyBuilder.setSymbol(new FirPropertySymbol(name2));
        firPropertyBuilder.setVar(false);
        firPropertyBuilder.setLocal(true);
        firPropertyBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL, EffectiveVisibility.Local.INSTANCE));
        if (collection != null) {
            firPropertyBuilder.getAnnotations().addAll(collection);
        }
        return firPropertyBuilder.mo6809build();
    }
}
