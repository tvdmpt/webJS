package com.example.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping({"/calculator", "/calc"})
    public String calculator() {
        return "calculator";
    }

    @PostMapping("/calculator/result")
    public String calculate(
            @RequestParam("num1") double num1,
            @RequestParam("num2") double num2,
            @RequestParam("operation") String operation,
            Model model) {

        double result = 0;
        String error = null;

        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 == 0) {
                    error = "Деление на ноль невозможно!";
                } else {
                    result = num1 / num2;
                }
                break;
        }

        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);
        model.addAttribute("error", error);

        return "calculator-result";
    }

    @GetMapping("/converter")
    public String converter() {
        return "converter";
    }

    @PostMapping("/converter/result")
    public String convert(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") double amount,
            Model model) {

        double result = 0;
        double rate = getExchangeRate(fromCurrency, toCurrency);
        result = amount * rate;

        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("rate", rate);

        return "converter-result";
    }

    private double getExchangeRate(String from, String to) {
        double fromRate = getRateValue(from);
        double toRate = getRateValue(to);
        return toRate / fromRate;
    }

    private double getRateValue(String currency) {
        switch (currency) {
            case "USD": return 1.0;
            case "EUR": return 0.92;
            case "RUB": return 90.5;
            case "GBP": return 0.79;
            default: return 1.0;
        }
    }
}