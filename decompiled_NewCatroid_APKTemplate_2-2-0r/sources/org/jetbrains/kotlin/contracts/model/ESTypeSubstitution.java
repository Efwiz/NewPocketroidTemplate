package org.jetbrains.kotlin.contracts.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;

/* JADX INFO: compiled from: Functor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESTypeSubstitution {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinBuiltIns builtIns;
    private final NewTypeSubstitutor substitutor;

    public ESTypeSubstitution(NewTypeSubstitutor substitutor, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.substitutor = substitutor;
        this.builtIns = builtIns;
    }

    public final NewTypeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    /* JADX INFO: compiled from: Functor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution$Companion;", "", "()V", "empty", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ESTypeSubstitution empty(KotlinBuiltIns builtIns) {
            Intrinsics.checkNotNullParameter(builtIns, "builtIns");
            return new ESTypeSubstitution(EmptySubstitutor.INSTANCE, builtIns);
        }
    }
}
