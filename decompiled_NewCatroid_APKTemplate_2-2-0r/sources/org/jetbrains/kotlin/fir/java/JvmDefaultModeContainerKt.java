package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: JvmDefaultModeContainer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"jvmDefaultModeComponent", "Lorg/jetbrains/kotlin/fir/java/FirJvmDefaultModeComponent;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getJvmDefaultModeComponent", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/FirJvmDefaultModeComponent;", "jvmDefaultModeComponent$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "jvmDefaultModeState", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmDefaultModeState", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/config/JvmDefaultMode;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultModeContainerKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmDefaultModeContainerKt.class, SuffixConstants.EXTENSION_java), "jvmDefaultModeComponent", "getJvmDefaultModeComponent(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/FirJvmDefaultModeComponent;"))};
    private static final ArrayMapAccessor jvmDefaultModeComponent$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirJvmDefaultModeComponent.class), (Object) null, 2, (Object) null);

    private static final FirJvmDefaultModeComponent getJvmDefaultModeComponent(FirSession firSession) {
        return (FirJvmDefaultModeComponent) jvmDefaultModeComponent$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final JvmDefaultMode getJvmDefaultModeState(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return getJvmDefaultModeComponent(firSession).getJvmDefaultMode();
    }
}
