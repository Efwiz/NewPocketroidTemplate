package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmBackendExtension;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.config.JvmAbiStability;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FirJvmBackendExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J2\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\bH\u0016J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmBackendExtension;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "irActualizedResult", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;)V", "createSerializer", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "bindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "parentSerializer", "generateMetadataExtraFlags", "", "abiStability", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "Companion", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmBackendExtension implements JvmBackendExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Fir2IrComponents components;
    private final IrActualizedResult irActualizedResult;

    public FirJvmBackendExtension(Fir2IrComponents components, IrActualizedResult irActualizedResult) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.irActualizedResult = irActualizedResult;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmBackendExtension
    public MetadataSerializer createSerializer(JvmBackendContext context, IrClass klass, Type type, JvmSerializationBindings bindings, MetadataSerializer parentSerializer) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        return FirMetadataSerializerKt.makeFirMetadataSerializerForIrClass(this.components.getSession(), context, klass, bindings, this.components, parentSerializer, this.irActualizedResult);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmBackendExtension
    public int generateMetadataExtraFlags(JvmAbiStability abiStability) {
        return INSTANCE.generateMetadataExtraFlags(abiStability);
    }

    /* JADX INFO: compiled from: FirJvmBackendExtension.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmBackendExtension$Companion;", "", "()V", "generateMetadataExtraFlags", "", "abiStability", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int generateMetadataExtraFlags(JvmAbiStability abiStability) {
            return (abiStability == JvmAbiStability.STABLE ? 32 : 0) | 80;
        }
    }
}
