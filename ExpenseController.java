package com.project.Expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.Expenses.model.Expense;
import com.project.Expenses.model.User;
import com.project.Expenses.service.ExpenseService;
import com.project.Expenses.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    // @Autowired
    ExpenseService expenseService = new ExpenseService();

    @Autowired
    private UserService userService;

    @GetMapping
    public String welcomePage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";

        }
        return "welcome";
    }

    @GetMapping("/list")
    public String viewExpenses(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "expense_list";
    }

    @GetMapping("/new")
    public String newExpenseForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("expense", new Expense());
        return "new_expense";
    }

    @PostMapping
    public String createExpense(@ModelAttribute Expense expense, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }
            expenseService.createExpense(expense);
            return "redirect:/expenses";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while adding the expense.");
            return "new_expense";
        }
    }

    @GetMapping("/update/{id}")
    public String updateExpense(@PathVariable String id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "new_expense";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable String id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        expenseService.deleteExpense(id);
        return "redirect:/expenses/list";

    }
}
