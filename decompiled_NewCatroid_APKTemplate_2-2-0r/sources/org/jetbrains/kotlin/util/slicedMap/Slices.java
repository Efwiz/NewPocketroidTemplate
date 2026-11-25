package org.jetbrains.kotlin.util.slicedMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactoryKt;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes6.dex */
public class Slices {
    private static final Logger LOG = Logger.getInstance((Class<?>) Slices.class);
    public static final RewritePolicy ONLY_REWRITE_TO_EQUAL = new RewritePolicy() { // from class: org.jetbrains.kotlin.util.slicedMap.Slices.1
        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K> boolean rewriteProcessingNeeded(K k) {
            return true;
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K, V> boolean processRewrite(WritableSlice<K, V> writableSlice, K k, V v, V v2) {
            if (v == null && v2 == null) {
                return true;
            }
            if (v != null && v.equals(v2)) {
                return true;
            }
            Slices.logErrorAboutRewritingNonEqualObjects(writableSlice, k, v, v2);
            return true;
        }
    };
    public static final RewritePolicy COMPILE_TIME_VALUE_REWRITE_POLICY = new RewritePolicy() { // from class: org.jetbrains.kotlin.util.slicedMap.Slices.2
        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K> boolean rewriteProcessingNeeded(K k) {
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K, V> boolean processRewrite(WritableSlice<K, V> writableSlice, K k, V v, V v2) {
            if ((v == 0 && v2 == 0) || (v != 0 && v.equals(v2))) {
                return true;
            }
            if ((v instanceof IntegerValueTypeConstant) && (v2 instanceof IntegerValueTypeConstant)) {
                IntegerValueTypeConstant integerValueTypeConstant = (IntegerValueTypeConstant) v;
                IntegerValueTypeConstant integerValueTypeConstant2 = (IntegerValueTypeConstant) v2;
                if (integerValueTypeConstant.getParameters().isPure() && integerValueTypeConstant2.getParameters().isUnsignedNumberLiteral()) {
                    long jLongValue = integerValueTypeConstant.getValue((KotlinType) TypeUtils.NO_EXPECTED_TYPE).longValue();
                    Number value = integerValueTypeConstant2.getValue((KotlinType) TypeUtils.NO_EXPECTED_TYPE);
                    if (jLongValue == value.longValue() || jLongValue == ConstantValueFactoryKt.fromUIntToLong(value.intValue()) || jLongValue == ConstantValueFactoryKt.fromUByteToLong(value.byteValue()) || jLongValue == ConstantValueFactoryKt.fromUShortToLong(value.shortValue())) {
                        return true;
                    }
                }
            }
            Slices.logErrorAboutRewritingNonEqualObjects(writableSlice, k, v, v2);
            return true;
        }
    };

    private Slices() {
    }

    public static <K, V> SliceBuilder<K, V> sliceBuilder() {
        return new SliceBuilder<>(ONLY_REWRITE_TO_EQUAL);
    }

    public static <K, V> WritableSlice<K, V> createSimpleSlice() {
        return new BasicWritableSlice(ONLY_REWRITE_TO_EQUAL);
    }

    public static <K, V> WritableSlice<K, V> createCollectiveSlice() {
        return new BasicWritableSlice(ONLY_REWRITE_TO_EQUAL, true);
    }

    public static <K> WritableSlice<K, Boolean> createSimpleSetSlice() {
        return new SetSlice(RewritePolicy.DO_NOTHING);
    }

    public static <K> WritableSlice<K, Boolean> createCollectiveSetSlice() {
        return new SetSlice(RewritePolicy.DO_NOTHING, true);
    }

    public static class SliceBuilder<K, V> {
        private String debugName;
        private List<ReadOnlySlice<K, V>> furtherLookupSlices;
        private final RewritePolicy rewritePolicy;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/util/slicedMap/Slices$SliceBuilder", "setDebugName"));
        }

        private SliceBuilder(RewritePolicy rewritePolicy) {
            this.rewritePolicy = rewritePolicy;
        }

        @SafeVarargs
        public final SliceBuilder<K, V> setFurtherLookupSlices(ReadOnlySlice<K, V>... readOnlySliceArr) {
            this.furtherLookupSlices = Arrays.asList(readOnlySliceArr);
            return this;
        }

        public SliceBuilder<K, V> setDebugName(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.debugName = str;
            return this;
        }

        public WritableSlice<K, V> build() {
            BasicWritableSlice<K, V> basicWritableSliceDoBuild = doBuild();
            String str = this.debugName;
            if (str != null) {
                basicWritableSliceDoBuild.setDebugName(str);
            }
            return basicWritableSliceDoBuild;
        }

        private BasicWritableSlice<K, V> doBuild() {
            if (this.furtherLookupSlices != null) {
                return new BasicWritableSlice<K, V>(this.rewritePolicy) { // from class: org.jetbrains.kotlin.util.slicedMap.Slices.SliceBuilder.1
                    @Override // org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
                    public V computeValue(SlicedMap slicedMap, K k, V v, boolean z) {
                        if (z) {
                            Iterator it = SliceBuilder.this.furtherLookupSlices.iterator();
                            while (it.hasNext()) {
                                V v2 = (V) slicedMap.get((ReadOnlySlice) it.next(), k);
                                if (v2 != null) {
                                    return v2;
                                }
                            }
                            return null;
                        }
                        return (V) super.computeValue(slicedMap, k, v, false);
                    }
                };
            }
            return new BasicWritableSlice<>(this.rewritePolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> void logErrorAboutRewritingNonEqualObjects(WritableSlice<K, V> writableSlice, K k, V v, V v2) {
        LOG.error("Rewrite at slice " + writableSlice + " key: " + k + " old value: " + v + '@' + System.identityHashCode(v) + " new value: " + v2 + '@' + System.identityHashCode(v2) + (k instanceof KtElement ? "\n" + PsiUtilsKt.getElementTextWithContext((KtElement) k) : ""));
    }
}
