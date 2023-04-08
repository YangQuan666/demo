package infosec.core.controller;

import infosec.core.model.Fare;
import infosec.core.model.TaxiRide;
import infosec.core.service.TaxiFareCalculatorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/taxi")
public class TaxiController {

    @Resource
    private TaxiFareCalculatorService taxiFareCalculatorService;

    @GetMapping("/{distanceInMile}/{isNightSurcharge}")
    public Long getCharge(@PathVariable Long distanceInMile, @PathVariable Boolean isNightSurcharge) {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(isNightSurcharge);
        taxiRide.setDistanceInMile(distanceInMile);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        return totalCharge;
    }
}
