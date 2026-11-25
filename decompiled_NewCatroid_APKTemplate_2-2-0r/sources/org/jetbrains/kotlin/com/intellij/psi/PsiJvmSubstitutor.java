package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJvmSubstitutor implements JvmSubstitutor {
    private final Project myProject;
    private final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "substitutor";
        } else if (i == 2) {
            objArr[0] = "typeParameter";
        } else if (i != 3) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiJvmSubstitutor";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiJvmSubstitutor";
        } else {
            objArr[1] = "getPsiSubstitutor";
        }
        if (i == 2) {
            objArr[2] = "substitute";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public PsiJvmSubstitutor(Project project, PsiSubstitutor psiSubstitutor) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myProject = project;
        this.mySubstitutor = psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor
    public Collection<JvmTypeParameter> getTypeParameters() {
        return new SmartList((Collection) this.mySubstitutor.getSubstitutionMap().keySet());
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor
    public JvmType substitute(JvmTypeParameter jvmTypeParameter) {
        if (jvmTypeParameter == null) {
            $$$reportNull$$$0(2);
        }
        return this.mySubstitutor.substitute(JvmPsiConversionHelper.getInstance(this.myProject).convertTypeParameter(jvmTypeParameter));
    }

    public PsiSubstitutor getPsiSubstitutor() {
        PsiSubstitutor psiSubstitutor = this.mySubstitutor;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        return psiSubstitutor;
    }
}
