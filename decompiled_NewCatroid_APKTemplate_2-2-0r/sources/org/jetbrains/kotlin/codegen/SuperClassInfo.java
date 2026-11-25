package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: SuperClassInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SuperClassInfo;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuperClassInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinType kotlinType;
    private final Type type;

    @JvmStatic
    public static final SuperClassInfo getSuperClassInfo(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        return INSTANCE.getSuperClassInfo(classDescriptor, kotlinTypeMapper);
    }

    public SuperClassInfo(Type type, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.kotlinType = kotlinType;
    }

    public final Type getType() {
        return this.type;
    }

    public final KotlinType getKotlinType() {
        return this.kotlinType;
    }

    /* JADX INFO: compiled from: SuperClassInfo.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SuperClassInfo$Companion;", "", "()V", "getSuperClassInfo", "Lorg/jetbrains/kotlin/codegen/SuperClassInfo;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final SuperClassInfo getSuperClassInfo(ClassDescriptor descriptor, KotlinTypeMapper typeMapper) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
            if (descriptor.getKind() == ClassKind.INTERFACE) {
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                return new SuperClassInfo(OBJECT_TYPE, null);
            }
            for (KotlinType kotlinType : descriptor.getTypeConstructor().mo7438getSupertypes()) {
                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                if (this$0 != null && !JvmCodegenUtil.isJvmInterface(this$0)) {
                    return new SuperClassInfo(typeMapper.mapClass(this$0), kotlinType);
                }
            }
            Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
            return new SuperClassInfo(OBJECT_TYPE2, null);
        }
    }
}
