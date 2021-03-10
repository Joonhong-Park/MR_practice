package jh.hadoop.mapreduce.sample.input;

import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.Compressor;
import org.apache.hadoop.io.compress.Decompressor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ZipCodec java version
 *
 * @author Haneul, Kim
 */
public class ZipCodec implements CompressionCodec {

    private static class ZipCompressionOutputStream extends CompressionOutputStream {

        ZipCompressionOutputStream(OutputStream out) {
            super(new ZipOutputStream(out));
        }

        @Override
        public void write(int b) throws IOException {
            this.out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
        }

        @Override
        public void finish() throws IOException {
            ((ZipOutputStream) this.out).finish();
        }

        @Override
        public void resetState() throws IOException {
            ((ZipOutputStream) this.out).closeEntry();
        }
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out) {
        return new ZipCompressionOutputStream(out);
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out, Compressor compressor) {
        return new ZipCompressionOutputStream(out);
    }

    @Override
    public Class<? extends Compressor> getCompressorType() {
        return null;
    }

    @Override
    public Compressor createCompressor() {
        return null;
    }

    private static class ZipCompressionInputStream extends CompressionInputStream {

        ZipCompressionInputStream(InputStream in) throws IOException {
            super(new ZipInputStream(in));
            ((ZipInputStream) this.in).getNextEntry();// must call
        }

        @Override
        public int read() throws IOException {
            return this.in.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return this.in.read(b, off, len);
        }

        @Override
        public void resetState() throws IOException {
            ((ZipInputStream) this.in).closeEntry();
        }
    }

    @Override
    public CompressionInputStream createInputStream(InputStream in) throws IOException {
        return new ZipCompressionInputStream(in);
    }

    @Override
    public CompressionInputStream createInputStream(InputStream in, Decompressor decompressor) throws IOException {
        return new ZipCompressionInputStream(in);
    }

    @Override
    public Class<? extends Decompressor> getDecompressorType() {
        return null;
    }

    @Override
    public Decompressor createDecompressor() {
        return null;
    }

    @Override
    public String getDefaultExtension() {
        return ".zip";
    }
}
