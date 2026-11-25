package org.jetbrains.kotlin.fir.pipeline;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.Fir2IrCommonMemberStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrResult;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.backend.jvm.Fir2IrJvmSpecialAnnotationSymbolProvider;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: convertToIr.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J@\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J-\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020*HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "fir", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Ljava/util/List;)V", "getFir", "()Ljava/util/List;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "component1", "component2", "component3", "convertToIr", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrResult;", "fir2IrExtensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fir2IrConfiguration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "commonMemberStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irMangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "kotlinBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ModuleCompilerAnalyzedOutput {
    private final List<FirFile> fir;
    private final ScopeSession scopeSession;
    private final FirSession session;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ModuleCompilerAnalyzedOutput copy$default(ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput, FirSession firSession, ScopeSession scopeSession, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            firSession = moduleCompilerAnalyzedOutput.session;
        }
        if ((i & 2) != 0) {
            scopeSession = moduleCompilerAnalyzedOutput.scopeSession;
        }
        if ((i & 4) != 0) {
            list = moduleCompilerAnalyzedOutput.fir;
        }
        return moduleCompilerAnalyzedOutput.copy(firSession, scopeSession, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final List<FirFile> component3() {
        return this.fir;
    }

    public final ModuleCompilerAnalyzedOutput copy(FirSession session, ScopeSession scopeSession, List<? extends FirFile> fir) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ModuleCompilerAnalyzedOutput(session, scopeSession, fir);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleCompilerAnalyzedOutput)) {
            return false;
        }
        ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput = (ModuleCompilerAnalyzedOutput) other;
        return Intrinsics.areEqual(this.session, moduleCompilerAnalyzedOutput.session) && Intrinsics.areEqual(this.scopeSession, moduleCompilerAnalyzedOutput.scopeSession) && Intrinsics.areEqual(this.fir, moduleCompilerAnalyzedOutput.fir);
    }

    public int hashCode() {
        return (((this.session.hashCode() * 31) + this.scopeSession.hashCode()) * 31) + this.fir.hashCode();
    }

    public String toString() {
        return "ModuleCompilerAnalyzedOutput(session=" + this.session + ", scopeSession=" + this.scopeSession + ", fir=" + this.fir + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ModuleCompilerAnalyzedOutput(FirSession session, ScopeSession scopeSession, List<? extends FirFile> fir) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(fir, "fir");
        this.session = session;
        this.scopeSession = scopeSession;
        this.fir = fir;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final List<FirFile> getFir() {
        return this.fir;
    }

    public final Fir2IrResult convertToIr(Fir2IrExtensions fir2IrExtensions, Fir2IrConfiguration fir2IrConfiguration, Fir2IrCommonMemberStorage commonMemberStorage, IrBuiltInsOverFir irBuiltIns, KotlinMangler.IrMangler irMangler, Fir2IrVisibilityConverter visibilityConverter, KotlinBuiltIns kotlinBuiltIns) {
        Intrinsics.checkNotNullParameter(fir2IrExtensions, "fir2IrExtensions");
        Intrinsics.checkNotNullParameter(fir2IrConfiguration, "fir2IrConfiguration");
        Intrinsics.checkNotNullParameter(commonMemberStorage, "commonMemberStorage");
        Intrinsics.checkNotNullParameter(irMangler, "irMangler");
        Intrinsics.checkNotNullParameter(visibilityConverter, "visibilityConverter");
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
        return Fir2IrConverter.INSTANCE.createModuleFragmentWithSignaturesIfNeeded(this.session, this.scopeSession, this.fir, fir2IrExtensions, fir2IrConfiguration, irMangler, IrFactoryImpl.INSTANCE, visibilityConverter, new Fir2IrJvmSpecialAnnotationSymbolProvider(), kotlinBuiltIns, commonMemberStorage, irBuiltIns);
    }
}
