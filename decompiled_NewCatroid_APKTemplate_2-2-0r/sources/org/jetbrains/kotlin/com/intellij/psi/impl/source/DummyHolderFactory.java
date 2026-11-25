package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public final class DummyHolderFactory {
    private static HolderFactory INSTANCE = new DefaultFactory();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13) ? 2 : 3];
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolderFactory";
        } else {
            objArr[0] = "manager";
        }
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13) {
            objArr[1] = "createHolder";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolderFactory";
        }
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 9 && i != 11 && i != 13) {
            objArr[2] = "createHolder";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 9 && i != 11 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private DummyHolderFactory() {
    }

    public static void setFactory(HolderFactory holderFactory) {
        INSTANCE = holderFactory;
    }

    public static DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, treeElement, psiElement);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(1);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, CharTable charTable, boolean z) {
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, charTable, z);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(3);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, psiElement);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(5);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, Language language, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(6);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, language, psiElement);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(7);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable) {
        if (psiManager == null) {
            $$$reportNull$$$0(8);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, treeElement, psiElement, charTable);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(9);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable) {
        if (psiManager == null) {
            $$$reportNull$$$0(10);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, psiElement, charTable);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(11);
        }
        return dummyHolderCreateHolder;
    }

    public static DummyHolder createHolder(PsiManager psiManager, CharTable charTable, Language language) {
        if (psiManager == null) {
            $$$reportNull$$$0(12);
        }
        DummyHolder dummyHolderCreateHolder = INSTANCE.createHolder(psiManager, charTable, language);
        if (dummyHolderCreateHolder == null) {
            $$$reportNull$$$0(13);
        }
        return dummyHolderCreateHolder;
    }

    private static class DefaultFactory implements HolderFactory {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "manager", "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolderFactory$DefaultFactory", "createHolder"));
        }

        private DefaultFactory() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement) {
            if (psiManager == null) {
                $$$reportNull$$$0(0);
            }
            return new DummyHolder(psiManager, treeElement, psiElement);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, CharTable charTable, boolean z) {
            if (psiManager == null) {
                $$$reportNull$$$0(1);
            }
            return new DummyHolder(psiManager, charTable, z);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement) {
            if (psiManager == null) {
                $$$reportNull$$$0(2);
            }
            return new DummyHolder(psiManager, psiElement);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, Language language, PsiElement psiElement) {
            if (psiManager == null) {
                $$$reportNull$$$0(3);
            }
            return new DummyHolder(psiManager, language, psiElement);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable) {
            if (psiManager == null) {
                $$$reportNull$$$0(4);
            }
            return new DummyHolder(psiManager, treeElement, psiElement, charTable);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable) {
            if (psiManager == null) {
                $$$reportNull$$$0(5);
            }
            return new DummyHolder(psiManager, psiElement, charTable);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
        public DummyHolder createHolder(PsiManager psiManager, CharTable charTable, Language language) {
            if (psiManager == null) {
                $$$reportNull$$$0(6);
            }
            return new DummyHolder(psiManager, charTable, language);
        }
    }
}
