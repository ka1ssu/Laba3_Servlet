package com.example.laba3;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet(name = "CarsServlet", value = "/CarsServlet")
public class CarsServlet extends HttpServlet {

    public void init() {
    }

    private static final String filePath = "cars.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        String color = request.getParameter("color");
        int price = Integer.parseInt(request.getParameter("price"));

        JSONObject car = new JSONObject();
        car.put("brand", brand);
        car.put("model", model);
        car.put("mileage", mileage);
        car.put("color", color);
        car.put("price", price);

        JSONArray carsList = new JSONArray();

        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            if (file.exists()) {
                carsList = (JSONArray) parser.parse(new FileReader(filePath));
            }
            carsList.add(car);
            System.out.println("Cars List: " + carsList);
            FileWriter fileWriter = new FileWriter(filePath);

            fileWriter.write(carsList.toJSONString());
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Laba3_war_exploded/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JSONParser parser = new JSONParser();
            JSONArray carsList = (JSONArray) parser.parse(new FileReader(filePath));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body><div><table class=\"table\"><thead><tr><th scope=\"col\">Марка</th><th scope=\"col\">Модель</th><th scope=\"col\">Пробег</th> <th scope=\"col\">Цвет</th><th scope=\"col\">Цена</th></tr></thead>");
            for (Object obj : carsList) {
                JSONObject car = (JSONObject) obj;
                out.println("<tbody> <tr><td>" + car.get("brand") + "</td><td>" + car.get("model") + "</td><td>" + car.get("mileage") + "</td><td>" + car.get("color") + "</td><td>" + car.get("price") + "</td>");
            }

            out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}