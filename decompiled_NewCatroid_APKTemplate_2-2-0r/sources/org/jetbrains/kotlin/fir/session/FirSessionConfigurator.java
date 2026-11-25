package org.jetbrains.kotlin.fir.session;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.SessionConfiguration;
import org.jetbrains.kotlin.fir.analysis.CheckersComponent;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers;
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension;
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtensionKt;
import org.jetbrains.kotlin.fir.extensions.BunchOfRegisteredExtensions;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;

/* JADX INFO: compiled from: FirSessionConfigurator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0007J\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0007J\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000fJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0010J\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "registeredExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions;", "configure", "", "registerExtensions", "extensions", "useCheckers", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSessionConfigurator {
    private final List<BunchOfRegisteredExtensions> registeredExtensions;
    private final FirSession session;

    public FirSessionConfigurator(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.registeredExtensions = CollectionsKt.mutableListOf(BunchOfRegisteredExtensions.INSTANCE.empty());
    }

    public final void registerExtensions(BunchOfRegisteredExtensions extensions) {
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.registeredExtensions.add(extensions);
    }

    public final void useCheckers(ExpressionCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CheckersComponentKt.getCheckersComponent(this.session).register(checkers);
    }

    public final void useCheckers(DeclarationCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CheckersComponentKt.getCheckersComponent(this.session).register(checkers);
    }

    public final void useCheckers(TypeCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CheckersComponentKt.getCheckersComponent(this.session).register(checkers);
    }

    public final void useCheckers(LanguageVersionSettingsCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CheckersComponentKt.getCheckersComponent(this.session).register(checkers);
    }

    @SessionConfiguration
    public final void configure() {
        Iterator<T> it = this.registeredExtensions.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = ((BunchOfRegisteredExtensions) next).plus((BunchOfRegisteredExtensions) it.next());
        }
        BunchOfRegisteredExtensions bunchOfRegisteredExtensions = (BunchOfRegisteredExtensions) next;
        if (this.session.getKind() == FirSession.Kind.Library) {
            Map<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> extensions = bunchOfRegisteredExtensions.getExtensions();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> entry : extensions.entrySet()) {
                if (FirExtensionRegistrar.INSTANCE.getALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION$entrypoint().contains(entry.getKey())) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            bunchOfRegisteredExtensions = new BunchOfRegisteredExtensions(linkedHashMap);
        }
        FirExtensionRegistrarKt.registerExtensions(FirExtensionServiceKt.getExtensionService(this.session), bunchOfRegisteredExtensions);
        if (this.session.getKind() == FirSession.Kind.Source) {
            List<FirAdditionalCheckersExtension> additionalCheckers = FirAdditionalCheckersExtensionKt.getAdditionalCheckers(FirExtensionServiceKt.getExtensionService(this.session));
            CheckersComponent checkersComponent = CheckersComponentKt.getCheckersComponent(this.session);
            Iterator<T> it2 = additionalCheckers.iterator();
            while (it2.hasNext()) {
                checkersComponent.register((FirAdditionalCheckersExtension) it2.next());
            }
        }
    }
}
