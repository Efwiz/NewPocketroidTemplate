package org.jetbrains.kotlin.fir.java.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: JavaClassStaticUseSiteScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B5\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u001a\u0010\u001c\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u001a2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001eH\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\u001eH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\rH\u0016J$\u0010\"\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\r2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020#0%H\u0016J(\u0010&\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\r2\u0016\u0010$\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0012\u0004\u0012\u00020#0%H\u0016R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000b\u001a*\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\fj\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u0013\u001a2\u0012\u0004\u0012\u00020\r\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u000e0\fj\u0018\u0012\u0004\u0012\u00020\r\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u000e`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassStaticUseSiteScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "declaredMemberScope", "superClassScope", "superTypesScopes", "", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Ljava/util/List;Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;)V", "functions", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lkotlin/collections/HashMap;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverrideChecker;", "properties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "scopeOwnerLookupNames", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "computeFunctions", "", "name", "computeProperties", "getCallableNames", "", "getClassifierNames", "mayContainName", "", "processFunctionsByName", "", "processor", "Lkotlin/Function1;", "processPropertiesByName", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassStaticUseSiteScope extends FirContainingNamesAwareScope {
    private final FirContainingNamesAwareScope declaredMemberScope;
    private final HashMap<Name, Collection<FirNamedFunctionSymbol>> functions;
    private final JavaOverrideChecker overrideChecker;
    private final HashMap<Name, Collection<FirVariableSymbol<?>>> properties;
    private final FirContainingNamesAwareScope superClassScope;
    private final List<FirContainingNamesAwareScope> superTypesScopes;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaClassStaticUseSiteScope(FirSession session, FirContainingNamesAwareScope declaredMemberScope, FirContainingNamesAwareScope superClassScope, List<? extends FirContainingNamesAwareScope> superTypesScopes, JavaTypeParameterStack javaTypeParameterStack) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        Intrinsics.checkNotNullParameter(superClassScope, "superClassScope");
        Intrinsics.checkNotNullParameter(superTypesScopes, "superTypesScopes");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        this.declaredMemberScope = declaredMemberScope;
        this.superClassScope = superClassScope;
        this.superTypesScopes = superTypesScopes;
        this.functions = new HashMap<>();
        this.properties = new HashMap<>();
        this.overrideChecker = new JavaOverrideChecker(session, javaTypeParameterStack, null, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        HashMap<Name, Collection<FirNamedFunctionSymbol>> map = this.functions;
        List<FirNamedFunctionSymbol> listComputeFunctions = map.get(name2);
        if (listComputeFunctions == null) {
            listComputeFunctions = computeFunctions(name2);
            map.put(name2, listComputeFunctions);
        }
        Iterator<T> it = listComputeFunctions.iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }

    private final List<FirNamedFunctionSymbol> computeFunctions(Name name2) {
        final ArrayList arrayList = new ArrayList();
        this.superClassScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope.computeFunctions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CollectionsKt.addIfNotNull(arrayList, it);
            }
        });
        final ArrayList arrayList2 = new ArrayList();
        this.declaredMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope.computeFunctions.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(final FirNamedFunctionSymbol functionSymbol) {
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                if (functionSymbol.getRawStatus().isStatic()) {
                    arrayList2.add(functionSymbol);
                    List<FirNamedFunctionSymbol> list = arrayList;
                    final JavaClassStaticUseSiteScope javaClassStaticUseSiteScope = this;
                    kotlin.collections.CollectionsKt.removeAll((List) list, (Function1) new Function1<FirNamedFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope.computeFunctions.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(FirNamedFunctionSymbol superClassSymbol) {
                            Intrinsics.checkNotNullParameter(superClassSymbol, "superClassSymbol");
                            return Boolean.valueOf(javaClassStaticUseSiteScope.overrideChecker.isOverriddenFunction((FirSimpleFunction) functionSymbol.getFir(), (FirSimpleFunction) superClassSymbol.getFir()));
                        }
                    });
                }
            }
        });
        kotlin.collections.CollectionsKt.addAll(arrayList2, arrayList);
        return arrayList2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        HashMap<Name, Collection<FirVariableSymbol<?>>> map = this.properties;
        List<FirVariableSymbol<?>> listComputeProperties = map.get(name2);
        if (listComputeProperties == null) {
            listComputeProperties = computeProperties(name2);
            map.put(name2, listComputeProperties);
        }
        Iterator<T> it = listComputeProperties.iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }

    private final List<FirVariableSymbol<?>> computeProperties(Name name2) {
        final ArrayList arrayList = new ArrayList();
        this.declaredMemberScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope.computeProperties.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                if (propertySymbol.getRawStatus().isStatic()) {
                    arrayList.add(propertySymbol);
                }
            }
        });
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        Iterator<FirContainingNamesAwareScope> it = this.superTypesScopes.iterator();
        while (it.hasNext()) {
            it.next().processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope.computeProperties.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                    Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                    if (propertySymbol.getRawStatus().isStatic()) {
                        arrayList.add(propertySymbol);
                    }
                }
            });
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.addAll(this.declaredMemberScope.getCallableNames());
        Iterator<FirContainingNamesAwareScope> it = this.superTypesScopes.iterator();
        while (it.hasNext()) {
            setCreateSetBuilder.addAll(it.next().getCallableNames());
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.addAll(this.declaredMemberScope.getClassifierNames());
        Iterator<FirContainingNamesAwareScope> it = this.superTypesScopes.iterator();
        while (it.hasNext()) {
            setCreateSetBuilder.addAll(it.next().getClassifierNames());
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!this.declaredMemberScope.mayContainName(name2)) {
            List<FirContainingNamesAwareScope> list = this.superTypesScopes;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((FirContainingNamesAwareScope) it.next()).mayContainName(name2)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return this.declaredMemberScope.getScopeOwnerLookupNames();
    }
}
