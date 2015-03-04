package de.tuberlin.cit.sdn.middleware.flowSwitching.model;


public class Link {

    private String switch1Id;
    private String switch2Id;

    public Link(String switch1Id, String switch2Id){
        this.switch1Id = switch1Id;
        this.switch2Id = switch2Id;
    }

    public boolean links(String switchId){
        return switchId.equals(this.switch1Id) || switchId.equals(this.switch2Id);
    }

}
