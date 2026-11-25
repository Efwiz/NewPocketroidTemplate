package org.jetbrains.kotlin.com.intellij.psi;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDisjunctionType extends PsiType.Stub {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CachedValue<PsiType> myLubCache;
    private final PsiManager myManager;
    private final List<PsiType> myTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            case 10:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
                i2 = 2;
                break;
            case 9:
            case 10:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "psiManager";
                break;
            case 2:
            default:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiDisjunctionType";
                break;
            case 9:
                objArr[0] = "text";
                break;
            case 10:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getLeastUpperBound";
                break;
            case 5:
                objArr[1] = "getDisjunctions";
                break;
            case 6:
                objArr[1] = "getPresentableText";
                break;
            case 7:
                objArr[1] = "getCanonicalText";
                break;
            case 8:
                objArr[1] = "getInternalCanonicalText";
                break;
            case 9:
            case 10:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDisjunctionType";
                break;
            case 11:
            case 12:
                objArr[1] = "getSuperTypes";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "createDisjunction";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
                break;
            case 9:
                objArr[2] = "equalsToText";
                break;
            case 10:
                objArr[2] = "accept";
                break;
            case 13:
                objArr[2] = "flattenAndRemoveDuplicates";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            case 9:
            case 10:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiDisjunctionType(List<PsiType> list, PsiManager psiManager) {
        super(TypeAnnotationProvider.EMPTY);
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        this.myManager = psiManager;
        this.myTypes = Collections.unmodifiableList(list);
        this.myLubCache = CachedValuesManager.getManager(psiManager.getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6529x6de7b1f0();
            }
        }, false);
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-PsiDisjunctionType, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6529x6de7b1f0() {
        PsiType leastUpperBound = this.myTypes.get(0);
        for (int i = 1; i < this.myTypes.size(); i++) {
            leastUpperBound = GenericsUtil.getLeastUpperBound(leastUpperBound, this.myTypes.get(i), this.myManager);
            if (leastUpperBound == null) {
                PsiManager psiManager = this.myManager;
                leastUpperBound = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
                break;
            }
        }
        return CachedValueProvider.Result.create(leastUpperBound, PsiModificationTracker.MODIFICATION_COUNT);
    }

    public static PsiType createDisjunction(List<PsiType> list, PsiManager psiManager) {
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        return list.size() == 1 ? list.get(0) : new PsiDisjunctionType(list, psiManager);
    }

    public PsiType getLeastUpperBound() {
        PsiType value = this.myLubCache.getValue();
        if (value == null) {
            $$$reportNull$$$0(4);
        }
        return value;
    }

    public List<PsiType> getDisjunctions() {
        List<PsiType> list = this.myTypes;
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        return list;
    }

    public PsiDisjunctionType newDisjunctionType(List<PsiType> list) {
        return new PsiDisjunctionType(list, this.myManager);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(final boolean z) {
        String strJoin = StringUtil.join((Collection) this.myTypes, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getPresentableText(z);
            }
        }, " | ");
        if (strJoin == null) {
            $$$reportNull$$$0(6);
        }
        return strJoin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(final boolean z) {
        String strJoin = StringUtil.join((Collection) this.myTypes, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getCanonicalText(z);
            }
        }, " | ");
        if (strJoin == null) {
            $$$reportNull$$$0(7);
        }
        return strJoin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String strJoin = StringUtil.join((Collection) this.myTypes, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiType) obj).getInternalCanonicalText();
            }
        }, " | ");
        if (strJoin == null) {
            $$$reportNull$$$0(8);
        }
        return strJoin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        Iterator<PsiType> it = this.myTypes.iterator();
        while (it.hasNext()) {
            if (!it.next().isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return Objects.equals(str, getCanonicalText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(10);
        }
        return psiTypeVisitor.visitDisjunctionType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return getLeastUpperBound().getResolveScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType leastUpperBound = getLeastUpperBound();
        if (leastUpperBound instanceof PsiIntersectionType) {
            PsiType[] conjuncts = ((PsiIntersectionType) leastUpperBound).getConjuncts();
            if (conjuncts == null) {
                $$$reportNull$$$0(11);
            }
            return conjuncts;
        }
        return new PsiType[]{leastUpperBound};
    }

    public int hashCode() {
        return this.myTypes.get(0).hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PsiDisjunctionType psiDisjunctionType = (PsiDisjunctionType) obj;
        if (psiDisjunctionType.myTypes.size() != this.myTypes.size()) {
            return false;
        }
        for (int i = 0; i < this.myTypes.size(); i++) {
            if (!this.myTypes.get(i).equals(psiDisjunctionType.myTypes.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<PsiType> flattenAndRemoveDuplicates(List<? extends PsiType> list) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends PsiType> it = list.iterator();
        while (it.hasNext()) {
            flatten(linkedHashSet, it.next());
        }
        ArrayList<PsiType> arrayList = new ArrayList(linkedHashSet);
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            PsiType psiType = (PsiType) it2.next();
            for (PsiType psiType2 : arrayList) {
                if (psiType != psiType2 && psiType2.isAssignableFrom(psiType)) {
                    it2.remove();
                    break;
                }
            }
        }
        return arrayList;
    }

    private static void flatten(Set<? super PsiType> set, PsiType psiType) {
        if (psiType instanceof PsiDisjunctionType) {
            Iterator<PsiType> it = ((PsiDisjunctionType) psiType).getDisjunctions().iterator();
            while (it.hasNext()) {
                flatten(set, it.next());
            }
            return;
        }
        set.add(psiType);
    }
}
