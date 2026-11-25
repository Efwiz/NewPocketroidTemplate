package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: GenerateMultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tR\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CorrespondingPropertyCache;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "facadeClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", ResponseCacheMiddleware.CACHE, "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getOrCopyProperty", TypedValues.TransitionType.S_FROM, "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CorrespondingPropertyCache {
    private Map<IrProperty, IrProperty> cache;
    private final JvmBackendContext context;
    private final IrClass facadeClass;

    public CorrespondingPropertyCache(JvmBackendContext context, IrClass facadeClass) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(facadeClass, "facadeClass");
        this.context = context;
        this.facadeClass = facadeClass;
    }

    public final IrProperty getOrCopyProperty(IrProperty from) {
        Intrinsics.checkNotNullParameter(from, "from");
        LinkedHashMap linkedHashMap = this.cache;
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            this.cache = linkedHashMap;
        }
        IrProperty irPropertyBuildProperty = linkedHashMap.get(from);
        if (irPropertyBuildProperty == null) {
            IrFactory irFactory = this.context.getIrFactory();
            IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
            irPropertyBuilder.updateFrom(from);
            irPropertyBuilder.setName(from.getName());
            irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(irFactory, irPropertyBuilder);
            irPropertyBuildProperty.setParent(this.facadeClass);
            IrUtilsKt.copyAnnotationsFrom(irPropertyBuildProperty, from);
            linkedHashMap.put(from, irPropertyBuildProperty);
        }
        return irPropertyBuildProperty;
    }
}
