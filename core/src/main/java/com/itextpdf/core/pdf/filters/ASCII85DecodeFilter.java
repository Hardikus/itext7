package com.itextpdf.core.pdf.filters;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfTokeniser;

import java.io.ByteArrayOutputStream;

/**
 * Handles ASCII85Decode filter
 */
public class ASCII85DecodeFilter implements FilterHandler {

    @Override
    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) throws PdfException {
        b = ASCII85Decode(b);
        return b;
    }

    public static byte[] ASCII85Decode(final byte in[]) throws PdfException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int state = 0;
        int chn[] = new int[5];
        for (int k = 0; k < in.length; ++k) {
            int ch = in[k] & 0xff;
            if (ch == '~')
                break;
            if (PdfTokeniser.isWhitespace(ch))
                continue;
            if (ch == 'z' && state == 0) {
                out.write(0);
                out.write(0);
                out.write(0);
                out.write(0);
                continue;
            }
            if (ch < '!' || ch > 'u')
                throw new PdfException(PdfException.IllegalCharacterInAscii85decode);
            chn[state] = ch - '!';
            ++state;
            if (state == 5) {
                state = 0;
                int r = 0;
                for (int j = 0; j < 5; ++j)
                    r = r * 85 + chn[j];
                out.write((byte)(r >> 24));
                out.write((byte)(r >> 16));
                out.write((byte)(r >> 8));
                out.write((byte)r);
            }
        }
        // We'll ignore the next two lines for the sake of perpetuating broken PDFs
        //if (state == 1)
        //    throw new PdfException(PdfException.IllegalLengthInAscii85decode));
        if (state == 2) {
            int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + 85 * 85 * 85  + 85 * 85 + 85;
            out.write((byte)(r >> 24));
        }
        else if (state == 3) {
            int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85  + chn[2] * 85 * 85 + 85 * 85 + 85;
            out.write((byte)(r >> 24));
            out.write((byte)(r >> 16));
        }
        else if (state == 4) {
            int r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85  + chn[2] * 85 * 85  + chn[3] * 85 + 85;
            out.write((byte)(r >> 24));
            out.write((byte)(r >> 16));
            out.write((byte)(r >> 8));
        }
        return out.toByteArray();
    }
}