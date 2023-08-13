package com.aen.connecttomysql;


import com.wicookin.connecttomysql.EventsEntity;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    private PlanificationRepository planificationRepository;

    private FormationRepository formationRepository;


    private TableView<MembersEntity> membersTable;
    private TableView<ActivitiesEntity> activitiesTable;

    private TableView<PlanificationEntity> planificationsTable;

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

    @Autowired
    private MembersService membersService;

   // private  ObservableList<FormationEntity> formationData = FXCollections.observableArrayList();

    private ObservableList<MembersEntity> membersData = FXCollections.observableArrayList();


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
        planificationRepository = context.getBean(PlanificationRepository.class);
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
                planificationsTable = new TableView<>();
                formationsTable = new TableView<>();

               //formationsTable.setItems(formationData);


                // Set up columns for members
                TableColumn<MembersEntity, Integer> idmColumn = new TableColumn<>("Member_ID");
                idmColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

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
                /*statsButton3.setOnAction(event -> showServiceStatisticsWindow());*/   // moooooooooooooooodif

                Button AddButton = new Button("Add");
                AddButton.setOnAction(event -> showAddWindow("Member"));

                Button UpdateButton = new Button("Update");
                UpdateButton.setOnAction(event -> showUpdateWindow("Member"));

                Button DeleteButton = new Button("Delete");
                DeleteButton.setOnAction(event -> showDeleteWindow("Member"));

                // Dans votre méthode start, créez un nouveau bouton pour ouvrir la fenêtre graphique
                Button statsButton2 = new Button("Show Activities Statistics");
                /*statsButton2.setOnAction(event -> showEventsStatisticsWindow());*/ //moooooooooooooodif

                Button AddButton2 = new Button("Add");
                AddButton2.setOnAction(event -> showAddWindow("Activity"));

                Button UpdateButton2 = new Button("Update");
                UpdateButton2.setOnAction(event -> showUpdateWindow("Activity"));

                Button DeleteButton2 = new Button("Delete");
                DeleteButton2.setOnAction(event -> showDeleteWindow("Activity"));

                Button calendarButton = new Button("Show Calendar Statistics");
                calendarButton.setOnAction(event -> {
                    try {
                        showPlanificationsWindow();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button AddButton3 = new Button("Add");
                AddButton3.setOnAction(event -> showAddWindow("Planification"));

                Button UpdateButton3 = new Button("Update");
                UpdateButton3.setOnAction(event -> showUpdateWindow("Planification"));

                Button DeleteButton3 = new Button("Delete");
                DeleteButton3.setOnAction(event -> showDeleteWindow("Planification"));

                Button statsButton3 = new Button("Show Formations Statistics");
                /*statsButton3.setOnAction(event -> showServiceStatisticsWindow());*/   // moooooooooooooooodif

                Button AddButton4 = new Button("Add");
                AddButton4.setOnAction(event -> showAddWindow("Formation"));
                Button UpdateButton4 = new Button("Update");
                UpdateButton4.setOnAction(event -> showUpdateWindow("Formation"));

                Button DeleteButton4 = new Button("Delete");
                DeleteButton4.setOnAction(event -> showDeleteWindow("Formation"));

                HBox buttonBox1 = new HBox(statsButton, AddButton, UpdateButton, DeleteButton);
                buttonBox1.setSpacing(10); // Set spacing between buttons


                HBox buttonBox2 = new HBox(statsButton2, AddButton2, UpdateButton2, DeleteButton2);
                buttonBox2.setSpacing(10); // Set spacing between buttons

                HBox buttonBox3 = new HBox(calendarButton, AddButton3, UpdateButton3, DeleteButton3);
                buttonBox3.setSpacing(10); // Set spacing between buttons

                HBox buttonBox4 = new HBox(statsButton3, AddButton4, UpdateButton4, DeleteButton4);
                buttonBox4.setSpacing(10); // Set spacing between buttons

                // Add the tables and the button to a VBox
                VBox layout = new VBox(membersTable, buttonBox1, activitiesTable, buttonBox2, planificationsTable, buttonBox3, formationsTable, buttonBox4);
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

                // Set up columns for activities
                TableColumn<ActivitiesEntity, Integer> idaColumn = new TableColumn<>("Activite_ID");
                idaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<ActivitiesEntity, String> activitenomColumn = new TableColumn<>("Nom d'activité");
                activitenomColumn.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));

                //TableColumn<ActivitiesEntity, String> descriptionColumn = new TableColumn<>("Description");
                //descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                TableColumn<ActivitiesEntity, Float> prixactiviteColumn = new TableColumn<>("Prix de l'activite");
                prixactiviteColumn.setCellValueFactory(new PropertyValueFactory<>("prix_activite"));





                // Set up columns for planifications
                TableColumn<PlanificationEntity, Integer> idpColumn = new TableColumn<>("Planification_ID");
                idpColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<PlanificationEntity, String> datepColumn = new TableColumn<>("Date de planification");
                datepColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<PlanificationEntity, Time> timepColumn = new TableColumn<>("Heure de planification");
                timepColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));

                TableColumn<PlanificationEntity, Integer> activitepColumn = new TableColumn<>("Activite");
                activitepColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getActivite_id().getId()));

                TableColumn<PlanificationEntity, Integer> avionpColumn = new TableColumn<>("Avion");
                avionpColumn.setCellValueFactory(cellData -> {
                    AvionEntity avion = cellData.getValue().getAvion_id();
                    return avion != null ? new ReadOnlyObjectWrapper<>(avion.getId()) : new ReadOnlyObjectWrapper<>(null);
                });

                TableColumn<PlanificationEntity, Integer> ulmpColumn = new TableColumn<>("Ulm");
                ulmpColumn.setCellValueFactory(cellData -> {
                    UlmEntity ulm = cellData.getValue().getUlm_id();
                    return ulm != null ? new ReadOnlyObjectWrapper<>(ulm.getId()) : new ReadOnlyObjectWrapper<>(null);
                });

                TableColumn<PlanificationEntity, Integer> clientpColumn = new TableColumn<>("Client_ID");
                clientpColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getClient_id().getId()));

                TableColumn<PlanificationEntity, Integer> pilotepColumn = new TableColumn<>("Pilote_ID");
                pilotepColumn.setCellValueFactory(cellData -> {
                    MembersEntity pilote = cellData.getValue().getPilote_id();
                    return pilote != null ? new ReadOnlyObjectWrapper<>(pilote.getId()) : new ReadOnlyObjectWrapper<>(null);
                });

                // Use a custom cell factory to format the time
                timepColumn.setCellFactory(column -> {
                    return new TableCell<PlanificationEntity, Time>() {
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


                // Set up columns for formations
                TableColumn<FormationEntity, Integer> idfColumn = new TableColumn<>("Formation_ID");
                idfColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<FormationEntity, String> formationnomColumn = new TableColumn<>("Nom de la formation");
                formationnomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

                TableColumn<FormationEntity, String> datedColumn = new TableColumn<>("Date_Debut");
                datedColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));

                TableColumn<FormationEntity, String> datefColumn = new TableColumn<>("Date_Fin");
                datefColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

                TableColumn<FormationEntity, Time> timeColumn = new TableColumn<>("Heure de début");
                timeColumn.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));

                /*TableColumn<FormationEntity, Integer> clientfColumn = new TableColumn<>("Client_ID");
                clientfColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getClient_id().getId()));

                TableColumn<FormationEntity, Integer> formateurfColumn = new TableColumn<>("Formateur_ID");
                formateurfColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getFormateur_id().getId()));*/

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
                membersTable.getColumns().addAll(idmColumn,nomColumn, prenomColumn, emailColumn,genreColumn, datenColumn, typeColumn);
                activitiesTable.getColumns().addAll(idaColumn,activitenomColumn, prixactiviteColumn); // add columns to new table
                planificationsTable.getColumns().addAll(idpColumn,datepColumn,timepColumn,activitepColumn,avionpColumn,ulmpColumn,  clientpColumn,pilotepColumn);
                formationsTable.getColumns().addAll(idfColumn,formationnomColumn, datedColumn, datefColumn,timeColumn/*,clientfColumn,formateurfColumn*/);

                // Retrieve data and set it as table items
                Platform.runLater(() -> {
                    membersTable.setItems(retrieveMembersData());
                    activitiesTable.setItems(retrieveActivitiesData()); // retrieve data for new table
                    planificationsTable.setItems(retrievePlanificationsData());
                    formationsTable.setItems(retrieveFormationsData());
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

    private ObservableList<ActivitiesEntity> retrieveActivitiesData() { // new method for retrieving event data
        Iterable<ActivitiesEntity> activities = activityRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(activities.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<PlanificationEntity> retrievePlanificationsData() { // new method for retrieving event data
        Iterable<PlanificationEntity> planifications = planificationRepository.findAll();
        return FXCollections.observableArrayList(
                StreamSupport.stream(planifications.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<FormationEntity> retrieveFormationsData() {
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

    private void showPlanificationsWindow() throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calendar!");
        stage.setScene(scene);
        stage.show();

    }

    private void showAddWindow(String table) {






        if(table.equals("Member")){
                    Dialog<MembersEntity> addDialog = new Dialog<>();
                    addDialog.setTitle("Add !");

                    // Set the button types
                    ButtonType addButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
                    addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
                    // Create the username and password labels and fields
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    //TextField userid = new TextField();
                    //userid.setPromptText("int");
                    TextField nom = new TextField();
                    nom.setPromptText("varchar(255)");
                    TextField prenom = new TextField();
                    prenom.setPromptText("varchar(255)");
                    TextField date_naissance = new TextField();
                    date_naissance.setPromptText("datetime(6)");
                    TextField adresse = new TextField();
                    adresse.setPromptText("varchar(255)");
                    TextField email = new TextField();
                    email.setPromptText("varchar(255)");
                    TextField cotisation = new TextField();
                    cotisation.setPromptText("tinyint(1)");
                    TextField ffa_adhesion = new TextField();
                    ffa_adhesion.setPromptText("tinyint(1)");
                    TextField date_adhesion = new TextField();
                    date_adhesion.setPromptText("datetime(6)");
                    TextField date_renouvellement = new TextField();
                    date_renouvellement.setPromptText("datetime(6)");
                    TextField type = new TextField();
                    type.setPromptText("varchar(255)");
                    TextField telephone = new TextField();
                    telephone.setPromptText("varchar(255)");
                    TextField genre = new TextField();
                    genre.setPromptText("varchar(255)");
                    TextField password = new TextField();
                    password.setPromptText("varchar(255)");



                    grid.add(new Label("Nom:"), 0, 1);
                    grid.add(nom, 1, 1);
                    grid.add(new Label("Prenom:"), 0, 2);
                    grid.add(prenom, 1, 2);
                    grid.add(new Label("Date Naissance:"), 0, 3);
                    grid.add(date_naissance, 1, 3);
                    grid.add(new Label("Adresse:"), 0, 4);
                    grid.add(adresse, 1, 4);
                    grid.add(new Label("Email:"), 0, 5);
                    grid.add(email, 1, 5);
                    grid.add(new Label("Cotisation:"), 0, 6);
                    grid.add(cotisation, 1, 6);
                    grid.add(new Label("FFA Adhesion:"), 0, 7);
                    grid.add(ffa_adhesion, 1, 7);
                    grid.add(new Label("Date Adhesion:"), 0, 8);
                    grid.add(date_adhesion, 1, 8);
                    grid.add(new Label("Date Renouvellement:"), 0, 9);
                    grid.add(date_renouvellement, 1, 9);
                    grid.add(new Label("Type:"), 0, 10);
                    grid.add(type, 1, 10);
                    grid.add(new Label("Telephone:"), 0, 11);
                    grid.add(telephone, 1, 11);
                    grid.add(new Label("Genre:"), 0, 12);
                    grid.add(genre, 1, 12);
                    grid.add(new Label("Password:"), 0, 13);
                    grid.add(password, 1, 13);


                    addDialog.getDialogPane().setContent(grid);



            // Convert the result to a MembersEntity when the button is clicked
            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    MembersEntity newMember = new MembersEntity();
                    //newMember.setId(Integer.parseInt(userid.getText()));
                    newMember.setNom(nom.getText());
                    newMember.setPrenom(prenom.getText());
                    newMember.setAdresse(adresse.getText());
                    newMember.setEmail(email.getText());
                    newMember.setType(type.getText());
                    newMember.setTelephone(telephone.getText());
                    newMember.setGenre(genre.getText());
                    newMember.setPassword(password.getText());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        if (!date_naissance.getText().isEmpty()) {
                            newMember.setDate_naissance(formatter.parse(date_naissance.getText()));
                        }
                        if (!date_adhesion.getText().isEmpty()) {
                            newMember.setDate_adhesion(formatter.parse(date_adhesion.getText()));
                        }
                        if (!date_renouvellement.getText().isEmpty()) {
                            newMember.setDate_renouvellement(formatter.parse(date_renouvellement.getText()));
                        }
                    } catch (ParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("Le format de la date est incorrect");
                        alert.setContentText("Veuillez entrer la date au format YYYY-MM-DD");
                        alert.showAndWait();
                        return null;
                    }

                    newMember.setCotisation(cotisation.getText().equals("1"));
                    newMember.setFfa_adhesion(ffa_adhesion.getText().equals("1"));

                    return newMember;
                }
                return null;
            });

            Optional<MembersEntity> result = addDialog.showAndWait();

            result.ifPresent(memberEntity -> {


                try {
                    MemberRepository memberRepository = context.getBean(MemberRepository.class);
                    membersService  = new MembersService(memberRepository);
                    membersService.addMember(memberEntity); // Utilisez l'instance injectée
                    //updateTableData();
                    membersTable.getItems().add(memberEntity);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Membre ajouté avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'ajout");
                    alert.setHeaderText("Erreur lors de l'ajout du membre à la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();


                }
            });





        } else if (table.equals("Activity")){
            Dialog<ActivitiesEntity> addDialog = new Dialog<>();
            addDialog.setTitle("Add !");

            // Set the button types
            ButtonType addButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");
            TextField prix_activite = new TextField();
            prix_activite.setPromptText("float");




            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);
            grid.add(new Label("Prix de l'Activite"), 0, 2);
            grid.add(prix_activite, 1, 2);



            addDialog.getDialogPane().setContent(grid);
            // Supposons que vous ayez un service ou un repository pour accéder à la base de données des membres.



            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    ActivitiesEntity newActivity = new ActivitiesEntity();


                    newActivity.setNom_activite(nom_activite.getText());
                    newActivity.setPrix_activite(Float.parseFloat(prix_activite.getText()));





                    return newActivity;
                }
                return null;
            });

            Optional<ActivitiesEntity> result = addDialog.showAndWait();

            result.ifPresent(activityEntity -> {
                try {
                    ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                    ActivitiesService activityService = new ActivitiesService(activityRepository);
                    activityService.addActivity(activityEntity); // Supposons que vous avez une méthode addActivity dans votre service
                   // updateTableData();
                    activitiesTable.getItems().add(activityEntity); // Supposons que vous avez une TableView pour les activités
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Activité ajoutée avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'ajout");
                    alert.setHeaderText("Erreur lors de l'ajout de l'activité à la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });



        } else if (table.equals("Planification")){

            Dialog<PlanificationEntity> addDialog = new Dialog<>();
            addDialog.setTitle("Add !");

            // Set the button types
            ButtonType addButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));


            TextField date = new TextField();
            date.setPromptText("datetime(6)");
            TextField heure = new TextField();
            heure.setPromptText("time(6)");
            TextField activite_id = new TextField();
            activite_id.setPromptText("int");
            TextField avion_id = new TextField();
            avion_id.setPromptText("int");
            TextField ulm_id = new TextField();
            ulm_id.setPromptText("int");
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField pilote_id = new TextField();
            pilote_id.setPromptText("int");




            grid.add(new Label("Date:"), 0, 1);
            grid.add(date, 1, 1);
            grid.add(new Label("Heure:"), 0, 2);
            grid.add(heure, 1, 2);
            grid.add(new Label("Activite ID:"), 0, 3);
            grid.add(activite_id, 1, 3);
            grid.add(new Label("Avion ID:"), 0, 4);
            grid.add(avion_id, 1, 4);
            grid.add(new Label("ULM ID:"), 0, 5);
            grid.add(ulm_id, 1, 5);
            grid.add(new Label("Client ID:"), 0, 6);
            grid.add(client_id, 1, 6);
            grid.add(new Label("Pilote ID:"), 0, 7);
            grid.add(pilote_id, 1, 7);

            addDialog.getDialogPane().setContent(grid);


            UlmRepository ulmRepository = context.getBean(UlmRepository.class);
            AvionRepository avionRepository = context.getBean(AvionRepository.class);

            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    PlanificationEntity newPlanification = new PlanificationEntity();


                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        if (!date.getText().isEmpty()) {
                            newPlanification.setDate(formatter.parse(date.getText()));
                        }

                    } catch (ParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("Le format de la date est incorrect");
                        alert.setContentText("Veuillez entrer la date au format YYYY-MM-DD");
                        alert.showAndWait();
                        return null;
                    }

                    try {
                        String heureString = heure.getText();  // récupère le texte du champ TextField
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // Format de l'heure. Assurez-vous que ce format correspond à la manière dont l'utilisateur entre l'heure.
                        java.util.Date parsed = format.parse(heureString);
                        java.sql.Time sqlTime = new java.sql.Time(parsed.getTime());
                        newPlanification.setHeure(sqlTime);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                    // Récupérer l'entité activité correspondant à l'ID de l'activité fourni
                    ActivitiesEntity activiteEntity = activityRepository.findById((long) Integer.parseInt(activite_id.getText())).orElse(null);
                    if (activiteEntity == null) {
                        // Gérer l'erreur si l'ID de l'activité n'est pas trouvé
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Activité non trouvée");
                        alert.setContentText("L'ID de l'activité fourni ne correspond à aucune activité enregistrée.");
                        alert.showAndWait();
                        return null;
                    }
                    newPlanification.setActivite_id(activiteEntity);

                    // Récupérer l'entité avion correspondant à l'ID de l'avion fourni
                    AvionEntity avionEntity = avionRepository.findById((long) Integer.parseInt(avion_id.getText())).orElse(null);

                    newPlanification.setAvion_id(avionEntity);

                    if (avionEntity == null) {
                        // Gérer l'erreur si l'ID de l'ULM n'est pas trouvé
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Avion non trouvé");
                        alert.setContentText("L'ID de l'Avion fourni ne correspond  à aucun avion enregistré.");
                        alert.showAndWait();
                        return null;
                    }

                    // Récupérer l'entité ULM correspondant à l'ID de l'ULM fourni
                    UlmEntity ulmEntity = ulmRepository.findById((long) Integer.parseInt(ulm_id.getText())).orElse(null);
                    newPlanification.setUlm_id(ulmEntity);
                    if (ulmEntity == null) {
                        // Gérer l'erreur si l'ID de l'ULM n'est pas trouvé
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("ULM non trouvé ");
                        alert.setContentText("L'ID de l'ULM  fourni ne correspond à aucun ULM enregistré.");
                        alert.showAndWait();
                        return null;
                    }

                    // Récupérer l'entité membre correspondant à l'ID du client fourni
                    MembersEntity clientEntity = memberRepository.findById((long) Integer.parseInt(client_id.getText())).orElse(null);
                    if (clientEntity == null) {
                        // Gérer l'erreur si l'ID du client n'est pas trouvé
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Client non trouvé");
                        alert.setContentText("L'ID du client fourni ne correspond à aucun membre.");
                        alert.showAndWait();
                        return null;
                    }
                    newPlanification.setClient_id(clientEntity);

                    // Récupérer l'entité membre correspondant à l'ID du pilote fourni
                    MembersEntity piloteEntity = memberRepository.findById((long) Integer.parseInt(pilote_id.getText())).orElse(null);
                    if (piloteEntity == null) {
                        // Gérer l'erreur si l'ID du pilote n'est pas trouvé
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Pilote non trouvé");
                        alert.setContentText("L'ID du pilote fourni ne correspond à aucun membre.");
                        alert.showAndWait();
                        return null;
                    }
                    newPlanification.setPilote_id(piloteEntity);


                    return newPlanification;
                }
                return null;
            });

            Optional<PlanificationEntity> result = addDialog.showAndWait();

            result.ifPresent(planificationEntity -> {
                try {
                    PlanificationRepository planificationRepository = context.getBean(PlanificationRepository.class);
                    PlanificationService planificationService = new PlanificationService(planificationRepository);

                    planificationService.addPlanification(planificationEntity); // Je suppose que vous avez une méthode addPlanification dans votre service

                    // Mettre à jour la table ou l'ajouter à la liste, selon votre mise en page
                    planificationsTable.getItems().add(planificationEntity);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Planification ajoutée avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'ajout");
                    alert.setHeaderText("Erreur lors de l'ajout de la planification à la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });



        } else if (table.equals("Formation")){

            Dialog<FormationEntity> addDialog = new Dialog<>();
            addDialog.setTitle("Add !");

            // Set the button types
            ButtonType addButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));


            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");
            TextField date_debut = new TextField();
            date_debut.setPromptText("datetime(6)");
            TextField date_fin = new TextField();
            date_fin.setPromptText("datetime(6)");
            TextField heure_debut = new TextField();
            heure_debut.setPromptText("time(6)");
            TextField heure_fin = new TextField();
            heure_fin.setPromptText("time(6)");
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField formateur_id = new TextField();
            formateur_id.setPromptText("int");




            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);
            grid.add(new Label("Date Debut:"), 0, 2);
            grid.add(date_debut, 1, 2);
            grid.add(new Label("Date Fin:"), 0, 3);
            grid.add(date_fin, 1, 3);
            grid.add(new Label("Heure Debut:"), 0, 4);
            grid.add(heure_debut, 1, 4);
            grid.add(new Label("Heure Fin:"), 0, 5);
            grid.add(heure_fin, 1, 5);
            grid.add(new Label("Client ID:"), 0, 6);
            grid.add(client_id, 1, 6);
            grid.add(new Label("Formateur ID:"), 0, 7);
            grid.add(formateur_id, 1, 7);

            addDialog.getDialogPane().setContent(grid);

            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    FormationEntity newFormation = new FormationEntity();

                    // Récupérer et régler les valeurs de base

                    newFormation.setNom(nom.getText());
                    // Vous aurez besoin de convertir les chaînes en dates et heures

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        if (!date_debut.getText().isEmpty()) {
                            newFormation.setDate_debut(formatter.parse(date_debut.getText()));
                        }
                        if (!date_fin.getText().isEmpty()) {
                            newFormation.setDate_fin(formatter.parse(date_fin.getText()));
                        }

                    } catch (ParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("Le format de la date est incorrect");
                        alert.setContentText("Veuillez entrer la date au format YYYY-MM-DD");
                        alert.showAndWait();
                        return null;
                    }

                    try {
                        String heuredebutString = heure_debut.getText();  // récupère le texte du champ TextField
                        String heurefinString = heure_fin.getText();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // Format de l'heure. Assurez-vous que ce format correspond à la manière dont l'utilisateur entre l'heure.
                        java.util.Date parsed = format.parse(heuredebutString);
                        java.util.Date parsed2 = format.parse(heurefinString);
                        java.sql.Time sqlTime = new java.sql.Time(parsed.getTime());
                        java.sql.Time sqlTime2 = new java.sql.Time(parsed2.getTime());
                        newFormation.setHeure_debut(sqlTime);
                        newFormation.setHeure_fin(sqlTime2);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                    // Récupérer l'entité membre correspondant à l'ID du client fourni
                    MembersEntity clientEntity = memberRepository.findById((long) Integer.parseInt(client_id.getText())).orElse(null);
                    if (clientEntity == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Client non trouvé");
                        alert.setContentText("L'ID du client fourni ne correspond à aucun membre.");
                        alert.showAndWait();
                        return null;
                    }
                    newFormation.setClient_id(clientEntity);

                    // Récupérer l'entité membre correspondant à l'ID du formateur fourni
                    MembersEntity formateurEntity = memberRepository.findById((long) Integer.parseInt(formateur_id.getText())).orElse(null);
                    if (formateurEntity == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Formateur non trouvé");
                        alert.setContentText("L'ID du formateur fourni ne correspond à aucun membre.");
                        alert.showAndWait();
                        return null;
                    }
                    newFormation.setFormateur_id(formateurEntity);

                    return newFormation;
                }
                return null;
            });

            Optional<FormationEntity> result = addDialog.showAndWait();

            result.ifPresent(formationEntity -> {
                try {
                    FormationRepository formationRepository = context.getBean(FormationRepository.class);
                    FormationService formationService = new FormationService(formationRepository);
                    formationService.addFormation(formationEntity); // Supposons que vous avez une méthode addFormation dans votre service

                    formationsTable.getItems().add(formationEntity); // Supposons que vous avez une TableView pour les formations
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Formation ajoutée avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'ajout");
                    alert.setHeaderText("Erreur lors de l'ajout de la formation à la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }


    }

    private void showUpdateWindow(String table) {


        if (table.equals("Member")) {
            Dialog<MembersEntity> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update !");

            // Set the button types
            ButtonType updateButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField userid = new TextField();
            userid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");
            TextField prenom = new TextField();
            prenom.setPromptText("varchar(255)");
            TextField date_naissance = new TextField();
            date_naissance.setPromptText("datetime(6)");
            TextField adresse = new TextField();
            adresse.setPromptText("varchar(255)");
            TextField email = new TextField();
            email.setPromptText("varchar(255)");
            TextField cotisation = new TextField();
            cotisation.setPromptText("tinyint(1)");
            TextField ffa_adhesion = new TextField();
            ffa_adhesion.setPromptText("tinyint(1)");
            TextField date_adhesion = new TextField();
            date_adhesion.setPromptText("datetime(6)");
            TextField date_renouvellement = new TextField();
            date_renouvellement.setPromptText("datetime(6)");
            TextField type = new TextField();
            type.setPromptText("varchar(255)");
            TextField telephone = new TextField();
            telephone.setPromptText("varchar(255)");
            TextField genre = new TextField();
            genre.setPromptText("varchar(255)");
            TextField password = new TextField();
            password.setPromptText("varchar(255)");
            // Listener to populate fields based on ID
            userid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int memberId = Integer.parseInt(newValue);
                        MemberRepository memberRepository = context.getBean(MemberRepository.class);
                        MembersEntity existingMember = memberRepository.findById((long) memberId).orElse(null);

                        if (existingMember != null) {
                            nom.setText(existingMember.getNom());
                            prenom.setText(existingMember.getPrenom());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Adaptez le format comme vous le souhaitez
                            String formattedDate = sdf.format(existingMember.getDate_naissance());
                            date_naissance.setText(formattedDate);
                            adresse.setText(existingMember.getAdresse());
                            email.setText(existingMember.getEmail());
                            cotisation.setText(String.valueOf(existingMember.getCotisation()));
                            ffa_adhesion.setText(String.valueOf(existingMember.getFfa_adhesion()));
                            date_adhesion.setText(existingMember.getDate_adhesion().toString());
                            date_renouvellement.setText(existingMember.getDate_renouvellement().toString());
                            type.setText(existingMember.getType());
                            telephone.setText(existingMember.getTelephone());
                            genre.setText(existingMember.getGenre());
                            password.setText(existingMember.getPassword());

                            //... [Set all the other fields from the existingMember object]

                        } else {
                            // Clear all fields if no member exists with the entered ID
                            nom.clear();
                            prenom.clear();
                            date_naissance.clear();
                            adresse.clear();
                            email.clear();
                            cotisation.clear();
                            ffa_adhesion.clear();
                            date_adhesion.clear();
                            date_renouvellement.clear();
                            type.clear();
                            telephone.clear();
                            genre.clear();
                            password.clear();

                        }

                    } catch (NumberFormatException e) {
                        // Handle exception for non-integer input
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID du membre doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            // ... [The rest of your code remains unchanged]

            grid.add(new Label("Member_ID:"), 0, 0);
            grid.add(userid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);
            grid.add(new Label("Prenom:"), 0, 2);
            grid.add(prenom, 1, 2);
            grid.add(new Label("Date Naissance:"), 0, 3);
            grid.add(date_naissance, 1, 3);
            grid.add(new Label("Adresse:"), 0, 4);
            grid.add(adresse, 1, 4);
            grid.add(new Label("Email:"), 0, 5);
            grid.add(email, 1, 5);
            grid.add(new Label("Cotisation:"), 0, 6);
            grid.add(cotisation, 1, 6);
            grid.add(new Label("FFA Adhesion:"), 0, 7);
            grid.add(ffa_adhesion, 1, 7);
            grid.add(new Label("Date Adhesion:"), 0, 8);
            grid.add(date_adhesion, 1, 8);
            grid.add(new Label("Date Renouvellement:"), 0, 9);
            grid.add(date_renouvellement, 1, 9);
            grid.add(new Label("Type:"), 0, 10);
            grid.add(type, 1, 10);
            grid.add(new Label("Telephone:"), 0, 11);
            grid.add(telephone, 1, 11);
            grid.add(new Label("Genre:"), 0, 12);
            grid.add(genre, 1, 12);
            grid.add(new Label("Password:"), 0, 13);
            grid.add(password, 1, 13);

            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    MembersEntity updatedMember;
                    int memberId = Integer.parseInt(userid.getText());
                    MemberRepository memberRepository = context.getBean(MemberRepository.class);
                    updatedMember = memberRepository.findById((long) memberId).orElse(new MembersEntity());

                    LocalDate localDateValue = LocalDate.parse(date_naissance.getText());
                    java.util.Date utilDate = java.sql.Date.valueOf(localDateValue);


                    // Now update the fields with the new values
                    updatedMember.setNom(nom.getText());
                    updatedMember.setPrenom(prenom.getText());
                    updatedMember.setDate_naissance(utilDate); // or another appropriate parsing method
                    updatedMember.setAdresse(adresse.getText());
                    updatedMember.setEmail(email.getText());
                    updatedMember.setCotisation(Boolean.parseBoolean(cotisation.getText())); // Assuming it's a boolean or a tinyint which represents boolean
                    updatedMember.setFfa_adhesion(Boolean.parseBoolean(ffa_adhesion.getText())); // Same assumption
                    updatedMember.setDate_adhesion(utilDate);
                    updatedMember.setDate_renouvellement(utilDate);
                    updatedMember.setType(type.getText());
                    updatedMember.setTelephone(telephone.getText());
                    updatedMember.setGenre(genre.getText());
                    updatedMember.setPassword(password.getText());

                    return updatedMember;
                }
                return null;
            });

            Optional<MembersEntity> result = updateDialog.showAndWait();

            result.ifPresent(memberEntity -> {
                try {
                    MemberRepository memberRepository = context.getBean(MemberRepository.class);
                    membersService = new MembersService(memberRepository);
                    membersService.updateMember(memberEntity);
                    // Here, you might want to update your table view with the updated data

                    int memberId = Integer.parseInt(userid.getText());
                    membersTable.getItems().set(memberId -1,memberEntity);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Membre mis à jour avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de mise à jour");
                    alert.setHeaderText("Erreur lors de la mise à jour du membre dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }

        else if (table.equals("Activity")) {
            Dialog<ActivitiesEntity> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Activity");

            ButtonType updateButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField activityId = new TextField();
            activityId.setPromptText("int");
            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");
            TextField prix_activite = new TextField();
            prix_activite.setPromptText("float");

            // Listener to populate fields based on ID
            activityId.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int id = Integer.parseInt(newValue);
                        ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                        ActivitiesEntity existingActivity = activityRepository.findById((long) id).orElse(null);

                        if (existingActivity != null) {
                            nom_activite.setText(existingActivity.getNom_activite());
                            prix_activite.setText(String.valueOf(existingActivity.getPrix_activite()));

                        } else {
                            nom_activite.clear();
                            prix_activite.clear();
                        }
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID de l'activité doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            grid.add(new Label("Activity_ID:"), 0, 0);
            grid.add(activityId, 1, 0);
            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);
            grid.add(new Label( "Prix de l'Activite"),0,2);
            grid.add(prix_activite,1,2);

            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    ActivitiesEntity updatedActivity;
                    int id = Integer.parseInt(activityId.getText());
                    ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                    updatedActivity = activityRepository.findById((long) id).orElse(new ActivitiesEntity());

                    updatedActivity.setNom_activite(nom_activite.getText());
                    updatedActivity.setPrix_activite(Float.parseFloat(prix_activite.getText()));


                    return updatedActivity;
                }
                return null;
            });

            Optional<ActivitiesEntity> result = updateDialog.showAndWait();

            result.ifPresent(activityEntity -> {
                try {
                    ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                    ActivitiesService activityService = new ActivitiesService(activityRepository);
                    activityService.updateActivity(activityEntity); // Assuming you have an updateActivity method in your service
                    // Here, you might want to update your table view with the updated data

                    int activityid = Integer.parseInt(activityId.getText());
                    activitiesTable.getItems().set(activityid -1,activityEntity);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Activité mise à jour avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de mise à jour");
                    alert.setHeaderText("Erreur lors de la mise à jour de l'activité dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });


        } else if (table.equals("Planification")) {

            Dialog<PlanificationEntity> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Planification!");

            ButtonType updateButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField planificationid = new TextField();
            planificationid.setPromptText("int");
            TextField date = new TextField();
            date.setPromptText("yyyy-MM-dd");
            TextField heure = new TextField();
            heure.setPromptText("HH:mm:ss");
            TextField activite_id = new TextField();
            activite_id.setPromptText("int");
            TextField avion_id = new TextField();
            avion_id.setPromptText("int");
            TextField ulm_id = new TextField();
            ulm_id.setPromptText("int");
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField pilote_id = new TextField();
            pilote_id.setPromptText("int");

            // Listener to populate fields based on planificationid
            planificationid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int id = Integer.parseInt(newValue);
                        PlanificationRepository planificationRepository = context.getBean(PlanificationRepository.class);
                        PlanificationEntity existingPlanification = planificationRepository.findById((long) id).orElse(null);

                        if (existingPlanification != null) {
                            date.setText(existingPlanification.getDate().toString());
                            heure.setText(existingPlanification.getHeure().toString());

                            // Populate associated IDs as well
                            activite_id.setText(String.valueOf(existingPlanification.getActivite_id().getId()));
                            client_id.setText(String.valueOf(existingPlanification.getClient_id().getId()));
                            ulm_id.setText(String.valueOf(existingPlanification.getUlm_id().getId()));
                            avion_id.setText(String.valueOf(existingPlanification.getAvion_id().getId()));
                            pilote_id.setText(String.valueOf(existingPlanification.getPilote_id().getId()));
                            System.out.println("Fetched Planification: " + existingPlanification);

                        } else {
                            // Clear all fields or show feedback to the user that the entity doesn't exist.
                            date.clear();
                            heure.clear();
                            activite_id.clear();
                            avion_id.clear();
                            ulm_id.clear();
                            client_id.clear();
                            pilote_id.clear();
                        }
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID de la planification doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });



            grid.add(new Label("Planification_ID:"), 0, 0);
            grid.add(planificationid, 1, 0);
            grid.add(new Label("Date:"), 0, 1);
            grid.add(date, 1, 1);
            grid.add(new Label("Heure:"), 0, 2);
            grid.add(heure, 1, 2);
            grid.add(new Label("Activite ID:"), 0, 3);
            grid.add(activite_id, 1, 3);
            grid.add(new Label("Avion ID:"), 0, 4);
            grid.add(avion_id, 1, 4);
            grid.add(new Label("ULM ID:"), 0, 5);
            grid.add(ulm_id, 1, 5);
            grid.add(new Label("Client ID:"), 0, 6);
            grid.add(client_id, 1, 6);
            grid.add(new Label("Pilote ID:"), 0, 7);
            grid.add(pilote_id, 1, 7);

            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    PlanificationEntity updatedPlanification = new PlanificationEntity();

                    // Assuming setters are present in PlanificationEntity
                    updatedPlanification.setId(Integer.parseInt(planificationid.getText()));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    ActivityRepository activitiesRepository = context.getBean(ActivityRepository.class);
                    AvionRepository avionRepository = context.getBean(AvionRepository.class);
                    UlmRepository ulmRepository = context.getBean(UlmRepository.class);
                    MemberRepository memberRepository = context.getBean(MemberRepository.class);
                    // Fetch the associated entities using the IDs from the text fields.
                    ActivitiesEntity associatedActivity = activitiesRepository.findById((long) Integer.parseInt(activite_id.getText())).orElse(null);
                    AvionEntity associatedAvion = avionRepository.findById((long) Integer.parseInt(avion_id.getText())).orElse(null);
                    UlmEntity associatedUlm = ulmRepository.findById((long) Integer.parseInt(ulm_id.getText())).orElse(null);
                    MembersEntity associatedClient = memberRepository.findById((long) Integer.parseInt(client_id.getText())).orElse(null);
                    MembersEntity associatedPilote = memberRepository.findById((long) Integer.parseInt(pilote_id.getText())).orElse(null);


                    try {
                        Date parsedDate = format.parse(date.getText());
                        updatedPlanification.setDate(parsedDate);
                        java.sql.Time parsedTime = java.sql.Time.valueOf(heure.getText());
                        updatedPlanification.setHeure(parsedTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Ideally, handle this exception more gracefully.
                    }

                    updatedPlanification.setActivite_id(associatedActivity);
                    updatedPlanification.setAvion_id(associatedAvion);
                    updatedPlanification.setUlm_id(associatedUlm);
                    updatedPlanification.setClient_id(associatedClient);
                    updatedPlanification.setPilote_id(associatedPilote);


                    return updatedPlanification;
                }
                return null;
            });

            Optional<PlanificationEntity> result = updateDialog.showAndWait();

            result.ifPresent(planificationEntity -> {
                try {
                    PlanificationRepository planificationRepository = context.getBean(PlanificationRepository.class);
                    PlanificationService planificationService = new PlanificationService(planificationRepository);

                    planificationService.updatePlanification(planificationEntity);

                    int planification = Integer.parseInt(planificationid.getText());
                    planificationsTable.getItems().set(planification -1,planificationEntity);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Planification mise à jour avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de mise à jour");
                    alert.setHeaderText("Erreur lors de la mise à jour de la planification dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }else if (table.equals("Formation")) {

            Dialog<FormationEntity> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Formation!");

            ButtonType updateButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField formationid = new TextField();
            formationid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");
            TextField date_debut = new TextField();
            date_debut.setPromptText("datetime(6)");
            TextField date_fin = new TextField();
            date_fin.setPromptText("datetime(6)");
            TextField heure_debut = new TextField();
            heure_debut.setPromptText("time(6)");
            TextField heure_fin = new TextField();
            heure_fin.setPromptText("time(6)");

            // Listener to populate fields based on formationid
            formationid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int id = Integer.parseInt(newValue);
                        FormationRepository formationRepository = context.getBean(FormationRepository.class);
                        FormationEntity existingFormation = formationRepository.findById((long) id).orElse(null);

                        if (existingFormation != null) {
                            nom.setText(existingFormation.getNom());

                            // Assuming dateDebut, dateFin, heureDebut, heureFin are of type java.util.Date or java.sql.Time
                            date_debut.setText(new SimpleDateFormat("yyyy-MM-dd").format(existingFormation.getDate_debut()));
                            date_fin.setText(new SimpleDateFormat("yyyy-MM-dd").format(existingFormation.getDate_fin()));
                            heure_debut.setText(new SimpleDateFormat("HH:mm:ss").format(existingFormation.getHeure_debut()));
                            heure_fin.setText(new SimpleDateFormat("HH:mm:ss").format(existingFormation.getHeure_fin()));




                        } else {
                            // Clear all fields or show feedback to the user that the entity doesn't exist.
                            nom.clear();
                            date_debut.clear();
                            date_fin.clear();
                            heure_debut.clear();
                            heure_fin.clear();

                        }
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID de la formation doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            grid.add(new Label("Formation_ID:"), 0, 0);
            grid.add(formationid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);
            grid.add(new Label("Date Debut:"), 0, 2);
            grid.add(date_debut, 1, 2);
            grid.add(new Label("Date Fin:"), 0, 3);
            grid.add(date_fin, 1, 3);
            grid.add(new Label("Heure Debut:"), 0, 4);
            grid.add(heure_debut, 1, 4);
            grid.add(new Label("Heure Fin:"), 0, 5);
            grid.add(heure_fin, 1, 5);


            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    FormationEntity updatedFormation = new FormationEntity();

                    // Assuming setters are present in FormationEntity
                    updatedFormation.setId(Integer.parseInt(formationid.getText()));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");




                    try {
                        Date parsedDateDebut = dateFormat.parse(date_debut.getText());
                        Date parsedDateFin = dateFormat.parse(date_fin.getText());
                        Time parsedHeureDebut = Time.valueOf(heure_debut.getText());
                        Time parsedHeureFin = Time.valueOf(heure_fin.getText());

                        updatedFormation.setDate_debut(parsedDateDebut);
                        updatedFormation.setDate_fin(parsedDateFin);
                        updatedFormation.setHeure_debut(parsedHeureDebut);
                        updatedFormation.setHeure_fin(parsedHeureFin);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Ideally, handle this exception more gracefully.
                    }

                    updatedFormation.setNom(nom.getText());


                    return updatedFormation;
                }
                return null;
            });


            Optional<FormationEntity> result = updateDialog.showAndWait();

            result.ifPresent(formationEntity -> {
                try {
                    // I'm making an assumption about the names of the repository and service classes.
                    // You may need to adjust these to your actual class names.
                    FormationRepository formationRepository = context.getBean(FormationRepository.class);
                    FormationService formationService = new FormationService(formationRepository);

                    formationService.updateFormation(formationEntity);

                    int formaid = Integer.parseInt(formationid.getText());
                    formationsTable.getItems().set(formaid -1,formationEntity);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Formation mise à jour avec succès!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de mise à jour");
                    alert.setHeaderText("Erreur lors de la mise à jour de la formation dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }

    }

    private void showDeleteWindow(String table) {



        if (table.equals("Member")) {

            Dialog<MembersEntity> deleteDialog = new Dialog<>();
            deleteDialog.setTitle("Supprimer le membre");

            // Set the button types
            ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
            deleteDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField userid = new TextField();
            userid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");

            // Listener to populate fields based on ID
            userid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int memberId = Integer.parseInt(newValue);
                        MemberRepository memberRepository = context.getBean(MemberRepository.class);
                        MembersEntity existingMember = memberRepository.findById((long) memberId).orElse(null);

                        if (existingMember != null) {
                            nom.setText(existingMember.getNom());
                            //... [Set all the other fields from the existingMember object]
                        } else {
                            // Clear all fields if no member exists with the entered ID
                            nom.clear();
                            //... [Clear all the other fields]
                        }

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID du membre doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            grid.add(new Label("Member_ID:"), 0, 0);
            grid.add(userid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);

            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    // Create and return the member to delete.
                    MembersEntity memberToDelete = new MembersEntity();
                    memberToDelete.setId(Integer.parseInt(userid.getText()));
                    memberToDelete.setNom(nom.getText());
                    return memberToDelete;
                }
                return null;
            });

            Optional<MembersEntity> result = deleteDialog.showAndWait();

            result.ifPresent(memberEntity -> {
                try {
                    MemberRepository memberRepository = context.getBean(MemberRepository.class);
                    memberRepository.delete(memberEntity);
                    membersTable.getItems().remove(memberEntity);

                    List<MembersEntity> updatedList = (List<MembersEntity>) memberRepository.findAll();
                    membersTable.getItems().setAll(updatedList);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Membre supprimé");
                    alert.setHeaderText(null);
                    alert.setContentText("Le membre a été supprimé avec succès.");
                    alert.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de suppression");
                    alert.setHeaderText("Erreur lors de la suppression du membre dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }
        else if (table.equals("Activity")) {
            Dialog<ActivitiesEntity> deleteDialog = new Dialog<>();
            deleteDialog.setTitle("Supprimer l'activité");
            ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
            // Set the button types (vous l'avez déjà défini pour les membres, donc nous l'utiliserons ici aussi)
            deleteDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField activiteid = new TextField();
            activiteid.setPromptText("int");
            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");

            // Listener pour peupler les champs en fonction de l'ID de l'activité
            activiteid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int activityId = Integer.parseInt(newValue);
                        ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                        ActivitiesEntity existingActivity = activityRepository.findById((long) activityId).orElse(null);

                        if (existingActivity != null) {
                            nom_activite.setText(existingActivity.getNom_activite());
                            //... [Set all the other fields from the existingActivity object if there are any]
                        } else {
                            // Clear the field if no activity exists with the entered ID
                            nom_activite.clear();
                            //... [Clear all the other fields if there are any]
                        }

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID de l'activité doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            grid.add(new Label("Activite_ID:"), 0, 0);
            grid.add(activiteid, 1, 0);
            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);

            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    ActivitiesEntity activityToDelete = new ActivitiesEntity();
                    activityToDelete.setId(Integer.parseInt(activiteid.getText()));
                    activityToDelete.setNom_activite(nom_activite.getText());
                    return activityToDelete;
                }
                return null;
            });

            Optional<ActivitiesEntity> result = deleteDialog.showAndWait();

            result.ifPresent(activityEntity -> {
                try {
                    ActivityRepository activityRepository = context.getBean(ActivityRepository.class);
                    activityRepository.delete(activityEntity);
                    // Assuming you have an activitiesTable similar to membersTable
                    activitiesTable.getItems().remove(activityEntity);

                    List<ActivitiesEntity> updatedList = (List<ActivitiesEntity>) activityRepository.findAll();
                    activitiesTable.getItems().setAll(updatedList);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Activité supprimée");
                    alert.setHeaderText(null);
                    alert.setContentText("L'activité a été supprimée avec succès.");
                    alert.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de suppression");
                    alert.setHeaderText("Erreur lors de la suppression de l'activité dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });





    }
                else if (table.equals("Planification")) {

                Dialog<PlanificationEntity> deleteDialog = new Dialog<>();
                deleteDialog.setTitle("Supprimer la planification");

                // Set the button types
                ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
                deleteDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField planificationid = new TextField();
                planificationid.setPromptText("int");
                TextField date = new TextField();
                date.setPromptText("datetime(6)");

                planificationid.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        try {
                            int planId = Integer.parseInt(newValue);
                            PlanificationRepository planRepo = context.getBean(PlanificationRepository.class);
                            PlanificationEntity existingPlan = planRepo.findById((long) planId).orElse(null);

                            if (existingPlan != null) {
                                date.setText(existingPlan.getDate().toString());
                                //... [Set all the other fields from the existingPlan object]
                            } else {
                                date.clear();
                                //... [Clear all the other fields]
                            }

                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur de format");
                            alert.setHeaderText("L'ID de la planification doit être un nombre entier");
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    }
                });

                grid.add(new Label("Planification_ID:"), 0, 0);
                grid.add(planificationid, 1, 0);
                grid.add(new Label("Date:"), 0, 1);
                grid.add(date, 1, 1);

                deleteDialog.getDialogPane().setContent(grid);

                deleteDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == deleteButtonType) {
                        PlanificationEntity planToDelete = new PlanificationEntity();
                        planToDelete.setId(Integer.parseInt(planificationid.getText()));

                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date parsedDate = sdf.parse(date.getText());
                            planToDelete.setDate(parsedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return planToDelete;
                    }
                    return null;
                });

                Optional<PlanificationEntity> result = deleteDialog.showAndWait();

                result.ifPresent(planEntity -> {
                    try {
                        PlanificationRepository planRepo = context.getBean(PlanificationRepository.class);
                        planRepo.delete(planEntity);
                        // Assuming you have a TableView for Planification named planificationsTable
                        planificationsTable.getItems().remove(planEntity);

                        List<PlanificationEntity> updatedList = (List<PlanificationEntity>) planRepo.findAll();
                        planificationsTable.getItems().setAll(updatedList);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Planification supprimée");
                        alert.setHeaderText(null);
                        alert.setContentText("La planification a été supprimée avec succès.");
                        alert.showAndWait();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de suppression");
                        alert.setHeaderText("Erreur lors de la suppression de la planification dans la base de données");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                });


        }
        else if (table.equals("Formation")) {

            Dialog<FormationEntity> deleteDialog = new Dialog<>();
            deleteDialog.setTitle("Supprimer la formation");
            ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
            // Set the button types (re-utilisation de deleteButtonType)
            deleteDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField formationid = new TextField();
            formationid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");

            // Listener pour peupler les champs en fonction de l'ID de la formation
            formationid.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        int formationId = Integer.parseInt(newValue);
                        FormationRepository formationRepository = context.getBean(FormationRepository.class);
                        FormationEntity existingFormation = formationRepository.findById((long) formationId).orElse(null);

                        if (existingFormation != null) {
                            nom.setText(existingFormation.getNom());
                            //... [Ajoutez tous les autres champs de l'objet existingFormation si nécessaire]
                        } else {
                            nom.clear();
                            //... [Effacez tous les autres champs si nécessaire]
                        }

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setHeaderText("L'ID de la formation doit être un nombre entier");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

            grid.add(new Label("Formation_ID:"), 0, 0);
            grid.add(formationid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);

            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    // Créez et renvoyez la formation à supprimer.
                    FormationEntity formationToDelete = new FormationEntity();
                    formationToDelete.setId(Integer.parseInt(formationid.getText()));
                    formationToDelete.setNom(nom.getText());
                    return formationToDelete;
                }
                return null;
            });

            Optional<FormationEntity> result = deleteDialog.showAndWait();

            result.ifPresent(formationEntity -> {
                try {
                    FormationRepository formationRepository = context.getBean(FormationRepository.class);
                    formationRepository.delete(formationEntity);
                    // Si vous avez une table des formations semblable à membersTable :
                    formationsTable.getItems().remove(formationEntity);
                    List<FormationEntity> updatedList = (List<FormationEntity>) formationRepository.findAll();
                    formationsTable.getItems().setAll(updatedList);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Formation supprimée");
                    alert.setHeaderText(null);
                    alert.setContentText("La formation a été supprimée avec succès.");
                    alert.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de suppression");
                    alert.setHeaderText("Erreur lors de la suppression de la formation dans la base de données");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });

        }

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
