package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import java.util.List;
import java.util.ListIterator;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class StaticImportResolveProcessor implements NameHint, PsiScopeProcessor {
    private final List<JavaResolveResult> myClassResult;
    private final List<JavaResolveResult> myFieldResults;
    private final String myName;
    private final PsiImportStaticReferenceElement myReference;
    private final List<JavaResolveResult> myResults;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "candidate";
        } else if (i == 2 || i == 3) {
            objArr[0] = "state";
        } else if (i == 4) {
            objArr[0] = "hintKey";
        } else if (i != 5) {
            objArr[0] = "reference";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/StaticImportResolveProcessor";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/StaticImportResolveProcessor";
        } else {
            objArr[1] = "getResults";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "execute";
        } else if (i == 3) {
            objArr[2] = "getName";
        } else if (i == 4) {
            objArr[2] = "getHint";
        } else if (i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    public StaticImportResolveProcessor(PsiImportStaticReferenceElement psiImportStaticReferenceElement) {
        if (psiImportStaticReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myFieldResults = new SmartList();
        this.myClassResult = new SmartList();
        this.myResults = new SmartList();
        this.myReference = psiImportStaticReferenceElement;
        this.myName = psiImportStaticReferenceElement.getReferenceName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if ((psiElement instanceof PsiMember) && ((PsiModifierListOwner) psiElement).hasModifierProperty("static")) {
            if (psiElement instanceof PsiField) {
                if (checkDomination((PsiMember) psiElement, this.myFieldResults)) {
                    return true;
                }
                this.myFieldResults.add(new OurResolveResult(psiElement, this.myReference));
            } else if (psiElement instanceof PsiClass) {
                if (checkDomination((PsiMember) psiElement, this.myClassResult)) {
                    return true;
                }
                this.myClassResult.add(new OurResolveResult(psiElement, this.myReference));
            } else {
                this.myResults.add(new OurResolveResult(psiElement, this.myReference));
            }
        }
        return true;
    }

    private static boolean checkDomination(PsiMember psiMember, List<JavaResolveResult> list) {
        if (list.isEmpty()) {
            return false;
        }
        ListIterator<JavaResolveResult> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            Domination dominationDominates = dominates(psiMember, (PsiMember) listIterator.previous().getElement());
            if (dominationDominates == Domination.DOMINATED_BY) {
                return true;
            }
            if (dominationDominates == Domination.DOMINATES) {
                listIterator.remove();
            }
        }
        return false;
    }

    private static Domination dominates(PsiMember psiMember, PsiMember psiMember2) {
        PsiClass containingClass = psiMember.getContainingClass();
        PsiClass containingClass2 = psiMember2.getContainingClass();
        if (containingClass != null && containingClass2 != null) {
            if (containingClass.isInheritor(containingClass2, true)) {
                return Domination.DOMINATES;
            }
            if (containingClass2.isInheritor(containingClass, true)) {
                return Domination.DOMINATED_BY;
            }
        }
        return Domination.EQUAL;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.NameHint
    public String getName(ResolveState resolveState) {
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        return this.myName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        if (key == NameHint.KEY) {
            return this;
        }
        return null;
    }

    public JavaResolveResult[] getResults() {
        if (this.myResults.size() + this.myFieldResults.size() + this.myClassResult.size() > 1) {
            filterInvalid(this.myResults);
            filterInvalid(this.myFieldResults);
            filterInvalid(this.myClassResult);
        }
        if (!this.myFieldResults.isEmpty()) {
            this.myResults.addAll(this.myFieldResults);
        }
        if (!this.myClassResult.isEmpty()) {
            this.myResults.addAll(this.myClassResult);
        }
        JavaResolveResult[] javaResolveResultArr = (JavaResolveResult[]) this.myResults.toArray(JavaResolveResult.EMPTY_ARRAY);
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(5);
        }
        return javaResolveResultArr;
    }

    private static void filterInvalid(List<JavaResolveResult> list) {
        if (list.isEmpty()) {
            return;
        }
        ListIterator<JavaResolveResult> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (!listIterator.previous().isValidResult()) {
                listIterator.remove();
            }
        }
    }

    private static class OurResolveResult extends CandidateInfo {
        private final PsiImportStaticReferenceElement myReference;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "candidate", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/StaticImportResolveProcessor$OurResolveResult", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isStaticsScopeCorrect() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        OurResolveResult(PsiElement psiElement, PsiImportStaticReferenceElement psiImportStaticReferenceElement) {
            super(psiElement, PsiSubstitutor.EMPTY);
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myReference = psiImportStaticReferenceElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isAccessible() {
            PsiResolveHelper resolveHelper = JavaPsiFacade.getInstance(this.myReference.getProject()).getResolveHelper();
            PsiElement element = getElement();
            return (element instanceof PsiMember) && resolveHelper.isAccessible((PsiMember) element, this.myReference, null);
        }
    }
}
