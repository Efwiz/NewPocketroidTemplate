package org.jetbrains.kotlin.fir.resolve.substitution;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ConeSubstitutor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "()V", "substituteOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "substituteOrSelf", "Empty", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeSubstitutor implements TypeSubstitutorMarker {
    public abstract ConeKotlinType substituteOrNull(ConeKotlinType type);

    public ConeKotlinType substituteOrSelf(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ConeKotlinType coneKotlinTypeSubstituteOrNull = substituteOrNull(type);
        return coneKotlinTypeSubstituteOrNull == null ? type : coneKotlinTypeSubstituteOrNull;
    }

    /* JADX INFO: compiled from: ConeSubstitutor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor$Empty;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "()V", "substituteOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "substituteOrSelf", "toString", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends ConeSubstitutor {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
        public ConeKotlinType substituteOrNull(ConeKotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
        public ConeKotlinType substituteOrSelf(ConeKotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return type;
        }

        private Empty() {
        }

        public String toString() {
            return "Empty";
        }
    }
}
