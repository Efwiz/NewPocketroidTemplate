package org.jetbrains.kotlin.types.checker;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.AbstractTypeRefiner;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: KotlinTypeRefiner.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'J-\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\b0\fH'¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H'J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H'J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H'J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\n\u001a\u00020\u0004H'J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH'¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "()V", "findClassAcrossModuleDependencies", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getOrPutScopeForClass", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "classDescriptor", "compute", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isRefinementNeededForModule", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "isRefinementNeededForTypeConstructor", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "refineDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "refineSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "refineType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinTypeRefiner extends AbstractTypeRefiner {
    public abstract ClassDescriptor findClassAcrossModuleDependencies(ClassId classId);

    public abstract <S extends MemberScope> S getOrPutScopeForClass(ClassDescriptor classDescriptor, Function0<? extends S> compute);

    public abstract boolean isRefinementNeededForModule(ModuleDescriptor moduleDescriptor);

    public abstract boolean isRefinementNeededForTypeConstructor(TypeConstructor typeConstructor);

    public abstract ClassifierDescriptor refineDescriptor(DeclarationDescriptor descriptor);

    public abstract Collection<KotlinType> refineSupertypes(ClassDescriptor classDescriptor);

    @Override // org.jetbrains.kotlin.types.AbstractTypeRefiner
    public abstract KotlinType refineType(KotlinTypeMarker type);

    /* JADX INFO: compiled from: KotlinTypeRefiner.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J-\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\b0\fH\u0017¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0017J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0017J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0017J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\n\u001a\u00020\u0004H\u0017J\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0017¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner$Default;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "()V", "findClassAcrossModuleDependencies", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getOrPutScopeForClass", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "classDescriptor", "compute", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isRefinementNeededForModule", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "isRefinementNeededForTypeConstructor", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "refineDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "refineSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "refineType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends KotlinTypeRefiner {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public ClassDescriptor findClassAcrossModuleDependencies(ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            return null;
        }

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public boolean isRefinementNeededForModule(ModuleDescriptor moduleDescriptor) {
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            return false;
        }

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public boolean isRefinementNeededForTypeConstructor(TypeConstructor typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            return false;
        }

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public ClassDescriptor refineDescriptor(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return null;
        }

        private Default() {
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeRefiner
        public KotlinType refineType(KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return (KotlinType) type;
        }

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public Collection<KotlinType> refineSupertypes(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            return collectionMo7438getSupertypes;
        }

        @Override // org.jetbrains.kotlin.types.checker.KotlinTypeRefiner
        public <S extends MemberScope> S getOrPutScopeForClass(ClassDescriptor classDescriptor, Function0<? extends S> compute) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(compute, "compute");
            return compute.invoke();
        }
    }
}
