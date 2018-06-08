package kojo.mything;

public class ItemObject {
    private String id;
    private String name;
    private String datavalue;
    private String imageResource;
    private String topic;
    private String zone;
    private String type;
    private Boolean actor;
    private String publishtopic;
    private String status;

    public ItemObject(String id, String name, String datavalue, String imageResource, String topic, String zone, String type, Boolean actor, String publishtopic, String status) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.datavalue = datavalue;
        this.topic = topic;
        this.zone = zone;
        this.type = type;
        this.actor = actor;
        this.publishtopic = publishtopic;
        this. status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataValue() {
        return datavalue;
    }

    public void setDataValue(String datavalue) {
        this.datavalue = datavalue;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActor(){
        return actor;
    }

    public void setActor(Boolean actor){
        this.actor = actor;
    }

    public String getPublishTopic(){
        return publishtopic;
    }

    public void setPublishTopic(String publishtopic){
        this.publishtopic = publishtopic;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
