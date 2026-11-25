package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.FrozenDocument;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.ManualRangeMarker;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event.DocumentEventImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UnfairTextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: loaded from: classes3.dex */
class MarkerCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Comparator<SelfElementInfo> INFO_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.MarkerCache$$ExternalSyntheticLambda2
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return MarkerCache.lambda$static$0((SelfElementInfo) obj, (SelfElementInfo) obj2);
        }
    };
    private final SmartPointerTracker myPointers;
    private UpdatedRanges myUpdatedRanges;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 10:
            case 13:
            case 17:
            case 19:
                objArr[0] = "frozen";
                break;
            case 2:
            case 8:
            case 11:
            case 14:
            case 18:
                objArr[0] = "events";
                break;
            case 3:
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/MarkerCache";
                break;
            case 5:
                objArr[0] = AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS;
                break;
            case 7:
            case 12:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 9:
                objArr[0] = "struct";
                break;
            case 15:
                objArr[0] = "containingFile";
                break;
            case 16:
                objArr[0] = "segment";
                break;
            case 20:
                objArr[0] = "event";
                break;
            default:
                objArr[0] = "pointers";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getUpdatedMarkers";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/MarkerCache";
        } else {
            objArr[1] = "createMarkers";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "getUpdatedMarkers";
                break;
            case 3:
            case 4:
            case 6:
                break;
            case 5:
                objArr[2] = "createMarkers";
                break;
            case 7:
                objArr[2] = "rangeEquals";
                break;
            case 8:
            case 9:
                objArr[2] = "applyEvents";
                break;
            case 10:
            case 11:
                objArr[2] = "updateMarkers";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "getUpdatedRange";
                break;
            case 19:
            case 20:
                objArr[2] = "isWholeDocumentReplace";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int lambda$static$0(SelfElementInfo selfElementInfo, SelfElementInfo selfElementInfo2) {
        int psiStartOffset = selfElementInfo.getPsiStartOffset();
        int psiStartOffset2 = selfElementInfo2.getPsiStartOffset();
        if (psiStartOffset < 0 || psiStartOffset2 < 0) {
            if (psiStartOffset >= 0) {
                return -1;
            }
            return psiStartOffset2 >= 0 ? 1 : 0;
        }
        if (psiStartOffset != psiStartOffset2) {
            return psiStartOffset > psiStartOffset2 ? 1 : -1;
        }
        int psiEndOffset = selfElementInfo.getPsiEndOffset();
        int psiEndOffset2 = selfElementInfo2.getPsiEndOffset();
        if (psiEndOffset != psiEndOffset2) {
            return psiEndOffset > psiEndOffset2 ? 1 : -1;
        }
        return (selfElementInfo.isGreedy() ? 1 : 0) - (selfElementInfo2.isGreedy() ? 1 : 0);
    }

    MarkerCache(SmartPointerTracker smartPointerTracker) {
        if (smartPointerTracker == null) {
            $$$reportNull$$$0(0);
        }
        this.myPointers = smartPointerTracker;
    }

    private UpdatedRanges getUpdatedMarkers(FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        UpdatedRanges updatedRangesApplyEvents;
        if (frozenDocument == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        int size = list.size();
        UpdatedRanges updatedRanges = this.myUpdatedRanges;
        if (updatedRanges != null && updatedRanges.myEventCount == size) {
            if (updatedRanges == null) {
                $$$reportNull$$$0(3);
            }
            return updatedRanges;
        }
        if (updatedRanges == null || updatedRanges.myEventCount >= size) {
            List<SelfElementInfo> sortedInfos = this.myPointers.getSortedInfos();
            updatedRangesApplyEvents = applyEvents(list, new UpdatedRanges(0, frozenDocument, sortedInfos, createMarkers(sortedInfos)));
        } else {
            updatedRangesApplyEvents = applyEvents(list.subList(updatedRanges.myEventCount, size), updatedRanges);
        }
        this.myUpdatedRanges = updatedRangesApplyEvents;
        if (updatedRangesApplyEvents == null) {
            $$$reportNull$$$0(4);
        }
        return updatedRangesApplyEvents;
    }

    private static ManualRangeMarker[] createMarkers(List<? extends SelfElementInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        int size = list.size();
        ManualRangeMarker[] manualRangeMarkerArr = new ManualRangeMarker[size];
        int i = 0;
        while (i < size) {
            SelfElementInfo selfElementInfo = list.get(i);
            boolean zIsGreedy = selfElementInfo.isGreedy();
            int psiStartOffset = selfElementInfo.getPsiStartOffset();
            int psiEndOffset = selfElementInfo.getPsiEndOffset();
            manualRangeMarkerArr[i] = new ManualRangeMarker(psiStartOffset, psiEndOffset, zIsGreedy, zIsGreedy, !zIsGreedy, null);
            while (true) {
                i++;
                if (i >= size || !rangeEquals(list.get(i), psiStartOffset, psiEndOffset, zIsGreedy)) {
                    break;
                }
                manualRangeMarkerArr[i] = manualRangeMarkerArr[i - 1];
            }
        }
        return manualRangeMarkerArr;
    }

    private static boolean rangeEquals(SelfElementInfo selfElementInfo, int i, int i2, boolean z) {
        if (selfElementInfo == null) {
            $$$reportNull$$$0(7);
        }
        return i == selfElementInfo.getPsiStartOffset() && i2 == selfElementInfo.getPsiEndOffset() && z == selfElementInfo.isGreedy();
    }

    private static UpdatedRanges applyEvents(List<? extends DocumentEvent> list, UpdatedRanges updatedRanges) {
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        if (updatedRanges == null) {
            $$$reportNull$$$0(9);
        }
        FrozenDocument frozenDocument = updatedRanges.myResultDocument;
        ManualRangeMarker[] manualRangeMarkerArr = (ManualRangeMarker[]) updatedRanges.myMarkers.clone();
        for (DocumentEvent documentEvent : list) {
            FrozenDocument frozenDocumentApplyEvent = frozenDocument.applyEvent(documentEvent, 0);
            DocumentEventImpl documentEventImpl = (DocumentEventImpl) documentEvent;
            DocumentEventImpl documentEventImpl2 = new DocumentEventImpl(frozenDocumentApplyEvent, documentEvent.getOffset(), documentEvent.getOldFragment(), documentEvent.getNewFragment(), documentEvent.getOldTimeStamp(), documentEvent.isWholeTextReplaced(), documentEventImpl.getInitialStartOffset(), documentEventImpl.getInitialOldLength(), documentEvent.getMoveOffset());
            int i = 0;
            while (i < manualRangeMarkerArr.length) {
                ManualRangeMarker manualRangeMarker = manualRangeMarkerArr[i];
                int i2 = i + 1;
                while (i2 < manualRangeMarkerArr.length && manualRangeMarkerArr[i2] == manualRangeMarker) {
                    i2++;
                }
                ManualRangeMarker updatedRange = manualRangeMarker == null ? null : manualRangeMarker.getUpdatedRange(documentEventImpl2, frozenDocument);
                while (i < i2) {
                    manualRangeMarkerArr[i] = updatedRange;
                    i++;
                }
            }
            frozenDocument = frozenDocumentApplyEvent;
        }
        return new UpdatedRanges(updatedRanges.myEventCount + list.size(), frozenDocument, updatedRanges.mySortedInfos, manualRangeMarkerArr);
    }

    boolean updateMarkers(FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (frozenDocument == null) {
            $$$reportNull$$$0(10);
        }
        if (list == null) {
            $$$reportNull$$$0(11);
        }
        UpdatedRanges updatedMarkers = getUpdatedMarkers(frozenDocument, list);
        boolean z = true;
        for (int i = 0; i < updatedMarkers.myMarkers.length; i++) {
            SelfElementInfo selfElementInfo = (SelfElementInfo) updatedMarkers.mySortedInfos.get(i);
            selfElementInfo.setRange(updatedMarkers.myMarkers[i]);
            if (z && i > 0 && INFO_COMPARATOR.compare((SelfElementInfo) updatedMarkers.mySortedInfos.get(i - 1), selfElementInfo) > 0) {
                z = false;
            }
        }
        this.myUpdatedRanges = null;
        return z;
    }

    TextRange getUpdatedRange(SelfElementInfo selfElementInfo, FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (selfElementInfo == null) {
            $$$reportNull$$$0(12);
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(13);
        }
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        UpdatedRanges updatedMarkers = getUpdatedMarkers(frozenDocument, list);
        int iBinarySearch = Collections.binarySearch(updatedMarkers.mySortedInfos, selfElementInfo, INFO_COMPARATOR);
        ManualRangeMarker manualRangeMarker = iBinarySearch >= 0 ? updatedMarkers.myMarkers[iBinarySearch] : null;
        if (manualRangeMarker == null) {
            return null;
        }
        return new UnfairTextRange(manualRangeMarker.getStartOffset(), manualRangeMarker.getEndOffset());
    }

    static Segment getUpdatedRange(PsiFile psiFile, Segment segment, boolean z, final FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (psiFile == null) {
            $$$reportNull$$$0(15);
        }
        if (segment == null) {
            $$$reportNull$$$0(16);
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(17);
        }
        if (list == null) {
            $$$reportNull$$$0(18);
        }
        SelfElementInfo selfElementInfo = new SelfElementInfo(ProperTextRange.create(segment), new Identikit() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.MarkerCache.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/MarkerCache$1", "findPsiElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
            public PsiElement findPsiElement(PsiFile psiFile2, int i, int i2) {
                if (psiFile2 != null) {
                    return null;
                }
                $$$reportNull$$$0(0);
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
            public boolean isForPsiFile() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
            public Language getFileLanguage() {
                throw new IllegalStateException();
            }
        }, psiFile, z);
        List listSingletonList = Collections.singletonList(selfElementInfo);
        boolean zIsGreedy = selfElementInfo.isGreedy();
        return applyEvents(ContainerUtil.map((Collection) list, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.MarkerCache$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return MarkerCache.lambda$getUpdatedRange$2(frozenDocument, (DocumentEvent) obj);
            }
        }), new UpdatedRanges(0, frozenDocument, listSingletonList, new ManualRangeMarker[]{new ManualRangeMarker(selfElementInfo.getPsiStartOffset(), selfElementInfo.getPsiEndOffset(), zIsGreedy, zIsGreedy, list.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.MarkerCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MarkerCache.isWholeDocumentReplace(frozenDocument, (DocumentEventImpl) ((DocumentEvent) obj));
            }
        }), null)})).myMarkers[0];
    }

    static /* synthetic */ DocumentEvent lambda$getUpdatedRange$2(FrozenDocument frozenDocument, DocumentEvent documentEvent) {
        DocumentEventImpl documentEventImpl = (DocumentEventImpl) documentEvent;
        return isWholeDocumentReplace(frozenDocument, documentEventImpl) ? new DocumentEventImpl(documentEvent.getDocument(), documentEvent.getOffset(), documentEvent.getOldFragment(), documentEvent.getNewFragment(), documentEvent.getOldTimeStamp(), true, documentEventImpl.getInitialStartOffset(), documentEventImpl.getInitialOldLength(), documentEvent.getMoveOffset()) : documentEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWholeDocumentReplace(FrozenDocument frozenDocument, DocumentEventImpl documentEventImpl) {
        if (frozenDocument == null) {
            $$$reportNull$$$0(19);
        }
        if (documentEventImpl == null) {
            $$$reportNull$$$0(20);
        }
        return documentEventImpl.getInitialStartOffset() == 0 && documentEventImpl.getInitialOldLength() == frozenDocument.getTextLength();
    }

    void rangeChanged() {
        this.myUpdatedRanges = null;
    }

    private static class UpdatedRanges {
        private final int myEventCount;
        private final ManualRangeMarker[] myMarkers;
        private final FrozenDocument myResultDocument;
        private final List<SelfElementInfo> mySortedInfos;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "sortedInfos";
            } else if (i != 2) {
                objArr[0] = "resultDocument";
            } else {
                objArr[0] = "markers";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/MarkerCache$UpdatedRanges";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        UpdatedRanges(int i, FrozenDocument frozenDocument, List<SelfElementInfo> list, ManualRangeMarker[] manualRangeMarkerArr) {
            if (frozenDocument == null) {
                $$$reportNull$$$0(0);
            }
            if (list == null) {
                $$$reportNull$$$0(1);
            }
            if (manualRangeMarkerArr == null) {
                $$$reportNull$$$0(2);
            }
            this.myEventCount = i;
            this.myResultDocument = frozenDocument;
            this.mySortedInfos = list;
            this.myMarkers = manualRangeMarkerArr;
        }
    }
}
