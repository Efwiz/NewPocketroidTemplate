package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.FrozenDocument;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.CommonProcessors;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
class SmartPointerTracker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ReferenceQueue<SmartPsiElementPointerImpl<?>> ourQueue = new ReferenceQueue<>();
    private boolean mySorted;
    private int nextAvailableIndex;
    private int size;
    private PointerReference[] references = new PointerReference[10];
    private final MarkerCache markerCache = new MarkerCache(this);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "reference";
                break;
            case 2:
                objArr[0] = "processor";
                break;
            case 3:
            case 10:
                objArr[0] = "frozen";
                break;
            case 4:
            case 7:
            case 11:
                objArr[0] = "events";
                break;
            case 5:
            case 12:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 6:
                objArr[0] = "document";
                break;
            case 8:
                objArr[0] = "containingFile";
                break;
            case 9:
                objArr[0] = "segment";
                break;
            case 13:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 14:
                objArr[0] = "manager";
                break;
            default:
                objArr[0] = "pointer";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPointerTracker";
        switch (i) {
            case 1:
                objArr[2] = "removeReference";
                break;
            case 2:
                objArr[2] = "processAlivePointers";
                break;
            case 3:
            case 4:
                objArr[2] = "updateMarkers";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "getUpdatedRange";
                break;
            case 12:
            case 13:
                objArr[2] = "switchStubToAst";
                break;
            case 14:
                objArr[2] = "fastenBelts";
                break;
            case 15:
                objArr[2] = "updatePointerTarget";
                break;
            default:
                objArr[2] = "addReference";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    SmartPointerTracker() {
    }

    static {
        Application application = ApplicationManager.getApplication();
        if (application.isDisposed()) {
            return;
        }
        LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerTracker$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SmartPointerTracker.processQueue();
            }
        }, application);
    }

    synchronized void addReference(SmartPsiElementPointerImpl<?> smartPsiElementPointerImpl) {
        if (smartPsiElementPointerImpl == null) {
            $$$reportNull$$$0(0);
        }
        PointerReference pointerReference = new PointerReference(smartPsiElementPointerImpl, this);
        if (needsExpansion() || isTooSparse()) {
            resize();
        }
        PointerReference[] pointerReferenceArr = this.references;
        int i = this.nextAvailableIndex;
        if (pointerReferenceArr[i] != null) {
            throw new AssertionError(this.references[this.nextAvailableIndex]);
        }
        this.nextAvailableIndex = i + 1;
        storePointerReference(pointerReferenceArr, i, pointerReference);
        this.size++;
        this.mySorted = false;
        if (((SelfElementInfo) smartPsiElementPointerImpl.getElementInfo()).hasRange()) {
            this.markerCache.rangeChanged();
        }
    }

    private boolean needsExpansion() {
        return this.nextAvailableIndex >= this.references.length;
    }

    private boolean isTooSparse() {
        return this.nextAvailableIndex > this.size * 2;
    }

    private void resize() {
        PointerReference[] pointerReferenceArr = new PointerReference[((this.size * 3) / 2) + 1];
        int i = 0;
        for (PointerReference pointerReference : this.references) {
            if (pointerReference != null) {
                storePointerReference(pointerReferenceArr, i, pointerReference);
                i++;
            }
        }
        this.references = pointerReferenceArr;
        this.nextAvailableIndex = i;
    }

    synchronized void removeReference(PointerReference pointerReference) {
        if (pointerReference == null) {
            $$$reportNull$$$0(1);
        }
        int i = pointerReference.index;
        if (i < 0) {
            return;
        }
        PointerReference pointerReference2 = this.references[i];
        if (pointerReference2 != pointerReference) {
            throw new AssertionError("At " + i + " expected " + pointerReference + ", found " + this.references[i]);
        }
        pointerReference2.index = -1;
        this.references[i] = null;
        this.size--;
    }

    private void processAlivePointers(Processor<? super SmartPsiElementPointerImpl<?>> processor) {
        if (processor == null) {
            $$$reportNull$$$0(2);
        }
        for (int i = 0; i < this.nextAvailableIndex; i++) {
            PointerReference pointerReference = this.references[i];
            if (pointerReference != null) {
                SmartPsiElementPointerImpl smartPsiElementPointerImpl = (SmartPsiElementPointerImpl) pointerReference.get();
                if (smartPsiElementPointerImpl == null) {
                    removeReference(pointerReference);
                } else if (!processor.process(smartPsiElementPointerImpl)) {
                    return;
                }
            }
        }
    }

    private void ensureSorted() {
        if (this.mySorted) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        processAlivePointers(new CommonProcessors.CollectProcessor(arrayList));
        if (this.size != arrayList.size()) {
            throw new AssertionError();
        }
        arrayList.sort(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerTracker$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MarkerCache.INFO_COMPARATOR.compare((SelfElementInfo) ((SmartPsiElementPointerImpl) obj).getElementInfo(), (SelfElementInfo) ((SmartPsiElementPointerImpl) obj2).getElementInfo());
            }
        });
        for (int i = 0; i < arrayList.size(); i++) {
            storePointerReference(this.references, i, ((SmartPsiElementPointerImpl) arrayList.get(i)).pointerReference);
        }
        Arrays.fill(this.references, arrayList.size(), this.nextAvailableIndex, (Object) null);
        this.nextAvailableIndex = arrayList.size();
        this.mySorted = true;
    }

    synchronized void updateMarkers(FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (frozenDocument == null) {
            try {
                $$$reportNull$$$0(3);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (!this.markerCache.updateMarkers(frozenDocument, list)) {
            this.mySorted = false;
        }
    }

    synchronized Segment getUpdatedRange(SelfElementInfo selfElementInfo, FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (selfElementInfo == null) {
            try {
                $$$reportNull$$$0(5);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        return this.markerCache.getUpdatedRange(selfElementInfo, frozenDocument, list);
    }

    synchronized Segment getUpdatedRange(PsiFile psiFile, Segment segment, boolean z, FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (psiFile == null) {
            try {
                $$$reportNull$$$0(8);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (segment == null) {
            $$$reportNull$$$0(9);
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(10);
        }
        if (list == null) {
            $$$reportNull$$$0(11);
        }
        return MarkerCache.getUpdatedRange(psiFile, segment, z, frozenDocument, list);
    }

    synchronized void switchStubToAst(AnchorElementInfo anchorElementInfo, PsiElement psiElement) {
        if (anchorElementInfo == null) {
            try {
                $$$reportNull$$$0(12);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        anchorElementInfo.switchToTreeRange(psiElement);
        this.markerCache.rangeChanged();
        this.mySorted = false;
    }

    synchronized void fastenBelts(final SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(14);
        }
        processQueue();
        processAlivePointers(new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerTracker$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return SmartPointerTracker.lambda$fastenBelts$2(smartPointerManagerImpl, (SmartPsiElementPointerImpl) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$fastenBelts$2(SmartPointerManagerImpl smartPointerManagerImpl, SmartPsiElementPointerImpl smartPsiElementPointerImpl) {
        smartPsiElementPointerImpl.getElementInfo().fastenBelt(smartPointerManagerImpl);
        return true;
    }

    synchronized void updatePointerTargetsAfterReparse() {
        processAlivePointers(new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerTracker$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return SmartPointerTracker.lambda$updatePointerTargetsAfterReparse$3((SmartPsiElementPointerImpl) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$updatePointerTargetsAfterReparse$3(SmartPsiElementPointerImpl smartPsiElementPointerImpl) {
        if (smartPsiElementPointerImpl instanceof SmartPsiFileRangePointerImpl) {
            return true;
        }
        updatePointerTarget(smartPsiElementPointerImpl, smartPsiElementPointerImpl.getPsiRange());
        return true;
    }

    private static <E extends PsiElement> void updatePointerTarget(SmartPsiElementPointerImpl<E> smartPsiElementPointerImpl, Segment segment) {
        if (smartPsiElementPointerImpl == null) {
            $$$reportNull$$$0(15);
        }
        PsiElement cachedElement = smartPsiElementPointerImpl.getCachedElement();
        if (cachedElement == null) {
            return;
        }
        boolean zIsValid = cachedElement.isValid();
        if (zIsValid) {
            if (segment == null) {
                ((SelfElementInfo) smartPsiElementPointerImpl.getElementInfo()).switchToAnchor(cachedElement);
                return;
            } else if (segment.equals(cachedElement.getTextRange())) {
                return;
            }
        }
        PsiElement psiElementDoRestoreElement = smartPsiElementPointerImpl.doRestoreElement();
        if ((psiElementDoRestoreElement == null && zIsValid && ((SelfElementInfo) smartPsiElementPointerImpl.getElementInfo()).updateRangeToPsi(segment, cachedElement)) || psiElementDoRestoreElement == cachedElement) {
            return;
        }
        smartPsiElementPointerImpl.cacheElement(psiElementDoRestoreElement);
    }

    private static void storePointerReference(PointerReference[] pointerReferenceArr, int i, PointerReference pointerReference) {
        pointerReferenceArr[i] = pointerReference;
        pointerReference.index = i;
    }

    synchronized List<SelfElementInfo> getSortedInfos() {
        final ArrayList arrayList;
        ensureSorted();
        arrayList = new ArrayList(this.size);
        processAlivePointers(new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerTracker$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return SmartPointerTracker.lambda$getSortedInfos$4(arrayList, (SmartPsiElementPointerImpl) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ boolean lambda$getSortedInfos$4(List list, SmartPsiElementPointerImpl smartPsiElementPointerImpl) {
        SelfElementInfo selfElementInfo = (SelfElementInfo) smartPsiElementPointerImpl.getElementInfo();
        if (!selfElementInfo.hasRange()) {
            return false;
        }
        list.add(selfElementInfo);
        return true;
    }

    synchronized int getSize() {
        return this.size;
    }

    static final class PointerReference extends WeakReference<SmartPsiElementPointerImpl<?>> {
        private int index;
        final SmartPointerTracker tracker;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "pointer";
            } else {
                objArr[0] = "tracker";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPointerTracker$PointerReference";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private PointerReference(SmartPsiElementPointerImpl<?> smartPsiElementPointerImpl, SmartPointerTracker smartPointerTracker) {
            super(smartPsiElementPointerImpl, SmartPointerTracker.ourQueue);
            if (smartPsiElementPointerImpl == null) {
                $$$reportNull$$$0(0);
            }
            if (smartPointerTracker == null) {
                $$$reportNull$$$0(1);
            }
            this.index = -2;
            this.tracker = smartPointerTracker;
            smartPsiElementPointerImpl.pointerReference = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void processQueue() {
        while (true) {
            PointerReference pointerReference = (PointerReference) ourQueue.poll();
            if (pointerReference == null) {
                return;
            }
            if (pointerReference.get() != null) {
                throw new IllegalStateException("Queued reference has referent!");
            }
            pointerReference.tracker.removeReference(pointerReference);
        }
    }
}
