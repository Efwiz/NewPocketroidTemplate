package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadValue;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: FastStoreLoadAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u001d\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\bJ\u001d\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0010J\u0015\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u000bH&¢\u0006\u0002\u0010\u0012J\r\u0010\u0013\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0017H&¢\u0006\u0002\u0010\u0018ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadInterpreter;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "", "iinc", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/IincInsnNode;", "value", "(Lorg/jetbrains/org/objectweb/asm/tree/IincInsnNode;Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;)Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "load", "", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;)V", "merge", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;)Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "store", "(Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;)Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "uninitialized", "()Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "valueParameter", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface StoreLoadInterpreter<V extends StoreLoadValue> {
    V iinc(IincInsnNode insn, V value);

    void load(VarInsnNode insn, V value);

    V merge(V a, V b);

    V store(VarInsnNode insn);

    V uninitialized();

    V valueParameter(Type type);
}
