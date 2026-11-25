package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallContainer;", "", "result", "Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "(Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;)V", "getResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialCallContainer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PartialCallContainer empty = new PartialCallContainer(null);
    private final PartialCallResolutionResult result;

    /* JADX INFO: compiled from: ResolutionAtoms.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallContainer$Companion;", "", "()V", "empty", "Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallContainer;", "getEmpty", "()Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallContainer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PartialCallContainer getEmpty() {
            return PartialCallContainer.empty;
        }
    }

    public PartialCallContainer(PartialCallResolutionResult partialCallResolutionResult) {
        this.result = partialCallResolutionResult;
    }

    public final PartialCallResolutionResult getResult() {
        return this.result;
    }
}
