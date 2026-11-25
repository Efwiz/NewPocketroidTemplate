package org.jetbrains.kotlin.fir.deserialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;

/* JADX INFO: compiled from: FirEnumEntryDeserializerAccessUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"toQualifiedPropertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "fir-deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirEnumEntryDeserializerAccessUtilKt {
    public static final FirPropertyAccessExpression toQualifiedPropertyAccessExpression(FirEnumEntryDeserializedAccessExpression firEnumEntryDeserializedAccessExpression, FirSession session) {
        Object next;
        FirErrorNamedReference firErrorNamedReferenceBuild;
        Intrinsics.checkNotNullParameter(firEnumEntryDeserializedAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        Iterator<T> it = FirSymbolProviderKt.getClassDeclaredPropertySymbols(FirSymbolProviderKt.getSymbolProvider(session), firEnumEntryDeserializedAccessExpression.getEnumClassId(), firEnumEntryDeserializedAccessExpression.getEnumEntryName()).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((FirVariableSymbol) next).getRawStatus().isStatic());
        FirVariableSymbol firVariableSymbol = (FirVariableSymbol) next;
        if (firVariableSymbol != null) {
            FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
            firResolvedNamedReferenceBuilder.setName(firEnumEntryDeserializedAccessExpression.getEnumEntryName());
            firResolvedNamedReferenceBuilder.setResolvedSymbol(firVariableSymbol);
            firErrorNamedReferenceBuild = firResolvedNamedReferenceBuilder.build();
        } else {
            FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
            firErrorNamedReferenceBuilder.setDiagnostic(new ConeSimpleDiagnostic("Strange deserialized enum value: " + firEnumEntryDeserializedAccessExpression.getEnumClassId() + '.' + firEnumEntryDeserializedAccessExpression.getEnumEntryName(), DiagnosticKind.Java));
            firErrorNamedReferenceBuild = firErrorNamedReferenceBuilder.build();
        }
        firPropertyAccessExpressionBuilder.setCalleeReference(firErrorNamedReferenceBuild);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(firEnumEntryDeserializedAccessExpression.getEnumClassId()), new ConeTypeProjection[0], false, null, 8, null));
        firPropertyAccessExpressionBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
        return firPropertyAccessExpressionBuilder.mo6809build();
    }
}
