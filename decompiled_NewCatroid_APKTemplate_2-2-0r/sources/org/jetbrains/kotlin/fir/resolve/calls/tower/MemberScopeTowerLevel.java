package org.jetbrains.kotlin.fir.resolve.calls.tower;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope;
import org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJV\u0010\u0016\u001a\u00020\u0017\"\f\b\u0000\u0010\u0018*\u0006\u0012\u0002\b\u00030\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u001e0\u001d2\u001c\b\u0002\u0010\u001f\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u001e\u0012\u0004\u0012\u00020\n\u0018\u00010 H\u0002J\n\u0010!\u001a\u0004\u0018\u00010\bH\u0002J\"\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0010\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030'0\u001bH\u0016J^\u0010(\u001a\u00020#\"\f\b\u0000\u0010\u0018*\u0006\u0012\u0002\b\u00030\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001b28\u0010)\u001a4\u0012\u0004\u0012\u00020+\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u00020\u00170,¢\u0006\f\b-\u0012\b\b.\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u00170*¢\u0006\u0002\b/H\u0002J\"\u00100\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0010\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003010\u001bH\u0016J\"\u00102\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0010\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003030\u001bH\u0016JI\u00104\u001a\u00020\u00172\b\u00105\u001a\u0004\u0018\u0001062\u0006\u0010$\u001a\u00020%2,\u00107\u001a(\u0012\u001e\u0012\u001c\u0012\u0006\u0012\u0004\u0018\u000106\u0012\n\u0012\b\u0012\u0004\u0012\u00020:09\u0012\u0004\u0012\u00020%08\u0012\u0004\u0012\u00020\u00170,H\u0082\bJl\u0010;\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u001e0\u00070<\"\f\b\u0000\u0010\u0018*\u0006\u0012\u0002\b\u00030\u0019*\u00020=28\u0010)\u001a4\u0012\u0004\u0012\u00020+\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u00020\u00170,¢\u0006\f\b-\u0012\b\b.\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u00170*¢\u0006\u0002\b/H\u0002J\u001e\u0010>\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\u00192\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J2\u0010@\u001a\u00020\u0017*\u001c\u0012\u0006\u0012\u0004\u0018\u000106\u0012\n\u0012\b\u0012\u0004\u0012\u00020:09\u0012\u0004\u0012\u00020%082\n\u0010A\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/MemberScopeTowerLevel;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "dispatchReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "givenExtensionReceiverOptions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "skipSynthetics", "", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;Ljava/util/List;Z)V", "getDispatchReceiverValue", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "consumeCandidates", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "output", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", "candidates", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "isFromSmartCast", "", "getOriginalReceiverExpressionIfStableSmartCast", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "processor", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "processMembers", "processScopeMembers", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlin/ExtensionFunctionType;", "processObjectsByName", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "withMemberCallLookup", "lookupTracker", "Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;", "body", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/utils/SmartList;", "", "collectCandidates", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "hasConsistentExtensionReceiver", "givenExtensionReceivers", "recordCallableMemberLookup", "callable", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemberScopeTowerLevel extends TowerScopeLevel {
    private final BodyResolveComponents bodyResolveComponents;
    private final ReceiverValue dispatchReceiverValue;
    private final List<FirExpression> givenExtensionReceiverOptions;
    private final boolean skipSynthetics;

    public final ReceiverValue getDispatchReceiverValue() {
        return this.dispatchReceiverValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MemberScopeTowerLevel(BodyResolveComponents bodyResolveComponents, ReceiverValue dispatchReceiverValue, List<? extends FirExpression> givenExtensionReceiverOptions, boolean z) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(dispatchReceiverValue, "dispatchReceiverValue");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        this.bodyResolveComponents = bodyResolveComponents;
        this.dispatchReceiverValue = dispatchReceiverValue;
        this.givenExtensionReceiverOptions = givenExtensionReceiverOptions;
        this.skipSynthetics = z;
    }

    private final ScopeSession getScopeSession() {
        return this.bodyResolveComponents.getScopeSession();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirSession getSession() {
        return this.bodyResolveComponents.getSession();
    }

    private final <T extends FirCallableSymbol<?>> ProcessResult processMembers(final TowerScopeLevel.TowerScopeLevelProcessor<? super T> output, Function2<? super FirScope, ? super Function1<? super T, Unit>, Unit> processScopeMembers) {
        FirTypeScope firTypeScopeScope;
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        final FirTypeScope firTypeScopeScope2 = this.dispatchReceiverValue.scope(getSession(), getScopeSession());
        if (firTypeScopeScope2 == null) {
            return ProcessResult.SCOPE_EMPTY;
        }
        Pair<Boolean, List<MemberWithBaseScope<T>>> pairCollectCandidates = collectCandidates(firTypeScopeScope2, processScopeMembers);
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = pairCollectCandidates.component1().booleanValue();
        List<MemberWithBaseScope<T>> listComponent2 = pairCollectCandidates.component2();
        FirExpression originalReceiverExpressionIfStableSmartCast = getOriginalReceiverExpressionIfStableSmartCast();
        FirTypeScope firTypeScopeScope3 = (originalReceiverExpressionIfStableSmartCast == null || (typeRef = originalReceiverExpressionIfStableSmartCast.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) ? null : ScopeUtilsKt.scope(coneType, getSession(), getScopeSession(), this.bodyResolveComponents.getReturnTypeCalculator().getFakeOverrideTypeCalculator(), FirResolvePhase.STATUS);
        if (firTypeScopeScope3 == null) {
            consumeCandidates$default(this, output, listComponent2, null, 4, null);
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Pair<Boolean, List<MemberWithBaseScope<T>>> pairCollectCandidates2 = collectCandidates(firTypeScopeScope3, processScopeMembers);
            boolean zBooleanValue = pairCollectCandidates2.component1().booleanValue();
            List<MemberWithBaseScope<T>> listComponent3 = pairCollectCandidates2.component2();
            booleanRef.element = booleanRef.element && zBooleanValue;
            Iterator<MemberWithBaseScope<T>> it = listComponent3.iterator();
            while (it.hasNext()) {
                linkedHashMap.put(it.next(), false);
            }
            Iterator<MemberWithBaseScope<T>> it2 = listComponent2.iterator();
            while (it2.hasNext()) {
                linkedHashMap.put(it2.next(), true);
            }
            consumeCandidates(output, linkedHashMap.keySet(), linkedHashMap);
        }
        if (this.givenExtensionReceiverOptions.isEmpty() && !this.skipSynthetics) {
            ConeKotlinType type = this.dispatchReceiverValue.getType();
            if (TypeUtilsKt.isRaw(type)) {
                ConeKotlinType coneKotlinTypeConvertToNonRawVersion = TypeUtilsKt.convertToNonRawVersion(type);
                firTypeScopeScope = ScopeUtilsKt.scope(coneKotlinTypeConvertToNonRawVersion, getSession(), getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
                if (firTypeScopeScope == null) {
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("No scope for flexible type scope, while it's not null", null);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, "dispatchReceiverType", type);
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
                type = coneKotlinTypeConvertToNonRawVersion;
            } else {
                firTypeScopeScope = firTypeScopeScope2;
            }
            FirSyntheticPropertiesScope firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined = FirSyntheticPropertiesScope.INSTANCE.createIfSyntheticNamesProviderIsDefined(getSession(), type, firTypeScopeScope, this.bodyResolveComponents.getReturnTypeCalculator());
            if (firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined != null) {
                processScopeMembers.invoke(firSyntheticPropertiesScopeCreateIfSyntheticNamesProviderIsDefined, new Function1<T, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel.processMembers.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                        invoke((FirCallableSymbol) obj);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Incorrect types in method signature: (TT;)V */
                    public final void invoke(FirCallableSymbol symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        booleanRef.element = false;
                        TowerScopeLevel.TowerScopeLevelProcessor.consumeCandidate$default(output, symbol, this.getDispatchReceiverValue().getReceiverExpression(), CollectionsKt.emptyList(), firTypeScopeScope2, false, false, 48, null);
                    }
                });
            }
        }
        return booleanRef.element ? ProcessResult.SCOPE_EMPTY : ProcessResult.FOUND;
    }

    private final <T extends FirCallableSymbol<?>> Pair<Boolean, List<MemberWithBaseScope<T>>> collectCandidates(final FirTypeScope firTypeScope, Function2<? super FirScope, ? super Function1<? super T, Unit>, Unit> function2) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        final ArrayList arrayList = new ArrayList();
        function2.invoke(firTypeScope, new Function1<T, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel.collectCandidates.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke((FirCallableSymbol) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Incorrect types in method signature: (TT;)V */
            /* JADX WARN: Multi-variable type inference failed */
            public final void invoke(FirCallableSymbol candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                booleanRef.element = false;
                MemberScopeTowerLevel memberScopeTowerLevel = this;
                if (memberScopeTowerLevel.hasConsistentExtensionReceiver(candidate, memberScopeTowerLevel.givenExtensionReceiverOptions)) {
                    FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) candidate.getFir();
                    FirConstructor firConstructor = firCallableDeclaration instanceof FirConstructor ? (FirConstructor) firCallableDeclaration : null;
                    if (firConstructor == null || firConstructor.getStatus().isInner()) {
                        arrayList.add(new MemberWithBaseScope<>(candidate, firTypeScope));
                    }
                }
            }
        });
        return TuplesKt.to(Boolean.valueOf(booleanRef.element), arrayList);
    }

    static /* synthetic */ void consumeCandidates$default(MemberScopeTowerLevel memberScopeTowerLevel, TowerScopeLevel.TowerScopeLevelProcessor towerScopeLevelProcessor, Collection collection, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = null;
        }
        memberScopeTowerLevel.consumeCandidates(towerScopeLevelProcessor, collection, map);
    }

    private final <T extends FirCallableSymbol<?>> void consumeCandidates(TowerScopeLevel.TowerScopeLevelProcessor<? super T> output, Collection<? extends MemberWithBaseScope<? extends T>> candidates, Map<MemberWithBaseScope<T>, Boolean> isFromSmartCast) {
        FirExpression receiverExpression;
        for (MemberWithBaseScope<? extends T> memberWithBaseScope : candidates) {
            FirCallableSymbol<?> firCallableSymbolComponent1 = memberWithBaseScope.component1();
            FirTypeScope baseScope = memberWithBaseScope.getBaseScope();
            if (hasConsistentExtensionReceiver(firCallableSymbolComponent1, this.givenExtensionReceiverOptions)) {
                boolean z = (isFromSmartCast == null || ((Boolean) MapsKt.getValue(isFromSmartCast, memberWithBaseScope)).booleanValue()) ? false : true;
                if (z) {
                    receiverExpression = getOriginalReceiverExpressionIfStableSmartCast();
                } else {
                    receiverExpression = this.dispatchReceiverValue.getReceiverExpression();
                }
                TowerScopeLevel.TowerScopeLevelProcessor.consumeCandidate$default(output, firCallableSymbolComponent1, receiverExpression, this.givenExtensionReceiverOptions, baseScope, false, z, 16, null);
            }
        }
    }

    private final FirExpression getOriginalReceiverExpressionIfStableSmartCast() {
        FirExpression receiverExpression = this.dispatchReceiverValue.getReceiverExpression();
        FirSmartCastExpression firSmartCastExpression = receiverExpression instanceof FirSmartCastExpression ? (FirSmartCastExpression) receiverExpression : null;
        if (firSmartCastExpression == null) {
            return null;
        }
        if (!firSmartCastExpression.isStable()) {
            firSmartCastExpression = null;
        }
        if (firSmartCastExpression != null) {
            return firSmartCastExpression.getOriginalExpression();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processFunctionsByName(final CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirFunctionSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        return processMembers(processor, new Function2<FirScope, Function1<? super FirFunctionSymbol<?>, ? extends Unit>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirScope firScope, Function1<? super FirFunctionSymbol<?>, ? extends Unit> function1) {
                invoke2(firScope, (Function1<? super FirFunctionSymbol<?>, Unit>) function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirScope processMembers, final Function1<? super FirFunctionSymbol<?>, Unit> consumer) {
                Intrinsics.checkNotNullParameter(processMembers, "$this$processMembers");
                Intrinsics.checkNotNullParameter(consumer, "consumer");
                final MemberScopeTowerLevel memberScopeTowerLevel = MemberScopeTowerLevel.this;
                FirLookupTrackerComponent firLookupTrackerComponent = lookupTracker;
                CallInfo callInfo = info;
                if (firLookupTrackerComponent != null) {
                    FirLookupTrackerComponentKt.recordCallLookup(firLookupTrackerComponent, callInfo, memberScopeTowerLevel.getDispatchReceiverValue().getType());
                }
                SmartList smartList = new SmartList();
                final Triple triple = new Triple(firLookupTrackerComponent, smartList, callInfo);
                ConstructorProcessingKt.processFunctionsAndConstructorsByName(processMembers, callInfo, memberScopeTowerLevel.getSession(), memberScopeTowerLevel.bodyResolveComponents, true, new Function1<FirCallableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel$processFunctionsByName$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
                        invoke2(firCallableSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirCallableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        memberScopeTowerLevel.recordCallableMemberLookup(triple, it);
                        consumer.invoke((FirFunctionSymbol) it);
                    }
                });
                if (smartList.isEmpty() || firLookupTrackerComponent == null) {
                    return;
                }
                FirLookupTrackerComponentKt.recordCallLookup(firLookupTrackerComponent, callInfo, smartList);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processPropertiesByName(final CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirVariableSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        return processMembers(processor, new Function2<FirScope, Function1<? super FirVariableSymbol<?>, ? extends Unit>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel.processPropertiesByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirScope firScope, Function1<? super FirVariableSymbol<?>, ? extends Unit> function1) {
                invoke2(firScope, (Function1<? super FirVariableSymbol<?>, Unit>) function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirScope processMembers, final Function1<? super FirVariableSymbol<?>, Unit> consumer) {
                Intrinsics.checkNotNullParameter(processMembers, "$this$processMembers");
                Intrinsics.checkNotNullParameter(consumer, "consumer");
                final MemberScopeTowerLevel memberScopeTowerLevel = MemberScopeTowerLevel.this;
                FirLookupTrackerComponent firLookupTrackerComponent = lookupTracker;
                CallInfo callInfo = info;
                if (firLookupTrackerComponent != null) {
                    FirLookupTrackerComponentKt.recordCallLookup(firLookupTrackerComponent, callInfo, memberScopeTowerLevel.getDispatchReceiverValue().getType());
                }
                SmartList smartList = new SmartList();
                final Triple triple = new Triple(firLookupTrackerComponent, smartList, callInfo);
                if (firLookupTrackerComponent != null) {
                    FirLookupTrackerComponentKt.recordCallLookup(firLookupTrackerComponent, callInfo, memberScopeTowerLevel.getDispatchReceiverValue().getType());
                }
                processMembers.processPropertiesByName(callInfo.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.MemberScopeTowerLevel$processPropertiesByName$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        memberScopeTowerLevel.recordCallableMemberLookup(triple, it);
                        consumer.invoke(it);
                    }
                });
                if (smartList.isEmpty() || firLookupTrackerComponent == null) {
                    return;
                }
                FirLookupTrackerComponentKt.recordCallLookup(firLookupTrackerComponent, callInfo, smartList);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processObjectsByName(CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirBasedSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessResult.FOUND;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordCallableMemberLookup(Triple<? extends FirLookupTrackerComponent, ? extends SmartList<String>, CallInfo> triple, FirCallableSymbol<?> firCallableSymbol) {
        FirLookupTrackerComponent first = triple.getFirst();
        if (first != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(first, ((FirCallableDeclaration) firCallableSymbol.getFir()).getReturnTypeRef(), triple.getThird().getCallSite().getSource(), triple.getThird().getContainingFile().getSource());
            FqName className = firCallableSymbol.getCallableId().getClassName();
            if (className != null) {
                triple.getSecond().add(className.asString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasConsistentExtensionReceiver(FirCallableSymbol<?> firCallableSymbol, List<? extends FirExpression> list) {
        return (list.isEmpty() ^ true) == TowerLevelsKt.hasExtensionReceiver(firCallableSymbol);
    }
}
