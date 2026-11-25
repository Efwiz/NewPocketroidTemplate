package org.jetbrains.kotlin.fir.backend.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.JvmBackendClassResolver;
import org.jetbrains.kotlin.codegen.JvmBackendClassResolverKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FirJvmBackendClassResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmBackendClassResolver;", "Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "getComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "resolveToClassDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmBackendClassResolver implements JvmBackendClassResolver {
    private final Fir2IrComponents components;

    public FirJvmBackendClassResolver(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
    }

    public final Fir2IrComponents getComponents() {
        return this.components;
    }

    @Override // org.jetbrains.kotlin.codegen.JvmBackendClassResolver
    public List<ClassDescriptor> resolveToClassDescriptors(Type type) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type.getSort() == 10 && (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(this.components.getSession()).getClassLikeSymbolByClassId(JvmBackendClassResolverKt.getClassId(type))) != null) {
            if (!(classLikeSymbolByClassId instanceof FirClassSymbol)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return CollectionsKt.listOf(this.components.getClassifierStorage().getIrClassSymbol((FirClassSymbol) classLikeSymbolByClassId).getDescriptor());
        }
        return CollectionsKt.emptyList();
    }
}
