package org.jetbrains.kotlin.js.patterns.typePredicates;

import kotlin.Metadata;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: compiled from: typePredicates.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"CHAR_SEQUENCE", "Lorg/jetbrains/kotlin/js/patterns/typePredicates/TypePredicate;", "getCHAR_SEQUENCE", "()Lorg/jetbrains/kotlin/js/patterns/typePredicates/TypePredicate;", "COMPARABLE", "getCOMPARABLE", "KOTLIN", "Lorg/jetbrains/kotlin/js/patterns/typePredicates/TypePredicateImpl;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypePredicatesKt {
    private static final TypePredicate CHAR_SEQUENCE;
    private static final TypePredicate COMPARABLE;
    private static final TypePredicateImpl KOTLIN;

    static {
        TypePredicateImpl typePredicateImpl = new TypePredicateImpl(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        KOTLIN = typePredicateImpl;
        COMPARABLE = typePredicateImpl.inner("Comparable");
        CHAR_SEQUENCE = typePredicateImpl.inner("CharSequence");
    }

    public static final TypePredicate getCOMPARABLE() {
        return COMPARABLE;
    }

    public static final TypePredicate getCHAR_SEQUENCE() {
        return CHAR_SEQUENCE;
    }
}
