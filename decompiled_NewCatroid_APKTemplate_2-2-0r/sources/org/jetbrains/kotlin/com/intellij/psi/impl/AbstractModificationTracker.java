package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public abstract class AbstractModificationTracker implements PsiTreeChangePreprocessor {
    private PsiModificationTrackerImpl myModificationTracker;
    private final PsiManagerImpl myPsiManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "org/jetbrains/kotlin/com/intellij/psi/impl/AbstractModificationTracker", "treeChanged"));
    }

    protected abstract boolean isInsideCodeBlock(PsiElement psiElement);

    public AbstractModificationTracker(PsiManagerImpl psiManagerImpl) {
        this.myPsiManager = psiManagerImpl;
    }

    public PsiManagerImpl getPsiManager() {
        return this.myPsiManager;
    }

    protected void initTracker() {
        this.myModificationTracker = (PsiModificationTrackerImpl) this.myPsiManager.getModificationTracker();
        this.myPsiManager.addTreeChangePreprocessor(this);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.AbstractModificationTracker$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType;

        static {
            int[] iArr = new int[PsiTreeChangeEventImpl.PsiEventType.values().length];
            $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType = iArr;
            try {
                iArr[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILDREN_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILDREN_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_ADDITION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REMOVAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_ADDED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_REMOVED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REPLACEMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_REPLACED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_MOVEMENT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_MOVED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_PROPERTY_CHANGE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:26:0x0067  */
    /* JADX WARN: Code duplicated, block: B:27:0x0069  */
    /* JADX WARN: Code duplicated, block: B:30:0x0070  */
    /* JADX WARN: Code duplicated, block: B:32:0x007a  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangePreprocessor
    public void treeChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        boolean zIsInsideCodeBlock = false;
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(0);
        }
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[psiTreeChangeEventImpl.getCode().ordinal()]) {
            case 1:
                if (psiTreeChangeEventImpl.getParent() instanceof PsiFile) {
                    zIsInsideCodeBlock = true;
                } else if (psiTreeChangeEventImpl.isGenericChange()) {
                    zIsInsideCodeBlock = isInsideCodeBlock(psiTreeChangeEventImpl.getParent());
                }
                if (!zIsInsideCodeBlock) {
                    processOutOfCodeBlockModification(psiTreeChangeEventImpl);
                }
                break;
            case 2:
                if (psiTreeChangeEventImpl.isGenericChange()) {
                    zIsInsideCodeBlock = isInsideCodeBlock(psiTreeChangeEventImpl.getParent());
                    if (!zIsInsideCodeBlock) {
                        processOutOfCodeBlockModification(psiTreeChangeEventImpl);
                    }
                    break;
                }
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                if (isInsideCodeBlock(psiTreeChangeEventImpl.getParent()) && isInsideCodeBlock(psiTreeChangeEventImpl.getChild()) && isInsideCodeBlock(psiTreeChangeEventImpl.getOldChild()) && isInsideCodeBlock(psiTreeChangeEventImpl.getNewChild())) {
                    zIsInsideCodeBlock = true;
                }
                if (!zIsInsideCodeBlock) {
                    processOutOfCodeBlockModification(psiTreeChangeEventImpl);
                }
                break;
            case 9:
            case 10:
                if (isInsideCodeBlock(psiTreeChangeEventImpl.getOldParent()) && isInsideCodeBlock(psiTreeChangeEventImpl.getNewParent()) && isInsideCodeBlock(psiTreeChangeEventImpl.getChild())) {
                    zIsInsideCodeBlock = true;
                }
                if (!zIsInsideCodeBlock) {
                    processOutOfCodeBlockModification(psiTreeChangeEventImpl);
                }
                break;
            case 11:
            case 12:
            default:
                if (!zIsInsideCodeBlock) {
                    processOutOfCodeBlockModification(psiTreeChangeEventImpl);
                }
                break;
        }
    }

    protected void processOutOfCodeBlockModification(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        this.myModificationTracker.incOutOfCodeBlockModificationCounter();
    }
}
