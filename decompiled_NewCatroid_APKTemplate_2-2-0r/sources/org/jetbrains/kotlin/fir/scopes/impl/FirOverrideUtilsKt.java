package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;

/* JADX INFO: compiled from: FirOverrideUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001au\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0001\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00042\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u00012?\u0010\u0006\u001a;\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u0003\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\n0\u0007j\b\u0012\u0004\u0012\u0002H\u0003`\u000b¢\u0006\u0002\b\f\u001a&\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00020\u00012\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00020\u0001\u001a&\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00020\u00012\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00020\u0001\u001aj\u0010\u0011\u001a\u00020\u0012\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00022\u0006\u0010\u0014\u001a\u0002H\u000329\u0010\u0006\u001a5\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\n0\u0015\u0012\u0004\u0012\u00020\n0\u0007j\b\u0012\u0004\u0012\u0002H\u0003`\u0016¢\u0006\u0002\b\f¢\u0006\u0002\u0010\u0017¨\u0006\u0018"}, d2 = {"filterOutOverridden", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "extractedOverridden", "processAllOverridden", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessOverriddenWithBaseScope;", "Lkotlin/ExtensionFunctionType;", "filterOutOverriddenFunctions", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "filterOutOverriddenProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "overrides", "", "f", "gMember", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessAllOverridden;", "(Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function3;)Z", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirOverrideUtilsKt {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt$filterOutOverriddenFunctions$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirOverrideUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenFunctionsWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenFunctionsWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirNamedFunctionSymbol p1, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenFunctionsWithBaseScope(p1, p2);
        }
    }

    public static final Collection<MemberWithBaseScope<FirNamedFunctionSymbol>> filterOutOverriddenFunctions(Collection<? extends MemberWithBaseScope<? extends FirNamedFunctionSymbol>> extractedOverridden) {
        Intrinsics.checkNotNullParameter(extractedOverridden, "extractedOverridden");
        return filterOutOverridden(extractedOverridden, AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt$filterOutOverriddenProperties$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirOverrideUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46091 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C46091 INSTANCE = new C46091();

        C46091() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenPropertiesWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenPropertiesWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenPropertiesWithBaseScope(p1, p2);
        }
    }

    public static final Collection<MemberWithBaseScope<FirPropertySymbol>> filterOutOverriddenProperties(Collection<? extends MemberWithBaseScope<? extends FirPropertySymbol>> extractedOverridden) {
        Intrinsics.checkNotNullParameter(extractedOverridden, "extractedOverridden");
        return filterOutOverridden(extractedOverridden, C46091.INSTANCE);
    }

    public static final <D extends FirCallableSymbol<?>> Collection<MemberWithBaseScope<D>> filterOutOverridden(Collection<? extends MemberWithBaseScope<? extends D>> extractedOverridden, final Function3<? super FirTypeScope, ? super D, ? super Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processAllOverridden) {
        Intrinsics.checkNotNullParameter(extractedOverridden, "extractedOverridden");
        Intrinsics.checkNotNullParameter(processAllOverridden, "processAllOverridden");
        Collection<? extends MemberWithBaseScope<? extends D>> collection = extractedOverridden;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) obj;
            if (!collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        MemberWithBaseScope memberWithBaseScope2 = (MemberWithBaseScope) it.next();
                        if (memberWithBaseScope != memberWithBaseScope2 && overrides(memberWithBaseScope2, memberWithBaseScope.getMember(), new Function3<FirTypeScope, D, Function1<? super D, ? extends ProcessorAction>, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt$filterOutOverridden$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(3);
                            }

                            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;TD;Lkotlin/jvm/functions/Function1<-TD;+Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;>;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
                            @Override // kotlin.jvm.functions.Function3
                            public final ProcessorAction invoke(FirTypeScope overrides, FirCallableSymbol symbol, Function1 processor) {
                                Intrinsics.checkNotNullParameter(overrides, "$this$overrides");
                                Intrinsics.checkNotNullParameter(symbol, "symbol");
                                Intrinsics.checkNotNullParameter(processor, "processor");
                                return FirTypeScopeKt.processAllOverriddenCallables(overrides, symbol, processor, processAllOverridden);
                            }
                        })) {
                            break;
                        }
                    }
                }
            }
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static final <D extends FirCallableSymbol<?>> boolean overrides(MemberWithBaseScope<? extends D> f, final D gMember, Function3<? super FirTypeScope, ? super D, ? super Function1<? super D, ? extends ProcessorAction>, ? extends ProcessorAction> processAllOverridden) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(gMember, "gMember");
        Intrinsics.checkNotNullParameter(processAllOverridden, "processAllOverridden");
        FirCallableSymbol firCallableSymbolComponent1 = f.component1();
        FirTypeScope baseScope = f.getBaseScope();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        processAllOverridden.invoke(baseScope, firCallableSymbolComponent1, new Function1<D, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt.overrides.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TD;Lkotlin/jvm/internal/Ref$BooleanRef;)V */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TD;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirCallableSymbol overridden) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                if (Intrinsics.areEqual(overridden, gMember)) {
                    booleanRef.element = true;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return booleanRef.element;
    }
}
