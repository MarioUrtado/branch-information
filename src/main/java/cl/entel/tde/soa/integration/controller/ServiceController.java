package cl.entel.tde.soa.integration.controller;

import cl.entel.tde.soa.integration.domain.Service;
import cl.entel.tde.soa.integration.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Service> find(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "operation", required = false) String operation, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "version", required = false) String version){
        return serviceService.find(name, operation, code, version);
    }

}
