package org.jetbrains.kotlin.com.intellij.util;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.ref.DebugReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
final class CachedValueLeakChecker {
    private static final Logger LOG = Logger.getInstance((Class<?>) CachedValueLeakChecker.class);
    private static final boolean DO_CHECKS = ApplicationManager.getApplication().isUnitTestMode();
    private static final Set<String> ourCheckedKeys = ContainerUtil.newConcurrentSet();

    /* JADX WARN: Code duplicated, block: B:12:0x0026  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i == 2) {
            objArr[0] = "userDataHolder";
        } else if (i == 3) {
            objArr[0] = "root";
        } else if (i == 4) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i != 5) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "toIgnore";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CachedValueLeakChecker";
        if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "findReferencedPsi";
        } else {
            objArr[2] = "checkProviderDoesNotLeakPSI";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    CachedValueLeakChecker() {
    }

    static void checkProviderDoesNotLeakPSI(CachedValueProvider<?> cachedValueProvider, Key<?> key, UserDataHolder userDataHolder) {
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(0);
        }
        if (key == null) {
            $$$reportNull$$$0(1);
        }
        if (userDataHolder == null) {
            $$$reportNull$$$0(2);
        }
        if (DO_CHECKS && !ApplicationManagerEx.isInStressTest() && ourCheckedKeys.add(key.toString())) {
            findReferencedPsi(cachedValueProvider, key, userDataHolder);
        }
    }

    private static synchronized void findReferencedPsi(final Object obj, Key<?> key, final UserDataHolder userDataHolder) {
        if (obj == null) {
            try {
                $$$reportNull$$$0(3);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        if (userDataHolder == null) {
            $$$reportNull$$$0(5);
        }
        DebugReflectionUtil.walkObjects(5, Collections.singletonMap(obj, "CachedValueProvider " + key), PsiElement.class, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueLeakChecker$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return CachedValueLeakChecker.lambda$findReferencedPsi$0(userDataHolder, obj2);
            }
        }, new PairProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueLeakChecker$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
            public final boolean process(Object obj2, Object obj3) {
                return CachedValueLeakChecker.lambda$findReferencedPsi$1(obj, (PsiElement) obj2, (DebugReflectionUtil.BackLink) obj3);
            }
        });
    }

    static /* synthetic */ boolean lambda$findReferencedPsi$0(UserDataHolder userDataHolder, Object obj) {
        if (obj == userDataHolder) {
            return false;
        }
        if (((obj instanceof ASTNode) && (obj = ((ASTNode) obj).getPsi()) == userDataHolder) || (obj instanceof Project) || (obj instanceof Module) || (obj instanceof Application)) {
            return false;
        }
        if ((obj instanceof PsiElement) && (userDataHolder instanceof PsiElement)) {
            PsiElement psiElement = (PsiElement) userDataHolder;
            if (psiElement.getContainingFile() != null && PsiTreeUtil.isAncestor((PsiElement) obj, psiElement, true)) {
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ boolean lambda$findReferencedPsi$1(Object obj, PsiElement psiElement, DebugReflectionUtil.BackLink backLink) {
        LOG.error("Provider '" + obj + "' is retaining PSI, causing memory leaks and possible invalid element access.\n" + backLink);
        return false;
    }
}
