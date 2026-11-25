package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.FrozenDocument;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiFileRange;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class SmartPointerManagerImpl extends SmartPointerManager implements Disposable {
    private final Key<WeakReference<SmartPointerTracker>> LIGHT_TRACKER_KEY;
    private final ConcurrentMap<VirtualFile, SmartPointerTracker> myPhysicalTrackers;
    private final Project myProject;
    private final PsiDocumentManagerBase myPsiDocManager;
    private static final Logger LOG = Logger.getInstance((Class<?>) SmartPointerManagerImpl.class);
    private static final Key<Reference<SmartPsiElementPointerImpl<?>>> CACHED_SMART_POINTER_KEY = Key.create("CACHED_SMART_POINTER_KEY");

    /* JADX WARN: Code duplicated, block: B:57:0x00b5  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 7 || i == 16 || i == 19 || i == 25 || i == 9 || i == 10 || i == 33 || i == 34) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 7 || i == 16 || i == 19 || i == 25 || i == 9 || i == 10 || i == 33 || i == 34) ? 2 : 3];
        switch (i) {
            case 2:
            case 5:
            case 7:
            case 9:
            case 10:
            case 16:
            case 19:
            case 25:
            case 33:
            case 34:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPointerManagerImpl";
                break;
            case 3:
            case 14:
            case 17:
            case 23:
            case 24:
            case 32:
                objArr[0] = "file";
                break;
            case 4:
            case 6:
            case 8:
            case 12:
            case 13:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
            default:
                objArr[0] = "project";
                break;
            case 15:
            case 18:
                objArr[0] = "range";
                break;
            case 20:
            case 22:
                objArr[0] = "pointer";
                break;
            case 21:
            case 26:
                objArr[0] = "containingFile";
                break;
            case 27:
                objArr[0] = "pointer1";
                break;
            case 28:
                objArr[0] = "pointer2";
                break;
            case 29:
                objArr[0] = "document";
                break;
            case 30:
                objArr[0] = "frozen";
                break;
            case 31:
                objArr[0] = "events";
                break;
        }
        if (i == 2) {
            objArr[1] = "anonymize";
        } else if (i == 5 || i == 7) {
            objArr[1] = "createSmartPsiElementPointer";
        } else if (i == 16 || i == 19) {
            objArr[1] = "createSmartPsiFileRangePointer";
        } else if (i == 25) {
            objArr[1] = "getOrCreateTracker";
        } else if (i == 9 || i == 10) {
            objArr[1] = "createSmartPsiElementPointer";
        } else if (i == 33) {
            objArr[1] = "getProject";
        } else if (i != 34) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPointerManagerImpl";
        } else {
            objArr[1] = "getPsiDocumentManager";
        }
        switch (i) {
            case 1:
                objArr[2] = "anonymize";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
            case 10:
            case 16:
            case 19:
            case 25:
            case 33:
            case 34:
                break;
            case 3:
                objArr[2] = "fastenBelts";
                break;
            case 4:
            case 6:
            case 8:
                objArr[2] = "createSmartPsiElementPointer";
                break;
            case 11:
                objArr[2] = "ensureMyProject";
                break;
            case 12:
                objArr[2] = "ensureValid";
                break;
            case 13:
                objArr[2] = "getCachedPointer";
                break;
            case 14:
            case 15:
            case 17:
            case 18:
                objArr[2] = "createSmartPsiFileRangePointer";
                break;
            case 20:
            case 21:
                objArr[2] = "trackPointer";
                break;
            case 22:
                objArr[2] = "removePointer";
                break;
            case 23:
                objArr[2] = "getTracker";
                break;
            case 24:
                objArr[2] = "getOrCreateTracker";
                break;
            case 26:
                objArr[2] = "getPointersNumber";
                break;
            case 27:
            case 28:
                objArr[2] = "pointToTheSameElement";
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "updatePointers";
                break;
            case 32:
                objArr[2] = "updatePointerTargetsAfterReparse";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 7 && i != 16 && i != 19 && i != 25 && i != 9 && i != 10 && i != 33 && i != 34) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public SmartPointerManagerImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myPhysicalTrackers = ContainerUtil.createConcurrentWeakValueMap();
        this.myProject = project;
        this.myPsiDocManager = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(project);
        this.LIGHT_TRACKER_KEY = Key.create("SMART_POINTERS " + (project.isDefault() ? "default" : Integer.valueOf(project.hashCode())));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        SmartPointerTracker.processQueue();
    }

    private static String anonymize(Project project) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        String str = (project.isDisposed() ? "(Disposed)" : "") + (project.isDefault() ? "(Default)" : "") + project.hashCode();
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    public void fastenBelts(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        SmartPointerTracker tracker = getTracker(virtualFile);
        if (tracker != null) {
            tracker.fastenBelts(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager
    public <E extends PsiElement> SmartPsiElementPointer<E> createSmartPsiElementPointer(E e) {
        if (e == null) {
            $$$reportNull$$$0(4);
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        SmartPsiElementPointer<E> smartPsiElementPointerCreateSmartPsiElementPointer = createSmartPsiElementPointer(e, e.getContainingFile());
        if (smartPsiElementPointerCreateSmartPsiElementPointer == null) {
            $$$reportNull$$$0(5);
        }
        return smartPsiElementPointerCreateSmartPsiElementPointer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager
    public <E extends PsiElement> SmartPsiElementPointer<E> createSmartPsiElementPointer(E e, PsiFile psiFile) {
        if (e == null) {
            $$$reportNull$$$0(6);
        }
        SmartPsiElementPointer<E> smartPsiElementPointerCreateSmartPsiElementPointer = createSmartPsiElementPointer(e, psiFile, false);
        if (smartPsiElementPointerCreateSmartPsiElementPointer == null) {
            $$$reportNull$$$0(7);
        }
        return smartPsiElementPointerCreateSmartPsiElementPointer;
    }

    public <E extends PsiElement> SmartPsiElementPointer<E> createSmartPsiElementPointer(E e, PsiFile psiFile, boolean z) {
        if (e == null) {
            $$$reportNull$$$0(8);
        }
        ensureValid(e, psiFile);
        SmartPointerTracker.processQueue();
        ensureMyProject(psiFile != null ? psiFile.getProject() : e.getProject());
        SmartPsiElementPointerImpl cachedPointer = getCachedPointer(e);
        if (cachedPointer != null && ((!(cachedPointer.getElementInfo() instanceof SelfElementInfo) || ((SelfElementInfo) cachedPointer.getElementInfo()).isForInjected() == z) && cachedPointer.incrementAndGetReferenceCount(1) > 0)) {
            if (cachedPointer == null) {
                $$$reportNull$$$0(9);
            }
            return cachedPointer;
        }
        SmartPsiElementPointerImpl<E> smartPsiElementPointerImpl = new SmartPsiElementPointerImpl<>(this, e, psiFile, z);
        if (psiFile != null) {
            trackPointer(smartPsiElementPointerImpl, psiFile.getViewProvider().getVirtualFile());
        }
        e.putUserData(CACHED_SMART_POINTER_KEY, new SoftReference(smartPsiElementPointerImpl));
        return smartPsiElementPointerImpl;
    }

    private void ensureMyProject(Project project) {
        if (project == null) {
            $$$reportNull$$$0(11);
        }
        if (project != this.myProject) {
            throw new IllegalArgumentException("Element from alien project: " + anonymize(project) + " expected: " + anonymize(this.myProject));
        }
    }

    private static void ensureValid(PsiElement psiElement, PsiFile psiFile) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (psiFile != null ? psiFile.isValid() : psiElement.isValid()) {
            return;
        }
        PsiUtilCore.ensureValid(psiElement);
        if (psiFile != null && !psiFile.isValid()) {
            throw new PsiInvalidElementAccessException(psiFile, "Element " + psiElement.getClass() + "(" + psiElement.getLanguage() + ") claims to be valid but returns invalid containing file ");
        }
    }

    private static <E extends PsiElement> SmartPsiElementPointerImpl<E> getCachedPointer(E e) {
        if (e == null) {
            $$$reportNull$$$0(13);
        }
        SmartPsiElementPointerImpl<E> smartPsiElementPointerImpl = (SmartPsiElementPointerImpl) SoftReference.dereference((Reference) e.getUserData(CACHED_SMART_POINTER_KEY));
        if (smartPsiElementPointerImpl == null || smartPsiElementPointerImpl.getElement() == e) {
            return smartPsiElementPointerImpl;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager
    public SmartPsiFileRange createSmartPsiFileRangePointer(PsiFile psiFile, TextRange textRange) {
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        if (textRange == null) {
            $$$reportNull$$$0(15);
        }
        SmartPsiFileRange smartPsiFileRangeCreateSmartPsiFileRangePointer = createSmartPsiFileRangePointer(psiFile, textRange, false);
        if (smartPsiFileRangeCreateSmartPsiFileRangePointer == null) {
            $$$reportNull$$$0(16);
        }
        return smartPsiFileRangeCreateSmartPsiFileRangePointer;
    }

    public SmartPsiFileRange createSmartPsiFileRangePointer(PsiFile psiFile, TextRange textRange, boolean z) {
        if (psiFile == null) {
            $$$reportNull$$$0(17);
        }
        if (textRange == null) {
            $$$reportNull$$$0(18);
        }
        PsiUtilCore.ensureValid(psiFile);
        SmartPointerTracker.processQueue();
        SmartPsiFileRangePointerImpl smartPsiFileRangePointerImpl = new SmartPsiFileRangePointerImpl(this, psiFile, ProperTextRange.create((Segment) textRange), z);
        trackPointer(smartPsiFileRangePointerImpl, psiFile.getViewProvider().getVirtualFile());
        return smartPsiFileRangePointerImpl;
    }

    private <E extends PsiElement> void trackPointer(SmartPsiElementPointerImpl<E> smartPsiElementPointerImpl, VirtualFile virtualFile) {
        if (smartPsiElementPointerImpl == null) {
            $$$reportNull$$$0(20);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(21);
        }
        if (smartPsiElementPointerImpl.getElementInfo() instanceof SelfElementInfo) {
            SmartPointerTracker tracker = getTracker(virtualFile);
            if (tracker == null) {
                tracker = getOrCreateTracker(virtualFile);
            }
            tracker.addReference(smartPsiElementPointerImpl);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager
    public void removePointer(SmartPsiElementPointer<?> smartPsiElementPointer) {
        if (smartPsiElementPointer == null) {
            $$$reportNull$$$0(22);
        }
        if (!(smartPsiElementPointer instanceof SmartPsiElementPointerImpl) || this.myProject.isDisposed()) {
            return;
        }
        ensureMyProject(smartPsiElementPointer.getProject());
        SmartPsiElementPointerImpl smartPsiElementPointerImpl = (SmartPsiElementPointerImpl) smartPsiElementPointer;
        int iIncrementAndGetReferenceCount = smartPsiElementPointerImpl.incrementAndGetReferenceCount(-1);
        if (iIncrementAndGetReferenceCount == -1) {
            LOG.error("Double smart pointer removal");
            return;
        }
        if (iIncrementAndGetReferenceCount == 0) {
            PsiElement cachedElement = ((SmartPointerEx) smartPsiElementPointer).getCachedElement();
            if (cachedElement != null) {
                cachedElement.putUserData(CACHED_SMART_POINTER_KEY, null);
            }
            smartPsiElementPointerImpl.getElementInfo().cleanup();
            SmartPointerTracker.PointerReference pointerReference = smartPsiElementPointerImpl.pointerReference;
            if (pointerReference != null) {
                if (pointerReference.get() != smartPsiElementPointer) {
                    throw new IllegalStateException("Reference points to " + pointerReference.get());
                }
                pointerReference.tracker.removeReference(pointerReference);
            }
        }
    }

    SmartPointerTracker getTracker(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(23);
        }
        return (SmartPointerTracker) (virtualFile instanceof LightVirtualFile ? SoftReference.dereference((Reference) virtualFile.getUserData(this.LIGHT_TRACKER_KEY)) : this.myPhysicalTrackers.get(virtualFile));
    }

    private SmartPointerTracker getOrCreateTracker(VirtualFile virtualFile) {
        SmartPointerTracker tracker;
        if (virtualFile == null) {
            $$$reportNull$$$0(24);
        }
        synchronized (this.myPhysicalTrackers) {
            tracker = getTracker(virtualFile);
            if (tracker == null) {
                tracker = new SmartPointerTracker();
                if (virtualFile instanceof LightVirtualFile) {
                    virtualFile.putUserData(this.LIGHT_TRACKER_KEY, new WeakReference(tracker));
                } else {
                    this.myPhysicalTrackers.put(virtualFile, tracker);
                }
            }
        }
        if (tracker == null) {
            $$$reportNull$$$0(25);
        }
        return tracker;
    }

    public int getPointersNumber(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(26);
        }
        SmartPointerTracker tracker = getTracker(psiFile.getViewProvider().getVirtualFile());
        if (tracker == null) {
            return 0;
        }
        return tracker.getSize();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager
    public boolean pointToTheSameElement(SmartPsiElementPointer<?> smartPsiElementPointer, SmartPsiElementPointer<?> smartPsiElementPointer2) {
        if (smartPsiElementPointer == null) {
            $$$reportNull$$$0(27);
        }
        if (smartPsiElementPointer2 == null) {
            $$$reportNull$$$0(28);
        }
        return SmartPsiElementPointerImpl.pointsToTheSameElementAs(smartPsiElementPointer, smartPsiElementPointer2);
    }

    public void updatePointers(Document document, FrozenDocument frozenDocument, List<? extends DocumentEvent> list) {
        if (document == null) {
            $$$reportNull$$$0(29);
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(30);
        }
        if (list == null) {
            $$$reportNull$$$0(31);
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        SmartPointerTracker tracker = file == null ? null : getTracker(file);
        if (tracker != null) {
            tracker.updateMarkers(frozenDocument, list);
        }
    }

    public void updatePointerTargetsAfterReparse(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(32);
        }
        SmartPointerTracker tracker = getTracker(virtualFile);
        if (tracker != null) {
            tracker.updatePointerTargetsAfterReparse();
        }
    }

    Project getProject() {
        Project project = this.myProject;
        if (project == null) {
            $$$reportNull$$$0(33);
        }
        return project;
    }

    PsiDocumentManagerBase getPsiDocumentManager() {
        PsiDocumentManagerBase psiDocumentManagerBase = this.myPsiDocManager;
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(34);
        }
        return psiDocumentManagerBase;
    }
}
