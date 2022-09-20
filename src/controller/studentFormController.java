package controller;

import Db.DbConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Student;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class studentFormController implements Initializable {
    public JFXTextField txtStudentID;
    public JFXTextField txtStudentName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public TableView tblStudent;
    public TableColumn colStuID;
    public TableColumn colStuName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again",ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    public void btnSearch(ActionEvent actionEvent) {
    }



}
