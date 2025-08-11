package com.project.Expenses.controller;

import com.project.Expenses.model.Expense;
import com.project.Expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // List expenses based on role
    @GetMapping
    public String listExpenses(Model model, Authentication authentication) {
        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Expense> expenses;
        if (isAdmin) {
            expenses = expenseService.getAllExpenses();
        } else {
            expenses = expenseService.getExpensesByAssignee(username);
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("isAdmin", isAdmin);
        return "expense_list"; // Thymeleaf template
    }

    // Show form to create expense
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "new_expense";
    }

    // Create expense
    @PostMapping
    public String createExpense(@ModelAttribute Expense expense, Authentication authentication) {
        expense.setAssignee(authentication.getName());
        expenseService.createExpense(expense);
        return "redirect:/expenses";
    }

    // Edit expense
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable String id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "edit_expense";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable String id, @ModelAttribute Expense updatedExpense) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            expense.setName(updatedExpense.getName());
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setStatus(updatedExpense.getStatus());
            expenseService.createExpense(expense);
        }
        return "redirect:/expenses";
    }

    // Delete expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
