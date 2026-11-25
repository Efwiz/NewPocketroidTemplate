package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;

/* JADX INFO: loaded from: classes5.dex */
public abstract class CompositeFIF implements FunctionIntrinsicFactory {
    private final List<Pair<Predicate<FunctionDescriptor>, FunctionIntrinsic>> patternsAndIntrinsics = Lists.newArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "context";
        } else if (i == 2) {
            objArr[0] = "pattern";
        } else if (i != 3) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = "intrinsic";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CompositeFIF";
        if (i == 2 || i == 3) {
            objArr[2] = ProductAction.ACTION_ADD;
        } else {
            objArr[2] = "getIntrinsic";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected CompositeFIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        for (Pair<Predicate<FunctionDescriptor>, FunctionIntrinsic> pair : this.patternsAndIntrinsics) {
            if (pair.first.test(functionDescriptor)) {
                return pair.second;
            }
        }
        return null;
    }

    protected void add(Predicate<FunctionDescriptor> predicate, FunctionIntrinsic functionIntrinsic) {
        if (predicate == null) {
            $$$reportNull$$$0(2);
        }
        if (functionIntrinsic == null) {
            $$$reportNull$$$0(3);
        }
        this.patternsAndIntrinsics.add(Pair.create(predicate, functionIntrinsic));
    }
}
