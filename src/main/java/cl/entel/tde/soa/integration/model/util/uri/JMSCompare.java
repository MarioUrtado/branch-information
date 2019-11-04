package cl.entel.tde.soa.integration.model.util.uri;

import cl.entel.tde.soa.integration.domain.Uri;

public class JMSCompare implements TransportCompare {
    @Override
    public boolean equals(Uri source, Uri destination) {
        String sourcePath = source.getPath().replace("/.", "/");
        String destinationPath = destination.getPath().replace("/.", "/");
        return sourcePath.equals(destinationPath);
    }
}
