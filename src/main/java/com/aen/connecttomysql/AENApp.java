package com.aen.connecttomysql;


import com.wicookin.connecttomysql.EventsEntity;
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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


@SpringBootApplication
public class AENApp extends Application {

    private ConfigurableApplicationContext context;
    private MemberRepository memberRepository;
    private ActivityRepository activityRepository;
    private FormationRepository formationRepository;


    private TableView<MembersEntity> membersTable;
    private TableView<ActivitiesEntity> activitiesTable;

    private TableView<FormationEntity> formationsTable;

    private Chart chart1 = null;
    private Chart chart2 = null;
    private Chart chart3 = null;
    private Chart chart4  = null;
    private Chart chart5 = null;

    private Chart chart6 = null;

    private Chart chart7 = null;

    private Chart chart8 = null;

    private Chart chart9 = null;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.context = new SpringApplicationBuilder()
                .sources(AENApp.class)
                .run(args);

        memberRepository = context.getBean(MemberRepository.class);
        activityRepository = context.getBean(ActivityRepository.class);
        formationRepository = context.getBean(FormationRepository.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

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
                primaryStage.setTitle("AEN Dashboard");

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
                activitiesTable = new TableView<>();
                formationsTable = new TableView<>();

                // Set up columns for members
                TableColumn<MembersEntity, String> nomColumn = new TableColumn<>("Nom");
                nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

                TableColumn<MembersEntity, String> prenomColumn = new TableColumn<>("Prenom");
                prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

                TableColumn<MembersEntity, String> emailColumn = new TableColumn<>("Email");
                emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<MembersEntity, String> genreColumn = new TableColumn<>("Genre");
                genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

                TableColumn<MembersEntity, String> datenColumn = new TableColumn<>("Date_Naissance");
                datenColumn.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));

                TableColumn<MembersEntity, String> typeColumn = new TableColumn<>("Type");
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

                TableColumn<MembersEntity, Boolean> cotisationColumn = new TableColumn<>("Cotisation");
                cotisationColumn.setCellValueFactory(new PropertyValueFactory<>("cotisation"));

                TableColumn<MembersEntity, Boolean> ffaadColumn = new TableColumn<>("FFA_Adhesion");
                ffaadColumn.setCellValueFactory(new PropertyValueFactory<>("ffa_adhesion"));





                // Dans votre méthode start, créez un nouveau bouton pour ouvrir la fenêtre graphique
                Button statsButton = new Button("Show Members Statistics");
                statsButton.setOnAction(event -> {
                    try {
                        showStatisticsWindow();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Dans votre méthode start, créez un nouveau bouton pour ouvrir la fenêtre graphique
                Button statsButton2 = new Button("Show Activities Statistics");
                /*statsButton2.setOnAction(event -> showEventsStatisticsWindow());*/ //moooooooooooooodif

                Button statsButton3 = new Button("Show Formations Statistics");
                /*statsButton3.setOnAction(event -> showServiceStatisticsWindow());*/   // moooooooooooooooodif

                // Add the tables and the button to a VBox
                VBox layout = new VBox(membersTable, statsButton, activitiesTable,statsButton2, formationsTable, statsButton3);
                layout.setSpacing(10);  // Set spacing between elements in the VBox

                /*exportMenuItem.setOnAction(e -> {
                    PDFGenerator pdfGenerator = new PDFGenerator();
                    List<Chart> charts = Arrays.asList(chart1, chart2, chart3);
                    List<Chart> charts2 = Arrays.asList( chart6,chart4, chart7);
                    List<Chart> charts3 = Arrays.asList(chart5,chart8,chart9);
                    pdfGenerator.generatePDF(charts,charts2,charts3);

                });*/

                // Ajoutez le bouton à votre layout principal
                mainLayout.setCenter(layout);

                // Set up columns for events
                TableColumn<ActivitiesEntity, String> activitenomColumn = new TableColumn<>("Nom d'activité");
                activitenomColumn.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));

                //TableColumn<ActivitiesEntity, String> descriptionColumn = new TableColumn<>("Description");
                //descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                TableColumn<ActivitiesEntity, Integer> clientColumn = new TableColumn<>("Client_ID");
                clientColumn.setCellValueFactory(new PropertyValueFactory<>("client_id"));

                TableColumn<ActivitiesEntity, Integer> piloteColumn = new TableColumn<>("Pilote_ID");
                piloteColumn.setCellValueFactory(new PropertyValueFactory<>("pilote_id"));




                TableColumn<FormationEntity, String> formationnomColumn = new TableColumn<>("Nom de la formation");
                formationnomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

                TableColumn<FormationEntity, String> datedColumn = new TableColumn<>("Date_Debut");
                datedColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));

                TableColumn<FormationEntity, String> datefColumn = new TableColumn<>("Date_Fin");
                datefColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

                TableColumn<FormationEntity, Time> timeColumn = new TableColumn<>("Heure de début");
                timeColumn.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));

                TableColumn<FormationEntity, Integer> clientfColumn = new TableColumn<>("Client_ID");
                clientfColumn.setCellValueFactory(new PropertyValueFactory<>("client_id"));

                TableColumn<FormationEntity, Integer> formateurfColumn = new TableColumn<>("Formateur_ID");
                formateurfColumn.setCellValueFactory(new PropertyValueFactory<>("formateur_id"));


                // Use a custom cell factory to format the time
                timeColumn.setCellFactory(column -> {
                    return new TableCell<FormationEntity, Time>() {
                        @Override
                        protected void updateItem(Time item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item == null || empty) {
                                setText(null);
                            } else {
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSSSS");
                                setText(sdf.format(item));
                            }
                        }
                    };
                });



                // Add columns to the tables
                membersTable.getColumns().addAll(nomColumn, prenomColumn, emailColumn,genreColumn, datenColumn, typeColumn);
                activitiesTable.getColumns().addAll(activitenomColumn,  clientColumn,piloteColumn); // add columns to new table
                formationsTable.getColumns().addAll(formationnomColumn, datedColumn, datefColumn,timeColumn,clientfColumn,formateurfColumn);

                // Retrieve data and set it as table items
                Platform.runLater(() -> {
                    membersTable.setItems(retrieveMembersData());
                    activitiesTable.setItems(retrieveEventsData()); // retrieve data for new table
                    formationsTable.setItems(retrieveServicesData());
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

    private ObservableList<ActivitiesEntity> retrieveEventsData() { // new method for retrieving event data
        Iterable<ActivitiesEntity> activities = activityRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(activities.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<FormationEntity> retrieveServicesData() {
        Iterable<FormationEntity> formations = formationRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(formations.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }



    // Authenticate user
    public boolean authenticate(String username, String password) {
        // Actual authentication mechanism depends on your needs
        // For simplicity, this just checks if username is "admin" and password is "password"
        return "debian".equals(username) && "Wicookin2023".equals(password);
    }

    private void showStatisticsWindow() throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calendar!");
        stage.setScene(scene);
        stage.show();

    }

    /*private void showStatisticsWindow() {
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

            // Count events by date
            Map<String, Long> eventCounts = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(e -> e.getDate().toString(), Collectors.counting())); // convert Date to String

            // Count events by time
            Map<String, Long> eventTimeCounts = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(e -> e.getStartTime().toString(), Collectors.counting())); // convert Time to String

            // Count events by name
            Map<String, Long> eventNameCounts = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(EventsEntity::getName, Collectors.counting()));

            // Create event count bar chart
            CategoryAxis eventXAxis = new CategoryAxis();
            eventXAxis.setLabel("Date");

            NumberAxis eventYAxis = new NumberAxis();
            eventYAxis.setLabel("Event Count");

            BarChart<String, Number> eventChart = new BarChart<>(eventXAxis, eventYAxis);
            eventChart.setTitle("Schedule Frequency");

            XYChart.Series<String, Number> eventSeries = new XYChart.Series<>();
            eventCounts.forEach((date, count) -> eventSeries.getData().add(new XYChart.Data<>(date, count)));

            eventChart.getData().add(eventSeries);

            // Create event count bar chart
            CategoryAxis eventXaxisTime = new CategoryAxis();
            eventXaxisTime.setLabel("Time");

            NumberAxis eventYaxisTime = new NumberAxis();
            eventYaxisTime.setLabel("Event Count");

            BarChart<String, Number> eventtimeChart = new BarChart<>(eventXaxisTime, eventYaxisTime);
            eventtimeChart.setTitle("Schedule Time Frequency");

            XYChart.Series<String, Number> eventTimeSeries = new XYChart.Series<>();
            eventTimeCounts.forEach((time, count) -> eventTimeSeries.getData().add(new XYChart.Data<>(time, count)));

            eventtimeChart.getData().add(eventTimeSeries);

            // Create event type pie chart
            ObservableList<PieChart.Data> eventTypeData = FXCollections.observableArrayList();
            eventNameCounts.forEach((name, count) -> eventTypeData.add(new PieChart.Data(name, count)));

            PieChart eventTypeChart = new PieChart(eventTypeData);
            eventTypeChart.setTitle("Tasting Type");

            chart4 = eventChart;
            chart6 = eventTypeChart;
            chart7 = eventtimeChart;

            VBox layout = new VBox(eventChart, eventTypeChart, eventtimeChart);
            layout.setSpacing(10);

            Scene scene = new Scene(layout, 800, 600);
            stage.setScene(scene);
            stage.show();






    }

    private void showServiceStatisticsWindow() {

        Stage stage = new Stage();
        stage.setTitle("Service Statistics");

        // Count services by price
        Map<String, Long> servicePriceCounts = StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(s -> s.getPrice().toString(), Collectors.counting())); // convert Price to String

        // Count services by name
        Map<String, Long> serviceNameCounts = StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(s -> s.getName().toString(), Collectors.counting())); // convert Name to String

        // Count services by category
        Map<String, Long> serviceCounts = StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(s -> s.getCategory_id().getName(), Collectors.counting()));

        // Create service count bar chart
        CategoryAxis servicexaxis = new CategoryAxis();
        servicexaxis.setLabel("Price");

        NumberAxis serviceyaxis = new NumberAxis();
        serviceyaxis.setLabel("Name Count");

        BarChart<String, Number> serviceNameChart = new BarChart<>(servicexaxis, serviceyaxis);
        serviceNameChart.setTitle("Services by Name");

        XYChart.Series<String, Number> serviceNameSeries = new XYChart.Series<>();
        serviceNameCounts.forEach((name, count) -> serviceNameSeries.getData().add(new XYChart.Data<>(name, count)));

        serviceNameChart.getData().add(serviceNameSeries);

        // Create service count bar chart
        CategoryAxis serviceXaxis = new CategoryAxis();
        serviceXaxis.setLabel("Price");

        NumberAxis serviceYaxis = new NumberAxis();
        serviceYaxis.setLabel("Price Count");

        BarChart<String, Number> servicePriceChart = new BarChart<>(serviceXaxis, serviceYaxis);
        servicePriceChart.setTitle("Services by Price");

        XYChart.Series<String, Number> servicePriceSeries = new XYChart.Series<>();
        servicePriceCounts.forEach((price, count) -> servicePriceSeries.getData().add(new XYChart.Data<>(price, count)));

        servicePriceChart.getData().add(servicePriceSeries);

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
        chart8 = servicePriceChart;
        chart9 = serviceNameChart;

        VBox layout = new VBox(serviceChart,servicePriceChart,serviceNameChart);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }*/

}
