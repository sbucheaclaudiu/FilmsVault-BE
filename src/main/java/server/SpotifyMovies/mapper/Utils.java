package server.SpotifyMovies.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public String transformDateToString(String birthday) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(birthday);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = outputFormat.format(date);

        return formattedDate;
    }
}
