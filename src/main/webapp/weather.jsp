<%@ page import="beans.WeatherBean" %>
<%
    String cityParam = request.getParameter("city");
    WeatherBean weather = new WeatherBean();
    
    if (cityParam != null && !cityParam.trim().isEmpty()) {
        weather.setCity(cityParam);
        weather.fetchWeatherData();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Weather App</title>
</head>
<body>
    <h1>Weather Application</h1>
    
    <form method="post">
        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="<%= weather.getCity() != null ? weather.getCity() : "" %>">
        <input type="submit" value="Get Weather">
    </form>
    
    <% if (weather.getErrorMessage() != null) { %>
        <div style="color: red;">
            Error: <%= weather.getErrorMessage() %>
        </div>
    <% } else if (weather.isDataAvailable()) { %>
        <div>
            <h2>Weather in <%= weather.getCity() %>, <%= weather.getCountry() %></h2>
            <p><strong>Temperature:</strong> <%= weather.getTemperature() %>°C</p>
            <p><strong>Feels like:</strong> <%= weather.getFeelsLike() %>°C</p>
            <p><strong>Conditions:</strong> <%= weather.getConditions() %></p>
            <p><strong>Humidity:</strong> <%= weather.getHumidity() %>%</p>
            <p><strong>Pressure:</strong> <%= weather.getPressure() %> hPa</p>
            <p><strong>Wind Speed:</strong> <%= weather.getWindSpeed() %> m/s</p>
        </div>
    <% } %>
</body>
</html>