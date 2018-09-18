/*************************************************************************
 * 
 * ADOBE CONFIDENTIAL
 * __________________
 * 
 *  [2002] - [2007] Adobe Systems Incorporated 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
package flex.messaging.io.amf;

import flex.messaging.io.SerializationContext;
import flex.messaging.util.XMLUtil;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.w3c.dom.Document;

public abstract class AbstractAmfOutput extends AmfIO implements ActionMessageOutput
{
    /**
     * @exclude
     */
    protected DataOutputStream out;

    /**
     * Construct a serializer without connecting it to an output stream
     */
    public AbstractAmfOutput(SerializationContext context)
    {
        super(context);
    }

    /**
     * Sets the output stream that the serializer should use
     *
     * @param out DataOutputStream to use
     */
    public void setOutputStream(OutputStream out)
    {
        if (out instanceof DataOutputStream)
        {
            this.out = (DataOutputStream) out;
        }
        else
        {
            this.out = new DataOutputStream(out);
        }
        reset();
    }

    protected String documentToString(Object value) throws IOException
    {
        return XMLUtil.documentToString((Document)value);
    }

    //
    // java.io.ObjectOutput implementations
    //

    public void close() throws IOException
    {
        out.close();
    }

    public void flush() throws IOException
    {
        out.flush();
    }

    public void write(int b) throws IOException
    {
        out.write(b);
    }

    public void write(byte bytes[]) throws IOException
    {
        out.write(bytes);
    }

    public void write(byte bytes[], int offset, int length) throws IOException
    {
        out.write(bytes, offset, length);
    }


    //
    // java.io.DataOutput implementations
    //

    public void writeBoolean(boolean v) throws IOException
    {
        out.writeBoolean(v);
    }

    public void writeByte(int v) throws IOException
    {
        out.writeByte(v);
    }

    public void writeBytes(String s) throws IOException
    {
        out.writeBytes(s);
    }

    public void writeChar(int v) throws IOException
    {
        out.writeChar(v);
    }

    public void writeChars(String s) throws IOException
    {
        out.writeChars(s);
    }

    public void writeDouble(double v) throws IOException
    {
        out.writeDouble(v);
    }

    public void writeFloat(float v) throws IOException
    {
        out.writeFloat(v);
    }

    public void writeInt(int v) throws IOException
    {
        out.writeInt(v);
    }

    public void writeLong(long v) throws IOException
    {
        out.writeLong(v);
    }

    public void writeShort(int v) throws IOException
    {
        out.writeShort(v);
    }

    public void writeUTF(String s) throws IOException
    {
        out.writeUTF(s);
    }
}