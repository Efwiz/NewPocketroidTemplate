package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.ClassTypePointerFactory;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassReferenceTypePointerFactory implements ClassTypePointerFactory {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiClassReferenceTypePointerFactory.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "classType";
        } else {
            objArr[0] = "project";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/PsiClassReferenceTypePointerFactory";
        objArr[2] = "createClassTypePointer";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ClassTypePointerFactory
    public SmartTypePointer createClassTypePointer(PsiClassType psiClassType, Project project) {
        if (psiClassType == null) {
            $$$reportNull$$$0(0);
        }
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClassType instanceof PsiClassReferenceType) {
            return new ClassReferenceTypePointer((PsiClassReferenceType) psiClassType, project);
        }
        return null;
    }

    private static class ClassReferenceTypePointer extends TypePointerBase<PsiClassReferenceType> {
        private final Project myProject;
        private final String myReferenceText;
        private final SmartPsiElementPointer mySmartPsiElementPointer;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/PsiClassReferenceTypePointerFactory$ClassReferenceTypePointer", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ClassReferenceTypePointer(PsiClassReferenceType psiClassReferenceType, Project project) {
            super(psiClassReferenceType);
            if (psiClassReferenceType == null) {
                $$$reportNull$$$0(0);
            }
            this.myProject = project;
            PsiJavaCodeReferenceElement reference = psiClassReferenceType.getReference();
            this.mySmartPsiElementPointer = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(reference);
            this.myReferenceText = reference.getText();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.TypePointerBase
        public PsiClassReferenceType calcType() {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) this.mySmartPsiElementPointer.getElement();
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.myProject);
            if (psiJavaCodeReferenceElement != null) {
                return (PsiClassReferenceType) elementFactory.createType(psiJavaCodeReferenceElement);
            }
            try {
                return (PsiClassReferenceType) elementFactory.createTypeFromText(this.myReferenceText, null);
            } catch (IncorrectOperationException e) {
                PsiClassReferenceTypePointerFactory.LOG.error((Throwable) e);
                return null;
            }
        }
    }
}
