package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.UserDataProperty;

/* JADX INFO: compiled from: isCommonSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\"3\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"3\u0010\u000b\u001a\u0004\u0018\u00010\n*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\n8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\t\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"<set-?>", "", "hmppModuleName", "Lorg/jetbrains/kotlin/psi/KtFile;", "getHmppModuleName", "(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/String;", "setHmppModuleName", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/String;)V", "hmppModuleName$delegate", "Lorg/jetbrains/kotlin/psi/UserDataProperty;", "", "isCommonSource", "(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/Boolean;", "setCommonSource", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/Boolean;)V", "isCommonSource$delegate", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IsCommonSourceKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(IsCommonSourceKt.class, "frontend"), "isCommonSource", "isCommonSource(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(IsCommonSourceKt.class, "frontend"), "hmppModuleName", "getHmppModuleName(Lorg/jetbrains/kotlin/psi/KtFile;)Ljava/lang/String;"))};
    private static final UserDataProperty hmppModuleName$delegate;
    private static final UserDataProperty isCommonSource$delegate;

    static {
        Key keyCreate = Key.create("IS_COMMON_SOURCE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        isCommonSource$delegate = new UserDataProperty(keyCreate);
        Key keyCreate2 = Key.create("HMPP_MODULE_NAME");
        Intrinsics.checkNotNullExpressionValue(keyCreate2, "create(...)");
        hmppModuleName$delegate = new UserDataProperty(keyCreate2);
    }

    public static final Boolean isCommonSource(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (Boolean) isCommonSource$delegate.getValue(ktFile, $$delegatedProperties[0]);
    }

    public static final void setCommonSource(KtFile ktFile, Boolean bool) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        isCommonSource$delegate.setValue(ktFile, $$delegatedProperties[0], bool);
    }

    public static final String getHmppModuleName(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (String) hmppModuleName$delegate.getValue(ktFile, $$delegatedProperties[1]);
    }

    public static final void setHmppModuleName(KtFile ktFile, String str) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        hmppModuleName$delegate.setValue(ktFile, $$delegatedProperties[1], str);
    }
}
