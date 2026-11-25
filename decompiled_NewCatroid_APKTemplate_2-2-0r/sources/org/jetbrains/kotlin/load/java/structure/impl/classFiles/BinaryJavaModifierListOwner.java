package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.arsc.chunk.SpecBlock;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner;
import org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner;

/* JADX INFO: compiled from: commonMixins.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b`\u0018\u00002\u00020\u00012\u00020\u0002J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0004H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0014\u0010\n\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\tR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", "access", "", "getAccess", "()I", "isAbstract", "", "()Z", "isDeprecatedInJavaDoc", "isFinal", "isStatic", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "isSet", SpecBlock.NAME_flag, "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BinaryJavaModifierListOwner extends JavaModifierListOwner, MapBasedJavaAnnotationOwner {
    int getAccess();

    default boolean isSet(int flag) {
        return CommonMixinsKt.isSet(getAccess(), flag);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    default boolean mo7260isAbstract() {
        return isSet(1024);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    default boolean mo7267isStatic() {
        return isSet(8);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    default boolean mo7264isFinal() {
        return isSet(16);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    default Visibility getVisibility() {
        if (isSet(2)) {
            return Visibilities.Private.INSTANCE;
        }
        if (isSet(4)) {
            return mo7267isStatic() ? JavaVisibilities.ProtectedStaticVisibility.INSTANCE : JavaVisibilities.ProtectedAndPackage.INSTANCE;
        }
        return isSet(1) ? Visibilities.Public.INSTANCE : JavaVisibilities.PackageVisibility.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    default boolean getIsDeprecatedInJavaDoc() {
        return isSet(131072);
    }
}
