package org.jetbrains.kotlin.backend.common.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: IrGenerationExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0002\u001a\u00020\u00038VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrGenerationExtension;", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$IrLinkerExtension;", "shouldAlsoBeAppliedInKaptStubGenerationMode", "", "getShouldAlsoBeAppliedInKaptStubGenerationMode$annotations", "()V", "getShouldAlsoBeAppliedInKaptStubGenerationMode", "()Z", CompilerOptions.GENERATE, "", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "pluginContext", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "getPlatformIntrinsicExtension", "Lorg/jetbrains/kotlin/backend/common/extensions/IrIntrinsicExtension;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrGenerationExtension extends IrDeserializer.IrLinkerExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @FirIncompatiblePluginAPI
    static /* synthetic */ void getShouldAlsoBeAppliedInKaptStubGenerationMode$annotations() {
    }

    void generate(IrModuleFragment moduleFragment, IrPluginContext pluginContext);

    default IrIntrinsicExtension getPlatformIntrinsicExtension(BackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        return null;
    }

    default boolean getShouldAlsoBeAppliedInKaptStubGenerationMode() {
        return false;
    }

    /* JADX INFO: compiled from: IrGenerationExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static IrDeclaration resolveSymbol(IrGenerationExtension irGenerationExtension, IrSymbol symbol, TranslationPluginContext context) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(context, "context");
            return IrDeserializer.IrLinkerExtension.DefaultImpls.resolveSymbol(irGenerationExtension, symbol, context);
        }
    }

    /* JADX INFO: compiled from: IrGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrGenerationExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrGenerationExtension;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<IrGenerationExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.irGenerationExtension", IrGenerationExtension.class);
        }
    }
}
