package com.beust.jcommander.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public class PositiveInteger implements IParameterValidator {
    @Override // com.beust.jcommander.IParameterValidator
    public void validate(String str, String str2) throws ParameterException {
        if (Integer.parseInt(str2) < 0) {
            throw new ParameterException("Parameter " + str + " should be positive (found " + str2 + Constant.AFTER_QUTO);
        }
    }
}
