package org.jetbrains.kotlin.fir.declarations.impl;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirDefaultPropertyBackingField.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyBackingField;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirBackingFieldImpl;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isVar", "", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;ZLorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultPropertyBackingField extends FirBackingFieldImpl {
    public /* synthetic */ FirDefaultPropertyBackingField(FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, KtSourceElement ktSourceElement, List list, FirTypeRef firTypeRef, boolean z, FirPropertySymbol firPropertySymbol, FirDeclarationStatus firDeclarationStatus, FirResolvePhase firResolvePhase, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firModuleData, firDeclarationOrigin, ktSourceElement, list, firTypeRef, z, firPropertySymbol, firDeclarationStatus, (i & 256) != 0 ? FirResolvePhase.RAW_FIR : firResolvePhase);
    }

    public FirDefaultPropertyBackingField(FirModuleData moduleData, FirDeclarationOrigin origin, KtSourceElement ktSourceElement, List<FirAnnotation> annotations, FirTypeRef returnTypeRef, boolean z, FirPropertySymbol propertySymbol, FirDeclarationStatus status, FirResolvePhase resolvePhase) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        FirDeclarationAttributes firDeclarationAttributes = new FirDeclarationAttributes();
        UnresolvedDeprecationProvider unresolvedDeprecationProvider = UnresolvedDeprecationProvider.INSTANCE;
        UnresolvedDeprecationProvider unresolvedDeprecationProvider2 = unresolvedDeprecationProvider;
        FirReceiverParameter firReceiverParameter = null;
        DeserializedContainerSource deserializedContainerSource = null;
        super(ktSourceElement, resolvePhase, moduleData, origin, firDeclarationAttributes, returnTypeRef, firReceiverParameter, unresolvedDeprecationProvider2, deserializedContainerSource, null, MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI(), StandardNames.BACKING_FIELD, null, z, !z, null, null, null, new FirBackingFieldSymbol(new CallableId(StandardNames.BACKING_FIELD, (FqName) null, 2, (DefaultConstructorMarker) null)), propertySymbol, null, FirBuilderDslKt.toMutableOrEmpty(annotations), new ArrayList(), status, null);
    }
}
