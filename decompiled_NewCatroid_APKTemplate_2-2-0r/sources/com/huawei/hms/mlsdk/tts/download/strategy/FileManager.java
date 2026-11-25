package com.huawei.hms.mlsdk.tts.download.strategy;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy;
import com.huawei.hms.mlsdk.t.a;
import com.huawei.hms.mlsdk.t.h0;
import java.io.File;
import java.util.Map;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes10.dex */
public class FileManager extends ModelFileManagerStrategy {
    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy
    public String getModelFilePath(MLRemoteModel mLRemoteModel, Map<String, String> map) {
        return a.a("tts-").append(mLRemoteModel.getModelName()).append(Parameters.DEFAULT_OPTION_PREFIXES).append(h0.a()).append(Parameters.DEFAULT_OPTION_PREFIXES).append(map.get(mLRemoteModel.getModelName() + "-model-version")).toString();
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy
    public String getModelFolderPath(MLRemoteModel mLRemoteModel) {
        String[] strArrSplit = mLRemoteModel.getModelName().split(Parameters.DEFAULT_OPTION_PREFIXES);
        return strArrSplit.length < 2 ? "tts" : a.a("tts").append(File.separator).append(strArrSplit[0]).toString();
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy
    public boolean isNeedUnCompress() {
        return true;
    }

    @Override // com.huawei.hms.mlsdk.model.download.strategy.ModelFileManagerStrategy
    public String getModelFilePath(MLRemoteModel mLRemoteModel, String str) {
        SmartLog.i("FileManager", "getModelFilePath: " + str);
        String[] strArrSplit = str.split(Parameters.DEFAULT_OPTION_PREFIXES);
        if (strArrSplit.length < 5) {
            return "tts";
        }
        String str2 = strArrSplit[5];
        return a.a("tts-").append(mLRemoteModel.getModelName()).append(Parameters.DEFAULT_OPTION_PREFIXES).append(h0.a()).append(Parameters.DEFAULT_OPTION_PREFIXES).append(str2.substring(0, str2.lastIndexOf(Constants.ZIP_EXTENSION))).toString();
    }
}
