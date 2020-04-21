package james.dsp.utils;

import android.media.audiofx.AudioEffect;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ParameterUtils {
    private byte[] IntToByte(int[] input) {
        int int_index, byte_index;
        int iterations = input.length;
        byte[] buffer = new byte[input.length * 4];
        int_index = byte_index = 0;
        for (; int_index != iterations; ) {
            buffer[byte_index] = (byte) (input[int_index] & 0x00FF);
            buffer[byte_index + 1] = (byte) ((input[int_index] & 0xFF00) >> 8);
            buffer[byte_index + 2] = (byte) ((input[int_index] & 0xFF0000) >> 16);
            buffer[byte_index + 3] = (byte) ((input[int_index] & 0xFF000000) >> 24);
            ++int_index;
            byte_index += 4;
        }
        return buffer;
    }

    private int byteArrayToInt(byte[] encodedValue) {
        int value = (encodedValue[3] << 24);
        value |= (encodedValue[2] & 0xFF) << 16;
        value |= (encodedValue[1] & 0xFF) << 8;
        value |= (encodedValue[0] & 0xFF);
        return value;
    }

    private byte[] concatArrays(byte[]... arrays) {
        int len = 0;
        for (byte[] a : arrays)
            len += a.length;
        byte[] b = new byte[len];
        int offs = 0;
        for (byte[] a : arrays) {
            System.arraycopy(a, 0, b, offs, a.length);
            offs += a.length;
        }
        return b;
    }

    public void setParameterIntArray(AudioEffect audioEffect, int parameter, int[] value) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = IntToByte(value);
            Method setParameter = AudioEffect.class.getMethod("setParameter", byte[].class, byte[].class);
            setParameter.invoke(audioEffect, arguments, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setParameterFloatArray(AudioEffect audioEffect, int parameter, float value[]) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = new byte[value.length * 4];
            ByteBuffer byteDataBuffer = ByteBuffer.wrap(result);
            byteDataBuffer.order(ByteOrder.nativeOrder());
            for (int i = 0; i < value.length; i++)
                byteDataBuffer.putFloat(value[i]);
            Method setParameter = AudioEffect.class.getMethod("setParameter", byte[].class, byte[].class);
            setParameter.invoke(audioEffect, arguments, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setParameterCharArray(AudioEffect audioEffect, int parameter, String value) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = value.getBytes(Charset.forName("US-ASCII"));
            if (result.length < 256) {
                int zeroPad = 256 - result.length;
                byte[] zeroArray = new byte[zeroPad];
                result = concatArrays(result, zeroArray);
                zeroArray = null;
            }
            Method setParameter = AudioEffect.class.getMethod("setParameter", byte[].class, byte[].class);
            setParameter.invoke(audioEffect, arguments, result);
            result = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setParameterInt(AudioEffect audioEffect, int parameter, int value) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = new byte[]
                    {
                            (byte) (value), (byte) (value >> 8),
                            (byte) (value >> 16), (byte) (value >> 24)
                    };
            Method setParameter = AudioEffect.class.getMethod("setParameter", byte[].class, byte[].class);
            setParameter.invoke(audioEffect, arguments, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setParameterShort(AudioEffect audioEffect, int parameter, short value) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = new byte[]
                    {
                            (byte) (value), (byte) (value >> 8)
                    };
            Method setParameter = AudioEffect.class.getMethod("setParameter", byte[].class, byte[].class);
            setParameter.invoke(audioEffect, arguments, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getParameter(AudioEffect audioEffect, int parameter) {
        try {
            byte[] arguments = new byte[]
                    {
                            (byte) (parameter), (byte) (parameter >> 8),
                            (byte) (parameter >> 16), (byte) (parameter >> 24)
                    };
            byte[] result = new byte[4];
            Method getParameter = AudioEffect.class.getMethod("getParameter", byte[].class, byte[].class);
            getParameter.invoke(audioEffect, arguments, result);
            return byteArrayToInt(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
