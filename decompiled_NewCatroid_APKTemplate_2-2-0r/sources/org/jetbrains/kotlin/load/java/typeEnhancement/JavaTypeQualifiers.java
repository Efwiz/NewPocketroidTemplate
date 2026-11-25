package org.jetbrains.kotlin.load.java.typeEnhancement;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: typeQualifiers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "", "nullability", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "mutability", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/MutabilityQualifier;", "definitelyNotNull", "", "isNullabilityQualifierForWarning", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;Lorg/jetbrains/kotlin/load/java/typeEnhancement/MutabilityQualifier;ZZ)V", "getDefinitelyNotNull", "()Z", "getMutability", "()Lorg/jetbrains/kotlin/load/java/typeEnhancement/MutabilityQualifier;", "getNullability", "()Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeQualifiers {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JavaTypeQualifiers NONE = new JavaTypeQualifiers(null, null, false, false, 8, null);
    private final boolean definitelyNotNull;
    private final boolean isNullabilityQualifierForWarning;
    private final MutabilityQualifier mutability;
    private final NullabilityQualifier nullability;

    public JavaTypeQualifiers(NullabilityQualifier nullabilityQualifier, MutabilityQualifier mutabilityQualifier, boolean z, boolean z2) {
        this.nullability = nullabilityQualifier;
        this.mutability = mutabilityQualifier;
        this.definitelyNotNull = z;
        this.isNullabilityQualifierForWarning = z2;
    }

    public /* synthetic */ JavaTypeQualifiers(NullabilityQualifier nullabilityQualifier, MutabilityQualifier mutabilityQualifier, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(nullabilityQualifier, mutabilityQualifier, z, (i & 8) != 0 ? false : z2);
    }

    public final NullabilityQualifier getNullability() {
        return this.nullability;
    }

    public final MutabilityQualifier getMutability() {
        return this.mutability;
    }

    public final boolean getDefinitelyNotNull() {
        return this.definitelyNotNull;
    }

    /* JADX INFO: renamed from: isNullabilityQualifierForWarning, reason: from getter */
    public final boolean getIsNullabilityQualifierForWarning() {
        return this.isNullabilityQualifierForWarning;
    }

    /* JADX INFO: compiled from: typeQualifiers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers$Companion;", "", "()V", "NONE", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "getNONE", "()Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaTypeQualifiers getNONE() {
            return JavaTypeQualifiers.NONE;
        }
    }
}
