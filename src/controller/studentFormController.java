package controller;

import Db.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import view.tm.studentTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class studentFormController implements Initializable {
    public JFXTextField txtStudentID;
    public JFXTextField txtStudentName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public TableView<studentTm> tblStudent;
    public TableColumn colStuID;
    public TableColumn colStuName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colStuID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStuName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        loadAllStudent();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtStudentID.setText(newValue.getStudentID());
            txtStudentName.setText(newValue.getStudentName());
            txtEmail.setText(newValue.getEmail());
            txtContact.setText(newValue.getContact());
            txtContact.setText(newValue.getAddress());
            txtNic.setText(newValue.getNic());
        });

    }

    public void btnSave(ActionEvent actionEvent) {
        Student student = new Student(txtStudentID.getText(),txtStudentName.getText(),txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNic.getText());

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student" + " VALUES(?,?,?,?,?,?)");
            preparedStatement.setObject(1,student.getStudentID());
            preparedStatement.setObject(2,student.getStudentName());
            preparedStatement.setObject(3,student.getEmail());
            preparedStatement.setObject(4,student.getContact());
            preparedStatement.setObject(5,student.getAddress());
            preparedStatement.setObject(6,student.getNic());

            int save = preparedStatement.executeUpdate();
            if (save > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK).show();
                loadAllStudent();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.OK).show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Student SET "+"student_name=?, email=?, contact=?, address=?, nic=?"+"WHERE student_id=?");
            preparedStatement.setObject(1,txtStudentName.getText());
            preparedStatement.setObject(2,txtEmail.getText());
            preparedStatement.setObject(3,txtContact.getText());
            preparedStatement.setObject(4,txtAddress.getText());
            preparedStatement.setObject(5,txtNic.getText());
            preparedStatement.setObject(6,txtStudentID.getText());

            int update = preparedStatement.executeUpdate();
            if (update > 0){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated",ButtonType.OK).show();
                loadAllStudent();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try agian",ButtonType.OK).show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Student WHERE student_id=?");
            preparedStatement.setObject(1,txtStudentID.getText());
            int delete = preparedStatement.executeUpdate();
            if (delete > 0){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted",ButtonType.OK).show();
                loadAllStudent();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again",ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    public void btnSearch(ActionEvent actionEvent) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student WHERE student_id=?");
            preparedStatement.setObject(1,txtStudentID.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                txtStudentName.setText(resultSet.getString(2));
                txtEmail.setText(resultSet.getString(3));
                txtContact.setText(resultSet.getString(4));
                txtAddress.setText(resultSet.getString(5));
                txtNic.setText(resultSet.getString(6));
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllStudent(){
        ArrayList<Student> students = new ArrayList<>();
        ObservableList<studentTm> observableList = FXCollections.observableArrayList();

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student");
            ResultSet rst = preparedStatement.executeQuery();

            while (rst.next()){
                students.add(new Student(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                ));
            }
            for (Student stu : students){
                observableList.add(new studentTm(stu.getStudentID(), stu.getStudentName(), stu.getEmail(),stu.getContact(), stu.getAddress(),stu.getNic()));

            }
            tblStudent.setItems(observableList);


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
