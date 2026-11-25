package org.jetbrains.kotlin.fir.extensions;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: generatedDeclarationsUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\b"}, d2 = {"generatedMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "generatedNestedClassifiers", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GeneratedDeclarationsUtilsKt {
    public static final List<FirClassLikeDeclaration> generatedNestedClassifiers(FirRegularClass firRegularClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirContainingNamesAwareScope firContainingNamesAwareScopeDeclaredMemberScope = FirDeclaredMemberScopeProviderKt.declaredMemberScope(session, firRegularClass, (FirResolvePhase) null);
        final ArrayList arrayList = new ArrayList();
        for (Name name2 : firContainingNamesAwareScopeDeclaredMemberScope.getClassifierNames()) {
            final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt.generatedNestedClassifiers.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                    invoke2(firClassifierSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it.getFir().getOrigin().getGenerated()) {
                        if (!(it instanceof FirClassLikeSymbol)) {
                            throw new IllegalArgumentException(("Plugins can not generate type parameters, but had " + it).toString());
                        }
                        arrayList.add(((FirClassLikeSymbol) it).getFir());
                    }
                }
            };
            firContainingNamesAwareScopeDeclaredMemberScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt$generatedNestedClassifiers$$inlined$processClassifiersByName$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                    function1.invoke(symbol);
                }
            });
        }
        return arrayList;
    }

    public static final List<FirCallableDeclaration> generatedMembers(FirRegularClass firRegularClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirContainingNamesAwareScope firContainingNamesAwareScopeDeclaredMemberScope = FirDeclaredMemberScopeProviderKt.declaredMemberScope(session, firRegularClass, (FirResolvePhase) null);
        final ArrayList arrayList = new ArrayList();
        for (Name name2 : firContainingNamesAwareScopeDeclaredMemberScope.getCallableNames()) {
            firContainingNamesAwareScopeDeclaredMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt.generatedMembers.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (((FirSimpleFunction) it.getFir()).getOrigin().getGenerated()) {
                        arrayList.add(it.getFir());
                    }
                }
            });
            firContainingNamesAwareScopeDeclaredMemberScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt.generatedMembers.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (((FirVariable) it.getFir()).getOrigin().getGenerated()) {
                        arrayList.add(it.getFir());
                    }
                }
            });
        }
        firContainingNamesAwareScopeDeclaredMemberScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt.generatedMembers.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (((FirConstructor) it.getFir()).getOrigin().getGenerated()) {
                    arrayList.add(it.getFir());
                }
            }
        });
        return arrayList;
    }
}
