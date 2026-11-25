package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers.CustomizableReferenceProvider;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ReferenceProviderType {
    private final String myId;
    public static final String EP_NAME = "org.jetbrains.kotlin.com.intellij.referenceProviderType";
    private static final KeyedExtensionCollector<PsiReferenceProvider, ReferenceProviderType> COLLECTOR = new KeyedExtensionCollector<PsiReferenceProvider, ReferenceProviderType>(EP_NAME) { // from class: org.jetbrains.kotlin.com.intellij.psi.ReferenceProviderType.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceProviderType$1";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceProviderType$1";
            } else {
                objArr[1] = "keyToString";
            }
            if (i != 1) {
                objArr[2] = "keyToString";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
        public String keyToString(ReferenceProviderType referenceProviderType) {
            if (referenceProviderType == null) {
                $$$reportNull$$$0(0);
            }
            String str = referenceProviderType.myId;
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            return str;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "id", "org/jetbrains/kotlin/com/intellij/psi/ReferenceProviderType", "<init>"));
    }

    public ReferenceProviderType(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myId = str;
    }

    public PsiReferenceProvider getProvider() {
        return new CompositePsiReferenceProvider();
    }

    public String toString() {
        return this.myId;
    }

    private static final class CompositePsiReferenceProvider extends PsiReferenceProvider implements CustomizableReferenceProvider {
        private final ReferenceProviderType myType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceProviderType$CompositePsiReferenceProvider";
            } else if (i == 3) {
                objArr[0] = "target";
            } else if (i != 5) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "hints";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceProviderType$CompositePsiReferenceProvider";
            } else {
                objArr[1] = "getReferencesByElement";
            }
            if (i != 2) {
                if (i == 3) {
                    objArr[2] = "acceptsTarget";
                } else if (i == 4 || i == 5) {
                    objArr[2] = "acceptsHints";
                } else {
                    objArr[2] = "getReferencesByElement";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private CompositePsiReferenceProvider(ReferenceProviderType referenceProviderType) {
            this.myType = referenceProviderType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider, org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers.CustomizableReferenceProvider
        public PsiReference[] getReferencesByElement(PsiElement psiElement, ProcessingContext processingContext) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            if (processingContext == null) {
                $$$reportNull$$$0(1);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = ReferenceProviderType.COLLECTOR.forKey(this.myType).iterator();
            while (it.hasNext()) {
                ContainerUtil.addAllNotNull(arrayList, ((PsiReferenceProvider) it.next()).getReferencesByElement(psiElement, processingContext));
            }
            PsiReference[] psiReferenceArr = (PsiReference[]) arrayList.toArray(PsiReference.EMPTY_ARRAY);
            if (psiReferenceArr == null) {
                $$$reportNull$$$0(2);
            }
            return psiReferenceArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider
        public boolean acceptsTarget(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(3);
            }
            Iterator it = ReferenceProviderType.COLLECTOR.forKey(this.myType).iterator();
            while (it.hasNext()) {
                if (((PsiReferenceProvider) it.next()).acceptsTarget(psiElement)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider
        public boolean acceptsHints(PsiElement psiElement, PsiReferenceService.Hints hints) {
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            if (hints == null) {
                $$$reportNull$$$0(5);
            }
            Iterator it = ReferenceProviderType.COLLECTOR.forKey(this.myType).iterator();
            while (it.hasNext()) {
                if (((PsiReferenceProvider) it.next()).acceptsHints(psiElement, hints)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers.CustomizableReferenceProvider
        public Map<CustomizableReferenceProvider.CustomizationKey, Object> getOptions() {
            for (Object obj : ReferenceProviderType.COLLECTOR.forKey(this.myType)) {
                if (obj instanceof CustomizableReferenceProvider) {
                    return ((CustomizableReferenceProvider) obj).getOptions();
                }
            }
            return Collections.emptyMap();
        }
    }
}
