package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.metadata.DescriptorMetadataSerializer;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.config.JvmAbiStability;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmBackendExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0011J2\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H&J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "", "createSerializer", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "bindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "parentSerializer", "generateMetadataExtraFlags", "", "abiStability", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "Default", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmBackendExtension {
    MetadataSerializer createSerializer(JvmBackendContext context, IrClass klass, Type type, JvmSerializationBindings bindings, MetadataSerializer parentSerializer);

    int generateMetadataExtraFlags(JvmAbiStability abiStability);

    /* JADX INFO: compiled from: JvmBackendExtension.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension$Default;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "()V", "createSerializer", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "bindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "parentSerializer", "generateMetadataExtraFlags", "", "abiStability", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements JvmBackendExtension {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.backend.jvm.JvmBackendExtension
        public MetadataSerializer createSerializer(JvmBackendContext context, IrClass klass, Type type, JvmSerializationBindings bindings, MetadataSerializer parentSerializer) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(bindings, "bindings");
            return new DescriptorMetadataSerializer(context, klass, type, bindings, parentSerializer);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.JvmBackendExtension
        public int generateMetadataExtraFlags(JvmAbiStability abiStability) {
            return (abiStability != JvmAbiStability.UNSTABLE ? 32 : 0) | 16;
        }
    }
}
