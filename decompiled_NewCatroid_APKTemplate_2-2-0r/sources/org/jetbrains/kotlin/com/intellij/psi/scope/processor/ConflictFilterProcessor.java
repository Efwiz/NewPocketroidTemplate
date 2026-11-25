package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ConflictFilterProcessor extends FilterScopeProcessor<CandidateInfo> implements NameHint {
    private JavaResolveResult[] myCachedResult;
    protected String myName;
    protected final PsiElement myPlace;
    protected final PsiFile myPlaceFile;
    private final PsiConflictResolver[] myResolvers;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 12 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 12 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "container";
                break;
            case 2:
                objArr[0] = "place";
                break;
            case 3:
                objArr[0] = "placeFile";
                break;
            case 4:
                objArr[0] = "resolvers";
                break;
            case 5:
            case 8:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
            case 13:
                objArr[0] = "state";
                break;
            case 7:
                objArr[0] = "cachedResult";
                break;
            case 9:
                objArr[0] = "substitutor";
                break;
            case 10:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 11:
                objArr[0] = "event";
                break;
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/ConflictFilterProcessor";
                break;
            case 14:
                objArr[0] = "hintKey";
                break;
            default:
                objArr[0] = "filter";
                break;
        }
        if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/ConflictFilterProcessor";
        } else {
            objArr[1] = "getResult";
        }
        switch (i) {
            case 5:
            case 6:
                objArr[2] = "execute";
                break;
            case 7:
                objArr[2] = "stopAtFoundResult";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 11:
                objArr[2] = "handleEvent";
                break;
            case 12:
                break;
            case 13:
                objArr[2] = "getName";
                break;
            case 14:
                objArr[2] = "getHint";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 12) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConflictFilterProcessor(String str, ElementFilter elementFilter, PsiConflictResolver[] psiConflictResolverArr, List<CandidateInfo> list, PsiElement psiElement, PsiFile psiFile) {
        super(elementFilter, list);
        if (elementFilter == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        if (psiConflictResolverArr == null) {
            $$$reportNull$$$0(4);
        }
        this.myResolvers = psiConflictResolverArr;
        this.myName = str;
        this.myPlace = psiElement;
        this.myPlaceFile = psiFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        JavaResolveResult[] javaResolveResultArr = this.myCachedResult;
        if (javaResolveResultArr != null && javaResolveResultArr.length == 1 && stopAtFoundResult(javaResolveResultArr[0])) {
            return false;
        }
        String str = this.myName;
        if (str == null || PsiUtil.checkName(psiElement, str, this.myPlace)) {
            return super.execute(psiElement, resolveState);
        }
        return true;
    }

    protected boolean stopAtFoundResult(JavaResolveResult javaResolveResult) {
        if (javaResolveResult == null) {
            $$$reportNull$$$0(7);
        }
        return javaResolveResult.isAccessible();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor
    protected void add(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
        add(new CandidateInfo(psiElement, psiSubstitutor));
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    protected void add(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            $$$reportNull$$$0(10);
        }
        this.myCachedResult = null;
        this.myResults.add((T) candidateInfo);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(11);
        }
        if (event != JavaScopeProcessorEvent.CHANGE_LEVEL || this.myName == null) {
            return;
        }
        getResult();
    }

    public JavaResolveResult[] getResult() {
        JavaResolveResult[] javaResolveResultArr = this.myCachedResult;
        if (javaResolveResultArr == null) {
            List<CandidateInfo> results = getResults();
            if (!results.isEmpty()) {
                for (PsiConflictResolver psiConflictResolver : this.myResolvers) {
                    CandidateInfo candidateInfoResolveConflict = psiConflictResolver.resolveConflict(results);
                    if (candidateInfoResolveConflict != null) {
                        results.clear();
                        results.add(candidateInfoResolveConflict);
                        break;
                    }
                }
            }
            javaResolveResultArr = (JavaResolveResult[]) results.toArray(JavaResolveResult.EMPTY_ARRAY);
            this.myCachedResult = javaResolveResultArr;
        }
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(12);
        }
        return javaResolveResultArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.NameHint
    public String getName(ResolveState resolveState) {
        if (resolveState == null) {
            $$$reportNull$$$0(13);
        }
        return this.myName;
    }

    public void setName(String str) {
        this.myName = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(14);
        }
        if (key == NameHint.KEY) {
            if (this.myName != null) {
                return this;
            }
            return null;
        }
        return (T) super.getHint(key);
    }
}
