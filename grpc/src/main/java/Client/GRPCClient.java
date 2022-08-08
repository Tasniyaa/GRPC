package Client;

import com.yrrhelp.grpc.User;
import com.yrrhelp.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class GRPCClient {
    public static void main(String[] args) {

        ManagedChannel channel =ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        //stubs
        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);
        String username, password;
        int age;
        boolean isLoggedIn = false;
        Scanner input = new Scanner(System.in);
        System.out.println("1. Registration");
        System.out.println("2. Login");
        System.out.println("3. Log Out");
        int choice = input.nextInt();
        if(choice == 1)
        {
            System.out.println("Register Here ");
            System.out.print("Username: ");
            username = input.next();
            System.out.print("age: ");
            age = input.nextInt();
            System.out.print("Password: ");
            password = input.next();
            User.Registration registration = User.Registration.newBuilder().setUsername(username).setAge(age).setPassword(password).build();
            User.APIResponse response = userStub.register(registration);
            if(response.getResponseCode() == 200){
                isLoggedIn = true;
            }
            System.out.println(response.getResponsemessage());
        }
        else if(choice == 2)
        {
            System.out.println("login Here ");
            System.out.print("Username: ");
            username = input.next();
            System.out.print("Password: ");
            password = input.next();
            User.LoginRequest loginReq = User.LoginRequest.newBuilder().setUsername(username).setPassword(password).build();
            User.APIResponse response = userStub.login(loginReq);
            System.out.println(response.getResponsemessage());
        }
        else if(choice == 3)
        {
            if(isLoggedIn)
            {
                System.out.println("log out");
            }
            else
            {
                System.out.println("Please Log In First!");
            }
        }
        else
        {
            System.out.println("Wrong choice!");
            System.out.println("Please Type Between 1, 2 and 3");
        }
    }
}

