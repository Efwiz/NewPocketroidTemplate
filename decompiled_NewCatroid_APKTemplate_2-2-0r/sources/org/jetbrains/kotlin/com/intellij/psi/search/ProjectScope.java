package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyKey;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;

/* JADX INFO: loaded from: classes3.dex */
public final class ProjectScope {
    private static final NotNullLazyKey<GlobalSearchScope, Project> ALL_SCOPE_KEY = NotNullLazyKey.create("ALL_SCOPE_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ProjectScopeBuilder.getInstance((Project) obj).buildAllScope();
        }
    });
    private static final NotNullLazyKey<GlobalSearchScope, Project> PROJECT_SCOPE_KEY = NotNullLazyKey.create("PROJECT_SCOPE_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ProjectScopeBuilder.getInstance((Project) obj).buildProjectScope();
        }
    });
    private static final NotNullLazyKey<GlobalSearchScope, Project> LIBRARIES_SCOPE_KEY = NotNullLazyKey.create("LIBRARIES_SCOPE_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope$$ExternalSyntheticLambda2
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ProjectScopeBuilder.getInstance((Project) obj).buildLibrariesScope();
        }
    });
    private static final NotNullLazyKey<GlobalSearchScope, Project> CONTENT_SCOPE_KEY = NotNullLazyKey.create("CONTENT_SCOPE_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope$$ExternalSyntheticLambda3
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ProjectScopeBuilder.getInstance((Project) obj).buildContentScope();
        }
    });
    private static final NotNullLazyKey<GlobalSearchScope, Project> EVERYTHING_SCOPE_KEY = NotNullLazyKey.create("EVERYTHING_SCOPE_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope$$ExternalSyntheticLambda4
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ProjectScopeBuilder.getInstance((Project) obj).buildEverythingScope();
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10) ? 2 : 3];
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/ProjectScope";
        } else {
            objArr[0] = "project";
        }
        if (i == 1) {
            objArr[1] = "getAllScope";
        } else if (i == 3) {
            objArr[1] = "getProjectScope";
        } else if (i == 5) {
            objArr[1] = "getLibrariesScope";
        } else if (i == 7) {
            objArr[1] = "getContentScope";
        } else if (i == 9) {
            objArr[1] = "getEverythingScope";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/ProjectScope";
        } else {
            objArr[1] = "getProjectFilesScopeName";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
                break;
            case 2:
                objArr[2] = "getProjectScope";
                break;
            case 4:
                objArr[2] = "getLibrariesScope";
                break;
            case 6:
                objArr[2] = "getContentScope";
                break;
            case 8:
                objArr[2] = "getEverythingScope";
                break;
            default:
                objArr[2] = "getAllScope";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 7 && i != 9 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ProjectScope() {
    }

    public static GlobalSearchScope getAllScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        GlobalSearchScope value = ALL_SCOPE_KEY.getValue(project);
        if (value == null) {
            $$$reportNull$$$0(1);
        }
        return value;
    }

    public static GlobalSearchScope getProjectScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(2);
        }
        GlobalSearchScope value = PROJECT_SCOPE_KEY.getValue(project);
        if (value == null) {
            $$$reportNull$$$0(3);
        }
        return value;
    }

    public static GlobalSearchScope getLibrariesScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(4);
        }
        GlobalSearchScope value = LIBRARIES_SCOPE_KEY.getValue(project);
        if (value == null) {
            $$$reportNull$$$0(5);
        }
        return value;
    }

    public static GlobalSearchScope getContentScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(6);
        }
        GlobalSearchScope value = CONTENT_SCOPE_KEY.getValue(project);
        if (value == null) {
            $$$reportNull$$$0(7);
        }
        return value;
    }

    public static GlobalSearchScope getEverythingScope(Project project) {
        if (project == null) {
            $$$reportNull$$$0(8);
        }
        GlobalSearchScope value = EVERYTHING_SCOPE_KEY.getValue(project);
        if (value == null) {
            $$$reportNull$$$0(9);
        }
        return value;
    }

    public static String getProjectFilesScopeName() {
        String strMessage = CoreBundle.message("psi.search.scope.project", new Object[0]);
        if (strMessage == null) {
            $$$reportNull$$$0(10);
        }
        return strMessage;
    }
}
