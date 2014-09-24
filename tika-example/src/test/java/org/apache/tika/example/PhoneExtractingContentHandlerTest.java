package org.apache.tika.example;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;

import java.io.InputStream;

import static org.apache.tika.TikaTest.assertContains;

/**
 * Test class for the {@link org.apache.tika.example.PhoneExtractingContentHandler}
 * class. This demonstrates how to parse a document and retrieve any phone numbers
 * found within.
 *
 * The phone numbers are added to a multivalued Metadata object under the key, "phonenumbers".
 * You can get an array of phone numbers by calling metadata.getValues("phonenumber").
 */
public class PhoneExtractingContentHandlerTest {
    @Test
    public void testExtractPhoneNumbers() throws Exception {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        // The PhoneExtractingContentHandler will examine any characters for phone numbers, before passing them
        // to the underlying Handler.
        PhoneExtractingContentHandler handler = new PhoneExtractingContentHandler(new BodyContentHandler(), metadata);
        InputStream stream = PhoneExtractingContentHandlerTest.class.getResourceAsStream("testPhoneNumberExtractor.odt");
        try {
            parser.parse(stream, handler, metadata, new ParseContext());
        }
        finally {
            stream.close();
        }
        String[] phoneNumbers = metadata.getValues("phonenumbers");
        assertContains("9498888888", phoneNumbers[0]);
        assertContains("9497777777", phoneNumbers[1]);
        assertContains("9496666666", phoneNumbers[2]);
    }
}
