package org.jetbrains.kotlin.com.intellij.lang.jvm.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public class JvmFacadeImpl implements JvmFacade {
    private static final Logger LOG = Logger.getInstance((Class<?>) JvmFacadeImpl.class);
    private final Map<GlobalSearchScope, Map<String, List<JvmClass>>> myClassCache;
    private final DumbService myDumbService;
    private final JavaPsiFacadeImpl myJavaPsiFacade;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 10 || i == 17 || i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 10 || i == 17 || i == 13 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 6:
            case 8:
                objArr[0] = "qualifiedName";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
            case 12:
                objArr[0] = "scope";
                break;
            case 3:
            case 10:
            case 13:
            case 14:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/jvm/facade/JvmFacadeImpl";
                break;
            case 11:
            case 16:
                objArr[0] = "classes";
                break;
            case 15:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 3) {
            objArr[1] = "findClasses";
        } else if (i == 10) {
            objArr[1] = "findClassesWithoutJavaFacade";
        } else if (i == 17) {
            objArr[1] = "filteredProviders";
        } else if (i == 13 || i == 14) {
            objArr[1] = "sortByScope";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/jvm/facade/JvmFacadeImpl";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "findClasses";
                break;
            case 3:
            case 10:
            case 13:
            case 14:
            case 17:
                break;
            case 4:
            case 5:
                objArr[2] = "doFindClassesWithJavaFacade";
                break;
            case 6:
            case 7:
                objArr[2] = "findClassesWithJavaFacade";
                break;
            case 8:
            case 9:
                objArr[2] = "findClassesWithoutJavaFacade";
                break;
            case 11:
            case 12:
                objArr[2] = "sortByScope";
                break;
            case 15:
            case 16:
                objArr[2] = "assertNotNullClasses";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 10 && i != 17 && i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JvmFacadeImpl(Project project, MessageBus messageBus) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myClassCache = ContainerUtil.createConcurrentWeakKeySoftValueMap();
        this.myDumbService = DumbService.getInstance(project);
        this.myJavaPsiFacade = (JavaPsiFacadeImpl) JavaPsiFacade.getInstance(project);
        if (messageBus != null) {
            messageBus.connect().subscribe(PsiModificationTracker.TOPIC, new PsiModificationTracker.Listener() { // from class: org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmFacadeImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker.Listener
                public final void modificationCountChanged() {
                    this.f$0.m6466x8b6a43c0();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-lang-jvm-facade-JvmFacadeImpl, reason: not valid java name */
    /* synthetic */ void m6466x8b6a43c0() {
        this.myClassCache.clear();
    }

    public List<JvmClass> findClassesWithoutJavaFacade(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        List listEmptyList = null;
        for (JvmElementProvider jvmElementProvider : filteredProviders()) {
            List<? extends JvmClass> classes = jvmElementProvider.getClasses(str, globalSearchScope);
            if (!classes.isEmpty()) {
                assertNotNullClasses(jvmElementProvider, classes);
                if (listEmptyList == null) {
                    listEmptyList = new ArrayList(classes);
                } else {
                    listEmptyList.addAll(classes);
                }
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(10);
        }
        return listEmptyList;
    }

    private static void assertNotNullClasses(JvmElementProvider jvmElementProvider, List<? extends JvmClass> list) {
        if (jvmElementProvider == null) {
            $$$reportNull$$$0(15);
        }
        if (list == null) {
            $$$reportNull$$$0(16);
        }
        Iterator<? extends JvmClass> it = list.iterator();
        while (it.hasNext()) {
            LOG.assertTrue(it.next() != null, "Provider " + jvmElementProvider + "returned null JvmClass");
        }
    }

    private List<JvmElementProvider> filteredProviders() {
        List<JvmElementProvider> listFilterByDumbAwareness = this.myDumbService.filterByDumbAwareness(JvmElementProvider.EP_NAME.getExtensions(this.myJavaPsiFacade.getProject()));
        if (listFilterByDumbAwareness == null) {
            $$$reportNull$$$0(17);
        }
        return listFilterByDumbAwareness;
    }
}
