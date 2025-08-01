package com.cloudwebrtc.webrtc.custom;

import android.util.Log;

import androidx.annotation.NonNull;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FrameEvent {
    private final int width;
    private final int height;
    private final byte[] y;
    private final byte[] u;
    private final byte[] v;
    private final int strideY;
    private final int uvPixelStride;
    private final int uvRowStride;

    public FrameEvent(int width, int height, byte[] y, byte[] u, byte[] v, int strideY, int uvPixelStride, int uvRowStride) {
        this.width = width;
        this.height = height;
        this.y = y;
        this.u = u;
        this.v = v;
        this.strideY = strideY;
        this.uvPixelStride = uvPixelStride;
        this.uvRowStride = uvRowStride;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getY() {
        return y;
    }

    public byte[] getU() {
        return u;
    }

    public byte[] getV() {
        return v;
    }

    public int getStrideY() {
        return strideY;
    }

    public int getUvPixelStride() {
        return uvPixelStride;
    }

    public int getUvRowStride() {
        return uvRowStride;
    }

    @NonNull
    @Override
    public String toString() {
        return "FrameEvent{" +
                "width=" + width +
                ", height=" + height +
                ", y.length=" + (y != null ? y.length : "null") +
                ", u.length=" + (u != null ? u.length : "null") +
                ", v.length=" + (v != null ? v.length : "null") +
                ", strideY=" + strideY +
                ", uvPixelStride=" + uvPixelStride +
                ", uvRowStride=" + uvRowStride +
                '}';
    }
}
