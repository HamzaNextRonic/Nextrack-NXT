package app.controller;

import app.helpers.DbConnect;
import app.helpers.MD5;
import app.model.Device;
import app.model.Event;
import app.model.LoginModel;
import app.model.User;
import app.model.prevalent.Prevalent;
import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.*;


public class HomeController implements Initializable {



    long duration;

    @FXML public JFXTimePicker timepikerI;
    @FXML public JFXTimePicker timepikerF;

    @FXML
    public Button btngetErase;
    Stage primaryStage;
    boolean isDelete = false;
    @FXML
    public Button btngetTimeChange;
    @FXML
    public Button btngetEventsList;
    @FXML
    public TextField filterField;
    @FXML
    public VBox VboxlastTeperature;
    @FXML
    public Pane pnlLoading;
    @FXML
    public Button btnsavePdfReport;
    @FXML
    public Button loadingImage;
    @FXML
    public javafx.scene.layout.StackPane stackPane;
    @FXML
    public Pane pnlchartLodingImage;
    ArrayList<Event> event1 = new ArrayList<>();
    //Liste


    @FXML
    public StackPane overlayDataLoading;

    @FXML
    public Label lblDateHeur;

    @FXML
    public StackPane overlayDataTuto;
    //zoom chart
    @FXML
    public Slider btnzoom;
    @FXML
    public Slider btnmove;
    @FXML
    public ChoiceBox btnChoiceDashboard;
    @FXML
    public Label lbltemperature;
    @FXML
    public TextField valiseId;
    @FXML
    public Label lblAddDevice;
    @FXML
    public Label lblAddUser;
    @FXML
    public Label lableAddEnr;
    @FXML
    public ChoiceBox region_added;
    @FXML
    public TextField province_added;
    @FXML
    public TextField province_updated;
    @FXML
    public ChoiceBox region_updated;

    public Button btnUpload;
    public ImageView imgDashboard;
    public ImageView imgUpload;
    public ImageView imgStatistics;
    public ImageView imgUsers;
    public ImageView imgDevice;
    @FXML
    public Pane pnlUpload;
    @FXML
    public Text lblRegion;
    @FXML
    public Pane pnlIntitialDash;
    @FXML
    public Label lblEMI;
    @FXML
    public Label lblValTemperature1;
    @FXML
    public Label lblValTemperature2;
    @FXML
    public Label lblValTemperature3;
    String valise = "0000000000000";
    int compteur = 0;
    public String serialNumber = "";
    double zoomChart = 0;
    double panRange = 0;
    //end zoom chart
    //SERIAL COM
    String vDate = "a";
    String vTime = "a";
    static SerialPort chosenPort;
    static int x = 0;
    boolean start = false;
    boolean is_firstTime = true;
    int index = 0;
    int j = 0;
    String portName = "";
    XYChart.Series<String, Number> Lseries = new XYChart.Series<String, Number>();

    public static final String deconnecter = "déconncté";
    public static final String connecter = "conncté";
    //Table
    // Table configuration
    // Page configuratio

    //private static final PDRectangle PAGE_SIZE =  new PDPage().getMediaBox();
    private static final float MARGIN = 70;
    private static final boolean IS_LANDSCAPE = false;
    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;
    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;
    @FXML
    public Label deviceLable;
    @FXML
    public static Button btnpaddUser;
    static String FolderPath = ".\\src\\app\\images\\";
    @FXML
    public ChoiceBox<String> statsSelectDevice = new ChoiceBox<>();
    @FXML
    public Button btnCloseApp;
    @FXML
    public Pane pnlDashboard;
    @FXML
    public Pane pnlDevices;
    @FXML
    public Pane pnlUsers;
    @FXML
    public Pane pnlAddDevice;
    @FXML
    public Pane pnlAddUsers;
    @FXML
    public Pane pnlStatistics;
    @FXML
    public Button btnDashboard;
    @FXML
    public Button btnStatistics;
    @FXML
    public Button btnDevice;
    @FXML
    public Button btnUsers;
    @FXML
    public Button btnSettings;
    @FXML
    public Button btnLogout;
    @FXML
    public Label chargementLabel;
    @FXML
    public LineChart<String, Number> mainChart;
    @FXML
    public Pane deviceOverlay;
    @FXML
    public TableColumn<User, Integer> col_idUser;
    @FXML
    public TableColumn<User, String> col_nameUser;
    @FXML
    public TableColumn<User, String> col_prenomUser;
    @FXML
    public TableColumn<User, Integer> col_typeUser;
    @FXML
    public TableColumn<User, String> col_actionUser;
    @FXML
    public TableColumn<User, String> col_lastConUser;
    @FXML
    public TableColumn<User, String> col_UsernameUser;
    @FXML
    public TableColumn<User, String> col_resion;
    @FXML
    public TableColumn<User, String> col_provice;
    @FXML
    public TableView<User> userTable;
    @FXML
    public TableColumn<User, Integer> col_StatusUser;
    @FXML
    public PasswordField password_added;
    @FXML
    public TextField username_added;
    @FXML
    public TextField lastname_added;
    @FXML
    public TextField name_added;
    @FXML
    public Button btnCreateUser;
    @FXML
    public CheckBox status_added;
    @FXML
    public ChoiceBox type_added;
    @FXML
    public DatePicker dtpikeri;
    @FXML
    public TextField name_updated;
    @FXML
    public Pane pnlUpdateUsers;
    @FXML
    public TextField lastname_updated;
    @FXML
    public TextField username_updated;
    @FXML
    public ChoiceBox type_updated;
    @FXML
    public CheckBox status_updated;
    @FXML
    public Button btnUpdateUser;
    @FXML
    public PasswordField password_updated;
    @FXML
    public Button btnCancelcUpdateUser;
    @FXML
    public TextField upstatednum_serial_id;
    @FXML
    public TextField updatedInterval;
    @FXML
    public TextArea updateAriaDescription;
    @FXML
    public TextField updatedTypeDevice;
    @FXML
    public TextField updateName_sensor;
    @FXML
    public Button btnupdateDevice;
    @FXML
    public Button btnCancelUpdateDevice;
    @FXML
    public Pane pnlUpdateDevice;
    @FXML
    public TextField search;
    @FXML
    public Pane pnlAddDeviceFromDash;
    @FXML
    public Button btnaddDevice;
    @FXML
    public Button btnvalidateSetting;
    @FXML
    public Pane pnlSettings;
    //save items
    @FXML
    public TextArea ariaDescription;
    @FXML
    public TextField type;
    @FXML
    public TextField interval;
    @FXML
    public TextField num_serial_id;
    @FXML
    public TextField name_sensor;
    @FXML
    public Button btndrawStatistics;
    @FXML
    public Button btnErraseStatistics;
    @FXML
    private TableColumn<Device, Integer> col_id;
    @FXML
    private TableColumn<Device, String> col_serial;
    @FXML
    private TableColumn<Device, String> col_event;
    @FXML
    private TableColumn<Device, Integer> col_type;
    @FXML
    private TableColumn<Device, String> col_action;
    @FXML
    private TableView<Device> deviceTable;
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;
    @FXML
    private Label lblprovince;


    public Connection connection;
    public LoginModel loginModel = new LoginModel();
    public MD5 md = new MD5();

    User fromTableUser;
    Device getDevice;
    PreparedStatement preparedStatement = null;
    int resultSet = 0;
    ObservableList<Device> devices = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();
    String colorStyle = "#e52342";
    String darkStyle = "#333333";
    //END TABLE
    private double xOffset = 0;
    private double yOffset = 0;

    //temperature valeur moyenne min max
    int cnt = 0;
    double min = 0, max = 0, avg = 0;
    double minw = 0, maxw = 0, avgw = 0;
    private int NbPoints = 0;
    private double sum = 0;
    private double sumw = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            connection = DbConnect.getConnection();
            // localData();
            if (loginModel.isDbConnected()) {
                System.out.println("Connected");
                String query = "CREATE DATABASE IF NOT EXISTS nextrack.db ;";
                Statement st = connection.createStatement();
                st.executeUpdate(query);
                System.out.println(" 11111111 ");


                //JOptionPane.showMessageDialog(null, "Database created");
                String q = "Create Table IF NOT EXISTS User (" +
                        " ID integer Primary KEY Autoincrement,\n" +
                        " lastName varchar(100) NOT NULL,\n" +
                        " firstName varchar(100) NOT NULL,\n" +
                        " username varchar(100) NOT NULL UNIQUE,\n" +
                        " password varchar(50),\n" +
                        " lastConnection varchar(50),\n" +
                        " status INT,\n" +
                        " type INT,\n" +
                        " region varchar(500),\n" +
                        " province varchar(500)\n" +
                        ");\n" +
                        "\n" +
                        "INSERT OR REPLACE into user(lastname,firstname,username,password,lastconnection,status,type,region,province) values ('admin','admin','admin','21232f297a57a5a743894a0e4a801fc3','',0,1, '','');\n" +

                        "create table IF NOT EXISTS device (\n" +
                        "ID integer Primary KEY Autoincrement,\n" +
                        " serial varchar(100) NOT NULL UNIQUE,\n" +
                        " name varchar(100) Not null,\n" +
                        " interval int,\n" +
                        " type INT,\n" +
                        " description varchar(65534),\n" +
                        " user_ID INT not null,\n" +
                        " FOREIGN KEY (user_ID) REFERENCES user(id)\n" +
                        ");\n" +
                        "\n" +
                        "\n" +

                        "create table IF NOT EXISTS record (\n" +
                        "ID integer Primary KEY Autoincrement,\n" +
                        "dateRecord Timestamp,\n" +
                        "user_ID INT not null,\n" +
                        "Device_ID int not null,\n" +
                        "FOREIGN KEY (user_ID) REFERENCES user(id),\n" +
                        "FOREIGN KEY (Device_ID) REFERENCES device(id)\n" +
                        ");\n" +
                        "\n" +

                        "create table IF NOT EXISTS warnning (\n" +
                        "ID integer Primary KEY Autoincrement,\n" +
                        "temperature float ,\n" +
                        "Time Timestamp not null,\n" +
                        "id_device int not null,\n" +
                        "record_ID int not null,\n" +
                        "valiseId varchar(200),\n" +
                        "FOREIGN KEY (id_device) REFERENCES device(id),\n" +
                        "FOREIGN KEY (record_ID) REFERENCES record(id)\n" +
                        ");" +

                        "create table IF NOT EXISTS events (\n" +
                        "ID integer Primary KEY Autoincrement,\n" +
                        "temperature float ,\n" +
                        "Time Timestamp not null,\n" +
                        "id_device int not null,\n" +
                        "record_ID int not null,\n" +
                        "valiseId varchar(200),\n" +
                        "FOREIGN KEY (id_device) REFERENCES device(id),\n" +
                        "FOREIGN KEY (record_ID) REFERENCES record(id)\n" +
                        ");";
                System.out.println("q= " + q);

                st.executeUpdate(q);


                //localData();
            } else {
                System.out.println("Not Connected");
            }

        } catch (Exception e) {

        }
    }

    public boolean chkDB() {
        boolean chk = false;
        String query = "select * from user;";

        try {
            PreparedStatement p = connection.prepareStatement(query);
            System.out.println("p.executeQuery().next() " + p.executeQuery().next());
            if (p.executeQuery().next())
                chk = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return chk;
    }

    public void getDevices(ActionEvent event) {

        selectDevices();
        //getViews(pnlDevices);
        getViews(pnlDevices, btnDevice, imgDevice);
        ///getViews(pnltable);
        //btnDevice.setStyle("-fx-text-fill: colorStyle");

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_event.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_action.setCellValueFactory(new PropertyValueFactory<>("Action"));
        col_action.setCellFactory(param -> new TableCell<Device, String>() {
            private Button editButton = new Button("");
            private Button deleteButton = new Button("");
            public ImageView delet = new ImageView();
            public ImageView edit = new ImageView();
            private final HBox pane = new HBox(deleteButton, editButton);

            {
                delet.setImage(new Image("/app/images/delete.png"));
                delet.setFitWidth(27);
                delet.setFitHeight(27);
                deleteButton.setGraphic(delet);
                deleteButton.setStyle("-fx-background-color: transparent");
                deleteButton.setOnAction(event -> {

                    Device getDevice = getTableView().getItems().get(getIndex());

                    //System.out.println(getDevice.getId() + "   " + getDevice.getSerial());
                    String query = "delete from device where id =" + getDevice.getId() + ";";
                    // + Prevalent.currentOnlineUser.getId()+")";

                    try {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        //alert.setHeaderText("Look, a Confirmation Dialog");
                        // Get the Stage.
                        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                        // Add a custom icon.
                        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet élément?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            preparedStatement = connection.prepareStatement(query);
                            resultSet = preparedStatement.executeUpdate();
                            selectDevices();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                edit.setImage(new Image("/app/images/edit.png"));
                edit.setFitWidth(27);
                edit.setFitHeight(27);
                editButton.setGraphic(edit);
                editButton.setStyle("-fx-background-color: transparent");
                editButton.setOnAction(event -> {

                            getDevice = getTableView().getItems().get(getIndex());
                            String query = "select * from device where id =" + getDevice.getId() + ";";
                            try {
                                // lblAddDevice.setText("Modifier un enregistreur");
                                ResultSet resultSet = null;
                                preparedStatement = connection.prepareStatement(query);
                                resultSet = preparedStatement.executeQuery();

                                if (resultSet.getInt("user_ID") == Prevalent.currentOnlineUser.getId()) {
                                    // getViews(pnlUpdateDevice);
                                    getViews(pnlUpdateDevice, btnupdateDevice, imgUpload);
                                    btnUpload.setStyle("-fx-text-fill: " + colorStyle);
                                    //System.out.println("name of sensor");
                                    updateName_sensor.setText(resultSet.getString("name"));
                                    updateAriaDescription.setText(resultSet.getString("description"));
                                    updatedInterval.setText(String.valueOf(getDevice.getInterval()));
                                    upstatednum_serial_id.setText(getDevice.getSerial());
                                    upstatednum_serial_id.setEditable(false);
                                    updatedTypeDevice.setText(String.valueOf(getDevice.getType()));


                                } else {
                                    System.out.println("Vous n'avez pas les permissions nécessaires pour modifier cet enregistreur");
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    // Get the Stage.
                                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                                    // Add a custom icon.
                                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                                    alert.setTitle("Erreur");
                                    alert.setContentText("Vous n'avez pas les permissions nécessaires pour modifier cet enregistreur");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // preparedStatement = connection.prepareStatement(query);
                                        // resultSet = preparedStatement.executeUpdate();


                                    }
                                }


                            } catch (SQLException e) {
                            }
                        }
                );
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        searchDevice();
        deviceTable.setItems(devices);

    }

    private void searchDevice() {


        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Device> filteredData = new FilteredList<>(devices, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (employee.getSerial().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(employee.getId()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false; // Does not match.
            });
            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Device> sortedData = new SortedList<>(filteredData);
            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(deviceTable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            deviceTable.setItems(sortedData);

        });

    }

    public void Login(ActionEvent event) {
        try {
            //
            //btnDashboard.
            String pass = md.getMd5(txtPassword.getText());
            if (loginModel.isLogin(txtUsername.getText(), pass)) {
                System.out.println("username and password are correct");

                Date date = new Date();
                Prevalent.currentOnlineUser.setLastCctn(date.toString());
                changeLastConnection(date.toString());


                ((Node) event.getSource()).getScene().getWindow().hide();
                primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = null;
                root = loader.load(getClass().getResource("/app/views/Home.fxml").openStream());
                primaryStage.setTitle("Nextrack | Dashboard");
                primaryStage.getIcons().add(new Image("/app/images/icon.png"));
                primaryStage.setResizable(false);
                //primaryStage.setScene(new Scene(root, 1100, 560));
                primaryStage.initStyle(StageStyle.UNDECORATED);
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                //move around here
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        primaryStage.setX(event.getScreenX() - xOffset);
                        primaryStage.setY(event.getScreenY() - yOffset);
                    }
                });

                primaryStage.setScene(new Scene(root, 1090, 550));
                primaryStage.setOnShown(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {

                    }
                });

                primaryStage.show();

            } else {
                System.out.println("username or password is not correct");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setTitle("Erreur d'authentification");
                alert.setHeaderText(null);
                alert.setContentText("Nom d'utilisateur et/ou mot de passe sont incorrects !");
                alert.showAndWait();

            }
        } catch (SQLException throwables) {
            System.out.println("error username and|or password  not correct");
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
            alert.setTitle("Login échouer");
            alert.setContentText("Nom d'utilisateur et | ou mot de passe incorrect !");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInfoCurrentUser() {
        String q = "select * from user where username = '" + Prevalent.currentOnlineUser.getUsername() + "';";

        ResultSet r = null;
        try {
            PreparedStatement p = connection.prepareStatement(q);
            r = p.executeQuery();
            Prevalent.currentOnlineUser.setId(r.getInt("id"));
            Prevalent.currentOnlineUser.setFname(r.getString("firstname"));
            Prevalent.currentOnlineUser.setLname(r.getString("lastName"));
            Prevalent.currentOnlineUser.setUsername(r.getString("username"));
            Prevalent.currentOnlineUser.setPassword(r.getString("password"));
            Prevalent.currentOnlineUser.setStatus((r.getString("status")));
            Prevalent.currentOnlineUser.setType(Integer.parseInt(r.getString("type")));
            Prevalent.currentOnlineUser.setLastCctn(r.getString("lastConnection"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    //change last connection in the login
    private void changeLastConnection(String date) {
        String query = "UPDATE user set lastconnection = '" + date + "' where id = " + Prevalent.currentOnlineUser.getId();
        try {
            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setString(1,updateName_sensor.getText());
            int resultSet = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //PDF CONTENT
    private Table createContent(PDRectangle PAGE_SIZE) {
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("Date / Heure", 200));
        columns.add(new Column("Température", 100));
        columns.add(new Column("Valise", 150));
        columns.add(new Column("Valise", 0));
        //BDD
        String serial = statsSelectDevice.getValue();
        LocalDate choosedDate = dtpikeri.getValue();

        //if(choosedDate.getMonth())
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        String date = choosedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String datePlus1 = choosedDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int idD = getIdDevice(serial);
        System.out.println("choosedDate " + choosedDate + "date = " + date);

        String query = "select * from events where id_device =" + idD + " and  time >= '" + date + " "+timepikerI.getValue()+":01" + "' and time <  '" + date + " "+timepikerF.getValue()+":00" + "'  ";
        System.out.println("creat contenet query  " +query);
        int count = 0;
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) count++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String[][] content = new String[count][4];
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            //int lengthTable ;
            // content= new String[resultSet.getFetchSize()][3];
            int index = 0;
            while (resultSet.next()) {
                // System.out.println("time "+sdf.format(resultSet.getTimestamp("Time")));
                String[] tm = resultSet.getString("Time").split(" ", -2);
                //String time="";
                //for(String a: tm) time= a.replace(":00","");
                content[index][0] = resultSet.getString("Time");
                content[index][1] = String.valueOf(resultSet.getFloat("temperature"));
                content[index][2] = resultSet.getString("valiseId");
               // content[index][3] = "";

                //series.getData().add(new XYChart.Data(""+time, resultSet.getFloat("temperature")));
                index++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }

    private Table createContentListWarning(PDRectangle PAGE_SIZE) {
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("Date / Heure", 200));
        columns.add(new Column("Température", 100));
        columns.add(new Column("Valise", 150));
        columns.add(new Column("Valise", 0));
        //BDD
        String serial = statsSelectDevice.getValue();
        LocalDate choosedDate = dtpikeri.getValue();
        //if(choosedDate.getMonth())
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        String date = choosedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String datePlus1 = choosedDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int idD = getIdDevice(serial);
        System.out.println("choosedDate " + choosedDate + "date = " + date);

        String query = "select * from warnning where id_device = " + idD + " and  time >= '" + date + " "+timepikerI.getValue()+":01" + "' and time <  '" + date + " "+timepikerF.getValue()+":00" + "'  ";
        int count = 0;
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) count++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String[][] content = new String[count][4];
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            //int lengthTable ;
            // content= new String[resultSet.getFetchSize()][3];
            int index = 0;
            //System.out.println("resultSet.next() = " + resultSet.next());
            while (resultSet.next()) {
                // System.out.println("time "+sdf.format(resultSet.getTimestamp("Time")));
                String[] tm = resultSet.getString("Time").split(" ", -2);
                //String time="";
                //for(String a: tm) time= a.replace(":00","");
                content[index][0] = resultSet.getString("Time");
                content[index][1] = String.valueOf(resultSet.getFloat("temperature"));
                content[index][2] = resultSet.getString("valiseId");
                //content[index][3] = "";

                //series.getData().add(new XYChart.Data(""+time, resultSet.getFloat("temperature")));
                index++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }

    //PDF Content Warning  "Statistiques d'alarme"
    private Table createContentWarning(PDRectangle PAGE_SIZE) {
        int countMax = 0;
        int countMin = 0;
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("Type d'alarme", 100));
        columns.add(new Column("Rang de température", 100));
        columns.add(new Column("Totale d'alarme", 100));
        columns.add(new Column("Temps d'alarme total", 100));
        columns.add(new Column("Statut d'alarme", 100));
        columns.add(new Column("Valise", 0));

        //BDD
        String serial = statsSelectDevice.getValue();
        LocalDate choosedDate = dtpikeri.getValue();
        //if(choosedDate.getMonth())
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        String date = choosedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int idD = getIdDevice(serial);
        System.out.println("choosedDate " + choosedDate + "date = " + date);

        //String query = "select Min(temperature) as minval , Max(temperature) as maxval , AVG(temperature) as avgval from warnning where id_device = " + idD + "";
        String query = "select count(temperature) as countMax from warnning where temperature >= 8  and  id_device = " + idD + "  ; ";
        String query1 = "select count(temperature) as countMin from warnning where temperature <= 2  and  id_device = " + idD + " ;";

//        String q = "select count(temperature) as countMax from events where temperature >= 8  and  id_device = " + idD + "  ; ";
//        String q1 = "select count(temperature) as countMin from events where temperature <= 2  and  id_device = " + idD + " ;";

        String[][] content = new String[2][6];
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement statement = null;
            statement = connection.prepareStatement(query1);
            ResultSet result = statement.executeQuery();



            countMax = resultSet.getInt("countMax");
            countMin = result.getInt("countMin");
            content[0][0] = "Alarme haute ";
            content[0][1] = "Au-dessus   de 8 °C";
            content[0][2] = String.valueOf(countMax);
            content[0][3] = String.valueOf(5 * countMax) + " minutes";
            if (countMax == 0)
                content[0][4] = "OK";
            else
                content[0][4] = "NON OK";
            content[0][5] = "";
            content[1][0] = "Alarme basse";
            content[1][1] = "En dessous de 2 °C";
            content[1][2] = String.valueOf(countMin);
            content[1][3] = String.valueOf(5 * countMin) + " minutes";
            if (countMin == 0)
                content[1][4] = "OK";
            else
                content[1][4] = "NON";
            content[1][5] = "";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }

    //PDF Content Warning  "Résumé de la journalisation"
    private Table createContentLogging(PDRectangle PAGE_SIZE) {

        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("Date de début", 120));
        columns.add(new Column("Date d'arrêt", 120));
        columns.add(new Column("Nombre de points", 100));
        columns.add(new Column("Intervalle de journalisation:", 160));
        columns.add(new Column("Valise", 0));
        //BDD
        String serial = statsSelectDevice.getValue();
        LocalDate choosedDate = dtpikeri.getValue();
        //if(choosedDate.getMonth())
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        String date = choosedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int idD = getIdDevice(serial);
        System.out.println("choosedDate " + choosedDate + "date = " + date);

        String query = " select count(temperature) as Wpoint from warnning where id_device = " + idD + "  ; ";
        String query1 = " select count(temperature) as Epoint from events where id_device = " + idD + "  ; ";

        String[][] content = new String[1][5];
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement prepa = null;
            prepa = connection.prepareStatement(query1);
            ResultSet r = prepa.executeQuery();

            Date WarningFirstSave0 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getWarningFirstSave(serial));
            Date EventFirstSave0 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getEventsFirstSave(serial));

            if(WarningFirstSave0.after(EventFirstSave0)){
                content[0][0] = getWarningFirstSave(serial);
            }else{
                content[0][0] = getEventsFirstSave(serial);
            }
            Date WarningFirstSave = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getWarningLastSave(serial));
            Date EventFirstSave = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getEventsLastSave(serial));

            if(WarningFirstSave.after(EventFirstSave)){
                content[0][1] =  getWarningLastSave(serial);
            }else{
                content[0][1] =  getEventsLastSave(serial);
            }
            content[0][2] = String.valueOf(resultSet.getInt("Wpoint") + r.getInt("Epoint"));
            content[0][3] = "5 minutes";
            content[0][4] = "";

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }

    //logout
    public void signOut(ActionEvent event) {

        try {

            Prevalent.currentOnlineUser.setStatus(deconnecter);
            String query = "Update  user  set status =" + 0 + " where id = " + Prevalent.currentOnlineUser.getId() + ";";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeUpdate();

            connection.close();
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = FXMLLoader.load(getClass().getResource("/app/views/Login.fxml"));

            primaryStage.setTitle("NexTrack | Authentification");
            primaryStage.getIcons().add(new Image("/app/images/icon.png"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 900, 600));

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            //move around here
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }

            });

            primaryStage.setOnShown(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                }
            });

            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDevice(ActionEvent event) {
        getViews(pnlAddDevice, btnDevice, imgDevice);
        deviceLable.setText("Modifier un enregistreur");
    }

    public void getViews(Pane newID, Button btn, ImageView img) {
        pnlDashboard.setVisible(false);
        pnlAddDevice.setVisible(false);
        pnlAddUsers.setVisible(false);
        pnlUsers.setVisible(false);
        pnlDevices.setVisible(false);
        pnlStatistics.setVisible(false);
        pnlUpload.setVisible(false);
        deviceOverlay.setVisible(false);
        pnlUpdateUsers.setVisible(false);
        pnlUpdateDevice.setVisible(false);
        pnlIntitialDash.setVisible(false);
        btnDashboard.setStyle("-fx-text-fill: " + darkStyle);
        //  btnSettings.setStyle("-fx-text-fill: "+darkStyle);
        btnUpload.setStyle("-fx-text-fill: " + darkStyle);
        btnStatistics.setStyle("-fx-text-fill: " + darkStyle);
        btnDevice.setStyle("-fx-text-fill: " + darkStyle);
        btnUsers.setStyle("-fx-text-fill: " + darkStyle);
        pnlUpdateUsers.setVisible(false);

        imgDashboard.setImage(new Image("/app/images/dashboard_inactive.png"));
        imgStatistics.setImage(new Image("/app/images/statistics_inactive.png"));
        imgDevice.setImage(new Image("/app/images/device_inactive.png"));
        imgUsers.setImage(new Image("/app/images/users_inactive.png"));
        imgUpload.setImage(new Image("/app/images/settings_inactive.png"));

        switch (img.getId()) {
            case "imgDashboard":
                imgDashboard.setImage(new Image("/app/images/dashboard_active.png"));
                break;
            case "imgStatistics":
                imgStatistics.setImage(new Image("/app/images/statistics_active.png"));

                break;
            case "imgDevice":
                imgDevice.setImage(new Image("/app/images/device_active.png"));
                break;
            case "imgUsers":
                imgUsers.setImage(new Image("/app/images/users_active.png"));
                break;
            case "imgUpload":
                imgUpload.setImage(new Image("/app/images/settings_active.png"));
                break;
        }
        btnUpload.setStyle("-fx-text-fill: " + darkStyle);
        btnStatistics.setStyle("-fx-text-fill: " + darkStyle);
        btnDevice.setStyle("-fx-text-fill: " + darkStyle);
        btnUsers.setStyle("-fx-text-fill: " + darkStyle);

        btn.setStyle("-fx-text-fill: " + colorStyle);
        newID.setVisible(true);
    }

    public void getDashbord(ActionEvent event) throws SQLException {
        localData();
        String serialTemperature =" ";
        System.out.println("btnChoiceDashboard.getValue().toString() "+btnChoiceDashboard.getItems().isEmpty());
        serialTemperature = (!btnChoiceDashboard.getItems().isEmpty() ?btnChoiceDashboard.getValue().toString():"---");
        String q = "Select name from device where serial ='" + serialTemperature + "'";
        PreparedStatement p = connection.prepareStatement(q);
        ResultSet res = p.executeQuery();
        String name = "";
        if (res.next())
            name = res.getString("name");
        lblEMI.setText((!btnChoiceDashboard.getItems().isEmpty() ?btnChoiceDashboard.getValue().toString() + " " + name:"---"));
        try {
            String query = "select * from user where username = '" + Prevalent.currentOnlineUser.getUsername() + "'";

            preparedStatement = connection.prepareStatement(query);
            ResultSet resu = preparedStatement.executeQuery();

            Prevalent.currentOnlineUser.setProvince(resu.getString("region"));
            Prevalent.currentOnlineUser.setProvince(resu.getString("province"));
            if (!resu.getString("region").equals("") || resu.getString("region") != null) {
                lblRegion.setText(resu.getString("region"));
            }
            if (!resu.getString("province").equals("")) {
                lblprovince.setText(resu.getString("province"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        getViews(pnlDashboard, btnDashboard, imgDashboard);


    }

    public void getStatistics(ActionEvent event) {
        getViews(pnlStatistics, btnStatistics, imgStatistics);
        timepikerI.setIs24HourView(true);
        timepikerF.setIs24HourView(true);
        getInfoCurrentUser();
        localData();
        dtpikeri.setValue(LocalDate.now());
    }

    public void getUsers(ActionEvent event) {
        //getViews(pnlUsers);
        getViews(pnlUsers, btnUsers, imgUsers);
        selectUsers();
        ///getViews(pnltable);
        col_idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_UsernameUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_nameUser.setCellValueFactory(new PropertyValueFactory<>("fname"));
        // col_typeUser.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_prenomUser.setCellValueFactory(new PropertyValueFactory<>("lname"));
        col_provice.setCellValueFactory(new PropertyValueFactory<>("province"));
        col_resion.setCellValueFactory(new PropertyValueFactory<>("region"));
        col_lastConUser.setCellValueFactory(new PropertyValueFactory<>("lastCctn"));
        col_StatusUser.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_actionUser.setCellValueFactory(new PropertyValueFactory<>("Action"));
        //col_actionUser.setResizable(true);
        col_actionUser.setCellFactory(param -> new TableCell<User, String>() {
            public ImageView edit = new ImageView();
            private Button editButton = new Button("");

            public ImageView delet = new ImageView();

            private Button deleteButton = new Button("");
            private HBox pane = new HBox(deleteButton, editButton);

            {
                delet.setImage(new Image("/app/images/delete.png"));
                delet.setFitWidth(27);
                delet.setFitHeight(27);
                deleteButton.setGraphic(delet);
                deleteButton.setStyle("-fx-background-color: transparent");


                deleteButton.setOnAction(event -> {
                    deleteButton.setDisable(true);
                    User getUser = getTableView().getItems().get(getIndex());

                    //String query = "delete from user where id ="+getUser.getId()+";";


                    PreparedStatement preparedStatement = null;
                    int resultSet = 0;
                    // try {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    //alert.setContentText("êtes-vous sûr de vouloir supprimer cet élément?");
                    alert.setContentText("Vous n'avez pas les permissions nécessaires pour exécuter cette action");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        // preparedStatement = connection.prepareStatement(query);
                        // resultSet = preparedStatement.executeUpdate();

                        selectUsers();
                    }

                    //  } catch (SQLException throwables) {
                    //    throwables.printStackTrace();
                    // }

                });
                edit.setImage(new Image("/app/images/edit.png"));
                edit.setFitWidth(27);
                edit.setFitHeight(27);
                editButton.setGraphic(edit);
                editButton.setStyle("-fx-background-color: transparent");
                //edit user
                editButton.setOnAction(event -> {
                    lableAddEnr.setText("Modifier Utilisateur");
                    fromTableUser = getTableView().getItems().get(getIndex());
                    //getViews(pnlUpdateUsers);
                    getViews(pnlUpdateUsers, btnUsers, imgUsers);
                    name_updated.setText(fromTableUser.getFname());
                    lastname_updated.setText(fromTableUser.getLname());
                    username_updated.setText(fromTableUser.getUsername());
                    province_updated.setText(fromTableUser.getProvince());
                    username_updated.setEditable(false);


                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        searchUser();
        deviceTable.setItems(devices);
    }

    private void selectDevices() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        devices.clear();
        deviceTable.setItems(devices);
        try {
            String query;
            // **** **** **** **** ****
            if (Prevalent.currentOnlineUser.getType() == 0) {
                query = "select * from device where user_ID=" + Prevalent.currentOnlineUser.getId() + "";
            } else query = "select * from device";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            //String fname, lname;
            while (resultSet.next()) {
                String req = "select * from user where ID =" + resultSet.getInt("user_ID") + "";
                PreparedStatement preparedStatemen = connection.prepareStatement(req);
                ResultSet result = preparedStatemen.executeQuery();
                if (result.next()) {
                    // System.out.println("111"+result.getString("firstName")+"2222"+result.getString("lastName"));
                    devices.add(new Device(resultSet.getInt("id"),
                            resultSet.getString("serial"),
                            resultSet.getInt("type"),
                            resultSet.getInt("interval"),
                            result.getString("firstName") + " " + result.getString("lastName")));
                    //devices.add(new Device(resultSet.getInt("id"),resultSet.getString("nameSensor"),resultSet.getString("serial"),resultSet.getInt("type"),resultSet.getInt("interval"),resultSet.getInt("user_ID")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //always executed
            // try {
            //preparedStatement.close();
            // resultSet.close();
            //} catch (SQLException throwables) {
            //    throwables.printStackTrace();
            //}
        }
    }

    private void selectUsers() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        users.clear();
        userTable.setItems(users);

        try {
            String query;
            if (Prevalent.currentOnlineUser.getType() == 0)
                query = "select * from user where id = " + Prevalent.currentOnlineUser.getId() + "";
            else query = "select * from user";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String stat;
                if (Prevalent.currentOnlineUser.getId() == resultSet.getInt("id"))
                    stat = connecter;
                else if (resultSet.getInt("status") == 1) {
                    stat = connecter;
                } else
                    stat = deconnecter;
                //System.out.println("lastConnecton "+resultSet.getString("lastConnection"));
                users.add(new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        stat,
                        resultSet.getString("lastConnection"),
                        resultSet.getInt("type"),
                        resultSet.getString("region"),
                        resultSet.getString("province")));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //always executed
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

    //pane to add devices
    public void getAddDevice(ActionEvent event) {
        getViews(pnlAddDevice, btnDevice, imgDevice);
    }

    //pane to add user if he is an admin
    public void addUser(ActionEvent event) {
        if (Prevalent.currentOnlineUser.getType() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de création");
            // Get the Stage.
            Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les permissions nécessaires pour exécuter cette action ");
            alert.showAndWait();
            getViews(pnlDashboard, btnUsers, imgUsers);
        } else {
            getViews(pnlAddUsers, btnUsers, imgUsers);
        }


    }

    //Create a new user
    public void CreateUser(ActionEvent event) {
        try {

            ArrayList<String> usernames = new ArrayList<>();
            String q = "select username from user";
            preparedStatement = connection.prepareStatement(q);
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()) {
                usernames.add(r.getString("username"));
            }


            boolean checked = false;
            int statutAdmin = 0;
            if (checked) statutAdmin = 1;

            //System.out.println("type: bool"+type_added.getValue()+" |: "+type_added.getValue().equals("Administrateur"));
            int typeAdmin = 0;
            if (type_added.getValue().equals("Administrateur")) {
                typeAdmin = 1;
            }
            Date date = new Date();
            String pass = md.getMd5(password_added.getText());
            String query = "insert into user (lastname, firstname, username, password,lastconnection,status,type,region,province) values ("
                    + " ?, ?,?,?,?,?,?, ?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,lastname_added.getText());
            preparedStatement.setString(2,name_added.getText());
            preparedStatement.setString(3,username_added.getText());
            preparedStatement.setString(4,pass);
            preparedStatement.setString(5,date.toString());
            preparedStatement.setInt(6,statutAdmin);
            preparedStatement.setInt(7,typeAdmin);
            preparedStatement.setString(8,region_added.getValue().toString());
            preparedStatement.setString(9,province_added.getText());
            if (!lastname_added.getText().isEmpty()
                    && !name_added.getText().isEmpty()
                    && !username_added.getText().isEmpty()
                    && !password_added.getText().isEmpty()) {

                if (!usernames.contains(username_added.getText())) {
                    resultSet = preparedStatement.executeUpdate();
                    selectUsers();
                    getViews(pnlUsers, btnUsers, imgUsers);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de création");
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez renseigner un autre username, l'identifiant \n " + username_added.getText() + " existe déjà");
                    alert.showAndWait();
                }

            } else {
                if (lastname_added.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de création");
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    alert.setHeaderText(null);
                    alert.setContentText("Le champs \"Nom\" est obligatoire ");
                    alert.showAndWait();
                } else {

                    if (name_added.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de création");
                        // Get the Stage.
                        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                        // Add a custom icon.
                        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                        alert.setHeaderText(null);
                        alert.setContentText(" Le champs \"Prénom\" est obligatoire  ");
                        alert.showAndWait();
                    } else {

                        if (username_added.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                            alert.setTitle("Erreur de création");
                            alert.setHeaderText(null);
                            alert.setContentText(" Le champs \"Nom d'utilisateur\" est obligatoire  ");
                            alert.showAndWait();
                        } else {

                            if (password_added.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                // Get the Stage.
                                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                                // Add a custom icon.
                                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                                alert.setTitle("Erreur de création");
                                alert.setHeaderText(null);
                                alert.setContentText("Le champs \"Mot de passe\" est obligatoire  ");
                                alert.showAndWait();
                            }
                        }
                    }

                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Create a new device
    public void CreateDevice(ActionEvent event) {

        try {
            ArrayList<String> serials = getAllDevices();

        /*String query = " insert into device values ('"+num_serial_id.getText()+"','"+ name_sensor.getText()+"',"
                + interval.getText()+",'"+ariaDescription.getText()+"')";*/


            String query = "insert into device (serial, name, interval, description ,type, user_ID) values ('"
                    + num_serial_id.getText() + "','" + name_sensor.getText() + "' ," + interval.getText() + ", '" + ariaDescription.getText() + "'," + type.getText() + ","
                    + Prevalent.currentOnlineUser.getId() + ")";

            if (!num_serial_id.getText().isEmpty()
                    && !interval.getText().isEmpty()
                    && !type.getText().isEmpty()
            ) {
                if (!serials.contains(num_serial_id.getText())) {
                    preparedStatement = connection.prepareStatement(query);
                    resultSet = preparedStatement.executeUpdate();
                    selectDevices();
                    getViews(pnlDevices, btnDevice, imgDevice);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de création");
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez renseigner un autre numéro de série, l'identifiant  \n " + num_serial_id.getText() + " existe déjà");
                    alert.showAndWait();
                }

            } else {
                if (num_serial_id.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de création");
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    alert.setHeaderText(null);
                    alert.setContentText(" Le champs \"Numéro de série\" est obligatoire  ");
                    alert.showAndWait();
                } else {
                    if (interval.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        // Get the Stage.
                        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                        // Add a custom icon.
                        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                        alert.setTitle("Erreur de création");
                        alert.setHeaderText(null);
                        alert.setContentText("Le champs \"Intervale\" est obligatoirez ");
                        alert.showAndWait();
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//
//            ArrayList <String > serials= getAllDevices();
//
//        /*String query = " insert into device values ('"+num_serial_id.getText()+"','"+ name_sensor.getText()+"',"
//                + interval.getText()+",'"+ariaDescription.getText()+"')";*/
//
//
//            String query = "insert into device (serial, name, interval, description ,type, user_ID) values ('"
//                    +num_serial_id.getText()+"','"+name_sensor.getText()+"' ,"+interval.getText()+", '"+ariaDescription.getText()+"',"+type.getText()+","
//                    + Prevalent.currentOnlineUser.getId()+")";
//
//            if(!num_serial_id.getText().isEmpty()
//                    && !interval.getText().isEmpty()
//                    && !type.getText().isEmpty()
//                    )
//            {
//                if(!serials.contains(num_serial_id.getText())){
//                    preparedStatement = connection.prepareStatement(query);
//                    resultSet = preparedStatement.executeUpdate();
//                    selectDevices();
//                    getViews(pnlDevices, btnDevice, imgDevice);
//
//                }else
//                {
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("Création échouer");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Veuillez changer le champ 'serial' s'il vous plaît \n "+num_serial_id.getText()+ " existe déjà");
//                    alert.showAndWait();
//                }
//
//            }else{
//                if(num_serial_id.getText().isEmpty()){
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("Création échouer");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Veuillez remplir le champ 'Numéro de série' s'il vous plaît ");
//                    alert.showAndWait();
//                }else{
//                    if(interval.getText().isEmpty()){
//                        Alert alert = new Alert(Alert.AlertType.WARNING);
//                        alert.setTitle("Création échouer");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Veuillez remplir le champ ' Intervale ' s'il vous plaît ");
//                        alert.showAndWait();
//                    }
//                }
//            }


//        }catch (Exception e){
//            e.printStackTrace();
//       }

    }

    private ArrayList<String> getAllDevices() {
        ArrayList<String> serials = new ArrayList<>();
        String q = "select serial from device";
        try {
            preparedStatement = connection.prepareStatement(q);
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()) {

                serials.add(r.getString("serial"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return serials;
    }

    boolean validate = false;

    public void getStatisticsSearch(ActionEvent event) throws ParseException {
        // pnlStatistics.getChildren().add(imageView);
        // pnlStatistics.getChildren().add(GetLoadinImage());
        try {
            Stage secondeStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/app/views/Loading.fxml"));
            secondeStage.setResizable(false);
            //primaryStage.setScene(new Scene(root, 1100, 560));
            // secondeStage.initStyle(StageStyle.TRANSPARENT);
            secondeStage.getIcons().add(new Image("/app/images/icon.png"));
            secondeStage.setScene(new Scene(root, 300, 224));
            secondeStage.initModality(Modality.APPLICATION_MODAL);
            secondeStage.setOnCloseRequest(even -> even.consume());
            //APPLICATION_MODAL
            secondeStage.show();
            DrawChart();
            secondeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //loadingImage.setVisible(true);

        return;
    }

    public void UpdateUser(ActionEvent event) {
        try {
            username_updated.setEditable(false);

            boolean checked = false;
            int statutAdmin = 0;
            if (checked) statutAdmin = 1;

            //System.out.println("type"+type_added.getValue());
            int typeAdmin = 0;
            //System.out.println("boool "+type_updated.getValue().equals("Administrateur"));


            if (!name_updated.getText().isEmpty()
                    && !lastname_updated.getText().isEmpty()
                    && !password_updated.getText().isEmpty()
                    && !username_updated.getText().isEmpty()
                    && !province_updated.getText().isEmpty()
                    && !region_updated.getSelectionModel().isEmpty()) {

                String pass = md.getMd5(password_updated.getText());
                String query;
                if (Prevalent.currentOnlineUser.getType() == 1) {
                    if (type_updated.getValue().equals("Administrateur")) {
                        typeAdmin = 1;
                    }
                }
                if (fromTableUser.getType() == 1) {
                    if (type_updated.getValue().equals("Administrateur")) {
                        typeAdmin = 1;
                    }
                }
                query = "Update  user  set lastname = ?, firstname= ?, username=?, password=?,lastconnection =? ,status= ?,type=?, province = ?, region = ? where id =" + fromTableUser.getId() + "";
                if (Prevalent.currentOnlineUser.getType() == 1) {
                    if (fromTableUser.getType() == 1 && !type_updated.getValue().equals("Administrateur")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        // Get the Stage.
                        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                        // Add a custom icon.
                        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                        alert.setContentText(" Est ce que vous voulez changer le type du compte ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1,name_updated.getText());
                            preparedStatement.setString(2,lastname_updated.getText());
                            preparedStatement.setString(3,username_updated.getText());
                            preparedStatement.setString(4,pass);
                            preparedStatement.setString(5,fromTableUser.getLastCctn());
                            preparedStatement.setInt(6,statutAdmin);
                            preparedStatement.setInt(7,typeAdmin);
                            preparedStatement.setString(8,province_updated.getText());
                            preparedStatement.setString(9,String.valueOf(region_updated.getValue()));
                            resultSet = preparedStatement.executeUpdate();

                            getViews(pnlUsers, btnUsers, imgUsers);
                            selectUsers();
                        } else {
                            getViews(pnlUsers, btnUsers, imgUsers);
                            selectUsers();
                        }
                    } else {
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,name_updated.getText());
                        preparedStatement.setString(2,lastname_updated.getText());
                        preparedStatement.setString(3,username_updated.getText());
                        preparedStatement.setString(4,pass);
                        preparedStatement.setString(5,fromTableUser.getLastCctn());
                        preparedStatement.setInt(6,statutAdmin);
                        preparedStatement.setInt(7,typeAdmin);
                        preparedStatement.setString(8,province_updated.getText());
                        preparedStatement.setString(9,String.valueOf(region_updated.getValue()));
                        resultSet = preparedStatement.executeUpdate();

                        getViews(pnlUsers, btnUsers, imgUsers);
                        selectUsers();
                    }
                } else {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,name_updated.getText());
                    preparedStatement.setString(2,lastname_updated.getText());
                    preparedStatement.setString(3,username_updated.getText());
                    preparedStatement.setString(4,pass);
                    preparedStatement.setString(5,fromTableUser.getLastCctn());
                    preparedStatement.setInt(6,statutAdmin);
                    preparedStatement.setInt(7,typeAdmin);
                    preparedStatement.setString(8,province_updated.getText());
                    preparedStatement.setString(9,String.valueOf(region_updated.getValue()));
                    resultSet = preparedStatement.executeUpdate();

                    getViews(pnlUsers, btnUsers, imgUsers);
                    selectUsers();
                }


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de modification");
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setHeaderText(null);
                alert.setContentText("Veuillez renseigner tous les champs s'il vous plaît \n ( vérifier le champ *** Mot de passe *** ) ");
                alert.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    public void UpdateDevice(ActionEvent event) {
        try {
            interval.setEditable(false);
            upstatednum_serial_id.setEditable(false);



            String query = "Update device set  name = ? , description = ? where serial = '" + upstatednum_serial_id.getText() + "';";
            System.out.println("QUERY = " + query);
            System.out.println("idDevice = " + serialNumber);

            if (!upstatednum_serial_id.getText().isEmpty()
                    && !updatedInterval.getText().isEmpty()
                    && !updatedTypeDevice.getText().isEmpty()
                    && !updateName_sensor.getText().isEmpty()
            ) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,updateName_sensor.getText());
                preparedStatement.setString(2,updateAriaDescription.getText());
                int r = preparedStatement.executeUpdate();
                System.out.println(".executeUpdate()= " + r);
                selectDevices();
                //getViews(pnlDevices);
                getViews(pnlDevices, btnDevice, imgDevice);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de modification");
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setHeaderText(null);
                alert.setContentText("Veuillez renseigner tous les champs s'il vous plaît ");
                alert.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void searchUser() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<User> filteredData = new FilteredList<>(users, a -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getFname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (employee.getLname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(employee.getId()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (employee.getUsername().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (employee.getLastCctn().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<User> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        userTable.setItems(sortedData);

    }

    public void addDev(ActionEvent event) {
        //  getViews(pnlAddDevice);
        //getViews(pnlAddDevice, btnDevice, imgDevice);
        getViews(pnlUpload, btnUpload, imgUpload);
        btnUpload.setStyle("-fx-text-fill: " + colorStyle);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajouter un enregistreur");
        // Get the Stage.
        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
        //alert.setContentText("êtes-vous sûr de vouloir supprimer cet élément?");
        alert.setContentText("Veuillez appuyer sur \"Récupérer les enregistrements\" ou \"Récupérer les alerts\" pour ajouter un enregistreur");
        Optional<ButtonType> result1 = alert.showAndWait();
        if (result1.get() == ButtonType.OK) {
        }
    }

    public void SettingDeviceData(ActionEvent event) {

    }

    public void getPnlParametre(ActionEvent event) {
        getViews(pnlSettings, btnDevice, imgDevice);
    }

    public void CloseScreen(ActionEvent actionEvent) {
        // get a handle to the stage
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Stage stage = (Stage) btnCloseApp.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

//    public void DrawChart() throws ParseException {
//        mainChart.setAnimated(false);
//        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
//        series.setName("Température");
//        LocalDate selectedDate = dtpikeri.getValue();
//        String serial = statsSelectDevice.getValue();
//        int id = getIdDevice(serial);
//        String r = "select Min(temperature) as minval , Max(temperature) as maxval , AVG(temperature) as avgval from events where id_device = "
//                + id + " and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 0:0:0' and time <= '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth()+1 + " 00:00:00')";
//        String rqw = "select Min(temperature) as minvalw , Max(temperature) as maxvalw , AVG(temperature) as avgvalw from warnning where id_device = "
//                + id + " and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth()+1  + " 00:00:00')";
//        try {
//            PreparedStatement preparedStatement1 = connection.prepareStatement(r);
//            ResultSet re = preparedStatement1.executeQuery();
//
//            PreparedStatement pw = connection.prepareStatement(rqw);
//            ResultSet rw = pw.executeQuery();
//
//
//            min =  (Math.round(re.getFloat("minval") * 100)) / 100.0;
//            System.out.println("min = "+re.getFloat("minval"));
//            //max
//            max = (Math.round(re.getFloat("maxval") * 100)) / 100.0;
//
//            // avg
//            avg = (int) (Math.round(re.getFloat("avgval") * 100)) / 100.0;
///**
// * *********************************WARNING***************************************************
// */
//            minw =  (Math.round(rw.getFloat("minvalw") * 100)) / 100.0;
//            //max
//            maxw =  (Math.round(rw.getFloat("maxvalw") * 100)) / 100.0;
//
//            // avg
//            avgw =  (Math.round(rw.getFloat("avgvalw") * 100)) / 100.0;
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//
//        String query = "select * from events " +
//                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth()+1  + " 00:00:00')";
//        System.out.println(query);
//        String query1 = "select * from warnning " +
//                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth()+1  + " 00:00:00')";
//        System.out.println(query1);
//
//        PreparedStatement preparedStatement = null;
//        PreparedStatement preparedStatement1 = null;
//
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            preparedStatement1 = connection.prepareStatement(query1);
//            ResultSet resultSet1 = preparedStatement1.executeQuery();
//
//            while (resultSet1.next()) {
//
//
//                String[] tm = resultSet1.getString("Time").toString().split(" ", -2);
//                String time = "";
//                for (String a : tm) time = a;
//
//                series.getData().add(new XYChart.Data("" + time, resultSet1.getFloat("temperature")));
//            }
//            while (resultSet.next()) {
//
//
//                String[] tm = resultSet.getString("Time").toString().split(" ", -2);
//                String time = "";
//                for (String a : tm) time = a;
//
//                series.getData().add(new XYChart.Data("" + time, resultSet.getFloat("temperature")));
//
//            }
//            series.getData().sort(Comparator.comparing(d-> Integer.parseInt(d.getXValue().split(":")[0])));
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//
//        mainChart.getData().clear();
//        //mainChart.getData().add(series);
//        mainChart.getData().add(reformatData(series));
//        /*Scene scene  = new Scene(lineChart,800,600);
//        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
//        lineChart.getData().add(series);
//        stage.setScene(scene);
//        stage.show();*/
//
//        /**
//         * Browsing through the Data and applying ToolTip
//         * as well as the class on hover
//         */
//
//
//        for (XYChart.Series<String, Number> s : mainChart.getData()) {
//            for (XYChart.Data<String, Number> d : s.getData()) {
//                Tooltip.install(d.getNode(), new Tooltip(
//                        "Date / Heure : " + d.getXValue().toString() + "\n" +
//                                "Température : " + d.getYValue().toString() + " °c"));
//
//                try {
//                    //  d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
//
//                    //Removing class on exit
//                    // d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                //Adding class on hover
//
//            }
//        }
//    }
    HashMap<String, Number> sortedList ;
    public void DrawChart() throws ParseException {

            System.out.println("timeI"+timepikerI.getValue());
            System.out.println("timeI"+timepikerF.getValue());


        mainChart.setAnimated(false);
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        sortedList = new HashMap<>();
        series.setName("Température");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        LocalDate selectedDate = dtpikeri.getValue();
        String selectedDateF = formatter.format(selectedDate.plusDays(1));
            String selectedDateI = formatter.format(selectedDate);
        System.out.println("selectedDateF= " + selectedDateF);
        String serial = statsSelectDevice.getValue();
        int id = getIdDevice(serial);
//    String r = "select Min(temperature) as minval , Max(temperature) as maxval ,sum(temperature) as sum ,count(temperature) as nbpointE from events where id_device = "
//                    + id + " and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time <= '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.plusDays(1).getDayOfMonth()  + " 00:00:00')";
        String r = "select Min(temperature) as minval , Max(temperature) as maxval ,sum(temperature) as sum ,count(temperature) as nbpointE from events where id_device = "
                + id + " and (time >='"
                + selectedDateI+ " "+timepikerI.getValue()+":00' and time <= '"
                + selectedDateI + " "+timepikerF.getValue()+":00')";
    System.out.println(r);
//    String rqw = "select Min(temperature) as minvalw , Max(temperature) as maxvalw , sum(temperature) as sumw, count(temperature) as nbpointW   from warnning where id_device = "
//                    + id + " and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.plusDays(1).getDayOfMonth()  + " 00:00:00')";
        String rqw = "select Min(temperature) as minvalw , Max(temperature) as maxvalw , sum(temperature) as sumw, count(temperature) as nbpointW   from warnning where id_device = "
                + id + " and (time >='"
                + selectedDateI+ " "+timepikerI.getValue()+":00' and time < '"
                +selectedDateI + " "+timepikerF.getValue()+":00')";

    System.out.println(rqw);
    try {
        PreparedStatement preparedStatement1 = connection.prepareStatement(r);
        ResultSet re = preparedStatement1.executeQuery();

        PreparedStatement pw = connection.prepareStatement(rqw);
        ResultSet rw = pw.executeQuery();

        min = (Math.round(re.getFloat("minval") * 100)) / 100.0;
        System.out.println("min = " + re.getFloat("minval"));
        //max
        max = (Math.round(re.getFloat("maxval") * 100)) / 100.0;
        System.out.println("max " + max);
        // avg

        sum = (Math.round(re.getFloat("sum") * 100)) / 100.0;
/**
 * *********************************WARNING***************************************************
 */
        minw = (Math.round(rw.getFloat("minvalw") * 100)) / 100.0;

        //max
        maxw = (Math.round(rw.getFloat("maxvalw") * 100)) / 100.0;
        System.out.println("maxw " + maxw);
        //sum
        sumw = (Math.round(rw.getFloat("sumw") * 100)) / 100.0;
        // avg
        NbPoints = rw.getInt("nbpointW") + re.getInt("nbpointE");
        if(NbPoints!=0)
            avg = (sum+sumw)/NbPoints;
        avg = (Math.round(avg * 100)) / 100.0;;
        System.out.println("avgw " + avg);
        System.out.println("sum " + sum);
        System.out.println("sumw " + sumw);
        System.out.println("NbPoints " + NbPoints);

    } catch (Exception throwables) {
        throwables.printStackTrace();
    }

    //01-16-2021

//        String query = "select * from events " +
//                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.plusDays(1).getDayOfMonth()  + " 00:00:00')";
        String query = "select * from events " +
                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
                + selectedDateI+ " "+timepikerI.getValue()+":00' and time < '"
                + selectedDateI+ " "+timepikerF.getValue()+":00')";
        System.out.println(query);
//        String query1 = "select * from warnning " +
//                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.getDayOfMonth() + " 00:00:00' and time < '"
//                + selectedDate.getYear() + "-" + selectedDate.getMonthValue() + "-"
//                + selectedDate.plusDays(1).getDayOfMonth()  + " 00:00:00')";
        String query1 = "select * from warnning " +
                "where id_device IN  (SELECT id from device where serial = '" + serial + "') and (time >='"
                + selectedDateI+ " "+timepikerI.getValue()+":00' and time < '"
                + selectedDateI + " "+timepikerF.getValue()+":00')";
        System.out.println(query1);

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;

    try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            preparedStatement1 = connection.prepareStatement(query1);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while (resultSet1.next()) {


                String[] tm = resultSet1.getString("Time").toString().split(" ", -2);
                String time = "";
                for (String a : tm) time = a;

                series.getData().add(new XYChart.Data("" + time, resultSet1.getFloat("temperature")));
                sortedList.put("" + time,resultSet1.getFloat("temperature"));


            }
            while (resultSet.next()) {


                String[] tm = resultSet.getString("Time").toString().split(" ", -2);
                String time = "";
                for (String a : tm) time = a;

                series.getData().add(new XYChart.Data("" + time, resultSet.getFloat("temperature")));
                System.out.println("time+++ " +time);
               // sortedList.put("" + time,resultSet1.getFloat("temperature"));

            }
            //series.getData().sort(Comparator.comparing(d-> Integer.parseInt(d.getXValue().split(":")[0])));
        SimpleDateFormat formatter1=new SimpleDateFormat("HH:mm:ss");

        series.getData().sort(Comparator.comparing(d -> {
            Date date1 = null;
            try {
                date1 = formatter1.parse(d.getXValue());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1;
        }));

//            XYChart.Series<Date, Number> testSeries = new XYChart.Series<>();
//            series.getData().sort(Comparator.comparing(d-> d.getXValue()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    mainChart.getData().clear();
    //mainChart.getData().add(series);
    mainChart.getData().add(reformatData(series));
        /*Scene scene  = new Scene(lineChart,800,600);
        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        lineChart.getData().add(series);
        stage.setScene(scene);
        stage.show();*/

    /**
     * Browsing through the Data and applying ToolTip
     * as well as the class on hover
     */


    for (XYChart.Series<String, Number> s : mainChart.getData()) {
        for (XYChart.Data<String, Number> d : s.getData()) {
            Tooltip.install(d.getNode(), new Tooltip(
                    "Date / Heure : " + d.getXValue().toString() + "\n" +
                            "Température : " + d.getYValue().toString() + " °c"));

            try {
                //  d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                // d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Adding class on hover

        }
    }
}

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            if (pageCount == 0) {
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 770);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setNonStrokingColor(229, 35, 66);
                contentStream.showText("     Liste des événements enregistrés ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.endText();
            }

            String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream, pageCount == 0 ? true : false);
        }
    }

    public void drawTableW(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            if (pageCount == 0) {
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 770);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setNonStrokingColor(229, 35, 66);
                contentStream.showText("     Liste des alarmes enregistrées ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.endText();
            }

            String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream, pageCount == 0 ? true : false);
        }
    }

    public void drawTableWarning(PDDocument doc, Table table, Table table2) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePageW(doc, table, table2);
            PDPageContentStream contentStream = generateContentStreamW(doc, page, table, table2);
            if (pageCount == 0) {
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 770);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setNonStrokingColor(229, 35, 66);
                contentStream.showText("     Les statistiques d'alarme ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.endText();
            }

            String[][] currentPageContent = getContentForCurrentPageW(table, rowsPerPage, pageCount);
            String[][] currentPageContent2 = getContentForCurrentPageW(table2, rowsPerPage, pageCount);
            drawCurrentPageW(table, table2, currentPageContent, currentPageContent2, contentStream, pageCount == 0 ? true : false);


        }
    }

    //résumé de la journalisation
    public void drawTableLogging(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            if (pageCount == 0) {
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 770);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setNonStrokingColor(229, 35, 66);
                contentStream.showText("     Résumé des enregistrements ");
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.endText();
            }

            String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPageL(table, currentPageContent, contentStream, pageCount == 0 ? true : false);
        }
    }

    // Draws current page table grid and border lines and content
    private void drawCurrentPageL(Table table, String[][] currentPageContent, PDPageContentStream contentStream, Boolean isFirst)
            throws IOException {
        float tableTopY = isFirst ? 750 : table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        for (int i = 0; i < currentPageContent.length; i++) {
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        contentStream.close();
    }

    private void drawCurrentPage(Table table, String[][] currentPageContent, PDPageContentStream contentStream, Boolean isFirst)
            throws IOException {
        float tableTopY = isFirst ? 750 : table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        for (int i = 0; i < currentPageContent.length; i++) {
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        contentStream.close();
    }

    private void drawCurrentPageW(Table table, Table table2, String[][] currentPageContent, String[][] currentPageContent2, PDPageContentStream contentStream, Boolean isFirst)
            throws IOException {
        float tableTopY = isFirst ? 750 : table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);
        drawTableGrid(table2, currentPageContent2, contentStream, tableTopY - 150);
        System.out.println("tableTopY " + tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();
        System.out.println("nextTextY" + nextTextY);
        // Write content
        System.out.println("currentPageContent.length" + currentPageContent.length);
        for (int i = 0; i < currentPageContent.length; i++) {
            writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }
        /**
         * ***********************************************
         */
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(50, 620);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.setNonStrokingColor(229, 35, 66);
        contentStream.showText("    Résumé des enregistrements ");
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.setNonStrokingColor(0, 0, 0);
        contentStream.endText();
        float nextTextX1 = table2.getMargin() + table2.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY1 = tableTopY - (table2.getRowHeight() / 2)
                - ((table2.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table2.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table2.getColumnsNamesAsArray(), contentStream, nextTextX1, nextTextY1 - 150, table2);
        nextTextY1 -= table2.getRowHeight();
        nextTextX1 = table2.getMargin() + table2.getCellMargin();
        System.out.println("nextTextY1" + nextTextY1);
        // Write content
        System.out.println("currentPageContent2.length" + currentPageContent2.length);
        for (int i = 0; i < currentPageContent2.length; i++) {
            writeContentLine(currentPageContent2[i], contentStream, nextTextX1, nextTextY1 - 150, table2);
            nextTextY1 -= table2.getRowHeight();
            nextTextX1 = table2.getMargin() + table2.getCellMargin();

        }
        contentStream.close();
    }

    // Writes the content for one line
    private void writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
                                  Table table) throws IOException {
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent[i];
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(nextTextX, nextTextY);
            contentStream.drawString(text != null ? text : "");
            contentStream.endText();
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

    private void drawTableGrid(Table table, String[][] currentPageContent, PDPageContentStream contentStream, float tableTopY)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        for (int i = 0; i <= currentPageContent.length + 1; i++) {
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            nextY -= table.getRowHeight();
        }

        // Draw column lines
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.length);
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private String[][] getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private String[][] getContentForCurrentPageW(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPage generatePageW(PDDocument doc, Table table, Table table2) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setMediaBox(table2.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        page.setRotation(table2.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }

    private PDPageContentStream generateContentStreamW(PDDocument doc, PDPage page, Table table, Table table2) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        if (table2.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table2.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(table2.getTextFont(), table2.getFontSize());
        return contentStream;
    }


    public void generatePdf(ActionEvent event) throws IOException, COSVisitorException, InterruptedException {
        try {

            try {
                Stage secondeStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/app/views/Loading.fxml"));
                secondeStage.getIcons().add(new Image("/app/images/icon.png"));
                secondeStage.setResizable(false);
                //primaryStage.setScene(new Scene(root, 1100, 560));
                // secondeStage.initStyle(StageStyle.TRANSPARENT);
                secondeStage.setScene(new Scene(root, 300, 224));
                secondeStage.initModality(Modality.APPLICATION_MODAL);
                secondeStage.setOnCloseRequest(even -> even.consume());
                //APPLICATION_MODAL
                secondeStage.show();

                final float rowHeight = 20f;
                float margin = 100;

                //à remplacer par les données de BDD ##.#
                //String y = (s > 120) ? "Slow Down" : "Safe";
                double minVal = 0.0;
                if (max == 0 && min == 0 )
                    minVal = minw;
                else if (maxw == 0 && minw == 0 )
                    minVal = min;
                else
                    minVal = Math.min(minw, min);
                System.out.println("minVal"+minVal);
                //final double maxVal = (max> maxw)? max:maxw;
                double maxVal = 0.0;
                if (max == 0 && min == 0)
                    maxVal = maxw;
                else if (maxw == 0 && minw == 0 )
                    maxVal = max;
                else
                    maxVal = Math.max(maxw, max);
                System.out.println("maxVal"+maxVal);
                double avgVal = avg;
                System.out.println("avg = "+avg);
                //Générer l'image du graphique à partir de la scène
                mainChart.setCreateSymbols(false);
                DrawChart();
                // if (validate) {
                //document = PDDocument.load(new File(FolderPath));
                //Adding the blank page to the document
                PDDocument document = new PDDocument();
                //Creating a blank page
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                System.out.println("Begin");

                // PDImageXObject LogoMinistre = PDImageXObject.createFromFile(FolderPath + "minterieur.png", document);
                PDImageXObject LogoSociete = PDImageXObject.createFromFile(FolderPath + "msante.png", document);
                PDImageXObject iconReport = PDImageXObject.createFromFile(FolderPath + "statistics_active.png", document);
                PDImageXObject ReportEnd = PDImageXObject.createFromFile(FolderPath + "report_end.png", document);
                System.out.println("Drawing Images");
                // contentStream.drawImage(LogoMinistre, 440, 710, 60, 60);
                contentStream.drawImage(LogoSociete, 510, 710, 60, 60);
                contentStream.drawImage(iconReport, 50, 642, 20, 24);
                WritableImage image = mainChart.snapshot(new SnapshotParameters(), null);
                saveAsPng(mainChart, FolderPath + "LineChart.png");
                PDImageXObject chart = PDImageXObject.createFromFile(FolderPath + "LineChart.png", document);
                contentStream.drawImage(chart, 10, 350, 560, 280);
                mainChart.setCreateSymbols(true);
                int erh = (int) Math.floor(600 * 0.7);
                contentStream.drawImage(ReportEnd, 0, 20, 600, erh);
                //Begin the Content Text drawing
                contentStream.beginText();
                //contentStream.setLeading(14.5f);


                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

                String Title = "Rapport";
                contentStream.showText(Title);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                //contentStream.newLine();

                // date
                String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Généré le: " + timeStamp);
                contentStream.newLine();
                // contentStream.showText("Généré par: " + Prevalent.currentOnlineUser.getUsername().toUpperCase() ); //+fname.toString()
                contentStream.showText("Généré par: " + Prevalent.currentOnlineUser.getLname().toUpperCase() + " " + Prevalent.currentOnlineUser.getFname().toUpperCase()); //+fname.toString()
                contentStream.newLine();
                String emi = (statsSelectDevice.getValue() != null) ? statsSelectDevice.getValue() : "";
                contentStream.showText("EMI Enregistreur: " + emi);//+ statsSelectDevice.getValue()!=null?statsSelectDevice.getValue().toString():"");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.newLine();
                //contentStream.setStrokingColor(120,100,255);
                String ddate = ":";
                LocalDate dtpikerf = dtpikeri.getValue().plusDays(1);
                if (dtpikeri.getValue() != null) {

                    ddate = "[" + dtpikeri.getValue() + "]:";
                }

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setNonStrokingColor(229, 35, 66);
                contentStream.showText("     Evolution de la température " + ddate);

                for (int i = 0; i < 15; i++) {
                    contentStream.newLine();
                    contentStream.newLine();
                }
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                //contentStream.moveTextPositionByAmount(100, 150);
                contentStream.showText("              " + maxVal + " °C");
                //contentStream.moveTextPositionByAmount(250, 150);
                contentStream.showText("                        " + avgVal + " °C");
                //contentStream.moveTextPositionByAmount(400, 150);
                contentStream.showText("                         " + minVal + " °C");

                contentStream.endText();


                PDPage secondpage = new PDPage();
                // document.addPage(secondpage);
                PDRectangle mediabox = secondpage.getMediaBox();
                float startX = mediabox.getLowerLeftX() + margin;
                float startY = mediabox.getUpperRightY() - margin;
                final int rows = 300;
                int nbRowsPerPage = 20;
                contentStream.close();
                drawTableWarning(document, createContentWarning(page.getMediaBox()), createContentLogging(page.getMediaBox()));
                //drawTableLogging(document, createContentLogging(page.getMediaBox()));

                drawTableW(document, createContentListWarning(page.getMediaBox()));
                drawTable(document, createContent(page.getMediaBox()));
                //close second stage
                secondeStage.close();
                // GetLoadinImage(false);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enregistrer le rapport PDF");
                String nameTs = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
                // getViews(pnlLoading, btnStatistics, imgStatistics);

                fileChooser.setInitialFileName("Nextrack_Rapport_" + nameTs + ".pdf");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));

                Stage stage = (Stage) btnCloseApp.getScene().getWindow();

                document.save(fileChooser.showSaveDialog(stage));

                //getViews(pnlLoading, btnStatistics, imgStatistics);


                System.out.println("PDF created");

                //Closing the document
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //loadingImage.setVisible(false);
    }

    public void saveAsPng(LineChart lineChart, String path) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setTransform(Transform.scale(5, 5));
        WritableImage image = lineChart.snapshot(sp, null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SelectSerialDevices(ActionEvent event) {


        String query = "select serial from device";

        statsSelectDevice.getItems().add("- Sélectionner enregistreur -");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statsSelectDevice.getSelectionModel()
                        .selectedItemProperty()
                        .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                        {
                            try {
                                statsSelectDevice.getItems().add(resultSet.getString("serial"));
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        });

            }
            statsSelectDevice.setValue("- Sélectionner enregistreur -");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    //ActionEvent event
    public void localData() {
        list.removeAll(list);
        try {
            if (btnChoiceDashboard.getItems() != null) btnChoiceDashboard.getItems().clear();
            if (statsSelectDevice.getItems() != null) statsSelectDevice.getItems().clear();
        } catch (Exception e) {
        }
        String query = "select serial from device";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("serial"));
                // System.out.println("list 0 "+list.get(0));
            }

            statsSelectDevice.getItems().addAll(list);
            btnChoiceDashboard.getItems().addAll(list);
            if (!statsSelectDevice.getItems().isEmpty()) statsSelectDevice.setValue(list.get(0));
            if (!btnChoiceDashboard.getItems().isEmpty()) btnChoiceDashboard.setValue(list.get(0));
            Date date = new Date();
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (dtpikeri.getValue() == null) dtpikeri.setValue(localDate);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //statsSelectDevice.getItems().addAll(statsSelectDevice.getValue());
        // statsSelectDevice.getItems().addAll(list);
    }

    boolean chooseDate = false;

    public void checkdate(ActionEvent event) {

        chooseDate = true;
    }

    public void updateDevice(String serialNumber) {

        String query = "select * from device where id =" + getIdDevice(serialNumber) + ";";
        try {
            //lblAddDevice.setText("Modifier un enregistreur");
            ResultSet resultSet = null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            upstatednum_serial_id.setText(serialNumber);
            upstatednum_serial_id.setEditable(false);
            if (resultSet.getInt("user_ID") == Prevalent.currentOnlineUser.getId()) {
                // getViews(pnlUpdateDevice);

                getViews(pnlUpdateDevice, btnupdateDevice, imgUpload);
                btnUpload.setStyle("-fx-text-fill: " + colorStyle);
                //System.out.println("name of sensor");
                updateName_sensor.setText(resultSet.getString("name"));
                updateAriaDescription.setText(resultSet.getString("description"));
                updatedInterval.setText(String.valueOf(getDevice.getInterval()));
                // System.out.println("/////"+serialNumber);
                upstatednum_serial_id.setText(serialNumber);

                updatedTypeDevice.setText(String.valueOf(getDevice.getType()));


            } else {
                System.out.println("Vous n'avez pas les permissions nécessaires pour modifier cet enregistreur ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setTitle("Erreur");
                alert.setContentText("Vous n'avez pas les permissions nécessaires pour modifier cet enregistreur ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // preparedStatement = connection.prepareStatement(query);
                    // resultSet = preparedStatement.executeUpdate();


                }
            }

        } catch (SQLException e) {
        }
    }

    public void getDatawithInput() throws SQLException {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Numéro du conteneur ");
        // Get the Stage.
        Stage d = (Stage) td.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        d.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
        td.setContentText(" Veuillez renseigner l'identifiant du conteneur ");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            valise = td.getEditor().getText();
            //System.out.println(valise);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            // Get the Stage.
            Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
            //alert.setContentText("êtes-vous sûr de vouloir supprimer cet élément?");
            alert.setContentText("Veuillez appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");

            Optional<ButtonType> result1 = alert.showAndWait();
            if (result1.get() == ButtonType.OK) {
                // preparedStatement = connection.prepareStatement(query);
                // resultSet = preparedStatement.executeUpdate();
                getSerialData();
                overlayDataLoading.setVisible(false);
            }

        }


    }

    public void getWarningwithInput() throws Exception {
//        if(event1.size() != 0){
//            event1.clear();
//        }
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Numéro du conteneur ");
        // Get the Stage.
        Stage s = (Stage) td.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
        td.setContentText(" Veuillez renseigner l'identifiant du conteneur ");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            valise = td.getEditor().getText();
            //System.out.println(valise);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            // Get the Stage.
            Stage q = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            q.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
            //alert.setContentText("êtes-vous sûr de vouloir supprimer cet élément?");
            alert.setContentText("Veuillez appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");

            Optional<ButtonType> result1 = alert.showAndWait();
            if (result1.get() == ButtonType.OK) {
                // preparedStatement = connection.prepareStatement(query);
                // resultSet = preparedStatement.executeUpdate();
                getSerialWarnings();
            }


        }

    }

    public void getSerialData() {
        LocalDateTime fromData = LocalDateTime.now();
        System.out.println("fromData" + fromData);

        if (is_firstTime) {
            System.out.println("is_firstTim 1 = " + is_firstTime);
            //AutoDetect
            SerialPort[] portNames = SerialPort.getCommPorts();
            //chosenPort.closePort();
            for (int i = 0; i < portNames.length; i++)
                portName = portNames[i].getSystemPortName();

            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("chosenPort " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);

            if (chosenPort.openPort()) {
                System.out.println(" openPort ? = " + chosenPort.openPort());
                String stringOut = "READ" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            } else {
                portName = "";
                SerialPort[] portNamee = SerialPort.getCommPorts();
                //chosenPort.closePort();
                for (int i = 0; i < portNamee.length; i++)
                    portName = portNamee[i].getSystemPortName();
                chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
                System.out.println("chosenPort " + portName);
                chosenPort.setBaudRate(115200);
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
                String stringOut = "READ" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            }
        } else {
            System.out.println("else is_firstTime = " + is_firstTime);
            chosenPort.closePort();
            portName = "";
            SerialPort[] portNamee = SerialPort.getCommPorts();
            //chosenPort.closePort();
            for (int i = 0; i < portNamee.length; i++)
                portName = portNamee[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("chosenPort " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);

            String stringOut = "READ" + "\n";
            if (chosenPort.openPort()) {
                System.out.println("open = true ");
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
                is_firstTime = true;
            }
        }
        if (is_firstTime) {
            System.out.println("is_firstTim= " + is_firstTime);
            // create a new thread that listens for incoming text and populates the DataSet
            Thread thread = new Thread() {
                @Override
                public void run() {
                    String date = null, time = null, temperature = null;
                    String date1 = null;
                    Scanner scanner = new Scanner(chosenPort.getInputStream());
                    int cnt = 0, cp = 0;
                    while (scanner.hasNextLine()) {
                        System.out.println("scanner.hasNextLine " + scanner.hasNextLine());
                        try {
                            String line = scanner.nextLine();
                            System.out.println("NewLine : " + line);
                            if (line.contains("@")) cnt++;
                            if (cnt < 2) continue;
                            if (line.contains("!")) {
                                overlayDataLoading.setVisible(false);
                                sleep(10);
                                overlayDataTuto.setVisible(true);
                                break;
                            }
                            String[] parts = line.split("\\|", -2);
                            System.out.println("parth " + parts.length);
                            if (parts.length > 3) {
                                continue;
                            }

                            int count = StringUtils.countMatches(line.trim(), ":");
                            if ((parts.length == 2 || parts.length == 1) && count != 5) {
                                continue;
                            }
                            if (count == 5 && line.length() <= 17) {
                                serialNumber = line.trim();
                                System.out.println("serial = " + serialNumber);
                                if (!getAllDevices().contains(serialNumber)) {
                                    System.out.println("please add your device first");
                                    String query = "insert into device (serial, name, interval, description ,type, user_ID) values ('"
                                            + serialNumber + "','" + "---" + "' ," + 0 + ", '" + "--" + "'," + 0 + ","
                                            + Prevalent.currentOnlineUser.getId() + ");";
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.executeUpdate();
                                    updateDevice(serialNumber);
                                    break;
                                }
                                continue;
                            }

                            start = true;
                            System.out.println("start = " + start);
                            overlayDataLoading.setVisible(true);
                            for (String a : parts) {
                                if (a.contains("@")) {
                                    start = true;
                                }
                                //this if not used bcs we test the existence of ! in the line not in each part
                                if (a.contains("!")) {
                                    overlayDataLoading.setVisible(false);
                                    overlayDataTuto.setVisible(true);
                                    pnlUpload.getChildren().get(4).setVisible(false);
                                    start = false;
                                    break;
                                }
                                if (!a.contains("@") && !a.contains("!") && start) {
                                    try {
                                        overlayDataTuto.setVisible(false);
                                        if (j == 0) {
                                          //  date
                                            date = a.trim().replace("/", "-");
                                            System.out.println(" j=0 " + date);

                                            boolean bDate = parseDate(date, "yyyy-m-d");
                                            if (!bDate) continue;
                                        }
                                        if (j == 1) {
                                            //time
                                            time = a.trim();
                                            System.out.println("j=1 " + time);
                                            boolean bTime = parseTime(time, "H:m:s");//
                                            if (!bTime) continue;
                                        }
                                        if (j == 2) {
                                            temperature = a.trim();
                                            System.out.println("j=2 " + temperature);
                                            if(Float.parseFloat(temperature)==-127|| temperature.equals(-127.00))continue;

                                        }

                                        j++;
                                        if (j == 3) {
                                            j = 0;
                                            if (!time.isEmpty() && !date.isEmpty() && !temperature.isEmpty()) {
                                                //System.out.println("cp " + cp);
                                               // System.out.println("day1 " + date1);
                                               //event1.get(cp).setTemperature(Float.parseFloat(temperature));
                                                Timestamp t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time).getTime());
                                                //Timestamp t = Timestamp.valueOf(day.toString() + " " + time);
                                                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getTime());
                                                System.out.println(" date " + date);
                                                System.out.println(" time " + time);
                                                System.out.println(" t " + t);
/**
 * *************************************************************************
 */
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(t);
                                                System.out.println("calendar "+calendar.getTime());
                                               //calendar.add(Calendar.MINUTE, 1920);
                                                //calendar.add(Calendar.MINUTE, 19500); 13J +13H

                                                System.out.println("calendarAffterAdd "+calendar.getTime());
                                                //System.out.println(" date1 "+date1);
                                                Date day =   calendar.getTime();
                                                System.out.println("day "+day);
//                                                date1 = formatter.format(day);
//                                                System.out.println(" date1 "+date1);
                                                int year       = calendar.get(Calendar.YEAR);
                                                int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
                                                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                                int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
                                                int minute     = calendar.get(Calendar.MINUTE);
                                                int second     = calendar.get(Calendar.SECOND);
                                                String dt = year+"-"+(month+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute+":"+second;

                                                Date dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt);
                                                System.out.println("dt1 "+dt1);
/**
 *****************************************************************************************************************************
 */
                                                Instant instant = calendar.getTime().toInstant();
                                                System.out.println("instant " + instant);
                                                Event e = new Event(Float.parseFloat(temperature),instant);
                                                event1.add(e);
                                                String d = getDateLastSave(serialNumber);
                                                System.out.println("d = "+d);
                                                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date lastDate = formatter.parse(d);
                                                System.out.println("lastDate  " + lastDate);
                                                //System.out.println("timeeeee " + time);
                                                Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt );
                                                System.out.println("currentDate  " + currentDate);
                                                System.out.println("+contains serial+ " + getAllDevices().contains(serialNumber));
                                                System.out.println("+currentDate+ " + currentDate.after(lastDate));
                                                if (getAllDevices().contains(serialNumber) && (currentDate.after(lastDate) || currentDate== lastDate)) {//
                                                    try {
                                                        String query = "";
                                                        if(Float.parseFloat(temperature)<=2 || Float.parseFloat(temperature)>=8 ){
                                                            query = "insert into warnning (temperature,Time,id_device,record_ID,valiseId) values ("
                                                                + Float.parseFloat(temperature) +
                                                                ",'" + timeStamp + "',"
                                                                + getIdDevice(serialNumber)
                                                                + "," + 1 + ",'"
                                                                + valise + "') ";
                                                        }
                                                        else{
                                                            query = "insert into events (temperature,Time,id_device,record_ID,valiseId) values ("
                                                                    + Float.parseFloat(temperature) +
                                                                    ",'" + timeStamp + "',"
                                                                    + getIdDevice(serialNumber)
                                                                    + "," + 1 + ",'"
                                                                    + valise + "') ";
                                                        }
                                                        // System.out.println("query" + query);
                                                        // System.out.println("1111");
                                                        preparedStatement = connection.prepareStatement(query);
                                                        //System.out.println("2222 into events");
                                                        resultSet = preparedStatement.executeUpdate();
                                                        //System.out.println("33333 into events");
                                                    } catch (Exception aa) {
                                                        aa.printStackTrace();
                                                    }
                                                }
                                            }
                                            else continue;
                                        }
                                        index++;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cp++;
                    }
                    scanner.close();
                }
            };
            thread.start();
            overlayDataLoading.setVisible(false);
        }
        is_firstTime = false;
        InsertEvent();
        overlayDataLoading.setVisible(false);
        //is_firstTime = false;
        LocalDateTime toData = LocalDateTime.now();
        System.out.println("toData " + toData);
        duration = ChronoUnit.MILLIS.between(fromData, toData);
    }

    public void getSerialWarnings() throws Exception{

        LocalDateTime fromData = LocalDateTime.now();
        System.out.println("fromData" + fromData);

        if (is_firstTime) {
            System.out.println("is_firstTim 1 = " + is_firstTime);
            //AutoDetect
            SerialPort[] portNames = SerialPort.getCommPorts();
            //chosenPort.closePort();
            for (int i = 0; i < portNames.length; i++)
                portName = portNames[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("chosenPort " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            if (chosenPort.openPort()) {
                System.out.println(" openPort ? = " + chosenPort.openPort());
                String stringOut = "READ WARNING" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            } else {
                portName = "";
                chosenPort.closePort();
                SerialPort[] portNamee = SerialPort.getCommPorts();
                //chosenPort.closePort();
                for (int i = 0; i < portNamee.length; i++)
                    portName = portNamee[i].getSystemPortName();
                chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
                System.out.println("chosenPort " + portName);
                chosenPort.setBaudRate(115200);
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
                String stringOut = "READ WARNING" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            }
        } else {
            System.out.println("else is_firstTime = " + is_firstTime);
            /*chosenPort.clearDTR();
            chosenPort.setRTS();*/
            portName = "";
            chosenPort.closePort();
            SerialPort[] portNamee = SerialPort.getCommPorts();
            //chosenPort.closePort();
            for (int i = 0; i < portNamee.length; i++)
                portName = portNamee[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("chosenPort " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            String stringOut = "READ WARNING" + "\n";
            chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            if (chosenPort.openPort()) {
                System.out.println("openPort == true" );
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
                is_firstTime = true;
            }
        }
        if (is_firstTime) {
            System.out.println("is_firstTim= " + is_firstTime);
            // create a new thread that listens for incoming text and populates the DataSet
            Thread threadW = new Thread() {

                @Override
                public void run() {
                    String date = null, time = null, temperature = null;
                    Scanner scanner = new Scanner(chosenPort.getInputStream());
                    //System.out.println(chosenPort.getBaudRate());
                    int cnt = 0, cp = 0;
                    while (scanner.hasNextLine()) {
                        System.out.println("scanner.hasNextLine " + scanner.hasNextLine());
                        try {
                            String line = scanner.nextLine();
                            System.out.println("NewLine : " + line);
                            if (line.contains("@")) cnt++;
                            if (cnt < 2) continue;
                            if (line.contains("!")) {
                                overlayDataLoading.setVisible(false);
                                sleep(10);
                                overlayDataTuto.setVisible(true);
                                break;
                            }


                            String[] parts = line.split("\\|", -2);
                            System.out.println("parth " + parts.length);
                            if (parts.length > 3) {
                                continue;
                            }
                            int count = StringUtils.countMatches(line.trim(), ":");
                            if ((parts.length == 2 || parts.length == 1) && count != 5) {
                                continue;
                            }
                            //System.out.println("11");
                            if (count == 5 && line.length() <= 17) {
                              //  System.out.println("11-1");
                                serialNumber = line.trim();
                                System.out.println("serial = " + serialNumber);
                                // System.out.println("serial "+serialNumber);
                                if (!getAllDevices().contains(serialNumber)) {
                                    System.out.println("please add your device first");
                                    //num_serial_id.setText(serialNumber);
                                    String query = "insert into device (serial, name, interval, description ,type, user_ID) values ('"
                                            + serialNumber + "','" + "---" + "' ," + 0 + ", '" + "--" + "'," + 0 + ","
                                            + Prevalent.currentOnlineUser.getId() + ");";
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.executeUpdate();
                                    //int id = getIdDevice(serialNumber);
                                    updateDevice(serialNumber);
                                    //overlayDataLoading.setVisible(false);
                                    break;
                                }
                                continue;

                            }
                            //System.out.println("33");


                            start = true;
                            System.out.println("start = " + start);
                            overlayDataLoading.setVisible(true);
                            for (String a : parts) {
                                if (a.contains("@")) {
                                    start = true;
                                   // System.out.println("44");
//                                    overlayDataLoading.setVisible(true);
//                                    overlayDataTuto.setVisible(false);
                                }
                                //this if not used bcs we test the existence of ! in the line not in each part
                               // System.out.println("55");
                                if (a.contains("!")) {

                                    // overlayDataTuto.setVisible(true);
                                    overlayDataLoading.setVisible(false);
                                    overlayDataTuto.setVisible(true);
                                    // overlayDataLoading.getChildren().clear();
                                    pnlUpload.getChildren().get(4).setVisible(false);
                                    start = false;
                                    break;
                                    //overlayDataLoading.setVisible(false);
                                }
                                //System.out.println("66");
                                if (!a.contains("@") && !a.contains("!") && start) {
                                    try {
                                       // System.out.println("77");
                                        overlayDataTuto.setVisible(false);
                                        // System.out.println("Serial "+serialNumber);
                                        if (j == 0) {
                                          //  System.out.println("77-1");
                                            date = a.trim().replace("/", "-");
                                            System.out.println("j=0 " + date);
                                            boolean bDate = parseDate(date, "yyyy-m-d");
                                            if (!bDate) continue;
                                        }
                                        if (j == 1) {
                                          //  System.out.println("77-2");
                                            time = a.trim();
                                            System.out.println("j=1 " + time);
                                            boolean bTime = parseTime(time, "H:m:s");//
                                            if (!bTime) continue;
                                        }
                                        if (j == 2) {
                                           // System.out.println("77-3");
                                            temperature = a.trim();
                                            System.out.println("j=2 " + temperature);
                                            if(Float.parseFloat(temperature)==-127|| temperature.equals(-127.00))continue;
                                        }
                                       // System.out.println("77-4");
                                        j++;
                                        if (j == 3) {
                                            j = 0;
                                            Timestamp t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time).getTime());
                                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getTime());
                                            //Timestamp t = Timestamp.valueOf(day.toString() + " " + time);
                                            System.out.println(" timeStamp " + timeStamp);
                                            System.out.println(" date " + date);
                                            System.out.println(" time " + time);
                                            System.out.println(" t " + t);
/**
 * *************************************************************************
 */
                                            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(t);
                                            System.out.println("calendar "+calendar.getTime());
                                            //calendar.add(Calendar.MINUTE, 19500);13J+13H

                                           // System.out.println("calendarAffterAdd "+calendar.getTime());
                                            //System.out.println(" date1 "+date1);
                                           // Date day =   calendar.getTime();
                                            //System.out.println("day "+day);
//                                                date1 = formatter.format(day);
//                                                System.out.println(" date1 "+date1);
                                            int year       = calendar.get(Calendar.YEAR);
                                            int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
                                            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                            int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
                                            int minute     = calendar.get(Calendar.MINUTE);
                                            int second     = calendar.get(Calendar.SECOND);
                                            String dt = year+"-"+(month+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute+":"+second;

                                           Date dt1 = formatter.parse(dt);
                                            System.out.println("dt1 "+dt1);
/**
 *
 */
                                            Instant instant = calendar.getTime().toInstant();

                                            //event1.get(cp).setTimeEvent(instant);
                                            // System.out.println("event1.get(cp).setTimeEvent1.get(cp).setTimeEvent(instant) "+event1.get(cp).getTimeEvent());
                                            Event e = new Event(Float.parseFloat(temperature),instant);
                                            event1.add(e);

                                            String d = getDateLastSave(serialNumber);
                                            //System.out.println("d = "+d);
                                            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            Date lastDate = formatter.parse(d);
                                            //System.out.println("lastDate  " + lastDate);
                                            //System.out.println("timeeeee " + time);
                                            Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt );



                                           // System.out.println("+contains serial+ " + getAllDevices().contains(serialNumber));
                                           // System.out.println("+currentDate+ " + currentDate.after(lastDate));
                                            if (getAllDevices().contains(serialNumber) && currentDate.after(lastDate)) {//
                                                try {
                                                   // System.out.println("0000000 insert into warning ");
                                                    String query = "insert into warnning (temperature,Time,id_device,record_ID,valiseId) values ("
                                                            + Float.parseFloat(temperature) +
                                                            ",'" + timeStamp+ "',"
                                                            + getIdDevice(serialNumber)
                                                            + "," + 1 + ",'"
                                                            + valise + "') ;";
                                                   // System.out.println("111111 insert into warning ");
                                                    preparedStatement = connection.prepareStatement(query);
                                                    //System.out.println("22222222 insert into warning ");
                                                    resultSet = preparedStatement.executeUpdate();
                                                    //System.out.println("3333333 insert into warning ");
                                                } catch (Exception aa) {
                                                    aa.printStackTrace();
                                                }
                                            }

                                            index++;
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cp++;
                    }
                    scanner.close();
                }
            };

            threadW.start();
            overlayDataLoading.setVisible(false);
        }
        is_firstTime = false;
        InsertWarnning();
        overlayDataLoading.setVisible(false);
        //is_firstTime = false;
        LocalDateTime toData = LocalDateTime.now();
        System.out.println("toData " + toData);
        duration = ChronoUnit.MILLIS.between(fromData, toData);
    }


    public void InsertEvent() {
        try {
            for (int i = 0; i < event1.size(); i++) {
                Date time = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(String.valueOf(Timestamp.from(event1.get(i).getTimeEvent())));
                System.out.println("insrtEvent: "+time);
                String query = "";
                if( event1.get(i).getTemperature() >= 8 || event1.get(i).getTemperature() <=2 ) {
                    query = "insert into warnning (temperature,Time,id_device,record_ID,valiseId) values (" + event1.get(i).getTemperature()
                            + ",'" + time + "'," + getIdDevice(serialNumber) + "," + 1 + ",'" + valise + "');";
                }else{
                    query = "insert into events (temperature,Time,id_device,record_ID,valiseId) values (" + event1.get(i).getTemperature()
                            + ",'" + time + "'," + getIdDevice(serialNumber) + "," + 1 + ",'" + valise + "');";
                }
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertWarnning() throws SQLException {
        try {
            for (int i = 0; i < event1.size(); i++) {
                Date time = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(String.valueOf(Timestamp.from(event1.get(i).getTimeEvent())));
                //Timestamp time = Timestamp.from(event1.get(i).getTimeEvent());
                String query = "insert into warnning (temperature,Time,id_device,record_ID,valiseId) values (" + event1.get(i).getTemperature()
                        + ",'" + time + "'," + getIdDevice(serialNumber) + "," + 1 + ",'" + valise + "');";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getEventsLastSave(String serial) {
        String query = "select time from events  where id_device =' " + getIdDevice(serial) + "' ORDER BY id DESC LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
            // System.out.println("time"+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }
    private String getWarningLastSave(String serial) {
        String query = "select time from warnning  where id_device =' " + getIdDevice(serial) + "' ORDER BY id DESC LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
            // System.out.println("time"+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }
    private String getEventsFirstSave(String serial) {
        String query = "select time from events  where id_device =' " + getIdDevice(serial) + "' ORDER BY id Asc LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
            // System.out.println("time"+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }

    private String getWarningFirstSave(String serial) {
        String query = "select time from warnning  where id_device =' " + getIdDevice(serial) + "' ORDER BY id Asc LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
            // System.out.println("time"+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }

    private String getevLastSave(String serial) {
        String query = "select time from events  where id_device =' " + getIdDevice(serial) + "' ORDER BY id DESC LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
            // System.out.println("time"+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }

    private String getDateLastSave(String serial) {
        String query = "select time from events  where id_device =' " + getIdDevice(serial) + "' ORDER BY id DESC LIMIT 1;  ";
        ResultSet resultSet = null;
        String time = "0000-00-00 00:00:00";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                time = resultSet.getString("time");
                System.out.println("time ** "+time);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return time;
    }

    public void getDeviceToSelect(ActionEvent event) {
        //localData();
    }

    public void movechart(MouseEvent mouseEvent) throws ParseException {
        //System.out.println(btnmove.getValue());
        panRange = btnmove.getValue();
        DrawChart();
    }

    public void zoomchart(MouseEvent mouseEvent) throws ParseException {
        //System.out.println(btnzoom.getValue());
        zoomChart = btnzoom.getValue();
        btnmove.setValue(0);
        panRange = 0;
        DrawChart();
    }

    public XYChart.Series<String, Number> reformatData(XYChart.Series<String, Number> series) {
        //series.getNode()
        int ss = series.getData().size();
        System.out.println((ss - 1));
        double zm = zoomChart >= (((double) ss - 1) / (double) ss) * 100.0 ? (((double) ss - 1) / (double) ss) * 100.0 : zoomChart;
        double pn = panRange >= (((double) ss - 1) / (double) ss) * 100.0 ? (((double) ss - 1) / (double) ss) * 100.0 : panRange;
        int c = (int) (ss - (ss * (zm / 100)) + 2);
        System.out.println(c);
        //double pansize=(dataSize-(dataSize*(panRange/100))-2);
        int x = (int) (ss * (pn / 100));
        System.out.println(x);
        // double fract=dataSize!=0?series.getData().size()/dataSize:0;
        int y = (x + c);
        System.out.println(y);
        // if((reformattedDs+(int)dataSize)!=series.getData().size())
        series.getData().remove(y, ss);
        series.getData().remove(0, x);
        // series.getData().remove((int)(reformattedDs+dataSize),series.getData().size());
        return series;
    }

    public int getIdDevice(String serial) {
        String query = "select id from device where serial = '" + serial + "'";
        int id = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("id");
            //System.out.println("**********"+id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public HBox getDesign(String url, String txt, String time, String temp) {
        HBox mhbox = new HBox();
        mhbox.setAlignment(Pos.CENTER_LEFT);
        mhbox.prefHeight(71);
        mhbox.setFillHeight(true);
        mhbox.prefWidth(310);
        mhbox.setStyle("-fx-background-color: #ffffff");
        // Style sheet
        HBox shbox = new HBox();
        shbox.setAlignment(Pos.CENTER);
        shbox.prefHeight(54);
        shbox.prefWidth(38);

        ImageView tempiv = new ImageView();
        tempiv.setFitHeight(34);
        tempiv.setFitWidth(32);
        tempiv.setPickOnBounds(true);
        tempiv.setPreserveRatio(true);
        tempiv.setImage(new Image(url));

        shbox.getChildren().add(tempiv);
        mhbox.getChildren().add(shbox);

        VBox fvbox = new VBox();
        fvbox.setAlignment(Pos.CENTER_LEFT);
        fvbox.prefHeight(54);
        fvbox.prefWidth(203);

        Label flbl = new Label();
        flbl.setPrefHeight(25);
        flbl.setPrefWidth(171);
        flbl.setFont(new Font("System Bold", 14));
        flbl.setText(txt);

        fvbox.getChildren().add(flbl);

        Label slbl = new Label();
        slbl.setPrefHeight(21);
        slbl.setPrefWidth(171);
        //slbl.setFont(new Font("System Bold", 14));
        slbl.setStyle("-fx-text-fill: #8e8e8e");
        slbl.setText(time);
        fvbox.getChildren().add(slbl);


        mhbox.getChildren().add(fvbox);

        HBox thbox = new HBox();
        thbox.setAlignment(Pos.CENTER_RIGHT);
        thbox.prefHeight(54);
        thbox.prefWidth(61);

        Label tlbl = new Label();
        tlbl.setPrefHeight(54);
        tlbl.setPrefWidth(70);
        tlbl.setAlignment(Pos.CENTER_RIGHT);
        tlbl.setContentDisplay(ContentDisplay.CENTER);
        tlbl.setFont(new Font("System Bold", 18));
        tlbl.setText(temp);

        thbox.getChildren().add(tlbl);

        ImageView civ = new ImageView();
        civ.setFitWidth(11);
        civ.setFitHeight(21);
        civ.setPreserveRatio(true);
        civ.setPickOnBounds(true);
        civ.setImage(new Image("/app/images/c_sign.png"));
        thbox.getChildren().add(civ);

        mhbox.getChildren().add(thbox);

        return mhbox;

    }

    public void getLastTemperature() throws SQLException, ParseException {
        VboxlastTeperature.getChildren().clear();
        int count = 10;
        String serialTemperature = "";
        if (btnChoiceDashboard.getValue() != null) serialTemperature = btnChoiceDashboard.getValue().toString();
        String query = "SELECT * FROM warnning e " +
                "INNER JOIN device d  on e.id_device = d.id  where d.serial ='" + serialTemperature +
                "' ORDER BY ID DESC LIMIT " + count + ";";
        PreparedStatement p = connection.prepareStatement(query);
        ResultSet res = p.executeQuery();
        HBox hBox = null;
        ;
        HBox pn;
        ImageView iv;

        int y = 0;
        while (res.next()) {
            pn = new HBox();
            iv = new ImageView();
            iv.setFitHeight(24);
            iv.setFitWidth(310);
            pn.getChildren().add(iv);
            float tem = res.getFloat("temperature");
            String dt = res.getString("time");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dt);
            PrettyTime pa = new PrettyTime(new Locale("fr"));
            // System.out.println(pa.format(date));

            if (tem > 8) {
                hBox = getDesign("/app/images/h_temp.png", "Température élevée", String.valueOf(pa.format(date)), String.valueOf(tem));

            } else {
                if (tem < 2) {
                    hBox = getDesign("/app/images/l_temp.png", "Température trop basse", String.valueOf(pa.format(date)), String.valueOf(tem));

                } else {
                    hBox = getDesign("/app/images/l_temp.png", "Température normale", String.valueOf(pa.format(date)), String.valueOf(tem));
                }
            }

            VboxlastTeperature.getChildren().add(hBox);
            VboxlastTeperature.getChildren().add(pn);
            y++;
        }
    }

    public void getValueTemperature(ActionEvent event) throws SQLException {
        try {
            String serialTemperature = "";
            if (btnChoiceDashboard.getValue() != null)
                serialTemperature = btnChoiceDashboard.getValue().toString();
            String q = "Select name from device where serial ='" + serialTemperature + "'";
            PreparedStatement p = connection.prepareStatement(q);
            ResultSet res = p.executeQuery();
            String name = "";
            if (res.next())
                name = res.getString("name");

            lblEMI.setText(serialTemperature + " " + name);

            Date WarningFirstSave = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getWarningLastSave(serialTemperature));
            Date EventFirstSave = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(getEventsLastSave(serialTemperature));
            String query = "";
            if(WarningFirstSave.after(EventFirstSave)){
                query = "SELECT * FROM warnning e " +
                        "INNER JOIN device d  on e.id_device = d.id  where d.serial ='" + serialTemperature +
                        "' ORDER BY ID DESC LIMIT 1  ";
            }else{
                query = "SELECT * FROM events e " +
                        "INNER JOIN device d  on e.id_device = d.id  where d.serial ='" + serialTemperature +
                        "' ORDER BY ID DESC LIMIT 1  ";
            }


            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    lblValTemperature1.setText(String.valueOf(resultSet.getFloat("temperature")));
                    lbltemperature.setText(String.valueOf(resultSet.getFloat("temperature") + " °C"));
                    lblDateHeur.setText(String.valueOf(resultSet.getString("Time")).replace(" ", "    à   "));
                } else
                    lbltemperature.setText("- - -");
            } catch (SQLException e) {
                e.printStackTrace();
                lbltemperature.setText("- - -");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getLastTemperature();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getUpload(ActionEvent actionEvent) {
        getViews(pnlUpload, btnUpload, imgUpload);
        btnUpload.setStyle("-fx-text-fill: " + colorStyle);
    }

    public void textAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                //
                //btnDashboard.
                String pass = md.getMd5(txtPassword.getText());
                if (loginModel.isLogin(txtUsername.getText(), pass)) {
                    System.out.println("username and password are correct");

                    Date date = new Date();
                    Prevalent.currentOnlineUser.setLastCctn(date.toString());
                    changeLastConnection(date.toString());

                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = null;
                    root = loader.load(getClass().getResource("/app/views/Home.fxml").openStream());
                    primaryStage.setTitle("Nextrack | Dashboard");
                    primaryStage.getIcons().add(new Image("/app/images/icon.png"));
                    primaryStage.setResizable(false);
                    //primaryStage.setScene(new Scene(root, 1100, 560));
                    primaryStage.initStyle(StageStyle.UNDECORATED);
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });

                    //move around here
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            primaryStage.setX(event.getScreenX() - xOffset);
                            primaryStage.setY(event.getScreenY() - yOffset);
                        }
                    });

                    primaryStage.setScene(new Scene(root, 1090, 550));
                    primaryStage.setOnShown(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                        }
                    });

                    primaryStage.show();
                } else {
                    System.out.println("username or password is not correct");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    alert.setTitle("Erreur d'authentification");
                    alert.setHeaderText(null);
                    alert.setContentText("Nom d'utilisateur et/ou mot de passe sont incorrects !");
                    alert.showAndWait();
                }
            } catch (SQLException throwables) {
                System.out.println("error username and|or password  not correct");
                throwables.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setTitle("Login échouer");
                alert.setContentText("Nom d'utilisateur et | ou mot de passe incorrect !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void getSerialDelete() {
        if (is_firstTime) {
            //AutoDetect
            SerialPort[] portNames = SerialPort.getCommPorts();
            for (int i = 0; i < portNames.length; i++)
                portName = portNames[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            // chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            if (chosenPort.openPort()) {
                String stringOut = "DELETE" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            }
            else {
                portName = "";
                SerialPort[] portNamee = SerialPort.getCommPorts();
                //chosenPort.closePort();
                for (int i = 0; i < portNamee.length; i++)
                    portName = portNamee[i].getSystemPortName();
                chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
                System.out.println("chosenPort " + portName);
                chosenPort.setBaudRate(115200);
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
                String stringOut = "DELETE" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
            }
        } else {
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            chosenPort.setBaudRate(115200);
            String stringOut = "DELETE" + "\n";
            chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
        }
    }

    public void deleteData(ActionEvent event) throws Exception {
        /***************************************************************************************************
         *
         Read data
         *
         */
        // getDateLastSave
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Enregistrement des données");
        // Get the Stage.
        Stage s1 = (Stage) alert1.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        s1.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
        alert1.setContentText("Enregistrement des données \n Veuillez  appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");

        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            getSerialData();

            /***************************************************************************************************
             *
             Read warning
             *
             */
            System.out.println("duration" + duration + 1000);
            //Thread.sleep( duration);
            Alert aw = new Alert(Alert.AlertType.INFORMATION);
            aw.setTitle("Enregistrement des avertissements");
            // Get the Stage.
            Stage sw = (Stage) aw.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            sw.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
            aw.setContentText(" Enregistrement des avertissements \n Veuillez  appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");
            Optional<ButtonType> rw = aw.showAndWait();
            if (rw.get() == ButtonType.OK) {

                getSerialWarnings();
                /***************************************************************************************************
                 *
                 *
                 Delet data
                 *
                 *
                 */
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Attention | Suppression");
                // Get the Stage.

                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();

                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setContentText("êtes-vous sûr de vouloir effacer les données de votre enregistreur? \n " +
                        "Attention !\n " +
                        "Merci de vérifier les enregistrements et les avertissements avant chaque suppression\n " +
                        "sinon vos données seront perdues ");
                //alert.setContentText("Si vous êtes sûr Veuillez  appuyer sur le bouton \"start\" de votre enregistreur et cliquez sur \"OK\"");
                Optional<ButtonType> result1 = alert.showAndWait();
                if (result1.get() == ButtonType.OK) {

                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Attention | Suppression");
                    // Get the Stage.
                    Stage s2 = (Stage) a.getDialogPane().getScene().getWindow();

                    // Add a custom icon.
                    s2.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                    a.setContentText("Si vous êtes sûr Veuillez  appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");

                    Optional<ButtonType> res = alert1.showAndWait();
                    if (res.get() == ButtonType.OK) {
                        getSerialDelete();
                    }
                }
                /**
                 *
                 *
                 *
                 End Of Deleting DATA
                 *
                 *
                 */
            }
        }
        //Thread.sleep(3000);
        //Thread.sleep(3000);
    }

    public void getEventsListCSV(ActionEvent event) throws Exception {
        Writer writer = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer la liste des événements");
            String nameTs = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());

            fileChooser.setInitialFileName("Nextrack_WarningsList_" + nameTs + ".csv");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));

            Stage stage = (Stage) btnCloseApp.getScene().getWindow();

            File file = new File(fileChooser.showSaveDialog(stage) + ".");
            writer = new BufferedWriter(new FileWriter(file));

            String serial = statsSelectDevice.getValue();
            int idD = getIdDevice(serial);
            String query = "select * from warnning where id_device =" + idD + " ";
            int count = 0;

            try {
                PreparedStatement preparedStatement = null;
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                //int lengthTable ;
                // content= new String[resultSet.getFetchSize()][3];
                int index = 0;
                while (resultSet.next()) {

                    String text = resultSet.getString("Time") + "," + String.valueOf(resultSet.getFloat("temperature")) + "," + resultSet.getString("valiseId") + "\n";
                    writer.write(text);

                    //series.getData().add(new XYChart.Data(""+time, resultSet.getFloat("temperature")));
                    index++;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            for (Person person : data) {
//                String text = person.getFirstName() + "," + person.getLastName() + "," + person.getEmail() + "\n";
//                writer.write(text);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public void getEventsListExcel(ActionEvent event) {
        Writer writer = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer la liste des événements");
            String nameTs = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());

            fileChooser.setInitialFileName("Nextrack_WarningsList_" + nameTs + ".xlsx");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx"));

            Stage stage = (Stage) btnCloseApp.getScene().getWindow();

            FileOutputStream file = new FileOutputStream(fileChooser.showSaveDialog(stage) + ".");
            //FileOutputStream fileOut = new FileOutputStream("UserDetails.xlsx");
            //writer = new BufferedWriter(new FileWriter(file));

            String serial = statsSelectDevice.getValue();
            int idD = getIdDevice(serial);
            String query = "select * from warnning where id_device =" + idD + " ";


            try {
                PreparedStatement preparedStatement = null;
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                //int lengthTable ;
                // content= new String[resultSet.getFetchSize()][3];

                //Apache POI Jar Link-
                //http://a.mbbsindia.com/poi/release/bin/poi-bin-3.13-20150929.zip
                XSSFWorkbook wb = new XSSFWorkbook();//for earlier version use HSSF
                XSSFSheet sheet = wb.createSheet("Détails des événements");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("Date Heure");
                header.createCell(1).setCellValue("Température");
                header.createCell(2).setCellValue("Numéro du conteneur");
                // header.createCell(3).setCellValue("Email");
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(0, 256 * 25);//256-character width
                // sheet.setColumnWidth(1, 256*6);
                sheet.setZoom(150);//scale-150%
                int index = 1;
                while (resultSet.next()) {
                    // String text = resultSet.getString("Time") + "," + String.valueOf(resultSet.getFloat("temperature")) + "," + resultSet.getString("valiseId") + "\n";
                    // writer.write(text);
                    XSSFRow row = sheet.createRow(index);

                    row.createCell(0).setCellValue(resultSet.getString("Time"));
                    row.createCell(1).setCellValue(resultSet.getFloat("temperature"));
                    row.createCell(2).setCellValue(resultSet.getString("valiseId"));
                    // row.createCell(3).setCellValue(rs.getString("Email"));
                    index++;
                }
//                FileOutputStream fileOut = new FileOutputStream("UserDetails.xlsx");// befor 2007 version xls
                wb.write(file);
//              fileOut.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Liste des événements ");
                alert.setHeaderText(null);
                // Get the Stage.
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                s.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));
                alert.setContentText(" La liste des avertissements est correctement enregistrée ");
                alert.showAndWait();

                preparedStatement.close();
                resultSet.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    boolean var = true;

    public void changeTime() throws InterruptedException {
        if (is_firstTime) {
            //AutoDetect
            SerialPort[] portNames = SerialPort.getCommPorts();
            for (int i = 0; i < portNames.length; i++)
                portName = portNames[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("portName " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            if (chosenPort.openPort()) {
                System.out.println("if openPort: " + chosenPort.openPort());
                String stringOut = "#CMD_TIME!" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = sdf.format(new Date());
                System.out.println("yyyy-MM-dd'T'HH:mm:ss " + date);
                chosenPort.writeBytes(date.getBytes(), date.getBytes().length);
            }else {
                portName = "";
                SerialPort[] portNamee = SerialPort.getCommPorts();
                //chosenPort.closePort();
                for (int i = 0; i < portNamee.length; i++)
                    portName = portNamee[i].getSystemPortName();
                chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
                System.out.println("chosenPort 1 " + portName);
                chosenPort.setBaudRate(115200);
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
                String stringOut = "#CMD_TIME!" + "\n";
                if(chosenPort.openPort()){

                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = sdf.format(new Date());
                System.out.println("1 yyyy-MM-dd'T'HH:mm:ss " + date);
                chosenPort.writeBytes(date.getBytes(), date.getBytes().length);
                }
            }
        } else {
            System.out.println("else is_firstTime 2 = " + is_firstTime);
            /*chosenPort.clearDTR();
            chosenPort.setRTS();*/
            chosenPort.closePort();
            portName = "";
            SerialPort[] portNames = SerialPort.getCommPorts();
            for (int i = 0; i < portNames.length; i++)
                portName = portNames[i].getSystemPortName();
            chosenPort = SerialPort.getCommPort(portName != "" ? portName : "COM6");
            System.out.println("portName " + portName);
            chosenPort.setBaudRate(115200);
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 100, 100);
            if (chosenPort.openPort()) {
                System.out.println("if openPort:2 " + chosenPort.openPort());
                String stringOut = "#CMD_TIME!" + "\n";
                chosenPort.writeBytes(stringOut.getBytes(), stringOut.getBytes().length);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = sdf.format(new Date());
                System.out.println("2 yyyy-MM-dd'T'HH:mm:ss " + date);
                chosenPort.writeBytes(date.getBytes(), date.getBytes().length);
            }
        }
        is_firstTime = false;
    }

    public void TimeInput() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        // Get the Stage.
        Stage q = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        q.getIcons().add(new Image(this.getClass().getResource("/app/images/icon.png").toString()));

        alert.setContentText("Veuillez appuyer sur le bouton \"check\" de votre enregistreur et cliquez sur \"OK\"");
        Optional<ButtonType> result1 = alert.showAndWait();
        if (result1.get() == ButtonType.OK) {
            changeTime();
        } else {

        }
    }

    //check the Time format
    public boolean parseTime(String time, String format) {
        /**
         *
         * "h:m:s"
         */
        // DateFormat sdf = new SimpleDateFormat(format);
        //  sdf.setLenient(false);
        DateTimeFormatter strictTimeFormatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalTime l = LocalTime.parse(time, strictTimeFormatter);
            System.out.println("LocalTime " + l);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //check the date format
    public boolean parseDate(String date, String format) {
        String dateFormat = format;
        /**
         * MM/dd/yyyy
         */
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    

    public void checkTimeI(ActionEvent event) {
    }

    public void checkTimeF(ActionEvent event) {
    }
}
