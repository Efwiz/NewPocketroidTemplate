package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: SupertypeLoopChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\rJR\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\n0\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\f0\tH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "", "findLoopsInSupertypesAndDisconnect", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "currentTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "superTypes", "neighbors", "Lkotlin/Function1;", "", "reportLoop", "", "EMPTY", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SupertypeLoopChecker {
    Collection<KotlinType> findLoopsInSupertypesAndDisconnect(TypeConstructor currentTypeConstructor, Collection<? extends KotlinType> superTypes, Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> neighbors, Function1<? super KotlinType, Unit> reportLoop);

    /* JADX INFO: compiled from: SupertypeLoopChecker.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JR\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\nH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker$EMPTY;", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "()V", "findLoopsInSupertypesAndDisconnect", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "currentTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "superTypes", "neighbors", "Lkotlin/Function1;", "", "reportLoop", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EMPTY implements SupertypeLoopChecker {
        public static final EMPTY INSTANCE = new EMPTY();

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.descriptors.SupertypeLoopChecker
        public Collection<KotlinType> findLoopsInSupertypesAndDisconnect(TypeConstructor currentTypeConstructor, Collection<? extends KotlinType> superTypes, Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> neighbors, Function1<? super KotlinType, Unit> reportLoop) {
            Intrinsics.checkNotNullParameter(currentTypeConstructor, "currentTypeConstructor");
            Intrinsics.checkNotNullParameter(superTypes, "superTypes");
            Intrinsics.checkNotNullParameter(neighbors, "neighbors");
            Intrinsics.checkNotNullParameter(reportLoop, "reportLoop");
            return superTypes;
        }

        private EMPTY() {
        }
    }
}
