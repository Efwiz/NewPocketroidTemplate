package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.SimpleModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiModificationTrackerImpl implements PsiTreeChangePreprocessor, PsiModificationTracker {
    private final SimpleModificationTracker myAllLanguagesTracker;
    private final Map<Language, SimpleModificationTracker> myLanguageTrackers;
    private final SimpleModificationTracker myModificationCount;
    private final PsiModificationTracker.Listener myPublisher;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "event";
                break;
            case 4:
            case 6:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiModificationTrackerImpl";
                break;
            case 5:
                objArr[0] = "language";
                break;
            case 7:
                objArr[0] = "condition";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 4) {
            objArr[1] = "getJavaStructureModificationTracker";
        } else if (i == 6) {
            objArr[1] = "forLanguage";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiModificationTrackerImpl";
        } else {
            objArr[1] = "forLanguages";
        }
        switch (i) {
            case 1:
                objArr[2] = "treeChanged";
                break;
            case 2:
                objArr[2] = "canAffectPsi";
                break;
            case 3:
                objArr[2] = "incLanguageCounters";
                break;
            case 4:
            case 6:
            case 8:
                break;
            case 5:
                objArr[2] = "forLanguage";
                break;
            case 7:
                objArr[2] = "forLanguages";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ SimpleModificationTracker lambda$new$0(Language language) {
        return new SimpleModificationTracker();
    }

    public PsiModificationTrackerImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myModificationCount = new SimpleModificationTracker();
        this.myAllLanguagesTracker = new SimpleModificationTracker();
        this.myLanguageTrackers = ConcurrentFactoryMap.createWeakMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return PsiModificationTrackerImpl.lambda$new$0((Language) obj);
            }
        });
        MessageBus messageBus = project.getMessageBus();
        this.myPublisher = (PsiModificationTracker.Listener) messageBus.syncPublisher(TOPIC);
        messageBus.connect().subscribe(DumbService.DUMB_MODE, new DumbService.DumbModeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl.1
            public void enteredDumbMode() {
                PsiModificationTrackerImpl.this.doIncCounter();
            }

            public void exitDumbMode() {
                PsiModificationTrackerImpl.this.doIncCounter();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doIncCounter() {
        ApplicationManager.getApplication().runWriteAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6570x5816776d();
            }
        });
    }

    @Deprecated
    public void incCounter() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        m6570x5816776d();
    }

    @Deprecated
    public void incOutOfCodeBlockModificationCounter() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        m6570x5816776d();
    }

    private void fireEvent() {
        ((TransactionGuardImpl) TransactionGuard.getInstance()).assertWriteActionAllowed();
        this.myPublisher.modificationCountChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: incCountersInner, reason: merged with bridge method [inline-methods] */
    public void m6570x5816776d() {
        this.myModificationCount.incModificationCount();
        fireEvent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangePreprocessor
    public void treeChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(1);
        }
        if (canAffectPsi(psiTreeChangeEventImpl)) {
            incLanguageCounters(psiTreeChangeEventImpl);
            m6570x5816776d();
        }
    }

    public static boolean canAffectPsi(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(2);
        }
        PsiTreeChangeEventImpl.PsiEventType code = psiTreeChangeEventImpl.getCode();
        return (code == PsiTreeChangeEventImpl.PsiEventType.BEFORE_PROPERTY_CHANGE || (code == PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED && psiTreeChangeEventImpl.getPropertyName() == "writable")) ? false : true;
    }

    private void incLanguageCounters(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(3);
        }
        PsiTreeChangeEventImpl.PsiEventType code = psiTreeChangeEventImpl.getCode();
        String propertyName = psiTreeChangeEventImpl.getPropertyName();
        if ((code == PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED && (propertyName == PsiTreeChangeEvent.PROP_UNLOADED_PSI || propertyName == PsiTreeChangeEvent.PROP_ROOTS || propertyName == PsiTreeChangeEvent.PROP_FILE_TYPES)) || (code == PsiTreeChangeEventImpl.PsiEventType.CHILD_REMOVED && (psiTreeChangeEventImpl.getChild() instanceof PsiDirectory))) {
            this.myAllLanguagesTracker.incModificationCount();
            return;
        }
        PsiElement[] psiElementArr = {psiTreeChangeEventImpl.getFile(), psiTreeChangeEventImpl.getParent(), psiTreeChangeEventImpl.getOldParent(), psiTreeChangeEventImpl.getNewParent(), psiTreeChangeEventImpl.getElement(), psiTreeChangeEventImpl.getChild(), psiTreeChangeEventImpl.getOldChild(), psiTreeChangeEventImpl.getNewChild()};
        incLanguageModificationCount(Language.ANY);
        for (int i = 0; i < 8; i++) {
            PsiElement psiElement = psiElementArr[i];
            if (psiElement != null && !(psiElement instanceof PsiDirectory)) {
                if (psiElement instanceof PsiFile) {
                    Iterator<Language> it = ((PsiFile) psiElement).getViewProvider().getLanguages().iterator();
                    while (it.hasNext()) {
                        incLanguageModificationCount(it.next());
                    }
                } else {
                    try {
                        IElementType elementType = PsiUtilCore.getElementType(psiElement);
                        incLanguageModificationCount(elementType != null ? elementType.getLanguage() : psiElement.getLanguage());
                    } catch (PsiInvalidElementAccessException e) {
                        PsiDocumentManagerBase.LOG.warn(e);
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker, org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        return this.myModificationCount.getModificationCount();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker
    public ModificationTracker getJavaStructureModificationTracker() {
        SimpleModificationTracker simpleModificationTracker = this.myModificationCount;
        if (simpleModificationTracker == null) {
            $$$reportNull$$$0(4);
        }
        return simpleModificationTracker;
    }

    public void incLanguageModificationCount(Language language) {
        if (language == null) {
            return;
        }
        this.myLanguageTrackers.get(language).incModificationCount();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker
    public ModificationTracker forLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        final SimpleModificationTracker simpleModificationTracker = this.myLanguageTrackers.get(language);
        return new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
            public final long getModificationCount() {
                return this.f$0.m6571x7e07a1ac(simpleModificationTracker);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$forLanguage$2$org-jetbrains-kotlin-com-intellij-psi-impl-PsiModificationTrackerImpl, reason: not valid java name */
    /* synthetic */ long m6571x7e07a1ac(SimpleModificationTracker simpleModificationTracker) {
        return simpleModificationTracker.getModificationCount() + this.myAllLanguagesTracker.getModificationCount();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker
    public ModificationTracker forLanguages(final Predicate<? super Language> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(7);
        }
        return new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
            public final long getModificationCount() {
                return this.f$0.m6572xd4ac4318(predicate);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$forLanguages$3$org-jetbrains-kotlin-com-intellij-psi-impl-PsiModificationTrackerImpl, reason: not valid java name */
    /* synthetic */ long m6572xd4ac4318(Predicate predicate) {
        long modificationCount = this.myAllLanguagesTracker.getModificationCount();
        for (Language language : this.myLanguageTrackers.keySet()) {
            if (predicate.test(language)) {
                modificationCount += this.myLanguageTrackers.get(language).getModificationCount();
            }
        }
        return modificationCount;
    }
}
