package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;

/* JADX INFO: compiled from: FirJavaFacade.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaFacadeForSource;", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "sourceModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "classFinder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/load/java/JavaClassFinder;)V", "getModuleDataForClass", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaFacadeForSource extends FirJavaFacade {
    private final FirModuleData sourceModuleData;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJavaFacadeForSource(FirSession session, FirModuleData sourceModuleData, JavaClassFinder classFinder) {
        super(session, sourceModuleData.getSession().getBuiltinTypes(), classFinder);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(sourceModuleData, "sourceModuleData");
        Intrinsics.checkNotNullParameter(classFinder, "classFinder");
        this.sourceModuleData = sourceModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.java.FirJavaFacade
    public FirModuleData getModuleDataForClass(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        return this.sourceModuleData;
    }
}
