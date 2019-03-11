/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.view;

import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.common.entity.User_Status;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.cfg.DatabaseConnection;
import com.imagine.chattingapp.server.dal.dao.CountryDAO;
import com.imagine.chattingapp.server.dal.dao.User_StatusDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Saror Mohamed
 */
public class DrawerViewController implements Initializable {
    
    @FXML
    private JFXButton btnStatus;
    @FXML
    private JFXButton btnStart;
    @FXML
    private JFXButton btnGender;
    @FXML
    private JFXButton btnCountry;
    @FXML
    private RadioButton radioBar;
    @FXML
    private RadioButton radioPie;
    @FXML
    private ImageView imgStatistics;
    @FXML
    private ToggleGroup radioGroup;
    @FXML
    private JFXButton btnRegisterUser;
    @FXML
    private JFXButton btnAnnounce;
    
    private StatisticsViewController statisticsViewController;
    private BarChart barChart;
    private PieChart pieChart;
    private DatabaseConnection base;
    private Connection connection;
    private Double width;
    private Double height;
    private AnchorPane pane;
    private MainController mainController;

    public DrawerViewController(MainController mainController, StatisticsViewController statisticsViewController) {
        this.mainController = mainController;
        this.statisticsViewController = statisticsViewController;
        this.width = statisticsViewController.getPnStackPane().getWidth();
        this.height = statisticsViewController.getPnStackPane().getHeight();
        
    }
   
//    public void setController(StatisticsViewController statisticsViewController) {
//        this.statisticsViewController = statisticsViewController;
//        this.width = statisticsViewController.getPnStackPane().getWidth();
//        this.height = statisticsViewController.getPnStackPane().getHeight();
//        
//    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView barImg = new ImageView();
        barImg.setImage(new Image(getClass().getResource("/bar.png").toString()));
        ImageView pieImg = new ImageView();
        pieImg.setImage(new Image(getClass().getResource("/pie.png").toString()));
        
        radioBar.setGraphic(barImg);
        radioPie.setGraphic(pieImg);
        
        Image img = new Image(getClass().getResource("/good.jpg").toString());
        imgStatistics.setSmooth(true);
        imgStatistics.setImage(img);
        
        base = new DatabaseConnection();
        try {
            connection = base.Connect();
        } catch (SQLException ex) {
            Logger.getLogger(DrawerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnCountry.setOnAction((event) -> {
            
            statisticsViewController.getPnStackPane().getChildren().clear();
            pane = new AnchorPane();
            pane.setPrefSize(width, height);
            
            List<Country> countryList = new ArrayList<>();
            CountryDAO country = null;
            try {
                country = new CountryDAO();
                countryList = country.getAll();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            if (radioBar.isSelected()) {
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart barChart = new BarChart(xAxis, yAxis);
                barChart.setTitle("Country Summary");
                xAxis.setLabel("Country");
                yAxis.setLabel("Users");
                
                XYChart.Series dataSeries = new XYChart.Series();
                dataSeries.setName("Countries in Chat");
                
                for (int i = 0; i < countryList.size(); i++) {
                    try {
                        
                        List<Object> countryIDList = new ArrayList();
                        countryIDList.add(i + 1);
                        Country countryDetails = country.getByPrimaryKey(countryIDList);
                        
                        PreparedStatement usersCountCountry = connection.prepareStatement("Select count(Phone_Number) from user where Country_ID = ?");
                        usersCountCountry.setInt(1, countryDetails.getId());
                        ResultSet usersCountResultSet = usersCountCountry.executeQuery();
                        
                        usersCountResultSet.first();
                        
                        dataSeries.getData().add(new XYChart.Data(countryDetails.getName(), usersCountResultSet.getInt(1)));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                barChart.getData().add(dataSeries);
                
                pane.getChildren().add(barChart);
                statisticsViewController.getPnStackPane().getChildren().add(pane);
                
            } else if (radioPie.isSelected()) {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                
                for (int i = 0; i < countryList.size(); i++) {
                    try {
                        
                        List<Object> countryIDList = new ArrayList();
                        countryIDList.add(i + 1);
                        Country countryDetails = country.getByPrimaryKey(countryIDList);
                        
                        PreparedStatement usersCountCountry = connection.prepareStatement("Select count(Phone_Number) from user where Country_ID = ?");
                        usersCountCountry.setInt(1, countryDetails.getId());
                        ResultSet usersCountResultSet = usersCountCountry.executeQuery();
                        
                        usersCountResultSet.first();
                        pieChartData.add(new PieChart.Data(countryDetails.getName(), usersCountResultSet.getInt(1)));
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    
                }
                PieChart piechart = new PieChart(pieChartData);
                piechart.setTitle("Status in Chat");
                
                piechart.setLabelLineLength(10);
                piechart.setLabelsVisible(true);
                piechart.setStartAngle(180);
                piechart.setLegendSide(Side.RIGHT);
                
                pane.getChildren().add(piechart);
                statisticsViewController.getPnStackPane().getChildren().add(pane);
                
            }
        });
        
        btnStatus.setOnAction((event) -> {
            
            statisticsViewController.getPnStackPane().getChildren().clear();
            pane = new AnchorPane();
            pane.setPrefSize(width, height);
            
            List<User_Status> requestList = new ArrayList<>();
            User_StatusDAO user_StatusDAO = null;
            try {
                user_StatusDAO = new User_StatusDAO();
                requestList = user_StatusDAO.getAll();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            if (radioBar.isSelected()) {
                try {
                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis();
                    barChart = new BarChart(xAxis, yAxis);
                    barChart.setTitle("Status Summary");
                    xAxis.setLabel("Status");
                    yAxis.setLabel("Users");
                    
                    XYChart.Series dataSeries = new XYChart.Series();
                    dataSeries.setName("Status in Chat");
                    
                    for (int i = 0; i < requestList.size(); i++) {
                        
                        List<Object> requestIDList = new ArrayList();
                        requestIDList.add(i);
                        User_Status requestStatusDetails = user_StatusDAO.getByPrimaryKey(requestIDList);
                        
                        PreparedStatement usersCountStatus = connection.prepareStatement("Select count(Phone_Number) from user where Status_ID = ?");
                        usersCountStatus.setInt(1,requestStatusDetails.getId());
                        ResultSet usersCountResultSet = usersCountStatus.executeQuery();
                        
                        usersCountResultSet.first();
                        
                        dataSeries.getData().add(new XYChart.Data(requestStatusDetails.getDescription(), usersCountResultSet.getInt(1)));
                    }
                    barChart.getData().add(dataSeries);
                    
                    pane.getChildren().add(barChart);
                    statisticsViewController.getPnStackPane().getChildren().add(pane);
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (radioPie.isSelected()) {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                for (int i = 0; i < requestList.size(); i++) {
                    try {
                        List<Object> requestIDList = new ArrayList();
                        requestIDList.add(i);
                        User_Status requestStatusDetails = user_StatusDAO.getByPrimaryKey(requestIDList);
                        
                        PreparedStatement usersCountStatus = connection.prepareStatement("Select count(Phone_Number) from user where Status_ID = ?");
                        usersCountStatus.setInt(1, i);
                        ResultSet usersCountResultSet = usersCountStatus.executeQuery();
                        
                        usersCountResultSet.first();
                        
                        pieChartData.add(new PieChart.Data(requestStatusDetails.getDescription(), usersCountResultSet.getInt(1)));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                PieChart piechart = new PieChart(pieChartData);
                piechart.setTitle("Status in Chat");
                
                piechart.setLabelLineLength(10);
                piechart.setLabelsVisible(true);
                piechart.setStartAngle(180);
                piechart.setLegendSide(Side.RIGHT);
                
                pane.getChildren().add(piechart);
                statisticsViewController.getPnStackPane().getChildren().add(pane);
            }
        });
        
        btnGender.setOnAction((event) -> {
            statisticsViewController.getPnStackPane().getChildren().clear();
            pane = new AnchorPane();
            pane.setPrefSize(width, height);
            
            if (radioBar.isSelected()) {
                try {
                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis();
                    BarChart barChart = new BarChart(xAxis, yAxis);
                    barChart.setTitle("Gender Summary");
                    xAxis.setLabel("Gender Type");
                    yAxis.setLabel("Users");
                    
                    XYChart.Series dataSeries = new XYChart.Series();
                    dataSeries.setName("Gender in Chat");
                    
                    for (int i = 0; i < 2; i++) {
                        PreparedStatement genderCountCountry = connection.prepareStatement("Select count(Phone_Number) from user where Gender = ?");
                        genderCountCountry.setInt(1, i);
                        ResultSet genderCountResultSet = genderCountCountry.executeQuery();
                        
                        genderCountResultSet.first();
                        
                        String genderType = "";
                        if (i == 1) {
                            genderType = "Male";
                        } else if (i == 0) {
                            genderType = "Female";
                        }
                        
                        dataSeries.getData().add(new XYChart.Data(genderType, genderCountResultSet.getInt(1)));
                    }
                    barChart.getData().add(dataSeries);
                    
                    pane.getChildren().add(barChart);
                    statisticsViewController.getPnStackPane().getChildren().add(pane);
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (radioPie.isSelected()) {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                
                for (int i = 0; i < 2; i++) {
                    try {
                        PreparedStatement genderCountCountry = connection.prepareStatement("Select count(Phone_Number) from user where Gender = ?");
                        genderCountCountry.setInt(1, i);
                        ResultSet genderCountResultSet = genderCountCountry.executeQuery();
                        
                        genderCountResultSet.first();
                        
                        String genderType = "";
                        if (i == 1) {
                            genderType = "Male";
                        } else if (i == 0) {
                            genderType = "Female";
                        }
                        pieChartData.add(new PieChart.Data(genderType, genderCountResultSet.getInt(1)));
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    
                }
                PieChart piechart = new PieChart(pieChartData);
                piechart.setTitle("Gender in Chat");
                
                piechart.setLabelLineLength(10);
                piechart.setLabelsVisible(true);
                piechart.setStartAngle(180);
                piechart.setLegendSide(Side.RIGHT);
                
                pane.getChildren().add(piechart);
                statisticsViewController.getPnStackPane().getChildren().add(pane);
                
            }
        });
        btnStart.setOnAction((event) -> {
            statisticsViewController.getPnStackPane().getChildren().clear();
            statisticsViewController.loadService();
        });
        btnRegisterUser.setOnAction((event) -> {
            mainController.switchToRegisterScence();
        });
        btnAnnounce.setOnAction((event) -> {
            try {
                statisticsViewController.getPnStackPane().getChildren().clear();
                FXMLLoader loader = new FXMLLoader();
                AnnouncementController announcementController = new AnnouncementController(mainController);
                loader.setController(announcementController);
                AnchorPane root = loader.load(getClass().getResource("/Announcement.fxml").openStream());
                pane = root;
                pane.setPrefSize(width, height);
                statisticsViewController.getPnStackPane().getChildren().add(pane);
            } catch (IOException ex) {
                Logger.getLogger(DrawerViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
