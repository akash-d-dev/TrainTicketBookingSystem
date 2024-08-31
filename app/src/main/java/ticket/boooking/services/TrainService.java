package ticket.boooking.services;

import ticket.boooking.entities.Train;

import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    public List<Train> searchTrains(String source, String destination){
    return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    public boolean validTrain(Train train, String source, String destination){
       List<String> stationsOrder = train.getStations();
       int sourceIndex = stationsOrder.indexOf(source.toLowerCase());
       int destinationIndex = stationsOrder.indexOf(destination.toLowerCase());

       return sourceIndex!=-1 && destinationIndex!=-1 && sourceIndex<destinationIndex; //destination should come after source
    }
}
