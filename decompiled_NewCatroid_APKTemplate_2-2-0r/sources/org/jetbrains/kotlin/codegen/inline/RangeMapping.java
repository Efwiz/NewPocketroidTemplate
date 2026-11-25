package org.jetbrains.kotlin.codegen.inline;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J\u0011\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0086\u0002J=\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u000e\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\u000e\u0010#\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0003J\u000e\u0010$\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0003J\t\u0010%\u001a\u00020&HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "", FirebaseAnalytics.Param.SOURCE, "", "dest", "range", "callSite", "Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "parent", "Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "(IIILorg/jetbrains/kotlin/codegen/inline/SourcePosition;Lorg/jetbrains/kotlin/codegen/inline/FileMapping;)V", "getCallSite", "()Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "getDest", "()I", "getParent", "()Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "getRange", "setRange", "(I)V", "getSource", "component1", "component2", "component3", "component4", "component5", "contains", "", "destLine", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hasMappingForSource", "sourceLine", "hashCode", "mapDestToSource", "mapSourceToDest", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RangeMapping {
    private final SourcePosition callSite;
    private final int dest;
    private final FileMapping parent;
    private int range;
    private final int source;

    public static /* synthetic */ RangeMapping copy$default(RangeMapping rangeMapping, int i, int i2, int i3, SourcePosition sourcePosition, FileMapping fileMapping, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = rangeMapping.source;
        }
        if ((i4 & 2) != 0) {
            i2 = rangeMapping.dest;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            i3 = rangeMapping.range;
        }
        int i6 = i3;
        if ((i4 & 8) != 0) {
            sourcePosition = rangeMapping.callSite;
        }
        SourcePosition sourcePosition2 = sourcePosition;
        if ((i4 & 16) != 0) {
            fileMapping = rangeMapping.parent;
        }
        return rangeMapping.copy(i, i5, i6, sourcePosition2, fileMapping);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getDest() {
        return this.dest;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getRange() {
        return this.range;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final SourcePosition getCallSite() {
        return this.callSite;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final FileMapping getParent() {
        return this.parent;
    }

    public final RangeMapping copy(int source, int dest, int range, SourcePosition callSite, FileMapping parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return new RangeMapping(source, dest, range, callSite, parent);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RangeMapping)) {
            return false;
        }
        RangeMapping rangeMapping = (RangeMapping) other;
        return this.source == rangeMapping.source && this.dest == rangeMapping.dest && this.range == rangeMapping.range && Intrinsics.areEqual(this.callSite, rangeMapping.callSite) && Intrinsics.areEqual(this.parent, rangeMapping.parent);
    }

    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.source) * 31) + Integer.hashCode(this.dest)) * 31) + Integer.hashCode(this.range)) * 31;
        SourcePosition sourcePosition = this.callSite;
        return ((iHashCode + (sourcePosition == null ? 0 : sourcePosition.hashCode())) * 31) + this.parent.hashCode();
    }

    public String toString() {
        return "RangeMapping(source=" + this.source + ", dest=" + this.dest + ", range=" + this.range + ", callSite=" + this.callSite + ", parent=" + this.parent + ')';
    }

    public RangeMapping(int i, int i2, int i3, SourcePosition sourcePosition, FileMapping parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.source = i;
        this.dest = i2;
        this.range = i3;
        this.callSite = sourcePosition;
        this.parent = parent;
    }

    public final SourcePosition getCallSite() {
        return this.callSite;
    }

    public final int getDest() {
        return this.dest;
    }

    public final FileMapping getParent() {
        return this.parent;
    }

    public final int getRange() {
        return this.range;
    }

    public final int getSource() {
        return this.source;
    }

    public final void setRange(int i) {
        this.range = i;
    }

    public final boolean contains(int destLine) {
        int i = this.dest;
        return i <= destLine && destLine < i + this.range;
    }

    public final boolean hasMappingForSource(int sourceLine) {
        int i = this.source;
        return i <= sourceLine && sourceLine < i + this.range;
    }

    public final SourcePosition mapDestToSource(int destLine) {
        return new SourcePosition(this.source + (destLine - this.dest), this.parent.getName(), this.parent.getPath());
    }

    public final int mapSourceToDest(int sourceLine) {
        return this.dest + (sourceLine - this.source);
    }
}
