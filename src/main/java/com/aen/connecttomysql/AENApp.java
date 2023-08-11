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

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
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

    private ObservableList<MembersEntity> membersData;




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

                membersData = FXCollections.observableArrayList();

                // Initialize the table views
                membersTable = new TableView<>(membersData);
                activitiesTable = new TableView<>();
                planificationsTable = new TableView<>();
                formationsTable = new TableView<>();




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

                TableColumn<ActivitiesEntity, Integer> clientColumn = new TableColumn<>("Client_ID");
                clientColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getClient_id().getId()));

                TableColumn<ActivitiesEntity, Integer> piloteColumn = new TableColumn<>("Pilote_ID");
                piloteColumn.setCellValueFactory(cellData -> {
                    MembersEntity pilote = cellData.getValue().getPilote_id();
                    return pilote != null ? new ReadOnlyObjectWrapper<>(pilote.getId()) : new ReadOnlyObjectWrapper<>(null);
                });




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

                TableColumn<FormationEntity, Integer> clientfColumn = new TableColumn<>("Client_ID");
                clientfColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getClient_id().getId()));

                TableColumn<FormationEntity, Integer> formateurfColumn = new TableColumn<>("Formateur_ID");
                formateurfColumn.setCellValueFactory(cellData ->
                        new ReadOnlyObjectWrapper<>(cellData.getValue().getFormateur_id().getId()));

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
                activitiesTable.getColumns().addAll(idaColumn,activitenomColumn,  clientColumn,piloteColumn); // add columns to new table
                planificationsTable.getColumns().addAll(idpColumn,datepColumn,timepColumn,activitepColumn,avionpColumn,ulmpColumn);
                formationsTable.getColumns().addAll(idfColumn,formationnomColumn, datedColumn, datefColumn,timeColumn,clientfColumn,formateurfColumn);

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

    private void updateTableData() {
        membersData.clear(); // Supprimer les anciennes données
        membersData.addAll(membersService.getAllMembers()); // Ajouter toutes les nouvelles données
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

       // Dialog<Pair<String, String>> addDialog = new Dialog<>();
        Dialog<MembersEntity> addDialog = new Dialog<>();
        addDialog.setTitle("Add !");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        if(table.equals("Member")){
                    // Create the username and password labels and fields
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

                    // PasswordField password = new PasswordField();
                    //password.setPromptText("Nom");

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


                    addDialog.getDialogPane().setContent(grid);

                   /* // Convert the result to a username-password-pair when the login button is clicked
                    addDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == addButtonType) {
                            return new Pair<>(userid.getText(), nom.getText());
                        }
                        return null;
                    });

                    Optional<Pair<String, String>> result = addDialog.showAndWait();*/

            // Convert the result to a MembersEntity when the button is clicked
            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    MembersEntity newMember = new MembersEntity();
                    newMember.setId(Integer.parseInt(userid.getText()));
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
                    updateTableData();
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
        }


        /*

        } else if (table.equals("Activity")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField activiteid = new TextField();
            activiteid.setPromptText("int");
            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField pilote_id = new TextField();
            pilote_id.setPromptText("int");


            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");

            grid.add(new Label("Activite_ID:"), 0, 0);
            grid.add(activiteid, 1, 0);
            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);
            grid.add(new Label("Client ID:"), 0, 2);
            grid.add(client_id, 1, 2);
            grid.add(new Label("Pilote ID:"), 0, 3);
            grid.add(pilote_id, 1, 3);


            addDialog.getDialogPane().setContent(grid);

            // Convert the result to a username-password-pair when the login button is clicked
            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    return new Pair<>(activiteid.getText(), nom_activite.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = addDialog.showAndWait();

        } else if (table.equals("Planification")){
        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField planificationid = new TextField();
        planificationid.setPromptText("int");
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


        // PasswordField password = new PasswordField();
        //password.setPromptText("Nom");
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



            addDialog.getDialogPane().setContent(grid);

        // Convert the result to a username-password-pair when the login button is clicked
        addDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(planificationid.getText(), date.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = addDialog.showAndWait();

        } else if (table.equals("Formation")){
            // Create the username and password labels and fields
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
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField formateur_id = new TextField();
            formateur_id.setPromptText("int");



            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");
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
            grid.add(new Label("Client ID:"), 0, 6);
            grid.add(client_id, 1, 6);
            grid.add(new Label("Formateur ID:"), 0, 7);
            grid.add(formateur_id, 1, 7);



            addDialog.getDialogPane().setContent(grid);

            // Convert the result to a username-password-pair when the login button is clicked
            addDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    return new Pair<>(formationid.getText(), nom.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = addDialog.showAndWait();
        }
    */
    }

    private void showUpdateWindow(String table) {

        Dialog<Pair<String, String>> updateDialog = new Dialog<>();
        updateDialog.setTitle("Update !");

        // Set the button types
        ButtonType updateButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
        updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        if(table.equals("Member")){
            // Create the username and password labels and fields
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

            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");

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
                    return new Pair<>(userid.getText(), nom.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = updateDialog.showAndWait();

        } else if (table.equals("Activity")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField activiteid = new TextField();
            activiteid.setPromptText("int");
            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField pilote_id = new TextField();
            pilote_id.setPromptText("int");


            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");

            grid.add(new Label("Activite_ID:"), 0, 0);
            grid.add(activiteid, 1, 0);
            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);
            grid.add(new Label("Client ID:"), 0, 2);
            grid.add(client_id, 1, 2);
            grid.add(new Label("Pilote ID:"), 0, 3);
            grid.add(pilote_id, 1, 3);


            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return new Pair<>(activiteid.getText(), nom_activite.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = updateDialog.showAndWait();

        } else if (table.equals("Planification")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField planificationid = new TextField();
            planificationid.setPromptText("int");
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


            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");
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



            updateDialog.getDialogPane().setContent(grid);


            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return new Pair<>(planificationid.getText(), date.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = updateDialog.showAndWait();

        } else if (table.equals("Formation")){
            // Create the username and password labels and fields
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
            TextField client_id = new TextField();
            client_id.setPromptText("int");
            TextField formateur_id = new TextField();
            formateur_id.setPromptText("int");



            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");
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
            grid.add(new Label("Client ID:"), 0, 6);
            grid.add(client_id, 1, 6);
            grid.add(new Label("Formateur ID:"), 0, 7);
            grid.add(formateur_id, 1, 7);



            updateDialog.getDialogPane().setContent(grid);

            updateDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return new Pair<>(formationid.getText(), nom.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = updateDialog.showAndWait();
        }

    }

    private void showDeleteWindow(String table) {

        Dialog<Pair<String, String>> deleteDialog = new Dialog<>();
        deleteDialog.setTitle("Delete !");

        // Set the button types
        ButtonType deleteButtonType = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
        deleteDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        if(table.equals("Member")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField userid = new TextField();
            userid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");


            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");

            grid.add(new Label("Member_ID:"), 0, 0);
            grid.add(userid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);



            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    return new Pair<>(userid.getText(), nom.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = deleteDialog.showAndWait();

        } else if (table.equals("Activity")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField activiteid = new TextField();
            activiteid.setPromptText("int");
            TextField nom_activite = new TextField();
            nom_activite.setPromptText("varchar(255)");


            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");

            grid.add(new Label("Activite_ID:"), 0, 0);
            grid.add(activiteid, 1, 0);
            grid.add(new Label("Nom Activite:"), 0, 1);
            grid.add(nom_activite, 1, 1);

            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    return new Pair<>(activiteid.getText(), nom_activite.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = deleteDialog.showAndWait();

        } else if (table.equals("Planification")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField planificationid = new TextField();
            planificationid.setPromptText("int");
            TextField date = new TextField();
            date.setPromptText("datetime(6)");



            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");
            grid.add(new Label("Planification_ID:"), 0, 0);
            grid.add(planificationid, 1, 0);
            grid.add(new Label("Date:"), 0, 1);
            grid.add(date, 1, 1);




            deleteDialog.getDialogPane().setContent(grid);


            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    return new Pair<>(planificationid.getText(), date.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = deleteDialog.showAndWait();

        } else if (table.equals("Formation")){
            // Create the username and password labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField formationid = new TextField();
            formationid.setPromptText("int");
            TextField nom = new TextField();
            nom.setPromptText("varchar(255)");




            // PasswordField password = new PasswordField();
            //password.setPromptText("Nom");
            grid.add(new Label("Formation_ID:"), 0, 0);
            grid.add(formationid, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(nom, 1, 1);




            deleteDialog.getDialogPane().setContent(grid);

            deleteDialog.setResultConverter(dialogButton -> {
                if (dialogButton == deleteButtonType) {
                    return new Pair<>(formationid.getText(), nom.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = deleteDialog.showAndWait();
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
