package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005j\u0002`\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005¢\u0006\u0002\u0010\nJ\"\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u0012H\u0016J\"\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0012H\u0016J\"\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00170\u0012H\u0016R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ContextReceiverGroupMemberScopeTowerLevel;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "contextReceiverGroup", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "givenExtensionReceiverOptions", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Ljava/util/List;Ljava/util/List;)V", "memberScopeLevels", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/MemberScopeTowerLevel;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "processor", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "processObjectsByName", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContextReceiverGroupMemberScopeTowerLevel extends TowerScopeLevel {
    private final List<MemberScopeTowerLevel> memberScopeLevels;

    public /* synthetic */ ContextReceiverGroupMemberScopeTowerLevel(BodyResolveComponents bodyResolveComponents, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bodyResolveComponents, list, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public ContextReceiverGroupMemberScopeTowerLevel(BodyResolveComponents bodyResolveComponents, List<? extends ContextReceiverValue<?>> contextReceiverGroup, List<? extends FirExpression> givenExtensionReceiverOptions) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(contextReceiverGroup, "contextReceiverGroup");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        List<? extends ContextReceiverValue<?>> list = contextReceiverGroup;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new MemberScopeTowerLevel(bodyResolveComponents, (ContextReceiverValue) it.next(), givenExtensionReceiverOptions, false));
        }
        this.memberScopeLevels = arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processFunctionsByName(CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirFunctionSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<T> it = this.memberScopeLevels.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        ProcessResult processResultProcessFunctionsByName = ((MemberScopeTowerLevel) it.next()).processFunctionsByName(info, processor);
        while (it.hasNext()) {
            ProcessResult processResultProcessFunctionsByName2 = ((MemberScopeTowerLevel) it.next()).processFunctionsByName(info, processor);
            if (processResultProcessFunctionsByName.compareTo(processResultProcessFunctionsByName2) > 0) {
                processResultProcessFunctionsByName = processResultProcessFunctionsByName2;
            }
        }
        return processResultProcessFunctionsByName;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processPropertiesByName(CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirVariableSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<T> it = this.memberScopeLevels.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        ProcessResult processResultProcessPropertiesByName = ((MemberScopeTowerLevel) it.next()).processPropertiesByName(info, processor);
        while (it.hasNext()) {
            ProcessResult processResultProcessPropertiesByName2 = ((MemberScopeTowerLevel) it.next()).processPropertiesByName(info, processor);
            if (processResultProcessPropertiesByName.compareTo(processResultProcessPropertiesByName2) > 0) {
                processResultProcessPropertiesByName = processResultProcessPropertiesByName2;
            }
        }
        return processResultProcessPropertiesByName;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processObjectsByName(CallInfo info, TowerScopeLevel.TowerScopeLevelProcessor<? super FirBasedSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<T> it = this.memberScopeLevels.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        ProcessResult processResultProcessObjectsByName = ((MemberScopeTowerLevel) it.next()).processObjectsByName(info, processor);
        while (it.hasNext()) {
            ProcessResult processResultProcessObjectsByName2 = ((MemberScopeTowerLevel) it.next()).processObjectsByName(info, processor);
            if (processResultProcessObjectsByName.compareTo(processResultProcessObjectsByName2) > 0) {
                processResultProcessObjectsByName = processResultProcessObjectsByName2;
            }
        }
        return processResultProcessObjectsByName;
    }
}
