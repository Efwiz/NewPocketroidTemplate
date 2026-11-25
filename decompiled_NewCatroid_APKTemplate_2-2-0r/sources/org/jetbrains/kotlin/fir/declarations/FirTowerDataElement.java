package org.jetbrains.kotlin.fir.declarations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: ImplicitReceiverUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BM\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005\u0012\u001a\b\u0002\u0010\u0006\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0018\u00010\u0007j\u0004\u0018\u0001`\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0006\u0010\u0018\u001a\u00020\u0000J-\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u001f\b\u0002\u0010\u001a\u001a\u0019\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001c0\u001b¢\u0006\u0002\b\u001eJ/\u0010\u001f\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00052\u001d\u0010\u001a\u001a\u0019\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001c0\u001b¢\u0006\u0002\b\u001eH\u0002R#\u0010\u0006\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0018\u00010\u0007j\u0004\u0018\u0001`\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "implicitReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "contextReceiverGroup", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "isLocal", "", "staticScopeOwnerSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "(Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;Ljava/util/List;ZLorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;)V", "getContextReceiverGroup", "()Ljava/util/List;", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "()Z", "getScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getStaticScopeOwnerSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "createSnapshot", "getAvailableScopes", "processTypeScope", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lkotlin/ExtensionFunctionType;", "getImplicitScope", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTowerDataElement {
    private final List<ContextReceiverValue<?>> contextReceiverGroup;
    private final ImplicitReceiverValue<?> implicitReceiver;
    private final boolean isLocal;
    private final FirScope scope;
    private final FirRegularClassSymbol staticScopeOwnerSymbol;

    /* JADX WARN: Multi-variable type inference failed */
    public FirTowerDataElement(FirScope firScope, ImplicitReceiverValue<?> implicitReceiverValue, List<? extends ContextReceiverValue<?>> list, boolean z, FirRegularClassSymbol firRegularClassSymbol) {
        this.scope = firScope;
        this.implicitReceiver = implicitReceiverValue;
        this.contextReceiverGroup = list;
        this.isLocal = z;
        this.staticScopeOwnerSymbol = firRegularClassSymbol;
    }

    public /* synthetic */ FirTowerDataElement(FirScope firScope, ImplicitReceiverValue implicitReceiverValue, List list, boolean z, FirRegularClassSymbol firRegularClassSymbol, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firScope, implicitReceiverValue, (i & 4) != 0 ? null : list, z, (i & 16) != 0 ? null : firRegularClassSymbol);
    }

    public final FirScope getScope() {
        return this.scope;
    }

    public final ImplicitReceiverValue<?> getImplicitReceiver() {
        return this.implicitReceiver;
    }

    public final List<ContextReceiverValue<?>> getContextReceiverGroup() {
        return this.contextReceiverGroup;
    }

    /* JADX INFO: renamed from: isLocal, reason: from getter */
    public final boolean getIsLocal() {
        return this.isLocal;
    }

    public final FirRegularClassSymbol getStaticScopeOwnerSymbol() {
        return this.staticScopeOwnerSymbol;
    }

    public final FirTowerDataElement createSnapshot() {
        FirScope firScope = this.scope;
        ImplicitReceiverValue<?> implicitReceiverValue = this.implicitReceiver;
        ArrayList arrayList = null;
        ImplicitReceiverValue implicitReceiverValueCreateSnapshot = implicitReceiverValue != null ? implicitReceiverValue.createSnapshot() : null;
        List<ContextReceiverValue<?>> list = this.contextReceiverGroup;
        if (list != null) {
            List<ContextReceiverValue<?>> list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList2.add(((ContextReceiverValue) it.next()).createSnapshot());
            }
            arrayList = arrayList2;
        }
        return new FirTowerDataElement(firScope, implicitReceiverValueCreateSnapshot, arrayList, this.isLocal, this.staticScopeOwnerSymbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List getAvailableScopes$default(FirTowerDataElement firTowerDataElement, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            function2 = new Function2<FirTypeScope, ConeKotlinType, FirTypeScope>() { // from class: org.jetbrains.kotlin.fir.declarations.FirTowerDataElement.getAvailableScopes.1
                @Override // kotlin.jvm.functions.Function2
                public final FirTypeScope invoke(FirTypeScope firTypeScope, ConeKotlinType it) {
                    Intrinsics.checkNotNullParameter(firTypeScope, "$this$null");
                    Intrinsics.checkNotNullParameter(it, "it");
                    return firTypeScope;
                }
            };
        }
        return firTowerDataElement.getAvailableScopes(function2);
    }

    public final List<FirScope> getAvailableScopes(Function2<? super FirTypeScope, ? super ConeKotlinType, ? extends FirTypeScope> processTypeScope) {
        Intrinsics.checkNotNullParameter(processTypeScope, "processTypeScope");
        FirScope firScope = this.scope;
        if (firScope != null) {
            return CollectionsKt.listOf(firScope);
        }
        ImplicitReceiverValue<?> implicitReceiverValue = this.implicitReceiver;
        if (implicitReceiverValue != null) {
            return CollectionsKt.listOf(getImplicitScope(implicitReceiverValue, processTypeScope));
        }
        List<ContextReceiverValue<?>> list = this.contextReceiverGroup;
        if (list != null) {
            List<ContextReceiverValue<?>> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(getImplicitScope((ContextReceiverValue) it.next(), processTypeScope));
            }
            return arrayList;
        }
        throw new IllegalStateException("Tower data element is expected to have either scope or implicit receivers.".toString());
    }

    private final FirScope getImplicitScope(ImplicitReceiverValue<?> implicitReceiverValue, Function2<? super FirTypeScope, ? super ConeKotlinType, ? extends FirTypeScope> function2) {
        FirTypeScope firTypeScopeInvoke;
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(implicitReceiverValue.getType(), implicitReceiverValue.getUseSiteSession());
        if (coneKotlinTypeFullyExpandedType instanceof ConeErrorType ? true : coneKotlinTypeFullyExpandedType instanceof ConeStubType) {
            return FirTypeScope.Empty.INSTANCE;
        }
        FirTypeScope implicitScope = implicitReceiverValue.getImplicitScope();
        if (implicitScope == null || (firTypeScopeInvoke = function2.invoke(implicitScope, coneKotlinTypeFullyExpandedType)) == null) {
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Scope for type " + Reflection.getOrCreateKotlinClass(coneKotlinTypeFullyExpandedType.getClass()).getSimpleName() + " is null", null);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneKotlinTypeFullyExpandedType);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
        return firTypeScopeInvoke;
    }
}
