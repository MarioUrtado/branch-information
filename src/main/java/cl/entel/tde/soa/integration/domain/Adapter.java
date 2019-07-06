package cl.entel.tde.soa.integration.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Adapter {

    private Target target;

    public Adapter() {
    }

    public Adapter(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }


}
