package org.jetbrains.kotlin.codegen.signature;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.KotlinToJvmSignatureMapper;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: KotlinToJvmSignatureMapperImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/signature/KotlinToJvmSignatureMapperImpl;", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/KotlinToJvmSignatureMapper;", "()V", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "mapToJvmMethodSignature", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinToJvmSignatureMapperImpl implements KotlinToJvmSignatureMapper {
    private final KotlinTypeMapper typeMapper;

    public KotlinToJvmSignatureMapperImpl() {
        BindingContext EMPTY = BindingContext.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        ClassBuilderMode LIGHT_CLASSES = ClassBuilderMode.LIGHT_CLASSES;
        Intrinsics.checkNotNullExpressionValue(LIGHT_CLASSES, "LIGHT_CLASSES");
        this.typeMapper = new KotlinTypeMapper(EMPTY, LIGHT_CLASSES, "main", KotlinTypeMapper.INSTANCE.getLANGUAGE_VERSION_SETTINGS_DEFAULT(), false, null, false, null, null, 480, null);
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.jvmSignature.KotlinToJvmSignatureMapper
    public Method mapToJvmMethodSignature(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.typeMapper.mapAsmMethod(function);
    }
}
