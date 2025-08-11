package com.project.Expenses.service;

import com.project.Expenses.model.Expense;
import com.project.Expenses.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByAssignee(String assignee) {
        return expenseRepository.findByAssignee(assignee);
    }

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    public Expense getExpenseById(String id) {
        return expenseRepository.findById(id).orElse(null);
    }
}
