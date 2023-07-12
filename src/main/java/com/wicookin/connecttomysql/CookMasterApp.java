package com.wicookin.connecttomysql;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@SpringBootApplication
public class CookMasterApp extends Application {

    private ConfigurableApplicationContext context;
    private MemberRepository memberRepository;
    private EventRepository eventRepository;
    private ServiceRepository serviceRepository;


    private TableView<MembersEntity> membersTable;
    private TableView<EventsEntity> eventsTable;

    private TableView<ServicesEntity> servicesTable;

    private Chart chart1 = null;
    private Chart chart2 = null;
    private Chart chart3 = null;
    private Chart chart4  = null;
    private Chart chart5 = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.context = new SpringApplicationBuilder()
                .sources(CookMasterApp.class)
                .run(args);

        memberRepository = context.getBean(MemberRepository.class);
        eventRepository = context.getBean(EventRepository.class);
        serviceRepository = context.getBean(ServiceRepository.class);
    }

    @Override
    public void start(Stage primaryStage) {

        // Create login dialog
        Dialog<Pair<String, String>> loginDialog = new Dialog<>();
        loginDialog.setTitle("Login");

        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        loginDialog.getDialogPane().setContent(grid);

        // Convert the result to a username-password-pair when the login button is clicked
        loginDialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = loginDialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            // Authentication process
            if (authenticate(usernamePassword.getKey(), usernamePassword.getValue())) {
                // if login successful
                // Create main window
                primaryStage.setTitle("Cook Master Dashboard");

                // Create the main layout
                BorderPane mainLayout = new BorderPane();
                mainLayout.setPadding(new Insets(10));

                // Create the menu bar
                MenuBar menuBar = new MenuBar();
                Menu fileMenu = new Menu("File");
                MenuItem exportMenuItem = new MenuItem("Export to PDF");

                fileMenu.getItems().add(exportMenuItem);
                menuBar.getMenus().add(fileMenu);
                mainLayout.setTop(menuBar);

                // Initialize the table views
                membersTable = new TableView<>();
                eventsTable = new TableView<>();
                servicesTable = new TableView<>();

                // Set up columns for members
                TableColumn<MembersEntity, String> lastNameColumn = new TableColumn<>("Last Name");
                lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

                TableColumn<MembersEntity, String> firstNameColumn = new TableColumn<>("First Name");
                firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

                TableColumn<MembersEntity, String> emailColumn = new TableColumn<>("Email");
                emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<MembersEntity, String> genderColumn = new TableColumn<>("Gender");
                genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

                TableColumn<MembersEntity, String> datebColumn = new TableColumn<>("Date of Birth");
                datebColumn.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));

                TableColumn<MembersEntity, String> typeColumn = new TableColumn<>("Type");
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));


                // Dans votre méthode start, créez un nouveau bouton pour ouvrir la fenêtre graphique
                Button statsButton = new Button("Show Members Statistics");
                statsButton.setOnAction(event -> showStatisticsWindow());

                // Dans votre méthode start, créez un nouveau bouton pour ouvrir la fenêtre graphique
                Button statsButton2 = new Button("Show Events Statistics");
                statsButton2.setOnAction(event -> showEventsStatisticsWindow());

                Button statsButton3 = new Button("Show Services Statistics");
                statsButton3.setOnAction(event -> showServiceStatisticsWindow());

                // Add the tables and the button to a VBox
                VBox layout = new VBox(membersTable, statsButton, eventsTable,statsButton2, servicesTable, statsButton3);
                layout.setSpacing(10);  // Set spacing between elements in the VBox

                exportMenuItem.setOnAction(e -> {
                    PDFGenerator pdfGenerator = new PDFGenerator();
                    List<Chart> charts = Arrays.asList(chart1, chart2, chart3, chart4, chart5);
                    pdfGenerator.generatePDF(charts);
                });

                // Ajoutez le bouton à votre layout principal
                mainLayout.setCenter(layout);

                // Set up columns for events
                TableColumn<EventsEntity, String> nameColumn = new TableColumn<>("Event Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<EventsEntity, String> descriptionColumn = new TableColumn<>("Description");
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                TableColumn<EventsEntity, String> dateColumn = new TableColumn<>("Date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));




                TableColumn<ServicesEntity, String> serviceNameColumn = new TableColumn<>("Service Name");
                serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<ServicesEntity, String> serviceDescriptionColumn = new TableColumn<>("Description");
                serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                TableColumn<ServicesEntity, BigDecimal> servicePriceColumn = new TableColumn<>("Price");
                servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));





                // Add columns to the tables
                membersTable.getColumns().addAll(lastNameColumn, firstNameColumn, emailColumn,genderColumn, datebColumn, typeColumn);
                eventsTable.getColumns().addAll(nameColumn, descriptionColumn, dateColumn); // add columns to new table
                servicesTable.getColumns().addAll(serviceNameColumn, serviceDescriptionColumn, servicePriceColumn);

                // Retrieve data and set it as table items
                Platform.runLater(() -> {
                    membersTable.setItems(retrieveMembersData());
                    eventsTable.setItems(retrieveEventsData()); // retrieve data for new table
                });



                // Set up the scene
                Scene scene = new Scene(mainLayout, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                // Create an Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de Connexion");
                alert.setHeaderText(null);
                alert.setContentText("Login Failed!");

                alert.showAndWait();
            }
        });


    }

    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }

    private ObservableList<MembersEntity> retrieveMembersData() {
        Iterable<MembersEntity> members = memberRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(members.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<EventsEntity> retrieveEventsData() { // new method for retrieving event data
        Iterable<EventsEntity> events = eventRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(events.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<ServicesEntity> retrieveServicesData() {
        Iterable<ServicesEntity> services = serviceRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(services.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }



    // Authenticate user
    public boolean authenticate(String username, String password) {
        // Actual authentication mechanism depends on your needs
        // For simplicity, this just checks if username is "admin" and password is "password"
        return "debian".equals(username) && "Wicookin2023".equals(password);
    }

    private void showStatisticsWindow() {
        Stage stage = new Stage();
        stage.setTitle("Statistics");

        // Count the genders
        long maleCount = memberRepository.countByGender("Male");
        long femaleCount = memberRepository.countByGender("Female");

        // Create gender pie chart
        ObservableList<PieChart.Data> genderData = FXCollections.observableArrayList(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount)
        );
        PieChart genderChart = new PieChart(genderData);
        genderChart.setTitle("Gender Distribution");

        // Count the types
        Map<String, Long> typeCounts = StreamSupport.stream(memberRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(MembersEntity::getType, Collectors.counting()));


        // Create type bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Type");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        BarChart<String, Number> typeChart = new BarChart<>(xAxis, yAxis);
        typeChart.setTitle("Type Distribution");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        typeCounts.forEach((type, count) -> pieChartData.add(new PieChart.Data(type, count)));

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Member Types");

        // Bar chart
        CategoryAxis xaxis = new CategoryAxis();
        NumberAxis yaxis = new NumberAxis();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        typeCounts.forEach((type, count) -> series.getData().add(new XYChart.Data<>(type, count)));

        BarChart<String, Number> barChart = new BarChart<>(xaxis, yaxis);
        barChart.getData().add(series);
        barChart.setTitle("Member Types");


        chart1 = genderChart;
        chart2 = pieChart;
        chart3 = barChart;


        VBox layout = new VBox(genderChart, pieChart, barChart);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showEventsStatisticsWindow() {
        Stage stage = new Stage();
        stage.setTitle("Events Statistics");

        // Count events by member
        Map<String, Long> eventCounts = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(e -> e.getMember_id().getFirstname() + " " + e.getMember_id().getLastname(), Collectors.counting()));

        // Create event count bar chart
        CategoryAxis eventXAxis = new CategoryAxis();
        eventXAxis.setLabel("Member");

        NumberAxis eventYAxis = new NumberAxis();
        eventYAxis.setLabel("Event Count");

        BarChart<String, Number> eventChart = new BarChart<>(eventXAxis, eventYAxis);
        eventChart.setTitle("Events by Member");

        XYChart.Series<String, Number> eventSeries = new XYChart.Series<>();
        eventCounts.forEach((member, count) -> eventSeries.getData().add(new XYChart.Data<>(member, count)));

        eventChart.getData().add(eventSeries);

        chart4 = eventChart;

        VBox layout = new VBox(eventChart);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showServiceStatisticsWindow() {
        Stage stage = new Stage();
        stage.setTitle("Service Statistics");

        // Count services by category
        Map<String, Long> serviceCounts = StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(s -> s.getCategory_id().getName(), Collectors.counting()));

        // Create service count bar chart
        CategoryAxis serviceXAxis = new CategoryAxis();
        serviceXAxis.setLabel("Category");

        NumberAxis serviceYAxis = new NumberAxis();
        serviceYAxis.setLabel("Service Count");

        BarChart<String, Number> serviceChart = new BarChart<>(serviceXAxis, serviceYAxis);
        serviceChart.setTitle("Services by Category");

        XYChart.Series<String, Number> serviceSeries = new XYChart.Series<>();
        serviceCounts.forEach((category, count) -> serviceSeries.getData().add(new XYChart.Data<>(category, count)));

        serviceChart.getData().add(serviceSeries);

        chart5 = serviceChart;

        VBox layout = new VBox(serviceChart);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

}
