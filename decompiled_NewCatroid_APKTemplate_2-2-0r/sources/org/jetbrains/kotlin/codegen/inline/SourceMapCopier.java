package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import gnu.trove.TIntIntHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;", "", "parent", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "smap", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "callSite", "Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "(Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;Lorg/jetbrains/kotlin/codegen/inline/SMAP;Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;)V", "getCallSite", "()Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "lastVisitedRange", "Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "getParent", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "visitedLines", "Lgnu/trove/TIntIntHashMap;", "mapLineNumber", "", "lineNumber", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapCopier {
    private final SourcePosition callSite;
    private RangeMapping lastVisitedRange;
    private final SourceMapper parent;
    private final SMAP smap;
    private final TIntIntHashMap visitedLines;

    public SourceMapCopier(SourceMapper parent, SMAP smap, SourcePosition sourcePosition) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(smap, "smap");
        this.parent = parent;
        this.smap = smap;
        this.callSite = sourcePosition;
        this.visitedLines = new TIntIntHashMap();
    }

    public /* synthetic */ SourceMapCopier(SourceMapper sourceMapper, SMAP smap, SourcePosition sourcePosition, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sourceMapper, smap, (i & 4) != 0 ? null : sourcePosition);
    }

    public final SourcePosition getCallSite() {
        return this.callSite;
    }

    public final SourceMapper getParent() {
        return this.parent;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0017  */
    /* JADX WARN: Code duplicated, block: B:14:0x001f A[RETURN] */
    public final int mapLineNumber(int lineNumber) {
        int i = this.visitedLines.get(lineNumber);
        if (i > 0) {
            return i;
        }
        RangeMapping rangeMappingFindRange = this.lastVisitedRange;
        if (rangeMappingFindRange == null) {
            rangeMappingFindRange = this.smap.findRange(lineNumber);
            if (rangeMappingFindRange == null) {
                return -1;
            }
        } else {
            if (!rangeMappingFindRange.contains(lineNumber)) {
                rangeMappingFindRange = null;
            }
            if (rangeMappingFindRange == null) {
                rangeMappingFindRange = this.smap.findRange(lineNumber);
                if (rangeMappingFindRange == null) {
                    return -1;
                }
            }
        }
        this.lastVisitedRange = rangeMappingFindRange;
        SourceMapper sourceMapper = this.parent;
        SourcePosition sourcePositionMapDestToSource = rangeMappingFindRange.mapDestToSource(lineNumber);
        SourcePosition callSite = this.callSite;
        if (callSite == null) {
            callSite = rangeMappingFindRange.getCallSite();
        }
        int iMapLineNumber = sourceMapper.mapLineNumber(sourcePositionMapDestToSource, callSite);
        this.visitedLines.put(lineNumber, iMapLineNumber);
        return iMapLineNumber;
    }
}
