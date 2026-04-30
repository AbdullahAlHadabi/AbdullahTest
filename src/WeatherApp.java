import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class WeatherApp {
    private static ArrayList<City> cities = new ArrayList<>();
    private static ArrayList<WeatherData> weatherHistory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("=== Weather Application ===");
        System.out.println("Built with OOP Principles\n");

        // Initialize sample cities
        initializeCities();

        // Load weather history
        loadWeatherHistory();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewCurrentWeather();
                    break;
                case 2:
                    viewWeatherForecast();
                    break;
                case 3:
                    viewWeatherHistory();
                    break;
                case 4:
                    addCity();
                    break;
                case 5:
                    generateWeatherReport();
                    break;
                case 6:
                    compareCities();
                    break;
                case 7:
                    saveWeatherHistory();
                    System.out.println("Thank you for using Weather App!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Weather App Menu ===");
        System.out.println("1. View Current Weather");
        System.out.println("2. View Weather Forecast");
        System.out.println("3. View Weather History");
        System.out.println("4. Add New City");
        System.out.println("5. Generate Weather Report");
        System.out.println("6. Compare Cities");
        System.out.println("7. Exit");
        System.out.println("========================");
    }

    private static void initializeCities() {
        cities.add(new City("New York", "USA", 40.7128, -74.0060));
        cities.add(new City("London", "UK", 51.5074, -0.1278));
        cities.add(new City("Tokyo", "Japan", 35.6762, 139.6503));
        cities.add(new City("Sydney", "Australia", -33.8688, 151.2093));
        cities.add(new City("Paris", "France", 48.8566, 2.3522));
    }

    private static void viewCurrentWeather() {
        System.out.println("\n=== Current Weather ===");
        displayCities();

        int cityIndex = getIntInput("Select city (1-" + cities.size() + "): ") - 1;
        if (cityIndex < 0 || cityIndex >= cities.size()) {
            System.out.println("Invalid city selection.");
            return;
        }

        City city = cities.get(cityIndex);
        WeatherData currentWeather = generateWeatherData(city);

        System.out.println("\nCurrent Weather for " + city.getName() + ", " + city.getCountry());
        System.out.println("Temperature: " + currentWeather.getTemperature() + "°C");
        System.out.println("Humidity: " + currentWeather.getHumidity() + "%");
        System.out.println("Wind Speed: " + currentWeather.getWindSpeed() + " km/h");
        System.out.println("Condition: " + currentWeather.getCondition());
        System.out.println("Feels Like: " + currentWeather.getFeelsLike() + "°C");
        System.out.println("Last Updated: " + currentWeather.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // Add to history
        weatherHistory.add(currentWeather);
    }

    private static void viewWeatherForecast() {
        System.out.println("\n=== Weather Forecast ===");
        displayCities();

        int cityIndex = getIntInput("Select city (1-" + cities.size() + "): ") - 1;
        if (cityIndex < 0 || cityIndex >= cities.size()) {
            System.out.println("Invalid city selection.");
            return;
        }

        City city = cities.get(cityIndex);
        System.out.println("\n5-Day Forecast for " + city.getName() + ", " + city.getCountry());
        System.out.println("========================================");

        for (int i = 1; i <= 5; i++) {
            WeatherData forecast = generateWeatherData(city);
            LocalDate forecastDate = LocalDate.now().plusDays(i);

            System.out.println(forecastDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd")) + ":");
            System.out.println("  " + forecast.getTemperature() + "°C, " + forecast.getCondition());
            System.out.println("  Humidity: " + forecast.getHumidity() + "%, Wind: " + forecast.getWindSpeed() + " km/h");
            System.out.println();
        }
    }

    private static void viewWeatherHistory() {
        System.out.println("\n=== Weather History ===");
        if (weatherHistory.isEmpty()) {
            System.out.println("No weather history available.");
            return;
        }

        System.out.println("Recent Weather Data:");
        System.out.println("====================");

        // Show last 10 entries
        int start = Math.max(0, weatherHistory.size() - 10);
        for (int i = start; i < weatherHistory.size(); i++) {
            WeatherData data = weatherHistory.get(i);
            City city = findCityByName(data.getCityName());
            if (city != null) {
                System.out.println(data.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                                 " | " + city.getName() + " | " + data.getTemperature() + "°C | " +
                                 data.getCondition());
            }
        }
    }

    private static void addCity() {
        System.out.println("\n=== Add New City ===");
        System.out.print("Enter city name: ");
        String name = scanner.nextLine();

        System.out.print("Enter country: ");
        String country = scanner.nextLine();

        double latitude = getDoubleInput("Enter latitude (-90 to 90): ");
        double longitude = getDoubleInput("Enter longitude (-180 to 180): ");

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            System.out.println("Invalid coordinates.");
            return;
        }

        City newCity = new City(name, country, latitude, longitude);
        cities.add(newCity);
        System.out.println("City added successfully!");
    }

    private static void generateWeatherReport() {
        System.out.println("\n=== Weather Report ===");
        if (cities.isEmpty()) {
            System.out.println("No cities available.");
            return;
        }

        System.out.println("Weather Summary for All Cities:");
        System.out.println("================================");

        double totalTemp = 0;
        int totalHumidity = 0;
        int cityCount = 0;

        for (City city : cities) {
            WeatherData weather = generateWeatherData(city);
            System.out.println(city.getName() + ", " + city.getCountry() + ":");
            System.out.println("  Temperature: " + weather.getTemperature() + "°C");
            System.out.println("  Condition: " + weather.getCondition());
            System.out.println("  Humidity: " + weather.getHumidity() + "%");
            System.out.println();

            totalTemp += weather.getTemperature();
            totalHumidity += weather.getHumidity();
            cityCount++;
        }

        if (cityCount > 0) {
            System.out.println("Overall Statistics:");
            System.out.println("Average Temperature: " + String.format("%.1f", totalTemp / cityCount) + "°C");
            System.out.println("Average Humidity: " + (totalHumidity / cityCount) + "%");
        }
    }

    private static void compareCities() {
        System.out.println("\n=== Compare Cities ===");
        if (cities.size() < 2) {
            System.out.println("Need at least 2 cities to compare.");
            return;
        }

        displayCities();
        int city1Index = getIntInput("Select first city (1-" + cities.size() + "): ") - 1;
        int city2Index = getIntInput("Select second city (1-" + cities.size() + "): ") - 1;

        if (city1Index < 0 || city1Index >= cities.size() ||
            city2Index < 0 || city2Index >= cities.size() ||
            city1Index == city2Index) {
            System.out.println("Invalid city selection.");
            return;
        }

        City city1 = cities.get(city1Index);
        City city2 = cities.get(city2Index);

        WeatherData weather1 = generateWeatherData(city1);
        WeatherData weather2 = generateWeatherData(city2);

        System.out.println("\nWeather Comparison:");
        System.out.println("==================");
        System.out.println(city1.getName() + " vs " + city2.getName());
        System.out.println("Temperature: " + weather1.getTemperature() + "°C vs " +
                          weather2.getTemperature() + "°C");
        System.out.println("Humidity: " + weather1.getHumidity() + "% vs " +
                          weather2.getHumidity() + "%");
        System.out.println("Wind Speed: " + weather1.getWindSpeed() + " km/h vs " +
                          weather2.getWindSpeed() + " km/h");
        System.out.println("Conditions: " + weather1.getCondition() + " vs " +
                          weather2.getCondition());

        // Determine which city has better weather
        if (Math.abs(weather1.getTemperature() - 22) < Math.abs(weather2.getTemperature() - 22)) {
            System.out.println("\n" + city1.getName() + " has more comfortable temperature.");
        } else {
            System.out.println("\n" + city2.getName() + " has more comfortable temperature.");
        }
    }

    private static void displayCities() {
        System.out.println("Available Cities:");
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            System.out.println((i + 1) + ". " + city.getName() + ", " + city.getCountry());
        }
    }

    private static WeatherData generateWeatherData(City city) {
        // Generate realistic weather data based on city and season
        int baseTemp = 20; // Base temperature in Celsius

        // Adjust for latitude (colder near poles)
        if (Math.abs(city.getLatitude()) > 40) {
            baseTemp -= 10;
        }

        // Add some randomness
        int temperature = baseTemp + random.nextInt(21) - 10; // -10 to +10 variation

        int humidity = 40 + random.nextInt(41); // 40-80%
        int windSpeed = 5 + random.nextInt(26); // 5-30 km/h

        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Partly Cloudy", "Thunderstorm", "Snowy", "Foggy"};
        String condition = conditions[random.nextInt(conditions.length)];

        // Calculate "feels like" temperature
        int feelsLike = temperature;
        if (windSpeed > 20) {
            feelsLike = temperature - 2; // Wind chill
        } else if (humidity > 70) {
            feelsLike = temperature + 1; // Humidity makes it feel warmer
        }

        return new WeatherData(city.getName(), temperature, humidity, windSpeed, condition, feelsLike);
    }

    private static City findCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }

    private static void saveWeatherHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("weather_history.txt"))) {
            for (WeatherData data : weatherHistory) {
                writer.println(data.getCityName() + "," + data.getTemperature() + "," +
                             data.getHumidity() + "," + data.getWindSpeed() + "," +
                             data.getCondition() + "," + data.getFeelsLike() + "," +
                             data.getTimestamp());
            }
            System.out.println("Weather history saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving weather history: " + e.getMessage());
        }
    }

    private static void loadWeatherHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("weather_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String cityName = parts[0];
                    int temperature = Integer.parseInt(parts[1]);
                    int humidity = Integer.parseInt(parts[2]);
                    int windSpeed = Integer.parseInt(parts[3]);
                    String condition = parts[4];
                    int feelsLike = Integer.parseInt(parts[5]);
                    LocalDateTime timestamp = LocalDateTime.parse(parts[6]);

                    WeatherData data = new WeatherData(cityName, temperature, humidity,
                                                     windSpeed, condition, feelsLike);
                    data.setTimestamp(timestamp);
                    weatherHistory.add(data);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, start with empty history
        } catch (IOException e) {
            System.out.println("Error loading weather history: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

// City class demonstrating encapsulation
class City {
    private String name;
    private String country;
    private double latitude;
    private double longitude;

    public City(String name, String country, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getName() { return name; }
    public String getCountry() { return country; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

// WeatherData class for storing weather information
class WeatherData {
    private String cityName;
    private int temperature;
    private int humidity;
    private int windSpeed;
    private String condition;
    private int feelsLike;
    private LocalDateTime timestamp;

    public WeatherData(String cityName, int temperature, int humidity, int windSpeed,
                      String condition, int feelsLike) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.feelsLike = feelsLike;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public String getCityName() { return cityName; }
    public int getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public int getWindSpeed() { return windSpeed; }
    public String getCondition() { return condition; }
    public int getFeelsLike() { return feelsLike; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
