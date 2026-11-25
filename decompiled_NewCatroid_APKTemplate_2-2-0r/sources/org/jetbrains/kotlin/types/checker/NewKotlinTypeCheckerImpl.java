package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0018\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0016J\u001a\u0010\u000f\u001a\u00020\u0010*\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0018J\u001a\u0010\u0014\u001a\u00020\u0010*\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeCheckerImpl;", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "(Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;)V", "getKotlinTypePreparator", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "overridingUtil", "Lorg/jetbrains/kotlin/resolve/OverridingUtil;", "getOverridingUtil", "()Lorg/jetbrains/kotlin/resolve/OverridingUtil;", "equalTypes", "", "a", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "isSubtypeOf", "subtype", "supertype", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "subType", "superType", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewKotlinTypeCheckerImpl implements NewKotlinTypeChecker {
    private final KotlinTypePreparator kotlinTypePreparator;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final OverridingUtil overridingUtil;

    public NewKotlinTypeCheckerImpl(KotlinTypeRefiner kotlinTypeRefiner, KotlinTypePreparator kotlinTypePreparator) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.kotlinTypePreparator = kotlinTypePreparator;
        OverridingUtil overridingUtilCreateWithTypeRefiner = OverridingUtil.createWithTypeRefiner(getKotlinTypeRefiner());
        Intrinsics.checkNotNullExpressionValue(overridingUtilCreateWithTypeRefiner, "createWithTypeRefiner(...)");
        this.overridingUtil = overridingUtilCreateWithTypeRefiner;
    }

    @Override // org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker
    public KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    public /* synthetic */ NewKotlinTypeCheckerImpl(KotlinTypeRefiner kotlinTypeRefiner, KotlinTypePreparator.Default r2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinTypeRefiner, (i & 2) != 0 ? KotlinTypePreparator.Default.INSTANCE : r2);
    }

    @Override // org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker
    public KotlinTypePreparator getKotlinTypePreparator() {
        return this.kotlinTypePreparator;
    }

    @Override // org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker
    public OverridingUtil getOverridingUtil() {
        return this.overridingUtil;
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker
    public boolean isSubtypeOf(KotlinType subtype, KotlinType supertype) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        return isSubtypeOf(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(true, false, null, getKotlinTypePreparator(), getKotlinTypeRefiner(), 6, null), subtype.unwrap(), supertype.unwrap());
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker
    public boolean equalTypes(KotlinType a, KotlinType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return equalTypes(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(false, false, null, getKotlinTypePreparator(), getKotlinTypeRefiner(), 6, null), a.unwrap(), b.unwrap());
    }

    public final boolean equalTypes(TypeCheckerState typeCheckerState, UnwrappedType a, UnwrappedType b) {
        Intrinsics.checkNotNullParameter(typeCheckerState, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerState, a, b);
    }

    public final boolean isSubtypeOf(TypeCheckerState typeCheckerState, UnwrappedType subType, UnwrappedType superType) {
        Intrinsics.checkNotNullParameter(typeCheckerState, "<this>");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerState, (KotlinTypeMarker) subType, (KotlinTypeMarker) superType, false, 8, (Object) null);
    }
}
