package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: KotlinIrLinker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "", "onDemand", "", "needBodies", "explicitlyExported", "theWholeWorld", "inlineBodies", "(Ljava/lang/String;IZZZZZ)V", "getExplicitlyExported", "()Z", "getInlineBodies", "getNeedBodies", "getOnDemand", "getTheWholeWorld", "ON_DEMAND", "ONLY_REFERENCED", "ALL", "EXPLICITLY_EXPORTED", "ONLY_DECLARATION_HEADERS", "WITH_INLINE_BODIES", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum DeserializationStrategy {
    ON_DEMAND(true, false, false, false, false),
    ONLY_REFERENCED(false, true, false, false, true),
    ALL(false, true, true, true, true),
    EXPLICITLY_EXPORTED(false, true, true, false, true),
    ONLY_DECLARATION_HEADERS(false, false, false, false, false),
    WITH_INLINE_BODIES(false, false, false, false, true);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean explicitlyExported;
    private final boolean inlineBodies;
    private final boolean needBodies;
    private final boolean onDemand;
    private final boolean theWholeWorld;

    public static EnumEntries<DeserializationStrategy> getEntries() {
        return $ENTRIES;
    }

    DeserializationStrategy(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.onDemand = z;
        this.needBodies = z2;
        this.explicitlyExported = z3;
        this.theWholeWorld = z4;
        this.inlineBodies = z5;
    }

    public final boolean getOnDemand() {
        return this.onDemand;
    }

    public final boolean getNeedBodies() {
        return this.needBodies;
    }

    public final boolean getExplicitlyExported() {
        return this.explicitlyExported;
    }

    public final boolean getTheWholeWorld() {
        return this.theWholeWorld;
    }

    public final boolean getInlineBodies() {
        return this.inlineBodies;
    }
}
