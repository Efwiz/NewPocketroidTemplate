package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;

/* JADX INFO: loaded from: classes3.dex */
public class ApplicationManager {
    protected static Application ourApplication;

    /* JADX WARN: Code duplicated, block: B:10:0x0017  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2) {
            objArr[0] = "parent";
        } else if (i == 4) {
            objArr[0] = "fileTypeRegistryGetter";
        } else if (i != 5) {
            objArr[0] = "instance";
        } else {
            objArr[0] = "parent";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ApplicationManager";
        objArr[2] = "setApplication";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static Application getApplication() {
        return ourApplication;
    }

    private static void setApplication(Application application) {
        if (application == null) {
            $$$reportNull$$$0(0);
        }
        ourApplication = application;
        CachedSingletonsRegistry.cleanupCachedFields();
    }

    public static void setApplication(Application application, Disposable disposable) {
        if (application == null) {
            $$$reportNull$$$0(1);
        }
        if (disposable == null) {
            $$$reportNull$$$0(2);
        }
        final Application application2 = ourApplication;
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                ApplicationManager.lambda$setApplication$0(application2);
            }
        });
        setApplication(application);
    }

    static /* synthetic */ void lambda$setApplication$0(Application application) {
        if (application != null) {
            setApplication(application);
        }
    }

    public static void setApplication(Application application, Supplier<? extends FileTypeRegistry> supplier, Disposable disposable) {
        if (application == null) {
            $$$reportNull$$$0(3);
        }
        if (supplier == null) {
            $$$reportNull$$$0(4);
        }
        if (disposable == null) {
            $$$reportNull$$$0(5);
        }
        final Application application2 = ourApplication;
        final Getter<FileTypeRegistry> getter = FileTypeRegistry.ourInstanceGetter;
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                ApplicationManager.lambda$setApplication$1(application2, getter);
            }
        });
        setApplication(application);
        FileTypeRegistry.setInstanceSupplier(supplier);
    }

    static /* synthetic */ void lambda$setApplication$1(Application application, Supplier supplier) {
        if (application != null) {
            setApplication(application);
            FileTypeRegistry.setInstanceSupplier(supplier);
        }
    }
}
