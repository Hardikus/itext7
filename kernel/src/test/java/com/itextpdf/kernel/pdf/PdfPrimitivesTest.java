package com.itextpdf.kernel.pdf;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Random;

@Category(IntegrationTest.class)
public class PdfPrimitivesTest extends ExtendedITextTest{

    static final String destinationFolder = "./target/test/com/itextpdf/kernel/pdf/PdfPrimitivesTest/";
    static final PdfName TestArray = new PdfName("TestArray");
    static final int DefaultArraySize = 64;
    static final int PageCount = 1000;

    public static class RandomString {

        private static final char[] symbols;
        private final Random random = new Random();
        private final char[] buf;

        static {
            StringBuilder tmp = new StringBuilder();
            for (char ch = 'A'; ch <= 'Z'; ++ch)
                tmp.append(ch);
            for (char ch = 'a'; ch <= 'z'; ++ch)
                tmp.append(ch);
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        }

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }
    }

    @Before
    public void setup() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void primitivesFloatNumberTest() throws IOException {
        String filename = "primitivesFloatNumberTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfArray array = generatePdfArrayWithFloatNumbers(null, false);
            page.getPdfObject().put(TestArray, array);
            array.flush();
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesIntNumberTest() throws IOException {
        String filename = "primitivesIntNumberTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfArray array = generatePdfArrayWithIntNumbers(null, false);
            page.getPdfObject().put(TestArray, array);
            array.flush();
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesNameTest() throws IOException {
        String filename = "primitivesNameTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfArray array = generatePdfArrayWithNames(null, false);
            page.getPdfObject().put(TestArray, array);
            array.flush();
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesStringTest() throws IOException {
        String filename = "primitivesStringTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfArray array = generatePdfArrayWithStrings(null, false);
            page.getPdfObject().put(TestArray, array);
            array.flush();
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesBooleanTest() throws IOException {
        String filename = "primitivesBooleanTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithBooleans(null, false));
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesFloatNumberIndirectTest() throws IOException {
        String filename = "primitivesFloatNumberIndirectTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithFloatNumbers(pdfDoc, true));
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesIntNumberIndirectTest() throws IOException {
        String filename = "primitivesIntNumberIndirectTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithIntNumbers(pdfDoc, true));
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesStringIndirectTest() throws IOException {
        String filename = "primitivesStringIndirectTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithStrings(pdfDoc, true));
            page.flush();
        }
        pdfDoc.close();
    }



    @Test
    public void primitivesNameIndirectTest() throws IOException {
        String filename = "primitivesNameIndirectTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithNames(pdfDoc, true));
            page.flush();
        }
        pdfDoc.close();
    }

    @Test
    public void primitivesBooleanIndirectTest() throws IOException {
        String filename = "primitivesBooleanIndirectTest.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + filename));
        for (int i = 0; i < PageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            page.getPdfObject().put(TestArray, generatePdfArrayWithBooleans(pdfDoc, true));
            page.flush();
        }
        pdfDoc.close();
    }


    @Test
    public void pdfNamesTest() {
        RandomString rnd = new RandomString(16);
        for (int i = 0; i < 10000000; i++) {
            new PdfName(rnd.nextString());
        }
    }


    private PdfArray generatePdfArrayWithFloatNumbers(PdfDocument doc, boolean indirects) {
        PdfArray array = new PdfArray().makeIndirect(doc);
        Random rnd = new Random();
        for (int i = 0; i < DefaultArraySize; i++) {
            PdfNumber num = new PdfNumber(rnd.nextFloat());
            if (indirects)
                num.makeIndirect(doc);
            array.add(num);
        }
        return array;
    }

    private PdfArray generatePdfArrayWithIntNumbers(PdfDocument doc, boolean indirects) {
        PdfArray array = new PdfArray().makeIndirect(doc);
        Random rnd = new Random();
        for (int i = 0; i < DefaultArraySize; i++) {
            array.add(new PdfNumber(rnd.nextInt()).makeIndirect(indirects ? doc : null));
        }
        return array;
    }

    private PdfArray generatePdfArrayWithStrings(PdfDocument doc, boolean indirects) {
        PdfArray array = new PdfArray().makeIndirect(doc);
        RandomString rnd = new RandomString(16);
        for (int i = 0; i < DefaultArraySize; i++) {
            array.add(new PdfString(rnd.nextString()).makeIndirect(indirects ? doc : null));
        }
        return array;
    }

    private PdfArray generatePdfArrayWithNames(PdfDocument doc, boolean indirects) {
        PdfArray array = new PdfArray().makeIndirect(doc);
        RandomString rnd = new RandomString(6);
        for (int i = 0; i < DefaultArraySize; i++) {
            array.add(new PdfName(rnd.nextString()).makeIndirect(indirects ? doc : null));
        }
        return array;
    }

    private PdfArray generatePdfArrayWithBooleans(PdfDocument doc, boolean indirects) {
        PdfArray array = new PdfArray().makeIndirect(doc);
        Random rnd = new Random();
        for (int i = 0; i < DefaultArraySize; i++) {
            array.add(new PdfBoolean(rnd.nextBoolean()).makeIndirect(indirects ? doc : null));
        }
        return array;
    }
}
