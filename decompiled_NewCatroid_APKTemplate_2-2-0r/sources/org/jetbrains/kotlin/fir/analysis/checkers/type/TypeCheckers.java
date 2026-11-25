package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: TypeCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002R1\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\nR$\u0010\r\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "", "()V", "allTypeRefCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "getAllTypeRefCheckers$checkers$annotations", "getAllTypeRefCheckers$checkers", "()Ljava/util/Set;", "allTypeRefCheckers$delegate", "Lkotlin/Lazy;", "typeRefCheckers", "getTypeRefCheckers", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeCheckers {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TypeCheckers EMPTY = new TypeCheckers() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers$Companion$EMPTY$1
    };
    private final Set<FirTypeChecker<FirTypeRef>> typeRefCheckers = SetsKt.emptySet();

    /* JADX INFO: renamed from: allTypeRefCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allTypeRefCheckers = LazyKt.lazy(new Function0<Set<? extends FirTypeChecker<? super FirTypeRef>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers$allTypeRefCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirTypeChecker<? super FirTypeRef>> invoke() {
            return this.this$0.getTypeRefCheckers();
        }
    });

    @CheckersComponentInternal
    public static /* synthetic */ void getAllTypeRefCheckers$checkers$annotations() {
    }

    /* JADX INFO: compiled from: TypeCheckers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TypeCheckers getEMPTY() {
            return TypeCheckers.EMPTY;
        }
    }

    public Set<FirTypeChecker<FirTypeRef>> getTypeRefCheckers() {
        return this.typeRefCheckers;
    }

    public final Set<FirTypeChecker<FirTypeRef>> getAllTypeRefCheckers$checkers() {
        return (Set) this.allTypeRefCheckers.getValue();
    }
}
