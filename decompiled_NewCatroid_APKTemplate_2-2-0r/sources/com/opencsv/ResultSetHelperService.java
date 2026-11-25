package com.opencsv;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.text.TextStringBuilder;

/* JADX INFO: loaded from: classes10.dex */
public class ResultSetHelperService implements ResultSetHelper {
    protected static final int CLOBBUFFERSIZE = 2048;
    static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    static final String DEFAULT_TIMESTAMP_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    private static final String DEFAULT_VALUE = "";
    private String dateFormat = DEFAULT_DATE_FORMAT;
    private String dateTimeFormat = DEFAULT_TIMESTAMP_FORMAT;

    public void setDateFormat(String str) {
        this.dateFormat = str;
    }

    public void setDateTimeFormat(String str) {
        this.dateTimeFormat = str;
    }

    @Override // com.opencsv.ResultSetHelper
    public String[] getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        String[] strArr = new String[metaData.getColumnCount()];
        int i = 0;
        while (i < metaData.getColumnCount()) {
            int i2 = i + 1;
            strArr[i] = metaData.getColumnLabel(i2);
            i = i2;
        }
        return strArr;
    }

    @Override // com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet) throws SQLException, IOException {
        return getColumnValues(resultSet, false, this.dateFormat, this.dateTimeFormat);
    }

    @Override // com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet, boolean z) throws SQLException, IOException {
        return getColumnValues(resultSet, z, this.dateFormat, this.dateTimeFormat);
    }

    @Override // com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet, boolean z, String str, String str2) throws SQLException, IOException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        String[] strArr = new String[metaData.getColumnCount()];
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            strArr[i - 1] = getColumnValue(resultSet, metaData.getColumnType(i), i, z, str, str2);
        }
        return strArr;
    }

    protected String handleTimestamp(Timestamp timestamp, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        if (timestamp == null) {
            return null;
        }
        return simpleDateFormat.format((Date) timestamp);
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0084  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a8  */
    private String getColumnValue(ResultSet resultSet, int i, int i2, boolean z, String str, String str2) throws SQLException, IOException {
        String strHandleNVarChar;
        if (i == -16 || i == -15 || i == -9) {
            strHandleNVarChar = handleNVarChar(resultSet, i2, z);
        } else if (i == -1 || i == 12) {
            strHandleNVarChar = handleVarChar(resultSet, i2, z);
        } else if (i == 16) {
            strHandleNVarChar = Objects.toString(Boolean.valueOf(resultSet.getBoolean(i2)));
        } else if (i == 2005) {
            strHandleNVarChar = handleClob(resultSet, i2);
        } else if (i == 2011) {
            strHandleNVarChar = handleNClob(resultSet, i2);
        } else if (i == -6) {
            strHandleNVarChar = Objects.toString(Integer.valueOf(resultSet.getInt(i2)));
        } else if (i == -5) {
            BigDecimal bigDecimal = resultSet.getBigDecimal(i2);
            strHandleNVarChar = Objects.toString(bigDecimal != null ? bigDecimal.toBigInteger() : null);
        } else {
            switch (i) {
                case 1:
                    strHandleNVarChar = handleVarChar(resultSet, i2, z);
                    break;
                case 2:
                case 3:
                case 7:
                    strHandleNVarChar = Objects.toString(resultSet.getBigDecimal(i2), "");
                    break;
                case 4:
                case 5:
                    strHandleNVarChar = Objects.toString(Integer.valueOf(resultSet.getInt(i2)));
                    break;
                case 6:
                    strHandleNVarChar = Objects.toString(Float.valueOf(resultSet.getFloat(i2)));
                    break;
                case 8:
                    strHandleNVarChar = Objects.toString(Double.valueOf(resultSet.getDouble(i2)));
                    break;
                default:
                    switch (i) {
                        case 91:
                            strHandleNVarChar = handleDate(resultSet, i2, str);
                            break;
                        case 92:
                            strHandleNVarChar = Objects.toString(resultSet.getTime(i2), "");
                            break;
                        case 93:
                            strHandleNVarChar = handleTimestamp(resultSet.getTimestamp(i2), str2);
                            break;
                        default:
                            strHandleNVarChar = Objects.toString(resultSet.getObject(i2), "");
                            break;
                    }
                    break;
            }
        }
        return (resultSet.wasNull() || strHandleNVarChar == null) ? "" : strHandleNVarChar;
    }

    private String handleVarChar(ResultSet resultSet, int i, boolean z) throws SQLException {
        String string = resultSet.getString(i);
        return (!z || string == null) ? string : string.trim();
    }

    private String handleNVarChar(ResultSet resultSet, int i, boolean z) throws SQLException {
        String nString = resultSet.getNString(i);
        return (!z || nString == null) ? nString : nString.trim();
    }

    private String handleDate(ResultSet resultSet, int i, String str) throws SQLException {
        java.sql.Date date = resultSet.getDate(i);
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(str).format((Date) date);
    }

    private String handleClob(ResultSet resultSet, int i) throws SQLException, IOException {
        Clob clob = resultSet.getClob(i);
        if (clob == null) {
            return "";
        }
        TextStringBuilder textStringBuilder = new TextStringBuilder();
        textStringBuilder.readFrom(clob.getCharacterStream());
        return textStringBuilder.toString();
    }

    private String handleNClob(ResultSet resultSet, int i) throws SQLException, IOException {
        NClob nClob = resultSet.getNClob(i);
        if (nClob == null) {
            return "";
        }
        TextStringBuilder textStringBuilder = new TextStringBuilder();
        textStringBuilder.readFrom(nClob.getCharacterStream());
        return textStringBuilder.toString();
    }
}
