package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnchor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class AnchorElementInfo extends SelfElementInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile long myStubElementTypeAndId;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "containingFile";
                break;
            case 2:
                objArr[0] = "identikit";
                break;
            case 3:
            default:
                objArr[0] = "anchor";
                break;
            case 5:
                objArr[0] = "stubElementType";
                break;
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
                objArr[0] = "manager";
                break;
            case 7:
                objArr[0] = "other";
                break;
            case 11:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/AnchorElementInfo";
        switch (i) {
            case 6:
                objArr[2] = "restoreElement";
                break;
            case 7:
            case 8:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 9:
                objArr[2] = "fastenBelt";
                break;
            case 10:
                objArr[2] = "switchToTree";
                break;
            case 11:
                objArr[2] = "switchToTreeRange";
                break;
            case 12:
                objArr[2] = "getRange";
                break;
            case 13:
                objArr[2] = "getPsiRange";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AnchorElementInfo(PsiElement psiElement, PsiFile psiFile, Identikit.ByAnchor byAnchor) {
        super(ProperTextRange.create((Segment) psiElement.getTextRange()), byAnchor, psiFile, false);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (byAnchor == null) {
            $$$reportNull$$$0(2);
        }
        this.myStubElementTypeAndId = pack(-1, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AnchorElementInfo(PsiElement psiElement, PsiFileWithStubSupport psiFileWithStubSupport, int i, IStubElementType<?, ?> iStubElementType) {
        super(null, Identikit.fromTypes(psiElement.getClass(), iStubElementType, LanguageUtil.getRootLanguage(psiFileWithStubSupport)), psiFileWithStubSupport, false);
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (psiFileWithStubSupport == null) {
            $$$reportNull$$$0(4);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(5);
        }
        this.myStubElementTypeAndId = pack(i, iStubElementType);
    }

    private static long pack(int i, IStubElementType<?, ?> iStubElementType) {
        return (((long) (iStubElementType == null ? (short) 0 : iStubElementType.getIndex())) << 32) | ((long) i);
    }

    private int getStubId() {
        return (int) this.myStubElementTypeAndId;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo, org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        long j = this.myStubElementTypeAndId;
        int i = (int) j;
        if (i != -1) {
            PsiFile psiFileRestoreFile = restoreFile(smartPointerManagerImpl);
            if (!(psiFileRestoreFile instanceof PsiFileWithStubSupport)) {
                return null;
            }
            return PsiAnchor.restoreFromStubIndex((PsiFileWithStubSupport) psiFileRestoreFile, i, (IStubElementType) IElementType.find((short) (j >> 32)), false);
        }
        return super.restoreElement(smartPointerManagerImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo, org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public boolean pointsToTheSameElementAs(final SmartPointerElementInfo smartPointerElementInfo, final SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(7);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(8);
        }
        if (smartPointerElementInfo instanceof AnchorElementInfo) {
            if (!getVirtualFile().equals(smartPointerElementInfo.getVirtualFile())) {
                return false;
            }
            long j = this.myStubElementTypeAndId;
            long j2 = ((AnchorElementInfo) smartPointerElementInfo).myStubElementTypeAndId;
            if (j != -1 && j2 != -1) {
                return j == j2;
            }
            if (j != -1 || j2 != -1) {
                return ((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.AnchorElementInfo$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return this.f$0.m6630xf4cac425(smartPointerManagerImpl, smartPointerElementInfo);
                    }
                })).booleanValue();
            }
        }
        return super.pointsToTheSameElementAs(smartPointerElementInfo, smartPointerManagerImpl);
    }

    /* JADX INFO: renamed from: lambda$pointsToTheSameElementAs$0$org-jetbrains-kotlin-com-intellij-psi-impl-smartPointers-AnchorElementInfo, reason: not valid java name */
    /* synthetic */ Boolean m6630xf4cac425(SmartPointerManagerImpl smartPointerManagerImpl, SmartPointerElementInfo smartPointerElementInfo) throws RuntimeException {
        return Boolean.valueOf(Comparing.equal(restoreElement(smartPointerManagerImpl), smartPointerElementInfo.restoreElement(smartPointerManagerImpl)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public void fastenBelt(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(9);
        }
        if (getStubId() != -1) {
            switchToTree(smartPointerManagerImpl);
        }
        super.fastenBelt(smartPointerManagerImpl);
    }

    private void switchToTree(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(10);
        }
        PsiElement psiElementRestoreElement = restoreElement(smartPointerManagerImpl);
        SmartPointerTracker tracker = smartPointerManagerImpl.getTracker(getVirtualFile());
        if (psiElementRestoreElement == null || tracker == null) {
            return;
        }
        tracker.switchStubToAst(this, psiElementRestoreElement);
    }

    void switchToTreeRange(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        switchToAnchor(psiElement);
        this.myStubElementTypeAndId = pack(-1, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo, org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(12);
        }
        if (getStubId() != -1) {
            switchToTree(smartPointerManagerImpl);
        }
        return super.getRange(smartPointerManagerImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo, org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public TextRange getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(13);
        }
        if (getStubId() != -1) {
            switchToTree(smartPointerManagerImpl);
        }
        return super.getPsiRange(smartPointerManagerImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo
    public String toString() {
        return super.toString() + ",stubId=" + getStubId();
    }
}
