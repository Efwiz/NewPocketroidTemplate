package org.jetbrains.kotlin.fir.resolve.substitution;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ConeSubstitutor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0001¨\u0006\u0004"}, d2 = {"substituteOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", ModuleXmlParser.TYPE, "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeSubstitutorKt {
    public static final ConeKotlinType substituteOrNull(ConeSubstitutor coneSubstitutor, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneSubstitutor, "<this>");
        if (coneKotlinType != null) {
            return coneSubstitutor.substituteOrNull(coneKotlinType);
        }
        return null;
    }
}
