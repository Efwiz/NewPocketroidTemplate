package org.jetbrains.kotlin.psi;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.resolve.ImportPath;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class KtImportsFactory {
    private final Map<ImportPath, KtImportDirective> importsCache;
    private final Project project;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "importPath";
                break;
            case 2:
            case 3:
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtImportsFactory";
                break;
            case 4:
            case 6:
                objArr[0] = "importPaths";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "createImportDirective";
        } else if (i == 5) {
            objArr[1] = "createImportDirectives";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtImportsFactory";
        } else {
            objArr[1] = "createImportDirectivesNotCached";
        }
        switch (i) {
            case 1:
                objArr[2] = "createImportDirective";
                break;
            case 2:
            case 3:
            case 5:
            case 7:
                break;
            case 4:
                objArr[2] = "createImportDirectives";
                break;
            case 6:
                objArr[2] = "createImportDirectivesNotCached";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public KtImportsFactory(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.importsCache = new HashMap();
        this.project = project;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KtImportDirective createImportDirective(ImportPath importPath) {
        if (importPath == null) {
            $$$reportNull$$$0(1);
        }
        KtImportDirective ktImportDirective = this.importsCache.get(importPath);
        if (ktImportDirective != null) {
            if (ktImportDirective == null) {
                $$$reportNull$$$0(2);
            }
            return ktImportDirective;
        }
        KtImportDirective ktImportDirectiveCreateImportDirective = new KtPsiFactory(this.project, false).createImportDirective(importPath);
        this.importsCache.put(importPath, ktImportDirectiveCreateImportDirective);
        if (ktImportDirectiveCreateImportDirective == null) {
            $$$reportNull$$$0(3);
        }
        return ktImportDirectiveCreateImportDirective;
    }

    public Collection<KtImportDirective> createImportDirectives(Collection<ImportPath> collection) {
        if (collection == null) {
            $$$reportNull$$$0(4);
        }
        List map = CollectionsKt.map(collection, new Function1() { // from class: org.jetbrains.kotlin.psi.KtImportsFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.createImportDirective((ImportPath) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(5);
        }
        return map;
    }

    public Collection<KtImportDirective> createImportDirectivesNotCached(Collection<ImportPath> collection) {
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        List<KtImportDirective> listCreateImportDirectives = new KtPsiFactory(this.project, false).createImportDirectives(collection);
        if (listCreateImportDirectives == null) {
            $$$reportNull$$$0(7);
        }
        return listCreateImportDirectives;
    }
}
