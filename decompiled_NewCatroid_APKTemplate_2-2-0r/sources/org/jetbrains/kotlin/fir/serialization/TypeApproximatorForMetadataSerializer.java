package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.diagnostics.ConeIntermediateDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: serializationUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/TypeApproximatorForMetadataSerializer;", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "createErrorType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "debugName", "", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeApproximatorForMetadataSerializer extends AbstractTypeApproximator {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeApproximatorForMetadataSerializer(FirSession session) {
        super(TypeComponentsKt.getTypeContext(session), FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypeApproximator, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return new ConeErrorType(new ConeIntermediateDiagnostic(debugName), false, null, null, 14, null);
    }
}
