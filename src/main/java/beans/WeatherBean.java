package beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

public class WeatherBean {
    private static final String API_KEY = "4e451818964d5f3e12078fe041afe366";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    // Weather properties
    private String city;
    private double temperature;
    private double feelsLike;
    private String conditions;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private String country;
    private String errorMessage;
    
    // Default constructor
    public WeatherBean() {}
    
    // Constructor with city
    public WeatherBean(String city) {
        this.city = city;
        fetchWeatherData();
    }
    
    public void fetchWeatherData() {
        if (city == null || city.trim().isEmpty()) {
            this.errorMessage = "City name cannot be empty";
            return;
        }
        
        try {
            // Build the request URL
            String urlString = String.format("%s?q=%s&appid=%s&units=metric", 
                                           BASE_URL, city, API_KEY);
            
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Read the response
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Parse JSON response
            parseWeatherData(response.toString());
            
        } catch (Exception e) {
            this.errorMessage = "Error fetching weather data: " + e.getMessage();
        }
    }
    
    private void parseWeatherData(String jsonResponse) {
        try {
            Gson gson = new Gson();
            JsonObject responseObj = gson.fromJson(jsonResponse, JsonObject.class);
            
            // Extract main data
            JsonObject main = responseObj.getAsJsonObject("main");
            JsonArray weatherArray = responseObj.getAsJsonArray("weather");
            JsonObject weather = weatherArray.get(0).getAsJsonObject();
            JsonObject wind = responseObj.getAsJsonObject("wind");
            JsonObject sys = responseObj.getAsJsonObject("sys");

            // Set properties
            this.city = responseObj.get("name").getAsString();
            this.temperature = main.get("temp").getAsDouble();
            this.feelsLike = main.get("feels_like").getAsDouble();
            this.conditions = weather.get("description").getAsString();
            this.humidity = main.get("humidity").getAsInt();
            this.pressure = main.get("pressure").getAsInt();
            this.windSpeed = wind.get("speed").getAsDouble();
            this.country = sys.get("country").getAsString();
            this.errorMessage = null;
            
        } catch (Exception e) {
            this.errorMessage = "Error parsing weather data: " + e.getMessage();
        }
    }
    
    // Getters and Setters
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public double getFeelsLike() {
        return feelsLike;
    }
    
    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }
    
    public String getConditions() {
        return conditions;
    }
    
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    
    public int getHumidity() {
        return humidity;
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    
    public int getPressure() {
        return pressure;
    }
    
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    // Utility method to check if data is available
    public boolean isDataAvailable() {
        return errorMessage == null && city != null;
    }
}