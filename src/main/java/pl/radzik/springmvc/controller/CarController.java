package pl.radzik.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import pl.radzik.springmvc.domain.Car;
import pl.radzik.springmvc.service.CarService;

import java.util.Optional;


@Controller
public class CarController {

private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("newCar", new Car());
        return  "cars";
    }



    @GetMapping(value = "/id")
    public String getCarById (@ModelAttribute("id") String id, Model model) {
        System.out.println(id);
        model.addAttribute("id", id);
        Optional<Car> carfounded = carService.getCarById(Integer.valueOf(id));

        if(carfounded.isPresent()) {
            model.addAttribute("car", carfounded.get());
        }
        return "updateCar";
    }

    @PostMapping(value = "/newCar")
    public String addCar (@ModelAttribute("car") Car car) {
        long count = carService.getAllCars().size();
        car.setId((int) ++count);
        carService.addCar(car);
            return "redirect:/cars";
    }

    @PostMapping(value = "/updateCar")
    public String updateCar (@ModelAttribute("car") Car car) {
        Optional<Car> carFound = carService.getCarById(car.getId());
        if(carFound.isPresent()){
            carService.removeCar(carFound.get());
            car.setId(carFound.get().getId());
            carService.addCar(car);
        }
        return "redirect:/cars";
    }

    @PostMapping(value = "/removeCar")
    public String removeCar (@ModelAttribute("id") String id) {
        Optional<Car> carFound = carService.getCarById(Integer.valueOf(id));
        if(carFound.isPresent()){
            carService.removeCar(carFound.get());
        }
        return "redirect:/cars";
    }

}
