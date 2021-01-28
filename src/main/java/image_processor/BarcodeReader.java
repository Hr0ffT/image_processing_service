package image_processor;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class BarcodeReader {

    private static final Logger log = Logger.getLogger(BarcodeReader.class);

    public static String readBarcodeFromRemoteImage(String imageURL) throws IOException, NotFoundException {
        Map<DecodeHintType, Object> hintMap = new EnumMap<>(DecodeHintType.class);
        hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        URL url = new URL(imageURL);
        log.info(imageURL);

        BufferedImage image = ImageIO.read(url);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

        return new MultiFormatReader().decode(bitmap, hintMap).toString();
    }
}
