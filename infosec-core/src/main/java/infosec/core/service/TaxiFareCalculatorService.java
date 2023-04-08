package infosec.core.service;

import infosec.core.model.Fare;
import infosec.core.model.TaxiRide;
import jakarta.annotation.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class TaxiFareCalculatorService {

    @Resource
    private KieContainer kieContainer;

    public Long calculateFare(TaxiRide taxiRide, Fare rideFare) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rideFare.getTotalFare();
    }
}
