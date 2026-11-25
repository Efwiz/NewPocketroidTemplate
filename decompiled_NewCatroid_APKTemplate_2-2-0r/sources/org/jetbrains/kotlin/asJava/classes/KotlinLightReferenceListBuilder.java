package org.jetbrains.kotlin.asJava.classes;

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
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder;

/* JADX INFO: loaded from: classes2.dex */
public class KotlinLightReferenceListBuilder extends LightReferenceListBuilder implements PsiReferenceList {
    private volatile PsiJavaCodeReferenceElement[] myCachedRefs;
    private volatile PsiClassType[] myCachedTypes;
    private final PsiElementFactory myFactory;
    private final List<PsiJavaCodeReferenceElement> myRefs;
    private final PsiReferenceList.Role myRole;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/asJava/classes/KotlinLightReferenceListBuilder";
        if (i != 1) {
            objArr[1] = "getReferenceElements";
        } else {
            objArr[1] = "getReferencedTypes";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public KotlinLightReferenceListBuilder(PsiManager psiManager, PsiReferenceList.Role role) {
        this(psiManager, JavaLanguage.INSTANCE, role);
    }

    public KotlinLightReferenceListBuilder(PsiManager psiManager, Language language, PsiReferenceList.Role role) {
        super(psiManager, language, role);
        this.myRefs = new ArrayList();
        this.myRole = role;
        this.myFactory = JavaPsiFacade.getElementFactory(getProject());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder
    public void addReference(PsiClass psiClass) {
        addReference(psiClass.getQualifiedName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder
    public void addReference(String str) {
        this.myRefs.add(this.myFactory.createReferenceElementByFQClassName(str, getResolveScope()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder
    public void addReference(PsiClassType psiClassType) {
        this.myRefs.add(this.myFactory.createReferenceElementByType(psiClassType));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiJavaCodeReferenceElement[] getReferenceElements() {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = this.myCachedRefs;
        if (psiJavaCodeReferenceElementArr == null) {
            psiJavaCodeReferenceElementArr = (PsiJavaCodeReferenceElement[]) this.myRefs.toArray(PsiJavaCodeReferenceElement.EMPTY_ARRAY);
            this.myCachedRefs = psiJavaCodeReferenceElementArr;
        }
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiJavaCodeReferenceElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public PsiReferenceList.Role getRole() {
        return this.myRole;
    }
}
