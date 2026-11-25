package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.UserDataProperty;

/* JADX INFO: compiled from: FileScopeProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\"3\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopesCustomizer;", "fileScopesCustomizer", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFileScopesCustomizer", "(Lorg/jetbrains/kotlin/psi/KtFile;)Lorg/jetbrains/kotlin/resolve/lazy/FileScopesCustomizer;", "setFileScopesCustomizer", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/resolve/lazy/FileScopesCustomizer;)V", "fileScopesCustomizer$delegate", "Lorg/jetbrains/kotlin/psi/UserDataProperty;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileScopeProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FileScopeProviderKt.class, "frontend"), "fileScopesCustomizer", "getFileScopesCustomizer(Lorg/jetbrains/kotlin/psi/KtFile;)Lorg/jetbrains/kotlin/resolve/lazy/FileScopesCustomizer;"))};
    private static final UserDataProperty fileScopesCustomizer$delegate;

    static {
        Key keyCreate = Key.create("FILE_SCOPES_CUSTOMIZER");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        fileScopesCustomizer$delegate = new UserDataProperty(keyCreate);
    }

    public static final FileScopesCustomizer getFileScopesCustomizer(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (FileScopesCustomizer) fileScopesCustomizer$delegate.getValue(ktFile, $$delegatedProperties[0]);
    }

    public static final void setFileScopesCustomizer(KtFile ktFile, FileScopesCustomizer fileScopesCustomizer) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        fileScopesCustomizer$delegate.setValue(ktFile, $$delegatedProperties[0], fileScopesCustomizer);
    }
}
