package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeCheckerState;

/* JADX INFO: compiled from: ClassicTypeCheckerState.kt */
/* JADX INFO: loaded from: classes6.dex */
@ClassicTypeCheckerStateInternals
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/ClassicTypeCheckerState;", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "isErrorTypeEqualsToAnything", "", "isStubTypeEqualsToAnything", "typeSystemContext", "Lorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(ZZLorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext;Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ClassicTypeCheckerState extends TypeCheckerState {
    public /* synthetic */ ClassicTypeCheckerState(boolean z, boolean z2, SimpleClassicTypeSystemContext simpleClassicTypeSystemContext, KotlinTypePreparator.Default r10, KotlinTypeRefiner.Default r11, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? true : z2, (i & 4) != 0 ? SimpleClassicTypeSystemContext.INSTANCE : simpleClassicTypeSystemContext, (i & 8) != 0 ? KotlinTypePreparator.Default.INSTANCE : r10, (i & 16) != 0 ? KotlinTypeRefiner.Default.INSTANCE : r11);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTypeCheckerState(boolean z, boolean z2, ClassicTypeSystemContext typeSystemContext, KotlinTypePreparator kotlinTypePreparator, KotlinTypeRefiner kotlinTypeRefiner) {
        super(z, z2, true, typeSystemContext, kotlinTypePreparator, kotlinTypeRefiner);
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
    }
}
