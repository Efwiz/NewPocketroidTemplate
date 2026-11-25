package com.reandroid.arsc.coder;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.Value;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.attribute.AttributeBag;
import com.reandroid.utils.StringsUtil;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class ValueCoder {
    public static final Coder[] CODERS = {CoderNull.INS, CoderBoolean.INS, CoderDimension.INS, CoderFraction.INS, CoderColorARGB4.INS, CoderColorRGB4.INS, CoderColorRGB8.INS, CoderColorARGB8.INS, CoderFloat.INS, CoderHex.INS, CoderInteger.INS};
    private static final Coder[] CODERS_NULL;
    private static final Map<ValueType, Coder> CODER_MAP;

    public static EncodeResult encodeAttributeValue(boolean z, Value value, ResourceEntry resourceEntry, String str) {
        AttributeBag attributeBagCreate;
        EncodeResult encodeResultEncodeReference = encodeReference(value.getPackageBlock(), str);
        if (encodeResultEncodeReference != null) {
            if (encodeResultEncodeReference.isError()) {
                return encodeResultEncodeReference;
            }
            value.setValue(encodeResultEncodeReference);
            return encodeResultEncodeReference;
        }
        if (resourceEntry != null && (attributeBagCreate = AttributeBag.create(resourceEntry.resolveReference().get())) != null && (encodeResultEncodeReference = attributeBagCreate.encode(str)) != null) {
            if (encodeResultEncodeReference.valueType == ValueType.STRING) {
                value.setValueAsString(XmlSanitizer.unEscapeSpecialCharacter(str));
                return new EncodeResult(ValueType.STRING, value.getData());
            }
            if (encodeResultEncodeReference.isError()) {
                if (z) {
                    return encodeResultEncodeReference;
                }
                encodeResultEncodeReference = null;
            }
        }
        if (encodeResultEncodeReference == null) {
            encodeResultEncodeReference = encode(str);
        }
        if (encodeResultEncodeReference != null) {
            value.setValue(encodeResultEncodeReference);
            return encodeResultEncodeReference;
        }
        value.setValueAsString(XmlSanitizer.unEscapeSpecialCharacter(str));
        return new EncodeResult(ValueType.STRING, value.getData());
    }

    public static String decodeReference(PackageBlock packageBlock, ValueType valueType, int i) {
        if (i == 0) {
            if (valueType == ValueType.REFERENCE) {
                return CoderNullReference.INS.decode(i);
            }
            return CoderNullAttribute.INS.decode(i);
        }
        ResourceEntry resource = packageBlock.getTableBlock().getResource(packageBlock, i);
        if (resource != null) {
            return resource.buildReference(packageBlock, valueType);
        }
        return decodeUnknownResourceId(valueType == ValueType.REFERENCE, i);
    }

    public static EncodeResult encodeReference(PackageBlock packageBlock, String str) {
        if (str == null || str.length() < 3) {
            return null;
        }
        EncodeResult encodeResultEncodeUnknownResourceId = encodeUnknownResourceId(str);
        if (encodeResultEncodeUnknownResourceId != null) {
            return encodeResultEncodeUnknownResourceId;
        }
        ReferenceString reference = ReferenceString.parseReference(str);
        if (reference == null) {
            return null;
        }
        EncodeResult encodeResultEncode = reference.encode(packageBlock, EncodeResult.RESOURCE_NOT_FOUND);
        return encodeResultEncode.isError() ? new EncodeResult(buildResourceNotFoundMessage(packageBlock, str)) : encodeResultEncode;
    }

    public static EncodeResult encodeReference(TableBlock tableBlock, String str) {
        if (str == null || str.length() < 3) {
            return null;
        }
        EncodeResult encodeResultEncodeUnknownResourceId = encodeUnknownResourceId(str);
        if (encodeResultEncodeUnknownResourceId != null) {
            return encodeResultEncodeUnknownResourceId;
        }
        ReferenceString reference = ReferenceString.parseReference(str);
        if (reference == null) {
            return null;
        }
        EncodeResult encodeResultEncode = reference.encode(tableBlock, EncodeResult.RESOURCE_NOT_FOUND);
        return encodeResultEncode.isError() ? new EncodeResult(buildResourceNotFoundMessage(tableBlock, str)) : encodeResultEncode;
    }

    private static String buildResourceNotFoundMessage(PackageBlock packageBlock, String str) {
        TableBlock tableBlock = packageBlock.getTableBlock();
        if (tableBlock == null) {
            return "Resource not found for: '" + str + "', package " + packageBlock + ", parent table = null";
        }
        return buildResourceNotFoundMessage(tableBlock, str);
    }

    private static String buildResourceNotFoundMessage(TableBlock tableBlock, String str) {
        return "Resource not found for: '" + str + "', frameworks " + StringsUtil.toString(tableBlock.getFrameWorks());
    }

    public static String decodeUnknownNameId(int i) {
        return CoderUnknownNameId.INS.decode(i);
    }

    public static String decodeUnknownResourceId(boolean z, int i) {
        if (z) {
            return CoderUnknownReferenceId.INS.decode(i);
        }
        return CoderUnknownAttributeId.INS.decode(i);
    }

    public static EncodeResult encodeUnknownNameId(String str) {
        return CoderUnknownNameId.INS.encode(str);
    }

    public static EncodeResult encodeUnknownResourceId(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        EncodeResult encodeResultEncodeNull = encodeNull(str);
        return encodeResultEncodeNull != null ? encodeResultEncodeNull : encodeUnknown(str);
    }

    public static EncodeResult encodeHexOrInteger(String str) {
        if (str == null) {
            return null;
        }
        EncodeResult encodeResultEncode = CoderHex.INS.encode(str);
        return encodeResultEncode == null ? CoderInteger.INS.encode(str) : encodeResultEncode;
    }

    public static EncodeResult encode(String str, AttributeDataFormat... attributeDataFormatArr) {
        if (isEmpty(attributeDataFormatArr)) {
            return encodeAny(str);
        }
        if (str == null || str.length() == 0) {
            return null;
        }
        EncodeResult encodeResultEncodeUnknown = encodeUnknown(str);
        return encodeResultEncodeUnknown != null ? encodeResultEncodeUnknown : encodeWithin(str, attributeDataFormatArr);
    }

    public static EncodeResult encode(String str, ValueType... valueTypeArr) {
        if (isEmpty(valueTypeArr)) {
            return encodeAny(str);
        }
        if (str == null || str.length() == 0) {
            return null;
        }
        EncodeResult encodeResultEncodeUnknown = encodeUnknown(str);
        return encodeResultEncodeUnknown != null ? encodeResultEncodeUnknown : encodeWithin(str, valueTypeArr);
    }

    public static EncodeResult encode(String str) {
        return encodeAny(str);
    }

    private static boolean isEmpty(Object[] objArr) {
        if (objArr != null && objArr.length != 0) {
            for (Object obj : objArr) {
                if (obj != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static EncodeResult encodeWithin(String str, AttributeDataFormat... attributeDataFormatArr) {
        if (str != null && str.length() != 0) {
            for (AttributeDataFormat attributeDataFormat : attributeDataFormatArr) {
                EncodeResult encodeResultEncodeWithin = encodeWithin(str, attributeDataFormat.valueTypes());
                if (encodeResultEncodeWithin != null) {
                    return encodeResultEncodeWithin;
                }
            }
        }
        return null;
    }

    private static EncodeResult encodeWithin(String str, ValueType... valueTypeArr) {
        EncodeResult encodeResultEncode;
        if (str != null && str.length() != 0) {
            char cCharAt = str.charAt(0);
            for (ValueType valueType : valueTypeArr) {
                Coder coder = getCoder(valueType);
                if (coder != null && coder.canStartWith(cCharAt) && (encodeResultEncode = coder.encode(str)) != null) {
                    return encodeResultEncode;
                }
            }
        }
        return null;
    }

    private static EncodeResult encodeAny(String str) {
        EncodeResult encodeResultEncode;
        if (str != null && str.length() != 0) {
            EncodeResult encodeResultEncodeUnknown = encodeUnknown(str);
            if (encodeResultEncodeUnknown != null) {
                return encodeResultEncodeUnknown;
            }
            char cCharAt = str.charAt(0);
            for (Coder coder : CODERS) {
                if (coder.canStartWith(cCharAt) && (encodeResultEncode = coder.encode(str)) != null) {
                    return encodeResultEncode;
                }
            }
        }
        return null;
    }

    private static EncodeResult encodeUnknown(String str) {
        EncodeResult encodeResultEncode;
        EncodeResult encodeResultEncode2;
        char cCharAt = str.charAt(0);
        CoderUnknownReferenceId coderUnknownReferenceId = CoderUnknownReferenceId.INS;
        if (coderUnknownReferenceId.canStartWith(cCharAt) && (encodeResultEncode2 = coderUnknownReferenceId.encode(str)) != null) {
            return encodeResultEncode2;
        }
        CoderUnknownAttributeId coderUnknownAttributeId = CoderUnknownAttributeId.INS;
        if (coderUnknownAttributeId.canStartWith(cCharAt) && (encodeResultEncode = coderUnknownAttributeId.encode(str)) != null) {
            return encodeResultEncode;
        }
        CoderUnknownStringRef coderUnknownStringRef = CoderUnknownStringRef.INS;
        if (coderUnknownStringRef.canStartWith(cCharAt)) {
            return coderUnknownStringRef.encode(str);
        }
        return null;
    }

    public static String decode(ValueType valueType, int i) {
        String strDecodeNull = decodeNull(valueType, i);
        if (strDecodeNull != null) {
            return strDecodeNull;
        }
        Coder coder = CODER_MAP.get(valueType);
        if (coder == null) {
            return null;
        }
        return coder.decode(i);
    }

    private static EncodeResult encodeNull(String str) {
        EncodeResult encodeResultEncode;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length != 5 && length != 6) {
            return null;
        }
        char cCharAt = str.charAt(0);
        for (Coder coder : CODERS_NULL) {
            if (coder.canStartWith(cCharAt) && (encodeResultEncode = coder.encode(str)) != null) {
                return encodeResultEncode;
            }
        }
        return null;
    }

    private static String decodeNull(ValueType valueType, int i) {
        if (i != 0 && i != 1) {
            return null;
        }
        if (valueType == ValueType.NULL) {
            return CoderNull.INS.decode(i);
        }
        if (i != 0) {
            return null;
        }
        if (valueType == ValueType.ATTRIBUTE) {
            return CoderNullAttribute.INS.decode(i);
        }
        if (valueType == ValueType.REFERENCE) {
            return CoderNullReference.INS.decode(i);
        }
        return null;
    }

    public static Coder getCoder(ValueType valueType) {
        return CODER_MAP.get(valueType);
    }

    static {
        HashMap map = new HashMap();
        map.put(CoderNull.INS.getValueType(), CoderNull.INS);
        map.put(CoderBoolean.INS.getValueType(), CoderBoolean.INS);
        map.put(CoderDimension.INS.getValueType(), CoderDimension.INS);
        map.put(CoderFraction.INS.getValueType(), CoderFraction.INS);
        map.put(CoderColorRGB4.INS.getValueType(), CoderColorRGB4.INS);
        map.put(CoderColorARGB4.INS.getValueType(), CoderColorARGB4.INS);
        map.put(CoderColorRGB8.INS.getValueType(), CoderColorRGB8.INS);
        map.put(CoderColorARGB8.INS.getValueType(), CoderColorARGB8.INS);
        map.put(CoderFloat.INS.getValueType(), CoderFloat.INS);
        map.put(CoderHex.INS.getValueType(), CoderHex.INS);
        map.put(CoderInteger.INS.getValueType(), CoderInteger.INS);
        CODER_MAP = map;
        CODERS_NULL = new Coder[]{CoderNullReference.INS, CoderNullAttribute.INS, CoderNull.INS};
    }
}
