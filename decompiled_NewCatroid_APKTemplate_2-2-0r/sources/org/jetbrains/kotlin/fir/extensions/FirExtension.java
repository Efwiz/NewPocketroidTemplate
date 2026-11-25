package org.jetbrains.kotlin.fir.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSession;

/* JADX INFO: compiled from: FirExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0016R\u001a\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "registerPredicates", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationPredicateRegistrar;", "Factory", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirExtension {
    private final FirSession session;

    /* JADX INFO: compiled from: FirExtension.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bæ\u0080\u0001\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003J\u0015\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "P", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory<P extends FirExtension> {
        P create(FirSession session);
    }

    public abstract KClass<? extends FirExtension> getExtensionType();

    public abstract FirExtensionPointName getName();

    public void registerPredicates(FirDeclarationPredicateRegistrar firDeclarationPredicateRegistrar) {
        Intrinsics.checkNotNullParameter(firDeclarationPredicateRegistrar, "<this>");
    }

    public FirExtension(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }
}
