package com.project.Expenses.controller;

import com.project.Expenses.model.Expense;
import com.project.Expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // List all expenses
    @GetMapping
    public String listExpenses(Model model,
                               @RequestParam(required = false) String username,
                               @RequestParam(required = false) String role) {

        boolean isAdmin = "ADMIN".equalsIgnoreCase(role);

        List<Expense> expenses;
        if (isAdmin) {
            expenses = expenseService.getAllExpenses();
        } else if (username != null && !username.isEmpty()) {
            expenses = expenseService.getExpensesByAssignee(username);
        } else {
            expenses = List.of(); // empty list if no user provided
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("isAdmin", isAdmin);
        return "expense_list";
    }

    // Show form to create expense
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "new_expense";
    }

    // Create expense
    @PostMapping
    public String createExpense(@ModelAttribute Expense expense,
                                @RequestParam String username) {
        expense.setAssignee(username);
        expenseService.createExpense(expense);
        return "redirect:/expenses?username=" + username;
    }

    // Edit expense
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable String id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "edit_expense";
    }

    // Update expense
    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable String id,
                                @ModelAttribute Expense updatedExpense,
                                @RequestParam String username) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            expense.setName(updatedExpense.getName());
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setStatus(updatedExpense.getStatus());
            expenseService.createExpense(expense);
        }
        return "redirect:/expenses?username=" + username;
    }

    // Delete expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable String id,
                                @RequestParam String username) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses?username=" + username;
    }
}
