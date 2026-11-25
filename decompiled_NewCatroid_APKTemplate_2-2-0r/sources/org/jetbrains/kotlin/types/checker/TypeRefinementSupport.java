package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinTypeRefiner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0006\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u0082\u0001\u0003\t\n\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport;", "", "isEnabled", "", "(Z)V", "()Z", "Disabled", "Enabled", "EnabledUninitialized", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$Disabled;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$Enabled;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$EnabledUninitialized;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeRefinementSupport {
    private final boolean isEnabled;

    public /* synthetic */ TypeRefinementSupport(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    /* JADX INFO: compiled from: KotlinTypeRefiner.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$Disabled;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Disabled extends TypeRefinementSupport {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
            super(false, null);
        }
    }

    private TypeRefinementSupport(boolean z) {
        this.isEnabled = z;
    }

    /* JADX INFO: renamed from: isEnabled, reason: from getter */
    public final boolean getIsEnabled() {
        return this.isEnabled;
    }

    /* JADX INFO: compiled from: KotlinTypeRefiner.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$EnabledUninitialized;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnabledUninitialized extends TypeRefinementSupport {
        public static final EnabledUninitialized INSTANCE = new EnabledUninitialized();

        private EnabledUninitialized() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KotlinTypeRefiner.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport$Enabled;", "Lorg/jetbrains/kotlin/types/checker/TypeRefinementSupport;", "typeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "getTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Enabled extends TypeRefinementSupport {
        private final KotlinTypeRefiner typeRefiner;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Enabled(KotlinTypeRefiner typeRefiner) {
            super(true, null);
            Intrinsics.checkNotNullParameter(typeRefiner, "typeRefiner");
            this.typeRefiner = typeRefiner;
        }

        public final KotlinTypeRefiner getTypeRefiner() {
            return this.typeRefiner;
        }
    }
}
