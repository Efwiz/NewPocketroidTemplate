package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: JavaSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"javaSymbolProvider", "Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getJavaSymbolProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider;", "javaSymbolProvider$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaSymbolProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JavaSymbolProviderKt.class, SuffixConstants.EXTENSION_java), "javaSymbolProvider", "getJavaSymbolProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider;"))};
    private static final NullableArrayMapAccessor javaSymbolProvider$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(JavaSymbolProvider.class));

    public static final JavaSymbolProvider getJavaSymbolProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (JavaSymbolProvider) javaSymbolProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
