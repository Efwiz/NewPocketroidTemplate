package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: TypeCapabilities.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/SubtypingRepresentatives;", "", "subTypeRepresentative", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSubTypeRepresentative", "()Lorg/jetbrains/kotlin/types/KotlinType;", "superTypeRepresentative", "getSuperTypeRepresentative", "sameTypeConstructor", "", ModuleXmlParser.TYPE, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SubtypingRepresentatives {
    KotlinType getSubTypeRepresentative();

    KotlinType getSuperTypeRepresentative();

    boolean sameTypeConstructor(KotlinType type);
}
