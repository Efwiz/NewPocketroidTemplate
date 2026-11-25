package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: nullabilityValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NotNullBasicValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotNullBasicValue extends StrictBasicValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final NotNullBasicValue NOT_NULL_REFERENCE_VALUE = new NotNullBasicValue(StrictBasicValue.REFERENCE_VALUE.getType());

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return 0;
    }

    public NotNullBasicValue(Type type) {
        super(type);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return (other instanceof NotNullBasicValue) && Intrinsics.areEqual(((NotNullBasicValue) other).getType(), getType());
    }

    /* JADX INFO: compiled from: nullabilityValues.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NotNullBasicValue$Companion;", "", "()V", "NOT_NULL_REFERENCE_VALUE", "Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NotNullBasicValue;", "getNOT_NULL_REFERENCE_VALUE", "()Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NotNullBasicValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NotNullBasicValue getNOT_NULL_REFERENCE_VALUE() {
            return NotNullBasicValue.NOT_NULL_REFERENCE_VALUE;
        }
    }
}
