package com.cloudwebrtc.webrtc.custom;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


public class CustomCapture implements VideoSink {

    private final VideoTrack videoTrack;
    private final String tag = "CustomCapture";
    private long lastFrameTime = 0;

    public CustomCapture(VideoTrack videoTrack) {
        this.videoTrack = videoTrack;
        videoTrack.addSink(this);
    }

    @Override
    public void onFrame(VideoFrame videoFrame) {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime < 33) {
            return; // 30fps 제한
        }
        lastFrameTime = now;

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


            Log.i(tag, "onFrame: " + frameData);
            FrameEvent event = new FrameEvent(width, height, yBytes, uBytes, vBytes);
            EventBus.getDefault().post(event);

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
