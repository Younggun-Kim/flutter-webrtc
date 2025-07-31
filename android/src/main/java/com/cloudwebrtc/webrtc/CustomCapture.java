package com.cloudwebrtc.webrtc;

import android.util.Log;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class CustomCapture implements VideoSink {

    private final String tag = "CustomCapture";
    private final VideoTrack videoTrack;
    private final MethodChannel methodChannel;

    public CustomCapture(VideoTrack videoTrack, MethodChannel methodChannel) {
        this.videoTrack = videoTrack;
        this.methodChannel = methodChannel;

        videoTrack.addSink(this);
    }

    @Override
    public void onFrame(VideoFrame videoFrame) {
        try {
            videoFrame.retain();

            VideoFrame.Buffer buffer = videoFrame.getBuffer();
            VideoFrame.I420Buffer i420Buffer = buffer.toI420();

            ByteBuffer y = i420Buffer.getDataY();
            ByteBuffer u = i420Buffer.getDataU();
            ByteBuffer v = i420Buffer.getDataV();

            int width = i420Buffer.getWidth();
            int height = i420Buffer.getHeight();
            int strideY = i420Buffer.getStrideY();
            int strideU = i420Buffer.getStrideU();
            int strideV = i420Buffer.getStrideV();

            // ByteArray로 변환 (Flutter로 넘길 수 있는 타입)
            byte[] yBytes = new byte[y.remaining()];
            byte[] uBytes = new byte[u.remaining()];
            byte[] vBytes = new byte[v.remaining()];

            y.get(yBytes);
            u.get(uBytes);
            v.get(vBytes);

            Map<String, Object> frameData = new HashMap<>();
            frameData.put("width", width);
            frameData.put("height", height);
            frameData.put("strideY", strideY);
            frameData.put("strideU", strideU);
            frameData.put("strideV", strideV);
            frameData.put("dataY", yBytes);
            frameData.put("dataU", uBytes);
            frameData.put("dataV", vBytes);

            methodChannel.invokeMethod("CustomCaptureEvent", frameData);

            i420Buffer.release();
            videoFrame.release();

        } catch (Exception e) {
            dispose();
            Log.e(tag, "onFrame Error: ", e);
        }
    }

    public void dispose() {
        videoTrack.removeSink(this);
    }
}
