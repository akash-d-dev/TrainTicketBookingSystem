package ticket.boooking.services;

import ticket.boooking.entities.Train;

import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    public List<Train> searchTrain(String source, String destination, String dateOfTravel){
    return trainList.stream().filter(train -> train.getStations().contains(source) && train.getStations().contains(destination)).collect(Collectors.toList());
    }
}
