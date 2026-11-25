package org.jetbrains.kotlin.fir.resolve.transformers.mpp;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContext;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContextKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.ExpectActualAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractTreeTransformer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirExpectActualMatcherTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatcherTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "expectActualMatchingContext", "Lorg/jetbrains/kotlin/fir/FirExpectActualMatchingContext;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "data", "transformConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "transformEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "transformMemberDeclaration", "", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "transformProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "transformTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirExpectActualMatcherTransformer extends FirAbstractTreeTransformer {
    private final FirExpectActualMatchingContext expectActualMatchingContext;
    private final ScopeSession scopeSession;
    private final FirSession session;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, Void data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        return anonymousInitializer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExpectActualMatcherTransformer(FirSession session, ScopeSession scopeSession) {
        super(FirResolvePhase.EXPECT_ACTUAL_MATCHING);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.expectActualMatchingContext = FirExpectActualMatchingContextKt.getExpectActualMatchingContextFactory(session).create(session, scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeAlias(FirTypeAlias typeAlias, Void data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        transformMemberDeclaration(typeAlias);
        return typeAlias;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Void data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        transformMemberDeclaration(regularClass);
        regularClass.transformChildren(this, data);
        return regularClass;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformEnumEntry(FirEnumEntry enumEntry, Void data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        transformMemberDeclaration(enumEntry);
        return enumEntry;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformProperty(FirProperty property, Void data) {
        Intrinsics.checkNotNullParameter(property, "property");
        transformMemberDeclaration(property);
        return property;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformConstructor(FirConstructor constructor, Void data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        transformMemberDeclaration(constructor);
        return constructor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSimpleFunction(FirSimpleFunction simpleFunction, Void data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        transformMemberDeclaration(simpleFunction);
        return simpleFunction;
    }

    public final void transformMemberDeclaration(FirMemberDeclaration memberDeclaration) {
        Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
        if (memberDeclaration.getStatus().isActual()) {
            Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> mapFindExpectForActual = FirExpectActualResolver.INSTANCE.findExpectForActual(memberDeclaration.getSymbol(), this.session, this.scopeSession, this.expectActualMatchingContext);
            if (mapFindExpectForActual == null) {
                mapFindExpectForActual = MapsKt.emptyMap();
            }
            ExpectActualAttributesKt.setExpectForActual(memberDeclaration, mapFindExpectForActual);
        }
    }
}
