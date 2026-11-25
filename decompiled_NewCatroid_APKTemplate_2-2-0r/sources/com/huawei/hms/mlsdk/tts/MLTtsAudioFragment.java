package com.huawei.hms.mlsdk.tts;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsAudioFragment {
    public static final int CHANNEL_OUT_MONO = 4;
    public static final int FORMAT_PCM_16BIT = 2;
    public static final int FORMAT_PCM_8BIT = 1;
    public static final int SAMPLE_RATE_16K = 16000;
    private byte[] audioData;
    private int audioFormat;
    private int channelInfo;
    private int sampleRateInHz;

    public static class Builder {
        private byte[] audioData;
        private int audioFormat;
        private int channelInfo;
        private int sampleRateInHz;

        public MLTtsAudioFragment build() {
            return new MLTtsAudioFragment(this.audioData, this.audioFormat, this.sampleRateInHz, this.channelInfo);
        }

        public Builder setAudioData(byte[] bArr) {
            this.audioData = (byte[]) bArr.clone();
            return this;
        }

        public Builder setAudioFormat(int i) {
            this.audioFormat = i;
            return this;
        }

        public Builder setChannelInfo(int i) {
            this.channelInfo = i;
            return this;
        }

        public Builder setSampleRateInHz(int i) {
            this.sampleRateInHz = i;
            return this;
        }
    }

    public byte[] getAudioData() {
        return (byte[]) this.audioData.clone();
    }

    public int getAudioFormat() {
        return this.audioFormat;
    }

    public int getChannelInfo() {
        return this.channelInfo;
    }

    public int getSampleRateInHz() {
        return this.sampleRateInHz;
    }

    private MLTtsAudioFragment(byte[] bArr, int i, int i2, int i3) {
        this.audioData = bArr;
        this.audioFormat = i;
        this.sampleRateInHz = i2;
        this.channelInfo = i3;
    }
}
