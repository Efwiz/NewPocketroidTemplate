package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.kotlin.JvmDescriptorTypeWriter;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AbstractTypeMapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J)\u0010\u000e\u001a\u00020\u000f*\u00028\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&¢\u0006\u0002\u0010\u0015R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeMappingContext;", "Writer", "Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "typeContext", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "getTypeContext", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "getClassInternalName", "", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "getScriptInternalName", "writeGenericType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "asmType", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "(Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;)V", "backend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeMappingContext<Writer extends JvmDescriptorTypeWriter<Type>> {
    String getClassInternalName(TypeConstructorMarker typeConstructor);

    String getScriptInternalName(TypeConstructorMarker typeConstructor);

    TypeSystemCommonBackendContextForTypeMapping getTypeContext();

    void writeGenericType(Writer writer, KotlinTypeMarker kotlinTypeMarker, Type type, TypeMappingMode typeMappingMode);
}
