package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.SamTypeFactory;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmSamTypeFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmSamTypeFactory;", "Lorg/jetbrains/kotlin/backend/common/SamTypeFactory;", "()V", "isSamType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSamTypeFactory extends SamTypeFactory {
    @Override // org.jetbrains.kotlin.backend.common.SamTypeFactory
    public boolean isSamType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return JavaSingleAbstractMethodUtils.isSamType(type);
    }
}
