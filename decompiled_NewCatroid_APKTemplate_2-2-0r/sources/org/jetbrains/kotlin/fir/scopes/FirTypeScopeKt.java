package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\u001a8\u0010\u0000\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0082\b¢\u0006\u0002\u0010\b\u001a&\u0010\t\u001a\u00020\u0007*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00070\u000e\u001a\u008c\u0001\u0010\u000f\u001a\u00020\u0010\"\f\b\u0000\u0010\u0011*\u0006\u0012\u0002\b\u00030\u0003*\u00020\n2\u0006\u0010\u0012\u001a\u0002H\u00112\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\u00100\u000e25\u0010\u0014\u001a1\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0011\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u0016\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u00172\u0018\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u00110\u001a0\u0019H\u0002¢\u0006\u0002\u0010\u001b\u001a\u0092\u0001\u0010\u000f\u001a\u00020\u0010\"\f\b\u0000\u0010\u0011*\u0006\u0012\u0002\b\u00030\u0003*\u00020\n2\u0006\u0010\u0012\u001a\u0002H\u00112\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u001625\u0010\u0014\u001a1\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0011\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u0016\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u00172\u0018\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u00110\u001a0\u0019H\u0002¢\u0006\u0002\u0010\u001c\u001a\"\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u001e*\u00020\n2\u0006\u0010\u001f\u001a\u00020\f2\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a\u001e\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0!0\u001e*\u00020\n2\u0006\u0010\u001f\u001a\u00020\f\u001a.\u0010\"\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020#0\u00030\u001e*\u00020\n2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a&\u0010%\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030!0\u001e*\u00020\n2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u0003\u001a\"\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u001e*\u00020\n2\u0006\u0010(\u001a\u00020'2\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a\u001e\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0!0\u001e*\u00020\n2\u0006\u0010(\u001a\u00020'\u001az\u0010*\u001a\u00020\u0010\"\f\b\u0000\u0010\u0011*\u0006\u0012\u0002\b\u00030\u0003*\u00020\n2\u0006\u0010\u0012\u001a\u0002H\u00112\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\u00100\u000e2?\u0010\u0014\u001a;\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0011\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u0016\u0012\u0004\u0012\u00020\u00100\u0015j\b\u0012\u0004\u0012\u0002H\u0011`+¢\u0006\u0002\b\u0017¢\u0006\u0002\u0010,\u001a.\u0010-\u001a\u00020\u0010*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0014\b\u0004\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u000eH\u0086\bø\u0001\u0000\u001a.\u0010.\u001a\u00020\u0010*\u00020\n2\u0006\u0010/\u001a\u00020'2\u0014\b\u0004\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00100\u000eH\u0086\bø\u0001\u0000\u001a,\u00100\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u001e2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u000e\u001a&\u00100\u001a\u00020\u0010*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u000e\u001aB\u00101\u001a\u00020\u0010*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0018\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\f0\u001a0\u00192\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u000eH\u0002\u001a,\u00102\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u001e2\u0006\u0010/\u001a\u00020'2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00100\u000e\u001a&\u00102\u001a\u00020\u0010*\u00020\n2\u0006\u0010/\u001a\u00020'2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00100\u000e\u001aD\u00103\u001a\u00020\u0010*\u00020\n2\u0006\u0010/\u001a\u00020'2\u001a\b\u0002\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020'0\u001a0\u00192\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00100\u000eH\u0002\u001a \u00104\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u001e*\u00020\n2\n\u00105\u001a\u0006\u0012\u0002\b\u00030\u0003*b\u00106\u001a\u0004\b\u0000\u0010\u0002\"+\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000e\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u00172+\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000e\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u0017*n\u00107\u001a\u0004\b\u0000\u0010\u0002\"1\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0002\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u0016\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u001721\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u0002\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u0016\u0012\u0004\u0012\u00020\u00100\u0015¢\u0006\u0002\b\u0017\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00068"}, d2 = {"addOverridden", "", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", NativeSymbol.TYPE_NAME, "unwrapIntersectionAndSubstitutionOverride", "", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Z)V", "anyOverriddenOf", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "predicate", "Lkotlin/Function1;", "doProcessAllOverriddenCallables", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", ExifInterface.LATITUDE_SOUTH, "callableSymbol", "processor", "processDirectOverriddenCallablesWithBaseScope", "Lkotlin/Function3;", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "visited", "", "Lkotlin/Pair;", "(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;Ljava/util/Set;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Ljava/util/Set;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "getDirectOverriddenFunctions", "", "function", "getDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "getDirectOverriddenMembers", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "member", "getDirectOverriddenMembersWithBaseScope", "getDirectOverriddenProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "property", "getDirectOverriddenPropertiesWithBaseScope", "processAllOverriddenCallables", "Lorg/jetbrains/kotlin/fir/scopes/ProcessOverriddenWithBaseScope;", "(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "processDirectlyOverriddenFunctions", "processDirectlyOverriddenProperties", "propertySymbol", "processOverriddenFunctions", "processOverriddenFunctionsWithVisited", "processOverriddenProperties", "processOverriddenPropertiesWithVisited", "retrieveDirectOverriddenOf", "memberSymbol", "ProcessAllOverridden", "ProcessOverriddenWithBaseScope", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTypeScopeKt {
    public static final ProcessorAction processOverriddenFunctions(FirTypeScope firTypeScope, FirNamedFunctionSymbol functionSymbol, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return doProcessAllOverriddenCallables(firTypeScope, functionSymbol, processor, C45911.INSTANCE, new LinkedHashSet());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$processOverriddenFunctions$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTypeScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45911 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45911 INSTANCE = new C45911();

        C45911() {
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

    public static final boolean anyOverriddenOf(FirTypeScope firTypeScope, FirNamedFunctionSymbol functionSymbol, final Function1<? super FirNamedFunctionSymbol, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        processOverriddenFunctions(firTypeScope, functionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.anyOverriddenOf.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (predicate.invoke(it).booleanValue()) {
                    booleanRef.element = true;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return booleanRef.element;
    }

    private static final ProcessorAction processOverriddenFunctionsWithVisited(FirTypeScope firTypeScope, FirNamedFunctionSymbol firNamedFunctionSymbol, Set<Pair<FirTypeScope, FirNamedFunctionSymbol>> set, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> function1) {
        return doProcessAllOverriddenCallables(firTypeScope, firNamedFunctionSymbol, function1, C45921.INSTANCE, set);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$processOverriddenFunctionsWithVisited$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTypeScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45921 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45921 INSTANCE = new C45921();

        C45921() {
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

    public static final ProcessorAction processOverriddenProperties(FirTypeScope firTypeScope, FirPropertySymbol propertySymbol, Function1<? super FirPropertySymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return doProcessAllOverriddenCallables(firTypeScope, propertySymbol, processor, C45931.INSTANCE, new LinkedHashSet());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$processOverriddenProperties$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTypeScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45931 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45931 INSTANCE = new C45931();

        C45931() {
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

    private static final ProcessorAction processOverriddenPropertiesWithVisited(FirTypeScope firTypeScope, FirPropertySymbol firPropertySymbol, Set<Pair<FirTypeScope, FirPropertySymbol>> set, Function1<? super FirPropertySymbol, ? extends ProcessorAction> function1) {
        return doProcessAllOverriddenCallables(firTypeScope, firPropertySymbol, function1, C45941.INSTANCE, set);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$processOverriddenPropertiesWithVisited$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTypeScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45941 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45941 INSTANCE = new C45941();

        C45941() {
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

    public static final void processOverriddenFunctions(List<? extends FirTypeScope> list, FirNamedFunctionSymbol functionSymbol, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends FirTypeScope> it = list.iterator();
        while (it.hasNext() && !processOverriddenFunctionsWithVisited(it.next(), functionSymbol, linkedHashSet, processor).not()) {
        }
    }

    public static final void processOverriddenProperties(List<? extends FirTypeScope> list, FirPropertySymbol propertySymbol, Function1<? super FirPropertySymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<? extends FirTypeScope> it = list.iterator();
        while (it.hasNext() && !processOverriddenPropertiesWithVisited(it.next(), propertySymbol, linkedHashSet, processor).not()) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <S extends FirCallableSymbol<?>> ProcessorAction doProcessAllOverriddenCallables(FirTypeScope firTypeScope, S s, final Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction> function2, final Function3<? super FirTypeScope, ? super S, ? super Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> function3, final Set<Pair<FirTypeScope, S>> set) {
        return !set.add(TuplesKt.to(firTypeScope, s)) ? ProcessorAction.NONE : function3.invoke(firTypeScope, s, new Function2<S, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.doProcessAllOverriddenCallables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Incorrect types in method signature: (TS;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirCallableSymbol overridden, FirTypeScope baseScope) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                return function2.invoke(overridden, baseScope).not() ? ProcessorAction.STOP : FirTypeScopeKt.doProcessAllOverriddenCallables(baseScope, overridden, (Function2<? super FirCallableSymbol, ? super FirTypeScope, ? extends ProcessorAction>) function2, (Function3<? super FirTypeScope, ? super FirCallableSymbol, ? super Function2<? super FirCallableSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction>) function3, (Set<Pair<FirTypeScope, FirCallableSymbol>>) set);
            }
        });
    }

    public static final <S extends FirCallableSymbol<?>> ProcessorAction processAllOverriddenCallables(FirTypeScope firTypeScope, S callableSymbol, Function1<? super S, ? extends ProcessorAction> processor, Function3<? super FirTypeScope, ? super S, ? super Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverriddenCallablesWithBaseScope) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(callableSymbol, "callableSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(processDirectOverriddenCallablesWithBaseScope, "processDirectOverriddenCallablesWithBaseScope");
        return doProcessAllOverriddenCallables(firTypeScope, callableSymbol, processor, processDirectOverriddenCallablesWithBaseScope, new LinkedHashSet());
    }

    private static final <S extends FirCallableSymbol<?>> ProcessorAction doProcessAllOverriddenCallables(FirTypeScope firTypeScope, S s, final Function1<? super S, ? extends ProcessorAction> function1, Function3<? super FirTypeScope, ? super S, ? super Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> function3, Set<Pair<FirTypeScope, S>> set) {
        return doProcessAllOverriddenCallables(firTypeScope, s, new Function2<S, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.doProcessAllOverriddenCallables.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Incorrect types in method signature: (TS;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirCallableSymbol s2, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                return function1.invoke(s2);
            }
        }, function3, set);
    }

    public static final ProcessorAction processDirectlyOverriddenFunctions(FirTypeScope firTypeScope, FirNamedFunctionSymbol functionSymbol, final Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return firTypeScope.processDirectOverriddenFunctionsWithBaseScope(functionSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.processDirectlyOverriddenFunctions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                return processor.invoke(overridden);
            }
        });
    }

    public static final ProcessorAction processDirectlyOverriddenProperties(FirTypeScope firTypeScope, FirPropertySymbol propertySymbol, final Function1<? super FirPropertySymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return firTypeScope.processDirectOverriddenPropertiesWithBaseScope(propertySymbol, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.processDirectlyOverriddenProperties.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                return processor.invoke(overridden);
            }
        });
    }

    public static /* synthetic */ List getDirectOverriddenMembers$default(FirTypeScope firTypeScope, FirCallableSymbol firCallableSymbol, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getDirectOverriddenMembers(firTypeScope, firCallableSymbol, z);
    }

    public static final List<FirCallableSymbol<? extends FirCallableDeclaration>> getDirectOverriddenMembers(FirTypeScope firTypeScope, FirCallableSymbol<?> member, boolean z) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(member, "member");
        if (member instanceof FirNamedFunctionSymbol) {
            return getDirectOverriddenFunctions(firTypeScope, (FirNamedFunctionSymbol) member, z);
        }
        return member instanceof FirPropertySymbol ? getDirectOverriddenProperties(firTypeScope, (FirPropertySymbol) member, z) : CollectionsKt.emptyList();
    }

    public static final List<MemberWithBaseScope<FirCallableSymbol<?>>> getDirectOverriddenMembersWithBaseScope(FirTypeScope firTypeScope, FirCallableSymbol<?> member) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(member, "member");
        if (member instanceof FirNamedFunctionSymbol) {
            return getDirectOverriddenFunctionsWithBaseScope(firTypeScope, (FirNamedFunctionSymbol) member);
        }
        return member instanceof FirPropertySymbol ? getDirectOverriddenPropertiesWithBaseScope(firTypeScope, (FirPropertySymbol) member) : CollectionsKt.emptyList();
    }

    public static final List<MemberWithBaseScope<FirNamedFunctionSymbol>> getDirectOverriddenFunctionsWithBaseScope(FirTypeScope firTypeScope, FirNamedFunctionSymbol function) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        firTypeScope.processDirectOverriddenFunctionsWithBaseScope(function, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.getDirectOverriddenFunctionsWithBaseScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol symbol, FirTypeScope baseScope) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                linkedHashSet.add(new MemberWithBaseScope<>(symbol, baseScope));
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final List<MemberWithBaseScope<FirPropertySymbol>> getDirectOverriddenPropertiesWithBaseScope(FirTypeScope firTypeScope, FirPropertySymbol property) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        firTypeScope.processDirectOverriddenPropertiesWithBaseScope(property, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.getDirectOverriddenPropertiesWithBaseScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol symbol, FirTypeScope baseScope) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                linkedHashSet.add(new MemberWithBaseScope<>(symbol, baseScope));
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static /* synthetic */ List getDirectOverriddenFunctions$default(FirTypeScope firTypeScope, FirNamedFunctionSymbol firNamedFunctionSymbol, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getDirectOverriddenFunctions(firTypeScope, firNamedFunctionSymbol, z);
    }

    public static final List<FirNamedFunctionSymbol> getDirectOverriddenFunctions(FirTypeScope firTypeScope, FirNamedFunctionSymbol function, final boolean z) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        firTypeScope.processDirectOverriddenFunctionsWithBaseScope(function, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$getDirectOverriddenFunctions$$inlined$processDirectlyOverriddenFunctions$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                Set set = linkedHashSet;
                if (z) {
                    FirNamedFunctionSymbol firNamedFunctionSymbol = overridden;
                    if (firNamedFunctionSymbol instanceof FirIntersectionCallableSymbol) {
                        Collection<FirCallableSymbol<?>> intersections = ((FirIntersectionCallableSymbol) firNamedFunctionSymbol).getIntersections();
                        Intrinsics.checkNotNull(intersections, "null cannot be cast to non-null type kotlin.collections.Collection<D of org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.addOverridden>");
                        set.addAll(intersections);
                    } else {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        Object obj = (FirCallableSymbol) ((FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null));
                        Object obj2 = firNamedFunctionSymbol;
                        if (obj != null) {
                            obj2 = obj;
                        }
                        set.add(obj2);
                    }
                } else {
                    set.add(overridden);
                }
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static /* synthetic */ List getDirectOverriddenProperties$default(FirTypeScope firTypeScope, FirPropertySymbol firPropertySymbol, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getDirectOverriddenProperties(firTypeScope, firPropertySymbol, z);
    }

    public static final List<FirPropertySymbol> getDirectOverriddenProperties(FirTypeScope firTypeScope, FirPropertySymbol property, final boolean z) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        firTypeScope.processDirectOverriddenPropertiesWithBaseScope(property, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt$getDirectOverriddenProperties$$inlined$processDirectlyOverriddenProperties$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                Set set = linkedHashSet;
                if (z) {
                    FirPropertySymbol firPropertySymbol = overridden;
                    if (firPropertySymbol instanceof FirIntersectionCallableSymbol) {
                        Collection<FirCallableSymbol<?>> intersections = ((FirIntersectionCallableSymbol) firPropertySymbol).getIntersections();
                        Intrinsics.checkNotNull(intersections, "null cannot be cast to non-null type kotlin.collections.Collection<D of org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.addOverridden>");
                        set.addAll(intersections);
                    } else {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        Object obj = (FirCallableSymbol) ((FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null));
                        Object obj2 = firPropertySymbol;
                        if (obj != null) {
                            obj2 = obj;
                        }
                        set.add(obj2);
                    }
                } else {
                    set.add(overridden);
                }
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final List<FirCallableSymbol<?>> retrieveDirectOverriddenOf(FirTypeScope firTypeScope, FirCallableSymbol<?> memberSymbol) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(memberSymbol, "memberSymbol");
        if (memberSymbol instanceof FirNamedFunctionSymbol) {
            firTypeScope.processFunctionsByName(memberSymbol.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.retrieveDirectOverriddenOf.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }
            });
            return getDirectOverriddenFunctions$default(firTypeScope, (FirNamedFunctionSymbol) memberSymbol, false, 2, null);
        }
        if (memberSymbol instanceof FirPropertySymbol) {
            firTypeScope.processPropertiesByName(memberSymbol.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.retrieveDirectOverriddenOf.2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }
            });
            return getDirectOverriddenProperties$default(firTypeScope, (FirPropertySymbol) memberSymbol, false, 2, null);
        }
        throw new IllegalArgumentException("unexpected member kind " + memberSymbol);
    }
}
