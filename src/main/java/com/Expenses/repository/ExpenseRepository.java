package com.project.Expenses.repository;

import com.project.Expenses.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByAssignee(String assignee);
}
