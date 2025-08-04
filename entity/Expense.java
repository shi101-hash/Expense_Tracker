package com.project.Expenses.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;
    private String name;
    private String description;
    private double amount;
    private String date;
    private String assignee; // user who added this expense
    private String status;
}