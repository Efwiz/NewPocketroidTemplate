package kotlin.script.experimental.api;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001:\u0004\n\u000b\f\rR\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/SourceCode;", "", "locationId", "", "getLocationId", "()Ljava/lang/String;", "name", "getName", "text", "getText", "Location", "LocationWithId", "Position", "Range", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SourceCode {
    String getLocationId();

    String getName();

    String getText();

    /* JADX INFO: compiled from: scriptData.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ.\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0019"}, d2 = {"Lkotlin/script/experimental/api/SourceCode$Position;", "Ljava/io/Serializable;", Constant.LINE, "", "col", "absolutePos", "(IILjava/lang/Integer;)V", "getAbsolutePos", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCol", "()I", "getLine", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(IILjava/lang/Integer;)Lkotlin/script/experimental/api/SourceCode$Position;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Position implements Serializable {
        private final Integer absolutePos;
        private final int col;
        private final int line;

        public static /* synthetic */ Position copy$default(Position position, int i, int i2, Integer num, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = position.line;
            }
            if ((i3 & 2) != 0) {
                i2 = position.col;
            }
            if ((i3 & 4) != 0) {
                num = position.absolutePos;
            }
            return position.copy(i, i2, num);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getLine() {
            return this.line;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getCol() {
            return this.col;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Integer getAbsolutePos() {
            return this.absolutePos;
        }

        public final Position copy(int line, int col, Integer absolutePos) {
            return new Position(line, col, absolutePos);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Position)) {
                return false;
            }
            Position position = (Position) other;
            return this.line == position.line && this.col == position.col && Intrinsics.areEqual(this.absolutePos, position.absolutePos);
        }

        public int hashCode() {
            int iHashCode = ((Integer.hashCode(this.line) * 31) + Integer.hashCode(this.col)) * 31;
            Integer num = this.absolutePos;
            return iHashCode + (num == null ? 0 : num.hashCode());
        }

        public String toString() {
            return "Position(line=" + this.line + ", col=" + this.col + ", absolutePos=" + this.absolutePos + ')';
        }

        public Position(int i, int i2, Integer num) {
            this.line = i;
            this.col = i2;
            this.absolutePos = num;
        }

        public /* synthetic */ Position(int i, int i2, Integer num, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i3 & 4) != 0 ? null : num);
        }

        public final Integer getAbsolutePos() {
            return this.absolutePos;
        }

        public final int getCol() {
            return this.col;
        }

        public final int getLine() {
            return this.line;
        }
    }

    /* JADX INFO: compiled from: scriptData.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/api/SourceCode$Range;", "Ljava/io/Serializable;", "start", "Lkotlin/script/experimental/api/SourceCode$Position;", "end", "(Lkotlin/script/experimental/api/SourceCode$Position;Lkotlin/script/experimental/api/SourceCode$Position;)V", "getEnd", "()Lkotlin/script/experimental/api/SourceCode$Position;", "getStart", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Range implements Serializable {
        private final Position end;
        private final Position start;

        public static /* synthetic */ Range copy$default(Range range, Position position, Position position2, int i, Object obj) {
            if ((i & 1) != 0) {
                position = range.start;
            }
            if ((i & 2) != 0) {
                position2 = range.end;
            }
            return range.copy(position, position2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Position getStart() {
            return this.start;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Position getEnd() {
            return this.end;
        }

        public final Range copy(Position start, Position end) {
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(end, "end");
            return new Range(start, end);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Range)) {
                return false;
            }
            Range range = (Range) other;
            return Intrinsics.areEqual(this.start, range.start) && Intrinsics.areEqual(this.end, range.end);
        }

        public int hashCode() {
            return (this.start.hashCode() * 31) + this.end.hashCode();
        }

        public String toString() {
            return "Range(start=" + this.start + ", end=" + this.end + ')';
        }

        public Range(Position start, Position end) {
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(end, "end");
            this.start = start;
            this.end = end;
        }

        public final Position getEnd() {
            return this.end;
        }

        public final Position getStart() {
            return this.start;
        }
    }

    /* JADX INFO: compiled from: scriptData.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/api/SourceCode$Location;", "Ljava/io/Serializable;", "start", "Lkotlin/script/experimental/api/SourceCode$Position;", "end", "(Lkotlin/script/experimental/api/SourceCode$Position;Lkotlin/script/experimental/api/SourceCode$Position;)V", "getEnd", "()Lkotlin/script/experimental/api/SourceCode$Position;", "getStart", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Location implements Serializable {
        private final Position end;
        private final Position start;

        public static /* synthetic */ Location copy$default(Location location, Position position, Position position2, int i, Object obj) {
            if ((i & 1) != 0) {
                position = location.start;
            }
            if ((i & 2) != 0) {
                position2 = location.end;
            }
            return location.copy(position, position2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Position getStart() {
            return this.start;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Position getEnd() {
            return this.end;
        }

        public final Location copy(Position start, Position end) {
            Intrinsics.checkNotNullParameter(start, "start");
            return new Location(start, end);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Location)) {
                return false;
            }
            Location location = (Location) other;
            return Intrinsics.areEqual(this.start, location.start) && Intrinsics.areEqual(this.end, location.end);
        }

        public int hashCode() {
            int iHashCode = this.start.hashCode() * 31;
            Position position = this.end;
            return iHashCode + (position == null ? 0 : position.hashCode());
        }

        public String toString() {
            return "Location(start=" + this.start + ", end=" + this.end + ')';
        }

        public Location(Position start, Position position) {
            Intrinsics.checkNotNullParameter(start, "start");
            this.start = start;
            this.end = position;
        }

        public /* synthetic */ Location(Position position, Position position2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(position, (i & 2) != 0 ? null : position2);
        }

        public final Position getEnd() {
            return this.end;
        }

        public final Position getStart() {
            return this.start;
        }
    }

    /* JADX INFO: compiled from: scriptData.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/api/SourceCode$LocationWithId;", "Ljava/io/Serializable;", "codeLocationId", "", "locationInText", "Lkotlin/script/experimental/api/SourceCode$Location;", "(Ljava/lang/String;Lkotlin/script/experimental/api/SourceCode$Location;)V", "getCodeLocationId", "()Ljava/lang/String;", "getLocationInText", "()Lkotlin/script/experimental/api/SourceCode$Location;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LocationWithId implements Serializable {
        private final String codeLocationId;
        private final Location locationInText;

        public static /* synthetic */ LocationWithId copy$default(LocationWithId locationWithId, String str, Location location, int i, Object obj) {
            if ((i & 1) != 0) {
                str = locationWithId.codeLocationId;
            }
            if ((i & 2) != 0) {
                location = locationWithId.locationInText;
            }
            return locationWithId.copy(str, location);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getCodeLocationId() {
            return this.codeLocationId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Location getLocationInText() {
            return this.locationInText;
        }

        public final LocationWithId copy(String codeLocationId, Location locationInText) {
            Intrinsics.checkNotNullParameter(codeLocationId, "codeLocationId");
            Intrinsics.checkNotNullParameter(locationInText, "locationInText");
            return new LocationWithId(codeLocationId, locationInText);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LocationWithId)) {
                return false;
            }
            LocationWithId locationWithId = (LocationWithId) other;
            return Intrinsics.areEqual(this.codeLocationId, locationWithId.codeLocationId) && Intrinsics.areEqual(this.locationInText, locationWithId.locationInText);
        }

        public int hashCode() {
            return (this.codeLocationId.hashCode() * 31) + this.locationInText.hashCode();
        }

        public String toString() {
            return "LocationWithId(codeLocationId=" + this.codeLocationId + ", locationInText=" + this.locationInText + ')';
        }

        public LocationWithId(String codeLocationId, Location locationInText) {
            Intrinsics.checkNotNullParameter(codeLocationId, "codeLocationId");
            Intrinsics.checkNotNullParameter(locationInText, "locationInText");
            this.codeLocationId = codeLocationId;
            this.locationInText = locationInText;
        }

        public final String getCodeLocationId() {
            return this.codeLocationId;
        }

        public final Location getLocationInText() {
            return this.locationInText;
        }
    }
}
