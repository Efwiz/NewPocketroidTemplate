package org.jetbrains.kotlin.backend.jvm.extensions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: ClassBuilderExtensionAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0082\b¢\u0006\u0002\u0010\u0004\u001a\u000e\u0010\u0005\u001a\u00020\u0003*\u0004\u0018\u00010\u0002H\u0002¨\u0006\u0006"}, d2 = {"unwrapOrigin", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "wrapToOrigin", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassBuilderExtensionAdapterKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final JvmDeclarationOrigin wrapToOrigin(IrDeclaration irDeclaration) {
        JvmIrDeclarationOrigin descriptorOrigin;
        return (irDeclaration == null || (descriptorOrigin = JvmIrDeclarationOriginKt.getDescriptorOrigin(irDeclaration)) == null) ? JvmDeclarationOrigin.NO_ORIGIN : descriptorOrigin;
    }
}
