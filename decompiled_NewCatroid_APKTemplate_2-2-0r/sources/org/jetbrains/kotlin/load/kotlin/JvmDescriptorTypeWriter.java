package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: typeSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0004J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0016J\u0015\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u0015\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019R$\u0010\u0007\u001a\u0004\u0018\u00018\u00002\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000@BX\u0084\u000e¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;", ExifInterface.GPS_DIRECTION_TRUE, "", "jvmTypeFactory", "Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;", "(Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;)V", "<set-?>", "jvmCurrentType", "getJvmCurrentType", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "jvmCurrentTypeArrayLevel", "", "clearCurrentType", "", "writeArrayEnd", "writeArrayType", "writeClass", "objectType", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "writeJvmTypeAsIs", ModuleXmlParser.TYPE, "writeTypeVariable", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Object;)V", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmDescriptorTypeWriter<T> {
    private T jvmCurrentType;
    private int jvmCurrentTypeArrayLevel;
    private final JvmTypeFactory<T> jvmTypeFactory;

    public void writeArrayEnd() {
    }

    public JvmDescriptorTypeWriter(JvmTypeFactory<T> jvmTypeFactory) {
        Intrinsics.checkNotNullParameter(jvmTypeFactory, "jvmTypeFactory");
        this.jvmTypeFactory = jvmTypeFactory;
    }

    protected final T getJvmCurrentType() {
        return this.jvmCurrentType;
    }

    protected final void clearCurrentType() {
        this.jvmCurrentType = null;
        this.jvmCurrentTypeArrayLevel = 0;
    }

    public void writeArrayType() {
        if (this.jvmCurrentType == null) {
            this.jvmCurrentTypeArrayLevel++;
        }
    }

    public void writeClass(T objectType) {
        Intrinsics.checkNotNullParameter(objectType, "objectType");
        writeJvmTypeAsIs(objectType);
    }

    protected final void writeJvmTypeAsIs(T type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (this.jvmCurrentType == null) {
            if (this.jvmCurrentTypeArrayLevel > 0) {
                type = this.jvmTypeFactory.createFromString(StringsKt.repeat("[", this.jvmCurrentTypeArrayLevel) + this.jvmTypeFactory.toString(type));
            }
            this.jvmCurrentType = type;
        }
    }

    public void writeTypeVariable(Name name2, T type) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        writeJvmTypeAsIs(type);
    }
}
