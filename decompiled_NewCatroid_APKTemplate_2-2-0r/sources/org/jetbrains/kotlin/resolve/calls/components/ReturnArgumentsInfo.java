package org.jetbrains.kotlin.resolve.calls.components;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;

/* JADX INFO: compiled from: ExternalComponents.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB-\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J9\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "", "nonErrorArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "lastExpression", "lastExpressionCoercedToUnit", "", "returnArgumentsExist", "(Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;ZZ)V", "getLastExpression", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getLastExpressionCoercedToUnit", "()Z", "getNonErrorArguments", "()Ljava/util/List;", "getReturnArgumentsExist", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReturnArgumentsInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ReturnArgumentsInfo empty = new ReturnArgumentsInfo(CollectionsKt.emptyList(), null, false, false);
    private final KotlinCallArgument lastExpression;
    private final boolean lastExpressionCoercedToUnit;
    private final List<KotlinCallArgument> nonErrorArguments;
    private final boolean returnArgumentsExist;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ReturnArgumentsInfo copy$default(ReturnArgumentsInfo returnArgumentsInfo, List list, KotlinCallArgument kotlinCallArgument, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = returnArgumentsInfo.nonErrorArguments;
        }
        if ((i & 2) != 0) {
            kotlinCallArgument = returnArgumentsInfo.lastExpression;
        }
        if ((i & 4) != 0) {
            z = returnArgumentsInfo.lastExpressionCoercedToUnit;
        }
        if ((i & 8) != 0) {
            z2 = returnArgumentsInfo.returnArgumentsExist;
        }
        return returnArgumentsInfo.copy(list, kotlinCallArgument, z, z2);
    }

    public final List<KotlinCallArgument> component1() {
        return this.nonErrorArguments;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KotlinCallArgument getLastExpression() {
        return this.lastExpression;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getLastExpressionCoercedToUnit() {
        return this.lastExpressionCoercedToUnit;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getReturnArgumentsExist() {
        return this.returnArgumentsExist;
    }

    public final ReturnArgumentsInfo copy(List<? extends KotlinCallArgument> nonErrorArguments, KotlinCallArgument lastExpression, boolean lastExpressionCoercedToUnit, boolean returnArgumentsExist) {
        Intrinsics.checkNotNullParameter(nonErrorArguments, "nonErrorArguments");
        return new ReturnArgumentsInfo(nonErrorArguments, lastExpression, lastExpressionCoercedToUnit, returnArgumentsExist);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReturnArgumentsInfo)) {
            return false;
        }
        ReturnArgumentsInfo returnArgumentsInfo = (ReturnArgumentsInfo) other;
        return Intrinsics.areEqual(this.nonErrorArguments, returnArgumentsInfo.nonErrorArguments) && Intrinsics.areEqual(this.lastExpression, returnArgumentsInfo.lastExpression) && this.lastExpressionCoercedToUnit == returnArgumentsInfo.lastExpressionCoercedToUnit && this.returnArgumentsExist == returnArgumentsInfo.returnArgumentsExist;
    }

    public int hashCode() {
        int iHashCode = this.nonErrorArguments.hashCode() * 31;
        KotlinCallArgument kotlinCallArgument = this.lastExpression;
        return ((((iHashCode + (kotlinCallArgument == null ? 0 : kotlinCallArgument.hashCode())) * 31) + Boolean.hashCode(this.lastExpressionCoercedToUnit)) * 31) + Boolean.hashCode(this.returnArgumentsExist);
    }

    public String toString() {
        return "ReturnArgumentsInfo(nonErrorArguments=" + this.nonErrorArguments + ", lastExpression=" + this.lastExpression + ", lastExpressionCoercedToUnit=" + this.lastExpressionCoercedToUnit + ", returnArgumentsExist=" + this.returnArgumentsExist + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ReturnArgumentsInfo(List<? extends KotlinCallArgument> nonErrorArguments, KotlinCallArgument kotlinCallArgument, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(nonErrorArguments, "nonErrorArguments");
        this.nonErrorArguments = nonErrorArguments;
        this.lastExpression = kotlinCallArgument;
        this.lastExpressionCoercedToUnit = z;
        this.returnArgumentsExist = z2;
    }

    public final List<KotlinCallArgument> getNonErrorArguments() {
        return this.nonErrorArguments;
    }

    public final KotlinCallArgument getLastExpression() {
        return this.lastExpression;
    }

    public final boolean getLastExpressionCoercedToUnit() {
        return this.lastExpressionCoercedToUnit;
    }

    public final boolean getReturnArgumentsExist() {
        return this.returnArgumentsExist;
    }

    /* JADX INFO: compiled from: ExternalComponents.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo$Companion;", "", "()V", "empty", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "getEmpty", "()Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReturnArgumentsInfo getEmpty() {
            return ReturnArgumentsInfo.empty;
        }
    }
}
