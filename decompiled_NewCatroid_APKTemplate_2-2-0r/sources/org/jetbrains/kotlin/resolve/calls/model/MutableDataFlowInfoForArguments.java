package org.jetbrains.kotlin.resolve.calls.model;

import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: loaded from: classes6.dex */
public abstract class MutableDataFlowInfoForArguments implements DataFlowInfoForArguments {
    protected final DataFlowInfo initialDataFlowInfo;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "initialDataFlowInfo";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/calls/model/MutableDataFlowInfoForArguments";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/MutableDataFlowInfoForArguments";
        } else {
            objArr[1] = "getResultInfo";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract void updateInfo(ValueArgument valueArgument, DataFlowInfo dataFlowInfo);

    public abstract void updateResultInfo(DataFlowInfo dataFlowInfo);

    public MutableDataFlowInfoForArguments(DataFlowInfo dataFlowInfo) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(0);
        }
        this.initialDataFlowInfo = dataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
    public DataFlowInfo getResultInfo() {
        DataFlowInfo dataFlowInfo = this.initialDataFlowInfo;
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(1);
        }
        return dataFlowInfo;
    }

    public static class WithoutArgumentsCheck extends MutableDataFlowInfoForArguments {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 4) {
                objArr[0] = "valueArgument";
            } else {
                objArr[0] = "dataFlowInfo";
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/MutableDataFlowInfoForArguments$WithoutArgumentsCheck";
            if (i == 1 || i == 2) {
                objArr[2] = "updateInfo";
            } else if (i == 3) {
                objArr[2] = "updateResultInfo";
            } else if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getInfo";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WithoutArgumentsCheck(DataFlowInfo dataFlowInfo) {
            super(dataFlowInfo);
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
        public void updateInfo(ValueArgument valueArgument, DataFlowInfo dataFlowInfo) {
            if (valueArgument == null) {
                $$$reportNull$$$0(1);
            }
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(2);
            }
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
        public void updateResultInfo(DataFlowInfo dataFlowInfo) {
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(3);
            }
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
        public DataFlowInfo getInfo(ValueArgument valueArgument) {
            if (valueArgument == null) {
                $$$reportNull$$$0(4);
            }
            throw new IllegalStateException();
        }
    }
}
