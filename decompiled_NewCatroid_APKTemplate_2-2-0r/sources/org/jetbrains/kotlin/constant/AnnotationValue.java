package org.jetbrains.kotlin.constant;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\f\rB\u000f\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J5\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/constant/AnnotationValue;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "Lorg/jetbrains/kotlin/constant/AnnotationValue$Value;", "value", "(Lorg/jetbrains/kotlin/constant/AnnotationValue$Value;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Companion", "Value", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationValue extends ConstantValue<Value> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ AnnotationValue(Value value, DefaultConstructorMarker defaultConstructorMarker) {
        this(value);
    }

    /* JADX INFO: compiled from: ConstantValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/constant/AnnotationValue$Value;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "argumentsMapping", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Ljava/util/Map;)V", "getArgumentsMapping", "()Ljava/util/Map;", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Value {
        private final Map<Name, ConstantValue<?>> argumentsMapping;
        private final KotlinTypeMarker type;

        /* JADX WARN: Multi-variable type inference failed */
        public Value(KotlinTypeMarker type, Map<Name, ? extends ConstantValue<?>> argumentsMapping) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(argumentsMapping, "argumentsMapping");
            this.type = type;
            this.argumentsMapping = argumentsMapping;
        }

        public final Map<Name, ConstantValue<?>> getArgumentsMapping() {
            return this.argumentsMapping;
        }

        public final KotlinTypeMarker getType() {
            return this.type;
        }

        public String toString() {
            return "Value(type=" + this.type + ", argumentsMapping=" + this.argumentsMapping + ')';
        }
    }

    private AnnotationValue(Value value) {
        super(value, null);
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnnotationValue(this, data);
    }

    /* JADX INFO: compiled from: ConstantValue.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/constant/AnnotationValue$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/constant/AnnotationValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "argumentsMapping", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AnnotationValue create(KotlinTypeMarker type, Map<Name, ? extends ConstantValue<?>> argumentsMapping) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(argumentsMapping, "argumentsMapping");
            return new AnnotationValue(new Value(type, argumentsMapping), null);
        }
    }
}
