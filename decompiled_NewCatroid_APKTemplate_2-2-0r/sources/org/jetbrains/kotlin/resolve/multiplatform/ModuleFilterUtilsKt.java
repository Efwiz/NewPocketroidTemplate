package org.jetbrains.kotlin.resolve.multiplatform;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: moduleFilterUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\b\u0003\u001a&\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u00042\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002\u001a\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u00042\u0006\u0010\t\u001a\u00020\u0002\u001a\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u00042\u0006\u0010\f\u001a\u00020\u0002\u001a8\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000e\"\b\b\u0000\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004\"!\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006*\"\u0010\u0013\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0014"}, d2 = {"ALL_MODULES", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ModuleFilter;", "getALL_MODULES", "()Lkotlin/jvm/functions/Function1;", "allModulesProvidingActualsFor", "commonModule", "platformModule", "allModulesProvidingExpectsFor", "onlyFromThisModule", "module", "applyFilter", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "filter", "ModuleFilter", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleFilterUtilsKt {
    private static final Function1<ModuleDescriptor, Boolean> ALL_MODULES = new Function1<ModuleDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt$ALL_MODULES$1
        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(ModuleDescriptor it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return true;
        }
    };

    public static final Function1<ModuleDescriptor, Boolean> onlyFromThisModule(final ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return new Function1<ModuleDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt.onlyFromThisModule.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it, module));
            }
        };
    }

    public static final Function1<ModuleDescriptor, Boolean> getALL_MODULES() {
        return ALL_MODULES;
    }

    public static final Function1<ModuleDescriptor, Boolean> allModulesProvidingExpectsFor(final ModuleDescriptor platformModule) {
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        return new Function1<ModuleDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt.allModulesProvidingExpectsFor.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it, platformModule) || platformModule.getAllExpectedByModules().contains(it));
            }
        };
    }

    public static final Function1<ModuleDescriptor, Boolean> allModulesProvidingActualsFor(final ModuleDescriptor commonModule, final ModuleDescriptor platformModule) {
        Intrinsics.checkNotNullParameter(commonModule, "commonModule");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        return new Function1<ModuleDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt.allModulesProvidingActualsFor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                boolean z = true;
                if (!Intrinsics.areEqual(module, commonModule) && !module.getAllExpectedByModules().contains(commonModule) && !Intrinsics.areEqual(module, platformModule)) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        };
    }

    public static final <T extends DeclarationDescriptor> List<T> applyFilter(Iterable<? extends T> iterable, Function1<? super ModuleDescriptor, Boolean> filter) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(filter, "filter");
        ArrayList arrayList = new ArrayList();
        for (T t : iterable) {
            if (filter.invoke(DescriptorUtilsKt.getModule(t)).booleanValue()) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }
}
