package image_processor;

import com.google.zxing.NotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BarcodeReaderTest {

    @Test
    void readBarcodeFromRemoteImage() {
        String fileId = "1jHO7k5HX1LUde6fMb_1sjAiH3QPxl0A_";
        String downloadLink = String.format("https://drive.google.com/u/0/uc?id=%s&export=download", fileId);
        try {
            String expected = "4605674000064";
            String actual = BarcodeReader.readBarcodeFromRemoteImage(downloadLink);
            assertEquals(expected, actual, "The method should read a code from a downloaded image");
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            fail();
        }


    }
}