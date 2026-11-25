package org.jetbrains.kotlin.synthetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopesKt;

/* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \t2\u00020\u00012\u00020\u0002:\u0001\tR\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SyntheticJavaPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/SyntheticPropertyDescriptor;", "getMethod", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getGetMethod", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setMethod", "getSetMethod", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticJavaPropertyDescriptor extends PropertyDescriptor, SyntheticPropertyDescriptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Override // org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor
    FunctionDescriptor getGetMethod();

    @Override // org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor
    FunctionDescriptor getSetMethod();

    /* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u0010\u001a\u00020\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SyntheticJavaPropertyDescriptor$Companion;", "", "()V", "findByGetterOrSetter", "Lorg/jetbrains/kotlin/synthetic/SyntheticJavaPropertyDescriptor;", "getterOrSetter", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "syntheticScope", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "propertyNameByGetMethodName", "Lorg/jetbrains/kotlin/name/Name;", "methodName", "propertyNamesByAccessorName", "", "name", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final SyntheticJavaPropertyDescriptor findByGetterOrSetter(FunctionDescriptor getterOrSetter, SyntheticScopes syntheticScopes) {
            Intrinsics.checkNotNullParameter(getterOrSetter, "getterOrSetter");
            Intrinsics.checkNotNullParameter(syntheticScopes, "syntheticScopes");
            Name name2 = getterOrSetter.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Object obj = null;
            if (name2.isSpecial()) {
                return null;
            }
            String identifier = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            if (!JavaSyntheticPropertiesScopeKt.canBePropertyAccessor(identifier)) {
                return null;
            }
            DeclarationDescriptor packageFragment = getterOrSetter.getContainingDeclaration();
            ClassDescriptor classDescriptor = packageFragment instanceof ClassDescriptor ? (ClassDescriptor) packageFragment : null;
            if (classDescriptor == null) {
                return null;
            }
            FunctionDescriptor original = getterOrSetter.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            List<Name> listPropertyNamesByAccessorName = propertyNamesByAccessorName(name2);
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = listPropertyNamesByAccessorName.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, SyntheticScopesKt.collectSyntheticExtensionProperties(syntheticScopes, CollectionsKt.listOf(classDescriptor.getDefaultType()), (Name) it.next(), NoLookupLocation.FROM_SYNTHETIC_SCOPE));
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                if (obj2 instanceof SyntheticJavaPropertyDescriptor) {
                    arrayList2.add(obj2);
                }
            }
            for (Object obj3 : arrayList2) {
                SyntheticJavaPropertyDescriptor syntheticJavaPropertyDescriptor = (SyntheticJavaPropertyDescriptor) obj3;
                if (Intrinsics.areEqual(original, syntheticJavaPropertyDescriptor.getGetMethod()) || Intrinsics.areEqual(original, syntheticJavaPropertyDescriptor.getSetMethod())) {
                    obj = obj3;
                    break;
                }
            }
            return (SyntheticJavaPropertyDescriptor) obj;
        }

        public final List<Name> propertyNamesByAccessorName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return PropertiesConventionUtilKt.propertyNamesByAccessorName(name2);
        }

        public final SyntheticJavaPropertyDescriptor findByGetterOrSetter(FunctionDescriptor getterOrSetter, final SyntheticScope syntheticScope) {
            Intrinsics.checkNotNullParameter(getterOrSetter, "getterOrSetter");
            Intrinsics.checkNotNullParameter(syntheticScope, "syntheticScope");
            return findByGetterOrSetter(getterOrSetter, new SyntheticScopes(syntheticScope) { // from class: org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor$Companion$findByGetterOrSetter$3
                private final Collection<SyntheticScope> scopes;

                {
                    this.scopes = CollectionsKt.listOf(syntheticScope);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScopes
                public Collection<SyntheticScope> getScopes() {
                    return this.scopes;
                }
            });
        }

        public final Name propertyNameByGetMethodName(Name methodName) {
            Intrinsics.checkNotNullParameter(methodName, "methodName");
            return PropertiesConventionUtilKt.propertyNameByGetMethodName(methodName);
        }
    }
}
