package org.jetbrains.kotlin.com.intellij.lang.impl;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
final class MarkerProduction extends IntArrayList {
    private static final Logger LOG = Logger.getInstance((Class<?>) MarkerProduction.class);
    private final MarkerOptionalData myOptionalData;
    private final MarkerPool myPool;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2) {
            objArr[0] = "doneMarker";
        } else if (i != 3) {
            objArr[0] = "marker";
        } else {
            objArr[0] = "pivot";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/MarkerProduction";
        if (i == 1) {
            objArr[2] = "dropMarker";
        } else if (i == 2) {
            objArr[2] = "doHeavyChecksOnMarkerDone";
        } else if (i != 3) {
            objArr[2] = "hasErrorsAfter";
        } else {
            objArr[2] = "assertNoDoneMarkerAround";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    MarkerProduction(MarkerPool markerPool, MarkerOptionalData markerOptionalData) {
        super(256);
        this.myPool = markerPool;
        this.myOptionalData = markerOptionalData;
    }

    void addBefore(PsiBuilderImpl.ProductionMarker productionMarker, PsiBuilderImpl.ProductionMarker productionMarker2) {
        add(indexOf(productionMarker2), productionMarker.markerId);
    }

    private int indexOf(PsiBuilderImpl.ProductionMarker productionMarker) {
        int iFindLinearly = findLinearly(productionMarker.markerId);
        if (iFindLinearly < 0) {
            for (int iFindMarkerAtLexeme = findMarkerAtLexeme(productionMarker.getLexemeIndex(false)); iFindMarkerAtLexeme < this.size; iFindMarkerAtLexeme++) {
                if (this.a[iFindMarkerAtLexeme] == productionMarker.markerId) {
                    iFindLinearly = iFindMarkerAtLexeme;
                    break;
                }
            }
        }
        if (iFindLinearly < 0) {
            LOG.error("Dropped or rolled-back marker");
        }
        return iFindLinearly;
    }

    private int findLinearly(int i) {
        int iMax = Math.max(0, size() - 20);
        for (int size = size() - 1; size >= iMax; size--) {
            if (this.a[size] == i) {
                return size;
            }
        }
        return -1;
    }

    private int findMarkerAtLexeme(final int i) {
        int iBinarySearch = ObjectUtils.binarySearch(0, size() - 20, new IntUnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.lang.impl.MarkerProduction$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i2) {
                return this.f$0.m6461x65a39a36(i, i2);
            }
        });
        if (iBinarySearch < 0) {
            return -1;
        }
        return findSameLexemeGroupStart(i, iBinarySearch);
    }

    /* JADX INFO: renamed from: lambda$findMarkerAtLexeme$0$org-jetbrains-kotlin-com-intellij-lang-impl-MarkerProduction, reason: not valid java name */
    /* synthetic */ int m6461x65a39a36(int i, int i2) {
        return Integer.compare(getLexemeIndexAt(i2), i);
    }

    private int findSameLexemeGroupStart(int i, int i2) {
        while (i2 > 0 && getLexemeIndexAt(i2 - 1) == i) {
            i2--;
        }
        return i2;
    }

    void addMarker(PsiBuilderImpl.ProductionMarker productionMarker) {
        add(productionMarker.markerId);
    }

    void rollbackTo(PsiBuilderImpl.ProductionMarker productionMarker) {
        int iIndexOf = indexOf(productionMarker);
        for (int size = size() - 1; size >= iIndexOf; size--) {
            int i = this.a[size];
            if (i > 0) {
                MarkerPool markerPool = this.myPool;
                markerPool.freeMarker(markerPool.get(i));
            }
        }
        removeElements(iIndexOf, size());
    }

    boolean hasErrorsAfter(PsiBuilderImpl.StartMarker startMarker) {
        if (startMarker == null) {
            $$$reportNull$$$0(0);
        }
        for (int iIndexOf = indexOf((PsiBuilderImpl.ProductionMarker) startMarker) + 1; iIndexOf < size(); iIndexOf++) {
            PsiBuilderImpl.ProductionMarker startMarkerAt = getStartMarkerAt(iIndexOf);
            if (startMarkerAt != null && hasError(startMarkerAt)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasError(PsiBuilderImpl.ProductionMarker productionMarker) {
        return (productionMarker instanceof PsiBuilderImpl.ErrorItem) || this.myOptionalData.getDoneError(productionMarker.markerId) != null;
    }

    void dropMarker(PsiBuilderImpl.StartMarker startMarker) {
        if (startMarker == null) {
            $$$reportNull$$$0(1);
        }
        if (startMarker.isDone()) {
            removeInt(lastIndexOf(-startMarker.markerId));
        }
        removeInt(indexOf((PsiBuilderImpl.ProductionMarker) startMarker));
        this.myPool.freeMarker(startMarker);
    }

    void addDone(PsiBuilderImpl.StartMarker startMarker, PsiBuilderImpl.ProductionMarker productionMarker) {
        add(productionMarker == null ? size() : indexOf(productionMarker), -startMarker.markerId);
    }

    PsiBuilderImpl.ProductionMarker getMarkerAt(int i) {
        int i2 = getInt(i);
        MarkerPool markerPool = this.myPool;
        if (i2 <= 0) {
            i2 = -i2;
        }
        return markerPool.get(i2);
    }

    PsiBuilderImpl.ProductionMarker getStartMarkerAt(int i) {
        int i2 = getInt(i);
        if (i2 > 0) {
            return this.myPool.get(i2);
        }
        return null;
    }

    PsiBuilderImpl.StartMarker getDoneMarkerAt(int i) {
        int i2 = getInt(i);
        if (i2 < 0) {
            return (PsiBuilderImpl.StartMarker) this.myPool.get(-i2);
        }
        return null;
    }

    int getLexemeIndexAt(int i) {
        int i2 = getInt(i);
        return this.myPool.get(Math.abs(i2)).getLexemeIndex(i2 < 0);
    }

    void confineMarkersToMaxLexeme(int i, int i2) {
        for (int i3 = i - 1; i3 > 1; i3--) {
            int i4 = this.a[i3];
            PsiBuilderImpl.ProductionMarker productionMarker = this.myPool.get(Math.abs(i4));
            boolean z = i4 < 0;
            if (productionMarker.getLexemeIndex(z) < i2) {
                return;
            }
            productionMarker.setLexemeIndex(i2, z);
        }
    }

    void doHeavyChecksOnMarkerDone(PsiBuilderImpl.StartMarker startMarker, PsiBuilderImpl.StartMarker startMarker2) {
        if (startMarker == null) {
            $$$reportNull$$$0(2);
        }
        int iIndexOf = indexOf((PsiBuilderImpl.ProductionMarker) startMarker);
        int size = size();
        if (startMarker2 != null && iIndexOf > (size = indexOf((PsiBuilderImpl.ProductionMarker) startMarker2))) {
            LOG.error("'Before' marker precedes this one.");
        }
        for (int i = size - 1; i > iIndexOf; i--) {
            PsiBuilderImpl.ProductionMarker startMarkerAt = getStartMarkerAt(i);
            if (startMarkerAt instanceof PsiBuilderImpl.StartMarker) {
                PsiBuilderImpl.StartMarker startMarker3 = (PsiBuilderImpl.StartMarker) startMarkerAt;
                if (!startMarker3.isDone()) {
                    Throwable allocationTrace = this.myOptionalData.getAllocationTrace(startMarker);
                    Throwable th = new Throwable();
                    if (allocationTrace != null) {
                        ExceptionUtil.makeStackTraceRelative(allocationTrace, th).printStackTrace(System.err);
                    }
                    Throwable allocationTrace2 = this.myOptionalData.getAllocationTrace(startMarker3);
                    if (allocationTrace2 != null) {
                        ExceptionUtil.makeStackTraceRelative(allocationTrace2, th).printStackTrace(System.err);
                    }
                    LOG.error("Another not done marker added after this one. Must be done before this.");
                }
            }
        }
    }

    void assertNoDoneMarkerAround(PsiBuilderImpl.StartMarker startMarker) {
        if (startMarker == null) {
            $$$reportNull$$$0(3);
        }
        int iIndexOf = indexOf((PsiBuilderImpl.ProductionMarker) startMarker);
        for (int i = iIndexOf + 1; i < size(); i++) {
            PsiBuilderImpl.StartMarker doneMarkerAt = getDoneMarkerAt(i);
            if (doneMarkerAt != null && doneMarkerAt.myLexemeIndex <= startMarker.myLexemeIndex && indexOf((PsiBuilderImpl.ProductionMarker) doneMarkerAt) < iIndexOf) {
                throw new AssertionError("There's a marker of type '" + doneMarkerAt.getTokenType() + "' that starts before and finishes after the current marker. See cause for its allocation trace.", this.myOptionalData.getAllocationTrace(doneMarkerAt));
            }
        }
    }
}
