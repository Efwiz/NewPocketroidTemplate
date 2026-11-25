package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullUtils;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiIntersectionType extends PsiType.Stub {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PsiType[] myConjuncts;

    /* JADX WARN: Code duplicated, block: B:25:0x0040  */
    /* JADX WARN: Code duplicated, block: B:9:0x0014  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 5 && i != 14 && i != 15) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 5 && i != 14 && i != 15) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 3 && i != 5) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 14:
                case 15:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiIntersectionType";
                    break;
                case 12:
                    objArr[0] = "text";
                    break;
                case 13:
                    objArr[0] = "visitor";
                    break;
                default:
                    objArr[0] = "conjuncts";
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiIntersectionType";
        }
        if (i == 3) {
            objArr[1] = "createIntersection";
        } else if (i == 5) {
            objArr[1] = "flattenAndRemoveDuplicates";
        } else if (i == 14) {
            objArr[1] = "getSuperTypes";
        } else if (i != 15) {
            switch (i) {
                case 7:
                    objArr[1] = "flatten";
                    break;
                case 8:
                    objArr[1] = "getConjuncts";
                    break;
                case 9:
                    objArr[1] = "getPresentableText";
                    break;
                case 10:
                    objArr[1] = "getCanonicalText";
                    break;
                case 11:
                    objArr[1] = "getInternalCanonicalText";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiIntersectionType";
                    break;
            }
        } else {
            objArr[1] = "getRepresentative";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createIntersection";
                break;
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
                break;
            case 4:
                objArr[2] = "flattenAndRemoveDuplicates";
                break;
            case 6:
                objArr[2] = "flatten";
                break;
            case 12:
                objArr[2] = "equalsToText";
                break;
            case 13:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 14 && i != 15) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(12);
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PsiIntersectionType(PsiType[] psiTypeArr) {
        super(TypeAnnotationProvider.EMPTY);
        if (psiTypeArr == null) {
            $$$reportNull$$$0(0);
        }
        if (NullUtils.hasNull(psiTypeArr)) {
            throw new IllegalArgumentException("Null conjunct");
        }
        this.myConjuncts = psiTypeArr;
    }

    public static PsiType createIntersection(List<PsiType> list) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        return createIntersection((PsiType[]) list.toArray(createArray(list.size())));
    }

    public static PsiType createIntersection(PsiType... psiTypeArr) {
        return createIntersection(true, psiTypeArr);
    }

    public static PsiType createIntersection(boolean z, PsiType... psiTypeArr) {
        if (psiTypeArr == null) {
            $$$reportNull$$$0(2);
        }
        if (z) {
            psiTypeArr = flattenAndRemoveDuplicates(psiTypeArr);
        }
        if (psiTypeArr.length != 1) {
            return new PsiIntersectionType(psiTypeArr);
        }
        PsiType psiType = psiTypeArr[0];
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        return psiType;
    }

    private static PsiType[] flattenAndRemoveDuplicates(PsiType[] psiTypeArr) {
        if (psiTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        try {
            Set<PsiType> setFlatten = flatten(psiTypeArr, new LinkedHashSet());
            PsiType[] psiTypeArr2 = (PsiType[]) setFlatten.toArray(createArray(setFlatten.size()));
            if (psiTypeArr2 == null) {
                $$$reportNull$$$0(5);
            }
            return psiTypeArr2;
        } catch (NoSuchElementException e) {
            throw new RuntimeException(Arrays.toString(psiTypeArr), e);
        }
    }

    public static Set<PsiType> flatten(PsiType[] psiTypeArr, Set<PsiType> set) {
        if (psiTypeArr == null) {
            $$$reportNull$$$0(6);
        }
        for (PsiType psiType : psiTypeArr) {
            if (psiType instanceof PsiIntersectionType) {
                flatten(((PsiIntersectionType) psiType).getConjuncts(), set);
            } else {
                set.add(psiType);
            }
        }
        if (set.size() > 1) {
            PsiType[] psiTypeArr2 = (PsiType[]) set.toArray(createArray(set.size()));
            Iterator<PsiType> it = set.iterator();
            while (it.hasNext()) {
                PsiType next = it.next();
                for (PsiType psiType2 : psiTypeArr2) {
                    if (next != psiType2) {
                        if (TypeConversionUtil.isAssignable(next, psiType2, (next instanceof PsiClassType) && ((PsiClassType) next).isRaw())) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
            if (set.isEmpty()) {
                set.add(psiTypeArr2[0]);
            }
        }
        if (set == null) {
            $$$reportNull$$$0(7);
        }
        return set;
    }

    public PsiType[] getConjuncts() {
        PsiType[] psiTypeArr = this.myConjuncts;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(final boolean z) {
        String strJoin = StringUtil.join(this.myConjuncts, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getPresentableText(z);
            }
        }, " & ");
        if (strJoin == null) {
            $$$reportNull$$$0(9);
        }
        return strJoin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String canonicalText = this.myConjuncts[0].getCanonicalText(z);
        if (canonicalText == null) {
            $$$reportNull$$$0(10);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String strJoin = StringUtil.join(this.myConjuncts, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getInternalCanonicalText();
            }
        }, " & ");
        if (strJoin == null) {
            $$$reportNull$$$0(11);
        }
        return strJoin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        for (PsiType psiType : this.myConjuncts) {
            if (!psiType.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(13);
        }
        return psiTypeVisitor.visitIntersectionType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return this.myConjuncts[0].getResolveScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType[] psiTypeArr = this.myConjuncts;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(14);
        }
        return psiTypeArr;
    }

    public PsiType getRepresentative() {
        PsiType psiType = this.myConjuncts[0];
        if (psiType == null) {
            $$$reportNull$$$0(15);
        }
        return psiType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PsiIntersectionType)) {
            return false;
        }
        PsiType[] conjuncts = getConjuncts();
        PsiType[] conjuncts2 = ((PsiIntersectionType) obj).getConjuncts();
        if (conjuncts.length != conjuncts2.length) {
            return false;
        }
        for (int i = 0; i < conjuncts.length; i++) {
            if (!conjuncts[i].equals(conjuncts2[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.myConjuncts[0].hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String toString() {
        return (String) Arrays.stream(this.myConjuncts).map(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PsiType) obj).getPresentableText();
            }
        }).collect(Collectors.joining(", ", "PsiIntersectionType: ", ""));
    }

    public String getConflictingConjunctsMessage() {
        PsiType[] conjuncts = getConjuncts();
        for (int i = 0; i < conjuncts.length; i++) {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(conjuncts[i]);
            if (psiClassResolveClassInClassTypeOnly != null && !psiClassResolveClassInClassTypeOnly.isInterface()) {
                for (int i2 = i + 1; i2 < conjuncts.length; i2++) {
                    PsiClass psiClassResolveClassInClassTypeOnly2 = PsiUtil.resolveClassInClassTypeOnly(conjuncts[i2]);
                    if (psiClassResolveClassInClassTypeOnly2 != null && !psiClassResolveClassInClassTypeOnly2.isInterface() && !psiClassResolveClassInClassTypeOnly.isInheritor(psiClassResolveClassInClassTypeOnly2, true) && !psiClassResolveClassInClassTypeOnly2.isInheritor(psiClassResolveClassInClassTypeOnly, true)) {
                        return JavaPsiBundle.message("conflicting.conjuncts", conjuncts[i].getPresentableText(), conjuncts[i2].getPresentableText());
                    }
                }
            }
        }
        return null;
    }
}
