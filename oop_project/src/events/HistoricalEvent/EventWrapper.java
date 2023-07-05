package HistoricalEvent;

import ExternalObj.Dynasty;
import ExternalObj.Figure;

import java.util.ArrayList;

public class EventWrapper extends Event {
    public EventWrapper(){
        super();
        this.relatedFigsDetails = new ArrayList<>();
    }
    public EventWrapper(ArrayList<Figure> relatedFigsDetails){
        this.relatedFigsDetails = relatedFigsDetails;
    }
    public Dynasty getDynastyDetails() {
        return dynastyDetails;
    }

    public void setDynastyDetails(Dynasty dynasty) {
        this.dynastyDetails = dynasty;
    }

    public ArrayList<Figure> getRelatedFigsDetails() {
        return relatedFigsDetails;
    }

    public void setRelatedFigsDetails(ArrayList<Figure> relatedFigsDetails) {
        this.relatedFigsDetails = relatedFigsDetails;
    }

    private ArrayList<Figure> relatedFigsDetails;
    private Dynasty dynastyDetails;

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder(this.getId()  + " " + this.getName() + " " + this.getTime() + " " + this.getLocation()
                + " " + this.getDescription());
        this.getRelatedFigsDetails().forEach(f ->{
            str.append(f.toString());
        });
        str.append(this.getDynastyDetails().toString());
        return str.toString();
    }
}
