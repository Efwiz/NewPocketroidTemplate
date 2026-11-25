package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;

/* JADX INFO: loaded from: classes3.dex */
public class LightReferenceListBuilder extends LightElement implements PsiReferenceList {
    private volatile PsiClassType[] myCachedTypes;
    private final PsiElementFactory myFactory;
    private final List<PsiJavaCodeReferenceElement> myRefs;
    private final PsiReferenceList.Role myRole;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightReferenceListBuilder";
        if (i != 1) {
            objArr[1] = "getReferenceElements";
        } else {
            objArr[1] = "getReferencedTypes";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public LightReferenceListBuilder(PsiManager psiManager, PsiReferenceList.Role role) {
        this(psiManager, JavaLanguage.INSTANCE, role);
    }

    public LightReferenceListBuilder(PsiManager psiManager, Language language, PsiReferenceList.Role role) {
        super(psiManager, language);
        this.myRefs = new ArrayList();
        this.myRole = role;
        this.myFactory = JavaPsiFacade.getElementFactory(getProject());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "light reference list";
    }

    public void addReference(PsiClass psiClass) {
        addReference(psiClass.getQualifiedName());
    }

    public void addReference(String str) {
        this.myRefs.add(this.myFactory.createReferenceElementByFQClassName(str, getResolveScope()));
    }

    public void addReference(PsiClassType psiClassType) {
        this.myRefs.add(this.myFactory.createReferenceElementByType(psiClassType));
    }

    public PsiJavaCodeReferenceElement[] getReferenceElements() {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = (PsiJavaCodeReferenceElement[]) this.myRefs.toArray(PsiJavaCodeReferenceElement.EMPTY_ARRAY);
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiJavaCodeReferenceElementArr;
    }

    public PsiClassType[] getReferencedTypes() {
        PsiClassType[] psiClassTypeArr = this.myCachedTypes;
        if (psiClassTypeArr == null) {
            int size = this.myRefs.size();
            PsiClassType[] psiClassTypeArr2 = size == 0 ? PsiClassType.EMPTY_ARRAY : new PsiClassType[size];
            for (int i = 0; i < size; i++) {
                psiClassTypeArr2[i] = this.myFactory.createType(this.myRefs.get(i));
            }
            this.myCachedTypes = psiClassTypeArr2;
            psiClassTypeArr = psiClassTypeArr2;
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiClassTypeArr;
    }

    public PsiReferenceList.Role getRole() {
        return this.myRole;
    }
}
