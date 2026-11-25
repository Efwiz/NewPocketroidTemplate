package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ExpressionCodegen$getOrCreateCallGenerator$mappings$1 extends FunctionReference implements Function2<IrType, JvmSignatureWriter, Type> {
    ExpressionCodegen$getOrCreateCallGenerator$mappings$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "mapTypeParameter(Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;)Lorg/jetbrains/org/objectweb/asm/Type;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "mapTypeParameter";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(IrTypeMappingKt.class, "backend.jvm.codegen");
    }

    @Override // kotlin.jvm.functions.Function2
    public final Type invoke(IrType p0, JvmSignatureWriter p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return IrTypeMappingKt.mapTypeParameter((IrTypeMapper) this.receiver, p0, p1);
    }
}
