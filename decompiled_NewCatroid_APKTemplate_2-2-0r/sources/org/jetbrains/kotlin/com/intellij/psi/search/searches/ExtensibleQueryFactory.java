package org.jetbrains.kotlin.com.intellij.psi.search.searches;

import java.beans.Introspector;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.SimpleSmartExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.QueryExecutor;
import org.jetbrains.kotlin.com.intellij.util.QueryFactory;

/* JADX INFO: loaded from: classes3.dex */
public class ExtensibleQueryFactory<Result, Parameters> extends QueryFactory<Result, Parameters> {
    private final SmartExtensionPoint<QueryExecutor<Result, Parameters>, QueryExecutor<Result, Parameters>> myPoint;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "queryExecutor";
        } else if (i != 3) {
            objArr[0] = "epName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/searches/ExtensibleQueryFactory";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/searches/ExtensibleQueryFactory";
        } else {
            objArr[1] = "getExecutors";
        }
        if (i == 1) {
            objArr[2] = "registerExecutor";
        } else if (i == 2) {
            objArr[2] = "unregisterExecutor";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    protected ExtensibleQueryFactory() {
        this("org.jetbrains.kotlin.com.intellij");
    }

    protected ExtensibleQueryFactory(final ExtensionPointName<QueryExecutor<Result, Parameters>> extensionPointName) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(0);
        }
        this.myPoint = new SimpleSmartExtensionPoint<QueryExecutor<Result, Parameters>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.searches.ExtensibleQueryFactory.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/search/searches/ExtensibleQueryFactory$1", "getExtensionPoint"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint
            protected ExtensionPoint<QueryExecutor<Result, Parameters>> getExtensionPoint() {
                ExtensionPoint<QueryExecutor<Result, Parameters>> extensionPoint = Extensions.getRootArea().getExtensionPoint(extensionPointName);
                if (extensionPoint == null) {
                    $$$reportNull$$$0(0);
                }
                return extensionPoint;
            }
        };
    }

    @Deprecated
    protected ExtensibleQueryFactory(final String str) {
        this.myPoint = new SimpleSmartExtensionPoint<QueryExecutor<Result, Parameters>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.searches.ExtensibleQueryFactory.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/search/searches/ExtensibleQueryFactory$2", "getExtensionPoint"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint
            protected ExtensionPoint<QueryExecutor<Result, Parameters>> getExtensionPoint() {
                String name2 = ExtensibleQueryFactory.this.getClass().getName();
                int iLastIndexOf = name2.lastIndexOf(46);
                if (iLastIndexOf >= 0) {
                    name2 = name2.substring(iLastIndexOf + 1);
                }
                ExtensionPoint<QueryExecutor<Result, Parameters>> extensionPoint = Extensions.getRootArea().getExtensionPoint(str + "." + Introspector.decapitalize(name2));
                if (extensionPoint == null) {
                    $$$reportNull$$$0(0);
                }
                return extensionPoint;
            }
        };
    }

    public void registerExecutor(final QueryExecutor<Result, Parameters> queryExecutor, Disposable disposable) {
        registerExecutor(queryExecutor);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.searches.ExtensibleQueryFactory.3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public void dispose() {
                ExtensibleQueryFactory.this.unregisterExecutor(queryExecutor);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.QueryFactory
    public void registerExecutor(QueryExecutor<Result, Parameters> queryExecutor) {
        if (queryExecutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myPoint.addExplicitExtension(queryExecutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.QueryFactory
    public void unregisterExecutor(QueryExecutor<Result, Parameters> queryExecutor) {
        if (queryExecutor == null) {
            $$$reportNull$$$0(2);
        }
        this.myPoint.removeExplicitExtension(queryExecutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.QueryFactory
    protected List<QueryExecutor<Result, Parameters>> getExecutors() {
        List<QueryExecutor<Result, Parameters>> extensions = this.myPoint.getExtensions();
        if (extensions == null) {
            $$$reportNull$$$0(3);
        }
        return extensions;
    }
}
