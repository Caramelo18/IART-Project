import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.HourlyForecast;
import org.codehaus.jettison.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by joliveira on 20/05/2017.
 */
public class testWeatherAPI      {
    public static void main(String[] args) throws IOException, MalformedURLException, JSONException {
        boolean isMetric = true;
        String owmApiKey = "12157ac458c5e1cfcd8d5dbd1e368120";
        String weatherCity = "Porto";
        byte forecastDays = 3;

        OpenWeatherMap.Units units = (isMetric) ? OpenWeatherMap.Units.METRIC
                                                : OpenWeatherMap.Units.IMPERIAL;
        OpenWeatherMap owm = new OpenWeatherMap(units, owmApiKey);

        try {
            DailyForecast forecast = owm.dailyForecastByCityName(weatherCity, forecastDays);

            System.out.println("Weather for: " + forecast.getCityInstance().getCityName());

            int numForecasts = forecast.getForecastCount();

            for (int i = 0; i < numForecasts; i++) {
                DailyForecast.Forecast dayForecast = forecast.getForecastInstance(i);
                DailyForecast.Forecast.Temperature temperature = dayForecast.getTemperatureInstance();
                System.out.println("\t" + dayForecast.getDateTime());
                System.out.println("\tTemperature: " + temperature.getMinimumTemperature() +
                        " to " + temperature.getMaximumTemperature() + "\n");
            }

            System.out.println("\n\n---------------------------------------------------------\n\n");

            CurrentWeather currWeather = owm.currentWeatherByCityName("Porto");

            CurrentWeather.Main mainWeather = currWeather.getMainInstance();
            CurrentWeather.Wind windWeather = currWeather.getWindInstance();

            System.out.println(mainWeather.getMaxTemperature());
            System.out.println(mainWeather.getMinTemperature());
            System.out.println(mainWeather.getHumidity());
            System.out.println(windWeather.getWindSpeed());



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
