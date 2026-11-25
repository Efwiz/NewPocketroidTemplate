package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.descriptors.ValidateableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.diagnostics.BindingContextSuppressCache;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.diagnostics.MutableDiagnosticsWithSuppression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl;
import org.jetbrains.kotlin.util.slicedMap.TrackingSlicedMap;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class BindingTraceContext implements BindingTrace {
    private static final boolean VALIDATION = Boolean.parseBoolean(System.getProperty("kotlin.bindingTrace.validation"));
    private final BindingContext bindingContext;
    private final boolean isValidationEnabled;
    private final MutableSlicedMap map;
    private final MutableDiagnosticsWithSuppression mutableDiagnostics;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "diagnostic";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/resolve/BindingTraceContext";
        } else if (i == 4 || i == 5) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "map";
        }
        if (i == 2) {
            objArr[1] = "getBindingContext";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/resolve/BindingTraceContext";
        } else {
            objArr[1] = "getKeys";
        }
        if (i == 1) {
            objArr[2] = "report";
        } else if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "getType";
            } else if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "recordType";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public BindingTraceContext() {
        this(false);
    }

    public BindingTraceContext(boolean z) {
        this(BindingTraceFilter.INSTANCE.getACCEPT_ALL(), z);
    }

    public BindingTraceContext(BindingTraceFilter bindingTraceFilter, boolean z) {
        this(bindingTraceFilter, z, VALIDATION);
    }

    public BindingTraceContext(BindingTraceFilter bindingTraceFilter, boolean z, boolean z2) {
        this(new SlicedMapImpl(z), bindingTraceFilter, z2);
    }

    private BindingTraceContext(MutableSlicedMap mutableSlicedMap, BindingTraceFilter bindingTraceFilter, boolean z) {
        if (mutableSlicedMap == null) {
            $$$reportNull$$$0(0);
        }
        CleanableBindingContext cleanableBindingContext = new CleanableBindingContext() { // from class: org.jetbrains.kotlin.resolve.BindingTraceContext.1
            /* JADX WARN: Code duplicated, block: B:30:0x0049  */
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 2 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[(i == 2 || i == 4 || i == 5) ? 3 : 2];
                if (i == 2) {
                    objArr[0] = "slice";
                } else if (i == 4) {
                    objArr[0] = "expression";
                } else if (i != 5) {
                    objArr[0] = "org/jetbrains/kotlin/resolve/BindingTraceContext$1";
                } else {
                    objArr[0] = "trace";
                }
                if (i == 1) {
                    objArr[1] = "getKeys";
                } else if (i == 2) {
                    objArr[1] = "org/jetbrains/kotlin/resolve/BindingTraceContext$1";
                } else if (i == 3) {
                    objArr[1] = "getSliceContents";
                } else if (i == 4 || i == 5) {
                    objArr[1] = "org/jetbrains/kotlin/resolve/BindingTraceContext$1";
                } else {
                    objArr[1] = "getDiagnostics";
                }
                if (i == 2) {
                    objArr[2] = "getSliceContents";
                } else if (i == 4) {
                    objArr[2] = "getType";
                } else if (i == 5) {
                    objArr[2] = "addOwnDataTo";
                }
                String str2 = String.format(str, objArr);
                if (i != 2 && i != 4 && i != 5) {
                    throw new IllegalStateException(str2);
                }
                throw new IllegalArgumentException(str2);
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public Diagnostics getDiagnostics() {
                Diagnostics empty = BindingTraceContext.this.mutableDiagnostics != null ? BindingTraceContext.this.mutableDiagnostics : Diagnostics.INSTANCE.getEMPTY();
                if (empty == null) {
                    $$$reportNull$$$0(0);
                }
                return empty;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
                return (V) BindingTraceContext.this.get(readOnlySlice, k);
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
                Collection<K> keys = BindingTraceContext.this.getKeys(writableSlice);
                if (keys == null) {
                    $$$reportNull$$$0(1);
                }
                return keys;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> readOnlySlice) {
                if (readOnlySlice == null) {
                    $$$reportNull$$$0(2);
                }
                ImmutableMap<K, V> sliceContents = BindingTraceContext.this.map.getSliceContents(readOnlySlice);
                if (sliceContents == null) {
                    $$$reportNull$$$0(3);
                }
                return sliceContents;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public KotlinType getType(KtExpression ktExpression) {
                if (ktExpression == null) {
                    $$$reportNull$$$0(4);
                }
                return BindingTraceContext.this.getType(ktExpression);
            }

            @Override // org.jetbrains.kotlin.resolve.BindingContext
            public void addOwnDataTo(BindingTrace bindingTrace, boolean z2) {
                if (bindingTrace == null) {
                    $$$reportNull$$$0(5);
                }
                BindingContextUtils.addOwnDataTo(bindingTrace, null, z2, BindingTraceContext.this.map, BindingTraceContext.this.mutableDiagnostics);
            }

            @Override // org.jetbrains.kotlin.resolve.CleanableBindingContext
            public void clear() {
                BindingTraceContext.this.map.clear();
            }
        };
        this.bindingContext = cleanableBindingContext;
        this.map = mutableSlicedMap;
        this.mutableDiagnostics = bindingTraceFilter.getIgnoreDiagnostics() ? null : new MutableDiagnosticsWithSuppression(new BindingContextSuppressCache(cleanableBindingContext), Diagnostics.INSTANCE.getEMPTY());
        this.isValidationEnabled = z;
    }

    public static BindingTraceContext createTraceableBindingTrace() {
        return new BindingTraceContext(new TrackingSlicedMap(true), BindingTraceFilter.INSTANCE.getACCEPT_ALL(), VALIDATION);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void report(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(1);
        }
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        if (mutableDiagnosticsWithSuppression == null) {
            return;
        }
        mutableDiagnosticsWithSuppression.report(diagnostic);
    }

    public void clearDiagnostics() {
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        if (mutableDiagnosticsWithSuppression != null) {
            mutableDiagnosticsWithSuppression.clear();
        }
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.mutableDiagnostics != null;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public BindingContext getBindingContext() {
        BindingContext bindingContext = this.bindingContext;
        if (bindingContext == null) {
            $$$reportNull$$$0(2);
        }
        return bindingContext;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> writableSlice, K k, V v) {
        if (this.isValidationEnabled && (v instanceof ValidateableDescriptor) && !ProgressManager.getInstance().isInNonCancelableSection()) {
            ((ValidateableDescriptor) v).validate();
        }
        this.map.put(writableSlice, k, v);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K> void record(WritableSlice<K, Boolean> writableSlice, K k) {
        record(writableSlice, k, true);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
        return (V) this.map.get(readOnlySlice, k);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
        Collection<K> keys = this.map.getKeys(writableSlice);
        if (keys == null) {
            $$$reportNull$$$0(3);
        }
        return keys;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public KotlinType getType(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(4);
        }
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        if (kotlinTypeInfo != null) {
            return kotlinTypeInfo.getType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public void recordType(KtExpression ktExpression, KotlinType kotlinType) {
        if (ktExpression == null) {
            $$$reportNull$$$0(5);
        }
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        record(BindingContext.EXPRESSION_TYPE_INFO, ktExpression, kotlinTypeInfo != null ? kotlinTypeInfo.replaceType(kotlinType) : TypeInfoFactoryKt.createTypeInfo(kotlinType));
    }
}
