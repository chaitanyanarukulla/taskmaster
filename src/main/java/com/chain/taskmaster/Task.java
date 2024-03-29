package com.chain.taskmaster;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;


@DynamoDBTable(tableName = "Taskmaster")
public class Task {
    private String id;
    private String title;
    private String description;
    private String status;
    private String pic;
    private String repic;


    @DynamoDBAttribute
    private String assignee;


    public Task() {}

    public Task(String pic){this.pic = pic;}

    public Task(String title, String description, String status, String assignee){
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
    }

    public Task(String title, String description, String status, String assignee, String pic){
        this(title,description,status,assignee);
        this.pic = pic;
    }

    public Task(String title, String description, String status, String assignee, String pic,String repic){
        this(title,description,status,assignee,pic);
        this.repic = repic;
    }


    @DynamoDBAttribute
    public String getRepic() {
        return repic;
    }

    public void setRepic(String repic) {
        this.repic = repic;
    }
    @DynamoDBAttribute
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}