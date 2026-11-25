package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class FilterScopeProcessor<T> implements PsiScopeProcessor {
    private PsiElement myCurrentDeclarationHolder;
    private final ElementFilter myFilter;
    private final PsiScopeProcessor myProcessor;
    protected final List<T> myResults;

    /* JADX WARN: Code duplicated, block: B:22:0x0043  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 13 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 13 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "container";
        } else if (i != 3) {
            switch (i) {
                case 6:
                    objArr[0] = "container";
                    break;
                case 7:
                    objArr[0] = "event";
                    break;
                case 8:
                case 10:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 9:
                    objArr[0] = "state";
                    break;
                case 11:
                    objArr[0] = "substitutor";
                    break;
                case 12:
                    objArr[0] = "hintKey";
                    break;
                case 13:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/FilterScopeProcessor";
                    break;
                default:
                    objArr[0] = "filter";
                    break;
            }
        } else {
            objArr[0] = "processor";
        }
        if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/FilterScopeProcessor";
        } else {
            objArr[1] = "getResults";
        }
        switch (i) {
            case 7:
                objArr[2] = "handleEvent";
                break;
            case 8:
            case 9:
                objArr[2] = "execute";
                break;
            case 10:
            case 11:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 12:
                objArr[2] = "getHint";
                break;
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 13) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FilterScopeProcessor(ElementFilter elementFilter, List<T> list) {
        this(elementFilter, null, list);
        if (elementFilter == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FilterScopeProcessor(ElementFilter elementFilter, PsiScopeProcessor psiScopeProcessor) {
        this(elementFilter, psiScopeProcessor, new SmartList());
        if (elementFilter == null) {
            $$$reportNull$$$0(2);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FilterScopeProcessor(ElementFilter elementFilter) {
        this(elementFilter, null, new SmartList());
        if (elementFilter == null) {
            $$$reportNull$$$0(4);
        }
    }

    public FilterScopeProcessor(ElementFilter elementFilter, PsiScopeProcessor psiScopeProcessor, List<T> list) {
        if (elementFilter == null) {
            $$$reportNull$$$0(5);
        }
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        this.myFilter = elementFilter;
        this.myProcessor = psiScopeProcessor;
        this.myResults = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(7);
        }
        PsiScopeProcessor psiScopeProcessor = this.myProcessor;
        if (psiScopeProcessor != null) {
            psiScopeProcessor.handleEvent(event, obj);
        }
        if (event == PsiScopeProcessor.Event.SET_DECLARATION_HOLDER && (obj instanceof PsiElement)) {
            this.myCurrentDeclarationHolder = (PsiElement) obj;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (!this.myFilter.isAcceptable(psiElement, this.myCurrentDeclarationHolder)) {
            return true;
        }
        PsiScopeProcessor psiScopeProcessor = this.myProcessor;
        if (psiScopeProcessor != null) {
            return psiScopeProcessor.execute(psiElement, resolveState);
        }
        add(psiElement, (PsiSubstitutor) resolveState.get(PsiSubstitutor.KEY));
        return true;
    }

    protected void add(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(11);
        }
        this.myResults.add(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <K> K getHint(Key<K> key) {
        if (key == null) {
            $$$reportNull$$$0(12);
        }
        PsiScopeProcessor psiScopeProcessor = this.myProcessor;
        if (psiScopeProcessor != null) {
            return (K) psiScopeProcessor.getHint(key);
        }
        return null;
    }

    public List<T> getResults() {
        List<T> list = this.myResults;
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        return list;
    }
}
