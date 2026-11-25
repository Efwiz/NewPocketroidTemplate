package org.jetbrains.kotlin.javac.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KotlinClassifiersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020#H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\bR\u0014\u0010\u0012\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0013\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\bR\u0014\u0010\u0014\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\bR\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\bR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/MockKotlinField;", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "psiField", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "(Lcom/intellij/psi/PsiField;)V", "annotations", "", "getAnnotations", "()Ljava/lang/Void;", "containingClass", "getContainingClass", "hasConstantNotNullInitializer", "getHasConstantNotNullInitializer", "initializerValue", "", "getInitializerValue", "()Ljava/lang/Object;", "isAbstract", "isDeprecatedInJavaDoc", "isEnumEntry", "isFinal", "isFromSource", "", "()Z", "isStatic", "name", "getName", ModuleXmlParser.TYPE, "getType", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MockKotlinField implements JavaField {
    private final PsiField psiField;

    public MockKotlinField(PsiField psiField) {
        Intrinsics.checkNotNullParameter(psiField, "psiField");
        this.psiField = psiField;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public /* bridge */ /* synthetic */ JavaAnnotation mo7251findAnnotation(FqName fqName) {
        return (JavaAnnotation) findAnnotation(fqName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public /* bridge */ /* synthetic */ Collection mo7252getAnnotations() {
        return (Collection) getAnnotations();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public /* bridge */ /* synthetic */ JavaClass getContainingClass() {
        return (JavaClass) getContainingClass();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getHasConstantNotNullInitializer, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7273getHasConstantNotNullInitializer() {
        return ((Boolean) getHasConstantNotNullInitializer()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public /* bridge */ /* synthetic */ Name getName() {
        return (Name) getName();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public /* bridge */ /* synthetic */ JavaType getType() {
        return (JavaType) getType();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public /* bridge */ /* synthetic */ boolean mo7260isAbstract() {
        return ((Boolean) isAbstract()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public /* bridge */ /* synthetic */ boolean getIsDeprecatedInJavaDoc() {
        return ((Boolean) isDeprecatedInJavaDoc()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: isEnumEntry, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean getIsEnumEntry() {
        return ((Boolean) isEnumEntry()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public /* bridge */ /* synthetic */ boolean mo7264isFinal() {
        return ((Boolean) isFinal()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public /* bridge */ /* synthetic */ boolean mo7267isStatic() {
        return ((Boolean) isStatic()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public Object getInitializerValue() {
        PsiExpression initializer = this.psiField.getInitializer();
        PsiLiteralExpression psiLiteralExpression = initializer instanceof PsiLiteralExpression ? (PsiLiteralExpression) initializer : null;
        if (psiLiteralExpression != null) {
            return psiLiteralExpression.getValue();
        }
        return null;
    }

    public Void getName() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getAnnotations() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isDeprecatedInJavaDoc() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isAbstract() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isStatic() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isFinal() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getContainingClass() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isEnumEntry() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getType() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getHasConstantNotNullInitializer() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }
}
