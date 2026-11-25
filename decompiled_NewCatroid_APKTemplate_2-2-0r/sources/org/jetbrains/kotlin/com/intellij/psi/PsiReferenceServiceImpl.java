package org.jetbrains.kotlin.com.intellij.psi;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceServiceImpl extends PsiReferenceService {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiReferenceService.class);
    private static final Set<String> ourReportedReferenceClasses = ContainerUtil.newConcurrentSet();

    /* JADX WARN: Code duplicated, block: B:27:0x003d  */
    /* JADX WARN: Code duplicated, block: B:28:0x0040  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "hints";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceServiceImpl";
        } else if (i == 4) {
            objArr[0] = "hints";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceServiceImpl";
        } else if (i != 9) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = OverlayablePolicy.NAME_references;
        }
        if (i == 2) {
            objArr[1] = "getReferences";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[1] = "doGetReferences";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceServiceImpl";
        }
        switch (i) {
            case 2:
            case 5:
            case 6:
            case 7:
                break;
            case 3:
            case 4:
                objArr[2] = "doGetReferences";
                break;
            case 8:
            case 9:
                objArr[2] = "assertReferencesHaveSameElement";
                break;
            default:
                objArr[2] = "getReferences";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService
    public List<PsiReference> getReferences(PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (hints == null) {
            $$$reportNull$$$0(1);
        }
        List<PsiReference> listDoGetReferences = doGetReferences(psiElement, hints);
        assertReferencesHaveSameElement(psiElement, listDoGetReferences);
        if (listDoGetReferences == null) {
            $$$reportNull$$$0(2);
        }
        return listDoGetReferences;
    }

    private static List<PsiReference> doGetReferences(PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (hints == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement instanceof ContributedReferenceHost) {
            List<PsiReference> listAsList = Arrays.asList(ReferenceProvidersRegistry.getReferencesFromProviders(psiElement, hints));
            if (listAsList == null) {
                $$$reportNull$$$0(5);
            }
            return listAsList;
        }
        if (psiElement instanceof HintedReferenceHost) {
            List<PsiReference> listAsList2 = Arrays.asList(((HintedReferenceHost) psiElement).getReferences(hints));
            if (listAsList2 == null) {
                $$$reportNull$$$0(6);
            }
            return listAsList2;
        }
        List<PsiReference> listAsList3 = Arrays.asList(psiElement.getReferences());
        if (listAsList3 == null) {
            $$$reportNull$$$0(7);
        }
        return listAsList3;
    }

    private static void assertReferencesHaveSameElement(PsiElement psiElement, List<? extends PsiReference> list) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        for (PsiReference psiReference : list) {
            PsiElement psiElement2 = psiReference.getPsiElement();
            if (psiElement2 != psiElement) {
                Class<?> cls = psiReference.getClass();
                if (ourReportedReferenceClasses.add(cls.getName())) {
                    PluginException.logPluginError(LOG, "Reference element is not the same element for which references were queried", new RuntimeExceptionWithAttachments("Element: " + psiElement.getClass().getName() + "; reference: " + cls.getName() + "; reference element: " + psiElement2.getClass().getName(), new Attachment("info.txt", "Element text: `" + psiElement.getText() + "`\nReference range: " + psiReference.getRangeInElement())), cls);
                }
            }
        }
    }
}
