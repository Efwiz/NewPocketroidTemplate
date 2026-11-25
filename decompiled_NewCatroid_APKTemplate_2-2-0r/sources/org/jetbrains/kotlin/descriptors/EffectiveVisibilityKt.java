package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: EffectiveVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0002¨\u0006\t"}, d2 = {"containerRelation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "first", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "second", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "createTypeCheckerContext", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EffectiveVisibilityKt {
    public static final EffectiveVisibility.Permissiveness containerRelation(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2, TypeCheckerProviderContext typeCheckerContextProvider) {
        Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
        if (typeConstructorMarker == null || typeConstructorMarker2 == null) {
            return EffectiveVisibility.Permissiveness.UNKNOWN;
        }
        if (Intrinsics.areEqual(typeConstructorMarker, typeConstructorMarker2)) {
            return EffectiveVisibility.Permissiveness.SAME;
        }
        if (AbstractTypeChecker.INSTANCE.isSubtypeOfClass(createTypeCheckerContext(typeCheckerContextProvider), typeConstructorMarker, typeConstructorMarker2)) {
            return EffectiveVisibility.Permissiveness.LESS;
        }
        return AbstractTypeChecker.INSTANCE.isSubtypeOfClass(createTypeCheckerContext(typeCheckerContextProvider), typeConstructorMarker2, typeConstructorMarker) ? EffectiveVisibility.Permissiveness.MORE : EffectiveVisibility.Permissiveness.UNKNOWN;
    }

    private static final TypeCheckerState createTypeCheckerContext(TypeCheckerProviderContext typeCheckerProviderContext) {
        return typeCheckerProviderContext.newTypeCheckerState(false, true);
    }
}
