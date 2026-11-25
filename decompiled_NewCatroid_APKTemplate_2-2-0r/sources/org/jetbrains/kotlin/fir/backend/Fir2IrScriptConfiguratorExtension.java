package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionPointName;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;

/* JADX INFO: compiled from: Fir2IrScriptConfiguratorExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00162\u00020\u0001:\u0002\u0016\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0013H&R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "configure", "", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "getIrScriptByFirSymbol", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "Companion", "Factory", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Fir2IrScriptConfiguratorExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("Fir2IrScriptConversion");
    private final KClass<? extends FirExtension> extensionType;

    /* JADX INFO: compiled from: Fir2IrScriptConfiguratorExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<Fir2IrScriptConfiguratorExtension> {
    }

    public abstract void configure(IrScript irScript, FirScript firScript, Function1<? super FirScriptSymbol, ? extends IrScriptSymbol> function1);

    /* JADX INFO: compiled from: Fir2IrScriptConfiguratorExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return Fir2IrScriptConfiguratorExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrScriptConfiguratorExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.extensionType = Reflection.getOrCreateKotlinClass(Fir2IrScriptConfiguratorExtension.class);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return this.extensionType;
    }
}
