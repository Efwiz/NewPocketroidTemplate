package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirInaccessibleReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.references.builder.FirImplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirReceivers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\n"}, d2 = {"receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "contextReceiverNumber", "", "inaccessibleReceiver", "", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirReceiversKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final FirExpression receiverExpression(FirBasedSymbol<?> firBasedSymbol, ConeKotlinType coneKotlinType, int i, boolean z) {
        FirImplicitThisReferenceBuilder firImplicitThisReferenceBuilder = new FirImplicitThisReferenceBuilder();
        firImplicitThisReferenceBuilder.setBoundSymbol(firBasedSymbol);
        firImplicitThisReferenceBuilder.setContextReceiverNumber(i);
        FirThisReference firThisReferenceBuild = firImplicitThisReferenceBuilder.build();
        FirResolvedTypeRef firResolvedTypeRef$default = TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinType, null, null, 3, null);
        if (!z) {
            FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
            firThisReceiverExpressionBuilder.setCalleeReference(firThisReferenceBuild);
            firThisReceiverExpressionBuilder.setTypeRef(firResolvedTypeRef$default);
            firThisReceiverExpressionBuilder.setImplicit(true);
            return firThisReceiverExpressionBuilder.mo6809build();
        }
        if (!z) {
            throw new NoWhenBranchMatchedException();
        }
        FirInaccessibleReceiverExpressionBuilder firInaccessibleReceiverExpressionBuilder = new FirInaccessibleReceiverExpressionBuilder();
        firInaccessibleReceiverExpressionBuilder.setCalleeReference(firThisReferenceBuild);
        firInaccessibleReceiverExpressionBuilder.setTypeRef(firResolvedTypeRef$default);
        return firInaccessibleReceiverExpressionBuilder.mo6809build();
    }
}
