package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.impl.AbstractLazyTypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotations;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributesKt;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: LazyJavaTypeParameterDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0014J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000fH\u0014J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaTypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractLazyTypeParameterDescriptor;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "javaTypeParameter", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "index", "", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;ILorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "getJavaTypeParameter", "()Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "computeNotEnhancedBounds", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "processBoundsWithoutCycles", "bounds", "reportSupertypeLoopError", "", ModuleXmlParser.TYPE, "resolveUpperBounds", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaTypeParameterDescriptor extends AbstractLazyTypeParameterDescriptor {
    private final LazyJavaResolverContext c;
    private final JavaTypeParameter javaTypeParameter;

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor
    /* JADX INFO: renamed from: reportSupertypeLoopError */
    protected void mo7470reportSupertypeLoopError(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public final JavaTypeParameter getJavaTypeParameter() {
        return this.javaTypeParameter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaTypeParameterDescriptor(LazyJavaResolverContext c, JavaTypeParameter javaTypeParameter, int i, DeclarationDescriptor containingDeclaration) {
        super(c.getStorageManager(), containingDeclaration, new LazyJavaAnnotations(c, javaTypeParameter, false, 4, null), javaTypeParameter.getName(), Variance.INVARIANT, false, i, SourceElement.NO_SOURCE, c.getComponents().getSupertypeLoopChecker());
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        this.c = c;
        this.javaTypeParameter = javaTypeParameter;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor
    protected List<KotlinType> resolveUpperBounds() {
        return computeNotEnhancedBounds();
    }

    private final List<KotlinType> computeNotEnhancedBounds() {
        Collection<JavaClassifierType> upperBounds = this.javaTypeParameter.getUpperBounds();
        if (upperBounds.isEmpty()) {
            SimpleType anyType = this.c.getModule().getBuiltIns().getAnyType();
            Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
            SimpleType nullableAnyType = this.c.getModule().getBuiltIns().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
            return CollectionsKt.listOf(KotlinTypeFactory.flexibleType(anyType, nullableAnyType));
        }
        Collection<JavaClassifierType> collection = upperBounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(this.c.getTypeResolver().transformJavaType((JavaClassifierType) it.next(), JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, this, 3, null)));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor
    protected List<KotlinType> processBoundsWithoutCycles(List<? extends KotlinType> bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        return this.c.getComponents().getSignatureEnhancement().enhanceTypeParameterBounds(this, bounds, this.c);
    }
}
