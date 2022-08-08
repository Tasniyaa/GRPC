package User;

import com.yrrhelp.grpc.User;
import com.yrrhelp.grpc.userGrpc.userImplBase;
import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserService extends userImplBase{
    String url = "jdbc:mysql://localhost:3306/ndb";
    String user = "root";
    String pass = "nishat78900*#";
    @Override
    public void register(User.Registration request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside register");
        String username = request.getUsername();
        int age = request.getAge();
        String password = request.getPassword();
        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("DB Connection Is Successful");
            String query = "INSERT INTO `ndb`.`user` (`UserName`, `UserAge`, `UserPass`) VALUES (\'"+username+ "\',\'"+ age + "\',\'" + password +"\')";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ndb.user where UserName=\""+ username + "\" and UserAge = \""+age+"\"");
            if(resultSet.next()){
                response.setResponseCode(400).setResponsemessage("User Already Exists!");
            }
            else
            {
                statement.executeUpdate(query);
                response.setResponseCode(200).setResponsemessage("User Registration is Successful");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }


    @Override
    public void login(User.LoginRequest request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside register");
        String username = request.getUsername();
        String password = request.getPassword();
        System.out.println(username);
        System.out.println(password);
        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("DB Connection Is Successful");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ndb.user where UserName=\""+ username + "\"");
            if(resultSet.next())
            {
                String name = resultSet.getString("UserName");
                String pass = resultSet.getString("UserPass");

                if(username.equals(name) && password.equals(pass)){
                    response.setResponseCode(200).setResponsemessage("Successfully Logged in");
                }
                else {
                    response.setResponseCode(400).setResponsemessage("Invalid Email or Password");
                }
            }
            else
            {
                response.setResponseCode(401).setResponsemessage("Invalid User!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(User.Empty request, StreamObserver<User.APIResponse> responseObserver) {
    }
}

