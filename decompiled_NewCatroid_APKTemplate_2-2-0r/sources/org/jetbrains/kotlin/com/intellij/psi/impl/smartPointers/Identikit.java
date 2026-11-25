package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.Objects;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Identikit {
    private static final Interner<ByType> ourPlainInterner = Interner.createWeakInterner();
    private static final Interner<ByAnchor> ourAnchorInterner = Interner.createWeakInterner();

    /* JADX WARN: Code duplicated, block: B:19:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "fileLanguage";
        } else if (i == 4) {
            objArr[0] = "elementClass";
        } else if (i == 5) {
            objArr[0] = "fileLanguage";
        } else if (i != 6) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/Identikit";
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/Identikit";
        } else {
            objArr[1] = "fromTypes";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "withAnchor";
        } else if (i == 4 || i == 5) {
            objArr[2] = "fromTypes";
        } else if (i != 6) {
            objArr[2] = "fromPsi";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract PsiElement findPsiElement(PsiFile psiFile, int i, int i2);

    public abstract Language getFileLanguage();

    public abstract boolean isForPsiFile();

    public static ByType fromPsi(PsiElement psiElement, Language language) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        return fromTypes(psiElement.getClass(), PsiUtilCore.getElementType(psiElement), language);
    }

    static Pair<ByAnchor, PsiElement> withAnchor(PsiElement psiElement, Language language) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        PsiUtilCore.ensureValid(psiElement);
        if (!psiElement.getIsPhysical()) {
            return null;
        }
        for (SmartPointerAnchorProvider smartPointerAnchorProvider : SmartPointerAnchorProvider.EP_NAME.getExtensions()) {
            PsiElement anchor = smartPointerAnchorProvider.getAnchor(psiElement);
            if (anchor != null && anchor.getIsPhysical() && smartPointerAnchorProvider.restoreElement(anchor) == psiElement) {
                return Pair.create(ourAnchorInterner.intern(new ByAnchor(fromPsi(psiElement, language), fromPsi(anchor, language), smartPointerAnchorProvider)), anchor);
            }
        }
        return null;
    }

    static ByType fromTypes(Class<? extends PsiElement> cls, IElementType iElementType, Language language) {
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        ByType byTypeIntern = ourPlainInterner.intern(new ByType(cls, iElementType, language));
        if (byTypeIntern == null) {
            $$$reportNull$$$0(6);
        }
        return byTypeIntern;
    }

    public static final class ByType extends Identikit {
        private final String myElementClassName;
        private final short myElementTypeId;
        private final String myFileLanguageId;

        /* JADX WARN: Code duplicated, block: B:10:0x001c  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "fileLanguage";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i == 3) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else if (i == 4) {
                objArr[0] = "anchor";
            } else if (i != 5) {
                objArr[0] = "elementClass";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/Identikit$ByType";
            if (i == 2) {
                objArr[2] = "findPsiElement";
            } else if (i == 3) {
                objArr[2] = "findInside";
            } else if (i == 4) {
                objArr[2] = "findParent";
            } else if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "isAcceptable";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ByType(Class<? extends PsiElement> cls, IElementType iElementType, Language language) {
            if (cls == null) {
                $$$reportNull$$$0(0);
            }
            if (language == null) {
                $$$reportNull$$$0(1);
            }
            this.myElementClassName = cls.getName();
            this.myElementTypeId = iElementType != null ? iElementType.getIndex() : (short) -1;
            this.myFileLanguageId = language.getID();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public PsiElement findPsiElement(PsiFile psiFile, int i, int i2) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            Language languageFindLanguageByID = Language.findLanguageByID(this.myFileLanguageId);
            if (languageFindLanguageByID == null) {
                return null;
            }
            if (languageFindLanguageByID == Language.ANY) {
                languageFindLanguageByID = psiFile.getViewProvider().getBaseLanguage();
            }
            PsiFile psi = psiFile.getViewProvider().getPsi(languageFindLanguageByID);
            if (psi == null) {
                return null;
            }
            return findInside(psi, i, i2);
        }

        public PsiElement findInside(PsiElement psiElement, int i, int i2) {
            if (psiElement == null) {
                $$$reportNull$$$0(3);
            }
            PsiElement psiElementFindElementAt = AbstractFileViewProvider.findElementAt(psiElement, i);
            if (psiElementFindElementAt == null && i == psiElement.getTextLength()) {
                psiElementFindElementAt = PsiTreeUtil.getDeepestLast(psiElement);
            }
            if (psiElementFindElementAt == null) {
                return null;
            }
            PsiElement psiElementFindParent = findParent(i, i2, psiElementFindElementAt);
            if (i2 == i) {
                while (true) {
                    if ((psiElementFindParent != null && psiElementFindParent.getTextRange().getStartOffset() == i) || psiElementFindElementAt.getTextRange().getStartOffset() != i2) {
                        break;
                    }
                    psiElementFindElementAt = PsiTreeUtil.prevLeaf(psiElementFindElementAt, false);
                    if (psiElementFindElementAt == null) {
                        break;
                    }
                    psiElementFindParent = findParent(i, i2, psiElementFindElementAt);
                }
            }
            return psiElementFindParent;
        }

        private PsiElement findParent(int i, int i2, PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            TextRange textRange = psiElement.getTextRange();
            if (textRange.getStartOffset() != i) {
                return null;
            }
            while (textRange.getEndOffset() < i2) {
                psiElement = psiElement.getLightParent();
                if (psiElement == null || (psiElement instanceof PsiDirectory)) {
                    return null;
                }
                textRange = psiElement.getTextRange();
            }
            while (textRange.getEndOffset() == i2) {
                if (isAcceptable(psiElement)) {
                    return psiElement;
                }
                psiElement = psiElement.getLightParent();
                if (psiElement == null || (psiElement instanceof PsiDirectory)) {
                    break;
                }
                textRange = psiElement.getTextRange();
            }
            return null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ByType byType = (ByType) obj;
            return this.myElementTypeId == byType.myElementTypeId && Objects.equals(this.myElementClassName, byType.myElementClassName) && Objects.equals(this.myFileLanguageId, byType.myFileLanguageId);
        }

        public int hashCode() {
            return Objects.hash(this.myElementClassName, Short.valueOf(this.myElementTypeId), this.myFileLanguageId);
        }

        public String toString() {
            return "Identikit(class='" + this.myElementClassName + "', elementType=" + ((int) this.myElementTypeId) + ", fileLanguage='" + this.myFileLanguageId + "')";
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public Language getFileLanguage() {
            return Language.findLanguageByID(this.myFileLanguageId);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public boolean isForPsiFile() {
            short s = this.myElementTypeId;
            if (s < 0) {
                return false;
            }
            return IElementType.find(s) instanceof IFileElementType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isAcceptable(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(5);
            }
            IElementType elementType = PsiUtilCore.getElementType(psiElement);
            return this.myElementClassName.equals(psiElement.getClass().getName()) && elementType != null && this.myElementTypeId == elementType.getIndex();
        }
    }

    static final class ByAnchor extends Identikit {
        private final ByType myAnchorInfo;
        private final SmartPointerAnchorProvider myAnchorProvider;
        private final ByType myElementInfo;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "anchorInfo";
            } else if (i == 2) {
                objArr[0] = "anchorProvider";
            } else if (i != 3) {
                objArr[0] = "elementInfo";
            } else {
                objArr[0] = "file";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/Identikit$ByAnchor";
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "findPsiElement";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        ByAnchor(ByType byType, ByType byType2, SmartPointerAnchorProvider smartPointerAnchorProvider) {
            if (byType == null) {
                $$$reportNull$$$0(0);
            }
            if (byType2 == null) {
                $$$reportNull$$$0(1);
            }
            if (smartPointerAnchorProvider == null) {
                $$$reportNull$$$0(2);
            }
            this.myElementInfo = byType;
            this.myAnchorInfo = byType2;
            this.myAnchorProvider = smartPointerAnchorProvider;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ByAnchor)) {
                return false;
            }
            ByAnchor byAnchor = (ByAnchor) obj;
            return this.myElementInfo.equals(byAnchor.myElementInfo) && this.myAnchorInfo.equals(byAnchor.myAnchorInfo) && this.myAnchorProvider.equals(byAnchor.myAnchorProvider);
        }

        public int hashCode() {
            return this.myElementInfo.hashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public PsiElement findPsiElement(PsiFile psiFile, int i, int i2) {
            if (psiFile == null) {
                $$$reportNull$$$0(3);
            }
            PsiElement psiElementFindPsiElement = this.myAnchorInfo.findPsiElement(psiFile, i, i2);
            PsiElement psiElementRestoreElement = psiElementFindPsiElement == null ? null : this.myAnchorProvider.restoreElement(psiElementFindPsiElement);
            if (psiElementRestoreElement == null || !this.myElementInfo.isAcceptable(psiElementRestoreElement)) {
                return null;
            }
            return psiElementRestoreElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public Language getFileLanguage() {
            return this.myAnchorInfo.getFileLanguage();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit
        public boolean isForPsiFile() {
            return this.myAnchorInfo.isForPsiFile();
        }
    }
}
