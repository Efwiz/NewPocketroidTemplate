package org.jetbrains.kotlin.fir.declarations.synthetic;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSyntheticPropertyBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010*\u001a\u00020+R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyBuilder;", "", "()V", "delegateGetter", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "getDelegateGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "setDelegateGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)V", "delegateSetter", "getDelegateSetter", "setDelegateSetter", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticProperty;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertyBuilder {
    public FirSimpleFunction delegateGetter;
    private FirSimpleFunction delegateSetter;
    public DeprecationsProvider deprecationsProvider;
    public FirModuleData moduleData;
    public Name name;
    private FirDeclarationStatus status;
    public FirSyntheticPropertySymbol symbol;

    public final FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public final void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public final Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public final void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public final FirSyntheticPropertySymbol getSymbol() {
        FirSyntheticPropertySymbol firSyntheticPropertySymbol = this.symbol;
        if (firSyntheticPropertySymbol != null) {
            return firSyntheticPropertySymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirSyntheticPropertySymbol firSyntheticPropertySymbol) {
        Intrinsics.checkNotNullParameter(firSyntheticPropertySymbol, "<set-?>");
        this.symbol = firSyntheticPropertySymbol;
    }

    public final FirSimpleFunction getDelegateGetter() {
        FirSimpleFunction firSimpleFunction = this.delegateGetter;
        if (firSimpleFunction != null) {
            return firSimpleFunction;
        }
        Intrinsics.throwUninitializedPropertyAccessException("delegateGetter");
        return null;
    }

    public final void setDelegateGetter(FirSimpleFunction firSimpleFunction) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<set-?>");
        this.delegateGetter = firSimpleFunction;
    }

    public final DeprecationsProvider getDeprecationsProvider() {
        DeprecationsProvider deprecationsProvider = this.deprecationsProvider;
        if (deprecationsProvider != null) {
            return deprecationsProvider;
        }
        Intrinsics.throwUninitializedPropertyAccessException("deprecationsProvider");
        return null;
    }

    public final void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    public final FirDeclarationStatus getStatus() {
        return this.status;
    }

    public final void setStatus(FirDeclarationStatus firDeclarationStatus) {
        this.status = firDeclarationStatus;
    }

    public final FirSimpleFunction getDelegateSetter() {
        return this.delegateSetter;
    }

    public final void setDelegateSetter(FirSimpleFunction firSimpleFunction) {
        this.delegateSetter = firSimpleFunction;
    }

    public final FirSyntheticProperty build() {
        FirModuleData moduleData = getModuleData();
        Name name2 = getName();
        boolean z = this.delegateSetter != null;
        FirSyntheticPropertySymbol symbol = getSymbol();
        FirDeclarationStatus status = this.status;
        if (status == null) {
            status = getDelegateGetter().getStatus();
        }
        FirDeclarationStatus firDeclarationStatus = status;
        FirResolvePhase resolvePhase = FirResolveStateKt.getResolvePhase(getDelegateGetter());
        FirSyntheticPropertyAccessor firSyntheticPropertyAccessor = new FirSyntheticPropertyAccessor(getDelegateGetter(), true, getSymbol());
        FirSimpleFunction firSimpleFunction = this.delegateSetter;
        return new FirSyntheticProperty(moduleData, name2, z, symbol, firDeclarationStatus, resolvePhase, firSyntheticPropertyAccessor, firSimpleFunction != null ? new FirSyntheticPropertyAccessor(firSimpleFunction, false, getSymbol()) : null, null, getDeprecationsProvider(), 256, null);
    }
}
