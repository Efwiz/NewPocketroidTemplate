package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaField;

/* JADX INFO: compiled from: ReflectJavaField.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaField;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "member", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "hasConstantNotNullInitializer", "", "getHasConstantNotNullInitializer", "()Z", "initializerValue", "", "getInitializerValue", "()Ljava/lang/Object;", "isEnumEntry", "getMember", "()Ljava/lang/reflect/Field;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "getType", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaField extends ReflectJavaMember implements JavaField {
    private final Field member;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getHasConstantNotNullInitializer */
    public boolean mo7273getHasConstantNotNullInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public Object getInitializerValue() {
        return null;
    }

    public ReflectJavaField(Field member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaMember
    public Field getMember() {
        return this.member;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: isEnumEntry */
    public boolean mo7276isEnumEntry() {
        return getMember().isEnumConstant();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getType */
    public ReflectJavaType mo7275getType() {
        ReflectJavaType.Companion companion = ReflectJavaType.INSTANCE;
        Type genericType = getMember().getGenericType();
        Intrinsics.checkNotNullExpressionValue(genericType, "getGenericType(...)");
        return companion.create(genericType);
    }
}
