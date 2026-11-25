package org.jetbrains.kotlin.fir.resolve.calls;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirReceivers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003BC\b\u0004\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H&R\u0014\u0010\u0012\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u0082\u0001\u0003\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "boundSymbol", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "labelName", "Lorg/jetbrains/kotlin/name/Name;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "mutable", "", "contextReceiverNumber", "", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;ZI)V", "isContextReceiver", "()Z", "getLabelName", "()Lorg/jetbrains/kotlin/name/Name;", "createSnapshot", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValueForCallable;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValueForClass;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValueForScript;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ContextReceiverValue<S extends FirBasedSymbol<?>> extends ImplicitReceiverValue<S> {
    private final Name labelName;

    public /* synthetic */ ContextReceiverValue(FirBasedSymbol firBasedSymbol, ConeKotlinType coneKotlinType, Name name2, FirSession firSession, ScopeSession scopeSession, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firBasedSymbol, coneKotlinType, name2, firSession, scopeSession, z, i);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue
    public abstract ContextReceiverValue<S> createSnapshot();

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue
    public boolean isContextReceiver() {
        return true;
    }

    public /* synthetic */ ContextReceiverValue(FirBasedSymbol firBasedSymbol, ConeKotlinType coneKotlinType, Name name2, FirSession firSession, ScopeSession scopeSession, boolean z, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(firBasedSymbol, coneKotlinType, name2, firSession, scopeSession, (i2 & 32) != 0 ? true : z, i, null);
    }

    public final Name getLabelName() {
        return this.labelName;
    }

    private ContextReceiverValue(S s, ConeKotlinType coneKotlinType, Name name2, FirSession firSession, ScopeSession scopeSession, boolean z, int i) {
        super(s, coneKotlinType, firSession, scopeSession, z, i, false, 64, null);
        this.labelName = name2;
    }
}
