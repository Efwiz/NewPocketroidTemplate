package org.jetbrains.kotlin.resolve.calls.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: loaded from: classes6.dex */
public class DataFlowInfoForArgumentsImpl extends MutableDataFlowInfoForArguments {
    private Map<ValueArgument, DataFlowInfo> infoMap;
    private Map<ValueArgument, ValueArgument> nextArgument;
    private DataFlowInfo resultInfo;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "call";
                break;
            case 2:
                objArr[0] = "valueArguments";
                break;
            case 3:
            case 6:
                objArr[0] = "valueArgument";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArgumentsImpl";
                break;
            case 7:
            case 10:
                objArr[0] = "dataFlowInfo";
                break;
            default:
                objArr[0] = "initialInfo";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "getInfo";
        } else if (i == 8 || i == 9) {
            objArr[1] = "getResultInfo";
        } else {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArgumentsImpl";
        }
        switch (i) {
            case 2:
                objArr[2] = "initNextArgMap";
                break;
            case 3:
                objArr[2] = "getInfo";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
                break;
            case 6:
            case 7:
                objArr[2] = "updateInfo";
                break;
            case 10:
                objArr[2] = "updateResultInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataFlowInfoForArgumentsImpl(DataFlowInfo dataFlowInfo, Call call) {
        super(dataFlowInfo);
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(0);
        }
        if (call == null) {
            $$$reportNull$$$0(1);
        }
        this.infoMap = null;
        this.nextArgument = null;
        initNextArgMap(call.getValueArguments());
    }

    private void initNextArgMap(List<? extends ValueArgument> list) {
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        ValueArgument valueArgument = null;
        for (ValueArgument valueArgument2 : list) {
            if (valueArgument != null) {
                if (this.nextArgument == null) {
                    this.nextArgument = new HashMap();
                }
                this.nextArgument.put(valueArgument, valueArgument2);
            }
            valueArgument = valueArgument2;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
    public DataFlowInfo getInfo(ValueArgument valueArgument) {
        if (valueArgument == null) {
            $$$reportNull$$$0(3);
        }
        Map<ValueArgument, DataFlowInfo> map = this.infoMap;
        DataFlowInfo dataFlowInfo = map == null ? null : map.get(valueArgument);
        if (dataFlowInfo == null) {
            DataFlowInfo dataFlowInfo2 = this.initialDataFlowInfo;
            if (dataFlowInfo2 == null) {
                $$$reportNull$$$0(4);
            }
            return dataFlowInfo2;
        }
        DataFlowInfo dataFlowInfoAnd = this.initialDataFlowInfo.and(dataFlowInfo);
        if (dataFlowInfoAnd == null) {
            $$$reportNull$$$0(5);
        }
        return dataFlowInfoAnd;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
    public void updateInfo(ValueArgument valueArgument, DataFlowInfo dataFlowInfo) {
        if (valueArgument == null) {
            $$$reportNull$$$0(6);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(7);
        }
        Map<ValueArgument, ValueArgument> map = this.nextArgument;
        ValueArgument valueArgument2 = map == null ? null : map.get(valueArgument);
        if (valueArgument2 != null) {
            if (this.infoMap == null) {
                this.infoMap = new HashMap();
            }
            this.infoMap.put(valueArgument2, dataFlowInfo);
            return;
        }
        this.resultInfo = dataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments, org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
    public DataFlowInfo getResultInfo() {
        if (this.resultInfo == null) {
            DataFlowInfo dataFlowInfo = this.initialDataFlowInfo;
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(8);
            }
            return dataFlowInfo;
        }
        DataFlowInfo dataFlowInfoAnd = this.initialDataFlowInfo.and(this.resultInfo);
        if (dataFlowInfoAnd == null) {
            $$$reportNull$$$0(9);
        }
        return dataFlowInfoAnd;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
    public void updateResultInfo(DataFlowInfo dataFlowInfo) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(10);
        }
        if (dataFlowInfo.equals(DataFlowInfo.INSTANCE.getEMPTY())) {
            return;
        }
        if (this.resultInfo == null) {
            this.resultInfo = this.initialDataFlowInfo;
        }
        this.resultInfo = this.resultInfo.and(dataFlowInfo);
    }
}
