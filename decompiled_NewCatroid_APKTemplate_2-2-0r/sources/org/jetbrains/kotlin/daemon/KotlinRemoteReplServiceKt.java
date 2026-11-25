package org.jetbrains.kotlin.daemon;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.util.ServiceLoaderLite;

/* JADX INFO: compiled from: KotlinRemoteReplService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0000\n\u0000\u001a(\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0086\bø\u0001\u0000\u001a\u0012\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a'\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082\b\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, d2 = {"internalRng", "Ljava/util/Random;", "getInternalRng", "()Ljava/util/Random;", "getValidId", "", "counter", "Ljava/util/concurrent/atomic/AtomicInteger;", "check", "Lkotlin/Function1;", "", "configureScripting", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "loadRegistrars", "", ExifInterface.GPS_DIRECTION_TRUE, "", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinRemoteReplServiceKt {
    private static final Random internalRng = new Random();

    public static final Random getInternalRng() {
        return internalRng;
    }

    public static final int getValidId(AtomicInteger counter, Function1<? super Integer, Boolean> check) {
        Intrinsics.checkNotNullParameter(counter, "counter");
        Intrinsics.checkNotNullParameter(check, "check");
        int iIncrementAndGet = counter.incrementAndGet();
        int i = 100;
        while (!check.invoke(Integer.valueOf(iIncrementAndGet)).booleanValue()) {
            i--;
            if (i <= 0) {
                throw new IllegalStateException("Invalid state or algorithm error");
            }
            iIncrementAndGet = counter.addAndGet(getInternalRng().nextInt());
        }
        return iIncrementAndGet;
    }

    private static final /* synthetic */ <T> List<T> loadRegistrars(CompilerConfiguration compilerConfiguration, CompilerId compilerId) {
        ClassLoader classLoader = compilerConfiguration.getClass().getClassLoader();
        URLClassLoader uRLClassLoader = classLoader instanceof URLClassLoader ? (URLClassLoader) classLoader : null;
        if (uRLClassLoader != null) {
            ServiceLoaderLite serviceLoaderLite = ServiceLoaderLite.INSTANCE;
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            List<T> listLoadImplementations = serviceLoaderLite.loadImplementations(Object.class, uRLClassLoader);
            if (listLoadImplementations != null) {
                return listLoadImplementations;
            }
        }
        ServiceLoaderLite serviceLoaderLite2 = ServiceLoaderLite.INSTANCE;
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        List<String> compilerClasspath = compilerId.getCompilerClasspath();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(compilerClasspath, 10));
        Iterator<T> it = compilerClasspath.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()));
        }
        ClassLoader classLoader2 = compilerConfiguration.getClass().getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader2, "getClassLoader(...)");
        return serviceLoaderLite2.loadImplementations(Object.class, arrayList, classLoader2);
    }

    public static final void configureScripting(CompilerConfiguration compilerConfiguration, CompilerId compilerId) {
        NoClassDefFoundError noClassDefFoundError;
        List listLoadImplementations;
        List listLoadImplementations2;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        try {
            ClassLoader classLoader = compilerConfiguration.getClass().getClassLoader();
            noClassDefFoundError = null;
            URLClassLoader uRLClassLoader = classLoader instanceof URLClassLoader ? (URLClassLoader) classLoader : null;
            if (uRLClassLoader == null || (listLoadImplementations = ServiceLoaderLite.INSTANCE.loadImplementations(ComponentRegistrar.class, uRLClassLoader)) == null) {
                ServiceLoaderLite serviceLoaderLite = ServiceLoaderLite.INSTANCE;
                List<String> compilerClasspath = compilerId.getCompilerClasspath();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(compilerClasspath, 10));
                Iterator<T> it = compilerClasspath.iterator();
                while (it.hasNext()) {
                    arrayList.add(new File((String) it.next()));
                }
                ClassLoader classLoader2 = compilerConfiguration.getClass().getClassLoader();
                Intrinsics.checkNotNullExpressionValue(classLoader2, "getClassLoader(...)");
                listLoadImplementations = serviceLoaderLite.loadImplementations(ComponentRegistrar.class, arrayList, classLoader2);
            }
            compilerConfiguration.addAll(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), listLoadImplementations);
            ClassLoader classLoader3 = compilerConfiguration.getClass().getClassLoader();
            URLClassLoader uRLClassLoader2 = classLoader3 instanceof URLClassLoader ? (URLClassLoader) classLoader3 : null;
            if (uRLClassLoader2 == null || (listLoadImplementations2 = ServiceLoaderLite.INSTANCE.loadImplementations(CompilerPluginRegistrar.class, uRLClassLoader2)) == null) {
                ServiceLoaderLite serviceLoaderLite2 = ServiceLoaderLite.INSTANCE;
                List<String> compilerClasspath2 = compilerId.getCompilerClasspath();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(compilerClasspath2, 10));
                Iterator<T> it2 = compilerClasspath2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(new File((String) it2.next()));
                }
                ClassLoader classLoader4 = compilerConfiguration.getClass().getClassLoader();
                Intrinsics.checkNotNullExpressionValue(classLoader4, "getClassLoader(...)");
                listLoadImplementations2 = serviceLoaderLite2.loadImplementations(CompilerPluginRegistrar.class, arrayList2, classLoader4);
            }
            compilerConfiguration.addAll(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS(), listLoadImplementations2);
        } catch (ClassNotFoundException e) {
            noClassDefFoundError = e;
        } catch (NoClassDefFoundError e2) {
            noClassDefFoundError = e2;
        }
        if (noClassDefFoundError != null) {
            throw new IllegalStateException("Unable to use scripting/REPL in the daemon, no kotlin-scripting-compiler.jar or its dependencies are found in the compiler classpath", noClassDefFoundError);
        }
    }
}
