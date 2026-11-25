package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;

/* JADX INFO: compiled from: ReflectJavaRecordComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaRecordComponent;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "recordComponent", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "isVararg", "", "()Z", "member", "Ljava/lang/reflect/Member;", "getMember", "()Ljava/lang/reflect/Member;", "getRecordComponent", "()Ljava/lang/Object;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaRecordComponent extends ReflectJavaMember implements JavaRecordComponent {
    private final Object recordComponent;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    public boolean isVararg() {
        return false;
    }

    public ReflectJavaRecordComponent(Object recordComponent) {
        Intrinsics.checkNotNullParameter(recordComponent, "recordComponent");
        this.recordComponent = recordComponent;
    }

    public final Object getRecordComponent() {
        return this.recordComponent;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    public JavaType getType() throws IllegalAccessException, InvocationTargetException {
        Class<?> clsLoadGetType = Java16RecordComponentsLoader.INSTANCE.loadGetType(this.recordComponent);
        if (clsLoadGetType != null) {
            return new ReflectJavaClassifierType(clsLoadGetType);
        }
        throw new NoSuchMethodError("Can't find `getType` method");
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaMember
    public Member getMember() throws IllegalAccessException, InvocationTargetException {
        Method methodLoadGetAccessor = Java16RecordComponentsLoader.INSTANCE.loadGetAccessor(this.recordComponent);
        if (methodLoadGetAccessor != null) {
            return methodLoadGetAccessor;
        }
        throw new NoSuchMethodError("Can't find `getAccessor` method");
    }
}
