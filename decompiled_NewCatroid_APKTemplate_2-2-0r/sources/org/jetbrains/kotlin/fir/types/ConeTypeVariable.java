package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "name", "", "originalTypeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)V", "defaultType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableType;", "getDefaultType", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableType;", "typeConstructor", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "getTypeConstructor", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "toString", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeTypeVariable implements TypeVariableMarker {
    private final ConeTypeVariableType defaultType;
    private final ConeTypeVariableTypeConstructor typeConstructor;

    public ConeTypeVariable(String name2, TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(name2, "name");
        ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor = new ConeTypeVariableTypeConstructor(name2, typeParameterMarker);
        this.typeConstructor = coneTypeVariableTypeConstructor;
        this.defaultType = new ConeTypeVariableType(ConeNullability.NOT_NULL, coneTypeVariableTypeConstructor, null, 4, null);
    }

    public /* synthetic */ ConeTypeVariable(String str, TypeParameterMarker typeParameterMarker, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : typeParameterMarker);
    }

    public final ConeTypeVariableTypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    public final ConeTypeVariableType getDefaultType() {
        return this.defaultType;
    }

    public String toString() {
        return this.defaultType.toString();
    }
}
