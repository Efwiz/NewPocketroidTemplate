package org.jetbrains.kotlin.incremental.components;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LookupLocation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/Position;", "Ljava/io/Serializable;", Constant.LINE, "", "column", "(II)V", "getColumn", "()I", "getLine", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Position implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Position NO_POSITION = new Position(-1, -1);
    private final int column;
    private final int line;

    public static /* synthetic */ Position copy$default(Position position, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = position.line;
        }
        if ((i3 & 2) != 0) {
            i2 = position.column;
        }
        return position.copy(i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getColumn() {
        return this.column;
    }

    public final Position copy(int line, int column) {
        return new Position(line, column);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position position = (Position) other;
        return this.line == position.line && this.column == position.column;
    }

    public int hashCode() {
        return (Integer.hashCode(this.line) * 31) + Integer.hashCode(this.column);
    }

    public String toString() {
        return "Position(line=" + this.line + ", column=" + this.column + ')';
    }

    /* JADX INFO: compiled from: LookupLocation.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/Position$Companion;", "", "()V", "NO_POSITION", "Lorg/jetbrains/kotlin/incremental/components/Position;", "getNO_POSITION", "()Lorg/jetbrains/kotlin/incremental/components/Position;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Position getNO_POSITION() {
            return Position.NO_POSITION;
        }
    }

    public Position(int i, int i2) {
        this.line = i;
        this.column = i2;
    }

    public final int getColumn() {
        return this.column;
    }

    public final int getLine() {
        return this.line;
    }
}
