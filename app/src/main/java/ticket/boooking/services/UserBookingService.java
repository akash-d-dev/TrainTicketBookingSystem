package ticket.boooking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.boooking.entities.Train;
import ticket.boooking.entities.User;
import ticket.boooking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();



    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDB/user.json";

    public UserBookingService() throws IOException {
        loadUsers(); // Load users from file
    };
    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers(); // Load users from file
    }

    public List<Train> getTrains(String source,String  destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination) ;
        }
        catch (IOException ex){
            return new ArrayList<>();
        }
    }
    public List<User> loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
        Optional<User> foundUser = userList.stream().filter(user1 -> user1.getUserId().equalsIgnoreCase(user.getUserId())).findFirst();
        if (foundUser.isPresent()){
            User user1 = foundUser.get();
            List<User> finalUserList = userList;
            user1.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equalsIgnoreCase(ticketId));
            finalUserList.removeIf(user2 -> user2.getUserId().equalsIgnoreCase(user1.getUserId()));
            finalUserList.add(user1);
            try {
                saveUserListToFile();
                return Boolean.TRUE;
            } catch (IOException ex){
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }
}
