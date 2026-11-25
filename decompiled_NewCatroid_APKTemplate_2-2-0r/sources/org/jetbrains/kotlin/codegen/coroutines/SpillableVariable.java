package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SpillableVariable;", "", "value", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "normalizedType", "fieldName", "", "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;Ljava/lang/String;)V", "getFieldName", "()Ljava/lang/String;", "getNormalizedType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getType", "getValue", "()Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SpillableVariable {
    private final String fieldName;
    private final Type normalizedType;
    private final Type type;
    private final BasicValue value;

    public SpillableVariable(BasicValue value, Type type, Type normalizedType, String fieldName) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(normalizedType, "normalizedType");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this.value = value;
        this.type = type;
        this.normalizedType = normalizedType;
        this.fieldName = fieldName;
    }

    public final BasicValue getValue() {
        return this.value;
    }

    public final Type getType() {
        return this.type;
    }

    public final Type getNormalizedType() {
        return this.normalizedType;
    }

    public final String getFieldName() {
        return this.fieldName;
    }
}
