package server.SpotifyMovies.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import org.apache.commons.io.IOUtils;

public class Utils {
    public String transformDateToString(String birthday) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(birthday);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = outputFormat.format(date);

        return formattedDate;
    }

    public String getImageAsBase64(String imagePath) throws IOException {
        System.out.println(imagePath);
        File imageFile = new File(imagePath);
        System.out.println(imageFile);

        if (!imageFile.exists()) {
            throw new IOException("Fișierul nu există");
        }

        byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(imageFile));

        // Codificarea șirului de octeți în Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        base64Image = "data:image/jpeg;base64," + base64Image;
        return base64Image;
    }
}
