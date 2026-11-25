package org.jetbrains.kotlin.wasm.ir.source.location;

import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SourceLocation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "", "()V", "Companion", "Location", "NoLocation", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation$Location;", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation$NoLocation;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SourceLocation {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ SourceLocation(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: SourceLocation.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation$NoLocation;", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class NoLocation extends SourceLocation {
        public static final NoLocation INSTANCE = new NoLocation();

        private NoLocation() {
            super(null);
        }
    }

    private SourceLocation() {
    }

    /* JADX INFO: compiled from: SourceLocation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation$Location;", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "file", "", Constant.LINE, "", "column", "(Ljava/lang/String;II)V", "getColumn", "()I", "getFile", "()Ljava/lang/String;", "getLine", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Location extends SourceLocation {
        private final int column;
        private final String file;
        private final int line;

        public static /* synthetic */ Location copy$default(Location location, String str, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                str = location.file;
            }
            if ((i3 & 2) != 0) {
                i = location.line;
            }
            if ((i3 & 4) != 0) {
                i2 = location.column;
            }
            return location.copy(str, i, i2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFile() {
            return this.file;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getLine() {
            return this.line;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getColumn() {
            return this.column;
        }

        public final Location copy(String file, int line, int column) {
            Intrinsics.checkNotNullParameter(file, "file");
            return new Location(file, line, column);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Location)) {
                return false;
            }
            Location location = (Location) other;
            return Intrinsics.areEqual(this.file, location.file) && this.line == location.line && this.column == location.column;
        }

        public int hashCode() {
            return (((this.file.hashCode() * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.column);
        }

        public String toString() {
            return "Location(file=" + this.file + ", line=" + this.line + ", column=" + this.column + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Location(String file, int i, int i2) {
            super(null);
            Intrinsics.checkNotNullParameter(file, "file");
            this.file = file;
            this.line = i;
            this.column = i2;
        }

        public final int getColumn() {
            return this.column;
        }

        public final String getFile() {
            return this.file;
        }

        public final int getLine() {
            return this.line;
        }
    }

    /* JADX INFO: compiled from: SourceLocation.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation$Companion;", "", "()V", "NoLocation", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", MediaTrack.ROLE_DESCRIPTION, "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SourceLocation NoLocation(String description) {
            Intrinsics.checkNotNullParameter(description, "description");
            return NoLocation.INSTANCE;
        }
    }
}
