package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: loaded from: classes5.dex */
public class JavaTypeParameterImpl extends JavaClassifierImpl<PsiTypeParameter> implements JavaTypeParameter {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaTypeParameterImpl";
        } else {
            objArr[0] = "psiTypeParameterSource";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 2 || i == 3) {
            objArr[1] = "getUpperBounds";
        } else {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaTypeParameterImpl";
        }
        if (i != 1 && i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaTypeParameterImpl(JavaElementPsiSource<PsiTypeParameter> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    public JavaTypeParameterImpl(PsiTypeParameter psiTypeParameter) {
        this((JavaElementPsiSource<PsiTypeParameter>) JavaElementSourceFactory.getInstance(psiTypeParameter.getProject()).createPsiSource(psiTypeParameter));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameSafeIdentifier = SpecialNames.safeIdentifier(((PsiTypeParameter) getPsi()).get_name());
        if (nameSafeIdentifier == null) {
            $$$reportNull$$$0(1);
        }
        return nameSafeIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameter
    public Collection<JavaClassifierType> getUpperBounds() {
        PsiClassType[] referencedTypes = ((PsiTypeParameter) getPsi()).getExtendsList().getReferencedTypes();
        if (referencedTypes.length == 0) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(referencedTypes.length);
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        for (int i = 0; i < referencedTypes.length; i++) {
            arrayList.add(new JavaClassifierTypeImpl(sourceFactory.createTypeParameterUpperBoundTypeSource(this.psiElementSource, i)));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationOwnerImpl
    public PsiAnnotationOwner getAnnotationOwnerPsi() {
        return (PsiAnnotationOwner) getPsi();
    }
}
